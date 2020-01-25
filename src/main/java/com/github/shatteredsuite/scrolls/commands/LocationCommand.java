package com.github.shatteredsuite.scrolls.commands;

import com.github.shatteredsuite.scrolls.ShatteredScrolls;
import com.github.shatteredsuite.utilities.commands.WrappedCommand;

public class LocationCommand extends WrappedCommand {

    final BaseCommand parent;

    public LocationCommand(ShatteredScrolls instance, BaseCommand parent) {
        super(instance, parent, "location", "shatteredscrolls.location.help", "location-cmd-help");
        this.parent = parent;
        addAlias("l");
        addAlias("loc");
        registerSubcommand(new LocationTeleportCommand(instance, this));
        registerSubcommand(new LocationAddCommand(instance, this));
        registerSubcommand(new LocationRemoveCommand(instance, this));
        registerSubcommand(new LocationEditCommand(instance, this));
        registerSubcommand(new LocationListCommand(instance, this));
    }
}
