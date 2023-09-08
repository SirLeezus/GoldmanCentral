package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.ItemUtil;
import lee.code.colors.ColorAPI;
import lee.code.playerdata.PlayerDataAPI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GiveCMD extends CustomCommand {
  private final Central central;

  public GiveCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "give";
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
    performSender(player, args, command);
  }

  @Override
  public void performConsole(CommandSender console, String[] args, Command command) {
    performSender(console, args, command);
  }

  @Override
  public void performSender(CommandSender sender, String[] args, Command command) {
    if (args.length < 3) {
      sender.sendMessage(Lang.USAGE.getComponent(new String[]{command.getUsage()}));
      return;
    }
    final String targetString = args[0];
    final Player target = PlayerDataAPI.getOnlinePlayer(targetString);
    if (target == null) {
      sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[]{targetString})));
      return;
    }
    final String materialString = args[1].toUpperCase();
    if (!central.getData().getMaterials().contains(materialString)) {
      sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_MATERIAL.getComponent(new String[]{materialString})));
      return;
    }
    final Material material = Material.valueOf(materialString);
    final String amountString = args[2];
    if (!CoreUtil.isPositiveIntNumber(amountString)) {
      sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_VALUE_INVALID.getComponent(new String[]{amountString})));
      return;
    }
    int amount = Integer.parseInt(amountString);
    if (amount > 1000) amount = 1000;
    final ItemStack item = new ItemStack(material);
    if (!ItemUtil.canReceiveItems(target, item, amount)) {
      sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NO_INVENTORY_SPACE.getComponent(new String[]{targetString})));
      return;
    }
    ItemUtil.giveItem(target, new ItemStack(material), amount);
    target.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GIVE_TARGET_SUCCESSFUL.getComponent(new String[]{CoreUtil.parseValue(amount), CoreUtil.capitalize(materialString)})));
    sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GIVE_SUCCESSFUL.getComponent(new String[]{ColorAPI.getNameColor(target.getUniqueId(), targetString), CoreUtil.parseValue(amount), CoreUtil.capitalize(materialString)})));
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    if (args.length == 1) return StringUtil.copyPartialMatches(args[0], CoreUtil.getOnlinePlayers(), new ArrayList<>());
    else if (args.length == 2) return StringUtil.copyPartialMatches(args[1], central.getData().getMaterials(), new ArrayList<>());
    else return new ArrayList<>();
  }
}
