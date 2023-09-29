//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.item.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.util.math.*;
import com.lemonclient.client.*;

@Module.Declaration(name = "AutoMend", category = Category.Combat)
public class AutoMend extends Module
{
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
    BooleanSetting crystal;
    DoubleSetting biasDamage;
    BooleanSetting health;
    IntegerSetting minHealth;
    BooleanSetting player;
    DoubleSetting maxSpeed;
    int tookOff;
    int delay_count;
    int waitted;
    char toMend;
    
    public AutoMend() {
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
        this.crystal = this.registerBoolean("Crystal Check", true);
        this.biasDamage = this.registerDouble("Bias Damage", 1.0, 0.0, 3.0);
        this.health = this.registerBoolean("Health Check", true);
        this.minHealth = this.registerInteger("Min Health", 16, 0, 36, () -> (Boolean)this.health.getValue());
        this.player = this.registerBoolean("Player Check", true);
        this.maxSpeed = this.registerDouble("Max Speed", 4.0, 0.0, 20.0, () -> (Boolean)this.player.getValue());
        this.toMend = '\0';
    }
    
    public void onEnable() {
        this.tookOff = 0;
    }
    
    public void onUpdate() {
        if (AutoMend.mc.player == null || AutoMend.mc.world == null || AutoMend.mc.player.isDead || AutoMend.mc.player.ticksExisted < 10) {
            this.disable();
            return;
        }
        this.check();
        if (this.waitted++ < (int)this.delay2.getValue()) {
            return;
        }
        this.waitted = 0;
        int sumOfDamage = 0;
        final List<ItemStack> armour = (List<ItemStack>)AutoMend.mc.player.inventory.armorInventory;
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
                final int totalXp = AutoMend.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityXPOrb).filter(entity -> entity.getDistanceSq((Entity)AutoMend.mc.player) <= 1.0).mapToInt(entity -> entity.xpValue).sum();
                if (totalXp * 2 < sumOfDamage) {
                    this.mendArmor(AutoMend.mc.player.inventory.currentItem);
                }
            }
            else {
                this.mendArmor(AutoMend.mc.player.inventory.currentItem);
            }
        }
    }
    
    private void mendArmor(final int oldSlot) {
        if (this.noEntityCollision.getValue()) {
            for (final EntityPlayer entityPlayer : AutoMend.mc.world.playerEntities) {
                if (entityPlayer.getDistance((Entity)AutoMend.mc.player) < 1.0f && entityPlayer != AutoMend.mc.player) {
                    return;
                }
            }
        }
        final int newSlot = this.findXPSlot();
        if (newSlot == -1) {
            return;
        }
        if (oldSlot != newSlot) {
            this.switchTo(newSlot);
            AutoMend.mc.playerController.syncCurrentPlayItem();
        }
        this.switchTo(newSlot);
        AutoMend.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 90.0f, true));
        AutoMend.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        this.switchTo(oldSlot);
        if (this.sync.getValue()) {
            AutoMend.mc.playerController.syncCurrentPlayItem();
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
                AutoMend.mc.playerController.windowClick(0, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoMend.mc.player);
            }
        }
    }
    
    private ItemStack getArmor(final int first) {
        return (ItemStack)AutoMend.mc.player.inventoryContainer.getInventory().get(first);
    }
    
    public Boolean notInInv(final Item itemOfChoice) {
        int n = 0;
        if (itemOfChoice == AutoMend.mc.player.getHeldItemOffhand().getItem()) {
            return true;
        }
        for (int i = 35; i >= 0; --i) {
            final Item item = AutoMend.mc.player.inventory.getStackInSlot(i).getItem();
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
            if (AutoMend.mc.player.inventory.getStackInSlot(i).getItem() == Items.EXPERIENCE_BOTTLE) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || AutoMend.mc.player.inventory.currentItem != slot)) {
            if (this.silentSwitch.getValue()) {
                AutoMend.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                AutoMend.mc.player.inventory.currentItem = slot;
                AutoMend.mc.playerController.updateController();
            }
        }
    }
    
    private boolean crystalDamage() {
        for (final Entity t : AutoMend.mc.world.loadedEntityList) {
            if (t instanceof EntityEnderCrystal && AutoMend.mc.player.getDistance(t) <= 12.0f && DamageUtil.calculateDamage(t.posX, t.posY, t.posZ, (Entity)AutoMend.mc.player) * (double)this.biasDamage.getValue() >= AutoMend.mc.player.getHealth()) {
                return true;
            }
        }
        return false;
    }
    
    private void check() {
        if ((boolean)this.crystal.getValue() && this.crystalDamage()) {
            this.setDisabledMessage("Lethal crystal nearby");
            this.disable();
            return;
        }
        if ((boolean)this.health.getValue() && AutoMend.mc.player.getHealth() + AutoMend.mc.player.getAbsorptionAmount() < (int)this.minHealth.getValue()) {
            this.setDisabledMessage("Low health");
            this.disable();
            return;
        }
        if ((boolean)this.player.getValue() && this.checkNearbyPlayers()) {
            this.setDisabledMessage("Players nearby");
            this.disable();
            return;
        }
        if (this.findXPSlot() == -1) {
            this.setDisabledMessage("No xp bottle found in hotbar");
            this.disable();
            return;
        }
        if (this.checkFinished()) {
            this.setDisabledMessage("Finished mending armors");
            this.disable();
        }
    }
    
    private boolean checkNearbyPlayers() {
        final AxisAlignedBB box = new AxisAlignedBB(AutoMend.mc.player.posX - 0.5, AutoMend.mc.player.posY - 0.5, AutoMend.mc.player.posZ - 0.5, AutoMend.mc.player.posX + 0.5, AutoMend.mc.player.posY + 2.5, AutoMend.mc.player.posZ + 0.5);
        for (final EntityPlayer entity : AutoMend.mc.world.playerEntities) {
            if (entity != AutoMend.mc.player && AutoMend.mc.player.connection.getPlayerInfo(entity.getName()) != null && !entity.isDead && entity.getHealth() > 0.0f) {
                if (LemonClient.speedUtil.getPlayerSpeed(entity) >= (double)this.maxSpeed.getValue()) {
                    continue;
                }
                if (box.intersects(entity.getEntityBoundingBox())) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    private boolean checkFinished() {
        int finished = 0;
        for (int slot = 5; slot <= 8; ++slot) {
            final ItemStack item = this.getArmor(slot);
            final double max_dam = item.getMaxDamage();
            final double dam_left = item.getMaxDamage() - item.getItemDamage();
            final double percent = dam_left / max_dam * 100.0;
            if (percent >= (int)this.maxHeal.getValue() || item == ItemStack.EMPTY) {
                ++finished;
            }
        }
        return finished >= 4;
    }
}
