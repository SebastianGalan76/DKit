package pl.dream.dkit.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import pl.dream.dkit.DKit;
import pl.dream.dkit.data.LocalPlayer;
import pl.dream.dkit.inventory.KitInventory;
import pl.dream.dkit.inventory.KitListInventory;

public class ClickInventoryListener implements Listener {

    @EventHandler
    public void onPlayerClick(InventoryClickEvent e){
        if(e.getClickedInventory()==null){return; }
        InventoryHolder invHolder = e.getInventory().getHolder();
        if(invHolder instanceof KitListInventory || invHolder instanceof KitInventory){
            if(e.getClick().isShiftClick() || e.getClick() == ClickType.DOUBLE_CLICK){
                e.setCancelled(true);
            }

            if(e.getAction() == InventoryAction.COLLECT_TO_CURSOR){
                e.setCancelled(true);
            }
        }

        if(e.getClickedInventory().getHolder() instanceof KitListInventory){
            e.setCancelled(true);
            KitListInventory inventory = (KitListInventory) e.getClickedInventory().getHolder();

            LocalPlayer p = DKit.getPlugin().players.get(e.getWhoClicked().getUniqueId());
            if(p==null){
                return;
            }

            inventory.clickItem(p, e.getSlot(), e.getClick());
        }
        else if(e.getClickedInventory().getHolder() instanceof KitInventory){
            e.setCancelled(true);
            KitInventory inventory = (KitInventory) e.getClickedInventory().getHolder();

            LocalPlayer p = DKit.getPlugin().players.get(e.getWhoClicked().getUniqueId());
            if(p==null){
                return;
            }

            inventory.clickItem(p, e.getSlot());
        }
    }
}
