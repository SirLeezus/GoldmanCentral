package lee.code.central.listeners;

import lee.code.central.lang.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeathMessage(PlayerDeathEvent e) {
        final Player player = e.getEntity();
        final Player killer = player.getKiller();
        Component deathMessage = e.deathMessage();
        if (deathMessage != null) {
            final TextReplacementConfig playerReplaceConfig = TextReplacementConfig.builder()
                    .matchLiteral(player.getName())
                    .replacement(player.displayName())
                    .build();
            deathMessage = deathMessage.replaceText(playerReplaceConfig);

            if (killer != null && killer != player) {
                final TextReplacementConfig killerReplaceConfig = TextReplacementConfig.builder()
                        .matchLiteral(killer.getName())
                        .replacement(killer.displayName())
                        .build();
                deathMessage = deathMessage.replaceText(killerReplaceConfig);
            }
            e.deathMessage(Lang.DEATH_PREFIX.getComponent(null).append(deathMessage).append(Component.text(".")).color(NamedTextColor.GRAY));
        }
    }
}
