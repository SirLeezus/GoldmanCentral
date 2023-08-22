package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpawnerCMD extends CustomCommand {

    private final Central central;

    public SpawnerCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "spawner";
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
        final String entityTypeString = args[1].toUpperCase();
        if (!central.getData().getEntityTypes().contains(entityTypeString)) {
            sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_ENTITY_TYPE.getComponent(new String[] { entityTypeString })));
            return;
        }
        final EntityType entityType = EntityType.valueOf(entityTypeString);
        final String amountString = args[2];
        if (!CoreUtil.isPositiveIntNumber(amountString)) {
            sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_VALUE_INVALID.getComponent(new String[] { amountString })));
            return;
        }
        int amount = Integer.parseInt(amountString);
        if (amount > 1000) amount = 1000;
        final ItemStack item = ItemUtil.createSpawner(entityType);
        if (!ItemUtil.canReceiveItems(player, item, amount)) {
            sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NO_INVENTORY_SPACE.getComponent(new String[] { playerString })));
            return;
        }
        ItemUtil.giveItem(player, item, amount);
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GIVE_TARGET_SUCCESSFUL.getComponent(new String[] { CoreUtil.parseValue(amount), Lang.SPAWNER_NAME.getString(new String[] { CoreUtil.capitalize(entityTypeString) }) })));
        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_GIVE_SUCCESSFUL.getComponent(new String[] { playerString, CoreUtil.parseValue(amount), Lang.SPAWNER_NAME.getString(new String[] { CoreUtil.capitalize(entityTypeString) }) })));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return StringUtil.copyPartialMatches(args[0], CoreUtil.getOnlinePlayers(), new ArrayList<>());
        else if (args.length == 2) return StringUtil.copyPartialMatches(args[1], central.getData().getEntityTypes(), new ArrayList<>());
        else return new ArrayList<>();
    }
}
