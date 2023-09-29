//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import com.lemonclient.api.event.*;
import net.minecraft.util.math.*;

public class StepEvent extends LemonClientEvent
{
    AxisAlignedBB BB;
    
    public StepEvent(final AxisAlignedBB bb) {
        this.BB = bb;
    }
    
    public AxisAlignedBB getBB() {
        return this.BB;
    }
}
