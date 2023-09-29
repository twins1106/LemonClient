//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.lemonclient.client.module.*;
import net.minecraft.client.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.client.*;
import org.lwjgl.util.glu.*;
import net.minecraftforge.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import com.lemonclient.client.module.modules.render.*;

@Mixin({ EntityRenderer.class })
public abstract class MixinEntityRenderer
{
    @Shadow
    public boolean debugView;
    @Shadow
    public float farPlaneDistance;
    @Final
    @Shadow
    public ItemRenderer itemRenderer;
    
    @Inject(method = { "renderHand" }, at = { @At("HEAD") }, cancellable = true)
    public void renderHandMain(final float partialTicks, final int pass, final CallbackInfo ci) {
        final ItemShaders module = (ItemShaders)ModuleManager.getModule((Class)ItemShaders.class);
        if (module.isEnabled()) {
            final Minecraft mc = Minecraft.getMinecraft();
            if (!(boolean)module.cancelItem.getValue()) {
                this.doRenderHand(partialTicks, pass, mc);
            }
            if (!((String)module.glowESP.getValue()).equals("None") && !((String)module.fillShader.getValue()).equals("None")) {
                GlStateManager.pushMatrix();
                final RenderHand.PreBoth hand = new RenderHand.PreBoth(partialTicks);
                LemonClient.EVENT_BUS.post((Object)hand);
                this.doRenderHand(partialTicks, pass, mc);
                final RenderHand.PostBoth hand2 = new RenderHand.PostBoth(partialTicks);
                LemonClient.EVENT_BUS.post((Object)hand2);
                GlStateManager.popMatrix();
            }
            if (!((String)module.glowESP.getValue()).equals("None")) {
                GlStateManager.pushMatrix();
                final RenderHand.PreOutline hand3 = new RenderHand.PreOutline(partialTicks);
                LemonClient.EVENT_BUS.post((Object)hand3);
                this.doRenderHand(partialTicks, pass, mc);
                final RenderHand.PostOutline hand4 = new RenderHand.PostOutline(partialTicks);
                LemonClient.EVENT_BUS.post((Object)hand4);
                GlStateManager.popMatrix();
            }
            if (!((String)module.fillShader.getValue()).equals("None")) {
                GlStateManager.pushMatrix();
                final RenderHand.PreFill hand5 = new RenderHand.PreFill(partialTicks);
                LemonClient.EVENT_BUS.post((Object)hand5);
                this.doRenderHand(partialTicks, pass, mc);
                final RenderHand.PostFill hand6 = new RenderHand.PostFill(partialTicks);
                LemonClient.EVENT_BUS.post((Object)hand6);
                GlStateManager.popMatrix();
            }
            ci.cancel();
        }
    }
    
    @Shadow
    public abstract float getFOVModifier(final float p0, final boolean p1);
    
    @Shadow
    public abstract void hurtCameraEffect(final float p0);
    
    @Shadow
    public abstract void applyBobbing(final float p0);
    
    @Shadow
    public abstract void enableLightmap();
    
    @Shadow
    public abstract void disableLightmap();
    
    void doRenderHand(final float partialTicks, final int pass, final Minecraft mc) {
        if (!this.debugView) {
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            final float f = 0.07f;
            if (mc.gameSettings.anaglyph) {
                GlStateManager.translate(-(pass * 2 - 1) * 0.07f, 0.0f, 0.0f);
            }
            Project.gluPerspective(this.getFOVModifier(partialTicks, false), mc.displayWidth / (float)mc.displayHeight, 0.05f, this.farPlaneDistance * 2.0f);
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            if (mc.gameSettings.anaglyph) {
                GlStateManager.translate((pass * 2 - 1) * 0.1f, 0.0f, 0.0f);
            }
            GlStateManager.pushMatrix();
            this.hurtCameraEffect(partialTicks);
            if (mc.gameSettings.viewBobbing) {
                this.applyBobbing(partialTicks);
            }
            final boolean flag = mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)mc.getRenderViewEntity()).isPlayerSleeping();
            if (!ForgeHooksClient.renderFirstPersonHand(mc.renderGlobal, partialTicks, pass) && mc.gameSettings.thirdPersonView == 0 && !flag && !mc.gameSettings.hideGUI && !mc.playerController.isSpectator()) {
                this.enableLightmap();
                this.itemRenderer.renderItemInFirstPerson(partialTicks);
                this.disableLightmap();
            }
            GlStateManager.popMatrix();
            if (mc.gameSettings.thirdPersonView == 0 && !flag) {
                this.itemRenderer.renderOverlays(partialTicks);
                this.hurtCameraEffect(partialTicks);
            }
            if (mc.gameSettings.viewBobbing) {
                this.applyBobbing(partialTicks);
            }
        }
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"))
    public RayTraceResult rayTraceBlocks(final WorldClient world, final Vec3d start, final Vec3d end) {
        final RenderTweaks renderTweaks = (RenderTweaks)ModuleManager.getModule((Class)RenderTweaks.class);
        if (renderTweaks.isEnabled() && (boolean)renderTweaks.viewClip.getValue()) {
            return null;
        }
        return world.rayTraceBlocks(start, end);
    }
    
    @Inject(method = { "setupFog" }, at = { @At("HEAD") }, cancellable = true)
    public void setupFog(final int startCoords, final float partialTicks, final CallbackInfo callbackInfo) {
        if (ModuleManager.isModuleEnabled("AntiFog") && AntiFog.type.equals("NoFog")) {
            callbackInfo.cancel();
        }
    }
    
    @Redirect(method = { "setupFog" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ActiveRenderInfo;getBlockStateAtEntityViewpoint(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;F)Lnet/minecraft/block/state/IBlockState;"))
    public IBlockState getBlockStateAtEntityViewpoint(final World worldIn, final Entity entityIn, final float p_186703_2_) {
        if (ModuleManager.isModuleEnabled("AntiFog") && AntiFog.type.equals("Air")) {
            return Blocks.AIR.defaultBlockState;
        }
        return ActiveRenderInfo.getBlockStateAtEntityViewpoint(worldIn, entityIn, p_186703_2_);
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCameraEffect(final float ticks, final CallbackInfo callbackInfo) {
        final NoRender noRender = (NoRender)ModuleManager.getModule((Class)NoRender.class);
        if (noRender.isEnabled() && (boolean)noRender.hurtCam.getValue()) {
            callbackInfo.cancel();
        }
    }
}
