package pl.dream.dkit.data.item;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import pl.dream.dkit.data.LocalPlayer;

public class Item {
    private final ItemStack itemStack;
    private final boolean give;

    public Item(ItemStack itemStack, boolean give){

        this.itemStack = itemStack;
        this.give = give;
    }

    public ItemStack getItemStack(){
        return itemStack.clone();
    }

    public void onClick(LocalPlayer player, ClickType clickType){

    }

    @Nullable
    public ItemStack getItem(){
        if(give){
            return getItemStack();
        }
        return null;
    }
}
