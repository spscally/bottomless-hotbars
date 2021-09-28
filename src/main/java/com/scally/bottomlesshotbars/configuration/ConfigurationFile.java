package com.scally.bottomlesshotbars.configuration;

import com.scally.bottomlesshotbars.BottomlessHotbars;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

// TODO: rename?
public class ConfigurationFile {

    private final File file;

    private static ConfigurationFile instance;

    private ConfigurationFile(BottomlessHotbars bhb) {
        file = new File(bhb.getDataFolder(), BottomlessHotbars.CONFIG_FILE_NAME);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            bhb.saveResource(BottomlessHotbars.CONFIG_FILE_NAME, false);
        }
    }

    public static ConfigurationFile getInstance(BottomlessHotbars bhb) {
        if (instance == null) {
            instance = new ConfigurationFile(bhb);
        }
        return instance;
    }

    public FileConfiguration load() {
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            System.out.println(e);
        }
        return config;
    }

    public void save(FileConfiguration yaml) throws IOException {
        yaml.save(file);
    }

}
