package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.managers.TeleportRequestManager;
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
import java.util.UUID;

public class TeleportAskCMD extends CustomCommand {

    private final Central central;

    public TeleportAskCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "teleportask";
    }

    @Override
    public boolean performAsync() {
        return true;
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
        final String targetString = args[0];
        if (!CoreUtil.getOnlinePlayers().contains(targetString)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { targetString })));
            return;
        }
        final Player target = Bukkit.getPlayer(targetString);
        if (target == null) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { targetString })));
            return;
        }
        final UUID targetID = target.getUniqueId();
        final UUID playerID = player.getUniqueId();
        final TeleportRequestManager teleportRequestManager = central.getTeleportRequestManager();
        if (args.length > 1) {
            final String option = args[1].toLowerCase();
            switch (option) {
                case "accept" -> {
                    if (!teleportRequestManager.hasActiveRequest(targetID, playerID)) {
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_TELEPORT_ASK_NO_REQUEST.getComponent(new String[] { ColorAPI.getNameColor(targetID, targetString) })));
                        return;
                    }
                    teleportRequestManager.removeActiveRequest(targetID, playerID);
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TELEPORT_ASK_ACCEPT_PLAYER_SUCCESSFUL.getComponent(new String[] { ColorAPI.getNameColor(targetID, targetString) })));
                    target.teleportAsync(player.getLocation()).thenAccept(result -> {
                        if (result) target.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TELEPORT_ASK_ACCEPT_SUCCESSFUL.getComponent(new String[] {  ColorAPI.getNameColor(playerID, player.getName()) })));
                        else target.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TELEPORT_ASK_ACCEPT_FAILED.getComponent(new String[] { ColorAPI.getNameColor(playerID, player.getName()) })));
                    });
                }
                case "deny" -> {
                    if (!teleportRequestManager.hasActiveRequest(targetID, playerID)) {
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_TELEPORT_ASK_NO_REQUEST.getComponent(new String[] { ColorAPI.getNameColor(targetID, targetString )})));
                        return;
                    }
                    teleportRequestManager.removeActiveRequest(targetID, playerID);
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TELEPORT_ASK_DENY_PLAYER_SUCCESSFUL.getComponent(new String[] { ColorAPI.getNameColor(targetID, target.getName()) })));
                    target.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TELEPORT_ASK_DENY_TARGET_SUCCESSFUL.getComponent(new String[] { ColorAPI.getNameColor(playerID, player.getName()) })));
                }
                default -> player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
            }
            return;
        }
        if (teleportRequestManager.hasActiveRequest(playerID, targetID)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_TELEPORT_ASK_ALREADY_REQUESTED.getComponent(new String[] { ColorAPI.getNameColor(targetID, target.getName()) })));
            return;
        }
        if (target.equals(player)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_TELEPORT_ASK_SELF.getComponent(null)));
            return;
        }
        teleportRequestManager.setActiveRequest(playerID, targetID);
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TELEPORT_ASK_SUCCESS.getComponent(new String[] { ColorAPI.getNameColor(targetID, target.getName()) })));
        CoreUtil.sendConfirmMessage(target,
                Lang.PREFIX.getComponent(null).append(Lang.COMMAND_TELEPORT_ASK_TARGET_SUCCESS.getComponent(new String[] { ColorAPI.getNameColor(playerID, player.getName()) })),
                "/teleportask " + player.getName(),
                Lang.COMMAND_TELEPORT_ASK_ACCEPT_HOVER.getComponent(new String[] { ColorAPI.getNameColor(playerID, player.getName()) }),
                Lang.COMMAND_TELEPORT_ASK_DENY_HOVER.getComponent(new String[] { ColorAPI.getNameColor(playerID, player.getName()) }),
                false
        );
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
        else return new ArrayList<>();
    }
}
