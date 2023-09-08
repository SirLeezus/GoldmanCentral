package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.playerdata.PlayerDataAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FeedCMD extends CustomCommand {
  private final Central central;

  public FeedCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "feed";
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
    if (args.length > 0) {
      final String targetString = args[0];
      final Player target = PlayerDataAPI.getOnlinePlayer(targetString);
      if (target == null) {
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[]{targetString})));
        return;
      }
      target.setFoodLevel(20);
      target.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_FEED_TARGET_SUCCESSFUL.getComponent(new String[]{player.getName()})));
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_FEED_PLAYER_TARGET_SUCCESSFUL.getComponent(new String[]{targetString})));
      return;
    }
    player.setFoodLevel(20);
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_FEED_SUCCESSFUL.getComponent(null)));
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
