//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.gui;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.render.*;
import com.lemonclient.api.util.misc.*;
import java.util.*;
import net.minecraft.util.text.*;

@Module.Declaration(name = "Colors", category = Category.GUI, drawn = false)
public class ColorMain extends Module
{
    public ColorSetting enabledColor;
    public BooleanSetting customFont;
    public BooleanSetting textFont;
    public ModeSetting friendColor;
    public ModeSetting enemyColor;
    public ModeSetting chatModuleColor;
    public ModeSetting chatEnableColor;
    public ModeSetting chatDisableColor;
    public ModeSetting colorModel;
    
    public ColorMain() {
        this.enabledColor = this.registerColor("Main Color", new GSColor(255, 0, 0, 255));
        this.customFont = this.registerBoolean("Custom Font", true);
        this.textFont = this.registerBoolean("Custom Text", false);
        this.friendColor = this.registerMode("Friend Color", ColorUtil.colors, "Blue");
        this.enemyColor = this.registerMode("Enemy Color", ColorUtil.colors, "Red");
        this.chatModuleColor = this.registerMode("Msg Module", ColorUtil.colors, "Aqua");
        this.chatEnableColor = this.registerMode("Msg Enable", ColorUtil.colors, "Green");
        this.chatDisableColor = this.registerMode("Msg Disable", ColorUtil.colors, "Red");
        this.colorModel = this.registerMode("Color Model", (List)Arrays.asList("RGB", "HSB"), "HSB");
    }
    
    public void onEnable() {
        this.disable();
    }
    
    public TextFormatting getFriendColor() {
        return ColorUtil.settingToTextFormatting(this.friendColor);
    }
    
    public TextFormatting getEnemyColor() {
        return ColorUtil.settingToTextFormatting(this.enemyColor);
    }
    
    public TextFormatting getModuleColor() {
        return ColorUtil.settingToTextFormatting(this.chatModuleColor);
    }
    
    public TextFormatting getEnabledColor() {
        return ColorUtil.settingToTextFormatting(this.chatEnableColor);
    }
    
    public TextFormatting getDisabledColor() {
        return ColorUtil.settingToTextFormatting(this.chatDisableColor);
    }
    
    public GSColor getFriendGSColor() {
        return new GSColor(ColorUtil.settingToColor(this.friendColor));
    }
    
    public GSColor getEnemyGSColor() {
        return new GSColor(ColorUtil.settingToColor(this.enemyColor));
    }
}
