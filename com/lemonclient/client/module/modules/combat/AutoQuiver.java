//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.client.settings.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.client.manager.managers.*;

@Module.Declaration(name = "AutoQuiver", category = Category.Combat, priority = 250)
public class AutoQuiver extends Module
{
    ArrayList<String> arrowType;
    ArrayList<String> disableWhen;
    ModeSetting firstArrow;
    ModeSetting disableFirst;
    ModeSetting secondArrow;
    ModeSetting disableSecond;
    ModeSetting active;
    IntegerSetting pitchMoving;
    IntegerSetting standDrawLength;
    IntegerSetting movingDrawLength;
    IntegerSetting tickWait;
    IntegerSetting tickWaitEnd;
    int[] slot;
    int oldslot;
    int firstWait;
    int secondWait;
    int slotCheck;
    int endWait;
    boolean blockedUp;
    boolean beforeActive;
    boolean hasBow;
    boolean isPowering;
    boolean isFirst;
    String arrow;
    @EventHandler
    private final Listener<OnUpdateWalkingPlayerEvent> onUpdateWalkingPlayerEventListener;
    
    public AutoQuiver() {
        this.arrowType = new ArrayList<String>() {
            {
                this.add("none");
                this.add("strength");
                this.add("swiftness");
            }
        };
        this.disableWhen = new ArrayList<String>() {
            {
                this.add("none");
                this.add("moving");
                this.add("stand");
            }
        };
        this.firstArrow = this.registerMode("First Arrow", (List)Arrays.asList((Object[])this.arrowType.toArray((T[])new String[0])), "strength");
        this.disableFirst = this.registerMode("Disable First", (List)Arrays.asList((Object[])this.disableWhen.toArray((T[])new String[0])), "none");
        this.secondArrow = this.registerMode("Second Arrow", (List)Arrays.asList((Object[])this.arrowType.toArray((T[])new String[0])), "none");
        this.disableSecond = this.registerMode("Disable Second", (List)Arrays.asList((Object[])this.disableWhen.toArray((T[])new String[0])), "none", () -> !((String)this.secondArrow.getValue()).equals("none"));
        this.active = this.registerMode("Active", (List)Arrays.asList("On Bow", "Switch"), "On Bow");
        this.pitchMoving = this.registerInteger("Pitch Moving", -45, 0, -70);
        this.standDrawLength = this.registerInteger("Stand Draw Length", 4, 0, 21);
        this.movingDrawLength = this.registerInteger("Moving Draw Length", 3, 0, 21);
        this.tickWait = this.registerInteger("Tick Retry Wait", 20, 1, 50);
        this.tickWaitEnd = this.registerInteger("Tick Arrow Wait", 0, 0, 100);
        this.arrow = "";
        this.onUpdateWalkingPlayerEventListener = (Listener<OnUpdateWalkingPlayerEvent>)new Listener(event -> {
            if (AutoQuiver.mc.player == null || AutoQuiver.mc.world == null || !this.isPowering) {
                return;
            }
            PlayerPacket packet;
            if (!this.isMoving()) {
                packet = new PlayerPacket((Module)this, new Vec2f(0.0f, -90.0f));
            }
            else {
                packet = new PlayerPacket((Module)this, new Vec2f(AutoQuiver.mc.player.rotationYaw, (float)(int)this.pitchMoving.getValue()));
            }
            PlayerPacketManager.INSTANCE.addPacket(packet);
        }, new Predicate[0]);
    }
    
    boolean isMoving() {
        return Math.abs(AutoQuiver.mc.player.motionX) + Math.abs(AutoQuiver.mc.player.motionZ) > 0.01;
    }
    
    public void onEnable() {
        if (AutoQuiver.mc.world == null || AutoQuiver.mc.player == null) {
            this.disable();
            return;
        }
        SpoofRotationUtil.ROTATION_UTIL.onEnable();
        this.resetValues();
    }
    
