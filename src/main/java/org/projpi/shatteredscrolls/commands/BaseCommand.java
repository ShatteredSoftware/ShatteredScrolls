package org.projpi.shatteredscrolls.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.shatteredscrolls.config.ScrollLocation;
import org.projpi.util.commands.WrappedCommand;

public class BaseCommand extends WrappedCommand {

    private final ShatteredScrolls instance;

    public BaseCommand(ShatteredScrolls instance) {
        super(instance, null, "scroll", "shatteredscrolls.help", "base-cmd-help");
        this.instance = instance;
        this.registerSubcommand(new ScrollCommand(instance, this));
        this.registerSubcommand(new LocationCommand(instance, this));
    }

    String getLocation(String[] args, int index, CommandSender sender) {
        if(args.length >= index + 1) {
            String location = args[index];
            if (org.projpi.util.StringUtil.isEmptyOrNull(location) || !instance
                .hasLocation(location)) {
                HashMap<String, String> msgArgs = new HashMap<>();
                msgArgs.put("input", args[index]);
                msgArgs.put("id", location);
                instance.getMessenger().sendErrorMessage(sender, "invalid-location", msgArgs);
                return null;
            }
            return location;
        }
        else {
            HashMap<String, String> msgArgs = new HashMap<>();
            msgArgs.put("argc", String.valueOf(args.length));
            msgArgs.put("argx", String.valueOf(index + 1));
            instance.getMessenger().sendErrorMessage(sender, "not-enough-args", msgArgs);
            return null;
        }
    }

    Integer getCharges(String[] args, int index, CommandSender sender) {
        int charges = instance.config().getCharges();
        if (args.length >= index + 1) {
            if(args[index].equalsIgnoreCase("infinity")) {
                return -1024;
            }
            try {
                charges = Integer.parseInt(args[index]);
            } catch (NumberFormatException ex) {
                HashMap<String, String> msgArgs = new HashMap<>();
                msgArgs.put("input", args[index]);
                instance.getMessenger().sendErrorMessage(sender, "invalid-number-format", msgArgs);
                return null;
            }
        }
        return charges;
    }

    Integer getCount(String[] args, int index, CommandSender sender) {
        int count = 1;
        if (args.length >= index + 1) {
            try {
                count = Integer.parseInt(args[2]);
            } catch (NumberFormatException ex) {
                HashMap<String, String> msgArgs = new HashMap<>();
                msgArgs.put("input", args[2]);
                instance.getMessenger().sendErrorMessage(sender, "invalid-number-format", msgArgs);
                return null;
            }
        }
        return count;
    }

    Location getPosition(String[] args, int startIndex, CommandSender sender) {
        Location location = sender instanceof Player ? ((Player) sender).getLocation() : null;
        // X Y Z
        if (location == null) {
            if (args.length < startIndex + 4) {
                HashMap<String, String> msgArgs = new HashMap<>();
                msgArgs.put("argc", String.valueOf(args.length));
                msgArgs.put("argx", String.valueOf(startIndex + 4));
                instance.getMessenger().sendErrorMessage(sender, "not-enough-args", msgArgs);
                return null;
            }
        }
        if (args.length > startIndex + 2) {
            double x, y, z;
            try {
                if (args[startIndex].equalsIgnoreCase("~") && sender instanceof Player) {
                    x = ((Player) sender).getLocation().getX();
                } else {
                    x = Integer.parseInt(args[startIndex]);
                }
            } catch (NumberFormatException ex) {
                HashMap<String, String> msgArgs = new HashMap<>();
                msgArgs.put("input", args[startIndex]);
                instance.getMessenger().sendErrorMessage(sender, "invalid-number-format", msgArgs);
                return null;
            }
            try {
                if (args[startIndex + 1].equalsIgnoreCase("~") && sender instanceof Player) {
                    y = ((Player) sender).getLocation().getY();
                } else {
                    y = Integer.parseInt(args[startIndex]);
                }
            } catch (NumberFormatException ex) {
                HashMap<String, String> msgArgs = new HashMap<>();
                msgArgs.put("input", args[startIndex + 1]);
                instance.getMessenger().sendErrorMessage(sender, "invalid-number-format", msgArgs);
                return null;
            }
            try {
                if (args[startIndex + 2].equalsIgnoreCase("~") && sender instanceof Player) {
                    z = ((Player) sender).getLocation().getZ();
                } else {
                    z = Integer.parseInt(args[startIndex + 2]);
                }
            } catch (NumberFormatException ex) {
                HashMap<String, String> msgArgs = new HashMap<>();
                msgArgs.put("input", args[startIndex + 2]);
                instance.getMessenger().sendErrorMessage(sender, "invalid-number-format", msgArgs);
                return null;
            }
            location.setX(x);
            location.setY(y);
            location.setZ(z);
        }
        // World
        if (args.length > startIndex + 3) {
            if (args[startIndex + 3].equalsIgnoreCase("~") && sender instanceof Player) {
                location.setWorld(((Player) sender).getWorld());
            } else {
                World world = instance.getServer().getWorld(args[startIndex + 3]);
                if (world == null) {
                    HashMap<String, String> msgArgs = new HashMap<>();
                    msgArgs.put("world", args[startIndex + 3]);
                    instance.getMessenger().sendErrorMessage(sender, "world-not-found", msgArgs);
                    return null;
                }
                location.setWorld(world);
            }
        }
        // Pitch Yaw
        if (args.length > startIndex + 5) {
            float pitch, yaw;
            try {
                if (args[startIndex + 4].equalsIgnoreCase("~") && sender instanceof Player) {
                    pitch = ((Player) sender).getLocation().getPitch();
                } else {
                    pitch = Float.parseFloat(args[startIndex + 4]);
                }
            } catch (NumberFormatException ex) {
                HashMap<String, String> msgArgs = new HashMap<>();
                msgArgs.put("input", args[startIndex + 4]);
                instance.getMessenger().sendErrorMessage(sender, "invalid-number-format", msgArgs);
                return null;
            }
            try {
                if (args[startIndex + 5].equalsIgnoreCase("~") && sender instanceof Player) {
                    yaw = ((Player) sender).getLocation().getYaw();
                } else {
                    yaw = Float.parseFloat(args[startIndex + 5]);
                }
            } catch (NumberFormatException ex) {
                HashMap<String, String> msgArgs = new HashMap<>();
                msgArgs.put("input", args[startIndex + 5]);
                instance.getMessenger().sendErrorMessage(sender, "invalid-number-format", msgArgs);
                return null;
            }
            location.setYaw(yaw);
            location.setPitch(pitch);
        }
        return location;
    }

