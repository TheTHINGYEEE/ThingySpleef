package com.github.thethingyee.thingyspleef.events.listeners;

import com.github.thethingyee.thingyspleef.components.Game;
import com.github.thethingyee.thingyspleef.components.GameState;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import com.github.thethingyee.thingyspleef.worldmap.GameMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.Optional;

public class ChangeWorld implements Listener {

    private final GameManager gameManager;

    public ChangeWorld(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onChange(PlayerChangedWorldEvent event) {
        if(getGameByWorld(event.getFrom()) == null) return;
        Game game = getGameByWorld(event.getFrom());
        if(game.getGameMap().getWorld().getPlayers().isEmpty()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Unloading game map for being empty.");
            game.setGameState(GameState.CLEANUP);
        }
    }
    private Game getGameByWorld(World w) {
        for(Game g : gameManager.getActiveGames()) {
            if(g.getGameMap().getWorld().equals(w)) return g;
        }
        return null;
    }
}
