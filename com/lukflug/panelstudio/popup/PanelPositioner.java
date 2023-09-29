//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.popup;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

public class PanelPositioner implements IPopupPositioner
{
    protected Point offset;
    
    public PanelPositioner(final Point offset) {
        this.offset = offset;
    }
    
    public Point getPosition(final IInterface inter, final Dimension popup, final Rectangle component, final Rectangle panel) {
        return new Point(panel.x + panel.width + this.offset.x, component.y + this.offset.y);
    }
}
