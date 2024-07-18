package com.github.thethingyee.thingyspleef.components;

import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import com.github.thethingyee.thingyspleef.worldmap.GameMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Game {
    private final Arena arena;
    private GameMap gameMap;
    private ArrayList<Player> players;
    private ArrayList<Player> remainingPlayers;
    private final GameManager gameManager;

    private BukkitRunnable countdownBeforeActive;

    private GameState gameState = GameState.QUEUEING;

    public Game(Arena arena, GameManager gameManager) {
        this.arena = arena;
        this.gameManager = gameManager;
        this.players = new ArrayList<>();
        this.remainingPlayers = new ArrayList<>();
    }

    public Arena getArena() {
        return arena;
    }

    public ArrayList<Player> getRemainingPlayers() {
        return remainingPlayers;
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

    public BukkitRunnable getCountdownBeforeActive() {
        return countdownBeforeActive;
    }

    public void setGameState(GameState gs) {
        if(gs == gameState) return;
        this.gameState = gs;

        switch(gameState) {
            case QUEUEING:
                gameManager.getGamesForQueue().add(this);

                // run checks to see how many players are on the queue
                // set to starting countdown when 2 or more players have joined the lobby

                // future thingy : already handled by the spleefjoinlobby event.

                break;
            case STARTING:
                // start countdown, can wait for more players
                countdownBeforeActive = new BukkitRunnable() {
                    int counter = 5;
                    @Override
                    public void run() {
                        getPlayers().forEach(player -> {
                            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Game starting in " + counter + "..");
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        });
                        counter--;
                        if(counter < 1) {
                            setGameState(GameState.ACTIVE);
                            cancel();
                        }
                    }
                };

                countdownBeforeActive.runTaskTimer(gameManager.getThingySpleef(), 0, 20);

                break;

            case ACTIVE:
                getPlayers().forEach(player -> player.sendMessage(ChatColor.GREEN + "Game has started! Good luck."));
                gameManager.getGamesForQueue().remove(this);
                players.forEach(player -> player.getInventory().addItem(new ItemStack(Material.DIAMOND_SHOVEL)));
                break;

            case END: { // cleanup
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Game " + getGameMap().getTempWorldName() + " has ended!");
                break;
            }
            case CLEANUP: {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Starting to unload world " + getGameMap().getTempWorldName() + "...");
                getGameMap().unloadWorld();
                this.remainingPlayers = null;
                this.players = null;
                this.gameMap = null;
                gameManager.getActiveGames().remove(this);
                gameManager.getGamesForQueue().remove(this);
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully unloaded...");
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
