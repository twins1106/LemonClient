//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import java.awt.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.setting.*;

public class Windows31Theme extends ThemeBase
{
    protected int height;
    protected int padding;
    protected int scroll;
    protected String separator;
    
    public Windows31Theme(final IColorScheme scheme, final int height, final int padding, final int scroll, final String separator) {
        super(scheme);
        this.height = height;
        this.padding = padding;
        this.separator = separator;
        this.scroll = scroll;
        scheme.createSetting((ITheme)this, "Title Color", "The color for panel titles.", false, true, new Color(0, 0, 168), false);
        scheme.createSetting((ITheme)this, "Background Color", "The color for the background.", false, true, new Color(252, 252, 252), false);
        scheme.createSetting((ITheme)this, "Button Color", "The main color for buttons.", false, true, new Color(192, 196, 200), false);
        scheme.createSetting((ITheme)this, "Shadow Color", "The color for button shadows.", false, true, new Color(132, 136, 140), false);
        scheme.createSetting((ITheme)this, "Font Color", "The main color for text.", false, true, new Color(0, 0, 0), false);
    }
    
    protected void drawButtonBase(final IInterface inter, final Rectangle rect, final boolean focus, final boolean clicked, final boolean small) {
        final Color c1 = this.scheme.getColor("Shadow Color");
        final Color c2 = this.getMainColor(focus, false);
        final Color c3 = this.getBackgroundColor(focus);
        if (clicked) {
            inter.fillRect(new Rectangle(rect.x, rect.y, 1, rect.height), c1, c1, c1, c1);
            inter.fillRect(new Rectangle(rect.x, rect.y, rect.width, 1), c1, c1, c1, c1);
            inter.fillRect(new Rectangle(rect.x + 1, rect.y + 1, rect.width - 1, rect.height - 1), c3, c3, c3, c3);
        }
        else {
            inter.fillRect(new Rectangle(rect.x + rect.width - 1, rect.y, 1, rect.height), c1, c1, c1, c1);
            inter.fillRect(new Rectangle(rect.x, rect.y + rect.height - 1, rect.width, 1), c1, c1, c1, c1);
            inter.fillRect(new Rectangle(rect.x + rect.width - 2, rect.y + 1, 1, rect.height - 1), c1, c1, c1, c1);
            inter.fillRect(new Rectangle(rect.x + 1, rect.y + rect.height - 2, rect.width - 1, 1), c1, c1, c1, c1);
            if (small) {
                inter.fillRect(new Rectangle(rect.x + 1, rect.y + 1, rect.width - 3, rect.height - 3), c3, c3, c3, c3);
            }
            else {
                inter.fillRect(new Rectangle(rect.x + 2, rect.y + 2, rect.width - 4, rect.height - 4), c3, c3, c3, c3);
            }
            inter.fillRect(new Rectangle(rect.x, rect.y, rect.width - 1, 1), c2, c2, c2, c2);
            inter.fillRect(new Rectangle(rect.x, rect.y, 1, rect.height - 1), c2, c2, c2, c2);
            if (!small) {
                inter.fillRect(new Rectangle(rect.x + 1, rect.y + 1, rect.width - 3, 1), c2, c2, c2, c2);
                inter.fillRect(new Rectangle(rect.x + 1, rect.y + 1, 1, rect.height - 3), c2, c2, c2, c2);
            }
        }
    }
    
    protected void drawButton(final IInterface inter, final Rectangle rect, final boolean focus, final boolean clicked, final boolean small) {
        final Color c0 = this.getFontColor(focus);
        if (small) {
            ITheme.drawRect(inter, rect, c0);
        }
        else {
            inter.fillRect(new Rectangle(rect.x, rect.y + 1, 1, rect.height - 2), c0, c0, c0, c0);
            inter.fillRect(new Rectangle(rect.x + 1, rect.y, rect.width - 2, 1), c0, c0, c0, c0);
            inter.fillRect(new Rectangle(rect.x + rect.width - 1, rect.y + 1, 1, rect.height - 2), c0, c0, c0, c0);
            inter.fillRect(new Rectangle(rect.x + 1, rect.y + rect.height - 1, rect.width - 2, 1), c0, c0, c0, c0);
        }
        if (focus && !small) {
            ITheme.drawRect(inter, new Rectangle(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2), c0);
            this.drawButtonBase(inter, new Rectangle(rect.x + 2, rect.y + 2, rect.width - 4, rect.height - 4), focus, clicked, small);
        }
        else {
            this.drawButtonBase(inter, new Rectangle(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2), focus, clicked, small);
        }
    }
    
