package lee.code.central.listeners;

import lee.code.central.Central;
import lee.code.central.database.CacheManager;
import lee.code.central.database.cache.players.CachePlayers;
import lee.code.central.lang.Lang;
import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.VariableUtil;
import lee.code.colors.ColorAPI;
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
    final CacheManager cacheManager = central.getCacheManager();
    final Player player = e.getPlayer();
    final UUID uuid = player.getUniqueId();
    //Player Data
    if (!cacheManager.getCachePlayers().hasPlayerData(uuid)) cacheManager.getCachePlayers().createPlayerData(uuid);
    //first time playing check
    if (!player.hasPlayedBefore()) Bukkit.getServer().sendMessage(Lang.PREFIX.getComponent(null).append(Lang.UNIQUE_JOINS.getComponent(new String[]{ColorAPI.getNameColor(uuid, player.getName()), CoreUtil.parseValue(cacheManager.getCacheServer().addAndGetUniqueJoins())})));
    //Is Flying Check
    if (cacheManager.getCachePlayers().isFlying(uuid)) {
      player.setAllowFlight(true);
      player.setFlying(true);
      cacheManager.getCachePlayers().setFlying(uuid, false);
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
    //Mail message
    if (cacheManager.getCachePlayers().getMailData().hasMail(uuid)) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.YOU_HAVE_MAIL.getComponent(null)));
  }
}
