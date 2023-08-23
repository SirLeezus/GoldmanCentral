package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WorldCMD extends CustomCommand {

    private final Central central;

    public WorldCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "world";
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
        if (args.length < 1) {
            player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
            return;
        }
        final String worldString = args[0];
        if (!central.getData().getWorlds().contains(worldString)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_WORLD_DOES_NOT_EXIST.getComponent(new String[] { worldString })));
            return;
        }
        final World world = Bukkit.getWorld(worldString);
        final Location location = new Location(world, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
        player.teleportAsync(location).thenAccept(result -> {
            if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_WORLD_SUCCESSFUL.getComponent(new String[] { worldString })));
            else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_WORLD_FAILED.getComponent(null)));
        });
    }

    @Override
    public void performConsole(CommandSender console, String[] args, Command command) {
        console.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
    }

    @Override
    public void performSender(CommandSender sender, String[] args, Command command) { }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return StringUtil.copyPartialMatches(args[0], central.getData().getWorlds(), new ArrayList<>());
        else return new ArrayList<>();
    }
}
