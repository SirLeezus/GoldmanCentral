package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.colors.ColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PatrolCMD extends CustomCommand {
  private final Central central;

  public PatrolCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "patrol";
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
    if (Bukkit.getOnlinePlayers().size() == 1) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PATROL_ONE_PLAYER.getComponent(null)));
      return;
    }
    final Player target = central.getPatrolManager().getNextPlayer(player);
    player.teleportAsync(target.getLocation()).thenAccept(result -> {
      if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_PATROL_SUCCESSFUL.getComponent(new String[]{ColorAPI.getNameColor(target.getUniqueId(), target.getName())})));
      else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_PATROL_FAILED.getComponent(new String[]{ColorAPI.getNameColor(target.getUniqueId(), target.getName())})));
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
