package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.Data;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StatCMD extends CustomCommand {
  private final Central central;

  public StatCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "stat";
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
    if (args.length < 1) {
      player.sendMessage(Lang.USAGE.getComponent(new String[]{command.getUsage()}));
      return;
    }
    final Data data = central.getData();
    final String statString = args[0];
    if (!data.getStatistics().contains(statString)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_STAT_DOES_NOT_EXIST.getComponent(new String[]{statString})));
      return;
    }

    final Statistic stat = Statistic.valueOf(statString);
    switch (stat) {
      case DROP, PICKUP, MINE_BLOCK, USE_ITEM, BREAK_ITEM, CRAFT_ITEM, ENTITY_KILLED_BY, KILL_ENTITY -> {
        if (args.length < 2) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_STAT_NO_OPTION_SELECTED.getComponent(new String[]{statString})));
          return;
        }
        if (stat.equals(Statistic.ENTITY_KILLED_BY) || stat.equals(Statistic.KILL_ENTITY)) {
          final String entityTypeString = args[1];
          if (!data.getEntityTypes().contains(entityTypeString)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_ENTITY_TYPE.getComponent(new String[]{entityTypeString})));
            return;
          }
          final EntityType entityType = EntityType.valueOf(entityTypeString);
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_STAT_TARGET_SUCCESSFUL.getComponent(new String[]{CoreUtil.capitalize(statString), CoreUtil.capitalize(entityTypeString), CoreUtil.getStatFormat(stat, player.getStatistic(stat, entityType))})));
          return;
        }
        final String itemString = args[1];
        if (!data.getMaterials().contains(itemString)) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_MATERIAL.getComponent(new String[]{itemString})));
          return;
        }
        final Material item = Material.valueOf(itemString);
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_STAT_TARGET_SUCCESSFUL.getComponent(new String[]{CoreUtil.capitalize(statString), CoreUtil.capitalize(itemString), CoreUtil.getStatFormat(stat, player.getStatistic(stat, item))})));
      }
      default -> player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_STAT_SUCCESSFUL.getComponent(new String[]{CoreUtil.capitalize(statString), CoreUtil.getStatFormat(stat, player.getStatistic(stat))})));
    }
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
    switch (args.length) {
      case 1 -> {
        return StringUtil.copyPartialMatches(args[0], central.getData().getStatistics(), new ArrayList<>());
      }
      case 2 -> {
        switch (args[0]) {
          case "DROP", "PICKUP", "MINE_BLOCK", "USE_ITEM", "BREAK_ITEM", "CRAFT_ITEM" -> {
            return StringUtil.copyPartialMatches(args[1], central.getData().getMaterials(), new ArrayList<>());
          }
          case "ENTITY_KILLED_BY", "KILL_ENTITY" -> {
            return StringUtil.copyPartialMatches(args[1], central.getData().getEntityTypes(), new ArrayList<>());
          }
          default -> {
            return new ArrayList<>();
          }
        }
      }
    }
    return new ArrayList<>();
  }
}
