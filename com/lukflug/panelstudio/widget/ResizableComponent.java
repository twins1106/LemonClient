//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import java.awt.*;
import com.lukflug.panelstudio.config.*;
import java.util.function.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.component.*;

public class ResizableComponent<T extends IFixedComponent> implements IFixedComponentProxy<T>
{
    protected T component;
    protected IResizeBorderRenderer renderer;
    protected IResizable size;
    protected boolean[] resizing;
    protected Point attachPoint;
    protected Rectangle attachRect;
    
    public ResizableComponent(final T component, final IResizeBorderRenderer renderer, final IResizable size) {
        this.resizing = new boolean[] { false, false, false, false };
        this.attachPoint = null;
        this.attachRect = null;
        this.component = component;
        this.renderer = renderer;
        this.size = size;
    }
    
    public void render(final Context context) {
        super.render(context);
        this.renderer.drawBorder(context, context.hasFocus());
    }
    
    public void handleButton(final Context context, final int button) {
        super.handleButton(context, button);
        if (button == 0 && context.isClicked(button)) {
            this.attachPoint = context.getInterface().getMouse();
            this.attachRect = new Rectangle(this.getComponent().getPosition(context.getInterface()), this.size.getSize());
            final Rectangle r = context.getRect();
            if (new Rectangle(r.x, r.y, r.width, this.renderer.getBorder()).contains(this.attachPoint)) {
                this.resizing[0] = true;
            }
            else if (new Rectangle(r.x, r.y + r.height - this.renderer.getBorder(), r.width, this.renderer.getBorder()).contains(this.attachPoint)) {
                this.resizing[1] = true;
            }
            if (new Rectangle(r.x, r.y, this.renderer.getBorder(), r.height).contains(this.attachPoint)) {
                this.resizing[2] = true;
            }
            else if (new Rectangle(r.x + r.width - this.renderer.getBorder(), r.y, this.renderer.getBorder(), r.height).contains(this.attachPoint)) {
                this.resizing[3] = true;
            }
        }
        else if (!context.getInterface().getButton(0)) {
            this.resizing[0] = false;
            this.resizing[1] = false;
            this.resizing[2] = false;
            this.resizing[3] = false;
        }
    }
    
    public int getHeight(final int height) {
        return height + 2 * this.renderer.getBorder();
    }
    
    public Context getContext(final Context context) {
        if (this.resizing[0]) {
            this.getComponent().setPosition(context.getInterface(), new Point(this.getComponent().getPosition(context.getInterface()).x, this.attachRect.y + context.getInterface().getMouse().y - this.attachPoint.y));
            this.size.setSize(new Dimension(this.size.getSize().width, this.attachRect.height - context.getInterface().getMouse().y + this.attachPoint.y));
        }
        else if (this.resizing[1]) {
            this.size.setSize(new Dimension(this.size.getSize().width, this.attachRect.height + context.getInterface().getMouse().y - this.attachPoint.y));
        }
        if (this.resizing[2]) {
            this.getComponent().setPosition(context.getInterface(), new Point(this.attachRect.x + context.getInterface().getMouse().x - this.attachPoint.x, this.getComponent().getPosition(context.getInterface()).y));
            this.size.setSize(new Dimension(this.attachRect.width - context.getInterface().getMouse().x + this.attachPoint.x, this.size.getSize().height));
        }
        else if (this.resizing[3]) {
            this.size.setSize(new Dimension(this.attachRect.width + context.getInterface().getMouse().x - this.attachPoint.x, this.size.getSize().height));
        }
        return new Context(context, context.getSize().width - 2 * this.renderer.getBorder(), new Point(this.renderer.getBorder(), this.renderer.getBorder()), true, true);
    }
    
    public Point getPosition(final IInterface inter) {
        final Point p = this.getComponent().getPosition(inter);
        p.translate(-this.renderer.getBorder(), -this.renderer.getBorder());
        return p;
    }
    
    public void setPosition(final IInterface inter, final Point position) {
        position.translate(this.renderer.getBorder(), this.renderer.getBorder());
        this.getComponent().setPosition(inter, position);
    }
    
    public int getWidth(final IInterface inter) {
        return this.size.getSize().width + 2 * this.renderer.getBorder();
    }
    
    public void saveConfig(final IInterface inter, final IPanelConfig config) {
        super.saveConfig(inter, config);
        config.saveSize(this.size.getSize());
    }
    
    public void loadConfig(final IInterface inter, final IPanelConfig config) {
        super.loadConfig(inter, config);
        final Dimension s = config.loadSize();
        if (s != null) {
            this.size.setSize(s);
        }
    }
    
    public T getComponent() {
        return this.component;
    }
    
    public static <S extends IComponent, T extends IComponent, U> IFixedComponent createResizableComponent(final S title, final T content, final Supplier<U> state, final AnimatedToggleable open, final RendererTuple<U> renderer, final IResizeBorderRenderer resizeRenderer, final IResizable size, final IScrollSize scrollSize, final Point position, final int width, final boolean savesState, final String configName) {
        final IFixedComponent draggable = (IFixedComponent)ClosableComponent.createDraggableComponent((IComponent)title, (IComponent)content, (Supplier)state, open, (RendererTuple)renderer, scrollSize, position, width, savesState, configName);
        if (size != null) {
            return (IFixedComponent)new ResizableComponent(draggable, resizeRenderer, size);
        }
        return draggable;
    }
}
