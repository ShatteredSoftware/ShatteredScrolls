package org.projpi.shatteredscrolls.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.projpi.shatteredscrolls.ShatteredScrolls;

@SerializableAs("ScrollConfig")
public class ScrollConfig implements Cloneable, ConfigurationSerializable {

    private String unboundName;
    private String unboundLore;
    private String boundName;
    private String boundLocationLore;
    private String boundPositionLore;
    private boolean unboundGlow;
    private boolean boundGlow;
    private int customModelData;
    private Material material;
    private int cooldown;
    private int charges;
    private ScrollCost cost;

    public ScrollConfig(
        String unboundName,
        String unboundLore,
        String boundName,
        String boundLocationLore,
        String boundPositionLore,
        boolean unboundGlow,
        boolean boundGlow,
        int customModelData,
        Material material,
        int cooldown,
        int charges,
        ScrollCost cost) {
        this.unboundName = unboundName;
        this.unboundLore = unboundLore;
        this.boundName = boundName;
        this.boundLocationLore = boundLocationLore;
        this.boundPositionLore = boundPositionLore;
        this.unboundGlow = unboundGlow;
        this.boundGlow = boundGlow;
        this.customModelData = customModelData;
        this.material = material;
        this.cooldown = cooldown;
        this.charges = charges;
        this.cost = cost;
    }

    public static ScrollConfig deserialize(Map<String, Object> map) {
        String unboundName = "§bUnbound Teleportation Scroll";
        if (map.containsKey("scroll-unbound-name")
            && map.get("scroll-unbound-name") instanceof String
            && !((String) map.get("scroll-unbound-name")).isEmpty()) {
            unboundName =
                ChatColor
                    .translateAlternateColorCodes('&', (String) map.get("scroll-unbound-name"));
        }

        String unboundLore = "§7An unbound teleportation scroll." + "\n§7Right click to bind.";
        if (map.containsKey("scroll-unbound-lore")
            && map.get("scroll-unbound-lore") instanceof String
            && !((String) map.get("scroll-unbound-lore")).isEmpty()) {
            unboundLore =
                ChatColor
                    .translateAlternateColorCodes('&', (String) map.get("scroll-unbound-lore"));
        }

        String boundName = "§bTeleportation Scroll";
        if (map.containsKey("scroll-bound-name")
            && map.get("scroll-bound-name") instanceof String
            && !((String) map.get("scroll-bound-name")).isEmpty()) {
            boundName =
                ChatColor.translateAlternateColorCodes('&', (String) map.get("scroll-bound-name"));
        }

        String boundPositionLore =
            "§7A bound teleportation scroll."
                + "\n§7It goes to §f%x% %y% %z%§7 in §f%world%§7."
                + "\n"
                + "\n§It has §f%charges%§7 left.";
        if (map.containsKey("scroll-bound-position-lore")
            && map.get("scroll-bound-position-lore") instanceof String
            && !((String) map.get("scroll-bound-position-lore")).isEmpty()) {
            boundPositionLore =
                ChatColor.translateAlternateColorCodes(
                    '&', (String) map.get("scroll-bound-position-lore"));
        }

        String boundLocationLore =
            "§7A bound teleportation scroll."
                + "\n§7It goes to §f%location%§7."
                + "\n"
                + "\n§It has §f%charges%§7 left.";
        if (map.containsKey("scroll-bound-location-lore")
            && map.get("scroll-bound-location-lore") instanceof String
            && !((String) map.get("scroll-bound-location-lore")).isEmpty()) {
            boundLocationLore =
                ChatColor.translateAlternateColorCodes(
                    '&', (String) map.get("scroll-bound-location-lore"));
        }

        boolean unboundGlow = false;
        if (map.containsKey("scroll-unbound-glow")
            && map.get("scroll-unbound-glow") instanceof Boolean) {
            unboundGlow = (boolean) map.get("scroll-unbound-glow");
        }

        boolean boundGlow = true;
        if (map.containsKey("scroll-bound-glow") && map
            .get("scroll-bound-glow") instanceof Boolean) {
            boundGlow = (boolean) map.get("scroll-bound-glow");
        }

        Material material = ShatteredScrolls.getInstance().getMaterialUtil().matchMaterial("PAPER");
        if (map.containsKey("scroll-material") && map.get("scroll-material") instanceof String) {
            material = ShatteredScrolls.getInstance().getMaterialUtil()
                .matchMaterial("scroll-material");
            if (material == null) {
                material = ShatteredScrolls.getInstance().getMaterialUtil().matchMaterial("PAPER");
            }
        }

        int cooldown = getIfValid(map, "cooldown", Integer.class, 5000);

        int charges = getIfValid(map, "charges", Integer.class, 5);

        int customModelData = getIfValid(map, "model", Integer.class, 0);

        ScrollCost cost =
            getIfValid(
                map,
                "cost",
                ScrollCost.class,
                new ScrollCost(
                    ScrollCost.CostType.POTION,
                    new PotionEffect(PotionEffectType.BLINDNESS, 5, 0)));

        return new ScrollConfig(
            unboundName,
            unboundLore,
            boundName,
            boundLocationLore,
            boundPositionLore,
            unboundGlow,
            boundGlow,
            customModelData,
            material,
            cooldown,
            charges,
            cost);
    }

    private static <T> T getIfValid(Map<String, Object> map, String key, Class<T> clazz, T def) {
        if (!map.containsKey(key)) {
            return def;
        }
        Object obj = map.get(key);
        if (obj == null || !obj.getClass().equals(clazz)) {
            return def;
        }
        return clazz.cast(map.get(key));
    }

    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("scroll-unbound-name", unboundName);
        result.put("scroll-bound-name", boundName);
        result.put("scroll-material", material.getKey().getKey());
        result.put("cooldown", cooldown);
        result.put("cost", cost);
        return result;
    }

    public String getUnboundName() {
        return unboundName;
    }

    public String getBoundName() {
        return boundName;
    }

    public Material getMaterial() {
        return material;
    }

    public int getCooldown() {
        return cooldown;
    }

    public ScrollCost getCost() {
        return cost;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public int getCharges() {
        return charges;
    }

    public String getBoundLocationLore() {
        return boundLocationLore;
    }

    public String getBoundPositionLore() {
        return boundPositionLore;
    }

    public boolean doesUnboundGlow() {
        return unboundGlow;
    }

    public boolean doesBoundGlow() {
        return boundGlow;
    }

    public String getUnboundLore() {
        return unboundLore;
    }
}
