package com.github.thethingyee.thingyspleef.commands.setup;

import com.github.thethingyee.thingyspleef.components.Arena;
import com.github.thethingyee.thingyspleef.components.Command;
import com.github.thethingyee.thingyspleef.components.Game;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SetSpawnCommand extends Command {
    public SetSpawnCommand(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public String getName() {
        return "setspawn";
    }

    @Override
    public String getArguments() {
        return "";
    }

    @Override
    public void execute(Player player, String[] args) {
        Location playerLoc = player.getLocation();

        Game g = getPlayerGame(player);
        if(g == null) return;

        int[] loc = {playerLoc.getBlockX(), playerLoc.getBlockY(), playerLoc.getBlockZ()};

        g.getArena().setSpawnLocation(loc);
        player.sendMessage(ChatColor.GREEN + "Successfully set spawn location to " + playerLoc.getBlockX() + ", " + playerLoc.getBlockY() + ", " + playerLoc.getBlockZ());
    }
}
