package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HatCMD extends CustomCommand {
  private final Central central;

  public HatCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "hat";
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
    final ItemStack headItem = player.getInventory().getHelmet();
    final ItemStack handItem = player.getInventory().getItemInMainHand();
    if (handItem.getType().isAir()) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HAT_AIR.getComponent(null)));
      return;
    }
    if (headItem != null && !headItem.getType().isAir()) player.getInventory().setItemInMainHand(headItem);
    else player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
    player.getInventory().setHelmet(handItem);
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
