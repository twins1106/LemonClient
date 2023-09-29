//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.setting;

import java.awt.*;

public interface IColorSetting extends ISetting<Color>
{
    Color getValue();
    
    void setValue(final Color p0);
    
    Color getColor();
    
    boolean getRainbow();
    
    void setRainbow(final boolean p0);
    
    default boolean hasAlpha() {
        return false;
    }
    
    default boolean allowsRainbow() {
        return true;
    }
    
    default boolean hasHSBModel() {
        return false;
    }
    
    default Color getSettingState() {
        return this.getValue();
    }
    
    default Class<Color> getSettingClass() {
        return Color.class;
    }
}
