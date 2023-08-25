package lee.code.central.managers;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ArmorStandManager {

    private final Set<UUID> armorStandsCurrentlyEditing = new HashSet<>();

    public void setEditingArmorStand(UUID armorStand) {
        armorStandsCurrentlyEditing.add(armorStand);
    }

    public boolean isBeingEdited(UUID armorStand) {
        return armorStandsCurrentlyEditing.contains(armorStand);
    }

    public void removeEditingArmorStand(UUID uuid) {
        armorStandsCurrentlyEditing.remove(uuid);
    }
}
