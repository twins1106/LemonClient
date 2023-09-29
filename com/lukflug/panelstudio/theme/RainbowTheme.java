//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import java.util.function.*;
import com.lukflug.panelstudio.base.*;
import java.awt.*;
import com.lukflug.panelstudio.setting.*;

public class RainbowTheme extends ThemeBase
{
    protected IBoolean ignoreDisabled;
    protected IBoolean buttonRainbow;
    protected IntSupplier rainbowGradient;
    protected int height;
    protected int padding;
    protected String separator;
    
    public RainbowTheme(final IColorScheme scheme, final IBoolean ignoreDisabled, final IBoolean buttonRainbow, final IntSupplier rainbowGradient, final int height, final int padding, final String separator) {
        super(scheme);
        this.ignoreDisabled = ignoreDisabled;
        this.buttonRainbow = buttonRainbow;
        this.rainbowGradient = rainbowGradient;
        this.height = height;
        this.padding = padding;
        this.separator = separator;
        scheme.createSetting((ITheme)this, "Title Color", "The color for panel titles.", false, true, new Color(64, 64, 64), false);
        scheme.createSetting((ITheme)this, "Rainbow Color", "The rainbow base color.", false, true, new Color(255, 0, 0), false);
        scheme.createSetting((ITheme)this, "Background Color", "The main color for disabled components.", false, true, new Color(64, 64, 64), false);
        scheme.createSetting((ITheme)this, "Font Color", "The main color for text.", false, true, new Color(255, 255, 255), false);
        scheme.createSetting((ITheme)this, "Highlight Color", "The color for highlighted text.", false, true, new Color(0, 0, 255), false);
    }
    
    protected void renderOverlay(final Context context) {
        final Color color = context.isHovered() ? new Color(0, 0, 0, 64) : new Color(0, 0, 0, 0);
        context.getInterface().fillRect(context.getRect(), color, color, color, color);
    }
    
