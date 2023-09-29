//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.setting;

public interface IKeybindSetting extends ISetting<String>
{
    int getKey();
    
    void setKey(final int p0);
    
    String getKeyName();
    
    default String getSettingState() {
        return this.getKeyName();
    }
    
    default Class<String> getSettingClass() {
        return String.class;
    }
}
