//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

public class ComponentProxy<T extends IComponent> implements IComponentProxy<T>
{
    protected final T component;
    
    public ComponentProxy(final T component) {
        this.component = component;
    }
    
    @Override
    public T getComponent() {
        return this.component;
    }
}
