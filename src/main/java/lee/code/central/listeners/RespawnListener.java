package lee.code.central.listeners;

import lee.code.central.Central;
import lee.code.central.database.cache.server.CacheServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {
  private final Central central;

  public RespawnListener(Central central) {
    this.central = central;
  }

  @EventHandler
  public void onPlayerRespawn(PlayerRespawnEvent e) {
    if (e.isAnchorSpawn()) return;
    final Player player = e.getPlayer();
    final CacheServer cacheServer = central.getCacheManager().getCacheServer();
    if (player.getBedSpawnLocation() != null) {
      e.setRespawnLocation(player.getBedSpawnLocation());
    } else if (cacheServer.hasSpawn()) {
      e.setRespawnLocation(cacheServer.getSpawn());
    }
  }
}
