package lee.code.central.menus.menu.menudata.armorstand;

import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.ItemUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

@AllArgsConstructor
public enum ArmorStandItem {

    ARMOR_STAND_BODY_X_POSITION(Material.BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 3, null, ArmorStandPosition.BODY, ArmorStandCoordinate.X, null),
    ARMOR_STAND_BODY_Y_POSITION(Material.BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false,4, null, ArmorStandPosition.BODY, ArmorStandCoordinate.Y, null),
    ARMOR_STAND_BODY_Z_POSITION(Material.BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 5, null, ArmorStandPosition.BODY, ArmorStandCoordinate.Z, null),

    ARMOR_STAND_HEAD_X_POSITION(Material.YELLOW_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 10, null, ArmorStandPosition.HEAD, ArmorStandCoordinate.X, null),
    ARMOR_STAND_HEAD_Y_POSITION(Material.YELLOW_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 11, null, ArmorStandPosition.HEAD, ArmorStandCoordinate.Y, null),
    ARMOR_STAND_HEAD_Z_POSITION(Material.YELLOW_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 12, null, ArmorStandPosition.HEAD, ArmorStandCoordinate.Z, null),

    ARMOR_STAND_TORSO_X_POSITION(Material.ORANGE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 14, null, ArmorStandPosition.TORSO, ArmorStandCoordinate.X, null),
    ARMOR_STAND_TORSO_Y_POSITION(Material.ORANGE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 15, null, ArmorStandPosition.TORSO, ArmorStandCoordinate.Y, null),
    ARMOR_STAND_TORSO_Z_POSITION(Material.ORANGE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 16, null, ArmorStandPosition.TORSO, ArmorStandCoordinate.Z, null),

    ARMOR_STAND_RIGHT_ARM_X_POSITION(Material.MAGENTA_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 24, null, ArmorStandPosition.RIGHT_ARM, ArmorStandCoordinate.X, null),
    ARMOR_STAND_RIGHT_ARM_Y_POSITION(Material.MAGENTA_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 25, null, ArmorStandPosition.RIGHT_ARM, ArmorStandCoordinate.Y, null),
    ARMOR_STAND_RIGHT_ARM_Z_POSITION(Material.MAGENTA_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 26, null, ArmorStandPosition.RIGHT_ARM, ArmorStandCoordinate.Z, null),

