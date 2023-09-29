//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "AutoSwiftness", category = Category.Dev)
public class AutoSwiftness extends Module
{
    BooleanSetting only;
    IntegerSetting tick;
    IntegerSetting slot;
    BooleanSetting silentSwitch;
    BooleanSetting check;
    int Slot;
    int waited;
    boolean inv;
    
    public AutoSwiftness() {
        this.only = this.registerBoolean("On GroundOnly", true);
        this.tick = this.registerInteger("Tick Delay", 1, 0, 20);
        this.slot = this.registerInteger("Slot", 1, 1, 9);
        this.silentSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
    }
    
    public void onUpdate() {
        if (AutoSwiftness.mc.world == null || AutoSwiftness.mc.player == null || AutoSwiftness.mc.player.isDead) {
            return;
        }
        if (AutoSwiftness.mc.currentScreen instanceof GuiContainer && !(AutoSwiftness.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if (this.waited++ < (int)this.tick.getValue()) {
            return;
        }
        this.waited = 0;
        if ((boolean)this.only.getValue() && !AutoSwiftness.mc.player.onGround) {
            return;
        }
        if (!AutoSwiftness.mc.player.isPotionActive(MobEffects.SPEED)) {
            this.Slot = InventoryUtil.getPotionInHotbar("swiftness");
            if (this.Slot == -1) {
                this.Slot = InventoryUtil.getPotionInInv("swiftness");
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
            AutoSwiftness.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(AutoSwiftness.mc.player.cameraYaw, 90.0f, AutoSwiftness.mc.player.onGround));
            final int oldslot = AutoSwiftness.mc.player.inventory.currentItem;
            this.switchTo(this.Slot);
            AutoSwiftness.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            this.switchTo(oldslot);
        }
    }
    
    private void swap(final int InvSlot, final int newSlot) {
        AutoSwiftness.mc.playerController.windowClick(0, InvSlot, 0, ClickType.SWAP, (EntityPlayer)AutoSwiftness.mc.player);
        AutoSwiftness.mc.playerController.windowClick(0, newSlot, 0, ClickType.SWAP, (EntityPlayer)AutoSwiftness.mc.player);
        AutoSwiftness.mc.playerController.updateController();
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || AutoSwiftness.mc.player.inventory.currentItem != slot)) {
            if (this.silentSwitch.getValue()) {
                AutoSwiftness.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                AutoSwiftness.mc.player.inventory.currentItem = slot;
                AutoSwiftness.mc.playerController.updateController();
            }
        }
    }
}
