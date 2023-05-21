package uk.jamieisgeek.battlebox.Storage.Config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import uk.jamieisgeek.battlebox.BattleBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigHandler {
    private final BattleBox plugin;
    private Configuration config;
    private Configuration messages;
    List<File> IslandSchems = new ArrayList<>();

    public ConfigHandler(BattleBox plugin) {
        this.plugin = plugin;
        this.initialize();
    }

    public void initialize() {
        try {
            plugin.saveDefaultConfig();
            plugin.saveResource("messages.yml", false);

            File messages = Arrays.stream(plugin.getDataFolder().listFiles())
                    .filter(file -> file.getName().equals("messages.yml"))
                    .findFirst()
                    .orElse(null);

            if(messages == null) {
                return;
            }

            this.config = plugin.getConfig();
            this.messages = YamlConfiguration.loadConfiguration(messages);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getFromConfig(String path) {
        return config.get(path);
    }

    public String getFromMessages(String path) {
        return messages.getString(path);
    }
}