package colony.colony.handlers;

import colony.colony.Citizen;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import colony.colony.Colony;

public class SummonCitizen implements Listener {

    public SummonCitizen(Colony plugin) {
        Bukkit.getPluginManager().registerEvents((Listener) this, (Plugin) plugin);
    }

    @EventHandler
    public void spawncitizen(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_AIR){
            if(event.getMaterial() == Material.BOOK) {
                Bukkit.broadcastMessage("citizen summoned");
                Location location;
                location = event.getPlayer().getLocation();
                new Citizen(location);
            }
        }
    }
}
