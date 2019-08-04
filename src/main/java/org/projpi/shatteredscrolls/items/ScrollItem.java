package org.projpi.shatteredscrolls.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.shatteredscrolls.config.ScrollCost;

import java.util.HashMap;

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

    public void interact(Player player)
    {
        ShatteredScrolls.getInstance().doCooldown(player);
        Location oldLoc = player.getLocation();
        Location newLoc;
        if(bindingType == ScrollItemNBT.BindingType.UNBOUND)
        {
            getNextItem(() -> ScrollItemBuilder.getBoundScroll(1, charges, player.getLocation()), player);
            return;
        }
        // Consume a charge.
        charges--;
        // Update the item.
        if(bindingType == ScrollItemNBT.BindingType.POSITION)
        {
            newLoc = ScrollItemNBT.getLocationFromScroll(stack);
            getNextItem(() -> ScrollItemBuilder.getBoundScroll(1, charges, newLoc), player);
        }
        else
        {
            newLoc = ShatteredScrolls.getInstance().getLocation(destination).getLocation();
            getNextItem(() -> ScrollItemBuilder.getBoundScroll(1, charges, destination), player);
        }

        // Do teleportation
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

    private void getNextItem(ItemProvider provider, Player player)
    {
        stack.setAmount(stack.getAmount() - 1);
        ItemStack newStack = provider.getItem();
        System.out.println(newStack);
        addItem(player, newStack);
    }

    private void addItem(Player player, ItemStack itemStack)
    {
        player.getInventory().addItem(itemStack);
    }

    public boolean isValid()
    {
        return valid;
    }
}
