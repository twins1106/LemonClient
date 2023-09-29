//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.container;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;
import java.util.*;
import java.util.function.*;

public abstract class Container<T extends IComponent> extends ComponentBase implements IContainer<T>
{
    protected List<ComponentState> components;
    protected IContainerRenderer renderer;
    private boolean visible;
    
    public Container(final ILabeled label, final IContainerRenderer renderer) {
        super(label);
        this.components = new ArrayList<ComponentState>();
        this.renderer = renderer;
    }
    
    public boolean addComponent(final T component) {
        if (this.getComponentState(component) == null) {
            this.components.add(new ComponentState(component, this.getDefaultVisibility()));
            return true;
        }
        return false;
    }
    
    public boolean addComponent(final T component, final IBoolean visible) {
        if (this.getComponentState(component) == null) {
            this.components.add(new ComponentState(component, visible));
            return true;
        }
        return false;
    }
    
    public boolean removeComponent(final T component) {
        final ComponentState state = this.getComponentState(component);
        if (state != null) {
            this.components.remove(state);
            if (state.lastVisible) {
                state.component.exit();
            }
            return true;
        }
        return false;
    }
    
    public void render(final Context context) {
        this.getHeight(context);
        if (this.renderer != null) {
            this.renderer.renderBackground(context, context.hasFocus());
        }
        this.doContextSensitiveLoop(context, (subContext, component) -> {
            component.render(subContext);
            if (subContext.isHovered() && subContext.getDescription() != null) {
                context.setDescription(new Description(subContext.getDescription(), subContext.getRect()));
            }
            return;
        });
        if (context.getDescription() == null && this.label.getDescription() != null) {
            context.setDescription(new Description(context.getRect(), this.label.getDescription()));
        }
    }
    
    public void handleButton(final Context context, final int button) {
        this.doContextSensitiveLoop(context, (subContext, component) -> component.handleButton(subContext, button));
    }
    
    public void handleKey(final Context context, final int scancode) {
        this.doContextSensitiveLoop(context, (subContext, component) -> component.handleKey(subContext, scancode));
    }
    
    public void handleChar(final Context context, final char character) {
        this.doContextSensitiveLoop(context, (subContext, component) -> component.handleChar(subContext, character));
    }
    
    public void handleScroll(final Context context, final int diff) {
        this.doContextSensitiveLoop(context, (subContext, component) -> component.handleScroll(subContext, diff));
    }
    
    public void getHeight(final Context context) {
        this.doContextSensitiveLoop(context, (subContext, component) -> component.getHeight(subContext));
    }
    
    public void enter() {
        this.visible = true;
        this.doContextlessLoop(component -> {});
    }
    
    public void exit() {
        this.visible = false;
        this.doContextlessLoop(component -> {});
    }
    
    public void releaseFocus() {
        this.doContextlessLoop(IComponent::releaseFocus);
    }
    
    protected int getHeight() {
        return 0;
    }
    
    protected ComponentState getComponentState(final T component) {
        for (final ComponentState state : this.components) {
            if (state.component == component) {
                return state;
            }
        }
        return null;
    }
    
    protected void doContextlessLoop(final Consumer<T> function) {
        final List<ComponentState> components = new ArrayList<ComponentState>();
        for (final ComponentState state : this.components) {
            components.add(state);
        }
        for (final ComponentState state : components) {
            state.update();
        }
        for (final ComponentState state : components) {
            if (state.lastVisible()) {
                function.accept(state.component);
            }
        }
    }
    
    protected abstract void doContextSensitiveLoop(final Context p0, final ContextSensitiveConsumer<T> p1);
    
    protected IBoolean getDefaultVisibility() {
        return () -> true;
    }
    
    protected final class ComponentState
    {
        public final T component;
        public final IBoolean externalVisibility;
        private boolean lastVisible;
        
        public ComponentState(final T component, final IBoolean externalVisibility) {
            this.lastVisible = false;
            this.component = component;
            this.externalVisibility = externalVisibility;
            this.update();
        }
        
        public void update() {
            if ((this.component.isVisible() && this.externalVisibility.isOn() && Container.this.visible) != this.lastVisible) {
                if (this.lastVisible) {
                    this.lastVisible = false;
                    this.component.exit();
                }
                else {
                    this.lastVisible = true;
                    this.component.enter();
                }
            }
        }
        
        public boolean lastVisible() {
            return this.lastVisible;
        }
    }
    
    @FunctionalInterface
    protected interface ContextSensitiveConsumer<T extends IComponent>
    {
        void accept(final Context p0, final T p1);
    }
}
