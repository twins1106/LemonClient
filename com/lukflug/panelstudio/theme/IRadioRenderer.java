//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.setting.*;
import java.awt.*;

public interface IRadioRenderer
{
    void renderItem(final Context p0, final ILabeled[] p1, final boolean p2, final int p3, final double p4, final boolean p5);
    
    int getDefaultHeight(final ILabeled[] p0, final boolean p1);
    
    default Rectangle getItemRect(final Context context, final ILabeled[] items, final int index, final boolean horizontal) {
        final Rectangle rect = context.getRect();
        if (horizontal) {
            final int start = (int)Math.round(rect.width / (double)items.length * index);
            final int end = (int)Math.round(rect.width / (double)items.length * (index + 1));
            return new Rectangle(rect.x + start, rect.y, end - start, rect.height);
        }
        final int start = (int)Math.round(rect.height / (double)items.length * index);
        final int end = (int)Math.round(rect.height / (double)items.length * (index + 1));
        return new Rectangle(rect.x, rect.y + start, rect.width, end - start);
    }
}
