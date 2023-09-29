//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

@FunctionalInterface
public interface IThemeMultiplexer extends ITheme
{
    default void loadAssets(final IInterface inter) {
        this.getTheme().loadAssets(inter);
    }
    
    default IDescriptionRenderer getDescriptionRenderer() {
        final IDescriptionRendererProxy proxy = () -> this.getTheme().getDescriptionRenderer();
        return (IDescriptionRenderer)proxy;
    }
    
    default IContainerRenderer getContainerRenderer(final int logicalLevel, final int graphicalLevel, final boolean horizontal) {
        final IContainerRendererProxy proxy = () -> this.getTheme().getContainerRenderer(logicalLevel, graphicalLevel, horizontal);
        return (IContainerRenderer)proxy;
    }
    
    default <T> IPanelRenderer<T> getPanelRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        final IPanelRendererProxy<T> proxy = (IPanelRendererProxy<T>)(() -> this.getTheme().getPanelRenderer(type, logicalLevel, graphicalLevel));
        return (IPanelRenderer<T>)proxy;
    }
    
    default <T> IScrollBarRenderer<T> getScrollBarRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        final IScrollBarRendererProxy<T> proxy = (IScrollBarRendererProxy<T>)(() -> this.getTheme().getScrollBarRenderer(type, logicalLevel, graphicalLevel));
        return (IScrollBarRenderer<T>)proxy;
    }
    
    default <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        final IEmptySpaceRendererProxy<T> proxy = (IEmptySpaceRendererProxy<T>)(() -> this.getTheme().getEmptySpaceRenderer(type, logicalLevel, graphicalLevel, container));
        return (IEmptySpaceRenderer<T>)proxy;
    }
    
    default <T> IButtonRenderer<T> getButtonRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        final IButtonRendererProxy<T> proxy = (IButtonRendererProxy<T>)(() -> this.getTheme().getButtonRenderer(type, logicalLevel, graphicalLevel, container));
        return (IButtonRenderer<T>)proxy;
    }
    
    default IButtonRenderer<Void> getSmallButtonRenderer(final int symbol, final int logicalLevel, final int graphicalLevel, final boolean container) {
        final IButtonRendererProxy<Void> proxy = (IButtonRendererProxy<Void>)(() -> this.getTheme().getSmallButtonRenderer(symbol, logicalLevel, graphicalLevel, container));
        return (IButtonRenderer<Void>)proxy;
    }
    
    default IButtonRenderer<String> getKeybindRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        final IButtonRendererProxy<String> proxy = (IButtonRendererProxy<String>)(() -> this.getTheme().getKeybindRenderer(logicalLevel, graphicalLevel, container));
        return (IButtonRenderer<String>)proxy;
    }
    
    default ISliderRenderer getSliderRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        final ISliderRendererProxy proxy = () -> this.getTheme().getSliderRenderer(logicalLevel, graphicalLevel, container);
        return (ISliderRenderer)proxy;
    }
    
    default IRadioRenderer getRadioRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        final IRadioRendererProxy proxy = () -> this.getTheme().getRadioRenderer(logicalLevel, graphicalLevel, container);
        return (IRadioRenderer)proxy;
    }
    
    default IResizeBorderRenderer getResizeRenderer() {
        final IResizeBorderRendererProxy proxy = () -> this.getTheme().getResizeRenderer();
        return (IResizeBorderRenderer)proxy;
    }
    
    default ITextFieldRenderer getTextRenderer(final boolean embed, final int logicalLevel, final int graphicalLevel, final boolean container) {
        final ITextFieldRendererProxy proxy = () -> this.getTheme().getTextRenderer(embed, logicalLevel, graphicalLevel, container);
        return (ITextFieldRenderer)proxy;
    }
    
    default ISwitchRenderer<Boolean> getToggleSwitchRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        final ISwitchRendererProxy<Boolean> proxy = (ISwitchRendererProxy<Boolean>)(() -> this.getTheme().getToggleSwitchRenderer(logicalLevel, graphicalLevel, container));
        return (ISwitchRenderer<Boolean>)proxy;
    }
    
    default ISwitchRenderer<String> getCycleSwitchRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        final ISwitchRendererProxy<String> proxy = (ISwitchRendererProxy<String>)(() -> this.getTheme().getCycleSwitchRenderer(logicalLevel, graphicalLevel, container));
        return (ISwitchRenderer<String>)proxy;
    }
    
    default IColorPickerRenderer getColorPickerRenderer() {
        final IColorPickerRendererProxy proxy = () -> this.getTheme().getColorPickerRenderer();
        return (IColorPickerRenderer)proxy;
    }
    
    default int getBaseHeight() {
        return this.getTheme().getBaseHeight();
    }
    
    default Color getMainColor(final boolean focus, final boolean active) {
        return this.getTheme().getMainColor(focus, active);
    }
    
    default Color getBackgroundColor(final boolean focus) {
        return this.getTheme().getBackgroundColor(focus);
    }
    
    default Color getFontColor(final boolean focus) {
        return this.getTheme().getFontColor(focus);
    }
    
    default void overrideMainColor(final Color color) {
        this.getTheme().overrideMainColor(color);
    }
    
    default void restoreMainColor() {
        this.getTheme().restoreMainColor();
    }
    
    ITheme getTheme();
}
