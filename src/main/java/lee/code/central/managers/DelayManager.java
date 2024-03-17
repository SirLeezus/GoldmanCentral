package lee.code.central.managers;

import lee.code.central.Central;
import lee.code.central.utils.CoreUtil;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DelayManager {
  private final Central central;
  private final ConcurrentHashMap<UUID, ConcurrentHashMap<String, Long>> playersOnDelay = new ConcurrentHashMap<>();

  public DelayManager(Central central) {
    this.central = central;
  }

  public boolean isOnDelay(UUID uuid, String key) {
    if (!playersOnDelay.containsKey(uuid)) return false;
    else return playersOnDelay.get(uuid).containsKey(key);
  }

  public void setOnDelay(UUID uuid, String key, long delay) {
    if (playersOnDelay.containsKey(uuid)) {
      playersOnDelay.get(uuid).put(key, System.currentTimeMillis() + delay);
    } else {
      final ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
      map.put(key, System.currentTimeMillis() + delay);
      playersOnDelay.put(uuid, map);
    }
    scheduleDelay(uuid, key, delay);
  }

  public String getRemainingTime(UUID uuid, String key) {
    return CoreUtil.parseTime(playersOnDelay.get(uuid).get(key) - System.currentTimeMillis());
  }

  private void scheduleDelay(UUID uuid, String key, long delay) {
    Bukkit.getServer().getAsyncScheduler().runDelayed(central, scheduledTask ->
      removeDelay(uuid, key), delay, TimeUnit.MILLISECONDS);
  }

  private void removeDelay(UUID uuid, String key) {
    playersOnDelay.get(uuid).remove(key);
    if (playersOnDelay.get(uuid).isEmpty()) playersOnDelay.remove(uuid);
  }
}
