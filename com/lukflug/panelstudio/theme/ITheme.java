//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

public interface ITheme
{
    public static final int NONE = 0;
    public static final int CLOSE = 1;
    public static final int MINIMIZE = 2;
    public static final int ADD = 3;
    public static final int LEFT = 4;
    public static final int RIGHT = 5;
    public static final int UP = 6;
    public static final int DOWN = 7;
    
    void loadAssets(final IInterface p0);
    
    IDescriptionRenderer getDescriptionRenderer();
    
    IContainerRenderer getContainerRenderer(final int p0, final int p1, final boolean p2);
    
     <T> IPanelRenderer<T> getPanelRenderer(final Class<T> p0, final int p1, final int p2);
    
     <T> IScrollBarRenderer<T> getScrollBarRenderer(final Class<T> p0, final int p1, final int p2);
    
     <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(final Class<T> p0, final int p1, final int p2, final boolean p3);
    
     <T> IButtonRenderer<T> getButtonRenderer(final Class<T> p0, final int p1, final int p2, final boolean p3);
    
    IButtonRenderer<Void> getSmallButtonRenderer(final int p0, final int p1, final int p2, final boolean p3);
    
    IButtonRenderer<String> getKeybindRenderer(final int p0, final int p1, final boolean p2);
    
    ISliderRenderer getSliderRenderer(final int p0, final int p1, final boolean p2);
    
    IRadioRenderer getRadioRenderer(final int p0, final int p1, final boolean p2);
    
    IResizeBorderRenderer getResizeRenderer();
    
    ITextFieldRenderer getTextRenderer(final boolean p0, final int p1, final int p2, final boolean p3);
    
    ISwitchRenderer<Boolean> getToggleSwitchRenderer(final int p0, final int p1, final boolean p2);
    
    ISwitchRenderer<String> getCycleSwitchRenderer(final int p0, final int p1, final boolean p2);
    
    IColorPickerRenderer getColorPickerRenderer();
    
    int getBaseHeight();
    
    Color getMainColor(final boolean p0, final boolean p1);
    
    Color getBackgroundColor(final boolean p0);
    
    Color getFontColor(final boolean p0);
    
    void overrideMainColor(final Color p0);
    
    void restoreMainColor();
    
    default Color combineColors(final Color main, final Color opacity) {
        return new Color(main.getRed(), main.getGreen(), main.getBlue(), opacity.getAlpha());
    }
    
    default void drawRect(final IInterface inter, final Rectangle rect, final Color color) {
        inter.fillRect(new Rectangle(rect.x, rect.y, 1, rect.height), color, color, color, color);
        inter.fillRect(new Rectangle(rect.x + 1, rect.y, rect.width - 2, 1), color, color, color, color);
        inter.fillRect(new Rectangle(rect.x + rect.width - 1, rect.y, 1, rect.height), color, color, color, color);
        inter.fillRect(new Rectangle(rect.x + 1, rect.y + rect.height - 1, rect.width - 2, 1), color, color, color, color);
    }
}
