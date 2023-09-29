//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;

public interface IScrollBarRenderer<T>
{
    default int renderScrollBar(final Context context, final boolean focus, final T state, final boolean horizontal, final int height, final int position) {
        return position;
    }
    
    default int getThickness() {
        return 0;
    }
}
