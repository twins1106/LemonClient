//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.component.*;
import java.util.*;
import java.util.function.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;

public abstract class TabItem<S extends Supplier<T>, T> extends ComponentBase
{
    protected ITabGUIRenderer<T> renderer;
    protected List<ContentItem<S, T>> contents;
    protected Animation tabState;
    protected final IntPredicate up;
    protected final IntPredicate down;
    protected final IntPredicate enter;
    protected final IntPredicate exit;
    
    public TabItem(final ILabeled label, final ITabGUIRenderer<T> renderer, final Animation animation, final IntPredicate up, final IntPredicate down, final IntPredicate enter, final IntPredicate exit) {
        super(label);
        this.renderer = renderer;
        this.tabState = animation;
        this.up = up;
        this.down = down;
        this.enter = enter;
        this.exit = exit;
    }
    
    public void render(final Context context) {
        super.render(context);
        this.renderer.renderTab(context, this.contents.size(), this.tabState.getValue());
        for (int i = 0; i < this.contents.size(); ++i) {
            this.renderer.renderItem(context, this.contents.size(), this.tabState.getValue(), i, this.contents.get(i).name, ((Supplier<Object>)this.contents.get(i).content).get());
        }
    }
    
    public void handleKey(final Context context, final int key) {
        super.handleKey(context, key);
        if (!this.hasChildren()) {
            if (this.up.test(key)) {
                int nextState = (int)this.tabState.getTarget() - 1;
                if (nextState < 0) {
                    nextState = this.contents.size() - 1;
                }
                this.tabState.setValue((double)nextState);
            }
            else if (this.down.test(key)) {
                int nextState = (int)this.tabState.getTarget() + 1;
                if (nextState >= this.contents.size()) {
                    nextState = 0;
                }
                this.tabState.setValue((double)nextState);
            }
            else if (this.enter.test(key)) {
                this.handleSelect(context);
            }
        }
        if (this.exit.test(key)) {
            this.handleExit(context);
        }
    }
    
    public void releaseFocus() {
    }
    
    protected int getHeight() {
        return this.renderer.getTabHeight(this.contents.size());
    }
    
    protected boolean hasChildren() {
        return false;
    }
    
    protected abstract void handleSelect(final Context p0);
    
    protected abstract void handleExit(final Context p0);
    
    protected static final class ContentItem<S extends Supplier<T>, T>
    {
        public final String name;
        public final S content;
        
        public ContentItem(final String name, final S content) {
            this.name = name;
            this.content = content;
        }
    }
}
