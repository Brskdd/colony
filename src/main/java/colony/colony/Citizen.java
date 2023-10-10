package colony.colony;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Citizen {

    public static Map<ArmorStand, Citizen> standtocitizen = new HashMap<>();
    public static Map<ArmorStand, Citizen> getMap() {
        return standtocitizen;
    }



    private final ArmorStand entity;
    private int level;

    private int ID;

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
        standtocitizen.put(entity,this);
    }
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

}
