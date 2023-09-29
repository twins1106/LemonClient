//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.base;

import java.util.function.*;

public class SettingsAnimation extends Animation
{
    protected final Supplier<Integer> speed;
    
    public SettingsAnimation(final Supplier<Integer> speed, final Supplier<Long> time) {
        super((Supplier)time);
        this.speed = speed;
    }
    
    protected int getSpeed() {
        return this.speed.get();
    }
}
