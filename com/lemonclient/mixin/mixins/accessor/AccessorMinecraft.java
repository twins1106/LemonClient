//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins.accessor;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ Minecraft.class })
public interface AccessorMinecraft
{
    @Accessor("timer")
    Timer getTimer();
    
    @Accessor("rightClickDelayTimer")
    int getRightClickDelayTimer();
    
    @Accessor("rightClickDelayTimer")
    void setRightClickDelayTimer(final int p0);
}
