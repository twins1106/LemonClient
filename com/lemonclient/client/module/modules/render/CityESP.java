//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.stream.*;
import com.lemonclient.api.util.player.social.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.item.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import com.lemonclient.api.event.events.*;
import java.util.concurrent.atomic.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import java.util.function.*;
import com.lemonclient.api.util.render.*;
import java.util.*;

@Module.Declaration(name = "CityESP", category = Category.Render)
public class CityESP extends Module
{
    IntegerSetting range;
    IntegerSetting down;
    IntegerSetting sides;
    IntegerSetting depth;
    DoubleSetting minDamage;
    DoubleSetting maxDamage;
    BooleanSetting ignoreCrystals;
    BooleanSetting mine;
    BooleanSetting automine;
    BooleanSetting switchPick;
    BooleanSetting swing;
    ModeSetting breakBlock;
    DoubleSetting distanceMine;
    ModeSetting targetMode;
    ModeSetting selectMode;
    ModeSetting renderMode;
    IntegerSetting width;
    ColorSetting color;
    private final HashMap<EntityPlayer, List<BlockPos>> cityable;
    BlockPos breaking;
    BlockPos targetPos;
    
    public CityESP() {
        this.range = this.registerInteger("Range", 20, 1, 30);
        this.down = this.registerInteger("Down", 1, 0, 3);
        this.sides = this.registerInteger("Sides", 1, 0, 4);
        this.depth = this.registerInteger("Depth", 3, 0, 10);
        this.minDamage = this.registerDouble("Min Damage", 5.0, 0.0, 10.0);
        this.maxDamage = this.registerDouble("Max Self Damage", 7.0, 0.0, 20.0);
        this.ignoreCrystals = this.registerBoolean("Ignore Crystals", true);
        this.mine = this.registerBoolean("Shift Mine", false);
        this.automine = this.registerBoolean("Auto Mine", false);
        this.switchPick = this.registerBoolean("Switch Pick", true);
        this.swing = this.registerBoolean("Swing", true);
        this.breakBlock = this.registerMode("Break Block", (List)Arrays.asList("Normal", "Packet"), "Packet");
        this.distanceMine = this.registerDouble("Distance Mine", 5.0, 0.0, 10.0);
        this.targetMode = this.registerMode("Target", (List)Arrays.asList("Single", "All"), "Single");
        this.selectMode = this.registerMode("Select", (List)Arrays.asList("Closest", "All"), "Closest");
        this.renderMode = this.registerMode("Render", (List)Arrays.asList("Outline", "Fill", "Both"), "Both");
        this.width = this.registerInteger("Width", 1, 1, 10);
        this.color = this.registerColor("Color", new GSColor(102, 51, 153));
        this.cityable = new HashMap<EntityPlayer, List<BlockPos>>();
    }
    
