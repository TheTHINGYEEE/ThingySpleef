package com.github.thethingyee.thingyspleef.commands.setup;

import com.github.thethingyee.thingyspleef.components.Arena;
import com.github.thethingyee.thingyspleef.components.Command;
import com.github.thethingyee.thingyspleef.components.Game;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Optional;

public class SaveCommand extends Command {
    public SaveCommand(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getArguments() {
        return "";
    }

    @Override
    public void execute(Player player, String[] args) {
        // do some shit to save config
        Game g = getPlayerGame(player);
        if(g == null) return;

        Arena arena = g.getArena();

        if((arena.getName() == null) || (arena.getSpawnLocation() == null)) {
            player.sendMessage(ChatColor.RED + "Incomplete configuration!");
            return;
        }

        player.sendMessage(ChatColor.GREEN + "Successfully saved arena " + arena.getName());
        player.sendMessage(arena.getName() + ", " + Arrays.toString(arena.getSpawnLocation()) + ", " + arena.getyKillZone());
        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());

        getGameManager().getArenaConfigManager().saveArena(arena);
        g.getGameMap().unloadWorld();

    }
}
