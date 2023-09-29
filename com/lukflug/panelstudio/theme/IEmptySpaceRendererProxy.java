//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;

@FunctionalInterface
public interface IEmptySpaceRendererProxy<T> extends IEmptySpaceRenderer<T>
{
    default void renderSpace(final Context context, final boolean focus, final T state) {
        this.getRenderer().renderSpace(context, focus, (Object)state);
    }
    
    IEmptySpaceRenderer<T> getRenderer();
}
