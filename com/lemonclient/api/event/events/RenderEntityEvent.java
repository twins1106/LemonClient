//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import com.lemonclient.api.event.*;
import net.minecraft.entity.*;

public class RenderEntityEvent extends LemonClientEvent
{
    private final Entity entity;
    private final Type type;
    
    public RenderEntityEvent(final Entity entity, final Type type) {
        this.entity = entity;
        this.type = type;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public enum Type
    {
        TEXTURE, 
        COLOR;
    }
    
    public static class Head extends RenderEntityEvent
    {
        public Head(final Entity entity, final Type type) {
            super(entity, type);
        }
    }
    
    public static class Return extends RenderEntityEvent
    {
        public Return(final Entity entity, final Type type) {
            super(entity, type);
        }
    }
}
