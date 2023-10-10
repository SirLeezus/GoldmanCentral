package lee.code.central.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DragonListener implements Listener {

  @EventHandler
  public void onDragonDeath(EntityDeathEvent e) {
    if (!(e.getEntity() instanceof EnderDragon)) return;
    if (!e.getEntity().getWorld().getEnvironment().equals(World.Environment.THE_END)) return;
    final DragonBattle battle = e.getEntity().getWorld().getEnderDragonBattle();
    if (battle == null || !battle.hasBeenPreviouslyKilled()) return;
    final Location eggLoc = new Location(e.getEntity().getWorld(), 0, 69, 0);
    final Block eggBlock = eggLoc.getBlock();
    eggBlock.setType(Material.DRAGON_EGG);
  }
}
