package pl.dream.dkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.dream.dkit.DKit;
import pl.dream.dkit.data.LocalPlayer;
import pl.dream.dkit.data.kit.IKit;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        LocalPlayer localPlayer = new LocalPlayer(player);
        DKit.getPlugin().players.put(player.getUniqueId(), localPlayer);

        if(!e.getPlayer().hasPlayedBefore()){
            IKit respawnKit = DKit.getPlugin().kits.get("firstJoinKit");
            if(respawnKit!=null){
                respawnKit.giveKit(localPlayer);
            }
        }
    }
}
