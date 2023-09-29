//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

public abstract class CollapsibleComponent<T extends IComponent> implements IComponentProxy<T>
{
    protected AnimatedToggleable toggle;
    
    public CollapsibleComponent(final IToggleable toggle, final Animation animation) {
        this.toggle = new AnimatedToggleable(toggle, animation);
    }
    
    public CollapsibleComponent(final AnimatedToggleable toggle) {
        this.toggle = toggle;
    }
    
    @Override
    public void render(final Context context) {
        this.doOperation(context, subContext -> {
            context.getInterface().window(context.getRect());
            this.getComponent().render(subContext);
            context.getInterface().restore();
        });
    }
    
    @Override
    public boolean isVisible() {
        return this.getComponent().isVisible() && this.toggle.getValue() != 0.0;
    }
    
    @Override
    public Context getContext(final Context context) {
        final Context subContext = new Context(context, context.getSize().width, new Point(0, 0), true, true);
        this.getComponent().getHeight(subContext);
        final int height = this.getHeight(subContext.getSize().height);
        final int offset = height - subContext.getSize().height;
        context.setHeight(height);
        return new Context(context, context.getSize().width, new Point(0, offset), true, context.isHovered());
    }
    
    @Override
    public int getHeight(final int height) {
        return (int)(this.toggle.getValue() * height);
    }
    
    public AnimatedToggleable getToggle() {
        return this.toggle;
    }
}
