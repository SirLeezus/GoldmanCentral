package lee.code.central.database;

import lee.code.central.Central;
import lee.code.central.database.cache.CachePlayers;
import lombok.Getter;

public class CacheManager {

    private final Central central;

    @Getter private final CachePlayers cachePlayers;

    public CacheManager(Central central, DatabaseManager databaseManager) {
        this.central = central;
        this.cachePlayers = new CachePlayers(databaseManager);
    }
}
