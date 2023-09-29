//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import com.lemonclient.client.module.modules.exploits.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;

@Module.Declaration(name = "AntiHoleCamper", category = Category.Dev)
public class AntiHoleCamper extends Module
{
    IntegerSetting delay;
    BooleanSetting pause;
    ModeSetting mode;
    IntegerSetting range;
    BooleanSetting look;
    BooleanSetting hole;
    BooleanSetting pushCheck;
    BooleanSetting breakRedstone;
    BooleanSetting instantMine;
    ModeSetting breakBlock;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting rotate;
    BooleanSetting block;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting debug;
    ModeSetting disable;
    private final Timing timer;
    private BlockPos beforePlayerPos;
    private BlockPos pistonPos;
    private BlockPos redstonePos;
    boolean useBlock;
    int redstoneSlot;
    int pistonSlot;
    int obsiSlot;
    int red;
    int oldslot;
    int waited;
    int[] enemyCoordsInt;
    EntityPlayer aimTarget;
    BlockPos savepos;
    BlockPos[] torch;
    BlockPos[] Block;
    
    public AntiHoleCamper() {
        this.delay = this.registerInteger("Delay", 0, 0, 20);
        this.pause = this.registerBoolean("Pause When Move", true);
        this.mode = this.registerMode("Mode", (List)Arrays.asList("Block", "Torch", "Both"), "Block");
        this.range = this.registerInteger("Range", 6, 0, 10);
        this.look = this.registerBoolean("Looking Target", false);
        this.hole = this.registerBoolean("Double Hole Check", false);
        this.pushCheck = this.registerBoolean("Push Check", false);
        this.breakRedstone = this.registerBoolean("Break Redstone", false);
        this.instantMine = this.registerBoolean("Packet Mine", true, () -> (Boolean)this.breakRedstone.getValue());
        this.breakBlock = this.registerMode("Break Block", (List)Arrays.asList("Normal", "Packet"), "Packet", () -> !(boolean)this.instantMine.getValue());
        this.packet = this.registerBoolean("Packet Place", true);
        this.swing = this.registerBoolean("Swing", true);
        this.rotate = this.registerBoolean("Rotate", false);
        this.block = this.registerBoolean("Place Block", true);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.debug = this.registerBoolean("Debug Msg", true);
        this.disable = this.registerMode("Disable Mode", (List)Arrays.asList("NoDisable", "Check", "AutoDisable"), "AutoDisable");
        this.timer = new Timing();
        this.beforePlayerPos = null;
        this.pistonPos = null;
        this.redstonePos = null;
        this.aimTarget = null;
        this.torch = new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) };
        this.Block = new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1), new BlockPos(0, 1, 0) };
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || AntiHoleCamper.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                AntiHoleCamper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                AntiHoleCamper.mc.player.inventory.currentItem = slot;
            }
            AntiHoleCamper.mc.playerController.updateController();
        }
    }
    
    private void calc() {
        this.beforePlayerPos = null;
        this.pistonPos = null;
        this.redstonePos = null;
        this.redstoneSlot = -1;
        this.pistonSlot = -1;
        this.obsiSlot = -1;
        this.ready();
        this.aimTarget = null;
        if (!(boolean)this.look.getValue()) {
            this.aimTarget = PlayerUtil.getNearestPlayer((double)(int)this.range.getValue());
        }
        else {
            this.aimTarget = PlayerUtil.findLookingPlayer((double)(int)this.range.getValue());
        }
        if (this.aimTarget != null) {
            this.beforePlayerPos = new BlockPos(this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ);
            this.enemyCoordsInt = new int[] { (int)this.aimTarget.posX, (int)this.aimTarget.posY, (int)this.aimTarget.posZ };
            this.getPos1();
        }
        else {
            if (this.debug.getValue()) {
                MessageBus.sendClientDeleteMessage("Cant find target", "AntiCamp", 7);
            }
            if (!((String)this.disable.getValue()).equals("NoDisable")) {
                this.disable();
            }
        }
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : AntiHoleCamper.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean canPlacePiston(final BlockPos pos, final BlockPos pos2) {
        return !this.intersectsWithEntity(pos.add(0, 1, 0)) && (!AntiHoleCamper.mc.world.isAirBlock(pos) || !(AntiHoleCamper.mc.world.getBlockState(pos.up()).getBlock() instanceof BlockPistonBase)) && AntiHoleCamper.mc.world.isAirBlock(pos.add(0, 1, 0)) && (!(boolean)this.hole.getValue() || !AntiHoleCamper.mc.world.isAirBlock(pos2)) && (!(boolean)this.pushCheck.getValue() || (AntiHoleCamper.mc.world.isAirBlock(pos2.add(0, 1, 0)) && AntiHoleCamper.mc.world.isAirBlock(pos2.add(0, 2, 0))));
    }
    
    private BlockPos getRedstonePos(final BlockPos pos) {
        for (final BlockPos side : this.useBlock ? this.Block : this.torch) {
            final BlockPos blockPos = pos.add((Vec3i)side);
            if (!this.intersectsWithEntity(blockPos)) {
                if (AntiHoleCamper.mc.world.isAirBlock(blockPos) || AntiHoleCamper.mc.world.getBlockState(blockPos).getBlock() == Blocks.REDSTONE_BLOCK || AntiHoleCamper.mc.world.getBlockState(blockPos).getBlock() == Blocks.REDSTONE_TORCH) {
                    return blockPos;
                }
            }
        }
        return null;
    }
    
    public void onUpdate() {
        if (AntiHoleCamper.mc.world == null || AntiHoleCamper.mc.player == null || AntiHoleCamper.mc.player.isDead) {
            this.disable();
            return;
        }
        if (this.waited++ < (int)this.delay.getValue() || (MotionUtil.isMoving((EntityLivingBase)AntiHoleCamper.mc.player) && (boolean)this.pause.getValue())) {
            return;
        }
        this.waited = 0;
        this.calc();
        if (this.aimTarget == null) {
            return;
        }
        boolean placed = false;
        final boolean hasMoved = (int)this.aimTarget.posX != this.enemyCoordsInt[0] || (int)this.aimTarget.posZ != this.enemyCoordsInt[2];
        if (this.beforePlayerPos != null && this.pistonPos != null && this.redstonePos != null) {
            if (this.isPos2(this.redstonePos, this.pistonPos.down())) {
                this.placeRedstone();
                this.placePiston();
            }
            else {
                this.placePiston();
                this.placeRedstone();
            }
            if (this.block.getValue()) {
                if (hasMoved && this.timer.passedMs(500L)) {
                    this.oldslot = AntiHoleCamper.mc.player.inventory.currentItem;
                    this.switchTo(this.obsiSlot);
                    this.place(this.beforePlayerPos);
                    this.switchTo(this.oldslot);
                    if (((String)this.disable.getValue()).equals("AutoDisable")) {
                        placed = true;
                        this.disable();
                    }
                }
            }
            else if (((String)this.disable.getValue()).equals("AutoDisable")) {
                this.disable();
            }
        }
        if (!hasMoved && (boolean)this.breakRedstone.getValue() && this.redstonePos != null && !AntiHoleCamper.mc.world.isAirBlock(this.redstonePos)) {
            this.dobreak(this.redstonePos);
        }
        if (((String)this.disable.getValue()).equals("Check") && hasMoved && (!(boolean)this.block.getValue() || placed)) {
            this.disable();
        }
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
    
    private void placePiston() {
        if (!((String)this.mode.getValue()).equals("Block") && AntiHoleCamper.mc.world.getBlockState(this.redstonePos.down()).getBlock() == Blocks.AIR) {
            final BlockPos obsipos = new BlockPos(this.redstonePos.x, this.redstonePos.y - 1, this.redstonePos.z);
            this.oldslot = AntiHoleCamper.mc.player.inventory.currentItem;
            this.switchTo(this.obsiSlot);
            this.place(obsipos);
            this.switchTo(this.oldslot);
        }
        this.oldslot = AntiHoleCamper.mc.player.inventory.currentItem;
        this.switchTo(this.pistonSlot);
        this.Rotation();
        this.place(this.pistonPos);
        this.switchTo(this.oldslot);
    }
    
    private void placeRedstone() {
        this.oldslot = AntiHoleCamper.mc.player.inventory.currentItem;
        this.switchTo(this.redstoneSlot);
        this.place(this.redstonePos);
        this.switchTo(this.oldslot);
    }
    
    private void getPos1() {
        if (this.canPlacePiston(this.beforePlayerPos.east(), this.beforePlayerPos.west())) {
            this.pistonPos = this.beforePlayerPos.east().up();
            this.redstonePos = this.getRedstonePos(this.pistonPos);
            if (this.redstonePos == null) {
                this.getPos2();
            }
            this.red = 1;
        }
        else {
            this.getPos2();
        }
    }
    
    private void getPos2() {
        if (this.canPlacePiston(this.beforePlayerPos.west(), this.beforePlayerPos.east())) {
            this.pistonPos = this.beforePlayerPos.west().up();
            this.redstonePos = this.getRedstonePos(this.pistonPos);
            if (this.redstonePos == null) {
                this.getPos3();
            }
            this.red = 2;
        }
        else {
            this.getPos3();
        }
    }
    
    private void getPos3() {
        if (this.canPlacePiston(this.beforePlayerPos.north(), this.beforePlayerPos.south())) {
            this.pistonPos = this.beforePlayerPos.north().up();
            this.redstonePos = this.getRedstonePos(this.pistonPos);
            if (this.redstonePos == null) {
                this.getPos4();
            }
            this.red = 3;
        }
        else {
            this.getPos4();
        }
    }
    
    private void getPos4() {
        if (this.canPlacePiston(this.beforePlayerPos.south(), this.beforePlayerPos.north())) {
            this.pistonPos = this.beforePlayerPos.south().up();
            this.redstonePos = this.getRedstonePos(this.pistonPos);
            if (this.redstonePos == null) {
                if (this.debug.getValue()) {
                    MessageBus.sendClientDeleteMessage("Cant Place RedStone", "AntiCamp", 7);
                }
                if (!((String)this.disable.getValue()).equals("NoDisable")) {
                    this.disable();
                }
            }
            this.red = 4;
        }
        else {
            if (this.debug.getValue()) {
                MessageBus.sendClientDeleteMessage("Cant Place Piston", "AntiCamp", 7);
            }
            if (!((String)this.disable.getValue()).equals("NoDisable")) {
                this.disable();
            }
        }
    }
    
    private void Rotation() {
        if (this.red == 1) {
            AntiHoleCamper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(-90.0f, 0.0f, AntiHoleCamper.mc.player.onGround));
        }
        else if (this.red == 2) {
            AntiHoleCamper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(90.0f, 0.0f, AntiHoleCamper.mc.player.onGround));
        }
        else if (this.red == 3) {
            AntiHoleCamper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(180.0f, 0.0f, AntiHoleCamper.mc.player.onGround));
        }
        else if (this.red == 4) {
            AntiHoleCamper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, AntiHoleCamper.mc.player.onGround));
        }
    }
    
    private void ready() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AntiHoleCamper.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockPistonBase) {
                        this.pistonSlot = i;
                    }
                    if (!((String)this.mode.getValue()).equals("Block") && block == Blocks.REDSTONE_TORCH) {
                        this.redstoneSlot = i;
                    }
                    if (!((String)this.mode.getValue()).equals("Torch") && block == Blocks.REDSTONE_BLOCK) {
                        this.redstoneSlot = i;
                    }
                }
            }
        }
        if ((!((String)this.mode.getValue()).equals("Block") || (boolean)this.block.getValue()) && BurrowUtil.findHotbarBlock((Class)BlockObsidian.class) == -1) {
            if (this.debug.getValue()) {
                MessageBus.sendClientDeleteMessage("Cant find Obsidian", "AntiCamp", 7);
            }
            if (!((String)this.disable.getValue()).equals("NoDisable")) {
                this.disable();
            }
        }
        else {
            this.obsiSlot = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
        }
        if (this.redstoneSlot == -1) {
            if (this.debug.getValue()) {
                MessageBus.sendClientDeleteMessage("Cant find Redsotne", "AntiCamp", 7);
            }
            if (!((String)this.disable.getValue()).equals("NoDisable")) {
                this.disable();
            }
        }
        else {
            final ItemStack stack2 = AntiHoleCamper.mc.player.inventory.getStackInSlot(this.redstoneSlot);
            final Block block2 = ((ItemBlock)stack2.getItem()).getBlock();
            this.useBlock = (block2 == Blocks.REDSTONE_BLOCK);
        }
        if (BurrowUtil.findHotbarBlock((Class)ItemPiston.class) == -1) {
            if (this.debug.getValue()) {
                MessageBus.sendClientDeleteMessage("Cant find Piston", "AntiCamp", 7);
            }
            if (!((String)this.disable.getValue()).equals("NoDisable")) {
                this.disable();
            }
        }
    }
    
    private void place(final BlockPos pos) {
        BurrowUtil.placeBlock(pos, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
    }
    
    private void dobreak(final BlockPos pos) {
        if (!this.isPos2(this.savepos, pos)) {
            if (this.instantMine.getValue()) {
                if (this.isPos2(InstantMine.INSTANCE.lastBlock, pos)) {
                    return;
                }
                InstantMine.INSTANCE.breaker(pos);
            }
            else {
                if (this.swing.getValue()) {
                    AntiHoleCamper.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                if (((String)this.breakBlock.getValue()).equals("Packet")) {
                    AntiHoleCamper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
                    AntiHoleCamper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
                }
                else {
                    AntiHoleCamper.mc.playerController.onPlayerDamageBlock(pos, EnumFacing.UP);
                }
            }
            this.savepos = pos;
        }
    }
}
