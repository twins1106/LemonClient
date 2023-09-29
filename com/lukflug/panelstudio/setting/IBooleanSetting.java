//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.setting;

import com.lukflug.panelstudio.base.*;

public interface IBooleanSetting extends ISetting<Boolean>, IToggleable
{
    default Boolean getSettingState() {
        return this.isOn();
    }
    
    default Class<Boolean> getSettingClass() {
        return Boolean.class;
    }
}