    ARMOR_STAND_LEFT_ARM_X_POSITION(Material.MAGENTA_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 18, null, ArmorStandPosition.LEFT_ARM, ArmorStandCoordinate.X, null),
    ARMOR_STAND_LEFT_ARM_Y_POSITION(Material.MAGENTA_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null),false,  19, null, ArmorStandPosition.LEFT_ARM, ArmorStandCoordinate.Y, null),
    ARMOR_STAND_LEFT_ARM_Z_POSITION(Material.MAGENTA_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null),false, 20, null, ArmorStandPosition.LEFT_ARM, ArmorStandCoordinate.Z, null),

    ARMOR_STAND_RIGHT_LEG_X_POSITION(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 32, null, ArmorStandPosition.RIGHT_LEG, ArmorStandCoordinate.X, null),
    ARMOR_STAND_RIGHT_LEG_Y_POSITION(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 33, null, ArmorStandPosition.RIGHT_LEG, ArmorStandCoordinate.Y, null),
    ARMOR_STAND_RIGHT_LEG_Z_POSITION(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 34, null, ArmorStandPosition.RIGHT_LEG, ArmorStandCoordinate.Z, null),

    ARMOR_STAND_LEFT_LEG_X_POSITION(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 28, null, ArmorStandPosition.LEFT_LEG, ArmorStandCoordinate.X, null),
    ARMOR_STAND_LEFT_LEG_Y_POSITION(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 29, null, ArmorStandPosition.LEFT_LEG, ArmorStandCoordinate.Y, null),
    ARMOR_STAND_LEFT_LEG_Z_POSITION(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "", Lang.MENU_ARMOR_STAND_POSITION_LORE.getString(null), false, 30, null, ArmorStandPosition.LEFT_LEG, ArmorStandCoordinate.Z, null),

    ARMOR_STAND_SETTING_INVULNERABLE(Material.RED_STAINED_GLASS_PANE, "", null,false, 45, ArmorStandSetting.INVULNERABLE, null, null, null),
    ARMOR_STAND_SETTING_ARMS(Material.RED_STAINED_GLASS_PANE, "", null, false, 46, ArmorStandSetting.ARMS, null, null, null),
    ARMOR_STAND_SETTING_GLOWING(Material.RED_STAINED_GLASS_PANE, "", null, false, 47, ArmorStandSetting.GLOWING, null, null, null),
    ARMOR_STAND_SETTING_PLATE(Material.RED_STAINED_GLASS_PANE, "", null, false, 48, ArmorStandSetting.PLATE, null, null, null),
    ARMOR_STAND_SETTING_VISIBLE(Material.RED_STAINED_GLASS_PANE, "", null, false, 50, ArmorStandSetting.VISIBLE, null, null, null),
    ARMOR_STAND_SETTING_SMALL(Material.RED_STAINED_GLASS_PANE, "", null, false, 51, ArmorStandSetting.SMALL, null, null, null),
    ARMOR_STAND_SETTING_GRAVITY(Material.RED_STAINED_GLASS_PANE, "", null, false, 52, ArmorStandSetting.GRAVITY, null, null, null),
    ARMOR_STAND_SETTING_CUSTOM_NAME_VISIBLE(Material.RED_STAINED_GLASS_PANE, "", null, false, 53, ArmorStandSetting.CUSTOM_NAME_VISIBLE, null, null, null),
    ARMOR_STAND_ROTATION(Material.COMPASS, "", Lang.MENU_ARMOR_STAND_DIRECTION_LORE.getString(null), true, 49, null, null, null, null),

    ARMOR_STAND_SLOT_HEAD(Material.STRUCTURE_VOID, "", null, false, 13, null, null, null, EquipmentSlot.HEAD),
    ARMOR_STAND_SLOT_CHEST(Material.STRUCTURE_VOID, "", null, false, 22, null, null, null, EquipmentSlot.CHEST),
    ARMOR_STAND_SLOT_LEGS(Material.STRUCTURE_VOID, "", null, false, 31, null, null, null, EquipmentSlot.LEGS),
    ARMOR_STAND_SLOT_FEET(Material.STRUCTURE_VOID, "", null, false, 40, null, null, null, EquipmentSlot.FEET),
    ARMOR_STAND_SLOT_HAND(Material.STRUCTURE_VOID, "", null, false, 23, null, null, null, EquipmentSlot.HAND),
    ARMOR_STAND_SLOT_OFF_HAND(Material.STRUCTURE_VOID, "", null, false, 21, null, null, null, EquipmentSlot.OFF_HAND),

    ;

    private final Material material;
    private final String name;
    private final String lore;
    @Getter private final boolean direction;
    @Getter private final int slot;
    @Getter private final ArmorStandSetting armorStandSetting;
    @Getter private final ArmorStandPosition armorStandPosition;
    @Getter private final ArmorStandCoordinate armorStandCoordinate;
    @Getter private final EquipmentSlot equipmentSlot;

    public ItemStack createItem() {
        return ItemUtil.createItem(material, name, lore, 0, null);
    }

    public ItemStack createSetting(boolean result) {
        final Material resultMat = result ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;
        final String resultSetting = result ? "&a" + true : "&c" + false;
        return ItemUtil.createItem(resultMat, Lang.MENU_ARMOR_STAND_SETTING.getString(new String[] { CoreUtil.capitalize(armorStandSetting.name()), resultSetting }), lore, 0, null);
    }

    public ItemStack createPosition(String position) {
        return ItemUtil.createItem(material, Lang.MENU_ARMOR_STAND_POSITION.getString(new String[] { CoreUtil.capitalize(armorStandPosition.name()), armorStandCoordinate.name(), position }), lore, 0, null);
    }

    public ItemStack createDirection(String direction, Location armorStand) {
        final ItemStack item = ItemUtil.createItem(material, Lang.MENU_ARMOR_STAND_DIRECTION.getString(new String[] { direction }), lore, 0, null);;
        final ItemMeta itemMeta = item.getItemMeta();
        final CompassMeta compassMeta = (CompassMeta) itemMeta;
        compassMeta.setLodestone(armorStand.add(armorStand.getDirection().setY(0).normalize().multiply(5)));
        compassMeta.setLodestoneTracked(true);
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack createSlot(ItemStack itemStack) {
        if (itemStack != null && !itemStack.getType().isAir()) return new ItemStack(itemStack);
        else return createItem();
    }
}
