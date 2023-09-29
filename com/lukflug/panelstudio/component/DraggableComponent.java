//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import java.awt.*;
import com.lukflug.panelstudio.config.*;
import com.lukflug.panelstudio.base.*;

public abstract class DraggableComponent<T extends IFixedComponent> implements IComponentProxy<T>, IFixedComponent
{
    protected boolean dragging;
    protected Point attachPoint;
    
    public DraggableComponent() {
        this.dragging = false;
    }
    
    @Override
    public Point getPosition(final IInterface inter) {
        final Point point = this.getComponent().getPosition(inter);
        if (this.dragging) {
            point.translate(inter.getMouse().x - this.attachPoint.x, inter.getMouse().y - this.attachPoint.y);
        }
        return point;
    }
    
    @Override
    public void setPosition(final IInterface inter, final Point position) {
        this.getComponent().setPosition(inter, position);
    }
    
    @Override
    public int getWidth(final IInterface inter) {
        return this.getComponent().getWidth(inter);
    }
    
    @Override
    public boolean savesState() {
        return this.getComponent().savesState();
    }
    
    @Override
    public void saveConfig(final IInterface inter, final IPanelConfig config) {
        this.getComponent().saveConfig(inter, config);
    }
    
    @Override
    public void loadConfig(final IInterface inter, final IPanelConfig config) {
        this.getComponent().loadConfig(inter, config);
    }
    
    @Override
    public String getConfigName() {
        return this.getComponent().getConfigName();
    }
    
    public <S extends IComponent> ComponentProxy<S> getWrappedDragComponent(final S dragComponent) {
        return new ComponentProxy<S>(dragComponent) {
            public void handleButton(final Context context, final int button) {
                super.handleButton(context, button);
                if (context.isClicked(button) && button == 0) {
                    DraggableComponent.this.dragging = true;
                    DraggableComponent.this.attachPoint = context.getInterface().getMouse();
                }
                else if (!context.getInterface().getButton(0) && DraggableComponent.this.dragging) {
                    final Point mouse = context.getInterface().getMouse();
                    DraggableComponent.this.dragging = false;
                    final Point p = DraggableComponent.this.getComponent().getPosition(context.getInterface());
                    p.translate(mouse.x - DraggableComponent.this.attachPoint.x, mouse.y - DraggableComponent.this.attachPoint.y);
                    DraggableComponent.this.getComponent().setPosition(context.getInterface(), p);
                }
            }
            
            public void exit() {
                DraggableComponent.this.dragging = false;
                super.exit();
            }
        };
    }
}
