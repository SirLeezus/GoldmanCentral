package lee.code.central.managers;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import lee.code.central.Central;
import lee.code.central.utils.CoreUtil;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class PvPManager {
  private final Central central;
  private final ConcurrentHashMap<UUID, Long> playersPvPDelay = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<UUID, ScheduledTask> playersPvPing = new ConcurrentHashMap<>();

  public PvPManager(Central central) {
    this.central = central;
  }
  public void addPlayers(UUID attacker, UUID victim) {
    playersPvPing.put(attacker, scheduleTask(attacker));
    playersPvPing.put(victim, scheduleTask(victim));
  }

  public void removePlayer(UUID uuid) {
    if (!playersPvPing.containsKey(uuid)) return;
    playersPvPing.get(uuid).cancel();
    playersPvPing.remove(uuid);
  }

  public String getDelayTime(UUID uuid) {
    final long delay = playersPvPDelay.getOrDefault(uuid, 0L);
    return CoreUtil.parseTime(Math.max(delay - System.currentTimeMillis(), 0));
  }

  public boolean isPvPing(UUID uuid) {
    return playersPvPing.containsKey(uuid);
  }

  private ScheduledTask scheduleTask(UUID uuid) {
    final long delay = 15 * 1000;
    if (playersPvPing.containsKey(uuid)) {
      playersPvPing.get(uuid).cancel();
    }
    playersPvPDelay.put(uuid, System.currentTimeMillis() + delay);
    return Bukkit.getAsyncScheduler().runDelayed(central, scheduledTask -> removePlayer(uuid), delay, TimeUnit.MILLISECONDS);
  }
}
