package lee.code.central.listeners;

import lee.code.central.Central;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodListener implements Listener {

    private final Central central;

    public GodListener(Central central) {
        this.central = central;
    }

    @EventHandler
    public void onHeadDrop(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player player) {
            if (central.getCacheManager().getCachePlayers().isGod(player.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }
}
