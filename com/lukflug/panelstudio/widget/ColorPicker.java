//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;

public class ColorPicker extends FocusableComponent
{
    protected IColorSetting setting;
    protected IColorPickerRenderer renderer;
    protected boolean dragging;
    
    public ColorPicker(final IColorSetting setting, final IColorPickerRenderer renderer) {
        super((ILabeled)setting);
        this.dragging = false;
        this.setting = setting;
        this.renderer = renderer;
    }
    
    public void render(final Context context) {
        super.render(context);
        if (this.dragging && context.getInterface().getButton(0)) {
            this.setting.setValue(this.renderer.transformPoint(context, this.setting.getColor(), context.getInterface().getMouse()));
        }
        this.renderer.renderPicker(context, this.hasFocus(context), this.setting.getColor());
    }
    
    public void handleButton(final Context context, final int button) {
        super.handleButton(context, button);
        if (button == 0 && context.isClicked(button)) {
            this.dragging = true;
        }
        else if (!context.getInterface().getButton(0)) {
            this.dragging = false;
        }
    }
    
    public void getHeight(final Context context) {
        context.setHeight(this.renderer.getDefaultHeight(context.getSize().width));
    }
    
    public void exit() {
        super.exit();
        this.dragging = false;
    }
    
    protected int getHeight() {
        return 0;
    }
}
