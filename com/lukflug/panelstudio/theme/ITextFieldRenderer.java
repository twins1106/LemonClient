//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

public interface ITextFieldRenderer
{
    int renderTextField(final Context p0, final String p1, final boolean p2, final String p3, final int p4, final int p5, final int p6, final boolean p7);
    
    int getDefaultHeight();
    
    Rectangle getTextArea(final Context p0, final String p1);
    
    int transformToCharPos(final Context p0, final String p1, final String p2, final int p3);
}
