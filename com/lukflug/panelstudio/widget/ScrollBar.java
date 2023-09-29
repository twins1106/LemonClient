//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;

public abstract class ScrollBar<T> extends FocusableComponent
{
    protected boolean horizontal;
    protected boolean attached;
    protected IScrollBarRenderer<T> renderer;
    
    public ScrollBar(final ILabeled label, final boolean horizontal, final IScrollBarRenderer<T> renderer) {
        super(label);
        this.attached = false;
        this.horizontal = horizontal;
        this.renderer = renderer;
    }
    
    public void render(final Context context) {
        super.render(context);
        final int value = this.renderer.renderScrollBar(context, this.hasFocus(context), this.getState(), this.horizontal, this.getContentHeight(), this.getScrollPosition());
        if (this.attached) {
            this.setScrollPosition(value);
        }
        if (!context.getInterface().getButton(0)) {
            this.attached = false;
        }
    }
    
    public void handleButton(final Context context, final int button) {
        super.handleButton(context, button);
        if (button == 0 && context.isClicked(button)) {
            this.attached = true;
        }
    }
    
    public void handleScroll(final Context context, final int diff) {
        super.handleScroll(context, diff);
        if (context.isHovered()) {
            this.setScrollPosition(this.getScrollPosition() + diff);
        }
    }
    
    protected int getHeight() {
        if (this.horizontal) {
            return this.renderer.getThickness();
        }
        return this.getLength();
    }
    
    public int getWidth() {
        if (this.horizontal) {
            return this.getLength();
        }
        return this.renderer.getThickness();
    }
    
    protected abstract int getLength();
    
    protected abstract int getContentHeight();
    
    protected abstract int getScrollPosition();
    
    protected abstract void setScrollPosition(final int p0);
    
    protected abstract T getState();
}
