//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.player.social.*;
import net.minecraft.client.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.*;
import java.util.*;

@Module.Declaration(name = "Auto32kLog", category = Category.Combat)
public class Auto32kLog extends Module
{
    private Set<EntityPlayer> sword;
    
    public Auto32kLog() {
        this.sword = Collections.newSetFromMap(new WeakHashMap<EntityPlayer, Boolean>());
    }
    
    private boolean is32k(final EntityPlayer player, final ItemStack stack) {
        final NBTTagList enchants;
        if (stack.getItem() instanceof ItemSword && (enchants = stack.getEnchantmentTagList()) != null) {
            for (int i = 0; i < enchants.tagCount(); ++i) {
                if (enchants.getCompoundTagAt(i).getShort("lvl") >= 32767) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void onUpdate() {
        for (final EntityPlayer player : Auto32kLog.mc.world.playerEntities) {
            int once = 0;
            final int Distanc = (int)Auto32kLog.mc.player.getDistance((Entity)player);
            if (player.equals((Object)Auto32kLog.mc.player)) {
                continue;
            }
            if (this.is32k(player, player.itemStackMainHand) && !this.sword.contains(player)) {
                this.sword.add(player);
            }
            if (this.is32k(player, player.itemStackMainHand)) {
                if (once > 0) {
                    return;
                }
                ++once;
                if (!SocialManager.isFriend(player.getName()) && Distanc < 15) {
                    Minecraft.getMinecraft().getConnection().handleDisconnect(new SPacketDisconnect((ITextComponent)new TextComponentString(ChatFormatting.BLUE + "Logged cuz nn tried to 32k you")));
                }
            }
            if (!this.sword.contains(player)) {
                continue;
            }
            if (this.is32k(player, player.itemStackMainHand)) {
                continue;
            }
            this.sword.remove(player);
        }
    }
}
