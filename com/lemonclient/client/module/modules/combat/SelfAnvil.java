//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.network.play.client.*;
import java.util.*;

@Module.Declaration(name = "SelfAnvil", category = Category.Combat)
public class SelfAnvil extends Module
{
    BooleanSetting packetPlace;
    BooleanSetting silentSwitch;
    BooleanSetting entityCheck;
    BooleanSetting crystalOnly;
    public BlockPos basePos;
    public int stage;
    public static List<Block> rightclickableBlocks;
    private EnumHand oldhand;
    private int oldslot;
    
    public SelfAnvil() {
        this.packetPlace = this.registerBoolean("Packet Place", true);
        this.silentSwitch = this.registerBoolean("Switch Back", true);
        this.entityCheck = this.registerBoolean("Entity Check", true);
        this.crystalOnly = this.registerBoolean("Crystal Only", true);
        this.oldhand = null;
        this.oldslot = -1;
    }
    
    public void onEnable() {
        this.basePos = null;
        this.stage = 0;
    }
    
    public void onUpdate() {
        final int obby = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
        final int anvil = BurrowUtil.findHotbarBlock((Class)BlockAnvil.class);
        if (obby == -1 || anvil == -1) {
            this.disable();
            return;
        }
        final BlockPos anvilPos = PlayerUtil.getPlayerPos().add(0, 2, 0);
        if (!BlockUtil.hasNeighbour(anvilPos)) {
            if ((this.basePos = this.findPos()) == null) {
                this.disable();
                return;
            }
            this.setItem(obby);
            final BlockPos pos0 = this.basePos.add(0, 1, 0);
            final BlockPos pos2 = this.basePos.add(0, 2, 0);
            placeBlock(pos0, (boolean)this.packetPlace.getValue());
            rightClickBlock(pos0, EnumFacing.UP, (boolean)this.packetPlace.getValue());
            this.setItem(anvil);
            EnumFacing facing = null;
            for (final EnumFacing f : EnumFacing.values()) {
                if (pos2.add(f.getDirectionVec()).equals((Object)anvilPos)) {
                    facing = f;
                }
            }
            rightClickBlock(anvilPos, facing, (boolean)this.packetPlace.getValue());
            this.restoreItem();
            this.disable();
        }
        else {
            this.setItem(anvil);
            placeBlock(anvilPos, (boolean)this.packetPlace.getValue());
            this.restoreItem();
            this.disable();
        }
    }
    
    public static EnumFacing getBackwardFacing(final EnumFacing facing) {
        final Vec3i vec = facing.getDirectionVec();
        return EnumFacing.getFacingFromVector((float)(vec.getX() * -1), (float)(vec.getY() * -1), (float)(vec.getZ() * -1));
    }
    
    public static EnumFacing getLookingFacing() {
        switch (MathHelper.floor(SelfAnvil.mc.player.rotationYaw * 8.0f / 360.0f + 0.5) & 0x7) {
            case 0:
            case 1: {
                return EnumFacing.SOUTH;
            }
            case 2:
            case 3: {
                return EnumFacing.WEST;
            }
            case 4:
            case 5: {
                return EnumFacing.NORTH;
            }
            case 6:
            case 7: {
                return EnumFacing.EAST;
            }
            default: {
                return EnumFacing.EAST;
            }
        }
    }
    
    public static boolean canRightClickForPlace(final BlockPos pos) {
        return !SelfAnvil.rightclickableBlocks.contains(getBlock(pos));
    }
    
    public BlockPos findPos() {
        final BlockPos playerPos = PlayerUtil.getPlayerPos();
        final BlockPos lookingPos = playerPos.add(getBackwardFacing(getLookingFacing()).getDirectionVec());
        final List<BlockPos> possiblePlacePositions = new ArrayList<BlockPos>();
        final BlockPos[] array;
        final BlockPos[] offsets = array = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        for (final BlockPos offset : array) {
            final BlockPos pos = playerPos.add((Vec3i)offset);
            if (BlockUtil.getBlock(pos) != Blocks.AIR) {
                if (canRightClickForPlace(pos)) {
                    final BlockPos pos2 = pos.add(0, 1, 0);
                    if (!this.entityCheck(pos2)) {
                        final BlockPos pos3 = pos2.add(0, 1, 0);
                        if (!this.entityCheck(pos3)) {
                            final BlockPos anvil = playerPos.add(0, 2, 0);
                            if (!this.entityCheck(anvil)) {
                                possiblePlacePositions.add(pos);
                            }
                        }
                    }
                }
            }
        }
        return possiblePlacePositions.stream().min(Comparator.comparing(b -> lookingPos.getDistance(b.getX(), b.getY(), b.getZ()))).orElse(null);
    }
    
