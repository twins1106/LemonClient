//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import java.awt.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.config.*;

public class FixedComponent<T extends IComponent> extends ComponentProxy<T> implements IFixedComponent
{
    protected Point position;
    protected int width;
    protected IToggleable state;
    protected boolean savesState;
    protected String configName;
    
    public FixedComponent(final T component, final Point position, final int width, final IToggleable state, final boolean savesState, final String configName) {
        super((IComponent)component);
        this.position = position;
        this.width = width;
        this.state = state;
        this.savesState = savesState;
        this.configName = configName;
    }
    
    public Point getPosition(final IInterface inter) {
        return new Point(this.position);
    }
    
    public void setPosition(final IInterface inter, final Point position) {
        this.position = new Point(position);
    }
    
    public int getWidth(final IInterface inter) {
        return this.width;
    }
    
    public boolean savesState() {
        return this.savesState;
    }
    
    public void saveConfig(final IInterface inter, final IPanelConfig config) {
        config.savePositon(this.position);
        if (this.state != null) {
            config.saveState(this.state.isOn());
        }
    }
    
    public void loadConfig(final IInterface inter, final IPanelConfig config) {
        this.position = config.loadPosition();
        if (this.state != null && this.state.isOn() != config.loadState()) {
            this.state.toggle();
        }
    }
    
    public String getConfigName() {
        return this.configName;
    }
}
