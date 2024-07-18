package com.github.thethingyee.thingyspleef.events.listeners;

import com.github.thethingyee.thingyspleef.components.GameState;
import com.github.thethingyee.thingyspleef.events.SpleefDeathEvent;
import com.github.thethingyee.thingyspleef.events.SpleefLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SpleefLeave implements Listener {

    @EventHandler
    public void onLeave(SpleefLeaveEvent event) {

        Bukkit.getPluginManager().callEvent(new SpleefDeathEvent(event.getPlayer(), event.getGame(), true));

        event.getGame().getPlayers().remove(event.getPlayer());
        event.getGame().getPlayers().forEach(player1 -> player1.sendMessage(ChatColor.RED + player1.getDisplayName() + " has left the game!"));
        event.getPlayer().teleport(Bukkit.getWorlds().get(0).getSpawnLocation());

        if(event.getGame().getGameState() != GameState.STARTING) return;
        if(event.getGame().getPlayers().size() < 2) {
            event.getGame().getCountdownBeforeActive().cancel();
            event.getGame().setGameState(GameState.QUEUEING);
            event.getGame().getPlayers().forEach(player -> player.sendMessage(ChatColor.RED + "Not enough players to start the game!"));
        }
    }
}
