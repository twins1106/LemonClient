//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.client.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.item.*;
import com.lemonclient.client.module.*;
import com.lemonclient.client.module.modules.render.*;

@Mixin({ ItemRenderer.class })
public class MixinItemRenderer
{
    @Inject(method = { "transformSideFirstPerson" }, at = { @At("HEAD") })
    public void transformSideFirstPerson(final EnumHandSide hand, final float p_187459_2_, final CallbackInfo callbackInfo) {
        final TransformSideFirstPersonEvent event = new TransformSideFirstPersonEvent(hand);
        LemonClient.EVENT_BUS.post((Object)event);
    }
    
    @Inject(method = { "transformEatFirstPerson" }, at = { @At("HEAD") }, cancellable = true)
    public void transformEatFirstPerson(final float p_187454_1_, final EnumHandSide hand, final ItemStack stack, final CallbackInfo callbackInfo) {
        final TransformSideFirstPersonEvent event = new TransformSideFirstPersonEvent(hand);
        LemonClient.EVENT_BUS.post((Object)event);
        final ViewModel viewModel = (ViewModel)ModuleManager.getModule((Class)ViewModel.class);
        if (viewModel.isEnabled() && (boolean)viewModel.cancelEating.getValue()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "transformFirstPerson" }, at = { @At("HEAD") })
    public void transformFirstPerson(final EnumHandSide hand, final float p_187453_2_, final CallbackInfo callbackInfo) {
        final TransformSideFirstPersonEvent event = new TransformSideFirstPersonEvent(hand);
        LemonClient.EVENT_BUS.post((Object)event);
    }
    
    @Inject(method = { "renderOverlays" }, at = { @At("HEAD") }, cancellable = true)
    public void renderOverlays(final float partialTicks, final CallbackInfo callbackInfo) {
        final NoRender noRender = (NoRender)ModuleManager.getModule((Class)NoRender.class);
        if (noRender.isEnabled() && (boolean)noRender.noOverlay.getValue()) {
            callbackInfo.cancel();
        }
    }
}
