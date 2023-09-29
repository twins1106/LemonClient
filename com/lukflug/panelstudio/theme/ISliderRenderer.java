//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

public interface ISliderRenderer
{
    void renderSlider(final Context p0, final String p1, final String p2, final boolean p3, final double p4);
    
    int getDefaultHeight();
    
    default Rectangle getSlideArea(final Context context, final String title, final String state) {
        return context.getRect();
    }
}
