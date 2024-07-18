package com.github.thethingyee.thingyspleef.commands;

import com.github.thethingyee.thingyspleef.components.Command;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import com.github.thethingyee.thingyspleef.events.SpleefLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LeaveCommand extends Command {
    public LeaveCommand(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getArguments() {
        return "";
    }

    @Override
    public void execute(Player player, String[] args) {
        if(!getGameManager().isPlayingGame(player)) {
            player.sendMessage(ChatColor.RED + "You are not on a game.");
            return;
        }

        Bukkit.getPluginManager().callEvent(new SpleefLeaveEvent(player, getGameManager().getGameByPlayer(player)));

        player.sendMessage(ChatColor.RED + "You have left the game.");
    }
}
