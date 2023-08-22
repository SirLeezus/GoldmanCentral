package lee.code.central.database.cache;

import lee.code.central.database.DatabaseManager;
import lee.code.central.database.handlers.DatabaseHandler;
import lee.code.central.database.tables.PlayerTable;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CachePlayers extends DatabaseHandler {

    private final ConcurrentHashMap<UUID, PlayerTable> playersCache = new ConcurrentHashMap<>();

    public CachePlayers(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    public PlayerTable getPlayerTable(UUID uuid) {
        return playersCache.get(uuid);
    }

    public void setPlayerTable(PlayerTable playerTable) {
        playersCache.put(playerTable.getUniqueId(), playerTable);
    }

    public boolean hasPlayerData(UUID uuid) {
        return playersCache.containsKey(uuid);
    }

    public void createPlayerData(UUID uuid) {
        final PlayerTable playerTable = new PlayerTable(uuid);
        setPlayerTable(playerTable);
        createPlayerDatabase(playerTable);
    }

    public boolean isFlying(UUID uuid) {
        return getPlayerTable(uuid).isFlying();
    }

    public void setFlying(UUID uuid, boolean isFlying) {
        final PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setFlying(isFlying);
        updatePlayerDatabase(playerTable);
    }
}
