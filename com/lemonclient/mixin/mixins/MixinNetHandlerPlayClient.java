//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.network.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import com.lemonclient.client.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.client.manager.managers.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ NetHandlerPlayClient.class })
public class MixinNetHandlerPlayClient
{
    @Inject(method = { "handleEntityMetadata" }, at = { @At("RETURN") }, cancellable = true)
    private void handleEntityMetadataHook(final SPacketEntityMetadata sPacketEntityMetadata, final CallbackInfo callbackInfo) {
        final Entity getEntityByID;
        final EntityPlayer entityPlayer;
        if (Minecraft.getMinecraft().world != null && (getEntityByID = Minecraft.getMinecraft().world.getEntityByID(sPacketEntityMetadata.getEntityId())) instanceof EntityPlayer && (entityPlayer = (EntityPlayer)getEntityByID).getHealth() <= 0.0f) {
            LemonClient.EVENT_BUS.post((Object)new DeathEvent(entityPlayer));
            if (TotemPopManager.INSTANCE.sendMsgs) {
                TotemPopManager.INSTANCE.death(entityPlayer);
            }
        }
    }
}
