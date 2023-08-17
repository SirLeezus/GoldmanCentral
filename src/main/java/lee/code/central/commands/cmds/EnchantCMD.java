package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EnchantCMD extends CustomCommand {

    private final Central central;

    public EnchantCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "enchant";
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
        if (args.length < 2) {
            player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
            return;
        }
        final String enchantName = args[0];
        if (!central.getData().getEnchantments().contains(enchantName)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_ENCHANT.getComponent(new String[] { enchantName })));
            return;
        }
        final NamespacedKey key = NamespacedKey.minecraft(enchantName);
        final Enchantment enchantment = Enchantment.getByKey(key);
        if (enchantment == null) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_ENCHANT.getComponent(new String[] { enchantName })));
            return;
        }
        final String levelString = args[1];
        if (!CoreUtil.isPositiveIntNumber(levelString)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_VALUE_INVALID.getComponent(new String[] { levelString })));
            return;
        }
        final int level = Integer.parseInt(levelString);
        final ItemStack handItem = player.getInventory().getItemInMainHand();
        if (handItem.getType().equals(Material.AIR)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_ENCHANT_NO_HAND_ITEM.getComponent(null)));
            return;
        }
        final ItemMeta handMeta = handItem.getItemMeta();
        if (handMeta.hasEnchant(enchantment)) handMeta.removeEnchant(enchantment);
        handMeta.addEnchant(enchantment, level, true);
        handItem.setItemMeta(handMeta);
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_ENCHANT_SUCCESSFUL.getComponent(new String[] { CoreUtil.capitalize(enchantName), levelString })));
    }

    @Override
    public void performConsole(CommandSender console, String[] args, Command command) {
        console.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
    }

    @Override
    public void performSender(CommandSender sender, String[] args, Command command) { }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return StringUtil.copyPartialMatches(args[0], central.getData().getEnchantments(), new ArrayList<>());
        else return new ArrayList<>();
    }
}
