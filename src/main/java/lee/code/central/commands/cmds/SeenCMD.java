package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SeenCMD extends CustomCommand {

    private final Central central;

    public SeenCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "seen";
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
        performSender(player, args, command);
    }

    @Override
    public void performConsole(CommandSender console, String[] args, Command command) {
        performSender(console, args, command);
    }

    @Override
    public void performSender(CommandSender sender, String[] args, Command command) {
        if (args.length < 1) {
            sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
            return;
        }
        final String playerString = args[0];
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(playerString);
        if (offlinePlayer == null) {
            sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { playerString })));
            return;
        }
        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SEEN_SUCCESSFUL.getComponent(new String[] { playerString, CoreUtil.getDate(offlinePlayer.getLastSeen()) })));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return StringUtil.copyPartialMatches(args[0], CoreUtil.getOnlinePlayers(), new ArrayList<>());
        else return new ArrayList<>();
    }
}
