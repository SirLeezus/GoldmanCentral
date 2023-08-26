package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.colors.ColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EnderChestCMD extends CustomCommand {

    private final Central central;

    public EnderChestCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "enderchest";
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
        if (args.length > 0) {
            final String playerString = args[0];
            if (!CoreUtil.getOnlinePlayers().contains(playerString)) {
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { playerString })));
                return;
            }
            final Player target = Bukkit.getPlayer(playerString);
            if (target == null) {
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { playerString })));
                return;
            }
            player.openInventory(target.getEnderChest());
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_ENDER_CHEST_TARGET_SUCCESSFUL.getComponent(new String[] { ColorAPI.getNameColor(target.getUniqueId(), target.getName()) })));
            return;
        }
        player.openInventory(player.getEnderChest());
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_ENDER_CHEST_SUCCESSFUL.getComponent(null)));
    }

    @Override
    public void performConsole(CommandSender console, String[] args, Command command) {
        console.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
    }

    @Override
    public void performSender(CommandSender sender, String[] args, Command command) { }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return StringUtil.copyPartialMatches(args[0], CoreUtil.getOnlinePlayers(), new ArrayList<>());
        return new ArrayList<>();
    }
}
