//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.entity.*;
import com.lemonclient.client.module.modules.render.*;
import com.lemonclient.client.module.*;
import net.minecraft.client.renderer.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.client.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderLivingBase.class })
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T>
{
    @Shadow
    protected ModelBase mainModel;
    protected final Minecraft mc;
    private boolean isClustered;
    
    public MixinRenderLivingBase(final RenderManager renderManagerIn, final ModelBase modelBaseIn, final float shadowSizeIn) {
        super(renderManagerIn);
        this.mc = Minecraft.getMinecraft();
        this.mainModel = modelBaseIn;
        this.shadowSize = shadowSizeIn;
    }
    
    protected MixinRenderLivingBase() {
        super((RenderManager)null);
        this.mc = Minecraft.getMinecraft();
    }
    
    @Inject(method = { "renderModel" }, at = { @At("HEAD") }, cancellable = true)
    void doRender(final T entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final CallbackInfo ci) {
        final NewRenderEntityEvent event = new NewRenderEntityEvent(this.mainModel, (Entity)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        if (!this.bindEntityTexture((Entity)entityIn)) {
            return;
        }
        final NoRender noRender = (NoRender)ModuleManager.getModule((Class)NoRender.class);
        if (noRender.isEnabled() && (boolean)noRender.noCluster.getValue() && this.mc.player.getDistance((Entity)entityIn) < 1.0f && entityIn != this.mc.player) {
            GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            this.isClustered = true;
            if (!noRender.incrementNoClusterRender()) {
                ci.cancel();
            }
        }
        else {
            this.isClustered = false;
        }
        final RenderEntityEvent.Head renderEntityHeadEvent = new RenderEntityEvent.Head((Entity)entityIn, RenderEntityEvent.Type.COLOR);
        LemonClient.EVENT_BUS.post((Object)renderEntityHeadEvent);
        GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
        LemonClient.EVENT_BUS.post((Object)event);
        GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderModel" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderModel(final T entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final CallbackInfo callbackInfo) {
        if (!this.bindEntityTexture((Entity)entitylivingbaseIn)) {
            return;
        }
        final NoRender noRender = (NoRender)ModuleManager.getModule((Class)NoRender.class);
        if (noRender.isEnabled() && (boolean)noRender.noCluster.getValue() && this.mc.player.getDistance((Entity)entitylivingbaseIn) < 1.0f && entitylivingbaseIn != this.mc.player) {
            GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            this.isClustered = true;
            if (!noRender.incrementNoClusterRender()) {
                callbackInfo.cancel();
            }
        }
        else {
            this.isClustered = false;
        }
        final RenderEntityEvent.Head renderEntityHeadEvent = new RenderEntityEvent.Head((Entity)entitylivingbaseIn, RenderEntityEvent.Type.COLOR);
        LemonClient.EVENT_BUS.post((Object)renderEntityHeadEvent);
        if (renderEntityHeadEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "renderModel" }, at = { @At("RETURN") }, cancellable = true)
    protected void renderModelReturn(final T entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final CallbackInfo callbackInfo) {
        final RenderEntityEvent.Return renderEntityReturnEvent = new RenderEntityEvent.Return((Entity)entitylivingbaseIn, RenderEntityEvent.Type.COLOR);
        LemonClient.EVENT_BUS.post((Object)renderEntityReturnEvent);
        if (!renderEntityReturnEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "renderLayers" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderLayers(final T entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleIn, final CallbackInfo callbackInfo) {
        if (this.isClustered && !((NoRender)ModuleManager.getModule((Class)NoRender.class)).getNoClusterRender()) {
            callbackInfo.cancel();
        }
    }
    
    @Redirect(method = { "setBrightness" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;glTexEnvi(III)V", ordinal = 6))
    protected void glTexEnvi0(int target, final int parameterName, final int parameter) {
        if (!this.isClustered) {
            GlStateManager.glTexEnvi(target, parameterName, parameter);
        }
    }
    
    @Redirect(method = { "setBrightness" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;glTexEnvi(III)V", ordinal = 7))
    protected void glTexEnvi1(int target, final int parameterName, final int parameter) {
        if (!this.isClustered) {
            GlStateManager.glTexEnvi(target, parameterName, parameter);
        }
    }
    
    @Redirect(method = { "setBrightness" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;glTexEnvi(III)V", ordinal = 8))
    protected void glTexEnvi2(int target, final int parameterName, final int parameter) {
        if (!this.isClustered) {
            GlStateManager.glTexEnvi(target, parameterName, parameter);
        }
    }
}
