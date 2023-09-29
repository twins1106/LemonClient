//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.lemonclient.api.util.misc.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;

@Module.Declaration(name = "AntiCamp", category = Category.Dev)
public class AntiCamp extends Module
{
    IntegerSetting range;
    BooleanSetting look;
    BooleanSetting hole;
    BooleanSetting check;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting close;
    BooleanSetting rotate;
    BooleanSetting block;
    BooleanSetting back;
    BooleanSetting debug;
    ModeSetting disable;
    private final Timing timer;
    private BlockPos Beforeplayerpos;
    private BlockPos Pistonpos;
    int pistonSlot;
    int oldslot;
    int red;
    int[] enemyCoordsInt;
    EntityPlayer aimTarget;
    
    public AntiCamp() {
        this.range = this.registerInteger("Range", 6, 0, 10);
        this.look = this.registerBoolean("Looking Target", false);
        this.hole = this.registerBoolean("Double Hole", false);
        this.check = this.registerBoolean("Push Check", false);
        this.packet = this.registerBoolean("Packet Place", true);
        this.swing = this.registerBoolean("Swing", true);
        this.close = this.registerBoolean("Close GUI", true);
        this.rotate = this.registerBoolean("Rotate", false);
        this.block = this.registerBoolean("Place Block", true);
        this.back = this.registerBoolean("Switch Back", true);
        this.debug = this.registerBoolean("Debug Msg", true);
        this.disable = this.registerMode("Disable Mode", (List)Arrays.asList("NoDisable", "Check", "AutoDisable"), "AutoDisable");
        this.timer = new Timing();
        this.Beforeplayerpos = null;
        this.Pistonpos = null;
        this.aimTarget = null;
    }
    
    public void onEnable() {
        if (this.back.getValue()) {
            this.oldslot = AntiCamp.mc.player.inventory.currentItem;
        }
        this.Beforeplayerpos = null;
        this.Pistonpos = null;
        this.pistonSlot = -1;
        this.ready();
        this.aimTarget = null;
        if (!(boolean)this.look.getValue()) {
            this.aimTarget = PlayerUtil.findClosestTarget((double)(int)this.range.getValue(), this.aimTarget);
        }
        else {
            this.aimTarget = PlayerUtil.findLookingPlayer((double)(int)this.range.getValue());
        }
        if (this.aimTarget != null) {
            this.Beforeplayerpos = new BlockPos(this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ);
            this.enemyCoordsInt = new int[] { (int)this.aimTarget.posX, (int)this.aimTarget.posY, (int)this.aimTarget.posZ };
            this.getPos1();
        }
        else {
            if (this.debug.getValue()) {
                MessageBus.sendClientPrefixMessage("Cant find target");
            }
            if (!((String)this.disable.getValue()).equals("NoDisable")) {
                this.disable();
            }
        }
    }
    
    public void onDisable() {
        if (this.back.getValue()) {
            BurrowUtil.switchToSlot(this.oldslot);
        }
    }
    
    public void onUpdate() {
        if (this.aimTarget == null || this.aimTarget.isDead || AntiCamp.mc.player == null || AntiCamp.mc.world == null) {
            this.disable();
            return;
        }
        boolean placed = false;
        final boolean hasMoved = (int)this.aimTarget.posX != this.enemyCoordsInt[0] || (int)this.aimTarget.posZ != this.enemyCoordsInt[2];
        if (this.Beforeplayerpos != null && this.Pistonpos != null) {
            BurrowUtil.switchToSlot(this.pistonSlot);
            this.Rotation();
            this.place(this.Pistonpos);
            this.openBlock();
            if (this.close.getValue()) {
                AntiCamp.mc.player.closeScreen();
            }
            if (this.block.getValue()) {
                if (hasMoved && this.timer.passedMs(500L)) {
                    BurrowUtil.switchToSlot(BurrowUtil.findHotbarBlock((Class)BlockObsidian.class));
                    this.place(this.Beforeplayerpos);
                    if (((String)this.disable.getValue()).equals("AutoDisable")) {
                        placed = true;
                        if (this.back.getValue()) {
                            BurrowUtil.switchToSlot(this.oldslot);
                        }
                        this.disable();
                    }
                    this.timer.reset();
                }
            }
            else if (((String)this.disable.getValue()).equals("AutoDisable")) {
                this.disable();
            }
        }
        if (((String)this.disable.getValue()).equals("Check") && hasMoved && (!(boolean)this.block.getValue() || placed)) {
            if (this.back.getValue()) {
                BurrowUtil.switchToSlot(this.oldslot);
            }
            this.disable();
        }
    }
    
