//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.misc;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import java.math.*;

public class MathUtil
{
    private static Minecraft mc;
    public static Random rnd;
    
    public static int getRandom(final int min, final int max) {
        return MathUtil.rnd.nextInt(max - min + 1) + min;
    }
    
    public static ArrayList moveItemToFirst(final ArrayList list, final int index) {
        final ArrayList newlist = new ArrayList();
        newlist.add(list.get(index));
        for (int i = 0; i < list.size(); ++i) {
            if (i != index) {
                newlist.add(list.get(index));
            }
        }
        return new ArrayList(list);
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static float square(final float v1) {
        return v1 * v1;
    }
    
    public static double calculateDistanceWithPartialTicks(final double n, final double n2, final float renderPartialTicks) {
        return n2 + (n - n2) * MathUtil.mc.getRenderPartialTicks();
    }
    
    public static Vec3d interpolateEntityClose(final Entity entity, final float renderPartialTicks) {
        return new Vec3d(calculateDistanceWithPartialTicks(entity.posX, entity.lastTickPosX, renderPartialTicks) - MathUtil.mc.getRenderManager().renderPosX, calculateDistanceWithPartialTicks(entity.posY, entity.lastTickPosY, renderPartialTicks) - MathUtil.mc.getRenderManager().renderPosY, calculateDistanceWithPartialTicks(entity.posZ, entity.lastTickPosZ, renderPartialTicks) - MathUtil.mc.getRenderManager().renderPosZ);
    }
    
    public static double radToDeg(final double rad) {
        return rad * 57.295780181884766;
    }
    
    public static double degToRad(final double deg) {
        return deg * 0.01745329238474369;
    }
    
    public static Vec3d direction(final float yaw) {
        return new Vec3d(Math.cos(degToRad(yaw + 90.0f)), 0.0, Math.sin(degToRad(yaw + 90.0f)));
    }
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            return value;
        }
        return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static float clamp(float val, final float min, final float max) {
        if (val <= min) {
            val = min;
        }
        if (val >= max) {
            val = max;
        }
        return val;
    }
    
    public static float wrap(float val) {
        val %= 360.0f;
        if (val >= 180.0f) {
            val -= 360.0f;
        }
        if (val < -180.0f) {
            val += 360.0f;
        }
        return val;
    }
    
    public static double map(double value, final double a, final double b, final double c, final double d) {
        value = (value - a) / (b - a);
        return c + value * (d - c);
    }
    
    public static double linear(final double from, final double to, final double incline) {
        return (from < to - incline) ? (from + incline) : ((from > to + incline) ? (from - incline) : to);
    }
    
    public static double parabolic(final double from, final double to, final double incline) {
        return from + (to - from) / incline;
    }
    
    public static double getDistance(final Vec3d pos, final double x, final double y, final double z) {
        final double deltaX = pos.x - x;
        final double deltaY = pos.y - y;
        final double deltaZ = pos.z - z;
        return MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }
    
    public static double[] calcIntersection(final double[] line, final double[] line2) {
        final double a1 = line[3] - line[1];
        final double b1 = line[0] - line[2];
        final double c1 = a1 * line[0] + b1 * line[1];
        final double a2 = line2[3] - line2[1];
        final double b2 = line2[0] - line2[2];
        final double c2 = a2 * line2[0] + b2 * line2[1];
        final double delta = a1 * b2 - a2 * b1;
        return new double[] { (b2 * c1 - b1 * c2) / delta, (a1 * c2 - a2 * c1) / delta };
    }
    
    public static double calculateAngle(final double x1, final double y1, final double x2, final double y2) {
        double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
        angle += Math.ceil(-angle / 360.0) * 360.0;
        return angle;
    }
    
    static {
        MathUtil.mc = Minecraft.getMinecraft();
        MathUtil.rnd = new Random();
    }
}
