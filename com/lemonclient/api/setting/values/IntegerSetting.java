//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.setting.values;

import com.lemonclient.api.setting.*;
import com.lemonclient.client.module.*;
import java.util.function.*;

public class IntegerSetting extends Setting<Integer>
{
    private final int min;
    private final int max;
    
    public IntegerSetting(final String name, final Module module, final int value, final int min, final int max) {
        super((Object)value, name, module);
        this.min = min;
        this.max = max;
    }
    
    public IntegerSetting(final String name, final String configName, final Module module, final Supplier<Boolean> isVisible, final int value, final int min, final int max) {
        super((Object)value, name, configName, module, (Supplier)isVisible);
        this.min = min;
        this.max = max;
    }
    
    public int getMin() {
        return this.min;
    }
    
    public int getMax() {
        return this.max;
    }
}
