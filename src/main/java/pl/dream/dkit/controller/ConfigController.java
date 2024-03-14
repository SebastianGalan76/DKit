package pl.dream.dkit.controller;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.dream.dkit.DKit;
import pl.dream.dkit.data.Kit;
import pl.dream.dkit.data.item.Item;
import pl.dream.dkit.data.item.KitListItem;
import pl.dream.dkit.inventory.KitListInventory;
import pl.dream.dreamlib.Color;
import pl.dream.dreamlib.Config;

import java.util.HashMap;
import java.util.List;

public class ConfigController {
    private final FileConfiguration config;

    public ConfigController(FileConfiguration config){
        this.config = config;

        loadKitListInventory();
        loadKits();
    }

    private void loadKitListInventory(){
        String title = Color.fixRGB(config.getString("kitListInventory.title", "&9Kit"));
        int size = config.getInt("kitListInventory.size", 54);

        HashMap<Integer, Item> items = new HashMap<>();
        for(String indexString : config.getConfigurationSection("kitListInventory.container").getKeys(false)){
            int index = Integer.parseInt(indexString);

            ItemStack itemStack = Config.getItemStack(config, "kitListInventory.container."+indexString);
            String kitName = config.getString("kitListInventory.container."+indexString+".kitName");
            if(kitName==null){
                items.put(index, new Item(itemStack, false));
            }
            else{
                items.put(index, new KitListItem(itemStack, kitName));
            }
        }

        DKit.getPlugin().kitListInventory = new KitListInventory(size, title, items);
    }
    private void loadKits(){
        for(String kitName:config.getConfigurationSection("kits").getKeys(false)){
            String title = Color.fixRGB(config.getString("kits."+kitName+".title", "&9Kit"));
            int size = config.getInt("kits."+kitName+".size", 54);

            HashMap<Integer, Item> items = new HashMap<>();
            for(String indexString : config.getConfigurationSection("kits."+kitName+".items").getKeys(false)){
                int index = Integer.parseInt(indexString);
                boolean give = config.getBoolean("kits."+kitName+".items."+indexString+".give", true);

                ItemStack itemStack = Config.getItemStack(config, "kits."+kitName+".items."+indexString);
                Item item = new Item(itemStack, give);
                items.put(index, item);
            }

            List<String> commands = config.getStringList("kits."+kitName+".commands");
            int requiredSpace = config.getInt("kits."+kitName+".requiredSpace");
            long delay = config.getLong("kits."+kitName+".delay");

            Kit kit = new Kit(size, title, items, commands, requiredSpace, kitName, delay);
            DKit.getPlugin().kits.put(kitName, kit);
        }
    }
}
