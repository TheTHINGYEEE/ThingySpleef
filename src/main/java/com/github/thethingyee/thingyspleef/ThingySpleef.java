package com.github.thethingyee.thingyspleef;

import com.github.thethingyee.thingyspleef.components.Game;
import com.github.thethingyee.thingyspleef.components.manager.CommandManager;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import com.github.thethingyee.thingyspleef.events.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ThingySpleef extends JavaPlugin {

    private GameManager gameManager;

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onEnable() {

        // register stuff
        this.gameManager = new GameManager(this);
        this.getCommand("spleef").setExecutor(new CommandManager(gameManager));

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new BlockBreak(gameManager), this);
        pm.registerEvents(new ChangeWorld(gameManager), this);
        pm.registerEvents(new GameWin(this), this);
        pm.registerEvents(new PlayerDamage(gameManager), this);
        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new PlayerLeave(gameManager), this);
        pm.registerEvents(new SpleefLeave(), this);

        initializeFiles();
    }

    @Override
    public void onDisable() {
        for (Game activeGame : gameManager.getActiveGames()) {
            activeGame.getPlayers().forEach(player -> player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation()));
        }
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
