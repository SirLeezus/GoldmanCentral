package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RulesCMD extends CustomCommand {
  private final Central central;

  public RulesCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "rules";
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
    final List<Component> lines = new ArrayList<>();
    lines.add(Lang.COMMAND_RULES_TITLE.getComponent(null));
    lines.add(Component.text(""));
    lines.add(Lang.COMMAND_RULES_LINE_1.getComponent(null));
    lines.add(Lang.COMMAND_RULES_LINE_2.getComponent(null));
    lines.add(Lang.COMMAND_RULES_LINE_3.getComponent(null));
    lines.add(Lang.COMMAND_RULES_LINE_4.getComponent(null));
    lines.add(Lang.COMMAND_RULES_LINE_5.getComponent(null));
    lines.add(Lang.COMMAND_RULES_LINE_6.getComponent(null));
    lines.add(Component.text(""));
    lines.add(Lang.COMMAND_RULES_WARNING.getComponent(null));
    lines.add(Component.text(""));
    lines.add(Lang.COMMAND_RULES_FOOTER.getComponent(null));
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
