package pl.dream.dkit;

import org.bukkit.plugin.java.JavaPlugin;
import pl.dream.dkit.command.KitCommand;
import pl.dream.dkit.controller.ConfigController;
import pl.dream.dkit.data.kit.IKit;
import pl.dream.dkit.data.kit.Kit;
import pl.dream.dkit.data.LocalPlayer;
import pl.dream.dkit.inventory.KitListInventory;
import pl.dream.dkit.listener.*;

import java.util.HashMap;
import java.util.UUID;

public final class DKit extends JavaPlugin {
    private static DKit plugin;
    public SQLite sqLite;

    public KitListInventory kitListInventory;

    public HashMap<UUID, LocalPlayer> players;
    public HashMap<String, IKit> kits;

    @Override
    public void onEnable() {
        plugin = this;
        players = new HashMap<>();
        kits = new HashMap<>();

        sqLite = new SQLite();
        sqLite.connect();

        loadPlugin();

        getCommand("kit").setExecutor(new KitCommand());

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new ClickInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(), this);
        getServer().getPluginManager().registerEvents(new DragItemListener(), this);
    }

    @Override
    public void onDisable() {
        sqLite.disconnect();
    }

    public void reloadPlugin(){
        reloadConfig();

        loadPlugin();;
    }

    private void loadPlugin(){
        saveDefaultConfig();

        kits.clear();

        Locale.loadMessages(this);
        new ConfigController(getConfig());
    }

    public static DKit getPlugin(){
        return plugin;
    }
}
