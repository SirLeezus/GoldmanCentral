package lee.code.central.managers;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import lee.code.central.Central;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.playerdata.PlayerDataAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class BedManager {
  private final Central central;
  private ScheduledTask scheduledTask;
  private final Set<UUID> playersSleeping = ConcurrentHashMap.newKeySet();

  public BedManager(Central central) {
    this.central = central;
  }

  public void addSleeper(Player player) {
    if (playersSleeping.contains(player.getUniqueId())) {
      sendSleepingTotal(player);
      return;
    }
    playersSleeping.add(player.getUniqueId());
    checkSkipNight();
  }

  public void removeSleeper(UUID uuid) {
    playersSleeping.remove(uuid);
    broadcastSleepingTotal();
  }

  public boolean isSleeping(UUID uuid) {
    return playersSleeping.contains(uuid);
  }

  public boolean isNight() {
    final long time = Bukkit.getWorlds().get(0).getTime();
    return time >= 13000 && time < 24000;
  }

  private void checkSkipNight() {
    final int online = Bukkit.getOnlinePlayers().size();
    final int sleeping = playersSleeping.size();
    final double percentageNotSleeping = ((double)(online - sleeping) / online) * 100.0;
    if (percentageNotSleeping >= 50 || percentageNotSleeping == 0) {
      Bukkit.getWorlds().get(0).setTime(1000);
      playersSleeping.clear();
      Bukkit.getServer().sendMessage(Lang.PREFIX.getComponent(null).append(Lang.BED_TIME_SKIP_SUCCESS.getComponent(null)));
      return;
    }
    broadcastSleepingTotal();
    if (scheduledTask == null) startTaskChecker();
  }

  private void broadcastSleepingTotal() {
    Bukkit.getAsyncScheduler().runNow(central, scheduledTask1 -> {
      for (UUID targetID : playersSleeping) {
        final Player target = PlayerDataAPI.getOnlinePlayer(targetID);
        if (target != null) sendSleepingTotal(target);
      }
    });
  }

  private void sendSleepingTotal(Player player) {
    player.sendActionBar(Lang.BED_TIME_SKIP_NEEDED.getComponent(new String[]{String.valueOf(playersSleeping.size()), String.valueOf(CoreUtil.getOnlinePlayers().size())}));
  }

  private void startTaskChecker() {
    scheduledTask = Bukkit.getAsyncScheduler().runAtFixedRate(central, scheduledTask -> {
      final long time = Bukkit.getWorlds().get(0).getTime();
      if (time >= 0 && time < 1000) {
        playersSleeping.clear();
        scheduledTask.isCancelled();
        this.scheduledTask = null;
      }
    }, 0, 5, TimeUnit.SECONDS);
  }
}
