//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.gui;

import com.lemonclient.client.module.*;
import java.util.*;
import com.lemonclient.client.*;
import java.util.function.*;
import com.lemonclient.api.util.render.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.setting.*;

@Module.Declaration(name = "ClickGUI", category = Category.GUI, bind = 25, drawn = false)
public class ClickGuiModule extends Module
{
    public IntegerSetting scrollSpeed;
    public IntegerSetting animationSpeed;
    public ModeSetting scrolling;
    public BooleanSetting showHUD;
    public BooleanSetting csgoLayout;
    public ModeSetting theme;
    
    public ClickGuiModule() {
        this.scrollSpeed = this.registerInteger("Scroll Speed", 10, 1, 20);
        this.animationSpeed = this.registerInteger("Animation Speed", 300, 0, 1000);
        this.scrolling = this.registerMode("Scrolling", (List)Arrays.asList("Screen", "Container"), "Container");
        this.showHUD = this.registerBoolean("Show HUD Panels", false);
        this.csgoLayout = this.registerBoolean("CSGO Layout", false);
        this.theme = this.registerMode("Skin", (List)Arrays.asList("2.2", "2.1.2"), "2.1.2");
    }
    
    public void onEnable() {
        LemonClient.INSTANCE.gameSenseGUI.enterGUI();
        this.disable();
    }
    
    public ColorSetting registerColor(final String name, final String configName, final Supplier<Boolean> isVisible, final boolean rainbow, final boolean rainbowEnabled, final boolean alphaEnabled, final GSColor value) {
        final ColorSetting setting = new ColorSetting(name, configName, (Module)this, (Supplier)isVisible, rainbow, rainbowEnabled, alphaEnabled, value);
        SettingsManager.addSetting((Setting)setting);
        return setting;
    }
}
