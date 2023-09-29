//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.item.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

@Module.Declaration(name = "FakePlayer", category = Category.Misc)
public class FakePlayer extends Module
{
    private final ItemStack[] armors;
    BooleanSetting playerStacked;
    
    public FakePlayer() {
        this.armors = new ItemStack[] { new ItemStack((Item)Items.DIAMOND_BOOTS), new ItemStack((Item)Items.DIAMOND_LEGGINGS), new ItemStack((Item)Items.DIAMOND_CHESTPLATE), new ItemStack((Item)Items.DIAMOND_HELMET) };
        this.playerStacked = this.registerBoolean("Player Stacked", false);
    }
    
    public void onEnable() {
        if (FakePlayer.mc.player == null || FakePlayer.mc.player.isDead) {
            this.disable();
            return;
        }
        final EntityOtherPlayerMP clonedPlayer = new EntityOtherPlayerMP((World)FakePlayer.mc.world, new GameProfile(UUID.fromString("fdee323e-7f0c-4c15-8d1c-0f277442342a"), "FakePlayer"));
        clonedPlayer.copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
        clonedPlayer.rotationYawHead = FakePlayer.mc.player.rotationYawHead;
        clonedPlayer.rotationYaw = FakePlayer.mc.player.rotationYaw;
        clonedPlayer.rotationPitch = FakePlayer.mc.player.rotationPitch;
        clonedPlayer.setGameType(GameType.SURVIVAL);
        clonedPlayer.setHealth(20.0f);
        FakePlayer.mc.world.addEntityToWorld(-1234, (Entity)clonedPlayer);
        if (this.playerStacked.getValue()) {
            for (int i = 0; i < 4; ++i) {
                final ItemStack item = this.armors[i];
                item.addEnchantment((i == 2) ? Enchantments.BLAST_PROTECTION : Enchantments.PROTECTION, 4);
                clonedPlayer.inventory.armorInventory.set(i, (Object)item);
            }
        }
        clonedPlayer.onLivingUpdate();
    }
    
    public void onDisable() {
        if (FakePlayer.mc.world != null) {
            FakePlayer.mc.world.removeEntityFromWorld(-1234);
        }
    }
}
