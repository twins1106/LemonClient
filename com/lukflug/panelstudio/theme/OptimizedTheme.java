//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import java.util.*;
import com.lukflug.panelstudio.base.*;
import java.awt.*;
import java.util.function.*;

public final class OptimizedTheme implements ITheme
{
    private final ITheme theme;
    private IDescriptionRenderer descriptionRenderer;
    private final Map<ParameterTuple<Void, Boolean>, IContainerRenderer> containerRenderer;
    private final Map<ParameterTuple<Class<?>, Void>, IPanelRenderer<?>> panelRenderer;
    private final Map<ParameterTuple<Class<?>, Void>, IScrollBarRenderer<?>> scrollBarRenderer;
    private final Map<ParameterTuple<Class<?>, Boolean>, IEmptySpaceRenderer<?>> emptySpaceRenderer;
    private final Map<ParameterTuple<Class<?>, Boolean>, IButtonRenderer<?>> buttonRenderer;
    private final Map<ParameterTuple<Integer, Boolean>, IButtonRenderer<Void>> smallButtonRenderer;
    private final Map<ParameterTuple<Void, Boolean>, IButtonRenderer<String>> keybindRenderer;
    private final Map<ParameterTuple<Void, Boolean>, ISliderRenderer> sliderRenderer;
    private final Map<ParameterTuple<Void, Boolean>, IRadioRenderer> radioRenderer;
    private IResizeBorderRenderer resizeRenderer;
    private final Map<ParameterTuple<Boolean, Boolean>, ITextFieldRenderer> textRenderer;
    private final Map<ParameterTuple<Void, Boolean>, ISwitchRenderer<Boolean>> toggleSwitchRenderer;
    private final Map<ParameterTuple<Void, Boolean>, ISwitchRenderer<String>> cycleSwitchRenderer;
    private IColorPickerRenderer colorPickerRenderer;
    
    public OptimizedTheme(final ITheme theme) {
        this.descriptionRenderer = null;
        this.containerRenderer = new HashMap<ParameterTuple<Void, Boolean>, IContainerRenderer>();
        this.panelRenderer = new HashMap<ParameterTuple<Class<?>, Void>, IPanelRenderer<?>>();
        this.scrollBarRenderer = new HashMap<ParameterTuple<Class<?>, Void>, IScrollBarRenderer<?>>();
        this.emptySpaceRenderer = new HashMap<ParameterTuple<Class<?>, Boolean>, IEmptySpaceRenderer<?>>();
        this.buttonRenderer = new HashMap<ParameterTuple<Class<?>, Boolean>, IButtonRenderer<?>>();
        this.smallButtonRenderer = new HashMap<ParameterTuple<Integer, Boolean>, IButtonRenderer<Void>>();
        this.keybindRenderer = new HashMap<ParameterTuple<Void, Boolean>, IButtonRenderer<String>>();
        this.sliderRenderer = new HashMap<ParameterTuple<Void, Boolean>, ISliderRenderer>();
        this.radioRenderer = new HashMap<ParameterTuple<Void, Boolean>, IRadioRenderer>();
        this.resizeRenderer = null;
        this.textRenderer = new HashMap<ParameterTuple<Boolean, Boolean>, ITextFieldRenderer>();
        this.toggleSwitchRenderer = new HashMap<ParameterTuple<Void, Boolean>, ISwitchRenderer<Boolean>>();
        this.cycleSwitchRenderer = new HashMap<ParameterTuple<Void, Boolean>, ISwitchRenderer<String>>();
        this.colorPickerRenderer = null;
        this.theme = theme;
    }
    
    public void loadAssets(final IInterface inter) {
        this.theme.loadAssets(inter);
    }
    
    public IDescriptionRenderer getDescriptionRenderer() {
        if (this.descriptionRenderer == null) {
            this.descriptionRenderer = this.theme.getDescriptionRenderer();
        }
        return this.descriptionRenderer;
    }
    
    public IContainerRenderer getContainerRenderer(final int logicalLevel, final int graphicalLevel, final boolean horizontal) {
        return getRenderer(this.containerRenderer, () -> this.theme.getContainerRenderer(logicalLevel, graphicalLevel, horizontal), null, logicalLevel, graphicalLevel, horizontal);
    }
    
