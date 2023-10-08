package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.colors.ColorAPI;
import lee.code.playerdata.PlayerDataAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayTimeCMD extends CustomCommand {
  private final Central central;

  public PlayTimeCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "playtime";
  }

  @Override
  public boolean performAsync() {
    return true;
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
      final UUID targetID = PlayerDataAPI.getUniqueId(targetString);
      if (targetID == null) {
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NO_PLAYER_DATA.getComponent(new String[]{targetString})));
        return;
      }
      final OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(targetID);
      final long secondsPlayed = targetOffline.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
      final long millisecondsPlayed = secondsPlayed * 1000;
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_PLAY_TIME_TARGET_SUCCESSFUL.getComponent(new String[]{ColorAPI.getNameColor(targetID, targetString), CoreUtil.parseTime(millisecondsPlayed)})));
      return;
    }
    final long secondsPlayed = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
    final long millisecondsPlayed = secondsPlayed * 1000;
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_PLAY_TIME_SUCCESSFUL.getComponent(new String[]{CoreUtil.parseTime(millisecondsPlayed)})));
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
