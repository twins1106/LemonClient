//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import com.lemonclient.api.event.*;

public class RenderEvent extends LemonClientEvent
{
    private final float partialTicks;
    
    public RenderEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    @Override
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
