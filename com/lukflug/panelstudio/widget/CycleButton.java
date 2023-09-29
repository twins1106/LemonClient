//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;

public class CycleButton extends FocusableComponent
{
    protected IEnumSetting setting;
    protected IButtonRenderer<String> renderer;
    
    public CycleButton(final IEnumSetting setting, final IButtonRenderer<String> renderer) {
        super((ILabeled)setting);
        this.setting = setting;
        this.renderer = renderer;
    }
    
    public void render(final Context context) {
        super.render(context);
        this.renderer.renderButton(context, this.getTitle(), this.hasFocus(context), (Object)this.setting.getValueName());
    }
    
    public void handleButton(final Context context, final int button) {
        super.handleButton(context, button);
        if (button == 0 && context.isClicked(button)) {
            this.setting.increment();
        }
    }
    
    protected int getHeight() {
        return this.renderer.getDefaultHeight();
    }
}
