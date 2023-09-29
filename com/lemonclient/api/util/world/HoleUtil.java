//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.world.combat.*;
import java.util.*;

public class HoleUtil
{
    private static final Minecraft mc;
    
    public static boolean isInHole(final Entity e, final boolean onlyOneWide, final boolean ignoreDown, final boolean render) {
        return !isHole(new BlockPos(e.getPositionVector()), onlyOneWide, ignoreDown, render).getType().equals(HoleType.NONE);
    }
    
    public static boolean isHoleBlock(final BlockPos blockPos, final boolean onlyOneWide, final boolean ignoreDown, final boolean render) {
        return !isHole(blockPos, onlyOneWide, ignoreDown, render).getType().equals(HoleType.NONE) && !isHole(blockPos, onlyOneWide, ignoreDown, render).getType().equals(HoleType.CUSTOM);
    }
    
    public static BlockSafety isBlockSafe(final Block block) {
        if (block == Blocks.BEDROCK) {
            return BlockSafety.UNBREAKABLE;
        }
        if (block == Blocks.OBSIDIAN || block == Blocks.ENDER_CHEST || block == Blocks.ANVIL) {
            return BlockSafety.RESISTANT;
        }
        return BlockSafety.BREAKABLE;
    }
    
    public static HoleInfo isHole(final BlockPos centreBlock, final boolean onlyOneWide, final boolean ignoreDown, final boolean render) {
        final HoleInfo output = new HoleInfo();
        final HashMap<BlockOffset, BlockSafety> unsafeSides = getUnsafeSides(centreBlock);
        if (unsafeSides.containsKey(BlockOffset.DOWN) && unsafeSides.remove(BlockOffset.DOWN, BlockSafety.BREAKABLE) && !ignoreDown) {
            output.setSafety(BlockSafety.BREAKABLE);
            return output;
        }
        int size = unsafeSides.size();
        unsafeSides.entrySet().removeIf(entry -> entry.getValue() == BlockSafety.RESISTANT);
        if (unsafeSides.size() != size) {
            output.setSafety(BlockSafety.RESISTANT);
        }
        size = unsafeSides.size();
        if (size == 0) {
            output.setType(HoleType.SINGLE);
            output.setCentre(new AxisAlignedBB(centreBlock));
            return output;
        }
        if (size == 1 && !onlyOneWide) {
            return isDoubleHole(output, centreBlock, unsafeSides.keySet().stream().findFirst().get());
        }
        if (size == 2 && !onlyOneWide) {
            return isFourHole(output, centreBlock, render);
        }
        output.setSafety(BlockSafety.BREAKABLE);
        return output;
    }
    
    private static HoleInfo isDoubleHole(final HoleInfo info, final BlockPos centreBlock, final BlockOffset weakSide) {
        final BlockPos unsafePos = weakSide.offset(centreBlock);
        final HashMap<BlockOffset, BlockSafety> unsafeSides = getUnsafeSides(unsafePos);
        final int size = unsafeSides.size();
        unsafeSides.entrySet().removeIf(entry -> entry.getValue() == BlockSafety.RESISTANT);
        if (unsafeSides.size() != size) {
            info.setSafety(BlockSafety.RESISTANT);
        }
        if (unsafeSides.containsKey(BlockOffset.DOWN)) {
            info.setType(HoleType.CUSTOM);
            unsafeSides.remove(BlockOffset.DOWN);
        }
        if (unsafeSides.size() > 1) {
            info.setType(HoleType.NONE);
            return info;
        }
        final double minX = Math.min(centreBlock.getX(), unsafePos.getX());
        final double maxX = Math.max(centreBlock.getX(), unsafePos.getX()) + 1;
        final double minZ = Math.min(centreBlock.getZ(), unsafePos.getZ());
        final double maxZ = Math.max(centreBlock.getZ(), unsafePos.getZ()) + 1;
        info.setCentre(new AxisAlignedBB(minX, (double)centreBlock.getY(), minZ, maxX, (double)(centreBlock.getY() + 1), maxZ));
        if (info.getType() != HoleType.CUSTOM) {
            info.setType(HoleType.DOUBLE);
        }
        return info;
    }
    
