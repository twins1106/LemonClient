//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.container;

import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.theme.*;
import java.awt.*;
import com.lukflug.panelstudio.popup.*;
import java.util.concurrent.atomic.*;
import com.lukflug.panelstudio.component.*;
import java.util.*;
import java.util.function.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.config.*;

public class FixedContainer extends Container<IFixedComponent> implements IPopupDisplayer
{
    protected boolean clip;
    protected List<PopupPair> popups;
    
    public FixedContainer(final ILabeled label, final IContainerRenderer renderer, final boolean clip) {
        super(label, renderer);
        this.popups = new ArrayList<PopupPair>();
        this.clip = clip;
    }
    
    public void displayPopup(final IPopup popup, final Rectangle rect, final IToggleable visible, final IPopupPositioner positioner) {
        this.popups.add(new PopupPair(popup, rect, visible, positioner));
    }
    
    public void render(final Context context) {
        context.setHeight(this.getHeight());
        if (this.clip) {
            context.getInterface().window(context.getRect());
        }
        if (this.renderer != null) {
            this.renderer.renderBackground(context, context.hasFocus());
        }
        final AtomicReference<IFixedComponent> highest = new AtomicReference<IFixedComponent>(null);
        final AtomicReference<IFixedComponent> first = new AtomicReference<IFixedComponent>(null);
        final AtomicReference<IFixedComponent> atomicReference;
        final Context subContext;
        final AtomicReference<IFixedComponent> atomicReference2;
        this.doContextlessLoop(component -> {
            if (atomicReference.get() == null) {
                atomicReference.set(component);
            }
            subContext = this.getSubContext(context, component, true, true);
            component.getHeight(subContext);
            if (subContext.isHovered() && atomicReference2.get() == null) {
                atomicReference2.set(component);
            }
            return;
        });
        final AtomicBoolean highestReached = new AtomicBoolean(false);
        if (highest.get() == null) {
            highestReached.set(true);
        }
        final AtomicReference<IFixedComponent> focusComponent = new AtomicReference<IFixedComponent>(null);
        final AtomicReference<IFixedComponent> atomicReference3;
        final AtomicBoolean atomicBoolean;
        final AtomicReference<IFixedComponent> atomicReference4;
        final Context subContext2;
        final AtomicReference<IFixedComponent> atomicReference5;
        final Iterator<PopupPair> iterator;
        PopupPair popup;
        super.doContextlessLoop(component -> {
            if (component == atomicReference3.get()) {
                atomicBoolean.set(true);
            }
            subContext2 = this.getSubContext(context, component, component == atomicReference4.get(), atomicBoolean.get());
            component.render(subContext2);
            if (subContext2.focusReleased()) {
                context.releaseFocus();
            }
            else if (subContext2.foucsRequested()) {
                atomicReference5.set(component);
                context.requestFocus();
            }
            if (subContext2.isHovered() && subContext2.getDescription() != null) {
                context.setDescription(new Description(subContext2.getDescription(), subContext2.getRect()));
            }
            this.popups.iterator();
            while (iterator.hasNext()) {
                popup = iterator.next();
                popup.popup.setPosition(context.getInterface(), popup.rect, subContext2.getRect(), popup.positioner);
                if (!popup.visible.isOn()) {
                    popup.visible.toggle();
                }
                if (popup.popup instanceof IFixedComponent) {
                    atomicReference5.set((IFixedComponent)popup.popup);
                }
            }
            this.popups.clear();
            return;
        });
        if (focusComponent.get() != null && this.removeComponent((IComponent)focusComponent.get())) {
            this.addComponent((IComponent)focusComponent.get());
        }
        if (context.getDescription() == null && this.label.getDescription() != null) {
            context.setDescription(new Description(context.getRect(), this.label.getDescription()));
        }
        if (this.clip) {
            context.getInterface().restore();
        }
    }
    
