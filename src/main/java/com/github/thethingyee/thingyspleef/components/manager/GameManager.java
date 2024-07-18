package com.github.thethingyee.thingyspleef.components.manager;

import com.github.thethingyee.thingyspleef.ThingySpleef;
import com.github.thethingyee.thingyspleef.components.Arena;
import com.github.thethingyee.thingyspleef.components.Game;
import com.github.thethingyee.thingyspleef.components.GameState;
import com.github.thethingyee.thingyspleef.events.SpleefDeathEvent;
import com.github.thethingyee.thingyspleef.worldmap.GameMap;
import com.github.thethingyee.thingyspleef.worldmap.LocalGameMap;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GameManager {

    private final ThingySpleef thingySpleef;

    private static final ArrayList<Arena> availableArenas = new ArrayList<>();

    private final Set<Game> gamesForQueue = new HashSet<>();
    private final Set<Game> activeGames = new HashSet<>();

    private final ArenaConfigManager arenaConfigManager;

    private final File mapsFolder;

    public GameManager(ThingySpleef thingySpleef) {
        this.thingySpleef = thingySpleef;
        this.mapsFolder = new File(thingySpleef.getDataFolder(), "maps");
        this.arenaConfigManager = new ArenaConfigManager(thingySpleef);
    }

    private GameMap convertWorldToGameMap(String worldName) {
        Bukkit.getLogger().info("Converting world to gamemap.");
        return new LocalGameMap(mapsFolder, worldName, true);
    }

    public boolean mapFilesExists(String mapName) {
        return new File(mapsFolder, mapName).exists();
    }

    public ArenaConfigManager getArenaConfigManager() {
        return arenaConfigManager;
    }

    public Set<Game> getActiveGames() {
        return activeGames;
    }

    public void leaveGame(Player player, Game game) {

        Bukkit.getPluginManager().callEvent(new SpleefDeathEvent(player, game, true));

    }

    public boolean gameOnQueue(Game game) {
        return gamesForQueue.contains(game);
    }

    public Game newGame(Arena arena) {
        Game g = new Game(arena, this);
        g.setGameMap(convertWorldToGameMap(arena.getName()));
        g.setGameState(GameState.QUEUEING);
        gamesForQueue.add(g);
        activeGames.add(g);
        return g;
    }

    public Game tempGameForConfig(Arena arena) {
        Game g = new Game(arena, this);
        g.setGameState(GameState.CONFIG);
        g.setGameMap(convertWorldToGameMap(arena.getName()));
        activeGames.add(g);
        return g;
    }

    public ThingySpleef getThingySpleef() {
        return thingySpleef;
    }

    public void cleanUp() {
        // remove gamemaps and games
        activeGames.forEach(game -> {
            Bukkit.getLogger().warning("Unloading world " + game.getGameMap().getWorld().getName());
            game.getGameMap().unloadWorld();
        });
    }

    public File getMapsFolder() {
        return mapsFolder;
    }

    public ArrayList<Arena> getAvailableArenas() {
        return availableArenas;
    }

    public static Optional<Arena> getArenaByName(String name) {
        return availableArenas.stream().filter(arena -> arena.getName().equalsIgnoreCase(name)).findFirst();
    }

    public static Optional<Arena> getArenaByWorld(World world) {
        return availableArenas.stream().filter(arena -> arena.getName().equalsIgnoreCase(world.getName())).findFirst();
    }

    public boolean isPlayingGame(Player player) {
        return (getGameByPlayer(player) != null);
    }

    public Game getGameByPlayer(Player player) {
        for (Game activeGame : activeGames) {
            if(activeGame.getPlayers().contains(player)) return activeGame;
        }
        return null;
    }

    public Set<Game> getGamesForQueue() {
        return gamesForQueue;
    }
}
