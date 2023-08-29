package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.menus.menu.HomeMenu;
import lee.code.central.menus.system.MenuPlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomesCMD extends CustomCommand {
  private final Central central;

  public HomesCMD(Central central) {
    this.central = central;
  }

  @Override
  public String getName() {
    return "homes";
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
    if (!central.getCacheManager().getCachePlayers().getHomeData().hasHome(player.getUniqueId())) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_HOME_NO_HOMES.getComponent(null)));
      return;
    }
    final MenuPlayerData menuPlayerData = central.getMenuManager().getMenuPlayerData(player.getUniqueId());
    central.getMenuManager().openMenu(new HomeMenu(menuPlayerData, central), player);
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
