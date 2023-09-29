//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;
import com.lukflug.panelstudio.setting.*;

public class ClearTheme extends ThemeBase
{
    protected IBoolean gradient;
    protected int height;
    protected int padding;
    protected int border;
    protected String separator;
    
    public ClearTheme(final IColorScheme scheme, final IBoolean gradient, final int height, final int padding, final int border, final String separator) {
        super(scheme);
        this.gradient = gradient;
        this.height = height;
        this.padding = padding;
        this.border = border;
        this.separator = separator;
        scheme.createSetting(this, "Title Color", "The color for panel titles.", false, true, new Color(90, 145, 240), false);
        scheme.createSetting(this, "Enabled Color", "The main color for enabled components.", false, true, new Color(90, 145, 240), false);
        scheme.createSetting(this, "Disabled Color", "The main color for disabled switches.", false, true, new Color(64, 64, 64), false);
        scheme.createSetting(this, "Background Color", "The background color.", true, true, new Color(195, 195, 195, 150), false);
        scheme.createSetting(this, "Font Color", "The main color for text.", false, true, new Color(255, 255, 255), false);
        scheme.createSetting(this, "Scroll Bar Color", "The color for the scroll bar.", false, true, new Color(90, 145, 240), false);
        scheme.createSetting(this, "Highlight Color", "The color for highlighted text.", false, true, new Color(0, 0, 255), false);
    }
    
    protected void renderOverlay(final Context context) {
        final Color color = context.isHovered() ? new Color(0, 0, 0, 64) : new Color(0, 0, 0, 0);
        context.getInterface().fillRect(context.getRect(), color, color, color, color);
    }
    
    protected void renderBackground(final Context context, final boolean focus, final int graphicalLevel) {
        if (graphicalLevel == 0) {
            final Color color = this.getBackgroundColor(focus);
            context.getInterface().fillRect(context.getRect(), color, color, color, color);
        }
    }
    
