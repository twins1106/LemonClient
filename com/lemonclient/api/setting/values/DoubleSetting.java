//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.setting.values;

import com.lemonclient.api.setting.*;
import com.lemonclient.client.module.*;
import java.util.function.*;

public class DoubleSetting extends Setting<Double>
{
    private final double min;
    private final double max;
    
    public DoubleSetting(final String name, final Module module, final double value, final double min, final double max) {
        super((Object)value, name, module);
        this.min = min;
        this.max = max;
    }
    
    public DoubleSetting(final String name, final String configName, final Module module, final Supplier<Boolean> isVisible, final double value, final double min, final double max) {
        super((Object)value, name, configName, module, (Supplier)isVisible);
        this.min = min;
        this.max = max;
    }
    
    public double getMin() {
        return this.min;
    }
    
    public double getMax() {
        return this.max;
    }
}
