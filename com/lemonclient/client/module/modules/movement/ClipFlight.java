//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.*;
import java.util.function.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

@Module.Declaration(name = "ClipFlight", category = Category.Exploits)
public class ClipFlight extends Module
{
    ModeSetting flight;
    IntegerSetting packets;
    IntegerSetting speed;
    IntegerSetting speedY;
    BooleanSetting bypass;
    IntegerSetting interval;
    BooleanSetting update;
    int num;
    double startFlat;
    @EventHandler
    private final Listener<PlayerMoveEvent> playerMoveEventListener;
    
    public ClipFlight() {
        this.flight = this.registerMode("Mode", (List)Arrays.asList("Flight", "Clip"), "Clip");
        this.packets = this.registerInteger("Packets", 80, 1, 300);
        this.speed = this.registerInteger("XZ Speed", 7, -99, 99, () -> ((String)this.flight.getValue()).equalsIgnoreCase("Flight"));
        this.speedY = this.registerInteger("Y Speed", 7, -99, 99, () -> !((String)this.flight.getValue()).equalsIgnoreCase("Relative"));
        this.bypass = this.registerBoolean("Bypass", false);
        this.interval = this.registerInteger("Interval", 25, 1, 100, () -> ((String)this.flight.getValue()).equalsIgnoreCase("Clip"));
        this.update = this.registerBoolean("Update Position Client Side", false);
        this.num = 0;
        this.startFlat = 0.0;
        this.playerMoveEventListener = (Listener<PlayerMoveEvent>)new Listener(event -> {
            final double[] dir = MotionUtil.forward((double)(int)this.speed.getValue());
            final String s = (String)this.flight.getValue();
            switch (s) {
                case "Flight": {
                    double xPos = ClipFlight.mc.player.posX;
                    double yPos = ClipFlight.mc.player.posY;
                    double zPos = ClipFlight.mc.player.posZ;
                    if (ClipFlight.mc.gameSettings.keyBindJump.isKeyDown() && !ClipFlight.mc.gameSettings.keyBindSneak.isKeyDown()) {
                        yPos += (int)this.speedY.getValue();
                    }
                    else if (ClipFlight.mc.gameSettings.keyBindSneak.isKeyDown()) {
                        yPos -= (int)this.speedY.getValue();
                    }
                    xPos += dir[0];
                    zPos += dir[1];
                    ClipFlight.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(xPos, yPos, zPos, false));
                    if (this.update.getValue()) {
                        ClipFlight.mc.player.setPosition(xPos, yPos, zPos);
                    }
                    if (this.bypass.getValue()) {
                        ClipFlight.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ClipFlight.mc.player.posX, ClipFlight.mc.player.posY + 0.05, ClipFlight.mc.player.posZ, true));
                        break;
                    }
                    break;
                }
                case "Clip": {
                    if (ClipFlight.mc.gameSettings.keyBindSprint.isKeyDown() || ClipFlight.mc.player.ticksExisted % (int)this.interval.getValue() == 0) {
                        for (int i = 0; i < (int)this.packets.getValue(); ++i) {
                            final double yposition = ClipFlight.mc.player.posY + (int)this.speedY.getValue();
                            ClipFlight.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ClipFlight.mc.player.posX, yposition, ClipFlight.mc.player.posZ, false));
                            if (this.update.getValue()) {
                                ClipFlight.mc.player.setPosition(ClipFlight.mc.player.posX, yposition, ClipFlight.mc.player.posZ);
                            }
                            if (this.bypass.getValue()) {
                                ClipFlight.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ClipFlight.mc.player.posX, ClipFlight.mc.player.posY + 0.05, ClipFlight.mc.player.posZ, true));
                            }
                        }
                        break;
                    }
                    break;
                }
            }
        }, new Predicate[0]);
    }
    
    public void onEnable() {
        this.startFlat = ClipFlight.mc.player.posY;
        this.num = 0;
    }
}
