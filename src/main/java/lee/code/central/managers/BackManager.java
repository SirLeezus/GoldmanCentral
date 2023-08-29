package lee.code.central.managers;

import lee.code.central.utils.CoreUtil;
import org.bukkit.Location;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BackManager {
  private final ConcurrentHashMap<UUID, String> lastBackLocation = new ConcurrentHashMap<>();

  public void setLastBackLocation(UUID uuid, Location location) {
    lastBackLocation.put(uuid, CoreUtil.serializeLocation(location));
  }

  public boolean hasLastBackLocation(UUID uuid) {
    return lastBackLocation.containsKey(uuid);
  }

  public Location getLastBackLocation(UUID uuid) {
   return CoreUtil.parseLocation(lastBackLocation.get(uuid));
  }

  public void removeLastBackLocation(UUID uuid) {
    lastBackLocation.remove(uuid);
  }
}
