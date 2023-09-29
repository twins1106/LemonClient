//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.setting.values;

import com.lemonclient.api.setting.*;
import com.lemonclient.client.module.*;
import java.util.function.*;

public class BooleanSetting extends Setting<Boolean>
{
    public BooleanSetting(final String name, final Module module, final boolean value) {
        super((Object)value, name, module);
    }
    
    public BooleanSetting(final String name, final String configName, final Module module, final Supplier<Boolean> isVisible, final boolean value) {
        super((Object)value, name, configName, module, (Supplier)isVisible);
    }
}