    protected void doContextlessLoop(final Consumer<IFixedComponent> function) {
        final List<Container.ComponentState> components = new ArrayList<Container.ComponentState>();
        for (final Container.ComponentState state : this.components) {
            components.add(state);
        }
        for (final Container.ComponentState state : components) {
            state.update();
        }
        for (int i = components.size() - 1; i >= 0; --i) {
            final Container.ComponentState state = components.get(i);
            if (state.lastVisible()) {
                function.accept((IFixedComponent)state.component);
            }
        }
    }
    
    protected void doContextSensitiveLoop(final Context context, final Container.ContextSensitiveConsumer<IFixedComponent> function) {
        context.setHeight(this.getHeight());
        final AtomicBoolean highest = new AtomicBoolean(true);
        final AtomicBoolean first = new AtomicBoolean(true);
        final AtomicReference<IFixedComponent> focusComponent = new AtomicReference<IFixedComponent>(null);
        final AtomicBoolean atomicBoolean;
        final AtomicBoolean atomicBoolean2;
        final Context subContext;
        final AtomicReference<IFixedComponent> atomicReference;
        final Iterator<PopupPair> iterator;
        PopupPair popup;
        this.doContextlessLoop(component -> {
            subContext = this.getSubContext(context, component, atomicBoolean.get(), atomicBoolean2.get());
            atomicBoolean.set(false);
            function.accept(subContext, (IComponent)component);
            if (subContext.focusReleased()) {
                context.releaseFocus();
            }
            else if (subContext.foucsRequested()) {
                atomicReference.set(component);
                context.requestFocus();
            }
            if (subContext.isHovered()) {
                atomicBoolean2.set(false);
            }
            this.popups.iterator();
            while (iterator.hasNext()) {
                popup = iterator.next();
                popup.popup.setPosition(context.getInterface(), popup.rect, subContext.getRect(), popup.positioner);
                if (!popup.visible.isOn()) {
                    popup.visible.toggle();
                }
                if (popup.popup instanceof IFixedComponent) {
                    atomicReference.set((IFixedComponent)popup.popup);
                }
            }
            this.popups.clear();
            return;
        });
        if (focusComponent.get() != null) {
            final Container.ComponentState focusState = (Container.ComponentState)this.components.stream().filter(state -> state.component == focusComponent.get()).findFirst().orElse(null);
            if (focusState != null) {
                this.components.remove(focusState);
                this.components.add(focusState);
            }
        }
    }
    
    protected Context getSubContext(final Context context, final IFixedComponent component, final boolean focus, final boolean highest) {
        final Context subContext = new Context(context, component.getWidth(context.getInterface()), component.getPosition(context.getInterface()), context.hasFocus() && focus, highest);
        subContext.setPopupDisplayer((IPopupDisplayer)this);
        return subContext;
    }
    
    public void saveConfig(final IInterface inter, final IConfigList config) {
        config.begin(false);
        for (final Container.ComponentState state : this.components) {
            if (((IFixedComponent)state.component).savesState()) {
                final IPanelConfig cf = config.addPanel(((IFixedComponent)state.component).getConfigName());
                if (cf == null) {
                    continue;
                }
                ((IFixedComponent)state.component).saveConfig(inter, cf);
            }
        }
        config.end(false);
    }
    
    public void loadConfig(final IInterface inter, final IConfigList config) {
        config.begin(true);
        for (final Container.ComponentState state : this.components) {
            if (((IFixedComponent)state.component).savesState()) {
                final IPanelConfig cf = config.getPanel(((IFixedComponent)state.component).getConfigName());
                if (cf == null) {
                    continue;
                }
                ((IFixedComponent)state.component).loadConfig(inter, cf);
            }
        }
        config.end(true);
    }
    
    protected final class PopupPair
    {
        public final IPopup popup;
        public final Rectangle rect;
        public final IToggleable visible;
        public final IPopupPositioner positioner;
        
        public PopupPair(final IPopup popup, final Rectangle rect, final IToggleable visible, final IPopupPositioner positioner) {
            this.popup = popup;
            this.rect = rect;
            this.visible = visible;
            this.positioner = positioner;
        }
    }
}
