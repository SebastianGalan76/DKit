package pl.dream.dkit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.dream.dkit.DKit;
import pl.dream.dkit.data.LocalPlayer;

public class KitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){

            return true;
        }

        LocalPlayer p = DKit.getPlugin().players.get(((Player) sender).getUniqueId());
        if(p==null){
            return true;
        }

        if(args.length==0){
            DKit.getPlugin().kitListInventory.openInventory(p);
        }

        return true;
    }
}
