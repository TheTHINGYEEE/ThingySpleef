package com.github.thethingyee.thingyspleef.commands.setup;

import com.github.thethingyee.thingyspleef.components.Arena;
import com.github.thethingyee.thingyspleef.components.Command;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Optional;

public class ArenaStatusCommand extends Command {
    public ArenaStatusCommand(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public String getName() {
        return "checkarena";
    }

    @Override
    public String getArguments() {
        return "";
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 1) return;

        Optional<Arena> opArena = GameManager.getArenaByName(args[0]);
        if(!opArena.isPresent()) {
            player.sendMessage("Sorry, that map is nowhere to be found.");
            return;
        }
        Arena arena = opArena.get();


        player.sendMessage(arena.getName());
        Arrays.stream(arena.getSpawnLocation()).forEach(spawn -> player.sendMessage(spawn + ""));
        player.sendMessage("kill-zone: " + arena.getyKillZone());

        String msg = (arena.getName() != null) && (arena.getSpawnLocation() != null) && (arena.getyKillZone() != 0) ? ChatColor.GREEN + "All good for arena "
                + arena.getName() : ChatColor.RED + "There must be an error.";
        player.sendMessage(msg);

        String msg2 = getGameManager().isPlayingGame(player) ? ChatColor.GREEN + "You are playing a game right now" : ChatColor.RED + "You are not playing a game right now.";
        player.sendMessage(msg2);
    }
}
