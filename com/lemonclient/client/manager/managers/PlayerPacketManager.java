//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.manager.managers;

import com.lemonclient.client.manager.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.util.math.*;
import me.zero.alpine.listener.*;
import net.minecraftforge.fml.common.gameevent.*;
import com.lemonclient.api.event.events.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.lemonclient.api.event.*;
import com.lemonclient.api.util.misc.*;

public enum PlayerPacketManager implements Manager
{
    INSTANCE;
    
    private final List<PlayerPacket> packets;
    private Vec3d prevServerSidePosition;
    private Vec3d serverSidePosition;
    private Vec2f prevServerSideRotation;
    private Vec2f serverSideRotation;
    private Vec2f clientSidePitch;
    @EventHandler
    private final Listener<OnUpdateWalkingPlayerEvent> onUpdateWalkingPlayerEventListener;
    @EventHandler
    private final Listener<PacketEvent.PostSend> postSendListener;
    @EventHandler
    private final Listener<TickEvent.ClientTickEvent> tickEventListener;
    @EventHandler
    private final Listener<RenderEntityEvent.Head> renderEntityEventHeadListener;
    @EventHandler
    private final Listener<RenderEntityEvent.Return> renderEntityEventReturnListener;
    
    private PlayerPacketManager() {
        this.packets = new ArrayList<PlayerPacket>();
        this.prevServerSidePosition = Vec3d.ZERO;
        this.serverSidePosition = Vec3d.ZERO;
        this.prevServerSideRotation = Vec2f.ZERO;
        this.serverSideRotation = Vec2f.ZERO;
        this.clientSidePitch = Vec2f.ZERO;
        this.onUpdateWalkingPlayerEventListener = (Listener<OnUpdateWalkingPlayerEvent>)new Listener(event -> {
            if (event.getPhase() != Phase.BY || this.packets.isEmpty()) {
                return;
            }
            final PlayerPacket packet = (PlayerPacket)CollectionUtil.maxOrNull((Iterable)this.packets, PlayerPacket::getPriority);
            if (packet != null) {
                event.cancel();
                event.apply(packet);
            }
            this.packets.clear();
        }, new Predicate[0]);
        this.postSendListener = (Listener<PacketEvent.PostSend>)new Listener(event -> {
            if (event.isCancelled()) {
                return;
            }
            final Packet<?> rawPacket = (Packet<?>)event.getPacket();
            final EntityPlayerSP player = this.getPlayer();
            if (player != null && rawPacket instanceof CPacketPlayer) {
                final CPacketPlayer packet = (CPacketPlayer)rawPacket;
                if (packet.moving) {
                    this.serverSidePosition = new Vec3d(packet.x, packet.y, packet.z);
                }
                if (packet.rotating) {
                    this.serverSideRotation = new Vec2f(packet.yaw, packet.pitch);
                    player.rotationYawHead = packet.yaw;
                }
            }
        }, -200, new Predicate[0]);
        this.tickEventListener = (Listener<TickEvent.ClientTickEvent>)new Listener(event -> {
            if (event.phase != TickEvent.Phase.START) {
                return;
            }
            this.prevServerSidePosition = this.serverSidePosition;
            this.prevServerSideRotation = this.serverSideRotation;
        }, new Predicate[0]);
        this.renderEntityEventHeadListener = (Listener<RenderEntityEvent.Head>)new Listener(event -> {
            final EntityPlayerSP player = this.getPlayer();
            if (player == null || player.isRiding() || event.getType() != RenderEntityEvent.Type.TEXTURE || event.getEntity() != player) {
                return;
            }
            this.clientSidePitch = new Vec2f(player.prevRotationPitch, player.rotationPitch);
            player.prevRotationPitch = this.prevServerSideRotation.y;
            player.rotationPitch = this.serverSideRotation.y;
        }, new Predicate[0]);
        this.renderEntityEventReturnListener = (Listener<RenderEntityEvent.Return>)new Listener(event -> {
            final EntityPlayerSP player = this.getPlayer();
            if (player == null || player.isRiding() || event.getType() != RenderEntityEvent.Type.TEXTURE || event.getEntity() != player) {
                return;
            }
            player.prevRotationPitch = this.clientSidePitch.x;
            player.rotationPitch = this.clientSidePitch.y;
        }, new Predicate[0]);
    }
    
    public void addPacket(final PlayerPacket packet) {
        this.packets.add(packet);
    }
    
    public Vec3d getPrevServerSidePosition() {
        return this.prevServerSidePosition;
    }
    
    public Vec3d getServerSidePosition() {
        return this.serverSidePosition;
    }
    
    public Vec2f getPrevServerSideRotation() {
        return this.prevServerSideRotation;
    }
    
    public Vec2f getServerSideRotation() {
        return this.serverSideRotation;
    }
}
