package lee.code.central.commands;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import lee.code.central.Central;
import lee.code.central.commands.cmds.FlyCMD;
import lee.code.central.commands.cmds.GameModeCMD;
import lee.code.central.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

    private final Central central;
    private final ConcurrentHashMap<String, CustomCommand> commands = new ConcurrentHashMap<>();

    public CommandManager(Central central) {
        this.central = central;
        storeCommands();
    }

    public CustomCommand getCommand(String cmd) {
        return commands.get(cmd);
    }

    public Set<CustomCommand> getCommands() {
        final Set<CustomCommand> commandList = ConcurrentHashMap.newKeySet();
        commandList.addAll(commands.values());
        return commandList;
    }

    private void storeCommands() {
        final CustomCommand gameMode = new GameModeCMD(central);
        commands.put(gameMode.getName(), gameMode);
        final CustomCommand fly = new FlyCMD(central);
        commands.put(fly.getName(), fly);
    }

    private final ConcurrentHashMap<UUID, ScheduledTask> asyncTasks = new ConcurrentHashMap<>();
    private final Object synchronizedThreadLock = new Object();

    public void perform(CommandSender sender, String[] args, CustomCommand customCommand, Command command) {
        if (sender instanceof Player player) {
            final UUID uuid = player.getUniqueId();
            if (asyncTasks.containsKey(uuid)) {
                player.sendMessage(Lang.ERROR_ONE_COMMAND_AT_A_TIME.getComponent(null));
                return;
            }
            if (customCommand.performAsync()) {
                if (customCommand.performAsyncSynchronized()) {
                    synchronized (synchronizedThreadLock) {
                        performCommandAsync(player, uuid, args, customCommand, command);
                    }
                } else {
                    performCommandAsync(player, uuid, args, customCommand, command);
                }
            } else {
                customCommand.perform(player, args, command);
            }
        } else if (sender instanceof ConsoleCommandSender console) {
            customCommand.performConsole(console, args, command);
        }
    }

    private void performCommandAsync(Player player, UUID uuid, String[] args, CustomCommand customCommand, Command command) {
        asyncTasks.put(uuid, Bukkit.getAsyncScheduler().runNow(central, scheduledTask -> {
            try {
                customCommand.perform(player, args, command);
            } finally {
                asyncTasks.remove(uuid);
            }
        }));
    }
}