//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world;

import net.minecraft.client.*;
import com.lemonclient.mixin.mixins.accessor.*;
import net.minecraft.util.*;
import java.util.*;

public class TimerUtils
{
    private static int counter;
    private static final HashMap<Integer, Float> multipliers;
    
    public static void setTickLength(final float speed) {
        final Timer timer = ((AccessorMinecraft)Minecraft.getMinecraft()).getTimer();
        ((AccessorTimer)timer).setTickLength(speed);
    }
    
    public static float getTickLength() {
        final Timer timer = ((AccessorMinecraft)Minecraft.getMinecraft()).getTimer();
        return ((AccessorTimer)timer).getTickLength();
    }
    
    public static void setSpeed(final float speed) {
        final Timer timer = ((AccessorMinecraft)Minecraft.getMinecraft()).getTimer();
        ((AccessorTimer)timer).setTickLength(50.0f / speed);
    }
    
    public static float getTimer() {
        final Timer timer = ((AccessorMinecraft)Minecraft.getMinecraft()).getTimer();
        return 50.0f / ((AccessorTimer)timer).getTickLength();
    }
    
    private static float getMultiplier() {
        float multiplier = 1.0f;
        for (final float f : TimerUtils.multipliers.values()) {
            multiplier *= f;
        }
        return multiplier;
    }
    
    public static int push(final float multiplier) {
        TimerUtils.multipliers.put(++TimerUtils.counter, multiplier);
        setSpeed(getMultiplier());
        return TimerUtils.counter;
    }
    
    public static void pop(final int counter) {
        TimerUtils.multipliers.remove(counter);
        setSpeed(getMultiplier());
    }
    
    static {
        multipliers = new HashMap<Integer, Float>();
    }
}
