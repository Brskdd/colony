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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Type;
import java.util.*;

import static colony.colony.Citizen.getMap;
import static colony.colony.Citizen.standtocitizen;


public class Handler implements Listener {
    public List<String> createtownrequests = new ArrayList<>();
    public Map<UUID, ArmorStand> initworkzone = new HashMap<>();
    public Map<UUID, ArmorStand> finishworkzone = new HashMap<>();

    public Map<UUID, ArmorStand> entityui = new HashMap<>();

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
            //Bukkit.broadcastMessage(getMap().toString());
            Bukkit.broadcastMessage(initworkzone.toString());
            Bukkit.broadcastMessage(finishworkzone.toString());
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
        //Bukkit.broadcastMessage("claimtest");
        if (event.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            ArmorStand entity = (ArmorStand) event.getRightClicked();
            Bukkit.broadcastMessage(entity.toString());
            Citizen citizen = getMap().get(entity);
            Bukkit.broadcastMessage(citizen.toString());
            Bukkit.broadcastMessage(towns.toString());
            Town parentTown = null;
            if (citizen.getOwner() == null) {
                citizen.setOwner(event.getPlayer().getUniqueId());
                Bukkit.broadcastMessage("setting owner to " + event.getPlayer().toString());
            } else {
                Bukkit.broadcastMessage("owner is " + citizen.getOwner().toString());
                citizen.getStand().setCustomName(Bukkit.getPlayer(event.getPlayer().getUniqueId()).getName());
            }
        }
    }
    @EventHandler
    public void initmainui(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            ArmorStand entity = (ArmorStand) event.getRightClicked();
            Citizen citizen = getMap().get(entity);
            if (citizen != null) {
                if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.PAPER) {
                    Bukkit.broadcastMessage("opening ui");
                    Inventory menu = Bukkit.createInventory(null, 9, "Citizen Options");
                    ItemStack setzone = new ItemStack(Material.BARRIER);
                    ItemMeta zonemeta = setzone.getItemMeta();
                    zonemeta.setDisplayName("Set Work Zone");
                    setzone.setItemMeta(zonemeta);
                    menu.setItem(0, setzone);
                    entityui.put(event.getPlayer().getUniqueId(), entity);
                    event.getPlayer().openInventory(menu);
                }

            }
        }
    }
    @EventHandler
    public void mainuimanage(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Citizen Options")) {
            Bukkit.broadcastMessage("correct title");
            event.setCancelled(true);
            ArmorStand entity = (ArmorStand) entityui.get(event.getWhoClicked());
            Citizen citizen = getMap().get(entity);
            if (citizen != null) {
                Bukkit.broadcastMessage("found citizen");
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Set Work Zone")) {
                    Bukkit.broadcastMessage("init zoning");
                    //init zoning
                    if (!initworkzone.containsKey(event.getWhoClicked().getUniqueId()) && !finishworkzone.containsKey(event.getWhoClicked().getUniqueId())) {
                        Bukkit.broadcastMessage("both lists empty");
                        citizen.setWorkzone(null);
                        event.getWhoClicked().sendMessage("right click first corner of work zone");
                        initworkzone.put(event.getWhoClicked().getUniqueId(),entity);
                        Bukkit.broadcastMessage("initworkzone is now " + initworkzone.toString());
                    }
                }

            }


        }

    }
    @EventHandler
    public void firstworkzone(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK) {
                Bukkit.broadcastMessage("starting firstworkzone");
                if (initworkzone.containsKey(event.getPlayer().getUniqueId())) {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("initworkzone found player");
                    ArmorStand entity = initworkzone.get(event.getPlayer().getUniqueId());
                    finishworkzone.put(event.getPlayer().getUniqueId(), entity);
                    Citizen citizen = getMap().get(entity);
                    List<Integer> zone = citizen.getWorkzone();
                    if (zone == null) {
                        zone = new ArrayList<>();
                    }
                    zone.add(event.getClickedBlock().getX());
                    zone.add(event.getClickedBlock().getZ());
                    citizen.setWorkzone(zone);
                    initworkzone.remove(event.getPlayer().getUniqueId());
                    event.getPlayer().sendMessage("left click second corner of work zone");
                    Bukkit.broadcastMessage("finished firstworkzone");
                    Bukkit.broadcastMessage(citizen.getWorkzone().toString());
                }
            }
        }
    }
    @EventHandler
    public void secondworkzone(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK) {
                Bukkit.broadcastMessage("starting secondworkzone");
                if (finishworkzone.containsKey(event.getPlayer().getUniqueId())) {
                    event.setCancelled(true);
                    Bukkit.broadcastMessage("secondworkzone found player");
                    ArmorStand entity = finishworkzone.get(event.getPlayer().getUniqueId());
                    Citizen citizen = getMap().get(entity);
                    List<Integer> zone = citizen.getWorkzone();
                    zone.add(event.getClickedBlock().getX());
                    zone.add(event.getClickedBlock().getZ());
                    citizen.setWorkzone(zone);
                    Bukkit.broadcastMessage("finished secondworkzone");
                    Bukkit.broadcastMessage(citizen.getWorkzone().toString());
                    event.getPlayer().sendMessage("Creating zone for " + citizen.getName() + " at " + citizen.getWorkzone().toString());
                    finishworkzone.remove(event.getPlayer().getUniqueId());
                }
            }
        }
    }
}
