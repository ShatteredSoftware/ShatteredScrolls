package org.projpi.shatteredscrolls.commands;

import java.util.Collection;
import java.util.HashMap;
import org.bukkit.command.CommandSender;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.shatteredscrolls.config.ScrollLocation;
import org.projpi.util.commands.WrappedCommand;

public class LocationListCommand extends WrappedCommand {

  private final ShatteredScrolls instance;

  public LocationListCommand(ShatteredScrolls instance, WrappedCommand parent) {
    super(instance, parent, "list", "shatteredscrolls.location.list", "location-list-cmd-help");
    this.instance = instance;
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
      HashMap<String, String> msgArgs = new HashMap<>();
      msgArgs.put("id", location.getId());
      msgArgs.put("name", location.getName());
      instance.getMessenger().sendMessage(sender, "location-list-item", msgArgs);
    }
    return true;
  }
}
