package com.github.thethingyee.thingyspleef.events.listeners;

import com.github.thethingyee.thingyspleef.ThingySpleef;
import com.github.thethingyee.thingyspleef.components.GameState;
import com.github.thethingyee.thingyspleef.events.SpleefWinEvent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class GameWin implements Listener {

    private final ThingySpleef thingySpleef;

    public GameWin(ThingySpleef thingySpleef) {
        this.thingySpleef = thingySpleef;
    }

    @EventHandler
    public void onWin(SpleefWinEvent event) {

        int[] spawnXyz = event.getGame().getArena().getSpawnLocation();
        Location spawn = new Location(event.getGame().getGameMap().getWorld(), spawnXyz[0], spawnXyz[1], spawnXyz[2]);
        event.getPlayer().teleport(spawn);
        event.getPlayer().setGameMode(GameMode.SPECTATOR);

        Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + ChatColor.GOLD + " has won a game!");
        event.getPlayer().sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "VICTORY", "", 0, 20 * 5, 0);

        ArrayList<Player> players = event.getGame().getPlayers();
        players.forEach(player -> {
            if(!player.equals(event.getPlayer())) player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "GAME OVER!", "", 0, 20 * 5, 0);
            player.sendMessage(ChatColor.RED + "You will be teleported back in 5 seconds");
        });

        new BukkitRunnable() {
            @Override
            public void run() {
                players.forEach(player -> {
                    World w = Bukkit.getWorlds().get(0);
                    player.teleport(w.getSpawnLocation());

                    event.getGame().setGameState(GameState.CLEANUP);
                });
            }
        }.runTaskLater(thingySpleef, 20L * 5L);
    }
}
