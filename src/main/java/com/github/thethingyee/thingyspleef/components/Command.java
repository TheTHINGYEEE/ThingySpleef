package com.github.thethingyee.thingyspleef.components;

import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;

public abstract class Command {

    private final GameManager gameManager;

    public Command(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public abstract String getName();
    public abstract String getArguments();
    public abstract void execute(Player player, String[] args);

    public GameManager getGameManager() {
        return gameManager;
    }


    // returns null if player is not on arena
    public Arena getPlayerArena(Player player) {
        return getPlayerGame(player).getArena();
    }

    public Game getPlayerGame(Player player) {
        Optional<Game> opGame = getGameManager().getActiveGames().stream().filter(game -> game.getGameMap().getWorld().equals(player.getWorld())).findFirst();
        if(!opGame.isPresent()) {
            player.sendMessage(ChatColor.RED + "You are not in an appropiate world to do this.");
            return null;
        }

        return opGame.get();
    }
}

