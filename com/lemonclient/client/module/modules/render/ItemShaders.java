//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.*;
import com.lemonclient.api.util.render.*;
import java.util.function.*;
import java.awt.*;
import com.lemonclient.api.util.render.shaders.impl.fill.*;
import com.lemonclient.api.util.render.shaders.impl.outline.*;
import net.minecraft.client.renderer.*;

@Module.Declaration(name = "ItemShaders", category = Category.Render)
public class ItemShaders extends Module
{
    public ModeSetting glowESP;
    ColorSetting colorESP;
    DoubleSetting radius;
    DoubleSetting quality;
    BooleanSetting GradientAlpha;
    IntegerSetting alphaValue;
    DoubleSetting PIOutline;
    DoubleSetting radOutline;
    DoubleSetting moreGradientOutline;
    DoubleSetting creepyOutline;
    IntegerSetting WaveLenghtOutline;
    IntegerSetting RSTARTOutline;
    IntegerSetting GSTARTOutline;
    IntegerSetting BSTARTOutline;
    ColorSetting colorImgOutline;
    ColorSetting secondColorImgOutline;
    ColorSetting thirdColorImgOutline;
    IntegerSetting NUM_OCTAVESOutline;
    IntegerSetting MaxIterOutline;
    DoubleSetting tauOutline;
    IntegerSetting redOutline;
    DoubleSetting greenOutline;
    DoubleSetting blueOutline;
    DoubleSetting alphaOutline;
    IntegerSetting iterationsOutline;
    DoubleSetting formuparam2Outline;
    DoubleSetting zoomOutline;
    IntegerSetting volumStepsOutline;
    DoubleSetting stepSizeOutline;
    DoubleSetting titleOutline;
    DoubleSetting distfadingOutline;
    DoubleSetting saturationOutline;
    BooleanSetting fadeOutline;
    public ModeSetting fillShader;
    DoubleSetting moreGradientFill;
    DoubleSetting creepyFill;
    IntegerSetting WaveLenghtFIll;
    IntegerSetting RSTARTFill;
    IntegerSetting GSTARTFill;
    IntegerSetting BSTARTFIll;
    ColorSetting colorImgFill;
    ColorSetting secondColorImgFill;
    ColorSetting thirdColorImgFIll;
    IntegerSetting NUM_OCTAVESFill;
    IntegerSetting MaxIterFill;
    DoubleSetting tauFill;
    IntegerSetting redFill;
    DoubleSetting greenFill;
    DoubleSetting blueFill;
    DoubleSetting alphaFill;
    IntegerSetting iterationsFill;
    DoubleSetting formuparam2Fill;
    DoubleSetting zoomFill;
    IntegerSetting volumStepsFill;
    DoubleSetting stepSizeFill;
    DoubleSetting titleFill;
    DoubleSetting distfadingFill;
    DoubleSetting saturationFill;
    BooleanSetting fadeFill;
    DoubleSetting PI;
    DoubleSetting rad;
    DoubleSetting speedFill;
    DoubleSetting speedOutline;
    DoubleSetting duplicateFill;
    DoubleSetting duplicateOutline;
    public BooleanSetting cancelItem;
    @EventHandler
    private final Listener<RenderHand.PreOutline> preOutline;
    @EventHandler
    private final Listener<RenderHand.PostOutline> postOutline;
    @EventHandler
    private final Listener<RenderHand.PreFill> preFill;
    @EventHandler
    private final Listener<RenderHand.PostFill> postFill;
    @EventHandler
    private final Listener<RenderHand.PreBoth> preBoth;
    @EventHandler
    private final Listener<RenderHand.PostBoth> postBoth;
    
