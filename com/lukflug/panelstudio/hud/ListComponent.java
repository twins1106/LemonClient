//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.hud;

import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;
import java.awt.*;
import com.lukflug.panelstudio.config.*;

public class ListComponent extends HUDComponent
{
    protected HUDList list;
    protected boolean lastUp;
    protected boolean lastRight;
    protected int height;
    protected int border;
    
    public ListComponent(final ILabeled label, final Point position, final String configName, final HUDList list, final int height, final int border) {
        super(label, position, configName);
        this.lastUp = false;
        this.lastRight = false;
        this.list = list;
        this.height = height;
        this.border = border;
    }
    
    public void render(final Context context) {
        super.render(context);
        for (int i = 0; i < this.list.getSize(); ++i) {
            final String s = this.list.getItem(i);
            final Point p = context.getPos();
            if (this.list.sortUp()) {
                p.translate(0, (this.height + this.border) * (this.list.getSize() - 1 - i));
            }
            else {
                p.translate(0, i * (this.height + this.border));
            }
            if (this.list.sortRight()) {
                p.translate(this.getWidth(context.getInterface()) - context.getInterface().getFontWidth(this.height, s), 0);
            }
            context.getInterface().drawString(p, this.height, s, this.list.getItemColor(i));
        }
    }
    
    public Point getPosition(final IInterface inter) {
        final Dimension size = this.getSize(inter);
        if (this.lastUp != this.list.sortUp()) {
            if (this.list.sortUp()) {
                this.position.translate(0, size.height);
            }
            else {
                this.position.translate(0, -size.height);
            }
            this.lastUp = this.list.sortUp();
        }
        if (this.lastRight != this.list.sortRight()) {
            if (this.list.sortRight()) {
                this.position.translate(size.width, 0);
            }
            else {
                this.position.translate(-size.width, 0);
            }
            this.lastRight = this.list.sortRight();
        }
        if (this.list.sortUp()) {
            if (this.list.sortRight()) {
                return new Point(this.position.x - size.width, this.position.y - size.height);
            }
            return new Point(this.position.x, this.position.y - size.height);
        }
        else {
            if (this.list.sortRight()) {
                return new Point(new Point(this.position.x - size.width, this.position.y));
            }
            return new Point(this.position);
        }
    }
    
    public void setPosition(final IInterface inter, final Point position) {
        final Dimension size = this.getSize(inter);
        if (this.list.sortUp()) {
            if (this.list.sortRight()) {
                this.position = new Point(position.x + size.width, position.y + size.height);
            }
            else {
                this.position = new Point(position.x, position.y + size.height);
            }
        }
        else if (this.list.sortRight()) {
            this.position = new Point(position.x + size.width, position.y);
        }
        else {
            this.position = new Point(position);
        }
    }
    
    public void loadConfig(final IInterface inter, final IPanelConfig config) {
        super.loadConfig(inter, config);
        this.lastUp = this.list.sortUp();
        this.lastRight = this.list.sortRight();
    }
    
    public Dimension getSize(final IInterface inter) {
        int width = inter.getFontWidth(this.height, this.getTitle());
        for (int i = 0; i < this.list.getSize(); ++i) {
            final String s = this.list.getItem(i);
            width = Math.max(width, inter.getFontWidth(this.height, s));
        }
        int height = (this.height + this.border) * this.list.getSize() - this.border;
        if (height < 0) {
            height = 0;
        }
        return new Dimension(width + 2 * this.border, height);
    }
}
