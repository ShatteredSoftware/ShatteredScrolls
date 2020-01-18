package org.projpi.shatteredscrolls.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.shatteredscrolls.items.ScrollItemBuilder;
import org.projpi.util.commands.WrappedCommand;

public class ScrollGetCommand extends WrappedCommand {
    private final ShatteredScrolls instance;
    private final ScrollCommand parent;

    public ScrollGetCommand(ShatteredScrolls instance, ScrollCommand parent) {
        super(instance, parent, "get", "shatteredscrolls.scroll.get", "scroll-get-cmd-help");
        this.instance = instance;
        this.parent = parent;
        this.addAlias("i");
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!showHelpOrNoPerms(sender, label, args)) {
            return true;
        }

        if (!(sender instanceof Player)) {
            instance.getMessenger().sendMessage(sender, "inventory-required");
            return true;
        }

        // For most of these we can make assumptions based on the first argument.
        if (args.length < 1) {
            HashMap<String, String> msgArgs = new HashMap<>();
            msgArgs.put("label", label);
            instance.getMessenger().sendMessage(sender, "scroll-get-cmd-help", msgArgs);
            return true;
        }

        if (args.length < 3) {
            HashMap<String, String> msgArgs = new HashMap<>();
            msgArgs.put("label", label);
            msgArgs.put("argc", String.valueOf(args.length));
            msgArgs.put("argx", "3");
            instance.getMessenger().sendErrorMessage(sender, "not-enough-args", msgArgs);
            return true;
        }

        // Item charge handling.
        Integer charges = parent.parent.getCharges(args, 1, sender);
        if (charges == null) {
            return true;
        }
        // Item count handling
        Integer count = parent.parent.getCount(args, 2, sender);
        if (count == null) {
            return true;
        }
        if (args[0].equalsIgnoreCase("unbound")) {
            ((InventoryHolder) sender)
                .getInventory()
                .addItem(ScrollItemBuilder.getUnboundScroll(count, charges));
            return true;
        } else if (args[0].equalsIgnoreCase("location")) {
            // Item count handling
            String location = parent.parent.getLocation(args, 3, sender);
            if (location == null) {
                return true;
            }
            ((InventoryHolder) sender)
                .getInventory()
                .addItem(ScrollItemBuilder.getBoundScrollLocation(count, charges, location));
            return true;
        } else if (args[0].equalsIgnoreCase("position")) {
            Location location = parent.parent.getPosition(args, 4, sender);
            if (location == null) {
                return false;
            }
            ((Player) sender)
                .getInventory()
                .addItem(ScrollItemBuilder.getBoundScrollPosition(count, charges, location));
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
        return parent.tabCompleteItem(args, 0, sender);
    }
}
