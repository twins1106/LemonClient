//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;
import com.lukflug.panelstudio.setting.*;

public class GameSenseTheme extends ThemeBase
{
    protected int height;
    protected int padding;
    protected int scroll;
    protected String separator;
    
    public GameSenseTheme(final IColorScheme scheme, final int height, final int padding, final int scroll, final String separator) {
        super(scheme);
        this.height = height;
        this.padding = padding;
        this.scroll = scroll;
        this.separator = separator;
        scheme.createSetting(this, "Title Color", "The color for panel titles.", false, true, new Color(255, 0, 0), false);
        scheme.createSetting(this, "Outline Color", "The color for panel outlines.", false, true, new Color(255, 0, 0), false);
        scheme.createSetting(this, "Enabled Color", "The main color for enabled components.", true, true, new Color(255, 0, 0, 150), false);
        scheme.createSetting(this, "Disabled Color", "The main color for disabled modules.", false, true, new Color(0, 0, 0), false);
        scheme.createSetting(this, "Settings Color", "The background color for settings.", false, true, new Color(30, 30, 30), false);
        scheme.createSetting(this, "Font Color", "The main color for text.", false, true, new Color(255, 255, 255), false);
        scheme.createSetting(this, "Highlight Color", "The color for highlighted text.", false, true, new Color(0, 0, 255), false);
    }
    
    protected void fillBaseRect(final Context context, final boolean focus, final boolean active, final int logicalLevel, final int graphicalLevel, final Color colorState) {
        Color color = this.getMainColor(focus, active);
        if (logicalLevel > 1 && !active) {
            color = this.getBackgroundColor(focus);
        }
        else if (graphicalLevel <= 0 && active) {
            color = ITheme.combineColors(this.getColor(this.scheme.getColor("Title Color")), this.scheme.getColor("Enabled Color"));
        }
        if (colorState != null) {
            color = ITheme.combineColors(colorState, this.scheme.getColor("Enabled Color"));
        }
        context.getInterface().fillRect(context.getRect(), color, color, color, color);
    }
    
    protected void renderOverlay(final Context context) {
        final Color color = context.isHovered() ? new Color(255, 255, 255, 64) : new Color(0, 0, 0, 0);
        context.getInterface().fillRect(context.getRect(), color, color, color, color);
    }
    
    @Override
    public IDescriptionRenderer getDescriptionRenderer() {
        return new IDescriptionRenderer() {
            @Override
            public void renderDescription(final IInterface inter, final Point pos, final String text) {
                final Rectangle rect = new Rectangle(pos, new Dimension(inter.getFontWidth(GameSenseTheme.this.height, text) + 4, GameSenseTheme.this.height + 4));
                final Color color = GameSenseTheme.this.getMainColor(true, false);
                inter.fillRect(rect, color, color, color, color);
                inter.drawString(new Point(pos.x + 2, pos.y + 2), GameSenseTheme.this.height, text, GameSenseTheme.this.getFontColor(true));
                ITheme.drawRect(inter, rect, GameSenseTheme.this.scheme.getColor("Outline Color"));
            }
        };
    }
    
