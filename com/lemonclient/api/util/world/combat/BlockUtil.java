//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world.combat;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;

public class BlockUtil
{
    static Minecraft mc;
    
    public static boolean isEntitiesEmpty(final BlockPos pos) {
        final List entities = (List)BlockUtil.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).stream().filter(e -> !(e instanceof EntityItem)).filter(e -> !(e instanceof EntityXPOrb)).collect(Collectors.toList());
        return entities.isEmpty();
    }
    
    public static boolean placeBlockScaffold(final BlockPos pos, final boolean rotate) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (rotate) {
                    faceVectorPacketInstant(hitVec);
                }
                BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                processRightClickBlock(neighbor, side2, hitVec);
                BlockUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
                BlockUtil.mc.rightClickDelayTimer = 0;
                BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                return true;
            }
        }
        return false;
    }
    
    private static PlayerControllerMP getPlayerController() {
        return BlockUtil.mc.playerController;
    }
    
    public static void processRightClickBlock(final BlockPos pos, final EnumFacing side, final Vec3d hitVec) {
        getPlayerController().processRightClickBlock(BlockUtil.mc.player, BlockUtil.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }
    
    public static IBlockState getState(final BlockPos pos) {
        return BlockUtil.mc.world.getBlockState(pos);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getNeededRotations2(vec);
        BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], BlockUtil.mc.player.onGround));
    }
    
    private static float[] getNeededRotations2(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { BlockUtil.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - BlockUtil.mc.player.rotationYaw), BlockUtil.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - BlockUtil.mc.player.rotationPitch) };
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(BlockUtil.mc.player.posX, BlockUtil.mc.player.posY + BlockUtil.mc.player.getEyeHeight(), BlockUtil.mc.player.posZ);
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
    
    static {
        BlockUtil.mc = Minecraft.getMinecraft();
    }
}
