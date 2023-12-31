//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.player;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;

public class BurrowUtil
{
    public static final Minecraft mc;
    static EnumFacing[] facing;
    
    public static void placeBlock(final BlockPos pos, final EnumHand hand, final boolean rotate, final boolean packet, final boolean isSneaking, final boolean swing) {
        if (pos == null || (!BurrowUtil.mc.world.isAirBlock(pos) && BurrowUtil.mc.world.getBlockState(pos).getBlock() != Blocks.FIRE)) {
            return;
        }
        final EnumFacing side = getFirstFacing(pos);
        if (side == null) {
            return;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        boolean sneak = false;
        if ((isSneaking || BlockUtil.blackList.contains(BurrowUtil.mc.world.getBlockState(neighbour).getBlock()) || BlockUtil.shulkerList.contains(BurrowUtil.mc.world.getBlockState(neighbour).getBlock())) && !BurrowUtil.mc.player.isSneaking()) {
            BurrowUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BurrowUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            BurrowUtil.mc.player.setSneaking(true);
            sneak = true;
        }
        if (rotate) {
            faceVector(hitVec, true);
        }
        rightClickBlock(neighbour, hitVec, hand, opposite, packet, swing);
        if (sneak) {
            BurrowUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BurrowUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        BurrowUtil.mc.rightClickDelayTimer = 4;
    }
    
    public static void placeBlockDown(final BlockPos pos, final EnumHand hand, final boolean rotate, final boolean packet, final boolean isSneaking, final boolean swing) {
        final BlockPos neighbour = pos.offset(EnumFacing.DOWN);
        final EnumFacing opposite = EnumFacing.DOWN.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        if (isSneaking && !BurrowUtil.mc.player.isSneaking()) {
            BurrowUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BurrowUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            BurrowUtil.mc.player.setSneaking(true);
        }
        if (rotate) {
            faceVector(hitVec, true);
        }
        rightClickBlock(neighbour, hitVec, hand, opposite, packet, swing);
        BurrowUtil.mc.rightClickDelayTimer = 4;
    }
    
    public static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        if (pos == null) {
            return null;
        }
        final List<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (BurrowUtil.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BurrowUtil.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = BurrowUtil.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
    
    public static List<EnumFacing> getTrapdoorPossibleSides(final BlockPos pos) {
        if (pos == null) {
            return null;
        }
        final List<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : BurrowUtil.facing) {
            final BlockPos neighbour = pos.offset(side);
            if (BurrowUtil.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BurrowUtil.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = BurrowUtil.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
    
    public static EnumFacing getFirstFacing(final BlockPos pos) {
        if (pos == null) {
            return null;
        }
        final Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            final EnumFacing facing = iterator.next();
            return facing;
        }
        return null;
    }
    
    public static EnumFacing getTrapdoorFacing(final BlockPos pos) {
        if (pos == null) {
            return null;
        }
        final Iterator<EnumFacing> iterator = getTrapdoorPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            final EnumFacing facing = iterator.next();
            return facing;
        }
        return null;
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(BurrowUtil.mc.player.posX, BurrowUtil.mc.player.posY + BurrowUtil.mc.player.getEyeHeight(), BurrowUtil.mc.player.posZ);
    }
    
    public static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { BurrowUtil.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - BurrowUtil.mc.player.rotationYaw), BurrowUtil.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - BurrowUtil.mc.player.rotationPitch) };
    }
    
    public static void faceVector(final Vec3d vec, final boolean normalizeAngle) {
        final float[] rotations = getLegitRotations(vec);
        BurrowUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? ((float)MathHelper.normalizeAngle((int)rotations[1], 360)) : rotations[1], BurrowUtil.mc.player.onGround));
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet, final boolean swing) {
        if (pos == null || vec == null || hand == null || direction == null) {
            return;
        }
        if (packet) {
            final float f = (float)(vec.x - pos.getX());
            final float f2 = (float)(vec.y - pos.getY());
            final float f3 = (float)(vec.z - pos.getZ());
            BurrowUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
        }
        else {
            BurrowUtil.mc.playerController.processRightClickBlock(BurrowUtil.mc.player, BurrowUtil.mc.world, pos, direction, vec, hand);
        }
        if (swing) {
            BurrowUtil.mc.player.swingArm(hand);
        }
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet) {
        if (pos == null || vec == null || direction == null) {
            return;
        }
        if (packet) {
            BurrowUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, 0.5f, 1.0f, 0.5f));
        }
        else {
            BurrowUtil.mc.playerController.processRightClickBlock(BurrowUtil.mc.player, BurrowUtil.mc.world, pos, direction, vec, hand);
        }
    }
    
    public static int findHotbarBlock(final Class clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = BurrowUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (clazz.isInstance(stack.getItem())) {
                    return i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (clazz.isInstance(block)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static void switchToSlot(final int slot) {
        BurrowUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        BurrowUtil.mc.player.inventory.currentItem = slot;
        BurrowUtil.mc.playerController.updateController();
    }
    
    static {
        mc = Minecraft.getMinecraft();
        BurrowUtil.facing = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.WEST, EnumFacing.EAST };
    }
}
