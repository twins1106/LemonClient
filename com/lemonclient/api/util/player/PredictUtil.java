//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.player;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import com.lemonclient.client.module.modules.combat.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.math.*;
import com.mojang.authlib.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import net.minecraft.potion.*;
import java.util.*;

public class PredictUtil
{
    static final Minecraft mc;
    
    public static EntityPlayer predictPlayer(final EntityPlayer entity, final PredictSettings settings) {
        double[] posVec = { entity.posX, entity.posY, entity.posZ };
        double[] newPosVec = posVec.clone();
        final double motionX = entity.posX - entity.prevPosX;
        double motionY = entity.posY - entity.prevPosY;
        if (settings.debug) {
            PistonCrystal.printDebug("Motion Y:" + motionY, false);
        }
        final double motionZ = entity.posZ - entity.prevPosZ;
        boolean goingUp = false;
        boolean start = true;
        int up = 0;
        int down = 0;
        if (settings.debug) {
            PistonCrystal.printDebug(String.format("Values: %f %f %f", newPosVec[0], newPosVec[1], newPosVec[2]), false);
        }
        boolean isHole = false;
        if (settings.manualOutHole && motionY > 0.2) {
            if (HoleUtil.isHole(EntityUtil.getPosition((Entity)entity), false, true, false).getType() != HoleUtil.HoleType.NONE && BlockUtil.getBlock(EntityUtil.getPosition((Entity)entity).add(0, 2, 0)) instanceof BlockAir) {
                isHole = true;
            }
            else if (settings.aboveHoleManual && HoleUtil.isHole(EntityUtil.getPosition((Entity)entity).add(0, -1, 0), false, true, false).getType() != HoleUtil.HoleType.NONE) {
                isHole = true;
            }
            if (isHole) {
                final double[] array = posVec;
                final int n = 1;
                ++array[n];
            }
        }
        boolean allowPredictStair = false;
        int stairPredicted = 0;
        if (settings.stairPredict) {
            allowPredictStair = (Math.abs(entity.posX - entity.prevPosX) + Math.abs(entity.posZ - entity.prevPosZ) > settings.speedActivationStairs);
            if (settings.debug) {
                PistonCrystal.printDebug(String.format("Speed: %.2f Activation speed Stairs: %.2f", Math.abs(entity.posX - entity.prevPosX) + Math.abs(entity.posZ - entity.prevPosZ), settings.speedActivationStairs), false);
            }
        }
        for (int i = 0; i < settings.tick; ++i) {
            if (settings.splitXZ) {
                final double[] array2;
                newPosVec = (array2 = posVec.clone());
                final int n2 = 0;
                array2[n2] += motionX;
                RayTraceResult result = PredictUtil.mc.world.rayTraceBlocks(new Vec3d(posVec[0], posVec[1], posVec[2]), new Vec3d(newPosVec[0], posVec[1], posVec[2]));
                boolean predictedStair = false;
                if (result == null || result.typeOfHit == RayTraceResult.Type.ENTITY) {
                    posVec = newPosVec.clone();
                }
                else if (settings.stairPredict && allowPredictStair && BlockUtil.getBlock(newPosVec[0], newPosVec[1] + 1.0, newPosVec[2]) instanceof BlockAir && stairPredicted++ < settings.nStairs) {
                    final double[] array3 = posVec;
                    final int n3 = 1;
                    ++array3[n3];
                    predictedStair = true;
                }
                final double[] array4;
                newPosVec = (array4 = posVec.clone());
                final int n4 = 2;
                array4[n4] += motionZ;
                result = PredictUtil.mc.world.rayTraceBlocks(new Vec3d(posVec[0], posVec[1], posVec[2]), new Vec3d(newPosVec[0], posVec[1], newPosVec[2]));
                if (result == null || result.typeOfHit == RayTraceResult.Type.ENTITY) {
                    posVec = newPosVec.clone();
                }
                else if (settings.stairPredict && allowPredictStair && !predictedStair && BlockUtil.getBlock(newPosVec[0], newPosVec[1] + 1.0, newPosVec[2]) instanceof BlockAir && stairPredicted++ < settings.nStairs) {
                    final double[] array5 = posVec;
                    final int n5 = 1;
                    ++array5[n5];
                }
            }
            else {
                final double[] array6;
                newPosVec = (array6 = posVec.clone());
                final int n6 = 0;
                array6[n6] += motionX;
                final double[] array7 = newPosVec;
                final int n7 = 2;
                array7[n7] += motionZ;
                final RayTraceResult result = PredictUtil.mc.world.rayTraceBlocks(new Vec3d(posVec[0], posVec[1], posVec[2]), new Vec3d(newPosVec[0], posVec[1], newPosVec[2]));
                if (result == null || result.typeOfHit == RayTraceResult.Type.ENTITY) {
                    posVec = newPosVec.clone();
                }
                else if (settings.stairPredict && allowPredictStair && BlockUtil.getBlock(newPosVec[0], newPosVec[1] + 1.0, newPosVec[2]) instanceof BlockAir && stairPredicted++ < settings.nStairs) {
                    final double[] array8 = posVec;
                    final int n8 = 1;
                    ++array8[n8];
                }
            }
            if (settings.calculateY && !isHole) {
                newPosVec = posVec.clone();
                if (!entity.onGround && motionY != -0.0784000015258789 && motionY != 0.0) {
                    final double decreasePow = settings.startDecrease / Math.pow(10.0, settings.exponentStartDecrease);
                    if (start) {
                        if (motionY == 0.0) {
                            motionY = decreasePow;
                        }
                        goingUp = false;
                        start = false;
                        if (settings.debug) {
                            PistonCrystal.printDebug("Start motionY: " + motionY, false);
                        }
                    }
                    final double increasePowY = settings.increaseY / Math.pow(10.0, settings.exponentIncreaseY);
                    final double decreasePowY = settings.decreaseY / Math.pow(10.0, settings.exponentDecreaseY);
                    motionY += (goingUp ? increasePowY : decreasePowY);
                    if (Math.abs(motionY) > decreasePow) {
                        goingUp = false;
                        if (settings.debug) {
                            ++up;
                        }
                        motionY = decreasePowY;
                    }
                    final double[] array9 = newPosVec;
                    final int n9 = 1;
                    array9[n9] += (goingUp ? 1 : -1) * motionY;
                    final RayTraceResult result = PredictUtil.mc.world.rayTraceBlocks(new Vec3d(posVec[0], posVec[1], posVec[2]), new Vec3d(newPosVec[0], newPosVec[1], newPosVec[2]));
                    if (result == null || result.typeOfHit == RayTraceResult.Type.ENTITY) {
                        posVec = newPosVec.clone();
                    }
                    else if (!goingUp) {
                        goingUp = true;
                        final double[] array10 = newPosVec;
                        final int n10 = 1;
                        array10[n10] += increasePowY;
                        motionY = increasePowY;
                        final double[] array11 = newPosVec;
                        final int n11 = 1;
                        array11[n11] += motionY;
                        if (settings.debug) {
                            ++down;
                        }
                    }
                }
            }
            if (settings.show) {
                PistonCrystal.printDebug(String.format("Values: %f %f %f", posVec[0], posVec[1], posVec[2]), false);
            }
        }
        if (settings.debug) {
            PistonCrystal.printDebug(String.format("Player: %s Total ticks: %d Up: %d Down: %d", entity.getGameProfile().getName(), settings.tick, up, down), false);
        }
        final EntityOtherPlayerMP clonedPlayer = new EntityOtherPlayerMP((World)PredictUtil.mc.world, new GameProfile(UUID.fromString("fdee323e-7f0c-4c15-8d1c-0f277442342a"), entity.getName()));
        clonedPlayer.setPosition(posVec[0], posVec[1], posVec[2]);
        clonedPlayer.inventory.copyInventory(entity.inventory);
        clonedPlayer.setHealth(entity.getHealth());
        clonedPlayer.prevPosX = entity.prevPosX;
        clonedPlayer.prevPosY = entity.prevPosY;
        clonedPlayer.prevPosZ = entity.prevPosZ;
        for (final PotionEffect effect : entity.getActivePotionEffects()) {
            clonedPlayer.addPotionEffect(effect);
        }
        return (EntityPlayer)clonedPlayer;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public static class PredictSettings
    {
        final int tick;
        final boolean calculateY;
        final int startDecrease;
        final int exponentStartDecrease;
        final int decreaseY;
        final int exponentDecreaseY;
        final int increaseY;
        final int exponentIncreaseY;
        final boolean splitXZ;
        final int width;
        final boolean debug;
        final boolean show;
        final boolean manualOutHole;
        final boolean aboveHoleManual;
        final boolean stairPredict;
        final int nStairs;
        final double speedActivationStairs;
        
        public PredictSettings(final int tick, final boolean calculateY, final int startDecrease, final int exponentStartDecrease, final int decreaseY, final int exponentDecreaseY, final int increaseY, final int exponentIncreaseY, final boolean splitXZ, final int width, final boolean debug, final boolean show, final boolean manualOutHole, final boolean aboveHoleManual, final boolean stairPredict, final int nStairs, final double speedActivationStairs) {
            this.tick = tick;
            this.calculateY = calculateY;
            this.startDecrease = startDecrease;
            this.exponentStartDecrease = exponentStartDecrease;
            this.decreaseY = decreaseY;
            this.exponentDecreaseY = exponentDecreaseY;
            this.increaseY = increaseY;
            this.exponentIncreaseY = exponentIncreaseY;
            this.splitXZ = splitXZ;
            this.width = width;
            this.debug = debug;
            this.show = show;
            this.manualOutHole = manualOutHole;
            this.aboveHoleManual = aboveHoleManual;
            this.stairPredict = stairPredict;
            this.nStairs = nStairs;
            this.speedActivationStairs = speedActivationStairs;
        }
    }
}
