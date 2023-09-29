//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.popup;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

public class MousePositioner implements IPopupPositioner
{
    protected Point offset;
    
    public MousePositioner(final Point offset) {
        this.offset = offset;
    }
    
    public Point getPosition(final IInterface inter, final Dimension popup, final Rectangle component, final Rectangle panel) {
        final Point pos = inter.getMouse();
        pos.translate(this.offset.x, this.offset.y);
        return pos;
    }
}
