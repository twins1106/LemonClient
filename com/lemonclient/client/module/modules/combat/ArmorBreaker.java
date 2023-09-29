//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.player.social.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.util.*;

@Module.Declaration(name = "ArmorBreaker", category = Category.Combat)
public class ArmorBreaker extends Module
{
    IntegerSetting range;
    BooleanSetting packet;
    BooleanSetting swing;
    IntegerSetting hit;
    int hasWaited;
    
    public ArmorBreaker() {
        this.range = this.registerInteger("Range", 6, 0, 20);
        this.packet = this.registerBoolean("Packet Attack", true);
        this.swing = this.registerBoolean("Swing", true);
        this.hit = this.registerInteger("Hit", 20, 0, 100);
    }
    
    public void onUpdate() {
        final int reqDelay = (int)Math.round(20.0 / (int)this.hit.getValue());
        if (this.hasWaited < reqDelay) {
            ++this.hasWaited;
            return;
        }
        this.hasWaited = 0;
        for (final Entity entity : ArmorBreaker.mc.world.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                if (entity == ArmorBreaker.mc.player) {
                    continue;
                }
                if (ArmorBreaker.mc.player.getDistance(entity) > (int)this.range.getValue() || ((EntityLivingBase)entity).getHealth() <= 0.0f) {
                    continue;
                }
                if (!(entity instanceof EntityPlayer)) {
                    continue;
                }
                if (SocialManager.isFriend(entity.getName())) {
                    continue;
                }
                if (this.packet.getValue()) {
                    ArmorBreaker.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
                }
                else {
                    ArmorBreaker.mc.playerController.attackEntity((EntityPlayer)ArmorBreaker.mc.player, entity);
                }
                if (!(boolean)this.swing.getValue()) {
                    continue;
                }
                ArmorBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
}
