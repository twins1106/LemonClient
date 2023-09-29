//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

@Cancelable
public class ClickBlockEvent extends Event
{
    private final BlockPos pos;
    private final EnumFacing side;
    private boolean damage;
    
    public ClickBlockEvent(final BlockPos pos, final EnumFacing side) {
        this.damage = false;
        this.pos = pos;
        this.side = side;
    }
    
    public ClickBlockEvent(final BlockPos pos, final EnumFacing side, final boolean damage) {
        this.damage = false;
        this.pos = pos;
        this.side = side;
        this.damage = damage;
    }
    
    public boolean isDamage() {
        return this.damage;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public EnumFacing getSide() {
        return this.side;
    }
}
