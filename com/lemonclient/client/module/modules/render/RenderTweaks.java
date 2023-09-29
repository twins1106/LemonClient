//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;

@Module.Declaration(name = "RenderTweaks", category = Category.Render)
public class RenderTweaks extends Module
{
    public BooleanSetting viewClip;
    BooleanSetting nekoAnimation;
    BooleanSetting lowOffhand;
    DoubleSetting lowOffhandSlider;
    BooleanSetting fovChanger;
    IntegerSetting fovChangerSlider;
    ItemRenderer itemRenderer;
    private float oldFOV;
    
    public RenderTweaks() {
        this.viewClip = this.registerBoolean("View Clip", false);
        this.nekoAnimation = this.registerBoolean("Neko Animation", false);
        this.lowOffhand = this.registerBoolean("Low Offhand", false);
        this.lowOffhandSlider = this.registerDouble("Offhand Height", 1.0, 0.1, 1.0);
        this.fovChanger = this.registerBoolean("FOV", false);
        this.fovChangerSlider = this.registerInteger("FOV Slider", 90, 70, 200);
        this.itemRenderer = RenderTweaks.mc.entityRenderer.itemRenderer;
    }
    
    public void onUpdate() {
        if ((boolean)this.nekoAnimation.getValue() && RenderTweaks.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && RenderTweaks.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            RenderTweaks.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            RenderTweaks.mc.entityRenderer.itemRenderer.itemStackMainHand = RenderTweaks.mc.player.getHeldItemMainhand();
        }
        if (this.lowOffhand.getValue()) {
            this.itemRenderer.equippedProgressOffHand = ((Double)this.lowOffhandSlider.getValue()).floatValue();
        }
        if (this.fovChanger.getValue()) {
            RenderTweaks.mc.gameSettings.fovSetting = (float)(int)this.fovChangerSlider.getValue();
        }
        if (!(boolean)this.fovChanger.getValue()) {
            RenderTweaks.mc.gameSettings.fovSetting = this.oldFOV;
        }
    }
    
    public void onEnable() {
        this.oldFOV = RenderTweaks.mc.gameSettings.fovSetting;
    }
    
    public void onDisable() {
        RenderTweaks.mc.gameSettings.fovSetting = this.oldFOV;
    }
}
