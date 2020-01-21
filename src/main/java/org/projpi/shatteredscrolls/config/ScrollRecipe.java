package org.projpi.shatteredscrolls.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.projpi.shatteredscrolls.ShatteredScrolls;

@SerializableAs("ScrollRecipe")
public class ScrollRecipe implements ConfigurationSerializable, Cloneable {
    private List<String> lines;
    private HashMap<String, String> items;
    private HashMap<String, Material> mappings;
    private int count;

    public ScrollRecipe(List<String> lines, HashMap<String, String> items,
        int count) {
        this.lines = lines;
        this.items = items;
        this.mappings = new HashMap<>();
        for(Map.Entry<String, String> entry : items.entrySet()) {
            mappings.put(entry.getKey(), ShatteredScrolls.getInstance().getMaterialUtil().matchMaterial(entry.getValue()));
        }
        this.count = count;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("recipe", lines);
        map.put("mappings", items);
        map.put("count", count);
        return map;
    }

    public static ScrollRecipe deserialize(Map<String, Object> map) {
        String recipeType = "shaped";
        ArrayList<String> lines;
        HashMap<String, String> items;
        int count;

        recipeType = ScrollConfig.getIfValid(map, "type", String.class, recipeType);

        // This line is a warning. There is no way around this. If it doesn't match the class we
        // want, getIfValid will warn the user and use the default, so this shouldn't be reached.
        lines = (ArrayList<String>) ScrollConfig.getIfValid(map, "recipe", ArrayList.class, null);
        if (lines == null) {
            lines = new ArrayList<>();
            lines.set(0, " E ");
            lines.set(1, "EPE");
            lines.set(2, " E ");
        }

        // This line is a warning. There is no way around this. If it doesn't match the class we
        // want, getIfValid will warn the user and use the default, so this shouldn't be reached.
        items = (LinkedHashMap<String, String>) ScrollConfig.getIfValid(map, "mapping", LinkedHashMap.class, null);
        if (items == null) {
            items = new HashMap<>();
            items.put("E", "ENDER_PEARL");
            items.put("P", "PAPER");
        }

        count = ScrollConfig.getIfValid(map, "amount", Integer.class, 1);
        return new ScrollRecipe(lines, items, count);
    }

    public List<String> getLines() {
        return lines;
    }

    public HashMap<String, Material> getMappings() {
        return mappings;
    }

    public int getCount() {
        return count;
    }
}
