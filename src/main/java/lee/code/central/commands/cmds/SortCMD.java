package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.ContainerSortBuilder;
import lee.code.central.utils.CoreUtil;
import lee.code.towns.TownsAPI;
import org.bukkit.block.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SortCMD extends CustomCommand {
  private final Central central;

  public SortCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "sort";
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
    final Block block = player.getTargetBlockExact(5);
    if (block == null) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SORT_COULD_NOT_FIND_BLOCK.getComponent(null)));
      return;
    }
    if (!isSupportedContainer(block.getState()) || !(block.getState() instanceof Container container)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SORT_NOT_SUPPORTED_BLOCK.getComponent(new String[]{CoreUtil.capitalize(block.getType().name())})));
      return;
    }
    if (!TownsAPI.canInteract(player.getUniqueId(), block.getChunk())) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SORT_INTERACTION_FAILED.getComponent(null)));
      return;
    }
    final ContainerSortBuilder sortBuilder = new ContainerSortBuilder();
    for (ItemStack item : container.getInventory().getContents()) {
      if (item != null && !item.getType().isAir()) sortBuilder.addItem(item);
    }
    container.getInventory().clear();
    final List<ItemStack> sortedList = sortBuilder.sort();
    container.getInventory().addItem(sortedList.toArray(new ItemStack[0]));
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SORT_SUCCESSFUL.getComponent(null)));
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

  private boolean isSupportedContainer(BlockState state) {
    return state instanceof Chest || state instanceof DoubleChest || state instanceof ShulkerBox || state instanceof Barrel;
  }
}
