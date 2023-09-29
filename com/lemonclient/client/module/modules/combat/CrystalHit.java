//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

@Module.Declaration(name = "CrystalHit", category = Category.Combat)
public class CrystalHit extends Module
{
    IntegerSetting range;
    IntegerSetting delay;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting antiWeakness;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting silent;
    private boolean isAttacking;
    private int oldSlot;
    
    public CrystalHit() {
        this.range = this.registerInteger("Range", 4, 0, 10);
        this.delay = this.registerInteger("Delay", 0, 0, 40);
        this.packet = this.registerBoolean("Packet Break", false);
        this.swing = this.registerBoolean("Swing", false);
        this.antiWeakness = this.registerBoolean("Anti Weakness", false);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.silent = this.registerBoolean("Silent Switch", false);
        this.isAttacking = false;
        this.oldSlot = -1;
    }
    
    public void onUpdate() {
        final EntityEnderCrystal crystal = (EntityEnderCrystal)CrystalHit.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).map(entity -> entity).min(Comparator.comparing(c -> CrystalHit.mc.player.getDistance(c))).orElse(null);
        if (crystal != null && CrystalHit.mc.player.getDistance((Entity)crystal) <= (int)this.range.getValue()) {
            final int delaytime = 0;
            if (delaytime >= (int)this.delay.getValue()) {
                if ((boolean)this.antiWeakness.getValue() && CrystalHit.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                    if (!this.isAttacking) {
                        this.oldSlot = CrystalHit.mc.player.inventory.currentItem;
                        this.isAttacking = true;
                    }
                    int newSlot = -1;
                    for (int i = 0; i < 9; ++i) {
                        final ItemStack stack = CrystalHit.mc.player.inventory.getStackInSlot(i);
                        if (stack != ItemStack.EMPTY) {
                            if (stack.getItem() instanceof ItemSword) {
                                newSlot = i;
                                break;
                            }
                            if (stack.getItem() instanceof ItemTool) {
                                newSlot = i;
                                break;
                            }
                        }
                    }
                    if (newSlot != -1) {
                        this.switchTo(newSlot);
                    }
                }
                if (!(boolean)this.packet.getValue()) {
                    CrystalUtil.breakCrystal((Entity)crystal, (boolean)this.swing.getValue());
                }
                else {
                    CrystalUtil.breakCrystalPacket((Entity)crystal, (boolean)this.swing.getValue());
                }
                if (this.silent.getValue()) {
                    this.switchTo(this.oldSlot);
                }
            }
        }
        else {
            if (this.oldSlot != -1) {
                CrystalHit.mc.player.inventory.currentItem = this.oldSlot;
                this.oldSlot = -1;
            }
            this.isAttacking = false;
        }
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || CrystalHit.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                CrystalHit.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                CrystalHit.mc.player.inventory.currentItem = slot;
            }
            CrystalHit.mc.playerController.updateController();
        }
    }
}