    private void openBlock() {
        final EnumFacing side = EnumFacing.getDirectionFromEntityLiving(this.Pistonpos, (EntityLivingBase)AntiCamp.mc.player);
        final BlockPos neighbour = this.Pistonpos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        AntiCamp.mc.playerController.processRightClickBlock(AntiCamp.mc.player, AntiCamp.mc.world, this.Pistonpos, opposite, hitVec, EnumHand.MAIN_HAND);
    }
    
    private void getPos1() {
        if (this.check.getValue()) {
            if (this.hole.getValue()) {
                if (this.canplace(this.Beforeplayerpos.east().up(), EnumFacing.EAST) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east().up()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west()).getBlock() != Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west().up()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west().up().up()).getBlock() == Blocks.AIR) {
                    this.Pistonpos = new BlockPos(this.Beforeplayerpos.x + 1, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z);
                    this.red = 1;
                }
                else {
                    this.getPos2();
                }
            }
            else if (this.canplace(this.Beforeplayerpos.east().up(), EnumFacing.EAST) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east().up()).getBlock() == Blocks.AIR && ((AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west().down()).getBlock() != Blocks.AIR) || AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west()).getBlock() != Blocks.AIR) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west().up()).getBlock() == Blocks.AIR && (AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west().up().up()).getBlock() == Blocks.AIR || AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west()).getBlock() == Blocks.AIR)) {
                this.Pistonpos = new BlockPos(this.Beforeplayerpos.x + 1, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z);
                this.red = 1;
            }
            else {
                this.getPos2();
            }
        }
        else if (this.hole.getValue()) {
            if (this.canplace(this.Beforeplayerpos.east().up(), EnumFacing.EAST) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east().up()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west()).getBlock() != Blocks.AIR) {
                this.Pistonpos = new BlockPos(this.Beforeplayerpos.x + 1, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z);
                this.red = 1;
            }
            else {
                this.getPos2();
            }
        }
        else if (this.canplace(this.Beforeplayerpos.east().up(), EnumFacing.EAST) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east().up()).getBlock() == Blocks.AIR) {
            this.Pistonpos = new BlockPos(this.Beforeplayerpos.x + 1, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z);
            this.red = 1;
        }
        else {
            this.getPos2();
        }
    }
    
    private void getPos2() {
        if (this.check.getValue()) {
            if (this.hole.getValue()) {
                if (this.canplace(this.Beforeplayerpos.west().up(), EnumFacing.WEST) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west().up()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east()).getBlock() != Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east().up()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east().up().up()).getBlock() == Blocks.AIR) {
                    this.Pistonpos = new BlockPos(this.Beforeplayerpos.x - 1, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z);
                    this.red = 2;
                }
                else {
                    this.getPos3();
                }
            }
            else if (this.canplace(this.Beforeplayerpos.west().up(), EnumFacing.WEST) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west().up()).getBlock() == Blocks.AIR && ((AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east().down()).getBlock() != Blocks.AIR) || AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east()).getBlock() != Blocks.AIR) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east().up()).getBlock() == Blocks.AIR && (AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east().up().up()).getBlock() == Blocks.AIR || AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east()).getBlock() == Blocks.AIR)) {
                this.Pistonpos = new BlockPos(this.Beforeplayerpos.x - 1, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z);
                this.red = 2;
            }
            else {
                this.getPos3();
            }
        }
        else if (this.hole.getValue()) {
            if (this.canplace(this.Beforeplayerpos.west().up(), EnumFacing.WEST) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west().up()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.east()).getBlock() != Blocks.AIR) {
                this.Pistonpos = new BlockPos(this.Beforeplayerpos.x - 1, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z);
                this.red = 2;
            }
            else {
                this.getPos3();
            }
        }
        else if (this.canplace(this.Beforeplayerpos.west().up(), EnumFacing.WEST) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.west().up()).getBlock() == Blocks.AIR) {
            this.Pistonpos = new BlockPos(this.Beforeplayerpos.x - 1, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z);
            this.red = 2;
        }
        else {
            this.getPos3();
        }
    }
    
    private void getPos3() {
        if (this.check.getValue()) {
            if (this.hole.getValue()) {
                if (this.canplace(this.Beforeplayerpos.north().up(), EnumFacing.NORTH) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north().up()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south()).getBlock() != Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south().up()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south().up().up()).getBlock() == Blocks.AIR) {
                    this.Pistonpos = new BlockPos(this.Beforeplayerpos.x, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z - 1);
                    this.red = 3;
                }
                else {
                    this.getPos4();
                }
            }
            else if (this.canplace(this.Beforeplayerpos.north().up(), EnumFacing.NORTH) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north().up()).getBlock() == Blocks.AIR && ((AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south().down()).getBlock() != Blocks.AIR) || AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south()).getBlock() != Blocks.AIR) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south().up()).getBlock() == Blocks.AIR && (AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south().up().up()).getBlock() == Blocks.AIR || AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south()).getBlock() == Blocks.AIR)) {
                this.Pistonpos = new BlockPos(this.Beforeplayerpos.x, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z - 1);
                this.red = 3;
            }
            else {
                this.getPos4();
            }
        }
        else if (this.hole.getValue()) {
            if (this.canplace(this.Beforeplayerpos.north().up(), EnumFacing.NORTH) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north().up()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south()).getBlock() != Blocks.AIR) {
                this.Pistonpos = new BlockPos(this.Beforeplayerpos.x, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z - 1);
                this.red = 3;
            }
            else {
                this.getPos4();
            }
        }
        else if (this.canplace(this.Beforeplayerpos.north().up(), EnumFacing.NORTH) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north().up()).getBlock() == Blocks.AIR) {
            this.Pistonpos = new BlockPos(this.Beforeplayerpos.x, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z - 1);
            this.red = 3;
        }
        else {
            this.getPos4();
        }
    }
    
    private void getPos4() {
        if (this.check.getValue()) {
            if (this.hole.getValue()) {
                if (this.canplace(this.Beforeplayerpos.south().up(), EnumFacing.SOUTH) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south().up()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north()).getBlock() != Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north().up()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north().up().up()).getBlock() == Blocks.AIR) {
                    this.Pistonpos = new BlockPos(this.Beforeplayerpos.x, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z + 1);
                    this.red = 4;
                }
                else {
                    if (this.debug.getValue()) {
                        MessageBus.sendClientPrefixMessage("Cant Place Piston");
                    }
                    this.disable();
                }
            }
            else if (this.canplace(this.Beforeplayerpos.south().up(), EnumFacing.SOUTH) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south().up()).getBlock() == Blocks.AIR && ((AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north().down()).getBlock() != Blocks.AIR) || AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north()).getBlock() != Blocks.AIR) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north().up()).getBlock() == Blocks.AIR && (AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north().up().up()).getBlock() == Blocks.AIR || AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north()).getBlock() == Blocks.AIR)) {
                this.Pistonpos = new BlockPos(this.Beforeplayerpos.x, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z + 1);
                this.red = 4;
            }
            else {
                if (this.debug.getValue()) {
                    MessageBus.sendClientPrefixMessage("Cant Place Piston");
                }
                this.disable();
            }
        }
        else if (this.hole.getValue()) {
            if (this.canplace(this.Beforeplayerpos.south().up(), EnumFacing.SOUTH) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south().up()).getBlock() == Blocks.AIR && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.north()).getBlock() != Blocks.AIR) {
                this.Pistonpos = new BlockPos(this.Beforeplayerpos.x, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z + 1);
                this.red = 4;
            }
            else {
                if (this.debug.getValue()) {
                    MessageBus.sendClientPrefixMessage("Cant Place Piston");
                }
                this.disable();
            }
        }
        else if (this.canplace(this.Beforeplayerpos.south().up(), EnumFacing.SOUTH) && AntiCamp.mc.world.getBlockState(this.Beforeplayerpos.south().up()).getBlock() == Blocks.AIR) {
            this.Pistonpos = new BlockPos(this.Beforeplayerpos.x, this.Beforeplayerpos.y + 1, this.Beforeplayerpos.z + 1);
            this.red = 4;
        }
        else {
            if (this.debug.getValue()) {
                MessageBus.sendClientPrefixMessage("Cant Place Piston");
            }
            this.disable();
        }
    }
    
    public boolean canplace(final BlockPos pos, final EnumFacing side) {
        final BlockPos neighbour = pos.offset(side);
        return AntiCamp.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(AntiCamp.mc.world.getBlockState(neighbour), false);
    }
    
    private void Rotation() {
        if (this.red == 1) {
            AntiCamp.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(-90.0f, 0.0f, AntiCamp.mc.player.onGround));
        }
        else if (this.red == 2) {
            AntiCamp.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(90.0f, 0.0f, AntiCamp.mc.player.onGround));
        }
        else if (this.red == 3) {
            AntiCamp.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(180.0f, 0.0f, AntiCamp.mc.player.onGround));
        }
        else if (this.red == 4) {
            AntiCamp.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, AntiCamp.mc.player.onGround));
        }
    }
    
    private void ready() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AntiCamp.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockShulkerBox) {
                        this.pistonSlot = i;
                    }
                }
            }
        }
        if (this.pistonSlot == -1) {
            if (this.debug.getValue()) {
                MessageBus.sendClientPrefixMessage("Cant find Piston");
            }
            this.disable();
        }
    }
    
    private void place(final BlockPos pos) {
        this.placeBlock(pos, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), (boolean)this.swing.getValue());
    }
    
    private void placeBlock(final BlockPos pos, final boolean rotate, final boolean packet, final boolean swing) {
        EnumFacing side = null;
        switch (this.red) {
            case 1: {
                side = EnumFacing.EAST;
                break;
            }
            case 2: {
                side = EnumFacing.WEST;
                break;
            }
            case 3: {
                side = EnumFacing.NORTH;
                break;
            }
            case 4: {
                side = EnumFacing.SOUTH;
                break;
            }
        }
        if (side == null) {
            return;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        if (rotate) {
            BurrowUtil.faceVector(hitVec, true);
        }
        boolean sneak = false;
        if ((BlockUtil.blackList.contains(AntiCamp.mc.world.getBlockState(neighbour).getBlock()) || BlockUtil.shulkerList.contains(AntiCamp.mc.world.getBlockState(neighbour).getBlock())) && !AntiCamp.mc.player.isSneaking()) {
            AntiCamp.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AntiCamp.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            AntiCamp.mc.player.setSneaking(true);
            sneak = true;
        }
        BurrowUtil.rightClickBlock(neighbour, hitVec, EnumHand.MAIN_HAND, opposite, packet, swing);
        if (sneak) {
            AntiCamp.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AntiCamp.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            AntiCamp.mc.player.setSneaking(false);
        }
        AntiCamp.mc.rightClickDelayTimer = 4;
    }
}
