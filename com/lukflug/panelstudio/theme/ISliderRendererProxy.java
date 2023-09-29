//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

@FunctionalInterface
public interface ISliderRendererProxy extends ISliderRenderer
{
    default void renderSlider(final Context context, final String title, final String state, final boolean focus, final double value) {
        this.getRenderer().renderSlider(context, title, state, focus, value);
    }
    
    default int getDefaultHeight() {
        return this.getRenderer().getDefaultHeight();
    }
    
    default Rectangle getSlideArea(final Context context, final String title, final String state) {
        return this.getRenderer().getSlideArea(context, title, state);
    }
    
    ISliderRenderer getRenderer();
}
