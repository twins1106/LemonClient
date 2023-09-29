//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.container.*;
import com.lukflug.panelstudio.setting.*;
import java.util.function.*;
import java.util.concurrent.atomic.*;
import java.awt.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.theme.*;

public class ClosableComponent<S extends IComponent, T extends IComponent> extends FocusableComponentProxy<VerticalContainer>
{
    protected final S title;
    protected final CollapsibleComponent<T> collapsible;
    protected final VerticalContainer container;
    
    public <U> ClosableComponent(final S title, final T content, final Supplier<U> state, final AnimatedToggleable open, final IPanelRenderer<U> panelRenderer, final boolean focus) {
        super(focus);
        this.title = title;
        this.container = new VerticalContainer(new Labeled(content.getTitle(), (String)null, () -> content.isVisible()), panelRenderer) {
            public void render(final Context context) {
                super.render(context);
                panelRenderer.renderPanelOverlay(context, this.hasFocus(context), state.get(), open.isOn());
            }
            
            protected boolean hasFocus(final Context context) {
                return ClosableComponent.this.hasFocus(context);
            }
        };
        this.collapsible = new CollapsibleComponent<T>(open) {
            public T getComponent() {
                return content;
            }
        };
        this.container.addComponent((IComponent)new ComponentProxy<IComponent>(title) {
            public void render(final Context context) {
                super.render(context);
                panelRenderer.renderTitleOverlay(context, ClosableComponent.this.hasFocus(context), state.get(), open.isOn());
            }
            
            public void handleButton(final Context context, final int button) {
                super.handleButton(context, button);
                if (button == 1 && context.isClicked(button)) {
                    ClosableComponent.this.collapsible.getToggle().toggle();
                }
            }
        });
        this.container.addComponent((IComponent)this.collapsible);
    }
    
    public final VerticalContainer getComponent() {
        return this.container;
    }
    
    public IComponent getTitleBar() {
        return this.title;
    }
    
    public CollapsibleComponent<T> getCollapsible() {
        return this.collapsible;
    }
    
    public static <S extends IComponent, T extends IComponent, U> DraggableComponent<FixedComponent<ClosableComponent<ComponentProxy<S>, ScrollBarComponent<U, T>>>> createStaticPopup(final S title, final T content, final Supplier<U> state, final Animation animation, final RendererTuple<U> renderer, final IScrollSize popupSize, final IToggleable shown, final IntSupplier widthSupplier, final boolean savesState, final String configName, final boolean closeOnClick) {
        final AtomicReference<ClosableComponent<ComponentProxy<S>, ScrollBarComponent<U, T>>> panel = new AtomicReference<ClosableComponent<ComponentProxy<S>, ScrollBarComponent<U, T>>>(null);
        final DraggableComponent<FixedComponent<ClosableComponent<ComponentProxy<S>, ScrollBarComponent<U, T>>>> draggable = new DraggableComponent<FixedComponent<ClosableComponent<ComponentProxy<S>, ScrollBarComponent<U, T>>>>() {
            FixedComponent<ClosableComponent<ComponentProxy<S>, ScrollBarComponent<U, T>>> fixedComponent = null;
            
            public void handleButton(final Context context, final int button) {
                super.handleButton(context, button);
                if (context.getInterface().getButton(button) && (!context.isHovered() || closeOnClick) && shown.isOn()) {
                    shown.toggle();
                }
            }
            
            public boolean isVisible() {
                return super.isVisible() && shown.isOn();
            }
            
            public FixedComponent<ClosableComponent<ComponentProxy<S>, ScrollBarComponent<U, T>>> getComponent() {
                if (this.fixedComponent == null) {
                    this.fixedComponent = new FixedComponent<ClosableComponent<ComponentProxy<S>, ScrollBarComponent<U, T>>>((ClosableComponent)panel.get(), new Point(0, 0), widthSupplier.getAsInt(), panel.get().getCollapsible().getToggle(), savesState, configName) {
                        public int getWidth(final IInterface inter) {
                            return widthSupplier.getAsInt();
                        }
                    };
                }
                return this.fixedComponent;
            }
        };
        panel.set(createScrollableComponent(draggable.getWrappedDragComponent((IComponent)title), content, state, new AnimatedToggleable((IToggleable)new ConstantToggleable(true), animation), renderer, popupSize, true));
        return draggable;
    }
    
