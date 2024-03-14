package pl.dream.dkit.data;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LocalPlayer {
    private final Player player;

    public LocalPlayer(Player player){
        this.player = player;
    }

    public void openInventory(Inventory inv){
        player.openInventory(inv);
    }

    public String getName(){
        return player.getName();
    }

    public void addItem(ItemStack itemStack){
        if(itemStack==null){
            return;
        }

        player.getInventory().addItem(itemStack);
    }
}
