package pl.dream.dkit.data.item;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import pl.dream.dkit.data.LocalPlayer;

public class Item {
    private final ItemStack itemStack;

    public Item(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack(){
        return itemStack.clone();
    }

    public void onClick(LocalPlayer player, ClickType clickType){

    }
}
