//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.hud;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.config.*;
import java.awt.*;

public abstract class HUDComponent implements IFixedComponent
{
    protected String title;
    protected IBoolean visible;
    protected String description;
    protected Point position;
    protected String configName;
    
    public HUDComponent(final ILabeled label, final Point position, final String configName) {
        this.title = label.getDisplayName();
        this.position = position;
        this.description = label.getDescription();
        this.configName = configName;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void render(final Context context) {
        context.setHeight(this.getSize(context.getInterface()).height);
        if (this.description != null) {
            context.setDescription(new Description(context.getRect(), this.description));
        }
    }
    
    public void handleButton(final Context context, final int button) {
        context.setHeight(this.getSize(context.getInterface()).height);
    }
    
    public void handleKey(final Context context, final int scancode) {
        context.setHeight(this.getSize(context.getInterface()).height);
    }
    
    public void handleChar(final Context context, final char character) {
        context.setHeight(this.getSize(context.getInterface()).height);
    }
    
    public void handleScroll(final Context context, final int diff) {
        context.setHeight(this.getSize(context.getInterface()).height);
    }
    
    public void getHeight(final Context context) {
        context.setHeight(this.getSize(context.getInterface()).height);
    }
    
    public void enter() {
    }
    
    public void exit() {
    }
    
    public void releaseFocus() {
    }
    
    public boolean isVisible() {
        return true;
    }
    
    public Point getPosition(final IInterface inter) {
        return new Point(this.position);
    }
    
    public void setPosition(final IInterface inter, final Point position) {
        this.position = new Point(position);
    }
    
    public int getWidth(final IInterface inter) {
        return this.getSize(inter).width;
    }
    
    public boolean savesState() {
        return true;
    }
    
    public void saveConfig(final IInterface inter, final IPanelConfig config) {
        config.savePositon(this.position);
    }
    
    public void loadConfig(final IInterface inter, final IPanelConfig config) {
        this.position = config.loadPosition();
    }
    
    public String getConfigName() {
        return this.configName;
    }
    
    public abstract Dimension getSize(final IInterface p0);
}
