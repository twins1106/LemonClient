//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "XCarry", category = Category.Misc)
public class XCarry extends Module
{
    @EventHandler
    private Listener<PacketEvent.Send> listener;
    
    public XCarry() {
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getPacket() instanceof CPacketCloseWindow && ((CPacketCloseWindow)event.getPacket()).windowId == XCarry.mc.player.inventoryContainer.windowId) {
                event.cancel();
            }
        }, new Predicate[0]);
    }
}