    @Override
    public IContainerRenderer getContainerRenderer(final int logicalLevel, final int graphicalLevel, final boolean horizontal) {
        return new IContainerRenderer() {
            @Override
            public void renderBackground(final Context context, final boolean focus) {
                if (graphicalLevel > 0) {
                    final Color color = GameSenseTheme.this.scheme.getColor("Outline Color");
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y, context.getSize().width, 1), color, color, color, color);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y + context.getSize().height - 1, context.getSize().width, 1), color, color, color, color);
                }
            }
            
            @Override
            public int getTop() {
                return (graphicalLevel > 0) ? 1 : 0;
            }
            
            @Override
            public int getBottom() {
                return (graphicalLevel > 0) ? 1 : 0;
            }
        };
    }
    
    @Override
    public <T> IPanelRenderer<T> getPanelRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        return new IPanelRenderer<T>() {
            @Override
            public int getLeft() {
                return (graphicalLevel == 0) ? 1 : 0;
            }
            
            @Override
            public int getRight() {
                return (graphicalLevel == 0) ? 1 : 0;
            }
            
            @Override
            public void renderPanelOverlay(final Context context, final boolean focus, final T state, final boolean open) {
                if (graphicalLevel == 0) {
                    ITheme.drawRect(context.getInterface(), context.getRect(), GameSenseTheme.this.scheme.getColor("Outline Color"));
                }
            }
            
            @Override
            public void renderTitleOverlay(final Context context, final boolean focus, final T state, final boolean open) {
            }
        };
    }
    
    @Override
    public <T> IScrollBarRenderer<T> getScrollBarRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        return new IScrollBarRenderer<T>() {
            @Override
            public int renderScrollBar(final Context context, final boolean focus, final T state, final boolean horizontal, final int height, final int position) {
                final Color activecolor = GameSenseTheme.this.getMainColor(focus, true);
                final Color inactivecolor = GameSenseTheme.this.getMainColor(focus, false);
                if (horizontal) {
                    final int a = (int)(position / (double)height * context.getSize().width);
                    final int b = (int)((position + context.getSize().width) / (double)height * context.getSize().width);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y, a, context.getSize().height), inactivecolor, inactivecolor, inactivecolor, inactivecolor);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x + a, context.getPos().y, b - a, context.getSize().height), activecolor, activecolor, activecolor, activecolor);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x + b, context.getPos().y, context.getSize().width - b, context.getSize().height), inactivecolor, inactivecolor, inactivecolor, inactivecolor);
                }
                else {
                    final int a = (int)(position / (double)height * context.getSize().height);
                    final int b = (int)((position + context.getSize().height) / (double)height * context.getSize().height);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y, context.getSize().width, a), inactivecolor, inactivecolor, inactivecolor, inactivecolor);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y + a, context.getSize().width, b - a), activecolor, activecolor, activecolor, activecolor);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y + b, context.getSize().width, context.getSize().height - b), inactivecolor, inactivecolor, inactivecolor, inactivecolor);
                }
                final Color bordercolor = GameSenseTheme.this.scheme.getColor("Outline Color");
                if (horizontal) {
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y, context.getSize().width, 1), bordercolor, bordercolor, bordercolor, bordercolor);
                }
                else {
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y, 1, context.getSize().height), bordercolor, bordercolor, bordercolor, bordercolor);
                }
                if (horizontal) {
                    return (int)((context.getInterface().getMouse().x - context.getPos().x) * height / (double)context.getSize().width - context.getSize().width / 2.0);
                }
                return (int)((context.getInterface().getMouse().y - context.getPos().y) * height / (double)context.getSize().height - context.getSize().height / 2.0);
            }
            
            @Override
            public int getThickness() {
                return GameSenseTheme.this.scroll;
            }
        };
    }
    
    @Override
    public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        Color color;
        return (context, focus, state) -> {
            if (container) {
                if (logicalLevel > 0) {
                    color = this.getBackgroundColor(focus);
                }
                else {
                    color = this.getMainColor(focus, false);
                }
            }
            else {
                color = this.scheme.getColor("Outline Color");
            }
            context.getInterface().fillRect(context.getRect(), color, color, color, color);
        };
    }
    
    @Override
    public <T> IButtonRenderer<T> getButtonRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new IButtonRenderer<T>() {
            @Override
            public void renderButton(final Context context, final String title, final boolean focus, final T state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                if (type == Boolean.class) {
                    GameSenseTheme.this.fillBaseRect(context, effFocus, (boolean)state, logicalLevel, graphicalLevel, null);
                }
                else if (type == Color.class) {
                    GameSenseTheme.this.fillBaseRect(context, effFocus, graphicalLevel <= 0, logicalLevel, graphicalLevel, (Color)state);
                }
                else {
                    GameSenseTheme.this.fillBaseRect(context, effFocus, graphicalLevel <= 0, logicalLevel, graphicalLevel, null);
                }
                if (graphicalLevel <= 0 && container) {
                    final Color color = GameSenseTheme.this.scheme.getColor("Outline Color");
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y + context.getSize().height - 1, context.getSize().width, 1), color, color, color, color);
                }
                GameSenseTheme.this.renderOverlay(context);
                if (type == String.class) {
                    context.getInterface().drawString(new Point(context.getPos().x + GameSenseTheme.this.padding, context.getPos().y + GameSenseTheme.this.padding), GameSenseTheme.this.height, title + GameSenseTheme.this.separator + state, GameSenseTheme.this.getFontColor(focus));
                }
                else {
                    context.getInterface().drawString(new Point(context.getPos().x + GameSenseTheme.this.padding, context.getPos().y + GameSenseTheme.this.padding), GameSenseTheme.this.height, title, GameSenseTheme.this.getFontColor(focus));
                }
            }
            
            @Override
            public int getDefaultHeight() {
                return GameSenseTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public IButtonRenderer<Void> getSmallButtonRenderer(final int symbol, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new IButtonRenderer<Void>() {
            @Override
            public void renderButton(final Context context, final String title, final boolean focus, final Void state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                GameSenseTheme.this.fillBaseRect(context, effFocus, true, logicalLevel, graphicalLevel, null);
                GameSenseTheme.this.renderOverlay(context);
                final Point[] points = new Point[3];
                final int padding = (context.getSize().height <= 2 * GameSenseTheme.this.padding) ? 2 : GameSenseTheme.this.padding;
                final Rectangle rect = new Rectangle(context.getPos().x + padding / 2, context.getPos().y + padding / 2, context.getSize().height - 2 * (padding / 2), context.getSize().height - 2 * (padding / 2));
                if (title == null) {
                    final Rectangle rectangle = rect;
                    rectangle.x += context.getSize().width / 2 - context.getSize().height / 2;
                }
                final Color color = GameSenseTheme.this.getFontColor(effFocus);
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
                    context.getInterface().drawString(new Point(context.getPos().x + ((symbol == 0) ? padding : context.getSize().height), context.getPos().y + padding), GameSenseTheme.this.height, title, GameSenseTheme.this.getFontColor(focus));
                }
            }
            
            @Override
            public int getDefaultHeight() {
                return GameSenseTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public IButtonRenderer<String> getKeybindRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new IButtonRenderer<String>() {
            @Override
            public void renderButton(final Context context, final String title, final boolean focus, final String state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                GameSenseTheme.this.fillBaseRect(context, effFocus, effFocus, logicalLevel, graphicalLevel, null);
                GameSenseTheme.this.renderOverlay(context);
                context.getInterface().drawString(new Point(context.getPos().x + GameSenseTheme.this.padding, context.getPos().y + GameSenseTheme.this.padding), GameSenseTheme.this.height, title + GameSenseTheme.this.separator + (focus ? "..." : state), GameSenseTheme.this.getFontColor(focus));
            }
            
            @Override
            public int getDefaultHeight() {
                return GameSenseTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public ISliderRenderer getSliderRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new ISliderRenderer() {
            @Override
            public void renderSlider(final Context context, final String title, final String state, final boolean focus, final double value) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                final Color colorA = GameSenseTheme.this.getMainColor(effFocus, true);
                final Color colorB = GameSenseTheme.this.getBackgroundColor(effFocus);
                final Rectangle rect = this.getSlideArea(context, title, state);
                final int divider = (int)(rect.width * value);
                context.getInterface().fillRect(new Rectangle(rect.x, rect.y, divider, rect.height), colorA, colorA, colorA, colorA);
                context.getInterface().fillRect(new Rectangle(rect.x + divider, rect.y, rect.width - divider, rect.height), colorB, colorB, colorB, colorB);
                GameSenseTheme.this.renderOverlay(context);
                context.getInterface().drawString(new Point(context.getPos().x + GameSenseTheme.this.padding, context.getPos().y + GameSenseTheme.this.padding), GameSenseTheme.this.height, title + GameSenseTheme.this.separator + state, GameSenseTheme.this.getFontColor(focus));
            }
            
            @Override
            public int getDefaultHeight() {
                return GameSenseTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public IRadioRenderer getRadioRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new IRadioRenderer() {
            @Override
            public void renderItem(final Context context, final ILabeled[] items, final boolean focus, final int target, final double state, final boolean horizontal) {
                for (int i = 0; i < items.length; ++i) {
                    final Rectangle rect = this.getItemRect(context, items, i, horizontal);
                    final Context subContext = new Context(context.getInterface(), rect.width, rect.getLocation(), context.hasFocus(), context.onTop());
                    subContext.setHeight(rect.height);
                    GameSenseTheme.this.fillBaseRect(subContext, focus, i == target, logicalLevel, graphicalLevel, null);
                    GameSenseTheme.this.renderOverlay(subContext);
                    context.getInterface().drawString(new Point(rect.x + GameSenseTheme.this.padding, rect.y + GameSenseTheme.this.padding), GameSenseTheme.this.height, items[i].getDisplayName(), GameSenseTheme.this.getFontColor(focus));
                }
            }
            
            @Override
            public int getDefaultHeight(final ILabeled[] items, final boolean horizontal) {
                return (horizontal ? 1 : items.length) * GameSenseTheme.this.getBaseHeight();
            }
        };
    }
    
    @Override
    public IResizeBorderRenderer getResizeRenderer() {
        return new IResizeBorderRenderer() {
            @Override
            public void drawBorder(final Context context, final boolean focus) {
                final Color color = ITheme.combineColors(GameSenseTheme.this.scheme.getColor("Outline Color"), GameSenseTheme.this.scheme.getColor("Enabled Color"));
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
                final Color color = focus ? GameSenseTheme.this.scheme.getColor("Outline Color") : GameSenseTheme.this.scheme.getColor("Settings Color");
                final Color textColor = GameSenseTheme.this.getFontColor(effFocus);
                final Color highlightColor = GameSenseTheme.this.scheme.getColor("Highlight Color");
                final Rectangle rect = this.getTextArea(context, title);
                final int strlen = context.getInterface().getFontWidth(GameSenseTheme.this.height, content.substring(0, position));
                if (boxPosition < position) {
                    int minPosition;
                    for (minPosition = boxPosition; minPosition < position && context.getInterface().getFontWidth(GameSenseTheme.this.height, content.substring(0, minPosition)) + rect.width - GameSenseTheme.this.padding < strlen; ++minPosition) {}
                    if (boxPosition < minPosition) {
                        boxPosition = minPosition;
                    }
                }
                else if (boxPosition > position) {
                    boxPosition = position - 1;
                }
                int maxPosition;
                for (maxPosition = content.length(); maxPosition > 0; --maxPosition) {
                    if (context.getInterface().getFontWidth(GameSenseTheme.this.height, content.substring(maxPosition)) >= rect.width - GameSenseTheme.this.padding) {
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
                final int offset = context.getInterface().getFontWidth(GameSenseTheme.this.height, content.substring(0, boxPosition));
                final int x1 = rect.x + GameSenseTheme.this.padding / 2 - offset + strlen;
                int x2 = rect.x + GameSenseTheme.this.padding / 2 - offset;
                if (position < content.length()) {
                    x2 += context.getInterface().getFontWidth(GameSenseTheme.this.height, content.substring(0, position + 1));
                }
                else {
                    x2 += context.getInterface().getFontWidth(GameSenseTheme.this.height, content + "X");
                }
                GameSenseTheme.this.fillBaseRect(context, effFocus, false, logicalLevel, graphicalLevel, null);
                GameSenseTheme.this.renderOverlay(context);
                context.getInterface().drawString(new Point(context.getRect().x + GameSenseTheme.this.padding, context.getRect().y + GameSenseTheme.this.padding / (embed ? 2 : 1)), GameSenseTheme.this.height, title + (embed ? GameSenseTheme.this.separator : ""), textColor);
                context.getInterface().window(rect);
                if (select >= 0) {
                    final int x3 = rect.x + GameSenseTheme.this.padding / 2 - offset + context.getInterface().getFontWidth(GameSenseTheme.this.height, content.substring(0, select));
                    context.getInterface().fillRect(new Rectangle(Math.min(x1, x3), rect.y + GameSenseTheme.this.padding / 2, Math.abs(x3 - x1), GameSenseTheme.this.height), highlightColor, highlightColor, highlightColor, highlightColor);
                }
                context.getInterface().drawString(new Point(rect.x + GameSenseTheme.this.padding / 2 - offset, rect.y + GameSenseTheme.this.padding / 2), GameSenseTheme.this.height, content, textColor);
                if (System.currentTimeMillis() / 500L % 2L == 0L && focus) {
                    if (insertMode) {
                        context.getInterface().fillRect(new Rectangle(x1, rect.y + GameSenseTheme.this.padding / 2 + GameSenseTheme.this.height, x2 - x1, 1), textColor, textColor, textColor, textColor);
                    }
                    else {
                        context.getInterface().fillRect(new Rectangle(x1, rect.y + GameSenseTheme.this.padding / 2, 1, GameSenseTheme.this.height), textColor, textColor, textColor, textColor);
                    }
                }
                ITheme.drawRect(context.getInterface(), rect, color);
                context.getInterface().restore();
                return boxPosition;
            }
            
            @Override
            public int getDefaultHeight() {
                if (embed) {
                    int height = GameSenseTheme.this.getBaseHeight() - GameSenseTheme.this.padding;
                    if (height % 2 == 1) {
                        ++height;
                    }
                    return height;
                }
                return 2 * GameSenseTheme.this.getBaseHeight();
            }
            
            @Override
            public Rectangle getTextArea(final Context context, final String title) {
                final Rectangle rect = context.getRect();
                if (embed) {
                    final int length = GameSenseTheme.this.padding + context.getInterface().getFontWidth(GameSenseTheme.this.height, title + GameSenseTheme.this.separator);
                    return new Rectangle(rect.x + length, rect.y, rect.width - length, rect.height);
                }
                return new Rectangle(rect.x + GameSenseTheme.this.padding, rect.y + GameSenseTheme.this.getBaseHeight(), rect.width - 2 * GameSenseTheme.this.padding, rect.height - GameSenseTheme.this.getBaseHeight() - GameSenseTheme.this.padding);
            }
            
            @Override
            public int transformToCharPos(final Context context, final String title, final String content, final int boxPosition) {
                final Rectangle rect = this.getTextArea(context, title);
                final Point mouse = context.getInterface().getMouse();
                final int offset = context.getInterface().getFontWidth(GameSenseTheme.this.height, content.substring(0, boxPosition));
                if (rect.contains(mouse)) {
                    for (int i = 1; i <= content.length(); ++i) {
                        if (rect.x + GameSenseTheme.this.padding / 2 - offset + context.getInterface().getFontWidth(GameSenseTheme.this.height, content.substring(0, i)) > mouse.x) {
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
                GameSenseTheme.this.fillBaseRect(context, effFocus, false, logicalLevel, graphicalLevel, null);
                final Color color = GameSenseTheme.this.scheme.getColor("Outline Color");
                if (graphicalLevel <= 0 && container) {
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y + context.getSize().height - 1, context.getSize().width, 1), color, color, color, color);
                }
                GameSenseTheme.this.renderOverlay(context);
                context.getInterface().drawString(new Point(context.getPos().x + GameSenseTheme.this.padding, context.getPos().y + GameSenseTheme.this.padding), GameSenseTheme.this.height, title + GameSenseTheme.this.separator + (state ? "On" : "Off"), GameSenseTheme.this.getFontColor(focus));
                final Color fillColor = GameSenseTheme.this.getMainColor(effFocus, true);
                Rectangle rect = state ? this.getOnField(context) : this.getOffField(context);
                context.getInterface().fillRect(rect, fillColor, fillColor, fillColor, fillColor);
                rect = context.getRect();
                rect = new Rectangle(rect.x + rect.width - 2 * rect.height + 3 * GameSenseTheme.this.padding, rect.y + GameSenseTheme.this.padding, 2 * rect.height - 4 * GameSenseTheme.this.padding, rect.height - 2 * GameSenseTheme.this.padding);
                ITheme.drawRect(context.getInterface(), rect, color);
            }
            
            @Override
            public int getDefaultHeight() {
                return GameSenseTheme.this.getBaseHeight();
            }
            
            @Override
            public Rectangle getOnField(final Context context) {
                final Rectangle rect = context.getRect();
                return new Rectangle(rect.x + rect.width - rect.height + GameSenseTheme.this.padding, rect.y + GameSenseTheme.this.padding, rect.height - 2 * GameSenseTheme.this.padding, rect.height - 2 * GameSenseTheme.this.padding);
            }
            
            @Override
            public Rectangle getOffField(final Context context) {
                final Rectangle rect = context.getRect();
                return new Rectangle(rect.x + rect.width - 2 * rect.height + 3 * GameSenseTheme.this.padding, rect.y + GameSenseTheme.this.padding, rect.height - 2 * GameSenseTheme.this.padding, rect.height - 2 * GameSenseTheme.this.padding);
            }
        };
    }
    
    @Override
    public ISwitchRenderer<String> getCycleSwitchRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return new ISwitchRenderer<String>() {
            @Override
            public void renderButton(final Context context, final String title, final boolean focus, final String state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                GameSenseTheme.this.fillBaseRect(context, effFocus, false, logicalLevel, graphicalLevel, null);
                final Color color = GameSenseTheme.this.scheme.getColor("Outline Color");
                if (graphicalLevel <= 0 && container) {
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y + context.getSize().height - 1, context.getSize().width, 1), color, color, color, color);
                }
                Context subContext = new Context(context, context.getSize().width - 2 * context.getSize().height, new Point(0, 0), true, true);
                subContext.setHeight(context.getSize().height);
                GameSenseTheme.this.renderOverlay(subContext);
                final Color textColor = GameSenseTheme.this.getFontColor(effFocus);
                context.getInterface().drawString(new Point(context.getPos().x + GameSenseTheme.this.padding, context.getPos().y + GameSenseTheme.this.padding), GameSenseTheme.this.height, title + GameSenseTheme.this.separator + state, textColor);
                Rectangle rect = this.getOnField(context);
                subContext = new Context(context, rect.width, new Point(rect.x - context.getPos().x, 0), true, true);
                subContext.setHeight(rect.height);
                GameSenseTheme.this.getSmallButtonRenderer(5, logicalLevel, graphicalLevel, container).renderButton(subContext, null, effFocus, null);
                rect = this.getOffField(context);
                subContext = new Context(context, rect.width, new Point(rect.x - context.getPos().x, 0), true, true);
                subContext.setHeight(rect.height);
                GameSenseTheme.this.getSmallButtonRenderer(4, logicalLevel, graphicalLevel, container).renderButton(subContext, null, effFocus, null);
            }
            
            @Override
            public int getDefaultHeight() {
                return GameSenseTheme.this.getBaseHeight();
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
                return GameSenseTheme.this.padding;
            }
            
            @Override
            public int getBaseHeight() {
                return GameSenseTheme.this.getBaseHeight();
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
            return ITheme.combineColors(this.getColor(this.scheme.getColor("Enabled Color")), this.scheme.getColor("Enabled Color"));
        }
        return ITheme.combineColors(this.scheme.getColor("Disabled Color"), this.scheme.getColor("Enabled Color"));
    }
    
    @Override
    public Color getBackgroundColor(final boolean focus) {
        return ITheme.combineColors(this.scheme.getColor("Settings Color"), this.scheme.getColor("Enabled Color"));
    }
    
    @Override
    public Color getFontColor(final boolean focus) {
        return this.scheme.getColor("Font Color");
    }
}
