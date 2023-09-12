package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.ItemUtil;
import lee.code.central.utils.VariableUtil;
import lee.code.economy.EcoAPI;
import lee.code.shops.ShopsAPI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SellCMD extends CustomCommand {
  private final Central central;

  public SellCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "sell";
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
    final ItemStack handItem = new ItemStack(player.getInventory().getItemInMainHand());
    final int amount = handItem.getAmount();
    handItem.setAmount(1);
    if (handItem.getType().equals(Material.AIR)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SELL_NO_ITEM.getComponent(null)));
      return;
    }
    final double worth = ShopsAPI.getItemSellValue(handItem);
    final double finalWorth = worth * amount;
    if (finalWorth <= 0) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SELL_NO_VALUE.getComponent(null)));
      return;
    }
    ItemUtil.removePlayerItems(player, handItem, amount, true);
    EcoAPI.addBalance(player.getUniqueId(), finalWorth);
    player.sendMessage(VariableUtil.parseItemVariables(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SELL_SUCCESSFUL.getComponent(new String[]{
      CoreUtil.parseValue(amount),
      Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(finalWorth)})
    })), handItem));
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
