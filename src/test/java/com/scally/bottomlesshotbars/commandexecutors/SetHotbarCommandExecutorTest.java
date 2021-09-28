package com.scally.bottomlesshotbars.commandexecutors;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.scally.bottomlesshotbars.BottomlessHotbars;
import com.scally.bottomlesshotbars.configuration.ConfigurationFile;
import com.scally.bottomlesshotbars.configuration.ConfigurationRepository;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.UUID;

@RunWith(JUnit4.class)
public class SetHotbarCommandExecutorTest {

    /*
        CLASSES:
        - ConfigurationFile : actually does the saving and loading (to be mocked)
        - ConfigurationRepository : translate input from executor to save/load calls
        - CommandExecutor : parses input, splits into commands
        - BottomlessHotbars : the plugin itself
     */

    private Server server;
    private BottomlessHotbars plugin;
    private ConfigurationFile configFile;
    private ConfigurationRepository repo;


    private CommandExecutor executor;
    private UUID uuid;
    private Player player;
    private PlayerInventory inventory;
    private Command command;

    @Before
    public void before() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(BottomlessHotbars.class);
        configFile = Mockito.mock(ConfigurationFile.class);
        repo = ConfigurationRepository.getInstance(plugin);
        executor = new CommandExecutor(repo);

        uuid = UUID.randomUUID();
        player = Mockito.spy(Player.class);
        Mockito.when(player.getUniqueId()).thenReturn(uuid);
        inventory = Mockito.spy(PlayerInventory.class);
        Mockito.when(player.getInventory()).thenReturn(inventory);
        command = Mockito.mock(Command.class);
    }

    @After
    public void after() {
        MockBukkit.unmock();
    }

    @Test
    public void setFullHotbar() {
        ItemStack[] full = new ItemStack[9];
        full[0] = new ItemStack(Material.ACACIA_PLANKS);
        full[1] = new ItemStack(Material.BIRCH_PLANKS);
        full[2] = new ItemStack(Material.CRIMSON_BUTTON);
        full[3] = new ItemStack(Material.DEEPSLATE_BRICKS);
        full[4] = new ItemStack(Material.EMERALD);
        full[5] = new ItemStack(Material.FERMENTED_SPIDER_EYE);
        full[6] = new ItemStack(Material.GLISTERING_MELON_SLICE);
        full[7] = new ItemStack(Material.HOPPER);
        full[8] = new ItemStack(Material.IRON_BLOCK);

        Mockito.when(player.getGameMode()).thenReturn(GameMode.CREATIVE);
        final String[] args = new String[] { "set", "full" };

        for (int i = 0; i < 9; i++) {
            Mockito.when(inventory.getItem(i)).thenReturn(full[i]);
        }

        final boolean result = executor.onCommand(player, command, "bhb", args);
        assert result == true;
    }

    // set hotbar completely empty works

    // set hotbar partially empty works

    // set hotbar updates existing hotbar

    // set hotbar not in creative mode fails

    // set hotbar with period in name fails

    // set hotbar with extra args fails

    // set hotbat with too few args fails

}