    public <T> IPanelRenderer<T> getPanelRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        return (IPanelRenderer<T>)getRenderer(this.panelRenderer, () -> this.theme.getPanelRenderer((Class)type, logicalLevel, graphicalLevel), (Class<?>)type, logicalLevel, graphicalLevel, (Void)null);
    }
    
    public <T> IScrollBarRenderer<T> getScrollBarRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        return (IScrollBarRenderer<T>)getRenderer(this.scrollBarRenderer, () -> this.theme.getScrollBarRenderer((Class)type, logicalLevel, graphicalLevel), (Class<?>)type, logicalLevel, graphicalLevel, (Void)null);
    }
    
    public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IEmptySpaceRenderer<T>)getRenderer(this.emptySpaceRenderer, () -> this.theme.getEmptySpaceRenderer((Class)type, logicalLevel, graphicalLevel, container), (Class<?>)type, logicalLevel, graphicalLevel, Boolean.valueOf(container));
    }
    
    public <T> IButtonRenderer<T> getButtonRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IButtonRenderer<T>)getRenderer(this.buttonRenderer, () -> this.theme.getButtonRenderer((Class)type, logicalLevel, graphicalLevel, container), (Class<?>)type, logicalLevel, graphicalLevel, Boolean.valueOf(container));
    }
    
    public IButtonRenderer<Void> getSmallButtonRenderer(final int symbol, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return getRenderer(this.smallButtonRenderer, () -> this.theme.getSmallButtonRenderer(symbol, logicalLevel, graphicalLevel, container), Integer.valueOf(symbol), logicalLevel, graphicalLevel, Boolean.valueOf(container));
    }
    
    public IButtonRenderer<String> getKeybindRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return getRenderer(this.keybindRenderer, () -> this.theme.getKeybindRenderer(logicalLevel, graphicalLevel, container), (Void)null, logicalLevel, graphicalLevel, Boolean.valueOf(container));
    }
    
    public ISliderRenderer getSliderRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return getRenderer(this.sliderRenderer, () -> this.theme.getSliderRenderer(logicalLevel, graphicalLevel, container), null, logicalLevel, graphicalLevel, container);
    }
    
    public IRadioRenderer getRadioRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return getRenderer(this.radioRenderer, () -> this.theme.getRadioRenderer(logicalLevel, graphicalLevel, container), null, logicalLevel, graphicalLevel, container);
    }
    
    public IResizeBorderRenderer getResizeRenderer() {
        if (this.resizeRenderer == null) {
            this.resizeRenderer = this.theme.getResizeRenderer();
        }
        return this.resizeRenderer;
    }
    
    public ITextFieldRenderer getTextRenderer(final boolean embed, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return getRenderer(this.textRenderer, () -> this.theme.getTextRenderer(embed, logicalLevel, graphicalLevel, container), embed, logicalLevel, graphicalLevel, container);
    }
    
    public ISwitchRenderer<Boolean> getToggleSwitchRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return getRenderer(this.toggleSwitchRenderer, () -> this.theme.getToggleSwitchRenderer(logicalLevel, graphicalLevel, container), (Void)null, logicalLevel, graphicalLevel, Boolean.valueOf(container));
    }
    
    public ISwitchRenderer<String> getCycleSwitchRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return getRenderer(this.cycleSwitchRenderer, () -> this.theme.getCycleSwitchRenderer(logicalLevel, graphicalLevel, container), (Void)null, logicalLevel, graphicalLevel, Boolean.valueOf(container));
    }
    
    public IColorPickerRenderer getColorPickerRenderer() {
        if (this.colorPickerRenderer == null) {
            this.colorPickerRenderer = this.theme.getColorPickerRenderer();
        }
        return this.colorPickerRenderer;
    }
    
    public int getBaseHeight() {
        return this.theme.getBaseHeight();
    }
    
    public Color getMainColor(final boolean focus, final boolean active) {
        return this.theme.getMainColor(focus, active);
    }
    
    public Color getBackgroundColor(final boolean focus) {
        return this.theme.getBackgroundColor(focus);
    }
    
    public Color getFontColor(final boolean focus) {
        return this.theme.getFontColor(focus);
    }
    
    public void overrideMainColor(final Color color) {
        this.theme.overrideMainColor(color);
    }
    
    public void restoreMainColor() {
        this.theme.restoreMainColor();
    }
    
    private static <S, T, U> U getRenderer(final Map<ParameterTuple<S, T>, U> table, final Supplier<U> init, final S type, final int logicalLevel, final int graphicalLevel, final T container) {
        final ParameterTuple<S, T> key = new ParameterTuple<S, T>(type, logicalLevel, graphicalLevel, container);
        U value = table.getOrDefault(key, null);
        if (value == null) {
            table.put(key, value = init.get());
        }
        return value;
    }
    
    private static class ParameterTuple<S, T>
    {
        private final S type;
        private final int logicalLevel;
        private final int graphicalLevel;
        private final T container;
        
        public ParameterTuple(final S type, final int logicalLevel, final int graphicalLevel, final T container) {
            this.type = type;
            this.logicalLevel = logicalLevel;
            this.graphicalLevel = graphicalLevel;
            this.container = container;
        }
        
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof ParameterTuple && this.toString().equals(o.toString());
        }
        
        @Override
        public String toString() {
            return "(" + this.type + "," + this.logicalLevel + "," + this.graphicalLevel + "," + this.container + ")";
        }
    }
}
