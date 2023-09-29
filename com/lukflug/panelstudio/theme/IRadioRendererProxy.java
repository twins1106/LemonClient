//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.setting.*;
import java.awt.*;

@FunctionalInterface
public interface IRadioRendererProxy extends IRadioRenderer
{
    default void renderItem(final Context context, final ILabeled[] items, final boolean focus, final int target, final double state, final boolean horizontal) {
        this.getRenderer().renderItem(context, items, focus, target, state, horizontal);
    }
    
    default int getDefaultHeight(final ILabeled[] items, final boolean horizontal) {
        return this.getRenderer().getDefaultHeight(items, horizontal);
    }
    
    default Rectangle getItemRect(final Context context, final ILabeled[] items, final int index, final boolean horizontal) {
        return this.getRenderer().getItemRect(context, items, index, horizontal);
    }
    
    IRadioRenderer getRenderer();
}
