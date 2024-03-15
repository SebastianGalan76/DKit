package pl.dream.dkit.data;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.dream.dkit.DKit;

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
    public void setDelay(String kitName, long delay){
        kitDelays.put(kitName, delay);
    }
    public Player getPlayer(){
        return player;
    }
}
