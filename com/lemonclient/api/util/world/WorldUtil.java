//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world;

import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;
import net.minecraft.init.*;
import java.util.*;

public class WorldUtil
{
    private static final Minecraft mc;
    public static List<Block> emptyBlocks;
    public static List<Block> rightclickableBlocks;
    
    public static void openBlock(final BlockPos pos) {
        final EnumFacing[] values;
        final EnumFacing[] facings = values = EnumFacing.values();
        for (final EnumFacing f : values) {
            final Block neighborBlock = WorldUtil.mc.world.getBlockState(pos.offset(f)).getBlock();
            if (WorldUtil.emptyBlocks.contains(neighborBlock)) {
                WorldUtil.mc.playerController.processRightClickBlock(WorldUtil.mc.player, WorldUtil.mc.world, pos, f.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
                return;
            }
        }
    }
    
    public static boolean placeBlock(final BlockPos pos, final int slot, final boolean rotate, final boolean rotateBack) {
        if (!isBlockEmpty(pos)) {
            return false;
        }
        if (slot != WorldUtil.mc.player.inventory.currentItem) {
            WorldUtil.mc.player.inventory.currentItem = slot;
        }
        final EnumFacing[] values;
        final EnumFacing[] facings = values = EnumFacing.values();
        for (final EnumFacing f : values) {
            final Block neighborBlock = WorldUtil.mc.world.getBlockState(pos.offset(f)).getBlock();
            final Vec3d vec = new Vec3d(pos.getX() + 0.5 + f.getXOffset() * 0.5, pos.getY() + 0.5 + f.getYOffset() * 0.5, pos.getZ() + 0.5 + f.getZOffset() * 0.5);
            if (!WorldUtil.emptyBlocks.contains(neighborBlock) && WorldUtil.mc.player.getPositionEyes(WorldUtil.mc.getRenderPartialTicks()).distanceTo(vec) <= 4.25) {
                final float[] rot = { WorldUtil.mc.player.rotationYaw, WorldUtil.mc.player.rotationPitch };
                if (rotate) {
                    rotatePacket(vec.x, vec.y, vec.z);
                }
                if (WorldUtil.rightclickableBlocks.contains(neighborBlock)) {
                    WorldUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WorldUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                }
                WorldUtil.mc.playerController.processRightClickBlock(WorldUtil.mc.player, WorldUtil.mc.world, pos.offset(f), f.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
                if (WorldUtil.rightclickableBlocks.contains(neighborBlock)) {
                    WorldUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WorldUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                if (rotateBack) {
                    WorldUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rot[0], rot[1], WorldUtil.mc.player.onGround));
                }
                return true;
            }
        }
        return false;
    }
    
    public static boolean isBlockEmpty(final BlockPos pos) {
        if (!WorldUtil.emptyBlocks.contains(WorldUtil.mc.world.getBlockState(pos).getBlock())) {
            return false;
        }
        final AxisAlignedBB box = new AxisAlignedBB(pos);
        for (final Entity e : WorldUtil.mc.world.loadedEntityList) {
            if (e instanceof EntityLivingBase && box.intersects(e.getEntityBoundingBox())) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean canPlaceBlock(final BlockPos pos) {
        if (!isBlockEmpty(pos)) {
            return false;
        }
        final EnumFacing[] values;
        final EnumFacing[] facings = values = EnumFacing.values();
        for (final EnumFacing f : values) {
            if (!WorldUtil.emptyBlocks.contains(WorldUtil.mc.world.getBlockState(pos.offset(f)).getBlock()) && WorldUtil.mc.player.getPositionEyes(WorldUtil.mc.getRenderPartialTicks()).distanceTo(new Vec3d(pos.getX() + 0.5 + f.getXOffset() * 0.5, pos.getY() + 0.5 + f.getYOffset() * 0.5, pos.getZ() + 0.5 + f.getZOffset() * 0.5)) <= 4.25) {
                return true;
            }
        }
        return false;
    }
    
    public static EnumFacing getClosestFacing(final BlockPos pos) {
        return EnumFacing.DOWN;
    }
    
    public static void rotateClient(final double x, final double y, final double z) {
        final double diffX = x - WorldUtil.mc.player.posX;
        final double diffY = y - (WorldUtil.mc.player.posY + WorldUtil.mc.player.getEyeHeight());
        final double diffZ = z - WorldUtil.mc.player.posZ;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        final EntityPlayerSP player = WorldUtil.mc.player;
        player.rotationYaw += MathHelper.wrapDegrees(yaw - WorldUtil.mc.player.rotationYaw);
        final EntityPlayerSP player2 = WorldUtil.mc.player;
        player2.rotationPitch += MathHelper.wrapDegrees(pitch - WorldUtil.mc.player.rotationPitch);
    }
    
    public static void rotatePacket(final double x, final double y, final double z) {
        final double diffX = x - WorldUtil.mc.player.posX;
        final double diffY = y - (WorldUtil.mc.player.posY + WorldUtil.mc.player.getEyeHeight());
        final double diffZ = z - WorldUtil.mc.player.posZ;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        WorldUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, WorldUtil.mc.player.onGround));
    }
    
    static {
        mc = Minecraft.getMinecraft();
        WorldUtil.emptyBlocks = Arrays.asList(Blocks.AIR, (Block)Blocks.FLOWING_LAVA, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_WATER, (Block)Blocks.WATER, Blocks.VINE, Blocks.SNOW_LAYER, (Block)Blocks.TALLGRASS, (Block)Blocks.FIRE);
        WorldUtil.rightclickableBlocks = Arrays.asList((Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.ANVIL, Blocks.WOODEN_BUTTON, Blocks.STONE_BUTTON, (Block)Blocks.UNPOWERED_COMPARATOR, (Block)Blocks.UNPOWERED_REPEATER, (Block)Blocks.POWERED_REPEATER, (Block)Blocks.POWERED_COMPARATOR, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.BREWING_STAND, Blocks.DISPENSER, Blocks.DROPPER, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.JUKEBOX, (Block)Blocks.BEACON, Blocks.BED, Blocks.FURNACE, (Block)Blocks.OAK_DOOR, (Block)Blocks.SPRUCE_DOOR, (Block)Blocks.BIRCH_DOOR, (Block)Blocks.JUNGLE_DOOR, (Block)Blocks.ACACIA_DOOR, (Block)Blocks.DARK_OAK_DOOR, Blocks.CAKE, Blocks.ENCHANTING_TABLE, Blocks.DRAGON_EGG, (Block)Blocks.HOPPER, Blocks.REPEATING_COMMAND_BLOCK, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.CRAFTING_TABLE);
    }
}
