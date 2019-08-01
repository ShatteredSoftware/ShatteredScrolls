package org.projpi.shatteredscrolls.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.util.commands.WrappedCommand;

import java.util.*;

public class ScrollCommand extends WrappedCommand
{
    final BaseCommand parent;
    private final ShatteredScrolls instance;

    public ScrollCommand(ShatteredScrolls instance, BaseCommand parent)
    {
        super(instance, parent, "scroll", "shatteredscrolls.scroll", "scroll-cmd-help");
        this.parent = parent;
        this.instance = instance;
        addAlias("s");
        registerSubcommand(new ScrollGiveCommand(instance, this));
        registerSubcommand(new ScrollGetCommand(instance, this));
    }

    private static final List<String> options = Arrays.asList("unbound", "location", "position");
    private static final List<String> chargeOptions = Arrays.asList("1", "3", "5");
    private static final List<String> countOptions = Arrays.asList("1", "64");

    List<String> tabCompleteItem(String[] args, int startIndex, CommandSender sender)
    {
        final List<String> completions = new ArrayList<>();
        if (args.length <= startIndex + 1)
        {
            StringUtil.copyPartialMatches(args[startIndex], options, completions);
            Collections.sort(completions);
            return completions;
        }
        if(args.length == startIndex + 2)
        {
            StringUtil.copyPartialMatches(args[startIndex + 1], chargeOptions, completions);
            Collections.sort(completions);
            return completions;
        }
        else if(args.length == startIndex + 3)
        {
            StringUtil.copyPartialMatches(args[startIndex + 2], countOptions, completions);
            Collections.sort(completions);
            return completions;
        }
        else if(args[startIndex].equalsIgnoreCase("location"))
        {
            if(args.length == startIndex + 4)
            {
                return parent.tabCompleteLocations(args, startIndex + 3, sender);
            }
        }
        else if(args[startIndex].equalsIgnoreCase("position"))
        {
            return parent.tabCompletePosition(args, startIndex, sender, null);
        }
        return Collections.emptyList();
    }
}
