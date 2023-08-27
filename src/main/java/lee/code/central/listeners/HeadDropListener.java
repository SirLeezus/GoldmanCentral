package lee.code.central.listeners;

import lee.code.central.utils.HeadUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class HeadDropListener implements Listener {

  @EventHandler(priority = EventPriority.MONITOR)
  public void onHeadDrop(EntityDeathEvent e) {
    if (e.isCancelled()) return;
    if (e.getEntity().getKiller() == null) return;
    final Iterator<ItemStack> iterator = e.getDrops().iterator();

    while (iterator.hasNext()) {
      final ItemStack item = iterator.next();
      if (item.getType().equals(Material.CREEPER_HEAD)) iterator.remove();
      else if (item.getType().equals(Material.ZOMBIE_HEAD)) iterator.remove();
      else if (item.getType().equals(Material.CREEPER_HEAD)) iterator.remove();
      else if (item.getType().equals(Material.WITHER_SKELETON_SKULL)) iterator.remove();
      else if (item.getType().equals(Material.ZOMBIE_HEAD)) iterator.remove();
      else if (item.getType().equals(Material.DRAGON_HEAD)) iterator.remove();
    }
    final Player killer = e.getEntity().getKiller();
    final int rng = killer.getGameMode().equals(GameMode.CREATIVE) ? 1000 : HeadUtil.headDropRNG();
    final ItemStack head = HeadUtil.getEntityHead(e.getEntity(), rng);
    if (head == null) return;
    e.getDrops().add(head);
  }
}
