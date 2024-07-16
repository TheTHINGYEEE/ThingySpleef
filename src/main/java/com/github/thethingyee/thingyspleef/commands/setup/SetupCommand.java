package com.github.thethingyee.thingyspleef.commands.setup;

import com.github.thethingyee.thingyspleef.components.Arena;
import com.github.thethingyee.thingyspleef.components.Command;
import com.github.thethingyee.thingyspleef.components.Game;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import com.github.thethingyee.thingyspleef.worldmap.GameMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;

public class SetupCommand extends Command {
    public SetupCommand(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public String getArguments() {
        return "";
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length == 0) {
            player.sendMessage(ChatColor.RED + "Please specify a map name.");
            return;
        }

        Arena arena = new Arena(args[0]);
        Game g = getGameManager().tempGameForConfig(arena);

        player.teleport(g.getGameMap().getWorld().getSpawnLocation());
        player.setFlying(true);



    }
}
