//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.tabgui;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.setting.*;

public class Tab extends TabItem<IToggleable, Boolean>
{
    public Tab(final ICategory category, final ITabGUIRenderer<Boolean> renderer, final Animation animation, final IntPredicate up, final IntPredicate down, final IntPredicate enter) {
        super((ILabeled)category, renderer, animation, up, down, enter, key -> false);
        this.contents = category.getModules().map(module -> new ContentItem(module.getDisplayName(), (Supplier)module.isEnabled())).collect((Collector<? super Object, ?, List<ContentItem<S, T>>>)Collectors.toList());
    }
    
    @Override
    protected void handleSelect(final Context context) {
        ((IToggleable)this.contents.get((int)this.tabState.getTarget()).content).toggle();
    }
    
    @Override
    protected void handleExit(final Context context) {
    }
}
