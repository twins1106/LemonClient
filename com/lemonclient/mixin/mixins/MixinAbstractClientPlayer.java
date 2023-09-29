//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import net.minecraft.client.entity.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.*;
import javax.annotation.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.*;
import com.lemonclient.client.module.modules.render.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.render.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ AbstractClientPlayer.class })
public abstract class MixinAbstractClientPlayer
{
    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo getPlayerInfo();
    
    @Inject(method = { "getLocationCape" }, at = { @At("HEAD") }, cancellable = true)
    public void getLocationCape(final CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        final UUID uuid = Objects.requireNonNull(this.getPlayerInfo()).getGameProfile().getId();
        if (ModuleManager.isModuleEnabled((Class)Cape.class) && CapeUtil.hasCape(uuid)) {
            callbackInfoReturnable.setReturnValue((Object)new ResourceLocation("lemonclient:cape.png"));
        }
    }
}
