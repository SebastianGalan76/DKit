package pl.dream.dkit.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import pl.dream.dkit.data.Kit;

public class KitInventory implements InventoryHolder {
    private final Inventory inv;

    public KitInventory(Kit kit){
        inv = Bukkit.createInventory(this, kit.size, kit.title);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
