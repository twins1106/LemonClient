//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module;

import me.zero.alpine.listener.*;
import net.minecraft.client.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.client.*;
import com.lemonclient.client.module.modules.gui.*;
import com.mojang.realmsclient.gui.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.util.text.*;
import com.lemonclient.api.setting.*;
import java.util.function.*;
import java.util.*;
import com.lemonclient.api.util.render.*;
import com.lemonclient.api.setting.values.*;
import java.lang.annotation.*;

public abstract class Module implements Listenable
{
    protected static final Minecraft mc;
    private final String name;
    private final Category category;
    private final int priority;
    private int bind;
    private boolean enabled;
    private boolean drawn;
    private boolean toggleMsg;
    private String disabledMessage;
    
    public Module() {
        this.name = this.getDeclaration().name();
        this.category = this.getDeclaration().category();
        this.priority = this.getDeclaration().priority();
        this.bind = this.getDeclaration().bind();
        this.enabled = this.getDeclaration().enabled();
        this.drawn = this.getDeclaration().drawn();
        this.toggleMsg = this.getDeclaration().toggleMsg();
        this.disabledMessage = "";
    }
    
    private Declaration getDeclaration() {
        return this.getClass().getAnnotation(Declaration.class);
    }
    
    public void onTick() {
    }
    
    public void fast() {
    }
    
    protected void onEnable() {
    }
    
    protected void onDisable() {
    }
    
    public void onUpdate() {
    }
    
    public void onRender() {
    }
    