    protected void renderRainbowRect(final Rectangle rect, final Context context, final boolean focus) {
        final Color source = this.getMainColor(focus, true);
        final float[] hsb = Color.RGBtoHSB(source.getRed(), source.getGreen(), source.getBlue(), null);
        float currentHue = hsb[0];
        float targetHue = hsb[0];
        if (this.rainbowGradient.getAsInt() != 0) {
            targetHue += rect.height / (float)this.rainbowGradient.getAsInt();
        }
        else {
            context.getInterface().fillRect(rect, source, source, source, source);
        }
        while (currentHue < targetHue) {
            float nextHue = (float)(Math.floor(currentHue * 6.0f) + 1.0) / 6.0f;
            if (nextHue > targetHue) {
                nextHue = targetHue;
            }
            final Color colorA = Color.getHSBColor(currentHue, hsb[1], hsb[2]);
            final Color colorB = Color.getHSBColor(nextHue, hsb[1], hsb[2]);
            final int top = Math.round((currentHue - hsb[0]) * this.rainbowGradient.getAsInt());
            final int bottom = Math.round((nextHue - hsb[0]) * this.rainbowGradient.getAsInt());
            context.getInterface().fillRect(new Rectangle(rect.x, rect.y + top, rect.width, bottom - top), colorA, colorA, colorB, colorB);
            currentHue = nextHue;
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
        final Color color = this.getFontColor(focus);
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
    
    public IDescriptionRenderer getDescriptionRenderer() {
        return (IDescriptionRenderer)new IDescriptionRenderer() {
            public void renderDescription(final IInterface inter, final Point pos, final String text) {
                final Rectangle rect = new Rectangle(pos, new Dimension(inter.getFontWidth(RainbowTheme.this.height, text) + 2, RainbowTheme.this.height + 2));
                final Color color = RainbowTheme.this.getBackgroundColor(true);
                inter.fillRect(rect, color, color, color, color);
                inter.drawString(new Point(pos.x + 1, pos.y + 1), RainbowTheme.this.height, text, RainbowTheme.this.getFontColor(true));
            }
        };
    }
    
    public IContainerRenderer getContainerRenderer(final int logicalLevel, final int graphicalLevel, final boolean horizontal) {
        return (IContainerRenderer)new IContainerRenderer() {
            public void renderBackground(final Context context, final boolean focus) {
                if (graphicalLevel == 0 && !RainbowTheme.this.buttonRainbow.isOn()) {
                    RainbowTheme.this.renderRainbowRect(context.getRect(), context, focus);
                }
            }
        };
    }
    
    public <T> IPanelRenderer<T> getPanelRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        return (IPanelRenderer<T>)new IPanelRenderer<T>() {
            public int getBorder() {
                return (graphicalLevel == 0) ? 1 : 0;
            }
            
            public void renderPanelOverlay(final Context context, final boolean focus, final T state, final boolean open) {
            }
            
            public void renderTitleOverlay(final Context context, final boolean focus, final T state, final boolean open) {
                if (graphicalLevel <= 0) {
                    final Color color = RainbowTheme.this.getFontColor(focus);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y + context.getSize().height, context.getSize().width, 1), color, color, color, color);
                }
                else {
                    Rectangle rect = context.getRect();
                    rect = new Rectangle(rect.width - rect.height, 0, rect.height, rect.height);
                    if (rect.width % 2 != 0) {
                        final Rectangle rectangle = rect;
                        --rectangle.width;
                        final Rectangle rectangle2 = rect;
                        --rectangle2.height;
                        final Rectangle rectangle3 = rect;
                        ++rectangle3.x;
                    }
                    final Context subContext = new Context(context, rect.width, rect.getLocation(), true, true);
                    subContext.setHeight(rect.height);
                    if (open) {
                        RainbowTheme.this.renderSmallButton(subContext, null, 7, focus);
                    }
                    else {
                        RainbowTheme.this.renderSmallButton(subContext, null, 5, focus);
                    }
                }
            }
        };
    }
    
    public <T> IScrollBarRenderer<T> getScrollBarRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        return (IScrollBarRenderer<T>)new IScrollBarRenderer<T>() {
            public int renderScrollBar(final Context context, final boolean focus, final T state, final boolean horizontal, final int height, final int position) {
                final Color color = RainbowTheme.this.getBackgroundColor(focus);
                if (graphicalLevel == 0 || RainbowTheme.this.buttonRainbow.isOn()) {
                    RainbowTheme.this.renderRainbowRect(context.getRect(), context, focus);
                }
                if (horizontal) {
                    final int a = (int)(position / (double)height * context.getSize().width);
                    final int b = (int)((position + context.getSize().width) / (double)height * context.getSize().width);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y, a, context.getSize().height), color, color, color, color);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x + b, context.getPos().y, context.getSize().width - b, context.getSize().height), color, color, color, color);
                }
                else {
                    final int a = (int)(position / (double)height * context.getSize().height);
                    final int b = (int)((position + context.getSize().height) / (double)height * context.getSize().height);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y, context.getSize().width, a), color, color, color, color);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y + b, context.getSize().width, context.getSize().height - b), color, color, color, color);
                }
                if (horizontal) {
                    return (int)((context.getInterface().getMouse().x - context.getPos().x) * height / (double)context.getSize().width - context.getSize().width / 2.0);
                }
                return (int)((context.getInterface().getMouse().y - context.getPos().y) * height / (double)context.getSize().height - context.getSize().height / 2.0);
            }
            
            public int getThickness() {
                return 4;
            }
        };
    }
    
    public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IEmptySpaceRenderer<T>)new IEmptySpaceRenderer<T>() {
            public void renderSpace(final Context context, final boolean focus, final T state) {
                final Color color = RainbowTheme.this.getBackgroundColor(focus);
                context.getInterface().fillRect(context.getRect(), color, color, color, color);
            }
        };
    }
    
    public <T> IButtonRenderer<T> getButtonRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IButtonRenderer<T>)new IButtonRenderer<T>() {
            public void renderButton(final Context context, final String title, final boolean focus, final Object state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                boolean active = container && graphicalLevel != 0;
                if (type == Boolean.class) {
                    active = ((boolean)state || (RainbowTheme.this.ignoreDisabled.isOn() && container));
                }
                if (!active) {
                    final Color color = RainbowTheme.this.getBackgroundColor(effFocus);
                    context.getInterface().fillRect(context.getRect(), color, color, color, color);
                }
                else if (graphicalLevel == 0 || RainbowTheme.this.buttonRainbow.isOn()) {
                    RainbowTheme.this.renderRainbowRect(context.getRect(), context, effFocus);
                }
                RainbowTheme.this.renderOverlay(context);
                final String text = ((logicalLevel >= 2) ? "> " : "") + title + ((type == String.class) ? (RainbowTheme.this.separator + state) : "");
                context.getInterface().drawString(new Point(context.getPos().x + RainbowTheme.this.padding, context.getPos().y + RainbowTheme.this.padding), RainbowTheme.this.height, text, RainbowTheme.this.getFontColor(effFocus));
            }
            
            public int getDefaultHeight() {
                return RainbowTheme.this.getBaseHeight();
            }
        };
    }
    
    public IButtonRenderer<Void> getSmallButtonRenderer(final int symbol, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IButtonRenderer<Void>)new IButtonRenderer<Void>() {
            public void renderButton(final Context context, final String title, final boolean focus, final Void state) {
                if (graphicalLevel == 0 || RainbowTheme.this.buttonRainbow.isOn()) {
                    RainbowTheme.this.renderRainbowRect(context.getRect(), context, focus);
                }
                RainbowTheme.this.renderOverlay(context);
                if (!container || logicalLevel <= 0) {
                    RainbowTheme.this.renderSmallButton(context, title, symbol, focus);
                }
            }
            
            public int getDefaultHeight() {
                return RainbowTheme.this.getBaseHeight();
            }
        };
    }
    
    public IButtonRenderer<String> getKeybindRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return this.getButtonRenderer(String.class, logicalLevel, graphicalLevel, container);
    }
    
    public ISliderRenderer getSliderRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (ISliderRenderer)new ISliderRenderer() {
            public void renderSlider(final Context context, final String title, final String state, final boolean focus, final double value) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                if (graphicalLevel == 0 || RainbowTheme.this.buttonRainbow.isOn()) {
                    RainbowTheme.this.renderRainbowRect(context.getRect(), context, effFocus);
                }
                final int divider = (int)(context.getSize().width * value);
                final Color color = RainbowTheme.this.getBackgroundColor(effFocus);
                context.getInterface().fillRect(new Rectangle(context.getPos().x + divider, context.getPos().y, context.getSize().width - divider, context.getSize().height), color, color, color, color);
                RainbowTheme.this.renderOverlay(context);
                final String text = ((logicalLevel >= 2) ? "> " : "") + title + RainbowTheme.this.separator + state;
                context.getInterface().drawString(new Point(context.getPos().x + RainbowTheme.this.padding, context.getPos().y + RainbowTheme.this.padding), RainbowTheme.this.height, text, RainbowTheme.this.getFontColor(effFocus));
            }
            
            public int getDefaultHeight() {
                return RainbowTheme.this.getBaseHeight();
            }
        };
    }
    
    public IRadioRenderer getRadioRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IRadioRenderer)new IRadioRenderer() {
            public void renderItem(final Context context, final ILabeled[] items, final boolean focus, final int target, final double state, final boolean horizontal) {
                if (graphicalLevel == 0 || RainbowTheme.this.buttonRainbow.isOn()) {
                    RainbowTheme.this.renderRainbowRect(context.getRect(), context, focus);
                }
                for (int i = 0; i < items.length; ++i) {
                    final Rectangle rect = this.getItemRect(context, items, i, horizontal);
                    final Context subContext = new Context(context.getInterface(), rect.width, rect.getLocation(), context.hasFocus(), context.onTop());
                    subContext.setHeight(rect.height);
                    if (i != target) {
                        final Color color = RainbowTheme.this.getBackgroundColor(focus);
                        context.getInterface().fillRect(subContext.getRect(), color, color, color, color);
                    }
                    RainbowTheme.this.renderOverlay(subContext);
                    context.getInterface().drawString(new Point(rect.x + RainbowTheme.this.padding, rect.y + RainbowTheme.this.padding), RainbowTheme.this.height, items[i].getDisplayName(), RainbowTheme.this.getFontColor(focus));
                }
            }
            
            public int getDefaultHeight(final ILabeled[] items, final boolean horizontal) {
                return (horizontal ? 1 : items.length) * RainbowTheme.this.getBaseHeight();
            }
        };
    }
    
    public IResizeBorderRenderer getResizeRenderer() {
        return (IResizeBorderRenderer)new IResizeBorderRenderer() {
            public void drawBorder(final Context context, final boolean focus) {
                final Color color = RainbowTheme.this.getBackgroundColor(focus);
                final Rectangle rect = context.getRect();
                context.getInterface().fillRect(new Rectangle(rect.x, rect.y, rect.width, this.getBorder()), color, color, color, color);
                context.getInterface().fillRect(new Rectangle(rect.x, rect.y + rect.height - this.getBorder(), rect.width, this.getBorder()), color, color, color, color);
                context.getInterface().fillRect(new Rectangle(rect.x, rect.y + this.getBorder(), this.getBorder(), rect.height - 2 * this.getBorder()), color, color, color, color);
                context.getInterface().fillRect(new Rectangle(rect.x + rect.width - this.getBorder(), rect.y + this.getBorder(), this.getBorder(), rect.height - 2 * this.getBorder()), color, color, color, color);
            }
            
            public int getBorder() {
                return 2;
            }
        };
    }
    
    public ITextFieldRenderer getTextRenderer(final boolean embed, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (ITextFieldRenderer)new ITextFieldRenderer() {
            public int renderTextField(final Context context, final String title, final boolean focus, final String content, final int position, final int select, int boxPosition, final boolean insertMode) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                if (graphicalLevel == 0 || RainbowTheme.this.buttonRainbow.isOn()) {
                    RainbowTheme.this.renderRainbowRect(context.getRect(), context, effFocus);
                }
                final Color textColor = RainbowTheme.this.getFontColor(effFocus);
                final Color highlightColor = RainbowTheme.this.scheme.getColor("Highlight Color");
                final Rectangle rect = this.getTextArea(context, title);
                final int strlen = context.getInterface().getFontWidth(RainbowTheme.this.height, content.substring(0, position));
                context.getInterface().fillRect(rect, new Color(0, 0, 0, 64), new Color(0, 0, 0, 64), new Color(0, 0, 0, 64), new Color(0, 0, 0, 64));
                ITheme.drawRect(context.getInterface(), rect, new Color(0, 0, 0, 64));
                if (boxPosition < position) {
                    int minPosition;
                    for (minPosition = boxPosition; minPosition < position && context.getInterface().getFontWidth(RainbowTheme.this.height, content.substring(0, minPosition)) + rect.width - RainbowTheme.this.padding < strlen; ++minPosition) {}
                    if (boxPosition < minPosition) {
                        boxPosition = minPosition;
                    }
                }
                else if (boxPosition > position) {
                    boxPosition = position - 1;
                }
                int maxPosition;
                for (maxPosition = content.length(); maxPosition > 0; --maxPosition) {
                    if (context.getInterface().getFontWidth(RainbowTheme.this.height, content.substring(maxPosition)) >= rect.width - RainbowTheme.this.padding) {
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
                final int offset = context.getInterface().getFontWidth(RainbowTheme.this.height, content.substring(0, boxPosition));
                final int x1 = rect.x + RainbowTheme.this.padding / 2 - offset + strlen;
                int x2 = rect.x + RainbowTheme.this.padding / 2 - offset;
                if (position < content.length()) {
                    x2 += context.getInterface().getFontWidth(RainbowTheme.this.height, content.substring(0, position + 1));
                }
                else {
                    x2 += context.getInterface().getFontWidth(RainbowTheme.this.height, content + "X");
                }
                RainbowTheme.this.renderOverlay(context);
                context.getInterface().drawString(new Point(context.getPos().x + RainbowTheme.this.padding, context.getPos().y + RainbowTheme.this.padding / 2), RainbowTheme.this.height, title + RainbowTheme.this.separator, textColor);
                context.getInterface().window(rect);
                if (select >= 0) {
                    final int x3 = rect.x + RainbowTheme.this.padding / 2 - offset + context.getInterface().getFontWidth(RainbowTheme.this.height, content.substring(0, select));
                    context.getInterface().fillRect(new Rectangle(Math.min(x1, x3), rect.y + RainbowTheme.this.padding / 2, Math.abs(x3 - x1), RainbowTheme.this.height), highlightColor, highlightColor, highlightColor, highlightColor);
                }
                context.getInterface().drawString(new Point(rect.x + RainbowTheme.this.padding / 2 - offset, rect.y + RainbowTheme.this.padding / 2), RainbowTheme.this.height, content, textColor);
                if (System.currentTimeMillis() / 500L % 2L == 0L && focus) {
                    if (insertMode) {
                        context.getInterface().fillRect(new Rectangle(x1, rect.y + RainbowTheme.this.padding / 2 + RainbowTheme.this.height, x2 - x1, 1), textColor, textColor, textColor, textColor);
                    }
                    else {
                        context.getInterface().fillRect(new Rectangle(x1, rect.y + RainbowTheme.this.padding / 2, 1, RainbowTheme.this.height), textColor, textColor, textColor, textColor);
                    }
                }
                context.getInterface().restore();
                return boxPosition;
            }
            
            public int getDefaultHeight() {
                int height = RainbowTheme.this.getBaseHeight() - RainbowTheme.this.padding;
                if (height % 2 == 1) {
                    ++height;
                }
                return height;
            }
            
            public Rectangle getTextArea(final Context context, final String title) {
                final Rectangle rect = context.getRect();
                final int length = RainbowTheme.this.padding + context.getInterface().getFontWidth(RainbowTheme.this.height, title + RainbowTheme.this.separator);
                return new Rectangle(rect.x + length, rect.y, rect.width - length, rect.height);
            }
            
            public int transformToCharPos(final Context context, final String title, final String content, final int boxPosition) {
                final Rectangle rect = this.getTextArea(context, title);
                final Point mouse = context.getInterface().getMouse();
                final int offset = context.getInterface().getFontWidth(RainbowTheme.this.height, content.substring(0, boxPosition));
                if (rect.contains(mouse)) {
                    for (int i = 1; i <= content.length(); ++i) {
                        if (rect.x + RainbowTheme.this.padding / 2 - offset + context.getInterface().getFontWidth(RainbowTheme.this.height, content.substring(0, i)) > mouse.x) {
                            return i - 1;
                        }
                    }
                    return content.length();
                }
                return -1;
            }
        };
    }
    
    public ISwitchRenderer<Boolean> getToggleSwitchRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (ISwitchRenderer<Boolean>)new ISwitchRenderer<Boolean>() {
            public void renderButton(final Context context, final String title, final boolean focus, final Boolean state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                if (graphicalLevel == 0 || RainbowTheme.this.buttonRainbow.isOn()) {
                    RainbowTheme.this.renderRainbowRect(context.getRect(), context, effFocus);
                }
                final Color color = RainbowTheme.this.getBackgroundColor(effFocus);
                if (graphicalLevel <= 0 && container) {
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y + context.getSize().height - 1, context.getSize().width, 1), color, color, color, color);
                }
                RainbowTheme.this.renderOverlay(context);
                context.getInterface().drawString(new Point(context.getPos().x + RainbowTheme.this.padding, context.getPos().y + RainbowTheme.this.padding), RainbowTheme.this.height, title + RainbowTheme.this.separator + (state ? "On" : "Off"), RainbowTheme.this.getFontColor(focus));
                Rectangle rect = state ? this.getOnField(context) : this.getOffField(context);
                context.getInterface().fillRect(rect, color, color, color, color);
                rect = context.getRect();
                rect = new Rectangle(rect.x + rect.width - 2 * rect.height + 3 * RainbowTheme.this.padding, rect.y + RainbowTheme.this.padding, 2 * rect.height - 4 * RainbowTheme.this.padding, rect.height - 2 * RainbowTheme.this.padding);
                ITheme.drawRect(context.getInterface(), rect, color);
            }
            
            public int getDefaultHeight() {
                return RainbowTheme.this.getBaseHeight();
            }
            
            public Rectangle getOnField(final Context context) {
                final Rectangle rect = context.getRect();
                return new Rectangle(rect.x + rect.width - rect.height + RainbowTheme.this.padding, rect.y + RainbowTheme.this.padding, rect.height - 2 * RainbowTheme.this.padding, rect.height - 2 * RainbowTheme.this.padding);
            }
            
            public Rectangle getOffField(final Context context) {
                final Rectangle rect = context.getRect();
                return new Rectangle(rect.x + rect.width - 2 * rect.height + 3 * RainbowTheme.this.padding, rect.y + RainbowTheme.this.padding, rect.height - 2 * RainbowTheme.this.padding, rect.height - 2 * RainbowTheme.this.padding);
            }
        };
    }
    
    public ISwitchRenderer<String> getCycleSwitchRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (ISwitchRenderer<String>)new ISwitchRenderer<String>() {
            public void renderButton(final Context context, final String title, final boolean focus, final String state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                if (graphicalLevel == 0 || RainbowTheme.this.buttonRainbow.isOn()) {
                    RainbowTheme.this.renderRainbowRect(context.getRect(), context, effFocus);
                }
                Context subContext = new Context(context, context.getSize().width - 2 * context.getSize().height, new Point(0, 0), true, true);
                subContext.setHeight(context.getSize().height);
                RainbowTheme.this.renderOverlay(subContext);
                final Color textColor = RainbowTheme.this.getFontColor(effFocus);
                context.getInterface().drawString(new Point(context.getPos().x + RainbowTheme.this.padding, context.getPos().y + RainbowTheme.this.padding), RainbowTheme.this.height, title + RainbowTheme.this.separator + state, textColor);
                Rectangle rect = this.getOnField(context);
                subContext = new Context(context, rect.width, new Point(rect.x - context.getPos().x, 0), true, true);
                subContext.setHeight(rect.height);
                RainbowTheme.this.getSmallButtonRenderer(5, logicalLevel, graphicalLevel, container).renderButton(subContext, (String)null, effFocus, (Object)null);
                rect = this.getOffField(context);
                subContext = new Context(context, rect.width, new Point(rect.x - context.getPos().x, 0), true, true);
                subContext.setHeight(rect.height);
                RainbowTheme.this.getSmallButtonRenderer(4, logicalLevel, graphicalLevel, false).renderButton(subContext, (String)null, effFocus, (Object)null);
            }
            
            public int getDefaultHeight() {
                return RainbowTheme.this.getBaseHeight();
            }
            
            public Rectangle getOnField(final Context context) {
                final Rectangle rect = context.getRect();
                return new Rectangle(rect.x + rect.width - rect.height, rect.y, rect.height, rect.height);
            }
            
            public Rectangle getOffField(final Context context) {
                final Rectangle rect = context.getRect();
                return new Rectangle(rect.x + rect.width - 2 * rect.height, rect.y, rect.height, rect.height);
            }
        };
    }
    
    public IColorPickerRenderer getColorPickerRenderer() {
        return (IColorPickerRenderer)new StandardColorPicker() {
            @Override
            public int getPadding() {
                return RainbowTheme.this.padding;
            }
            
            @Override
            public int getBaseHeight() {
                return RainbowTheme.this.getBaseHeight();
            }
        };
    }
    
    public int getBaseHeight() {
        return this.height + 2 * this.padding;
    }
    
    public Color getMainColor(final boolean focus, final boolean active) {
        if (active) {
            return this.scheme.getColor("Rainbow Color");
        }
        return this.scheme.getColor("Background Color");
    }
    
    public Color getBackgroundColor(final boolean focus) {
        return this.scheme.getColor("Background Color");
    }
    
    public Color getFontColor(final boolean focus) {
        return this.scheme.getColor("Font Color");
    }
}
