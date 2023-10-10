package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.managers.DelayManager;
import lee.code.central.utils.CoreUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RandomTeleportCMD extends CustomCommand {
  private final Central central;

  public RandomTeleportCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "randomteleport";
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
    final DelayManager delayManager = central.getDelayManager();
    final UUID uuid = player.getUniqueId();
    if (delayManager.isOnDelay(uuid, "rtp")) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_ON_COMMAND_DELAY.getComponent(new String[]{delayManager.getRemainingTime(uuid, "rtp")})));
      return;
    }
    final World world = player.getWorld();
    if (!world.getName().equalsIgnoreCase("world")) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_RANDOM_TELEPORT_WORLD.getComponent(null)));
      return;
    }
    final int worldBorderSize = (int) world.getWorldBorder().getSize() / 2;
    for (int i = 0; i < 30; i++) {
      final int x = (int) (Math.random() * (worldBorderSize * 2)) - worldBorderSize;
      final int z = (int) (Math.random() * (worldBorderSize * 2)) - worldBorderSize;
      world.getChunkAtAsync(new Location(world, x, 0, z), true).join();
      final int y = world.getHighestBlockYAt(x, z);
      final Location randomLocation = new Location(world, x, y, z);
      final Material material = randomLocation.getBlock().getType();
      if (!material.equals(Material.WATER) && !material.equals(Material.LAVA)) {
        final Vector box = randomLocation.getBlock().getBoundingBox().getCenter();
        final Location location = new Location(randomLocation.getWorld(), box.getX(), box.getY() + 0.5, box.getZ());
        if (!location.getWorld().getWorldBorder().isInside(location)) continue;
        delayManager.setOnDelay(uuid, "rtp", 15000);
        player.teleportAsync(location).thenAccept(result -> player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANDOM_TELEPORT_SUCCESSFUL.getComponent(null))));
        return;
      }
    }
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_RANDOM_TELEPORT_FAILED.getComponent(null)));
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
