package org.projpi.shatteredscrolls.items;

import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.projpi.shatteredscrolls.ShatteredScrolls;
import org.projpi.shatteredscrolls.config.ScrollConfig;
import org.projpi.util.MaterialUtil;
import org.projpi.util.nms.NBTUtil;

public class ScrollItemNBT {
  private static ShatteredScrolls instance = ShatteredScrolls.getInstance();
  private static NBTUtil nbtUtil = instance.getNbtUtil();
  private static MaterialUtil materialUtil = instance.getMaterialUtil();
  private static ScrollConfig config = instance.config();

  private ScrollItemNBT() {}

  public static Location getLocationFromScroll(ItemStack stack) {
    String world = nbtUtil.getString(stack, "shatteredscrolls_dest_world");
    double x = nbtUtil.getDouble(stack, "shatteredscrolls_dest_x");
    double y = nbtUtil.getDouble(stack, "shatteredscrolls_dest_y");
    double z = nbtUtil.getDouble(stack, "shatteredscrolls_dest_z");
    float yaw = nbtUtil.getFloat(stack, "shatteredscrolls_dest_yaw");
    float pitch = nbtUtil.getFloat(stack, "shatteredscrolls_dest_pitch");
    return new Location(instance.getServer().getWorld(UUID.fromString(world)), x, y, z, yaw, pitch);
  }

  public static int getCharges(ItemStack stack) {
    return nbtUtil.getInt(stack, "shatteredscrolls_charges");
  }

  public static ItemStack setUnbound(ItemStack stack) {
    return nbtUtil.setByte(stack, "shatteredscrolls_bound", (byte) 0);
  }

  public static ItemStack setBound(ItemStack stack) {
    return nbtUtil.setByte(stack, "shatteredscrolls_bound", (byte) 1);
  }

  public static BindingType getBindingType(ItemStack stack) {
    if (isBound(stack)) {
      return nbtUtil.hasString(stack, "shatteredscrolls_destination")
          ? BindingType.LOCATION
          : BindingType.POSITION;
    }
    return BindingType.UNBOUND;
  }

  private static boolean isBound(ItemStack stack) {
    if (!nbtUtil.hasByte(stack, "shatteredscrolls_bound")) {
      return false;
    }
    return nbtUtil.getByte(stack, "shatteredscrolls_bound") != 0;
  }

  public static ItemStack setCharges(ItemStack stack, int charges) {
    return nbtUtil.setInt(stack, "shatteredscrolls_charges", charges);
  }

  public static ItemStack bindTo(ItemStack stack, Location location) {
    if (location.getWorld() != null) {
      stack =
          nbtUtil.setString(
              stack, "shatteredscrolls_dest_world", location.getWorld().getUID().toString());
    }
    stack = nbtUtil.setDouble(stack, "shatteredscrolls_dest_x", location.getX());
    stack = nbtUtil.setDouble(stack, "shatteredscrolls_dest_y", location.getY());
    stack = nbtUtil.setDouble(stack, "shatteredscrolls_dest_z", location.getZ());
    stack = nbtUtil.setDouble(stack, "shatteredscrolls_dest_yaw", location.getYaw());
    stack = nbtUtil.setDouble(stack, "shatteredscrolls_dest_pitch", location.getPitch());
    return stack;
  }

  public static ItemStack bindTo(ItemStack stack, String destination) {
    if (!instance.hasLocation(destination)) {
      throw new IllegalArgumentException("Invalid destination!");
    }
    stack = nbtUtil.setByte(stack, "shatteredscrolls_bound", (byte) 1);
    stack = nbtUtil.setString(stack, "shatteredscrolls_destination", destination);
    return stack;
  }

  public static String getDestinationFromScroll(ItemStack stack) {
    String destination = nbtUtil.getString(stack, "shatteredscrolls_destination");
    return destination;
  }

  public static boolean isScroll(ItemStack stack) {
    return nbtUtil.hasByte(stack, "shatteredscrolls_bound");
  }

  public enum BindingType {
    LOCATION,
    POSITION,
    UNBOUND
  }
}
