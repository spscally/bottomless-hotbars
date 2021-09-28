package com.scally.bottomlesshotbars.configuration;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.scally.bottomlesshotbars.BottomlessHotbars;
import org.bukkit.Server;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RunWith(JUnit4.class)
public class ListConfigurationRepositoryTest {

    private static String testYml;

    private Server server;
    private BottomlessHotbars bhb;
    private ConfigurationFile configFile;
    private UUID uuid = UUID.fromString("40e9a6d5-aeb5-4818-9d7d-886921c77a28");

    @BeforeClass
    public static void beforeClass() throws IOException {
        testYml = new String(Files.readAllBytes(Paths.get("src/test/resources/bhb-test.yml")));
    }

    @Before
    public void before() {
        server = MockBukkit.mock();
        bhb = MockBukkit.load(BottomlessHotbars.class);
        configFile = Mockito.mock(ConfigurationFile.class);
    }

    @Test
    public void listHotbarsPresent() throws InvalidConfigurationException {
        FileConfiguration yaml = new YamlConfiguration();
        yaml.loadFromString(testYml);

        // TODO: finish up these tests
        yaml.get(uuid.toString());
    }

    // test list all

    // test list none
}
