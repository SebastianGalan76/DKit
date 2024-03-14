package pl.dream.dkit.data;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.dream.dkit.DKit;

import java.util.HashMap;
import java.util.UUID;

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

    public void addItem(ItemStack itemStack){
        if(itemStack==null){
            return;
        }

        player.getInventory().addItem(itemStack);
    }

    public void getKit(String kitName, long delay){
        kitDelays.put(kitName, delay);
    }

    public long getDelay(String kitName){
        long delay = kitDelays.get(kitName);

        return delay - System.currentTimeMillis();
    }

    public String getName(){
        return player.getName();
    }

    public UUID getUUID(){
        return player.getUniqueId();
    }
}
