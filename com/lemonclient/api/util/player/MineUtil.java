//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.player;

import net.minecraft.util.math.*;
import com.lemonclient.mixin.mixins.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.enchantment.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.init.*;
import java.util.*;

public class MineUtil
{
    public static boolean canHarvestBlock(final BlockPos pos, final ItemStack stack) {
        IBlockState state = BurrowUtil.mc.world.getBlockState(pos);
        state = state.getActualState((IBlockAccess)BurrowUtil.mc.world, pos);
        final Block block = state.getBlock();
        if (state.getMaterial().isToolNotRequired()) {
            return true;
        }
        if (stack.isEmpty()) {
            return stack.canHarvestBlock(state);
        }
        final String tool = ((IBlock)block).getHarvestToolNonForge(state);
        if (tool == null) {
            return stack.canHarvestBlock(state);
        }
        int toolLevel = -1;
        if (stack.getItem() instanceof IItemTool) {
            String toolClass = null;
            if (stack.getItem() instanceof ItemPickaxe) {
                toolClass = "pickaxe";
            }
            else if (stack.getItem() instanceof ItemAxe) {
                toolClass = "axe";
            }
            else if (stack.getItem() instanceof ItemSpade) {
                toolClass = "shovel";
            }
            if (tool.equals(toolClass)) {
                toolLevel = ((IItemTool)stack.getItem()).getToolMaterial().getHarvestLevel();
            }
        }
        if (toolLevel < 0) {
            return stack.canHarvestBlock(state);
        }
        return toolLevel >= ((IBlock)block).getHarvestLevelNonForge(state);
    }
    
    public static int findBestTool(final BlockPos pos) {
        return findBestTool(pos, BurrowUtil.mc.world.getBlockState(pos));
    }
    
    public static int findBestTool(final BlockPos pos, final IBlockState state) {
        int result = BurrowUtil.mc.player.inventory.currentItem;
        if (state.getBlockHardness((World)BurrowUtil.mc.world, pos) > 0.0f) {
            double speed = getSpeed(state, BurrowUtil.mc.player.getHeldItemMainhand());
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = BurrowUtil.mc.player.inventory.getStackInSlot(i);
                final double stackSpeed = getSpeed(state, stack);
                if (stackSpeed > speed) {
                    speed = stackSpeed;
                    result = i;
                }
            }
        }
        return result;
    }
    
    public static double getSpeed(final IBlockState state, final ItemStack stack) {
        final double str = stack.getDestroySpeed(state);
        final int effect = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
        return Math.max(str + ((str > 1.0) ? (effect * effect + 1.0) : 0.0), 0.0);
    }
    
    public static float getDamage(final ItemStack stack, final BlockPos pos, final boolean onGround) {
        final IBlockState state = BurrowUtil.mc.world.getBlockState(pos);
        return getDamage(state, stack, pos, onGround);
    }
    
    public static float getDamage(final IBlockState state, final ItemStack stack, final BlockPos pos, final boolean onGround) {
        return getDigSpeed(stack, state, onGround) / (state.getBlockHardness((World)BurrowUtil.mc.world, pos) * (canHarvestBlock(pos, stack) ? 30 : 100));
    }
    
    private static float getDigSpeed(final ItemStack stack, final IBlockState state, final boolean onGround) {
        float digSpeed = 1.0f;
        if (!stack.isEmpty()) {
            digSpeed *= stack.getDestroySpeed(state);
        }
        if (digSpeed > 1.0f) {
            final int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
            if (i > 0 && !stack.isEmpty()) {
                digSpeed += i * i + 1;
            }
        }
        if (BurrowUtil.mc.player.isPotionActive(MobEffects.HASTE)) {
            digSpeed *= 1.0f + (BurrowUtil.mc.player.getActivePotionEffect(MobEffects.HASTE).getAmplifier() + 1) * 0.2f;
        }
        if (BurrowUtil.mc.player.isPotionActive(MobEffects.MINING_FATIGUE)) {
            float miningFatigue = 0.0f;
            switch (BurrowUtil.mc.player.getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier()) {
                case 0: {
                    miningFatigue = 0.3f;
                    break;
                }
                case 1: {
                    miningFatigue = 0.09f;
                    break;
                }
                case 2: {
                    miningFatigue = 0.0027f;
                    break;
                }
                default: {
                    miningFatigue = 8.1E-4f;
                    break;
                }
            }
            digSpeed *= miningFatigue;
        }
        if (BurrowUtil.mc.player.isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier((EntityLivingBase)BurrowUtil.mc.player)) {
            digSpeed /= 5.0f;
        }
        if (onGround && !BurrowUtil.mc.player.onGround) {
            digSpeed /= 5.0f;
        }
        return (digSpeed < 0.0f) ? 0.0f : digSpeed;
    }
    
    public static boolean canBreak(final BlockPos pos) {
        return canBreak(BurrowUtil.mc.world.getBlockState(pos), pos);
    }
    
    public static boolean canBreak(final IBlockState state, final BlockPos pos) {
        return state.getBlockHardness((World)BurrowUtil.mc.world, pos) != -1.0f || state.getMaterial().isLiquid();
    }
    
    private static void setupHarvestLevels() {
        Set<Block> blocks = (Set<Block>)ReflectionUtil.getField((Class)ItemPickaxe.class, (Object)null, 0);
        for (final Block block : blocks) {
            ((IBlock)block).setHarvestLevelNonForge("pickaxe", 0);
        }
        blocks = (Set<Block>)ReflectionUtil.getField((Class)ItemSpade.class, (Object)null, 0);
        for (final Block block : blocks) {
            ((IBlock)block).setHarvestLevelNonForge("shovel", 0);
        }
        blocks = (Set<Block>)ReflectionUtil.getField((Class)ItemAxe.class, (Object)null, 0);
        for (final Block block : blocks) {
            ((IBlock)block).setHarvestLevelNonForge("axe", 0);
        }
        ((IBlock)Blocks.OBSIDIAN).setHarvestLevelNonForge("pickaxe", 3);
        ((IBlock)Blocks.ENCHANTING_TABLE).setHarvestLevelNonForge("pickaxe", 0);
        final Block[] array;
        final Block[] oreBlocks = array = new Block[] { Blocks.EMERALD_ORE, Blocks.EMERALD_BLOCK, Blocks.DIAMOND_ORE, Blocks.DIAMOND_BLOCK, Blocks.GOLD_ORE, Blocks.GOLD_BLOCK, Blocks.REDSTONE_ORE, Blocks.LIT_REDSTONE_ORE };
        for (final Block block2 : array) {
            ((IBlock)block2).setHarvestLevelNonForge("pickaxe", 2);
        }
        ((IBlock)Blocks.IRON_ORE).setHarvestLevelNonForge("pickaxe", 1);
        ((IBlock)Blocks.IRON_BLOCK).setHarvestLevelNonForge("pickaxe", 1);
        ((IBlock)Blocks.LAPIS_ORE).setHarvestLevelNonForge("pickaxe", 1);
        ((IBlock)Blocks.LAPIS_BLOCK).setHarvestLevelNonForge("pickaxe", 1);
        ((IBlock)Blocks.QUARTZ_ORE).setHarvestLevelNonForge("pickaxe", 0);
    }
    
    static {
        setupHarvestLevels();
    }
    
    public interface IBlock
    {
        void setHarvestLevelNonForge(final String p0, final int p1);
        
        String getHarvestToolNonForge(final IBlockState p0);
        
        int getHarvestLevelNonForge(final IBlockState p0);
    }
}
