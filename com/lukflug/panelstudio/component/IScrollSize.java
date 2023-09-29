//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.*;

public interface IScrollSize
{
    default int getScrollHeight(final Context context, final int componentHeight) {
        return componentHeight;
    }
    
    default int getComponentWidth(final Context context) {
        return context.getSize().width;
    }
}