    void resetValues() {
        final boolean blockedUp = false;
        this.isPowering = blockedUp;
        this.beforeActive = blockedUp;
        this.blockedUp = blockedUp;
        final boolean b = true;
        this.isFirst = b;
        this.hasBow = b;
        final int n = 0;
        this.secondWait = n;
        this.firstWait = n;
        this.endWait = 0;
        final int n2 = -1;
        this.slotCheck = n2;
        this.oldslot = n2;
        this.arrow = "";
    }
    
    boolean playerCheck() {
        this.blockedUp = !(BlockUtil.getBlock(EntityUtil.getPosition((Entity)AutoQuiver.mc.player).add(0, 2, 0)) instanceof BlockAir);
        if (this.blockedUp) {
            this.disable();
            return false;
        }
        return true;
    }
    
    public void onDisable() {
        if (AutoQuiver.mc.world == null || AutoQuiver.mc.player == null) {
            return;
        }
        if (AutoQuiver.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && AutoQuiver.mc.player.isHandActive()) {
            AutoQuiver.mc.player.stopActiveHand();
            KeyBinding.setKeyBindState(AutoQuiver.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
        }
        String output = "";
        if (this.blockedUp) {
            output = "There is a block above you";
        }
        else if (!this.hasBow) {
            output = "No bow detected";
        }
        this.setDisabledMessage(output + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getModuleColor() + "AutoQuiver" + ChatFormatting.GRAY + " turned " + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getDisabledColor() + "OFF" + ChatFormatting.GRAY + "!");
        if (this.oldslot != -1) {
            AutoQuiver.mc.player.inventory.currentItem = this.oldslot;
        }
    }
    
    boolean canArrow(final boolean isMoving, final String notWanted) {
        switch (notWanted) {
            case "none": {
                return true;
            }
            case "moving": {
                return !isMoving;
            }
            case "stand": {
                return isMoving;
            }
            default: {
                return false;
            }
        }
    }
    
    public void onUpdate() {
        if (AutoQuiver.mc.world == null || AutoQuiver.mc.player == null) {
            return;
        }
        if (this.endWait > 0) {
            --this.endWait;
            return;
        }
        if (AutoQuiver.mc.player.inventory.getCurrentItem().getItem() != Items.BOW) {
            if (this.isPowering) {
                KeyBinding.setKeyBindState(AutoQuiver.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            }
            if (((String)this.active.getValue()).equals("Switch")) {
                final int slot = InventoryUtil.findFirstItemSlot((Class)Items.BOW.getClass(), 0, 8);
                if (slot == -1) {
                    this.hasBow = false;
                    this.disable();
                    return;
                }
                this.oldslot = AutoQuiver.mc.player.inventory.currentItem;
                AutoQuiver.mc.player.inventory.currentItem = slot;
            }
            else if (((String)this.active.getValue()).equals("On Bow")) {
                this.isPowering = false;
                return;
            }
        }
        if (!this.beforeActive) {
            this.resetValues();
            if (!this.playerCheck()) {
                return;
            }
        }
        final boolean isMoving = this.isMoving();
        if (!this.isPowering) {
            if (--this.firstWait < 0) {
                final boolean enter = this.canArrow(isMoving, (String)this.disableFirst.getValue());
                if (enter) {
                    this.slot = this.getSlotArrow((String)this.firstArrow.getValue());
                    this.isFirst = true;
                }
                else {
                    this.slot = new int[] { -1, -1 };
                }
            }
            if (this.slot[1] == -1) {
                if (--this.secondWait < 0) {
                    final boolean enter = this.canArrow(isMoving, (String)this.disableFirst.getValue());
                    if (enter) {
                        this.slot = this.getSlotArrow((String)this.secondArrow.getValue());
                        this.secondWait = (int)this.tickWait.getValue();
                    }
                    else {
                        this.slot = new int[] { -1, -1 };
                    }
                }
                this.isFirst = false;
            }
            else {
                this.firstWait = (int)this.tickWait.getValue();
            }
            if (this.slot[1] == -1) {
                return;
            }
            this.switchArrow();
        }
        KeyBinding.setKeyBindState(AutoQuiver.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
        final boolean b = true;
        this.beforeActive = b;
        this.isPowering = b;
        if (AutoQuiver.mc.player.getItemInUseMaxCount() >= (int)(this.isMoving() ? this.movingDrawLength.getValue() : ((Integer)this.standDrawLength.getValue()))) {
            KeyBinding.setKeyBindState(AutoQuiver.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            AutoQuiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, AutoQuiver.mc.player.getHorizontalFacing()));
            AutoQuiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(AutoQuiver.mc.player.getActiveHand()));
            AutoQuiver.mc.player.stopActiveHand();
            this.switchArrow();
            this.slot = new int[] { -1, -1 };
            this.isPowering = false;
            this.arrow = "";
            this.endWait = (int)this.tickWaitEnd.getValue();
            if (this.isFirst) {
                this.isFirst = false;
                this.firstWait = (int)this.tickWait.getValue();
            }
            else {
                this.secondWait = (int)this.tickWait.getValue();
                if (((String)this.active.getValue()).equals("Switch")) {
                    this.disable();
                }
            }
        }
    }
    
    private void switchArrow() {
        if (this.slot[0] != -1) {
            AutoQuiver.mc.playerController.windowClick(0, this.slot[0], 0, ClickType.PICKUP, (EntityPlayer)AutoQuiver.mc.player);
            AutoQuiver.mc.playerController.windowClick(0, this.slot[1], 0, ClickType.PICKUP, (EntityPlayer)AutoQuiver.mc.player);
            AutoQuiver.mc.playerController.windowClick(0, this.slot[0], 0, ClickType.PICKUP, (EntityPlayer)AutoQuiver.mc.player);
            this.slotCheck = this.slot[0];
            AutoQuiver.mc.playerController.updateController();
        }
    }
    
    int[] getSlotArrow(final String wanted) {
        final int[] returnValeus = { -1, -1 };
        if (this.haveEffect(wanted)) {
            return returnValeus;
        }
        for (int i = 0; i < AutoQuiver.mc.player.inventory.getSizeInventory(); ++i) {
            final Item temp = AutoQuiver.mc.player.inventory.getStackInSlot(i).getItem();
            if (returnValeus[0] == -1 && (temp == Items.ARROW || temp == Items.SPECTRAL_ARROW || (temp == Items.TIPPED_ARROW && !AutoQuiver.mc.player.inventory.getStackInSlot(i).getTagCompound().getTag("Potion").toString().contains(wanted)))) {
                final int n;
                returnValeus[0] = n + (((n = i) < 9) ? 36 : 0);
            }
            if (temp == Items.TIPPED_ARROW && AutoQuiver.mc.player.inventory.getStackInSlot(i).getTagCompound().getTag("Potion").toString().contains(wanted)) {
                returnValeus[1] = i + ((i < 9) ? 36 : 0);
                return returnValeus;
            }
        }
        return returnValeus;
    }
    
    boolean haveEffect(final String wanted) {
        this.arrow = wanted;
        for (int i = 0; i < AutoQuiver.mc.player.getActivePotionEffects().toArray().length; ++i) {
            final PotionEffect effect = (PotionEffect)AutoQuiver.mc.player.getActivePotionEffects().toArray()[i];
            final String name = I18n.format(effect.getPotion().getName(), new Object[0]);
            if (name.toLowerCase().contains(wanted.toLowerCase())) {
                return true;
            }
            if (wanted.equals("swiftness") && name.equals("Speed")) {
                return true;
            }
        }
        return false;
    }
    
    public String getHudInfo() {
        if (!this.arrow.equals("")) {
            return "[" + ChatFormatting.WHITE + this.arrow + ChatFormatting.GRAY + "]";
        }
        return "";
    }
}
