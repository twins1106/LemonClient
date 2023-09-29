//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.player;

import net.minecraft.client.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;

public class InventoryUtil
{
    private static final Minecraft mc;
    
    public static void swap(final int InvSlot, final int newSlot) {
        InventoryUtil.mc.playerController.windowClick(0, InvSlot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
        InventoryUtil.mc.playerController.windowClick(0, newSlot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
        InventoryUtil.mc.playerController.windowClick(0, InvSlot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
        InventoryUtil.mc.playerController.updateController();
    }
    
    public static int findObsidianSlot(final boolean offHandActived, final boolean activeBefore) {
        int slot = -1;
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockObsidian) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    public static int findEChestSlot(final boolean offHandActived, final boolean activeBefore) {
        int slot = -1;
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockEnderChest) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    public static int findSkullSlot(final boolean offHandActived, final boolean activeBefore) {
        final int slot = -1;
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemSkull) {
                return i;
            }
        }
        return slot;
    }
    
    public static int findTotemSlot(final int lower, final int upper) {
        int slot = -1;
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = lower; i <= upper; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY && stack.getItem() == Items.TOTEM_OF_UNDYING) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    public static int findFirstItemSlot(final Class<? extends Item> itemToFind, final int lower, final int upper) {
        int slot = -1;
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = lower; i <= upper; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY) {
                if (itemToFind.isInstance(stack.getItem())) {
                    if (itemToFind.isInstance(stack.getItem())) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    public static int findStackInventory(final Item input, final boolean withHotbar) {
        int n;
        for (int i = n = (withHotbar ? 0 : 9); i < 36; ++i) {
            final Item item = InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem();
            if (Item.getIdFromItem(input) == Item.getIdFromItem(item)) {
                return i + ((i < 9) ? 36 : 0);
            }
        }
        return -1;
    }
    
    public static int getItemSlot(final Item input) {
        if (InventoryUtil.mc.player == null) {
            return 0;
        }
        for (int i = 0; i < InventoryUtil.mc.player.inventoryContainer.getInventory().size(); ++i) {
            if (i != 0 && i != 5 && i != 6 && i != 7 && i != 8) {
                final ItemStack s = (ItemStack)InventoryUtil.mc.player.inventoryContainer.getInventory().get(i);
                if (!s.isEmpty() && s.getItem() == input) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static int getItemInHotbar(final Item p_Item) {
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = InventoryUtil.mc.player.inventory.getStackInSlot(l_I);
            if (l_Stack != ItemStack.EMPTY && l_Stack.getItem() == p_Item) {
                return l_I;
            }
        }
        return -1;
    }
    
    public static int getPotionInHotbar(final String potion) {
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = InventoryUtil.mc.player.inventory.getStackInSlot(l_I);
            if (l_Stack != ItemStack.EMPTY) {
                if (Objects.requireNonNull(PotionUtils.getPotionFromItem(InventoryUtil.mc.player.inventory.getStackInSlot(l_I)).getRegistryName()).getPath().contains(potion)) {
                    return l_I;
                }
            }
        }
        return -1;
    }
    
    public static int getPotionInInv(final String potion) {
        for (int l_I = 9; l_I < 36; ++l_I) {
            final ItemStack l_Stack = InventoryUtil.mc.player.inventory.getStackInSlot(l_I);
            if (l_Stack != ItemStack.EMPTY) {
                if (Objects.requireNonNull(PotionUtils.getPotionFromItem(InventoryUtil.mc.player.inventory.getStackInSlot(l_I)).getRegistryName()).getPath().contains(potion)) {
                    return l_I;
                }
            }
        }
        return -1;
    }
    
    public static int getPotionHot(final Potion effect) {
        for (int slot = 0; slot < 9; ++slot) {
            final ItemStack itemStack = InventoryUtil.mc.player.inventory.getStackInSlot(slot);
            if (PotionUtils.getEffectsFromStack(itemStack).contains(effect)) {
                return slot;
            }
        }
        return -1;
    }
    
    public static int getPotion(final Potion effect) {
        for (int slot = 9; slot < 36; ++slot) {
            final ItemStack itemStack = InventoryUtil.mc.player.inventory.getStackInSlot(slot);
            if (PotionUtils.getEffectsFromStack(itemStack).contains(effect)) {
                return slot;
            }
        }
        return -1;
    }
    
    public static int findFirstBlockSlot(final Class<? extends Block> blockToFind, final int lower, final int upper) {
        int slot = -1;
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = lower; i <= upper; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    if (blockToFind.isInstance(((ItemBlock)stack.getItem()).getBlock())) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    public static List<Integer> findAllItemSlots(final Class<? extends Item> itemToFind) {
        final List<Integer> slots = new ArrayList<Integer>();
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = 0; i < 36; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY) {
                if (itemToFind.isInstance(stack.getItem())) {
                    slots.add(i);
                }
            }
        }
        return slots;
    }
    
    public static List<Integer> findAllBlockSlots(final Class<? extends Block> blockToFind) {
        final List<Integer> slots = new ArrayList<Integer>();
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        for (int i = 0; i < 36; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    if (blockToFind.isInstance(((ItemBlock)stack.getItem()).getBlock())) {
                        slots.add(i);
                    }
                }
            }
        }
        return slots;
    }
    
    public static int findToolForBlockState(final IBlockState iBlockState, final int lower, final int upper) {
        int slot = -1;
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.player.inventory.mainInventory;
        double foundMaxSpeed = 0.0;
        for (int i = lower; i <= upper; ++i) {
            final ItemStack itemStack = mainInventory.get(i);
            if (itemStack != ItemStack.EMPTY) {
                float breakSpeed = itemStack.getDestroySpeed(iBlockState);
                final int efficiencySpeed = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemStack);
                if (breakSpeed > 1.0f) {
                    breakSpeed += (float)((efficiencySpeed > 0) ? (Math.pow(efficiencySpeed, 2.0) + 1.0) : 0.0);
                    if (breakSpeed > foundMaxSpeed) {
                        foundMaxSpeed = breakSpeed;
                        slot = i;
                    }
                }
            }
        }
        return slot;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
