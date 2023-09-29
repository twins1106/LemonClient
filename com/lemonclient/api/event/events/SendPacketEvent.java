//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;

public class SendPacketEvent extends Event
{
    private final Packet packet;
    
    public SendPacketEvent(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public static class Receive extends SendPacketEvent
    {
        public Receive(final Packet packet) {
            super(packet);
        }
    }
    
    public static class Send extends SendPacketEvent
    {
        public Send(final Packet packet) {
            super(packet);
        }
    }
}
