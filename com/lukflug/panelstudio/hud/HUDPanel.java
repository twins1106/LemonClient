//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.hud;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.widget.*;
import com.lukflug.panelstudio.setting.*;
import java.awt.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.config.*;

public class HUDPanel<T extends IFixedComponent> extends DraggableComponent<HUDPanelComponent>
{
    protected T component;
    protected HUDPanelComponent panel;
    protected IBoolean renderState;
    
    public HUDPanel(final T component, final IToggleable state, final Animation animation, final ITheme theme, final IBoolean renderState, final int border) {
        this.component = component;
        this.panel = new HUDPanelComponent(state, animation, theme, renderState, border);
        this.renderState = renderState;
    }
    
    public HUDPanelComponent getComponent() {
        return this.panel;
    }
    
    public void handleButton(final Context context, final int button) {
        if (this.renderState.isOn()) {
            super.handleButton(context, button);
        }
        else {
            super.getHeight(context);
        }
    }
    
    public void handleScroll(final Context context, final int diff) {
        if (this.renderState.isOn()) {
            super.handleScroll(context, diff);
        }
        else {
            super.getHeight(context);
        }
    }
    
    protected class HUDPanelComponent implements IFixedComponent, IComponentProxy<ComponentProxy<ClosableComponent<ToggleButton, ComponentProxy<T>>>>
    {
        protected ComponentProxy<ClosableComponent<ToggleButton, ComponentProxy<T>>> closable;
        protected IButtonRenderer<Boolean> titleRenderer;
        protected IPanelRenderer<Boolean> panelRenderer;
        protected int border;
        
        public HUDPanelComponent(final IToggleable state, final Animation animation, final ITheme theme, final IBoolean renderState, final int border) {
            this.border = border;
            this.panelRenderer = theme.getPanelRenderer(Boolean.class, 0, 0);
            this.titleRenderer = theme.getButtonRenderer(Boolean.class, 0, 0, true);
            this.closable = (ComponentProxy<ClosableComponent<ToggleButton, ComponentProxy<T>>>)HUDPanel.this.getWrappedDragComponent((IComponent)new ClosableComponent((IComponent)new ToggleButton(new Labeled(HUDPanel.this.component.getTitle(), null, () -> HUDPanel.this.component.isVisible()), (IToggleable)new IToggleable() {
                public boolean isOn() {
                    return state.isOn();
                }
                
                public void toggle() {
                }
            }, new IButtonRendererProxy<Boolean>() {
                @Override
                public void renderButton(final Context context, final String title, final boolean focus, final Boolean state) {
                    if (renderState.isOn()) {
                        super.renderButton(context, title, focus, state);
                    }
                }
                
                @Override
                public IButtonRenderer<Boolean> getRenderer() {
                    return HUDPanelComponent.this.titleRenderer;
                }
            }), (IComponent)new ComponentProxy<T>(HUDPanel.this.component) {
                public int getHeight(final int height) {
                    return height + 2 * border;
                }
                
                public Context getContext(final Context context) {
                    return new Context(context, context.getSize().width - 2 * border, new Point(border, border), context.hasFocus(), context.onTop());
                }
            }, () -> state.isOn(), new AnimatedToggleable(state, animation), (IPanelRenderer<U>)new IPanelRendererProxy<Boolean>() {
                @Override
                public void renderBackground(final Context context, final boolean focus) {
                    if (renderState.isOn()) {
                        super.renderBackground(context, focus);
                    }
                }
                
                @Override
                public void renderPanelOverlay(final Context context, final boolean focus, final Boolean state, final boolean open) {
                    if (renderState.isOn()) {
                        super.renderPanelOverlay(context, focus, state, open);
                    }
                }
                
                @Override
                public void renderTitleOverlay(final Context context, final boolean focus, final Boolean state, final boolean open) {
                    if (renderState.isOn()) {
                        super.renderTitleOverlay(context, focus, state, open);
                    }
                }
                
                @Override
                public IPanelRenderer<Boolean> getRenderer() {
                    return HUDPanelComponent.this.panelRenderer;
                }
            }, false));
        }
        
        public ComponentProxy<ClosableComponent<ToggleButton, ComponentProxy<T>>> getComponent() {
            return this.closable;
        }
        
        public Point getPosition(final IInterface inter) {
            final Point pos = HUDPanel.this.component.getPosition(inter);
            pos.translate(-this.panelRenderer.getLeft() - this.border, -this.panelRenderer.getTop() - this.titleRenderer.getDefaultHeight() - this.panelRenderer.getBorder() - this.border);
            return pos;
        }
        
        public void setPosition(final IInterface inter, final Point position) {
            position.translate(this.panelRenderer.getLeft() + this.border, this.panelRenderer.getTop() + this.titleRenderer.getDefaultHeight() + this.panelRenderer.getBorder() + this.border);
            HUDPanel.this.component.setPosition(inter, position);
        }
        
        public int getWidth(final IInterface inter) {
            return HUDPanel.this.component.getWidth(inter) + this.panelRenderer.getLeft() + this.panelRenderer.getRight() + 2 * this.border;
        }
        
        public boolean savesState() {
            return HUDPanel.this.component.savesState();
        }
        
        public void saveConfig(final IInterface inter, final IPanelConfig config) {
            HUDPanel.this.component.saveConfig(inter, config);
        }
        
        public void loadConfig(final IInterface inter, final IPanelConfig config) {
            HUDPanel.this.component.loadConfig(inter, config);
        }
        
        public String getConfigName() {
            return HUDPanel.this.component.getConfigName();
        }
    }
}
