//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.base;

public class SimpleToggleable extends ConstantToggleable
{
    public SimpleToggleable(final boolean value) {
        super(value);
    }
    
    public void toggle() {
        this.value = !this.value;
    }
}
