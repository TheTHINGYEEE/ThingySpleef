package com.github.thethingyee.thingyspleef.components;

public class Arena {

    private final String name;
    private int[] spawnLocation;
    private int yKillZone;

    public Arena(String arenaName) {
        this.name = arenaName;
    }

    public String getName() {
        return name;
    }

    public int[] getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(int[] spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public int getyKillZone() {
        return yKillZone;
    }

    public void setyKillZone(int yKillZone) {
        this.yKillZone = yKillZone;
    }
}
