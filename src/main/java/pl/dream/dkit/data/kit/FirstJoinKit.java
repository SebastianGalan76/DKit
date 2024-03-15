package pl.dream.dkit.data.kit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.dream.dkit.Locale;
import pl.dream.dkit.data.LocalPlayer;
import pl.dream.dkit.data.item.Item;
import pl.dream.dreamlib.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirstJoinKit implements IKit{
    private final List<String> commands;
    private final HashMap<Integer, Item> items;
    private final String message;

    public FirstJoinKit(HashMap<Integer, Item> items, List<String> commands, String message){
        this.items = items;
        this.commands = commands;
        this.message = message;
    }

    @Override
    public void getKit(LocalPlayer localPlayer) {
        Message.sendMessage(localPlayer.getPlayer(), Locale.NO_PERMISSIONS.toString());
    }

    @Override
    public void giveKit(LocalPlayer localPlayer) {
        Player player = localPlayer.getPlayer();

        if(commands!=null){
            for(String cmd:commands){
                cmd = cmd.replace("{PLAYER}", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            }
        }

        List<ItemStack> kitItems = new ArrayList<>();
        for(Item item:items.values()){
            ItemStack itemStack = item.getItem();

            if(itemStack!=null){
                kitItems.add(itemStack);
            }
        }

        HashMap<Integer, ItemStack> itemsToDrop = player.getInventory().addItem(kitItems.toArray(new ItemStack[0]));
        World world = player.getWorld();
        Location loc = player.getLocation();

        for(ItemStack item:itemsToDrop.values()){
            world.dropItem(loc, item);
        }

        if(!itemsToDrop.isEmpty()){
            Message.sendMessage(player, Locale.ITEM_DROP.toString());
        }

        if(message!=null){
            Message.sendMessage(player, message);
        }
    }
}
