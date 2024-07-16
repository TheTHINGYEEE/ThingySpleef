package com.github.thethingyee.thingyspleef.commands.setup;

import com.github.thethingyee.thingyspleef.components.Arena;
import com.github.thethingyee.thingyspleef.components.Command;
import com.github.thethingyee.thingyspleef.components.Game;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SetYKillZoneCommand extends Command {
    public SetYKillZoneCommand(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public String getName() {
        return "setkillzone";
    }

    @Override
    public String getArguments() {
        return "";
    }

    @Override
    public void execute(Player player, String[] args) {
        int yKillZone = player.getLocation().getBlockY();
        if(args.length == 1) yKillZone = Integer.parseInt(args[0]);

        Game g = getPlayerGame(player);
        if(g == null) return;


        g.getArena().setyKillZone(yKillZone);
        player.sendMessage(ChatColor.GREEN + "Successfully set Y kill zone to " + yKillZone + "!");
    }
}
