package colony.colony.Town;

import colony.colony.Citizen;

import java.util.List;
import java.util.UUID;

public class Town {
    private UUID owner;
    private List<Citizen> members;


    public Town() {

    }

    public void SetOwner(UUID uuid) {
        owner = uuid;
    }

    public UUID GetOwner() {
        return owner;
    }

    public void SetMembers(List<Citizen> list) {
        members = list;
    }

    public List<Citizen> GetMembers() {
        return members;
    }


}
