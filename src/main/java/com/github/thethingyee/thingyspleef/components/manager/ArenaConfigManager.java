package com.github.thethingyee.thingyspleef.components.manager;

import com.github.thethingyee.thingyspleef.ThingySpleef;
import com.github.thethingyee.thingyspleef.components.Arena;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ArenaConfigManager {

    private YamlConfiguration config = new YamlConfiguration();
    private File file;

    private final ThingySpleef thingySpleef;

    public ArenaConfigManager(ThingySpleef thingySpleef) {
        this.thingySpleef = thingySpleef;
    }

    public void loadFile() {
        file = new File(thingySpleef.getDataFolder(), "maps.yml");
        if(!file.exists()) thingySpleef.saveResource("maps.yml", false);

        config = YamlConfiguration.loadConfiguration(file);
        config.options().parseComments(true);
        config.options().copyDefaults(true);

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAllArenas() {
        for(String key : config.getConfigurationSection("arenas").getKeys(false)) {
            Arena a = new Arena(key);
            int[] i = config.getIntegerList("arenas." + key + ".spawn-location").stream().mapToInt(Integer::intValue).toArray();
            int killZone = config.getInt("arenas." + key + ".kill-zone");
            a.setSpawnLocation(i);
            a.setyKillZone(killZone);

            Bukkit.getLogger().info("Successfully loaded arena " + a.getName());

            thingySpleef.getGameManager().getAvailableArenas().add(a);
        }
    }

    public void saveArena(Arena arena) {
        config.set("arenas." + arena.getName() + ".spawn-location", arena.getSpawnLocation());
        config.set("arenas." + arena.getName() + ".kill-zone", arena.getyKillZone());
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
