//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.popup.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.base.*;
import java.awt.*;

public class StandardTheme implements ITabGUITheme
{
    protected final IColorScheme scheme;
    protected int width;
    protected int height;
    protected int padding;
    protected IPopupPositioner positioner;
    protected RendererBase<Void> parentRenderer;
    protected RendererBase<Boolean> childRenderer;
    
    public StandardTheme(final IColorScheme scheme, final int width, final int height, final int padding, final int distance) {
        this.scheme = scheme;
        this.width = width;
        this.height = height;
        this.padding = padding;
        this.positioner = (IPopupPositioner)new PanelPositioner(new Point(distance, 0));
        scheme.createSetting(null, "Selected Color", "The color for the selected tab element.", false, true, new Color(0, 0, 255), false);
        scheme.createSetting(null, "Background Color", "The color for the tab background.", true, true, new Color(32, 32, 32, 128), false);
        scheme.createSetting(null, "Outline Color", "The color for the tab outline.", false, true, new Color(0, 0, 0), false);
        scheme.createSetting(null, "Font Color", "The main color for the text.", false, true, new Color(255, 255, 255), false);
        scheme.createSetting(null, "Enabled Color", "The color for enabled text.", false, true, new Color(255, 0, 0), false);
        this.parentRenderer = new RendererBase<Void>() {
            @Override
            protected Color getFontColor(final Void itemState) {
                return scheme.getColor("Font Color");
            }
        };
        this.childRenderer = new RendererBase<Boolean>() {
            @Override
            protected Color getFontColor(final Boolean itemState) {
                if (itemState) {
                    return scheme.getColor("Enabled Color");
                }
                return scheme.getColor("Font Color");
            }
        };
    }
    
    public int getTabWidth() {
        return this.width;
    }
    
    public IPopupPositioner getPositioner() {
        return this.positioner;
    }
    
    public ITabGUIRenderer<Void> getParentRenderer() {
        return (ITabGUIRenderer<Void>)this.parentRenderer;
    }
    
    public ITabGUIRenderer<Boolean> getChildRenderer() {
        return (ITabGUIRenderer<Boolean>)this.childRenderer;
    }
    
    protected abstract class RendererBase<T> implements ITabGUIRenderer<T>
    {
        public void renderTab(final Context context, final int amount, final double tabState) {
            final Color color = StandardTheme.this.scheme.getColor("Selected Color");
            final Color fill = StandardTheme.this.scheme.getColor("Background Color");
            final Color border = StandardTheme.this.scheme.getColor("Outline Color");
            context.getInterface().fillRect(context.getRect(), fill, fill, fill, fill);
            context.getInterface().fillRect(this.getItemRect(context.getInterface(), context.getRect(), amount, tabState), color, color, color, color);
            context.getInterface().drawRect(this.getItemRect(context.getInterface(), context.getRect(), amount, tabState), border, border, border, border);
            context.getInterface().drawRect(context.getRect(), border, border, border, border);
        }
        
        public void renderItem(final Context context, final int amount, final double tabState, final int index, final String title, final T itemState) {
            context.getInterface().drawString(new Point(context.getPos().x + StandardTheme.this.padding, context.getPos().y + context.getSize().height * index / amount + StandardTheme.this.padding), StandardTheme.this.height, title, this.getFontColor(itemState));
        }
        
        public int getTabHeight(final int amount) {
            return (StandardTheme.this.height + 2 * StandardTheme.this.padding) * amount;
        }
        
        public Rectangle getItemRect(final IInterface inter, final Rectangle rect, final int amount, final double tabState) {
            return new Rectangle(rect.x, rect.y + (int)Math.round(rect.height * tabState / amount), rect.width, StandardTheme.this.height + 2 * StandardTheme.this.padding);
        }
        
        protected abstract Color getFontColor(final T p0);
    }
}
