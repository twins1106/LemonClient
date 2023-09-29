//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.lemonclient.client.module.modules.render.*;
import com.lemonclient.client.module.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiIngame.class })
public class MixinPlayerOverlay
{
    @Inject(method = { "renderPumpkinOverlay" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPumpkinOverlayHook(final ScaledResolution scaledRes, final CallbackInfo callbackInfo) {
        final NoRender noRender = (NoRender)ModuleManager.getModule((Class)NoRender.class);
        if (noRender.isEnabled() && (boolean)noRender.noOverlay.getValue()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "renderPotionEffects" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPotionEffectsHook(final ScaledResolution scaledRes, final CallbackInfo callbackInfo) {
        final NoRender noRender = (NoRender)ModuleManager.getModule((Class)NoRender.class);
        if (noRender.isEnabled() && (boolean)noRender.noOverlay.getValue()) {
            callbackInfo.cancel();
        }
    }
}
