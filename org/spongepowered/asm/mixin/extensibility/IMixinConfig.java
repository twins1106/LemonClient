//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.extensibility;

import org.spongepowered.asm.mixin.*;
import java.util.*;

public interface IMixinConfig
{
    public static final int DEFAULT_PRIORITY = 1000;
    
    MixinEnvironment getEnvironment();
    
    String getName();
    
    String getMixinPackage();
    
    int getPriority();
    
    IMixinConfigPlugin getPlugin();
    
    boolean isRequired();
    
    Set<String> getTargets();
}
