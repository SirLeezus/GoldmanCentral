package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.managers.ReplyManager;
import lee.code.central.utils.CoreUtil;
import lee.code.colors.ColorAPI;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReplyCMD extends CustomCommand {

    private final Central central;

    public ReplyCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "reply";
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
        final ReplyManager replyManager = central.getReplyManager();
        final UUID ownerID = player.getUniqueId();
        if (!replyManager.hasLastMessage(ownerID)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_REPLY_NONE.getComponent(null)));
            return;
        }
        final UUID targetID = replyManager.getLastMessage(ownerID);
        final OfflinePlayer targetOfflinePlayer = Bukkit.getOfflinePlayer(targetID);
        if (!targetOfflinePlayer.isOnline()) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { targetOfflinePlayer.getName() })));
            return;
        }
        final Player targetPlayer = targetOfflinePlayer.getPlayer();
        if (targetPlayer == null) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { targetOfflinePlayer.getName() })));
            return;
        }
        central.getReplyManager().setLastMessage(ownerID, targetID);
        final String message = CoreUtil.buildStringFromArgs(args, 0);
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
        return new ArrayList<>();
    }
}
