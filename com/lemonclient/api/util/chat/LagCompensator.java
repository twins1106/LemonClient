//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.chat;

import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.client.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.server.*;

public class LagCompensator implements Listenable
{
    public static LagCompensator INSTANCE;
    private final float[] tickRates;
    private int nextIndex;
    private long timeLastTimeUpdate;
    @EventHandler
    Listener<PacketEvent.Receive> packetEventListener;
    
    public LagCompensator() {
        this.tickRates = new float[20];
        this.nextIndex = 0;
        this.packetEventListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketTimeUpdate) {
                LagCompensator.INSTANCE.onTimeUpdate();
            }
        }, new Predicate[0]);
        LemonClient.EVENT_BUS.subscribe((Listenable)this);
        this.reset();
    }
    
    public void reset() {
        this.nextIndex = 0;
        this.timeLastTimeUpdate = -1L;
        Arrays.fill(this.tickRates, 0.0f);
    }
    
    public float getTickRate() {
        float numTicks = 0.0f;
        float sumTickRates = 0.0f;
        for (final float tickRate : this.tickRates) {
            if (tickRate > 0.0f) {
                sumTickRates += tickRate;
                ++numTicks;
            }
        }
        return MathHelper.clamp(sumTickRates / numTicks, 0.0f, 20.0f);
    }
    
    public void onTimeUpdate() {
        if (this.timeLastTimeUpdate != -1L) {
            final float timeElapsed = (System.currentTimeMillis() - this.timeLastTimeUpdate) / 1000.0f;
            this.tickRates[this.nextIndex % this.tickRates.length] = MathHelper.clamp(20.0f / timeElapsed, 0.0f, 20.0f);
            ++this.nextIndex;
        }
        this.timeLastTimeUpdate = System.currentTimeMillis();
    }
}
