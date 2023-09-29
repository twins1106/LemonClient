//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import java.util.*;

@Module.Declaration(name = "AutoSelfFill", category = Category.Combat)
public class AutoSelfFill extends Module
{
    ModeSetting time;
    BooleanSetting alwayson;
    BooleanSetting rotate;
    BooleanSetting packet;
    BooleanSetting ps;
    BooleanSetting swing;
    ModeSetting mode;
    DoubleSetting webRange;
    DoubleSetting yRange;
    BooleanSetting onlyinhole;
    BooleanSetting only;
    BooleanSetting obsidian;
    BooleanSetting echest;
    BooleanSetting web;
    BooleanSetting skull;
    BooleanSetting plate;
    BooleanSetting upPlate;
    BooleanSetting trapdoor;
    int new_slot;
    boolean door;
    boolean block;
    
    public AutoSelfFill() {
        this.time = this.registerMode("Time Mode", (List)Arrays.asList("Tick", "onUpdate", "Both", "Fast"), "Tick");
        this.alwayson = this.registerBoolean("AlwaysOn", false);
        this.rotate = this.registerBoolean("Rotate", true);
        this.packet = this.registerBoolean("Packet Place", true);
        this.ps = this.registerBoolean("Packet Switch", true);
        this.swing = this.registerBoolean("Swing", true);
        this.mode = this.registerMode("Mode", (List)Arrays.asList("XZ", "Distance", "Both", "Always"), "Distance");
        this.webRange = this.registerDouble("EnemyRange", 1.5, 0.0, 10.0);
        this.yRange = this.registerDouble("Y Range", 1.5, 0.0, 10.0);
        this.onlyinhole = this.registerBoolean("Only InHole", true);
        this.only = this.registerBoolean("Only 1x1", true);
        this.obsidian = this.registerBoolean("Obsidian", true);
        this.echest = this.registerBoolean("Ender Chest", true);
        this.web = this.registerBoolean("Web", true);
        this.skull = this.registerBoolean("Skull", true);
        this.plate = this.registerBoolean("Slab", true);
        this.upPlate = this.registerBoolean("Up Slab", true);
        this.trapdoor = this.registerBoolean("Trapdoor", true);
        this.new_slot = -1;
    }
    
    public void onUpdate() {
        if (((String)this.time.getValue()).equals("onUpdate") || ((String)this.time.getValue()).equals("Both")) {
            this.selfFill();
        }
    }
    
    public void onTick() {
        if (((String)this.time.getValue()).equals("Tick") || ((String)this.time.getValue()).equals("Both")) {
            this.selfFill();
        }
    }
    
    public void fast() {
        if (((String)this.time.getValue()).equals("Fast")) {
            this.selfFill();
        }
    }
    
    private void selfFill() {
        if (AutoSelfFill.mc.world == null || AutoSelfFill.mc.player == null || AutoSelfFill.mc.player.isDead) {
            return;
        }
        if (!AutoSelfFill.mc.world.isAirBlock(PlayerUtil.getPlayerPos()) || !AutoSelfFill.mc.world.isAirBlock(PlayerUtil.getPlayerPos().up()) || !AutoSelfFill.mc.world.isAirBlock(PlayerUtil.getPlayerPos().up().up()) || !AutoSelfFill.mc.player.onGround) {
            return;
        }
        if (this.onlyinhole.getValue()) {
            final HoleUtil.HoleInfo holeInfo = HoleUtil.isHole(AntiBurrow.getEntityPos((Entity)AutoSelfFill.mc.player), false, false, false);
            final HoleUtil.HoleType holeType = holeInfo.getType();
            if (holeType == HoleUtil.HoleType.NONE && !this.is_surround()) {
                return;
            }
            if ((boolean)this.only.getValue() && holeType != HoleUtil.HoleType.SINGLE && !this.is_surround()) {
                return;
            }
        }
        if (this.alwayson.getValue()) {
            final EntityPlayer target = PlayerUtil.getNearestPlayer((double)this.webRange.getValue());
            if (target == null) {
                return;
            }
            if (target.posY <= AutoSelfFill.mc.player.posY) {
                return;
            }
            final String s = (String)this.mode.getValue();
            switch (s) {
                case "XZ": {
                    if ((int)target.posX == (int)AutoSelfFill.mc.player.posX && (int)target.posZ == (int)AutoSelfFill.mc.player.posZ && target.posY - AutoSelfFill.mc.player.posY <= (double)this.yRange.getValue()) {
                        this.placeBlock();
                        break;
                    }
                    break;
                }
                case "Distance": {
                    if (AutoSelfFill.mc.player.getDistance((Entity)target) <= (double)this.webRange.getValue()) {
                        this.placeBlock();
                        break;
                    }
                    break;
                }
                case "Both": {
                    if (AutoSelfFill.mc.player.getDistance((Entity)target) <= (double)this.webRange.getValue() || ((int)target.posX == (int)AutoSelfFill.mc.player.posX && (int)target.posZ == (int)AutoSelfFill.mc.player.posZ && target.posY - AutoSelfFill.mc.player.posY <= (double)this.yRange.getValue())) {
                        this.placeBlock();
                        break;
                    }
                    break;
                }
            }
        }
        else {
            this.placeBlock();
            if (!((String)this.mode.getValue()).equals("Always")) {
                this.disable();
            }
        }
    }
    
