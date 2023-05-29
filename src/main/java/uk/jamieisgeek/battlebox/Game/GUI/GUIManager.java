package uk.jamieisgeek.battlebox.Game.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.jamieisgeek.battlebox.Game.Kits.Kit;
import uk.jamieisgeek.battlebox.Game.Kits.KitManager;

import java.util.List;

public class GUIManager {
    private static GUIManager guiManager;
    public GUIManager() {
        guiManager = this;
    }

    public void kits(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 9, ChatColor.GREEN + "Kit Selection");
        KitManager kitManager = KitManager.getKitManager();
        List<Kit> kits = kitManager.getKits();
        kits.forEach(kit -> {
            ItemStack itemStack = new ItemStack(Material.valueOf(kit.icon()), 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GREEN + kit.name());
            itemMeta.setLore(List.of(ChatColor.GRAY + kit.description()));
            itemStack.setItemMeta(itemMeta);
            inventory.addItem(itemStack);
        });

        this.open(player, inventory);
    }

    private void open(Player player, Inventory inventory) {
        player.openInventory(inventory);
    }

    public static GUIManager getGuiManager() {
        return guiManager;
    }
}
