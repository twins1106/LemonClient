//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.*;

@FunctionalInterface
public interface IHorizontalComponentProxy<T extends IHorizontalComponent> extends IComponentProxy<T>, IHorizontalComponent
{
    default int getWidth(final IInterface inter) {
        return ((IHorizontalComponent)this.getComponent()).getWidth(inter);
    }
    
    default int getWeight() {
        return ((IHorizontalComponent)this.getComponent()).getWeight();
    }
}
