package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GiveCMD extends CustomCommand {

    private final Central central;

    public GiveCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "give";
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
        if (args.length < 3) {
            sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
            return;
        }
        final String playerString = args[0];
        if (!CoreUtil.getOnlinePlayers().contains(playerString)) {
            sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(new String[] { playerString })));
            return;
        }
        final Player player = Bukkit.getPlayer(playerString);
        if (player == null) {
            sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { playerString })));
            return;
        }
        final String materialString = args[1];
        if (!central.getData().getMaterials().contains(materialString)) {
            sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_MATERIAL.getComponent(new String[] { materialString })));
            return;
        }
        final Material material = Material.valueOf(materialString);
        final String amountString = args[2];
        if (!CoreUtil.isPositiveIntNumber(amountString)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_VALUE_INVALID.getComponent(new String[] { amountString })));
            return;
        }
        int amount = Integer.parseInt(amountString);
        if (amount > 100) amount = 100;
        ItemUtil.giveItemOrDrop(player, new ItemStack(material), amount);
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GIVE_TARGET_SUCCESSFUL.getComponent(new String[] { CoreUtil.parseValue(amount), CoreUtil.capitalize(materialString) })));
        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GIVE_SUCCESSFUL.getComponent(new String[] { playerString, CoreUtil.parseValue(amount), CoreUtil.capitalize(materialString) })));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return StringUtil.copyPartialMatches(args[0], CoreUtil.getOnlinePlayers(), new ArrayList<>());
        else if (args.length == 2) return StringUtil.copyPartialMatches(args[1], central.getData().getMaterials(), new ArrayList<>());
        else return new ArrayList<>();
    }
}
