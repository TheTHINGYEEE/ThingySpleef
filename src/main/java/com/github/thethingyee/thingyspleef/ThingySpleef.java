package com.github.thethingyee.thingyspleef;

import com.github.thethingyee.thingyspleef.components.manager.ArenaConfigManager;
import com.github.thethingyee.thingyspleef.components.manager.CommandManager;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ThingySpleef extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.gameManager = new GameManager(this);
        this.getCommand("spleef").setExecutor(new CommandManager(gameManager));

        initializeFiles();
    }

    @Override
    public void onDisable() {
        gameManager.cleanUp();
    }

    private void initializeFiles() {
        getDataFolder().mkdirs();
        File mapsFolder = new File(getDataFolder(), "maps");
        if(!mapsFolder.exists()) mapsFolder.mkdirs();

        getGameManager().getArenaConfigManager().loadFile();
        getGameManager().getArenaConfigManager().loadAllArenas();
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
