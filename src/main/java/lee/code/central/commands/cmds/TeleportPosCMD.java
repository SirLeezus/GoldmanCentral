package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TeleportPosCMD extends CustomCommand {
  private final Central central;

  public TeleportPosCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "teleportpos";
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
    if (args.length < 3) {
      player.sendMessage(Lang.USAGE.getComponent(new String[]{command.getUsage()}));
      return;
    }
    final String x = args[0];
    final String y = args[1];
    final String z = args[2];
    if (!CoreUtil.isDoubleNumber(x)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_VALUE_INVALID.getComponent(new String[]{x})));
      return;
    }
    if (!CoreUtil.isDoubleNumber(y)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_VALUE_INVALID.getComponent(new String[]{y})));
      return;
    }
    if (!CoreUtil.isDoubleNumber(z)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_VALUE_INVALID.getComponent(new String[]{z})));
      return;
    }
    final Location location = new Location(player.getWorld(), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));
    player.teleportAsync(location).thenAccept(result -> {
      if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TELEPORT_POS_SUCCESSFUL.getComponent(new String[]{x, y, z})));
      else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TELEPORT_POS_FAILED.getComponent(new String[]{x, y, z})));
    });
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