    public static <S extends IComponent, T extends IComponent, U> PopupComponent<ClosableComponent<S, ScrollBarComponent<U, T>>> createDynamicPopup(final S title, final T content, final Supplier<U> state, final Animation animation, final RendererTuple<U> renderer, final IScrollSize popupSize, final IToggleable shown, final int width) {
        final ClosableComponent<S, ScrollBarComponent<U, T>> panel = createScrollableComponent(title, content, state, new AnimatedToggleable((IToggleable)new ConstantToggleable(true), animation), renderer, popupSize, true);
        return new PopupComponent<ClosableComponent<S, ScrollBarComponent<U, T>>>(panel, width) {
            public void handleButton(final Context context, final int button) {
                this.doOperation(context, subContext -> ((ClosableComponent)this.getComponent()).handleButton(subContext, button));
                if (context.getInterface().getButton(button) && !context.isHovered() && shown.isOn()) {
                    shown.toggle();
                }
            }
            
            public boolean isVisible() {
                return ((ClosableComponent)this.getComponent()).isVisible() && shown.isOn();
            }
        };
    }
    
    public static <S extends IComponent, T extends IComponent, U> DraggableComponent<FixedComponent<ClosableComponent<ComponentProxy<S>, ScrollBarComponent<U, T>>>> createDraggableComponent(final S title, final T content, final Supplier<U> state, final AnimatedToggleable open, final RendererTuple<U> renderer, final IScrollSize scrollSize, final Point position, final int width, final boolean savesState, final String configName) {
        final AtomicReference<ClosableComponent<ComponentProxy<S>, ScrollBarComponent<U, T>>> panel = new AtomicReference<ClosableComponent<ComponentProxy<S>, ScrollBarComponent<U, T>>>(null);
        final DraggableComponent<FixedComponent<ClosableComponent<ComponentProxy<S>, ScrollBarComponent<U, T>>>> draggable = createDraggableComponent(() -> panel.get(), position, width, savesState, configName);
        panel.set(createScrollableComponent(draggable.getWrappedDragComponent((IComponent)title), content, state, open, renderer, scrollSize, false));
        return draggable;
    }
    
    public static <S extends IComponent, T extends IComponent, U> DraggableComponent<FixedComponent<ClosableComponent<S, T>>> createDraggableComponent(final Supplier<ClosableComponent<S, T>> panel, final Point position, final int width, final boolean savesState, final String configName) {
        return new DraggableComponent<FixedComponent<ClosableComponent<S, T>>>() {
            FixedComponent<ClosableComponent<S, T>> fixedComponent = null;
            
            public FixedComponent<ClosableComponent<S, T>> getComponent() {
                if (this.fixedComponent == null) {
                    this.fixedComponent = (FixedComponent<ClosableComponent<S, T>>)new FixedComponent((IComponent)panel.get(), position, width, (IToggleable)panel.get().getCollapsible().getToggle(), savesState, configName);
                }
                return this.fixedComponent;
            }
        };
    }
    
    public static <S extends IComponent, T extends IComponent, U> ClosableComponent<S, ScrollBarComponent<U, T>> createScrollableComponent(final S title, final T content, final Supplier<U> state, final AnimatedToggleable open, final RendererTuple<U> renderer, final IScrollSize scrollSize, final boolean focus) {
        return new ClosableComponent<S, ScrollBarComponent<U, T>>(title, new ScrollBarComponent<U, T>(content, renderer.scrollRenderer, renderer.cornerRenderer, renderer.emptyRenderer) {
            public int getScrollHeight(final Context context, final int componentHeight) {
                return scrollSize.getScrollHeight(context, componentHeight);
            }
            
            public int getComponentWidth(final Context context) {
                return scrollSize.getComponentWidth(context);
            }
            
            @Override
            protected U getState() {
                return state.get();
            }
        }, (Supplier<U>)state, open, (IPanelRenderer<U>)renderer.panelRenderer, focus);
    }
}
