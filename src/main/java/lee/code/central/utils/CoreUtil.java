package lee.code.central.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import lee.code.central.lang.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CoreUtil {
  private final static DecimalFormat amountFormatter = new DecimalFormat("#,###.##");
  private static final DecimalFormat shortDecimalFormatter = new DecimalFormat("#.##");
  private final static Pattern numberPositiveDoublePattern = Pattern.compile("^(?=.*[1-9])(\\d*\\.?\\d*)$");
  private final static Pattern numberDoublePattern = Pattern.compile("[-+]?\\d+(\\.\\d+)?");
  private final static Pattern numberIntPattern = Pattern.compile("^[1-9]\\d*$");
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm aa");

  public static String parseValue(int value) {
    if (value == 0) return "0";
    return amountFormatter.format(value);
  }

  public static String parseValue(double value) {
    if (value == 0) return "0";
    return amountFormatter.format(value);
  }

  public static String shortenString(String text, int max) {
    if (text.length() > max) return text.substring(0, max);
    else return text;
  }

  public static String serializeColorComponentJson(String text) {
    final GsonComponentSerializer serializer = GsonComponentSerializer.gson();
    final Component component = parseColorComponent(text);
    return serializer.serialize(component);
  }

  public static Component parseColorComponent(String text) {
    final LegacyComponentSerializer serializer = LegacyComponentSerializer.legacyAmpersand();
    return (Component.empty().decoration(TextDecoration.ITALIC, false)).append(serializer.deserialize(text));
  }

  public static String stripColorCodes(String text) {
    return PlainTextComponentSerializer.plainText().serialize(LegacyComponentSerializer.legacyAmpersand().deserialize(text));
  }

  public static String getTextBeforeCharacter(String input, char character) {
    final int index = input.indexOf(character);
    if (index == -1) return input;
    else return input.substring(0, index);
  }

  public static List<String> getOnlinePlayers() {
    return Bukkit.getOnlinePlayers().stream()
      .filter(player -> !player.getGameMode().equals(GameMode.SPECTATOR))
      .map(Player::getName)
      .collect(Collectors.toList());
  }

  public static String buildStringFromArgs(String[] words, int startIndex) {
    final StringBuilder sb = new StringBuilder();
    for (int i = startIndex; i < words.length; i++) {
      sb.append(words[i]);
      if (i < words.length - 1) sb.append(" ");
    }
    return sb.toString();
  }

  public static String serializeLocation(Location location) {
    if (location == null) return null;
    else if (location.getWorld() == null) return null;
    return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
  }

  public static Location parseLocation(String location) {
    if (location == null) return null;
    final String[] split = location.split(",", 6);
    return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), (float) Double.parseDouble(split[4]), (float) Double.parseDouble(split[5]));
  }

  @SuppressWarnings("deprecation")
  public static String capitalize(String message) {
    final String format = message.toLowerCase().replaceAll("_", " ");
    return WordUtils.capitalize(format);
  }

  public static String parseTime(long time) {
    final long days = TimeUnit.MILLISECONDS.toDays(time);
    final long hours = TimeUnit.MILLISECONDS.toHours(time) - TimeUnit.DAYS.toHours(days);
    final long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(hours) - TimeUnit.DAYS.toMinutes(days);
    final long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.DAYS.toSeconds(days);
    if (days != 0L) return "&e" + days + "&6d&e, " + hours + "&6h&e, " + minutes + "&6m&e, " + seconds + "&6s";
    else if (hours != 0L) return "&e" + hours + "&6h&e, " + minutes + "&6m&e, " + seconds + "&6s";
    else return minutes != 0L ? "&e" + minutes + "&6m&e, " + seconds + "&6s" : "&e" + seconds + "&6s";
  }

  public static boolean isPositiveDoubleNumber(String numbers) {
    return numberPositiveDoublePattern.matcher(numbers).matches();
  }

  public static boolean isDoubleNumber(String numbers) {
    return numberDoublePattern.matcher(numbers).matches();
  }

  public static boolean isPositiveIntNumber(String numbers) {
    final String intMax = String.valueOf(Integer.MAX_VALUE);
    if (numbers.length() > intMax.length() || (numbers.length() == intMax.length() && numbers.compareTo(intMax) > 0)) return false;
    return numberIntPattern.matcher(numbers).matches();
  }

  public static String parseShortDecimalValue(double value) {
    return shortDecimalFormatter.format(value);
  }

  public static String getDate(long date) {
    final Date resultDate = new Date(date);
    return dateFormat.format(resultDate);
  }

  public static <K, V extends Comparable<? super V>> HashMap<K, V> sortByValue(Map<K, V> hm, Comparator<V> comparator) {
    final HashMap<K, V> temp = new LinkedHashMap<>();
    hm.entrySet().stream()
      .sorted(Map.Entry.comparingByValue(comparator))
      .forEachOrdered(entry -> temp.put(entry.getKey(), entry.getValue()));
    return temp;
  }

  public static Component createPageSelectionComponent(String command, int page) {
    final Component next = Lang.NEXT_PAGE_TEXT.getComponent(null).hoverEvent(Lang.NEXT_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, command + " " + (page + 1)));
    final Component split = Lang.PAGE_SPACER_TEXT.getComponent(null);
    final Component prev = Lang.PREVIOUS_PAGE_TEXT.getComponent(null).hoverEvent(Lang.PREVIOUS_PAGE_HOVER.getComponent(null)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, command + " " + (page - 1)));
    return prev.append(split).append(next);
  }

  public static String getStatFormat(Statistic statistic, int value) {
    switch (statistic) {
      case PLAY_ONE_MINUTE, TIME_SINCE_DEATH, TIME_SINCE_REST, TOTAL_WORLD_TIME, SNEAK_TIME -> {
        final long secondsPlayed = value / 20;
        final long millisecondsPlayed = secondsPlayed * 1000;
        return CoreUtil.parseTime(millisecondsPlayed);
      }
      default -> {
        return CoreUtil.parseValue(value);
      }
    }
  }

  public static void sendConfirmMessage(Player player, Component message, String command, Component hoverYes, Component hoverNo, boolean isConfirm) {
    final ArrayList<Component> lines = new ArrayList<>();
    final Component yes;
    if (isConfirm) {
      yes = Lang.CONFIRM.getComponent(null)
        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, command + " confirm"))
        .hoverEvent(hoverYes);
    } else {
      yes = Lang.ACCEPT.getComponent(null)
        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, command + " accept"))
        .hoverEvent(hoverYes);
    }
    final Component no = Lang.DENY.getComponent(null)
      .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, command + " deny"))
      .hoverEvent(hoverNo);
    final Component click = Lang.CLICK.getComponent(null);
    lines.add(message);
    lines.add(click.append(yes.append(Component.text("  ")).append(no)));
    for (Component line : lines) player.sendMessage(line);
  }

  public static int getHighestPermission(Player player, String permission, int maxSearch) {
    int highestLevel = 0;
    for (int i = maxSearch; i >= 1; i--) {
      final String targetPermission = permission + i;
      if (player.hasPermission(targetPermission) && i > highestLevel) {
        highestLevel = i;
      }
    }
    return highestLevel;
  }

  public static String removeSpecialCharacters(String input) {
    final StringBuilder output = new StringBuilder();
    final String regex = "[^a-zA-Z0-9\\s]";
    for (int i = 0; i < input.length(); i++) {
      final char c = input.charAt(i);
      if (Character.toString(c).matches(regex)) continue;
      output.append(c);
    }
    return output.toString();
  }

  public static boolean isWorldGuardClaimed(Location location) {
    final RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(location.getWorld()));
    if (regionManager == null) return false;
    return regionManager.getApplicableRegions(BlockVector3.at(location.getX(), location.getY(), location.getZ())).getRegions().size() != 0;
  }
}