    public boolean entityCheck(final BlockPos pos) {
        if (!(boolean)this.entityCheck.getValue()) {
            return false;
        }
        for (final Entity e : SelfAnvil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
            if (!(e instanceof EntityEnderCrystal) && (boolean)this.crystalOnly.getValue()) {
                continue;
            }
            return true;
        }
        return false;
    }
    
    public void setItem(final int slot) {
        if (this.silentSwitch.getValue()) {
            this.oldhand = null;
            if (SelfAnvil.mc.player.isHandActive()) {
                this.oldhand = SelfAnvil.mc.player.getActiveHand();
            }
            this.oldslot = SelfAnvil.mc.player.inventory.currentItem;
            SelfAnvil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            SelfAnvil.mc.player.inventory.currentItem = slot;
            SelfAnvil.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.oldslot != -1 && (boolean)this.silentSwitch.getValue()) {
            if (this.oldhand != null) {
                SelfAnvil.mc.player.setActiveHand(this.oldhand);
            }
            SelfAnvil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
            this.oldslot = -1;
            this.oldhand = null;
        }
    }
    
    public static boolean placeBlock(final BlockPos pos, final boolean packet) {
        final Block block = SelfAnvil.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        final EnumFacing side = getPlaceableSide(pos);
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!canBeClicked(neighbour)) {
            return false;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        boolean sneak = false;
        if ((BlockUtil.blackList.contains(SelfAnvil.mc.world.getBlockState(neighbour).getBlock()) || BlockUtil.shulkerList.contains(SelfAnvil.mc.world.getBlockState(neighbour).getBlock())) && !SelfAnvil.mc.player.isSneaking()) {
            SelfAnvil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)SelfAnvil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            SelfAnvil.mc.player.setSneaking(true);
            sneak = true;
        }
        if (packet) {
            rightClickBlock(neighbour, hitVec, EnumHand.MAIN_HAND, opposite);
        }
        else {
            SelfAnvil.mc.playerController.processRightClickBlock(SelfAnvil.mc.player, SelfAnvil.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
            SelfAnvil.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        if (sneak) {
            SelfAnvil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)SelfAnvil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            SelfAnvil.mc.player.setSneaking(false);
        }
        return true;
    }
    
    public static EnumFacing getPlaceableSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (SelfAnvil.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(SelfAnvil.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = SelfAnvil.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable() && !BlockUtil.blackList.contains(getBlock(neighbour))) {
                    return side;
                }
            }
        }
        return null;
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static Block getBlock(final double x, final double y, final double z) {
        return SelfAnvil.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    
    private static IBlockState getState(final BlockPos pos) {
        return SelfAnvil.mc.world.getBlockState(pos);
    }
    
    public static void rightClickBlock(final BlockPos pos, final EnumFacing facing, final boolean packet) {
        final Vec3d hitVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5).add(new Vec3d(facing.getDirectionVec()).scale(0.5));
        if (packet) {
            rightClickBlock(pos, hitVec, EnumHand.MAIN_HAND, facing);
        }
        else {
            SelfAnvil.mc.playerController.processRightClickBlock(SelfAnvil.mc.player, SelfAnvil.mc.world, pos, facing, hitVec, EnumHand.MAIN_HAND);
            SelfAnvil.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction) {
        final float f = (float)(vec.x - pos.getX());
        final float f2 = (float)(vec.y - pos.getY());
        final float f3 = (float)(vec.z - pos.getZ());
        SelfAnvil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
        SelfAnvil.mc.rightClickDelayTimer = 4;
    }
    
    static {
        SelfAnvil.rightclickableBlocks = Arrays.asList((Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.ANVIL, Blocks.WOODEN_BUTTON, Blocks.STONE_BUTTON, (Block)Blocks.UNPOWERED_COMPARATOR, (Block)Blocks.UNPOWERED_REPEATER, (Block)Blocks.POWERED_REPEATER, (Block)Blocks.POWERED_COMPARATOR, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.BREWING_STAND, Blocks.DISPENSER, Blocks.DROPPER, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.JUKEBOX, (Block)Blocks.BEACON, Blocks.BED, Blocks.FURNACE, (Block)Blocks.OAK_DOOR, (Block)Blocks.SPRUCE_DOOR, (Block)Blocks.BIRCH_DOOR, (Block)Blocks.JUNGLE_DOOR, (Block)Blocks.ACACIA_DOOR, (Block)Blocks.DARK_OAK_DOOR, Blocks.CAKE, Blocks.ENCHANTING_TABLE, Blocks.DRAGON_EGG, (Block)Blocks.HOPPER, Blocks.REPEATING_COMMAND_BLOCK, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.CRAFTING_TABLE);
    }
}
