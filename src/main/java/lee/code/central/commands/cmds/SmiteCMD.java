package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.colors.ColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SmiteCMD extends CustomCommand {
  private final Central central;

  public SmiteCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "smite";
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
    final Location location;
    if (args.length > 0) {
      final String targetName = args[0];
      if (!CoreUtil.getOnlinePlayers().contains(targetName)) {
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[]{targetName})));
        return;
      }
      final Player targetPlayer = Bukkit.getPlayer(targetName);
      if (targetPlayer == null) {
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[]{targetName})));
        return;
      }
      location = targetPlayer.getLocation();
      location.getWorld().strikeLightning(location);
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SMITE_TARGET_SUCCESSFUL.getComponent(new String[]{ColorAPI.getNameColor(targetPlayer.getUniqueId(), targetName)})));
    } else {
      final Block block = player.getTargetBlockExact(100);
      if (block == null) {
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SMITE_NO_BLOCK.getComponent(null)));
        return;
      }
      location = block.getLocation();
      location.getWorld().strikeLightning(location);
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SMITE_BLOCK_SUCCESSFUL.getComponent(null)));
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
    if (args.length == 1) return StringUtil.copyPartialMatches(args[0], CoreUtil.getOnlinePlayers(), new ArrayList<>());
    else return new ArrayList<>();
  }
}
