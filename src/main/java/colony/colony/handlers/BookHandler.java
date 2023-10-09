package colony.colony.handlers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import java.net.http.WebSocket;

public class TorchHandler implements WebSocket.Listener {
    public TorchHandler(TorchHandler plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onTorchPlace(BlockPlaceEvent event) {
        
    }
}
