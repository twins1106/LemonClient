//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.setting;

public interface INumberSetting extends ISetting<String>
{
    double getNumber();
    
    void setNumber(final double p0);
    
    double getMaximumValue();
    
    double getMinimumValue();
    
    int getPrecision();
    
    default String getSettingState() {
        if (this.getPrecision() == 0) {
            return "" + (int)this.getNumber();
        }
        return String.format("%." + this.getPrecision() + "f", this.getNumber());
    }
    
    default Class<String> getSettingClass() {
        return String.class;
    }
}
