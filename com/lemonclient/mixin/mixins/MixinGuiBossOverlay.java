//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.client.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiBossOverlay.class })
public class MixinGuiBossOverlay
{
    @Inject(method = { "renderBossHealth" }, at = { @At("HEAD") }, cancellable = true)
    private void renderBossHealth(final CallbackInfo callbackInfo) {
        final BossbarEvent event = new BossbarEvent();
        LemonClient.EVENT_BUS.post((Object)event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
