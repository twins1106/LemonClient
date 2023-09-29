//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.block.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import java.util.function.*;
import com.lemonclient.client.module.modules.combat.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.item.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.item.*;
import com.lemonclient.api.util.player.*;
import com.mojang.realmsclient.gui.*;

@Module.Declaration(name = "LemonOffhand3", category = Category.Dev)
public class NewOffHand2 extends Module
{
    ModeSetting defaultItem;
    ModeSetting nonDefaultItem;
    ModeSetting noPlayerItem;
    ModeSetting swordMode;
    ModeSetting gappleMode;
    ModeSetting pickaxeMode;
    ModeSetting shiftpickaxeMode;
    ModeSetting potionChoose;
    IntegerSetting healthSwitch;
    IntegerSetting swordhealth;
    IntegerSetting tickDelay;
    IntegerSetting fallDistance;
    IntegerSetting maxSwitchPerSecond;
    DoubleSetting biasDamage;
    DoubleSetting playerDistance;
    BooleanSetting rightGap;
    BooleanSetting shiftPot;
    BooleanSetting swordCheck;
    BooleanSetting crystalGap;
    BooleanSetting fallDistanceBol;
    BooleanSetting crystalCheck;
    BooleanSetting noHotBar;
    BooleanSetting onlyHotBar;
    BooleanSetting antiWeakness;
    BooleanSetting hotBarTotem;
    BooleanSetting refill;
    BooleanSetting check;
    IntegerSetting totemSlot;
    ModeSetting HudMode;
    String Itemname;
    String itemCheck;
    int prevSlot;
    int tickWaited;
    int counts;
    int totems;
    boolean returnBack;
    boolean stepChanging;
    boolean firstChange;
    Item item;
    private final ArrayList<Long> switchDone;
    Map<String, Item> allowedItemsItem;
    Map<String, Block> allowedItemsBlock;
    
    public NewOffHand2() {
        this.defaultItem = this.registerMode("Default", (List)Arrays.asList("Totem", "Crystal", "Gapple", "Plates", "Obby", "EChest", "Pot", "Exp", "Bed"), "Totem");
        this.nonDefaultItem = this.registerMode("Non Default", (List)Arrays.asList("Totem", "Crystal", "Gapple", "Obby", "EChest", "Pot", "Exp", "Plates", "String", "Skull", "Bed"), "Crystal");
        this.noPlayerItem = this.registerMode("No Player", (List)Arrays.asList("Totem", "Crystal", "Gapple", "Plates", "Obby", "EChest", "Pot", "Exp", "Bed"), "Gapple");
        this.swordMode = this.registerMode("Sword Switch", (List)Arrays.asList("Gapple", "Crystal", "Pot", "None"), "Gapple");
        this.gappleMode = this.registerMode("Gap Switch", (List)Arrays.asList("Totem", "Gapple", "Crystal", "None"), "Crystal");
        this.pickaxeMode = this.registerMode("Pick Switch", (List)Arrays.asList("Obsidian", "EChest", "Gapple", "Crystal", "None"), "Gapple");
        this.shiftpickaxeMode = this.registerMode("Shift Pick", (List)Arrays.asList("Obsidian", "EChest", "Gapple", "Crystal", "None"), "Gapple");
        this.potionChoose = this.registerMode("Potion", (List)Arrays.asList("first", "strength", "swiftness"), "first");
        this.healthSwitch = this.registerInteger("Health Switch", 14, 0, 36);
        this.swordhealth = this.registerInteger("Sword Health", 14, 0, 36);
        this.tickDelay = this.registerInteger("Tick Delay", 0, 0, 20);
        this.fallDistance = this.registerInteger("Fall Distance", 12, 0, 30);
        this.maxSwitchPerSecond = this.registerInteger("Max Switch", 6, 2, 10);
        this.biasDamage = this.registerDouble("Bias Damage", 1.0, 0.0, 3.0);
        this.playerDistance = this.registerDouble("Player Distance", 0.0, 0.0, 30.0);
        this.rightGap = this.registerBoolean("Right Click Gap", false);
        this.shiftPot = this.registerBoolean("Shift Pot", false);
        this.swordCheck = this.registerBoolean("Only Sword", true);
        this.crystalGap = this.registerBoolean("Crystal Gap", false);
        this.fallDistanceBol = this.registerBoolean("Fall Distance", true);
        this.crystalCheck = this.registerBoolean("Crystal Check", false);
        this.noHotBar = this.registerBoolean("No HotBar", false);
        this.onlyHotBar = this.registerBoolean("Only HotBar", false);
        this.antiWeakness = this.registerBoolean("AntiWeakness", false);
        this.hotBarTotem = this.registerBoolean("Switch HotBar Totem", false);
        this.refill = this.registerBoolean("ReFill", true, () -> (Boolean)this.hotBarTotem.getValue());
        this.check = this.registerBoolean("Check", true, () -> (boolean)this.hotBarTotem.getValue() && (boolean)this.refill.getValue());
        this.totemSlot = this.registerInteger("Totem Slot", 1, 1, 9, () -> (boolean)this.hotBarTotem.getValue() && (boolean)this.refill.getValue());
        this.HudMode = this.registerMode("Hud Mode", (List)Arrays.asList("Totem", "Offhand"), "Offhand");
        this.itemCheck = "";
        this.switchDone = new ArrayList<Long>();
        this.allowedItemsItem = new HashMap<String, Item>() {
            {
                this.put("Totem", Items.TOTEM_OF_UNDYING);
                this.put("Crystal", Items.END_CRYSTAL);
                this.put("Gapple", Items.GOLDEN_APPLE);
                this.put("Pot", (Item)Items.POTIONITEM);
                this.put("Exp", Items.EXPERIENCE_BOTTLE);
                this.put("Bed", Items.BED);
                this.put("String", Items.STRING);
            }
        };
        this.allowedItemsBlock = new HashMap<String, Block>() {
            {
                this.put("Plates", Blocks.WOODEN_PRESSURE_PLATE);
                this.put("EChest", Blocks.ENDER_CHEST);
                this.put("Skull", (Block)Blocks.SKULL);
                this.put("Obby", Blocks.OBSIDIAN);
            }
        };
    }
    
