//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world.combat;

import net.minecraft.client.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import java.util.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.api.util.world.*;
import java.util.stream.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;

public class CrystalUtil
{
    private static final Minecraft mc;
    
    public static boolean canPlaceCrystal(final BlockPos blockPos, final boolean newPlacement) {
        if (notValidBlock(CrystalUtil.mc.world.getBlockState(blockPos).getBlock())) {
            return false;
        }
        final BlockPos posUp = blockPos.up();
        if (newPlacement) {
            if (!CrystalUtil.mc.world.isAirBlock(posUp)) {
                return false;
            }
        }
        else if (notValidMaterial(CrystalUtil.mc.world.getBlockState(posUp).getMaterial()) || notValidMaterial(CrystalUtil.mc.world.getBlockState(posUp.up()).getMaterial())) {
            return false;
        }
        final AxisAlignedBB box = new AxisAlignedBB((double)posUp.getX(), (double)posUp.getY(), (double)posUp.getZ(), posUp.getX() + 1.0, posUp.getY() + 2.0, posUp.getZ() + 1.0);
        return CrystalUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, box, Entity::isEntityAlive).isEmpty();
    }
    
    public static boolean canPlaceCrystalExcludingCrystals(final BlockPos blockPos, final boolean newPlacement) {
        if (notValidBlock(CrystalUtil.mc.world.getBlockState(blockPos).getBlock())) {
            return false;
        }
        final BlockPos posUp = blockPos.up();
        if (newPlacement) {
            if (!CrystalUtil.mc.world.isAirBlock(posUp)) {
                return false;
            }
        }
        else if (notValidMaterial(CrystalUtil.mc.world.getBlockState(posUp).getMaterial()) || notValidMaterial(CrystalUtil.mc.world.getBlockState(posUp.up()).getMaterial())) {
            return false;
        }
        final AxisAlignedBB box = new AxisAlignedBB((double)posUp.getX(), (double)posUp.getY(), (double)posUp.getZ(), posUp.getX() + 1.0, posUp.getY() + 2.0, posUp.getZ() + 1.0);
        return CrystalUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, box, entity -> !entity.isDead && !(entity instanceof EntityEnderCrystal)).isEmpty();
    }
    
    public static boolean notValidBlock(final Block block) {
        return block != Blocks.BEDROCK && block != Blocks.OBSIDIAN;
    }
    
    public static boolean notValidMaterial(final Material material) {
        return material.isLiquid() || !material.isReplaceable();
    }
    
    public static List<BlockPos> findCrystalBlocks(final float placeRange, final boolean mode) {
        return EntityUtil.getSphere(PlayerUtil.getPlayerPos(), (double)placeRange, (double)placeRange, false, true, 0).stream().filter(pos -> canPlaceCrystal(pos, mode)).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
    }
    
    public static List<BlockPos> findCrystalBlocksExcludingCrystals(final float placeRange, final boolean mode) {
        return EntityUtil.getSphere(PlayerUtil.getPlayerPos(), (double)placeRange, (double)placeRange, false, true, 0).stream().filter(pos -> canPlaceCrystalExcludingCrystals(pos, mode)).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
    }
    
    public static void breakCrystal(final Entity crystal, final boolean swing) {
        CrystalUtil.mc.playerController.attackEntity((EntityPlayer)CrystalUtil.mc.player, crystal);
        if (swing) {
            CrystalUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    public static void breakCrystalPacket(final Entity crystal, final boolean swing) {
        CrystalUtil.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
        if (swing) {
            CrystalUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
