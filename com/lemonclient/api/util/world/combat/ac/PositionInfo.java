//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world.combat.ac;

import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;

public class PositionInfo
{
    public BlockPos pos;
    public EntityEnderCrystal crystal;
    private final double selfDamage;
    public double rapp;
    public double damage;
    public double distance;
    public double distancePlayer;
    
    public double getSelfDamage() {
        return this.selfDamage;
    }
    
    public PositionInfo(final BlockPos pos, final double selfDamage) {
        this.pos = pos;
        this.selfDamage = selfDamage;
    }
    
    public PositionInfo(final EntityEnderCrystal pos, final double selfDamage) {
        this.crystal = pos;
        this.selfDamage = selfDamage;
    }
    
    public PositionInfo() {
        this.pos = null;
        this.selfDamage = 100.0;
        this.damage = 0.0;
        this.rapp = 100.0;
        this.distancePlayer = 100.0;
    }
    
    public void setEnemyDamage(final double damage) {
        this.damage = damage;
        this.rapp = damage / this.selfDamage;
    }
}
