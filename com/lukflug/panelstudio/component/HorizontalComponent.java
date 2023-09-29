//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.*;

public class HorizontalComponent<T extends IComponent> extends ComponentProxy<T> implements IHorizontalComponent
{
    protected int width;
    protected int weight;
    
    public HorizontalComponent(final T component, final int width, final int weight) {
        super((IComponent)component);
        this.width = width;
        this.weight = weight;
    }
    
    public int getWidth(final IInterface inter) {
        return this.width;
    }
    
    public int getWeight() {
        return this.weight;
    }
}
