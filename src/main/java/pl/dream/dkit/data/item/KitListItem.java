package pl.dream.dkit.data.item;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import pl.dream.dkit.DKit;
import pl.dream.dkit.data.kit.IKit;
import pl.dream.dkit.data.kit.Kit;
import pl.dream.dkit.data.LocalPlayer;
import pl.dream.dkit.util.Utils;

public class KitListItem extends Item{
    private final String kitName;

    public KitListItem(ItemStack itemStack, String kitName) {
        super(itemStack, false);
        this.kitName = kitName;
    }

    @Override
    public void onClick(LocalPlayer player, ClickType clickType) {
        Kit kit = (Kit)DKit.getPlugin().kits.get(kitName);

        if(kit==null){
            return;
        }

        if(clickType==ClickType.LEFT){
            kit.displayPreview(player);
        }
        else if(clickType==ClickType.RIGHT){
            kit.getKit(player);
        }

        Utils.playUISounds(player.getPlayer());
    }

    public String getKitName(){
        return kitName;
    }
}
