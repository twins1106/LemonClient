//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.network.play.server.*;

@Module.Declaration(name = "ClientTime", category = Category.Render)
public class ClientTime extends Module
{
    IntegerSetting time;
    @EventHandler
    private final Listener<PacketEvent.Receive> noTimeUpdates;
    
    public ClientTime() {
        this.time = this.registerInteger("Time", 1000, 0, 24000);
        this.noTimeUpdates = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketTimeUpdate) {
                event.cancel();
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        ClientTime.mc.world.setWorldTime((long)(int)this.time.getValue());
    }
}
