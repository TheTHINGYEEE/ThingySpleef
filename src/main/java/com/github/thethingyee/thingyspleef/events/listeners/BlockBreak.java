package com.github.thethingyee.thingyspleef.events.listeners;

import com.github.thethingyee.thingyspleef.components.Game;
import com.github.thethingyee.thingyspleef.components.GameState;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    private final GameManager gameManager;

    public BlockBreak(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(!gameManager.isPlayingGame(player)) return;

        Game game = gameManager.getGameByPlayer(player);

        if(!game.getGameState().equals(GameState.ACTIVE)) event.setCancelled(true);
    }
}
