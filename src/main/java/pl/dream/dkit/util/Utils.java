package pl.dream.dkit.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.dream.dkit.Locale;
import pl.dream.dreamlib.Message;

import java.util.HashMap;
import java.util.List;

public class Utils {
    public static int getFreeSpaceInInventory(Player player){
        int amountInInventory = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            if(stack==null){
                amountInInventory++;
            }
        }

        return amountInInventory;
    }

    public static void giveItems(Player player, List<ItemStack> items){
        World world = player.getWorld();
        Location loc = player.getLocation();
        boolean droppedItem = false;

        for(ItemStack item:items){
            if(checkArmor(player, item)){
                continue;
            }

            HashMap<Integer, ItemStack> itemsToDrop = player.getInventory().addItem(item);
            if(!droppedItem && !itemsToDrop.isEmpty()){
                droppedItem = true;
            }

            for(ItemStack itemToDrop:itemsToDrop.values()){
                world.dropItem(loc, itemToDrop);
            }
        }

        if(droppedItem){
            Message.sendMessage(player, Locale.ITEM_DROP.toString());
        }
    }

    public static boolean checkArmor(Player p, ItemStack item){
        PlayerInventory inv = p.getInventory();
        if(item.getType().name().contains("HELMET")){
            if(inv.getHelmet()==null){
                inv.setHelmet(item);
                return true;
            }
        }
        else if(item.getType().name().contains("CHESTPLATE")){
            if(inv.getChestplate()==null){
                inv.setChestplate(item);
                return true;
            }
        }
        else if(item.getType().name().contains("LEGGINGS")){
            if(inv.getLeggings()==null){
                inv.setLeggings(item);
                return true;
            }
        }
        else if(item.getType().name().contains("BOOTS")){
            if(inv.getBoots()==null){
                inv.setBoots(item);
                return true;
            }
        }

        return false;
    }

    public static void playUISounds(Player player){
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
    }

    public static void playFailSounds(Player player){
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
    }

    public static void playSuccessSounds(Player player){
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1f);
    }
}
