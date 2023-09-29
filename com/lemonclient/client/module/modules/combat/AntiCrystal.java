//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import com.lemonclient.api.util.world.combat.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import com.lemonclient.api.util.player.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

@Module.Declaration(name = "AntiCrystal", category = Category.Combat)
public class AntiCrystal extends Module
{
    ModeSetting time;
    DoubleSetting rangePlace;
    DoubleSetting enemyRange;
    DoubleSetting damageMin;
    DoubleSetting biasDamage;
    ModeSetting blockPlaced;
    IntegerSetting tickDelay;
    IntegerSetting blocksPerTick;
    BooleanSetting packet;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting swing;
    BooleanSetting rotate;
    BooleanSetting onlyIfEnemy;
    BooleanSetting checkDamage;
    private int delayTimeTicks;
    private boolean isSneaking;
    
    public AntiCrystal() {
        this.time = this.registerMode("Time Mode", (List)Arrays.asList("Tick", "onUpdate", "Both", "Fast"), "Tick");
        this.rangePlace = this.registerDouble("Range Place", 5.9, 0.0, 6.0);
        this.enemyRange = this.registerDouble("Enemy Range", 12.0, 0.0, 20.0);
        this.damageMin = this.registerDouble("Damage Min", 4.0, 0.0, 15.0);
        this.biasDamage = this.registerDouble("Bias Damage", 1.0, 0.0, 3.0);
        this.blockPlaced = this.registerMode("Block Place", (List)Arrays.asList("Pressure", "String"), "String");
        this.tickDelay = this.registerInteger("Tick Delay", 5, 0, 10);
        this.blocksPerTick = this.registerInteger("Blocks Per Tick", 4, 0, 8);
        this.packet = this.registerBoolean("Packet Place", false);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.swing = this.registerBoolean("Swing", true);
        this.rotate = this.registerBoolean("Rotate", false);
        this.onlyIfEnemy = this.registerBoolean("Only If Enemy", true);
        this.checkDamage = this.registerBoolean("Damage Check", true);
        this.isSneaking = false;
    }
    
    public void onEnable() {
        this.delayTimeTicks = 0;
    }
    
    public void onDisable() {
        if (this.isSneaking) {
            AntiCrystal.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AntiCrystal.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
    }
    
    public void onUpdate() {
        if (((String)this.time.getValue()).equals("onUpdate") || ((String)this.time.getValue()).equals("Both")) {
            this.antiCrystal();
        }
    }
    
    public void onTick() {
        if (((String)this.time.getValue()).equals("Tick") || ((String)this.time.getValue()).equals("Both")) {
            this.antiCrystal();
        }
    }
    
    public void fast() {
        if (((String)this.time.getValue()).equals("Fast")) {
            this.antiCrystal();
        }
    }
    
    private void antiCrystal() {
        if (AntiCrystal.mc.world == null || AntiCrystal.mc.player == null || AntiCrystal.mc.player.isDead) {
            return;
        }
        if (this.delayTimeTicks < (int)this.tickDelay.getValue()) {
            ++this.delayTimeTicks;
            return;
        }
        this.delayTimeTicks = 0;
        if (this.onlyIfEnemy.getValue()) {
            if (AntiCrystal.mc.world.playerEntities.size() <= 1) {
                return;
            }
            boolean found = false;
            for (final EntityPlayer check : AntiCrystal.mc.world.playerEntities) {
                if (check != AntiCrystal.mc.player && AntiCrystal.mc.player.getDistance((Entity)check) <= (double)this.enemyRange.getValue()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return;
            }
        }
        int blocksPlaced = 0;
        boolean pressureSwitch = true;
        int slotPressure = -1;
        for (final Entity t : AntiCrystal.mc.world.loadedEntityList) {
            if (t instanceof EntityEnderCrystal && AntiCrystal.mc.player.getDistance(t) <= (double)this.rangePlace.getValue()) {
                if (pressureSwitch) {
                    if ((slotPressure = getHotBarPressure((String)this.blockPlaced.getValue())) == -1) {
                        return;
                    }
                    pressureSwitch = false;
                }
                if (this.checkDamage.getValue()) {
                    final float damage = (float)(DamageUtil.calculateDamage(t.posX, t.posY, t.posZ, (Entity)AntiCrystal.mc.player) * (double)this.biasDamage.getValue());
                    if (damage < (double)this.damageMin.getValue() && damage < AntiCrystal.mc.player.getHealth()) {
                        return;
                    }
                }
                if (BlockUtil.getBlock(t.posX, t.posY, t.posZ) instanceof BlockAir) {
                    final int oldslot = AntiCrystal.mc.player.inventory.currentItem;
                    this.switchTo(slotPressure);
                    BurrowUtil.placeBlock(new BlockPos(t.posX, t.posY, t.posZ), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
                    this.switchTo(oldslot);
                    if (++blocksPlaced == (int)this.blocksPerTick.getValue()) {
                        return;
                    }
                }
                if (!this.isSneaking) {
                    continue;
                }
                AntiCrystal.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AntiCrystal.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
        }
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || AntiCrystal.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                AntiCrystal.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                AntiCrystal.mc.player.inventory.currentItem = slot;
            }
            AntiCrystal.mc.playerController.updateController();
        }
    }
    
    public static boolean isPressure(final ItemStack stack) {
        return stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock && ((ItemBlock)stack.getItem()).getBlock() instanceof BlockPressurePlate;
    }
    
    public static boolean isString(final ItemStack stack) {
        return stack != ItemStack.EMPTY && !(stack.getItem() instanceof ItemBlock) && stack.getItem() == Items.STRING;
    }
    
    public static int getHotBarPressure(final String mode) {
        for (int i = 0; i < 9; ++i) {
            if (mode.equals("Pressure")) {
                if (isPressure(AntiCrystal.mc.player.inventory.getStackInSlot(i))) {
                    return i;
                }
            }
            else if (isString(AntiCrystal.mc.player.inventory.getStackInSlot(i))) {
                return i;
            }
        }
        return -1;
    }
}
