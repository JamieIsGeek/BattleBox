package uk.jamieisgeek.battlebox.Game;

import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import uk.jamieisgeek.battlebox.BattleBox;
import uk.jamieisgeek.battlebox.Game.Arena.ArenaManager;
import uk.jamieisgeek.battlebox.Game.State.GameState;
import uk.jamieisgeek.battlebox.Game.State.State;
import uk.jamieisgeek.battlebox.Misc.Countdown;
import uk.jamieisgeek.battlebox.Misc.ScoreboardHelper;

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
        plugin.getQueueManager().getQueue().forEach((uuid, s) -> {
            Player player = Bukkit.getPlayer(uuid);
            plugin.getGuiManager().kits(player);
        });

        new Countdown().beginCountdown();
    }

    public void beginGame() {
        plugin.getQueueManager().getQueue().forEach((uuid, name) -> {
            Bukkit.getPlayer(uuid).sendMessage("do the game :nodders:");
        });
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

            if(team1.contains(randomKey) || team2.contains(randomKey)) return;

            if (i < queue.size() / 2) {
                team1.add(randomKey);
            } else {
                team2.add(randomKey);
            }
        }
    }

    public void killPlayer(Player player, String killer) {
        Location location = player.getLocation();
        String team;
        if(team1.contains(player.getUniqueId())) {
            team = "team1";
            team1.remove(player.getUniqueId());
        } else {
            team = "team2";
            team2.remove(player.getUniqueId());
        }

        Location fireworkLoc = new Location(location.getWorld(), location.getX(), location.getY() + 2, location.getZ());

        Firework firework = location.getWorld().spawn(fireworkLoc, Firework.class);
        FireworkMeta fwm = firework.getFireworkMeta();
        fwm.addEffect(FireworkEffect.builder().withColor(getTeamColor(team)).with(FireworkEffect.Type.BALL).build());
        firework.setFireworkMeta(fwm);

        firework.detonate();

        player.setGameMode(GameMode.SPECTATOR);
        plugin.getQueueManager().getQueue().forEach((uuid, s) -> {
            if(uuid.equals(player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + String.valueOf(ChatColor.BOLD) + "You have been eliminated!");
            } else {
                Player receiver = Bukkit.getPlayer(uuid);
                if(receiver == null) return;
                receiver.sendMessage(ChatColor.RED + String.valueOf(ChatColor.BOLD) + player.getName() + " has been eliminated by " + killer);
            }
        });

        if(team1.isEmpty() || team2.isEmpty()) {
            this.endGame();
        }
    }

    public void endGame() {
        plugin.getQueueManager().getQueue().forEach(((uuid, s) -> ScoreboardHelper.getScoreboardHelper().deleteScoreboard(Bukkit.getPlayer(uuid))));
    }

    private Color getTeamColor(String team) {
        if(team.equals("team1")) {
            return Color.AQUA;
        } else {
            return Color.RED;
        }
    }
}
