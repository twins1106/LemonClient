//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.setting;

import com.lukflug.panelstudio.base.*;

@FunctionalInterface
public interface ILabeled
{
    String getDisplayName();
    
    default String getDescription() {
        return null;
    }
    
    default IBoolean isVisible() {
        return () -> true;
    }
}
