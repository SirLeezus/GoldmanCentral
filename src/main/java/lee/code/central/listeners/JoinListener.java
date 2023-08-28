package lee.code.central.listeners;

import lee.code.central.Central;
import lee.code.central.database.cache.players.CachePlayers;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.VariableUtil;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {
  private final Central central;

  public JoinListener(Central central) {
    this.central = central;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerJoin(PlayerJoinEvent e) {
    final CachePlayers cachePlayers = central.getCacheManager().getCachePlayers();
    final Player player = e.getPlayer();
    final UUID uuid = player.getUniqueId();
    //Player Data
    if (!cachePlayers.hasPlayerData(uuid)) cachePlayers.createPlayerData(uuid);
    //Is Flying Check
    if (cachePlayers.isFlying(uuid)) {
      player.setAllowFlight(true);
      player.setFlying(true);
      cachePlayers.setFlying(uuid, false);
    }
    //Set Attack Speed
    final AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
    if (attribute != null) attribute.setBaseValue(23.4);
    //Set Join Message
    e.joinMessage(VariableUtil.parseVariables(player, Lang.PLAYER_JOIN.getComponent(null)));
    //Send Tab List
    Bukkit.getServer().sendPlayerListHeaderAndFooter(Lang.TABLIST_HEADER.getComponent(null), Lang.TABLIST_FOOTER.getComponent(new String[]{String.valueOf(CoreUtil.getOnlinePlayers().size())}));
    //Message of the day
    central.getMotdManager().sendMOTD(player);
  }
}
