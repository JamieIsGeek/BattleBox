package uk.jamieisgeek.battlebox.Game.Arena;

import org.bukkit.Location;
import org.bukkit.Material;
import uk.jamieisgeek.battlebox.BattleBox;
import uk.jamieisgeek.battlebox.Storage.Config.ConfigHandler;

public class ArenaManager {
    private final BattleBox plugin;
    private Arena arena;
    private Material team1;
    private Material team2;
    private Material center;
    public ArenaManager(BattleBox plugin) {
        this.plugin = plugin;

        this.makeArena();
    }

    public void makeArena() {
        ConfigHandler configHandler = plugin.getConfigHandler();
        arena = new Arena(
                "Battle Box",
                new Location(
                        plugin.getServer().getWorld(configHandler.getFromConfig("arena.world").toString()),
                        (double) configHandler.getFromConfig("arena.team1.x"),
                        (double) configHandler.getFromConfig("arena.team1.y"),
                        (double) configHandler.getFromConfig("arena.team1.z")
                ),
                new Location(
                        plugin.getServer().getWorld(configHandler.getFromConfig("arena.world").toString()),
                        (double) configHandler.getFromConfig("arena.team2.x"),
                        (double) configHandler.getFromConfig("arena.team2.y"),
                        (double) configHandler.getFromConfig("arena.team2.z")
                ),
                configHandler.getFromConfig("arena.world").toString()
        );

        this.team1 = Material.valueOf(configHandler.getFromConfig("arena.team1.block").toString());
        this.team2 = Material.valueOf(configHandler.getFromConfig("arena.team2.block").toString());
        this.center = Material.valueOf(configHandler.getFromConfig("arena.center.block").toString());
    }
}
