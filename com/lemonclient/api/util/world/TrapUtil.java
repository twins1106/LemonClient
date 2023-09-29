//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.*;
import jdk.nashorn.internal.objects.*;
import java.util.*;
import net.minecraft.entity.item.*;

public class TrapUtil
{
    private static final Minecraft mc;
    public static final Vec3d[] antiDropOffsetList;
    public static final Vec3d[] platformOffsetList;
    public static final Vec3d[] legOffsetList;
    public static final Vec3d[] OffsetList;
    public static final Vec3d[] antiStepOffsetList;
    public static final Vec3d[] antiScaffoldOffsetList;
    
    public static void placeBlock(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.VALUES) {
            final BlockPos neighbor = pos.offset(side);
            final IBlockState neighborState = TrapUtil.mc.world.getBlockState(neighbor);
            if (neighborState.getBlock().canCollideCheck(neighborState, false)) {
                final boolean sneak = !TrapUtil.mc.player.isSneaking() && neighborState.getBlock().onBlockActivated((World)TrapUtil.mc.world, pos, TrapUtil.mc.world.getBlockState(pos), (EntityPlayer)TrapUtil.mc.player, EnumHand.MAIN_HAND, side, 0.5f, 0.5f, 0.5f);
                if (sneak) {
                    TrapUtil.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)TrapUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                }
                TrapUtil.mc.getConnection().sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(neighbor, side.getOpposite(), EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
                TrapUtil.mc.getConnection().sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                if (sneak) {
                    TrapUtil.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)TrapUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
            }
        }
    }
    
    public static boolean canPlaceCrystal(final BlockPos pos, final boolean checkSecond) {
        final Chunk chunk = TrapUtil.mc.world.getChunk(pos);
        final Block block = chunk.getBlockState(pos).getBlock();
        if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN) {
            return false;
        }
        final BlockPos boost = pos.offset(EnumFacing.UP, 1);
        return chunk.getBlockState(boost).getBlock() == Blocks.AIR && chunk.getBlockState(pos.offset(EnumFacing.UP, 2)).getBlock() == Blocks.AIR && TrapUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB((double)boost.getX(), (double)boost.getY(), (double)boost.getZ(), (double)(boost.getX() + 1), (double)(boost.getY() + (checkSecond ? 2 : 1)), (double)(boost.getZ() + 1)), e -> !(e instanceof EntityEnderCrystal)).isEmpty();
    }
    
    public static List<BlockPos> getSphere(final float radius) {
        final List<BlockPos> sphere = new ArrayList<BlockPos>();
        final BlockPos pos = new BlockPos(TrapUtil.mc.player.posX, TrapUtil.mc.player.posY, TrapUtil.mc.player.posZ);
        final int posX = pos.getX();
        final int posY = pos.getY();
        final int posZ = pos.getZ();
        for (int x = posX - (int)radius; x <= posX + radius; ++x) {
            for (int z = posZ - (int)radius; z <= posZ + radius; ++z) {
                for (int y = posY - (int)radius; y < posY + radius; ++y) {
                    if ((posX - x) * (posX - x) + (posZ - z) * (posZ - z) + (posY - y) * (posY - y) < radius * radius) {
                        sphere.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
        return sphere;
    }
    
    public static int isPositionPlaceable(final BlockPos pos, final boolean entityCheck) {
        try {
            final Block block = TrapUtil.mc.world.getBlockState(pos).getBlock();
            if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid) && !(block instanceof BlockTallGrass) && !(block instanceof BlockFire) && !(block instanceof BlockDeadBush) && !(block instanceof BlockSnow)) {
                return 0;
            }
            if (entityCheck) {
                for (final Entity entity : TrapUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
                    if (!entity.isDead && !(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                        return 1;
                    }
                }
            }
            for (final EnumFacing side : getPossibleSides(pos)) {
                if (canBeClicked(pos.offset(side))) {
                    return 3;
                }
            }
            return 2;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    private static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    private static IBlockState getState(final BlockPos pos) {
        return TrapUtil.mc.world.getBlockState(pos);
    }
    
    public static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final List<EnumFacing> facings = new ArrayList<EnumFacing>(6);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (TrapUtil.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(TrapUtil.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = TrapUtil.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
    
    public static Vec3d[] getHelpingBlocks(final Vec3d vec3d) {
        return new Vec3d[] { new Vec3d(vec3d.x, vec3d.y - 1.0, vec3d.z), new Vec3d((vec3d.x != 0.0) ? (vec3d.x * 2.0) : vec3d.x, vec3d.y, (vec3d.x != 0.0) ? vec3d.z : (vec3d.z * 2.0)), new Vec3d((vec3d.x == 0.0) ? (vec3d.x + 1.0) : vec3d.x, vec3d.y, (vec3d.x == 0.0) ? vec3d.z : (vec3d.z + 1.0)), new Vec3d((vec3d.x == 0.0) ? (vec3d.x - 1.0) : vec3d.x, vec3d.y, (vec3d.x == 0.0) ? vec3d.z : (vec3d.z - 1.0)), new Vec3d(vec3d.x, vec3d.y + 1.0, vec3d.z) };
    }
    
    public static List<Vec3d> getOffsetList(final int y, final boolean floor) {
        final List<Vec3d> offsets = new ArrayList<Vec3d>(5);
        offsets.add(new Vec3d(-1.0, (double)y, 0.0));
        offsets.add(new Vec3d(1.0, (double)y, 0.0));
        offsets.add(new Vec3d(0.0, (double)y, -1.0));
        offsets.add(new Vec3d(0.0, (double)y, 1.0));
        if (floor) {
            offsets.add(new Vec3d(0.0, (double)(y - 1), 0.0));
        }
        return offsets;
    }
    
    public static Vec3d[] getOffsets(final int y, final boolean floor) {
        final List<Vec3d> offsets = getOffsetList(y, floor);
        final Vec3d[] array = new Vec3d[offsets.size()];
        return offsets.toArray(array);
    }
    
    public static Vec3d[] getUnsafeBlockArray(final Entity entity, final int height, final boolean floor) {
        final List<Vec3d> list = getUnsafeBlocks(entity, height, floor);
        final Vec3d[] array = new Vec3d[list.size()];
        return list.toArray(array);
    }
    
    public static boolean isSafe(final Entity entity, final int height, final boolean floor) {
        return getUnsafeBlocks(entity, height, floor).size() == 0;
    }
    
    public static List<Vec3d> getUnsafeBlocks(final Entity entity, final int height, final boolean floor) {
        return getUnsafeBlocksFromVec3d(entity.getPositionVector(), height, floor);
    }
    
    public static List<Vec3d> getUnsafeBlocksFromVec3d(final Vec3d pos, final int height, final boolean floor) {
        final List<Vec3d> vec3ds = new ArrayList<Vec3d>(5);
        for (final Vec3d vector : getOffsets(height, floor)) {
            final Block block = TrapUtil.mc.world.getBlockState(new BlockPos(pos).add(vector.x, vector.y, vector.z)).getBlock();
            if (block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow) {
                vec3ds.add(vector);
            }
        }
        return vec3ds;
    }
    
    public static Vec3d[] getTrapOffsets(final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop) {
        final List<Vec3d> offsets = getTrapOffsetsList(antiScaffold, antiStep, legs, platform, antiDrop);
        final Vec3d[] array = new Vec3d[offsets.size()];
        return offsets.toArray(array);
    }
    
    public static List<Vec3d> getTrapOffsetsList(final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop) {
        final List<Vec3d> offsets = new ArrayList<Vec3d>(getOffsetList(1, false));
        offsets.add(new Vec3d(0.0, 2.0, 0.0));
        if (antiScaffold) {
            offsets.add(new Vec3d(0.0, 3.0, 0.0));
        }
        if (antiStep) {
            offsets.addAll(getOffsetList(2, false));
        }
        if (legs) {
            offsets.addAll(getOffsetList(0, false));
        }
        if (platform) {
            offsets.addAll(getOffsetList(-1, false));
            offsets.add(new Vec3d(0.0, -1.0, 0.0));
        }
        if (antiDrop) {
            offsets.add(new Vec3d(0.0, -2.0, 0.0));
        }
        return offsets;
    }
    
    public static boolean isTrapped(final EntityPlayer player, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop) {
        return getUntrappedBlocks(player, antiScaffold, antiStep, legs, platform, antiDrop).size() == 0;
    }
    
    public static boolean isTrappedExtended(final int extension, final EntityPlayer player, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop, final boolean raytrace) {
        return getUntrappedBlocksExtended(extension, player, antiScaffold, antiStep, legs, platform, antiDrop, raytrace).size() == 0;
    }
    
    public static List<Vec3d> getBlockBlocks(final Entity entity) {
        final List<Vec3d> vec3ds = new ArrayList<Vec3d>(8);
        final AxisAlignedBB bb = entity.getEntityBoundingBox();
        final double y = entity.posY;
        final double minX = NativeMath.round(bb.minX, 0);
        final double minZ = NativeMath.round(bb.minZ, 0);
        final double maxX = NativeMath.round(bb.maxX, 0);
        final double maxZ = NativeMath.round(bb.maxZ, 0);
        if (minX != maxX) {
            vec3ds.add(new Vec3d(minX, y, minZ));
            vec3ds.add(new Vec3d(maxX, y, minZ));
            if (minZ != maxZ) {
                vec3ds.add(new Vec3d(minX, y, maxZ));
                vec3ds.add(new Vec3d(maxX, y, maxZ));
                return vec3ds;
            }
        }
        else if (minZ != maxZ) {
            vec3ds.add(new Vec3d(minX, y, minZ));
            vec3ds.add(new Vec3d(minX, y, maxZ));
            return vec3ds;
        }
        vec3ds.add(entity.getPositionVector());
        return vec3ds;
    }
    
    public static List<Vec3d> getUntrappedBlocksExtended(final int extension, final EntityPlayer player, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop, final boolean raytrace) {
        final List<Vec3d> placeTargets = new ArrayList<Vec3d>();
        if (extension == 1) {
            placeTargets.addAll(targets(player.getPositionVector(), antiScaffold, antiStep, legs, platform, antiDrop, raytrace));
        }
        else {
            int extend = 1;
            for (final Vec3d vec3d : getBlockBlocks((Entity)player)) {
                if (extend > extension) {
                    break;
                }
                placeTargets.addAll(targets(vec3d, antiScaffold, antiStep, legs, platform, antiDrop, raytrace));
                ++extend;
            }
        }
        final List<Vec3d> removeList = new ArrayList<Vec3d>();
        for (final Vec3d vec3d : placeTargets) {
            final BlockPos pos = new BlockPos(vec3d);
            if (isPositionPlaceable(pos, raytrace) == -1) {
                removeList.add(vec3d);
            }
        }
        for (final Vec3d vec3d : removeList) {
            placeTargets.remove(vec3d);
        }
        return placeTargets;
    }
    
    public static List<Vec3d> getUntrappedBlocks(final EntityPlayer player, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop) {
        final List<Vec3d> vec3ds = new ArrayList<Vec3d>();
        if (!antiStep && getUnsafeBlocks((Entity)player, 2, false).size() == 4) {
            vec3ds.addAll(getUnsafeBlocks((Entity)player, 2, false));
        }
        final Vec3d[] trapOffsets = getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop);
        for (int i = 0; i < trapOffsets.length; ++i) {
            final Vec3d vector = trapOffsets[i];
            final BlockPos targetPos = new BlockPos(player.getPositionVector()).add(vector.x, vector.y, vector.z);
            final Block block = TrapUtil.mc.world.getBlockState(targetPos).getBlock();
            if (block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow) {
                vec3ds.add(vector);
            }
        }
        return vec3ds;
    }
    
    public static List<Vec3d> targets(final Vec3d vec3d, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop, final boolean raytrace) {
        final List<Vec3d> placeTargets = new ArrayList<Vec3d>();
        if (antiDrop) {
            Collections.addAll(placeTargets, convertVec3ds(vec3d, TrapUtil.antiDropOffsetList));
        }
        if (platform) {
            Collections.addAll(placeTargets, convertVec3ds(vec3d, TrapUtil.platformOffsetList));
        }
        if (legs) {
            Collections.addAll(placeTargets, convertVec3ds(vec3d, TrapUtil.legOffsetList));
        }
        Collections.addAll(placeTargets, convertVec3ds(vec3d, TrapUtil.OffsetList));
        if (antiStep) {
            Collections.addAll(placeTargets, convertVec3ds(vec3d, TrapUtil.antiStepOffsetList));
        }
        else {
            final List<Vec3d> vec3ds = getUnsafeBlocksFromVec3d(vec3d, 2, false);
            if (vec3ds.size() == 4) {
                for (final Vec3d vector : vec3ds) {
                    final BlockPos position = new BlockPos(vec3d).add(vector.x, vector.y, vector.z);
                    switch (isPositionPlaceable(position, raytrace)) {
                        case -1:
                        case 1:
                        case 2: {
                            continue;
                        }
                        case 3: {
                            placeTargets.add(vec3d.add(vector));
                            break;
                        }
                    }
                    break;
                }
            }
        }
        if (antiScaffold) {
            Collections.addAll(placeTargets, convertVec3ds(vec3d, TrapUtil.antiScaffoldOffsetList));
        }
        return placeTargets;
    }
    
    public static Vec3d[] convertVec3ds(final Vec3d vec3d, final Vec3d[] input) {
        final Vec3d[] output = new Vec3d[input.length];
        for (int length = input.length, i = 0; i < length; ++i) {
            output[i] = vec3d.add(input[i]);
        }
        return output;
    }
    
    static {
        mc = Minecraft.getMinecraft();
        antiDropOffsetList = new Vec3d[] { new Vec3d(0.0, -2.0, 0.0) };
        platformOffsetList = new Vec3d[] { new Vec3d(0.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(1.0, -1.0, 0.0) };
        legOffsetList = new Vec3d[] { new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 1.0) };
        OffsetList = new Vec3d[] { new Vec3d(1.0, 1.0, 0.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(0.0, 2.0, 0.0) };
        antiStepOffsetList = new Vec3d[] { new Vec3d(-1.0, 2.0, 0.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(0.0, 2.0, -1.0) };
        antiScaffoldOffsetList = new Vec3d[] { new Vec3d(0.0, 3.0, 0.0) };
    }
}
