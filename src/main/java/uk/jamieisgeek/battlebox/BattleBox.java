package uk.jamieisgeek.battlebox;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import uk.jamieisgeek.battlebox.Commands.BattleBoxAdmin;
import uk.jamieisgeek.battlebox.Commands.BattleBoxCommand;
import uk.jamieisgeek.battlebox.Game.GameManager;
import uk.jamieisgeek.battlebox.Game.Queue.QueueManager;
import uk.jamieisgeek.battlebox.Game.State.GameState;
import uk.jamieisgeek.battlebox.Storage.Config.ConfigHandler;
import uk.jamieisgeek.battlebox.Storage.Database.Database;

public final class BattleBox extends JavaPlugin {
    private Database database;
    private ConfigHandler configHandler;
    private QueueManager queueManager;
    private GameState gameState;
    private GameManager gameManager;
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
        this.queueManager = new QueueManager(this);
        this.gameState = new GameState();

        plugin = this;
        this.getCommand("battlebox").setExecutor(new BattleBoxCommand(this));
        this.getCommand("battleboxadmin").setExecutor(new BattleBoxAdmin(this));
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
    public QueueManager getQueueManager() {
        return queueManager;
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
