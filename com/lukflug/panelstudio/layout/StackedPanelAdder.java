//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.layout;

import com.lukflug.panelstudio.container.*;
import java.awt.*;
import java.util.function.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.widget.*;
import com.lukflug.panelstudio.popup.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.setting.*;

public class StackedPanelAdder implements IComponentAdder, IScrollSize
{
    protected IContainer<? super IFixedComponent> container;
    protected ChildUtil.ChildMode mode;
    protected VerticalContainer content;
    protected ChildUtil util;
    protected IBoolean isVisible;
    
    public StackedPanelAdder(final IContainer<? super IFixedComponent> container, final ILabeled label, final ITheme theme, final Point position, final int width, final Supplier<Animation> animation, final ChildUtil.ChildMode mode, final IPopupPositioner popupPos, final IBoolean isVisible, final String configName) {
        this.container = container;
        this.mode = mode;
        this.isVisible = isVisible;
        this.content = new VerticalContainer(label, theme.getContainerRenderer(-1, -1, true));
        final IResizable size = this.getResizable(width);
        final IScrollSize scrollSize = this.getScrollSize(size);
        container.addComponent((IComponent)ResizableComponent.createResizableComponent(new Button<Void>(label, () -> null, theme.getButtonRenderer(Void.class, -1, -1, true)), this.content, () -> null, new AnimatedToggleable((IToggleable)new SimpleToggleable(true), (Animation)animation.get()), new RendererTuple<Object>((Class<Object>)Void.class, new ThemeTuple(theme, -1, -1)), theme.getResizeRenderer(), size, scrollSize, position, width, true, configName), isVisible);
        this.util = new ChildUtil(width, (Supplier)animation, new PopupTuple(popupPos, false, (IScrollSize)this));
    }
    
    public <S extends IComponent, T extends IComponent> void addComponent(final S title, final T content, final ThemeTuple theme, final Point position, final int width, final Supplier<Animation> animation) {
        this.util.addContainer((ILabeled)new Labeled(content.getTitle(), null, () -> content.isVisible()), (IComponent)title, (IComponent)content, () -> null, (Class)Void.class, this.content, (IComponentAdder)this, theme, this.mode);
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
