package pl.dream.dkit.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.dream.dkit.DKit;
import pl.dream.dkit.Locale;
import pl.dream.dkit.data.item.Item;
import pl.dream.dkit.inventory.KitInventory;
import pl.dream.dkit.util.Time;
import pl.dream.dkit.util.Utils;
import pl.dream.dreamlib.Message;

import java.util.ArrayList;
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

    public void getKit(LocalPlayer localPlayer){
        Player player = localPlayer.getPlayer();

        if(!player.hasPermission("dkit.kit."+name)){
            if(noPermission == null){
                Message.sendMessage(player, Locale.NO_PERMISSIONS.toString());
            }
            else{
                Message.sendMessage(player, noPermission);
            }

            player.closeInventory();
            Utils.playFailSounds(player);
            return;
        }

        long delay = localPlayer.getDelay(name);
        if(delay>0){
            Message.sendMessage(player, Locale.DELAY.toString().replace("{DELAY}", Time.convertTime(delay)));
            player.closeInventory();
            Utils.playFailSounds(player);
            return;
        }

        int freeSpace = Utils.getFreeSpaceInInventory(player);
        if(freeSpace<requiredSpace){
            Message.sendMessage(player, Locale.NOT_ENOUGH_SPACE.toString());
            player.closeInventory();
            Utils.playFailSounds(player);
            return;
        }

        if(commands!=null){
            for(String cmd:commands){
                cmd = cmd.replace("{PLAYER}", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            }
        }

        List<ItemStack> kitItems = new ArrayList<>();
        for(Item item:items.values()){
            ItemStack itemStack = item.getItem();

            if(itemStack!=null){
                kitItems.add(itemStack);
            }
        }

        HashMap<Integer, ItemStack> itemsToDrop = player.getInventory().addItem(kitItems.toArray(new ItemStack[0]));
        World world = player.getWorld();
        Location loc = player.getLocation();

        for(ItemStack item:itemsToDrop.values()){
            world.dropItem(loc, item);
        }

        if(!itemsToDrop.isEmpty()){
            Message.sendMessage(player, Locale.ITEM_DROP.toString());
        }

        localPlayer.setDelay(name, (System.currentTimeMillis()/1000) + delay);
        DKit.getPlugin().sqLite.takeKit(player.getUniqueId(), name, delay);
        Message.sendMessage(player, Locale.GET_KIT_CORRECT.toString());
        player.closeInventory();
        Utils.playSuccessSounds(player);
    }

    public void displayPreview(LocalPlayer player){
        inventory.openInventory(player);
    }
}
