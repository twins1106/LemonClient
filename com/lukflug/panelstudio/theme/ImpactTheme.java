//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;
import com.lukflug.panelstudio.setting.*;

public class ImpactTheme extends ThemeBase
{
    int height;
    int padding;
    
    public ImpactTheme(final IColorScheme scheme, final int height, final int padding) {
        super(scheme);
        this.height = height;
        this.padding = padding;
        scheme.createSetting((ITheme)this, "Title Color", "The color for panel titles.", true, true, new Color(16, 16, 16, 198), false);
        scheme.createSetting((ITheme)this, "Background Color", "The panel background color.", true, true, new Color(30, 30, 30, 192), false);
        scheme.createSetting((ITheme)this, "Panel Outline Color", "The main color for panel outlines.", false, true, new Color(20, 20, 20), false);
        scheme.createSetting((ITheme)this, "Component Outline Color", "The main color for component outlines.", true, true, new Color(0, 0, 0, 92), false);
        scheme.createSetting((ITheme)this, "Active Font Color", "The color for active text.", false, true, new Color(255, 255, 255), false);
        scheme.createSetting((ITheme)this, "Hovered Font Color", "The color for hovered text.", false, true, new Color(192, 192, 192), false);
        scheme.createSetting((ITheme)this, "Inactive Font Color", "The color for inactive text.", false, true, new Color(128, 128, 128), false);
        scheme.createSetting((ITheme)this, "Enabled Color", "The color for enabled modules.", false, true, new Color(91, 201, 79), false);
        scheme.createSetting((ITheme)this, "Disabled Color", "The  color for disabled modules.", false, true, new Color(194, 48, 48), false);
        scheme.createSetting((ITheme)this, "Highlight Color", "The color for highlighted text.", false, true, new Color(0, 0, 255), false);
        scheme.createSetting((ITheme)this, "Tooltip Color", "The color for description tooltips.", false, true, new Color(0, 0, 0, 128), false);
    }
    
