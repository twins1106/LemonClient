//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;

@FunctionalInterface
public interface IContainerRendererProxy extends IContainerRenderer
{
    default void renderBackground(final Context context, final boolean focus) {
        this.getRenderer().renderBackground(context, focus);
    }
    
    default int getBorder() {
        return this.getRenderer().getBorder();
    }
    
    default int getLeft() {
        return this.getRenderer().getLeft();
    }
    
    default int getRight() {
        return this.getRenderer().getRight();
    }
    
    default int getTop() {
        return this.getRenderer().getTop();
    }
    
    default int getBottom() {
        return this.getRenderer().getBottom();
    }
    
    IContainerRenderer getRenderer();
}
