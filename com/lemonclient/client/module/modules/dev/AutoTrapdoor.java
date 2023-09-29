//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import com.lemonclient.client.module.modules.combat.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "AutoTrapdoor", category = Category.Dev)
public class AutoTrapdoor extends Module
{
    IntegerSetting range;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting feet;
    BooleanSetting head;
    BooleanSetting top;
    
    public AutoTrapdoor() {
        this.range = this.registerInteger("Range", 5, 0, 10);
        this.packet = this.registerBoolean("Pakcet Place", false);
        this.swing = this.registerBoolean("Swing", false);
        this.feet = this.registerBoolean("Feet", false);
        this.head = this.registerBoolean("Head", false);
        this.top = this.registerBoolean("Top", false);
    }
    
    public void onUpdate() {
        if (AutoTrapdoor.mc.world == null || AutoTrapdoor.mc.player == null || AutoTrapdoor.mc.player.isDead) {
            return;
        }
        final EntityPlayer target = PlayerUtil.findClosestTarget((double)(int)this.range.getValue(), (EntityPlayer)null);
        if (target == null) {
            return;
        }
        final double y = target.posY;
        if (!target.onGround || y - (int)y > 0.1875 || target.motionY > 0.0) {
            if (this.feet.getValue()) {
                final BlockPos originalPos = AntiBurrow.getEntityPos((Entity)target);
                this.placeblock(originalPos);
            }
            if (this.head.getValue()) {
                final BlockPos originalPos = AntiBurrow.getEntityPos((Entity)target).up();
                this.placeblock(originalPos);
            }
            if (this.top.getValue()) {
                final BlockPos originalPos = AntiBurrow.getEntityPos((Entity)target).up().up();
                this.placeblock(originalPos);
            }
        }
    }
    
    public void placeblock(final BlockPos originalPos) {
        if (BurrowUtil.findHotbarBlock((Class)BlockTrapDoor.class) == -1) {
            return;
        }
        if (!AutoTrapdoor.mc.world.isAirBlock(originalPos)) {
            return;
        }
        BlockPos neighbour;
        EnumFacing opposite;
        if (!AutoTrapdoor.mc.world.isAirBlock(originalPos.south())) {
            neighbour = originalPos.offset(EnumFacing.SOUTH);
            opposite = EnumFacing.SOUTH.getOpposite();
        }
        else if (!AutoTrapdoor.mc.world.isAirBlock(originalPos.north())) {
            neighbour = originalPos.offset(EnumFacing.NORTH);
            opposite = EnumFacing.NORTH.getOpposite();
        }
        else if (!AutoTrapdoor.mc.world.isAirBlock(originalPos.east())) {
            neighbour = originalPos.offset(EnumFacing.EAST);
            opposite = EnumFacing.EAST.getOpposite();
        }
        else {
            if (AutoTrapdoor.mc.world.isAirBlock(originalPos.west())) {
                return;
            }
            neighbour = originalPos.offset(EnumFacing.WEST);
            opposite = EnumFacing.WEST.getOpposite();
        }
        AutoTrapdoor.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(BurrowUtil.findHotbarBlock((Class)BlockTrapDoor.class)));
        rightClickBlock(neighbour, opposite, new Vec3d(0.5, 0.8, 0.5), (boolean)this.packet.getValue(), (boolean)this.swing.getValue());
        AutoTrapdoor.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(AutoTrapdoor.mc.player.inventory.currentItem));
    }
    
    public static void rightClickBlock(final BlockPos pos, final EnumFacing facing, final Vec3d hVec, final boolean packet, final boolean swing) {
        final Vec3d hitVec = new Vec3d((Vec3i)pos).add(hVec).add(new Vec3d(facing.getDirectionVec()).scale(0.5));
        if (packet) {
            rightClickBlock(pos, hitVec, EnumHand.MAIN_HAND, facing);
        }
        else {
            AutoTrapdoor.mc.playerController.processRightClickBlock(AutoTrapdoor.mc.player, AutoTrapdoor.mc.world, pos, facing, hitVec, EnumHand.MAIN_HAND);
        }
        if (swing) {
            AutoTrapdoor.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction) {
        final float f = (float)(vec.x - pos.getX());
        final float f2 = (float)(vec.y - pos.getY());
        final float f3 = (float)(vec.z - pos.getZ());
        AutoTrapdoor.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
    }
}
