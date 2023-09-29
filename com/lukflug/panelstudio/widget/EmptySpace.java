//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.component.*;
import java.util.function.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;

public abstract class EmptySpace<T> extends ComponentBase
{
    protected Supplier<Integer> height;
    protected IEmptySpaceRenderer<T> renderer;
    
    public EmptySpace(final ILabeled label, final Supplier<Integer> height, final IEmptySpaceRenderer<T> renderer) {
        super(label);
        this.height = height;
        this.renderer = renderer;
    }
    
    public void render(final Context context) {
        super.getHeight(context);
        this.renderer.renderSpace(context, this.isVisible(), this.getState());
    }
    
    public void releaseFocus() {
    }
    
    protected int getHeight() {
        return this.height.get();
    }
    
    protected abstract T getState();
}
