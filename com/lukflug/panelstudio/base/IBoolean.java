//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.base;

import java.util.function.*;

@FunctionalInterface
public interface IBoolean extends BooleanSupplier, Supplier<Boolean>, Predicate<Void>
{
    boolean isOn();
    
    default boolean getAsBoolean() {
        return this.isOn();
    }
    
    default Boolean get() {
        return this.isOn();
    }
    
    default boolean test(final Void t) {
        return this.isOn();
    }
}
