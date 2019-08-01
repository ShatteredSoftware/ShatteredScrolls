package org.projpi.shatteredscrolls.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.shatteredscrolls.items.ScrollItem;
import org.projpi.util.nms.NBTUtil;

public class InteractListener implements Listener
{
    private transient final ShatteredScrolls instance;
    private transient final NBTUtil nbtUtil;

    public InteractListener(ShatteredScrolls instance)
    {
        this.instance = instance;
        this.nbtUtil = instance.getNbtUtil();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        // Ignore non-right clicks.
        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
        {
            return;
        }
        ItemStack stack = event.getItem();
        if(stack == null)
        {
            return;
        }
        // Ignore sneaking.
        if(event.getPlayer().isSneaking())
        {
            return;
        }
        if(!event.getPlayer().hasPermission("shatteredScrolls.use"))
        {
            return;
        }
        if(!instance.canTeleport(event.getPlayer()))
        {
            return;
        }
        Player player = event.getPlayer();
        ScrollItem item = new ScrollItem(stack);
        if(!item.isValid())
        {
            return;
        }
        item.interact(player);
    }

}
