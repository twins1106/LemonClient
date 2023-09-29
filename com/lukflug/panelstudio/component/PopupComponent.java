//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.popup.*;
import java.awt.*;
import com.lukflug.panelstudio.base.*;

public class PopupComponent<T extends IComponent> extends FixedComponent<T> implements IPopup
{
    protected Rectangle component;
    protected Rectangle panel;
    protected IPopupPositioner positioner;
    
    public PopupComponent(final T component, final int width) {
        super((IComponent)component, new Point(0, 0), width, (IToggleable)null, false, "");
    }
    
    public Point getPosition(final IInterface inter) {
        final Context temp = new Context(inter, this.width, this.position, true, true);
        this.getHeight(temp);
        return this.positioner.getPosition(inter, temp.getSize(), this.component, this.panel);
    }
    
    public void setPosition(final IInterface inter, final Rectangle component, final Rectangle panel, final IPopupPositioner positioner) {
        this.component = component;
        this.panel = panel;
        this.positioner = positioner;
    }
}
