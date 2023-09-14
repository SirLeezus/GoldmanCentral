package lee.code.central.menus.menu;

import lee.code.central.lang.Lang;
import lee.code.central.managers.ArmorStandManager;
import lee.code.central.menus.menu.menudata.armorstand.ArmorStandCoordinate;
import lee.code.central.menus.menu.menudata.armorstand.ArmorStandItem;
import lee.code.central.menus.menu.menudata.armorstand.ArmorStandPosition;
import lee.code.central.menus.menu.menudata.armorstand.ArmorStandSetting;
import lee.code.central.menus.system.MenuButton;
import lee.code.central.menus.system.MenuGUI;
import lee.code.central.utils.CoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class ArmorStandEditorMenu extends MenuGUI {
  private final ArmorStandManager armorStandManager;
  private final ArmorStand armorStand;

  public ArmorStandEditorMenu(ArmorStandManager armorStandManager, ArmorStand armorStand) {
    this.armorStandManager = armorStandManager;
    this.armorStand = armorStand;
    setInventory();
  }

  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 54, Lang.MENU_ARMOR_STAND_EDITOR_TITLE.getComponent(null));
  }

  @Override
  public void decorate(Player player) {
    addFillerGlass();
    for (ArmorStandItem armorStandItem : ArmorStandItem.values()) {
      addButton(armorStandItem.getSlot(), createButton(player, armorStandItem));
    }
    super.decorate(player);
  }

  @Override
  public void onClose(InventoryCloseEvent e) {
    armorStandManager.removeEditingArmorStand(armorStand.getUniqueId());
  }

  private MenuButton createButton(Player player, ArmorStandItem armorStandItem) {
    if (armorStandItem.getArmorStandSetting() != null) {
      return new MenuButton().creator(p -> armorStandItem.createSetting(checkArmorStandSetting(armorStandItem.getArmorStandSetting())))
        .consumer(e -> {
          getMenuSoundManager().playClickSound(player);
          final boolean currentResult = checkArmorStandSetting(armorStandItem.getArmorStandSetting());
          updateArmorStandSetting(armorStandItem.getArmorStandSetting(), !currentResult);
          final ItemStack item = e.getCurrentItem();
          if (item == null) return;
          final ItemStack newItem = armorStandItem.createSetting(!currentResult);
          item.setItemMeta(newItem.getItemMeta());
          item.setType(newItem.getType());
        });
    } else if (armorStandItem.getArmorStandPosition() != null) {
      return new MenuButton().creator(p -> armorStandItem.createPosition(CoreUtil.parseShortDecimalValue(checkArmorStandPosition(armorStandItem.getArmorStandPosition(), armorStandItem.getArmorStandCoordinate()))))
        .consumer(e -> {
          getMenuSoundManager().playClickSound(player);
          double amount = 0.01;
          if (e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) amount = 0.10;
          if (e.getClick().isLeftClick()) amount = -amount;
          updateArmorStandPosition(armorStandItem.getArmorStandPosition(), armorStandItem.getArmorStandCoordinate(), amount);
          final ItemStack item = e.getCurrentItem();
          if (item == null) return;
          final ItemStack newItem = armorStandItem.createPosition(CoreUtil.parseShortDecimalValue(checkArmorStandPosition(armorStandItem.getArmorStandPosition(), armorStandItem.getArmorStandCoordinate())));
          item.setItemMeta(newItem.getItemMeta());
          item.setType(newItem.getType());
        });
    } else if (armorStandItem.isDirection()) {
      return new MenuButton().creator(p -> armorStandItem.createDirection(CoreUtil.parseShortDecimalValue(armorStand.getLocation().getYaw()), armorStand.getLocation()))
        .consumer(e -> {
          getMenuSoundManager().playClickSound(player);
          double amount = 1;
          if (e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) amount = 10;
          if (e.getClick().isLeftClick()) amount = -amount;
          updateArmorStandDirection(amount);
          final ItemStack item = e.getCurrentItem();
          if (item == null) return;
          final ItemStack newItem = armorStandItem.createDirection(CoreUtil.parseShortDecimalValue(armorStand.getLocation().getYaw()), armorStand.getLocation());
          item.setItemMeta(newItem.getItemMeta());
        });
    } else if (armorStandItem.getEquipmentSlot() != null) {
      return new MenuButton().creator(p -> armorStandItem.createSlot(armorStand.getEquipment().getItem(armorStandItem.getEquipmentSlot())))
        .consumer(e -> {
          getMenuSoundManager().playClickSound(player);
          if (e.getCurrentItem() == null) return;
          if (e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) return;
          equipmentChange(player, armorStandItem, armorStandItem.getEquipmentSlot(), e.getCurrentItem(), e.getCursor());
        });
    }
    return null;
  }

  private boolean checkArmorStandSetting(ArmorStandSetting armorStandSetting) {
    switch (armorStandSetting) {
      case ARMS -> {
        return armorStand.hasArms();
      }
      case VISIBLE -> {
        return armorStand.isVisible();
      }
      case SMALL -> {
        return armorStand.isSmall();
      }
      case INVULNERABLE -> {
        return armorStand.isInvulnerable();
      }
      case PLATE -> {
        return armorStand.hasBasePlate();
      }
      case GLOWING -> {
        return armorStand.isGlowing();
      }
      case GRAVITY -> {
        return armorStand.hasGravity();
      }
      case CUSTOM_NAME_VISIBLE -> {
        return armorStand.isCustomNameVisible();
      }
      default -> {
        return false;
      }
    }
  }

  private void updateArmorStandSetting(ArmorStandSetting armorStandSetting, boolean result) {
    switch (armorStandSetting) {
      case ARMS -> armorStand.setArms(result);
      case VISIBLE -> armorStand.setVisible(result);
      case SMALL -> armorStand.setSmall(result);
      case INVULNERABLE -> armorStand.setInvulnerable(result);
      case PLATE -> armorStand.setBasePlate(result);
      case GLOWING -> armorStand.setGlowing(result);
      case GRAVITY -> armorStand.setGravity(result);
      case CUSTOM_NAME_VISIBLE -> armorStand.setCustomNameVisible(result);
    }
  }

  private void updateArmorStandPosition(ArmorStandPosition armorStandPosition, ArmorStandCoordinate armorStandCoordinate, double amount) {
    switch (armorStandPosition) {
      case BODY -> {
        switch (armorStandCoordinate) {
          case X ->
            armorStand.teleport(new Location(armorStand.getWorld(), armorStand.getLocation().getX() + amount, armorStand.getLocation().getY(), armorStand.getLocation().getZ()));
          case Y -> {
            if (!armorStand.hasGravity()) {
              armorStand.teleport(new Location(armorStand.getWorld(), armorStand.getLocation().getX(), armorStand.getLocation().getY() + amount, armorStand.getLocation().getZ()));
            }
          }
          case Z ->
            armorStand.teleport(new Location(armorStand.getWorld(), armorStand.getLocation().getX(), armorStand.getLocation().getY(), armorStand.getLocation().getZ() + amount));
        }
      }
      case HEAD -> {
        switch (armorStandCoordinate) {
          case X ->
            armorStand.setHeadPose(new EulerAngle(armorStand.getHeadPose().getX() + amount, armorStand.getHeadPose().getY(), armorStand.getHeadPose().getZ()));
          case Y ->
            armorStand.setHeadPose(new EulerAngle(armorStand.getHeadPose().getX(), armorStand.getHeadPose().getY() + amount, armorStand.getHeadPose().getZ()));
          case Z ->
            armorStand.setHeadPose(new EulerAngle(armorStand.getHeadPose().getX(), armorStand.getHeadPose().getY(), armorStand.getHeadPose().getZ() + amount));
        }
      }
      case TORSO -> {
        switch (armorStandCoordinate) {
          case X ->
            armorStand.setBodyPose(new EulerAngle(armorStand.getBodyPose().getX() + amount, armorStand.getBodyPose().getY(), armorStand.getBodyPose().getZ()));
          case Y ->
            armorStand.setBodyPose(new EulerAngle(armorStand.getBodyPose().getX(), armorStand.getBodyPose().getY() + amount, armorStand.getBodyPose().getZ()));
          case Z ->
            armorStand.setBodyPose(new EulerAngle(armorStand.getBodyPose().getX(), armorStand.getBodyPose().getY(), armorStand.getBodyPose().getZ() + amount));
        }
      }
      case RIGHT_LEG -> {
        switch (armorStandCoordinate) {
          case X ->
            armorStand.setRightLegPose(new EulerAngle(armorStand.getRightLegPose().getX() + amount, armorStand.getRightLegPose().getY(), armorStand.getRightLegPose().getZ()));
          case Y ->
            armorStand.setRightLegPose(new EulerAngle(armorStand.getRightLegPose().getX(), armorStand.getRightLegPose().getY() + amount, armorStand.getRightLegPose().getZ()));
          case Z ->
            armorStand.setRightLegPose(new EulerAngle(armorStand.getRightLegPose().getX(), armorStand.getRightLegPose().getY(), armorStand.getRightLegPose().getZ() + amount));
        }
      }
      case LEFT_LEG -> {
        switch (armorStandCoordinate) {
          case X ->
            armorStand.setLeftLegPose(new EulerAngle(armorStand.getLeftLegPose().getX() + amount, armorStand.getLeftLegPose().getY(), armorStand.getLeftLegPose().getZ()));
          case Y ->
            armorStand.setLeftLegPose(new EulerAngle(armorStand.getLeftLegPose().getX(), armorStand.getLeftLegPose().getY() + amount, armorStand.getLeftLegPose().getZ()));
          case Z ->
            armorStand.setLeftLegPose(new EulerAngle(armorStand.getLeftLegPose().getX(), armorStand.getLeftLegPose().getY(), armorStand.getLeftLegPose().getZ() + amount));
        }
      }
      case RIGHT_ARM -> {
        switch (armorStandCoordinate) {
          case X ->
            armorStand.setRightArmPose(new EulerAngle(armorStand.getRightArmPose().getX() + amount, armorStand.getRightArmPose().getY(), armorStand.getRightArmPose().getZ()));
          case Y ->
            armorStand.setRightArmPose(new EulerAngle(armorStand.getRightArmPose().getX(), armorStand.getRightArmPose().getY() + amount, armorStand.getRightArmPose().getZ()));
          case Z ->
            armorStand.setRightArmPose(new EulerAngle(armorStand.getRightArmPose().getX(), armorStand.getRightArmPose().getY(), armorStand.getRightArmPose().getZ() + amount));
        }
      }
      case LEFT_ARM -> {
        switch (armorStandCoordinate) {
          case X ->
            armorStand.setLeftArmPose(new EulerAngle(armorStand.getLeftArmPose().getX() + amount, armorStand.getLeftArmPose().getY(), armorStand.getLeftArmPose().getZ()));
          case Y ->
            armorStand.setLeftArmPose(new EulerAngle(armorStand.getLeftArmPose().getX(), armorStand.getLeftArmPose().getY() + amount, armorStand.getLeftArmPose().getZ()));
          case Z ->
            armorStand.setLeftArmPose(new EulerAngle(armorStand.getLeftArmPose().getX(), armorStand.getLeftArmPose().getY(), armorStand.getLeftArmPose().getZ() + amount));
        }
      }
    }
  }

  private double checkArmorStandPosition(ArmorStandPosition armorStandPosition, ArmorStandCoordinate armorStandCoordinate) {
    switch (armorStandPosition) {
      case BODY -> {
        switch (armorStandCoordinate) {
          case X -> {
            return armorStand.getLocation().getX();
          }
          case Y -> {
            return armorStand.getLocation().getY();
          }
          case Z -> {
            return armorStand.getLocation().getZ();
          }
        }
      }
      case HEAD -> {
        switch (armorStandCoordinate) {
          case X -> {
            return armorStand.getHeadPose().getX();
          }
          case Y -> {
            return armorStand.getHeadPose().getY();
          }
          case Z -> {
            return armorStand.getHeadPose().getZ();
          }
        }
      }
      case TORSO -> {
        switch (armorStandCoordinate) {
          case X -> {
            return armorStand.getBodyPose().getX();
          }
          case Y -> {
            return armorStand.getBodyPose().getY();
          }
          case Z -> {
            return armorStand.getBodyPose().getZ();
          }
        }
      }
      case RIGHT_ARM -> {
        switch (armorStandCoordinate) {
          case X -> {
            return armorStand.getRightArmPose().getX();
          }
          case Y -> {
            return armorStand.getRightArmPose().getY();
          }
          case Z -> {
            return armorStand.getRightArmPose().getZ();
          }
        }
      }
      case LEFT_ARM -> {
        switch (armorStandCoordinate) {
          case X -> {
            return armorStand.getLeftArmPose().getX();
          }
          case Y -> {
            return armorStand.getLeftArmPose().getY();
          }
          case Z -> {
            return armorStand.getLeftArmPose().getZ();
          }
        }
      }
      case RIGHT_LEG -> {
        switch (armorStandCoordinate) {
          case X -> {
            return armorStand.getRightLegPose().getX();
          }
          case Y -> {
            return armorStand.getRightLegPose().getY();
          }
          case Z -> {
            return armorStand.getRightLegPose().getZ();
          }
        }
      }
      case LEFT_LEG -> {
        switch (armorStandCoordinate) {
          case X -> {
            return armorStand.getLeftLegPose().getX();
          }
          case Y -> {
            return armorStand.getLeftLegPose().getY();
          }
          case Z -> {
            return armorStand.getLeftLegPose().getZ();
          }
        }
      }
    }
    return 0;
  }

  private void updateArmorStandDirection(double amount) {
    armorStand.teleport(new Location(armorStand.getWorld(), armorStand.getLocation().getX(), armorStand.getLocation().getY(), armorStand.getLocation().getZ(), armorStand.getLocation().getYaw() + (float) amount, armorStand.getLocation().getPitch()));
  }

  private void equipmentChange(Player player, ArmorStandItem armorStandItem, EquipmentSlot equipmentSlot, ItemStack currentItem, ItemStack cursorItem) {
    if (currentItem.getType().equals(Material.STRUCTURE_VOID) && !cursorItem.getType().isAir()) {
      final ItemStack newCursorItem = new ItemStack(cursorItem);
      currentItem.setType(newCursorItem.getType());
      currentItem.setItemMeta(newCursorItem.getItemMeta());
      currentItem.setAmount(newCursorItem.getAmount());
      armorStand.getEquipment().setItem(equipmentSlot, newCursorItem);
      player.setItemOnCursor(new ItemStack(Material.AIR));
    } else if (!currentItem.getType().equals(Material.STRUCTURE_VOID) && cursorItem.getType().isAir()) {
      player.setItemOnCursor(new ItemStack(currentItem));
      armorStand.getEquipment().setItem(equipmentSlot, new ItemStack(Material.AIR));
      final ItemStack noneItem = armorStandItem.createSlot(null);
      currentItem.setType(noneItem.getType());
      currentItem.setItemMeta(noneItem.getItemMeta());
      currentItem.setAmount(1);
    } else if (!currentItem.getType().equals(Material.STRUCTURE_VOID) && !cursorItem.getType().isAir()) {
      final ItemStack newCursorItem = new ItemStack(cursorItem);
      final ItemStack newCurrentItem = new ItemStack(currentItem);
      currentItem.setType(newCursorItem.getType());
      currentItem.setItemMeta(newCursorItem.getItemMeta());
      currentItem.setAmount(newCursorItem.getAmount());
      armorStand.getEquipment().setItem(equipmentSlot, newCursorItem);
      player.setItemOnCursor(newCurrentItem);
    }
  }
}
