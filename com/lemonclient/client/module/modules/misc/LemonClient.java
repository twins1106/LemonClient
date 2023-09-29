//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "LemonClient", category = Category.Misc)
public class LemonClient extends Module
{
    BooleanSetting commands;
    String SUFFIX;
    @EventHandler
    public Listener<PacketEvent.Send> listener;
    
    public LemonClient() {
        this.commands = this.registerBoolean("Commands", false);
        this.SUFFIX = " \u23d0 \u2113\u0454\u043c\u2134\u0e20";
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
