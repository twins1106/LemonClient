//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;

@Module.Declaration(name = "NoKick", category = Category.Misc)
public class NoKick extends Module
{
    public BooleanSetting noPacketKick;
    BooleanSetting noSlimeCrash;
    BooleanSetting noOffhandCrash;
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener;
    
    public NoKick() {
        this.noPacketKick = this.registerBoolean("Packet", true);
        this.noSlimeCrash = this.registerBoolean("Slime", false);
        this.noOffhandCrash = this.registerBoolean("Offhand", false);
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if ((boolean)this.noOffhandCrash.getValue() && event.getPacket() instanceof SPacketSoundEffect && ((SPacketSoundEffect)event.getPacket()).getSound() == SoundEvents.ITEM_ARMOR_EQUIP_GENERIC) {
                event.cancel();
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        if (NoKick.mc.world != null && (boolean)this.noSlimeCrash.getValue()) {
            EntitySlime slime;
            NoKick.mc.world.loadedEntityList.forEach(entity -> {
                if (entity instanceof EntitySlime) {
                    slime = entity;
                    if (slime.getSlimeSize() > 4) {
                        NoKick.mc.world.removeEntity((Entity)entity);
                    }
                }
            });
        }
    }
}