    public void onUpdate() {
        if (CityESP.mc.player == null || CityESP.mc.world == null) {
            return;
        }
        this.cityable.clear();
        final List<EntityPlayer> players = (List<EntityPlayer>)CityESP.mc.world.playerEntities.stream().filter(entityPlayer -> entityPlayer.getDistanceSq((Entity)CityESP.mc.player) <= (int)this.range.getValue() * (int)this.range.getValue()).filter(entityPlayer -> !EntityUtil.basicChecksEntity(entityPlayer)).collect(Collectors.toList());
        for (final EntityPlayer player : players) {
            if (player != CityESP.mc.player) {
                if (SocialManager.isFriend(player.getName())) {
                    continue;
                }
                List<BlockPos> blocks = (List<BlockPos>)EntityUtil.getBlocksIn((Entity)player);
                if (blocks.size() == 0) {
                    continue;
                }
                int minY = Integer.MAX_VALUE;
                for (final BlockPos block : blocks) {
                    final int y = block.getY();
                    if (y < minY) {
                        minY = y;
                    }
                }
                if (player.posY % 1.0 > 0.2) {
                    ++minY;
                }
                final int finalMinY = minY;
                blocks = blocks.stream().filter(blockPos -> blockPos.getY() == finalMinY).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
                final Optional<BlockPos> any = blocks.stream().findAny();
                if (!any.isPresent()) {
                    continue;
                }
                final HoleUtil.HoleInfo holeInfo = HoleUtil.isHole((BlockPos)any.get(), false, true, false);
                if (holeInfo.getType() == HoleUtil.HoleType.NONE) {
                    continue;
                }
                if (holeInfo.getSafety() == HoleUtil.BlockSafety.UNBREAKABLE) {
                    continue;
                }
                final List<BlockPos> sides = new ArrayList<BlockPos>();
                for (final BlockPos block2 : blocks) {
                    sides.addAll(this.cityableSides(block2, HoleUtil.getUnsafeSides(block2).keySet(), player));
                }
                if (sides.size() <= 0) {
                    continue;
                }
                this.cityable.put(player, sides);
            }
        }
        if ((boolean)this.mine.getValue() && (CityESP.mc.player.isSneaking() || (boolean)this.automine.getValue())) {
            for (final List<BlockPos> poss : this.cityable.values()) {
                boolean found = false;
                for (final BlockPos block3 : poss) {
                    if (CityESP.mc.player.getDistance((double)block3.x, (double)block3.y, (double)block3.z) <= (double)this.distanceMine.getValue()) {
                        if (this.targetPos != null && !CityESP.mc.world.isAirBlock(this.targetPos)) {
                            return;
                        }
                        this.targetPos = block3;
                        found = true;
                        if (CityESP.mc.player.getHeldItemMainhand().getItem() != Items.DIAMOND_PICKAXE && (boolean)this.switchPick.getValue()) {
                            final int slot = InventoryUtil.findFirstItemSlot((Class)ItemPickaxe.class, 0, 9);
                            if (slot != 1) {
                                CityESP.mc.player.inventory.currentItem = slot;
                            }
                        }
                        if (this.targetPos != this.breaking) {
                            if (this.swing.getValue()) {
                                CityESP.mc.player.swingArm(EnumHand.MAIN_HAND);
                            }
                            if (((String)this.breakBlock.getValue()).equals("Packet")) {
                                CityESP.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.targetPos, EnumFacing.UP));
                                CityESP.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.targetPos, EnumFacing.UP));
                            }
                            else {
                                CityESP.mc.playerController.onPlayerDamageBlock(this.targetPos, EnumFacing.UP);
                            }
                            this.breaking = this.targetPos;
                            break;
                        }
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        }
    }
    
    public void onWorldRender(final RenderEvent event) {
        final AtomicBoolean noRender = new AtomicBoolean(false);
        final AtomicBoolean atomicBoolean;
        this.cityable.entrySet().stream().sorted((entry, entry1) -> (int)entry.getKey().getDistanceSq((Entity)entry1.getKey())).forEach(entry -> {
            if (!atomicBoolean.get()) {
                this.renderBoxes(entry.getValue());
                if (((String)this.targetMode.getValue()).equalsIgnoreCase("All")) {
                    atomicBoolean.set(true);
                }
            }
        });
    }
    
    private List<BlockPos> cityableSides(final BlockPos centre, final Set<HoleUtil.BlockOffset> weakSides, final EntityPlayer player) {
        final List<BlockPos> cityableSides = new ArrayList<BlockPos>();
        final HashMap<BlockPos, HoleUtil.BlockOffset> directions = new HashMap<BlockPos, HoleUtil.BlockOffset>();
        for (final HoleUtil.BlockOffset weakSide : weakSides) {
            final BlockPos pos = weakSide.offset(centre);
            if (CityESP.mc.world.getBlockState(pos).getBlock() != Blocks.AIR) {
                directions.put(pos, weakSide);
            }
        }
        BlockPos pos2;
        BlockPos pos3;
        List<BlockPos> square;
        IBlockState holder;
        final Iterator<BlockPos> iterator2;
        BlockPos pos4;
        final List<BlockPos> list;
        directions.forEach((blockPos, blockOffset) -> {
            if (blockOffset == HoleUtil.BlockOffset.DOWN) {
                return;
            }
            else {
                pos2 = blockOffset.left(blockPos.down((int)this.down.getValue()), (int)this.sides.getValue());
                pos3 = blockOffset.forward(blockOffset.right(blockPos, (int)this.sides.getValue()), (int)this.depth.getValue());
                square = (List<BlockPos>)EntityUtil.getSquare(pos2, pos3);
                holder = CityESP.mc.world.getBlockState(blockPos);
                CityESP.mc.world.setBlockToAir(blockPos);
                square.iterator();
                while (iterator2.hasNext()) {
                    pos4 = iterator2.next();
                    if (this.canPlaceCrystal(pos4.down(), (boolean)this.ignoreCrystals.getValue()) && DamageUtil.calculateDamage(pos4.getX() + 0.5, (double)pos4.getY(), pos4.getZ() + 0.5, (Entity)player) >= (double)this.minDamage.getValue()) {
                        if (DamageUtil.calculateDamage(pos4.getX() + 0.5, (double)pos4.getY(), pos4.getZ() + 0.5, (Entity)CityESP.mc.player) <= (double)this.maxDamage.getValue()) {
                            list.add(blockPos);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                }
                CityESP.mc.world.setBlockState(blockPos, holder);
                return;
            }
        });
        return cityableSides;
    }
    
    private boolean canPlaceCrystal(final BlockPos blockPos, final boolean ignoreCrystal) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(boost, boost2);
        if (CityESP.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && CityESP.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        if (CityESP.mc.world.getBlockState(boost).getBlock() != Blocks.AIR) {
            return false;
        }
        if (CityESP.mc.world.getBlockState(boost2).getBlock() != Blocks.AIR) {
            return false;
        }
        if (!ignoreCrystal) {
            return CityESP.mc.world.getEntitiesWithinAABB((Class)Entity.class, axisAlignedBB).isEmpty();
        }
        final List<Entity> entityList = (List<Entity>)CityESP.mc.world.getEntitiesWithinAABB((Class)Entity.class, axisAlignedBB);
        entityList.removeIf(entity -> entity instanceof EntityEnderCrystal);
        return entityList.isEmpty();
    }
    
    private void renderBoxes(final List<BlockPos> blockPosList) {
        final String s = (String)this.selectMode.getValue();
        switch (s) {
            case "Closest": {
                blockPosList.stream().min(Comparator.comparing(blockPos -> blockPos.distanceSq((double)(int)CityESP.mc.player.posX, (double)(int)CityESP.mc.player.posY, (double)(int)CityESP.mc.player.posZ))).ifPresent((Consumer<? super Object>)this::renderBox);
                break;
            }
            case "All": {
                for (final BlockPos blockPos2 : blockPosList) {
                    this.renderBox(blockPos2);
                }
                break;
            }
        }
    }
    
    private void renderBox(final BlockPos blockPos) {
        final GSColor gsColor1 = new GSColor(this.color.getValue(), 255);
        final GSColor gsColor2 = new GSColor(this.color.getValue(), 50);
        final String s = (String)this.renderMode.getValue();
        switch (s) {
            case "Both": {
                RenderUtil.drawBox(blockPos, 1.0, gsColor2, 63);
                RenderUtil.drawBoundingBox(blockPos, 1.0, (float)(int)this.width.getValue(), gsColor1);
                break;
            }
            case "Outline": {
                RenderUtil.drawBoundingBox(blockPos, 1.0, (float)(int)this.width.getValue(), gsColor1);
                break;
            }
            case "Fill": {
                RenderUtil.drawBox(blockPos, 1.0, gsColor2, 63);
                break;
            }
        }
    }
}
