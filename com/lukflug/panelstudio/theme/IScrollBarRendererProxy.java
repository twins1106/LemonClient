//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;

@FunctionalInterface
public interface IScrollBarRendererProxy<T> extends IScrollBarRenderer<T>
{
    default int renderScrollBar(final Context context, final boolean focus, final T state, final boolean horizontal, final int height, final int position) {
        return this.getRenderer().renderScrollBar(context, focus, (Object)state, horizontal, height, position);
    }
    
    default int getThickness() {
        return this.getRenderer().getThickness();
    }
    
    IScrollBarRenderer<T> getRenderer();
}
