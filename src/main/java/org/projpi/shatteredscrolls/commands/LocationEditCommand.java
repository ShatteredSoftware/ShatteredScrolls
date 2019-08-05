package org.projpi.shatteredscrolls.commands;

import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.util.commands.WrappedCommand;

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
