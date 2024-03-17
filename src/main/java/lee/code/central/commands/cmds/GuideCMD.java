package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.managers.DelayManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuideCMD extends CustomCommand {
  private final Central central;

  public GuideCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "guide";
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
    final DelayManager delayManager = central.getDelayManager();
    final UUID uuid = player.getUniqueId();
    if (delayManager.isOnDelay(uuid, "guide")) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_ON_COMMAND_DELAY.getComponent(new String[]{delayManager.getRemainingTime(uuid, "guide")})));
      return;
    }
    central.getStarterLootManager().giveBook(player);
    if (!player.isOp()) delayManager.setOnDelay(uuid, "guide", 3600000);
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GUIDE_SUCCESSFUL.getComponent(null)));
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
