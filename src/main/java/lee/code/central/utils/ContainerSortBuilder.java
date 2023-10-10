package lee.code.central.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ContainerSortBuilder {
  private final List<ItemStack> items;

  public ContainerSortBuilder() {
    this.items = new ArrayList<>();
  }

  public void addItem(ItemStack item) {
    items.add(item);
  }

  @SuppressWarnings("deprecation")
  public List<ItemStack> sort() {
    // Sort the items alphabetically by item name
    items.sort((o1, o2) -> {
      final ItemMeta meta1 = o1.getItemMeta();
      final ItemMeta meta2 = o2.getItemMeta();
      final String name1 = meta1.hasDisplayName() ? meta1.getDisplayName() : o1.getType().name();
      final String name2 = meta2.hasDisplayName() ? meta2.getDisplayName() : o2.getType().name();
      return name1.compareTo(name2);
    });

    // Combine items if they are not at max stack size (e.g., 64 for most items)
    final List<ItemStack> sortedItems = new ArrayList<>();
    for (ItemStack item : items) {
      boolean combined = false;
      for (ItemStack sortedItem : sortedItems) {
        if (sortedItem.isSimilar(item) && sortedItem.getAmount() < sortedItem.getMaxStackSize()) {
          final int spaceLeft = sortedItem.getMaxStackSize() - sortedItem.getAmount();
          if (item.getAmount() <= spaceLeft) {
            sortedItem.setAmount(sortedItem.getAmount() + item.getAmount());
            combined = true;
            break;
          }
        }
      }
      if (!combined) {
        sortedItems.add(item);
      }
    }
    return sortedItems;
  }
}
