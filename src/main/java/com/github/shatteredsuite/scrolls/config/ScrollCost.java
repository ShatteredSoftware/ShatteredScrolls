package com.github.shatteredsuite.scrolls.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SerializableAs("ScrollCost")
public class ScrollCost implements ConfigurationSerializable {

    private CostType type;
    private Object data;

    public ScrollCost(CostType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public static ScrollCost deserialize(Map map) {
        CostType type;
        Object data = null;
        if (!map.containsKey("type") || !(map.get("type") instanceof String)) {
            type = CostType.XP;
        } else {
            type = CostType.fromString((String) map.get("type"));
            if (type == null) {
                type = CostType.XP;
            }
        }
        switch (type) {
            case XP:
            case HUNGER:
            case HEALTH:
                if (map.containsKey("data") && map.get("data") instanceof Integer) {
                    data = map.get("data");
                } else {
                    data = 5;
                }
                break;
            case POTION:
                if (map.containsKey("data") && map.get("data") instanceof PotionEffect) {
                    data = map.get("data");
                } else {
                    data = new PotionEffect(PotionEffectType.BLINDNESS, 5, 0);
                }
                break;
        }
        return new ScrollCost(type, data);
    }

    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("type", type.name());
        result.put("data", data);
        return result;
    }

    public CostType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public enum CostType {
        XP("XP"),
        HUNGER("HUNGER"),
        HEALTH("HEALTH"),
        POTION("POTION");

        public final String value;

        CostType(String value) {
            this.value = value;
        }

        public static CostType fromString(String value) {
            for (CostType type : CostType.values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            return null;
        }
    }
}
