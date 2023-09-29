//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.popup;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

public class FixedPositioner implements IPopupPositioner
{
    protected Point pos;
    
    public FixedPositioner(final Point pos) {
        this.pos = pos;
    }
    
    @Override
    public Point getPosition(final IInterface inter, final Dimension popup, final Rectangle component, final Rectangle panel) {
        return new Point(this.pos);
    }
}
