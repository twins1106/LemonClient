//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "AutoWeakness", category = Category.Dev)
public class AutoWeakness extends Module
{
    DoubleSetting range;
    BooleanSetting only;
    IntegerSetting tick;
    IntegerSetting slot;
    BooleanSetting silentSwitch;
    BooleanSetting check;
    int Slot;
    int waited;
    boolean inv;
    
    public AutoWeakness() {
        this.range = this.registerDouble("Range", 5.5, 0.0, 10.0);
        this.only = this.registerBoolean("On GroundOnly", true);
        this.tick = this.registerInteger("Tick Delay", 1, 0, 20);
        this.slot = this.registerInteger("Slot", 1, 1, 9);
        this.silentSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
    }
    
    public void onUpdate() {
        if (AutoWeakness.mc.world == null || AutoWeakness.mc.player == null || AutoWeakness.mc.player.isDead) {
            return;
        }
        if (AutoWeakness.mc.currentScreen instanceof GuiContainer && !(AutoWeakness.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if (this.waited++ < (int)this.tick.getValue()) {
            return;
        }
        this.waited = 0;
        if ((boolean)this.only.getValue() && !AutoWeakness.mc.player.onGround) {
            return;
        }
        for (final EntityPlayer player : AutoWeakness.mc.world.playerEntities) {
            if (EntityUtil.basicChecksEntity(player)) {
                if (AutoWeakness.mc.player.getDistance((Entity)player) > (double)this.range.getValue()) {
                    continue;
                }
                if (player.isPotionActive(MobEffects.WEAKNESS)) {
                    continue;
                }
                this.Slot = InventoryUtil.getPotionInHotbar("weakness");
                if (this.Slot == -1) {
                    this.Slot = InventoryUtil.getPotionInInv("weakness");
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
                AutoWeakness.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(AutoWeakness.mc.player.cameraYaw, 90.0f, AutoWeakness.mc.player.onGround));
                final int oldslot = AutoWeakness.mc.player.inventory.currentItem;
                this.switchTo(this.Slot);
                AutoWeakness.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                this.switchTo(oldslot);
            }
        }
    }
    
    private void swap(final int InvSlot, final int newSlot) {
        AutoWeakness.mc.playerController.windowClick(0, InvSlot, 0, ClickType.SWAP, (EntityPlayer)AutoWeakness.mc.player);
        AutoWeakness.mc.playerController.windowClick(0, newSlot, 0, ClickType.SWAP, (EntityPlayer)AutoWeakness.mc.player);
        AutoWeakness.mc.playerController.updateController();
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || AutoWeakness.mc.player.inventory.currentItem != slot)) {
            if (this.silentSwitch.getValue()) {
                AutoWeakness.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                AutoWeakness.mc.player.inventory.currentItem = slot;
                AutoWeakness.mc.playerController.updateController();
            }
        }
    }
}
