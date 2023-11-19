package lee.code.central.managers;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import lee.code.central.Central;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.playerdata.PlayerDataAPI;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
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
    checkAdvancement(player);
    addSleepStat(player);
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
    final double percentageSleeping = ((double) sleeping / online) * 100.0;
    if (percentageSleeping >= 50 || percentageSleeping == 0) {
      Bukkit.getWorlds().get(0).setTime(1000);
      playersSleeping.clear();
      Bukkit.getServer().sendMessage(Lang.PREFIX.getComponent(null).append(Lang.BED_TIME_SKIP_SUCCESS.getComponent(null)));
      return;
    }
    broadcastSleepingTotal();
    if (scheduledTask == null) startTaskChecker();
  }

  private void broadcastSleepingTotal() {
    Bukkit.getAsyncScheduler().runNow(central, scheduledTask -> {
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
      if (!isNight()) {
        playersSleeping.clear();
        scheduledTask.cancel();
        this.scheduledTask = null;
      }
    }, 0, 5, TimeUnit.SECONDS);
  }

  private void checkAdvancement(Player player) {
    final Advancement advancement = Bukkit.getAdvancement(NamespacedKey.minecraft("adventure/sleep_in_bed"));
    if (advancement == null) return;
    final AdvancementProgress progress = player.getAdvancementProgress(advancement);
    for (String criteria : progress.getRemainingCriteria()) progress.awardCriteria(criteria);
  }

  private void addSleepStat(Player player) {
    player.setStatistic(Statistic.SLEEP_IN_BED, player.getStatistic(Statistic.SLEEP_IN_BED) + 1);
  }
}
