//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.refmap;

import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.struct.*;

public interface IMixinContext
{
    IMixinInfo getMixin();
    
    Extensions getExtensions();
    
    String getClassRef();
    
    String getTargetClassRef();
    
    IReferenceMapper getReferenceMapper();
    
    boolean getOption(final MixinEnvironment.Option p0);
    
    int getPriority();
    
    Target getTargetMethod(final MethodNode p0);
}
