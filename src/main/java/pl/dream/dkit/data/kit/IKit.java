package pl.dream.dkit.data.kit;

import org.bukkit.entity.Player;
import pl.dream.dkit.data.LocalPlayer;

public interface IKit {
    void getKit(LocalPlayer localPlayer);
    void giveKit(Player Player);
}
