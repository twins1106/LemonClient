//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;

public class NumberSlider extends Slider
{
    protected INumberSetting setting;
    
    public NumberSlider(final INumberSetting setting, final ISliderRenderer renderer) {
        super((ILabeled)setting, renderer);
        this.setting = setting;
    }
    
    @Override
    protected double getValue() {
        return (this.setting.getNumber() - this.setting.getMinimumValue()) / (this.setting.getMaximumValue() - this.setting.getMinimumValue());
    }
    
    @Override
    protected void setValue(final double value) {
        this.setting.setNumber(value * (this.setting.getMaximumValue() - this.setting.getMinimumValue()) + this.setting.getMinimumValue());
    }
    
    @Override
    protected String getDisplayState() {
        return this.setting.getSettingState();
    }
}
