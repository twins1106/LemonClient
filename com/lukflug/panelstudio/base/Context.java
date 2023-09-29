//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.base;

import com.lukflug.panelstudio.popup.*;
import java.awt.*;

public final class Context
{
    private final IInterface inter;
    private final Dimension size;
    private final Point position;
    private final boolean focus;
    private final boolean onTop;
    private boolean focusRequested;
    private boolean focusOverride;
    private Description description;
    private IPopupDisplayer popupDisplayer;
    
    public Context(final Context context, final int width, final Point offset, final boolean focus, final boolean onTop) {
        this.focusRequested = false;
        this.focusOverride = false;
        this.description = null;
        this.popupDisplayer = null;
        this.inter = context.getInterface();
        this.size = new Dimension(width, 0);
        (this.position = context.getPos()).translate(offset.x, offset.y);
        this.focus = (context.hasFocus() && focus);
        this.onTop = (context.onTop() && onTop);
        this.popupDisplayer = context.getPopupDisplayer();
    }
    
    public Context(final IInterface inter, final int width, final Point position, final boolean focus, final boolean onTop) {
        this.focusRequested = false;
        this.focusOverride = false;
        this.description = null;
        this.popupDisplayer = null;
        this.inter = inter;
        this.size = new Dimension(width, 0);
        this.position = new Point(position);
        this.focus = focus;
        this.onTop = onTop;
    }
    
    public IInterface getInterface() {
        return this.inter;
    }
    
    public Dimension getSize() {
        return new Dimension(this.size);
    }
    
    public void setHeight(final int height) {
        this.size.height = height;
    }
    
    public Point getPos() {
        return new Point(this.position);
    }
    
    public boolean hasFocus() {
        return this.focus;
    }
    
    public boolean onTop() {
        return this.onTop;
    }
    
    public void requestFocus() {
        if (!this.focusOverride) {
            this.focusRequested = true;
        }
    }
    
    public void releaseFocus() {
        this.focusRequested = false;
        this.focusOverride = true;
    }
    
    public boolean foucsRequested() {
        return this.focusRequested && !this.focusOverride;
    }
    
    public boolean focusReleased() {
        return this.focusOverride;
    }
    
    public boolean isHovered() {
        return new Rectangle(this.position, this.size).contains(this.inter.getMouse()) && this.onTop;
    }
    
    public boolean isClicked(final int button) {
        return this.isHovered() && this.inter.getButton(button);
    }
    
    public Rectangle getRect() {
        return new Rectangle(this.position, this.size);
    }
    
    public Description getDescription() {
        return this.description;
    }
    
    public void setDescription(final Description description) {
        this.description = description;
    }
    
    public IPopupDisplayer getPopupDisplayer() {
        return this.popupDisplayer;
    }
    
    public void setPopupDisplayer(final IPopupDisplayer popupDisplayer) {
        this.popupDisplayer = popupDisplayer;
    }
}
