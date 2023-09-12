package lee.code.central.menus.menu;

import lee.code.central.Central;
import lee.code.central.database.cache.players.data.MailData;
import lee.code.central.lang.Lang;
import lee.code.central.menus.menu.menudata.MenuItem;
import lee.code.central.menus.system.MenuButton;
import lee.code.central.menus.system.MenuPaginatedGUI;
import lee.code.central.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class MailboxMenu extends MenuPaginatedGUI {
  private final Central central;

  public MailboxMenu(Central central) {
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
    for (int i = 0; i < maxItemsPerPage; i++) {
      index = maxItemsPerPage * page + i;
      if (index >= books.size()) break;
      final int targetBookID = books.get(index);
      addButton(paginatedSlots.get(slot), createBookButton(player, mailData.getMailBook(uuid, targetBookID), targetBookID));
      slot++;
    }
    addPaginatedButtons(player);
    super.decorate(player);
  }

  private MenuButton createBookButton(Player player, ItemStack book, int bookID) {
    return new MenuButton().creator(p -> book)
      .consumer(e -> {
        if (!ItemUtil.canReceiveItems(player, book, 1)) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_BOOK_NO_INVENTORY_SPACE.getComponent(null)));
          return;
        }
        getMenuSoundManager().playRedeemBookSound(player);
        central.getCacheManager().getCachePlayers().getMailData().removeMail(player.getUniqueId(), bookID);
        ItemUtil.giveItem(player, book, 1);
        clearInventory();
        clearButtons();
        decorate(player);
      });
  }

  private void addPaginatedButtons(Player player) {
    addButton(51, new MenuButton().creator(p -> MenuItem.NEXT_PAGE.createItem())
      .consumer(e -> {
        if (!((index + 1) >= central.getCacheManager().getCachePlayers().getMailData().getBookAmount(player.getUniqueId()))) {
          page += 1;
          getMenuSoundManager().playClickSound(player);
          clearInventory();
          clearButtons();
          decorate(player);
        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NEXT_PAGE.getComponent(null)));
      }));
    addButton(47, new MenuButton().creator(p -> MenuItem.PREVIOUS_PAGE.createItem())
      .consumer(e -> {
        if (page == 0) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PREVIOUS_PAGE.getComponent(null)));
        } else {
          page -= 1;
          getMenuSoundManager().playClickSound(player);
          clearInventory();
          clearButtons();
          decorate(player);
        }
      }));
  }
}
