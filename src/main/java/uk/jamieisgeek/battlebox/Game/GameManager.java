package uk.jamieisgeek.battlebox.Game;

import org.bukkit.entity.Player;
import uk.jamieisgeek.battlebox.BattleBox;
import uk.jamieisgeek.battlebox.Game.Arena.ArenaManager;
import uk.jamieisgeek.battlebox.Game.State.GameState;
import uk.jamieisgeek.battlebox.Game.State.State;

import java.util.*;

public class GameManager {
    private final BattleBox plugin;
    private final ArenaManager arenaManager;
    private final List<UUID> team1;
    private final List<UUID> team2;
    public GameManager(BattleBox plugin) {
        this.plugin = plugin;
        this.arenaManager = new ArenaManager(plugin);

        this.team1 = new ArrayList<>();
        this.team2 = new ArrayList<>();
    }

    public void Setup() {
        GameState gameState = plugin.getGameState();
        gameState.setState(State.IN_PROGRESS);

        // Prepare the arena
        arenaManager.Prepare();

        // Organise teams
        this.OrganiseTeams();
        System.out.println(team1);
        System.out.println(team2);

        // Teleport players
        this.TeleportPlayers();

        // Show kit selection

    }

    private void TeleportPlayers() {
        team1.forEach(uuid -> {
            Player player = plugin.getServer().getPlayer(uuid);
            player.teleport(arenaManager.getArena().team1());
        });

        team2.forEach(uuid -> {
            Player player = plugin.getServer().getPlayer(uuid);
            player.teleport(arenaManager.getArena().team2());
        });
    }

    private void OrganiseTeams() {
        HashMap<UUID, String> queue = plugin.getQueueManager().getQueue();
        Random random = new Random();
        for (int i = 0; i < queue.size() / 2; i++) {
            int randomIndex = random.nextInt(queue.size());

            List<UUID> keys = new ArrayList<>(queue.keySet());
            UUID randomKey = keys.get(randomIndex);

            queue.remove(randomKey);

            if (i < queue.size() / 4) {
                team1.add(randomKey);
            } else {
                team2.add(randomKey);
            }
        }
    }
}
