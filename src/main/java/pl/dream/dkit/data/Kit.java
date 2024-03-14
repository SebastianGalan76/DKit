package pl.dream.dkit.data;

import pl.dream.dkit.data.item.Item;
import pl.dream.dkit.inventory.KitInventory;

import java.util.HashMap;

public class Kit {
    public int size;
    public String title;
    public HashMap<Integer, Item> items;
    public KitInventory inventory;

    public Kit(int size, String title, HashMap<Integer, Item> items){

    }
}
