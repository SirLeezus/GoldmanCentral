package lee.code.central.database;

import lee.code.central.Central;
import lee.code.central.database.cache.CachePlayers;
import lee.code.central.database.cache.CacheServer;
import lombok.Getter;

public class CacheManager {
  private final Central central;

  @Getter private final CachePlayers cachePlayers;
  @Getter private final CacheServer cacheServer;

  public CacheManager(Central central, DatabaseManager databaseManager) {
    this.central = central;
    this.cachePlayers = new CachePlayers(databaseManager);
    this.cacheServer = new CacheServer(databaseManager);
  }
}
