package colony.colony;

import org.bukkit.attribute.Attributable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

public class Citizen {
    public Citizen(Location location) {
        ArmorStand entity = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        entity.setCustomName("test citizen");
        entity.setCustomNameVisible(true);
    }
}
