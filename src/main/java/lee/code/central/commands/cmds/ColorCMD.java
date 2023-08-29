package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.colors.ColorAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class ColorCMD extends CustomCommand {
  private final Central central;

  public ColorCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "color";
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
    final String colorString = args[0];
    if (!central.getData().getPlayerColors().contains(colorString)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COLOR_INVALID.getComponent(new String[]{colorString})));
      return;
    }
    final ChatColor color = ChatColor.valueOf(colorString);
    ColorAPI.setColor(player, color);
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_COLOR_SUCCESSFUL.getComponent(new String[]{"&" + color.getChar() + CoreUtil.capitalize(colorString)})));
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
    if (args.length == 1)
      return StringUtil.copyPartialMatches(args[0], central.getData().getPlayerColors(), new ArrayList<>());
    else return new ArrayList<>();
  }
}
