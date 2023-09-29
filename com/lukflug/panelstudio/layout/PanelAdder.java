//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.layout;

import com.lukflug.panelstudio.container.*;
import java.awt.*;
import java.util.function.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.widget.*;
import com.lukflug.panelstudio.component.*;

public class PanelAdder implements IComponentAdder
{
    protected IContainer<? super IFixedComponent> container;
    protected boolean open;
    protected IBoolean isVisible;
    protected UnaryOperator<String> configName;
    
    public PanelAdder(final IContainer<? super IFixedComponent> container, final boolean open, final IBoolean isVisible, final UnaryOperator<String> configName) {
        this.container = container;
        this.open = open;
        this.isVisible = isVisible;
        this.configName = configName;
    }
    
    public <S extends IComponent, T extends IComponent> void addComponent(final S title, final T content, final ThemeTuple theme, final Point position, final int width, final Supplier<Animation> animation) {
        final AnimatedToggleable toggle = new AnimatedToggleable((IToggleable)new SimpleToggleable(this.open), (Animation)animation.get());
        final RendererTuple<Void> renderer = new RendererTuple<Void>(Void.class, theme);
        final IResizable size = this.getResizable(width);
        this.container.addComponent((IComponent)ResizableComponent.createResizableComponent(title, content, () -> null, toggle, renderer, theme.theme.getResizeRenderer(), size, this.getScrollSize(size), position, width, true, (String)this.configName.apply(content.getTitle())), this.isVisible);
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
