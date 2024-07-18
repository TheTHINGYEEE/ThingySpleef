package com.github.thethingyee.thingyspleef.events;


import com.github.thethingyee.thingyspleef.components.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpleefLeaveEvent extends Event {

    private static HandlerList HANDLERS_LIST = new HandlerList();

    private final Player player;
    private final Game game;
    private final boolean playerQuit;

    public SpleefLeaveEvent(Player player, Game game, boolean playerQuit) {
        this.player = player;
        this.game = game;
        this.playerQuit = playerQuit;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public boolean isPlayerQuit() {
        return playerQuit;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
