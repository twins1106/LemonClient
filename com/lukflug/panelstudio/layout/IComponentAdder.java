//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.layout;

import com.lukflug.panelstudio.theme.*;
import java.awt.*;
import java.util.function.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.component.*;

public interface IComponentAdder
{
     <S extends IComponent, T extends IComponent> void addComponent(final S p0, final T p1, final ThemeTuple p2, final Point p3, final int p4, final Supplier<Animation> p5);
    
    void addPopup(final IFixedComponent p0);
}
