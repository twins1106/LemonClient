//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.*;
import java.util.function.*;

public abstract class FocusableComponentProxy<T extends IComponent> implements IComponentProxy<T>
{
    private final boolean initFocus;
    private boolean focus;
    private boolean requestFocus;
    
    public FocusableComponentProxy(final boolean focus) {
        this.requestFocus = false;
        this.initFocus = focus;
        this.focus = focus;
    }
    
    @Override
    public void handleButton(final Context context, final int button) {
        super.handleButton(context, button);
        if (context.getInterface().getButton(button)) {
            this.focus = context.isHovered();
            if (this.focus) {
                context.requestFocus();
            }
        }
    }
    
    @Override
    public Context doOperation(final Context context, final Consumer<Context> operation) {
        if (this.requestFocus) {
            context.requestFocus();
        }
        else if (!context.hasFocus()) {
            this.focus = false;
        }
        this.requestFocus = false;
        return super.doOperation(context, operation);
    }
    
    @Override
    public void releaseFocus() {
        this.focus = false;
        super.releaseFocus();
    }
    
    @Override
    public void enter() {
        this.focus = this.initFocus;
        if (this.focus) {
            this.requestFocus = true;
        }
        super.enter();
    }
    
    @Override
    public void exit() {
        this.focus = this.initFocus;
        super.exit();
    }
    
    public boolean hasFocus(final Context context) {
        return context.hasFocus() && this.focus;
    }
}