    public ItemShaders() {
        this.glowESP = this.registerMode("Glow ESP", (List)Arrays.asList("None", "Color", "Astral", "RainbowCube", "Gradient", "Aqua", "Circle", "Smoke"), "None");
        this.colorESP = this.registerColor("Color ESP", new GSColor(255, 255, 255, 255));
        this.radius = this.registerDouble("Radius ESP", 1.0, 0.0, 5.0);
        this.quality = this.registerDouble("Quality ESP", 1.0, 0.0, 20.0);
        this.GradientAlpha = this.registerBoolean("Gradient Alpha", false);
        this.alphaValue = this.registerInteger("Alpha Outline", 255, 0, 255, () -> !(boolean)this.GradientAlpha.getValue());
        this.PIOutline = this.registerDouble("PI Outline", 3.141592653, 0.0, 10.0, () -> ((String)this.glowESP.getValue()).equals("Circle"));
        this.radOutline = this.registerDouble("RAD Outline", 0.75, 0.0, 5.0, () -> ((String)this.glowESP.getValue()).equals("Circle"));
        this.moreGradientOutline = this.registerDouble("More Gradient", 1.0, 0.0, 10.0, () -> ((String)this.glowESP.getValue()).equals("Gradient"));
        this.creepyOutline = this.registerDouble("Creepy", 1.0, 0.0, 20.0, () -> ((String)this.glowESP.getValue()).equals("Gradient"));
        this.WaveLenghtOutline = this.registerInteger("Wave Lenght", 555, 0, 2000, () -> ((String)this.glowESP.getValue()).equals("RainbowCube"));
        this.RSTARTOutline = this.registerInteger("RSTART", 0, 0, 1000, () -> ((String)this.glowESP.getValue()).equals("RainbowCube"));
        this.GSTARTOutline = this.registerInteger("GSTART", 0, 0, 1000, () -> ((String)this.glowESP.getValue()).equals("RainbowCube"));
        this.BSTARTOutline = this.registerInteger("BSTART", 0, 0, 1000, () -> ((String)this.glowESP.getValue()).equals("RainbowCube"));
        this.colorImgOutline = this.registerColor("Color Img", new GSColor(0, 0, 0, 255), () -> ((String)this.glowESP.getValue()).equals("Aqua") || ((String)this.glowESP.getValue()).equals("Smoke") || ((String)this.glowESP.getValue()).equals("RainbowCube"), Boolean.valueOf(true));
        this.secondColorImgOutline = this.registerColor("Second Color Img", new GSColor(255, 255, 255, 255), () -> ((String)this.glowESP.getValue()).equals("Smoke"));
        this.thirdColorImgOutline = this.registerColor("Third Color Img", new GSColor(255, 255, 255, 255), () -> ((String)this.glowESP.getValue()).equals("Smoke"));
        this.NUM_OCTAVESOutline = this.registerInteger("NUM_OCTAVES", 5, 1, 30, () -> ((String)this.glowESP.getValue()).equals("Smoke"));
        this.MaxIterOutline = this.registerInteger("Max Iter", 5, 0, 30, () -> ((String)this.glowESP.getValue()).equals("Aqua"));
        this.tauOutline = this.registerDouble("TAU", 6.28318530718, 0.0, 20.0, () -> ((String)this.glowESP.getValue()).equals("Aqua"));
        this.redOutline = this.registerInteger("Red", 0, 0, 100, () -> ((String)this.glowESP.getValue()).equals("Astral"));
        this.greenOutline = this.registerDouble("Green", 0.0, 0.0, 5.0, () -> ((String)this.glowESP.getValue()).equals("Astral"));
        this.blueOutline = this.registerDouble("Blue", 0.0, 0.0, 5.0, () -> ((String)this.glowESP.getValue()).equals("Astral"));
        this.alphaOutline = this.registerDouble("Alpha", 1.0, 0.0, 1.0, () -> ((String)this.glowESP.getValue()).equals("Astral") || ((String)this.glowESP.getValue()).equals("Gradient"));
        this.iterationsOutline = this.registerInteger("Iteration", 4, 3, 20, () -> ((String)this.glowESP.getValue()).equals("Astral"));
        this.formuparam2Outline = this.registerDouble("formuparam2", 0.89, 0.0, 1.5, () -> ((String)this.glowESP.getValue()).equals("Astral"));
        this.zoomOutline = this.registerDouble("Zoom", 3.9, 0.0, 20.0, () -> ((String)this.glowESP.getValue()).equals("Astral"));
        this.volumStepsOutline = this.registerInteger("Volum Steps", 10, 0, 10, () -> ((String)this.glowESP.getValue()).equals("Astral"));
        this.stepSizeOutline = this.registerDouble("Step Size", 0.19, 0.0, 0.7, () -> ((String)this.glowESP.getValue()).equals("Astral"));
        this.titleOutline = this.registerDouble("Tile", 0.45, 0.0, 1.3, () -> ((String)this.glowESP.getValue()).equals("Astral"));
        this.distfadingOutline = this.registerDouble("distfading", 0.56, 0.0, 1.0, () -> ((String)this.glowESP.getValue()).equals("Astral"));
        this.saturationOutline = this.registerDouble("saturation", 0.4, 0.0, 3.0, () -> ((String)this.glowESP.getValue()).equals("Astral"));
        this.fadeOutline = this.registerBoolean("Fade Fill", false, () -> ((String)this.glowESP.getValue()).equals("Astral"));
        this.fillShader = this.registerMode("Fill Shader", (List)Arrays.asList("Astral", "Aqua", "Smoke", "RainbowCube", "Gradient", "Fill", "Circle", "Phobos", "None"), "None");
        this.moreGradientFill = this.registerDouble("More Gradient", 1.0, 0.0, 10.0, () -> ((String)this.fillShader.getValue()).equals("Gradient"));
        this.creepyFill = this.registerDouble("Creepy", 1.0, 0.0, 20.0, () -> ((String)this.fillShader.getValue()).equals("Gradient"));
        this.WaveLenghtFIll = this.registerInteger("Wave Lenght", 555, 0, 2000, () -> ((String)this.fillShader.getValue()).equals("RainbowCube"));
        this.RSTARTFill = this.registerInteger("RSTART", 0, 0, 1000, () -> ((String)this.fillShader.getValue()).equals("RainbowCube"));
        this.GSTARTFill = this.registerInteger("GSTART", 0, 0, 1000, () -> ((String)this.fillShader.getValue()).equals("RainbowCube"));
        this.BSTARTFIll = this.registerInteger("BSTART", 0, 0, 1000, () -> ((String)this.fillShader.getValue()).equals("RainbowCube"));
        this.colorImgFill = this.registerColor("Color Img", new GSColor(0, 0, 0, 255), () -> ((String)this.fillShader.getValue()).equals("Aqua") || ((String)this.fillShader.getValue()).equals("Smoke") || ((String)this.fillShader.getValue()).equals("RainbowCube") || ((String)this.fillShader.getValue()).equals("Fill") || ((String)this.fillShader.getValue()).equals("Circle") || ((String)this.fillShader.getValue()).equals("Future"), Boolean.valueOf(true));
        this.secondColorImgFill = this.registerColor("Second Color Img", new GSColor(255, 255, 255, 255), () -> ((String)this.fillShader.getValue()).equals("Smoke"));
        this.thirdColorImgFIll = this.registerColor("Third Color Img", new GSColor(255, 255, 255, 255), () -> ((String)this.fillShader.getValue()).equals("Smoke"));
        this.NUM_OCTAVESFill = this.registerInteger("NUM_OCTAVES", 5, 1, 30, () -> ((String)this.fillShader.getValue()).equals("Smoke"));
        this.MaxIterFill = this.registerInteger("Max Iter", 5, 0, 30, () -> ((String)this.fillShader.getValue()).equals("Aqua"));
        this.tauFill = this.registerDouble("TAU", 6.28318530718, 0.0, 20.0, () -> ((String)this.fillShader.getValue()).equals("Aqua"));
        this.redFill = this.registerInteger("Red", 0, 0, 100, () -> ((String)this.fillShader.getValue()).equals("Astral"));
        this.greenFill = this.registerDouble("Green", 0.0, 0.0, 5.0, () -> ((String)this.fillShader.getValue()).equals("Astral"));
        this.blueFill = this.registerDouble("Blue", 0.0, 0.0, 5.0, () -> ((String)this.fillShader.getValue()).equals("Astral"));
        this.alphaFill = this.registerDouble("Alpha", 1.0, 0.0, 1.0, () -> ((String)this.fillShader.getValue()).equals("Astral") || ((String)this.fillShader.getValue()).equals("Gradient"));
        this.iterationsFill = this.registerInteger("Iteration", 4, 3, 20, () -> ((String)this.fillShader.getValue()).equals("Astral"));
        this.formuparam2Fill = this.registerDouble("formuparam2", 0.89, 0.0, 1.5, () -> ((String)this.fillShader.getValue()).equals("Astral"));
        this.zoomFill = this.registerDouble("Zoom", 3.9, 0.0, 20.0, () -> ((String)this.fillShader.getValue()).equals("Astral"));
        this.volumStepsFill = this.registerInteger("Volum Steps", 10, 0, 10, () -> ((String)this.fillShader.getValue()).equals("Astral"));
        this.stepSizeFill = this.registerDouble("Step Size", 0.19, 0.0, 0.7, () -> ((String)this.fillShader.getValue()).equals("Astral"));
        this.titleFill = this.registerDouble("Tile", 0.45, 0.0, 1.3, () -> ((String)this.fillShader.getValue()).equals("Astral"));
        this.distfadingFill = this.registerDouble("distfading", 0.56, 0.0, 1.0, () -> ((String)this.fillShader.getValue()).equals("Astral"));
        this.saturationFill = this.registerDouble("saturation", 0.4, 0.0, 3.0, () -> ((String)this.fillShader.getValue()).equals("Astral"));
        this.fadeFill = this.registerBoolean("Fade Fill", false, () -> ((String)this.fillShader.getValue()).equals("Astral"));
        this.PI = this.registerDouble("PI Fill", 3.141592653, 0.0, 10.0, () -> ((String)this.fillShader.getValue()).equals("Circle"));
        this.rad = this.registerDouble("RAD Fill", 0.75, 0.0, 5.0, () -> ((String)this.fillShader.getValue()).equals("Circle"));
        this.speedFill = this.registerDouble("Speed Fill", 0.1, 0.001, 0.1);
        this.speedOutline = this.registerDouble("Speed Outline", 0.1, 0.001, 0.1);
        this.duplicateFill = this.registerDouble("Duplicate Fill", 1.0, 0.0, 5.0);
        this.duplicateOutline = this.registerDouble("Duplicate Outline", 1.0, 0.0, 20.0);
        this.cancelItem = this.registerBoolean("Cancel Item", false);
        this.preOutline = (Listener<RenderHand.PreOutline>)new Listener(event -> {
            if (ItemShaders.mc.world == null || ItemShaders.mc.player == null) {
                return;
            }
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableAlpha();
            final String s = (String)this.glowESP.getValue();
            switch (s) {
                case "Color": {
                    GlowShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "RainbowCube": {
                    RainbowCubeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Gradient": {
                    GradientOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Astral": {
                    AstralOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Aqua": {
                    AquaOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Circle": {
                    CircleOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Smoke": {
                    SmokeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
            }
        }, new Predicate[0]);
        this.postOutline = (Listener<RenderHand.PostOutline>)new Listener(event -> {
            if (ItemShaders.mc.world == null || ItemShaders.mc.player == null) {
                return;
            }
            final String s = (String)this.glowESP.getValue();
            switch (s) {
                case "Color": {
                    GlowShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue());
                    break;
                }
                case "RainbowCube": {
                    RainbowCubeOutlineShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue(), ((Double)this.duplicateOutline.getValue()).floatValue(), (Color)this.colorImgOutline.getColor(), (int)this.WaveLenghtOutline.getValue(), (int)this.RSTARTOutline.getValue(), (int)this.GSTARTOutline.getValue(), (int)this.BSTARTOutline.getValue());
                    RainbowCubeOutlineShader.INSTANCE.update((double)this.speedOutline.getValue());
                    break;
                }
                case "Gradient": {
                    GradientOutlineShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue(), ((Double)this.duplicateOutline.getValue()).floatValue(), ((Double)this.moreGradientOutline.getValue()).floatValue(), ((Double)this.creepyOutline.getValue()).floatValue(), ((Double)this.alphaOutline.getValue()).floatValue(), (int)this.NUM_OCTAVESOutline.getValue());
                    GradientOutlineShader.INSTANCE.update((double)this.speedOutline.getValue());
                    break;
                }
                case "Astral": {
                    AstralOutlineShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue(), ((Double)this.duplicateOutline.getValue()).floatValue(), (float)this.redOutline.getValue(), ((Double)this.greenOutline.getValue()).floatValue(), ((Double)this.blueOutline.getValue()).floatValue(), ((Double)this.alphaOutline.getValue()).floatValue(), (int)this.iterationsOutline.getValue(), ((Double)this.formuparam2Outline.getValue()).floatValue(), ((Double)this.zoomOutline.getValue()).floatValue(), (float)(int)this.volumStepsOutline.getValue(), ((Double)this.stepSizeOutline.getValue()).floatValue(), ((Double)this.titleOutline.getValue()).floatValue(), ((Double)this.distfadingOutline.getValue()).floatValue(), ((Double)this.saturationOutline.getValue()).floatValue(), 0.0f, (int)(((boolean)this.fadeOutline.getValue()) ? 1 : 0));
                    AstralOutlineShader.INSTANCE.update((double)this.speedOutline.getValue());
                    break;
                }
                case "Aqua": {
                    AquaOutlineShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue(), ((Double)this.duplicateOutline.getValue()).floatValue(), (int)this.MaxIterOutline.getValue(), (double)this.tauOutline.getValue());
                    AquaOutlineShader.INSTANCE.update((double)this.speedOutline.getValue());
                    break;
                }
                case "Circle": {
                    CircleOutlineShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue(), ((Double)this.duplicateOutline.getValue()).floatValue(), (Double)this.PIOutline.getValue(), (Double)this.radOutline.getValue());
                    CircleOutlineShader.INSTANCE.update((double)this.speedOutline.getValue());
                    break;
                }
                case "Smoke": {
                    SmokeOutlineShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue(), ((Double)this.duplicateOutline.getValue()).floatValue(), (Color)this.secondColorImgOutline.getValue(), (Color)this.thirdColorImgOutline.getValue(), (int)this.NUM_OCTAVESOutline.getValue());
                    SmokeOutlineShader.INSTANCE.update((double)this.speedOutline.getValue());
                    break;
                }
            }
            GlStateManager.disableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.disableDepth();
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }, new Predicate[0]);
        this.preFill = (Listener<RenderHand.PreFill>)new Listener(event -> {
            if (ItemShaders.mc.world == null || ItemShaders.mc.player == null) {
                return;
            }
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableAlpha();
            final String s = (String)this.fillShader.getValue();
            switch (s) {
                case "Astral": {
                    FlowShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Aqua": {
                    AquaShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Smoke": {
                    SmokeShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "RainbowCube": {
                    RainbowCubeShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Gradient": {
                    GradientShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Fill": {
                    FillShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Circle": {
                    CircleShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Phobos": {
                    PhobosShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
            }
        }, new Predicate[0]);
        this.postFill = (Listener<RenderHand.PostFill>)new Listener(event -> {
            if (ItemShaders.mc.world == null || ItemShaders.mc.player == null) {
                return;
            }
            final String s = (String)this.fillShader.getValue();
            switch (s) {
                case "Astral": {
                    FlowShader.INSTANCE.stopDraw(Color.WHITE, 1.0f, 1.0f, ((Double)this.duplicateFill.getValue()).floatValue(), (float)this.redFill.getValue(), ((Double)this.greenFill.getValue()).floatValue(), ((Double)this.blueFill.getValue()).floatValue(), ((Double)this.alphaFill.getValue()).floatValue(), (int)this.iterationsFill.getValue(), ((Double)this.formuparam2Fill.getValue()).floatValue(), ((Double)this.zoomFill.getValue()).floatValue(), (float)(int)this.volumStepsFill.getValue(), ((Double)this.stepSizeFill.getValue()).floatValue(), ((Double)this.titleFill.getValue()).floatValue(), ((Double)this.distfadingFill.getValue()).floatValue(), ((Double)this.saturationFill.getValue()).floatValue(), 0.0f, (int)(((boolean)this.fadeFill.getValue()) ? 1 : 0));
                    FlowShader.INSTANCE.update((double)this.speedFill.getValue());
                    break;
                }
                case "Aqua": {
                    AquaShader.INSTANCE.stopDraw((Color)this.colorImgFill.getColor(), 1.0f, 1.0f, ((Double)this.duplicateFill.getValue()).floatValue(), (int)this.MaxIterFill.getValue(), (double)this.tauFill.getValue());
                    AquaShader.INSTANCE.update((double)this.speedFill.getValue());
                    break;
                }
                case "Smoke": {
                    SmokeShader.INSTANCE.stopDraw(Color.WHITE, 1.0f, 1.0f, ((Double)this.duplicateFill.getValue()).floatValue(), (Color)this.colorImgFill.getColor(), (Color)this.secondColorImgFill.getColor(), (Color)this.thirdColorImgFIll.getColor(), (int)this.NUM_OCTAVESFill.getValue());
                    SmokeShader.INSTANCE.update((double)this.speedFill.getValue());
                    break;
                }
                case "RainbowCube": {
                    RainbowCubeShader.INSTANCE.stopDraw(Color.WHITE, 1.0f, 1.0f, ((Double)this.duplicateFill.getValue()).floatValue(), (Color)this.colorImgFill.getColor(), (int)this.WaveLenghtFIll.getValue(), (int)this.RSTARTFill.getValue(), (int)this.GSTARTFill.getValue(), (int)this.BSTARTFIll.getValue());
                    RainbowCubeShader.INSTANCE.update((double)this.speedFill.getValue());
                    break;
                }
                case "Gradient": {
                    GradientShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), 1.0f, 1.0f, ((Double)this.duplicateFill.getValue()).floatValue(), ((Double)this.moreGradientFill.getValue()).floatValue(), ((Double)this.creepyFill.getValue()).floatValue(), ((Double)this.alphaFill.getValue()).floatValue(), (int)this.NUM_OCTAVESFill.getValue());
                    GradientShader.INSTANCE.update((double)this.speedFill.getValue());
                    break;
                }
                case "Fill": {
                    FillShader.INSTANCE.stopDraw((Color)new GSColor(this.colorImgFill.getValue(), this.colorImgFill.getColor().getAlpha()));
                    FillShader.INSTANCE.update((double)this.speedFill.getValue());
                    break;
                }
                case "Circle": {
                    CircleShader.INSTANCE.stopDraw(((Double)this.duplicateFill.getValue()).floatValue(), (Color)this.colorImgFill.getValue(), (Double)this.PI.getValue(), (Double)this.rad.getValue());
                    CircleShader.INSTANCE.update((double)this.speedFill.getValue());
                    break;
                }
                case "Phobos": {
                    PhobosShader.INSTANCE.stopDraw((Color)this.colorImgFill.getColor(), 1.0f, 1.0f, ((Double)this.duplicateFill.getValue()).floatValue(), (int)this.MaxIterFill.getValue(), (double)this.tauFill.getValue());
                    PhobosShader.INSTANCE.update((double)this.speedFill.getValue());
                    break;
                }
            }
            GlStateManager.disableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.disableDepth();
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }, new Predicate[0]);
        this.preBoth = (Listener<RenderHand.PreBoth>)new Listener(event -> {
            if (ItemShaders.mc.world == null || ItemShaders.mc.player == null) {
                return;
            }
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableAlpha();
            final String s = (String)this.glowESP.getValue();
            switch (s) {
                case "Color": {
                    GlowShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "RainbowCube": {
                    RainbowCubeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Gradient": {
                    GradientOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Astral": {
                    AstralOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Aqua": {
                    AquaOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Circle": {
                    CircleOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
                case "Smoke": {
                    SmokeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                    break;
                }
            }
        }, new Predicate[0]);
        this.postBoth = (Listener<RenderHand.PostBoth>)new Listener(event -> {
            if (ItemShaders.mc.world == null || ItemShaders.mc.player == null) {
                return;
            }
            final Predicate<Boolean> newFill = this.getFill();
            final String s = (String)this.glowESP.getValue();
            switch (s) {
                case "Color": {
                    GlowShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue(), (Predicate)newFill);
                    break;
                }
                case "RainbowCube": {
                    RainbowCubeOutlineShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue(), ((Double)this.duplicateOutline.getValue()).floatValue(), (Color)this.colorImgOutline.getColor(), (int)this.WaveLenghtOutline.getValue(), (int)this.RSTARTOutline.getValue(), (int)this.GSTARTOutline.getValue(), (int)this.BSTARTOutline.getValue(), (Predicate)newFill);
                    RainbowCubeOutlineShader.INSTANCE.update((double)this.speedOutline.getValue());
                    break;
                }
                case "Gradient": {
                    GradientOutlineShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue(), ((Double)this.duplicateOutline.getValue()).floatValue(), ((Double)this.moreGradientOutline.getValue()).floatValue(), ((Double)this.creepyOutline.getValue()).floatValue(), ((Double)this.alphaOutline.getValue()).floatValue(), (int)this.NUM_OCTAVESOutline.getValue(), (Predicate)newFill);
                    GradientOutlineShader.INSTANCE.update((double)this.speedOutline.getValue());
                    break;
                }
                case "Astral": {
                    AstralOutlineShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue(), ((Double)this.duplicateOutline.getValue()).floatValue(), (float)this.redOutline.getValue(), ((Double)this.greenOutline.getValue()).floatValue(), ((Double)this.blueOutline.getValue()).floatValue(), ((Double)this.alphaOutline.getValue()).floatValue(), (int)this.iterationsOutline.getValue(), ((Double)this.formuparam2Outline.getValue()).floatValue(), ((Double)this.zoomOutline.getValue()).floatValue(), (float)(int)this.volumStepsOutline.getValue(), ((Double)this.stepSizeOutline.getValue()).floatValue(), ((Double)this.titleOutline.getValue()).floatValue(), ((Double)this.distfadingOutline.getValue()).floatValue(), ((Double)this.saturationOutline.getValue()).floatValue(), 0.0f, (int)(((boolean)this.fadeOutline.getValue()) ? 1 : 0), (Predicate)newFill);
                    AstralOutlineShader.INSTANCE.update((double)this.speedOutline.getValue());
                    break;
                }
                case "Aqua": {
                    AquaOutlineShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue(), ((Double)this.duplicateOutline.getValue()).floatValue(), (int)this.MaxIterOutline.getValue(), (double)this.tauOutline.getValue(), (Predicate)newFill);
                    AquaOutlineShader.INSTANCE.update((double)this.speedOutline.getValue());
                    break;
                }
                case "Circle": {
                    CircleOutlineShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue(), ((Double)this.duplicateOutline.getValue()).floatValue(), (Double)this.PIOutline.getValue(), (Double)this.radOutline.getValue(), (Predicate)newFill);
                    CircleOutlineShader.INSTANCE.update((double)this.speedOutline.getValue());
                    break;
                }
                case "Smoke": {
                    SmokeOutlineShader.INSTANCE.stopDraw((Color)this.colorESP.getValue(), ((Double)this.radius.getValue()).floatValue(), ((Double)this.quality.getValue()).floatValue(), (boolean)this.GradientAlpha.getValue(), (int)this.alphaValue.getValue(), ((Double)this.duplicateOutline.getValue()).floatValue(), (Color)this.secondColorImgOutline.getValue(), (Color)this.thirdColorImgOutline.getValue(), (int)this.NUM_OCTAVESOutline.getValue(), (Predicate)newFill);
                    SmokeOutlineShader.INSTANCE.update((double)this.speedOutline.getValue());
                    break;
                }
            }
            GlStateManager.disableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.disableDepth();
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }, new Predicate[0]);
    }
    
    Predicate<Boolean> getFill() {
        Predicate<Boolean> output = a -> true;
        final String s = (String)this.fillShader.getValue();
        switch (s) {
            case "Astral": {
                output = (a -> {
                    FlowShader.INSTANCE.startShader(((Double)this.duplicateFill.getValue()).floatValue(), (float)this.redFill.getValue(), ((Double)this.greenFill.getValue()).floatValue(), ((Double)this.blueFill.getValue()).floatValue(), ((Double)this.alphaFill.getValue()).floatValue(), (int)this.iterationsFill.getValue(), ((Double)this.formuparam2Fill.getValue()).floatValue(), ((Double)this.zoomFill.getValue()).floatValue(), (float)(int)this.volumStepsFill.getValue(), ((Double)this.stepSizeFill.getValue()).floatValue(), ((Double)this.titleFill.getValue()).floatValue(), ((Double)this.distfadingFill.getValue()).floatValue(), ((Double)this.saturationFill.getValue()).floatValue(), 0.0f, (int)(((boolean)this.fadeFill.getValue()) ? 1 : 0));
                    return true;
                });
                FlowShader.INSTANCE.update((double)this.speedFill.getValue());
                break;
            }
            case "Aqua": {
                output = (a -> {
                    AquaShader.INSTANCE.startShader(((Double)this.duplicateFill.getValue()).floatValue(), (Color)this.colorImgFill.getColor(), (int)this.MaxIterFill.getValue(), (double)this.tauFill.getValue());
                    return true;
                });
                AquaShader.INSTANCE.update((double)this.speedFill.getValue());
                break;
            }
            case "Smoke": {
                output = (a -> {
                    SmokeShader.INSTANCE.startShader(((Double)this.duplicateFill.getValue()).floatValue(), (Color)this.colorImgFill.getColor(), (Color)this.secondColorImgFill.getColor(), (Color)this.thirdColorImgFIll.getColor(), (int)this.NUM_OCTAVESFill.getValue());
                    return true;
                });
                SmokeShader.INSTANCE.update((double)this.speedFill.getValue());
                break;
            }
            case "RainbowCube": {
                output = (a -> {
                    RainbowCubeShader.INSTANCE.startShader(((Double)this.duplicateFill.getValue()).floatValue(), (Color)this.colorImgFill.getColor(), (int)this.WaveLenghtFIll.getValue(), (int)this.RSTARTFill.getValue(), (int)this.GSTARTFill.getValue(), (int)this.BSTARTFIll.getValue());
                    return true;
                });
                RainbowCubeShader.INSTANCE.update((double)this.speedFill.getValue());
                break;
            }
            case "Gradient": {
                output = (a -> {
                    GradientShader.INSTANCE.startShader(((Double)this.duplicateFill.getValue()).floatValue(), ((Double)this.moreGradientFill.getValue()).floatValue(), ((Double)this.creepyFill.getValue()).floatValue(), ((Double)this.alphaFill.getValue()).floatValue(), (int)this.NUM_OCTAVESFill.getValue());
                    return true;
                });
                GradientShader.INSTANCE.update((double)this.speedFill.getValue());
                break;
            }
            case "Fill": {
                final GSColor col = new GSColor(this.colorImgFill.getValue(), this.colorImgFill.getColor().getAlpha());
                final GSColor gsColor;
                output = (a -> {
                    FillShader.INSTANCE.startShader(gsColor.getRed() / 255.0f, gsColor.getGreen() / 255.0f, gsColor.getBlue() / 255.0f, gsColor.getAlpha() / 255.0f);
                    return false;
                });
                FillShader.INSTANCE.update((double)this.speedFill.getValue());
                break;
            }
            case "Circle": {
                output = (a -> {
                    CircleShader.INSTANCE.startShader(((Double)this.duplicateFill.getValue()).floatValue(), (Color)this.colorImgFill.getValue(), (Double)this.PI.getValue(), (Double)this.rad.getValue());
                    return true;
                });
                CircleShader.INSTANCE.update((double)this.speedFill.getValue());
                break;
            }
            case "Phobos": {
                output = (a -> {
                    PhobosShader.INSTANCE.startShader(((Double)this.duplicateFill.getValue()).floatValue(), (Color)this.colorImgFill.getColor(), (int)this.MaxIterFill.getValue(), (double)this.tauFill.getValue());
                    return true;
                });
                PhobosShader.INSTANCE.update((double)this.speedFill.getValue());
                break;
            }
        }
        return output;
    }
}
