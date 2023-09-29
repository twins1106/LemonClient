//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.client.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.renderer.culling.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.lemonclient.client.module.modules.render.*;
import com.lemonclient.client.module.*;
import net.minecraft.entity.item.*;

@Mixin({ RenderManager.class })
public class MixinRenderManager
{
    @Inject(method = { "renderEntity" }, at = { @At("HEAD") }, cancellable = true)
    public void renderEntityHead(final Entity entityIn, final double x, final double y, final double z, final float yaw, final float partialTicks, final boolean p_188391_10_, final CallbackInfo callbackInfo) {
        final RenderEntityEvent.Head renderEntityHeadEvent = new RenderEntityEvent.Head(entityIn, RenderEntityEvent.Type.TEXTURE);
        LemonClient.EVENT_BUS.post((Object)renderEntityHeadEvent);
        if (entityIn instanceof EntityEnderPearl || entityIn instanceof EntityXPOrb || entityIn instanceof EntityExpBottle || entityIn instanceof EntityEnderCrystal) {
            final RenderEntityEvent.Head renderEntityEvent = new RenderEntityEvent.Head(entityIn, RenderEntityEvent.Type.COLOR);
            LemonClient.EVENT_BUS.post((Object)renderEntityEvent);
            if (renderEntityEvent.isCancelled()) {
                callbackInfo.cancel();
            }
        }
        if (renderEntityHeadEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "shouldRender" }, at = { @At("HEAD") }, cancellable = true)
    private void onShouldRender(final Entity entity, final ICamera camera, final double camX, final double camY, final double camZ, final CallbackInfoReturnable<Boolean> cir) {
        if (ModuleManager.isModuleEnabled((Class)NoFallingBlocks.class) && entity instanceof EntityFallingBlock) {
            cir.setReturnValue((Object)false);
        }
    }
    
    @Inject(method = { "renderEntity" }, at = { @At("RETURN") }, cancellable = true)
    public void renderEntityReturn(final Entity entityIn, final double x, final double y, final double z, final float yaw, final float partialTicks, final boolean p_188391_10_, final CallbackInfo callbackInfo) {
        final RenderEntityEvent.Return renderEntityReturnEvent = new RenderEntityEvent.Return(entityIn, RenderEntityEvent.Type.TEXTURE);
        LemonClient.EVENT_BUS.post((Object)renderEntityReturnEvent);
        if (entityIn instanceof EntityEnderPearl || entityIn instanceof EntityXPOrb || entityIn instanceof EntityExpBottle || entityIn instanceof EntityEnderCrystal) {
            final RenderEntityEvent.Return renderEntityEvent = new RenderEntityEvent.Return(entityIn, RenderEntityEvent.Type.COLOR);
            LemonClient.EVENT_BUS.post((Object)renderEntityEvent);
            if (renderEntityEvent.isCancelled()) {
                callbackInfo.cancel();
            }
        }
        if (renderEntityReturnEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
