package lee.code.central.menus.menu;

import lee.code.central.Central;
import lee.code.central.database.cache.players.data.MailData;
import lee.code.central.lang.Lang;
import lee.code.central.menus.menu.menudata.MenuItem;
import lee.code.central.menus.system.MenuButton;
import lee.code.central.menus.system.MenuGUI;
import lee.code.central.menus.system.MenuPlayerData;
import lee.code.central.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class MailboxMenu extends MenuGUI {
  private final Central central;

  public MailboxMenu(MenuPlayerData menuPlayerData, Central central) {
    super(menuPlayerData);
    this.central = central;
    setInventory();
  }

  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 54, Lang.MENU_MAILBOX_TITLE.getComponent(null));
  }

  @Override
  public void decorate(Player player) {
    addBorderGlass();
    final UUID uuid = player.getUniqueId();
    final MailData mailData = central.getCacheManager().getCachePlayers().getMailData();
    final List<Integer> books = mailData.getAllMailIDs(uuid);
    int slot = 0;
    paginatedPage = menuPlayerData.getPage();
    for (int i = 0; i < paginatedMaxItemsPerPage; i++) {
      paginatedIndex = paginatedMaxItemsPerPage * paginatedPage + i;
      if (paginatedIndex >= books.size()) break;
      final int targetBookID = books.get(paginatedIndex);
      addButton(paginatedSlots.get(slot), createBookButton(uuid, mailData.getMailBook(uuid, targetBookID), targetBookID));
      slot++;
    }
    addPaginatedButtons();
    super.decorate(player);
  }

  private MenuButton createBookButton(UUID uuid, ItemStack book, int bookID) {
    return new MenuButton().creator(p -> book)
      .consumer(e -> {
        final Player player = (Player) e.getWhoClicked();
        if (!ItemUtil.canReceiveItems(player, book, 1)) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_BOOK_NO_INVENTORY_SPACE.getComponent(null)));
          return;
        }
        central.getCacheManager().getCachePlayers().getMailData().removeMail(uuid, bookID);
        ItemUtil.giveItem(player, book, 1);
        clearInventory();
        clearButtons();
        decorate(player);
      });
  }

  private void addPaginatedButtons() {
    addButton(51, new MenuButton().creator(p -> MenuItem.NEXT_PAGE.createItem())
      .consumer(e -> {
        final Player player = (Player) e.getWhoClicked();
        if (!((paginatedIndex + 1) >= central.getCacheManager().getCachePlayers().getMailData().getBookAmount(player.getUniqueId()))) {
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
