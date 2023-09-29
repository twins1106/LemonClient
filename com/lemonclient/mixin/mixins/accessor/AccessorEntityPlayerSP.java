//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins.accessor;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ EntityPlayerSP.class })
public interface AccessorEntityPlayerSP
{
    @Accessor("handActive")
    void gsSetHandActive(final boolean p0);
}
