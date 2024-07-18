package com.github.thethingyee.thingyspleef.commands;

import com.github.thethingyee.thingyspleef.components.Arena;
import com.github.thethingyee.thingyspleef.components.Command;
import com.github.thethingyee.thingyspleef.components.Game;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import com.github.thethingyee.thingyspleef.events.SpleefJoinLobby;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;

public class PlayCommand extends Command {
    public PlayCommand(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getArguments() {
        return "<map_name>";
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 1) {
            if(getGameManager().getGamesForQueue().isEmpty()) {
                player.sendMessage(ChatColor.RED + "There are no games to queue into.");
                player.sendMessage(ChatColor.RED + "Please make a new game by specifying the map name.");
                return;
            }
            Optional<Game> queuedGame = getGameManager().getGamesForQueue().stream().findAny();
            if(!queuedGame.isPresent()) return;
            Game g = queuedGame.get();

            Bukkit.getServer().getPluginManager().callEvent(new SpleefJoinLobby(g, player));

            return;
        }

        Optional<Arena> opArena = GameManager.getArenaByName(args[0]);
        if(!opArena.isPresent()) {
            player.sendMessage("Sorry, that map is nowhere to be found.");
            return;
        }
        Arena arena = opArena.get();

        Game g;

        if((g = getActiveQueueGameByArena(arena)) == null) {
            player.sendMessage(ChatColor.YELLOW + "Creating new game with the map " + arena.getName());
            g = getGameManager().newGame(arena);
        }

        Bukkit.getServer().getPluginManager().callEvent(new SpleefJoinLobby(g, player));
    }

    private Game getActiveQueueGameByArena(Arena arena) {
        return getGameManager().getGamesForQueue().stream().filter(game -> game.getArena().equals(arena)).findFirst().orElse(null);
    }
}
