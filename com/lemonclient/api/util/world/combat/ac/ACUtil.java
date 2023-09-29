//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world.combat.ac;

import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.entity.item.*;

public class ACUtil
{
    public static CrystalInfo.PlaceInfo calculateBestPlacement(final ACSettings settings, final PlayerInfo target, final List<BlockPos> possibleLocations) {
        final double x = settings.playerPos.x;
        final double y = settings.playerPos.y;
        final double z = settings.playerPos.z;
        BlockPos best = null;
        float bestDamage = 0.0f;
        for (final BlockPos crystal : possibleLocations) {
            if (target.entity.getDistanceSq(crystal.getX() + 0.5, crystal.getY() + 1.0, crystal.getZ() + 0.5) <= settings.enemyRangeSq) {
                final float currentDamage = DamageUtil.calculateDamageThreaded(crystal.getX() + 0.5, crystal.getY() + 1.0, crystal.getZ() + 0.5, target, false);
                if (currentDamage == bestDamage) {
                    if (best != null && crystal.distanceSq(x, y, z) >= best.distanceSq(x, y, z)) {
                        continue;
                    }
                    bestDamage = currentDamage;
                    best = crystal;
                }
                else {
                    if (currentDamage <= bestDamage) {
                        continue;
                    }
                    bestDamage = currentDamage;
                    best = crystal;
                }
            }
        }
        if (best != null && (bestDamage >= settings.minDamage || ((target.health <= settings.facePlaceHealth || target.lowArmour) && bestDamage >= settings.minFacePlaceDamage))) {
            return new CrystalInfo.PlaceInfo(bestDamage, target, best, 0.0);
        }
        return null;
    }
    
    public static CrystalInfo.BreakInfo calculateBestBreakable(final ACSettings settings, final PlayerInfo target, final List<EntityEnderCrystal> crystals) {
        final double x = settings.playerPos.x;
        final double y = settings.playerPos.y;
        final double z = settings.playerPos.z;
        final boolean smart = settings.breakMode.equalsIgnoreCase("Smart");
        EntityEnderCrystal best = null;
        float bestDamage = 0.0f;
        for (final EntityEnderCrystal crystal : crystals) {
            final float currentDamage = DamageUtil.calculateDamageThreaded(crystal.posX, crystal.posY, crystal.posZ, target, false);
            if (currentDamage == bestDamage) {
                if (best != null && crystal.getDistanceSq(x, y, z) >= best.getDistanceSq(x, y, z)) {
                    continue;
                }
                bestDamage = currentDamage;
                best = crystal;
            }
            else {
                if (currentDamage <= bestDamage) {
                    continue;
                }
                bestDamage = currentDamage;
                best = crystal;
            }
        }
        if (best != null) {
            boolean shouldAdd = false;
            if (smart) {
                if (bestDamage >= (double)settings.minBreakDamage || ((target.health <= settings.facePlaceHealth || target.lowArmour) && bestDamage > settings.minFacePlaceDamage)) {
                    shouldAdd = true;
                }
            }
            else {
                shouldAdd = true;
            }
            if (shouldAdd) {
                return new CrystalInfo.BreakInfo(bestDamage, target, best);
            }
        }
        return null;
    }
}
