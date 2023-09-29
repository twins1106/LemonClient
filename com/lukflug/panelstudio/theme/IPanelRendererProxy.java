//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;

@FunctionalInterface
public interface IPanelRendererProxy<T> extends IPanelRenderer<T>
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
    
    default void renderPanelOverlay(final Context context, final boolean focus, final T state, final boolean open) {
        this.getRenderer().renderPanelOverlay(context, focus, (Object)state, open);
    }
    
    default void renderTitleOverlay(final Context context, final boolean focus, final T state, final boolean open) {
        this.getRenderer().renderTitleOverlay(context, focus, (Object)state, open);
    }
    
    IPanelRenderer<T> getRenderer();
}
