package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BackCMD extends CustomCommand {
  private final Central central;

  public BackCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "back";
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
    if (!central.getBackManager().hasLastBackLocation(player.getUniqueId())) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_BACK_NO_LOCATION.getComponent(null)));
      return;
    }
    player.teleportAsync(central.getBackManager().getLastBackLocation(player.getUniqueId())).thenAccept(result-> {
      if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_BACK_SUCCESSFUL.getComponent(null)));
      else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_BACK_FAILED.getComponent(null)));
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
    return new ArrayList<>();
  }
}
