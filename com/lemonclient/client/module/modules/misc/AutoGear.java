//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.client.command.commands.*;
import com.lemonclient.client.module.modules.combat.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import java.util.*;

@Module.Declaration(name = "AutoGear", category = Category.Misc)
public class AutoGear extends Module
{
    IntegerSetting tickDelay;
    IntegerSetting switchForTick;
    BooleanSetting enderChest;
    BooleanSetting confirmSort;
    BooleanSetting invasive;
    BooleanSetting closeAfter;
    BooleanSetting infoMsgs;
    BooleanSetting debugMode;
    private HashMap<Integer, String> planInventory;
    private final HashMap<Integer, String> containerInv;
    private ArrayList<Integer> sortItems;
    private int delayTimeTicks;
    private int stepNow;
    private boolean openedBefore;
    private boolean finishSort;
    private boolean doneBefore;
    
    public AutoGear() {
        this.tickDelay = this.registerInteger("Tick Delay", 0, 0, 20);
        this.switchForTick = this.registerInteger("Switch Per Tick", 1, 1, 100);
        this.enderChest = this.registerBoolean("EnderChest", false);
        this.confirmSort = this.registerBoolean("Confirm Sort", true);
        this.invasive = this.registerBoolean("Invasive", false);
        this.closeAfter = this.registerBoolean("Close After", false);
        this.infoMsgs = this.registerBoolean("Info Msgs", true);
        this.debugMode = this.registerBoolean("Debug Mode", false);
        this.planInventory = new HashMap<Integer, String>();
        this.containerInv = new HashMap<Integer, String>();
        this.sortItems = new ArrayList<Integer>();
    }
    
    public void onEnable() {
        final String curConfigName = AutoGearCommand.getCurrentSet();
        if (curConfigName.equals("")) {
            this.disable();
            return;
        }
        if (this.infoMsgs.getValue()) {
            PistonCrystal.printDebug("Config " + curConfigName + " actived", Boolean.valueOf(false));
        }
        final String inventoryConfig = AutoGearCommand.getInventoryKit(curConfigName);
        if (inventoryConfig.equals("")) {
            this.disable();
            return;
        }
        final String[] inventoryDivided = inventoryConfig.split(" ");
        this.planInventory = new HashMap<Integer, String>();
        final HashMap<String, Integer> nItems = new HashMap<String, Integer>();
        for (int i = 0; i < inventoryDivided.length; ++i) {
            if (!inventoryDivided[i].contains("air")) {
                this.planInventory.put(i, inventoryDivided[i]);
                if (nItems.containsKey(inventoryDivided[i])) {
                    nItems.put(inventoryDivided[i], nItems.get(inventoryDivided[i]) + 1);
                }
                else {
                    nItems.put(inventoryDivided[i], 1);
                }
            }
        }
        this.delayTimeTicks = 0;
        final boolean b = false;
        this.doneBefore = b;
        this.openedBefore = b;
    }
    
    public void onDisable() {
        if ((boolean)this.infoMsgs.getValue() && this.planInventory.size() > 0) {
            PistonCrystal.printDebug("AutoSort Disabled!", Boolean.valueOf(true));
        }
    }
    
    public void onUpdate() {
        if (this.delayTimeTicks < (int)this.tickDelay.getValue()) {
            ++this.delayTimeTicks;
            return;
        }
        this.delayTimeTicks = 0;
        if (this.planInventory.size() == 0) {
            this.disable();
        }
        if ((AutoGear.mc.player.openContainer instanceof ContainerChest && ((boolean)this.enderChest.getValue() || !((ContainerChest)AutoGear.mc.player.openContainer).getLowerChestInventory().getDisplayName().getUnformattedText().equals("Ender Chest"))) || AutoGear.mc.player.openContainer instanceof ContainerShulkerBox) {
            this.sortInventoryAlgo();
        }
        else {
            this.openedBefore = false;
        }
    }
    
