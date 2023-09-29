//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;

public abstract class FocusableComponent extends ComponentBase
{
    private boolean focus;
    
    public FocusableComponent(final ILabeled label) {
        super(label);
        this.focus = false;
    }
    
    public void handleButton(final Context context, final int button) {
        super.handleButton(context, button);
        this.updateFocus(context, button);
    }
    
    public void releaseFocus() {
        this.focus = false;
    }
    
    public void exit() {
        this.focus = false;
    }
    
    public boolean hasFocus(final Context context) {
        if (!context.hasFocus()) {
            this.focus = false;
        }
        return this.focus;
    }
    
    protected void updateFocus(final Context context, final int button) {
        if (context.getInterface().getButton(button)) {
            this.focus = context.isHovered();
            if (this.focus) {
                context.requestFocus();
            }
            this.handleFocus(context, this.focus && context.hasFocus());
        }
    }
    
    protected void handleFocus(final Context context, final boolean focus) {
    }
}
