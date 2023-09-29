//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.base;

import java.awt.*;

public final class Description
{
    private final Rectangle componentPos;
    private final Rectangle panelPos;
    private final String content;
    
    public Description(final Rectangle position, final String content) {
        this.componentPos = position;
        this.panelPos = position;
        this.content = content;
    }
    
    public Description(final Description description, final Rectangle position) {
        this.componentPos = description.componentPos;
        this.panelPos = position;
        this.content = description.content;
    }
    
    public Rectangle getComponentPos() {
        return this.componentPos;
    }
    
    public Rectangle getPanelPos() {
        return this.panelPos;
    }
    
    public String getContent() {
        return this.content;
    }
}
