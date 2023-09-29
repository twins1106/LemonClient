//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import com.lemonclient.api.event.*;

public class RenderHand extends LemonClientEvent
{
    private final float ticks;
    
    public RenderHand(final float ticks) {
        this.ticks = ticks;
    }
    
    @Override
    public float getPartialTicks() {
        return this.ticks;
    }
    
    public static class PostOutline extends RenderHand
    {
        public PostOutline(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PreOutline extends RenderHand
    {
        public PreOutline(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PostFill extends RenderHand
    {
        public PostFill(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PreFill extends RenderHand
    {
        public PreFill(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PostBoth extends RenderHand
    {
        public PostBoth(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PreBoth extends RenderHand
    {
        public PreBoth(final float ticks) {
            super(ticks);
        }
    }
}
