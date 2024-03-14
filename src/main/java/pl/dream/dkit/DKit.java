package pl.dream.dkit;

import org.bukkit.plugin.java.JavaPlugin;

public final class DKit extends JavaPlugin {
    private static DKit plugin;

    @Override
    public void onEnable() {
        plugin = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static DKit getPlugin(){
        return plugin;
    }
}
