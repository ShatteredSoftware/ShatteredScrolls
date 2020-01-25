package com.github.shatteredsuite.scrolls.recipes;

import com.github.shatteredsuite.scrolls.items.ScrollItemBuilder;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import com.github.shatteredsuite.scrolls.ShatteredScrolls;

public class RecipeHandler {

    public static void addRecipe(ShatteredScrolls plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "teleport_scroll");
        ItemStack unboundScroll = ScrollItemBuilder.getUnboundScroll(plugin.config().getRecipe().getCount());

        ShapedRecipe recipe = new ShapedRecipe(key, unboundScroll);
        recipe.shape(plugin.config().getRecipe().getLines().get(0),
            plugin.config().getRecipe().getLines().get(1),
            plugin.config().getRecipe().getLines().get(2));
        for (Map.Entry<String, Material> entry : plugin.config().getRecipe().getMappings().entrySet()) {
            recipe.setIngredient(entry.getKey().charAt(0), entry.getValue());
        }

        plugin.getServer().addRecipe(recipe);
    }
}