    List<String> tabCompletePosition(
        String[] args, int startIndex, CommandSender sender, Location location) {
        final List<String> completions = new ArrayList<>();
        if (args.length == startIndex + 1) {
            ArrayList<String> options = new ArrayList<>();
            if (sender instanceof Player) {
                Player p = (Player) sender;
                options.add("~");
                options.add(String.valueOf(p.getLocation().getX()));
                options.add(String.valueOf(p.getLocation().getBlockX()));
            }
            if (location != null) {
                options.add(String.valueOf(location.getX()));
            }
            StringUtil.copyPartialMatches(args[startIndex], options, completions);
            Collections.sort(completions);
            return completions;
        } else if (args.length == startIndex + 2) {
            ArrayList<String> options = new ArrayList<>();
            if (sender instanceof Player) {
                Player p = (Player) sender;
                options.add("~");
                options.add(String.valueOf(p.getLocation().getY()));
                options.add(String.valueOf(p.getLocation().getBlockY()));
            }
            if (location != null) {
                options.add(String.valueOf(location.getY()));
            }
            StringUtil.copyPartialMatches(args[startIndex + 1], options, completions);
            Collections.sort(completions);
            return completions;
        } else if (args.length == startIndex + 3) {
            ArrayList<String> options = new ArrayList<>();
            if (sender instanceof Player) {
                Player p = (Player) sender;
                options.add("~");
                options.add(String.valueOf(p.getLocation().getZ()));
                options.add(String.valueOf(p.getLocation().getBlockZ()));
            }
            if (location != null) {
                options.add(String.valueOf(location.getY()));
            }
            StringUtil.copyPartialMatches(args[startIndex + 2], options, completions);
            Collections.sort(completions);
            return completions;
        } else if (args.length == startIndex + 4) {
            StringUtil.copyPartialMatches(
                args[startIndex + 3],
                instance.getServer().getWorlds().stream()
                    .map(World::getName)
                    .collect(Collectors.toCollection(ArrayList::new)),
                completions);
            Collections.sort(completions);
            return completions;
        } else if (args.length == startIndex + 5) {
            ArrayList<String> options = new ArrayList<>();
            if (sender instanceof Player) {
                Player p = (Player) sender;
                options.add("~");
                options.add(String.valueOf(p.getLocation().getPitch()));
            }
            if (location != null) {
                options.add(String.valueOf(location.getPitch()));
            }
            StringUtil.copyPartialMatches(args[startIndex + 4], options, completions);
            Collections.sort(completions);
            return completions;
        } else if (args.length == startIndex + 6) {
            ArrayList<String> options = new ArrayList<>();
            if (sender instanceof Player) {
                Player p = (Player) sender;
                options.add("~");
                options.add(String.valueOf(p.getLocation().getYaw()));
            }
            if (location != null) {
                options.add(String.valueOf(location.getYaw()));
            }
            StringUtil.copyPartialMatches(args[startIndex + 5], options, completions);
            Collections.sort(completions);
            return completions;
        }
        return Collections.emptyList();
    }

    List<String> tabCompleteLocations(String[] args, int startIndex, CommandSender sender) {
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[startIndex], instance.getLocationKeys(), completions);
        Collections.sort(completions);
        return completions;
    }

    public HashMap<String, String> buildArgs(ScrollLocation loc) {
        HashMap<String, String> msgArgs = new HashMap<>();
        msgArgs.put("location", loc.toString());
        msgArgs.put("id", loc.getId());
        msgArgs.put("name", loc.getName());
        msgArgs.put("x", String.valueOf(loc.getLocation().getBlockX()));
        msgArgs.put("y", String.valueOf(loc.getLocation().getBlockY()));
        msgArgs.put("z", String.valueOf(loc.getLocation().getBlockZ()));
        msgArgs.put("pitch", String.valueOf(loc.getLocation().getPitch()));
        msgArgs.put("yaw", String.valueOf(loc.getLocation().getYaw()));
        if(loc.getLocation().getWorld() != null) {
            msgArgs.put("world", loc.getLocation().getWorld().getName());
        }
        return msgArgs;
    }
}
