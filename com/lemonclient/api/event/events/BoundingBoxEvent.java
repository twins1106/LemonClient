//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import com.lemonclient.api.event.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

public class BoundingBoxEvent extends LemonClientEvent
{
    Block block;
    AxisAlignedBB bb;
    Vec3d pos;
    public boolean changed;
    
    public BoundingBoxEvent(final Block block, final Vec3d pos) {
        this.block = block;
        this.pos = pos;
    }
    
    public void setbb(final AxisAlignedBB BoundingBox) {
        this.bb = BoundingBox;
        this.changed = true;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public Vec3d getPos() {
        return this.pos;
    }
    
    public AxisAlignedBB getbb() {
        return this.bb;
    }
}
