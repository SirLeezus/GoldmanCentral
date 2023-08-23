package lee.code.central.commands.cmds;

import lee.code.central.Central;
import lee.code.central.Data;
import lee.code.central.commands.CustomCommand;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class StatTopCMD extends CustomCommand {

    private final Central central;

    public StatTopCMD(Central central) {
        this.central = central;
    }

    @Override
    public String getName() {
        return "stattop";
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
        //TODO add name colors
        if (args.length < 1) {
            player.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
            return;
        }
        final Data data = central.getData();
        final String statString = args[0];
        if (!data.getStatistics().contains(statString)) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_STAT_DOES_NOT_EXIST.getComponent(new String[] { statString })));
            return;
        }
        final Statistic statistic = Statistic.valueOf(statString);
        final StringBuilder entityType = new StringBuilder();
        final StringBuilder materialType = new StringBuilder();
        switch (statistic) {
            case DROP, PICKUP, MINE_BLOCK, USE_ITEM, BREAK_ITEM, CRAFT_ITEM, ENTITY_KILLED_BY, KILL_ENTITY -> {
                if (args.length < 2) {
                    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_STAT_NO_OPTION_SELECTED.getComponent(new String[] { statString })));
                    return;
                }
                if (statistic.equals(Statistic.ENTITY_KILLED_BY) || statistic.equals(Statistic.KILL_ENTITY)) {
                    final String entityTypeString = args[1];
                    if (!data.getEntityTypes().contains(entityTypeString)) {
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_ENTITY_TYPE.getComponent(new String[] { entityTypeString })));
                        return;
                    }
                    entityType.append(entityTypeString);
                } else {
                    final String itemString = args[1];
                    if (!data.getMaterials().contains(itemString)) {
                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_MATERIAL.getComponent(new String[] { itemString })));
                        return;
                    }
                    materialType.append(itemString);
                }
            }
        }

        final ArrayList<UUID> players = central.getCacheManager().getCachePlayers().getPlayers();
        final Map<UUID, Integer> playerStatMap = new HashMap<>();
        for (UUID targetID : players) {
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetID);
            if (offlinePlayer.hasPlayedBefore()) {
                if (!entityType.isEmpty()) playerStatMap.put(targetID, offlinePlayer.getStatistic(statistic, EntityType.valueOf(entityType.toString())));
                else if (!materialType.isEmpty()) playerStatMap.put(targetID, offlinePlayer.getStatistic(statistic, Material.valueOf(materialType.toString())));
                else playerStatMap.put(targetID, offlinePlayer.getStatistic(statistic));
            }
        }

        final Map<UUID, Integer> sortedStats = CoreUtil.sortByValue(playerStatMap, Comparator.reverseOrder());
        final ArrayList<UUID> sortedPlayers = new ArrayList<>(sortedStats.keySet());

        int index;
        int page = 0;
        final int maxDisplayed = 10;
        if (args.length > 1) {
            if (!entityType.isEmpty() || !materialType.isEmpty()) {
                if (args.length > 2) {
                    if (CoreUtil.isPositiveIntNumber(args[2])) page = Integer.parseInt(args[2]);
                }
            } else {
                if (CoreUtil.isPositiveIntNumber(args[1])) page = Integer.parseInt(args[1]);
            }
        }

        int position = page * maxDisplayed + 1;

        final StringBuilder statChecked = new StringBuilder();
        if (!entityType.isEmpty()) statChecked.append(CoreUtil.capitalize(statString + " " + entityType));
        else if (!materialType.isEmpty()) statChecked.append(CoreUtil.capitalize(statString + " " + materialType));
        else statChecked.append(CoreUtil.capitalize(statString));

        final ArrayList<Component> lines = new ArrayList<>();
        lines.add(Lang.COMMAND_STAT_TOP_TITLE.getComponent(null));
        lines.add(Component.text(" "));
        lines.add(Lang.COMMAND_STAT_TOP_STAT.getComponent(new String[] { statChecked.toString() }));
        lines.add(Component.text(" "));

        for (int i = 0; i < maxDisplayed; i++) {
            index = maxDisplayed * page + i;
            if (index >= sortedPlayers.size()) break;
            final UUID targetID = sortedPlayers.get(index);
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetID);
            final String statFormat = CoreUtil.getStatFormat(statistic, sortedStats.get(targetID));
            lines.add(Lang.COMMAND_STAT_TOP_LINE.getComponent(new String[] {
                    String.valueOf(position),
                    offlinePlayer.getName(),
                    statFormat
            }));
            position++;
        }

        if (lines.size() == 4) return;
        lines.add(Component.text(" "));
        if (!entityType.isEmpty()) lines.add(CoreUtil.createPageSelectionComponent("/stattop " + statString + " " + entityType, page));
        else if (!materialType.isEmpty()) lines.add(CoreUtil.createPageSelectionComponent("/stattop " + statString + " " + materialType, page));
        else lines.add(CoreUtil.createPageSelectionComponent("/stattop " + statString, page));
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
        switch (args.length) {
            case 1 -> {
                return StringUtil.copyPartialMatches(args[0], central.getData().getStatistics(), new ArrayList<>());
            }
            case 2 -> {
                switch (args[0]) {
                    case "DROP", "PICKUP", "MINE_BLOCK", "USE_ITEM", "BREAK_ITEM", "CRAFT_ITEM" -> {
                        return StringUtil.copyPartialMatches(args[1], central.getData().getMaterials(), new ArrayList<>());
                    }
                    case "ENTITY_KILLED_BY", "KILL_ENTITY" -> {
                        return StringUtil.copyPartialMatches(args[1], central.getData().getEntityTypes(), new ArrayList<>());
                    }
                    default -> {
                        return new ArrayList<>();
                    }
                }
            }
        }
        return new ArrayList<>();
    }
}
