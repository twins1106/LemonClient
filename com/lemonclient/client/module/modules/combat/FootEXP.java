//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.api.event.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;

@Module.Declaration(name = "FootXP", category = Category.Combat)
public class FootEXP extends Module
{
    @EventHandler
    public Listener<PacketEvent.Send> sendPacket;
    
    public FootEXP() {
        this.sendPacket = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getEra() == LemonClientEvent.Era.PRE) {
                if (event.getPacket() instanceof CPacketPlayer.Rotation && FootEXP.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
                    ((CPacketPlayer.Rotation)event.getPacket()).pitch = 90.0f;
                }
                if (event.getPacket() instanceof CPacketPlayer.PositionRotation && FootEXP.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
                    ((CPacketPlayer.PositionRotation)event.getPacket()).pitch = 90.0f;
                }
            }
        }, new Predicate[0]);
    }
}
