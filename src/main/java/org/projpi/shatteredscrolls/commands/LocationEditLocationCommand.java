package org.projpi.shatteredscrolls.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.shatteredscrolls.config.ScrollLocation;
import org.projpi.util.commands.WrappedCommand;

public class LocationEditLocationCommand extends WrappedCommand {
  private final ShatteredScrolls instance;
  private final LocationEditCommand parent;

  public LocationEditLocationCommand(ShatteredScrolls instance, LocationEditCommand parent) {
    super(
        instance,
        parent,
        "location",
        "shatteredscrolls.location.edit",
        "location-edit-location-cmd-help");
    this.parent = parent;
    this.instance = instance;
    addAlias("loc");
    addAlias("l");
  }

  @Override
  public boolean onCommand(CommandSender sender, String label, String[] args) {
    if (!showHelpOrNoPerms(sender, label, args)) {
      return true;
    }

    if (args.length < 1) {
      HashMap<String, String> msgArgs = new HashMap<>();
      msgArgs.put("label", label);
      instance.getMessenger().sendErrorMessage(sender, "location-edit-location-cmd-help", msgArgs);
    }

    ScrollLocation scrollLoc =
        instance.getLocation(parent.parent.parent.getLocation(args, 0, sender));
    Location location = parent.parent.parent.getPosition(args, 1, sender);
    if (location == null) {
      return true;
    }
    scrollLoc.location = location;
    return true;
  }

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command command, String alias, String[] args) {
    if (!hasPerms(sender)) {
      return Collections.emptyList();
    }
    if (args.length <= 1) {
      return parent.parent.parent.tabCompleteLocations(args, 0, sender);
    }
    if (args.length == 2) {
      return parent.parent.parent.tabCompletePosition(
          args,
          1,
          sender,
          (instance.hasLocation(args[0]) ? instance.getLocation(args[0]).getLocation() : null));
    }
    return Collections.emptyList();
  }
}
