//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "FastBow", category = Category.Combat)
public class FastBow extends Module
{
    IntegerSetting drawLength;
    
    public FastBow() {
        this.drawLength = this.registerInteger("Draw Length", 3, 3, 21);
    }
    
    public void onUpdate() {
        if (FastBow.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && FastBow.mc.player.isHandActive() && FastBow.mc.player.getItemInUseMaxCount() >= (int)this.drawLength.getValue()) {
            FastBow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, FastBow.mc.player.getHorizontalFacing()));
            FastBow.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(FastBow.mc.player.getActiveHand()));
            FastBow.mc.player.stopActiveHand();
        }
    }
}
