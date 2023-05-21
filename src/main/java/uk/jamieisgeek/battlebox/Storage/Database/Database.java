package uk.jamieisgeek.battlebox.Storage.Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class Database {
    private final HikariDataSource dataSource;

    private final String address;
    private final String databaseName;
    private final String username;
    private final String password;
    private final String port;
    private final String tablePrefix;
    private static Database database = null;
    private final DatabaseManager databaseManager;

    public Database(String address, String database, String username, String password, String port, String tablePrefix) {
        this.address = address;
        this.databaseName = database;
        this.username = username;
        this.password = password;
        this.port = port;
        this.tablePrefix = tablePrefix;

        this.dataSource = createDataSource();

        Database.database = this;
        this.databaseManager = new DatabaseManager(this);
    }

    private HikariDataSource createDataSource() {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(String.format("jdbc:mariadb://%s:%s/%s?autoReconnect=true&useSSL=false", address, port, databaseName));
            config.setUsername(username);
            config.setPassword(password);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            return new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection() {
        try {
            dataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public String getTablePrefix() {
        return this.tablePrefix;
    }
}