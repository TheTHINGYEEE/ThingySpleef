package com.github.thethingyee.thingyspleef.events;

import com.github.thethingyee.thingyspleef.components.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpleefJoinLobby extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final Game game;
    private final Player player;

    public SpleefJoinLobby(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public Game getGame() {
       return game;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
