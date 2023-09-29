//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.base;

public class ConstantToggleable implements IToggleable
{
    protected boolean value;
    
    public ConstantToggleable(final boolean value) {
        this.value = value;
    }
    
    @Override
    public boolean isOn() {
        return this.value;
    }
    
    @Override
    public void toggle() {
    }
}
