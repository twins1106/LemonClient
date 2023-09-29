//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;

@FunctionalInterface
public interface IButtonRendererProxy<T> extends IButtonRenderer<T>
{
    default void renderButton(final Context context, final String title, final boolean focus, final T state) {
        this.getRenderer().renderButton(context, title, focus, (Object)state);
    }
    
    default int getDefaultHeight() {
        return this.getRenderer().getDefaultHeight();
    }
    
    IButtonRenderer<T> getRenderer();
}
