package colony.colony.handlers;

import colony.colony.Citizen;
import colony.colony.Colony;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static colony.colony.Citizen.getMap;


public class Handler implements Listener {
    public List<String> createtownrequests = new ArrayList<>();
    public Handler(Colony plugin) {
        Bukkit.getPluginManager().registerEvents((Listener) this, (Plugin) plugin);
    }

    @EventHandler
    public void checkbanner(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            /*Bukkit.broadcastMessage(event.getAction().toString());
            Bukkit.broadcastMessage(event.getClickedBlock().toString());
            Bukkit.broadcastMessage(event.getPlayer().toString());*/
            if (event.getClickedBlock().getType() == Material.WHITE_BANNER) {
                if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BOOK) {
                    if (!createtownrequests.contains(event.getPlayer().getUniqueId().toString())) {
                        Bukkit.broadcastMessage("create town");
                        createtownrequests.add(event.getPlayer().getUniqueId().toString());
                        event.getPlayer().sendMessage("Type Y to create town");
                    }
                }
            }
        }
    }
    @EventHandler
    public void printcreatetownrequests(AsyncPlayerChatEvent event) {
        if (event.getMessage().equals("a")) {
            for (String i : createtownrequests) {
                Bukkit.broadcastMessage(i);
            }
            Map<ArmorStand, Citizen> readmap = getMap();
            for (Map.Entry<ArmorStand, Citizen> thing : readmap.entrySet()) {
                Bukkit.broadcastMessage("key " + thing.getKey() + " value " + thing.getValue());
            }
        }
    }
    @EventHandler
    public void playerrequestsmaketown(AsyncPlayerChatEvent event) {
        createtownrequests.remove(event.getPlayer().getUniqueId().toString());
        if (event.getMessage().equals("Y")) {
            if (createtownrequests.contains(event.getPlayer().getUniqueId().toString())) {
                Bukkit.broadcastMessage(event.getPlayer().toString() + " wants to make a town");
            }
        }
    }

    @EventHandler
    public void claimcitizen(PlayerInteractAtEntityEvent event) {
        Bukkit.broadcastMessage("claimtest");
        if (event.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            Citizen citizen = Citizen.standtocitizen.get(event.getRightClicked());
            if (citizen != null) {
                Bukkit.broadcastMessage("citizen found");
            }
            Bukkit.broadcastMessage(citizen.getStand().getLocation().toString());
        }
    }
}
