package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.colors.ColorAPI;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageCMD extends CustomCommand {

    private final Central central;

    public MessageCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "message";
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
        if (args.length < 2) {
            player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
            return;
        }
        final String playerString = args[0];
        if (!CoreUtil.getOnlinePlayers().contains(playerString)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { playerString })));
            return;
        }
        final Player targetPlayer = Bukkit.getPlayer(playerString);
        if (targetPlayer == null) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { playerString })));
            return;
        }
        if (targetPlayer.equals(player)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_MESSAGE_SELF.getComponent(null)));
            return;
        }
        final UUID targetID = targetPlayer.getUniqueId();
        final UUID ownerID = player.getUniqueId();
        central.getReplyManager().setLastMessage(ownerID, targetID);
        final String message = CoreUtil.buildStringFromArgs(args, 1);
        player.sendMessage(Lang.COMMAND_MESSAGE_SENT_SUCCESSFUL.getComponent(new String[] {
                ColorAPI.getColorChar(targetID) + targetPlayer.getName(),
                message
        }).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + targetPlayer.getName() + " ")));
        targetPlayer.sendMessage(Lang.COMMAND_MESSAGE_RECEIVED_SUCCESSFUL.getComponent(new String[] {
                ColorAPI.getColorChar(ownerID) + player.getName(),
                message
        }).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + player.getName() + " ")));
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