    public IDescriptionRenderer getDescriptionRenderer() {
        return (IDescriptionRenderer)new IDescriptionRenderer() {
            public void renderDescription(final IInterface inter, final Point pos, final String text) {
                final Rectangle rect = new Rectangle(pos, new Dimension(inter.getFontWidth(Windows31Theme.this.height, text) + 4, Windows31Theme.this.height + 4));
                final Color color = Windows31Theme.this.getMainColor(true, false);
                inter.fillRect(rect, color, color, color, color);
                inter.drawString(new Point(pos.x + 2, pos.y + 2), Windows31Theme.this.height, text, Windows31Theme.this.getFontColor(true));
                ITheme.drawRect(inter, rect, Windows31Theme.this.getMainColor(true, true));
            }
        };
    }
    
    public IContainerRenderer getContainerRenderer(final int logicalLevel, final int graphicalLevel, final boolean horizontal) {
        return (IContainerRenderer)new IContainerRenderer() {
            public int getBorder() {
                return 1;
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
        };
    }
    
    public <T> IPanelRenderer<T> getPanelRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        return (IPanelRenderer<T>)new IPanelRenderer<T>() {
            public void renderBackground(final Context context, final boolean focus) {
                final Rectangle rect = context.getRect();
                final Color c = Windows31Theme.this.getMainColor(focus, false);
                context.getInterface().fillRect(new Rectangle(rect.x + 3, rect.y + 3, rect.width - 6, rect.height - 6), c, c, c, c);
            }
            
            public int getBorder() {
                return 1;
            }
            
            public int getLeft() {
                return 4;
            }
            
            public int getRight() {
                return 4;
            }
            
            public int getTop() {
                return 4;
            }
            
            public int getBottom() {
                return 4;
            }
            
            public void renderPanelOverlay(final Context context, final boolean focus, final T state, final boolean open) {
                final Rectangle rect = context.getRect();
                ITheme.drawRect(context.getInterface(), rect, Windows31Theme.this.getFontColor(focus));
                ITheme.drawRect(context.getInterface(), new Rectangle(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2), Windows31Theme.this.getMainColor(focus, focus));
                ITheme.drawRect(context.getInterface(), new Rectangle(rect.x + 2, rect.y + 2, rect.width - 4, rect.height - 4), Windows31Theme.this.getMainColor(focus, focus));
            }
            
            public void renderTitleOverlay(final Context context, final boolean focus, final T state, final boolean open) {
            }
        };
    }
    
