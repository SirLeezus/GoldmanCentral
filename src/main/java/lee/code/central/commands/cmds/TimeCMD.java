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

public class TimeCMD extends CustomCommand {

    private final Central central;

    public TimeCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "time";
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
        final String option = args[0].toLowerCase();
        switch (option) {
            case "day" -> player.getWorld().setTime(1000);
            case "noon" -> player.getWorld().setTime(6000);
            case "midnight" -> player.getWorld().setTime(18000);
            case "night" -> player.getWorld().setTime(13000);
            default ->  {
                player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
                return;
            }
        }
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TIME_SUCCESSFUL.getComponent(new String[] { option })));
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
