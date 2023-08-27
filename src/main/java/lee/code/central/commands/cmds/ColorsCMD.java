package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ColorsCMD extends CustomCommand {
  private final Central central;

  public ColorsCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "colors";
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
    final List<Component> lines = new ArrayList<>();
    lines.add(Lang.COMMAND_COLORS_TITLE.getComponent(null));
    lines.add(Component.text(""));
    lines.add(Component.text("  §0&0  §1&1  §2&2  §3&3  §4&4"));
    lines.add(Component.text("  §5&5  §6&6  §7&7  §8&8  §9&9"));
    lines.add(Component.text("  §a&a  §b&b  §c&c  §d&d  §e&e"));
    lines.add(Component.text("  §f&f  &k§kl§r  §l&l§r  §m&m§r  §n&n§r"));
    lines.add(Component.text("  §o&o§r  §r&r"));
    lines.add(Component.text(""));
    lines.add(CoreUtil.parseColorComponent("&#059CF8Hex Colors: ").append(Component.text("&#F500AE").color(TextColor.color(245, 0, 174))).hoverEvent(CoreUtil.parseColorComponent("&#FFF667Click to open website!")).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://htmlcolorcodes.com")));
    lines.add(Component.text(""));
    lines.add(Lang.COMMAND_COLORS_SPLITTER.getComponent(null));

    for (Component line : lines) sender.sendMessage(line);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    return new ArrayList<>();
  }
}
