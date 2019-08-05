package org.projpi.shatteredscrolls.recipes;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.shatteredscrolls.items.ScrollItemBuilder;

public class RecipeHandler {

    public static void addRecipe(ShatteredScrolls plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "teleport_scroll");
        ItemStack unboundScroll = ScrollItemBuilder.getUnboundScroll(1);

        ShapedRecipe recipe = new ShapedRecipe(key, unboundScroll);
        recipe.shape(" E ", "EPE", " E ");
        recipe.setIngredient('E', plugin.getMaterialUtil().matchMaterial("ENDER_PEARL"));
        recipe.setIngredient('P', plugin.getMaterialUtil().matchMaterial("PAPER"));

        plugin.getServer().addRecipe(recipe);
    }
}
