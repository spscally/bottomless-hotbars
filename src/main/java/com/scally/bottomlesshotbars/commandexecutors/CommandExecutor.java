package com.scally.bottomlesshotbars.commandexecutors;

import com.scally.bottomlesshotbars.utils.NameUtils;
import com.scally.bottomlesshotbars.configuration.ConfigurationRepository;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

    private final ConfigurationRepository configRepo;

    public CommandExecutor(ConfigurationRepository configRepo) {
        this.configRepo = configRepo;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("Command must be executed by a player!");
            return false;
        }

        final Player p = (Player) sender;
        if (p.getGameMode() != GameMode.CREATIVE) {
            return false;
        }

        if (args.length < 1) {
            return false;
        }

        switch (args[0]) {
            case "set": case "s":
                return setHotbar(p, args);
            case "get": case "g":
                return getHotbar(p, args);
            case "list": case "l":
                return listHotbars(p, args);
//            case "delete":
//                return deleteHotbar(p, args);
            default:
                return false;
        }

        // TODO: implement shared logic from set command
        // TODO: implement subcommand-specific logic
    }

    private boolean setHotbar(Player player, String[] args) {
        if (args.length != 2) {
            return false;
        }

        final ItemStack[] hotbar = getCurrentBar(player);
        try {
            configRepo.setHotbar(player.getUniqueId(), hotbar, args[1]);
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        } catch (IllegalArgumentException e) {
            // TODO: print help message
            return false;
        }
    }

    private boolean getHotbar(Player player, String[] args) {
        if (args.length != 2) {
            return false;
        }

        final ItemStack[] hotbar = configRepo.getHotbar(player.getUniqueId(), args[1]);
        for (int i = 0; i < 9; i++) {
            player.getInventory().setItem(i, hotbar[i]);
        }
        return true;
    }

    private boolean listHotbars(Player player, String[] args) {
        if (args.length != 1) {
            return false;
        }

        final Collection<String> names = configRepo.getHotbarNames(player.getUniqueId());
        for (String name : names) {
            player.sendMessage(String.format("/bhb get %s", name));
        }
        return true;
    }

    private ItemStack[] getCurrentBar(Player player) {
        final ItemStack[] currentBar = new ItemStack[9];
        for (int i = 0; i < 9; i++) {
            currentBar[i] = player.getInventory().getItem(i);
        }
        return currentBar;
    }

}
