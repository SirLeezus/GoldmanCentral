package lee.code.central.listeners;

import lee.code.central.lang.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementListener implements Listener {

  @EventHandler
  public void onPlayerAchievement(PlayerAdvancementDoneEvent e) {
    final Player player = e.getPlayer();
    Component advancementMessage = e.message();
    if (advancementMessage != null) {
      final TextReplacementConfig textReplacementConfig = TextReplacementConfig.builder()
        .matchLiteral(player.getName())
        .replacement(player.displayName())
        .build();
      advancementMessage = advancementMessage.replaceText(textReplacementConfig);
      e.message(Lang.ADVANCEMENT_PREFIX.getComponent(null).append(advancementMessage).append(Component.text("!")).color(NamedTextColor.DARK_GREEN));
    }
  }
}
