//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;

public interface IContainerRenderer
{
    default void renderBackground(final Context context, final boolean focus) {
    }
    
    default int getBorder() {
        return 0;
    }
    
    default int getLeft() {
        return 0;
    }
    
    default int getRight() {
        return 0;
    }
    
    default int getTop() {
        return 0;
    }
    
    default int getBottom() {
        return 0;
    }
}
