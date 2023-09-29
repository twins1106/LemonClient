//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.setting;

import java.util.*;

public interface IEnumSetting extends ISetting<String>
{
    void increment();
    
    void decrement();
    
    String getValueName();
    
    default int getValueIndex() {
        final ILabeled[] stuff = this.getAllowedValues();
        final String compare = this.getValueName();
        for (int i = 0; i < stuff.length; ++i) {
            if (stuff[i].getDisplayName().equals(compare)) {
                return i;
            }
        }
        return -1;
    }
    
    void setValueIndex(final int p0);
    
    ILabeled[] getAllowedValues();
    
    default String getSettingState() {
        return this.getValueName();
    }
    
    default Class<String> getSettingClass() {
        return String.class;
    }
    
    default ILabeled[] getVisibleValues(final IEnumSetting setting) {
        return Arrays.stream(setting.getAllowedValues()).filter(value -> value.isVisible().isOn()).toArray(ILabeled[]::new);
    }
}
