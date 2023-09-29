//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.popup.*;
import java.awt.*;
import com.lukflug.panelstudio.config.*;

public interface IFixedComponent extends IComponent, IPopup
{
    Point getPosition(final IInterface p0);
    
    void setPosition(final IInterface p0, final Point p1);
    
    default void setPosition(final IInterface inter, final Rectangle component, final Rectangle panel, final IPopupPositioner positioner) {
        this.setPosition(inter, positioner.getPosition(inter, null, component, panel));
    }
    
    int getWidth(final IInterface p0);
    
    boolean savesState();
    
    void saveConfig(final IInterface p0, final IPanelConfig p1);
    
    void loadConfig(final IInterface p0, final IPanelConfig p1);
    
    String getConfigName();
}
