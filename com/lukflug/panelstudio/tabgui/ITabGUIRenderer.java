//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

public interface ITabGUIRenderer<T>
{
    void renderTab(final Context p0, final int p1, final double p2);
    
    void renderItem(final Context p0, final int p1, final double p2, final int p3, final String p4, final T p5);
    
    int getTabHeight(final int p0);
    
    Rectangle getItemRect(final IInterface p0, final Rectangle p1, final int p2, final double p3);
}
