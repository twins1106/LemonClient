//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.*;

public interface IComponent
{
    String getTitle();
    
    void render(final Context p0);
    
    void handleButton(final Context p0, final int p1);
    
    void handleKey(final Context p0, final int p1);
    
    void handleChar(final Context p0, final char p1);
    
    void handleScroll(final Context p0, final int p1);
    
    void getHeight(final Context p0);
    
    void enter();
    
    void exit();
    
    void releaseFocus();
    
    boolean isVisible();
}
