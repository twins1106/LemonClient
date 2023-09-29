//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "OuOChat", category = Category.Misc)
public class OuOChat extends Module
{
    BooleanSetting commands;
    String SUFFIX;
    @EventHandler
    public Listener<PacketEvent.Send> listener;
    
    public OuOChat() {
        this.commands = this.registerBoolean("Commands", false);
        this.SUFFIX = " \u23d0 \u0585\u1d1c\u0585\u2720 \u157c\uff41\u03c2\u04a1";
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getPacket() instanceof CPacketChatMessage) {
                String s = ((CPacketChatMessage)event.getPacket()).getMessage();
                if (s.startsWith("/") && !(boolean)this.commands.getValue()) {
                    return;
                }
                if (s.contains(this.SUFFIX) || s.isEmpty()) {
                    return;
                }
                s += this.SUFFIX;
                if (s.length() >= 256) {
                    s = s.substring(0, 256);
                }
                ((CPacketChatMessage)event.getPacket()).message = s;
            }
        }, new Predicate[0]);
    }
}
