package com.scally.bottomlesshotbars;

import com.scally.bottomlesshotbars.commandexecutors.CommandExecutor;
import com.scally.bottomlesshotbars.configuration.ConfigurationRepository;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class BottomlessHotbars extends JavaPlugin {

    public static final String CONFIG_FILE_NAME = "bhb.yml";

    public BottomlessHotbars() {
        super();
    }

    protected BottomlessHotbars(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        ConfigurationRepository config = ConfigurationRepository.getInstance(this);
        this.getCommand("bhb").setExecutor(new CommandExecutor(config));
    }
}