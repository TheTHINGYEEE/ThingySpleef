package com.github.thethingyee.thingyspleef.components;

import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import com.github.thethingyee.thingyspleef.worldmap.GameMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Game {
    private final Arena arena;
    private GameMap gameMap;
    private final ArrayList<Player> players;
    private final GameManager gameManager;

    private GameState gameState = GameState.QUEUEING;

    public Game(Arena arena, GameManager gameManager) {
        this.arena = arena;
        this.gameManager = gameManager;
        this.players = new ArrayList<>();
    }

    public Arena getArena() {
        return arena;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void setGameState(GameState gameState) {
        if(gameState == GameState.ACTIVE || gameState == GameState.STARTING) return;
        this.gameState = gameState;

        switch(gameState) {
            case QUEUEING: {
                // run checks to see how many players are on the queue
                // set to starting countdown when 2 or more players have joined the lobby
                break;
            }
            case STARTING: {
                // start countdown, can wait for more players
                break;
            }
            case ACTIVE: {
                gameManager.getGamesForQueue().remove(this);
                players.forEach(player -> player.getInventory().addItem(new ItemStack(Material.DIAMOND_SHOVEL)));
                break;
            }
            case END: {
                // declare winner
                // teleport players back to spawn
            }
            case CLEANUP: {
                gameMap.unloadWorld();
                break;
            }
            case CONFIG: {
                Bukkit.getLogger().info("Currently configuring " + arena.getName());
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + gameState);
        }
    }
}
