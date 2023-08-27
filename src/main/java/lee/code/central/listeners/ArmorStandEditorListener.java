package lee.code.central.listeners;

import lee.code.central.Central;
import lee.code.central.lang.Lang;
import lee.code.central.managers.ArmorStandManager;
import lee.code.central.menus.menu.ArmorStandEditor;
import lee.code.central.menus.system.MenuPlayerData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ArmorStandEditorListener implements Listener {
  private final Central central;

  public ArmorStandEditorListener(Central central) {
    this.central = central;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onEditArmorStand(PlayerInteractAtEntityEvent e) {
    if (e.isCancelled()) return;
    final Player player = e.getPlayer();
    if (e.getRightClicked() instanceof ArmorStand armorStand) {
      final ArmorStandManager armorStandManager = central.getArmorStandManager();
      if (armorStandManager.isBeingEdited(armorStand.getUniqueId())) {
        e.setCancelled(true);
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_ARMOR_STAND_BEING_EDITED.getComponent(null)));
        return;
      }
      if (!player.isSneaking()) return;
      e.setCancelled(true);
      armorStandManager.setEditingArmorStand(armorStand.getUniqueId());
      final MenuPlayerData menuPlayerData = central.getMenuManager().getMenuPlayerData(player.getUniqueId());
      central.getMenuManager().openMenu(new ArmorStandEditor(armorStandManager, armorStand, menuPlayerData), player);
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onEditingArmorStandDamage(EntityDamageEvent e) {
    if (e.isCancelled()) return;
    if (e.getEntity() instanceof ArmorStand armorStand) {
      if (central.getArmorStandManager().isBeingEdited(armorStand.getUniqueId())) e.setCancelled(true);
    }
  }
}
