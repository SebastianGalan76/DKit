package pl.dream.dkit.data.kit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.dream.dkit.Locale;
import pl.dream.dkit.data.LocalPlayer;
import pl.dream.dkit.data.item.Item;
import pl.dream.dkit.util.Utils;
import pl.dream.dreamlib.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RespawnKit implements IKit {

    private final List<String> commands;
    private final HashMap<Integer, Item> items;
    private final String message;

    public RespawnKit(HashMap<Integer, Item> items, List<String> commands, String message){
        this.items = items;
        this.commands = commands;
        this.message = message;
    }

    @Override
    public void getKit(LocalPlayer localPlayer) {
        Message.sendMessage(localPlayer.getPlayer(), Locale.NO_PERMISSIONS.toString());
    }

    @Override
    public void giveKit(Player player) {
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

        Utils.giveItems(player, kitItems);
        if(message!=null){
            Message.sendMessage(player, message);
        }
    }
}
