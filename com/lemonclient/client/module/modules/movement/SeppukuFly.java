//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraftforge.client.event.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;

@Module.Declaration(name = "SeppukuFly", category = Category.Movement)
public class SeppukuFly extends Module
{
    DoubleSetting speed;
    BooleanSetting noKick;
    private int teleportId;
    private final List<CPacketPlayer> packets;
    final CPacketPlayer[] bounds;
    final double[] ySpeed;
    final double[] ySpeed2;
    final double[] n;
    final double[][] directionalSpeed;
    final int[] i;
    final int[] j;
    final int[] k;
    @EventHandler
    private final Listener<InputUpdateEvent> listener;
    final CPacketPlayer[] packet;
    @EventHandler
    private final Listener<PacketEvent.Send> sendListener;
    final SPacketPlayerPosLook[] packet2;
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener;
    
    public SeppukuFly() {
        this.speed = this.registerDouble("Speed", 0.1, 0.0, 5.0);
        this.noKick = this.registerBoolean("NoKick", true);
        this.packets = new ArrayList<CPacketPlayer>();
        this.bounds = new CPacketPlayer[1];
        this.ySpeed = new double[1];
        this.ySpeed2 = new double[1];
        this.n = new double[1];
        this.directionalSpeed = new double[1][1];
        this.i = new int[1];
        this.j = new int[1];
        this.k = new int[1];
        this.listener = (Listener<InputUpdateEvent>)new Listener(event -> {
            if (this.teleportId <= 0) {
                this.bounds[0] = (CPacketPlayer)new CPacketPlayer.Position(Minecraft.getMinecraft().player.posX, 0.0, Minecraft.getMinecraft().player.posZ, Minecraft.getMinecraft().player.onGround);
                this.packets.add(this.bounds[0]);
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)this.bounds[0]);
            }
            else {
                SeppukuFly.mc.player.setVelocity(0.0, 0.0, 0.0);
                if (SeppukuFly.mc.world.getCollisionBoxes((Entity)SeppukuFly.mc.player, SeppukuFly.mc.player.getEntityBoundingBox().expand(-0.0625, 0.0, -0.0625)).isEmpty()) {
                    this.ySpeed[0] = 0.0;
                    if (SeppukuFly.mc.gameSettings.keyBindJump.isKeyDown()) {
                        if (this.noKick.getValue()) {
                            this.ySpeed2[0] = ((SeppukuFly.mc.player.ticksExisted % 20 == 0) ? -0.03999999910593033 : 0.06199999898672104);
                        }
                        else {
                            this.ySpeed2[0] = 0.06199999898672104;
                        }
                    }
                    else if (SeppukuFly.mc.gameSettings.keyBindSneak.isKeyDown()) {
                        this.ySpeed2[0] = -0.062;
                    }
                    else {
                        if (SeppukuFly.mc.world.getCollisionBoxes((Entity)SeppukuFly.mc.player, SeppukuFly.mc.player.getEntityBoundingBox().expand(-0.0625, -0.0625, -0.0625)).isEmpty()) {
                            if (SeppukuFly.mc.player.ticksExisted % 4 == 0) {
                                this.n[0] = (this.noKick.getValue() ? -0.04f : 0.0f);
                            }
                            else {
                                this.n[0] = 0.0;
                            }
                        }
                        else {
                            this.n[0] = 0.0;
                        }
                        this.ySpeed2[0] = this.n[0];
                    }
                    this.directionalSpeed[0] = directionSpeed((double)this.speed.getValue());
                    if (SeppukuFly.mc.gameSettings.keyBindJump.isKeyDown() || SeppukuFly.mc.gameSettings.keyBindSneak.isKeyDown() || SeppukuFly.mc.gameSettings.keyBindForward.isKeyDown() || SeppukuFly.mc.gameSettings.keyBindBack.isKeyDown() || SeppukuFly.mc.gameSettings.keyBindRight.isKeyDown() || SeppukuFly.mc.gameSettings.keyBindLeft.isKeyDown()) {
                        if (this.directionalSpeed[0][0] != 0.0 || this.ySpeed2[0] != 0.0 || this.directionalSpeed[0][1] != 0.0) {
                            if (SeppukuFly.mc.player.movementInput.jump && (SeppukuFly.mc.player.moveStrafing != 0.0f || SeppukuFly.mc.player.moveForward != 0.0f)) {
                                SeppukuFly.mc.player.setVelocity(0.0, 0.0, 0.0);
                                this.move(0.0, 0.0, 0.0);
                                this.i[0] = 0;
                                while (this.i[0] <= 3) {
                                    SeppukuFly.mc.player.setVelocity(0.0, this.ySpeed2[0] * this.i[0], 0.0);
                                    this.move(0.0, this.ySpeed2[0] * this.i[0], 0.0);
                                    final int[] i = this.i;
                                    final int n = 0;
                                    ++i[n];
                                }
                            }
                            else if (SeppukuFly.mc.player.movementInput.jump) {
                                SeppukuFly.mc.player.setVelocity(0.0, 0.0, 0.0);
                                this.move(0.0, 0.0, 0.0);
                                this.j[0] = 0;
                                while (this.j[0] <= 3) {
                                    SeppukuFly.mc.player.setVelocity(0.0, this.ySpeed2[0] * this.j[0], 0.0);
                                    this.move(0.0, this.ySpeed2[0] * this.j[0], 0.0);
                                    final int[] j = this.j;
                                    final int n2 = 0;
                                    ++j[n2];
                                }
                            }
                            else {
                                this.k[0] = 0;
                                while (this.k[0] <= 2) {
                                    SeppukuFly.mc.player.setVelocity(this.directionalSpeed[0][0] * this.k[0], this.ySpeed2[0] * this.k[0], this.directionalSpeed[0][1] * this.k[0]);
                                    this.move(this.directionalSpeed[0][0] * this.k[0], this.ySpeed2[0] * this.k[0], this.directionalSpeed[0][1] * this.k[0]);
                                    final int[] k = this.k;
                                    final int n3 = 0;
                                    ++k[n3];
                                }
                            }
                        }
                    }
                    else if ((boolean)this.noKick.getValue() && SeppukuFly.mc.world.getCollisionBoxes((Entity)SeppukuFly.mc.player, SeppukuFly.mc.player.getEntityBoundingBox().expand(-0.0625, -0.0625, -0.0625)).isEmpty()) {
                        SeppukuFly.mc.player.setVelocity(0.0, (SeppukuFly.mc.player.ticksExisted % 2 == 0) ? 0.03999999910593033 : -0.03999999910593033, 0.0);
                        this.move(0.0, (SeppukuFly.mc.player.ticksExisted % 2 == 0) ? 0.03999999910593033 : -0.03999999910593033, 0.0);
                    }
                }
            }
        }, new Predicate[0]);
        this.packet = new CPacketPlayer[1];
        this.sendListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getPacket() instanceof CPacketPlayer && !(event.getPacket() instanceof CPacketPlayer.Position)) {
                event.cancel();
            }
            if (event.getPacket() instanceof CPacketPlayer) {
                this.packet[0] = (CPacketPlayer)event.getPacket();
                if (this.packets.contains(this.packet[0])) {
                    this.packets.remove(this.packet[0]);
                }
                else {
                    event.cancel();
                }
            }
        }, new Predicate[0]);
        this.packet2 = new SPacketPlayerPosLook[1];
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                this.packet2[0] = (SPacketPlayerPosLook)event.getPacket();
                if (Minecraft.getMinecraft().player.isEntityAlive() && Minecraft.getMinecraft().world.isBlockLoaded(new BlockPos(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ)) && !(Minecraft.getMinecraft().currentScreen instanceof GuiDownloadTerrain)) {
                    if (this.teleportId <= 0) {
                        this.teleportId = this.packet2[0].getTeleportId();
                    }
                    else {
                        event.cancel();
                    }
                }
            }
        }, new Predicate[0]);
    }
    
    public static double[] directionSpeed(final double speed) {
        final Minecraft mc = Minecraft.getMinecraft();
        float forward = mc.player.movementInput.moveForward;
        float side = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public void onEnable() {
        if (SeppukuFly.mc.world != null) {
            this.teleportId = 0;
            this.packets.clear();
            final CPacketPlayer bounds = (CPacketPlayer)new CPacketPlayer.Position(SeppukuFly.mc.player.posX, 0.0, SeppukuFly.mc.player.posZ, SeppukuFly.mc.player.onGround);
            this.packets.add(bounds);
            SeppukuFly.mc.player.connection.sendPacket((Packet)bounds);
        }
    }
    
    private void move(final double x, final double y, final double z) {
        final Minecraft mc = Minecraft.getMinecraft();
        final CPacketPlayer pos = (CPacketPlayer)new CPacketPlayer.Position(mc.player.posX + x, mc.player.posY + y, mc.player.posZ + z, mc.player.onGround);
        this.packets.add(pos);
        mc.player.connection.sendPacket((Packet)pos);
        final CPacketPlayer bounds = (CPacketPlayer)new CPacketPlayer.Position(mc.player.posX + x, 0.0, mc.player.posZ + z, mc.player.onGround);
        this.packets.add(bounds);
        mc.player.connection.sendPacket((Packet)bounds);
        ++this.teleportId;
        mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportId - 1));
        mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportId));
        mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportId + 1));
    }
}
