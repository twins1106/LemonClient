//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import net.minecraft.util.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.util.render.*;
import java.util.function.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.player.social.*;

@Module.Declaration(name = "Chams", category = Category.Render)
public class Chams extends Module
{
    final ResourceLocation RES_ITEM_GLINT;
    BooleanSetting self;
    BooleanSetting crystals;
    BooleanSetting players;
    BooleanSetting customBlendFunc;
    BooleanSetting playerImage;
    BooleanSetting playerCancel;
    BooleanSetting crystalCancel;
    BooleanSetting playerTexture;
    BooleanSetting crystalTexture;
    BooleanSetting playerSecondaryTexture;
    BooleanSetting crystalSecondaryTexture;
    ColorSetting playerSecondaryTextureColor;
    ColorSetting crystalSecondaryTextureColor;
    ColorSetting friendLine;
    ColorSetting playerLine;
    ColorSetting crystalLine1;
    ColorSetting friendFill;
    ColorSetting playerFill;
    ColorSetting crystalFill1;
    BooleanSetting playerGlint;
    BooleanSetting crystalGlint;
    ColorSetting crystalGlint1;
    ColorSetting playerGlintColor;
    ColorSetting friendGlintColor;
    DoubleSetting crystalRotateSpeed;
    DoubleSetting crystalScale;
    DoubleSetting lineWidth;
    DoubleSetting lineWidthInterp;
    ColorSetting resetColor;
    boolean cancel;
    Action currentAction;
    @EventHandler
    private final Listener<NewRenderEntityEvent> renderEntityHeadEventListener;
    
