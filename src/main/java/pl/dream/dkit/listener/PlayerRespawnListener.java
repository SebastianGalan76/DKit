package pl.dream.dkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.dream.dkit.DKit;
import pl.dream.dkit.data.kit.IKit;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        if(e.getRespawnReason() != PlayerRespawnEvent.RespawnReason.DEATH){
            return;
        }

        IKit respawnKit = DKit.getPlugin().kits.get("respawn");
        if(respawnKit!=null){
            respawnKit.giveKit(e.getPlayer());
        }
    }
}
