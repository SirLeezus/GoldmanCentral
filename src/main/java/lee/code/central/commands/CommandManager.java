package lee.code.central.commands;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import lee.code.central.Central;
import lee.code.central.commands.cmds.*;
import lee.code.central.lang.Lang;
import lombok.Getter;
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
    @Getter private final Set<CustomCommand> commands = ConcurrentHashMap.newKeySet();
    private final ConcurrentHashMap<UUID, ScheduledTask> asyncTasks = new ConcurrentHashMap<>();
    private final Object synchronizedThreadLock = new Object();

    public CommandManager(Central central) {
        this.central = central;
        storeCommands();
    }

    private void storeCommands() {
        commands.add(new GameModeCMD(central));
        commands.add(new FlyCMD(central));
        commands.add(new FlySpeedCMD(central));
        commands.add(new WeatherCMD(central));
        commands.add(new SmiteCMD(central));
        commands.add(new EnchantCMD(central));
        commands.add(new GiveCMD(central));
        commands.add(new SpawnerCMD(central));
        commands.add(new GlowCMD(central));
        commands.add(new HeadCMD(central));
        commands.add(new HealCMD(central));
        commands.add(new FeedCMD(central));
        commands.add(new SeenCMD(central));
        commands.add(new ColorsCMD(central));
        commands.add(new BalanceCMD(central));
        commands.add(new BalanceTopCMD(central));
        commands.add(new MoneyCMD(central));
        commands.add(new WorthCMD(central));
        commands.add(new SellCMD(central));
        commands.add(new PayCMD(central));
        commands.add(new SpawnCMD(central));
        commands.add(new SetSpawnCMD(central));
        commands.add(new ClearCMD(central));
        commands.add(new TimeCMD(central));
        commands.add(new WorldCMD(central));
        commands.add(new StatCMD(central));
        commands.add(new StatTopCMD(central));
        commands.add(new ColorCMD(central));
        commands.add(new MessageCMD(central));
        commands.add(new ReplyCMD(central));
        commands.add(new TeleportAskCMD(central));
        commands.add(new RestartWarningCMD(central));
        commands.add(new GodCMD(central));
        commands.add(new EnderChestCMD(central));
        commands.add(new SummonCMD(central));
    }

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
