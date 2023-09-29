//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;

@FunctionalInterface
public interface IResizeBorderRendererProxy extends IResizeBorderRenderer
{
    default void drawBorder(final Context context, final boolean focus) {
        this.getRenderer().drawBorder(context, focus);
    }
    
    default int getBorder() {
        return this.getRenderer().getBorder();
    }
    
    IResizeBorderRenderer getRenderer();
}
