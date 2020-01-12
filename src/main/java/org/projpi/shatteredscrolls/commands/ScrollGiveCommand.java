package org.projpi.shatteredscrolls.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.util.StringUtil;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.shatteredscrolls.items.ScrollItemBuilder;
import org.projpi.util.commands.WrappedCommand;

public class ScrollGiveCommand extends WrappedCommand {

    private final ShatteredScrolls instance;
    private final ScrollCommand parent;

    public ScrollGiveCommand(ShatteredScrolls instance, ScrollCommand parent) {
        super(instance, parent, "give", "shatteredscrolls.scroll.give", "scroll-give-cmd-help");
        addAlias("g");
        this.instance = instance;
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!showHelpOrNoPerms(sender, label, args)) {
            return true;
        }

        // For most of these we can make assumptions based on the first argument.
        if (args.length < 1) {
            HashMap<String, String> msgArgs = new HashMap<>();
            msgArgs.put("label", label);
            instance.getMessenger().sendMessage(sender, "scroll-give-cmd-help", msgArgs);
            return true;
        }

        if (args.length < 4) {
            HashMap<String, String> msgArgs = new HashMap<>();
            msgArgs.put("label", label);
            msgArgs.put("argc", String.valueOf(args.length));
            msgArgs.put("argx", "4");
            instance.getMessenger().sendErrorMessage(sender, "not-enough-args", msgArgs);
            return true;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if(p == null) {
            HashMap<String, String> msgArgs = new HashMap<>();
            msgArgs.put("label", label);
            msgArgs.put("player", String.valueOf(args[0]));
            instance.getMessenger().sendErrorMessage(sender, "player-not-found", msgArgs);
            return true;
        }

        // Item charge handling.
        Integer charges = parent.parent.getCharges(args, 2, sender);
        if (charges == null) {
            return true;
        }
        // Item count handling
        Integer count = parent.parent.getCount(args, 3, sender);
        if (count == null) {
            return true;
        }
        if (args[1].equalsIgnoreCase("unbound")) {
            p.getInventory()
                .addItem(ScrollItemBuilder.getUnboundScroll(count, charges));
            return true;
        } else if (args[1].equalsIgnoreCase("location")) {
            // Item count handling
            String location = parent.parent.getLocation(args, 4, sender);
            if (location == null) {
                return true;
            }
            p.getInventory()
                .addItem(ScrollItemBuilder.getBoundScroll(count, charges, location));
            return true;
        } else if (args[1].equalsIgnoreCase("position")) {
            Location location = parent.parent.getPosition(args, 4, sender);
            if (location == null) {
                return false;
            }
            p.getInventory()
                .addItem(ScrollItemBuilder.getBoundScroll(count, charges, location));
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(
        CommandSender sender, Command command, String alias, String[] args) {
        if (!hasPerms(sender)) {
            return Collections.emptyList();
        }
        final List<String> completions = new ArrayList<>();
        if (args.length <= 1) {
            LinkedList<String> names = new LinkedList<>();
            for (Player player : instance.getServer().getOnlinePlayers()) {
                String playername = player.getName();
                names.add(playername);
            }
            StringUtil.copyPartialMatches(args[0], names, completions);
            return completions;
        }
        return parent.tabCompleteItem(args, 1, sender);
    }
}
