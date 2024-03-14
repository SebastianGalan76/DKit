package pl.dream.dkit.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pl.dream.dkit.DKit;
import pl.dream.dkit.data.Kit;
import pl.dream.dkit.data.LocalPlayer;
import pl.dream.dkit.data.item.Item;

import java.util.HashMap;

public class KitInventory implements InventoryHolder {
    private final Inventory inv;
    private final Kit kit;

    private final int size;
    private final String title;
    private final HashMap<Integer, Item> items;

    public KitInventory(Kit kit, int size, String title, HashMap<Integer, Item> items){
        inv = Bukkit.createInventory(this, size, title);
        this.kit = kit;
        this.size = size;
        this.title = title;
        this.items = items;
    }

    public void openInventory(LocalPlayer player){
        Inventory inv = Bukkit.createInventory(this, size, title);

        items.forEach((index, item) ->{
            inv.setItem(index, item.getItemStack());
        });

        createFooter(inv, player, size-9);

        player.openInventory(inv);
    }

    public void clickItem(LocalPlayer player, int slot){
        int startSlot = size - 9;

        if(slot==startSlot){
            kit.get(player);
        }
        else if(slot==startSlot+4){
            DKit.getPlugin().kitListInventory.openInventory(player);
        }
    }

    private void createFooter(Inventory inv, LocalPlayer player, int startSlot){
        ItemStack takeItem = new ItemStack(Material.SLIME_BALL);
        inv.setItem(startSlot, takeItem);

        ItemStack backItem = new ItemStack(Material.BARRIER);
        inv.setItem(startSlot + 4, backItem);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
