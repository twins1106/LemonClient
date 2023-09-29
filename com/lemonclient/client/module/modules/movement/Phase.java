//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import java.util.*;
import java.util.function.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.network.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.block.*;

@Module.Declaration(name = "Phase", category = Category.Movement)
public class Phase extends Module
{
    ModeSetting mode;
    DoubleSetting safety;
    BooleanSetting bounded;
    BooleanSetting h;
    ModeSetting bound;
    BooleanSetting twoBeePvP;
    BooleanSetting update;
    BooleanSetting clipCheck;
    BooleanSetting sprint;
    int tpid;
    boolean clipped;
    @EventHandler
    private final Listener<BoundingBoxEvent> boundingBoxEventListener;
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener;
    @EventHandler
    private final Listener<PacketEvent.Send> sendListener;
    
    public Phase() {
        this.mode = this.registerMode("Mode", (List)Arrays.asList("NCP", "Vanilla", "Skip"), "NCP");
        this.safety = this.registerDouble("Safety", 0.15, 0.0, 1.0, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Skip"));
        this.bounded = this.registerBoolean("Bounded", false, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Skip"));
        this.h = this.registerBoolean("Keep Floor", false, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Vanilla"));
        this.bound = this.registerMode("Bounds", PhaseUtil.bound, "Min", () -> ((String)this.mode.getValue()).equalsIgnoreCase("NCP") || (((String)this.mode.getValue()).equalsIgnoreCase("Skip") && (boolean)this.bounded.getValue()));
        this.twoBeePvP = this.registerBoolean("2b2tpvp", false, () -> ((String)this.mode.getValue()).equalsIgnoreCase("NCP"));
        this.update = this.registerBoolean("Update Pos", false, () -> ((String)this.mode.getValue()).equalsIgnoreCase("NCP"));
        this.clipCheck = this.registerBoolean("Clipped Check", false, () -> !((String)this.mode.getValue()).equalsIgnoreCase("Skip"));
        this.sprint = this.registerBoolean("Sprint Force Enable", true, () -> !((String)this.mode.getValue()).equalsIgnoreCase("Skip"));
        this.tpid = 0;
        this.clipped = false;
        this.boundingBoxEventListener = (Listener<BoundingBoxEvent>)new Listener(event -> {
            try {
                if (((String)this.mode.getValue()).equalsIgnoreCase("Vanilla") && (this.clipped || !(boolean)this.clipCheck.getValue() || (Phase.mc.gameSettings.keyBindSprint.isKeyDown() && (boolean)this.sprint.getValue())) && (event.getPos().y >= Phase.mc.player.getPositionVector().y || !(boolean)this.h.getValue() || Phase.mc.gameSettings.keyBindSneak.isKeyDown())) {
                    event.setbb(Block.NULL_AABB);
                }
            }
            catch (Exception e) {
                MessageBus.sendClientPrefixMessage(e.getMessage());
                this.disable();
            }
        }, new Predicate[0]);
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                this.tpid = ((SPacketPlayerPosLook)event.getPacket()).teleportId;
            }
        }, new Predicate[0]);
        this.sendListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getPacket() instanceof CPacketPlayer.PositionRotation || event.getPacket() instanceof CPacketPlayer.Position) {
                ++this.tpid;
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        this.clipped = PlayerUtil.isPlayerClipped();
        if (((String)this.mode.getValue()).equalsIgnoreCase("NCP") && (((Phase.mc.player.collidedHorizontally || Phase.mc.gameSettings.keyBindSneak.isKeyDown()) && (this.clipped || !(boolean)this.clipCheck.getValue())) || (Phase.mc.gameSettings.keyBindSprint.isKeyDown() && (boolean)this.sprint.getValue() && Phase.mc.player.collidedHorizontally)) && ((String)this.mode.getValue()).equalsIgnoreCase("NCP")) {
            this.packetFly();
        }
        if (((String)this.mode.getValue()).equalsIgnoreCase("Skip")) {
            this.skip();
        }
    }
    
    void skip() {
        final double[] dir = MotionUtil.forward(1.0, (float)(Math.round(Phase.mc.player.rotationYaw / 8.0f) * 8));
        if (!Phase.mc.player.collidedHorizontally || Phase.mc.player.motionX + Phase.mc.player.motionZ != 0.0) {
            return;
        }
        for (float i = 0.1f; i < 1.0f; i += (float)0.1) {
            final double dirX = dir[0] * i;
            final double dirZ = dir[1] * i;
            if (!Phase.mc.world.collidesWithAnyBlock(Phase.mc.player.getEntityBoundingBox().offset(dirX, 0.0, dirZ))) {
                final double[] safetyDir = MotionUtil.forward(i + (double)this.safety.getValue(), (float)(Math.round((Phase.mc.player.rotationYaw + Phase.mc.player.moveStrafing * 45.0f + ((Phase.mc.player.moveForward < 0.0f) ? 180 : 0)) / 8.0f) * 8));
                Phase.mc.player.setPosition(Phase.mc.player.posX + safetyDir[0], Phase.mc.player.posY, Phase.mc.player.posZ + safetyDir[1]);
                if (this.bounded.getValue()) {
                    PhaseUtil.doBounds((String)this.bound.getValue(), true);
                }
                Phase.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.tpid - 1));
                Phase.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.tpid));
                Phase.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.tpid + 1));
            }
            else {
                MessageBus.sendClientDeleteMessage("Pos: " + dirX + " " + dirZ, "Phase", 8);
            }
        }
    }
    
    void packetFly() {
        final double[] clip = MotionUtil.forward(0.0624);
        if (Phase.mc.gameSettings.keyBindSneak.isKeyDown() && Phase.mc.player.onGround) {
            this.tp(0.0, -0.0624, 0.0, false);
        }
        else {
            this.tp(clip[0], 0.0, clip[1], true);
        }
    }
    
    void tp(final double x, final double y, final double z, final boolean onGround) {
        final double[] dir = MotionUtil.forward(-0.0312);
        if (this.twoBeePvP.getValue()) {
            Phase.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Phase.mc.player.posX + dir[0], Phase.mc.player.posY, Phase.mc.player.posZ + dir[1], onGround));
        }
        Phase.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position((this.twoBeePvP.getValue() ? (x / 2.0) : x) + Phase.mc.player.posX, y + Phase.mc.player.posY, (this.twoBeePvP.getValue() ? (z / 2.0) : z) + Phase.mc.player.posZ, onGround));
        Phase.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.tpid - 1));
        Phase.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.tpid));
        Phase.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.tpid + 1));
        PhaseUtil.doBounds((String)this.bound.getValue(), true);
        if (this.update.getValue()) {
            Phase.mc.player.setPosition(x, y, z);
        }
    }
}
