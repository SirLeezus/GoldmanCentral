package lee.code.central.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HopperFilterListener  implements Listener {

  @EventHandler(priority = EventPriority.MONITOR)
  public void onHopperFilterMove(InventoryMoveItemEvent e) {
    if (e.isCancelled()) return;
    if (e.getDestination().getHolder() instanceof Hopper) {
      final ItemStack movingItem = e.getItem();
      final Location location = e.getDestination().getLocation();
      if (location != null) {
        final Block block = location.getBlock();
        final List<ItemFrame> frames = findAttachedItemFrames(block);
        if (frames != null && !frames.isEmpty()) {
          final List<Material> whitelist = new ArrayList<>();
          for (ItemFrame frame : frames) whitelist.add(frame.getItem().getType());
          if (!whitelist.contains(movingItem.getType())) e.setCancelled(true);
        }
      }
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onHopperFilterPickup(InventoryPickupItemEvent e) {
    if (e.isCancelled()) return;
    if (e.getInventory().getHolder() instanceof Hopper) {
      final ItemStack pickupItem = e.getItem().getItemStack();
      final Location location = e.getInventory().getLocation();
      if (location != null) {
        final Block block = location.getBlock();
        final List<ItemFrame> frames = findAttachedItemFrames(block);
        if (frames != null && !frames.isEmpty()) {
          final List<Material> whitelist = new ArrayList<>();
          for (ItemFrame frame : frames) whitelist.add(frame.getItem().getType());
          if (!whitelist.contains(pickupItem.getType())) e.setCancelled(true);
        }
      }
    }
  }

  private List<ItemFrame> findAttachedItemFrames(Block block) {
    return block.getWorld().getNearbyEntities(block.getLocation(), 2, 2, 2).stream()
      .filter(f -> f instanceof ItemFrame).map(ItemFrame.class::cast)
      .filter(f -> block.equals(getHopperAttachedTo(f).orElse(null))).collect(Collectors.toList());
  }

  private Optional<Block> getHopperAttachedTo(ItemFrame frame) {
    final Block attached = frame.getLocation().getBlock().getRelative(frame.getAttachedFace());
    if (attached.getType() != Material.HOPPER) return Optional.empty();
    return Optional.of(attached);
  }
}
