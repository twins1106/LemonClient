//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import com.lemonclient.client.module.modules.exploits.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.item.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "CevBreaker", category = Category.Combat)
public class CevBreaker extends Module
{
    BooleanSetting instantMine;
    ModeSetting breakCrystal;
    ModeSetting breakBlock;
    BooleanSetting alwaysbreak;
    BooleanSetting packet;
    BooleanSetting rotate;
    BooleanSetting swing;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    IntegerSetting Delay;
    BooleanSetting antiWeakness;
    boolean offhand;
    boolean start;
    int blockslot;
    int crystalslot;
    int time;
    BlockPos placepos;
    
    public CevBreaker() {
        this.instantMine = this.registerBoolean("Packet Mine", true);
        this.breakCrystal = this.registerMode("Break Crystal", (List)Arrays.asList("Vanilla", "Packet", "None"), "Packet");
        this.breakBlock = this.registerMode("Break Block", (List)Arrays.asList("Normal", "Packet", "False"), "Packet", () -> !(boolean)this.instantMine.getValue());
        this.alwaysbreak = this.registerBoolean("Break Always", true);
        this.packet = this.registerBoolean("Packet Place", true);
        this.rotate = this.registerBoolean("Rotate", false);
        this.swing = this.registerBoolean("Swing", true);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.Delay = this.registerInteger("Delay", 1, 0, 20);
        this.antiWeakness = this.registerBoolean("AntiWeakness", true);
        this.time = 0;
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || CevBreaker.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                CevBreaker.mc.player.inventory.currentItem = slot;
            }
            CevBreaker.mc.playerController.updateController();
        }
    }
    
    public void onEnable() {
        if (CevBreaker.mc.objectMouseOver == null || CevBreaker.mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK || CevBreaker.mc.world.getBlockState(CevBreaker.mc.objectMouseOver.getBlockPos()).getBlock() == Blocks.BEDROCK) {
            this.disable();
            return;
        }
        this.placepos = CevBreaker.mc.objectMouseOver.getBlockPos();
        final boolean b = false;
        this.offhand = b;
        this.start = b;
        this.getItem();
        this.dobreak();
        this.time = 0;
    }
    
    public void onUpdate() {
        if (CevBreaker.mc.world == null || CevBreaker.mc.player == null || this.placepos == null || CevBreaker.mc.player.isDead) {
            this.disable();
            return;
        }
        if ((boolean)this.instantMine.getValue() && !ModuleManager.isModuleEnabled((Class)InstantMine.class)) {
            this.disable();
            return;
        }
        if (!CevBreaker.mc.world.isAirBlock(this.placepos.up()) || !CevBreaker.mc.world.isAirBlock(this.placepos.up().up())) {
            this.disable();
            return;
        }
        if (CevBreaker.mc.world.getBlockState(this.placepos.down()).getBlock() instanceof BlockAir && CevBreaker.mc.world.getBlockState(this.placepos.north()).getBlock() instanceof BlockAir && CevBreaker.mc.world.getBlockState(this.placepos.west()).getBlock() instanceof BlockAir && CevBreaker.mc.world.getBlockState(this.placepos.east()).getBlock() instanceof BlockAir && CevBreaker.mc.world.getBlockState(this.placepos.south()).getBlock() instanceof BlockAir) {
            this.disable();
            return;
        }
        if (CevBreaker.mc.player.isDead) {
            this.start = false;
            this.disable();
            return;
        }
        Entity crystal = this.getCrystal();
        if (CevBreaker.mc.world.getBlockState(this.placepos).getBlock() instanceof BlockAir) {
            this.breakCrystalPiston(crystal);
        }
        if (!this.start && CevBreaker.mc.world.isAirBlock(this.placepos)) {
            this.start = true;
        }
        if (this.start) {
            if (this.time++ < (int)this.Delay.getValue()) {
                return;
            }
            this.time = 0;
            this.getItem();
            final int oldslot = CevBreaker.mc.player.inventory.currentItem;
            if (this.alwaysbreak.getValue()) {
                if (this.instantMine.getValue()) {
                    InstantMine.INSTANCE.lastBlock = this.placepos;
                }
                else {
                    this.dobreak();
                }
            }
            if (CevBreaker.mc.world.getBlockState(this.placepos).getBlock() instanceof BlockAir) {
                crystal = this.getCrystal();
                if (crystal == null) {
                    this.switchTo(this.blockslot);
                    BurrowUtil.placeBlock(this.placepos, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
                    this.switchTo(oldslot);
                    if (this.offhand) {
                        CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.placepos, EnumFacing.DOWN, EnumHand.OFF_HAND, 0.0f, 0.0f, 0.0f));
                    }
                    else {
                        this.switchTo(this.crystalslot);
                        CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.placepos, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                        this.switchTo(oldslot);
                    }
                    crystal = this.getCrystal();
                }
                this.breakCrystalPiston(crystal);
            }
        }
    }
    
    private void getItem() {
        final int n = -1;
        this.crystalslot = n;
        this.blockslot = n;
        if (CevBreaker.mc.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal) {
            this.crystalslot = 11;
            this.offhand = true;
        }
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = CevBreaker.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (this.crystalslot == -1 && stack.getItem() instanceof ItemEndCrystal) {
                    this.crystalslot = i;
                }
            }
        }
        this.blockslot = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
        if (this.crystalslot == -1) {
            this.disable();
        }
        if (this.blockslot == -1 || this.crystalslot == -1) {
            this.disable();
        }
    }
    
    private void breakCrystalPiston(final Entity crystal) {
        if (crystal == null) {
            return;
        }
        final int oldSlot = CevBreaker.mc.player.inventory.currentItem;
        if ((boolean)this.antiWeakness.getValue() && CevBreaker.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
                if (stack != ItemStack.EMPTY) {
                    if (stack.getItem() instanceof ItemSword) {
                        newSlot = i;
                        break;
                    }
                    if (stack.getItem() instanceof ItemTool) {
                        newSlot = i;
                        break;
                    }
                }
            }
            if (newSlot != -1) {
                this.switchTo(newSlot);
            }
        }
        if (((String)this.breakCrystal.getValue()).equalsIgnoreCase("Vanilla")) {
            CrystalUtil.breakCrystal(crystal, (boolean)this.swing.getValue());
        }
        else if (((String)this.breakCrystal.getValue()).equalsIgnoreCase("Packet")) {
            CrystalUtil.breakCrystalPacket(crystal, (boolean)this.swing.getValue());
        }
        this.switchTo(oldSlot);
    }
    
    private Entity getCrystal() {
        for (final Entity t : CevBreaker.mc.world.loadedEntityList) {
            if (t instanceof EntityEnderCrystal && (((int)t.posX == this.placepos.x && ((int)(t.posX - 1.0) == this.placepos.x || (int)t.posX == this.placepos.x || (int)(t.posX + 1.0) == this.placepos.x)) || (int)(t.posZ - 1.0) == this.placepos.z || (int)t.posZ == this.placepos.z || (int)(t.posZ + 1.0) == this.placepos.z) && (int)t.posY == this.placepos.y + 1) {
                return t;
            }
        }
        return null;
    }
    
    private void dobreak() {
        if (this.placepos == null || CevBreaker.mc.world.isAirBlock(this.placepos) || CevBreaker.mc.world.getBlockState(this.placepos).getBlock() == Blocks.BEDROCK) {
            return;
        }
        if (this.instantMine.getValue()) {
            if (InstantMine.INSTANCE.lastBlock == this.placepos) {
                return;
            }
            InstantMine.INSTANCE.breaker(this.placepos);
        }
        else {
            if (this.swing.getValue()) {
                CevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            if (((String)this.breakBlock.getValue()).equals("Packet")) {
                CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.placepos, EnumFacing.UP));
                CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.placepos, EnumFacing.UP));
            }
            else {
                CevBreaker.mc.playerController.onPlayerDamageBlock(this.placepos, EnumFacing.UP);
            }
        }
    }
}
