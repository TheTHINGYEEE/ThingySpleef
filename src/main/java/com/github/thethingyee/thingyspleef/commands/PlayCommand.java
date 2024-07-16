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
            player.sendMessage(ChatColor.RED + "Please specify a map name.");
            return;
        }

        Optional<Arena> opArena = GameManager.getArenaByName(args[0]);
        if(!opArena.isPresent()) {
            player.sendMessage("Sorry, that map is nowhere to be found.");
            return;
        }
        Arena arena = opArena.get();

        Game g = getGameManager().newGame(arena);

        Bukkit.getServer().getPluginManager().callEvent(new SpleefJoinLobby(g, player));
    }
}
