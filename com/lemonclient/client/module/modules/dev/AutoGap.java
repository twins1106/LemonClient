//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.client.settings.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;

@Module.Declaration(name = "AutoGap", category = Category.Dev)
public class AutoGap extends Module
{
    ModeSetting mode;
    IntegerSetting health;
    IntegerSetting absorption;
    BooleanSetting cancel;
    BooleanSetting pause;
    BooleanSetting cancelInMenu;
    BooleanSetting switchToGap;
    BooleanSetting debug;
    int oldSlot;
    boolean ran;
    boolean offhand;
    boolean eating;
    private boolean wasEating;
    @EventHandler
    private final Listener<EventPlayerOnStoppedUsingItem> OnStopUsingItem;
    
    public AutoGap() {
        this.mode = this.registerMode("Mode", (List)Arrays.asList("Always", "Smart"), "Always");
        this.health = this.registerInteger("Health", 16, 0, 20);
        this.absorption = this.registerInteger("Absorption", 0, 0, 16);
        this.cancel = this.registerBoolean("AntiCancel", true);
        this.pause = this.registerBoolean("Pause When Pick", true);
        this.cancelInMenu = this.registerBoolean("cancelInMenu", false);
        this.switchToGap = this.registerBoolean("switchToGap", false);
        this.debug = this.registerBoolean("Debug", false);
        this.wasEating = false;
        this.OnStopUsingItem = (Listener<EventPlayerOnStoppedUsingItem>)new Listener(event -> {
            if ((boolean)this.cancel.getValue() && this.eating) {
                event.cancel();
            }
        }, new Predicate[0]);
    }
    
    public void onEnable() {
        if (((String)this.mode.getValue()).equals("always")) {
            this.oldSlot = AutoGap.mc.player.inventory.currentItem;
        }
        this.ran = false;
        this.eating = false;
    }
    
    public void onDisable() {
        if (((String)this.mode.getValue()).equals("always")) {
            AutoGap.mc.player.inventory.currentItem = this.oldSlot;
        }
        if (this.wasEating) {
            this.wasEating = false;
            KeyBinding.setKeyBindState(AutoGap.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
        }
        this.eating = false;
    }
    
    public void onUpdate() {
        if (AutoGap.mc.world == null || AutoGap.mc.player == null || AutoGap.mc.player.isDead) {
            this.eating = false;
            return;
        }
        if ((boolean)this.pause.getValue() && AutoGap.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
            this.eating = false;
            return;
        }
        final String s = (String)this.mode.getValue();
        switch (s) {
            case "Always": {
                if (this.switchToGap.getValue()) {
                    if (this.findGappleSlot() != -1) {
                        AutoGap.mc.player.inventory.currentItem = this.findGappleSlot();
                    }
                    else if (this.debug.getValue()) {
                        MessageBus.sendClientDeleteMessage("cannot find a golden apple in the hotbar or offhand.", "AutoGap", 9);
                    }
                }
                this.eatGap();
                break;
            }
            case "Smart": {
                if (AutoGap.mc.player.getHealth() <= (int)this.health.getValue() || AutoGap.mc.player.getAbsorptionAmount() <= (int)this.absorption.getValue()) {
                    if (this.switchToGap.getValue()) {
                        if (this.findGappleSlot() != -1) {
                            if (!this.ran) {
                                this.oldSlot = AutoGap.mc.player.inventory.currentItem;
                                this.ran = true;
                            }
                            AutoGap.mc.player.inventory.currentItem = this.findGappleSlot();
                        }
                        else if (this.debug.getValue()) {
                            MessageBus.sendClientDeleteMessage("cannot find a golden apple in the hotbar or offhand.", "AutoGap", 9);
                        }
                    }
                    this.eatGap();
                    break;
                }
                if (this.wasEating) {
                    if (this.ran) {
                        AutoGap.mc.player.inventory.currentItem = this.oldSlot;
                    }
                    this.ran = false;
                    KeyBinding.setKeyBindState(AutoGap.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                    this.wasEating = false;
                    this.eating = false;
                    break;
                }
                break;
            }
        }
    }
    
    public void eatGap() {
        if (AutoGap.mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE || AutoGap.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
            this.offhand = (AutoGap.mc.player.getHeldItemOffhand().getItem() != Items.GOLDEN_APPLE);
            if (AutoGap.mc.currentScreen == null) {
                KeyBinding.setKeyBindState(AutoGap.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                this.wasEating = true;
            }
            else if (!(boolean)this.cancelInMenu.getValue()) {
                AutoGap.mc.playerController.processRightClick((EntityPlayer)AutoGap.mc.player, (World)AutoGap.mc.world, EnumHand.MAIN_HAND);
            }
            this.eating = true;
        }
        else {
            KeyBinding.setKeyBindState(AutoGap.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            this.eating = false;
        }
    }
    
    private int findGappleSlot() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoGap.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Items.GOLDEN_APPLE) {
                slot = i;
                break;
            }
        }
        return slot;
    }
}
