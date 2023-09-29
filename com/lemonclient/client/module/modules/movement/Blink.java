//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.network.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import com.lemonclient.client.module.modules.combat.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import com.mojang.realmsclient.gui.*;

@Module.Declaration(name = "Blink", category = Category.Movement)
public class Blink extends Module
{
    BooleanSetting ghostPlayer;
    BooleanSetting keepRotations;
    ModeSetting scatterTiming;
    IntegerSetting millisecondPackets;
    IntegerSetting nPacketsLimit;
    IntegerSetting outputScatter;
    BooleanSetting debug;
    BooleanSetting shiftScatter;
    private EntityOtherPlayerMP entity;
    public final ArrayList<Packet<?>> packets;
    private boolean startScatter;
    private long startScatterTimer;
    private int nPackets;
    boolean isRemoving;
    @EventHandler
    private final Listener<PacketEvent.Send> packetSendListener;
    
    public Blink() {
        this.ghostPlayer = this.registerBoolean("Ghost Player", true);
        this.keepRotations = this.registerBoolean("Keep Rotations", false);
        this.scatterTiming = this.registerMode("Scatter", (List)Arrays.asList("Numbers", "Timer"), "Milliseconds");
        this.millisecondPackets = this.registerInteger("Milliseconds", 5000, 1000, 10000, () -> ((String)this.scatterTiming.getValue()).equals("Timer"));
        this.nPacketsLimit = this.registerInteger("Number Packets", 150, 0, 2000, () -> ((String)this.scatterTiming.getValue()).equals("Numbers"));
        this.outputScatter = this.registerInteger("Output Scatter", 10, 0, 50);
        this.debug = this.registerBoolean("Debug", false);
        this.shiftScatter = this.registerBoolean("Shift Scatter", false);
        this.packets = new ArrayList<Packet<?>>();
        this.isRemoving = false;
        this.packetSendListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (this.isRemoving) {
                return;
            }
            if (Blink.mc.player == null || Blink.mc.world == null) {
                this.disable();
            }
            if (!(boolean)this.keepRotations.getValue()) {
                final Packet<?> packet = (Packet<?>)event.getPacket();
                if (Blink.mc.player != null && Blink.mc.player.isEntityAlive() && packet instanceof CPacketPlayer) {
                    this.packets.add(packet);
                    ++this.nPackets;
                    event.cancel();
                }
            }
            else {
                final Packet<?> packet = (Packet<?>)event.getPacket();
                if (Blink.mc.player != null && Blink.mc.player.isEntityAlive() && (packet instanceof CPacketPlayer.Position || packet instanceof CPacketPlayer.PositionRotation)) {
                    this.packets.add(packet);
                    ++this.nPackets;
                    event.cancel();
                }
            }
        }, new Predicate[0]);
    }
    
    public void onEnable() {
        final EntityPlayerSP player = Blink.mc.player;
        final WorldClient world = Blink.mc.world;
        final boolean b = false;
        this.isRemoving = b;
        this.startScatter = b;
        this.nPackets = 0;
        this.startScatterTimer = System.currentTimeMillis();
        if (player == null || world == null) {
            this.disable();
        }
        else if (this.ghostPlayer.getValue()) {
            (this.entity = new EntityOtherPlayerMP((World)world, Blink.mc.getSession().getProfile())).copyLocationAndAnglesFrom((Entity)player);
            this.entity.inventory.copyInventory(player.inventory);
            this.entity.rotationYaw = player.rotationYaw;
            this.entity.rotationYawHead = player.rotationYawHead;
            world.addEntityToWorld(667, (Entity)this.entity);
        }
        this.packets.clear();
    }
    
    public void onUpdate() {
        if (Blink.mc.player == null || Blink.mc.world == null) {
            this.disable();
        }
        final Entity entity = (Entity)this.entity;
        final WorldClient world = Blink.mc.world;
        if (!(boolean)this.ghostPlayer.getValue() && entity != null && world != null) {
            world.removeEntity(entity);
        }
        if ((boolean)this.shiftScatter.getValue() && Blink.mc.gameSettings.keyBindSneak.isPressed()) {
            this.startScatter = true;
        }
        if (!this.startScatter) {
            final String s = (String)this.scatterTiming.getValue();
            switch (s) {
                case "Timer": {
                    if (System.currentTimeMillis() - this.startScatterTimer < (int)this.millisecondPackets.getValue()) {
                        break;
                    }
                    this.startScatter = true;
                    if (this.debug.getValue()) {
                        PistonCrystal.printDebug("N^Packets: " + this.nPackets, Boolean.valueOf(false));
                        break;
                    }
                    break;
                }
                case "Numbers": {
                    if (this.nPackets < (int)this.nPacketsLimit.getValue()) {
                        break;
                    }
                    this.startScatter = true;
                    if (this.debug.getValue()) {
                        PistonCrystal.printDebug("N^Packets: " + this.nPackets, Boolean.valueOf(false));
                        break;
                    }
                    break;
                }
            }
        }
        if (this.startScatter) {
            this.isRemoving = true;
            for (int i = 0; i < (int)this.outputScatter.getValue(); ++i) {
                if (this.packets.size() == 0) {
                    this.disable();
                    return;
                }
                if (this.ghostPlayer.getValue()) {
                    final CPacketPlayer packet = (CPacketPlayer)this.packets.get(0);
                    Objects.requireNonNull(entity).setPosition(packet.x, packet.y, packet.z);
                }
                Blink.mc.player.connection.sendPacket((Packet)this.packets.get(0));
                this.packets.remove(0);
                --this.nPackets;
            }
            this.isRemoving = false;
        }
    }
    
    public void onDisable() {
        final Entity entity = (Entity)this.entity;
        final WorldClient world = Blink.mc.world;
        if (entity != null && world != null) {
            world.removeEntity(entity);
        }
        final EntityPlayerSP player = Blink.mc.player;
        if (this.packets.size() > 0 && player != null) {
            for (final Packet<?> packet : this.packets) {
                player.connection.sendPacket((Packet)packet);
            }
            this.packets.clear();
        }
    }
    
    public String getHudInfo() {
        return "[" + ChatFormatting.WHITE + this.nPackets + ChatFormatting.GRAY + "]";
    }
}
