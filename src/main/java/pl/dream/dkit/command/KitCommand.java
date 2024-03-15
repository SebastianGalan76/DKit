package pl.dream.dkit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.dream.dkit.DKit;
import pl.dream.dkit.Locale;
import pl.dream.dkit.data.Kit;
import pl.dream.dkit.data.LocalPlayer;
import pl.dream.dreamlib.Message;

public class KitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            if(args.length>0 && args[0].equalsIgnoreCase("reload")){
                DKit.getPlugin().reloadPlugin();
                Message.sendMessage(sender, Locale.RELOAD.toString());
            }

            return true;
        }

        LocalPlayer p = DKit.getPlugin().players.get(((Player) sender).getUniqueId());
        if(p==null){
            return true;
        }

        if(args.length==0){
            DKit.getPlugin().kitListInventory.openInventory(p);
        }
        else if(args.length==1){
            if(args[0].equalsIgnoreCase("reload")){
                if(sender.hasPermission("dkit.kit.reload")){
                    DKit.getPlugin().reloadPlugin();
                    Message.sendMessage(sender, Locale.RELOAD.toString());
                }
                else{
                    Message.sendMessage(sender, Locale.NO_PERMISSIONS.toString());
                }
            }
            else{
                Kit kit = DKit.getPlugin().kits.get(args[0]);

                if(kit!=null){
                    kit.getKit(p);
                }
                else{
                    DKit.getPlugin().kitListInventory.openInventory(p);
                }
            }
        }
        else{

        }

        return true;
    }
}
