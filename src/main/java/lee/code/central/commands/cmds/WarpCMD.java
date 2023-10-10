package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.database.cache.server.data.WarpData;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WarpCMD extends CustomCommand {
  private final Central central;

  public WarpCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "warp";
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
    final WarpData warpData = central.getCacheManager().getCacheServer().getWarpData();
    final String warpName = CoreUtil.buildStringFromArgs(args, 0);
    if (!warpData.isWarp(warpName)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_WARP_DOES_NOT_EXIST.getComponent(new String[]{warpName})));
      return;
    }
    player.teleportAsync(warpData.getWarpLocation(warpName)).thenAccept(result -> {
      if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_WARP_SUCCESSFUL.getComponent(new String[]{warpName})));
      else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_WARP_FAILED.getComponent(new String[]{warpName})));
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
    if (args.length == 1) return StringUtil.copyPartialMatches(args[0], central.getCacheManager().getCacheServer().getWarpData().getAllWarps(), new ArrayList<>());
    return new ArrayList<>();
  }
}