    protected void renderBackground(final Context context, final boolean focus) {
        final Color color = this.getBackgroundColor(focus);
        context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y, context.getSize().width, context.getSize().height), color, color, color, color);
    }
    
    protected void renderOverlay(final Context context) {
        if (context.isHovered()) {
            final Color color = new Color(0, 0, 0, 24);
            context.getInterface().fillRect(context.getRect(), color, color, color, color);
        }
    }
    
    protected void renderSmallButton(final Context context, final String title, final int symbol, final boolean focus) {
        final Point[] points = new Point[3];
        final int padding = (context.getSize().height <= 12) ? ((context.getSize().height <= 8) ? 2 : 4) : 6;
        final Rectangle rect = new Rectangle(context.getPos().x + padding / 2, context.getPos().y + padding / 2, context.getSize().height - 2 * (padding / 2), context.getSize().height - 2 * (padding / 2));
        if (title == null) {
            final Rectangle rectangle = rect;
            rectangle.x += context.getSize().width / 2 - context.getSize().height / 2;
        }
        Color color = this.getFontColor(focus);
        if (context.isHovered()) {
            color = this.scheme.getColor("Active Font Color");
        }
        switch (symbol) {
            case 1: {
                context.getInterface().drawLine(new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), color, color);
                context.getInterface().drawLine(new Point(rect.x, rect.y + rect.height), new Point(rect.x + rect.width, rect.y), color, color);
                break;
            }
            case 2: {
                context.getInterface().fillRect(new Rectangle(rect.x, rect.y + rect.height - 2, rect.width, 2), color, color, color, color);
                break;
            }
            case 3: {
                if (rect.width % 2 == 1) {
                    final Rectangle rectangle2 = rect;
                    --rectangle2.width;
                }
                if (rect.height % 2 == 1) {
                    final Rectangle rectangle3 = rect;
                    --rectangle3.height;
                }
                context.getInterface().fillRect(new Rectangle(rect.x + rect.width / 2 - 1, rect.y, 2, rect.height), color, color, color, color);
                context.getInterface().fillRect(new Rectangle(rect.x, rect.y + rect.height / 2 - 1, rect.width, 2), color, color, color, color);
                break;
            }
            case 4: {
                if (rect.height % 2 == 1) {
                    final Rectangle rectangle4 = rect;
                    --rectangle4.height;
                }
                points[2] = new Point(rect.x + rect.width, rect.y);
                points[0] = new Point(rect.x + rect.width, rect.y + rect.height);
                points[1] = new Point(rect.x, rect.y + rect.height / 2);
                break;
            }
            case 5: {
                if (rect.height % 2 == 1) {
                    final Rectangle rectangle5 = rect;
                    --rectangle5.height;
                }
                points[0] = new Point(rect.x, rect.y);
                points[2] = new Point(rect.x, rect.y + rect.height);
                points[1] = new Point(rect.x + rect.width, rect.y + rect.height / 2);
                break;
            }
            case 6: {
                if (rect.width % 2 == 1) {
                    final Rectangle rectangle6 = rect;
                    --rectangle6.width;
                }
                points[0] = new Point(rect.x, rect.y + rect.height);
                points[2] = new Point(rect.x + rect.width, rect.y + rect.height);
                points[1] = new Point(rect.x + rect.width / 2, rect.y);
                break;
            }
            case 7: {
                if (rect.width % 2 == 1) {
                    final Rectangle rectangle7 = rect;
                    --rectangle7.width;
                }
                points[2] = new Point(rect.x, rect.y);
                points[0] = new Point(rect.x + rect.width, rect.y);
                points[1] = new Point(rect.x + rect.width / 2, rect.y + rect.height);
                break;
            }
        }
        if (symbol >= 4 && symbol <= 7) {
            context.getInterface().drawLine(points[0], points[1], color, color);
            context.getInterface().drawLine(points[1], points[2], color, color);
        }
        if (title != null) {
            context.getInterface().drawString(new Point(context.getPos().x + ((symbol == 0) ? padding : context.getSize().height), context.getPos().y + padding), this.height, title, this.getFontColor(focus));
        }
    }
    
    @Override
    public IDescriptionRenderer getDescriptionRenderer() {
        return (IDescriptionRenderer)new IDescriptionRenderer() {
            public void renderDescription(final IInterface inter, final Point pos, final String text) {
                final Rectangle rect = new Rectangle(pos, new Dimension(inter.getFontWidth(ImpactTheme.this.height, text) + 2 * ImpactTheme.this.padding - 2, ImpactTheme.this.height + 2 * ImpactTheme.this.padding - 2));
                final Color color = ImpactTheme.this.scheme.getColor("Tooltip Color");
                inter.fillRect(rect, color, color, color, color);
                inter.drawString(new Point(pos.x + ImpactTheme.this.padding - 1, pos.y + ImpactTheme.this.padding - 1), ImpactTheme.this.height, text, ImpactTheme.this.getFontColor(true));
            }
        };
    }
    
    @Override
    public IContainerRenderer getContainerRenderer(final int logicalLevel, final int graphicalLevel, final boolean horizontal) {
        return (IContainerRenderer)new IContainerRenderer() {
            public void renderBackground(final Context context, final boolean focus) {
                if (graphicalLevel == 0) {
                    ImpactTheme.this.renderBackground(context, focus);
                }
            }
            
            public int getBorder() {
                return 2;
            }
            
            public int getLeft() {
                return 2;
            }
            
            public int getRight() {
                return 2;
            }
            
            public int getTop() {
                return 2;
            }
            
            public int getBottom() {
                return 2;
            }
        };
    }
    
    @Override
    public <T> IPanelRenderer<T> getPanelRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        return new IPanelRenderer<T>() {
            public int getBorder() {
                return (graphicalLevel <= 0) ? 1 : 0;
            }
            
            public int getLeft() {
                return 1;
            }
            
            public int getRight() {
                return 1;
            }
            
            public int getTop() {
                return 1;
            }
            
            public int getBottom() {
                return 1;
            }
            
            @Override
            public void renderPanelOverlay(final Context context, final boolean focus, final T state, final boolean open) {
                final Color color = (graphicalLevel <= 0) ? ImpactTheme.this.scheme.getColor("Panel Outline Color") : ImpactTheme.this.scheme.getColor("Component Outline Color");
                ITheme.drawRect(context.getInterface(), context.getRect(), color);
            }
            
            @Override
            public void renderTitleOverlay(final Context context, final boolean focus, final T state, final boolean open) {
                if (graphicalLevel <= 0) {
                    final Color colorA = ImpactTheme.this.scheme.getColor("Panel Outline Color");
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y + context.getSize().height, context.getSize().width, 1), colorA, colorA, colorA, colorA);
                }
                else {
                    ImpactTheme.this.renderOverlay(context);
                    final Context subContext = new Context(context, ImpactTheme.this.height, new Point(ImpactTheme.this.padding / 2, ImpactTheme.this.padding / 2), true, true);
                    subContext.setHeight(context.getSize().height - ImpactTheme.this.padding);
                    ImpactTheme.this.renderSmallButton(subContext, null, open ? 7 : 5, focus);
                }
            }
        };
    }
    
    @Override
    public <T> IScrollBarRenderer<T> getScrollBarRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        return new IScrollBarRenderer<T>() {};
    }
    
    @Override
    public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IEmptySpaceRenderer<T>)new IEmptySpaceRenderer<T>() {
            public void renderSpace(final Context context, final boolean focus, final T state) {
                if (graphicalLevel == 0) {
                    ImpactTheme.this.renderBackground(context, focus);
                }
            }
        };
    }
    
    @Override
    public <T> IButtonRenderer<T> getButtonRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IButtonRenderer<T>)new IButtonRenderer<T>() {
            public void renderButton(final Context context, final String title, final boolean focus, final T state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                if (graphicalLevel <= 0) {
                    if (container) {
                        final Color color = ImpactTheme.this.scheme.getColor("Title Color");
                        context.getInterface().fillRect(context.getRect(), color, color, color, color);
                    }
                    else {
                        ImpactTheme.this.renderBackground(context, effFocus);
                    }
                }
                if (!container) {
                    final Color color = (graphicalLevel <= 0) ? ImpactTheme.this.scheme.getColor("Panel Outline Color") : ImpactTheme.this.scheme.getColor("Component Outline Color");
                    ITheme.drawRect(context.getInterface(), context.getRect(), color);
                    ImpactTheme.this.renderOverlay(context);
                }
                int colorLevel = 1;
                if (type == Boolean.class) {
                    colorLevel = (state ? 2 : 0);
                }
                else if (type == String.class) {
                    colorLevel = 2;
                }
                if (container && graphicalLevel <= 0) {
                    colorLevel = 2;
                }
                Color valueColor = ImpactTheme.this.getFontColor(effFocus);
                if (context.isHovered() && context.getInterface().getMouse().x > context.getPos().x + context.getSize().height - ImpactTheme.this.padding) {
                    if (colorLevel < 2) {
                        ++colorLevel;
                    }
                    valueColor = ImpactTheme.this.scheme.getColor("Active Font Color");
                }
                Color fontColor = ImpactTheme.this.getFontColor(effFocus);
                if (colorLevel == 2) {
                    fontColor = ImpactTheme.this.scheme.getColor("Active Font Color");
                }
                else if (colorLevel == 0) {
                    fontColor = ImpactTheme.this.scheme.getColor("Inactive Font Color");
                }
                int xpos = context.getPos().x + context.getSize().height - ImpactTheme.this.padding;
                if (container && graphicalLevel <= 0) {
                    xpos = context.getPos().x + context.getSize().width / 2 - context.getInterface().getFontWidth(ImpactTheme.this.height, title) / 2;
                }
                context.getInterface().drawString(new Point(xpos, context.getPos().y + ImpactTheme.this.padding - (container ? 1 : 0)), ImpactTheme.this.height, title, fontColor);
                if (type == String.class) {
                    context.getInterface().drawString(new Point(context.getPos().x + context.getSize().width - ImpactTheme.this.padding - context.getInterface().getFontWidth(ImpactTheme.this.height, (String)state), context.getPos().y + ImpactTheme.this.padding - (container ? 1 : 0)), ImpactTheme.this.height, (String)state, valueColor);
                }
                else if (type == Boolean.class) {
                    if (context.isHovered() && container) {
                        final int width = context.getInterface().getFontWidth(ImpactTheme.this.height, "OFF") + 2 * ImpactTheme.this.padding;
                        final Rectangle rect = new Rectangle(context.getPos().x + context.getSize().width - width, context.getPos().y + ImpactTheme.this.padding / 2, width, context.getSize().height - 2 * (ImpactTheme.this.padding / 2));
                        final String text = state ? "ON" : "OFF";
                        final Color color2 = ImpactTheme.this.getMainColor(effFocus, (boolean)state);
                        context.getInterface().fillRect(rect, color2, color2, color2, color2);
                        context.getInterface().drawString(new Point(rect.x + (rect.width - context.getInterface().getFontWidth(ImpactTheme.this.height, text)) / 2, context.getPos().y + ImpactTheme.this.padding / 2), ImpactTheme.this.height, text, ImpactTheme.this.scheme.getColor("Active Font Color"));
                    }
                    else if (!container && (boolean)state) {
                        final Point a = new Point(context.getPos().x + context.getSize().width - context.getSize().height + ImpactTheme.this.padding, context.getPos().y + context.getSize().height / 2);
                        final Point b = new Point(context.getPos().x + context.getSize().width - context.getSize().height / 2, context.getPos().y + context.getSize().height - ImpactTheme.this.padding);
                        final Point c = new Point(context.getPos().x + context.getSize().width - ImpactTheme.this.padding, context.getPos().y + ImpactTheme.this.padding);
                        final Color checkColor = ImpactTheme.this.scheme.getColor("Active Font Color");
                        context.getInterface().drawLine(a, b, checkColor, checkColor);
                        context.getInterface().drawLine(b, c, checkColor, checkColor);
                    }
                }
            }
            
            public int getDefaultHeight() {
                return container ? (ImpactTheme.this.getBaseHeight() - 2) : ImpactTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public IButtonRenderer<Void> getSmallButtonRenderer(final int symbol, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IButtonRenderer<Void>)new IButtonRenderer<Void>() {
            public void renderButton(final Context context, final String title, final boolean focus, final Void state) {
                if (graphicalLevel <= 0) {
                    if (container) {
                        final Color color = ImpactTheme.this.scheme.getColor("Title Color");
                        context.getInterface().fillRect(context.getRect(), color, color, color, color);
                    }
                    else {
                        ImpactTheme.this.renderBackground(context, focus);
                    }
                }
                if (!container) {
                    final Color color = (graphicalLevel <= 0) ? ImpactTheme.this.scheme.getColor("Panel Outline Color") : ImpactTheme.this.scheme.getColor("Component Outline Color");
                    ITheme.drawRect(context.getInterface(), context.getRect(), color);
                    ImpactTheme.this.renderOverlay(context);
                }
                ImpactTheme.this.renderOverlay(context);
                if (!container || logicalLevel <= 0) {
                    ImpactTheme.this.renderSmallButton(context, title, symbol, focus);
                }
            }
            
            public int getDefaultHeight() {
                return ImpactTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public IButtonRenderer<String> getKeybindRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IButtonRenderer<String>)new IButtonRenderer<String>() {
            public void renderButton(final Context context, final String title, final boolean focus, final String state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                if (graphicalLevel <= 0) {
                    if (container) {
                        final Color color = ImpactTheme.this.scheme.getColor("Title Color");
                        context.getInterface().fillRect(context.getRect(), color, color, color, color);
                    }
                    else {
                        ImpactTheme.this.renderBackground(context, effFocus);
                    }
                }
                if (!container) {
                    final Color color = (graphicalLevel <= 0) ? ImpactTheme.this.scheme.getColor("Panel Outline Color") : ImpactTheme.this.scheme.getColor("Component Outline Color");
                    ITheme.drawRect(context.getInterface(), context.getRect(), color);
                    ImpactTheme.this.renderOverlay(context);
                }
                final Color valueColor = ImpactTheme.this.scheme.getColor("Active Font Color");
                Color fontColor = ImpactTheme.this.getFontColor(effFocus);
                if (context.isHovered() && context.getInterface().getMouse().x > context.getPos().x + context.getSize().height - ImpactTheme.this.padding) {
                    fontColor = ImpactTheme.this.scheme.getColor("Active Font Color");
                }
                int xpos = context.getPos().x + context.getSize().height - ImpactTheme.this.padding;
                if (container && graphicalLevel <= 0) {
                    xpos = context.getPos().x + context.getSize().width / 2 - context.getInterface().getFontWidth(ImpactTheme.this.height, title) / 2;
                }
                context.getInterface().drawString(new Point(xpos, context.getPos().y + ImpactTheme.this.padding - (container ? 1 : 0)), ImpactTheme.this.height, title, fontColor);
                context.getInterface().drawString(new Point(context.getPos().x + context.getSize().width - ImpactTheme.this.padding - context.getInterface().getFontWidth(ImpactTheme.this.height, effFocus ? "..." : state), context.getPos().y + ImpactTheme.this.padding - (container ? 1 : 0)), ImpactTheme.this.height, effFocus ? "..." : state, valueColor);
            }
            
            public int getDefaultHeight() {
                return container ? (ImpactTheme.this.getBaseHeight() - 2) : ImpactTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public ISliderRenderer getSliderRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new ISliderRenderer() {
            @Override
            public void renderSlider(final Context context, final String title, final String state, final boolean focus, final double value) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                if (graphicalLevel <= 0) {
                    if (container) {
                        final Color color = ImpactTheme.this.scheme.getColor("Title Color");
                        context.getInterface().fillRect(context.getRect(), color, color, color, color);
                    }
                    else {
                        ImpactTheme.this.renderBackground(context, effFocus);
                    }
                }
                if (!container) {
                    final Color color = (graphicalLevel <= 0) ? ImpactTheme.this.scheme.getColor("Panel Outline Color") : ImpactTheme.this.scheme.getColor("Component Outline Color");
                    ITheme.drawRect(context.getInterface(), context.getRect(), color);
                    ImpactTheme.this.renderOverlay(context);
                }
                Rectangle rect = context.getRect();
                if (!container) {
                    rect = new Rectangle(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
                }
                if (ImpactTheme.this.getColor(null) != null && (title.equals("Red") || title.equals("Green") || title.equals("Blue") || title.equals("Hue") || title.equals("Saturation") || title.equals("Brightness"))) {
                    final Color main = ImpactTheme.this.getColor(null);
                    Color colorA = null;
                    Color colorB = null;
                    final float[] hsb = Color.RGBtoHSB(main.getRed(), main.getGreen(), main.getBlue(), null);
                    if (title.equals("Red")) {
                        colorA = new Color(0, main.getGreen(), main.getBlue());
                        colorB = new Color(255, main.getGreen(), main.getBlue());
                    }
                    else if (title.equals("Green")) {
                        colorA = new Color(main.getRed(), 0, main.getBlue());
                        colorB = new Color(main.getRed(), 255, main.getBlue());
                    }
                    else if (title.equals("Blue")) {
                        colorA = new Color(main.getRed(), main.getGreen(), 0);
                        colorB = new Color(main.getRed(), main.getGreen(), 255);
                    }
                    else if (title.equals("Saturation")) {
                        colorA = Color.getHSBColor(hsb[0], 0.0f, hsb[2]);
                        colorB = Color.getHSBColor(hsb[0], 1.0f, hsb[2]);
                    }
                    else if (title.equals("Brightness")) {
                        colorA = Color.getHSBColor(hsb[0], hsb[1], 0.0f);
                        colorB = Color.getHSBColor(hsb[0], hsb[1], 1.0f);
                    }
                    if (colorA != null && colorB != null) {
                        context.getInterface().fillRect(new Rectangle(context.getPos().x + 1, context.getPos().y + 1, context.getSize().width - 2, context.getSize().height - 2), colorA, colorB, colorB, colorA);
                    }
                    else {
                        final int a = rect.x;
                        int b = rect.width / 6;
                        int c = rect.width * 2 / 6;
                        int d = rect.width * 3 / 6;
                        int e = rect.width * 4 / 6;
                        int f = rect.width * 5 / 6;
                        int g = rect.width;
                        b += a;
                        c += a;
                        d += a;
                        e += a;
                        f += a;
                        g += a;
                        final Color c2 = Color.getHSBColor(0.0f, hsb[1], hsb[2]);
                        final Color c3 = Color.getHSBColor(0.16666667f, hsb[1], hsb[2]);
                        final Color c4 = Color.getHSBColor(0.33333334f, hsb[1], hsb[2]);
                        final Color c5 = Color.getHSBColor(0.5f, hsb[1], hsb[2]);
                        final Color c6 = Color.getHSBColor(0.6666667f, hsb[1], hsb[2]);
                        final Color c7 = Color.getHSBColor(0.8333333f, hsb[1], hsb[2]);
                        context.getInterface().fillRect(new Rectangle(a, rect.y, b - a, rect.height), c2, c3, c3, c2);
                        context.getInterface().fillRect(new Rectangle(b, rect.y, c - b, rect.height), c3, c4, c4, c3);
                        context.getInterface().fillRect(new Rectangle(c, rect.y, d - c, rect.height), c4, c5, c5, c4);
                        context.getInterface().fillRect(new Rectangle(d, rect.y, e - d, rect.height), c5, c6, c6, c5);
                        context.getInterface().fillRect(new Rectangle(e, rect.y, f - e, rect.height), c6, c7, c7, c6);
                        context.getInterface().fillRect(new Rectangle(f, rect.y, g - f, rect.height), c7, c2, c2, c7);
                    }
                    ImpactTheme.this.renderOverlay(context);
                    final Color lineColor = ImpactTheme.this.scheme.getColor("Active Font Color");
                    final int separator = (int)Math.round((rect.width - 1) * value);
                    context.getInterface().fillRect(new Rectangle(rect.x + separator, rect.y, 1, rect.height), lineColor, lineColor, lineColor, lineColor);
                }
                else {
                    final Color valueColor = ImpactTheme.this.scheme.getColor("Active Font Color");
                    Color fontColor = ImpactTheme.this.getFontColor(effFocus);
                    if (context.isHovered() && context.getInterface().getMouse().x > context.getPos().x + context.getSize().height - ImpactTheme.this.padding) {
                        fontColor = ImpactTheme.this.scheme.getColor("Active Font Color");
                    }
                    int xpos = context.getPos().x + context.getSize().height - ImpactTheme.this.padding;
                    if (container && graphicalLevel <= 0) {
                        xpos = context.getPos().x + context.getSize().width / 2 - context.getInterface().getFontWidth(ImpactTheme.this.height, title) / 2;
                    }
                    context.getInterface().drawString(new Point(xpos, context.getPos().y + ImpactTheme.this.padding - (container ? 1 : 0)), ImpactTheme.this.height, title, fontColor);
                    if (context.isHovered()) {
                        context.getInterface().drawString(new Point(context.getPos().x + context.getSize().width - ImpactTheme.this.padding - context.getInterface().getFontWidth(ImpactTheme.this.height, state), context.getPos().y + ImpactTheme.this.padding - (container ? 1 : 0)), ImpactTheme.this.height, state, valueColor);
                    }
                    final Color lineColor2 = ImpactTheme.this.scheme.getColor("Active Font Color");
                    final int separator2 = (int)Math.round((context.getSize().width - context.getSize().height + ImpactTheme.this.padding - (container ? 0 : 1)) * value);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x + context.getSize().height - ImpactTheme.this.padding, context.getPos().y + context.getSize().height - (container ? 1 : 2), separator2, 1), lineColor2, lineColor2, lineColor2, lineColor2);
                }
            }
            
            @Override
            public int getDefaultHeight() {
                return container ? (ImpactTheme.this.getBaseHeight() - 2) : ImpactTheme.this.getBaseHeight();
            }
            
            @Override
            public Rectangle getSlideArea(final Context context, final String title, final String state) {
                if (ImpactTheme.this.getColor(null) != null && (title.equals("Red") || title.equals("Green") || title.equals("Blue") || title.equals("Hue") || title.equals("Saturation") || title.equals("Brightness"))) {
                    Rectangle rect = context.getRect();
                    if (!container) {
                        rect = new Rectangle(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
                    }
                    return rect;
                }
                return new Rectangle(context.getPos().x + context.getSize().height - ImpactTheme.this.padding, context.getPos().y, context.getSize().width - context.getSize().height + ImpactTheme.this.padding - (container ? 0 : 1), context.getSize().height);
            }
        };
    }
    
    @Override
    public IRadioRenderer getRadioRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new IRadioRenderer() {
            @Override
            public void renderItem(final Context context, final ILabeled[] items, final boolean focus, final int target, final double state, final boolean horizontal) {
                if (graphicalLevel <= 0) {
                    ImpactTheme.this.renderBackground(context, focus);
                }
                for (int i = 0; i < items.length; ++i) {
                    final Rectangle rect = this.getItemRect(context, items, i, horizontal);
                    final Context subContext = new Context(context.getInterface(), rect.width, rect.getLocation(), context.hasFocus(), context.onTop());
                    subContext.setHeight(rect.height);
                    ImpactTheme.this.renderOverlay(subContext);
                    Color color = ImpactTheme.this.getFontColor(focus);
                    if (i == target) {
                        color = ImpactTheme.this.scheme.getColor("Active Font Color");
                    }
                    else if (!subContext.isHovered()) {
                        color = ImpactTheme.this.scheme.getColor("Inactive Font Color");
                    }
                    context.getInterface().drawString(new Point(rect.x + ImpactTheme.this.padding, rect.y + ImpactTheme.this.padding), ImpactTheme.this.height, items[i].getDisplayName(), color);
                }
            }
            
            @Override
            public int getDefaultHeight(final ILabeled[] items, final boolean horizontal) {
                return (horizontal ? 1 : items.length) * ImpactTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public IResizeBorderRenderer getResizeRenderer() {
        return new IResizeBorderRenderer() {
            @Override
            public void drawBorder(final Context context, final boolean focus) {
                final Color color = ImpactTheme.this.getBackgroundColor(focus);
                final Rectangle rect = context.getRect();
                context.getInterface().fillRect(new Rectangle(rect.x, rect.y, rect.width, this.getBorder()), color, color, color, color);
                context.getInterface().fillRect(new Rectangle(rect.x, rect.y + rect.height - this.getBorder(), rect.width, this.getBorder()), color, color, color, color);
                context.getInterface().fillRect(new Rectangle(rect.x, rect.y + this.getBorder(), this.getBorder(), rect.height - 2 * this.getBorder()), color, color, color, color);
                context.getInterface().fillRect(new Rectangle(rect.x + rect.width - this.getBorder(), rect.y + this.getBorder(), this.getBorder(), rect.height - 2 * this.getBorder()), color, color, color, color);
            }
            
            @Override
            public int getBorder() {
                return 2;
            }
        };
    }
    
    @Override
    public ITextFieldRenderer getTextRenderer(final boolean embed, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new ITextFieldRenderer() {
            @Override
            public int renderTextField(final Context context, final String title, final boolean focus, final String content, final int position, final int select, int boxPosition, final boolean insertMode) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                if (graphicalLevel <= 0) {
                    ImpactTheme.this.renderBackground(context, effFocus);
                }
                if (!container) {
                    final Color color = (graphicalLevel <= 0) ? ImpactTheme.this.scheme.getColor("Panel Outline Color") : ImpactTheme.this.scheme.getColor("Component Outline Color");
                    ITheme.drawRect(context.getInterface(), context.getRect(), color);
                    ImpactTheme.this.renderOverlay(context);
                }
                Color textColor = ImpactTheme.this.getFontColor(effFocus);
                if (context.isHovered() && context.getInterface().getMouse().x > context.getPos().x + context.getSize().height - ImpactTheme.this.padding) {
                    textColor = ImpactTheme.this.scheme.getColor("Active Font Color");
                }
                final Color highlightColor = ImpactTheme.this.scheme.getColor("Highlight Color");
                final Rectangle rect = this.getTextArea(context, title);
                final int strlen = context.getInterface().getFontWidth(ImpactTheme.this.height, content.substring(0, position));
                context.getInterface().fillRect(rect, new Color(0, 0, 0, 64), new Color(0, 0, 0, 64), new Color(0, 0, 0, 64), new Color(0, 0, 0, 64));
                if (boxPosition < position) {
                    int minPosition;
                    for (minPosition = boxPosition; minPosition < position && context.getInterface().getFontWidth(ImpactTheme.this.height, content.substring(0, minPosition)) + rect.width - ImpactTheme.this.padding < strlen; ++minPosition) {}
                    if (boxPosition < minPosition) {
                        boxPosition = minPosition;
                    }
                }
                else if (boxPosition > position) {
                    boxPosition = position - 1;
                }
                int maxPosition;
                for (maxPosition = content.length(); maxPosition > 0; --maxPosition) {
                    if (context.getInterface().getFontWidth(ImpactTheme.this.height, content.substring(maxPosition)) >= rect.width - ImpactTheme.this.padding) {
                        ++maxPosition;
                        break;
                    }
                }
                if (boxPosition > maxPosition) {
                    boxPosition = maxPosition;
                }
                else if (boxPosition < 0) {
                    boxPosition = 0;
                }
                final int offset = context.getInterface().getFontWidth(ImpactTheme.this.height, content.substring(0, boxPosition));
                final int x1 = rect.x + ImpactTheme.this.padding / 2 - offset + strlen;
                int x2 = rect.x + ImpactTheme.this.padding / 2 - offset;
                if (position < content.length()) {
                    x2 += context.getInterface().getFontWidth(ImpactTheme.this.height, content.substring(0, position + 1));
                }
                else {
                    x2 += context.getInterface().getFontWidth(ImpactTheme.this.height, content + "X");
                }
                ImpactTheme.this.renderOverlay(context);
                context.getInterface().drawString(new Point(context.getPos().x + context.getSize().height - ImpactTheme.this.padding, context.getPos().y + ImpactTheme.this.padding - (container ? 1 : 0)), ImpactTheme.this.height, title, textColor);
                context.getInterface().window(rect);
                if (select >= 0) {
                    final int x3 = rect.x + ImpactTheme.this.padding / 2 - offset + context.getInterface().getFontWidth(ImpactTheme.this.height, content.substring(0, select));
                    context.getInterface().fillRect(new Rectangle(Math.min(x1, x3), context.getPos().y + ImpactTheme.this.padding - (container ? 1 : 0), Math.abs(x3 - x1), ImpactTheme.this.height), highlightColor, highlightColor, highlightColor, highlightColor);
                }
                context.getInterface().drawString(new Point(rect.x + ImpactTheme.this.padding / 2 - offset, context.getPos().y + ImpactTheme.this.padding - (container ? 1 : 0)), ImpactTheme.this.height, content, textColor);
                if (System.currentTimeMillis() / 500L % 2L == 0L && focus) {
                    if (insertMode) {
                        context.getInterface().fillRect(new Rectangle(x1, context.getPos().y + ImpactTheme.this.padding - (container ? 1 : 0) + ImpactTheme.this.height, x2 - x1, 1), textColor, textColor, textColor, textColor);
                    }
                    else {
                        context.getInterface().fillRect(new Rectangle(x1, context.getPos().y + ImpactTheme.this.padding - (container ? 1 : 0), 1, ImpactTheme.this.height), textColor, textColor, textColor, textColor);
                    }
                }
                context.getInterface().restore();
                return boxPosition;
            }
            
            @Override
            public int getDefaultHeight() {
                return container ? (ImpactTheme.this.getBaseHeight() - 2) : ImpactTheme.this.getBaseHeight();
            }
            
            @Override
            public Rectangle getTextArea(final Context context, final String title) {
                final Rectangle rect = context.getRect();
                final int length = rect.height - ImpactTheme.this.padding + context.getInterface().getFontWidth(ImpactTheme.this.height, title + "X");
                return new Rectangle(rect.x + length, rect.y + (container ? 0 : 1), rect.width - length, rect.height - (container ? 0 : 2));
            }
            
            @Override
            public int transformToCharPos(final Context context, final String title, final String content, final int boxPosition) {
                final Rectangle rect = this.getTextArea(context, title);
                final Point mouse = context.getInterface().getMouse();
                final int offset = context.getInterface().getFontWidth(ImpactTheme.this.height, content.substring(0, boxPosition));
                if (rect.contains(mouse)) {
                    for (int i = 1; i <= content.length(); ++i) {
                        if (rect.x + ImpactTheme.this.padding / 2 - offset + context.getInterface().getFontWidth(ImpactTheme.this.height, content.substring(0, i)) > mouse.x) {
                            return i - 1;
                        }
                    }
                    return content.length();
                }
                return -1;
            }
        };
    }
    
    @Override
    public ISwitchRenderer<Boolean> getToggleSwitchRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new ISwitchRenderer<Boolean>() {
            public void renderButton(final Context context, final String title, final boolean focus, final Boolean state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                if (graphicalLevel <= 0) {
                    if (container) {
                        final Color color = ImpactTheme.this.scheme.getColor("Title Color");
                        context.getInterface().fillRect(context.getRect(), color, color, color, color);
                    }
                    else {
                        ImpactTheme.this.renderBackground(context, effFocus);
                    }
                }
                if (!container) {
                    final Color color = (graphicalLevel <= 0) ? ImpactTheme.this.scheme.getColor("Panel Outline Color") : ImpactTheme.this.scheme.getColor("Component Outline Color");
                    ITheme.drawRect(context.getInterface(), context.getRect(), color);
                    ImpactTheme.this.renderOverlay(context);
                }
                ImpactTheme.this.renderOverlay(context);
                context.getInterface().drawString(new Point(context.getPos().x + ImpactTheme.this.padding, context.getPos().y + ImpactTheme.this.padding), ImpactTheme.this.height, title, ImpactTheme.this.getFontColor(focus));
                final Color fillColor = ImpactTheme.this.getMainColor(focus, state);
                Rectangle rect = state ? this.getOnField(context) : this.getOffField(context);
                context.getInterface().fillRect(rect, fillColor, fillColor, fillColor, fillColor);
                rect = context.getRect();
                rect = new Rectangle(rect.x + rect.width - 2 * rect.height + 3 * ImpactTheme.this.padding, rect.y + ImpactTheme.this.padding, 2 * rect.height - 4 * ImpactTheme.this.padding, rect.height - 2 * ImpactTheme.this.padding);
                ITheme.drawRect(context.getInterface(), rect, ImpactTheme.this.scheme.getColor("Component Outline Color"));
            }
            
            public int getDefaultHeight() {
                return ImpactTheme.this.getBaseHeight();
            }
            
            @Override
            public Rectangle getOnField(final Context context) {
                final Rectangle rect = context.getRect();
                return new Rectangle(rect.x + rect.width - rect.height + ImpactTheme.this.padding, rect.y + ImpactTheme.this.padding, rect.height - 2 * ImpactTheme.this.padding, rect.height - 2 * ImpactTheme.this.padding);
            }
            
            @Override
            public Rectangle getOffField(final Context context) {
                final Rectangle rect = context.getRect();
                return new Rectangle(rect.x + rect.width - 2 * rect.height + 3 * ImpactTheme.this.padding, rect.y + ImpactTheme.this.padding, rect.height - 2 * ImpactTheme.this.padding, rect.height - 2 * ImpactTheme.this.padding);
            }
        };
    }
    
    @Override
    public ISwitchRenderer<String> getCycleSwitchRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new ISwitchRenderer<String>() {
            public void renderButton(final Context context, final String title, final boolean focus, final String state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                Context subContext = new Context(context, context.getSize().width - 2 * context.getSize().height, new Point(0, 0), true, true);
                subContext.setHeight(context.getSize().height);
                if (graphicalLevel <= 0) {
                    if (container) {
                        final Color color = ImpactTheme.this.scheme.getColor("Title Color");
                        context.getInterface().fillRect(subContext.getRect(), color, color, color, color);
                    }
                    else {
                        ImpactTheme.this.renderBackground(subContext, effFocus);
                    }
                }
                if (!container) {
                    final Color color = (graphicalLevel <= 0) ? ImpactTheme.this.scheme.getColor("Panel Outline Color") : ImpactTheme.this.scheme.getColor("Component Outline Color");
                    ITheme.drawRect(context.getInterface(), subContext.getRect(), color);
                    ImpactTheme.this.renderOverlay(subContext);
                }
                Color valueColor = ImpactTheme.this.getFontColor(effFocus);
                if (context.isHovered() && context.getInterface().getMouse().x > subContext.getPos().x + subContext.getSize().height - ImpactTheme.this.padding) {
                    valueColor = ImpactTheme.this.scheme.getColor("Active Font Color");
                }
                final Color fontColor = ImpactTheme.this.scheme.getColor("Active Font Color");
                int xpos = context.getPos().x + context.getSize().height - ImpactTheme.this.padding;
                if (container && graphicalLevel <= 0) {
                    xpos = subContext.getPos().x + subContext.getSize().width / 2 - context.getInterface().getFontWidth(ImpactTheme.this.height, title) / 2;
                }
                context.getInterface().drawString(new Point(xpos, subContext.getPos().y + ImpactTheme.this.padding - (container ? 1 : 0)), ImpactTheme.this.height, title, fontColor);
                context.getInterface().drawString(new Point(subContext.getPos().x + subContext.getSize().width - ImpactTheme.this.padding - context.getInterface().getFontWidth(ImpactTheme.this.height, state), subContext.getPos().y + ImpactTheme.this.padding - (container ? 1 : 0)), ImpactTheme.this.height, state, valueColor);
                Rectangle rect = this.getOnField(context);
                subContext = new Context(context, rect.width, new Point(rect.x - context.getPos().x, 0), true, true);
                subContext.setHeight(rect.height);
                ImpactTheme.this.getSmallButtonRenderer(5, logicalLevel, graphicalLevel, false).renderButton(subContext, (String)null, effFocus, (Object)null);
                rect = this.getOffField(context);
                subContext = new Context(context, rect.width, new Point(rect.x - context.getPos().x, 0), true, true);
                subContext.setHeight(rect.height);
                ImpactTheme.this.getSmallButtonRenderer(4, logicalLevel, graphicalLevel, false).renderButton(subContext, (String)null, effFocus, (Object)null);
            }
            
            public int getDefaultHeight() {
                return ImpactTheme.this.getBaseHeight();
            }
            
            @Override
            public Rectangle getOnField(final Context context) {
                final Rectangle rect = context.getRect();
                return new Rectangle(rect.x + rect.width - rect.height, rect.y, rect.height, rect.height);
            }
            
            @Override
            public Rectangle getOffField(final Context context) {
                final Rectangle rect = context.getRect();
                return new Rectangle(rect.x + rect.width - 2 * rect.height, rect.y, rect.height, rect.height);
            }
        };
    }
    
    @Override
    public IColorPickerRenderer getColorPickerRenderer() {
        return (IColorPickerRenderer)new StandardColorPicker() {
            @Override
            public int getPadding() {
                return ImpactTheme.this.padding;
            }
            
            @Override
            public int getBaseHeight() {
                return ImpactTheme.this.getBaseHeight();
            }
            
            public void renderCursor(final Context context, final Point p, final Color color) {
                final Color fontColor = ImpactTheme.this.scheme.getColor("Active Font Color");
                context.getInterface().fillRect(new Rectangle(p.x - 1, p.y - 1, 2, 2), fontColor, fontColor, fontColor, fontColor);
            }
        };
    }
    
    @Override
    public int getBaseHeight() {
        return this.height + 2 * this.padding;
    }
    
    @Override
    public Color getMainColor(final boolean focus, final boolean active) {
        if (active) {
            return this.scheme.getColor("Enabled Color");
        }
        return this.scheme.getColor("Disabled Color");
    }
    
    @Override
    public Color getBackgroundColor(final boolean focus) {
        return this.scheme.getColor("Background Color");
    }
    
    @Override
    public Color getFontColor(final boolean focus) {
        return this.scheme.getColor("Hovered Font Color");
    }
}
