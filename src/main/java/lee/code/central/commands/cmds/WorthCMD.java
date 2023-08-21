package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.enums.ItemValue;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WorthCMD extends CustomCommand {

    private final Central central;

    public WorthCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "worth";
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
        final ItemStack handItem = player.getInventory().getItemInMainHand();
        final Material handMaterial = handItem.getType();
        if (handMaterial.equals(Material.AIR)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_WORTH_NO_ITEM.getComponent(null)));
            return;
        }
        if (!central.getData().getSellableMaterials().contains(handMaterial.name())) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_WORTH_NO_VALUE.getComponent(new String[] { CoreUtil.capitalize(handMaterial.name()) })));
            return;
        }
        final double worth = ItemValue.valueOf(handMaterial.name()).getValue();
        final int amount = ItemValue.valueOf(handMaterial.name()).getAmount();
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_WORTH_SUCCESSFUL.getComponent(new String[] {
                CoreUtil.parseValue(amount),
                CoreUtil.capitalize(handMaterial.name()),
                Lang.VALUE_FORMAT.getString(new String[] { CoreUtil.parseValue(worth) })
        })));
    }

    @Override
    public void performConsole(CommandSender console, String[] args, Command command) {
        console.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
    }

    @Override
    public void performSender(CommandSender sender, String[] args, Command command) {}

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
