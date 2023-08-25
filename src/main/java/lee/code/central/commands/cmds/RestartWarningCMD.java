package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.CountdownUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RestartWarningCMD extends CustomCommand {

    private CountdownUtil countdownUtil;

    private final Central central;

    public RestartWarningCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "restartwarning";
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
        if (countdownUtil != null) {
            sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_RESTART_ACTIVE.getComponent(null)));
            return;
        }
        countdownUtil = new CountdownUtil(central,
                30,
                () -> Bukkit.getServer().sendMessage(Lang.WARNING.getComponent(null).append(Lang.COMMAND_RESTART_WARNING_START.getComponent(null))),
                () -> {
                    Bukkit.getServer().sendMessage(Lang.WARNING.getComponent(null).append(Lang.COMMAND_RESTART_WARNING_END.getComponent(null)));
                    Bukkit.getServer().savePlayers();
                    for (World world : Bukkit.getWorlds()) world.save();
                    Bukkit.getServer().shutdown();
                },
                (t) -> Bukkit.getServer().sendActionBar(Lang.COMMAND_RESTART_TIME.getComponent(new String[] { String.valueOf(t.getSecondsLeft()) })));
        countdownUtil.scheduleTimer();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
