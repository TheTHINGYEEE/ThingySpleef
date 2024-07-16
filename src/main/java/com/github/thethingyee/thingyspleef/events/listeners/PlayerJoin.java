package com.github.thethingyee.thingyspleef.events.listeners;

import com.github.thethingyee.thingyspleef.components.GameState;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import com.github.thethingyee.thingyspleef.events.SpleefJoinLobby;
import com.github.thethingyee.thingyspleef.worldmap.GameMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;


public class PlayerJoin implements Listener {

    private final GameManager gameManager;

    public PlayerJoin(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onJoinLobby(SpleefJoinLobby event) {
        GameMap gameMap = event.getGame().getGameMap();
        Player player = event.getPlayer();

        int[] spawnLoc = event.getGame().getArena().getSpawnLocation();

        Location arenaLoc = new Location(gameMap.getWorld(), spawnLoc[0], spawnLoc[1], spawnLoc[2]);

        player.teleport(arenaLoc);
        ArrayList<Player> players = event.getGame().getPlayers();
        players.add(event.getPlayer());

        if(players.size() >= 2) event.getGame().setGameState(GameState.STARTING);

        player.sendMessage(ChatColor.GREEN + "You have joined the queue.");
    }
}
