package lee.code.central.listeners;

import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import lee.code.central.Central;
import lee.code.central.enums.PlaceLimitMaterial;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MobLimitListener implements Listener {
  private final Central central;

  public MobLimitListener(Central central) {
    this.central = central;
  }

  @EventHandler
  public void onMobPreSpawn(PreCreatureSpawnEvent e) {
    if (e.getReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
    e.setCancelled(central.getMobLimitManager().hasReachedMobLimit(e.getSpawnLocation().getChunk(), e.getType()));
  }

  @EventHandler
  public void onMobSpawn(CreatureSpawnEvent e) {
    if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
    e.setCancelled(central.getMobLimitManager().hasReachedMobLimit(e.getEntity().getChunk(), e.getEntityType()));
  }

  @EventHandler
  public void onEntityPlace(PlayerInteractEvent e) {
    if (!e.hasBlock()) return;
    final Material itemType = e.getPlayer().getInventory().getItemInMainHand().getType();
    if (!central.getData().getEntityPlaceLimitMaterials().contains(itemType.name())) return;
    final EntityType entityType = PlaceLimitMaterial.valueOf(itemType.name()).getEntityType();
    if (central.getMobLimitManager().hasReachedMobLimit(e.getPlayer().getLocation().getChunk(), entityType)) {
      e.setCancelled(true);
      central.getMobLimitManager().sendPlayerLimitMessage(e.getPlayer(), entityType);
    }
  }
}
