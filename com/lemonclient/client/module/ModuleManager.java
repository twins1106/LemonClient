//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module;

import com.lemonclient.api.util.misc.*;
import java.util.*;

public class ModuleManager
{
    private static final String modulePath = "com.lemonclient.client.module.modules";
    private static final LinkedHashMap<Class<? extends Module>, Module> modulesClassMap;
    private static final LinkedHashMap<String, Module> modulesNameMap;
    
    public static void init() {
        for (final Category category : Category.values()) {
            for (final Class<?> clazz : ClassUtil.findClassesInPath("com.lemonclient.client.module.modules." + category.toString().toLowerCase())) {
                if (clazz == null) {
                    continue;
                }
                if (!Module.class.isAssignableFrom(clazz)) {
                    continue;
                }
                try {
                    final Module module = (Module)clazz.newInstance();
                    addMod(module);
                }
                catch (InstantiationException | IllegalAccessException ex2) {
                    final ReflectiveOperationException ex;
                    final ReflectiveOperationException e = ex;
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static void addMod(final Module module) {
        ModuleManager.modulesClassMap.put(module.getClass(), module);
        ModuleManager.modulesNameMap.put(module.getName().toLowerCase(Locale.ROOT), module);
    }
    
    public static Collection<Module> getModules() {
        return ModuleManager.modulesClassMap.values();
    }
    
    public static ArrayList<Module> getModulesInCategory(final Category category) {
        final ArrayList<Module> list = new ArrayList<Module>();
        for (final Module module : ModuleManager.modulesClassMap.values()) {
            if (!module.getCategory().equals((Object)category)) {
                continue;
            }
            list.add(module);
        }
        return list;
    }
    
    public static <T extends Module> T getModule(final Class<T> clazz) {
        return (T)ModuleManager.modulesClassMap.get(clazz);
    }
    
    public static Module getModule(final String name) {
        if (name == null) {
            return null;
        }
        return ModuleManager.modulesNameMap.get(name.toLowerCase(Locale.ROOT));
    }
    
    public static boolean isModuleEnabled(final Class<? extends Module> clazz) {
        final Module module = getModule(clazz);
        return module != null && module.isEnabled();
    }
    
    public static boolean isModuleEnabled(final String name) {
        final Module module = getModule(name);
        return module != null && module.isEnabled();
    }
    
    static {
        modulesClassMap = new LinkedHashMap<Class<? extends Module>, Module>();
        modulesNameMap = new LinkedHashMap<String, Module>();
    }
}
