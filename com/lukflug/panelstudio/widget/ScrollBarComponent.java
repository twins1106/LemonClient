//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import java.awt.*;
import com.lukflug.panelstudio.container.*;
import java.util.function.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.base.*;

public abstract class ScrollBarComponent<S, T extends IComponent> extends HorizontalContainer implements IScrollSize
{
    protected final T component;
    
    public ScrollBarComponent(final T component, final IScrollBarRenderer<S> renderer, final IEmptySpaceRenderer<S> cornerRenderer, final IEmptySpaceRenderer<S> emptyRenderer) {
        super((ILabeled)new Labeled(component.getTitle(), (String)null, () -> component.isVisible()), (IContainerRenderer)new IContainerRenderer() {});
        this.component = component;
        final ScrollableComponent<T> scrollComponent = new ScrollableComponent<T>() {
            public T getComponent() {
                return component;
            }
            
            public int getScrollHeight(final Context context, final int height) {
                return ScrollBarComponent.this.getScrollHeight(context, height);
            }
            
            public int getComponentWidth(final Context context) {
                return ScrollBarComponent.this.getComponentWidth(context);
            }
            
            public void fillEmptySpace(final Context context, final Rectangle rect) {
                final Context subContext = new Context(context.getInterface(), rect.width, rect.getLocation(), context.hasFocus(), context.onTop());
                subContext.setHeight(rect.height);
                emptyRenderer.renderSpace(subContext, context.hasFocus(), ScrollBarComponent.this.getState());
            }
        };
        final ScrollBar<S> verticalBar = new ScrollBar<S>(new Labeled(component.getTitle(), (String)null, () -> scrollComponent.isScrollingY()), false, renderer) {
            protected int getLength() {
                return scrollComponent.getScrollSize().height;
            }
            
            protected int getContentHeight() {
                return scrollComponent.getContentSize().height;
            }
            
            protected int getScrollPosition() {
                return scrollComponent.getScrollPos().y;
            }
            
            protected void setScrollPosition(final int position) {
                scrollComponent.setScrollPosY(position);
            }
            
            protected S getState() {
                return ScrollBarComponent.this.getState();
            }
        };
        final ScrollBar<S> horizontalBar = new ScrollBar<S>(new Labeled(component.getTitle(), (String)null, () -> scrollComponent.isScrollingX()), true, renderer) {
            protected int getLength() {
                return scrollComponent.getScrollSize().width;
            }
            
            protected int getContentHeight() {
                return scrollComponent.getContentSize().width;
            }
            
            protected int getScrollPosition() {
                return scrollComponent.getScrollPos().x;
            }
            
            protected void setScrollPosition(final int position) {
                scrollComponent.setScrollPosX(position);
            }
            
            protected S getState() {
                return ScrollBarComponent.this.getState();
            }
        };
        final VerticalContainer leftContainer = new VerticalContainer((ILabeled)new Labeled(component.getTitle(), (String)null, () -> true), (IContainerRenderer)new IContainerRenderer() {});
        leftContainer.addComponent((IComponent)scrollComponent);
        leftContainer.addComponent((IComponent)horizontalBar);
        final VerticalContainer rightContainer = new VerticalContainer((ILabeled)new Labeled(component.getTitle(), (String)null, () -> true), (IContainerRenderer)new IContainerRenderer() {});
        rightContainer.addComponent((IComponent)verticalBar);
        rightContainer.addComponent((IComponent)new EmptySpace<S>(new Labeled("Empty", (String)null, () -> scrollComponent.isScrollingX() && scrollComponent.isScrollingY()), () -> renderer.getThickness(), cornerRenderer) {
            protected S getState() {
                return ScrollBarComponent.this.getState();
            }
        });
        this.addComponent((IComponent)new HorizontalComponent((IComponent)leftContainer, 0, 1));
        this.addComponent((IComponent)new HorizontalComponent<VerticalContainer>(rightContainer, 0, 0) {
            public int getWidth(final IInterface inter) {
                return renderer.getThickness();
            }
        }, () -> scrollComponent.isScrollingY());
    }
    
    public T getContentComponent() {
        return this.component;
    }
    
    protected abstract S getState();
}
