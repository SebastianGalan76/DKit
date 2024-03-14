package pl.dream.dkit.data;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.dream.dkit.DKit;
import pl.dream.dkit.data.item.Item;
import pl.dream.dkit.inventory.KitInventory;

import java.util.HashMap;
import java.util.List;

public class Kit {
    public final KitInventory inventory;

    private final List<String> commands;
    private final int requiredSpace;
    private final String name;
    private final long delay;
    private final String delayInfo;
    private final String accessInfo;

    private final CommandSender console;
    private final HashMap<Integer, Item> items;

    public Kit(int size, String title, HashMap<Integer, Item> items, List<String> commands, int requiredSpace, String name, long delay, String delayInfo, String accessInfo){
        inventory = new KitInventory(this, size, title, items);
        this.items = items;
        this.commands = commands;
        this.requiredSpace = requiredSpace;
        this.name = name;
        this.delay = delay;
        this.delayInfo = delayInfo;
        this.accessInfo = accessInfo;

        console = Bukkit.getConsoleSender();
    }

    public void get(LocalPlayer player){
        if(commands!=null){
            for(String cmd:commands){
                cmd = cmd.replace("{PLAYER}", player.getName());
                Bukkit.dispatchCommand(console, cmd);
            }
        }

        for(Item item:items.values()){
            player.addItem(item.getItem());
        }

        DKit.getPlugin().sqLite.takeKit(player.getUUID(), name, delay);
        player.getKit(name, (System.currentTimeMillis()/1000) + delay);
    }

    public void displayPreview(LocalPlayer player){
        inventory.openInventory(player);
    }

    public String getDelayInfo(){
        return delayInfo;
    }

    public String getAccessInfo(){
        return accessInfo;
    }
}
