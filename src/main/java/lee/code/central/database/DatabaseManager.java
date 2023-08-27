package lee.code.central.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.db.DatabaseTypeUtils;
import com.j256.ormlite.logger.LogBackendType;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lee.code.central.Central;
import lee.code.central.database.tables.PlayerTable;
import lee.code.central.database.tables.ServerTable;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.SQLException;

public class DatabaseManager {
  private final Central central;
  private Dao<PlayerTable, String> playerDao;
  private Dao<ServerTable, Integer> serverDao;
  private ConnectionSource connectionSource;

  public DatabaseManager(Central central) {
    this.central = central;
  }

  private String getDatabaseURL() {
    //Setup MongoDB
    if (!central.getDataFolder().exists()) central.getDataFolder().mkdir();
    return "jdbc:sqlite:" + new File(central.getDataFolder(), "database.db");
  }

  public void initialize(boolean debug) {
    if (!debug) LoggerFactory.setLogBackendFactory(LogBackendType.NULL);
    try {
      final String databaseURL = getDatabaseURL();
      connectionSource = new JdbcConnectionSource(
        databaseURL,
        "test",
        "test",
        DatabaseTypeUtils.createDatabaseType(databaseURL));
      createOrCacheTables();
      createDefaultServerData();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void closeConnection() {
    try {
      connectionSource.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void createDefaultServerData() {
    if (central.getCacheManager().getCacheServer().getServerTable() == null) {
      final ServerTable serverTable = new ServerTable();
      central.getCacheManager().getCacheServer().setServerTable(serverTable);
      createServerTable(serverTable);
    }
  }

  private void createOrCacheTables() throws SQLException {
    final CacheManager cacheManager = central.getCacheManager();

    //Player data
    TableUtils.createTableIfNotExists(connectionSource, PlayerTable.class);
    playerDao = DaoManager.createDao(connectionSource, PlayerTable.class);

    for (PlayerTable playerTable : playerDao.queryForAll()) {
      cacheManager.getCachePlayers().setPlayerTable(playerTable);
    }

    //Server data
    TableUtils.createTableIfNotExists(connectionSource, ServerTable.class);
    serverDao = DaoManager.createDao(connectionSource, ServerTable.class);
    for (ServerTable serverTable : serverDao.queryForAll()) {
      cacheManager.getCacheServer().setServerTable(serverTable);
    }
  }

  public synchronized void createPlayerTable(PlayerTable playerTable) {
    Bukkit.getAsyncScheduler().runNow(central, scheduledTask -> {
      try {
        playerDao.createIfNotExists(playerTable);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  public synchronized void updatePlayerTable(PlayerTable playerTable) {
    Bukkit.getAsyncScheduler().runNow(central, scheduledTask -> {
      try {
        playerDao.update(playerTable);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  public synchronized void deletePlayerTable(PlayerTable playerTable) {
    Bukkit.getAsyncScheduler().runNow(central, scheduledTask -> {
      try {
        playerDao.delete(playerTable);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  private synchronized void createServerTable(ServerTable serverTable) {
    Bukkit.getAsyncScheduler().runNow(central, scheduledTask -> {
      try {
        serverDao.createIfNotExists(serverTable);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  public synchronized void updateServerTable(ServerTable serverTable) {
    Bukkit.getAsyncScheduler().runNow(central, scheduledTask -> {
      try {
        serverDao.update(serverTable);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }
}
