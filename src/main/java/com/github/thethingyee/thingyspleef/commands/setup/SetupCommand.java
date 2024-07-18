package com.github.thethingyee.thingyspleef.commands.setup;

import com.github.thethingyee.thingyspleef.components.Arena;
import com.github.thethingyee.thingyspleef.components.Command;
import com.github.thethingyee.thingyspleef.components.Game;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
