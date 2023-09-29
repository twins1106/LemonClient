//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.component.*;
import java.util.function.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;

public class Button<T> extends FocusableComponent
{
    protected Supplier<T> state;
    protected IButtonRenderer<T> renderer;
    
    public Button(final ILabeled label, final Supplier<T> state, final IButtonRenderer<T> renderer) {
        super(label);
        this.renderer = renderer;
        this.state = state;
    }
    
    public void render(final Context context) {
        super.render(context);
        this.renderer.renderButton(context, this.getTitle(), this.hasFocus(context), (Object)this.state.get());
    }
    
    protected int getHeight() {
        return this.renderer.getDefaultHeight();
    }
}
