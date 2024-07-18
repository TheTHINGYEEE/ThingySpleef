package com.github.thethingyee.thingyspleef.events.listeners;

import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import com.github.thethingyee.thingyspleef.events.SpleefLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    private final GameManager gameManager;

    public PlayerLeave(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if(!gameManager.isPlayingGame(event.getPlayer())) return;
        Bukkit.getPluginManager().callEvent(new SpleefLeaveEvent(event.getPlayer(), gameManager.getGameByPlayer(event.getPlayer()), true));
    }
}
