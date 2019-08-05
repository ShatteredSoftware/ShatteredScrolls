package org.projpi.shatteredscrolls.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.shatteredscrolls.config.ScrollLocation;
import org.projpi.util.commands.WrappedCommand;

public class LocationTeleportCommand extends WrappedCommand {

    private final ShatteredScrolls instance;
    private final LocationCommand parent;

    public LocationTeleportCommand(ShatteredScrolls instance, LocationCommand parent) {
        super(
            instance, parent, "teleport", "shatteredscrolls.location.tp",
            "location-teleport-cmd-help");
        this.instance = instance;
        this.parent = parent;
        addAlias("tp");
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!showHelpOrNoPerms(sender, label, args)) {
            return true;
        }

        if (args.length < 1) {
            HashMap<String, String> msgArgs = new HashMap<>();
            msgArgs.put("label", label);
            instance.getMessenger().sendErrorMessage(sender, "location-teleport-cmd-help", msgArgs);
        }

        if (args.length > 1) {
            HashMap<String, String> msgArgs = new HashMap<>();
            msgArgs.put("label", label);
            msgArgs.put("argc", String.valueOf(args.length));
            msgArgs.put("args", Arrays.stream(args).reduce((a, i) -> a + ", " + i).orElse(""));
            instance.getMessenger().sendErrorMessage(sender, "too-many-args", msgArgs);
        }

        ScrollLocation scrollLoc = instance.getLocation(parent.parent.getLocation(args, 0, sender));

        HashMap<String, String> msgArgs = new HashMap<>();
        msgArgs.put("location", scrollLoc.getName());
        instance.getMessenger().sendMessage(sender, "teleported", msgArgs);

        ((Player) sender).teleport(scrollLoc.getLocation());
        return true;
    }

    @Override
    public List<String> onTabComplete(
        CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length <= 1) {
            StringUtil.copyPartialMatches(args[0], instance.getLocationKeys(), completions);
            Collections.sort(completions);
            return completions;
        } else {
            return Collections.emptyList();
        }
    }
}
