package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ClearCMD extends CustomCommand {
  private final Central central;

  public ClearCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "clear";
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
    final PlayerInventory inventory = player.getInventory();
    for (int i = 0; i < inventory.getSize(); i++) {
      if (i != 36 && i != 37 && i != 38 && i != 39) {
        inventory.setItem(i, new ItemStack(Material.AIR));
      }
    }
    inventory.setItem(40, new ItemStack(Material.AIR));
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_CLEAR_SUCCESSFUL.getComponent(null)));
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
