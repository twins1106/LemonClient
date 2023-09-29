//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

public final class ThemeTuple
{
    public final ITheme theme;
    public final int logicalLevel;
    public final int graphicalLevel;
    
    public ThemeTuple(final ITheme theme, final int logicalLevel, final int graphicalLevel) {
        this.theme = theme;
        this.logicalLevel = logicalLevel;
        this.graphicalLevel = graphicalLevel;
    }
    
    public ThemeTuple(final ThemeTuple previous, final int logicalDiff, final int graphicalDiff) {
        this.theme = previous.theme;
        this.logicalLevel = previous.logicalLevel + logicalDiff;
        this.graphicalLevel = previous.graphicalLevel + graphicalDiff;
    }
    
    public IContainerRenderer getContainerRenderer(final boolean horizontal) {
        return this.theme.getContainerRenderer(this.logicalLevel, this.graphicalLevel, horizontal);
    }
    
    public <T> IPanelRenderer<T> getPanelRenderer(final Class<T> type) {
        return (IPanelRenderer<T>)this.theme.getPanelRenderer((Class)type, this.logicalLevel, this.graphicalLevel);
    }
    
    public <T> IScrollBarRenderer<T> getScrollBarRenderer(final Class<T> type) {
        return (IScrollBarRenderer<T>)this.theme.getScrollBarRenderer((Class)type, this.logicalLevel, this.graphicalLevel);
    }
    
    public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(final Class<T> type, final boolean container) {
        return (IEmptySpaceRenderer<T>)this.theme.getEmptySpaceRenderer((Class)type, this.logicalLevel, this.graphicalLevel, container);
    }
    
    public <T> IButtonRenderer<T> getButtonRenderer(final Class<T> type, final boolean container) {
        return (IButtonRenderer<T>)this.theme.getButtonRenderer((Class)type, this.logicalLevel, this.graphicalLevel, container);
    }
    
    public IButtonRenderer<Void> getSmallButtonRenderer(final int symbol, final boolean container) {
        return (IButtonRenderer<Void>)this.theme.getSmallButtonRenderer(symbol, this.logicalLevel, this.graphicalLevel, container);
    }
    
    public IButtonRenderer<String> getKeybindRenderer(final boolean container) {
        return (IButtonRenderer<String>)this.theme.getKeybindRenderer(this.logicalLevel, this.graphicalLevel, container);
    }
    
    public ISliderRenderer getSliderRenderer(final boolean container) {
        return this.theme.getSliderRenderer(this.logicalLevel, this.graphicalLevel, container);
    }
    
    public IRadioRenderer getRadioRenderer(final boolean container) {
        return this.theme.getRadioRenderer(this.logicalLevel, this.graphicalLevel, container);
    }
    
    public ITextFieldRenderer getTextRenderer(final boolean embed, final boolean container) {
        return this.theme.getTextRenderer(embed, this.logicalLevel, this.graphicalLevel, container);
    }
    
    public ISwitchRenderer<Boolean> getToggleSwitchRenderer(final boolean container) {
        return (ISwitchRenderer<Boolean>)this.theme.getToggleSwitchRenderer(this.logicalLevel, this.graphicalLevel, container);
    }
    
    public ISwitchRenderer<String> getCycleSwitchRenderer(final boolean container) {
        return (ISwitchRenderer<String>)this.theme.getCycleSwitchRenderer(this.logicalLevel, this.graphicalLevel, container);
    }
}
