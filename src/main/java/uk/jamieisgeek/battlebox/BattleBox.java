package uk.jamieisgeek.battlebox;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import uk.jamieisgeek.battlebox.Commands.BattleBoxCommand;
import uk.jamieisgeek.battlebox.Storage.Config.ConfigHandler;
import uk.jamieisgeek.battlebox.Storage.Database.Database;

public final class BattleBox extends JavaPlugin {
    private Database database;
    private ConfigHandler configHandler;
    private static BattleBox plugin;

    @Override
    public void onEnable() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.configHandler = new ConfigHandler(this);
        this.database = new Database(
                configHandler.getFromConfig("sql.host").toString(),
                configHandler.getFromConfig("sql.database").toString(),
                configHandler.getFromConfig("sql.username").toString(),
                configHandler.getFromConfig("sql.password").toString(),
                configHandler.getFromConfig("sql.port").toString(),
                configHandler.getFromConfig("sql.table_prefix").toString()
        );

        plugin = this;
        this.getCommand("battleboxtest").setExecutor(new BattleBoxCommand());
        getLogger().info(ChatColor.GREEN + "BattleBox has been enabled!");
    }

    @Override
    public void onDisable() {
        try {
            database.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getLogger().info(ChatColor.GREEN + "BattleBox has been disabled!");
    }

    public Database getDatabase() {
        return database;
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public static BattleBox getPlugin() {
        return plugin;
    }
}