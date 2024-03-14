package pl.dream.dkit.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import pl.dream.dkit.DKit;
import pl.dream.dkit.Locale;
import pl.dream.dkit.Utils;
import pl.dream.dkit.data.Kit;
import pl.dream.dkit.data.LocalPlayer;
import pl.dream.dkit.data.item.Item;
import pl.dream.dkit.data.item.KitListItem;

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

    public void clickItem(LocalPlayer player, int slot, ClickType clickType){
        if(items.containsKey(slot)){
            items.get(slot).onClick(player, clickType);
        }
    }

    private ItemStack getItemStack(Item item, LocalPlayer player){
        ItemStack itemStack = item.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta != null){
            if(item instanceof KitListItem){
                KitListItem kitItem = (KitListItem) item;
                String kitName = kitItem.getKitName();
                Kit kit = DKit.getPlugin().kits.get(kitName);

                List<String> lore = new ArrayList<>();
                lore.add("");
                if(player.hasPermission("dkit.kit."+kitName.toLowerCase())){
                    lore.add(Locale.KIT_LORE_ACCESS_YES.toString());
                }
                else{
                    lore.add(Locale.KIT_LORE_ACCESS_NO.toString());
                }
                lore.add(Locale.KIT_LORE_DELAY_INFO.toString()
                        .replace("{DELAY_INFO}", kit.getDelayInfo()));

                long kitDelay = player.getDelay(kitName);

                if(player.hasPermission("dkit.kit."+kitName) && kitDelay>0){
                    lore.add(Locale.KIT_LORE_DELAY_LEFT.toString()
                            .replace("{DELAY_LEFT}", Utils.convertTime(kitDelay)));
                }

                String accessInfo = kit.getAccessInfo();
                if(accessInfo!=null){
                    lore.add(accessInfo);
                }

                lore.add("");
                lore.add(Locale.KIT_LORE_ACTION_PREVIEW.toString());
                if(player.hasPermission("dkit.kit."+kitName) && kitDelay<=0){
                    lore.add(Locale.KIT_LORE_ACTION_GET.toString());
                }

                itemMeta.setLore(lore);
            }
        }
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
