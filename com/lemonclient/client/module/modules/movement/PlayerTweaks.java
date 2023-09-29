//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraftforge.client.event.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;

@Module.Declaration(name = "PlayerTweaks", category = Category.Movement)
public class PlayerTweaks extends Module
{
    public BooleanSetting guiMove;
    BooleanSetting noPush;
    BooleanSetting noFall;
    public BooleanSetting noSlow;
    BooleanSetting antiKnockBack;
    @EventHandler
    private final Listener<InputUpdateEvent> eventListener;
    @EventHandler
    private final Listener<EntityCollisionEvent> entityCollisionEventListener;
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener;
    @EventHandler
    private final Listener<PacketEvent.Send> sendListener;
    @EventHandler
    private final Listener<WaterPushEvent> waterPushEventListener;
    
    public PlayerTweaks() {
        this.guiMove = this.registerBoolean("Gui Move", false);
        this.noPush = this.registerBoolean("No Push", false);
        this.noFall = this.registerBoolean("No Fall", false);
        this.noSlow = this.registerBoolean("No Slow", false);
        this.antiKnockBack = this.registerBoolean("Velocity", false);
        this.eventListener = (Listener<InputUpdateEvent>)new Listener(event -> {
            if ((boolean)this.noSlow.getValue() && PlayerTweaks.mc.player.isHandActive() && !PlayerTweaks.mc.player.isRiding()) {
                final MovementInput movementInput = event.getMovementInput();
                movementInput.moveStrafe *= 5.0f;
                final MovementInput movementInput2 = event.getMovementInput();
                movementInput2.moveForward *= 5.0f;
            }
        }, new Predicate[0]);
        this.entityCollisionEventListener = (Listener<EntityCollisionEvent>)new Listener(event -> {
            if (this.noPush.getValue()) {
                event.cancel();
            }
        }, new Predicate[0]);
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (this.antiKnockBack.getValue()) {
                if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == PlayerTweaks.mc.player.getEntityId()) {
                    event.cancel();
                }
                if (event.getPacket() instanceof SPacketExplosion) {
                    event.cancel();
                }
            }
        }, new Predicate[0]);
        this.sendListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if ((boolean)this.noFall.getValue() && event.getPacket() instanceof CPacketPlayer && PlayerTweaks.mc.player.fallDistance >= 3.0) {
                final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
                packet.onGround = true;
            }
        }, new Predicate[0]);
        this.waterPushEventListener = (Listener<WaterPushEvent>)new Listener(event -> {
            if (this.noPush.getValue()) {
                event.cancel();
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        if ((boolean)this.guiMove.getValue() && PlayerTweaks.mc.currentScreen != null && !(PlayerTweaks.mc.currentScreen instanceof GuiChat)) {
            if (Keyboard.isKeyDown(200)) {
                final EntityPlayerSP player = PlayerTweaks.mc.player;
                player.rotationPitch -= 5.0f;
            }
            if (Keyboard.isKeyDown(208)) {
                final EntityPlayerSP player2 = PlayerTweaks.mc.player;
                player2.rotationPitch += 5.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                final EntityPlayerSP player3 = PlayerTweaks.mc.player;
                player3.rotationYaw += 5.0f;
            }
            if (Keyboard.isKeyDown(203)) {
                final EntityPlayerSP player4 = PlayerTweaks.mc.player;
                player4.rotationYaw -= 5.0f;
            }
            if (PlayerTweaks.mc.player.rotationPitch > 90.0f) {
                PlayerTweaks.mc.player.rotationPitch = 90.0f;
            }
            if (PlayerTweaks.mc.player.rotationPitch < -90.0f) {
                PlayerTweaks.mc.player.rotationPitch = -90.0f;
            }
        }
    }
}
