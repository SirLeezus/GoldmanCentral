package lee.code.central.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class VariableUtil {

    private static final Pattern displayNamePattern = Pattern.compile("\\{display-name\\}");

    public static Component parseVariables(Player player, Component component) {
        Component targetMessage = component;
        targetMessage = targetMessage.replaceText(createTextReplacementConfig(displayNamePattern, player.displayName()));
        return targetMessage;
    }

    private static TextReplacementConfig createTextReplacementConfig(Pattern pattern, String message) {
        return TextReplacementConfig.builder().match(pattern).replacement(message).build();
    }

    private static TextReplacementConfig createTextReplacementConfig(Pattern pattern, Component message) {
        return TextReplacementConfig.builder().match(pattern).replacement(message).build();
    }
}
