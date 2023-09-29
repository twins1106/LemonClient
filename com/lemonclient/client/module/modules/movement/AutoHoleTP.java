//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.world.*;
import java.util.*;

@Module.Declaration(name = "0iqHoleTp", category = Category.Movement)
public class AutoHoleTP extends Module
{
    IntegerSetting range;
    BooleanSetting stopMotion;
    private static final BlockPos[] surroundOffset;
    public static List<BlockPos> midSafety;
    public static List<Block> unSolidBlocks;
    
    public AutoHoleTP() {
        this.range = this.registerInteger("Range", 1, 0, 6);
        this.stopMotion = this.registerBoolean("StopMotion", false);
    }
    
    public void onEnable() {
        final BlockPos hole = calcHoles().stream().min(Comparator.comparing(p -> AutoHoleTP.mc.player.getDistance((double)p.getX(), (double)p.getY(), (double)p.getZ()))).orElse(null);
        if (hole != null && AutoHoleTP.mc.player.getDistance((double)hole.getX(), (double)hole.getY(), (double)hole.getZ()) < (int)this.range.getValue() + 1.5) {
            AutoHoleTP.mc.player.setPosition(hole.getX() + 0.5, AutoHoleTP.mc.player.posY, hole.getZ() + 0.5);
            if (this.stopMotion.getValue()) {
                AutoHoleTP.mc.player.motionX = 0.0;
                AutoHoleTP.mc.player.motionZ = 0.0;
            }
            AutoHoleTP.mc.player.motionY = -3.0;
        }
        this.disable();
    }
    
    public static boolean isBlockUnSolid(final Block block) {
        return AutoHoleTP.unSolidBlocks.contains(block);
    }
    
    public static List<BlockPos> calcHoles() {
        final ArrayList<BlockPos> safeSpots = new ArrayList<BlockPos>();
        AutoHoleTP.midSafety.clear();
        final List<BlockPos> positions = (List<BlockPos>)CrystalUtil.getSphere(new BlockPos(AutoHoleTP.mc.player.posX, AutoHoleTP.mc.player.posY, AutoHoleTP.mc.player.posZ), 6.0f, 6, false, true, 0);
        for (final BlockPos pos : positions) {
            if (AutoHoleTP.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && AutoHoleTP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                if (!AutoHoleTP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                    continue;
                }
                boolean isSafe = true;
                boolean midSafe = true;
                for (final BlockPos offset : AutoHoleTP.surroundOffset) {
                    final Block block = AutoHoleTP.mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
                    if (isBlockUnSolid(block)) {
                        midSafe = false;
                    }
                    if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                        isSafe = false;
                    }
                }
                if (isSafe) {
                    safeSpots.add(pos);
                }
                if (!midSafe) {
                    continue;
                }
                AutoHoleTP.midSafety.add(pos);
            }
        }
        return safeSpots;
    }
    
    static {
        surroundOffset = BlockUtil.toBlockPos(EntityUtil.getOffsets(0, true));
        AutoHoleTP.midSafety = new ArrayList<BlockPos>();
        AutoHoleTP.unSolidBlocks = Arrays.asList((Block)Blocks.FLOWING_LAVA, Blocks.FLOWER_POT, Blocks.SNOW, Blocks.CARPET, Blocks.END_ROD, (Block)Blocks.SKULL, Blocks.FLOWER_POT, Blocks.TRIPWIRE, (Block)Blocks.TRIPWIRE_HOOK, Blocks.WOODEN_BUTTON, Blocks.LEVER, Blocks.STONE_BUTTON, Blocks.LADDER, (Block)Blocks.UNPOWERED_COMPARATOR, (Block)Blocks.POWERED_COMPARATOR, (Block)Blocks.UNPOWERED_REPEATER, (Block)Blocks.POWERED_REPEATER, Blocks.UNLIT_REDSTONE_TORCH, Blocks.REDSTONE_TORCH, (Block)Blocks.REDSTONE_WIRE, Blocks.AIR, (Block)Blocks.PORTAL, Blocks.END_PORTAL, (Block)Blocks.WATER, (Block)Blocks.FLOWING_WATER, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_LAVA, Blocks.SAPLING, (Block)Blocks.RED_FLOWER, (Block)Blocks.YELLOW_FLOWER, (Block)Blocks.BROWN_MUSHROOM, (Block)Blocks.RED_MUSHROOM, Blocks.WHEAT, Blocks.CARROTS, Blocks.POTATOES, Blocks.BEETROOTS, (Block)Blocks.REEDS, Blocks.PUMPKIN_STEM, Blocks.MELON_STEM, Blocks.WATERLILY, Blocks.NETHER_WART, Blocks.COCOA, Blocks.CHORUS_FLOWER, Blocks.CHORUS_PLANT, (Block)Blocks.TALLGRASS, (Block)Blocks.DEADBUSH, Blocks.VINE, (Block)Blocks.FIRE, Blocks.RAIL, Blocks.ACTIVATOR_RAIL, Blocks.DETECTOR_RAIL, Blocks.GOLDEN_RAIL, Blocks.TORCH);
    }
}
