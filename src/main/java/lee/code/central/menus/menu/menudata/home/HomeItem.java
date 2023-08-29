package lee.code.central.menus.menu.menudata.home;

import lee.code.central.lang.Lang;
import lee.code.central.utils.ItemUtil;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum HomeItem {
  HOME(Material.AIR, "", "&e» Click to teleport to home!"),
  HOME_BED(Material.RED_BED, "&c&lBed", "&e» Click to teleport to your bed!"),
  ;

  private final Material material;
  private final String name;
  private final String lore;

  public ItemStack createItem() {
    return ItemUtil.createItem(material, name, lore, 0, null);
  }

  public ItemStack createHomeItem(String world, String name) {
    final Material mat;
    switch (world) {
      case "world_nether" -> mat = Material.NETHERRACK;
      case "world_the_end" -> mat = Material.END_STONE;
      default -> mat = Material.GRASS_BLOCK;
    }
    return ItemUtil.createItem(mat, Lang.MENU_HOME_ITEM_NAME.getString(new String[]{name}), lore, 0, null);
  }
}
