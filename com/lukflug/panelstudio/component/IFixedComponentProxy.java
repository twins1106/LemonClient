//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.*;
import java.awt.*;
import com.lukflug.panelstudio.popup.*;
import com.lukflug.panelstudio.config.*;

@FunctionalInterface
public interface IFixedComponentProxy<T extends IFixedComponent> extends IComponentProxy<T>, IFixedComponent
{
    default Point getPosition(final IInterface inter) {
        return ((IFixedComponent)this.getComponent()).getPosition(inter);
    }
    
    default void setPosition(final IInterface inter, final Point position) {
        ((IFixedComponent)this.getComponent()).setPosition(inter, position);
    }
    
    default void setPosition(final IInterface inter, final Rectangle component, final Rectangle panel, final IPopupPositioner positioner) {
        ((IFixedComponent)this.getComponent()).setPosition(inter, component, panel, positioner);
    }
    
    default int getWidth(final IInterface inter) {
        return ((IFixedComponent)this.getComponent()).getWidth(inter);
    }
    
    default boolean savesState() {
        return ((IFixedComponent)this.getComponent()).savesState();
    }
    
    default void saveConfig(final IInterface inter, final IPanelConfig config) {
        ((IFixedComponent)this.getComponent()).saveConfig(inter, config);
    }
    
    default void loadConfig(final IInterface inter, final IPanelConfig config) {
        ((IFixedComponent)this.getComponent()).loadConfig(inter, config);
    }
    
    default String getConfigName() {
        return ((IFixedComponent)this.getComponent()).getConfigName();
    }
}
