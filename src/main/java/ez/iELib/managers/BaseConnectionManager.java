package ez.iELib.managers;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import ez.iELib.Logger;
import ez.iELib.iELib;
import ez.iELib.utils.colorUtils.ColorUtils;
import lombok.Getter;
import lombok.extern.java.Log;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.SQLException;

public abstract class BaseConnectionManager {
    private final String sqlHost;
    private final String sqlPort;
    public static String sqlDatabase;
    private final String sqlUsername;
    private final String sqlPassword;

    @Getter
    private ConnectionSource connectionSource;
    @Getter
    private ConnectionType connectionType;
    private Logger logger;

    public BaseConnectionManager(Logger logger) {
        this.logger = logger;
        sqlHost = getConfigValue("MySQL.host");
        sqlPort = getConfigValue("MySQL.port");
        sqlDatabase = getConfigValue("MySQL.database_name");
        sqlUsername = getConfigValue("MySQL.username");
        sqlPassword = getConfigValue("MySQL.password");

        if (sqlHost.isEmpty() || sqlHost.equals("null")) {
            useSQLite();
        } else {
            String mysqlUrl = "jdbc:mysql://" + sqlHost + ":" + sqlPort + "/" + sqlDatabase + "?useSSL=false";
            try {
                connectionSource = new JdbcConnectionSource(mysqlUrl, sqlUsername, sqlPassword);
                connectionType = ConnectionType.MYSQL;
                logger.log("Connected to MySQL database using OrmLite!", true);
            } catch (SQLException e) {
                logger.error("MySQL connection failed! Falling back to SQLite.", true);
                useSQLite();
            }
        }
    }

    public BaseConnectionManager() {
        sqlHost = getConfigValue("MySQL.host");
        sqlPort = getConfigValue("MySQL.port");
        sqlDatabase = getConfigValue("MySQL.database_name");
        sqlUsername = getConfigValue("MySQL.username");
        sqlPassword = getConfigValue("MySQL.password");

        if (sqlHost.isEmpty() || sqlHost.equals("null")) {
            useSQLite();
        } else {
            String mysqlUrl = "jdbc:mysql://" + sqlHost + ":" + sqlPort + "/" + sqlDatabase + "?useSSL=false";
            try {
                connectionSource = new JdbcConnectionSource(mysqlUrl, sqlUsername, sqlPassword);
                connectionType = ConnectionType.MYSQL;
                Bukkit.getConsoleSender().sendMessage(ColorUtils.color("Connected to MySQL database using OrmLite!"));
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(ColorUtils.color("MySQL connection failed! Falling back to SQLite."));
                useSQLite();
            }
        }
    }

    private void useSQLite() {
        try {
            String backupFolderName = "database";
            String backupFolderPath = iELib.getPlugin().getDataFolder().getAbsolutePath() + File.separator + backupFolderName;
            File backupFolder = new File(backupFolderPath);

            if (!backupFolder.exists()) {
                backupFolder.mkdirs();
            }

            connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + backupFolder + File.separator + "database");
            connectionType = ConnectionType.SQLITE;
            logger.log("Connected to SQLite database using OrmLite!", true);
        } catch (SQLException ex) {
            logger.error("SQLite connection failed!", true);
            throw new RuntimeException("Failed to connect to both MySQL and SQLite databases.", ex);
        }
    }

    private String getConfigValue(String key) {
        String value = iELib.getPlugin().getConfig().getString(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Configuration value for " + key + " is missing or empty.");
        }
        return value.replace("[", "").replace("]", "");
    }

    public void close() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
                System.out.println("Database connection closed.");
            } catch (Exception e) {
                System.err.println("Failed to close database connection.");
                e.printStackTrace();
            }
        }
    }
}