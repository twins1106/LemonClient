//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import com.lemonclient.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "PacketXP", category = Category.Combat)
public class PacketXP extends Module
{
    BooleanSetting key;
    BooleanSetting noEntityCollision;
    BooleanSetting silentSwitch;
    BooleanSetting check;
    BooleanSetting sync;
    IntegerSetting delay2;
    IntegerSetting minDamage;
    IntegerSetting maxHeal;
    BooleanSetting TakeOff;
    IntegerSetting delay;
    BooleanSetting predict;
    public static boolean isActive;
    int delay_count;
    int waitted;
    char toMend;
    
    public PacketXP() {
        this.key = this.registerBoolean("KeyDown Only", true);
        this.noEntityCollision = this.registerBoolean("No Collision", false);
        this.silentSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.sync = this.registerBoolean("Sync", true);
        this.delay2 = this.registerInteger("Delay", 0, 0, 10);
        this.minDamage = this.registerInteger("Min Damage", 50, 1, 100);
        this.maxHeal = this.registerInteger("Repair To", 90, 1, 100);
        this.TakeOff = this.registerBoolean("TakeOff", true);
        this.delay = this.registerInteger("TakeOff Delay", 0, 0, 10);
        this.predict = this.registerBoolean("Predict", true);
        this.toMend = '\0';
    }
    
    public void onUpdate() {
        if (PacketXP.mc.player == null || PacketXP.mc.world == null || PacketXP.mc.player.isDead || PacketXP.mc.player.ticksExisted < 10) {
            PacketXP.isActive = false;
            return;
        }
        if (this.waitted++ < (int)this.delay2.getValue()) {
            return;
        }
        this.waitted = 0;
        int sumOfDamage = 0;
        final List<ItemStack> armour = (List<ItemStack>)PacketXP.mc.player.inventory.armorInventory;
        for (int i = 0; i < armour.size(); ++i) {
            final ItemStack itemStack = armour.get(i);
            if (!itemStack.isEmpty) {
                final float damageOnArmor = (float)(itemStack.getMaxDamage() - itemStack.getItemDamage());
                final float damagePercent = 100.0f - 100.0f * (1.0f - damageOnArmor / itemStack.getMaxDamage());
                if (damagePercent <= (int)this.maxHeal.getValue()) {
                    if (damagePercent <= (int)this.minDamage.getValue()) {
                        this.toMend |= (char)(1 << i);
                    }
                    if (this.predict.getValue()) {
                        sumOfDamage += (int)(itemStack.getMaxDamage() * (int)this.maxHeal.getValue() / 100.0f - (itemStack.getMaxDamage() - itemStack.getItemDamage()));
                    }
                }
                else {
                    this.toMend &= (char)~(1 << i);
                }
            }
        }
        if (this.toMend > '\0') {
            if (this.predict.getValue()) {
                final int totalXp = PacketXP.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityXPOrb).filter(entity -> entity.getDistanceSq((Entity)PacketXP.mc.player) <= 1.0).mapToInt(entity -> entity.xpValue).sum();
                if (totalXp * 2 < sumOfDamage) {
                    this.mendArmor(PacketXP.mc.player.inventory.currentItem);
                }
            }
            else {
                this.mendArmor(PacketXP.mc.player.inventory.currentItem);
            }
            PacketXP.isActive = true;
        }
        else {
            PacketXP.isActive = false;
        }
    }
    
    private void mendArmor(final int oldSlot) {
        if (this.noEntityCollision.getValue()) {
            for (final EntityPlayer entityPlayer : PacketXP.mc.world.playerEntities) {
                if (entityPlayer.getDistance((Entity)PacketXP.mc.player) < 1.0f && entityPlayer != PacketXP.mc.player) {
                    return;
                }
            }
        }
        if ((boolean)this.key.getValue() && !LemonClient.PacketXPBind.isKeyDown()) {
            return;
        }
        final int newSlot = this.findXPSlot();
        if (newSlot == -1) {
            return;
        }
        if (oldSlot != newSlot) {
            this.switchTo(newSlot);
            PacketXP.mc.playerController.syncCurrentPlayItem();
        }
        this.switchTo(newSlot);
        PacketXP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 90.0f, true));
        PacketXP.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        this.switchTo(oldSlot);
        if (this.sync.getValue()) {
            PacketXP.mc.playerController.syncCurrentPlayItem();
        }
        if (this.TakeOff.getValue()) {
            this.takeArmorOff();
        }
    }
    
    private void takeArmorOff() {
        for (int slot = 5; slot <= 8; ++slot) {
            final ItemStack item = this.getArmor(slot);
            final double max_dam = item.getMaxDamage();
            final double dam_left = item.getMaxDamage() - item.getItemDamage();
            final double percent = dam_left / max_dam * 100.0;
            if (percent >= (int)this.maxHeal.getValue() && item.getItem() != Items.AIR) {
                if (!this.notInInv(Items.AIR)) {
                    return;
                }
                if (this.delay_count < (int)this.delay.getValue()) {
                    ++this.delay_count;
                    return;
                }
                this.delay_count = 0;
                PacketXP.mc.playerController.windowClick(0, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)PacketXP.mc.player);
            }
        }
    }
    
    private ItemStack getArmor(final int first) {
        return (ItemStack)PacketXP.mc.player.inventoryContainer.getInventory().get(first);
    }
    
    public Boolean notInInv(final Item itemOfChoice) {
        int n = 0;
        if (itemOfChoice == PacketXP.mc.player.getHeldItemOffhand().getItem()) {
            return true;
        }
        for (int i = 35; i >= 0; --i) {
            final Item item = PacketXP.mc.player.inventory.getStackInSlot(i).getItem();
            if (item == itemOfChoice) {
                return true;
            }
            ++n;
        }
        return n <= 35;
    }
    
    private int findXPSlot() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            if (PacketXP.mc.player.inventory.getStackInSlot(i).getItem() == Items.EXPERIENCE_BOTTLE) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || PacketXP.mc.player.inventory.currentItem != slot)) {
            if (this.silentSwitch.getValue()) {
                PacketXP.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                PacketXP.mc.player.inventory.currentItem = slot;
                PacketXP.mc.playerController.updateController();
            }
        }
    }
}
