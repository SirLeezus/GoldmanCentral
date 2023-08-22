package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpawnCMD extends CustomCommand {

    private final Central central;

    public SpawnCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "spawn";
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
        final Location location = central.getCacheManager().getCacheServer().getSpawn();
        if (location == null) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SPAWN_NOT_SET.getComponent(null)));
            return;
        }
        player.teleportAsync(location).thenAccept(result -> {
            if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SPAWN_SUCCESSFUL.getComponent(null)));
            else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SPAWN_FAILED.getComponent(null)));
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
        return new ArrayList<>();
    }
}
