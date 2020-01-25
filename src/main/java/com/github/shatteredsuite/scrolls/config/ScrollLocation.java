package com.github.shatteredsuite.scrolls.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import com.github.shatteredsuite.utilities.StringUtil;

@SerializableAs("ScrollLocation")
public class ScrollLocation implements ConfigurationSerializable, Cloneable {

    public String name;
    public Location location;
    private String id;

    public ScrollLocation(String id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public static ScrollLocation deserialize(Map<String, Object> map) {
        if (!map.containsKey("id") || !(map.get("id") instanceof String)) {
            throw new IllegalArgumentException("Location ID must exist and be a string.");
        }
        String id = (String) map.get("id");
        if (StringUtil.isEmptyOrNull(id)) {
            throw new IllegalArgumentException("Location ID cannot be empty.");
        }
        if (!map.containsKey("name") || !(map.get("name") instanceof String)) {
            throw new IllegalArgumentException("Location name must exist and be a string.");
        }
        String name = (String) map.get("name");
        if (StringUtil.isEmptyOrNull(id)) {
            throw new IllegalArgumentException("Location Name cannot be empty.");
        }
        if (!map.containsKey("location") || !(map.get("location") instanceof Location)) {
            throw new IllegalArgumentException("Location location must exist and be a Location.");
        }
        Location location = (Location) map.get("location");
        return new ScrollLocation(id, name, location);
    }

    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("location", location);
        return map;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        String res = name + " (" + id + ") @ " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
        if(location.getWorld() != null) {
            res += " in world " + location.getWorld().getName();
        }
        return res;
    }
}
