package lee.code.central.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lee.code.central.Central;
import lee.code.central.database.cache.server.CacheServer;
import org.bukkit.Location;
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
    final Player player = e.getPlayer();
    if (shouldRespawnInPvPArena(player)) {
      e.setRespawnLocation(new Location(player.getWorld(), 428.50059527742025,55.0,2519.488781263998, (float) 0.22973633, (float) -2.1242402));
      return;
    }
    if (e.isAnchorSpawn()) return;
    final CacheServer cacheServer = central.getCacheManager().getCacheServer();
    if (player.getBedSpawnLocation() != null) {
      e.setRespawnLocation(player.getBedSpawnLocation());
      return;
    }
    if (cacheServer.hasSpawn()) {
      e.setRespawnLocation(cacheServer.getSpawn());
    }
  }

  private boolean shouldRespawnInPvPArena(Player player) {
    final RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
    if (regionManager == null) return false;
    final LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
    final ApplicableRegionSet regionSet = regionManager.getApplicableRegions(localPlayer.getLocation().toVector().toBlockPoint());
    for (ProtectedRegion region : regionSet) {
      if (!region.getId().equals("pvp")) continue;
      return true;
    }
    return false;
  }
}
