//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.client.gui.inventory.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "AutoPot", category = Category.Dev)
public class AutoPot extends Module
{
    IntegerSetting health;
    IntegerSetting absorption;
    BooleanSetting only;
    IntegerSetting tick;
    IntegerSetting slot;
    BooleanSetting silentSwitch;
    BooleanSetting check;
    int Slot;
    int waited;
    boolean inv;
    
    public AutoPot() {
        this.health = this.registerInteger("Health", 16, 1, 20);
        this.absorption = this.registerInteger("Absorption", 0, 0, 16);
        this.only = this.registerBoolean("On GroundOnly", true);
        this.tick = this.registerInteger("Tick Delay", 1, 0, 20);
        this.slot = this.registerInteger("Slot", 1, 1, 9);
        this.silentSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
    }
    
    public void onUpdate() {
        if (AutoPot.mc.world == null || AutoPot.mc.player == null || AutoPot.mc.player.isDead) {
            return;
        }
        if (AutoPot.mc.currentScreen instanceof GuiContainer && !(AutoPot.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if (this.waited++ < (int)this.tick.getValue()) {
            return;
        }
        this.waited = 0;
        if ((boolean)this.only.getValue() && !AutoPot.mc.player.onGround) {
            return;
        }
        if (AutoPot.mc.player.getHealth() < (int)this.health.getValue() || AutoPot.mc.player.getAbsorptionAmount() < (int)this.absorption.getValue()) {
            this.Slot = InventoryUtil.getPotionInHotbar("healing");
            if (this.Slot == -1) {
                this.Slot = InventoryUtil.getPotionInInv("healing");
                if (this.Slot == -1) {
                    return;
                }
                this.inv = true;
            }
            else {
                this.inv = false;
            }
            if (this.inv) {
                this.swap(this.Slot, (int)this.slot.getValue() + 35);
            }
            AutoPot.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(AutoPot.mc.player.cameraYaw, 90.0f, AutoPot.mc.player.onGround));
            final int oldslot = AutoPot.mc.player.inventory.currentItem;
            this.switchTo(this.Slot);
            AutoPot.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            this.switchTo(oldslot);
        }
    }
    
    private void swap(final int InvSlot, final int newSlot) {
        AutoPot.mc.playerController.windowClick(0, InvSlot, 0, ClickType.SWAP, (EntityPlayer)AutoPot.mc.player);
        AutoPot.mc.playerController.windowClick(0, newSlot, 0, ClickType.SWAP, (EntityPlayer)AutoPot.mc.player);
        AutoPot.mc.playerController.updateController();
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || AutoPot.mc.player.inventory.currentItem != slot)) {
            if (this.silentSwitch.getValue()) {
                AutoPot.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                AutoPot.mc.player.inventory.currentItem = slot;
                AutoPot.mc.playerController.updateController();
            }
        }
    }
}
