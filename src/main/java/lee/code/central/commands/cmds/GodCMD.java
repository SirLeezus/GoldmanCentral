package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.database.cache.players.CachePlayers;
import lee.code.central.lang.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GodCMD extends CustomCommand {
  private final Central central;

  public GodCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "god";
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
    final UUID uuid = player.getUniqueId();
    final CachePlayers cachePlayers = central.getCacheManager().getCachePlayers();
    if (cachePlayers.isGod(uuid)) {
      cachePlayers.setGod(uuid, false);
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GOD_SUCCESSFUL.getComponent(new String[]{Lang.OFF.getString()})));
    } else {
      cachePlayers.setGod(uuid, true);
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GOD_SUCCESSFUL.getComponent(new String[]{Lang.ON.getString()})));
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
