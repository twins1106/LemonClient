//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

@Module.Declaration(name = "AutoWeb", category = Category.Combat)
public class AutoWeb extends Module
{
    ModeSetting offsetMode;
    ModeSetting targetMode;
    IntegerSetting enemyRange;
    IntegerSetting delayTicks;
    IntegerSetting blocksPerTick;
    BooleanSetting rotate;
    BooleanSetting sneakOnly;
    BooleanSetting disableNoBlock;
    BooleanSetting disableOnCa;
    BooleanSetting silentSwitch;
    private final Timer delayTimer;
    private EntityPlayer targetPlayer;
    private int oldSlot;
    private int offsetSteps;
    private boolean outOfTargetBlock;
    
    public AutoWeb() {
        this.offsetMode = this.registerMode("Pattern", (List)Arrays.asList("Single", "Double"), "Single");
        this.targetMode = this.registerMode("Target", (List)Arrays.asList("Nearest", "Looking"), "Nearest");
        this.enemyRange = this.registerInteger("Range", 4, 0, 6);
        this.delayTicks = this.registerInteger("Tick Delay", 3, 0, 10);
        this.blocksPerTick = this.registerInteger("Blocks Per Tick", 4, 1, 8);
        this.rotate = this.registerBoolean("Rotate", true);
        this.sneakOnly = this.registerBoolean("Sneak Only", false);
        this.disableNoBlock = this.registerBoolean("Disable No Web", true);
        this.disableOnCa = this.registerBoolean("Disable on CA", true);
        this.silentSwitch = this.registerBoolean("Silent Switch", false);
        this.delayTimer = new Timer();
        this.targetPlayer = null;
        this.oldSlot = -1;
        this.offsetSteps = 0;
        this.outOfTargetBlock = false;
    }
    
    public void onEnable() {
        PlacementUtil.onEnable();
        if (AutoWeb.mc.player == null || AutoWeb.mc.world == null) {
            this.disable();
            return;
        }
        this.oldSlot = AutoWeb.mc.player.inventory.currentItem;
    }
    
    public void onDisable() {
        PlacementUtil.onDisable();
        if (AutoWeb.mc.player == null | AutoWeb.mc.world == null) {
            return;
        }
        if (this.outOfTargetBlock) {
            this.setDisabledMessage("No web detected... " + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getModuleColor() + "AutoWeb" + ChatFormatting.GRAY + " turned " + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getDisabledColor() + "OFF" + ChatFormatting.GRAY + "!");
        }
        if (this.oldSlot != AutoWeb.mc.player.inventory.currentItem && this.oldSlot != -1) {
            if (this.silentSwitch.getValue()) {
                AutoWeb.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldSlot));
            }
            else {
                AutoWeb.mc.player.inventory.currentItem = this.oldSlot;
            }
            this.oldSlot = -1;
        }
        AutoCrystalRewrite.stopAC = false;
        this.outOfTargetBlock = false;
        this.targetPlayer = null;
    }
    
    public void onUpdate() {
        if (AutoWeb.mc.player == null || AutoWeb.mc.world == null) {
            this.disable();
            return;
        }
        if ((boolean)this.disableOnCa.getValue() && ModuleManager.isModuleEnabled((Class)AutoCrystalRewrite.class)) {
            return;
        }
        if ((boolean)this.sneakOnly.getValue() && !AutoWeb.mc.player.isSneaking()) {
            return;
        }
        final int targetBlockSlot = InventoryUtil.findFirstBlockSlot((Class)BlockWeb.class, 0, 8);
        if ((this.outOfTargetBlock || targetBlockSlot == -1) && (boolean)this.disableNoBlock.getValue()) {
            this.outOfTargetBlock = true;
            this.disable();
            return;
        }
        final String s = (String)this.targetMode.getValue();
        switch (s) {
            case "Nearest": {
                this.targetPlayer = PlayerUtil.findClosestTarget((double)(int)this.enemyRange.getValue(), this.targetPlayer);
                break;
            }
            case "Looking": {
                this.targetPlayer = PlayerUtil.findLookingPlayer((double)(int)this.enemyRange.getValue());
                break;
            }
            default: {
                this.targetPlayer = null;
                break;
            }
        }
        if (this.targetPlayer == null) {
            return;
        }
        final Vec3d targetVec3d = this.targetPlayer.getPositionVector();
        if (this.delayTimer.getTimePassed() / 50L >= (int)this.delayTicks.getValue()) {
            this.delayTimer.reset();
            int blocksPlaced = 0;
            while (blocksPlaced <= (int)this.blocksPerTick.getValue()) {
                final String s2 = (String)this.offsetMode.getValue();
                int n2 = -1;
                switch (s2.hashCode()) {
                    case 2052876273: {
                        if (s2.equals("Double")) {
                            n2 = 0;
                            break;
                        }
                        break;
                    }
                }
                Vec3d[] offsetPattern = null;
                int maxSteps = 0;
                switch (n2) {
                    case 0: {
                        offsetPattern = Offsets.BURROW_DOUBLE;
                        maxSteps = Offsets.BURROW_DOUBLE.length;
                        break;
                    }
                    default: {
                        offsetPattern = Offsets.BURROW;
                        maxSteps = Offsets.BURROW.length;
                        break;
                    }
                }
                if (this.offsetSteps >= maxSteps) {
                    this.offsetSteps = 0;
                    break;
                }
                final BlockPos offsetPos = new BlockPos(offsetPattern[this.offsetSteps]);
                BlockPos targetPos = new BlockPos(targetVec3d).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
                boolean tryPlacing = true;
                if (this.targetPlayer.posY % 1.0 > 0.2) {
                    targetPos = new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ());
                }
                if (!AutoWeb.mc.world.getBlockState(targetPos).getMaterial().isReplaceable()) {
                    tryPlacing = false;
                }
                if (tryPlacing && this.placeBlock(targetPos)) {
                    ++blocksPlaced;
                }
                ++this.offsetSteps;
            }
        }
    }
    
    private boolean placeBlock(final BlockPos pos) {
        final EnumHand handSwing = EnumHand.MAIN_HAND;
        final int targetBlockSlot = InventoryUtil.findFirstBlockSlot((Class)BlockWeb.class, 0, 8);
        if (targetBlockSlot == -1) {
            this.outOfTargetBlock = true;
            return false;
        }
        if (AutoWeb.mc.player.inventory.currentItem != targetBlockSlot) {
            if (this.silentSwitch.getValue()) {
                AutoWeb.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(targetBlockSlot));
            }
            else {
                AutoWeb.mc.player.inventory.currentItem = targetBlockSlot;
            }
        }
        return PlacementUtil.place(pos, handSwing, (boolean)this.rotate.getValue(), true);
    }
}
