package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.economy.EcoAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BalanceTopCMD extends CustomCommand {

    private final Central central;

    public BalanceTopCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "balancetop";
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
        //TODO name colors
        final Map<UUID, Double> sortedBalances = CoreUtil.sortByValue(EcoAPI.getBalances(), Comparator.reverseOrder());
        final ArrayList<UUID> players = new ArrayList<>(sortedBalances.keySet());
        int index;
        int page = 0;
        int maxDisplayed = 10;
        if (args.length > 0) {
            if (CoreUtil.isPositiveIntNumber(args[0])) page = Integer.parseInt(args[0]);
        }
        int position = page * maxDisplayed + 1;
        final ArrayList<Component> lines = new ArrayList<>();
        lines.add(Lang.COMMAND_BALANCE_TOP_TITLE.getComponent(null));
        lines.add(Component.text(" "));

        for (int i = 0; i < maxDisplayed; i++) {
            index = maxDisplayed * page + i;
            if (index >= players.size()) break;
            final UUID targetID = players.get(index);
            final OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(targetID);
            if (offlineTarget.hasPlayedBefore()) {
                lines.add(Lang.COMMAND_BALANCE_TOP_LINE.getComponent(new String[] {
                        String.valueOf(position),
                        offlineTarget.getName(),
                        Lang.VALUE_FORMAT.getString(new String[] { CoreUtil.parseValue(sortedBalances.get(targetID)) })
                }));
                position++;
                continue;
            }
            maxDisplayed++;
        }

        if (lines.size() == 2) return;
        lines.add(Component.text(" "));
        lines.add(CoreUtil.createPageSelectionComponent("/balancetop", page));
        for (Component line : lines) player.sendMessage(line);
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
