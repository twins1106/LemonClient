//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins.accessor;

import org.spongepowered.asm.mixin.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ Timer.class })
public interface AccessorTimer
{
    @Accessor("tickLength")
    float getTickLength();
    
    @Accessor("tickLength")
    void setTickLength(final float p0);
}
