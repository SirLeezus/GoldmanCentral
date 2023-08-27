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

public class GlowCMD extends CustomCommand {
  private final Central central;

  public GlowCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "glow";
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
    if (player.isGlowing()) {
      player.setGlowing(false);
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GLOW_SUCCESSFUL.getComponent(new String[]{Lang.OFF.getString()})));
    } else {
      player.setGlowing(true);
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GLOW_SUCCESSFUL.getComponent(new String[]{Lang.ON.getString()})));
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
