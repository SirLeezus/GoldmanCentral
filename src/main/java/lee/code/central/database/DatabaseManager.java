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
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.SQLException;

public class DatabaseManager {

    private final Central central;
    private Dao<PlayerTable, String> playerDao;

    @Getter(AccessLevel.NONE)
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

    private void createOrCacheTables() throws SQLException {
        final CacheManager cacheManager = central.getCacheManager();

        //Player data
        TableUtils.createTableIfNotExists(connectionSource, PlayerTable.class);
        playerDao = DaoManager.createDao(connectionSource, PlayerTable.class);

        for (PlayerTable playerTable : playerDao.queryForAll()) {
            cacheManager.getCachePlayers().setPlayerTable(playerTable);
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
}