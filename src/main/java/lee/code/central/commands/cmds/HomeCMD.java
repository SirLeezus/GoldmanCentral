package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.database.cache.players.data.HomeData;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeCMD extends CustomCommand {
  private final Central central;

  public HomeCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "home";
  }

  @Override
  public boolean performAsync() {
    return true;
  }

  @Override
  public boolean performAsyncSynchronized() {
    return true;
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    central.getCommandManager().perform(sender, args, this, command);
    return true;
  }

  @Override
  public void perform(Player player, String[] args, Command command) {
    //home <tp/remove/add/max> <home>
    if (args.length < 2) {
      player.sendMessage(Lang.USAGE.getComponent(new String[]{command.getUsage()}));
      return;
    }
    final UUID uuid = player.getUniqueId();
    final String option = args[0].toLowerCase();
    final String name = args[1];
    final HomeData homeData = central.getCacheManager().getCachePlayers().getHomeData();
    switch (option) {
      case "tp" -> {
        if (!homeData.hasHome(uuid)) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_NO_HOMES.getComponent(null)));
          return;
        }
        if (!homeData.isHome(uuid, name)) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_NOT_HOME.getComponent(new String[]{name})));
          return;
        }
        final Location location = homeData.getHomeLocation(uuid, name);
        player.teleportAsync(location).thenAccept(result -> {
          if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_HOME_TP_SUCCESSFUL.getComponent(new String[]{name})));
          else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_HOME_TP_FAILED.getComponent(new String[]{name})));
        });
      }
      case "remove" -> {
        if (!homeData.hasHome(uuid)) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_NO_HOMES.getComponent(null)));
          return;
        }
        if (!homeData.isHome(uuid, name)) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_NOT_HOME.getComponent(new String[]{name})));
          return;
        }
        homeData.removeHome(uuid, name);
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_HOME_REMOVE_SUCCESSFUL.getComponent(new String[]{name})));
      }
      case "add" -> {

      }
      case "max" -> {

      }
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
    if (args.length == 1) return StringUtil.copyPartialMatches(args[0], CoreUtil.getOnlinePlayers(), new ArrayList<>());
    else return new ArrayList<>();
  }
}
