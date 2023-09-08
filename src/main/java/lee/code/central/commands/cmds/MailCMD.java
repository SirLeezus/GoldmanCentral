package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.database.cache.players.CachePlayers;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.colors.ColorAPI;
import lee.code.playerdata.PlayerDataAPI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MailCMD extends CustomCommand {
  private final Central central;

  public MailCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "mail";
  }

  @Override
  public boolean performAsync() {
    return false;
  }

  @Override
  public boolean performAsyncSynchronized() {
    return false;
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    central.getCommandManager().perform(sender, args, this, command);
    return true;
  }

  @Override
  public void perform(Player player, String[] args, Command command) {
    if (args.length < 1) {
      player.sendMessage(Lang.USAGE.getComponent(new String[]{command.getUsage()}));
      return;
    }
    final CachePlayers cachePlayers = central.getCacheManager().getCachePlayers();
    final String targetString = args[0];
    final UUID targetID = PlayerDataAPI.getUniqueId(targetString);
    if (targetID == null) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NO_PLAYER_DATA.getComponent(new String[]{targetString})));
      return;
    }
    final ItemStack handItem = player.getInventory().getItemInMainHand();
    if (!handItem.getType().equals(Material.WRITTEN_BOOK)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_MAIL_NO_BOOK.getComponent(null)));
      return;
    }
    final BookMeta bookMeta = (BookMeta) handItem.getItemMeta();
    if (bookMeta == null) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_MAIL_BOOK_META_INVALID.getComponent(null)));
      return;
    }
    if (!bookMeta.hasAuthor()) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_MAIL_BOOK_NOT_SIGNED.getComponent(null)));
      return;
    }
    final String authorName = bookMeta.getAuthor();
    if (authorName == null) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_MAIL_BOOK_NOT_SIGNED.getComponent(null)));
      return;
    }
    if (!authorName.equals(player.getName())) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_MAIL_BOOK_NOT_SIGNED.getComponent(null)));
      return;
    }
    if (player.getUniqueId().equals(targetID)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_MAIL_SELF.getComponent(null)));
      return;
    }
    if (cachePlayers.getMailData().getBookAmount(targetID) + 1 > 100) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_MAIL_BOOK_MAX.getComponent(new String[]{ColorAPI.getNameColor(targetID, targetString)})));
      return;
    }
    handItem.setAmount(1);
    cachePlayers.getMailData().addMail(targetID, handItem);
    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_MAIL_SUCCESSFUL.getComponent(new String[] {ColorAPI.getNameColor(targetID, targetString)})));
    PlayerDataAPI.sendPlayerMessageIfOnline(targetID, Lang.PREFIX.getComponent(null).append(Lang.COMMAND_MAIL_TARGET_SUCCESSFUL.getComponent(new String[]{ColorAPI.getNameColor(player.getUniqueId(), player.getName())})));
  }

  @Override
  public void performConsole(CommandSender console, String[] args, Command command) {
    console.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
  }

  @Override
  public void performSender(CommandSender sender, String[] args, Command command) {
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    if (args.length == 1) return StringUtil.copyPartialMatches(args[0], CoreUtil.getOnlinePlayers(), new ArrayList<>());
    else return new ArrayList<>();
  }
}
