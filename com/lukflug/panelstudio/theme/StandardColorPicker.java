//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

public abstract class StandardColorPicker implements IColorPickerRenderer
{
    public void renderPicker(final Context context, final boolean focus, final Color color) {
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        final Color colorA = Color.getHSBColor(hsb[0], 0.0f, 1.0f);
        final Color colorB = Color.getHSBColor(hsb[0], 1.0f, 1.0f);
        context.getInterface().fillRect(context.getRect(), colorA, colorB, colorB, colorA);
        final Color colorC = new Color(0, 0, 0, 0);
        final Color colorD = new Color(0, 0, 0);
        context.getInterface().fillRect(context.getRect(), colorC, colorC, colorD, colorD);
        final Point p = new Point(Math.round(context.getPos().x + hsb[1] * (context.getSize().width - 1)), Math.round(context.getPos().y + (1.0f - hsb[2]) * (context.getSize().height - 1)));
        this.renderCursor(context, p, color);
    }
    
    public Color transformPoint(final Context context, final Color color, final Point point) {
        final float hue = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null)[0];
        float saturation = (point.x - context.getPos().x) / (float)(context.getSize().width - 1);
        float brightness = 1.0f + (context.getPos().y - point.y) / (float)(context.getSize().height - 1);
        if (saturation > 1.0f) {
            saturation = 1.0f;
        }
        else if (saturation < 0.0f) {
            saturation = 0.0f;
        }
        if (brightness > 1.0f) {
            brightness = 1.0f;
        }
        else if (brightness < 0.0f) {
            brightness = 0.0f;
        }
        final Color value = Color.getHSBColor(hue, saturation, brightness);
        return ITheme.combineColors(value, color);
    }
    
    public int getDefaultHeight(final int width) {
        return Math.min(width, 8 * this.getBaseHeight());
    }
    
    protected void renderCursor(final Context context, final Point p, final Color color) {
        final Color fontColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
        context.getInterface().fillRect(new Rectangle(p.x, p.y - this.getPadding(), 1, 2 * this.getPadding() + 1), fontColor, fontColor, fontColor, fontColor);
        context.getInterface().fillRect(new Rectangle(p.x - this.getPadding(), p.y, 2 * this.getPadding() + 1, 1), fontColor, fontColor, fontColor, fontColor);
    }
    
    public abstract int getPadding();
    
    public abstract int getBaseHeight();
}
