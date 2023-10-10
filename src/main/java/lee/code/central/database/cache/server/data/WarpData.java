package lee.code.central.database.cache.server.data;

import lee.code.central.database.cache.server.CacheServer;
import lee.code.central.database.tables.ServerTable;
import lee.code.central.utils.CoreUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WarpData {
  private final CacheServer cacheServer;
  private final ConcurrentHashMap<String, String> warpCache = new ConcurrentHashMap<>();

  public WarpData(CacheServer cacheServer) {
    this.cacheServer = cacheServer;
  }

  private void setWarpCache(String name, String location) {
    warpCache.put(name, location);
  }

  private void removeWarpCache(String name) {
    warpCache.remove(name);
  }

  public String getWarpLocationString(String name) {
    return warpCache.get(name);
  }

  public Location getWarpLocation(String name) {
    return CoreUtil.parseLocation(warpCache.get(name));
  }

  public boolean isWarp(String name) {
    return warpCache.containsKey(name);
  }

  public Set<String> getAllWarps() {
    return warpCache.keySet();
  }

  public void cacheWarps(ServerTable serverTable) {
    if (serverTable.getWarps() == null) return;
    final String[] allWarps = serverTable.getWarps().split("!");
    for (String warps : allWarps) {
      final String[] warpData = warps.split("\\+");
      setWarpCache(warpData[0], warpData[1]);
    }
  }

  public void addWarp(String name, Location location) {
    final String locationString = CoreUtil.serializeLocation(location);
    final ServerTable serverTable = cacheServer.getServerTable();
    if (serverTable.getWarps() == null) serverTable.setWarps(name + "+" + locationString);
    else serverTable.setWarps(serverTable.getWarps() + "!" + name + "+" + locationString);
    setWarpCache(name, locationString);
    cacheServer.updateServerDatabase(serverTable);
  }

  public void removeWarp(String name) {
    final ServerTable serverTable = cacheServer.getServerTable();
    final Set<String> allWarps = Collections.synchronizedSet(new HashSet<>(List.of(serverTable.getWarps().split("!"))));
    allWarps.remove(name + "+" + getWarpLocationString(name));
    if (allWarps.isEmpty()) serverTable.setWarps(null);
    else serverTable.setWarps(StringUtils.join(allWarps, "!"));
    removeWarpCache(name);
    cacheServer.updateServerDatabase(serverTable);
  }
}
