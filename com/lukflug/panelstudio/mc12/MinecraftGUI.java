//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.mc12;

import net.minecraft.client.gui.*;
import java.awt.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import com.lukflug.panelstudio.container.*;

public abstract class MinecraftGUI extends GuiScreen
{
    private Point mouse;
    private boolean lButton;
    private boolean rButton;
    private long lastTime;
    
    public MinecraftGUI() {
        this.mouse = new Point();
        this.lButton = false;
        this.rButton = false;
    }
    
    public void enterGUI() {
        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)this);
    }
    
    public void exitGUI() {
        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
    }
    
    protected void renderGUI() {
        this.lastTime = System.currentTimeMillis();
        this.getInterface().begin(true);
        this.getGUI().render();
        this.getInterface().end(true);
    }
    
    public void initGui() {
        this.getGUI().enter();
    }
    
    public void onGuiClosed() {
        this.getGUI().exit();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.mouse = this.getInterface().screenToGui(new Point(Mouse.getX(), Mouse.getY()));
        this.renderGUI();
        final int scroll = Mouse.getDWheel();
        if (scroll != 0) {
            if (scroll > 0) {
                this.getGUI().handleScroll(-this.getScrollSpeed());
            }
            else {
                this.getGUI().handleScroll(this.getScrollSpeed());
            }
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        this.mouse = this.getInterface().screenToGui(new Point(Mouse.getX(), Mouse.getY()));
        switch (clickedButton) {
            case 0: {
                this.lButton = true;
                break;
            }
            case 1: {
                this.rButton = true;
                break;
            }
        }
        this.getGUI().handleButton(clickedButton);
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
        this.mouse = this.getInterface().screenToGui(new Point(Mouse.getX(), Mouse.getY()));
        switch (releaseButton) {
            case 0: {
                this.lButton = false;
                break;
            }
            case 1: {
                this.rButton = false;
                break;
            }
        }
        this.getGUI().handleButton(releaseButton);
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
        if (keyCode == 1) {
            this.exitGUI();
        }
        else {
            this.getGUI().handleKey(keyCode);
            this.getGUI().handleChar(typedChar);
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    protected abstract GUI getGUI();
    
    protected abstract GUIInterface getInterface();
    
    protected abstract int getScrollSpeed();
    
    public abstract class GUIInterface extends GLInterface
    {
        public GUIInterface(final boolean clipX) {
            super(clipX);
        }
        
        public long getTime() {
            return MinecraftGUI.this.lastTime;
        }
        
        public boolean getButton(final int button) {
            switch (button) {
                case 0: {
                    return MinecraftGUI.this.lButton;
                }
                case 1: {
                    return MinecraftGUI.this.rButton;
                }
                default: {
                    return false;
                }
            }
        }
        
        public Point getMouse() {
            return new Point(MinecraftGUI.this.mouse);
        }
        
        protected float getZLevel() {
            return MinecraftGUI.this.zLevel;
        }
    }
}
