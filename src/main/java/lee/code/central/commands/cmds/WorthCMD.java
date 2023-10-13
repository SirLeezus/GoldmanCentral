package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.ItemUtil;
import lee.code.central.utils.VariableUtil;
import lee.code.shops.ShopsAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class WorthCMD extends CustomCommand {
  private final Central central;

  public WorthCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "worth";
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
    if (args.length > 0 && args[0].equalsIgnoreCase("list")) {
      final Map<ItemStack, Double> sortedItems = ShopsAPI.getAllItemSellValues();
      final ArrayList<ItemStack> items = new ArrayList<>(sortedItems.keySet());
      int index;
      int page = 0;
      final int maxDisplayed = 10;
      if (args.length > 1) {
        if (CoreUtil.isPositiveIntNumber(args[1])) page = Integer.parseInt(args[1]);
      }
      int position = page * maxDisplayed + 1;
      final ArrayList<Component> lines = new ArrayList<>();
      lines.add(Lang.COMMAND_WORTH_LIST_TITLE.getComponent(null));
      lines.add(Component.text(" "));

      for (int i = 0; i < maxDisplayed; i++) {
        index = maxDisplayed * page + i;
        if (index >= items.size()) break;
        final ItemStack targetItem = items.get(index);
        lines.add(VariableUtil.parseItemVariables(Lang.COMMAND_WORTH_LIST_LINE.getComponent(new String[]{
          String.valueOf(position),
          Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(sortedItems.get(targetItem))})
        }), targetItem));
        position++;
      }

      if (lines.size() == 2) return;
      lines.add(Component.text(" "));
      lines.add(CoreUtil.createPageSelectionComponent("/worth list", page));
      for (Component line : lines) player.sendMessage(line);
      return;
    }
    final ItemStack handItem = new ItemStack(player.getInventory().getItemInMainHand());
    final int handAmount = handItem.getAmount();
    handItem.setAmount(1);
    final Material handMaterial = handItem.getType();
    if (handMaterial.equals(Material.AIR)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_WORTH_NO_ITEM.getComponent(null)));
      return;
    }
    final double worth = ShopsAPI.getItemSellValue(handItem);
    final double handWorth = worth * handAmount;
    final double inventoryAmount = worth * ItemUtil.getItemAmount(player, handItem);

    final List<Component> lines = new ArrayList<>();
    lines.add(Lang.COMMAND_WORTH_HEADER.getComponent(null));
    lines.add(Component.text(" "));
    lines.add(VariableUtil.parseItemVariables(Lang.COMMAND_WORTH_ITEM.getComponent(null), handItem));
    lines.add(Lang.COMMAND_WORTH_WORTH.getComponent(new String[]{Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(worth)})}));
    lines.add(Lang.COMMAND_WORTH_INVENTORY.getComponent(new String[]{Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(inventoryAmount)})}));
    lines.add(Lang.COMMAND_WORTH_HAND.getComponent(new String[]{Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(handWorth)})}));
    lines.add(Component.text(" "));
    lines.add(Lang.COMMAND_WORTH_SPLITTER.getComponent(null));
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
