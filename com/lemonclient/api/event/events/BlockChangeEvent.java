//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import com.lemonclient.api.event.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;

public class BlockChangeEvent extends LemonClientEvent
{
    private final BlockPos position;
    private final Block block;
    
    public BlockChangeEvent(final BlockPos position, final Block block) {
        this.position = position;
        this.block = block;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
}
