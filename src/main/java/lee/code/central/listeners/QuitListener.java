package lee.code.central.listeners;

import lee.code.central.Central;
import lee.code.central.database.cache.CachePlayers;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.VariableUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class QuitListener implements Listener {

    private final Central central;

    public QuitListener(Central central) {
        this.central = central;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        final CachePlayers cachePlayers = central.getCacheManager().getCachePlayers();
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        //Is Flying Check
        if (player.isFlying()) cachePlayers.setFlying(uuid, true);
        //Update Tab list
        Bukkit.getServer().sendPlayerListHeaderAndFooter(Lang.TABLIST_HEADER.getComponent(null), Lang.TABLIST_FOOTER.getComponent(new String[] { String.valueOf( CoreUtil.getOnlinePlayers().size() - 1) }));
        //Set Quit Message
        e.quitMessage(VariableUtil.parseVariables(player, Lang.PLAYER_QUIT.getComponent(null)));
    }
}
