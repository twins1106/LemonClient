//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.*;
import com.lemonclient.client.module.modules.exploits.*;
import com.lemonclient.client.module.*;
import net.minecraft.client.*;
import com.lemonclient.client.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.lemonclient.api.event.events.*;

@Mixin({ EntityPlayer.class })
public abstract class MixinEntityPlayer
{
    @Shadow
    public abstract String getName();
    
    @ModifyConstant(method = { "getPortalCooldown" }, constant = { @Constant(intValue = 10) })
    private int getPortalCooldownHook(final int n) {
        int intValue = n;
        final Portal portal = (Portal)ModuleManager.getModule((Class)Portal.class);
        if (portal.isEnabled() && (boolean)portal.fastPortal.getValue()) {
            intValue = (int)portal.cooldown.getValue();
        }
        return intValue;
    }
    
    @Inject(method = { "jump" }, at = { @At("HEAD") }, cancellable = true)
    public void onJump(final CallbackInfo callbackInfo) {
        if (Minecraft.getMinecraft().player.getName() == this.getName()) {
            LemonClient.EVENT_BUS.post((Object)new PlayerJumpEvent());
        }
    }
    
    @Inject(method = { "isPushedByWater" }, at = { @At("HEAD") }, cancellable = true)
    private void onPushedByWater(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final WaterPushEvent event = new WaterPushEvent();
        LemonClient.EVENT_BUS.post((Object)event);
        if (event.isCancelled()) {
            callbackInfoReturnable.setReturnValue((Object)false);
        }
    }
}
