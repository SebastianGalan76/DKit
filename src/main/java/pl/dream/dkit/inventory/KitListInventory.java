package pl.dream.dkit.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import pl.dream.dkit.data.LocalPlayer;
import pl.dream.dkit.data.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitListInventory implements InventoryHolder {
    private final Inventory inv;

    private final int size;
    private final String title;
    private final HashMap<Integer, Item> items;

    public KitListInventory(int size, String title, HashMap<Integer, Item> items){
        this.size = size;
        this.title = title;
        this.items = items;

        inv = Bukkit.createInventory(this, size, title);
    }

    public void openInventory(LocalPlayer player){
        Inventory inv = Bukkit.createInventory(this, size, title);

        items.forEach((index, item) ->{
            inv.setItem(index, getItemStack(item, player));
        });

        player.openInventory(inv);
    }

    private ItemStack getItemStack(Item item, LocalPlayer player){
        ItemStack itemStack = item.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta != null && itemMeta.hasLore()){
            List<String> lore = itemMeta.getLore();
            List<String> formattedLore = new ArrayList<>();

            for(String line:lore){
                line = line.replace("{PLAYER}", player.getName());
                formattedLore.add(line);
            }

            itemMeta.setLore(formattedLore);
        }
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
