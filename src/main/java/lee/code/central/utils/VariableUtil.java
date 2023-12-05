package lee.code.central.utils;

import lee.code.central.lang.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.regex.Pattern;

public class VariableUtil {
  private static final Pattern displayItemNamePattern = Pattern.compile("\\{display-name-item\\}");
  private static final Pattern displayNamePattern = Pattern.compile("\\{display-name\\}");
  private static final Pattern discordPattern = Pattern.compile("\\{discord\\}");
  private static final Pattern storePattern = Pattern.compile("\\{store\\}");
  private static final Pattern mapPattern = Pattern.compile("\\{map\\}");

  public static Component parseVariables(Player player, Component component) {
    component = component.replaceText(createTextReplacementConfig(displayNamePattern, player.displayName()));
    return component;
  }

  public static Component parseVariables(Component component) {
    component = component.replaceText(createTextReplacementConfig(discordPattern, Lang.DISCORD.getComponent(null).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, Lang.DISCORD.getString())).hoverEvent(Lang.LINK_HOVER.getComponent(null))));
    component = component.replaceText(createTextReplacementConfig(storePattern, Lang.STORE.getComponent(null).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, Lang.STORE.getString())).hoverEvent(Lang.LINK_HOVER.getComponent(null))));
    component = component.replaceText(createTextReplacementConfig(mapPattern, Lang.MAP.getComponent(null).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, Lang.MAP.getString())).hoverEvent(Lang.LINK_HOVER.getComponent(null))));
    return component;
  }

  public static Component parseItemVariables(Component component, ItemStack itemStack) {
    final ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) return component;
    if (itemMeta.hasDisplayName()) component = component.replaceText(createTextReplacementConfig(displayItemNamePattern, itemStack.getItemMeta().displayName()));
    if (!itemMeta.hasDisplayName()) component = component.replaceText(createTextReplacementConfig(displayItemNamePattern, CoreUtil.parseColorComponent(CoreUtil.capitalize(itemStack.getType().name()))));
    return component;
  }

  private static TextReplacementConfig createTextReplacementConfig(Pattern pattern, String message) {
    return TextReplacementConfig.builder().match(pattern).replacement(message).build();
  }

  private static TextReplacementConfig createTextReplacementConfig(Pattern pattern, Component message) {
    return TextReplacementConfig.builder().match(pattern).replacement(message).build();
  }
}
