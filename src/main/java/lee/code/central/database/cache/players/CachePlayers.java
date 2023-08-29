package lee.code.central.database.cache.players;

import lee.code.central.database.DatabaseManager;
import lee.code.central.database.cache.players.data.HomeData;
import lee.code.central.database.cache.players.data.MailData;
import lee.code.central.database.handlers.DatabaseHandler;
import lee.code.central.database.tables.PlayerTable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CachePlayers extends DatabaseHandler {
  @Getter private final MailData mailData;
  @Getter private final HomeData homeData;
  private final ConcurrentHashMap<UUID, PlayerTable> playersCache = new ConcurrentHashMap<>();

  public CachePlayers(DatabaseManager databaseManager) {
    super(databaseManager);
    this.mailData = new MailData(this);
    this.homeData = new HomeData(this);
  }

  public PlayerTable getPlayerTable(UUID uuid) {
    return playersCache.get(uuid);
  }

  public void setPlayerTable(PlayerTable playerTable) {
    playersCache.put(playerTable.getUniqueId(), playerTable);
    mailData.cacheMail(playerTable);
    homeData.cacheHomes(playerTable);
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

  public ArrayList<UUID> getPlayers() {
    return new ArrayList<>(playersCache.keySet());
  }

  public boolean isGod(UUID uuid) {
    return getPlayerTable(uuid).isGod();
  }

  public void setGod(UUID uuid, boolean result) {
    final PlayerTable playerTable = getPlayerTable(uuid);
    playerTable.setGod(result);
    updatePlayerDatabase(playerTable);
  }
}