    public void onWorldRender(final RenderEvent event) {
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public void setDisabledMessage(final String message) {
        this.disabledMessage = message;
    }
    
    public void enable() {
        this.setEnabled(true);
        LemonClient.EVENT_BUS.subscribe((Listenable)this);
        this.onEnable();
        if (this.toggleMsg && Module.mc.world != null && Module.mc.player != null) {
            MessageBus.sendClientDeleteMessage(ModuleManager.getModule(ColorMain.class).getModuleColor() + this.name + ChatFormatting.GRAY + " turned " + ModuleManager.getModule(ColorMain.class).getEnabledColor() + "ON" + ChatFormatting.GRAY + "!", "Module", 0);
        }
    }
    
    public void disable() {
        this.setEnabled(false);
        LemonClient.EVENT_BUS.unsubscribe((Listenable)this);
        this.onDisable();
        if (this.toggleMsg && Module.mc.world != null && Module.mc.player != null) {
            MessageBus.sendClientDeleteMessage(this.disabledMessage.equals("") ? (ModuleManager.getModule(ColorMain.class).getModuleColor() + this.name + ChatFormatting.GRAY + " turned " + ModuleManager.getModule(ColorMain.class).getDisabledColor() + "OFF" + TextFormatting.GRAY + "!") : this.disabledMessage, "Module", 0);
        }
        this.setDisabledMessage(ModuleManager.getModule(ColorMain.class).getModuleColor() + this.name + ChatFormatting.GRAY + " turned " + ModuleManager.getModule(ColorMain.class).getDisabledColor() + "OFF" + TextFormatting.GRAY + "!");
    }
    
    public static int getIdFromString(String name) {
        StringBuilder s = new StringBuilder();
        name = name.replace("\u79ae", "e");
        final String blacklist = "[^a-z]";
        for (int i = 0; i < name.length(); ++i) {
            s.append(Integer.parseInt(String.valueOf(name.charAt(i)).replaceAll(blacklist, "e"), 36));
        }
        try {
            s = new StringBuilder(s.substring(0, 8));
        }
        catch (StringIndexOutOfBoundsException ignored) {
            s = new StringBuilder(Integer.MAX_VALUE);
        }
        return Integer.MAX_VALUE - Integer.parseInt(s.toString().toLowerCase());
    }
    
    public void toggle() {
        if (this.isEnabled()) {
            this.disable();
        }
        else {
            this.enable();
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public int getBind() {
        return this.bind;
    }
    
    public void setBind(final int bind) {
        if (bind >= 0 && bind <= 255) {
            this.bind = bind;
        }
    }
    
    public String getHudInfo() {
        return "";
    }
    
    public boolean isDrawn() {
        return this.drawn;
    }
    
    public void setDrawn(final boolean drawn) {
        this.drawn = drawn;
    }
    
    public boolean isToggleMsg() {
        return this.toggleMsg;
    }
    
    public void setToggleMsg(final boolean toggleMsg) {
        this.toggleMsg = toggleMsg;
    }
    
    protected IntegerSetting registerInteger(final String name, final int value, final int min, final int max) {
        final IntegerSetting integerSetting = new IntegerSetting(name, this, value, min, max);
        SettingsManager.addSetting((Setting)integerSetting);
        return integerSetting;
    }
    
    protected IntegerSetting registerInteger(final String name, final int value, final int min, final int max, final Supplier<Boolean> dipendent) {
        final IntegerSetting integerSetting = new IntegerSetting(name, this, value, min, max);
        integerSetting.setVisible((Supplier)dipendent);
        SettingsManager.addSetting((Setting)integerSetting);
        return integerSetting;
    }
    
    protected StringSetting registerString(final String name, final String value) {
        final StringSetting stringSetting = new StringSetting(name, this, value);
        SettingsManager.addSetting((Setting)stringSetting);
        return stringSetting;
    }
    
    protected StringSetting registerString(final String name, final String value, final Supplier<Boolean> dipendent) {
        final StringSetting stringSetting = new StringSetting(name, this, value);
        stringSetting.setVisible((Supplier)dipendent);
        SettingsManager.addSetting((Setting)stringSetting);
        return stringSetting;
    }
    
    protected DoubleSetting registerDouble(final String name, final double value, final double min, final double max) {
        final DoubleSetting doubleSetting = new DoubleSetting(name, this, value, min, max);
        SettingsManager.addSetting((Setting)doubleSetting);
        return doubleSetting;
    }
    
    protected DoubleSetting registerDouble(final String name, final double value, final double min, final double max, final Supplier<Boolean> dipendent) {
        final DoubleSetting doubleSetting = new DoubleSetting(name, this, value, min, max);
        doubleSetting.setVisible((Supplier)dipendent);
        SettingsManager.addSetting((Setting)doubleSetting);
        return doubleSetting;
    }
    
    protected BooleanSetting registerBoolean(final String name, final boolean value) {
        final BooleanSetting booleanSetting = new BooleanSetting(name, this, value);
        SettingsManager.addSetting((Setting)booleanSetting);
        return booleanSetting;
    }
    
    protected BooleanSetting registerBoolean(final String name, final boolean value, final Supplier<Boolean> dipendent) {
        final BooleanSetting booleanSetting = new BooleanSetting(name, this, value);
        booleanSetting.setVisible((Supplier)dipendent);
        SettingsManager.addSetting((Setting)booleanSetting);
        return booleanSetting;
    }
    
    protected ModeSetting registerMode(final String name, final List<String> modes, final String value) {
        final ModeSetting modeSetting = new ModeSetting(name, this, value, (List)modes);
        SettingsManager.addSetting((Setting)modeSetting);
        return modeSetting;
    }
    
    protected ModeSetting registerMode(final String name, final List<String> modes, final String value, final Supplier<Boolean> dipendent) {
        final ModeSetting modeSetting = new ModeSetting(name, this, value, (List)modes);
        modeSetting.setVisible((Supplier)dipendent);
        SettingsManager.addSetting((Setting)modeSetting);
        return modeSetting;
    }
    
    protected ColorSetting registerColor(final String name, final GSColor color) {
        final ColorSetting colorSetting = new ColorSetting(name, this, false, color);
        SettingsManager.addSetting((Setting)colorSetting);
        return colorSetting;
    }
    
    protected ColorSetting registerColor(final String name, final GSColor color, final Supplier<Boolean> dipendent) {
        final ColorSetting colorSetting = new ColorSetting(name, this, false, color);
        colorSetting.setVisible((Supplier)dipendent);
        colorSetting.alphaEnabled();
        SettingsManager.addSetting((Setting)colorSetting);
        return colorSetting;
    }
    
    protected ColorSetting registerColor(final String name, final GSColor color, final Supplier<Boolean> dipendent, final Boolean alphaEnabled) {
        final ColorSetting colorSetting = new ColorSetting(name, this, false, color, (boolean)alphaEnabled);
        colorSetting.setVisible((Supplier)dipendent);
        colorSetting.alphaEnabled();
        SettingsManager.addSetting((Setting)colorSetting);
        return colorSetting;
    }
    
    protected ColorSetting registerColor(final String name) {
        return this.registerColor(name, new GSColor(90, 145, 240));
    }
    
    protected ColorSetting registerColor(final String name, final Supplier<Boolean> dipendent) {
        final ColorSetting color = this.registerColor(name, new GSColor(90, 145, 240));
        color.setVisible((Supplier)dipendent);
        return color;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    public @interface Declaration {
        String name();
        
        Category category();
        
        int priority() default 0;
        
        int bind() default 0;
        
        boolean enabled() default false;
        
        boolean drawn() default true;
        
        boolean toggleMsg() default false;
    }
}
