package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameModeCMD extends CustomCommand {

    private final Central central;

    public GameModeCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "gamemode";
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
        final String gameMode = args[0].toLowerCase();
        switch (gameMode) {
            case "survival", "0" -> {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GAMEMODE_SUCCESSFUL.getComponent(new String[]{"Survival"})));
            }
            case "creative", "1" -> {
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GAMEMODE_SUCCESSFUL.getComponent(new String[]{"Creative"})));
                player.setGameMode(GameMode.CREATIVE);
            }
            case "adventure", "2" -> {
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GAMEMODE_SUCCESSFUL.getComponent(new String[]{"Adventure"})));
                player.setGameMode(GameMode.ADVENTURE);
            }
            case "spectator", "3" -> {
                player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GAMEMODE_SUCCESSFUL.getComponent(new String[]{"Spectator"})));
                player.setGameMode(GameMode.SPECTATOR);
            }
            default -> player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        }
    }

    @Override
    public void performConsole(CommandSender console, String[] args, Command command) {
        console.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
    }

    @Override
    public void performSender(CommandSender sender, String[] args, Command command) { }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return StringUtil.copyPartialMatches(args[0], Arrays.asList("survival", "creative", "adventure", "spectator"), new ArrayList<>());
        return new ArrayList<>();
    }
}
