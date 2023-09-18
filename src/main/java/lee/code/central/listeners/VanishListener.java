package lee.code.central.listeners;

import lee.code.central.Central;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class VanishListener implements Listener {
  private final Central central;

  public VanishListener(Central central) {
    this.central = central;
  }

  @EventHandler
  public void onVanishHit(EntityDamageEvent e) {
    if (e.getEntity() instanceof Player player) {
      if (central.getCacheManager().getCachePlayers().isVanished(player.getUniqueId())) {
        e.setCancelled(true);
      }
    }
  }
}
