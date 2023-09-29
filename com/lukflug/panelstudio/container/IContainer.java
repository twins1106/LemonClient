//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.container;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.base.*;

public interface IContainer<T extends IComponent>
{
    boolean addComponent(final T p0);
    
    boolean addComponent(final T p0, final IBoolean p1);
    
    boolean removeComponent(final T p0);
}
