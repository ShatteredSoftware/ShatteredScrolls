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
    private boolean refundInvalid;
    private final boolean allowCrafting;
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
        boolean refundInvalid,
        boolean allowCrafting,
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
        this.refundInvalid = refundInvalid;
        this.allowCrafting = allowCrafting;
        this.customModelData = customModelData;
        this.material = material;
        this.cooldown = cooldown;
        this.charges = charges;
        this.cost = cost;
    }

    public static ScrollConfig deserialize(Map<String, Object> map) {
        String unboundName = "&bUnbound Teleportation Scroll";
        unboundName = ChatColor.translateAlternateColorCodes('&',
            getIfValid(map, "scroll-unbound-name", String.class, unboundName));

        String unboundLore = "    &8---=[ &7Description &8]=---\n"
            + "    &7An unbound teleportation scroll.\n"
            + "    &7Right click it to bind it to your location.\n"
            + "    &7It has &f%charges%&7 charges.";
        unboundLore = ChatColor.translateAlternateColorCodes('&',
            getIfValid(map, "scroll-unbound-lore", String.class, unboundLore));

        String boundName = "&bTeleportation Scroll";
        boundName = ChatColor.translateAlternateColorCodes('&',
            getIfValid(map, "scroll-bound-name", String.class, boundName));

        String boundPositionLore = "&8---=[ &7Description &8]=---\n"
                + "&7A bound teleportation scroll.\n"
                + "&7It goes to &f%x% %y% %z%&7 in &f%world%&7.\n"
                + "&7It has &f%charges%&7 charges remaining.";
        boundPositionLore = ChatColor.translateAlternateColorCodes('&',
            getIfValid(map, "scroll-bound-position-lore", String.class, boundPositionLore));


        String boundLocationLore ="&8---=[ &7Description &8]=---\n"
                + "&7A bound teleportation scroll.\n"
                + "&7It goes to &f%destination%&7.\n"
                + "&7It has &f%charges%&7 charges remaining.";
        boundLocationLore = ChatColor.translateAlternateColorCodes('&',
            getIfValid(map, "scroll-bound-location-lore", String.class, boundLocationLore));

        boolean unboundGlow = getIfValid(map, "scroll-unbound-glow", Boolean.class, false);

        boolean boundGlow = getIfValid(map, "scroll-bound-glow", Boolean.class, true);

        boolean refundInvalid = getIfValid(map, "refund-invalid", Boolean.class, true);

        boolean allowCrafting = getIfValid(map, "allow-crafting", Boolean.class, true);

        Material material = ShatteredScrolls.getInstance().getMaterialUtil().matchMaterial(
            getIfValid(map, "scroll-material", String.class, "PAPER"));

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
            refundInvalid,
            allowCrafting,
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

    public boolean doesRefundInvalid() {
        return refundInvalid;
    }
}
