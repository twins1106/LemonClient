//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world.combat.ac;

import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;

public class CrystalInfo
{
    public final float damage;
    public final PlayerInfo target;
    
    private CrystalInfo(final float damage, final PlayerInfo target) {
        this.damage = damage;
        this.target = target;
    }
    
    public static class BreakInfo extends CrystalInfo
    {
        public final EntityEnderCrystal crystal;
        
        public BreakInfo(final float damage, final PlayerInfo target, final EntityEnderCrystal crystal) {
            super(damage, target, null);
            this.crystal = crystal;
        }
    }
    
    public static class NewBreakInfo extends CrystalInfo
    {
        public final EntityEnderCrystal crystal;
        public final double distance;
        
        public NewBreakInfo(final float damage, final PlayerInfo target, final EntityEnderCrystal crystal, final double distance) {
            super(damage, target, null);
            this.crystal = crystal;
            this.distance = distance;
        }
    }
    
    public static class PlaceInfo extends CrystalInfo
    {
        public final BlockPos crystal;
        public final double distance;
        
        public PlaceInfo(final float damage, final PlayerInfo target, final BlockPos crystal, final double distance) {
            super(damage, target, null);
            this.crystal = crystal;
            this.distance = distance;
        }
        
        public EntityPlayer getTarget() {
            return this.target.entity;
        }
    }
}
