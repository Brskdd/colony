package colony.colony.handlers;

import colony.colony.Citizen;
import colony.colony.Colony;
import colony.colony.Town.Town;
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
import static colony.colony.Citizen.standtocitizen;


public class Handler implements Listener {
    public List<String> createtownrequests = new ArrayList<>();

    public List<Town> towns = new ArrayList<>();
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
            event.setCancelled(true);
            Bukkit.broadcastMessage(getMap().toString());

        }
    }
    @EventHandler
    public void playerrequestsmaketown(AsyncPlayerChatEvent event) {
        if (createtownrequests.contains(event.getPlayer().getUniqueId().toString())) {
            createtownrequests.remove(event.getPlayer().getUniqueId().toString());
            event.setCancelled(true);
            if (event.getMessage().equals("Y")) {
                Bukkit.broadcastMessage(event.getPlayer().toString() + " wants to make a town");
                Town town = new Town();
                town.SetOwner(event.getPlayer().getUniqueId());
                towns.add(town);
            }
        }
    }

    @EventHandler
    public void claimcitizen(PlayerInteractAtEntityEvent event) {
        Bukkit.broadcastMessage("claimtest");
        if (event.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            ArmorStand entity = (ArmorStand) event.getRightClicked();
            Bukkit.broadcastMessage(entity.toString());
            Citizen citizen = getMap().get(entity);
            Bukkit.broadcastMessage(citizen.toString());
            Bukkit.broadcastMessage(towns.toString());
            Town parentTown = null;
            for (Town x : towns) {
                Bukkit.broadcastMessage(x.toString());
                /*if (x.GetMembers().contains(citizen)) {
                    Bukkit.broadcastMessage("already in town");
                    parentTown = x;
                }*/
            }
            /*if (parentTown == null) {
                Bukkit.broadcastMessage("no parent town");
                for (Town x : towns) {
                    if (x.GetOwner().equals(event.getPlayer().getUniqueId())) {
                        Bukkit.broadcastMessage("adding to town");
                        List<Citizen> citizenList = x.GetMembers();
                        citizenList.add(citizen);
                        x.SetMembers(citizenList);
                    }
                }
            }*/
        }
    }
}
