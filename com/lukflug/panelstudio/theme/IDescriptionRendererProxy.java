//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

@FunctionalInterface
public interface IDescriptionRendererProxy extends IDescriptionRenderer
{
    default void renderDescription(final IInterface inter, final Point pos, final String text) {
        this.getRenderer().renderDescription(inter, pos, text);
    }
    
    IDescriptionRenderer getRenderer();
}
