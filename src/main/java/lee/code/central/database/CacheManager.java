package lee.code.central.database;

import lee.code.central.database.cache.players.CachePlayers;
import lee.code.central.database.cache.server.CacheServer;
import lombok.Getter;

public class CacheManager {

  @Getter private final CachePlayers cachePlayers;
  @Getter private final CacheServer cacheServer;

  public CacheManager(DatabaseManager databaseManager) {
    this.cachePlayers = new CachePlayers(databaseManager);
    this.cacheServer = new CacheServer(databaseManager);
  }
}