    private void sortInventoryAlgo() {
        if (!this.openedBefore) {
            if ((boolean)this.infoMsgs.getValue() && !this.doneBefore) {
                PistonCrystal.printDebug("Start sorting inventory...", Boolean.valueOf(false));
            }
            final int maxValue = (AutoGear.mc.player.openContainer instanceof ContainerChest) ? ((ContainerChest)AutoGear.mc.player.openContainer).getLowerChestInventory().getSizeInventory() : 27;
            for (int i = 0; i < maxValue; ++i) {
                final ItemStack item = (ItemStack)AutoGear.mc.player.openContainer.getInventory().get(i);
                this.containerInv.put(i, Objects.requireNonNull(item.getItem().getRegistryName()).toString() + item.getMetadata());
            }
            this.openedBefore = true;
            final HashMap<Integer, String> inventoryCopy = this.getInventoryCopy(maxValue);
            final HashMap<Integer, String> aimInventory = this.getInventoryCopy(maxValue, this.planInventory);
            this.sortItems = this.getInventorySort(inventoryCopy, aimInventory, maxValue);
            if (this.sortItems.size() == 0 && !this.doneBefore) {
                this.finishSort = false;
                if (this.infoMsgs.getValue()) {
                    PistonCrystal.printDebug("Inventory already sorted...", Boolean.valueOf(true));
                }
                if (this.closeAfter.getValue()) {
                    AutoGear.mc.player.closeScreen();
                }
            }
            else {
                this.finishSort = true;
                this.stepNow = 0;
            }
            this.openedBefore = true;
        }
        else if (this.finishSort) {
            int j = 0;
            while (j < (int)this.switchForTick.getValue()) {
                if (!(AutoGear.mc.currentScreen instanceof GuiContainer)) {
                    return;
                }
                if (this.sortItems.size() != 0) {
                    final int slotChange = this.sortItems.get(this.stepNow++);
                    AutoGear.mc.playerController.windowClick(AutoGear.mc.player.openContainer.windowId, slotChange, 0, ClickType.PICKUP, (EntityPlayer)AutoGear.mc.player);
                }
                if (this.stepNow == this.sortItems.size()) {
                    if ((boolean)this.confirmSort.getValue() && !this.doneBefore) {
                        this.openedBefore = false;
                        this.finishSort = false;
                        this.doneBefore = true;
                        this.checkLastItem();
                        return;
                    }
                    this.finishSort = false;
                    if (this.infoMsgs.getValue()) {
                        PistonCrystal.printDebug("Inventory sorted", Boolean.valueOf(false));
                    }
                    this.checkLastItem();
                    this.doneBefore = false;
                    if (this.closeAfter.getValue()) {
                        AutoGear.mc.player.closeScreen();
                    }
                }
                else {
                    ++j;
                }
            }
        }
    }
    
    private void checkLastItem() {
        if (this.sortItems.size() != 0) {
            final int slotChange = this.sortItems.get(this.sortItems.size() - 1);
            if (((ItemStack)AutoGear.mc.player.openContainer.getInventory().get(slotChange)).isEmpty()) {
                AutoGear.mc.playerController.windowClick(0, slotChange, 0, ClickType.PICKUP, (EntityPlayer)AutoGear.mc.player);
            }
        }
    }
    
