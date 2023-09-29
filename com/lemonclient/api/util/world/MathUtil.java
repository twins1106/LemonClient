//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world;

import java.math.*;
import net.minecraft.util.math.*;

public class MathUtil
{
    public static int clamp(final int num, final int min, final int max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static float clamp(final float num, final float min, final float max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static double clamp(final double num, final double min, final double max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static long clamp(final long num, final long min, final long max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static BigDecimal clamp(final BigDecimal num, final BigDecimal min, final BigDecimal max) {
        return smallerThan(num, min) ? min : (biggerThan(num, max) ? max : num);
    }
    
    public static Vec3d roundVec(final Vec3d vec3d, final int places) {
        return new Vec3d(round(vec3d.x, places), round(vec3d.y, places), round(vec3d.z, places));
    }
    
    public static boolean biggerThan(final BigDecimal bigger, final BigDecimal than) {
        return bigger.compareTo(than) > 0;
    }
    
    public static boolean smallerThan(final BigDecimal smaller, final BigDecimal than) {
        return smaller.compareTo(than) < 0;
    }
    
    public static double round(final double value, final int places) {
        return (places < 0) ? value : new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static float round(final float value, final int places) {
        return (places < 0) ? value : new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).floatValue();
    }
    
    public static float round(final float value, final int places, final float min, final float max) {
        return MathHelper.clamp((places < 0) ? value : new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).floatValue(), min, max);
    }
}
