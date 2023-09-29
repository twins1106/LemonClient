//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

@FunctionalInterface
public interface IColorPickerRendererProxy extends IColorPickerRenderer
{
    default void renderPicker(final Context context, final boolean focus, final Color color) {
        this.getRenderer().renderPicker(context, focus, color);
    }
    
    default Color transformPoint(final Context context, final Color color, final Point point) {
        return this.getRenderer().transformPoint(context, color, point);
    }
    
    default int getDefaultHeight(final int width) {
        return this.getRenderer().getDefaultHeight(width);
    }
    
    IColorPickerRenderer getRenderer();
}
