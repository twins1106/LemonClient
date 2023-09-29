//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.lemonclient.client.module.modules.render.*;
import com.lemonclient.client.module.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.client.*;

@Mixin({ RenderGlobal.class })
public class MixinRenderGlobal
{
    @Inject(method = { "drawSelectionBox" }, at = { @At("HEAD") }, cancellable = true)
    public void drawSelectionBox(final EntityPlayer player, final RayTraceResult movingObjectPositionIn, final int execute, final float partialTicks, final CallbackInfo callbackInfo) {
        if (ModuleManager.isModuleEnabled((Class)BlockHighlight.class)) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "drawBlockDamageTexture" }, at = { @At("HEAD") }, cancellable = true)
    public void drawBlockDamageTexture(final Tessellator tessellatorIn, final BufferBuilder bufferBuilderIn, final Entity entityIn, final float partialTicks, final CallbackInfo callbackInfo) {
        final DrawBlockDamageEvent drawBlockDamageEvent = new DrawBlockDamageEvent();
        LemonClient.EVENT_BUS.post((Object)drawBlockDamageEvent);
        if (drawBlockDamageEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
