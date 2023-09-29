//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.setting;

public interface IStringSetting extends ISetting<String>
{
    String getValue();
    
    void setValue(final String p0);
    
    default String getSettingState() {
        return this.getValue();
    }
    
    default Class<String> getSettingClass() {
        return String.class;
    }
}
