package com.github.shatteredsuite.scrolls.commands;

import com.github.shatteredsuite.scrolls.config.ScrollLocation;
import java.util.Collection;
import java.util.HashMap;
import org.bukkit.command.CommandSender;
import com.github.shatteredsuite.scrolls.ShatteredScrolls;
import com.github.shatteredsuite.utilities.commands.WrappedCommand;

public class LocationListCommand extends WrappedCommand {

    private final ShatteredScrolls instance;
    private final LocationCommand parent;

    public LocationListCommand(ShatteredScrolls instance, LocationCommand parent) {
        super(instance, parent, "list", "shatteredscrolls.location.list", "location-list-cmd-help");
        this.instance = instance;
        this.parent = parent;
        addAlias("l");
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!showHelpOrNoPerms(sender, label, args)) {
            return true;
        }

        Collection<ScrollLocation> locations = instance.getLocations();
        instance.getMessenger().sendMessage(sender, "location-list-header");
        for (ScrollLocation location : locations) {
            HashMap<String, String> msgArgs = parent.parent.buildArgs(location);
            instance.getMessenger().sendMessage(sender, "location-list-item", msgArgs);
        }
        return true;
    }
}
