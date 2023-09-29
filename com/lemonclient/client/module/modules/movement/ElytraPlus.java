//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;

@Module.Declaration(name = "Elytra+", category = Category.Movement)
public class ElytraPlus extends Module
{
    ModeSetting mode;
    
    public ElytraPlus() {
        this.mode = this.registerMode("Mode", (List)Arrays.asList("Boost", "Fly"), "Fly");
    }
    
    public void onUpdate() {
        if (!ElytraPlus.mc.player.isElytraFlying()) {
            return;
        }
        final String s = (String)this.mode.getValue();
        switch (s) {
            case "Boost": {
                if (ElytraPlus.mc.player.isInWater()) {
                    ElytraPlus.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraPlus.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                    return;
                }
                if (ElytraPlus.mc.gameSettings.keyBindJump.isKeyDown()) {
                    final EntityPlayerSP player = ElytraPlus.mc.player;
                    player.motionY += 0.08;
                }
                else if (ElytraPlus.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    final EntityPlayerSP player2 = ElytraPlus.mc.player;
                    player2.motionY -= 0.04;
                }
                if (ElytraPlus.mc.gameSettings.keyBindForward.isKeyDown()) {
                    final float yaw = (float)Math.toRadians(ElytraPlus.mc.player.rotationYaw);
                    final EntityPlayerSP player3 = ElytraPlus.mc.player;
                    player3.motionX -= MathHelper.sin(yaw) * 0.05f;
                    final EntityPlayerSP player4 = ElytraPlus.mc.player;
                    player4.motionZ += MathHelper.cos(yaw) * 0.05f;
                    break;
                }
                if (!ElytraPlus.mc.gameSettings.keyBindBack.isKeyDown()) {
                    break;
                }
                final float yaw = (float)Math.toRadians(ElytraPlus.mc.player.rotationYaw);
                final EntityPlayerSP player5 = ElytraPlus.mc.player;
                player5.motionX += MathHelper.sin(yaw) * 0.05f;
                final EntityPlayerSP player6 = ElytraPlus.mc.player;
                player6.motionZ -= MathHelper.cos(yaw) * 0.05f;
                break;
            }
            case "Fly": {
                ElytraPlus.mc.player.capabilities.isFlying = true;
                break;
            }
        }
    }
    
    protected void onDisable() {
        if (ElytraPlus.mc.player.capabilities.isCreativeMode) {
            return;
        }
        ElytraPlus.mc.player.capabilities.isFlying = false;
    }
}
