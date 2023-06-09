package uk.jamieisgeek.battlebox.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.jamieisgeek.battlebox.BattleBox;
import uk.jamieisgeek.battlebox.Game.GUI.GUIManager;

public class BattleBoxAdmin implements CommandExecutor {
    private final BattleBox plugin;
    public BattleBoxAdmin(BattleBox plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.YELLOW + "Only players can use this command!");
            return true;
        }

        if(!player.hasPermission("battlebox.admin")) {
            player.sendMessage(plugin.getConfigHandler().getFromMessages("error.no_permission"));
            return true;
        }

        if(args.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "Usage: /battleboxadmin <subcommand>");
            return true;
        }

        switch (args[0]) {
            case "listqueue" -> {
                player.sendMessage(ChatColor.YELLOW + "Players in queue:\n");

                plugin.getQueueManager().getQueue().forEach((uuid, name) -> player.sendMessage(ChatColor.YELLOW + "- " + name + "\n"));
            }

            case "kitmenu" -> GUIManager.getGuiManager().kits(player);
            case "killself" -> plugin.getGameManager().killPlayer(player, "Dream OPMG REALLL!!!!!");
            case "forcestart" -> plugin.getGameManager().Setup();
        }
        return true;
    }
}
