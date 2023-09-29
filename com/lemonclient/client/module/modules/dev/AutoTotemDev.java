//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import java.util.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.function.*;

@Module.Declaration(name = "AutoTotemDev", category = Category.Dev)
public class AutoTotemDev extends Module
{
    ModeSetting modeSetting;
    BooleanSetting hotbar;
    IntegerSetting delay;
    int totems;
    int hasWaited;
    boolean moving;
    boolean returnI;
    
    public AutoTotemDev() {
        this.modeSetting = this.registerMode("Mode", (List)Arrays.asList("Neither", "Replace Offhand", "Inventory"), "Replace Offhand");
        this.hotbar = this.registerBoolean("HotBar", false);
        this.delay = this.registerInteger("Delay", 0, 0, 20);
        this.moving = false;
        this.returnI = false;
    }
    
    public void onUpdate() {
        if (this.hasWaited < (int)this.delay.getValue()) {
            ++this.hasWaited;
            return;
        }
        this.hasWaited = 0;
        if (!((String)this.modeSetting.getValue()).equals("Inventory") && AutoTotemDev.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.returnI) {
            int t = -1;
            for (int i = 0; i < 45; ++i) {
                if (AutoTotemDev.mc.player.inventory.getStackInSlot(i).isEmpty) {
                    t = i;
                    break;
                }
            }
            if (t == -1) {
                return;
            }
            AutoTotemDev.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)AutoTotemDev.mc.player);
            this.returnI = false;
        }
        this.totems = AutoTotemDev.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (AutoTotemDev.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++this.totems;
        }
        else {
            if (!((String)this.modeSetting.getValue()).equals("Replace Offhand") && !AutoTotemDev.mc.player.getHeldItemOffhand().isEmpty) {
                return;
            }
            if (this.moving) {
                AutoTotemDev.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotemDev.mc.player);
                this.moving = false;
                if (!AutoTotemDev.mc.player.inventory.itemStack.isEmpty()) {
                    this.returnI = true;
                }
                return;
            }
            if (AutoTotemDev.mc.player.inventory.itemStack.isEmpty()) {
                if (this.totems == 0) {
                    return;
                }
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (AutoTotemDev.mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return;
                }
                AutoTotemDev.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)AutoTotemDev.mc.player);
                this.moving = true;
            }
            else if (((String)this.modeSetting.getValue()).equals("Replace Offhand")) {
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (AutoTotemDev.mc.player.inventory.getStackInSlot(i).isEmpty) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return;
                }
                AutoTotemDev.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)AutoTotemDev.mc.player);
            }
        }
        if (this.hotbar.getValue()) {
            if (AutoTotemDev.mc.player.inventory.getStackInSlot(0).getItem() == Items.TOTEM_OF_UNDYING) {
                return;
            }
            for (int j = 9; j < 35; ++j) {
                if (AutoTotemDev.mc.player.inventory.getStackInSlot(j).getItem() == Items.TOTEM_OF_UNDYING) {
                    AutoTotemDev.mc.playerController.windowClick(AutoTotemDev.mc.player.inventoryContainer.windowId, j, 0, ClickType.SWAP, (EntityPlayer)AutoTotemDev.mc.player);
                    AutoTotemDev.mc.playerController.windowClick(AutoTotemDev.mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotemDev.mc.player);
                    break;
                }
            }
        }
    }
}
