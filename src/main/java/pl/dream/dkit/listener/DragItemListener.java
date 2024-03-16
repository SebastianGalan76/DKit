package pl.dream.dkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryHolder;
import pl.dream.dkit.inventory.KitInventory;
import pl.dream.dkit.inventory.KitListInventory;

public class DragItemListener implements Listener {

    @EventHandler
    public void onDragItem(InventoryDragEvent e){
        InventoryHolder inv = e.getInventory().getHolder();

        if(inv instanceof KitInventory || inv instanceof KitListInventory){
            e.setCancelled(true);
            return;
        }
    }
}
