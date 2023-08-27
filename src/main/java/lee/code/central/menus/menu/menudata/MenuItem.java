package lee.code.central.menus.menu.menudata;

import lee.code.central.utils.ItemUtil;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum MenuItem {
  FILLER_GLASS(Material.BLACK_STAINED_GLASS_PANE, "", null, false, false, null),

  ;

  private final Material material;
  private final String name;
  private final String lore;
  private final boolean hideItemFlags;
  private final boolean enchantItem;
  private final String skin;

  public ItemStack createItem() {
    final ItemStack item = ItemUtil.createItem(material, name, lore, 0, skin);
    if (hideItemFlags) ItemUtil.hideItemFlags(item);
    if (enchantItem) ItemUtil.enchantItem(item, Enchantment.ARROW_INFINITE, 1);
    return item;
  }
}
