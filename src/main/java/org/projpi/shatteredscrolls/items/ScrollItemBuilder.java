package org.projpi.shatteredscrolls.items;

import java.util.Arrays;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.shatteredscrolls.config.ScrollConfig;
import org.projpi.util.MaterialUtil;

public class ScrollItemBuilder {

    private static ShatteredScrolls instance = ShatteredScrolls.getInstance();
    private static MaterialUtil materialUtil = instance.getMaterialUtil();
    private static ScrollConfig config = instance.config();

    private ScrollItemBuilder() {
    }

    public static ItemStack getUnboundScroll(int count) {
        return getUnboundScroll(count, config.getCharges());
    }

    public static ItemStack getUnboundScroll(int count, int charges) {
        if (charges <= 0 && charges != -1024) {
            return new ItemStack(Material.AIR);
        }

        ItemStack stack = new ItemStack(config.getMaterial(), count);
        stack = ScrollItemNBT.setUnbound(stack);
        stack = ScrollItemNBT.setCharges(stack, charges);

        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            String chargeAmount = charges != -1024 ? String.valueOf(charges) : "\u221E"; //∞
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config
                .getUnboundName()
                .replaceAll("%charges%", chargeAmount)));
            String lore = config.getUnboundLore()
                .replaceAll("%charges%", chargeAmount); //∞
            meta.setLore(Arrays.asList(lore.split("\n")));
            stack.setItemMeta(meta);
            if(config.doesUnboundGlow()) {
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                if(config.getMaterial() != materialUtil.matchMaterial("arrow")) {
                    meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                }
                else
                {
                    meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
                }
            }
            stack = materialUtil.setCustomModelData(stack, config.getCustomModelData());
        }
        return stack;
    }

    public static ItemStack getBoundScroll(int count, Location destination) {
        return getBoundScroll(count, config.getCharges(), destination);
    }

    public static ItemStack getBoundScroll(int count, int charges, Location destination) {
        if (charges <= 0 && charges != -1024) {
            return new ItemStack(Material.AIR);
        }

        ItemStack stack = new ItemStack(config.getMaterial(), count);

        stack = ScrollItemNBT.setBound(stack);
        stack = ScrollItemNBT.bindTo(stack, destination);
        stack = ScrollItemNBT.setCharges(stack, charges);

        if (stack.hasItemMeta()) {
            ItemMeta meta = stack.getItemMeta();
            String chargeAmount = charges != -1024 ? String.valueOf(charges) : "\u221E"; //∞
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config
                .getBoundName()
                .replaceAll("\r", "")
                .replaceAll("%x%", String.valueOf(destination.getBlockX()))
                .replaceAll("%y%", String.valueOf(destination.getBlockY()))
                .replaceAll("%z%", String.valueOf(destination.getBlockZ()))
                .replaceAll("%charges%", chargeAmount)));
            String lore =
                config
                    .getBoundPositionLore()
                    .replaceAll("\r", "")
                    .replaceAll("%x%", String.valueOf(destination.getBlockX()))
                    .replaceAll("%y%", String.valueOf(destination.getBlockY()))
                    .replaceAll("%z%", String.valueOf(destination.getBlockZ()))
                    .replaceAll("%charges%", chargeAmount);
            if (destination.getWorld() != null) {
                lore = lore.replaceAll("%world%", destination.getWorld().getName());
            }
            meta.setLore(Arrays.asList(lore.split("\n")));
            if(config.doesBoundGlow()) {
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                if(config.getMaterial() != materialUtil.matchMaterial("arrow")) {
                    meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                }
                else
                {
                    meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
                }
            }
            stack.setItemMeta(meta);
            stack = materialUtil.setCustomModelData(stack, config.getCustomModelData());
        }
        return stack;
    }

    public static ItemStack getBoundScroll(int count, String destination) {
        return getBoundScroll(count, config.getCharges(), destination);
    }

    public static ItemStack getBoundScroll(int count, int charges, String destination) {
        if (charges <= 0 && charges != -1024) {
            return new ItemStack(Material.AIR);
        }

        ItemStack stack = new ItemStack(config.getMaterial(), count);

        stack = ScrollItemNBT.setBound(stack);
        stack = ScrollItemNBT.bindTo(stack, destination);
        stack = ScrollItemNBT.setCharges(stack, charges);

        if (stack.hasItemMeta()) {
            ItemMeta meta = stack.getItemMeta();
            String chargeAmount = charges != -1024 ? String.valueOf(charges) : "\u221E"; //∞
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config
                .getBoundName()
                .replaceAll("%destination%", instance.getLocation(destination).getName())
                .replaceAll("%charges%", chargeAmount)));
            String lore =
                config
                    .getBoundLocationLore()
                    .replaceAll("%destination%", instance.getLocation(destination).getName())
                    .replaceAll("%charges%", chargeAmount);
            meta.setLore(Arrays.asList(lore.split("\n")));
            if(config.doesBoundGlow()) {
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                if(config.getMaterial() != materialUtil.matchMaterial("arrow")) {
                    meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                }
                else
                {
                    meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
                }
            }
            stack.setItemMeta(meta);
            stack = materialUtil.setCustomModelData(stack, config.getCustomModelData());
        }
        return stack;
    }
}
