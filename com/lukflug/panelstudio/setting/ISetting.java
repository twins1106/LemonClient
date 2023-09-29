//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.setting;

import java.util.stream.*;

public interface ISetting<T> extends ILabeled
{
    T getSettingState();
    
    Class<T> getSettingClass();
    
    default Stream<ISetting<?>> getSubSettings() {
        return null;
    }
}
