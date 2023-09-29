//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.container.*;
import com.lukflug.panelstudio.popup.*;
import java.util.function.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;
import com.lukflug.panelstudio.component.*;
import java.util.*;
import com.lukflug.panelstudio.setting.*;
import java.awt.*;
import com.lukflug.panelstudio.base.*;

public class TabGUI extends TabItem<ChildTab, Void>
{
    private final FixedComponent<TabGUI> fixedComponent;
    protected int width;
    protected IContainer<? super FixedComponent<Tab>> container;
    protected IPopupPositioner positioner;
    protected ITabGUIRenderer<Boolean> childRenderer;
    
    public TabGUI(final ILabeled label, final IClient client, final ITabGUITheme theme, final IContainer<? super FixedComponent<Tab>> container, final Supplier<Animation> animation, final IntPredicate up, final IntPredicate down, final IntPredicate enter, final IntPredicate exit, final Point position, final String configName) {
        super(label, theme.getParentRenderer(), animation.get(), up, down, enter, exit);
        this.width = theme.getTabWidth();
        this.container = container;
        this.positioner = theme.getPositioner();
        this.childRenderer = (ITabGUIRenderer<Boolean>)theme.getChildRenderer();
        final AtomicInteger i = new AtomicInteger(0);
        final ContentItem contentItem;
        final AtomicInteger atomicInteger;
        this.contents = client.getCategories().map(category -> {
            new ContentItem(category.getDisplayName(), new ChildTab(category, animation.get(), atomicInteger.getAndIncrement()));
            return contentItem;
        }).collect((Collector<? super Object, ?, List<ContentItem<S, T>>>)Collectors.toList());
        this.fixedComponent = (FixedComponent<TabGUI>)new FixedComponent((IComponent)this, position, this.width, (IToggleable)null, true, configName);
    }
    
    public FixedComponent<TabGUI> getWrappedComponent() {
        return this.fixedComponent;
    }
    
    @Override
    protected boolean hasChildren() {
        for (final ContentItem<ChildTab, Void> tab : this.contents) {
            if (tab.content.visible.isOn()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected void handleSelect(final Context context) {
        final ChildTab tab = (ChildTab)this.contents.get((int)this.tabState.getTarget()).content;
        if (!tab.visible.isOn()) {
            tab.visible.toggle();
        }
    }
    
    @Override
    protected void handleExit(final Context context) {
        final ChildTab tab = (ChildTab)this.contents.get((int)this.tabState.getTarget()).content;
        if (tab.visible.isOn()) {
            tab.visible.toggle();
        }
    }
    
    protected class ChildTab implements Supplier<Void>
    {
        public final FixedComponent<Tab> tab;
        public final IToggleable visible;
        
        public ChildTab(final ICategory category, final Animation animation, final int index) {
            this.tab = new FixedComponent<Tab>(new Tab(category, (ITabGUIRenderer)TabGUI.this.childRenderer, animation, TabGUI.this.up, TabGUI.this.down, TabGUI.this.enter), new Point(0, 0), TabGUI.this.width, null, false, category.getDisplayName()) {
                public Point getPosition(final IInterface inter) {
                    final Rectangle rect = new Rectangle(TabGUI.this.fixedComponent.getPosition(inter), new Dimension(this.width, TabGUI.this.getHeight()));
                    final Dimension dim = new Dimension(this.width, ((Tab)this.component).getHeight());
                    return TabGUI.this.positioner.getPosition(inter, dim, TabGUI.this.renderer.getItemRect(inter, rect, TabGUI.this.contents.size(), (double)index), rect);
                }
            };
            this.visible = (IToggleable)new SimpleToggleable(false);
            TabGUI.this.container.addComponent((IComponent)this.tab, (IBoolean)this.visible);
        }
        
        @Override
        public Void get() {
            return null;
        }
    }
}
