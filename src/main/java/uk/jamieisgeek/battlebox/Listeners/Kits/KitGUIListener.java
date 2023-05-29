package uk.jamieisgeek.battlebox.Listeners.Kits;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import uk.jamieisgeek.battlebox.Game.Kits.KitManager;

public class KitGUIListener implements Listener {
    @EventHandler
    public void onKitSelect(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(ChatColor.GREEN + "Kit Selection")) {
            return;
        }

        event.setCancelled(true);
        KitManager kitManager = KitManager.getKitManager();
        kitManager.getKits().forEach(kit -> {
            String kitName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
            if (kit.name().equals(kitName)) {
                Player player = (Player) event.getWhoClicked();
                kitManager.giveKitItems(player, kit.name());
                player.closeInventory();
            }
        });
    }
}
