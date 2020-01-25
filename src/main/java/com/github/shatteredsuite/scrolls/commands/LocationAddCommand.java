package com.github.shatteredsuite.scrolls.commands;

import com.github.shatteredsuite.scrolls.config.ScrollLocation;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import com.github.shatteredsuite.scrolls.ShatteredScrolls;
import com.github.shatteredsuite.utilities.commands.WrappedCommand;

public class LocationAddCommand extends WrappedCommand {

    private final LocationCommand parent;
    private final ShatteredScrolls instance;

    public LocationAddCommand(ShatteredScrolls instance, LocationCommand parent) {
        super(instance, parent, "add", "shatteredscrolls.location.add", "location-add-help");
        this.instance = instance;
        this.parent = parent;
        addAlias("a");
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!showHelpOrNoPerms(sender, label, args)) {
            return true;
        }

        if (sender instanceof Player && args.length < 2) {
            HashMap<String, String> msgArgs = new HashMap<>();
            msgArgs.put("label", label);
            msgArgs.put("argc", String.valueOf(args.length));
            msgArgs.put("argx", "at least 2");
            instance.getMessenger().sendErrorMessage(sender, "not-enough-args", msgArgs);
            return true;
        }

        if (sender instanceof ConsoleCommandSender && args.length < 9) {
            HashMap<String, String> msgArgs = new HashMap<>();
            msgArgs.put("label", label);
            msgArgs.put("argc", String.valueOf(args.length));
            msgArgs.put("argx", "8");
            instance.getMessenger().sendErrorMessage(sender, "not-enough-args", msgArgs);
            return true;
        }

        String id = args[0];
        String name = args[1];
        Location location = parent.parent.getPosition(args, 2, sender);
        if(location == null) {
            return true;
        }
        ScrollLocation loc = new ScrollLocation(id, name, location);

        instance.addLocation(loc);
        HashMap<String, String> msgArgs = parent.parent.buildArgs(loc);
        instance.getMessenger().sendMessage(sender, "location-added", msgArgs);

        return true;
    }
}
