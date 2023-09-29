//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.mc12;

import com.lukflug.panelstudio.hud.*;
import com.lukflug.panelstudio.container.*;

public abstract class MinecraftHUDGUI extends MinecraftGUI
{
    private boolean guiOpened;
    
    public MinecraftHUDGUI() {
        this.guiOpened = false;
    }
    
    public void enterGUI() {
        if (!this.getGUI().getGUIVisibility().isOn()) {
            this.getGUI().getGUIVisibility().toggle();
        }
        if (!this.getGUI().getHUDVisibility().isOn()) {
            this.getGUI().getHUDVisibility().toggle();
        }
        super.enterGUI();
    }
    
    public void exitGUI() {
        if (this.getGUI().getGUIVisibility().isOn()) {
            this.getGUI().getGUIVisibility().toggle();
        }
        if (this.getGUI().getHUDVisibility().isOn()) {
            this.getGUI().getHUDVisibility().toggle();
        }
        super.exitGUI();
    }
    
    public void enterHUDEditor() {
        if (this.getGUI().getGUIVisibility().isOn()) {
            this.getGUI().getGUIVisibility().toggle();
        }
        if (!this.getGUI().getHUDVisibility().isOn()) {
            this.getGUI().getHUDVisibility().toggle();
        }
        super.enterGUI();
    }
    
    public void initGui() {
    }
    
    public void onGuiClosed() {
    }
    
    protected void renderGUI() {
        if (!this.guiOpened) {
            this.getGUI().enter();
        }
        this.guiOpened = true;
        super.renderGUI();
    }
    
    public void render() {
        if (!this.getGUI().getGUIVisibility().isOn() && !this.getGUI().getHUDVisibility().isOn()) {
            this.renderGUI();
        }
    }
    
    public void handleKeyEvent(final int scancode) {
        if (scancode != 1 && !this.getGUI().getGUIVisibility().isOn() && !this.getGUI().getGUIVisibility().isOn()) {
            this.getGUI().handleKey(scancode);
        }
    }
    
    protected abstract HUDGUI getGUI();
}
