//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.player;

import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.network.*;
import java.util.*;

public class PhaseUtil
{
    public static List<String> bound;
    public static String normal;
    private static final Minecraft mc;
    
    public static CPacketPlayer doBounds(final String mode, final boolean send) {
        CPacketPlayer packet = (CPacketPlayer)new CPacketPlayer.PositionRotation(0.0, 0.0, 0.0, 0.0f, 0.0f, false);
        switch (mode) {
            case "Up": {
                packet = (CPacketPlayer)new CPacketPlayer.PositionRotation(PhaseUtil.mc.player.posX, PhaseUtil.mc.player.posY + 69420.0, PhaseUtil.mc.player.posZ, PhaseUtil.mc.player.rotationYaw, PhaseUtil.mc.player.rotationPitch, false);
                break;
            }
            case "Down": {
                packet = (CPacketPlayer)new CPacketPlayer.PositionRotation(PhaseUtil.mc.player.posX, PhaseUtil.mc.player.posY - 69420.0, PhaseUtil.mc.player.posZ, PhaseUtil.mc.player.rotationYaw, PhaseUtil.mc.player.rotationPitch, false);
                break;
            }
            case "Zero": {
                packet = (CPacketPlayer)new CPacketPlayer.PositionRotation(PhaseUtil.mc.player.posX, 0.0, PhaseUtil.mc.player.posZ, PhaseUtil.mc.player.rotationYaw, PhaseUtil.mc.player.rotationPitch, false);
                break;
            }
            case "Min": {
                packet = (CPacketPlayer)new CPacketPlayer.PositionRotation(PhaseUtil.mc.player.posX, PhaseUtil.mc.player.posY + 100.0, PhaseUtil.mc.player.posZ, PhaseUtil.mc.player.rotationYaw, PhaseUtil.mc.player.rotationPitch, false);
                break;
            }
            case "Alternate": {
                if (PhaseUtil.mc.player.ticksExisted % 2 == 0) {
                    packet = (CPacketPlayer)new CPacketPlayer.PositionRotation(PhaseUtil.mc.player.posX, PhaseUtil.mc.player.posY + 69420.0, PhaseUtil.mc.player.posZ, PhaseUtil.mc.player.rotationYaw, PhaseUtil.mc.player.rotationPitch, false);
                    break;
                }
                packet = (CPacketPlayer)new CPacketPlayer.PositionRotation(PhaseUtil.mc.player.posX, PhaseUtil.mc.player.posY - 69420.0, PhaseUtil.mc.player.posZ, PhaseUtil.mc.player.rotationYaw, PhaseUtil.mc.player.rotationPitch, false);
                break;
            }
            case "Forward": {
                final double[] dir = MotionUtil.forward(67.0);
                packet = (CPacketPlayer)new CPacketPlayer.PositionRotation(PhaseUtil.mc.player.posX + dir[0], PhaseUtil.mc.player.posY + 33.4, PhaseUtil.mc.player.posZ + dir[1], PhaseUtil.mc.player.rotationYaw, PhaseUtil.mc.player.rotationPitch, false);
                break;
            }
            case "Flat": {
                final double[] dir = MotionUtil.forward(100.0);
                packet = (CPacketPlayer)new CPacketPlayer.PositionRotation(PhaseUtil.mc.player.posX + dir[0], PhaseUtil.mc.player.posY, PhaseUtil.mc.player.posZ + dir[1], PhaseUtil.mc.player.rotationYaw, PhaseUtil.mc.player.rotationPitch, false);
                break;
            }
            case "Constrict": {
                final double[] dir = MotionUtil.forward(67.0);
                packet = (CPacketPlayer)new CPacketPlayer.PositionRotation(PhaseUtil.mc.player.posX + dir[0], PhaseUtil.mc.player.posY + ((PhaseUtil.mc.player.posY > 64.0) ? -33.4 : 33.4), PhaseUtil.mc.player.posZ + dir[1], PhaseUtil.mc.player.rotationYaw, PhaseUtil.mc.player.rotationPitch, false);
                break;
            }
        }
        PhaseUtil.mc.player.connection.sendPacket((Packet)packet);
        return packet;
    }
    
    static {
        PhaseUtil.bound = Arrays.asList("Up", "Alternate", "Down", "Zero", "Min", "Forward", "Flat", "LimitJitter", "Constrict", "None");
        PhaseUtil.normal = "Forward";
        mc = Minecraft.getMinecraft();
    }
}
