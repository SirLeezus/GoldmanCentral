package lee.code.central.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CoreUtil {

    private final static DecimalFormat amountFormatter = new DecimalFormat("#,###.##");
    private final static Pattern numberDoublePattern = Pattern.compile("^(?=.*[1-9])(\\d*\\.?\\d*)$");
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
        return numberDoublePattern.matcher(numbers).matches();
    }

    public static boolean isPositiveIntNumber(String numbers) {
        final String intMax = String.valueOf(Integer.MAX_VALUE);
        if (numbers.length() > intMax.length() || (numbers.length() == intMax.length() && numbers.compareTo(intMax) > 0)) return false;
        return numberIntPattern.matcher(numbers).matches();
    }

    public static String getDate(long date) {
        final Date resultDate = new Date(date);
        return dateFormat.format(resultDate);
    }
}
