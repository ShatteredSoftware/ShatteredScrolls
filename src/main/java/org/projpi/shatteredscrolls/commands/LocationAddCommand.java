package org.projpi.shatteredscrolls.commands;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.shatteredscrolls.config.ScrollLocation;
import org.projpi.util.commands.WrappedCommand;

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

    if (sender instanceof Player && args.length < 3) {
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
    ScrollLocation loc = new ScrollLocation(id, name, location);

    instance.addLocation(loc);

    return true;
  }
}
