package com.github.shatteredsuite.scrolls.items;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import com.github.shatteredsuite.scrolls.ShatteredScrolls;
import com.github.shatteredsuite.scrolls.config.ScrollCost;
import com.github.shatteredsuite.scrolls.config.ScrollLocation;

public class ScrollItem {

    private final boolean valid;
    private ItemStack stack;
    private ScrollItemNBT.BindingType bindingType;
    private int charges;
    private Location position;
    private String destination;

    public ScrollItem(ItemStack stack) {
        this.stack = stack;
        this.valid = ScrollItemNBT.isScroll(stack);
        this.charges = ScrollItemNBT.getCharges(stack);
        if (charges == 0) {
            charges = ShatteredScrolls.getInstance().config().getCharges();
        }
        this.bindingType = ScrollItemNBT.getBindingType(stack);
        if (bindingType == ScrollItemNBT.BindingType.POSITION) {
            position = ScrollItemNBT.getLocationFromScroll(stack);
        } else if (bindingType == ScrollItemNBT.BindingType.LOCATION) {
            destination = ScrollItemNBT.getDestinationFromScroll(stack);
        }
    }

    public void interact(Player player) {
        ShatteredScrolls.getInstance().doCooldown(player);
        Location oldLoc = player.getLocation();
        Location newLoc;
        if (bindingType == ScrollItemNBT.BindingType.UNBOUND) {
            getNextItem(() -> ScrollItemBuilder.getBoundScrollPosition(1, charges, player.getLocation()),
                player);
            return;
        }
        // Consume a charge.
        if(charges != -1024) {
            charges--;
        }
        // Update the item.
        if (bindingType == ScrollItemNBT.BindingType.POSITION) {
            newLoc = ScrollItemNBT.getLocationFromScroll(stack);
            getNextItem(() -> ScrollItemBuilder.getBoundScrollPosition(1, charges, newLoc), player);
        } else {
            ScrollLocation dest = ShatteredScrolls.getInstance().getLocation(destination);
            if(dest == null) {
                if(ShatteredScrolls.getInstance().config().doesRefundInvalid()) {
                    ShatteredScrolls.getInstance().getMessenger().sendImportantMessage(player, "refunded-location");
                    getNextItem(() -> ScrollItemBuilder.getUnboundScroll(1, charges + 1), player);
                }
                else {
                    ShatteredScrolls.getInstance().getMessenger().sendImportantMessage(player, "bound-invalid-location");
                    getNextItem(() -> ScrollItemBuilder.getBoundScrollLocation(1, charges + 1, destination), player);
                }
                return;
            }
            newLoc = dest.getLocation();
            getNextItem(() -> ScrollItemBuilder.getBoundScrollLocation(1, charges, destination), player);
        }

        // Do teleportation
        doTeleport(oldLoc, newLoc, player);
    }

    private void doTeleport(Location oldLocation, Location newLocation, Player player) {
        if (oldLocation.getWorld() != null) {
            oldLocation.getWorld().spawnParticle(Particle.PORTAL, oldLocation, 50, 0, 1, 0, 0.5F);
            oldLocation.getWorld().playSound(oldLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        }
        player.teleport(newLocation);
        ScrollCost cost = ShatteredScrolls.getInstance().config().getCost();
        switch (cost.getType()) {
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
        if (newLocation.getWorld() != null) {
            newLocation.getWorld().spawnParticle(Particle.PORTAL, newLocation, 50, 0, 1, 0, 0.5F);
            newLocation.getWorld().playSound(newLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        }
    }

    private void getNextItem(ItemProvider provider, Player player) {
        ItemStack toTake = new ItemStack(stack);
        toTake.setAmount(1);
        player.getInventory().removeItem(toTake);
        ItemStack newStack = provider.getItem();
        assert newStack != null;
        player.getInventory().addItem(newStack);
    }

    public boolean isValid() {
        return valid;
    }
}
