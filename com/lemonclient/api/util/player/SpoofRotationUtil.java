//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.player;

import net.minecraft.client.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.client.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class SpoofRotationUtil implements Listenable
{
    private static final Minecraft mc;
    public static final SpoofRotationUtil ROTATION_UTIL;
    private int rotationConnections;
    private boolean shouldSpoofAngles;
    private boolean isSpoofingAngles;
    private double yaw;
    private double pitch;
    @EventHandler
    private final Listener<PacketEvent.Send> packetSendListener;
    
    private SpoofRotationUtil() {
        this.rotationConnections = 0;
        this.packetSendListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            final Packet packet = event.getPacket();
            if (packet instanceof CPacketPlayer && this.shouldSpoofAngles && this.isSpoofingAngles) {
                ((CPacketPlayer)packet).yaw = (float)this.yaw;
                ((CPacketPlayer)packet).pitch = (float)this.pitch;
            }
        }, new Predicate[0]);
    }
    
    public void onEnable() {
        ++this.rotationConnections;
        if (this.rotationConnections == 1) {
            LemonClient.EVENT_BUS.subscribe((Listenable)this);
        }
    }
    
    public void onDisable() {
        --this.rotationConnections;
        if (this.rotationConnections == 0) {
            LemonClient.EVENT_BUS.unsubscribe((Listenable)this);
        }
    }
    
    public void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = EntityUtil.calculateLookAt(px, py, pz, (Entity)me);
        this.setYawAndPitch((float)v[0], (float)v[1]);
    }
    
    public void setYawAndPitch(final float yaw1, final float pitch1) {
        this.yaw = yaw1;
        this.pitch = pitch1;
        this.isSpoofingAngles = true;
    }
    
    public void resetRotation() {
        if (this.isSpoofingAngles) {
            this.yaw = SpoofRotationUtil.mc.player.rotationYaw;
            this.pitch = SpoofRotationUtil.mc.player.rotationPitch;
            this.isSpoofingAngles = false;
        }
    }
    
    public void shouldSpoofAngles(final boolean e) {
        this.shouldSpoofAngles = e;
    }
    
    public boolean isSpoofingAngles() {
        return this.isSpoofingAngles;
    }
    
    static {
        mc = Minecraft.getMinecraft();
        ROTATION_UTIL = new SpoofRotationUtil();
    }
}
