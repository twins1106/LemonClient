//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.*;
import java.awt.*;
import java.util.function.*;

public abstract class ScrollableComponent<T extends IComponent> implements IComponentProxy<T>, IScrollSize
{
    private Context tempContext;
    protected Point scrollPos;
    protected Point nextScrollPos;
    protected Dimension contentSize;
    protected Dimension scrollSize;
    
    public ScrollableComponent() {
        this.scrollPos = new Point(0, 0);
        this.nextScrollPos = null;
        this.contentSize = new Dimension(0, 0);
        this.scrollSize = new Dimension(0, 0);
    }
    
    public void render(final Context context) {
        final Rectangle a;
        final Rectangle b;
        this.doOperation(context, subContext -> {
            context.getInterface().window(context.getRect());
            this.getComponent().render(subContext);
            a = context.getRect();
            b = subContext.getRect();
            if (b.width < a.width) {
                this.fillEmptySpace(context, new Rectangle(a.x + b.width, a.y, a.width - b.width, b.height));
            }
            if (b.height < a.height) {
                this.fillEmptySpace(context, new Rectangle(a.x, a.y + b.height, b.width, a.height - b.height));
            }
            if (b.width < a.width && b.height < a.height) {
                this.fillEmptySpace(context, new Rectangle(a.x + b.width, a.y + b.height, a.width - b.width, a.height - b.height));
            }
            context.getInterface().restore();
        });
    }
    
    public void handleScroll(final Context context, final int diff) {
        final Context sContext = this.doOperation(context, subContext -> this.getComponent().handleScroll(subContext, diff));
        if (context.isHovered()) {
            if (this.isScrollingY()) {
                this.scrollPos.translate(0, diff);
            }
            else if (this.isScrollingX()) {
                this.scrollPos.translate(diff, 0);
            }
            this.clampScrollPos(context.getSize(), sContext.getSize());
        }
    }
    
    public Context doOperation(final Context context, final Consumer<Context> operation) {
        this.tempContext = context;
        final Context subContext = super.doOperation(context, (Consumer)operation);
        if (this.nextScrollPos != null) {
            this.scrollPos = this.nextScrollPos;
            this.nextScrollPos = null;
        }
        this.clampScrollPos(context.getSize(), subContext.getSize());
        this.contentSize = subContext.getSize();
        this.scrollSize = context.getSize();
        return subContext;
    }
    
    public Context getContext(final Context context) {
        final Context subContext = new Context(context, context.getSize().width, new Point(-this.scrollPos.x, -this.scrollPos.y), true, true);
        this.getComponent().getHeight(subContext);
        final int height = this.getScrollHeight(context, subContext.getSize().height);
        context.setHeight(height);
        return new Context(context, this.getComponentWidth(context), new Point(-this.scrollPos.x, -this.scrollPos.y), true, context.isHovered());
    }
    
    public Point getScrollPos() {
        return new Point(this.scrollPos);
    }
    
    public void setScrollPosX(final int scrollPos) {
        if (this.nextScrollPos == null) {
            this.nextScrollPos = new Point(scrollPos, this.scrollPos.y);
        }
        else {
            this.nextScrollPos.x = scrollPos;
        }
    }
    
    public void setScrollPosY(final int scrollPos) {
        if (this.nextScrollPos == null) {
            this.nextScrollPos = new Point(this.scrollPos.x, scrollPos);
        }
        else {
            this.nextScrollPos.y = scrollPos;
        }
    }
    
    public Dimension getContentSize() {
        return this.contentSize;
    }
    
    public Dimension getScrollSize() {
        return this.scrollSize;
    }
    
    public boolean isScrollingX() {
        return this.contentSize.width > this.scrollSize.width;
    }
    
    public boolean isScrollingY() {
        return this.contentSize.height > this.scrollSize.height;
    }
    
    protected void clampScrollPos(final Dimension scrollSize, final Dimension contentSize) {
        if (this.scrollPos.x > contentSize.width - scrollSize.width) {
            this.scrollPos.x = contentSize.width - scrollSize.width;
        }
        if (this.scrollPos.x < 0) {
            this.scrollPos.x = 0;
        }
        if (this.scrollPos.y > contentSize.height - scrollSize.height) {
            this.scrollPos.y = contentSize.height - scrollSize.height;
        }
        if (this.scrollPos.y < 0) {
            this.scrollPos.y = 0;
        }
    }
    
    public final int getHeight(final int height) {
        return this.getScrollHeight(this.tempContext, height);
    }
    
    public abstract void fillEmptySpace(final Context p0, final Rectangle p1);
}
