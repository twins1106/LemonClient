//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import com.lemonclient.api.event.*;
import net.minecraft.entity.*;

public class EntityUseTotemEvent extends LemonClientEvent
{
    private final Entity entity;
    
    public EntityUseTotemEvent(final Entity entity) {
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
