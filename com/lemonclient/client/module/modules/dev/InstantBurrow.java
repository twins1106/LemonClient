//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import com.lemonclient.api.util.player.*;

@Module.Declaration(name = "BurrowBypass", category = Category.Dev)
public class InstantBurrow extends Module
{
    ModeSetting mode;
    BooleanSetting enderchest;
    BooleanSetting rotate;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting silent;
    DoubleSetting height;
    IntegerSetting height2;
    float oldTickLength;
    
    public InstantBurrow() {
        this.mode = this.registerMode("BurrowMode", (List)Arrays.asList("NormalBurrow", "5bBypass"), "NormalBurrow");
        this.enderchest = this.registerBoolean("EnderChest", true, () -> ((String)this.mode.getValue()).equals("NormalBurrow"));
        this.rotate = this.registerBoolean("Rotate", true);
        this.packet = this.registerBoolean("Packet", true);
        this.swing = this.registerBoolean("Swing", true);
        this.silent = this.registerBoolean("Silent", true);
        this.height = this.registerDouble("Height", 1.5, -5.0, 10.0, () -> ((String)this.mode.getValue()).equals("NormalBurrow"));
        this.height2 = this.registerInteger("5b Height", 2, -30, 30, () -> ((String)this.mode.getValue()).equals("5bBypass"));
    }
    
    public static int getHotbarSlot(final Block obj) {
        for (int i = 0; i < 9; ++i) {
            final Item getItem = InstantBurrow.mc.player.inventory.getStackInSlot(i).getItem();
            if (getItem instanceof ItemBlock && ((ItemBlock)getItem).getBlock().equals(obj)) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean isInterceptedByOther(final BlockPos blockPos) {
        for (final Entity entity : InstantBurrow.mc.world.loadedEntityList) {
            if (entity.equals((Object)InstantBurrow.mc.player)) {
                continue;
            }
            if (new AxisAlignedBB(blockPos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    public void enable() {
        if (((String)this.mode.getValue()).equals("NormalBurrow")) {
            if (InstantBurrow.mc.player == null || InstantBurrow.mc.world == null) {
                return;
            }
            final int currentItem = InstantBurrow.mc.player.inventory.currentItem;
            this.oldTickLength = InstantBurrow.mc.timer.tickLength;
            final BlockPos blockPos = new BlockPos(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY, InstantBurrow.mc.player.posZ);
            if (InstantBurrow.mc.world.getBlockState(new BlockPos(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY, InstantBurrow.mc.player.posZ)).getBlock().equals(Blocks.OBSIDIAN) || InstantBurrow.mc.world.getBlockState(new BlockPos(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY, InstantBurrow.mc.player.posZ)).getBlock().equals(Blocks.ENDER_CHEST)) {
                this.disable();
                return;
            }
            if (isInterceptedByOther(blockPos)) {
                this.disable();
                return;
            }
            if (getHotbarSlot(Blocks.OBSIDIAN) == -1 && getHotbarSlot(Blocks.ENDER_CHEST) == -1) {
                this.disable();
                return;
            }
            if (!InstantBurrow.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !InstantBurrow.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || !InstantBurrow.mc.world.getBlockState(blockPos.add(0, 3, 0)).getBlock().equals(Blocks.AIR)) {
                this.disable();
                return;
            }
            if (InstantBurrow.mc.isSingleplayer()) {
                this.disable();
                return;
            }
            if (this.silent.getValue()) {
                InstantBurrow.mc.timer.tickLength = 1.0f;
            }
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 0.41999998688698, InstantBurrow.mc.player.posZ, true));
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 0.7531999805211997, InstantBurrow.mc.player.posZ, true));
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 1.00133597911214, InstantBurrow.mc.player.posZ, true));
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 1.16610926093821, InstantBurrow.mc.player.posZ, true));
            InstantBurrow.mc.player.setPosition(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 1.16610926093821, InstantBurrow.mc.player.posZ);
            if ((boolean)this.enderchest.getValue() && getHotbarSlot(Blocks.ENDER_CHEST) != -1) {
                InstantBurrow.mc.player.inventory.currentItem = getHotbarSlot(Blocks.ENDER_CHEST);
            }
            else if (getHotbarSlot(Blocks.OBSIDIAN) != -1) {
                InstantBurrow.mc.player.inventory.currentItem = getHotbarSlot(Blocks.OBSIDIAN);
            }
            else {
                InstantBurrow.mc.player.inventory.currentItem = getHotbarSlot(Blocks.ENDER_CHEST);
            }
            BurrowUtil.placeBlock(blockPos, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
            InstantBurrow.mc.player.inventory.currentItem = currentItem;
            InstantBurrow.mc.player.setPosition(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY - 1.16610926093821, InstantBurrow.mc.player.posZ);
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + (double)this.height.getValue(), InstantBurrow.mc.player.posZ, true));
            InstantBurrow.mc.timer.tickLength = this.oldTickLength;
            this.disable();
        }
        else {
            if (InstantBurrow.mc.player == null || InstantBurrow.mc.world == null) {
                return;
            }
            final int currentItem2 = InstantBurrow.mc.player.inventory.currentItem;
            this.oldTickLength = InstantBurrow.mc.timer.tickLength;
            final BlockPos blockPos2 = new BlockPos(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY, InstantBurrow.mc.player.posZ);
            if (InstantBurrow.mc.world.getBlockState(new BlockPos(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY, InstantBurrow.mc.player.posZ)).getBlock().equals(Blocks.PISTON)) {
                this.disable();
                return;
            }
            if (isInterceptedByOther(blockPos2)) {
                this.disable();
                return;
            }
            if (getHotbarSlot((Block)Blocks.PISTON) == -1) {
                this.disable();
                return;
            }
            if (!InstantBurrow.mc.world.getBlockState(blockPos2.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !InstantBurrow.mc.world.getBlockState(blockPos2.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || !InstantBurrow.mc.world.getBlockState(blockPos2.add(0, 3, 0)).getBlock().equals(Blocks.AIR)) {
                this.disable();
                return;
            }
            if (InstantBurrow.mc.isSingleplayer()) {
                this.disable();
                return;
            }
            if (this.silent.getValue()) {
                InstantBurrow.mc.timer.tickLength = 1.0f;
            }
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 0.41999998688698, InstantBurrow.mc.player.posZ, true));
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 0.7531999805211997, InstantBurrow.mc.player.posZ, true));
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 1.00133597911214, InstantBurrow.mc.player.posZ, true));
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 1.16610926093821, InstantBurrow.mc.player.posZ, true));
            InstantBurrow.mc.player.setPosition(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + 1.16610926093821, InstantBurrow.mc.player.posZ);
            if (getHotbarSlot((Block)Blocks.PISTON) != -1) {
                InstantBurrow.mc.player.inventory.currentItem = getHotbarSlot((Block)Blocks.PISTON);
            }
            BurrowUtil.placeBlock(blockPos2, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
            InstantBurrow.mc.player.inventory.currentItem = currentItem2;
            InstantBurrow.mc.player.setPosition(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY - 1.16610926093821, InstantBurrow.mc.player.posZ);
            InstantBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(InstantBurrow.mc.player.posX, InstantBurrow.mc.player.posY + (int)this.height2.getValue(), InstantBurrow.mc.player.posZ, true));
            InstantBurrow.mc.timer.tickLength = this.oldTickLength;
            this.disable();
        }
    }
}
