package lee.code.central.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ItemFrameListener implements Listener {

  @EventHandler (priority = EventPriority.MONITOR)
  public void onPlayerShearItemFrame(PlayerInteractEntityEvent e) {
    if (e.isCancelled()) return;
    if (!e.getPlayer().isSneaking()) return;
    final ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
    if (!handItem.getType().equals(Material.SHEARS)) return;
    if (!(e.getRightClicked() instanceof ItemFrame itemFrame)) return;
    e.setCancelled(true);
    itemFrame.setVisible(!itemFrame.isVisible());
    e.getPlayer().playSound(e.getPlayer(), Sound.ENTITY_SHEEP_SHEAR, (float) 0.5, (float) 1);
  }

  @EventHandler (priority = EventPriority.MONITOR)
  public void onInvisibleItemFrameDamage(EntityDamageByEntityEvent e) {
    if (e.isCancelled()) return;
    if (!(e.getEntity() instanceof ItemFrame itemFrame)) return;
    if (!itemFrame.isVisible()) itemFrame.setVisible(true);
  }
}
