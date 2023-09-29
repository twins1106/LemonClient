//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.popup;

import java.util.function.*;
import com.lukflug.panelstudio.base.*;
import java.awt.*;

public class CenteredPositioner implements IPopupPositioner
{
    protected Supplier<Rectangle> rect;
    
    public CenteredPositioner(final Supplier<Rectangle> rect) {
        this.rect = rect;
    }
    
    @Override
    public Point getPosition(final IInterface inter, final Dimension popup, final Rectangle component, final Rectangle panel) {
        final Rectangle rect = this.rect.get();
        return new Point(rect.x + rect.width / 2 - popup.width / 2, rect.y + rect.height / 2 - popup.height / 2);
    }
}
