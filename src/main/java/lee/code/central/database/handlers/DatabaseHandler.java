package lee.code.central.database.handlers;

import lee.code.central.database.DatabaseManager;
import lee.code.central.database.tables.PlayerTable;
import lee.code.central.database.tables.ServerTable;

public class DatabaseHandler {
  private final DatabaseManager databaseManager;

  public DatabaseHandler(DatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  public void createPlayerDatabase(PlayerTable playerTable) {
    databaseManager.createPlayerTable(playerTable);
  }

  public void updatePlayerDatabase(PlayerTable playerTable) {
    databaseManager.updatePlayerTable(playerTable);
  }

  public void deletePlayerDatabase(PlayerTable playerTable) {
    databaseManager.deletePlayerTable(playerTable);
  }

  public void updateServerDatabase(ServerTable serverTable) {
    databaseManager.updateServerTable(serverTable);
  }
}
