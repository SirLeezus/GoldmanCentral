package lee.code.central.database.cache.server;

import lee.code.central.database.DatabaseManager;
import lee.code.central.database.handlers.DatabaseHandler;
import lee.code.central.database.tables.ServerTable;
import lee.code.central.utils.CoreUtil;
import lombok.Getter;
import org.bukkit.Location;

public class CacheServer extends DatabaseHandler {
  @Getter private ServerTable serverTable = null;

  public CacheServer(DatabaseManager databaseManager) {
    super(databaseManager);
  }

  public void setServerTable(ServerTable serverTable) {
    this.serverTable = serverTable;
  }

  public boolean hasSpawn() {
    return serverTable.getSpawn() != null;
  }

  public Location getSpawn() {
    if (serverTable.getSpawn() == null) return null;
    return CoreUtil.parseLocation(serverTable.getSpawn());
  }

  public void setSpawn(Location location) {
    serverTable.setSpawn(CoreUtil.serializeLocation(location));
    updateServerDatabase(serverTable);
  }

  public int addAndGetUniqueJoins() {
    serverTable.setUniqueJoins(serverTable.getUniqueJoins() + 1);
    updateServerDatabase(serverTable);
    return serverTable.getUniqueJoins();
  }
}
