//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

public interface IColorPickerRenderer
{
    void renderPicker(final Context p0, final boolean p1, final Color p2);
    
    Color transformPoint(final Context p0, final Color p1, final Point p2);
    
    int getDefaultHeight(final int p0);
}
