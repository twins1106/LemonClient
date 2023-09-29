//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.container.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.setting.*;
import java.awt.*;

public abstract class ColorComponent extends VerticalContainer
{
    protected IColorSetting setting;
    protected ITheme theme;
    
    public ColorComponent(final IColorSetting setting, final ThemeTuple theme) {
        super((ILabeled)setting, theme.getContainerRenderer(false));
        this.setting = setting;
        this.theme = theme.theme;
        this.populate(new ThemeTuple(theme, 0, 1));
    }
    
    public void render(final Context context) {
        this.theme.overrideMainColor(this.setting.getValue());
        super.render(context);
        this.theme.restoreMainColor();
    }
    
    public abstract void populate(final ThemeTuple p0);
    
    protected final class RainbowToggle implements IBooleanSetting
    {
        public String getDisplayName() {
            return "Rainbow";
        }
        
        public IBoolean isVisible() {
            return () -> ColorComponent.this.setting.allowsRainbow();
        }
        
        public boolean isOn() {
            return ColorComponent.this.setting.getRainbow();
        }
        
        public void toggle() {
            ColorComponent.this.setting.setRainbow(!ColorComponent.this.setting.getRainbow());
        }
    }
    
    protected final class ColorNumber implements INumberSetting
    {
        private final int value;
        private final IBoolean model;
        
        public ColorNumber(final int value, final IBoolean model) {
            this.value = value;
            this.model = model;
        }
        
        public String getDisplayName() {
            switch (this.value) {
                case 0: {
                    return this.model.isOn() ? "Hue" : "Red";
                }
                case 1: {
                    return this.model.isOn() ? "Saturation" : "Green";
                }
                case 2: {
                    return this.model.isOn() ? "Brightness" : "Blue";
                }
                case 3: {
                    return this.model.isOn() ? "Opacity" : "Alpha";
                }
                default: {
                    return "";
                }
            }
        }
        
        public IBoolean isVisible() {
            return () -> this.value != 3 || ColorComponent.this.setting.hasAlpha();
        }
        
        public double getNumber() {
            final Color c = ColorComponent.this.setting.getColor();
            if (this.value < 3) {
                if (this.model.isOn()) {
                    return Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null)[this.value] * this.getMaximumValue();
                }
                switch (this.value) {
                    case 0: {
                        return c.getRed();
                    }
                    case 1: {
                        return c.getGreen();
                    }
                    case 2: {
                        return c.getBlue();
                    }
                }
            }
            return c.getAlpha() * this.getMaximumValue() / 255.0;
        }
        
        public void setNumber(final double value) {
            Color c = ColorComponent.this.setting.getColor();
            final float[] color = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
            switch (this.value) {
                case 0: {
                    if (this.model.isOn()) {
                        c = Color.getHSBColor((float)value / 360.0f, color[1], color[2]);
                    }
                    else {
                        c = new Color((int)Math.round(value), c.getGreen(), c.getBlue());
                    }
                    if (ColorComponent.this.setting.hasAlpha()) {
                        ColorComponent.this.setting.setValue(new Color(c.getRed(), c.getGreen(), c.getBlue(), ColorComponent.this.setting.getColor().getAlpha()));
                        break;
                    }
                    ColorComponent.this.setting.setValue(c);
                    break;
                }
                case 1: {
                    if (this.model.isOn()) {
                        c = Color.getHSBColor(color[0], (float)value / 100.0f, color[2]);
                    }
                    else {
                        c = new Color(c.getRed(), (int)Math.round(value), c.getBlue());
                    }
                    if (ColorComponent.this.setting.hasAlpha()) {
                        ColorComponent.this.setting.setValue(new Color(c.getRed(), c.getGreen(), c.getBlue(), ColorComponent.this.setting.getColor().getAlpha()));
                        break;
                    }
                    ColorComponent.this.setting.setValue(c);
                    break;
                }
                case 2: {
                    if (this.model.isOn()) {
                        c = Color.getHSBColor(color[0], color[1], (float)value / 100.0f);
                    }
                    else {
                        c = new Color(c.getRed(), c.getGreen(), (int)Math.round(value));
                    }
                    if (ColorComponent.this.setting.hasAlpha()) {
                        ColorComponent.this.setting.setValue(new Color(c.getRed(), c.getGreen(), c.getBlue(), ColorComponent.this.setting.getColor().getAlpha()));
                        break;
                    }
                    ColorComponent.this.setting.setValue(c);
                    break;
                }
                case 3: {
                    ColorComponent.this.setting.setValue(new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)Math.round(value / this.getMaximumValue() * 255.0)));
                    break;
                }
            }
        }
        
        public double getMaximumValue() {
            int max = 100;
            if (!this.model.isOn()) {
                max = 255;
            }
            else if (this.value == 0) {
                max = 360;
            }
            return max;
        }
        
        public double getMinimumValue() {
            return 0.0;
        }
        
        public int getPrecision() {
            return 0;
        }
    }
}
