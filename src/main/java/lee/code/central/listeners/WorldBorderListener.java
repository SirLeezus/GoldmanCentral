package lee.code.central.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
public class WorldBorderListener implements Listener {

  @EventHandler(priority = EventPriority.MONITOR)
  public void onTeleportOutsideBorder(PlayerTeleportEvent e) {
    if (e.isCancelled()) return;
    final Location location = e.getTo();
    if (location.getWorld().getWorldBorder().isInside(location)) return;
    e.setCancelled(true);
  }
}
