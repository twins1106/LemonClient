//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.api.util.render.*;

@Module.Declaration(name = "BedCev", category = Category.Dev)
public class BedCev extends Module
{
    public int oldSlot;
    BooleanSetting swing;
    BooleanSetting packet;
    DoubleSetting range;
    BooleanSetting autoDis;
    ColorSetting color;
    IntegerSetting ufoAlpha;
    IntegerSetting Alpha;
    List<BlockPos> blocks;
    boolean flag;
    int progress;
    int firsttime;
    int pickaxeitem;
    int crystalitem;
    int ObiItem;
    int currentitem;
    private BlockPos cobi;
    EntityPlayer entityPlayer;
    
    public BedCev() {
        this.swing = this.registerBoolean("Swing", false);
        this.packet = this.registerBoolean("Packet", false);
        this.range = this.registerDouble("Range", 5.0, 0.0, 6.0);
        this.autoDis = this.registerBoolean("Auto Disable", false);
        this.color = this.registerColor("Color", new GSColor(120, 0, 0));
        this.ufoAlpha = this.registerInteger("Alpha", 120, 0, 255);
        this.Alpha = this.registerInteger("Outline Alpha", 255, 0, 255);
        this.blocks = new ArrayList<BlockPos>();
    }
    
    public void onEnable() {
        this.progress = 0;
        this.flag = false;
        this.firsttime = 0;
        this.cobi = null;
    }
    
    private boolean block(final BlockPos pos) {
        return BedCev.mc.world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN || BedCev.mc.world.getBlockState(pos).getBlock() == Blocks.BEDROCK;
    }
    
    private boolean canPlaceBed(final BlockPos blockPos) {
        return (this.block(blockPos.east()) && (BedCev.mc.world.isAirBlock(blockPos.east().up()) || BedCev.mc.world.getBlockState(blockPos.east().up()).getBlock() == Blocks.BED)) || (this.block(blockPos.north()) && (BedCev.mc.world.isAirBlock(blockPos.north().up()) || BedCev.mc.world.getBlockState(blockPos.east().up()).getBlock() == Blocks.BED)) || (this.block(blockPos.west()) && (BedCev.mc.world.isAirBlock(blockPos.west().up()) || BedCev.mc.world.getBlockState(blockPos.east().up()).getBlock() == Blocks.BED)) || (this.block(blockPos.south()) && (BedCev.mc.world.isAirBlock(blockPos.south().up()) || BedCev.mc.world.getBlockState(blockPos.east().up()).getBlock() == Blocks.BED));
    }
    
    private boolean validposition(final BlockPos blockPos) {
        return BedCev.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && (BedCev.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock() == Blocks.AIR || BedCev.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock() == Blocks.BED) && !this.intersectsWithEntity(blockPos) && this.canPlaceBed(blockPos);
    }
    
