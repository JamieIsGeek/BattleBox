package uk.jamieisgeek.battlebox.Game.Kits;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
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
                configHandler.getFromConfig("kits." + key + ".items").toString().split(","),
                configHandler.getFromConfig("kits." + key + ".armor").toString().split(",")
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

        String newItem;

        for (String item : kit.items()) {
            if(kit.name().contains(":")) {
                continue;
            }

            newItem = strip(item);

            String[] itemInfo = newItem.split(":");

            int amount = Integer.parseInt(itemInfo[1]);
            String itemName = itemInfo[0];

            ItemStack itemStack = new ItemStack(Material.valueOf(itemName), amount);
            player.getInventory().addItem(itemStack);
        }

        for (String armor : kit.armor()) {
            newItem = strip(armor);

            Material armorMaterial = Material.valueOf(newItem);
            ItemStack item = new ItemStack(armorMaterial, 1);

            if(armorMaterial.getEquipmentSlot().equals(EquipmentSlot.HEAD)) {
                player.getInventory().setHelmet(item);
            } else if (armorMaterial.getEquipmentSlot().equals(EquipmentSlot.CHEST)) {
                player.getInventory().setChestplate(item);
            } else if (armorMaterial.getEquipmentSlot().equals(EquipmentSlot.LEGS)) {
                player.getInventory().setLeggings(item);
            } else if (armorMaterial.getEquipmentSlot().equals(EquipmentSlot.FEET)){
                player.getInventory().setBoots(item);
            }
        }
    }

    private String strip(String string) {
        if(string.contains("[")) {
            string = string.replace("[", "");
        }

        if(string.contains("]")) {
            string = string.replace("]", "");
        }

        if(string.contains(" ")) {
            string = string.replace(" ", "");
        }

        return string;
    }

    public List<Kit> getKits() {
        return kits;
    }

    public static KitManager getKitManager() {
        return kitManager;
    }
}
