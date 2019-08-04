package org.projpi.shatteredscrolls.commands;

import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.util.commands.WrappedCommand;

public class LocationCommand extends WrappedCommand {
  final BaseCommand parent;

  public LocationCommand(ShatteredScrolls instance, BaseCommand parent) {
    super(instance, parent, "location", "shatteredscrolls.location.help", "scroll-cmd-help");
    this.parent = parent;
    addAlias("l");
    addAlias("loc");
    registerSubcommand(new LocationTeleportCommand(instance, this));
    registerSubcommand(new LocationAddCommand(instance, this));
    registerSubcommand(new LocationRemoveCommand(instance, this));
    registerSubcommand(new LocationEditCommand(instance, this));
  }
}
