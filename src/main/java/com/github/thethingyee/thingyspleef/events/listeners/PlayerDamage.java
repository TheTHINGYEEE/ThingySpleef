package com.github.thethingyee.thingyspleef.events.listeners;

import com.github.thethingyee.thingyspleef.components.Game;
import com.github.thethingyee.thingyspleef.components.GameState;
import com.github.thethingyee.thingyspleef.components.manager.GameManager;
import com.github.thethingyee.thingyspleef.events.SpleefDeathEvent;
import com.github.thethingyee.thingyspleef.events.SpleefWinEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

public class PlayerDamage implements Listener {

    private final GameManager gameManager;

    public PlayerDamage(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(!gameManager.isPlayingGame(player)) return;

        Game g = gameManager.getGameByPlayer(player);

        event.setCancelled(true);

        if(event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) Bukkit.getPluginManager().callEvent(new SpleefDeathEvent(player, g, false));

    }

    @EventHandler
    public void onSpleefDeath(SpleefDeathEvent event) {
        Player player = event.getPlayer();
        ArrayList<Player> remaining = event.getGame().getRemainingPlayers();
        remaining.remove(player);

        if(event.getGame().getGameState() == GameState.END) return;


        if(remaining.size() <= 1 && event.getGame().getGameState() == GameState.ACTIVE) {
            Player winner = remaining.get(0);
            event.getGame().setGameState(GameState.END);
            Bukkit.getPluginManager().callEvent(new SpleefWinEvent(winner, event.getGame()));
            return;
        }

        int[] spawnXyz = event.getGame().getArena().getSpawnLocation();
        Location spawn = new Location(event.getGame().getGameMap().getWorld(), spawnXyz[0], spawnXyz[1], spawnXyz[2]);
        player.teleport(spawn);
        player.setGameMode(GameMode.SPECTATOR);

        player.sendMessage(ChatColor.RED + "You have died!");
        player.sendMessage(ChatColor.RED + "" + event.getGame().getRemainingPlayers().size() + " players remaining.");

    }
}
