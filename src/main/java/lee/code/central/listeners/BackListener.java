package lee.code.central.listeners;

import lee.code.central.Central;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BackListener implements Listener {
  private final Central central;

  public BackListener(Central central) {
    this.central = central;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onBackTeleport(PlayerTeleportEvent e) {
    if (e.isCancelled()) return;
    central.getBackManager().setLastBackLocation(e.getPlayer().getUniqueId(), e.getFrom());
  }

  @EventHandler
  public void onBackPlayerDeath(PlayerDeathEvent e) {
    if (e.isCancelled()) return;
    central.getBackManager().setLastBackLocation(e.getPlayer().getUniqueId(), e.getPlayer().getLocation());
  }
}