    public void onEnable() {
        this.firstChange = true;
        this.returnBack = false;
    }
    
    public void onDisable() {
    }
    
    public void onUpdate() {
        if (NewOffHand2.mc.currentScreen instanceof GuiContainer && !(NewOffHand2.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if ((boolean)this.hotBarTotem.getValue() && (boolean)this.refill.getValue()) {
            boolean hasTotem = false;
            for (int i = 0; i < 9; ++i) {
                if (NewOffHand2.mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                    hasTotem = true;
                }
            }
            if (!hasTotem || !(boolean)this.check.getValue()) {
                int i = 9;
                while (i < 36) {
                    if (NewOffHand2.mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                        if ((int)this.totemSlot.getValue() == 1) {
                            NewOffHand2.mc.playerController.windowClick(NewOffHand2.mc.player.inventoryContainer.windowId, i, 0, ClickType.SWAP, (EntityPlayer)NewOffHand2.mc.player);
                            break;
                        }
                        NewOffHand2.mc.playerController.windowClick(0, i, 0, ClickType.SWAP, (EntityPlayer)NewOffHand2.mc.player);
                        NewOffHand2.mc.playerController.windowClick(0, (int)this.totemSlot.getValue() + 35, 0, ClickType.SWAP, (EntityPlayer)NewOffHand2.mc.player);
                        NewOffHand2.mc.playerController.windowClick(0, i, 0, ClickType.SWAP, (EntityPlayer)NewOffHand2.mc.player);
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
        }
        if (this.stepChanging) {
            if (this.tickWaited++ < (int)this.tickDelay.getValue()) {
                return;
            }
            this.tickWaited = 0;
            this.stepChanging = false;
            NewOffHand2.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)NewOffHand2.mc.player);
            this.switchDone.add(System.currentTimeMillis());
        }
        this.totems = NewOffHand2.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (this.returnBack) {
            if (this.tickWaited++ < (int)this.tickDelay.getValue()) {
                return;
            }
            this.changeBack();
        }
        this.itemCheck = this.getItem();
        if (this.offHandSame(this.itemCheck)) {
            boolean done = false;
            if ((boolean)this.hotBarTotem.getValue() && this.itemCheck.equals("Totem")) {
                done = this.switchItemTotemHot();
            }
            if (!done) {
                this.switchItemNormal(this.itemCheck);
            }
        }
        this.GetOffhand();
    }
    
    private void GetOffhand() {
        if (((String)this.HudMode.getValue()).equals("Offhand")) {
            this.item = NewOffHand2.mc.player.getHeldItemOffhand().getItem();
            final int items = NewOffHand2.mc.player.getHeldItemOffhand().getCount();
            this.Itemname = NewOffHand2.mc.player.getHeldItemOffhand().getDisplayName();
            this.counts = NewOffHand2.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == this.item).mapToInt(ItemStack::getCount).sum() + items;
        }
    }
    
    private void changeBack() {
        if (this.prevSlot == -1 || !NewOffHand2.mc.player.inventory.getStackInSlot(this.prevSlot).isEmpty()) {
            this.prevSlot = this.findEmptySlot();
        }
        if (this.prevSlot != -1) {
            NewOffHand2.mc.playerController.windowClick(0, (this.prevSlot < 9) ? (this.prevSlot + 36) : this.prevSlot, 0, ClickType.PICKUP, (EntityPlayer)NewOffHand2.mc.player);
        }
        else {
            PistonCrystal.printDebug("Your inventory is full.", Boolean.valueOf(true));
        }
        this.returnBack = false;
        this.tickWaited = 0;
    }
    
    private boolean switchItemTotemHot() {
        final int slot = InventoryUtil.findTotemSlot(0, 8);
        if (slot != -1) {
            if (NewOffHand2.mc.player.inventory.currentItem != slot) {
                NewOffHand2.mc.player.inventory.currentItem = slot;
            }
            return true;
        }
        return false;
    }
    
    private void switchItemNormal(final String itemCheck) {
        final int t = this.getInventorySlot(itemCheck);
        if (t == -1) {
            return;
        }
        if (!itemCheck.equals("Totem") && this.canSwitch()) {
            return;
        }
        this.toOffHand(t);
    }
    
    private String getItem() {
        String itemCheck = "";
        boolean normalOffHand = true;
        if (((boolean)this.fallDistanceBol.getValue() && NewOffHand2.mc.player.fallDistance >= (int)this.fallDistance.getValue() && NewOffHand2.mc.player.prevPosY != NewOffHand2.mc.player.posY && !NewOffHand2.mc.player.isElytraFlying()) || ((boolean)this.crystalCheck.getValue() && this.crystalDamage())) {
            normalOffHand = false;
            itemCheck = "Totem";
        }
        final Item mainHandItem = NewOffHand2.mc.player.getHeldItemMainhand().getItem();
        if (mainHandItem instanceof ItemSword) {
            boolean can = true;
            if (NewOffHand2.mc.gameSettings.keyBindUseItem.isKeyDown() && (boolean)this.swordCheck.getValue()) {
                if ((boolean)this.shiftPot.getValue() && NewOffHand2.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    can = false;
                    itemCheck = "Pot";
                    normalOffHand = false;
                }
                else if ((boolean)this.rightGap.getValue() && !((String)this.swordMode.getValue()).equals("Gapple")) {
                    can = false;
                    itemCheck = "Gapple";
                    normalOffHand = false;
                }
            }
            if (can) {
                final String s = (String)this.swordMode.getValue();
                switch (s) {
                    case "Gapple": {
                        itemCheck = "Gapple";
                        normalOffHand = false;
                        break;
                    }
                    case "Crystal": {
                        itemCheck = "Crystal";
                        normalOffHand = false;
                        break;
                    }
                    case "Pot": {
                        itemCheck = "Pot";
                        normalOffHand = false;
                        break;
                    }
                }
            }
        }
        else if (!(boolean)this.swordCheck.getValue()) {
            if ((boolean)this.shiftPot.getValue() && NewOffHand2.mc.gameSettings.keyBindSneak.isKeyDown()) {
                itemCheck = "Pot";
                normalOffHand = false;
            }
            else if ((boolean)this.rightGap.getValue() && !((String)this.swordMode.getValue()).equals("Gapple")) {
                itemCheck = "Gapple";
                normalOffHand = false;
            }
        }
        if (mainHandItem == Items.DIAMOND_PICKAXE) {
            if (!NewOffHand2.mc.gameSettings.keyBindSneak.isKeyDown() || NewOffHand2.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final String s2 = (String)this.pickaxeMode.getValue();
                switch (s2) {
                    case "Obsidian": {
                        itemCheck = "Obby";
                        normalOffHand = false;
                        break;
                    }
                    case "EChest": {
                        itemCheck = "EChest";
                        normalOffHand = false;
                        break;
                    }
                    case "Gapple": {
                        itemCheck = "Gapple";
                        normalOffHand = false;
                        break;
                    }
                    case "Crystal": {
                        itemCheck = "Crystal";
                        normalOffHand = false;
                        break;
                    }
                }
            }
            if (NewOffHand2.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final String s3 = (String)this.shiftpickaxeMode.getValue();
                switch (s3) {
                    case "Obsidian": {
                        itemCheck = "Obby";
                        normalOffHand = false;
                        break;
                    }
                    case "EChest": {
                        itemCheck = "EChest";
                        normalOffHand = false;
                        break;
                    }
                    case "Gapple": {
                        itemCheck = "Gapple";
                        normalOffHand = false;
                        break;
                    }
                    case "Crystal": {
                        itemCheck = "Crystal";
                        normalOffHand = false;
                        break;
                    }
                }
            }
        }
        if (mainHandItem == Items.GOLDEN_APPLE) {
            final String s4 = (String)this.gappleMode.getValue();
            switch (s4) {
                case "Totem": {
                    itemCheck = "Totem";
                    normalOffHand = false;
                    break;
                }
                case "Gapple": {
                    itemCheck = "Gapple";
                    normalOffHand = false;
                    break;
                }
                case "Crystal": {
                    itemCheck = "Crystal";
                    normalOffHand = false;
                    break;
                }
            }
        }
        if ((boolean)this.crystalGap.getValue() && mainHandItem == Items.END_CRYSTAL) {
            itemCheck = "Gapple";
            normalOffHand = false;
        }
        if (normalOffHand && (boolean)this.antiWeakness.getValue() && NewOffHand2.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
            normalOffHand = false;
            itemCheck = "Crystal";
        }
        if (normalOffHand && !this.nearPlayer()) {
            itemCheck = (String)this.noPlayerItem.getValue();
        }
        itemCheck = this.getItemToCheck(itemCheck);
        return itemCheck;
    }
    
    private boolean canSwitch() {
        final long now = System.currentTimeMillis();
        for (int i = 0; i < this.switchDone.size() && now - this.switchDone.get(i) > 1000L; ++i) {
            this.switchDone.remove(i);
        }
        if (this.switchDone.size() / 2 >= (int)this.maxSwitchPerSecond.getValue()) {
            return true;
        }
        this.switchDone.add(now);
        return false;
    }
    
    private boolean nearPlayer() {
        if (((Double)this.playerDistance.getValue()).intValue() == 0) {
            return true;
        }
        for (final EntityPlayer pl : NewOffHand2.mc.world.playerEntities) {
            if (pl != NewOffHand2.mc.player && NewOffHand2.mc.player.getDistance((Entity)pl) < (double)this.playerDistance.getValue()) {
                return true;
            }
        }
        return false;
    }
    
    private boolean crystalDamage() {
        for (final Entity t : NewOffHand2.mc.world.loadedEntityList) {
            if (t instanceof EntityEnderCrystal && NewOffHand2.mc.player.getDistance(t) <= 12.0f && DamageUtil.calculateDamage(t.posX, t.posY, t.posZ, (Entity)NewOffHand2.mc.player) * (double)this.biasDamage.getValue() >= NewOffHand2.mc.player.getHealth() && this.totems > 0) {
                return true;
            }
        }
        return false;
    }
    
    private int findEmptySlot() {
        for (int i = 35; i > -1; --i) {
            if (NewOffHand2.mc.player.inventory.getStackInSlot(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean offHandSame(final String itemCheck) {
        final Item offHandItem = NewOffHand2.mc.player.getHeldItemOffhand().getItem();
        if (!this.allowedItemsBlock.containsKey(itemCheck)) {
            final Item item = this.allowedItemsItem.get(itemCheck);
            return item != offHandItem;
        }
        final Block item2 = this.allowedItemsBlock.get(itemCheck);
        if (offHandItem instanceof ItemBlock) {
            return ((ItemBlock)offHandItem).getBlock() != item2;
        }
        return !(offHandItem instanceof ItemSkull) || item2 != Blocks.SKULL || true;
    }
    
    private String getItemToCheck(final String str) {
        if (NewOffHand2.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
            return (String)((PlayerUtil.getHealth() > (int)this.swordhealth.getValue()) ? (str.equals("") ? this.nonDefaultItem.getValue() : str) : ((String)this.defaultItem.getValue()));
        }
        return (String)((PlayerUtil.getHealth() > (int)this.healthSwitch.getValue()) ? (str.equals("") ? this.nonDefaultItem.getValue() : str) : ((String)this.defaultItem.getValue()));
    }
    
    private int getInventorySlot(final String itemName) {
        boolean blockBool = false;
        Object item;
        if (this.allowedItemsItem.containsKey(itemName)) {
            item = this.allowedItemsItem.get(itemName);
        }
        else {
            item = this.allowedItemsBlock.get(itemName);
            blockBool = true;
        }
        if (!this.firstChange && this.prevSlot != -1) {
            final int res = this.isCorrect(this.prevSlot, blockBool, item, itemName);
            if (res != -1) {
                return res;
            }
        }
        for (int i = this.onlyHotBar.getValue() ? 8 : 35; i > (this.noHotBar.getValue() ? 9 : -1); --i) {
            final int res = this.isCorrect(i, blockBool, item, itemName);
            if (res != -1) {
                return res;
            }
        }
        return -1;
    }
    
    private int isCorrect(final int i, final boolean blockBool, final Object item, final String itemName) {
        final Item temp = NewOffHand2.mc.player.inventory.getStackInSlot(i).getItem();
        if (blockBool) {
            if (temp instanceof ItemBlock) {
                if (((ItemBlock)temp).getBlock() == item) {
                    return i;
                }
            }
            else if (temp instanceof ItemSkull && item == Blocks.SKULL) {
                return i;
            }
        }
        else if (item == temp) {
            if (itemName.equals("Pot") && !((String)this.potionChoose.getValue()).equalsIgnoreCase("first") && !NewOffHand2.mc.player.inventory.getStackInSlot(i).stackTagCompound.toString().split(":")[2].contains((CharSequence)this.potionChoose.getValue())) {
                return -1;
            }
            return i;
        }
        return -1;
    }
    
    private void toOffHand(final int t) {
        if (!NewOffHand2.mc.player.getHeldItemOffhand().isEmpty()) {
            if (this.firstChange) {
                this.prevSlot = t;
            }
            this.returnBack = true;
            this.firstChange = !this.firstChange;
        }
        else {
            this.prevSlot = -1;
        }
        NewOffHand2.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)NewOffHand2.mc.player);
        this.stepChanging = true;
        this.tickWaited = 0;
    }
    
    public String getHudInfo() {
        if (((String)this.HudMode.getValue()).equals("Totem")) {
            this.counts = this.totems;
            if (NewOffHand2.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                ++this.counts;
            }
            return "[" + ChatFormatting.WHITE + "Totem " + this.counts + ChatFormatting.GRAY + "]";
        }
        if (!this.itemCheck.equals("")) {
            return "[" + ChatFormatting.WHITE + this.itemCheck + " " + this.counts + ChatFormatting.GRAY + "]";
        }
        return "[NONE]";
    }
}
