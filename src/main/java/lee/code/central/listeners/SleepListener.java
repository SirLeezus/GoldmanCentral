package lee.code.central.listeners;

import lee.code.central.Central;
import lee.code.central.lang.Lang;
import org.bukkit.World;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SleepListener implements Listener {
  private final Central central;

  public SleepListener(Central central) {
    this.central = central;
  }

  @EventHandler
  public void onBedSleep(PlayerInteractEvent e) {
    if (!e.hasBlock()) return;
    if (e.getClickedBlock() == null) return;
    if (!(e.getClickedBlock().getBlockData() instanceof Bed)) return;
    if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
    final Player player = e.getPlayer();
    if (!(player.getWorld().getEnvironment().equals(World.Environment.NORMAL))) return;
    e.setCancelled(true);
    if (central.getDelayManager().isOnDelay(player.getUniqueId(), "bed")) return;
    central.getDelayManager().setOnDelay(player.getUniqueId(), "bed", 500);
    if (player.isSneaking() || player.getBedSpawnLocation() == null) {
      player.setBedSpawnLocation(e.getClickedBlock().getLocation());
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.BED_SET_SPAWN_SUCCESS.getComponent(null)));
    }
    if (central.getBedManager().isNight()) central.getBedManager().addSleeper(player);
    else player.sendActionBar(Lang.ERROR_SLEEP_DURING_DAY.getComponent(null));
  }

  @EventHandler
  public void onSleepInBed(PlayerBedEnterEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  public void onBedQuit(PlayerQuitEvent e) {
    if (central.getBedManager().isSleeping(e.getPlayer().getUniqueId())) {
      central.getBedManager().removeSleeper(e.getPlayer().getUniqueId());
    }
  }
}
