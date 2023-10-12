package lee.code.central.listeners;

import lee.code.central.Central;
import lee.code.central.lang.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PvPListener implements Listener {
  private final Central central;

  public PvPListener(Central central) {
    this.central = central;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPvP(EntityDamageByEntityEvent e) {
    if (e.isCancelled()) return;
    if (!(e.getEntity() instanceof Player victim)) return;
    if (!(e.getDamager() instanceof Player attacker)) return;
    central.getPvpManager().addPlayers(attacker.getUniqueId(), victim.getUniqueId());
  }

  @EventHandler
  public void onCommandWhileInPvP(PlayerCommandPreprocessEvent e) {
    if (!central.getPvpManager().isPvPing(e.getPlayer().getUniqueId())) return;
    e.setCancelled(true);
    e.getPlayer().sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PVP_COMMAND.getComponent(new String[]{central.getPvpManager().getDelayTime(e.getPlayer().getUniqueId())})));
  }

  @EventHandler
  public void onTeleportWhileInPvP(PlayerTeleportEvent e) {
    if (!central.getPvpManager().isPvPing(e.getPlayer().getUniqueId())) return;
    if (!e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL) && !e.getCause().equals(PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT)) return;
    e.setCancelled(true);
    e.getPlayer().sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PVP_TELEPORT.getComponent(new String[]{central.getPvpManager().getDelayTime(e.getPlayer().getUniqueId())})));
  }

  @EventHandler
  public void onLogoutWhileInPvP(PlayerQuitEvent e) {
    if (!central.getPvpManager().isPvPing(e.getPlayer().getUniqueId())) return;
    e.getPlayer().setHealth(0);
  }
}
