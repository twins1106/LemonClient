//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import com.lemonclient.api.event.*;

public class ReachDistanceEvent extends LemonClientEvent
{
    private float distance;
    
    public ReachDistanceEvent(final float distance) {
        this.distance = distance;
    }
    
    public float getDistance() {
        return this.distance;
    }
    
    public void setDistance(final float distance) {
        this.distance = distance;
    }
}
