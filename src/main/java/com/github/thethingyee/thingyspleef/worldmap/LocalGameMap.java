package com.github.thethingyee.thingyspleef.worldmap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.*;
import java.nio.file.Files;

// Huge thanks to Jordan Osterberg at this part. https://youtu.be/mqoe_9u7qjc
public class LocalGameMap implements GameMap {

    private final File source;
    private File tempActive;

    private World world;

    public LocalGameMap(File worldFolder, String worldName, boolean loadOnInit) {
        this.source = new File(worldFolder, worldName);
        if(loadOnInit) loadWorld();
    }

    @Override
    public boolean loadWorld() {
        if(isLoaded()) return true;
        this.tempActive = new File(Bukkit.getWorldContainer().getParentFile(), source.getName() + "_temp_" + System.currentTimeMillis());
        try {
            this.copy(source, tempActive);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.world = Bukkit.createWorld(new WorldCreator(tempActive.getName()));

        if(world != null) world.setAutoSave(false);
        return isLoaded();
    }

    @Override
    public void unloadWorld() {

        Bukkit.getLogger().info("Unloading world " + tempActive.getName());

        if(world != null) Bukkit.unloadWorld(world, false);
        if(tempActive != null) deleteTempDirectory(tempActive);

        world = null;
        tempActive = null;
    }

    @Override
    public boolean restoreFromSource() {
        unloadWorld();
        return loadWorld();
    }

    @Override
    public boolean isLoaded() {
        return world != null;
    }

    @Override
    public World getWorld() {
        return world;
    }

    private void copy(File source, File dest) throws IOException {
        if(source.isDirectory()) {
            if(!dest.exists()) dest.mkdir();

            String[] files = source.list();
            if(files == null) return;
            for (String file : files) {
                File newSource = new File(source, file);
                File newDest = new File(dest, file);
                copy(newSource, newDest);
            }
            return;
        }
        InputStream in = Files.newInputStream(source.toPath());
        OutputStream out = Files.newOutputStream(dest.toPath());

        byte[] buffer = new byte[1024];

        int length;
        while((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();
    }

    @Override
    public String getTempWorldName() {
        return tempActive.getName();
    }

    private void deleteTempDirectory(File file) {
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for (File c : files) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Deleting " + c.getName());
                deleteTempDirectory(c);
            }
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Deleting " + file.getName());
        file.delete();
    }
}