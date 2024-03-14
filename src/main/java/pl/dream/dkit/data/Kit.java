package pl.dream.dkit.data;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.dream.dkit.DKit;
import pl.dream.dkit.data.item.Item;
import pl.dream.dkit.inventory.KitInventory;
import pl.dream.dkit.util.Utils;

import java.util.HashMap;
import java.util.List;

public class Kit {
    public final KitInventory inventory;

    public final String name;
    public final long delay;
    public final String delayInfo;
    public final String accessInfo;
    public final int requiredSpace;
    public final List<String> commands;
    public final HashMap<Integer, Item> items;
    public final List<String> noPermission;

    public Kit(int size, String title, HashMap<Integer, Item> items, List<String> commands, int requiredSpace, String name, long delay, String delayInfo, String accessInfo, List<String> noPermission){
        inventory = new KitInventory(this, size, title, items);
        this.items = items;
        this.commands = commands;
        this.requiredSpace = requiredSpace;
        this.name = name;
        this.delay = delay;
        this.delayInfo = delayInfo;
        this.accessInfo = accessInfo;
        this.noPermission = noPermission;
    }

    public void displayPreview(LocalPlayer player){
        inventory.openInventory(player);
    }
}
