//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.container;

import com.lukflug.panelstudio.popup.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.base.*;
import java.awt.*;
import com.lukflug.panelstudio.config.*;

public class GUI implements IContainer<IFixedComponent>
{
    protected FixedContainer container;
    protected IInterface inter;
    protected IDescriptionRenderer descriptionRenderer;
    protected IPopupPositioner descriptionPosition;
    
    public GUI(final IInterface inter, final IDescriptionRenderer descriptionRenderer, final IPopupPositioner descriptionPosition) {
        this.inter = inter;
        this.descriptionRenderer = descriptionRenderer;
        this.descriptionPosition = descriptionPosition;
        this.container = new FixedContainer(() -> "GUI", (IContainerRenderer)null, false);
    }
    
    @Override
    public boolean addComponent(final IFixedComponent component) {
        return this.container.addComponent((IComponent)component);
    }
    
    @Override
    public boolean addComponent(final IFixedComponent component, final IBoolean visible) {
        return this.container.addComponent((IComponent)component, visible);
    }
    
    @Override
    public boolean removeComponent(final IFixedComponent component) {
        return this.container.removeComponent((IComponent)component);
    }
    
    public void render() {
        final Context context = this.getContext();
        this.container.render(context);
        if (context.getDescription() != null) {
            final Point pos = this.descriptionPosition.getPosition(this.inter, null, context.getDescription().getComponentPos(), context.getDescription().getPanelPos());
            this.descriptionRenderer.renderDescription(this.inter, pos, context.getDescription().getContent());
        }
    }
    
    public void handleButton(final int button) {
        this.container.handleButton(this.getContext(), button);
    }
    
    public void handleKey(final int scancode) {
        this.container.handleKey(this.getContext(), scancode);
    }
    
    public void handleChar(final char character) {
        this.container.handleChar(this.getContext(), character);
    }
    
    public void handleScroll(final int diff) {
        this.container.handleScroll(this.getContext(), diff);
    }
    
    public void enter() {
        this.container.enter();
    }
    
    public void exit() {
        this.container.exit();
    }
    
    public void saveConfig(final IConfigList config) {
        this.container.saveConfig(this.inter, config);
    }
    
    public void loadConfig(final IConfigList config) {
        this.container.loadConfig(this.inter, config);
    }
    
    protected Context getContext() {
        return new Context(this.inter, 0, new Point(0, 0), true, true);
    }
}