    public void placeBlock() {
        this.new_slot = this.find_in_hotbar();
        if (this.new_slot == -1) {
            return;
        }
        final int oldslot = AutoSelfFill.mc.player.inventory.currentItem;
        this.switchTo(this.new_slot);
        if (this.door) {
            this.placeTrapdoor();
        }
        else if ((boolean)this.upPlate.getValue() && this.new_slot == BurrowUtil.findHotbarBlock((Class)BlockSlab.class)) {
            this.burrowUp();
        }
        else if (this.block) {
            this.burrow();
        }
        else {
            BurrowUtil.placeBlock(PlayerUtil.getPlayerPos(), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
        }
        if (this.ps.getValue()) {
            AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(AutoSelfFill.mc.player.inventory.currentItem));
        }
        else {
            this.switchTo(oldslot);
        }
    }
    
    private void switchTo(final int slot) {
        if (AutoSelfFill.mc.player.inventory.currentItem != slot && slot > -1 && slot < 9) {
            if (this.ps.getValue()) {
                AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.new_slot));
            }
            else {
                AutoSelfFill.mc.player.inventory.currentItem = slot;
                AutoSelfFill.mc.playerController.updateController();
            }
        }
    }
    
    private int find_in_hotbar() {
        final boolean b = false;
        this.block = b;
        this.door = b;
        int newHand = -1;
        if (this.trapdoor.getValue()) {
            newHand = BurrowUtil.findHotbarBlock((Class)BlockTrapDoor.class);
            if (newHand != -1) {
                this.door = true;
            }
        }
        if (newHand == -1 && (boolean)this.skull.getValue()) {
            newHand = InventoryUtil.findSkullSlot(false, false);
        }
        if (newHand == -1 && (boolean)this.web.getValue()) {
            newHand = BurrowUtil.findHotbarBlock((Class)BlockWeb.class);
        }
        if (newHand == -1 && (boolean)this.plate.getValue()) {
            newHand = BurrowUtil.findHotbarBlock((Class)BlockSlab.class);
        }
        if (newHand == -1 && (boolean)this.obsidian.getValue()) {
            newHand = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
            if (newHand != -1) {
                this.block = true;
            }
        }
        if (newHand == -1 && (boolean)this.echest.getValue()) {
            newHand = BurrowUtil.findHotbarBlock((Class)BlockEnderChest.class);
            if (newHand != -1) {
                this.block = true;
            }
        }
        return newHand;
    }
    
    private boolean is_surround() {
        final BlockPos player_block = PlayerUtil.getPlayerPos();
        return AutoSelfFill.mc.world.getBlockState(player_block.east()).getBlock() != Blocks.AIR && AutoSelfFill.mc.world.getBlockState(player_block.west()).getBlock() != Blocks.AIR && AutoSelfFill.mc.world.getBlockState(player_block.north()).getBlock() != Blocks.AIR && AutoSelfFill.mc.world.getBlockState(player_block.south()).getBlock() != Blocks.AIR;
    }
    
    private void placeTrapdoor() {
        final BlockPos originalPos = PlayerUtil.getPlayerPos();
        BlockPos neighbour;
        EnumFacing opposite;
        if (!AutoSelfFill.mc.world.isAirBlock(originalPos.south())) {
            neighbour = originalPos.offset(EnumFacing.SOUTH);
            opposite = EnumFacing.SOUTH.getOpposite();
        }
        else if (!AutoSelfFill.mc.world.isAirBlock(originalPos.north())) {
            neighbour = originalPos.offset(EnumFacing.NORTH);
            opposite = EnumFacing.NORTH.getOpposite();
        }
        else if (!AutoSelfFill.mc.world.isAirBlock(originalPos.east())) {
            neighbour = originalPos.offset(EnumFacing.EAST);
            opposite = EnumFacing.EAST.getOpposite();
        }
        else {
            if (AutoSelfFill.mc.world.isAirBlock(originalPos.west())) {
                return;
            }
            neighbour = originalPos.offset(EnumFacing.WEST);
            opposite = EnumFacing.WEST.getOpposite();
        }
        final double x = AutoSelfFill.mc.player.posX;
        final double y = AutoSelfFill.mc.player.posY;
        final double z = AutoSelfFill.mc.player.posZ;
        AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y + 0.20000000298023224, z, AutoSelfFill.mc.player.onGround));
        TrapdoorSelfFill.rightClickBlock(neighbour, opposite, new Vec3d(0.5, 0.8, 0.5), (boolean)this.packet.getValue(), (boolean)this.swing.getValue());
        AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, AutoSelfFill.mc.player.onGround));
    }
    
    private void burrow() {
        final BlockPos originalPos = new BlockPos(AutoSelfFill.mc.player.posX, AutoSelfFill.mc.player.posY, AutoSelfFill.mc.player.posZ);
        if (this.intersectsWithEntity(originalPos)) {
            return;
        }
        AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoSelfFill.mc.player.posX, AutoSelfFill.mc.player.posY + 0.42, AutoSelfFill.mc.player.posZ, true));
        AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoSelfFill.mc.player.posX, AutoSelfFill.mc.player.posY + 0.75, AutoSelfFill.mc.player.posZ, true));
        AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoSelfFill.mc.player.posX, AutoSelfFill.mc.player.posY + 1.01, AutoSelfFill.mc.player.posZ, true));
        AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoSelfFill.mc.player.posX, AutoSelfFill.mc.player.posY + 1.16, AutoSelfFill.mc.player.posZ, true));
        BurrowUtil.placeBlock(originalPos, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
        AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoSelfFill.mc.player.posX, AutoSelfFill.mc.player.posY + 1.01, AutoSelfFill.mc.player.posZ, false));
    }
    
    private void burrowUp() {
        final BlockPos originalPos = PlayerUtil.getPlayerPos();
        BlockPos neighbour;
        EnumFacing opposite;
        if (!AutoSelfFill.mc.world.isAirBlock(originalPos.south())) {
            neighbour = originalPos.offset(EnumFacing.SOUTH);
            opposite = EnumFacing.SOUTH.getOpposite();
        }
        else if (!AutoSelfFill.mc.world.isAirBlock(originalPos.north())) {
            neighbour = originalPos.offset(EnumFacing.NORTH);
            opposite = EnumFacing.NORTH.getOpposite();
        }
        else if (!AutoSelfFill.mc.world.isAirBlock(originalPos.east())) {
            neighbour = originalPos.offset(EnumFacing.EAST);
            opposite = EnumFacing.EAST.getOpposite();
        }
        else {
            if (AutoSelfFill.mc.world.isAirBlock(originalPos.west())) {
                return;
            }
            neighbour = originalPos.offset(EnumFacing.WEST);
            opposite = EnumFacing.WEST.getOpposite();
        }
        if (this.intersectsWithEntity(originalPos)) {
            return;
        }
        AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoSelfFill.mc.player.posX, AutoSelfFill.mc.player.posY + 0.42, AutoSelfFill.mc.player.posZ, true));
        AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoSelfFill.mc.player.posX, AutoSelfFill.mc.player.posY + 0.75, AutoSelfFill.mc.player.posZ, true));
        AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoSelfFill.mc.player.posX, AutoSelfFill.mc.player.posY + 1.01, AutoSelfFill.mc.player.posZ, true));
        AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoSelfFill.mc.player.posX, AutoSelfFill.mc.player.posY + 1.16, AutoSelfFill.mc.player.posZ, true));
        TrapdoorSelfFill.rightClickBlock(neighbour, opposite, new Vec3d(0.5, 0.8, 0.5), (boolean)this.packet.getValue(), (boolean)this.swing.getValue());
        AutoSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoSelfFill.mc.player.posX, AutoSelfFill.mc.player.posY + 1.01, AutoSelfFill.mc.player.posZ, false));
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : AutoSelfFill.mc.world.loadedEntityList) {
            if (entity.equals((Object)AutoSelfFill.mc.player)) {
                continue;
            }
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
}
