//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.setting.*;

public class ColorPickerComponent extends ColorComponent
{
    public ColorPickerComponent(final IColorSetting setting, final ThemeTuple theme) {
        super(setting, theme);
    }
    
    public void populate(final ThemeTuple theme) {
        this.addComponent((IComponent)new ToggleButton((IBooleanSetting)new ColorComponent.RainbowToggle((ColorComponent)this), (IButtonRenderer<Boolean>)theme.getButtonRenderer((Class)Boolean.class, false)));
        this.addComponent((IComponent)new ColorPicker(this.setting, theme.theme.getColorPickerRenderer()));
        this.addComponent((IComponent)new NumberSlider((INumberSetting)new ColorComponent.ColorNumber((ColorComponent)this, 0, () -> true), theme.getSliderRenderer(false)));
        this.addComponent((IComponent)new NumberSlider((INumberSetting)new ColorComponent.ColorNumber((ColorComponent)this, 3, () -> true), theme.getSliderRenderer(false)));
    }
}
