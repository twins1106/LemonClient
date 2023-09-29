//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.base;

import java.awt.*;

public interface IInterface
{
    public static final int LBUTTON = 0;
    public static final int RBUTTON = 1;
    public static final int SHIFT = 0;
    public static final int CTRL = 1;
    public static final int ALT = 2;
    public static final int SUPER = 3;
    
    long getTime();
    
    Point getMouse();
    
    boolean getButton(final int p0);
    
    boolean getModifier(final int p0);
    
    void drawString(final Point p0, final int p1, final String p2, final Color p3);
    
    int getFontWidth(final int p0, final String p1);
    
    void fillTriangle(final Point p0, final Point p1, final Point p2, final Color p3, final Color p4, final Color p5);
    
    void drawLine(final Point p0, final Point p1, final Color p2, final Color p3);
    
    void fillRect(final Rectangle p0, final Color p1, final Color p2, final Color p3, final Color p4);
    
    void drawRect(final Rectangle p0, final Color p1, final Color p2, final Color p3, final Color p4);
    
    int loadImage(final String p0);
    
    default void drawImage(final Rectangle r, final int rotation, final boolean parity, final int image) {
        this.drawImage(r, rotation, parity, image, new Color(255, 255, 255));
    }
    
    void drawImage(final Rectangle p0, final int p1, final boolean p2, final int p3, final Color p4);
    
    Dimension getWindowSize();
    
    void window(final Rectangle p0);
    
    void restore();
}
