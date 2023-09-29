//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import net.minecraftforge.fml.common.eventhandler.*;

public class UpdateEvent extends Event
{
    private final Stage stage;
    
    public UpdateEvent(final Stage stage) {
        this.stage = stage;
    }
    
    public Stage getStage() {
        return this.stage;
    }
    
    public enum Stage
    {
        PRE, 
        POST;
    }
}
