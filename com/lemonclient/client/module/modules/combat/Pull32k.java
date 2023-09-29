//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

@Module.Declaration(name = "Pull32k", category = Category.Combat)
public class Pull32k extends Module
{
    DoubleSetting range;
    boolean foundsword;
    
    public Pull32k() {
        this.range = this.registerDouble("Range", 7.5, 0.0, 64.0);
        this.foundsword = false;
    }
    
    public void onUpdate() {
        if (Pull32k.mc.world == null || Pull32k.mc.player == null || Pull32k.mc.player.isDead) {
            return;
        }
        if ((double)this.range.getValue() != 0.0) {
            final EntityPlayer enemy = PlayerUtil.getNearestPlayer((double)this.range.getValue());
            if (enemy == null) {
                return;
            }
        }
        boolean foundair = false;
        int enchantedSwordIndex = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = (ItemStack)Pull32k.mc.player.inventory.mainInventory.get(i);
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, itemStack) >= 25) {
                enchantedSwordIndex = i;
                this.foundsword = true;
            }
            if (!this.foundsword) {
                enchantedSwordIndex = -1;
            }
        }
        if (enchantedSwordIndex != -1 && Pull32k.mc.player.inventory.currentItem != enchantedSwordIndex) {
            Pull32k.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(enchantedSwordIndex));
            Pull32k.mc.player.inventory.currentItem = enchantedSwordIndex;
            Pull32k.mc.playerController.updateController();
        }
        if (enchantedSwordIndex == -1 && Pull32k.mc.player.openContainer != null && Pull32k.mc.player.openContainer instanceof ContainerHopper && Pull32k.mc.player.openContainer.inventorySlots != null && !Pull32k.mc.player.openContainer.inventorySlots.isEmpty()) {
            for (int i = 0; i < 5; ++i) {
                if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, Pull32k.mc.player.openContainer.inventorySlots.get(0).inventory.getStackInSlot(i)) >= 20) {
                    enchantedSwordIndex = i;
                    break;
                }
            }
            if (enchantedSwordIndex == -1) {
                return;
            }
            for (int i = 0; i < 9; ++i) {
                final ItemStack itemStack = (ItemStack)Pull32k.mc.player.inventory.mainInventory.get(i);
                if (itemStack.getItem() instanceof ItemAir) {
                    if (Pull32k.mc.player.inventory.currentItem != i) {
                        Pull32k.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(i));
                        Pull32k.mc.player.inventory.currentItem = i;
                        Pull32k.mc.playerController.updateController();
                    }
                    foundair = true;
                    break;
                }
            }
            if (foundair || this.checkStuff()) {
                Pull32k.mc.playerController.windowClick(Pull32k.mc.player.openContainer.windowId, enchantedSwordIndex, Pull32k.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Pull32k.mc.player);
            }
        }
    }
    
    public boolean checkStuff() {
        return EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, Pull32k.mc.player.inventory.getCurrentItem()) == 5;
    }
}
