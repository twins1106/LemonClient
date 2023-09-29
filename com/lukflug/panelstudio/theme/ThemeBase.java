//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import java.awt.*;
import com.lukflug.panelstudio.base.*;

public abstract class ThemeBase implements ITheme
{
    protected final IColorScheme scheme;
    private Color overrideColor;
    
    public ThemeBase(final IColorScheme scheme) {
        this.overrideColor = null;
        this.scheme = scheme;
    }
    
    public void loadAssets(final IInterface inter) {
    }
    
    public void overrideMainColor(final Color color) {
        this.overrideColor = color;
    }
    
    public void restoreMainColor() {
        this.overrideColor = null;
    }
    
    protected Color getColor(final Color color) {
        if (this.overrideColor == null) {
            return color;
        }
        return this.overrideColor;
    }
}
