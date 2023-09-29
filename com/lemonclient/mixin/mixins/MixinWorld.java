//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.lemonclient.client.module.modules.render.*;
import com.lemonclient.client.module.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ World.class })
public class MixinWorld
{
    @Inject(method = { "checkLightFor" }, at = { @At("HEAD") }, cancellable = true)
    private void updateLightmapHook(final EnumSkyBlock lightType, final BlockPos pos, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final NoRender noRender = (NoRender)ModuleManager.getModule((Class)NoRender.class);
        if (noRender.isEnabled() && (boolean)noRender.noSkylight.getValue() && lightType == EnumSkyBlock.SKY) {
            callbackInfoReturnable.setReturnValue((Object)true);
            callbackInfoReturnable.cancel();
        }
    }
}
