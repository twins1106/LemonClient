//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.player.*;
import java.util.stream.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.block.*;

@Module.Declaration(name = "HotbarRefill", category = Category.Misc)
public class HotbarRefill extends Module
{
    IntegerSetting threshold;
    IntegerSetting tickDelay;
    private int delayStep;
    
    public HotbarRefill() {
        this.threshold = this.registerInteger("Threshold", 32, 1, 63);
        this.tickDelay = this.registerInteger("Tick Delay", 2, 1, 10);
        this.delayStep = 0;
    }
    
    public void onUpdate() {
        if (HotbarRefill.mc.player == null) {
            return;
        }
        if (HotbarRefill.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.delayStep < (int)this.tickDelay.getValue()) {
            ++this.delayStep;
            return;
        }
        this.delayStep = 0;
        final Pair<Integer, Integer> slots = this.findReplenishableHotbarSlot();
        if (slots == null) {
            return;
        }
        final int inventorySlot = (int)slots.getKey();
        final int hotbarSlot = (int)slots.getValue();
        HotbarRefill.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)HotbarRefill.mc.player);
        HotbarRefill.mc.playerController.windowClick(0, hotbarSlot + 36, 0, ClickType.PICKUP, (EntityPlayer)HotbarRefill.mc.player);
        HotbarRefill.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)HotbarRefill.mc.player);
    }
    
    private Pair<Integer, Integer> findReplenishableHotbarSlot() {
        final List<ItemStack> inventory = (List<ItemStack>)HotbarRefill.mc.player.inventory.mainInventory;
        for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
            final ItemStack stack = inventory.get(hotbarSlot);
            if (stack.isStackable()) {
                if (!stack.isEmpty) {
                    if (stack.getItem() != Items.AIR) {
                        if (stack.stackSize < stack.getMaxStackSize()) {
                            if (stack.stackSize <= (int)this.threshold.getValue()) {
                                final int inventorySlot = this.findCompatibleInventorySlot(stack);
                                if (inventorySlot != -1) {
                                    return (Pair<Integer, Integer>)new Pair((Object)inventorySlot, (Object)hotbarSlot);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    
    private int findCompatibleInventorySlot(final ItemStack hotbarStack) {
        final Item item = hotbarStack.getItem();
        List<Integer> potentialSlots;
        if (item instanceof ItemBlock) {
            potentialSlots = (List<Integer>)InventoryUtil.findAllBlockSlots((Class)((ItemBlock)item).getBlock().getClass());
        }
        else {
            potentialSlots = (List<Integer>)InventoryUtil.findAllItemSlots((Class)item.getClass());
        }
        potentialSlots = potentialSlots.stream().filter(integer -> integer > 8 && integer < 36).sorted(Comparator.comparingInt(interger -> -interger)).collect((Collector<? super Object, ?, List<Integer>>)Collectors.toList());
        for (final int slot : potentialSlots) {
            if (this.isCompatibleStacks(hotbarStack, HotbarRefill.mc.player.inventory.getStackInSlot(slot))) {
                return slot;
            }
        }
        return -1;
    }
    
    private boolean isCompatibleStacks(final ItemStack stack1, final ItemStack stack2) {
        if (!stack1.getItem().equals(stack2.getItem())) {
            return false;
        }
        if (stack1.getItem() instanceof ItemBlock && stack2.getItem() instanceof ItemBlock) {
            final Block block1 = ((ItemBlock)stack1.getItem()).getBlock();
            final Block block2 = ((ItemBlock)stack2.getItem()).getBlock();
            if (!block1.material.equals(block2.material)) {
                return false;
            }
        }
        return stack1.getDisplayName().equals(stack2.getDisplayName()) && stack1.getItemDamage() == stack2.getItemDamage();
    }
}
