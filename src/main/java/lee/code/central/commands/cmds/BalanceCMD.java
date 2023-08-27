package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.colors.ColorAPI;
import lee.code.economy.EcoAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BalanceCMD extends CustomCommand {
  private final Central central;

  public BalanceCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "balance";
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
      final OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(targetString);
      if (target == null) {
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[]{targetString})));
        return;
      }
      if (!EcoAPI.hasPlayerData(target.getUniqueId())) {
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NO_PLAYER_DATA.getComponent(new String[]{targetString})));
        return;
      }
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_BALANCE_TARGET_SUCCESSFUL.getComponent(new String[]{
        ColorAPI.getNameColor(target.getUniqueId(), target.getName()),
        Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(EcoAPI.getBalance(target.getUniqueId()))})
      })));
      return;
    }
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_BALANCE_SUCCESSFUL.getComponent(new String[]{
      Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(EcoAPI.getBalance(player.getUniqueId()))})
    })));
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
