package pl.dream.dkit.data;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

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
}