    protected void renderSmallButton(final Context context, final String title, final int symbol, final boolean focus) {
        final Point[] points = new Point[3];
        final int padding = (context.getSize().height <= 8) ? 2 : 4;
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
                points[1] = new Point(rect.x + rect.width, rect.y + rect.height);
                points[0] = new Point(rect.x, rect.y + rect.height / 2);
                break;
            }
            case 5: {
                if (rect.height % 2 == 1) {
                    final Rectangle rectangle5 = rect;
                    --rectangle5.height;
                }
                points[0] = new Point(rect.x, rect.y);
                points[1] = new Point(rect.x, rect.y + rect.height);
                points[2] = new Point(rect.x + rect.width, rect.y + rect.height / 2);
                break;
            }
            case 6: {
                if (rect.width % 2 == 1) {
                    final Rectangle rectangle6 = rect;
                    --rectangle6.width;
                }
                points[0] = new Point(rect.x, rect.y + rect.height);
                points[1] = new Point(rect.x + rect.width, rect.y + rect.height);
                points[2] = new Point(rect.x + rect.width / 2, rect.y);
                break;
            }
            case 7: {
                if (rect.width % 2 == 1) {
                    final Rectangle rectangle7 = rect;
                    --rectangle7.width;
                }
                points[2] = new Point(rect.x, rect.y);
                points[1] = new Point(rect.x + rect.width, rect.y);
                points[0] = new Point(rect.x + rect.width / 2, rect.y + rect.height);
                break;
            }
        }
        if (symbol >= 4 && symbol <= 7) {
            context.getInterface().fillTriangle(points[0], points[1], points[2], color, color, color);
        }
        if (title != null) {
            context.getInterface().drawString(new Point(context.getPos().x + ((symbol == 0) ? padding : context.getSize().height), context.getPos().y + padding), this.height, title, this.getFontColor(focus));
        }
    }
    
    @Override
    public IDescriptionRenderer getDescriptionRenderer() {
        return new IDescriptionRenderer() {
            @Override
            public void renderDescription(final IInterface inter, final Point pos, final String text) {
                final Rectangle rect = new Rectangle(pos, new Dimension(inter.getFontWidth(ClearTheme.this.height, text) + 2, ClearTheme.this.height + 2));
                final Color color = ClearTheme.this.getBackgroundColor(true);
                inter.fillRect(rect, color, color, color, color);
                inter.drawString(new Point(pos.x + 1, pos.y + 1), ClearTheme.this.height, text, ClearTheme.this.getFontColor(true));
            }
        };
    }
    
    @Override
    public IContainerRenderer getContainerRenderer(final int logicalLevel, final int graphicalLevel, final boolean horizontal) {
        return new IContainerRenderer() {
            @Override
            public void renderBackground(final Context context, final boolean focus) {
                ClearTheme.this.renderBackground(context, focus, graphicalLevel);
            }
            
            @Override
            public int getBorder() {
                return horizontal ? 0 : ClearTheme.this.border;
            }
            
            @Override
            public int getTop() {
                return horizontal ? 0 : ClearTheme.this.border;
            }
        };
    }
    
    @Override
    public <T> IPanelRenderer<T> getPanelRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        return new IPanelRenderer<T>() {
            @Override
            public void renderPanelOverlay(final Context context, final boolean focus, final T state, final boolean open) {
            }
            
            @Override
            public void renderTitleOverlay(final Context context, final boolean focus, final T state, final boolean open) {
                if (graphicalLevel > 0) {
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
                        ClearTheme.this.renderSmallButton(subContext, null, 7, focus);
                    }
                    else {
                        ClearTheme.this.renderSmallButton(subContext, null, 5, focus);
                    }
                }
            }
        };
    }
    
    @Override
    public <T> IScrollBarRenderer<T> getScrollBarRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        return new IScrollBarRenderer<T>() {
            @Override
            public int renderScrollBar(final Context context, final boolean focus, final T state, final boolean horizontal, final int height, final int position) {
                ClearTheme.this.renderBackground(context, focus, graphicalLevel);
                final Color color = ITheme.combineColors(ClearTheme.this.scheme.getColor("Scroll Bar Color"), ClearTheme.this.getBackgroundColor(focus));
                if (horizontal) {
                    final int a = (int)(position / (double)height * context.getSize().width);
                    final int b = (int)((position + context.getSize().width) / (double)height * context.getSize().width);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x + a + 1, context.getPos().y + 1, b - a - 2, 2), color, color, color, color);
                    context.getInterface().drawRect(new Rectangle(context.getPos().x + a + 1, context.getPos().y + 1, b - a - 2, 2), color, color, color, color);
                }
                else {
                    final int a = (int)(position / (double)height * context.getSize().height);
                    final int b = (int)((position + context.getSize().height) / (double)height * context.getSize().height);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x + 1, context.getPos().y + a + 1, 2, b - a - 2), color, color, color, color);
                    context.getInterface().drawRect(new Rectangle(context.getPos().x + 1, context.getPos().y + a + 1, 2, b - a - 2), color, color, color, color);
                }
                if (horizontal) {
                    return (int)((context.getInterface().getMouse().x - context.getPos().x) * height / (double)context.getSize().width - context.getSize().width / 2.0);
                }
                return (int)((context.getInterface().getMouse().y - context.getPos().y) * height / (double)context.getSize().height - context.getSize().height / 2.0);
            }
            
            @Override
            public int getThickness() {
                return 4;
            }
        };
    }
    
    @Override
    public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new IEmptySpaceRenderer<T>() {
            @Override
            public void renderSpace(final Context context, final boolean focus, final T state) {
                ClearTheme.this.renderBackground(context, focus, graphicalLevel);
            }
        };
    }
    
    @Override
    public <T> IButtonRenderer<T> getButtonRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new IButtonRenderer<T>() {
            @Override
            public void renderButton(final Context context, final String title, final boolean focus, final T state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                if (container && graphicalLevel <= 0) {
                    final Color colorA = ClearTheme.this.getColor(ClearTheme.this.scheme.getColor("Title Color"));
                    final Color colorB = ClearTheme.this.gradient.isOn() ? ClearTheme.this.getBackgroundColor(effFocus) : colorA;
                    context.getInterface().fillRect(context.getRect(), colorA, colorA, colorB, colorB);
                }
                else {
                    ClearTheme.this.renderBackground(context, effFocus, graphicalLevel);
                }
                Color color = ClearTheme.this.getFontColor(effFocus);
                if (type == Boolean.class && (boolean)state) {
                    color = ClearTheme.this.getMainColor(effFocus, true);
                }
                else if (type == Color.class) {
                    color = (Color)state;
                }
                if (graphicalLevel > 0) {
                    ClearTheme.this.renderOverlay(context);
                }
                if (type == String.class) {
                    context.getInterface().drawString(new Point(context.getPos().x + ClearTheme.this.padding, context.getPos().y + ClearTheme.this.padding), ClearTheme.this.height, title + ClearTheme.this.separator + state, color);
                }
                else {
                    context.getInterface().drawString(new Point(context.getPos().x + ClearTheme.this.padding, context.getPos().y + ClearTheme.this.padding), ClearTheme.this.height, title, color);
                }
            }
            
            @Override
            public int getDefaultHeight() {
                return ClearTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public IButtonRenderer<Void> getSmallButtonRenderer(final int symbol, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new IButtonRenderer<Void>() {
            @Override
            public void renderButton(final Context context, final String title, final boolean focus, final Void state) {
                ClearTheme.this.renderBackground(context, focus, graphicalLevel);
                ClearTheme.this.renderOverlay(context);
                if (!container || logicalLevel <= 0) {
                    ClearTheme.this.renderSmallButton(context, title, symbol, focus);
                }
            }
            
            @Override
            public int getDefaultHeight() {
                return ClearTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public IButtonRenderer<String> getKeybindRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new IButtonRenderer<String>() {
            @Override
            public void renderButton(final Context context, final String title, final boolean focus, final String state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                if (container && graphicalLevel <= 0) {
                    final Color colorA = ClearTheme.this.getColor(ClearTheme.this.scheme.getColor("Title Color"));
                    final Color colorB = ClearTheme.this.gradient.isOn() ? ClearTheme.this.getBackgroundColor(effFocus) : colorA;
                    context.getInterface().fillRect(context.getRect(), colorA, colorA, colorB, colorB);
                }
                else {
                    ClearTheme.this.renderBackground(context, effFocus, graphicalLevel);
                }
                Color color = ClearTheme.this.getFontColor(effFocus);
                if (effFocus) {
                    color = ClearTheme.this.getMainColor(effFocus, true);
                }
                ClearTheme.this.renderOverlay(context);
                context.getInterface().drawString(new Point(context.getPos().x + ClearTheme.this.padding, context.getPos().y + ClearTheme.this.padding), ClearTheme.this.height, title + ClearTheme.this.separator + (focus ? "..." : state), color);
            }
            
            @Override
            public int getDefaultHeight() {
                return ClearTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public ISliderRenderer getSliderRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new ISliderRenderer() {
            @Override
            public void renderSlider(final Context context, final String title, final String state, final boolean focus, final double value) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                ClearTheme.this.renderBackground(context, effFocus, graphicalLevel);
                final Color color = ClearTheme.this.getFontColor(effFocus);
                final Color colorA = ClearTheme.this.getMainColor(effFocus, true);
                final Rectangle rect = this.getSlideArea(context, title, state);
                final int divider = (int)(rect.width * value);
                context.getInterface().fillRect(new Rectangle(rect.x, rect.y, divider, rect.height), colorA, colorA, colorA, colorA);
                ClearTheme.this.renderOverlay(context);
                context.getInterface().drawString(new Point(context.getPos().x + ClearTheme.this.padding, context.getPos().y + ClearTheme.this.padding), ClearTheme.this.height, title + ClearTheme.this.separator + state, color);
            }
            
            @Override
            public int getDefaultHeight() {
                return ClearTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public IRadioRenderer getRadioRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new IRadioRenderer() {
            @Override
            public void renderItem(final Context context, final ILabeled[] items, final boolean focus, final int target, final double state, final boolean horizontal) {
                ClearTheme.this.renderBackground(context, focus, graphicalLevel);
                for (int i = 0; i < items.length; ++i) {
                    final Rectangle rect = this.getItemRect(context, items, i, horizontal);
                    final Context subContext = new Context(context.getInterface(), rect.width, rect.getLocation(), context.hasFocus(), context.onTop());
                    subContext.setHeight(rect.height);
                    ClearTheme.this.renderOverlay(subContext);
                    context.getInterface().drawString(new Point(rect.x + ClearTheme.this.padding, rect.y + ClearTheme.this.padding), ClearTheme.this.height, items[i].getDisplayName(), (i == target) ? ClearTheme.this.getMainColor(focus, true) : ClearTheme.this.getFontColor(focus));
                }
            }
            
            @Override
            public int getDefaultHeight(final ILabeled[] items, final boolean horizontal) {
                return (horizontal ? 1 : items.length) * ClearTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public IResizeBorderRenderer getResizeRenderer() {
        return new IResizeBorderRenderer() {
            @Override
            public void drawBorder(final Context context, final boolean focus) {
                final Color color = ClearTheme.this.getBackgroundColor(focus);
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
                ClearTheme.this.renderBackground(context, effFocus, graphicalLevel);
                final Color textColor = ClearTheme.this.getFontColor(effFocus);
                final Color highlightColor = ClearTheme.this.scheme.getColor("Highlight Color");
                final Rectangle rect = this.getTextArea(context, title);
                final int strlen = context.getInterface().getFontWidth(ClearTheme.this.height, content.substring(0, position));
                context.getInterface().fillRect(rect, new Color(0, 0, 0, 64), new Color(0, 0, 0, 64), new Color(0, 0, 0, 64), new Color(0, 0, 0, 64));
                if (boxPosition < position) {
                    int minPosition;
                    for (minPosition = boxPosition; minPosition < position && context.getInterface().getFontWidth(ClearTheme.this.height, content.substring(0, minPosition)) + rect.width - ClearTheme.this.padding < strlen; ++minPosition) {}
                    if (boxPosition < minPosition) {
                        boxPosition = minPosition;
                    }
                }
                else if (boxPosition > position) {
                    boxPosition = position - 1;
                }
                int maxPosition;
                for (maxPosition = content.length(); maxPosition > 0; --maxPosition) {
                    if (context.getInterface().getFontWidth(ClearTheme.this.height, content.substring(maxPosition)) >= rect.width - ClearTheme.this.padding) {
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
                final int offset = context.getInterface().getFontWidth(ClearTheme.this.height, content.substring(0, boxPosition));
                final int x1 = rect.x + ClearTheme.this.padding / 2 - offset + strlen;
                int x2 = rect.x + ClearTheme.this.padding / 2 - offset;
                if (position < content.length()) {
                    x2 += context.getInterface().getFontWidth(ClearTheme.this.height, content.substring(0, position + 1));
                }
                else {
                    x2 += context.getInterface().getFontWidth(ClearTheme.this.height, content + "X");
                }
                ClearTheme.this.renderOverlay(context);
                context.getInterface().drawString(new Point(context.getPos().x + ClearTheme.this.padding, context.getPos().y + ClearTheme.this.padding / 2), ClearTheme.this.height, title + ClearTheme.this.separator, textColor);
                context.getInterface().window(rect);
                if (select >= 0) {
                    final int x3 = rect.x + ClearTheme.this.padding / 2 - offset + context.getInterface().getFontWidth(ClearTheme.this.height, content.substring(0, select));
                    context.getInterface().fillRect(new Rectangle(Math.min(x1, x3), rect.y + ClearTheme.this.padding / 2, Math.abs(x3 - x1), ClearTheme.this.height), highlightColor, highlightColor, highlightColor, highlightColor);
                }
                context.getInterface().drawString(new Point(rect.x + ClearTheme.this.padding / 2 - offset, rect.y + ClearTheme.this.padding / 2), ClearTheme.this.height, content, textColor);
                if (System.currentTimeMillis() / 500L % 2L == 0L && focus) {
                    if (insertMode) {
                        context.getInterface().fillRect(new Rectangle(x1, rect.y + ClearTheme.this.padding / 2 + ClearTheme.this.height, x2 - x1, 1), textColor, textColor, textColor, textColor);
                    }
                    else {
                        context.getInterface().fillRect(new Rectangle(x1, rect.y + ClearTheme.this.padding / 2, 1, ClearTheme.this.height), textColor, textColor, textColor, textColor);
                    }
                }
                context.getInterface().restore();
                return boxPosition;
            }
            
            @Override
            public int getDefaultHeight() {
                int height = ClearTheme.this.getBaseHeight() - ClearTheme.this.padding;
                if (height % 2 == 1) {
                    ++height;
                }
                return height;
            }
            
            @Override
            public Rectangle getTextArea(final Context context, final String title) {
                final Rectangle rect = context.getRect();
                final int length = ClearTheme.this.padding + context.getInterface().getFontWidth(ClearTheme.this.height, title + ClearTheme.this.separator);
                return new Rectangle(rect.x + length, rect.y, rect.width - length, rect.height);
            }
            
            @Override
            public int transformToCharPos(final Context context, final String title, final String content, final int boxPosition) {
                final Rectangle rect = this.getTextArea(context, title);
                final Point mouse = context.getInterface().getMouse();
                final int offset = context.getInterface().getFontWidth(ClearTheme.this.height, content.substring(0, boxPosition));
                if (rect.contains(mouse)) {
                    for (int i = 1; i <= content.length(); ++i) {
                        if (rect.x + ClearTheme.this.padding / 2 - offset + context.getInterface().getFontWidth(ClearTheme.this.height, content.substring(0, i)) > mouse.x) {
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
            @Override
            public void renderButton(final Context context, final String title, final boolean focus, final Boolean state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                ClearTheme.this.renderBackground(context, effFocus, graphicalLevel);
                ClearTheme.this.renderOverlay(context);
                context.getInterface().drawString(new Point(context.getPos().x + ClearTheme.this.padding, context.getPos().y + ClearTheme.this.padding), ClearTheme.this.height, title + ClearTheme.this.separator + (state ? "On" : "Off"), ClearTheme.this.getFontColor(focus));
                final Color color = state ? ClearTheme.this.scheme.getColor("Enabled Color") : ClearTheme.this.scheme.getColor("Disabled Color");
                final Color fillColor = ITheme.combineColors(color, ClearTheme.this.getBackgroundColor(effFocus));
                Rectangle rect = state ? this.getOnField(context) : this.getOffField(context);
                context.getInterface().fillRect(rect, fillColor, fillColor, fillColor, fillColor);
                rect = context.getRect();
                rect = new Rectangle(rect.x + rect.width - 2 * rect.height + 3 * ClearTheme.this.padding, rect.y + ClearTheme.this.padding, 2 * rect.height - 4 * ClearTheme.this.padding, rect.height - 2 * ClearTheme.this.padding);
                context.getInterface().drawRect(rect, color, color, color, color);
            }
            
            @Override
            public int getDefaultHeight() {
                return ClearTheme.this.getBaseHeight();
            }
            
            @Override
            public Rectangle getOnField(final Context context) {
                final Rectangle rect = context.getRect();
                return new Rectangle(rect.x + rect.width - rect.height + ClearTheme.this.padding, rect.y + ClearTheme.this.padding, rect.height - 2 * ClearTheme.this.padding, rect.height - 2 * ClearTheme.this.padding);
            }
            
            @Override
            public Rectangle getOffField(final Context context) {
                final Rectangle rect = context.getRect();
                return new Rectangle(rect.x + rect.width - 2 * rect.height + 3 * ClearTheme.this.padding, rect.y + ClearTheme.this.padding, rect.height - 2 * ClearTheme.this.padding, rect.height - 2 * ClearTheme.this.padding);
            }
        };
    }
    
    @Override
    public ISwitchRenderer<String> getCycleSwitchRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new ISwitchRenderer<String>() {
            @Override
            public void renderButton(final Context context, final String title, final boolean focus, final String state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                ClearTheme.this.renderBackground(context, effFocus, graphicalLevel);
                Context subContext = new Context(context, context.getSize().width - 2 * context.getSize().height, new Point(0, 0), true, true);
                subContext.setHeight(context.getSize().height);
                ClearTheme.this.renderOverlay(subContext);
                final Color textColor = ClearTheme.this.getFontColor(effFocus);
                context.getInterface().drawString(new Point(context.getPos().x + ClearTheme.this.padding, context.getPos().y + ClearTheme.this.padding), ClearTheme.this.height, title + ClearTheme.this.separator + state, textColor);
                Rectangle rect = this.getOnField(context);
                subContext = new Context(context, rect.width, new Point(rect.x - context.getPos().x, 0), true, true);
                subContext.setHeight(rect.height);
                ClearTheme.this.getSmallButtonRenderer(5, logicalLevel, graphicalLevel, container).renderButton(subContext, null, effFocus, null);
                rect = this.getOffField(context);
                subContext = new Context(context, rect.width, new Point(rect.x - context.getPos().x, 0), true, true);
                subContext.setHeight(rect.height);
                ClearTheme.this.getSmallButtonRenderer(4, logicalLevel, graphicalLevel, false).renderButton(subContext, null, effFocus, null);
            }
            
            @Override
            public int getDefaultHeight() {
                return ClearTheme.this.getBaseHeight();
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
        return new StandardColorPicker() {
            @Override
            public int getPadding() {
                return ClearTheme.this.padding;
            }
            
            @Override
            public int getBaseHeight() {
                return ClearTheme.this.getBaseHeight();
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
            return this.getColor(this.scheme.getColor("Enabled Color"));
        }
        return new Color(0, 0, 0, 0);
    }
    
    @Override
    public Color getBackgroundColor(final boolean focus) {
        return this.scheme.getColor("Background Color");
    }
    
    @Override
    public Color getFontColor(final boolean focus) {
        return this.scheme.getColor("Font Color");
    }
}
