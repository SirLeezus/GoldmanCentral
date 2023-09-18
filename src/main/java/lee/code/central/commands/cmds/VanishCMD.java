package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.managers.VanishManager;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VanishCMD extends CustomCommand {
  private final Central central;

  public VanishCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "vanish";
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
    final VanishManager vanishManager = central.getVanishManager();
    if (vanishManager.isVanished(player)) {
      vanishManager.removeVanished(player);
      player.setGameMode(GameMode.SURVIVAL);
      player.setAllowFlight(true);
      player.setFlying(true);
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_VANISH_SUCCESSFUL.getComponent(new String[]{Lang.OFF.getString()})));
    } else {
      vanishManager.addVanished(player, true);
      player.setGameMode(GameMode.SPECTATOR);
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_VANISH_SUCCESSFUL.getComponent(new String[]{Lang.ON.getString()})));
    }
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
