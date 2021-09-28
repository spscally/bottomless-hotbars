package com.scally.bottomlesshotbars.configuration;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.scally.bottomlesshotbars.BottomlessHotbars;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(JUnit4.class)
public class GetConfigurationRepositoryTest {

    private Server server;
    private BottomlessHotbars bhb;
    private ConfigurationFile configFile;
    private FileConfiguration yaml;
    private ConfigurationRepository repo;
    private UUID uuid;

    @Before
    public void before() {
        server = MockBukkit.mock();
        bhb = MockBukkit.load(BottomlessHotbars.class);
        configFile = Mockito.mock(ConfigurationFile.class);
        yaml = Mockito.mock(FileConfiguration.class);
        repo = ConfigurationRepository.getInstance(bhb);
        uuid = UUID.randomUUID();
    }

    @After
    public void after() {
        MockBukkit.unmock();
    }

    @Test
    public void getFullHotbar() throws IllegalAccessException {
        final List<String> items = new ArrayList<>();
        items.add(Material.IRON_ORE.toString());
        items.add(Material.DIAMOND_ORE.toString());
        items.add(Material.BIRCH_PLANKS.toString());
        items.add(Material.CRIMSON_BUTTON.toString());
        items.add(Material.GUNPOWDER.toString());
        items.add(Material.CRAFTING_TABLE.toString());
        items.add(Material.CARTOGRAPHY_TABLE.toString());
        items.add(Material.POTION.toString());
        items.add(Material.TRIPWIRE_HOOK.toString());

        Mockito.when(yaml.getStringList(String.format("%s.%s", uuid, "full")))
                        .thenReturn(items);
        Mockito.when(configFile.load()).thenReturn(yaml);
        FieldUtils.writeDeclaredField(repo, "configFile", configFile, true);

        final ItemStack[] full = repo.getHotbar(uuid, "full");
        Assert.assertEquals(9, full.length);

        for (int i = 0; i < 9; i++) {
            Assert.assertEquals(new ItemStack(Material.getMaterial(items.get(i))), full[i]);
        }
    }

    @Test
    public void getPartialHotbar() throws IllegalAccessException {
        final List<String> items = new ArrayList<>();
        items.add(Material.AIR.toString());
        items.add(Material.DIAMOND_ORE.toString());
        items.add(Material.BIRCH_PLANKS.toString());
        items.add(Material.CRIMSON_BUTTON.toString());
        items.add(Material.AIR.toString());
        items.add(Material.CRAFTING_TABLE.toString());
        items.add(Material.CARTOGRAPHY_TABLE.toString());
        items.add(Material.POTION.toString());
        items.add(Material.AIR.toString());

        Mockito.when(yaml.getStringList(String.format("%s.%s", uuid, "partial")))
                .thenReturn(items);
        Mockito.when(configFile.load()).thenReturn(yaml);
        FieldUtils.writeDeclaredField(repo, "configFile", configFile, true);

        final ItemStack[] partial = repo.getHotbar(uuid, "partial");
        Assert.assertEquals(9, partial.length);

        for (int i = 0; i < 9; i++) {
            Assert.assertEquals(new ItemStack(Material.getMaterial(items.get(i))), partial[i]);
        }
    }

    @Test
    public void getEmptyHotbar() throws IllegalAccessException {
        final List<String> items = new ArrayList<>();
        items.add(Material.AIR.toString());
        items.add(Material.AIR.toString());
        items.add(Material.AIR.toString());
        items.add(Material.AIR.toString());
        items.add(Material.AIR.toString());
        items.add(Material.AIR.toString());
        items.add(Material.AIR.toString());
        items.add(Material.AIR.toString());
        items.add(Material.AIR.toString());

        Mockito.when(yaml.getStringList(String.format("%s.%s", uuid, "empty")))
                .thenReturn(items);
        Mockito.when(configFile.load()).thenReturn(yaml);
        FieldUtils.writeDeclaredField(repo, "configFile", configFile, true);

        final ItemStack[] empty = repo.getHotbar(uuid, "empty");
        Assert.assertEquals(9, empty.length);

        for (int i = 0; i < 9; i++) {
            Assert.assertEquals(new ItemStack(Material.getMaterial(items.get(i))), empty[i]);
        }
    }

    @Test
    public void getNonExistentHotbar() throws IllegalAccessException {
        final List<String> items = new ArrayList<>();
        Mockito.when(yaml.getStringList(String.format("%s.%s", uuid, "non-existent")))
                .thenReturn(items);
        Mockito.when(configFile.load()).thenReturn(yaml);
        FieldUtils.writeDeclaredField(repo, "configFile", configFile, true);

        final ItemStack[] nonExistent = repo.getHotbar(uuid, "non-existent");
        Assert.assertEquals(9, nonExistent.length);

        for (int i = 0; i < 9; i++) {
            Assert.assertNull(nonExistent[i]);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getHotbarInvalidName() {
        final ItemStack[] invalid = repo.getHotbar(uuid, "this.is.invalid");
        Assert.assertTrue(false);
    }
}
