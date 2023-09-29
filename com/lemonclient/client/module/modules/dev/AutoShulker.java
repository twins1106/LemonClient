//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.world.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "AutoShulker", category = Category.Dev)
public class AutoShulker extends Module
{
    ModeSetting HowplaceBlock;
    DoubleSetting range;
    DoubleSetting yRange;
    IntegerSetting tickDelay;
    BooleanSetting inventory;
    IntegerSetting Slot;
    BooleanSetting packetPlace;
    BooleanSetting placeswing;
    BooleanSetting packetswing;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    private int delayTimeTicks;
    BlockPos blockAim;
    private boolean looking;
    private boolean noSpace;
    private boolean materialsNeeded;
    Vec3d lastHitVec;
    int slot;
    boolean swapped;
    
    public AutoShulker() {
        this.HowplaceBlock = this.registerMode("Place Block", (List)Arrays.asList("Near", "Looking", "Auto"), "Looking");
        this.range = this.registerDouble("Range", 5.0, 0.0, 10.0);
        this.yRange = this.registerDouble("Y Range", 5.0, 0.0, 10.0);
        this.tickDelay = this.registerInteger("Tick Delay", 5, 0, 10);
        this.inventory = this.registerBoolean("Inventory", true);
        this.Slot = this.registerInteger("Slot", 1, 1, 9);
        this.packetPlace = this.registerBoolean("Packet Place", true);
        this.placeswing = this.registerBoolean("Place Swing", true);
        this.packetswing = this.registerBoolean("Packet Swing", true);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.swapped = false;
    }
    
