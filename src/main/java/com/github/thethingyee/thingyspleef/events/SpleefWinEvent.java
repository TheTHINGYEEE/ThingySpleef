package com.github.thethingyee.thingyspleef.events;

import com.github.thethingyee.thingyspleef.components.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpleefWinEvent extends Event {

    private static HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final Game game;

    public SpleefWinEvent(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
