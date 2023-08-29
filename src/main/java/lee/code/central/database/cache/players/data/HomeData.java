package lee.code.central.database.cache.players.data;

import lee.code.central.database.cache.players.CachePlayers;
import lee.code.central.database.tables.PlayerTable;
import lee.code.central.utils.CoreUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HomeData {
  private final CachePlayers cachePlayers;
  private final ConcurrentHashMap<UUID, ConcurrentHashMap<String, String>> homeCache = new ConcurrentHashMap<>();

  public HomeData(CachePlayers cachePlayers) {
    this.cachePlayers = cachePlayers;
  }

  private void setHomeCache(UUID uuid, String name, String location) {
    if (homeCache.containsKey(uuid)) {
      homeCache.get(uuid).put(name, location);
    } else {
      final ConcurrentHashMap<String, String> homes = new ConcurrentHashMap<>();
      homes.put(name, location);
      homeCache.put(uuid, homes);
    }
  }

  private void removeHomeCache(UUID uuid, String name) {
    homeCache.get(uuid).remove(name);
    if (homeCache.get(uuid).isEmpty()) homeCache.remove(uuid);
  }

  public void cacheHomes(PlayerTable playerTable) {
    if (playerTable.getHomes() == null) return;
    final String[] allHomes = playerTable.getHomes().split(",");
    for (String home : allHomes) {
      final String[] homeData = home.split("\\+");
      setHomeCache(playerTable.getUniqueId(), homeData[0], homeData[1]);
    }
  }

  public int getMaxHomes(Player player) {
    if (player.isOp()) return 100;
    return CoreUtil.getHighestPermission(player, "central.homes.", 100);
  }

  public boolean hasHome(UUID uuid) {
    return homeCache.containsKey(uuid);
  }

  public int getHomeAmount(UUID uuid) {
    return homeCache.get(uuid).size();
  }

  public boolean isHome(UUID uuid, String name) {
    return homeCache.get(uuid).containsKey(name);
  }

  public List<String> getHomeNames(UUID uuid) {
    if (!homeCache.containsKey(uuid)) return new ArrayList<>();
    return new ArrayList<>(homeCache.get(uuid).keySet());
  }

  public String getHomeLocationString(UUID uuid, String name) {
    return homeCache.get(uuid).get(name);
  }

  public Location getHomeLocation(UUID uuid, String name) {
    return CoreUtil.parseLocation(homeCache.get(uuid).get(name));
  }

  public void addHome(UUID uuid, String name, Location location) {
    final String locationString = CoreUtil.serializeLocation(location);
    final PlayerTable playerTable = cachePlayers.getPlayerTable(uuid);
    if (playerTable.getHomes() == null) playerTable.setHomes(name + "+" + locationString);
    else playerTable.setHomes(playerTable.getHomes() + "," + name + "+" + locationString);
    setHomeCache(uuid, name, locationString);
    cachePlayers.updatePlayerDatabase(playerTable);
  }

  public void removeHome(UUID uuid, String name) {
    final PlayerTable playerTable = cachePlayers.getPlayerTable(uuid);
    final Set<String> allHomes = Collections.synchronizedSet(new HashSet<>(List.of(playerTable.getHomes().split(","))));
    allHomes.remove(name + "+" + getHomeLocationString(uuid, name));
    if (allHomes.isEmpty()) playerTable.setHomes(null);
    else playerTable.setHomes(StringUtils.join(allHomes, ","));
    removeHomeCache(uuid, name);
    cachePlayers.updatePlayerDatabase(playerTable);
  }
}
