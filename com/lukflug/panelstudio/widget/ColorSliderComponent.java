//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.theme.*;

public class ColorSliderComponent extends ColorComponent
{
    public ColorSliderComponent(final IColorSetting setting, final ThemeTuple theme) {
        super(setting, theme);
    }
    
    public void populate(final ThemeTuple theme) {
        this.addComponent(this.getRainbowComponent(theme, (IBooleanSetting)new ColorComponent.RainbowToggle((ColorComponent)this)));
        this.addComponent(this.getColorComponent(theme, 0, (INumberSetting)new ColorComponent.ColorNumber((ColorComponent)this, 0, () -> this.setting.hasHSBModel())));
        this.addComponent(this.getColorComponent(theme, 1, (INumberSetting)new ColorComponent.ColorNumber((ColorComponent)this, 1, () -> this.setting.hasHSBModel())));
        this.addComponent(this.getColorComponent(theme, 2, (INumberSetting)new ColorComponent.ColorNumber((ColorComponent)this, 2, () -> this.setting.hasHSBModel())));
        this.addComponent(this.getColorComponent(theme, 3, (INumberSetting)new ColorComponent.ColorNumber((ColorComponent)this, 3, () -> this.setting.hasHSBModel())));
    }
    
    public IComponent getRainbowComponent(final ThemeTuple theme, final IBooleanSetting toggle) {
        return (IComponent)new ToggleButton(toggle, (IButtonRenderer<Boolean>)theme.getButtonRenderer((Class)Boolean.class, false));
    }
    
    public IComponent getColorComponent(final ThemeTuple theme, final int value, final INumberSetting number) {
        return (IComponent)new NumberSlider(number, theme.getSliderRenderer(false));
    }
}