    private static HoleInfo isFourHole(final HoleInfo info, final BlockPos centreBlock, final boolean render) {
        Label_0201: {
            if (render) {
                if (!HoleFinder.is2x2single(centreBlock, true)) {
                    break Label_0201;
                }
            }
            else if (!HoleFinder.is2x2(centreBlock, true)) {
                break Label_0201;
            }
            info.setSafety(BlockSafety.RESISTANT);
            double minX = centreBlock.getX();
            double maxX = centreBlock.getX() + 2;
            double minZ = centreBlock.getZ();
            double maxZ = centreBlock.getZ() + 2;
            if (!render) {
                BlockPos blockPos = centreBlock;
                if (HoleUtil.mc.world.isAirBlock(centreBlock.add(-1, 0, 0))) {
                    blockPos = blockPos.add(-1, 0, 0);
                }
                if (HoleUtil.mc.world.isAirBlock(centreBlock.add(0, 0, -1))) {
                    blockPos = blockPos.add(0, 0, -1);
                }
                minX = blockPos.getX();
                maxX = blockPos.getX() + 2;
                minZ = blockPos.getZ();
                maxZ = blockPos.getZ() + 2;
            }
            info.setCentre(new AxisAlignedBB(minX, (double)centreBlock.getY(), minZ, maxX, (double)(centreBlock.getY() + 1), maxZ));
            info.setType(HoleType.FOUR);
            return info;
        }
        info.setType(HoleType.NONE);
        return info;
    }
    
    public static HashMap<BlockOffset, BlockSafety> getUnsafeSides(final BlockPos pos) {
        final HashMap<BlockOffset, BlockSafety> output = new HashMap<BlockOffset, BlockSafety>();
        BlockSafety temp = isBlockSafe(HoleUtil.mc.world.getBlockState(BlockOffset.DOWN.offset(pos)).getBlock());
        if (temp != BlockSafety.UNBREAKABLE) {
            output.put(BlockOffset.DOWN, temp);
        }
        temp = isBlockSafe(HoleUtil.mc.world.getBlockState(BlockOffset.NORTH.offset(pos)).getBlock());
        if (temp != BlockSafety.UNBREAKABLE) {
            output.put(BlockOffset.NORTH, temp);
        }
        temp = isBlockSafe(HoleUtil.mc.world.getBlockState(BlockOffset.SOUTH.offset(pos)).getBlock());
        if (temp != BlockSafety.UNBREAKABLE) {
            output.put(BlockOffset.SOUTH, temp);
        }
        temp = isBlockSafe(HoleUtil.mc.world.getBlockState(BlockOffset.EAST.offset(pos)).getBlock());
        if (temp != BlockSafety.UNBREAKABLE) {
            output.put(BlockOffset.EAST, temp);
        }
        temp = isBlockSafe(HoleUtil.mc.world.getBlockState(BlockOffset.WEST.offset(pos)).getBlock());
        if (temp != BlockSafety.UNBREAKABLE) {
            output.put(BlockOffset.WEST, temp);
        }
        return output;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public enum BlockSafety
    {
        UNBREAKABLE, 
        RESISTANT, 
        BREAKABLE;
    }
    
    public enum HoleType
    {
        SINGLE, 
        DOUBLE, 
        CUSTOM, 
        FOUR, 
        NONE;
    }
    
    public static class HoleInfo
    {
        private HoleType type;
        private BlockSafety safety;
        private AxisAlignedBB centre;
        
        public HoleInfo() {
            this(BlockSafety.UNBREAKABLE, HoleType.NONE);
        }
        
        public HoleInfo(final BlockSafety safety, final HoleType type) {
            this.type = type;
            this.safety = safety;
        }
        
        public void setType(final HoleType type) {
            this.type = type;
        }
        
        public void setSafety(final BlockSafety safety) {
            this.safety = safety;
        }
        
        public void setCentre(final AxisAlignedBB centre) {
            this.centre = centre;
        }
        
        public HoleType getType() {
            return this.type;
        }
        
        public BlockSafety getSafety() {
            return this.safety;
        }
        
        public AxisAlignedBB getCentre() {
            return this.centre;
        }
    }
    
    public enum BlockOffset
    {
        DOWN(0, -1, 0), 
        UP(0, 1, 0), 
        NORTH(0, 0, -1), 
        EAST(1, 0, 0), 
        SOUTH(0, 0, 1), 
        WEST(-1, 0, 0);
        
        private final int x;
        private final int y;
        private final int z;
        
        private BlockOffset(final int x, final int y, final int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public BlockPos offset(final BlockPos pos) {
            return pos.add(this.x, this.y, this.z);
        }
        
        public BlockPos forward(final BlockPos pos, final int scale) {
            return pos.add(this.x * scale, 0, this.z * scale);
        }
        
        public BlockPos backward(final BlockPos pos, final int scale) {
            return pos.add(-this.x * scale, 0, -this.z * scale);
        }
        
        public BlockPos left(final BlockPos pos, final int scale) {
            return pos.add(this.z * scale, 0, -this.x * scale);
        }
        
        public BlockPos right(final BlockPos pos, final int scale) {
            return pos.add(-this.z * scale, 0, this.x * scale);
        }
    }
}
