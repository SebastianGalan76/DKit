package pl.dream.dkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.dream.dkit.data.LocalPlayer;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class SQLite {
    public Connection con;

    public void connect(){
        File dataFolder = new File(DKit.getPlugin().getDataFolder(), "kit.db");

        if (!dataFolder.exists()){
            new File(DKit.getPlugin().getDataFolder().getPath()).mkdir();
        }

        String URL = "jdbc:sqlite:"+dataFolder;

        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(URL);
            createTables();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void disconnect(){
        try{
            con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void createTables(){
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS PlayerKits(ID INTEGER PRIMARY KEY AUTOINCREMENT, UUID varchar(60), KitName varchar(15), Delay DATETIME);");
            ps.execute();

            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void loadDelay(LocalPlayer player){
        JavaPlugin plugin = DKit.getPlugin();

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                HashMap<String, Long> kitDelays = new HashMap<>();
                String kitName;
                long delayLeft = 0;

                PreparedStatement ps = null;
                try{
                    ps = con.prepareStatement("SELECT STRFTIME('%s',Delay) DelayLeft, KitName FROM PlayerKits WHERE UUID = ?");
                    ps.setString(1, player.getUUID().toString());

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        kitName = rs.getString("KitName");
                        delayLeft = rs.getLong("DelayLeft");

                        if(DKit.getPlugin().kits.containsKey(kitName)){
                            kitDelays.put(kitName, delayLeft);
                        }
                    }

                    rs.close();
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }

                player.loadDelays(kitDelays);
            }
        });
    }

    public int getDelayTime(UUID uuid, String kitName){
        int delayLeft = 0;

        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("SELECT STRFTIME('%s',Delay)-STRFTIME('%s',datetime()) DelayLeft FROM PlayerKits WHERE UUID = ? AND KitName = ?;");
            ps.setString(1, uuid.toString());
            ps.setString(2, kitName);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                delayLeft = rs.getInt("DelayLeft");
            }
            rs.close();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        if(delayLeft<0){
            delayLeft=0;
        }

        return delayLeft;
    }
    public void takeKit(UUID uuid, String kitName, long delay){
        JavaPlugin plugin = DKit.getPlugin();
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                PreparedStatement ps =null;
                try{
                    ps = con.prepareStatement("DELETE FROM PlayerKits WHERE UUID = ? AND KitName = ?;");
                    ps.setString(1, uuid.toString());
                    ps.setString(2, kitName);
                    ps.executeUpdate();

                    String query = "INSERT INTO PlayerKits(UUID, KitName, Delay) VALUES (?, ?, DATETIME('now', '"+delay+" seconds'));";
                    ps = con.prepareStatement(query);
                    ps.setString(1, uuid.toString());
                    ps.setString(2, kitName);

                    ps.executeUpdate();
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
