package com.github.shatteredsuite.scrolls.commands;

import com.github.shatteredsuite.scrolls.ShatteredScrolls;
import com.github.shatteredsuite.utilities.commands.WrappedCommand;

public class LocationEditCommand extends WrappedCommand {

    final LocationCommand parent;
    final ShatteredScrolls instance;

    public LocationEditCommand(ShatteredScrolls instance, LocationCommand parent) {
        super(instance, parent, "edit", "shatteredscrolls.location.edit", "location-edit-cmd-help");
        this.instance = instance;
        this.parent = parent;
        registerSubcommand(new LocationEditNameCommand(instance, this));
        registerSubcommand(new LocationEditLocationCommand(instance, this));
    }
}
