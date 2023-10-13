package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class HelpCMD extends CustomCommand {
  private final Central central;

  public HelpCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "help";
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
      final String option = args[0].toLowerCase();
      switch (option) {
        case "central" -> {
          int index;
          int page = 0;
          final int maxDisplayed = 10;
          if (args.length > 1) {
            if (CoreUtil.isPositiveIntNumber(args[1])) page = Integer.parseInt(args[1]);
          }
          int position = page * maxDisplayed + 1;

          final Map<Command, String> commands = new HashMap<>();
          for (CustomCommand customCommand : central.getCommandManager().getCommands()) {
            final Command targetCommand = Bukkit.getPluginCommand(customCommand.getName());
            if (targetCommand == null) continue;
            final String perm = targetCommand.getPermission();
            if (perm == null) continue;
            if (player.hasPermission(perm)) commands.put(targetCommand, customCommand.getName());
          }
          final Map<Command, String> sortedCommands = CoreUtil.sortByValue(commands, Comparator.naturalOrder());
          final List<Command> sortedCommandList = new ArrayList<>(sortedCommands.keySet());

          final List<Component> lines = new ArrayList<>();
          lines.add(Lang.COMMAND_HELP_CENTRAL_TITLE.getComponent(null));
          lines.add(Component.text(""));

          for (int i = 0; i < maxDisplayed; i++) {
            index = maxDisplayed * page + i;
            if (index >= sortedCommandList.size()) break;
            final Command targetCommand = sortedCommandList.get(index);
            final Component helpCentral = Lang.COMMAND_HELP_CENTRAL_COMMAND.getComponent(new String[]{String.valueOf(position), targetCommand.getUsage()})
              .hoverEvent(Lang.COMMAND_HELP_CENTRAL_COMMAND_HOVER.getComponent(new String[]{targetCommand.getDescription()}))
              .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, CoreUtil.getTextBeforeCharacter(command.getUsage(), '&')));
            lines.add(helpCentral);
            position++;
          }

          if (lines.size() == 2) return;
          lines.add(Component.text(""));
          lines.add(CoreUtil.createPageSelectionComponent("/help central", page));
          for (Component line : lines) player.sendMessage(line);
          return;
        }
        case "resourceworlds" -> {
          Bukkit.getScheduler().runTask(central, () -> player.chat("/resourceworlds help"));
          return;
        }
        case "pets" -> {
          Bukkit.getScheduler().runTask(central, () -> player.chat("/pets help"));
          return;
        }
        case "trails" -> {
          Bukkit.getScheduler().runTask(central, () -> player.chat("/trails help"));
          return;
        }
        case "towns" -> {
          Bukkit.getScheduler().runTask(central, () -> player.chat("/t help"));
          return;
        }
        case "locks" -> {
          Bukkit.getScheduler().runTask(central, () -> player.chat("/lock help"));
          return;
        }
        case "vanilla" -> {
          return;
        }
      }
    }
    final List<Component> lines = new ArrayList<>();
    lines.add(Lang.COMMAND_HELP_TITLE.getComponent(null));
    lines.add(Component.text(""));
    lines.add(Lang.COMMAND_HELP_LINE_1.getComponent(null).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help central")));
    lines.add(Lang.COMMAND_HELP_LINE_2.getComponent(null).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/t help")));
    lines.add(Lang.COMMAND_HELP_LINE_3.getComponent(null).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/shop help")));
    lines.add(Lang.COMMAND_HELP_LINE_4.getComponent(null).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/lock help")));
    lines.add(Lang.COMMAND_HELP_LINE_5.getComponent(null).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/resourceworlds help")));
    lines.add(Lang.COMMAND_HELP_LINE_6.getComponent(null).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/vault help")));
    lines.add(Lang.COMMAND_HELP_LINE_7.getComponent(null).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pets help")));
    lines.add(Lang.COMMAND_HELP_LINE_8.getComponent(null).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/trails help")));
    lines.add(Lang.COMMAND_HELP_LINE_9.getComponent(null).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/help vanilla")));
    lines.add(Component.text(""));
    lines.add(Lang.COMMAND_HELP_FOOTER.getComponent(null));
    for (Component line : lines) player.sendMessage(line);
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
    return new ArrayList<>();
  }
}
