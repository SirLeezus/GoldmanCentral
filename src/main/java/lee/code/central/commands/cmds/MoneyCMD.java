package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.colors.ColorAPI;
import lee.code.economy.EcoAPI;
import lee.code.playerdata.PlayerDataAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MoneyCMD extends CustomCommand {
  private final Central central;

  public MoneyCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "money";
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
    performSender(player, args, command);
  }

  @Override
  public void performConsole(CommandSender console, String[] args, Command command) {
    performSender(console, args, command);
  }

  @Override
  public void performSender(CommandSender sender, String[] args, Command command) {
    if (args.length < 3) {
      sender.sendMessage(Lang.USAGE.getComponent(new String[]{command.getUsage()}));
      return;
    }
    final String targetString = args[0];
    final UUID targetID = PlayerDataAPI.getUniqueId(targetString);
    if (targetID == null) {
      sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NO_PLAYER_DATA.getComponent(new String[]{targetString})));
      return;
    }
    final String option = args[1].toLowerCase();
    final String amountString = args[2];
    if (!CoreUtil.isPositiveDoubleNumber(amountString)) {
      sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_VALUE_INVALID.getComponent(new String[]{amountString})));
      return;
    }
    final double amount = Double.parseDouble(amountString);
    switch (option) {
      case "set" -> {
        EcoAPI.setBalance(targetID, amount);
        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_MONEY_SET_SUCCESSFUL.getComponent(new String[]{
          ColorAPI.getNameColor(targetID, PlayerDataAPI.getName(targetID)),
          Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(amount)})
        })));
      }
      case "add" -> {
        EcoAPI.addBalance(targetID, amount);
        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_MONEY_ADDED_SUCCESSFUL.getComponent(new String[]{
          Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(amount)}),
          ColorAPI.getNameColor(targetID, PlayerDataAPI.getName(targetID))
        })));
      }
      case "remove" -> {
        EcoAPI.removeBalance(targetID, amount);
        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_MONEY_REMOVED_SUCCESSFUL.getComponent(new String[]{
          Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(amount)}),
          ColorAPI.getNameColor(targetID, PlayerDataAPI.getName(targetID))
        })));
      }
      default -> sender.sendMessage(Lang.USAGE.getComponent(new String[]{command.getUsage()}));
    }
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    if (args.length == 1) return StringUtil.copyPartialMatches(args[0], CoreUtil.getOnlinePlayers(), new ArrayList<>());
    else return new ArrayList<>();
  }
}
