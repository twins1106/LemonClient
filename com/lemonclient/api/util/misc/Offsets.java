//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.misc;

import net.minecraft.util.math.*;

public class Offsets
{
    public static final Vec3d[] SURROUND;
    public static final Vec3d[] SURROUND_CITY;
    public static final Vec3d[] TRAP_FULL;
    public static final Vec3d[] TRAP_STEP;
    public static final Vec3d[] TRAP_SIMPLE;
    public static final Vec3d[] BURROW;
    public static final Vec3d[] BURROW_DOUBLE;
    
    static {
        SURROUND = new Vec3d[] { new Vec3d(0.0, -1.0, 0.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 1.0) };
        SURROUND_CITY = new Vec3d[] { new Vec3d(0.0, -1.0, 0.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-2.0, 0.0, 0.0), new Vec3d(2.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -2.0), new Vec3d(0.0, 0.0, 2.0) };
        TRAP_FULL = new Vec3d[] { new Vec3d(0.0, -1.0, 0.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 0.0) };
        TRAP_STEP = new Vec3d[] { new Vec3d(0.0, -1.0, 0.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 0.0), new Vec3d(0.0, 3.0, 0.0) };
        TRAP_SIMPLE = new Vec3d[] { new Vec3d(0.0, -1.0, 0.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 0.0) };
        BURROW = new Vec3d[] { new Vec3d(0.0, 0.0, 0.0) };
        BURROW_DOUBLE = new Vec3d[] { new Vec3d(0.0, 0.0, 0.0), new Vec3d(0.0, 1.0, 0.0) };
    }
}