    public Chams() {
        this.RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
        this.self = this.registerBoolean("Self", false);
        this.crystals = this.registerBoolean("Crystal", false);
        this.players = this.registerBoolean("Players", false);
        this.customBlendFunc = this.registerBoolean("customBlendFunc", false);
        this.playerImage = this.registerBoolean("playerImage", false);
        this.playerCancel = this.registerBoolean("playerCancel", false);
        this.crystalCancel = this.registerBoolean("crystalCancel", false);
        this.playerTexture = this.registerBoolean("playerTexture", false);
        this.crystalTexture = this.registerBoolean("crystalTexture", false);
        this.playerSecondaryTexture = this.registerBoolean("playerSecondaryTexture", false);
        this.crystalSecondaryTexture = this.registerBoolean("crystalSecondaryTexture", false);
        this.playerSecondaryTextureColor = this.registerColor("playerSecondaryTextureColor", new GSColor(255, 255, 255, 255), () -> true);
        this.crystalSecondaryTextureColor = this.registerColor("crystalSecondaryTextureColor", new GSColor(255, 255, 255, 255), () -> true);
        this.friendLine = this.registerColor("friendLine", new GSColor(255, 255, 255, 255), () -> true, Boolean.valueOf(true));
        this.playerLine = this.registerColor("playerLine", new GSColor(255, 255, 255, 255), () -> true, Boolean.valueOf(true));
        this.crystalLine1 = this.registerColor("crystalLine1", new GSColor(255, 255, 255, 255), () -> true, Boolean.valueOf(true));
        this.friendFill = this.registerColor("friendFill", new GSColor(255, 255, 255, 255), () -> true, Boolean.valueOf(true));
        this.playerFill = this.registerColor("playerFill", new GSColor(255, 255, 255, 255), () -> true, Boolean.valueOf(true));
        this.crystalFill1 = this.registerColor("crystalFill1", new GSColor(255, 255, 255, 255), () -> true, Boolean.valueOf(true));
        this.playerGlint = this.registerBoolean("playerGlint", false);
        this.crystalGlint = this.registerBoolean("crystalGlint", false);
        this.crystalGlint1 = this.registerColor("crystalGlint1", new GSColor(255, 255, 255, 255), () -> true, Boolean.valueOf(true));
        this.playerGlintColor = this.registerColor("playerGlintColor", new GSColor(255, 255, 255, 255), () -> true, Boolean.valueOf(true));
        this.friendGlintColor = this.registerColor("friendGlintColor", new GSColor(255, 255, 255, 255), () -> true, Boolean.valueOf(true));
        this.crystalRotateSpeed = this.registerDouble("crystalRotateSpeed", 1.0, 0.0, 2.0);
        this.crystalScale = this.registerDouble("crystalScale", 1.0, 0.0, 2.0);
        this.lineWidth = this.registerDouble("lineWidth", 1.0, 0.0, 4.0);
        this.lineWidthInterp = this.registerDouble("lineWidthInterp", 1.0, 0.1, 4.0);
        this.resetColor = this.registerColor("Reset Color", new GSColor(255, 255, 255, 255), () -> true, Boolean.valueOf(true));
        this.cancel = false;
        this.currentAction = Action.NONE;
        this.renderEntityHeadEventListener = (Listener<NewRenderEntityEvent>)new Listener(event -> {
            if (Chams.mc.player == null || Chams.mc.world == null || event.entityIn == null || event.entityIn.getName().length() == 0) {
                return;
            }
            if (event.entityIn instanceof EntityPlayer) {
                if (event.entityIn == Chams.mc.player) {
                    if (!(boolean)this.self.getValue()) {
                        return;
                    }
                }
                else if (!(boolean)this.players.getValue()) {
                    return;
                }
            }
            else {
                if (!(event.entityIn instanceof EntityEnderCrystal)) {
                    return;
                }
                if (!(boolean)this.crystals.getValue()) {
                    return;
                }
            }
            this.prepare();
            GL11.glPushAttrib(1048575);
            if (this.customBlendFunc.getValue()) {
                GL11.glBlendFunc(770, 32772);
            }
            GL11.glEnable(2881);
            GL11.glEnable(2848);
            final boolean image = !(event.entityIn instanceof EntityEnderCrystal) && (boolean)this.playerImage.getValue();
            final boolean cancelRender = (event.entityIn instanceof EntityLivingBase) ? this.playerCancel.getValue() : ((boolean)this.crystalCancel.getValue());
            final boolean texture2d = (event.entityIn instanceof EntityLivingBase) ? this.playerTexture.getValue() : ((boolean)this.crystalTexture.getValue());
            final boolean secondaryTexture = (event.entityIn instanceof EntityLivingBase) ? this.playerSecondaryTexture.getValue() : ((boolean)this.crystalSecondaryTexture.getValue());
            final Color color;
            final Color secondaryTextureColor = color = (Color)((event.entityIn instanceof EntityLivingBase) ? this.playerSecondaryTextureColor.getValue() : this.crystalSecondaryTextureColor.getValue());
            final Color line = (Color)((event.entityIn instanceof EntityLivingBase) ? (SocialManager.isFriend(event.entityIn.getName()) ? this.friendLine.getValue() : this.playerLine.getValue()) : this.crystalLine1.getValue());
            final Color fill = (Color)((event.entityIn instanceof EntityLivingBase) ? (SocialManager.isFriend(event.entityIn.getName()) ? this.friendFill.getValue() : this.playerFill.getValue()) : this.crystalFill1.getValue());
            final boolean bl;
            final boolean texture = bl = ((event.entityIn instanceof EntityLivingBase) ? this.playerGlint.getValue() : ((boolean)this.crystalGlint.getValue()));
            final Color textureColor = (Color)((event.entityIn instanceof EntityLivingBase) ? (SocialManager.isFriend(event.entityIn.getName()) ? this.friendGlintColor.getValue() : this.playerGlintColor.getValue()) : this.crystalGlint1.getValue());
            final float limbSwingAmt = (event.entityIn instanceof EntityEnderCrystal) ? (event.limbSwingAmount * ((Double)this.crystalRotateSpeed.getValue()).floatValue()) : event.limbSwingAmount;
            final float scale = (event.entityIn instanceof EntityEnderCrystal) ? ((Double)this.crystalScale.getValue()).floatValue() : event.scale;
            GlStateManager.glLineWidth(this.getInterpolatedLinWid(Chams.mc.player.getDistance(event.entityIn) + 1.0f, ((Double)this.lineWidth.getValue()).floatValue(), ((Double)this.lineWidthInterp.getValue()).floatValue()));
            if (!image) {
                GlStateManager.disableAlpha();
                this.glColor(fill);
                if (texture2d) {
                    GL11.glEnable(3553);
                }
                else {
                    GL11.glDisable(3553);
                }
                this.currentAction = Action.FILL;
                event.modelBase.render(event.entityIn, event.limbSwing, limbSwingAmt, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scale);
                GL11.glDisable(3553);
                if (secondaryTexture) {
                    this.currentAction = Action.NONE;
                    this.glColor(secondaryTextureColor);
                    event.modelBase.render(event.entityIn, event.limbSwing, limbSwingAmt, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scale);
                }
                GL11.glPolygonMode(1032, 6913);
                this.currentAction = Action.LINE;
                this.glColor(line);
                event.modelBase.render(event.entityIn, event.limbSwing, limbSwingAmt, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scale);
                this.currentAction = Action.GLINT;
                GL11.glPolygonMode(1032, 6914);
                if (texture) {
                    Chams.mc.getTextureManager().bindTexture(this.RES_ITEM_GLINT);
                    GL11.glEnable(3553);
                    GL11.glBlendFunc(768, 771);
                    this.glColor(textureColor);
                    event.modelBase.render(event.entityIn, event.limbSwing, limbSwingAmt, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scale);
                    if (this.customBlendFunc.getValue()) {
                        GL11.glBlendFunc(770, 32772);
                    }
                    else {
                        GL11.glBlendFunc(770, 771);
                    }
                }
                if (event.entityIn instanceof EntityLivingBase) {}
                event.limbSwingAmount = limbSwingAmt;
                this.currentAction = Action.NONE;
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popAttrib();
            this.release();
            if (cancelRender) {
                event.cancel();
            }
        }, new Predicate[0]);
    }
    
    void glColor(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    void prepare() {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
    }
    
    void release() {
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
        GL11.glEnable(3553);
        GL11.glPolygonMode(1032, 6914);
        this.resetColor.getValue().glColor();
    }
    
    float getInterpolatedLinWid(final float distance, final float line, final float lineFactor) {
        return line * lineFactor / distance;
    }
    
    enum Action
    {
        FILL, 
        LINE, 
        GLINT, 
        NONE;
    }
}
