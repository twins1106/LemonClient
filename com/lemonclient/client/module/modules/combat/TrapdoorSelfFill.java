//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "TrapdoorSelfFill", category = Category.Combat)
public class TrapdoorSelfFill extends Module
{
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting rotate;
    BooleanSetting block;
    BooleanSetting disable;
    
    public TrapdoorSelfFill() {
        this.packet = this.registerBoolean("Packet Place", true);
        this.swing = this.registerBoolean("Swing", true);
        this.rotate = this.registerBoolean("Rotate", true);
        this.block = this.registerBoolean("Block", true);
        this.disable = this.registerBoolean("AutoDisable", true);
    }
    
    public void onEnable() {
        final BlockPos originalPos = PlayerUtil.getPlayerPos();
        if (BurrowUtil.findHotbarBlock((Class)BlockTrapDoor.class) == -1) {
            this.disable();
            return;
        }
        if (!TrapdoorSelfFill.mc.world.isAirBlock(originalPos)) {
            this.disable();
            return;
        }
        BlockPos neighbour;
        EnumFacing opposite;
        if (!TrapdoorSelfFill.mc.world.isAirBlock(originalPos.south())) {
            neighbour = originalPos.offset(EnumFacing.SOUTH);
            opposite = EnumFacing.SOUTH.getOpposite();
        }
        else if (!TrapdoorSelfFill.mc.world.isAirBlock(originalPos.north())) {
            neighbour = originalPos.offset(EnumFacing.NORTH);
            opposite = EnumFacing.NORTH.getOpposite();
        }
        else if (!TrapdoorSelfFill.mc.world.isAirBlock(originalPos.east())) {
            neighbour = originalPos.offset(EnumFacing.EAST);
            opposite = EnumFacing.EAST.getOpposite();
        }
        else {
            if (TrapdoorSelfFill.mc.world.isAirBlock(originalPos.west())) {
                this.disable();
                return;
            }
            neighbour = originalPos.offset(EnumFacing.WEST);
            opposite = EnumFacing.WEST.getOpposite();
        }
        final double x = TrapdoorSelfFill.mc.player.posX;
        final double y = TrapdoorSelfFill.mc.player.posY;
        final double z = TrapdoorSelfFill.mc.player.posZ;
        TrapdoorSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y + 0.20000000298023224, z, TrapdoorSelfFill.mc.player.onGround));
        TrapdoorSelfFill.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(BurrowUtil.findHotbarBlock((Class)BlockTrapDoor.class)));
        rightClickBlock(neighbour, opposite, new Vec3d(0.5, 0.8, 0.5), (boolean)this.packet.getValue(), (boolean)this.swing.getValue());
        if ((boolean)this.block.getValue() && BurrowUtil.findHotbarBlock((Class)BlockObsidian.class) != -1) {
            TrapdoorSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y - 1.0, z, TrapdoorSelfFill.mc.player.onGround));
            TrapdoorSelfFill.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(BurrowUtil.findHotbarBlock((Class)BlockObsidian.class)));
            BurrowUtil.placeBlockDown(originalPos.up(), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
        }
        TrapdoorSelfFill.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(TrapdoorSelfFill.mc.player.inventory.currentItem));
        TrapdoorSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, TrapdoorSelfFill.mc.player.onGround));
        if (this.disable.getValue()) {
            this.disable();
        }
    }
    
    public static void rightClickBlock(final BlockPos pos, final EnumFacing facing, final Vec3d hVec, final boolean packet, final boolean swing) {
        final Vec3d hitVec = new Vec3d((Vec3i)pos).add(hVec).add(new Vec3d(facing.getDirectionVec()).scale(0.5));
        if (packet) {
            rightClickBlock(pos, hitVec, EnumHand.MAIN_HAND, facing);
        }
        else {
            TrapdoorSelfFill.mc.playerController.processRightClickBlock(TrapdoorSelfFill.mc.player, TrapdoorSelfFill.mc.world, pos, facing, hitVec, EnumHand.MAIN_HAND);
        }
        if (swing) {
            TrapdoorSelfFill.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction) {
        final float f = (float)(vec.x - pos.getX());
        final float f2 = (float)(vec.y - pos.getY());
        final float f3 = (float)(vec.z - pos.getZ());
        TrapdoorSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
    }
}
