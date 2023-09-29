//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.client.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ItemChorusFruit.class })
public class MixinChorus extends ItemFood
{
    public MixinChorus(final int amount, final float saturation) {
        super(amount, saturation, false);
    }
    
    @Inject(method = { "onItemUseFinish" }, at = { @At("HEAD") })
    public void attemptTeleportHook(final ItemStack stack, final World worldIn, final EntityLivingBase entityLiving, final CallbackInfoReturnable<ItemStack> cir) {
        final ChorusEvent event = new ChorusEvent();
        LemonClient.EVENT_BUS.post((Object)event);
    }
}
