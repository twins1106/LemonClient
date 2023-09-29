//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.client.command.commands.*;
import com.lemonclient.client.module.modules.combat.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import java.util.*;

@Module.Declaration(name = "SortInventory", category = Category.Misc)
public class SortInventory extends Module
{
    IntegerSetting tickDelay;
    BooleanSetting confirmSort;
    BooleanSetting instaSort;
    BooleanSetting closeAfter;
    BooleanSetting infoMsgs;
    BooleanSetting debugMode;
    private HashMap<Integer, String> planInventory;
    private HashMap<String, Integer> nItems;
    private ArrayList<Integer> sortItems;
    private int delayTimeTicks;
    private int stepNow;
    private boolean openedBefore;
    private boolean finishSort;
    private boolean doneBefore;
    
    public SortInventory() {
        this.tickDelay = this.registerInteger("Tick Delay", 0, 0, 20);
        this.confirmSort = this.registerBoolean("Confirm Sort", true);
        this.instaSort = this.registerBoolean("Insta Sort", false);
        this.closeAfter = this.registerBoolean("Close After", false);
        this.infoMsgs = this.registerBoolean("Info Msgs", true);
        this.debugMode = this.registerBoolean("Debug Mode", false);
        this.planInventory = new HashMap<Integer, String>();
        this.nItems = new HashMap<String, Integer>();
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
        this.nItems = new HashMap<String, Integer>();
        for (int i = 0; i < inventoryDivided.length; ++i) {
            if (!inventoryDivided[i].contains("air")) {
                this.planInventory.put(i, inventoryDivided[i]);
                if (this.nItems.containsKey(inventoryDivided[i])) {
                    this.nItems.put(inventoryDivided[i], this.nItems.get(inventoryDivided[i]) + 1);
                }
                else {
                    this.nItems.put(inventoryDivided[i], 1);
                }
            }
        }
        this.delayTimeTicks = 0;
        final boolean b = false;
        this.doneBefore = b;
        this.openedBefore = b;
        if (this.instaSort.getValue()) {
            SortInventory.mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)SortInventory.mc.player));
        }
    }
    
    public void onDisable() {
        if ((boolean)this.infoMsgs.getValue() && this.planInventory.size() > 0) {
            PistonCrystal.printDebug("AutoSort Turned Off!", Boolean.valueOf(true));
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
        if (SortInventory.mc.currentScreen instanceof GuiInventory) {
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
            this.sortItems = this.getInventorySort();
            if (this.sortItems.size() == 0 && !this.doneBefore) {
                this.finishSort = false;
                if (this.infoMsgs.getValue()) {
                    PistonCrystal.printDebug("Inventory arleady sorted...", Boolean.valueOf(true));
                }
                if ((boolean)this.instaSort.getValue() || (boolean)this.closeAfter.getValue()) {
                    SortInventory.mc.player.closeScreen();
                    if (this.instaSort.getValue()) {
                        this.disable();
                    }
                }
            }
            else {
                this.finishSort = true;
                this.stepNow = 0;
            }
            this.openedBefore = true;
        }
        else if (this.finishSort) {
            if (this.sortItems.size() != 0) {
                final int slotChange = this.sortItems.get(this.stepNow++);
                SortInventory.mc.playerController.windowClick(0, (slotChange < 9) ? (slotChange + 36) : slotChange, 0, ClickType.PICKUP, (EntityPlayer)SortInventory.mc.player);
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
                if ((boolean)this.instaSort.getValue() || (boolean)this.closeAfter.getValue()) {
                    SortInventory.mc.player.closeScreen();
                    if (this.instaSort.getValue()) {
                        this.disable();
                    }
                }
            }
        }
    }
    
    private void checkLastItem() {
        if (this.sortItems.size() != 0) {
            final int slotChange = this.sortItems.get(this.sortItems.size() - 1);
            if (SortInventory.mc.player.inventory.getStackInSlot(slotChange).isEmpty()) {
                SortInventory.mc.playerController.windowClick(0, (slotChange < 9) ? (slotChange + 36) : slotChange, 0, ClickType.PICKUP, (EntityPlayer)SortInventory.mc.player);
            }
        }
    }
    
    private ArrayList<Integer> getInventorySort() {
        final ArrayList<Integer> planMove = new ArrayList<Integer>();
        final ArrayList<String> copyInventory = this.getInventoryCopy();
        final HashMap<Integer, String> planInventoryCopy = (HashMap<Integer, String>)this.planInventory.clone();
        final HashMap<String, Integer> nItemsCopy = (HashMap<String, Integer>)this.nItems.clone();
        final ArrayList<Integer> ignoreValues = new ArrayList<Integer>();
        for (int i = 0; i < this.planInventory.size(); ++i) {
            final int value = (int)this.planInventory.keySet().toArray()[i];
            if (copyInventory.get(value).equals(planInventoryCopy.get(value))) {
                ignoreValues.add(value);
                nItemsCopy.put(planInventoryCopy.get(value), nItemsCopy.get(planInventoryCopy.get(value)) - 1);
                if (nItemsCopy.get(planInventoryCopy.get(value)) == 0) {
                    nItemsCopy.remove(planInventoryCopy.get(value));
                }
                planInventoryCopy.remove(value);
            }
        }
        String pickedItem = null;
        for (int j = 0; j < copyInventory.size(); ++j) {
            if (!ignoreValues.contains(j)) {
                final String itemCheck = copyInventory.get(j);
                final Optional<Map.Entry<Integer, String>> momentAim = planInventoryCopy.entrySet().stream().filter(x -> x.getValue().equals(itemCheck)).findFirst();
                if (momentAim.isPresent()) {
                    if (pickedItem == null) {
                        planMove.add(j);
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
                    copyInventory.set(j, copyInventory.get(aimKey));
                    copyInventory.set(aimKey, itemCheck);
                    if (!copyInventory.get(aimKey).equals("minecraft:air0")) {
                        if (j >= copyInventory.size()) {
                            continue;
                        }
                        pickedItem = copyInventory.get(j);
                        --j;
                    }
                    else {
                        pickedItem = null;
                    }
                    planInventoryCopy.remove(aimKey);
                }
                else if (pickedItem != null) {
                    planMove.add(j);
                    copyInventory.set(j, pickedItem);
                    pickedItem = null;
                }
            }
        }
        if (planMove.size() != 0 && planMove.get(planMove.size() - 1).equals(planMove.get(planMove.size() - 2))) {
            planMove.remove(planMove.size() - 1);
        }
        if (this.debugMode.getValue()) {
            for (final int valuePath : planMove) {
                PistonCrystal.printDebug(Integer.toString(valuePath), Boolean.valueOf(false));
            }
        }
        return planMove;
    }
    
    private ArrayList<String> getInventoryCopy() {
        final ArrayList<String> output = new ArrayList<String>();
        for (final ItemStack i : SortInventory.mc.player.inventory.mainInventory) {
            output.add(Objects.requireNonNull(i.getItem().getRegistryName()).toString() + i.getMetadata());
        }
        return output;
    }
}
