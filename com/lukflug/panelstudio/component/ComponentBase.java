//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;

public abstract class ComponentBase implements IComponent
{
    protected final ILabeled label;
    
    public ComponentBase(final ILabeled label) {
        this.label = label;
    }
    
    @Override
    public String getTitle() {
        return this.label.getDisplayName();
    }
    
    @Override
    public void render(final Context context) {
        this.getHeight(context);
        if (context.isHovered() && this.label.getDescription() != null) {
            context.setDescription(new Description(context.getRect(), this.label.getDescription()));
        }
    }
    
    @Override
    public void handleButton(final Context context, final int button) {
        this.getHeight(context);
    }
    
    @Override
    public void handleKey(final Context context, final int scancode) {
        this.getHeight(context);
    }
    
    @Override
    public void handleChar(final Context context, final char character) {
        this.getHeight(context);
    }
    
    @Override
    public void handleScroll(final Context context, final int diff) {
        this.getHeight(context);
    }
    
    @Override
    public void getHeight(final Context context) {
        context.setHeight(this.getHeight());
    }
    
    @Override
    public void enter() {
    }
    
    @Override
    public void exit() {
    }
    
    @Override
    public boolean isVisible() {
        return this.label.isVisible().isOn();
    }
    
    protected abstract int getHeight();
}
