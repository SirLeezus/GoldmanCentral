package lee.code.central.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TrampleListener implements Listener {

  @EventHandler
  public void onTrampleMob(EntityInteractEvent e) {
    if (e.getEntity() instanceof Player) return;
    if (!e.getBlock().getType().equals(Material.FARMLAND)) return;
    e.setCancelled(true);
  }

  @EventHandler
  public void onTramplePlayer(PlayerInteractEvent e) {
    if (!e.getAction().equals(Action.PHYSICAL)) return;
    if (e.getClickedBlock() == null) return;
    if (!e.getClickedBlock().getType().equals(Material.FARMLAND)) return;
    e.setCancelled(true);
  }
}
