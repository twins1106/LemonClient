//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.setting;

import com.lemonclient.client.module.*;
import java.util.function.*;
import java.util.*;
import java.util.stream.*;

public abstract class Setting<T>
{
    private T value;
    private final String name;
    private final String configName;
    private final Module module;
    private Supplier<Boolean> isVisible;
    private final List<Setting<?>> subSettings;
    
    public Setting(final T value, final String name, final String configName, final Module module, final Supplier<Boolean> isVisible) {
        this.subSettings = new ArrayList<Setting<?>>();
        this.value = value;
        this.name = name;
        this.configName = configName;
        this.module = module;
        this.isVisible = isVisible;
    }
    
    public void setVisible(final Supplier<Boolean> vis) {
        this.isVisible = vis;
    }
    
    public Setting(final T value, final String name, final Module module) {
        this(value, name, name.replace(" ", ""), module, () -> true);
    }
    
    public T getValue() {
        return this.value;
    }
    
    public void setValue(final T value) {
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getConfigName() {
        return this.configName;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public boolean isVisible() {
        return this.isVisible.get();
    }
    
    public Stream<Setting<?>> getSubSettings() {
        return this.subSettings.stream();
    }
    
    public void addSubSetting(final Setting<?> setting) {
        this.subSettings.add(setting);
    }
}
