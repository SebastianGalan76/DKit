package pl.dream.dkit.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.dream.dkit.DKit;
import pl.dream.dkit.Locale;
import pl.dream.dkit.data.kit.IKit;
import pl.dream.dkit.data.kit.Kit;
import pl.dream.dkit.data.LocalPlayer;
import pl.dream.dreamlib.Color;
import pl.dream.dreamlib.Message;

public class KitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            if(args.length>0){
                if(args[1].equalsIgnoreCase("reload")){
                    DKit.getPlugin().reloadPlugin();
                    Message.sendMessage(sender, Locale.RELOAD.toString());
                }
                else if(args[1].equalsIgnoreCase("give")){
                    give(sender, args);
                }
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
                if(checkPermission(sender, "dkit.kit.reload")){
                    DKit.getPlugin().reloadPlugin();
                    Message.sendMessage(sender, Locale.RELOAD.toString());
                }
            }
            else{
                IKit kit = DKit.getPlugin().kits.get(args[0]);

                if(kit!=null){
                    kit.getKit(p);
                }
                else{
                    DKit.getPlugin().kitListInventory.openInventory(p);
                }
            }
        }
        else{
            if(args[0].equalsIgnoreCase("give")){
                give(sender, args);
            }
        }

        return true;
    }

    private void give(CommandSender sender, String[] args){
        if(checkPermission(sender, "dkit.kit.give")){
            if(args.length==3){
                if(args[1].equalsIgnoreCase("*")){
                    IKit kit = DKit.getPlugin().kits.get(args[2]);
                    if(kit==null){
                        Message.sendMessage(sender, Locale.NO_KIT.toString());
                        return;
                    }

                    for(Player player:Bukkit.getOnlinePlayers()){
                        kit.giveKit(player);
                    }
                }
                else{
                    Player player = Bukkit.getPlayer(args[1]);
                    IKit kit = DKit.getPlugin().kits.get(args[2]);

                    if(player==null){
                        Message.sendMessage(sender, Locale.NO_PLAYER.toString());
                        return;
                    }
                    if(kit==null){
                        Message.sendMessage(sender, Locale.NO_KIT.toString());
                        return;
                    }

                    kit.giveKit(player);
                    if(sender instanceof Player){
                        Message.sendMessage(sender, Locale.GIVE_KIT_TO_PLAYER.toString()
                                .replace("{PLAYER}", player.getName())
                                .replace("{KIT}", args[2]));
                    }
                }
            }
            else{
                Color.sendMessage(sender, "&7Poprawne użycie: &c/kit give [nick/*] [kit]");
            }
        }
    }

    private boolean checkPermission(CommandSender sender, String permission){
        if(!sender.hasPermission(permission)){
            Message.sendMessage(sender, Locale.NO_PERMISSIONS.toString());
            return false;
        }

        return true;
    }
}
