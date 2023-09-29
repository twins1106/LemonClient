//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;

public abstract class RadioButton extends FocusableComponent
{
    protected IEnumSetting setting;
    protected IRadioRenderer renderer;
    protected AnimatedEnum animation;
    protected final boolean horizontal;
    
    public RadioButton(final IEnumSetting setting, final IRadioRenderer renderer, final Animation animation, final boolean horizontal) {
        super((ILabeled)setting);
        this.setting = setting;
        this.renderer = renderer;
        this.animation = new AnimatedEnum(setting, animation);
        this.horizontal = horizontal;
    }
    
    public void render(final Context context) {
        super.render(context);
        final ILabeled[] values = IEnumSetting.getVisibleValues(this.setting);
        final String compare = this.setting.getValueName();
        int value = -1;
        for (int i = 0; i < values.length; ++i) {
            if (values[i].getDisplayName().equals(compare)) {
                value = i;
                break;
            }
        }
        this.renderer.renderItem(context, values, this.hasFocus(context), value, this.animation.getValue(), this.horizontal);
    }
    
    public void handleButton(final Context context, final int button) {
        super.handleButton(context, button);
        if (button == 0 && context.isClicked(button)) {
            int index = 0;
            final ILabeled[] values = this.setting.getAllowedValues();
            final ILabeled[] visibleValues = IEnumSetting.getVisibleValues(this.setting);
            for (int i = 0; i < values.length; ++i) {
                if (values[i].isVisible().isOn()) {
                    if (this.renderer.getItemRect(context, visibleValues, index, this.horizontal).contains(context.getInterface().getMouse())) {
                        this.setting.setValueIndex(i);
                        return;
                    }
                    ++index;
                }
            }
        }
    }
    
    public void handleKey(final Context context, final int key) {
        super.handleKey(context, key);
        if (context.hasFocus()) {
            if (this.isUpKey(key)) {
                this.setting.decrement();
            }
            else if (this.isDownKey(key)) {
                this.setting.increment();
            }
        }
    }
    
    protected int getHeight() {
        return this.renderer.getDefaultHeight(IEnumSetting.getVisibleValues(this.setting), this.horizontal);
    }
    
    protected abstract boolean isUpKey(final int p0);
    
    protected abstract boolean isDownKey(final int p0);
}
