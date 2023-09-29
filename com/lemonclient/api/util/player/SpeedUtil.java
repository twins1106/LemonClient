//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.player;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.math.*;

public class SpeedUtil
{
    static Minecraft mc;
    public static final double LAST_JUMP_INFO_DURATION_DEFAULT = 3.0;
    public static boolean didJumpThisTick;
    public static boolean isJumping;
    private final int distancer = 20;
    public double firstJumpSpeed;
    public double lastJumpSpeed;
    public double percentJumpSpeedChanged;
    public double jumpSpeedChanged;
    public boolean didJumpLastTick;
    public long jumpInfoStartTime;
    public boolean wasFirstJump;
    public double speedometerCurrentSpeed;
    public HashMap<EntityPlayer, Double> playerSpeeds;
    
    public SpeedUtil() {
        this.firstJumpSpeed = 0.0;
        this.lastJumpSpeed = 0.0;
        this.percentJumpSpeedChanged = 0.0;
        this.jumpSpeedChanged = 0.0;
        this.didJumpLastTick = false;
        this.jumpInfoStartTime = 0L;
        this.wasFirstJump = true;
        this.speedometerCurrentSpeed = 0.0;
        this.playerSpeeds = new HashMap<EntityPlayer, Double>();
    }
    
    public static void setDidJumpThisTick(final boolean val) {
        SpeedUtil.didJumpThisTick = val;
    }
    
    public static void setIsJumping(final boolean val) {
        SpeedUtil.isJumping = val;
    }
    
    public float lastJumpInfoTimeRemaining() {
        return (Minecraft.getSystemTime() - this.jumpInfoStartTime) / 1000.0f;
    }
    
    public void updateValues() {
        final double distTraveledLastTickX = SpeedUtil.mc.player.posX - SpeedUtil.mc.player.prevPosX;
        final double distTraveledLastTickZ = SpeedUtil.mc.player.posZ - SpeedUtil.mc.player.prevPosZ;
        this.speedometerCurrentSpeed = distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ;
        if (SpeedUtil.didJumpThisTick && (!SpeedUtil.mc.player.onGround || SpeedUtil.isJumping)) {
            if (SpeedUtil.didJumpThisTick && !this.didJumpLastTick) {
                this.wasFirstJump = (this.lastJumpSpeed == 0.0);
                this.percentJumpSpeedChanged = ((this.speedometerCurrentSpeed != 0.0) ? (this.speedometerCurrentSpeed / this.lastJumpSpeed - 1.0) : -1.0);
                this.jumpSpeedChanged = this.speedometerCurrentSpeed - this.lastJumpSpeed;
                this.jumpInfoStartTime = Minecraft.getSystemTime();
                this.lastJumpSpeed = this.speedometerCurrentSpeed;
                this.firstJumpSpeed = (this.wasFirstJump ? this.lastJumpSpeed : 0.0);
            }
            this.didJumpLastTick = SpeedUtil.didJumpThisTick;
        }
        else {
            this.didJumpLastTick = false;
            this.lastJumpSpeed = 0.0;
        }
        this.updatePlayers();
    }
    
    public void updatePlayers() {
        for (final EntityPlayer player : SpeedUtil.mc.world.playerEntities) {
            final double getDistanceSq = SpeedUtil.mc.player.getDistanceSq((Entity)player);
            this.getClass();
            final int n = 20;
            this.getClass();
            if (getDistanceSq >= n * 20) {
                continue;
            }
            final double distTraveledLastTickX = player.posX - player.prevPosX;
            final double distTraveledLastTickZ = player.posZ - player.prevPosZ;
            final double playerSpeed = distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ;
            this.playerSpeeds.put(player, playerSpeed);
        }
    }
    
    public double getPlayerSpeed(final EntityPlayer player) {
        if (this.playerSpeeds.get(player) == null) {
            return 0.0;
        }
        return this.turnIntoKpH(this.playerSpeeds.get(player));
    }
    
    public double turnIntoKpH(final double input) {
        return MathHelper.sqrt(input) * 71.2729367892;
    }
    
    public double getSpeedKpH() {
        double speedometerkphdouble = this.turnIntoKpH(this.speedometerCurrentSpeed);
        speedometerkphdouble = Math.round(10.0 * speedometerkphdouble) / 10.0;
        return speedometerkphdouble;
    }
    
    public double getSpeedMpS() {
        double speedometerMpsdouble = this.turnIntoKpH(this.speedometerCurrentSpeed) / 3.6;
        speedometerMpsdouble = Math.round(10.0 * speedometerMpsdouble) / 10.0;
        return speedometerMpsdouble;
    }
    
    static {
        SpeedUtil.mc = Minecraft.getMinecraft();
        SpeedUtil.didJumpThisTick = false;
        SpeedUtil.isJumping = false;
    }
}
