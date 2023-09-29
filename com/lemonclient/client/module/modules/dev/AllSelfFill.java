//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import com.lemonclient.api.util.player.*;

@Module.Declaration(name = "AllSelfFill", category = Category.Dev)
public class AllSelfFill extends Module
{
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting snake;
    BooleanSetting stsnake;
    BooleanSetting rotate;
    DoubleSetting offset;
    
    public AllSelfFill() {
        this.packet = this.registerBoolean("PacketPlace", true);
        this.swing = this.registerBoolean("Swing", true);
        this.snake = this.registerBoolean("Snake", true);
        this.stsnake = this.registerBoolean("StopSnakeDisable", true);
        this.rotate = this.registerBoolean("Rotate", false);
        this.offset = this.registerDouble("Offset", 7.0, -20.0, 20.0);
    }
    
    public void onDisable() {
        if (this.stsnake.getValue()) {
            AllSelfFill.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AllSelfFill.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    public void onEnable() {
        if (this.snake.getValue()) {
            AllSelfFill.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AllSelfFill.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        final BlockPos playerPos = new BlockPos(AllSelfFill.mc.player.posX, AllSelfFill.mc.player.posY, AllSelfFill.mc.player.posZ);
        AllSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AllSelfFill.mc.player.posX, AllSelfFill.mc.player.posY + 0.41999998688698, AllSelfFill.mc.player.posZ, true));
        AllSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AllSelfFill.mc.player.posX, AllSelfFill.mc.player.posY + 0.7531999805211997, AllSelfFill.mc.player.posZ, true));
        AllSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AllSelfFill.mc.player.posX, AllSelfFill.mc.player.posY + 1.00133597911214, AllSelfFill.mc.player.posZ, true));
        AllSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AllSelfFill.mc.player.posX, AllSelfFill.mc.player.posY + 1.16610926093821, AllSelfFill.mc.player.posZ, true));
        BurrowUtil.placeBlock(playerPos, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
        AllSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AllSelfFill.mc.player.posX, AllSelfFill.mc.player.posY + (double)this.offset.getValue(), AllSelfFill.mc.player.posZ, false));
        this.toggle();
    }
}
