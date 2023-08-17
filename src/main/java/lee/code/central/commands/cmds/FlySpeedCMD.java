package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FlySpeedCMD extends CustomCommand {

    private final Central central;

    public FlySpeedCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "flyspeed";
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
        final String speedString = args[0];
        if (!CoreUtil.isPositiveIntNumber(speedString)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_VALUE_INVALID.getComponent(new String[] { speedString })));
            return;
        }
        final int speed = Integer.parseInt(speedString);
        if (speed > 10) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_FLYSPEED_LIMIT.getComponent(null)));
            return;
        }
        float speedFloat = (float) 0;
        switch (speed) {
            case 1 -> speedFloat = (float) 0.1;
            case 2 -> speedFloat = (float) 0.2;
            case 3 -> speedFloat = (float) 0.3;
            case 4 -> speedFloat = (float) 0.4;
            case 5 -> speedFloat = (float) 0.5;
            case 6 -> speedFloat = (float) 0.6;
            case 7 -> speedFloat = (float) 0.7;
            case 8 -> speedFloat = (float) 0.8;
            case 9 -> speedFloat = (float) 0.9;
            case 10 -> speedFloat = (float) 1;
        }
        player.setFlySpeed(speedFloat);
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_FLYSPEED_SUCCESSFUL.getComponent(new String[]{String.valueOf(speed)})));
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
