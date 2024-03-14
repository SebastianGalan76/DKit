package pl.dream.dkit.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.dream.dkit.DKit;
import pl.dream.dkit.Locale;
import pl.dream.dkit.data.item.Item;
import pl.dream.dkit.util.Time;
import pl.dream.dkit.util.Utils;
import pl.dream.dreamlib.Message;

import java.util.*;

public class LocalPlayer {
    private final Player player;
    private HashMap<String, Long> kitDelays;

    public LocalPlayer(Player player){
        this.player = player;
        DKit.getPlugin().sqLite.loadDelay(this);
    }

    public void loadDelays(HashMap<String, Long> kitDelays){
        this.kitDelays = kitDelays;
    }

    public void openInventory(Inventory inv){
        player.openInventory(inv);
    }

    public void getKit(Kit kit){
        if(!player.hasPermission("dkit.kit."+kit.name)){
            if(kit.noPermission == null){
                Message.sendMessage(player, Locale.NO_PERMISSIONS.toString());
            }
            else{
                Message.sendMessage(player, kit.noPermission);
            }

            player.closeInventory();
            Utils.playFailSounds(player);
            return;
        }

        long delay = getDelay(kit.name);
        if(delay>0){
            Message.sendMessage(player, Locale.DELAY.toString().replace("{DELAY}", Time.convertTime(delay)));
            player.closeInventory();
            Utils.playFailSounds(player);
            return;
        }

        int freeSpace = Utils.getFreeSpaceInInventory(player);
        if(freeSpace<kit.requiredSpace){
            Message.sendMessage(player, Locale.NOT_ENOUGH_SPACE.toString());
            player.closeInventory();
            Utils.playFailSounds(player);
            return;
        }

        if(kit.commands!=null){
            for(String cmd:kit.commands){
                cmd = cmd.replace("{PLAYER}", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            }
        }

        List<ItemStack> kitItems = new ArrayList<>();
        for(Item item:kit.items.values()){
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

        kitDelays.put(kit.name, (System.currentTimeMillis()/1000) + kit.delay);
        DKit.getPlugin().sqLite.takeKit(player.getUniqueId(), kit.name, kit.delay);
        Message.sendMessage(player, Locale.GET_KIT_CORRECT.toString());
        player.closeInventory();
        Utils.playSuccessSounds(player);
    }

    public long getDelay(String kitName){
        if(kitDelays==null){
            return 1000;
        }

        if(kitDelays.containsKey(kitName)){
            long delay = kitDelays.get(kitName);
            return delay - System.currentTimeMillis()/1000;
        }
        return 0;
    }
    public Player getPlayer(){
        return player;
    }
}
