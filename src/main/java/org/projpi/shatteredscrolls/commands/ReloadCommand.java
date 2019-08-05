package org.projpi.shatteredscrolls.commands;

import org.bukkit.command.CommandSender;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.util.commands.WrappedCommand;

public class ReloadCommand extends WrappedCommand {

    ShatteredScrolls instance;

    public ReloadCommand(ShatteredScrolls instance, BaseCommand parent) {
        super(instance, parent, "reload", "shatteredscrolls.reload", "null");
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!showHelpOrNoPerms(sender, label, args)) {
            return true;
        }

        instance.getMessenger().sendImportantMessage(sender, "reloading");
        instance.reload();
        instance.getMessenger().sendImportantMessage(sender, "reloaded");
        return true;
    }
}
