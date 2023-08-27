package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.enums.ItemValue;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.ItemUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
    final ItemStack handItem = player.getInventory().getItemInMainHand();
    final Material handMaterial = handItem.getType();
    if (handMaterial.equals(Material.AIR)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_WORTH_NO_ITEM.getComponent(null)));
      return;
    }
    if (!central.getData().getSellableMaterials().contains(handMaterial.name())) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NO_VALUE.getComponent(new String[]{CoreUtil.capitalize(handMaterial.name())})));
      return;
    }
    final double worth = ItemValue.valueOf(handMaterial.name()).getValue();
    final double handWorth = worth * handItem.getAmount();
    final double inventoryAmount = worth * ItemUtil.getItemAmount(player, handItem);

    final List<Component> lines = new ArrayList<>();
    lines.add(Lang.COMMAND_WORTH_HEADER.getComponent(null));
    lines.add(Component.text(" "));
    lines.add(Lang.COMMAND_WORTH_ITEM.getComponent(new String[]{CoreUtil.capitalize(handMaterial.name())}));
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
