//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import net.minecraftforge.event.entity.player.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import com.lemonclient.client.module.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.inventory.*;
import java.util.concurrent.atomic.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "AutoArmor", category = Category.Combat)
public class AutoArmor extends Module
{
    IntegerSetting delay;
    BooleanSetting strict;
    BooleanSetting stackArmor;
    IntegerSetting slot;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting armorSaver;
    BooleanSetting pauseWhenSafe;
    IntegerSetting depletion;
    BooleanSetting allowMend;
    Timing rightClickTimer;
    private boolean sleep;
    @EventHandler
    private final Listener<PlayerInteractEvent.RightClickItem> listener;
    
    public AutoArmor() {
        this.delay = this.registerInteger("Delay", 1, 1, 10);
        this.strict = this.registerBoolean("Strict", false);
        this.stackArmor = this.registerBoolean("Stack Armor", false);
        this.slot = this.registerInteger("Swap Slot", 1, 1, 9, () -> (Boolean)this.stackArmor.getValue());
        this.packetSwitch = this.registerBoolean("Packet Switch", true, () -> (Boolean)this.stackArmor.getValue());
        this.check = this.registerBoolean("Switch Check", true, () -> (Boolean)this.stackArmor.getValue());
        this.armorSaver = this.registerBoolean("Armor Saver", false);
        this.pauseWhenSafe = this.registerBoolean("Pause When Safe", false);
        this.depletion = this.registerInteger("Depletion", 20, 0, 99, () -> (Boolean)this.armorSaver.getValue());
        this.allowMend = this.registerBoolean("Allow Mend", false);
        this.rightClickTimer = new Timing();
        this.listener = (Listener<PlayerInteractEvent.RightClickItem>)new Listener(event -> {
            if (event.getEntityPlayer() != AutoArmor.mc.player) {
                return;
            }
            if (event.getItemStack().getItem() != Items.EXPERIENCE_BOTTLE) {
                return;
            }
            this.rightClickTimer.reset();
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        if (AutoArmor.mc.world == null || AutoArmor.mc.player == null || AutoArmor.mc.player.isDead) {
            return;
        }
        if (AutoArmor.mc.player.ticksExisted % (int)this.delay.getValue() != 0) {
            return;
        }
        if ((boolean)this.strict.getValue() && (AutoArmor.mc.player.motionX != 0.0 || AutoArmor.mc.player.motionZ != 0.0)) {
            return;
        }
        if (this.pauseWhenSafe.getValue()) {
            final List<Entity> proximity = (List<Entity>)AutoArmor.mc.world.loadedEntityList.stream().filter(e -> (e instanceof EntityPlayer && !e.equals((Object)AutoArmor.mc.player) && AutoArmor.mc.player.getDistance(e) <= 6.0f) || (e instanceof EntityEnderCrystal && AutoArmor.mc.player.getDistance(e) <= 12.0f)).collect(Collectors.toList());
            if (proximity.isEmpty()) {
                return;
            }
        }
        if (PacketXP.isActive || ModuleManager.isModuleEnabled((Class)AutoMend.class)) {
            return;
        }
        if ((boolean)this.allowMend.getValue() && !this.rightClickTimer.passedMs(500L)) {
            for (int i = 0; i < AutoArmor.mc.player.inventory.armorInventory.size(); ++i) {
                final ItemStack armorPiece = (ItemStack)AutoArmor.mc.player.inventory.armorInventory.get(i);
                if (armorPiece.isEmpty) {
                    return;
                }
                boolean mending = false;
                for (final Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.getEnchantments(armorPiece).entrySet()) {
                    if (entry.getKey().getName().contains("mending")) {
                        mending = true;
                        break;
                    }
                }
                if (mending) {
                    if (!armorPiece.isEmpty()) {
                        final long freeSlots = AutoArmor.mc.player.inventory.mainInventory.stream().filter(is -> is.isEmpty() || is.getItem() == Items.AIR).map(is -> AutoArmor.mc.player.inventory.getSlotFor(is)).count();
                        if (freeSlots <= 0L) {
                            return;
                        }
                        if (armorPiece.getItemDamage() != 0) {
                            this.shiftClickSpot(8 - i);
                            return;
                        }
                    }
                }
            }
            return;
        }
        if (AutoArmor.mc.currentScreen instanceof GuiContainer && !(AutoArmor.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        final AtomicBoolean hasSwapped = new AtomicBoolean(false);
        if (this.sleep) {
            this.sleep = false;
            return;
        }
        final Set<InvStack> replacements = new HashSet<InvStack>();
        for (int slot = 0; slot < 36; ++slot) {
            final InvStack invStack2 = new InvStack(slot, AutoArmor.mc.player.inventory.getStackInSlot(slot));
            if (invStack2.stack.getItem() instanceof ItemArmor || invStack2.stack.getItem() instanceof ItemElytra) {
                replacements.add(invStack2);
            }
        }
        List<InvStack> armors = replacements.stream().filter(invStack -> invStack.stack.getItem() instanceof ItemArmor).filter(invStack -> !(boolean)this.armorSaver.getValue() || invStack.stack.getItem().getDurabilityForDisplay(invStack.stack) < (int)this.depletion.getValue()).sorted(Comparator.comparingInt(invStack -> invStack.slot)).sorted(Comparator.comparingInt(invStack -> ((ItemArmor)invStack.stack.getItem()).damageReduceAmount)).collect((Collector<? super Object, ?, List<InvStack>>)Collectors.toList());
        final boolean wasEmpty = armors.isEmpty();
        if (wasEmpty) {
            armors = replacements.stream().filter(invStack -> invStack.stack.getItem() instanceof ItemArmor).sorted(Comparator.comparingInt(invStack -> invStack.slot)).sorted(Comparator.comparingInt(invStack -> ((ItemArmor)invStack.stack.getItem()).damageReduceAmount)).collect((Collector<? super Object, ?, List<InvStack>>)Collectors.toList());
        }
        final ItemStack currentHeadItem = AutoArmor.mc.player.inventory.getStackInSlot(39);
        final ItemStack currentChestItem = AutoArmor.mc.player.inventory.getStackInSlot(38);
        final ItemStack currentLegsItem = AutoArmor.mc.player.inventory.getStackInSlot(37);
        final ItemStack currentFeetItem = AutoArmor.mc.player.inventory.getStackInSlot(36);
        final boolean saveHead = !wasEmpty && currentHeadItem.getCount() == 1 && (boolean)this.armorSaver.getValue() && this.getItemDamage(39) <= (int)this.depletion.getValue();
        final boolean saveChest = !wasEmpty && currentChestItem.getCount() == 1 && (boolean)this.armorSaver.getValue() && this.getItemDamage(38) <= (int)this.depletion.getValue();
        final boolean saveLegs = !wasEmpty && currentLegsItem.getCount() == 1 && (boolean)this.armorSaver.getValue() && this.getItemDamage(37) <= (int)this.depletion.getValue();
        final boolean saveFeet = !wasEmpty && currentFeetItem.getCount() == 1 && (boolean)this.armorSaver.getValue() && this.getItemDamage(36) <= (int)this.depletion.getValue();
        final boolean replaceHead = currentHeadItem.isEmpty || saveHead;
        final boolean replaceChest = currentChestItem.isEmpty || saveChest;
        final boolean replaceLegs = currentLegsItem.isEmpty || saveLegs;
        final boolean replaceFeet = currentFeetItem.isEmpty || saveFeet;
        if (replaceHead && !hasSwapped.get()) {
            final AtomicBoolean atomicBoolean;
            armors.stream().filter(invStack -> invStack.stack.getItem() instanceof ItemArmor).filter(invStack -> ((ItemArmor)invStack.stack.getItem()).armorType.equals((Object)EntityEquipmentSlot.HEAD)).filter(invStack -> !saveHead || this.getItemDamage(invStack.slot) > (int)this.depletion.getValue()).findFirst().ifPresent(invStack -> {
                this.swapSlot(invStack.slot, 5);
                atomicBoolean.set(true);
                return;
            });
        }
        if (replaceChest || (currentChestItem.getItem() instanceof ItemElytra && !hasSwapped.get())) {
            final AtomicBoolean atomicBoolean2;
            armors.stream().filter(invStack -> invStack.stack.getItem() instanceof ItemArmor).filter(invStack -> ((ItemArmor)invStack.stack.getItem()).armorType.equals((Object)EntityEquipmentSlot.CHEST)).filter(invStack -> !saveChest || this.getItemDamage(invStack.slot) > (int)this.depletion.getValue()).findFirst().ifPresent(invStack -> {
                this.swapSlot(invStack.slot, 6);
                atomicBoolean2.set(true);
                return;
            });
        }
        if (replaceLegs && !hasSwapped.get()) {
            final AtomicBoolean atomicBoolean3;
            armors.stream().filter(invStack -> invStack.stack.getItem() instanceof ItemArmor).filter(invStack -> ((ItemArmor)invStack.stack.getItem()).armorType.equals((Object)EntityEquipmentSlot.LEGS)).filter(invStack -> !saveLegs || this.getItemDamage(invStack.slot) > (int)this.depletion.getValue()).findFirst().ifPresent(invStack -> {
                this.swapSlot(invStack.slot, 7);
                atomicBoolean3.set(true);
                return;
            });
        }
        if (replaceFeet && !hasSwapped.get()) {
            final AtomicBoolean atomicBoolean4;
            armors.stream().filter(invStack -> invStack.stack.getItem() instanceof ItemArmor).filter(invStack -> ((ItemArmor)invStack.stack.getItem()).armorType.equals((Object)EntityEquipmentSlot.FEET)).filter(invStack -> !saveFeet || this.getItemDamage(invStack.slot) > (int)this.depletion.getValue()).findFirst().ifPresent(invStack -> {
                this.swapSlot(invStack.slot, 8);
                atomicBoolean4.set(true);
            });
        }
    }
    
    private int getItemDamage(final int slot) {
        final ItemStack itemStack = AutoArmor.mc.player.inventory.getStackInSlot(slot);
        final float green = (itemStack.getMaxDamage() - (float)itemStack.getItemDamage()) / itemStack.getMaxDamage();
        final float red = 1.0f - green;
        return 100 - (int)(red * 100.0f);
    }
    
    private void swapSlot(final int source, final int target) {
        final ItemStack sourceStack = AutoArmor.mc.player.inventory.getStackInSlot(source);
        final boolean stacked = sourceStack.getCount() > 1;
        if (stacked) {
            this.swapStack(source, target);
        }
        else {
            this.swap(source, target);
        }
        this.sleep = true;
    }
    
    private void swapStack(final int slotFrom, final int slotTo) {
        if (!(boolean)this.stackArmor.getValue()) {
            return;
        }
        if (AutoArmor.mc.player.inventory.getStackInSlot(slotTo) != ItemStack.EMPTY) {
            AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, slotTo, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmor.mc.player);
        }
        final int oldslot = AutoArmor.mc.player.inventory.currentItem;
        if (slotFrom >= 9) {
            this.swapToHotbar(slotFrom);
        }
        this.switchTo((int)this.slot.getValue() - 1);
        AutoArmor.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        this.switchTo(oldslot);
        AutoArmor.mc.playerController.updateController();
        if (slotFrom >= 9) {
            this.swapToHotbar(slotFrom);
        }
    }
    
    private void swapToHotbar(final int InvSlot) {
        if ((int)this.slot.getValue() == 1) {
            AutoArmor.mc.playerController.windowClick(0, InvSlot, 0, ClickType.SWAP, (EntityPlayer)AutoArmor.mc.player);
        }
        else {
            AutoArmor.mc.playerController.windowClick(0, InvSlot, 0, ClickType.SWAP, (EntityPlayer)AutoArmor.mc.player);
            AutoArmor.mc.playerController.windowClick(0, (int)this.slot.getValue() + 35, 0, ClickType.SWAP, (EntityPlayer)AutoArmor.mc.player);
            AutoArmor.mc.playerController.windowClick(0, InvSlot, 0, ClickType.SWAP, (EntityPlayer)AutoArmor.mc.player);
        }
        AutoArmor.mc.playerController.updateController();
    }
    
    private void swap(final int slotFrom, final int slotTo) {
        if (AutoArmor.mc.player.inventory.getStackInSlot(slotTo).isEmpty) {
            AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, (slotFrom < 9) ? (slotFrom + 36) : slotFrom, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmor.mc.player);
        }
        else {
            boolean hasEmpty = false;
            for (int l_I = 0; l_I < 36; ++l_I) {
                final ItemStack l_Stack = AutoArmor.mc.player.inventory.getStackInSlot(l_I);
                if (l_Stack.isEmpty) {
                    hasEmpty = true;
                    break;
                }
            }
            if (hasEmpty) {
                AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, slotTo, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmor.mc.player);
                AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, (slotFrom < 9) ? (slotFrom + 36) : slotFrom, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmor.mc.player);
            }
            else {
                AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, (slotFrom < 9) ? (slotFrom + 36) : slotFrom, 0, ClickType.PICKUP, (EntityPlayer)AutoArmor.mc.player);
                AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, slotTo, 0, ClickType.PICKUP, (EntityPlayer)AutoArmor.mc.player);
                AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, (slotFrom < 9) ? (slotFrom + 36) : slotFrom, 0, ClickType.PICKUP, (EntityPlayer)AutoArmor.mc.player);
            }
        }
        AutoArmor.mc.playerController.updateController();
    }
    
    private void shiftClickSpot(final int source) {
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, source, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmor.mc.player);
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || AutoArmor.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                AutoArmor.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                AutoArmor.mc.player.inventory.currentItem = slot;
            }
            AutoArmor.mc.playerController.updateController();
        }
    }
}
