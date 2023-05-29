package uk.jamieisgeek.battlebox.Game.Kits;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import uk.jamieisgeek.battlebox.Storage.Config.ConfigHandler;

import java.util.ArrayList;
import java.util.List;

public class KitManager {
    private static KitManager kitManager;
    private final List<Kit> kits;
    public KitManager() {
        kitManager = this;
        this.kits = new ArrayList<>();

        this.addKit();
    }

    public void addKit() {
        ConfigHandler configHandler = ConfigHandler.getConfigHandler();
        ConfigurationSection kits = configHandler.getConfigurationSection("kits");

        kits.getKeys(false).forEach(key -> {
            Kit kit = new Kit(
                configHandler.getFromConfig("kits." + key + ".name").toString(),
                configHandler.getFromConfig("kits." + key + ".description").toString(),
                configHandler.getFromConfig("kits." + key + ".icon").toString(),
                configHandler.getFromConfig("kits." + key + ".items").toString().split(",")
            );

            this.kits.add(kit);
        });
    }

    public Kit getKit(String name) {
        return kits.stream().filter(kit -> kit.name().equals(name)).findFirst().orElse(null);
    }

    public void giveKitItems(Player player, String kitName) {
        Kit kit = this.getKit(kitName);
        if (kit == null) {
            throw new NullPointerException("Kit " + kitName + " does not exist");
        }

        for (String item : kit.items()) {
            ItemStack itemStack = new ItemStack(Material.valueOf(item), 1);
            player.getInventory().addItem(itemStack);
        }
    }

    public List<Kit> getKits() {
        return kits;
    }

    public static KitManager getKitManager() {
        return kitManager;
    }
}
