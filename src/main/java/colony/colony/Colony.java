package colony.colony;

import colony.colony.handlers.Handler;
import colony.colony.handlers.Handler;
import colony.colony.handlers.SummonCitizen;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Colony extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("Hello World");
        new Handler(this);
        new SummonCitizen(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Goodbye World");
    }
}
