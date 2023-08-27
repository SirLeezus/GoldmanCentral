package lee.code.central.listeners;

import lee.code.central.utils.ItemUtil;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class SpawnerListener implements Listener {

  @EventHandler(priority = EventPriority.MONITOR)
  public void onSpawnerBreak(BlockBreakEvent e) {
    if (e.isCancelled()) return;
    if (e.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
      final ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
      if (!handItem.containsEnchantment(Enchantment.SILK_TOUCH) && !e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
        return;
      final ItemStack spawner = ItemUtil.createSpawner(creatureSpawner.getSpawnedType());
      e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), spawner);
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onSpawnerPlace(BlockPlaceEvent e) {
    if (e.isCancelled()) return;
    if (e.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
      final ItemStack handSpawner = e.getPlayer().getInventory().getItemInMainHand();
      final BlockStateMeta handSpawnerMeta = (BlockStateMeta) handSpawner.getItemMeta();
      if (handSpawnerMeta == null) return;
      final CreatureSpawner handCreatureSpawner = (CreatureSpawner) handSpawnerMeta.getBlockState();
      creatureSpawner.setSpawnedType(handCreatureSpawner.getSpawnedType());
      creatureSpawner.update();
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onSpawnerExplode(EntityExplodeEvent e) {
    if (e.isCancelled()) return;
    for (Block block : e.blockList()) {
      if (block.getState() instanceof CreatureSpawner creatureSpawner) {
        block.getWorld().dropItemNaturally(block.getLocation(), ItemUtil.createSpawner(creatureSpawner.getSpawnedType()));
      }
    }
  }
}
