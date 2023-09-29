//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.layout;

import com.lukflug.panelstudio.container.*;
import com.lukflug.panelstudio.setting.*;
import java.awt.*;
import java.util.function.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.widget.*;
import com.lukflug.panelstudio.theme.*;

public class SinglePanelAdder implements IComponentAdder
{
    protected IContainer<? super IFixedComponent> container;
    protected IBoolean isVisible;
    protected HorizontalContainer title;
    protected HorizontalContainer content;
    protected final IScrollSize size;
    
    public SinglePanelAdder(final IContainer<? super IFixedComponent> container, final ILabeled label, final ITheme theme, final Point position, final int width, final Supplier<Animation> animation, final IBoolean isVisible, final String configName) {
        this.container = container;
        this.isVisible = isVisible;
        this.title = new HorizontalContainer(label, theme.getContainerRenderer(-1, -1, true));
        this.content = new HorizontalContainer(label, theme.getContainerRenderer(-1, -1, true));
        final AnimatedToggleable toggle = new AnimatedToggleable((IToggleable)new SimpleToggleable(true), (Animation)animation.get());
        final RendererTuple<Void> renderer = new RendererTuple<Void>(Void.class, new ThemeTuple(theme, -1, -1));
        final IResizable size = this.getResizable(width);
        this.size = this.getScrollSize(size);
        container.addComponent((IComponent)ResizableComponent.createResizableComponent(this.title, this.content, () -> null, toggle, (RendererTuple<Object>)renderer, theme.getResizeRenderer(), size, (IScrollSize)new IScrollSize() {
            public int getComponentWidth(final Context context) {
                return SinglePanelAdder.this.size.getComponentWidth(context);
            }
        }, position, width, true, configName), isVisible);
    }
    
    public <S extends IComponent, T extends IComponent> void addComponent(final S title, final T content, final ThemeTuple theme, final Point position, final int width, final Supplier<Animation> animation) {
        this.title.addComponent((IComponent)new HorizontalComponent((IComponent)title, 0, 1));
        this.content.addComponent((IComponent)new HorizontalComponent((IComponent)new ScrollBarComponent<Void, T>(content, theme.getScrollBarRenderer(Void.class), theme.getEmptySpaceRenderer(Void.class, false), theme.getEmptySpaceRenderer(Void.class, true)) {
            public int getScrollHeight(final Context context, final int componentHeight) {
                return SinglePanelAdder.this.size.getScrollHeight(context, componentHeight);
            }
            
            @Override
            protected Void getState() {
                return null;
            }
        }, 0, 1));
    }
    
    public void addPopup(final IFixedComponent popup) {
        this.container.addComponent((IComponent)popup, this.isVisible);
    }
    
    protected IResizable getResizable(final int width) {
        return null;
    }
    
    protected IScrollSize getScrollSize(final IResizable size) {
        return (IScrollSize)new IScrollSize() {};
    }
}