    public <T> IScrollBarRenderer<T> getScrollBarRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel) {
        return (IScrollBarRenderer<T>)new IScrollBarRenderer<T>() {
            public int renderScrollBar(final Context context, final boolean focus, final T state, final boolean horizontal, final int height, final int position) {
                final Color color = Windows31Theme.this.getBackgroundColor(focus);
                context.getInterface().fillRect(context.getRect(), color, color, color, color);
                final int d = horizontal ? context.getSize().height : context.getSize().width;
                final int x = context.getPos().x + (horizontal ? ((int)(position / (double)(height - context.getSize().width) * (context.getSize().width - 2 * d))) : 0);
                final int y = context.getPos().y + (horizontal ? 0 : ((int)(position / (double)(height - context.getSize().height) * (context.getSize().height - 2 * d))));
                final Rectangle rect = new Rectangle(x, y, d * (horizontal ? 2 : 1), d * (horizontal ? 1 : 2));
                Windows31Theme.this.drawButton(context.getInterface(), rect, focus, context.isClicked(0) && rect.contains(context.getInterface().getMouse()), true);
                if (horizontal) {
                    return (int)Math.round((context.getInterface().getMouse().x - context.getPos().x - d) / (double)(context.getSize().width - 2 * d) * (height - context.getSize().width));
                }
                return (int)Math.round((context.getInterface().getMouse().y - context.getPos().y - d) / (double)(context.getSize().height - 2 * d) * (height - context.getSize().height));
            }
            
            public int getThickness() {
                return Windows31Theme.this.scroll;
            }
        };
    }
    
    public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IEmptySpaceRenderer<T>)new IEmptySpaceRenderer<T>() {
            public void renderSpace(final Context context, final boolean focus, final T state) {
                Color color;
                if (container) {
                    color = Windows31Theme.this.getMainColor(focus, false);
                }
                else {
                    color = Windows31Theme.this.getBackgroundColor(focus);
                }
                context.getInterface().fillRect(context.getRect(), color, color, color, color);
            }
        };
    }
    
    public <T> IButtonRenderer<T> getButtonRenderer(final Class<T> type, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IButtonRenderer<T>)new IButtonRenderer<T>() {
            public void renderButton(final Context context, final String title, final boolean focus, final T state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                final boolean active = (boolean)((type == Boolean.class) ? state : effFocus);
                if (!container && type == Boolean.class) {
                    ITheme.drawRect(context.getInterface(), new Rectangle(context.getPos().x, context.getPos().y, Windows31Theme.this.height, Windows31Theme.this.height), Windows31Theme.this.getFontColor(effFocus));
                    if (state) {
                        context.getInterface().drawLine(context.getPos(), new Point(context.getPos().x + Windows31Theme.this.height - 1, context.getPos().y + Windows31Theme.this.height - 1), Windows31Theme.this.getFontColor(effFocus), Windows31Theme.this.getFontColor(effFocus));
                        context.getInterface().drawLine(new Point(context.getPos().x + Windows31Theme.this.height - 1, context.getPos().y + 1), new Point(context.getPos().x, context.getPos().y + Windows31Theme.this.height), Windows31Theme.this.getFontColor(effFocus), Windows31Theme.this.getFontColor(effFocus));
                    }
                    context.getInterface().drawString(new Point(context.getPos().x + Windows31Theme.this.height + Windows31Theme.this.padding, context.getPos().y), Windows31Theme.this.height, title, Windows31Theme.this.getFontColor(effFocus));
                    return;
                }
                if (container) {
                    final Color color = Windows31Theme.this.getMainColor(effFocus, active);
                    context.getInterface().fillRect(context.getRect(), color, color, color, color);
                    final Color lineColor = Windows31Theme.this.getFontColor(effFocus);
                    context.getInterface().fillRect(new Rectangle(context.getPos().x, context.getPos().y + context.getSize().height - 1, context.getSize().width, 1), lineColor, lineColor, lineColor, lineColor);
                }
                else {
                    Windows31Theme.this.drawButton(context.getInterface(), context.getRect(), effFocus, context.isClicked(0), false);
                }
                Color color = (container && active) ? Windows31Theme.this.getMainColor(effFocus, false) : Windows31Theme.this.getFontColor(effFocus);
                String string = title;
                if (type == String.class) {
                    string = string + Windows31Theme.this.separator + state;
                }
                else if (type == Color.class) {
                    color = (Color)state;
                }
                context.getInterface().drawString(new Point(context.getPos().x + context.getSize().width / 2 - context.getInterface().getFontWidth(Windows31Theme.this.height, string) / 2, context.getPos().y + (container ? 0 : 3) + Windows31Theme.this.padding), Windows31Theme.this.height, string, color);
            }
            
            public int getDefaultHeight() {
                if (!container && type == Boolean.class) {
                    return Windows31Theme.this.height;
                }
                return container ? Windows31Theme.this.getBaseHeight() : (Windows31Theme.this.getBaseHeight() + 6);
            }
        };
    }
    
    public IButtonRenderer<Void> getSmallButtonRenderer(final int symbol, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IButtonRenderer<Void>)new IButtonRenderer<Void>() {
            public void renderButton(final Context context, final String title, final boolean focus, final Void state) {
                Windows31Theme.this.drawButton(context.getInterface(), context.getRect(), focus, context.isClicked(0), true);
                final Point[] points = new Point[3];
                final int padding = (context.getSize().height <= 12) ? 4 : 6;
                final Rectangle rect = new Rectangle(context.getPos().x + padding / 2, context.getPos().y + padding / 2, context.getSize().height - 2 * (padding / 2), context.getSize().height - 2 * (padding / 2));
                if (title == null) {
                    final Rectangle rectangle = rect;
                    rectangle.x += context.getSize().width / 2 - context.getSize().height / 2;
                }
                final Color color = Windows31Theme.this.getFontColor(focus);
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
                    context.getInterface().drawString(new Point(context.getPos().x + ((symbol == 0) ? padding : context.getSize().height), context.getPos().y + padding), Windows31Theme.this.height, title, Windows31Theme.this.getFontColor(focus));
                }
            }
            
            public int getDefaultHeight() {
                return Windows31Theme.this.getBaseHeight();
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
                final Color colorA = Windows31Theme.this.getMainColor(effFocus, true);
                if (container && effFocus) {
                    context.getInterface().fillRect(context.getRect(), colorA, colorA, colorA, colorA);
                }
                final Rectangle rect = this.getSlideArea(context, title, state);
                final Color colorB = Windows31Theme.this.getBackgroundColor(effFocus);
                context.getInterface().fillRect(rect, colorB, colorB, colorB, colorB);
                ITheme.drawRect(context.getInterface(), rect, Windows31Theme.this.getFontColor(effFocus));
                final int divider = (int)((rect.width - rect.height) * value);
                final Rectangle buttonRect = new Rectangle(rect.x + divider, rect.y, rect.height, rect.height);
                final boolean clicked = context.isClicked(0) && buttonRect.contains(context.getInterface().getMouse());
                Windows31Theme.this.drawButton(context.getInterface(), buttonRect, effFocus, clicked, true);
                final Color color = (container && effFocus) ? Windows31Theme.this.getMainColor(effFocus, false) : Windows31Theme.this.getFontColor(effFocus);
                final String string = title + Windows31Theme.this.separator + state;
                context.getInterface().drawString(new Point(context.getPos().x + Windows31Theme.this.padding, context.getPos().y + Windows31Theme.this.padding), Windows31Theme.this.height, string, color);
            }
            
            public Rectangle getSlideArea(final Context context, final String title, final String state) {
                if (container) {
                    return context.getRect();
                }
                return new Rectangle(context.getPos().x, context.getPos().y + context.getSize().height - Windows31Theme.this.height, context.getSize().width, Windows31Theme.this.height);
            }
            
            public int getDefaultHeight() {
                return Windows31Theme.this.getBaseHeight() + Windows31Theme.this.height;
            }
        };
    }
    
    public IRadioRenderer getRadioRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (IRadioRenderer)new IRadioRenderer() {
            public void renderItem(final Context context, final ILabeled[] items, final boolean focus, final int target, final double state, final boolean horizontal) {
                for (int i = 0; i < items.length; ++i) {
                    final Rectangle rect = this.getItemRect(context, items, i, horizontal);
                    final Color color = Windows31Theme.this.getMainColor(focus, true);
                    if (i == target) {
                        context.getInterface().fillRect(rect, color, color, color, color);
                    }
                    context.getInterface().drawString(new Point(rect.x + Windows31Theme.this.padding, rect.y + Windows31Theme.this.padding), Windows31Theme.this.height, items[i].getDisplayName(), (i == target) ? Windows31Theme.this.getMainColor(focus, false) : Windows31Theme.this.getFontColor(focus));
                }
            }
            
            public int getDefaultHeight(final ILabeled[] items, final boolean horizontal) {
                return (horizontal ? 1 : items.length) * Windows31Theme.this.getBaseHeight();
            }
        };
    }
    
    public ITextFieldRenderer getTextRenderer(final boolean embed, final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (ITextFieldRenderer)new ITextFieldRenderer() {
            public int renderTextField(final Context context, final String title, final boolean focus, final String content, final int position, final int select, int boxPosition, final boolean insertMode) {
                final boolean effFocus = container ? (context.hasFocus() || focus) : focus;
                final Color textColor = Windows31Theme.this.getFontColor(effFocus);
                final Color titleColor = (container && effFocus) ? Windows31Theme.this.getMainColor(effFocus, false) : textColor;
                final Color highlightColor = Windows31Theme.this.getMainColor(effFocus, true);
                final Rectangle rect = this.getTextArea(context, title);
                final int strlen = context.getInterface().getFontWidth(Windows31Theme.this.height, content.substring(0, position));
                if (container && effFocus) {
                    context.getInterface().fillRect(context.getRect(), highlightColor, highlightColor, highlightColor, highlightColor);
                    context.getInterface().fillRect(rect, titleColor, titleColor, titleColor, titleColor);
                }
                if (boxPosition < position) {
                    int minPosition;
                    for (minPosition = boxPosition; minPosition < position && context.getInterface().getFontWidth(Windows31Theme.this.height, content.substring(0, minPosition)) + rect.width - Windows31Theme.this.padding < strlen; ++minPosition) {}
                    if (boxPosition < minPosition) {
                        boxPosition = minPosition;
                    }
                }
                else if (boxPosition > position) {
                    boxPosition = position - 1;
                }
                int maxPosition;
                for (maxPosition = content.length(); maxPosition > 0; --maxPosition) {
                    if (context.getInterface().getFontWidth(Windows31Theme.this.height, content.substring(maxPosition)) >= rect.width - Windows31Theme.this.padding) {
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
                final int offset = context.getInterface().getFontWidth(Windows31Theme.this.height, content.substring(0, boxPosition));
                final int x1 = rect.x + Windows31Theme.this.padding / 2 - offset + strlen;
                int x2 = rect.x + Windows31Theme.this.padding / 2 - offset;
                if (position < content.length()) {
                    x2 += context.getInterface().getFontWidth(Windows31Theme.this.height, content.substring(0, position + 1));
                }
                else {
                    x2 += context.getInterface().getFontWidth(Windows31Theme.this.height, content + "X");
                }
                context.getInterface().drawString(new Point(context.getPos().x + Windows31Theme.this.padding, context.getPos().y + Windows31Theme.this.padding), Windows31Theme.this.height, title + Windows31Theme.this.separator, titleColor);
                context.getInterface().window(rect);
                if (select >= 0) {
                    final int x3 = rect.x + Windows31Theme.this.padding / 2 - offset + context.getInterface().getFontWidth(Windows31Theme.this.height, content.substring(0, select));
                    context.getInterface().fillRect(new Rectangle(Math.min(x1, x3), rect.y + Windows31Theme.this.padding, Math.abs(x3 - x1), Windows31Theme.this.height), highlightColor, highlightColor, highlightColor, highlightColor);
                    context.getInterface().drawString(new Point(rect.x + Windows31Theme.this.padding / 2 - offset, rect.y + Windows31Theme.this.padding), Windows31Theme.this.height, content.substring(0, Math.min(position, select)), textColor);
                    context.getInterface().drawString(new Point(Math.min(x1, x3), rect.y + Windows31Theme.this.padding), Windows31Theme.this.height, content.substring(Math.min(position, select), Math.max(position, select)), Windows31Theme.this.getMainColor(effFocus, false));
                    context.getInterface().drawString(new Point(Math.max(x1, x3), rect.y + Windows31Theme.this.padding), Windows31Theme.this.height, content.substring(Math.max(position, select)), textColor);
                }
                else {
                    context.getInterface().drawString(new Point(rect.x + Windows31Theme.this.padding / 2 - offset, rect.y + Windows31Theme.this.padding), Windows31Theme.this.height, content, textColor);
                }
                if (System.currentTimeMillis() / 500L % 2L == 0L && focus) {
                    if (insertMode) {
                        context.getInterface().fillRect(new Rectangle(x1, rect.y + Windows31Theme.this.padding + Windows31Theme.this.height, x2 - x1, 1), textColor, textColor, textColor, textColor);
                    }
                    else {
                        context.getInterface().fillRect(new Rectangle(x1, rect.y + Windows31Theme.this.padding, 1, Windows31Theme.this.height), textColor, textColor, textColor, textColor);
                    }
                }
                ITheme.drawRect(context.getInterface(), rect, textColor);
                context.getInterface().restore();
                return boxPosition;
            }
            
            public int getDefaultHeight() {
                int height = Windows31Theme.this.getBaseHeight();
                if (height % 2 == 1) {
                    ++height;
                }
                return height;
            }
            
            public Rectangle getTextArea(final Context context, final String title) {
                final Rectangle rect = context.getRect();
                final int length = Windows31Theme.this.padding + context.getInterface().getFontWidth(Windows31Theme.this.height, title + Windows31Theme.this.separator);
                return new Rectangle(rect.x + length, rect.y, rect.width - length, rect.height);
            }
            
            public int transformToCharPos(final Context context, final String title, final String content, final int boxPosition) {
                final Rectangle rect = this.getTextArea(context, title);
                final Point mouse = context.getInterface().getMouse();
                final int offset = context.getInterface().getFontWidth(Windows31Theme.this.height, content.substring(0, boxPosition));
                if (rect.contains(mouse)) {
                    for (int i = 1; i <= content.length(); ++i) {
                        if (rect.x + Windows31Theme.this.padding / 2 - offset + context.getInterface().getFontWidth(Windows31Theme.this.height, content.substring(0, i)) > mouse.x) {
                            return i - 1;
                        }
                    }
                    return content.length();
                }
                return -1;
            }
        };
    }
    
    public IResizeBorderRenderer getResizeRenderer() {
        return (IResizeBorderRenderer)new IResizeBorderRenderer() {
            public void drawBorder(final Context context, final boolean focus) {
                final Color color = Windows31Theme.this.getBackgroundColor(focus);
                final Rectangle rect = context.getRect();
                context.getInterface().fillRect(new Rectangle(rect.x, rect.y, rect.width, this.getBorder()), color, color, color, color);
                context.getInterface().fillRect(new Rectangle(rect.x, rect.y + rect.height - this.getBorder(), rect.width, this.getBorder()), color, color, color, color);
                context.getInterface().fillRect(new Rectangle(rect.x, rect.y + this.getBorder(), this.getBorder(), rect.height - 2 * this.getBorder()), color, color, color, color);
                context.getInterface().fillRect(new Rectangle(rect.x + rect.width - this.getBorder(), rect.y + this.getBorder(), this.getBorder(), rect.height - 2 * this.getBorder()), color, color, color, color);
                final Color borderColor = Windows31Theme.this.getFontColor(focus);
                ITheme.drawRect(context.getInterface(), rect, borderColor);
                ITheme.drawRect(context.getInterface(), new Rectangle(rect.x, rect.y + this.getBorder(), rect.width, rect.height - 2 * this.getBorder()), borderColor);
                ITheme.drawRect(context.getInterface(), new Rectangle(rect.x + this.getBorder(), rect.y, rect.width - 2 * this.getBorder(), rect.height), borderColor);
            }
            
            public int getBorder() {
                return 4;
            }
        };
    }
    
    public ISwitchRenderer<Boolean> getToggleSwitchRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (ISwitchRenderer<Boolean>)new ISwitchRenderer<Boolean>() {
            public void renderButton(final Context context, final String title, final boolean focus, final Boolean state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                final Color colorA = Windows31Theme.this.getMainColor(effFocus, true);
                if (container && effFocus) {
                    context.getInterface().fillRect(context.getRect(), colorA, colorA, colorA, colorA);
                }
                context.getInterface().drawString(new Point(context.getPos().x + Windows31Theme.this.padding, context.getPos().y + Windows31Theme.this.padding), Windows31Theme.this.height, title + Windows31Theme.this.separator + (state ? "On" : "Off"), Windows31Theme.this.getFontColor(focus));
                final Rectangle rect = new Rectangle(context.getPos().x + context.getSize().width - 2 * context.getSize().height, context.getPos().y, 2 * context.getSize().height, context.getSize().height);
                final Color colorB = Windows31Theme.this.getMainColor(effFocus, state);
                context.getInterface().fillRect(rect, colorB, colorB, colorB, colorB);
                ITheme.drawRect(context.getInterface(), rect, Windows31Theme.this.getFontColor(effFocus));
                final Rectangle field = state ? this.getOnField(context) : this.getOffField(context);
                Windows31Theme.this.drawButton(context.getInterface(), field, focus, context.isClicked(0) && field.contains(context.getInterface().getMouse()), true);
            }
            
            public int getDefaultHeight() {
                return Windows31Theme.this.getBaseHeight();
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
    
    public ISwitchRenderer<String> getCycleSwitchRenderer(final int logicalLevel, final int graphicalLevel, final boolean container) {
        return (ISwitchRenderer<String>)new ISwitchRenderer<String>() {
            public void renderButton(final Context context, final String title, final boolean focus, final String state) {
                final boolean effFocus = container ? context.hasFocus() : focus;
                final Color colorA = Windows31Theme.this.getMainColor(effFocus, true);
                if (container && effFocus) {
                    context.getInterface().fillRect(context.getRect(), colorA, colorA, colorA, colorA);
                }
                Context subContext = new Context(context, context.getSize().width - 2 * context.getSize().height, new Point(0, 0), true, true);
                subContext.setHeight(context.getSize().height);
                final Color textColor = (container && effFocus) ? Windows31Theme.this.getMainColor(effFocus, false) : Windows31Theme.this.getFontColor(effFocus);
                context.getInterface().drawString(new Point(context.getPos().x + Windows31Theme.this.padding, context.getPos().y + Windows31Theme.this.padding), Windows31Theme.this.height, title + Windows31Theme.this.separator + state, textColor);
                Rectangle rect = this.getOnField(context);
                subContext = new Context(context, rect.width, new Point(rect.x - context.getPos().x, 0), true, true);
                subContext.setHeight(rect.height);
                Windows31Theme.this.getSmallButtonRenderer(5, logicalLevel, graphicalLevel, container).renderButton(subContext, (String)null, effFocus, (Object)null);
                rect = this.getOffField(context);
                subContext = new Context(context, rect.width, new Point(rect.x - context.getPos().x, 0), true, true);
                subContext.setHeight(rect.height);
                Windows31Theme.this.getSmallButtonRenderer(4, logicalLevel, graphicalLevel, false).renderButton(subContext, (String)null, effFocus, (Object)null);
            }
            
            public int getDefaultHeight() {
                return Windows31Theme.this.getBaseHeight();
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
            public int getPadding() {
                return Windows31Theme.this.padding;
            }
            
            public int getBaseHeight() {
                return Windows31Theme.this.getBaseHeight();
            }
        };
    }
    
    public int getBaseHeight() {
        return this.height + 2 * this.padding;
    }
    
    public Color getMainColor(final boolean focus, final boolean active) {
        if (active) {
            return this.getColor(this.scheme.getColor("Title Color"));
        }
        return this.scheme.getColor("Background Color");
    }
    
    public Color getBackgroundColor(final boolean focus) {
        return this.scheme.getColor("Button Color");
    }
    
    public Color getFontColor(final boolean focus) {
        return this.scheme.getColor("Font Color");
    }
}
