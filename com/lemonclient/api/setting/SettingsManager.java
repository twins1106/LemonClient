//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.setting;

import com.lemonclient.client.module.*;
import java.util.*;
import java.util.stream.*;

public class SettingsManager
{
    private static final ArrayList<Setting> settings;
    
    public static void addSetting(final Setting setting) {
        SettingsManager.settings.add(setting);
    }
    
    public static ArrayList<Setting> getSettings() {
        return SettingsManager.settings;
    }
    
    public static List<Setting> getSettingsForModule(final Module module) {
        return SettingsManager.settings.stream().filter(setting -> setting.getModule().equals(module)).collect((Collector<? super Object, ?, List<Setting>>)Collectors.toList());
    }
    
    static {
        settings = new ArrayList<Setting>();
    }
}