    private ArrayList<Integer> getInventorySort(final HashMap<Integer, String> copyInventory, final HashMap<Integer, String> planInventoryCopy, final int startValues) {
        final ArrayList<Integer> planMove = new ArrayList<Integer>();
        final HashMap<String, Integer> nItemsCopy = new HashMap<String, Integer>();
        for (final String value : planInventoryCopy.values()) {
            if (nItemsCopy.containsKey(value)) {
                nItemsCopy.put(value, nItemsCopy.get(value) + 1);
            }
            else {
                nItemsCopy.put(value, 1);
            }
        }
        final ArrayList<Integer> ignoreValues = new ArrayList<Integer>();
        final int[] listValue = new int[planInventoryCopy.size()];
        int id = 0;
        for (final int idx : planInventoryCopy.keySet()) {
            listValue[id++] = idx;
        }
        for (final int item : listValue) {
            if (copyInventory.get(item).equals(planInventoryCopy.get(item))) {
                ignoreValues.add(item);
                nItemsCopy.put(planInventoryCopy.get(item), nItemsCopy.get(planInventoryCopy.get(item)) - 1);
                if (nItemsCopy.get(planInventoryCopy.get(item)) == 0) {
                    nItemsCopy.remove(planInventoryCopy.get(item));
                }
                planInventoryCopy.remove(item);
            }
        }
        String pickedItem = null;
        for (int i = startValues; i < startValues + copyInventory.size(); ++i) {
            if (!ignoreValues.contains(i)) {
                final String itemCheck = copyInventory.get(i);
                final Optional<Map.Entry<Integer, String>> momentAim = planInventoryCopy.entrySet().stream().filter(x -> x.getValue().equals(itemCheck)).findFirst();
                if (momentAim.isPresent()) {
                    if (pickedItem == null) {
                        planMove.add(i);
                    }
                    final int aimKey = momentAim.get().getKey();
                    planMove.add(aimKey);
                    if (pickedItem == null || !pickedItem.equals(itemCheck)) {
                        ignoreValues.add(aimKey);
                    }
                    nItemsCopy.put(itemCheck, nItemsCopy.get(itemCheck) - 1);
                    if (nItemsCopy.get(itemCheck) == 0) {
                        nItemsCopy.remove(itemCheck);
                    }
                    copyInventory.put(i, copyInventory.get(aimKey));
                    copyInventory.put(aimKey, itemCheck);
                    if (!copyInventory.get(aimKey).equals("minecraft:air0")) {
                        if (i >= startValues + copyInventory.size()) {
                            continue;
                        }
                        pickedItem = copyInventory.get(i);
                        --i;
                    }
                    else {
                        pickedItem = null;
                    }
                    planInventoryCopy.remove(aimKey);
                }
                else if (pickedItem != null) {
                    planMove.add(i);
                    copyInventory.put(i, pickedItem);
                    pickedItem = null;
                }
            }
        }
        if (planMove.size() != 0 && planMove.get(planMove.size() - 1).equals(planMove.get(planMove.size() - 2))) {
            planMove.remove(planMove.size() - 1);
        }
        final Object[] array2;
        final Object[] keyList = array2 = this.containerInv.keySet().toArray();
        for (final Object o : array2) {
            final int itemC = (int)o;
            if (nItemsCopy.containsKey(this.containerInv.get(itemC))) {
                final int start = planInventoryCopy.entrySet().stream().filter(x -> x.getValue().equals(this.containerInv.get(itemC))).findFirst().get().getKey();
                if ((boolean)this.invasive.getValue() || ((ItemStack)AutoGear.mc.player.openContainer.getInventory().get(start)).isEmpty()) {
                    planMove.add(start);
                    planMove.add(itemC);
                    planMove.add(start);
                    nItemsCopy.put(planInventoryCopy.get(start), nItemsCopy.get(planInventoryCopy.get(start)) - 1);
                    if (nItemsCopy.get(planInventoryCopy.get(start)) == 0) {
                        nItemsCopy.remove(planInventoryCopy.get(start));
                    }
                    planInventoryCopy.remove(start);
                }
            }
        }
        if (this.debugMode.getValue()) {
            for (final int valuePath : planMove) {
                PistonCrystal.printDebug(Integer.toString(valuePath), Boolean.valueOf(false));
            }
        }
        return planMove;
    }
    
    private HashMap<Integer, String> getInventoryCopy(final int startPoint) {
        final HashMap<Integer, String> output = new HashMap<Integer, String>();
        for (int sizeInventory = AutoGear.mc.player.inventory.mainInventory.size(), i = 0; i < sizeInventory; ++i) {
            final int value = i + startPoint + ((i < 9) ? (sizeInventory - 9) : -9);
            final ItemStack item = (ItemStack)AutoGear.mc.player.openContainer.getInventory().get(value);
            output.put(value, Objects.requireNonNull(item.getItem().getRegistryName()).toString() + item.getMetadata());
        }
        return output;
    }
    
    private HashMap<Integer, String> getInventoryCopy(final int startPoint, final HashMap<Integer, String> inventory) {
        final HashMap<Integer, String> output = new HashMap<Integer, String>();
        final int sizeInventory = AutoGear.mc.player.inventory.mainInventory.size();
        for (final int val : inventory.keySet()) {
            output.put(val + startPoint + ((val < 9) ? (sizeInventory - 9) : -9), inventory.get(val));
        }
        return output;
    }
}
