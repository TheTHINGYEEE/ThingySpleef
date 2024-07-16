package com.github.thethingyee.thingyspleef.components.manager;

import com.github.thethingyee.thingyspleef.commands.setup.SaveCommand;
import com.github.thethingyee.thingyspleef.commands.setup.SetSpawnCommand;
import com.github.thethingyee.thingyspleef.commands.setup.SetYKillZoneCommand;
import com.github.thethingyee.thingyspleef.commands.setup.SetupCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CommandManager implements CommandExecutor {

    private final GameManager gameManager;

    private Set<com.github.thethingyee.thingyspleef.components.Command> registeredCommands = new HashSet<>();

    public CommandManager(GameManager gameManager) {
        this.gameManager = gameManager;

        registeredCommands.add(new SetupCommand(gameManager));
        registeredCommands.add(new SetYKillZoneCommand(gameManager));
        registeredCommands.add(new SetSpawnCommand(gameManager));
        registeredCommands.add(new SaveCommand(gameManager));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(command.getName().equalsIgnoreCase("spleef"))) return true;
        if(!(commandSender instanceof Player)) return true;
        String[] strippedArgs = Arrays.copyOfRange(args, 1, args.length);
        Player player = (Player) commandSender;

        if(!(args.length > 0)) return true;

        for (com.github.thethingyee.thingyspleef.components.Command registeredCommand : registeredCommands) {
            if(!registeredCommand.getName().equalsIgnoreCase(args[0])) continue;
            registeredCommand.execute(player, strippedArgs);
        }


        return false;
    }
}
