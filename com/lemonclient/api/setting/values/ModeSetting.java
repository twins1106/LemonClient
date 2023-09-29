//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.setting.values;

import com.lemonclient.api.setting.*;
import java.util.*;
import com.lemonclient.client.module.*;
import java.util.function.*;

public class ModeSetting extends Setting<String>
{
    private final List<String> modes;
    
    public ModeSetting(final String name, final Module module, final String value, final List<String> modes) {
        super((Object)value, name, module);
        this.modes = modes;
    }
    
    public ModeSetting(final String name, final String configName, final Module module, final String value, final Supplier<Boolean> isVisible, final List<String> modes) {
        super((Object)value, name, configName, module, (Supplier)isVisible);
        this.modes = modes;
    }
    
    public List<String> getModes() {
        return this.modes;
    }
    
    public void increment() {
        int modeIndex = this.modes.indexOf(this.getValue());
        modeIndex = (modeIndex + 1) % this.modes.size();
        this.setValue((Object)this.modes.get(modeIndex));
    }
    
    public void decrement() {
        int modeIndex = this.modes.indexOf(this.getValue());
        if (--modeIndex < 0) {
            modeIndex = this.modes.size() - 1;
        }
        this.setValue((Object)this.modes.get(modeIndex));
    }
}
