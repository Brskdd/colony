package colony.colony.handlers;

import colony.colony.Colony;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;


public class BookHandler implements Listener {
    public BookHandler(Colony plugin) {
        Bukkit.getPluginManager().registerEvents((Listener) this, (Plugin) plugin);
    }

    @EventHandler
    public void onBookUse(BlockPlaceEvent event) {
        Bukkit.broadcastMessage("block placed");
    }
}
