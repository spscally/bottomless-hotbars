package com.scally.bottomlesshotbars.configuration;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.scally.bottomlesshotbars.BottomlessHotbars;
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

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RunWith(JUnit4.class)
public class SetConfigurationRepositoryTest {

    private Server server;
    private BottomlessHotbars bhb;
    private ConfigurationFile configFile;
    private ConfigurationRepository repo;
    private UUID uuid;

    @Before
    public void before() {
        server = MockBukkit.mock();
        bhb = MockBukkit.load(BottomlessHotbars.class);
        configFile = Mockito.mock(ConfigurationFile.class);
        repo = ConfigurationRepository.getInstance(bhb);
        uuid = UUID.randomUUID();
    }

    @After
    public void after() {
        MockBukkit.unmock();
    }

    @Test
    public void setNewFullHotbar() throws IOException {
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

        final FileConfiguration fileConfig = repo.setHotbar(uuid, full, "full");

        final List<String> items = fileConfig.getStringList(String.format("%s.%s", uuid.toString(), "full"));
        Assert.assertEquals(9, items.size());

        for (int i = 0; i < 9; i++) {
            Assert.assertEquals(full[i].getType(), Material.getMaterial(items.get(i)));
        }
    }

    @Test
    public void setNewPartialHotbar() throws IOException {
        ItemStack[] partial = new ItemStack[9];
        partial[2] = new ItemStack(Material.DIAMOND_AXE);
        partial[7] = new ItemStack(Material.ELYTRA);

        final FileConfiguration fileConfig = repo.setHotbar(uuid, partial, "partial");

        final List<String> items = fileConfig.getStringList(String.format("%s.%s", uuid.toString(), "partial"));
        Assert.assertEquals(9, items.size());

        for (int i = 0; i < 9; i++) {
            if (partial[i] == null) {
                Assert.assertEquals(Material.AIR, Material.getMaterial(items.get(i)));
            } else {
                Assert.assertEquals(partial[i].getType(), Material.getMaterial(items.get(i)));
            }
        }
    }

    @Test
    public void setNewEmptyHotbar() throws IOException {
        ItemStack[] empty = new ItemStack[9];

        final FileConfiguration fileConfig = repo.setHotbar(uuid, empty, "empty");
        final List<String> items = fileConfig.getStringList(String.format("%s.%s", uuid.toString(), "empty"));
        Assert.assertEquals(9, items.size());

        for (String s : items) {
            Assert.assertEquals(Material.AIR, Material.getMaterial(s));
        }
    }

    @Test
    public void setHotbarOverwritesExisting() throws IOException {
        ItemStack[] existing = new ItemStack[9];
        existing[5] = new ItemStack(Material.IRON_NUGGET);
        existing[6] = new ItemStack(Material.ROTTEN_FLESH);

        FileConfiguration fileConfig = repo.setHotbar(uuid, existing, "existing");
        List<String> items = fileConfig.getStringList(String.format("%s.%s", uuid.toString(), "existing"));
        Assert.assertEquals(Material.AIR, Material.getMaterial(items.get(0)));

        existing[0] = new ItemStack(Material.GUNPOWDER);
        fileConfig = repo.setHotbar(uuid, existing, "existing");
        items = fileConfig.getStringList(String.format("%s.%s", uuid.toString(), "existing"));
        Assert.assertEquals(Material.GUNPOWDER, Material.getMaterial(items.get(0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setHotbarInvalidName() throws IOException {
        ItemStack[] hotbar = new ItemStack[9];
        repo.setHotbar(uuid, hotbar, "this.name.is.invalid");
        Assert.assertTrue(false);
    }
}
