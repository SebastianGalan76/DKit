package pl.dream.dkit.data.item;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import pl.dream.dkit.data.LocalPlayer;

public class KitListItem extends Item{
    private final String kitName;

    public KitListItem(ItemStack itemStack, String kitName) {
        super(itemStack);
        this.kitName = kitName;
    }

    @Override
    public void onClick(LocalPlayer player, ClickType clickType) {
        //TODO openKitInventory

    }

    public String getKitName(){
        return kitName;
    }
}
