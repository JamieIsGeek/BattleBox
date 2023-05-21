package uk.jamieisgeek.battlebox.Game.Arena;


import org.bukkit.Location;

public record Arena(String name, Location team1, Location team2, String world) {
}
