package com.scally.bottomlesshotbars.configuration;

import com.scally.bottomlesshotbars.BottomlessHotbars;
import com.scally.bottomlesshotbars.utils.NameUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class ConfigurationRepository {

    private ConfigurationFile configFile;

    private static ConfigurationRepository instance;

    private ConfigurationRepository(BottomlessHotbars bhb) {
        configFile = ConfigurationFile.getInstance(bhb);
    }

    public static ConfigurationRepository getInstance(BottomlessHotbars plugin) {
        if (instance == null) {
            instance = new ConfigurationRepository(plugin);
        }
        return instance;
    }

    public FileConfiguration setHotbar(UUID playerId, ItemStack[] hotbar, String name) throws IOException {
        if (!NameUtils.isValid(name)) {
            throw new IllegalArgumentException("Name cannot contain a period!");
        }

        final List<String> items = new ArrayList<String>();
        for (ItemStack itemStack : hotbar) {
            if (itemStack == null) {
                items.add(Material.AIR.toString());
                continue;
            }

            final Material material = itemStack.getType();
            if (material == null) {
                items.add(Material.AIR.toString());
                continue;
            }

            items.add(material.toString());
        }

        FileConfiguration config = configFile.load();
        config.set(String.format("%s.%s", playerId.toString(), name), items);
        configFile.save(config);
        return config;
    }

    public ItemStack[] getHotbar(UUID playerId, String name) {
        if (!NameUtils.isValid(name)) {
            throw new IllegalArgumentException("Name cannot contain a period!");
        }

        FileConfiguration config = configFile.load();
        List<String> items = config.getStringList(String.format("%s.%s", playerId.toString(), name));

        final ItemStack[] hotbar = new ItemStack[9];
        for (int i = 0; i < items.size(); i++) {
            hotbar[i] = new ItemStack(Material.getMaterial(items.get(i)));
        }
        return hotbar;
    }

    public Collection<String> getHotbarNames(UUID playerId) {
        FileConfiguration config = configFile.load();
        return config.getConfigurationSection(playerId.toString()).getKeys(false);
    }

    /**
     *
     * methods to implement:
     * - get
     * - delete
     */

}