    public void onEnable() {
        this.initValues();
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || AutoShulker.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                AutoShulker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                AutoShulker.mc.player.inventory.currentItem = slot;
            }
            AutoShulker.mc.playerController.updateController();
        }
    }
    
    private int getShulkerSlot() {
        for (int i = 0; i < AutoShulker.mc.player.inventory.mainInventory.size(); ++i) {
            if (AutoShulker.mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && ((ItemBlock)AutoShulker.mc.player.inventory.getStackInSlot(i).getItem()).getBlock() instanceof BlockShulkerBox) {
                return i;
            }
        }
        return -1;
    }
    
    private void initValues() {
        final int shulkerSlot = this.getShulkerSlot();
        this.slot = shulkerSlot;
        if (shulkerSlot == -1) {
            this.materialsNeeded = false;
            return;
        }
        this.materialsNeeded = true;
        if (((String)this.HowplaceBlock.getValue()).equals("Looking")) {
            this.blockAim = AutoShulker.mc.objectMouseOver.getBlockPos();
            final BlockPos blockAim = this.blockAim;
            ++blockAim.y;
            if (BlockUtil.getPlaceableSide(this.blockAim) == null) {
                this.looking = false;
                return;
            }
        }
        else if (((String)this.HowplaceBlock.getValue()).equals("Near")) {
            for (final int[] sur : new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } }) {
                for (final int h : new int[] { 1, 0 }) {
                    if (BlockUtil.getBlock(AutoShulker.mc.player.posX + sur[0], AutoShulker.mc.player.posY + h, AutoShulker.mc.player.posZ + sur[1]) instanceof BlockAir && BlockUtil.getPlaceableSide(new BlockPos(AutoShulker.mc.player.posX + sur[0], AutoShulker.mc.player.posY + h, AutoShulker.mc.player.posZ + sur[1])) != null && BlockUtil.getBlock(AutoShulker.mc.player.posX + sur[0], AutoShulker.mc.player.posY + h + 1.0, AutoShulker.mc.player.posZ + sur[1]) instanceof BlockAir && !this.intersectsWithEntity(new BlockPos(AutoShulker.mc.player.posX + sur[0], AutoShulker.mc.player.posY + h, AutoShulker.mc.player.posZ + sur[1]))) {
                        this.blockAim = new BlockPos(AutoShulker.mc.player.posX + sur[0], AutoShulker.mc.player.posY + h, AutoShulker.mc.player.posZ + sur[1]);
                        break;
                    }
                }
                if (this.blockAim != null) {
                    break;
                }
            }
        }
        else {
            for (final BlockPos pos : EntityUtil.getSphere(PlayerUtil.getEyesPos(), (Double)this.range.getValue(), (Double)this.yRange.getValue(), false, true, 0)) {
                if (AutoShulker.mc.world.isAirBlock(pos) && AutoShulker.mc.world.isAirBlock(pos.up()) && !AutoShulker.mc.world.isAirBlock(pos.down()) && !this.intersectsWithEntity(pos)) {
                    this.blockAim = pos;
                    break;
                }
            }
        }
        if (this.blockAim == null) {
            this.noSpace = false;
            return;
        }
        final EnumFacing side = EnumFacing.getDirectionFromEntityLiving(this.blockAim, (EntityLivingBase)AutoShulker.mc.player);
        final BlockPos neighbour = this.blockAim.offset(side);
        final EnumFacing opposite = side.getOpposite();
        this.lastHitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : AutoShulker.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    public void onUpdate() {
        if (AutoShulker.mc.player == null) {
            this.disable();
            return;
        }
        if (this.delayTimeTicks < (int)this.tickDelay.getValue()) {
            ++this.delayTimeTicks;
            return;
        }
        this.delayTimeTicks = 0;
        if (this.blockAim == null || !this.materialsNeeded || this.looking || this.noSpace) {
            this.disable();
            return;
        }
        if (this.slot > 8 && !this.swapped) {
            if (!(boolean)this.inventory.getValue()) {
                this.disable();
                return;
            }
            AutoShulker.mc.playerController.windowClick(0, this.slot, 0, ClickType.SWAP, (EntityPlayer)AutoShulker.mc.player);
            AutoShulker.mc.playerController.windowClick(0, (int)this.Slot.getValue() + 35, 0, ClickType.SWAP, (EntityPlayer)AutoShulker.mc.player);
            AutoShulker.mc.playerController.windowClick(0, this.slot, 0, ClickType.SWAP, (EntityPlayer)AutoShulker.mc.player);
            AutoShulker.mc.playerController.updateController();
            this.swapped = true;
            if ((int)this.tickDelay.getValue() != 0) {
                return;
            }
        }
        if (BlockUtil.getBlock(this.blockAim) instanceof BlockAir) {
            final int oldslot = AutoShulker.mc.player.inventory.currentItem;
            this.switchTo(this.slot);
            final BlockPos neighbour = this.blockAim.offset(EnumFacing.DOWN);
            final EnumFacing opposite = EnumFacing.DOWN.getOpposite();
            final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
            boolean sneak = false;
            if (BlockUtil.blackList.contains(AutoShulker.mc.world.getBlockState(neighbour).getBlock()) && !AutoShulker.mc.player.isSneaking()) {
                AutoShulker.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoShulker.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                sneak = true;
            }
            BurrowUtil.rightClickBlock(neighbour, hitVec, EnumHand.MAIN_HAND, opposite, (boolean)this.packetPlace.getValue());
            if (sneak) {
                AutoShulker.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoShulker.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (this.placeswing.getValue()) {
                this.swing();
            }
            this.switchTo(oldslot);
            if ((int)this.tickDelay.getValue() == 0) {
                this.openBlock();
            }
        }
        else {
            this.openBlock();
        }
    }
    
    private void swing() {
        if (this.packetswing.getValue()) {
            AutoShulker.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        }
        else {
            AutoShulker.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    private void openBlock() {
        final EnumFacing side = EnumFacing.getDirectionFromEntityLiving(this.blockAim, (EntityLivingBase)AutoShulker.mc.player);
        final BlockPos neighbour = this.blockAim.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        AutoShulker.mc.playerController.processRightClickBlock(AutoShulker.mc.player, AutoShulker.mc.world, this.blockAim, opposite, hitVec, EnumHand.MAIN_HAND);
        this.disable();
    }
}
