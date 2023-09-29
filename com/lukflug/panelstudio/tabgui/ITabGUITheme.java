//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.popup.*;

public interface ITabGUITheme
{
    int getTabWidth();
    
    IPopupPositioner getPositioner();
    
    ITabGUIRenderer<Void> getParentRenderer();
    
    ITabGUIRenderer<Boolean> getChildRenderer();
}
