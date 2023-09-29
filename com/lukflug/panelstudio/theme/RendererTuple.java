//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

public class RendererTuple<T>
{
    public final IPanelRenderer<T> panelRenderer;
    public final IScrollBarRenderer<T> scrollRenderer;
    public final IEmptySpaceRenderer<T> cornerRenderer;
    public final IEmptySpaceRenderer<T> emptyRenderer;
    
    public RendererTuple(final Class<T> type, final ThemeTuple theme) {
        this.panelRenderer = theme.getPanelRenderer(type);
        this.scrollRenderer = theme.getScrollBarRenderer(type);
        this.cornerRenderer = theme.getEmptySpaceRenderer(type, false);
        this.emptyRenderer = theme.getEmptySpaceRenderer(type, true);
    }
}
