//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import com.lemonclient.api.event.*;

public class PlayerJoinEvent extends LemonClientEvent
{
    private final String name;
    
    public PlayerJoinEvent(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
