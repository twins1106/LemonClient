//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import net.minecraft.client.multiplayer.*;
import org.spongepowered.asm.mixin.*;
import com.lemonclient.client.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.lemonclient.client.module.modules.misc.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.client.module.modules.exploits.*;
import net.minecraft.item.*;

@Mixin({ PlayerControllerMP.class })
public abstract class MixinPlayerControllerMP
{
    @Shadow
    public abstract void syncCurrentPlayItem();
    
    @Inject(method = { "resetBlockRemoving" }, at = { @At("HEAD") }, cancellable = true)
    private void resetBlockWrapper(final CallbackInfo callbackInfo) {
        final BlockResetEvent uwu = new BlockResetEvent();
        LemonClient.EVENT_BUS.post((Object)uwu);
        if (uwu.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "onStoppedUsingItem" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDestroyBlock(final EntityPlayer playerIn, final CallbackInfo info) {
        final EventPlayerOnStoppedUsingItem event = new EventPlayerOnStoppedUsingItem();
        LemonClient.EVENT_BUS.post((Object)event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z" }, at = { @At("HEAD") }, cancellable = true)
    private void onPlayerDamageBlock(final BlockPos posBlock, final EnumFacing directionFacing, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final DamageBlockEvent event = new DamageBlockEvent(posBlock, directionFacing);
        LemonClient.EVENT_BUS.post((Object)event);
        if (event.isCancelled()) {
            callbackInfoReturnable.setReturnValue((Object)false);
        }
    }
    
    @Inject(method = { "clickBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void clickBlockHook(final BlockPos pos, final EnumFacing face, final CallbackInfoReturnable<Boolean> info) {
        final DamageBlockEvent event = new DamageBlockEvent(pos, face);
        LemonClient.EVENT_BUS.post((Object)event);
    }
    
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void onPlayerDamageBlockHook(final BlockPos pos, final EnumFacing face, final CallbackInfoReturnable<Boolean> info) {
        final DamageBlockEvent event = new DamageBlockEvent(pos, face);
        LemonClient.EVENT_BUS.post((Object)event);
    }
    
    @Inject(method = { "onPlayerDestroyBlock" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V") }, cancellable = true)
    private void onPlayerDestroyBlock(final BlockPos pos, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final noGlitchBlock noGlitchBlock = (noGlitchBlock)ModuleManager.getModule((Class)noGlitchBlock.class);
        if (noGlitchBlock.isEnabled() && (boolean)noGlitchBlock.breakBlock.getValue()) {
            callbackInfoReturnable.cancel();
            callbackInfoReturnable.setReturnValue((Object)false);
        }
        LemonClient.EVENT_BUS.post((Object)new DestroyBlockEvent(pos));
    }
    
    @Inject(method = { "getBlockReachDistance" }, at = { @At("RETURN") }, cancellable = true)
    private void getReachDistanceHook(final CallbackInfoReturnable<Float> distance) {
        final ReachDistanceEvent reachDistanceEvent = new ReachDistanceEvent((float)distance.getReturnValue());
        LemonClient.EVENT_BUS.post((Object)reachDistanceEvent);
        distance.setReturnValue((Object)reachDistanceEvent.getDistance());
    }
    
    @Inject(method = { "onStoppedUsingItem" }, at = { @At("HEAD") }, cancellable = true)
    public void onStoppedUsingItem(final EntityPlayer playerIn, final CallbackInfo ci) {
        final PacketUtils packetUtils = (PacketUtils)ModuleManager.getModule((Class)PacketUtils.class);
        if (packetUtils.isEnabled() && (((boolean)packetUtils.food.getValue() && playerIn.getHeldItem(playerIn.getActiveHand()).getItem() instanceof ItemFood) || ((boolean)packetUtils.potion.getValue() && playerIn.getHeldItem(playerIn.getActiveHand()).getItem() instanceof ItemPotion) || (boolean)packetUtils.all.getValue())) {
            this.syncCurrentPlayItem();
            playerIn.stopActiveHand();
            ci.cancel();
        }
    }
}
