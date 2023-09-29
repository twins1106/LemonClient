//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;

public class ToggleSwitch extends FocusableComponent
{
    protected IToggleable toggle;
    protected ISwitchRenderer<Boolean> renderer;
    
    public ToggleSwitch(final ILabeled label, final IToggleable toggle, final ISwitchRenderer<Boolean> renderer) {
        super(label);
        this.toggle = toggle;
        this.renderer = renderer;
        if (this.toggle == null) {
            this.toggle = (IToggleable)new SimpleToggleable(false);
        }
    }
    
    public ToggleSwitch(final IBooleanSetting setting, final ISwitchRenderer<Boolean> renderer) {
        this((ILabeled)setting, (IToggleable)setting, renderer);
    }
    
    public void render(final Context context) {
        super.render(context);
        this.renderer.renderButton(context, this.getTitle(), this.hasFocus(context), (Object)this.toggle.isOn());
    }
    
    public void handleButton(final Context context, final int button) {
        super.handleButton(context, button);
        if (button == 0 && context.isClicked(button)) {
            if (this.renderer.getOnField(context).contains(context.getInterface().getMouse()) && !this.toggle.isOn()) {
                this.toggle.toggle();
            }
            else if (this.renderer.getOffField(context).contains(context.getInterface().getMouse()) && this.toggle.isOn()) {
                this.toggle.toggle();
            }
        }
    }
    
    protected int getHeight() {
        return this.renderer.getDefaultHeight();
    }
}
