package org.projpi.shatteredscrolls.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.util.commands.WrappedCommand;

public class ScrollGiveCommand extends WrappedCommand {

  private final ShatteredScrolls instance;
  private final ScrollCommand parent;

  public ScrollGiveCommand(ShatteredScrolls instance, ScrollCommand parent) {
    super(instance, parent, "edit", "shatteredscrolls.scroll.edit", "scroll-edit-cmd-help");
    addAlias("g");
    this.instance = instance;
    this.parent = parent;
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
