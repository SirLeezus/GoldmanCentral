package lee.code.central.menus.menu;

import lee.code.central.Central;
import lee.code.central.database.cache.players.data.HomeData;
import lee.code.central.lang.Lang;
import lee.code.central.menus.menu.menudata.MenuItem;
import lee.code.central.menus.menu.menudata.home.HomeItem;
import lee.code.central.menus.system.MenuButton;
import lee.code.central.menus.system.MenuGUI;
import lee.code.central.menus.system.MenuPlayerData;
import lee.code.central.utils.CoreUtil;
import lee.code.economy.EcoAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class HomeMenu extends MenuGUI {
  private final Central central;

  public HomeMenu(MenuPlayerData menuPlayerData, Central central) {
    super(menuPlayerData);
    this.central = central;
    setInventory();
  }

  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 54, Lang.MENU_HOME_TITLE.getComponent(null));
  }

  @Override
  public void decorate(Player player) {
    addBorderGlass();
    final UUID uuid = player.getUniqueId();
    final HomeData homeData = central.getCacheManager().getCachePlayers().getHomeData();
    final List<String> homes = homeData.getHomeNames(uuid);
    Collections.sort(homes);
    int slot = 0;
    paginatedPage = menuPlayerData.getPage();
    for (int i = 0; i < paginatedMaxItemsPerPage; i++) {
      paginatedIndex = paginatedMaxItemsPerPage * paginatedPage + i;
      if (paginatedIndex >= homes.size()) break;
      final String targetHomeName = homes.get(paginatedIndex);
      addButton(paginatedSlots.get(slot), createHomeButton(homeData.getHomeLocationString(uuid, targetHomeName), targetHomeName));
      slot++;
    }
    addBedButton();
    addPaginatedButtons();
    super.decorate(player);
  }

  private void addBedButton() {
    addButton(49, new MenuButton().creator(p -> HomeItem.HOME_BED.createItem())
      .consumer(e -> {
        final Player player = (Player) e.getWhoClicked();
        if (player.getBedSpawnLocation() == null) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_MENU_HOME_BED_INVALID.getComponent(null)));
          return;
        }
        player.teleportAsync(player.getBedSpawnLocation()).thenAccept(result -> {
          if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.MENU_HOME_BED_TELEPORT_SUCCESSFUL.getComponent(null)));
          else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.MENU_HOME_BED_TELEPORT_FAILED.getComponent(null)));
        });
      }));
  }

  private MenuButton createHomeButton(String location, String name) {
    final String[] locationData = location.split(",");
    return new MenuButton().creator(p -> HomeItem.HOME.createHomeItem(locationData[0], name))
      .consumer(e -> {
        final Player player = (Player) e.getWhoClicked();
        player.teleportAsync(CoreUtil.parseLocation(location)).thenAccept(result -> {
          if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_HOME_TP_SUCCESSFUL.getComponent(new String[]{name})));
          else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_HOME_TP_FAILED.getComponent(new String[]{name})));
        });
      });
  }

  private void addPaginatedButtons() {
    addButton(51, new MenuButton().creator(p -> MenuItem.NEXT_PAGE.createItem())
      .consumer(e -> {
        final Player player = (Player) e.getWhoClicked();
        if (!((paginatedIndex + 1) >= central.getCacheManager().getCachePlayers().getHomeData().getHomeAmount(player.getUniqueId()))) {
          menuPlayerData.setPage(paginatedPage + 1);
          clearInventory();
          clearButtons();
          decorate(player);
        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NEXT_PAGE.getComponent(null)));
      }));
    addButton(47, new MenuButton().creator(p -> MenuItem.PREVIOUS_PAGE.createItem())
      .consumer(e -> {
        final Player player = (Player) e.getWhoClicked();
        if (paginatedPage == 0) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PREVIOUS_PAGE.getComponent(null)));
        } else {
          menuPlayerData.setPage(paginatedPage - 1);
          clearInventory();
          clearButtons();
          decorate(player);
        }
      }));
  }
}
