//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.config;

import java.awt.*;

public interface IPanelConfig
{
    void savePositon(final Point p0);
    
    void saveSize(final Dimension p0);
    
    Point loadPosition();
    
    Dimension loadSize();
    
    void saveState(final boolean p0);
    
    boolean loadState();
}
