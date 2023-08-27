package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SummonCMD extends CustomCommand {
  private final Central central;

  public SummonCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "summon";
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
    if (args.length < 1) {
      player.sendMessage(Lang.USAGE.getComponent(new String[]{command.getUsage()}));
      return;
    }
    final String entityTypeString = args[0].toUpperCase();
    if (!central.getData().getEntityTypes().contains(entityTypeString)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_ENTITY_TYPE.getComponent(new String[]{entityTypeString})));
      return;
    }
    final EntityType entityType = EntityType.valueOf(entityTypeString);
    int amount = 1;
    if (args.length > 1) {
      final String amountString = args[1];
      if (!CoreUtil.isPositiveIntNumber(amountString)) {
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_VALUE_INVALID.getComponent(new String[]{amountString})));
        return;
      }
      amount = Integer.parseInt(amountString);
    }
    if (amount > 100) amount = 100;
    final Block block = player.getTargetBlockExact(100);
    if (block == null) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SUMMON_NO_BLOCK.getComponent(null)));
      return;
    }
    final Location targetLocation = new Location(block.getLocation().getWorld(), block.getLocation().getX(), block.getLocation().getY() + 0.5, block.getLocation().getZ());
    for (int i = 0; i < amount; i++) targetLocation.getWorld().spawnEntity(targetLocation, entityType);
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SUMMON_SUCCESSFUL.getComponent(new String[]{String.valueOf(amount), CoreUtil.capitalize(entityTypeString)})));
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
      return StringUtil.copyPartialMatches(args[0], central.getData().getEntityTypes(), new ArrayList<>());
    else return new ArrayList<>();
  }
}
