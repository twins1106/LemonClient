//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import com.lemonclient.api.event.*;
import net.minecraft.entity.player.*;

public class DeathEvent extends LemonClientEvent
{
    public EntityPlayer player;
    
    public DeathEvent(final EntityPlayer player) {
        this.player = player;
    }
}
