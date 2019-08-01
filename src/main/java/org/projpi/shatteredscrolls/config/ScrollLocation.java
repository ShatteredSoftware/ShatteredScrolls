package org.projpi.shatteredscrolls.config;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.projpi.util.StringUtil;

import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("ScrollLocation")
public class ScrollLocation implements ConfigurationSerializable, Cloneable
{
    private String id;
    public String name;
    public Location location;

    public ScrollLocation(String id, String name, Location location)
    {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    @Override
    public Map<String, Object> serialize()
    {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("location", location);
        return map;
    }

    public static ScrollLocation deserialize(Map<String, Object> map)
    {
        if(!map.containsKey("id") || !(map.get("id") instanceof String))
        {
            throw new IllegalArgumentException("Location ID must exist and be a string.");
        }
        String id = (String) map.get("id");
        if(StringUtil.isEmptyOrNull(id))
        {
            throw new IllegalArgumentException("Location ID cannot be empty.");
        }
        if(!map.containsKey("name") || !(map.get("name") instanceof String))
        {
            throw new IllegalArgumentException("Location name must exist and be a string.");
        }
        String name = (String) map.get("name");
        if(StringUtil.isEmptyOrNull(id))
        {
            throw new IllegalArgumentException("Location Name cannot be empty.");
        }
        if(!map.containsKey("location") || !(map.get("location") instanceof Location))
        {
            throw new IllegalArgumentException("Location location must exist and be a Location.");
        }
        Location location = (Location) map.get("location");
        return new ScrollLocation(id, name, location);
    }

    public String getName()
    {
        return name;
    }

    public Location getLocation()
    {
        return location;
    }

    public String getId()
    {
        return id;
    }
}
