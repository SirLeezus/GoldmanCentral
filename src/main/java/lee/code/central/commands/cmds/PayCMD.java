package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.colors.ColorAPI;
import lee.code.economy.EcoAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PayCMD extends CustomCommand {

    private final Central central;

    public PayCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "pay";
    }

    @Override
    public boolean performAsync() {
        return true;
    }

    @Override
    public boolean performAsyncSynchronized() {
        return true;
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
        final OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(targetString);
        if (target == null) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { targetString })));
            return;
        }
        final UUID targetID = target.getUniqueId();
        if (!EcoAPI.hasPlayerData(targetID)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NO_PLAYER_DATA.getComponent(new String[] { targetString })));
            return;
        }
        if (player.getUniqueId().equals(targetID)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PAY_SELF.getComponent(new String[] { targetString })));
            return;
        }
        final String amountString = args[1];
        if (!CoreUtil.isPositiveDoubleNumber(amountString)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_VALUE_INVALID.getComponent(new String[] { amountString })));
            return;
        }
        final double amount = Double.parseDouble(amountString);
        final double balance = EcoAPI.getBalance(player.getUniqueId());
        if (amount > balance) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PAY_INSUFFICIENT_FUNDS.getComponent(new String[] {
                    Lang.VALUE_FORMAT.getString(new String[] { CoreUtil.parseValue(balance) }),
                    Lang.VALUE_FORMAT.getString(new String[] { CoreUtil.parseValue(amount) })
            })));
            return;
        }
        if (amount < 1) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PAY_BELOW_ZERO.getComponent(new String[] {
                    Lang.VALUE_FORMAT.getString(new String[] { "1" })
            })));
            return;
        }
        EcoAPI.removeBalance(player.getUniqueId(), amount);
        EcoAPI.addBalance(targetID, amount);
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_PAY_SUCCESSFUL.getComponent(new String[] {
                Lang.VALUE_FORMAT.getString(new String[] { CoreUtil.parseValue(amount) }),
                ColorAPI.getNameColor(targetID, target.getName())
        })));
        if (target.isOnline()) {
            final Player onlineTarget = target.getPlayer();
            if (onlineTarget == null) return;
            onlineTarget.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_PAY_TARGET_SUCCESSFUL.getComponent(new String[] {
                    Lang.VALUE_FORMAT.getString(new String[] { CoreUtil.parseValue(amount) }),
                    ColorAPI.getNameColor(player.getUniqueId(), player.getName())
            })));
        }
    }

    @Override
    public void performConsole(CommandSender console, String[] args, Command command) {
        console.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
    }

    @Override
    public void performSender(CommandSender sender, String[] args, Command command) {

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return StringUtil.copyPartialMatches(args[0], CoreUtil.getOnlinePlayers(), new ArrayList<>());
        else return new ArrayList<>();
    }
}
