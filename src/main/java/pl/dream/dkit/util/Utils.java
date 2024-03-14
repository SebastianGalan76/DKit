package pl.dream.dkit.util;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {
    public static boolean takeItemFromPlayer(Player player, ItemStack item, int amount) {
        if (getItemAmountInInventory(player, item) >= amount) {
            int remainingAmount = amount;
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (stack != null && stack.isSimilar(item)) {
                    int stackAmount = stack.getAmount();
                    if (stackAmount <= remainingAmount) {
                        remainingAmount -= stackAmount;
                        player.getInventory().setItem(i, new ItemStack(Material.AIR));
                        if (remainingAmount == 0) {
                            player.updateInventory();
                            return true;
                        }
                    } else {
                        stack.setAmount(stackAmount - remainingAmount);
                        player.updateInventory();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static int getItemAmountInInventory(Player player, ItemStack item){
        int amountInInventory = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack != null && stack.isSimilar(item)) {
                amountInInventory += stack.getAmount();
            }
        }

        return amountInInventory;
    }

    public static int getFreeSpaceInInventory(Player player){
        int amountInInventory = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            if(stack==null){
                amountInInventory++;
            }
        }

        return amountInInventory;
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
