package org.projpi.shatteredscrolls.items;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.shatteredscrolls.config.ScrollCost;

public class ScrollItem
{
    private final boolean valid;
    private ItemStack stack;
    private ScrollItemNBT.BindingType bindingType;
    private int charges;
    private Location position;
    private String destination;

    public ScrollItem(ItemStack stack)
    {
        this.stack = stack;
        this.valid = ScrollItemNBT.isScroll(stack);
        this.charges = ScrollItemNBT.getCharges(stack);
        if(charges == 0)
        {
            charges = ShatteredScrolls.getInstance().config().getCharges();
        }
        this.bindingType = ScrollItemNBT.getBindingType(stack);
        if(bindingType == ScrollItemNBT.BindingType.POSITION)
        {
            position = ScrollItemNBT.getLocationFromScroll(stack);
        }
        else if(bindingType == ScrollItemNBT.BindingType.LOCATION)
        {
            destination = ScrollItemNBT.getDestinationFromScroll(stack);
        }
    }

    public void update()
    {
        if(bindingType == ScrollItemNBT.BindingType.UNBOUND)
        {
            stack = ScrollItemBuilder.getUnboundScroll(stack.getAmount());
        }
        else if(bindingType == ScrollItemNBT.BindingType.POSITION)
        {
            if(position != null)
            {
                stack = ScrollItemBuilder.getBoundScroll(stack.getAmount(), charges, position);
            }
            else
            {
                position = ScrollItemNBT.getLocationFromScroll(stack);
                stack = ScrollItemBuilder.getBoundScroll(stack.getAmount(), charges, position);
            }
        }
        else if(bindingType == ScrollItemNBT.BindingType.LOCATION)
        {
            if(destination != null && !destination.isEmpty())
            {
                stack = ScrollItemBuilder.getBoundScroll(stack.getAmount(), charges, destination);
            }
            else
            {
                destination = ScrollItemNBT.getDestinationFromScroll(stack);
                stack = ScrollItemBuilder.getBoundScroll(stack.getAmount(), charges, destination);
            }
        }
    }

    public void interact(Player player)
    {
        ShatteredScrolls.getInstance().doCooldown(player);
        Location oldLoc = player.getLocation();
        Location newLoc;
        if(bindingType == ScrollItemNBT.BindingType.UNBOUND)
        {
            stack.setAmount(stack.getAmount() - 1);
            stack = ScrollItemBuilder.getBoundScroll(1, charges, player.getLocation());
            player.getInventory().addItem(stack);
            return;
        }
        if(bindingType == ScrollItemNBT.BindingType.POSITION)
        {
            newLoc = position;
            stack = ScrollItemBuilder.getBoundScroll(1, charges, destination);
        }
        else
        {
            newLoc = ShatteredScrolls.getInstance().getLocation(destination).getLocation();
            stack = ScrollItemBuilder.getBoundScroll(1, charges, destination);
        }
        charges--;
        update();
        if(oldLoc.getWorld() != null)
        {
            oldLoc.getWorld().spawnParticle(Particle.PORTAL, oldLoc, 50, 0, 1, 0, 0.5F);
            oldLoc.getWorld().playSound(oldLoc, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        }
        player.teleport(newLoc);
        ScrollCost cost = ShatteredScrolls.getInstance().config().getCost();
        switch (cost.getType())
        {
            case XP:
                player.setTotalExperience(player.getTotalExperience() - (int) cost.getData());
                break;
            case HUNGER:
                player.setFoodLevel(player.getFoodLevel() - (int) cost.getData());
                break;
            case HEALTH:
                player.setHealth(player.getHealth() - (int) cost.getData());
                break;
            case POTION:
                player.addPotionEffect((PotionEffect) cost.getData());
                break;
        }
        if(newLoc.getWorld() != null)
        {
            newLoc.getWorld().spawnParticle(Particle.PORTAL, oldLoc, 50, 0, 1, 0, 0.5F);
            newLoc.getWorld().playSound(oldLoc, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        }
    }

    public boolean isValid()
    {
        return valid;
    }
}
