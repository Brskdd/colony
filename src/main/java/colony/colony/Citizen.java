package colony.colony;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Citizen {

    public static Map<ArmorStand, Citizen> standtocitizen = new HashMap<>();
    public static Map<ArmorStand, Citizen> getMap() {
        return standtocitizen;
    }

    private UUID owner;

    private final ArmorStand entity;
    private int level;
    private int ID;
    private entityai ai;

    public Citizen(Location location) {
        entity = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10);
        entity.setHealth(10);
        entity.setCustomName("test citizen");
        entity.setCustomNameVisible(true);
        entity.setBasePlate(false);
        entity.setSmall(true);
        entity.setArms(true);
        entity.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        entity.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        entity.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
        standtocitizen.put(entity, this);
        Bukkit.broadcastMessage("entity exists " + entity.toString());
        ai = new entityai(entity);
        Bukkit.broadcastMessage("ai exists " + ai.toString());
    }

    private static class entityai {

        public entityai(ArmorStand armorStand) {
            Bukkit.broadcastMessage("skibidi toilet");
            BukkitTask task = new BukkitRunnable() {
                int mode = 0;


                @Override
                public void run() {
                    switch (mode) {
                        case 0:
                            //idle around
                        break;
                        case 1:
                            //follow player
                    }
                    armorStand.teleport(Bukkit.getPlayer(getMap().get(armorStand).getOwner()).getLocation());
                }
            }.runTaskTimer(Colony.plugin,0,10);
        }




    }

    public entityai getAI() {return ai;}
    public String getName() {
        return entity.getCustomName();
    }


    public void setName(String Name) {
        entity.setCustomName(Name);
    }


    public double getMaxHealth() {
        return entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
    }


    public void setMaxHealth(double MaxHealth) {
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(MaxHealth);
    }


    public double getHealth() {
        return entity.getHealth();
    }


    public void setHealth(double Health) {
        if (entity != null) {
            entity.setHealth(Health);
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int lvl) {
        level = lvl;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }

    public ArmorStand getStand() {
        return entity;
    }

    public void setOwner(UUID uuid) {
        owner = uuid;
    }

    public UUID getOwner() {
        return owner;
    }

}