    public static int findHotbarBlock(final Class<?> clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = BedCev.mc.player.inventory.getStackInSlot(i);
            if (itemStack != ItemStack.EMPTY) {
                if (clazz.isInstance(itemStack.getItem())) {
                    return i;
                }
                if (itemStack.getItem() instanceof ItemBlock) {
                    if (clazz.isInstance(((ItemBlock)itemStack.getItem()).getBlock())) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : BedCev.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    public static void switchToHotbarSlot(final int n, final boolean bl) {
        if (BedCev.mc.player == null || BedCev.mc.world == null) {
            return;
        }
        try {
            if (BedCev.mc.player.inventory.currentItem == n || n < 0) {
                return;
            }
            if (bl) {
                BedCev.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
                BedCev.mc.playerController.updateController();
            }
            else {
                BedCev.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
                BedCev.mc.player.inventory.currentItem = n;
                BedCev.mc.playerController.updateController();
            }
        }
        catch (Exception ex) {}
    }
    
    private void placeBlock(final BlockPos blockPos, final boolean bl2) {
        final EnumFacing enumFacing = BurrowUtil.getFirstFacing(blockPos);
        if (enumFacing == null) {
            return;
        }
        final BlockPos blockPos2 = blockPos.offset(enumFacing);
        final EnumFacing enumFacing2 = enumFacing.getOpposite();
        final Vec3d vec3d = new Vec3d((Vec3i)blockPos2).add(0.5, 0.5, 0.5).add(new Vec3d(enumFacing2.getDirectionVec()).scale(0.5));
        final Block block = BedCev.mc.world.getBlockState(blockPos2).getBlock();
        if (!BedCev.mc.player.isSneaking() && (BlockUtil.blackList.contains(block) || BlockUtil.shulkerList.contains(block))) {
            BedCev.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedCev.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            BedCev.mc.player.setSneaking(true);
        }
        this.rightClickBlock(blockPos2, vec3d, enumFacing2, bl2);
        BedCev.mc.rightClickDelayTimer = 4;
    }
    
    private void rightClickBlock(final BlockPos blockPos, final Vec3d vec3d, final EnumFacing enumFacing, final boolean bl) {
        if (bl) {
            BedCev.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(blockPos, enumFacing, EnumHand.MAIN_HAND, 0.5f, 1.0f, 0.5f));
        }
        else {
            BedCev.mc.playerController.processRightClickBlock(BedCev.mc.player, BedCev.mc.world, blockPos, enumFacing, vec3d, EnumHand.MAIN_HAND);
        }
        if (this.swing.getValue()) {
            BedCev.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        BedCev.mc.rightClickDelayTimer = 4;
    }
    
    private void reset() {
        this.firsttime = 0;
        this.cobi = null;
        this.flag = false;
    }
    
    @SubscribeEvent
    public void onUpdate() {
        if (BedCev.mc.world == null || BedCev.mc.player == null || BedCev.mc.player.isDead) {
            this.reset();
            if (this.autoDis.getValue()) {
                this.disable();
            }
            return;
        }
        this.oldSlot = BedCev.mc.player.inventory.currentItem;
        this.pickaxeitem = findHotbarBlock(ItemPickaxe.class);
        this.crystalitem = findHotbarBlock(ItemBed.class);
        this.ObiItem = findHotbarBlock(BlockObsidian.class);
        this.currentitem = BedCev.mc.player.inventory.currentItem;
        if (this.pickaxeitem == -1 || this.crystalitem == -1 || this.ObiItem == -1) {
            return;
        }
        this.entityPlayer = PlayerUtil.getNearestPlayer((double)this.range.getValue());
        if (this.entityPlayer == null) {
            this.reset();
            if (this.autoDis.getValue()) {
                this.disable();
            }
            return;
        }
        final BlockPos blockPos = new BlockPos((Entity)this.entityPlayer).add(-1.0, 1.0, 0.0);
        final BlockPos blockPos2 = new BlockPos((Entity)this.entityPlayer).add(0.0, 1.0, 1.0);
        final BlockPos blockPos3 = new BlockPos((Entity)this.entityPlayer).add(0.0, 1.0, -1.0);
        final BlockPos blockPos4 = new BlockPos((Entity)this.entityPlayer).add(1.0, 1.0, 0.0);
        final BlockPos blockPos5 = new BlockPos((Entity)this.entityPlayer).add(0.0, 2.0, 0.0);
        this.blocks.clear();
        this.blocks.add(blockPos);
        this.blocks.add(blockPos2);
        this.blocks.add(blockPos3);
        this.blocks.add(blockPos4);
        this.blocks.add(blockPos5);
        if (!this.isPos2(this.cobi, blockPos) && !this.isPos2(this.cobi, blockPos2) && !this.isPos2(this.cobi, blockPos3) && !this.isPos2(this.cobi, blockPos4) && !this.isPos2(this.cobi, blockPos5)) {
            if (this.autoDis.getValue()) {
                this.disable();
                return;
            }
            this.reset();
            this.cobi = this.getBestPos();
        }
        if (this.cobi != null && this.validposition(this.cobi)) {
            switch (this.progress) {
                case 0: {
                    if (this.firsttime < 1) {
                        this.flag = true;
                    }
                    switchToHotbarSlot(this.ObiItem, false);
                    this.placeBlock(this.cobi, (boolean)this.packet.getValue());
                    switchToHotbarSlot(this.oldSlot, false);
                    switchToHotbarSlot(this.crystalitem, false);
                    BlockPos bedpos = this.cobi;
                    if (BedCev.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH)) {
                        bedpos = new BlockPos(bedpos.x, bedpos.y + 1, bedpos.z - 1);
                    }
                    else if (BedCev.mc.player.getHorizontalFacing().equals((Object)EnumFacing.WEST)) {
                        bedpos = new BlockPos(bedpos.x + 1, bedpos.y + 1, bedpos.z);
                    }
                    else if (BedCev.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH)) {
                        bedpos = new BlockPos(bedpos.x, bedpos.y + 1, bedpos.z + 1);
                    }
                    else {
                        bedpos = new BlockPos(bedpos.x - 1, bedpos.y + 1, bedpos.z);
                    }
                    if (!this.block(bedpos.down()) || !BedCev.mc.world.isAirBlock(bedpos)) {
                        bedpos = this.cobi;
                        if (this.block(bedpos.east()) && BedCev.mc.world.isAirBlock(bedpos.east().up())) {
                            BedCev.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(90.0f, BedCev.mc.player.rotationPitch, BedCev.mc.player.onGround));
                            bedpos = new BlockPos(bedpos.x + 1, bedpos.y + 1, bedpos.z);
                        }
                        else if (this.block(bedpos.north()) && BedCev.mc.world.isAirBlock(bedpos.north().up())) {
                            BedCev.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, BedCev.mc.player.rotationPitch, BedCev.mc.player.onGround));
                            bedpos = new BlockPos(bedpos.x, bedpos.y + 1, bedpos.z - 1);
                        }
                        else if (this.block(bedpos.west()) && BedCev.mc.world.isAirBlock(bedpos.west().up())) {
                            BedCev.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(-90.0f, BedCev.mc.player.rotationPitch, BedCev.mc.player.onGround));
                            bedpos = new BlockPos(bedpos.x - 1, bedpos.y + 1, bedpos.z);
                        }
                        else if (this.block(bedpos.south()) && BedCev.mc.world.isAirBlock(bedpos.south().up())) {
                            BedCev.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(180.0f, BedCev.mc.player.rotationPitch, BedCev.mc.player.onGround));
                            bedpos = new BlockPos(bedpos.x, bedpos.y + 1, bedpos.z + 1);
                        }
                    }
                    BurrowUtil.placeBlock(bedpos, EnumHand.MAIN_HAND, false, (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
                    switchToHotbarSlot(this.oldSlot, false);
                    ++this.progress;
                    break;
                }
                case 1: {
                    if (this.firsttime < 1) {
                        BedCev.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.cobi, EnumFacing.UP));
                    }
                    switchToHotbarSlot(this.crystalitem, false);
                    BedCev.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.cobi, EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                    switchToHotbarSlot(this.oldSlot, false);
                    switchToHotbarSlot(this.pickaxeitem, false);
                    BedCev.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.cobi, EnumFacing.UP));
                    switchToHotbarSlot(this.oldSlot, false);
                    if (BedCev.mc.world.isAirBlock(this.cobi)) {
                        BedCev.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedCev.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        BedCev.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.cobi.up(), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                        if (this.swing.getValue()) {
                            BedCev.mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                    }
                    ++this.progress;
                    break;
                }
                case 2: {
                    int n = 0;
                    if (!BedCev.mc.world.isAirBlock(this.cobi.up())) {
                        BedCev.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedCev.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        BedCev.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.cobi.up(), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                        if (this.swing.getValue()) {
                            BedCev.mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                        ++n;
                    }
                    if (n != 0 && !this.flag) {
                        break;
                    }
                    ++this.progress;
                    break;
                }
                case 3: {
                    switchToHotbarSlot(this.ObiItem, false);
                    this.placeBlock(this.cobi, (boolean)this.packet.getValue());
                    switchToHotbarSlot(this.oldSlot, false);
                    this.progress = 0;
                    ++this.firsttime;
                    break;
                }
            }
            return;
        }
        this.reset();
        if (this.autoDis.getValue()) {
            this.disable();
        }
    }
    
    public BlockPos getBestPos() {
        if (this.blocks.isEmpty()) {
            return null;
        }
        BlockPos bestPos = null;
        double bestDistance = 0.0;
        for (final BlockPos pos : this.blocks) {
            if (!this.validposition(pos)) {
                continue;
            }
            final double distance = BedCev.mc.player.getDistanceSq(pos);
            if (bestPos == null) {
                bestPos = pos;
                bestDistance = distance;
            }
            else {
                if (distance >= bestDistance) {
                    continue;
                }
                bestPos = pos;
                bestDistance = distance;
            }
        }
        return bestPos;
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (this.entityPlayer != null && this.cobi != null) {
            RenderUtil.drawBox(this.cobi, 1.0, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 63);
            RenderUtil.drawBoundingBox(this.cobi, 1.0, 1.0f, new GSColor(this.color.getValue(), (int)this.Alpha.getValue()));
        }
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
}
