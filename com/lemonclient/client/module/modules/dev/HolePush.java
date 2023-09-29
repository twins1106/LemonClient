//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.entity.player.*;
import com.lemonclient.client.module.modules.combat.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import com.lemonclient.client.module.modules.exploits.*;
import net.minecraft.network.play.client.*;
import com.lemonclient.client.*;
import com.lemonclient.api.util.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.block.properties.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.*;

@Module.Declaration(name = "HolePush", category = Category.Dev, priority = 1000)
public class HolePush extends Module
{
    ModeSetting setting;
    DoubleSetting targetRange;
    DoubleSetting minRange;
    DoubleSetting maxTargetSpeed;
    ModeSetting surroundCheck;
    BooleanSetting burCheck;
    IntegerSetting minArmorPieces;
    BooleanSetting onlyPushOnGround;
    DoubleSetting targetMinHP;
    IntegerSetting delay;
    IntegerSetting circulateDelay;
    ModeSetting feetPlace;
    DoubleSetting placeRange;
    BooleanSetting noOutOfDistancePlace;
    BooleanSetting checkPlaceable;
    BooleanSetting farPlace;
    BooleanSetting noPlacePisOnBreakPos;
    BooleanSetting noPlaceRstOnBreakPos;
    IntegerSetting advanceMine;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting packet;
    BooleanSetting placeSwing;
    BooleanSetting rotate;
    BooleanSetting attackCry;
    BooleanSetting crystalAttackSwing;
    BooleanSetting crystalAttackRotate;
    BooleanSetting crystalPacketAttack;
    BooleanSetting antiWeakness;
    BooleanSetting silent;
    IntegerSetting attackRange;
    IntegerSetting noSuicide;
    BooleanSetting disableOnNoBlock;
    BooleanSetting onGroundCheck;
    IntegerSetting count;
    BooleanSetting speedCheck;
    DoubleSetting maxSpeed;
    BooleanSetting noPushSelf;
    ModeSetting onUpdateMode;
    DoubleSetting cryRange;
    IntegerSetting maxCount;
    IntegerSetting balance;
    IntegerSetting cryWeight;
    BooleanSetting raytrace;
    BooleanSetting strictRotate;
    BooleanSetting instantMine;
    BooleanSetting swing;
    ModeSetting breakBlock;
    IntegerSetting mineDelay;
    BlockPos savePos;
    Timing mineTimer;
    Timing timer;
    Timing circulateTimer;
    PushInfo info;
    BlockPos piston;
    BlockPos rst;
    boolean pull;
    int stage;
    int ct;
    int ct1;
    EntityPlayer target;
    public static boolean hasCry;
    public static boolean oldCry;
    public static int var;
    
    public HolePush() {
        this.setting = this.registerMode("Page", (List)Arrays.asList("Push", "Mine", "CrystalAttack", "Target"), "Push");
        this.targetRange = this.registerDouble("Target Max Range", 4.0, 1.0, 6.0, () -> ((String)this.setting.getValue()).equals("Target"));
        this.minRange = this.registerDouble("Target Min Range", 2.6, 0.0, 4.0, () -> ((String)this.setting.getValue()).equals("Target"));
        this.maxTargetSpeed = this.registerDouble("Max Target Speed", 4.0, 0.0, 15.0, () -> ((String)this.setting.getValue()).equals("Target"));
        this.surroundCheck = this.registerMode("Hole Check", (List)Arrays.asList("Disable", "Normal", "Center", "Smart"), "Normal", () -> ((String)this.setting.getValue()).equals("Target"));
        this.burCheck = this.registerBoolean("SelfFill Check", true, () -> ((String)this.setting.getValue()).equals("Target"));
        this.minArmorPieces = this.registerInteger("Min Target Armor Pieces", 3, 0, 4, () -> ((String)this.setting.getValue()).equals("Target"));
        this.onlyPushOnGround = this.registerBoolean("Target On Ground Check", true, () -> ((String)this.setting.getValue()).equals("Target"));
        this.targetMinHP = this.registerDouble("Target Min HP", 11.0, 0.0, 36.0, () -> ((String)this.setting.getValue()).equals("Target"));
        this.delay = this.registerInteger("Push Delay", 200, 0, 1000, () -> ((String)this.setting.getValue()).equals("Push"));
        this.circulateDelay = this.registerInteger("Run Push Task Delay", 0, 0, 200, () -> ((String)this.setting.getValue()).equals("Push"));
        this.feetPlace = this.registerMode("Foot Place", (List)Arrays.asList("Obsidian", "RedStone"), "Obsidian", () -> ((String)this.setting.getValue()).equals("Push"));
        this.placeRange = this.registerDouble("Place Range", 5.0, 0.0, 6.0, () -> ((String)this.setting.getValue()).equals("Push"));
        this.noOutOfDistancePlace = this.registerBoolean("Out Of Distance Place Check", true, () -> ((String)this.setting.getValue()).equals("Push"));
        this.checkPlaceable = this.registerBoolean("Placeable Check", false, () -> ((String)this.setting.getValue()).equals("Push"));
        this.farPlace = this.registerBoolean("Far Place", false, () -> ((String)this.setting.getValue()).equals("Push"));
        this.noPlacePisOnBreakPos = this.registerBoolean("Piston Pre PlaceChecker", true, () -> ((String)this.setting.getValue()).equals("Push"));
        this.noPlaceRstOnBreakPos = this.registerBoolean("Electronic Pre Place Checker", true, () -> ((String)this.setting.getValue()).equals("Push"));
        this.advanceMine = this.registerInteger("Advance Mine On Stage", 2, 0, 3, () -> ((String)this.setting.getValue()).equals("Push"));
        this.packetSwitch = this.registerBoolean("Packet Switch", true, () -> ((String)this.setting.getValue()).equals("Push"));
        this.check = this.registerBoolean("Switch Check", true, () -> ((String)this.setting.getValue()).equals("Push"));
        this.packet = this.registerBoolean("Packet Place", true, () -> ((String)this.setting.getValue()).equals("Push"));
        this.placeSwing = this.registerBoolean("Swing", true, () -> ((String)this.setting.getValue()).equals("Push"));
        this.rotate = this.registerBoolean("Rotate", false, () -> ((String)this.setting.getValue()).equals("Push"));
        this.attackCry = this.registerBoolean("Attack Crystal", true, () -> ((String)this.setting.getValue()).equals("CrystalAttack"));
        this.crystalAttackSwing = this.registerBoolean("Crystal Attack Swing", true, () -> (boolean)this.attackCry.getValue() && ((String)this.setting.getValue()).equals("CrystalAttack"));
        this.crystalAttackRotate = this.registerBoolean("Crystal Attack Rotate", true, () -> (boolean)this.attackCry.getValue() && ((String)this.setting.getValue()).equals("CrystalAttack"));
        this.crystalPacketAttack = this.registerBoolean("Crystal Packet Attack", true, () -> (boolean)this.attackCry.getValue() && ((String)this.setting.getValue()).equals("CrystalAttack"));
        this.antiWeakness = this.registerBoolean("Anti Weakness", false, () -> (boolean)this.attackCry.getValue() && ((String)this.setting.getValue()).equals("CrystalAttack"));
        this.silent = this.registerBoolean("Silent Switch", false, () -> (boolean)this.attackCry.getValue() && ((String)this.setting.getValue()).equals("CrystalAttack") && (boolean)this.antiWeakness.getValue());
        this.attackRange = this.registerInteger("Attack Crystal Range", 5, 0, 7, () -> (boolean)this.attackCry.getValue() && ((String)this.setting.getValue()).equals("CrystalAttack"));
        this.noSuicide = this.registerInteger("No Suicide Health", 5, 0, 36, () -> (boolean)this.attackCry.getValue() && ((String)this.setting.getValue()).equals("CrystalAttack"));
        this.disableOnNoBlock = this.registerBoolean("Disable On No Block", true, () -> ((String)this.setting.getValue()).equals("Push"));
        this.onGroundCheck = this.registerBoolean("On Ground Check", true, () -> ((String)this.setting.getValue()).equals("Push"));
        this.count = this.registerInteger("Max Try Do Push Count", 20, 0, 200, () -> ((String)this.setting.getValue()).equals("Push"));
        this.speedCheck = this.registerBoolean("Move Speed Check", true, () -> ((String)this.setting.getValue()).equals("Push"));
        this.maxSpeed = this.registerDouble("Max Self Speed", 4.0, 0.0, 20.0, () -> ((String)this.setting.getValue()).equals("Push") && (boolean)this.speedCheck.getValue());
        this.noPushSelf = this.registerBoolean("No Self Push", true, () -> ((String)this.setting.getValue()).equals("Push"));
        this.onUpdateMode = this.registerMode("Crystal Check Update Mode", (List)Arrays.asList("Tick", "onUpdate"), "Tick", () -> ((String)this.setting.getValue()).equals("Push"));
        this.cryRange = this.registerDouble("Crystal Range", 5.0, 0.0, 8.0, () -> ((String)this.setting.getValue()).equals("Push"));
        this.maxCount = this.registerInteger("Max Count", 30, 2, 50, () -> ((String)this.setting.getValue()).equals("Push") && (double)this.cryRange.getValue() > this.cryRange.getMin());
        this.balance = this.registerInteger("Balance", 17, 2, 50, () -> ((String)this.setting.getValue()).equals("Push") && (double)this.cryRange.getValue() > this.cryRange.getMin());
        this.cryWeight = this.registerInteger("Crystal Weight", 5, 1, 10, () -> ((String)this.setting.getValue()).equals("Push") && (double)this.cryRange.getValue() > this.cryRange.getMin());
        this.raytrace = this.registerBoolean("Ray Trace", false, () -> ((String)this.setting.getValue()).equals("Push"));
        this.strictRotate = this.registerBoolean("Rotate Mode", false, () -> ((String)this.setting.getValue()).equals("Push"));
        this.instantMine = this.registerBoolean("Packet Mine", true, () -> ((String)this.setting.getValue()).equals("Mine"));
        this.swing = this.registerBoolean("Break Swing", true, () -> ((String)this.setting.getValue()).equals("Mine"));
        this.breakBlock = this.registerMode("Break Mode", (List)Arrays.asList("Normal", "Packet"), "Packet", () -> ((String)this.setting.getValue()).equals("Mine") && !(boolean)this.instantMine.getValue());
        this.mineDelay = this.registerInteger("Mine Delay", 20, 0, 400, () -> ((String)this.setting.getValue()).equals("Mine"));
        this.mineTimer = new Timing();
        this.timer = new Timing();
        this.circulateTimer = new Timing();
        this.stage = 0;
        this.ct = 0;
        this.ct1 = 0;
    }
    
    public void onEnable() {
        this.piston = null;
        this.rst = null;
        this.target = null;
        this.stage = 0;
        this.ct = 0;
        this.ct1 = 0;
        this.circulateTimer.setMs(10000L);
    }
    
    public void onTick() {
        if (((String)this.onUpdateMode.getValue()).equals("Tick")) {
            caCheck((double)this.cryRange.getValue(), 0, (int)this.maxCount.getValue(), (int)this.balance.getValue(), (int)this.cryWeight.getValue(), false);
        }
    }
    
    public void onUpdate() {
        if (((String)this.onUpdateMode.getValue()).equals("Tick")) {
            caCheck((double)this.cryRange.getValue(), 0, (int)this.maxCount.getValue(), (int)this.balance.getValue(), (int)this.cryWeight.getValue(), false);
        }
        if (!this.circulateTimer.passedDms((double)(int)this.circulateDelay.getValue())) {
            return;
        }
        final int oldSlot = HolePush.mc.player.inventory.currentItem;
        final int obbySlot = AutoCev.getItemHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
        int pisSlot = AutoCev.getItemHotbar(Item.getItemFromBlock((Block)Blocks.PISTON));
        if (pisSlot == -1) {
            pisSlot = AutoCev.getItemHotbar(Item.getItemFromBlock((Block)Blocks.STICKY_PISTON));
            if (pisSlot == -1) {
                if (this.disableOnNoBlock.getValue()) {
                    this.disable();
                }
                return;
            }
        }
        final int rstSlot = AutoCev.getItemHotbar(Item.getItemFromBlock(Blocks.REDSTONE_BLOCK));
        if (rstSlot == -1) {
            if (this.disableOnNoBlock.getValue()) {
                this.disable();
            }
            return;
        }
        if (this.stage == 0) {
            if (!this.timer.passedDms((double)(int)this.delay.getValue())) {
                return;
            }
            final EntityPlayer target = this.getTarget((double)this.targetRange.getValue());
            this.target = target;
            if (this.getPistonPos(target, (boolean)this.raytrace.getValue()) == null) {
                return;
            }
            this.info = this.getPistonPos(target, (boolean)this.raytrace.getValue());
            this.pull = this.info.pullMode;
            this.piston = this.info.pistonPos;
            this.rst = this.info.rstPos;
            this.ct = 0;
            this.ct1 = 0;
            ++this.stage;
        }
        if (this.stage == 1) {
            if (!this.timer.passedDms((double)(int)this.delay.getValue())) {
                return;
            }
            this.timer.reset();
            if (this.attackCry.getValue()) {
                this.attackCrystal();
            }
            if (((String)this.feetPlace.getValue()).equals("Redstone") && HolePush.mc.world.getBlockState(this.piston.add(0, -1, 0)).getBlock().equals(Blocks.AIR) && isNoBBoxBlocked(this.piston.add(0, -1, 0))) {
                if ((boolean)this.noOutOfDistancePlace.getValue() && Math.sqrt(HolePush.mc.player.getDistanceSq(this.piston.add(0, -1, 0))) > (double)this.placeRange.getValue()) {
                    this.stage = 0;
                    return;
                }
                this.switchTo(rstSlot);
                BurrowUtil.placeBlock(this.piston.add(0, -1, 0), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.placeSwing.getValue());
                this.switchTo(oldSlot);
            }
            if (((String)this.feetPlace.getValue()).equals("Obsidian") && HolePush.mc.world.getBlockState(this.piston.add(0, -1, 0)).getBlock().equals(Blocks.AIR) && isNoBBoxBlocked(this.piston.add(0, -1, 0))) {
                if (obbySlot == -1) {
                    this.stage = 2;
                    return;
                }
                if ((boolean)this.noOutOfDistancePlace.getValue() && Math.sqrt(HolePush.mc.player.getDistanceSq(this.piston.add(0, -1, 0))) > (double)this.placeRange.getValue()) {
                    this.stage = 0;
                    return;
                }
                this.switchTo(obbySlot);
                BurrowUtil.placeBlock(this.piston.add(0, -1, 0), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.placeSwing.getValue());
                this.switchTo(oldSlot);
            }
            if ((int)this.advanceMine.getValue() == 1) {
                this.mineRst(this.target, this.piston);
            }
            ++this.stage;
        }
        if (this.stage == 2) {
            if (!this.timer.passedDms((double)(int)this.delay.getValue())) {
                return;
            }
            this.timer.reset();
            if (HolePush.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.PISTON) || HolePush.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.STICKY_PISTON)) {
                ++this.stage;
                return;
            }
            if (!HolePush.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.AIR) && !HolePush.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.PISTON) && !HolePush.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.STICKY_PISTON)) {
                this.stage = 0;
                return;
            }
            if ((boolean)this.noOutOfDistancePlace.getValue() && Math.sqrt(HolePush.mc.player.getDistanceSq(this.piston)) > (double)this.placeRange.getValue()) {
                this.stage = 0;
                return;
            }
            this.switchTo(pisSlot);
            if (!(boolean)this.strictRotate.getValue()) {
                if (this.info.pisFac == EnumFacing.EAST) {
                    HolePush.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(90.0f, 0.0f, HolePush.mc.player.onGround));
                }
                else if (this.info.pisFac == EnumFacing.WEST) {
                    HolePush.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(-90.0f, 0.0f, HolePush.mc.player.onGround));
                }
                else if (this.info.pisFac == EnumFacing.NORTH) {
                    HolePush.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, HolePush.mc.player.onGround));
                }
                else if (this.info.pisFac == EnumFacing.SOUTH) {
                    HolePush.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(180.0f, 0.0f, HolePush.mc.player.onGround));
                }
            }
            BurrowUtil.placeBlock(this.piston, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.placeSwing.getValue());
            this.switchTo(oldSlot);
            if ((HolePush.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.PISTON) || !(boolean)this.checkPlaceable.getValue()) && isNoBBoxBlocked(this.piston)) {
                ++this.stage;
                if ((int)this.advanceMine.getValue() == 2) {
                    this.mineRst(this.target, this.piston);
                }
            }
            else {
                ++this.ct1;
                if (this.ct1 > (int)this.count.getValue()) {
                    this.stage = 0;
                }
            }
        }
        if (this.stage == 3) {
            if (haveNeighborBlock(this.piston, Blocks.REDSTONE_BLOCK).size() > 0) {
                this.mineTimer.reset();
                this.stage = 4;
                return;
            }
            if (isNoBBoxBlocked(this.rst) && (!(boolean)this.checkPlaceable.getValue() || HolePush.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.PISTON) || HolePush.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.STICKY_PISTON))) {
                if (!HolePush.mc.world.getBlockState(this.rst).getBlock().equals(Blocks.AIR) && !HolePush.mc.world.getBlockState(this.rst).getBlock().equals(Blocks.REDSTONE_BLOCK)) {
                    this.stage = 0;
                    return;
                }
                this.switchTo(rstSlot);
                BurrowUtil.placeBlock(this.rst, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.placeSwing.getValue());
                this.switchTo(oldSlot);
                if (HolePush.mc.world.getBlockState(this.rst).getBlock().equals(Blocks.REDSTONE_BLOCK)) {
                    this.mineTimer.reset();
                    this.stage = 4;
                    if ((int)this.advanceMine.getValue() == 3) {
                        this.mineRst(this.target, this.piston);
                    }
                }
                ++this.ct;
                if (this.ct > (int)this.count.getValue()) {
                    this.stage = 0;
                }
            }
            else {
                this.stage = 0;
            }
        }
        if (this.stage == 4) {
            if (!this.pull) {
                this.stage = 0;
                return;
            }
            if (!this.mineTimer.passedDms((double)(int)this.mineDelay.getValue())) {
                return;
            }
            this.mineRst(this.target, this.piston);
            this.stage = 0;
        }
        this.circulateTimer.reset();
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || HolePush.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                HolePush.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                HolePush.mc.player.inventory.currentItem = slot;
            }
            HolePush.mc.playerController.updateController();
        }
    }
    
    public void mineRst(final EntityPlayer target, final BlockPos piston) {
        if (HolePush.mc.world.getBlockState(getFlooredPosition((Entity)target).add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !HolePush.mc.world.getBlockState(piston.add(0, -1, 0)).getBlock().equals(Blocks.AIR) && haveNeighborBlock(piston, Blocks.REDSTONE_BLOCK).size() == 1) {
            final BlockPos minePos = haveNeighborBlock(piston, Blocks.REDSTONE_BLOCK).get(0);
            if (minePos != null && (this.savePos == null || !this.savePos.equals((Object)minePos))) {
                this.pMine(minePos);
            }
        }
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
    
    private void pMine(final BlockPos minePos) {
        if (!this.isPos2(this.savePos, minePos)) {
            if (this.instantMine.getValue()) {
                if (this.isPos2(InstantMine.INSTANCE.lastBlock, minePos)) {
                    return;
                }
                InstantMine.INSTANCE.breaker(minePos);
            }
            else {
                if (this.swing.getValue()) {
                    HolePush.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                if (((String)this.breakBlock.getValue()).equals("Packet")) {
                    HolePush.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, minePos, EnumFacing.UP));
                    HolePush.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, minePos, EnumFacing.UP));
                }
                else {
                    HolePush.mc.playerController.onPlayerDamageBlock(minePos, EnumFacing.UP);
                }
            }
            this.savePos = minePos;
        }
    }
    
    public boolean isSur(final Entity player, final String checkMode) {
        final BlockPos playerPos = getFlooredPosition(player);
        if (checkMode.equals("Normal") && !HolePush.mc.world.getBlockState(playerPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) && !HolePush.mc.world.getBlockState(playerPos.add(-1, 0, 0)).getBlock().equals(Blocks.AIR) && !HolePush.mc.world.getBlockState(playerPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) && !HolePush.mc.world.getBlockState(playerPos.add(0, 0, -1)).getBlock().equals(Blocks.AIR)) {
            return true;
        }
        if (checkMode.equals("Center")) {
            final double x = Math.abs(player.posX) - Math.floor(Math.abs(player.posX));
            final double z = Math.abs(player.posZ) - Math.floor(Math.abs(player.posZ));
            if (x <= 0.7 && x >= 0.3 && z <= 0.7 && z >= 0.3) {
                return true;
            }
        }
        if (checkMode.equals("Smart")) {
            return (!HolePush.mc.world.getBlockState(playerPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !HolePush.mc.world.getBlockState(playerPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR)) && (!HolePush.mc.world.getBlockState(playerPos.add(-1, 0, 0)).getBlock().equals(Blocks.AIR) || !HolePush.mc.world.getBlockState(playerPos.add(-1, 1, 0)).getBlock().equals(Blocks.AIR)) && (!HolePush.mc.world.getBlockState(playerPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) || !HolePush.mc.world.getBlockState(playerPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR)) && (!HolePush.mc.world.getBlockState(playerPos.add(0, 0, -1)).getBlock().equals(Blocks.AIR) || !HolePush.mc.world.getBlockState(playerPos.add(0, 0, -1)).getBlock().equals(Blocks.AIR));
        }
        return checkMode.equals("Disable");
    }
    
    public boolean helpingBlockCheck(final BlockPos pos) {
        return !HolePush.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !HolePush.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock().equals(Blocks.AIR) || !HolePush.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !HolePush.mc.world.getBlockState(pos.add(0, -1, 0)).getBlock().equals(Blocks.AIR) || !HolePush.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock().equals(Blocks.AIR) || !HolePush.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock().equals(Blocks.AIR);
    }
    
    public static byte getArmorPieces(final EntityPlayer target) {
        byte i = 0;
        if (target.inventoryContainer.getSlot(5).getStack().getItem().equals(Items.DIAMOND_HELMET)) {
            ++i;
        }
        if (target.inventoryContainer.getSlot(6).getStack().getItem().equals(Items.DIAMOND_CHESTPLATE)) {
            ++i;
        }
        if (target.inventoryContainer.getSlot(7).getStack().getItem().equals(Items.DIAMOND_LEGGINGS)) {
            ++i;
        }
        if (target.inventoryContainer.getSlot(8).getStack().getItem().equals(Items.DIAMOND_BOOTS)) {
            ++i;
        }
        return i;
    }
    
    private EntityPlayer getTarget(final double range) {
        if (!caCheck((double)this.cryRange.getValue(), 0, (int)this.maxCount.getValue(), (int)this.balance.getValue(), (int)this.cryWeight.getValue(), true)) {
            return null;
        }
        if ((boolean)this.onGroundCheck.getValue() && !HolePush.mc.player.onGround) {
            return null;
        }
        EntityPlayer target = null;
        double distance = range;
        if ((boolean)this.speedCheck.getValue() && LemonClient.speedUtil.getPlayerSpeed((EntityPlayer)HolePush.mc.player) > (double)this.maxSpeed.getValue()) {
            return null;
        }
        for (final EntityPlayer player : HolePush.mc.world.playerEntities) {
            if (this.getPistonPos(player, (boolean)this.raytrace.getValue()) == null) {
                continue;
            }
            final BlockPos pistonPos = this.getPistonPos(player, (boolean)this.raytrace.getValue()).pistonPos;
            final BlockPos rstPos = this.getPistonPos(player, (boolean)this.raytrace.getValue()).rstPos;
            if (EntityUtil.basicChecksEntity(player) || HolePush.mc.player.getDistance((Entity)player) > range) {
                continue;
            }
            if (LemonClient.speedUtil.getPlayerSpeed(player) > (double)this.maxTargetSpeed.getValue()) {
                continue;
            }
            if (pistonPos == null) {
                continue;
            }
            if (rstPos == null) {
                continue;
            }
            final boolean armor = getArmorPieces(player) < (int)this.minArmorPieces.getValue();
            final boolean onGround = (boolean)this.onlyPushOnGround.getValue() && !player.onGround;
            final boolean hp = (double)this.targetMinHP.getValue() > player.getHealth();
            if (!armor && !onGround && !hp && !((String)this.surroundCheck.getValue()).equals("Disable") && !LemonClient.ForcePush.isKeyDown()) {
                boolean p = true;
                if (!this.isSur((Entity)player, (String)this.surroundCheck.getValue())) {
                    if (this.burCheck.getValue()) {
                        if (HolePush.mc.world.getBlockState(getFlooredPosition((Entity)player)).getBlock().equals(Blocks.AIR)) {
                            continue;
                        }
                    }
                    else {
                        p = false;
                    }
                    if (p) {
                        continue;
                    }
                }
            }
            if ((((boolean)this.noPushSelf.getValue() && getFlooredPosition((Entity)player).equals((Object)getFlooredPosition((Entity)HolePush.mc.player))) || ((HolePush.mc.player.posY - player.posY <= -1.0 || HolePush.mc.player.posY - player.posY >= 2.0) && this.distanceToXZ(pistonPos.getX() + 0.5, pistonPos.getZ() + 0.5) < (double)this.minRange.getValue()) || target != null) && HolePush.mc.player.getDistanceSq((Entity)player) >= distance) {
                continue;
            }
            target = player;
            distance = HolePush.mc.player.getDistanceSq((Entity)player);
        }
        return target;
    }
    
    public double distanceToXZ(final double x, final double z) {
        final double dx = HolePush.mc.player.posX - x;
        final double dz = HolePush.mc.player.posZ - z;
        return Math.sqrt(dx * dx + dz * dz);
    }
    
    public void attackCrystal() {
        if (HolePush.mc.player.getHealth() < (int)this.noSuicide.getValue()) {
            return;
        }
        final ArrayList<Entity> crystalList = new ArrayList<Entity>();
        for (final Entity entity : HolePush.mc.world.loadedEntityList) {
            if (entity instanceof EntityEnderCrystal) {
                crystalList.add(entity);
            }
        }
        if (crystalList.size() == 0) {
            return;
        }
        final HashMap<Entity, Double> distantMap = new HashMap<Entity, Double>();
        for (final Entity crystal : crystalList) {
            if (HolePush.mc.player.getDistance(crystal.posX, crystal.posY, crystal.posZ) < (int)this.attackRange.getValue()) {
                distantMap.put(crystal, HolePush.mc.player.getDistance(crystal.posX, crystal.posY, crystal.posZ));
            }
        }
        final List<Map.Entry<Entity, Double>> list = new ArrayList<Map.Entry<Entity, Double>>(distantMap.entrySet());
        list.sort((Comparator<? super Map.Entry<Entity, Double>>)Map.Entry.comparingByValue());
        if (list.size() == 0) {
            return;
        }
        if (list.get(0).getValue() < 5.0) {
            final int oldSlot = HolePush.mc.player.inventory.currentItem;
            if ((boolean)this.antiWeakness.getValue() && HolePush.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                int newSlot = -1;
                for (int i = 0; i < 9; ++i) {
                    final ItemStack stack = HolePush.mc.player.inventory.getStackInSlot(i);
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
            if (!(boolean)this.crystalPacketAttack.getValue()) {
                CrystalUtil.breakCrystal((Entity)list.get(list.size() - 1).getKey(), (boolean)this.crystalAttackSwing.getValue());
            }
            else {
                CrystalUtil.breakCrystalPacket((Entity)list.get(list.size() - 1).getKey(), (boolean)this.crystalAttackSwing.getValue());
            }
            if (this.silent.getValue()) {
                this.switchTo(oldSlot);
            }
            if (this.crystalAttackRotate.getValue()) {
                final Entity crystal2 = (Entity)list.get(list.size() - 1);
                if (crystal2 == null) {
                    return;
                }
                final Vec3d lastHitVec = new Vec3d(crystal2.posX, crystal2.posY, crystal2.posZ).add(0.5, 1.0, 0.5);
                BurrowUtil.faceVector(lastHitVec, true);
            }
        }
    }
    
    public static ArrayList<BlockPos> haveNeighborBlock(final BlockPos pos, final Block neighbor) {
        final ArrayList<BlockPos> blockList = new ArrayList<BlockPos>();
        if (HolePush.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock().equals(neighbor)) {
            blockList.add(pos.add(1, 0, 0));
        }
        if (HolePush.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock().equals(neighbor)) {
            blockList.add(pos.add(-1, 0, 0));
        }
        if (HolePush.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(neighbor)) {
            blockList.add(pos.add(0, 1, 0));
        }
        if (HolePush.mc.world.getBlockState(pos.add(0, -1, 0)).getBlock().equals(neighbor)) {
            blockList.add(pos.add(0, -1, 0));
        }
        if (HolePush.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock().equals(neighbor)) {
            blockList.add(pos.add(0, 0, 1));
        }
        if (HolePush.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock().equals(neighbor)) {
            blockList.add(pos.add(0, 0, -1));
        }
        return blockList;
    }
    
    public BlockPos getRSTPos2(final BlockPos pistonPos, final double range, final boolean rayTrace, final boolean instaMineCheck, final boolean helpBlockCheck) {
        if (pistonPos == null) {
            return null;
        }
        if (haveNeighborBlock(pistonPos, Blocks.REDSTONE_BLOCK).size() > 0 && isNoBBoxBlocked(pistonPos)) {
            return haveNeighborBlock(pistonPos, Blocks.REDSTONE_BLOCK).get(0);
        }
        final ArrayList<BlockPos> placePosList = new ArrayList<BlockPos>();
        placePosList.add(pistonPos.add(0, 1, 0));
        placePosList.add(pistonPos.add(-1, 0, 0));
        placePosList.add(pistonPos.add(1, 0, 0));
        placePosList.add(pistonPos.add(0, 0, -1));
        placePosList.add(pistonPos.add(0, 0, 1));
        final HashMap<BlockPos, Double> distantMap = new HashMap<BlockPos, Double>();
        for (final BlockPos rSTPos : placePosList) {
            if (HolePush.mc.world.getBlockState(rSTPos).getBlock().equals(Blocks.AIR) && isNoBBoxBlocked(rSTPos)) {
                if (Math.sqrt(HolePush.mc.player.getDistanceSq(rSTPos)) > range) {
                    continue;
                }
                if (rayTrace && !rayTraceRangeCheck(rSTPos, 0.0, 0.0)) {
                    continue;
                }
                if (instaMineCheck && this.savePos != null && this.savePos.equals((Object)rSTPos)) {
                    continue;
                }
                if (helpBlockCheck && !this.helpingBlockCheck(rSTPos)) {
                    continue;
                }
                distantMap.put(rSTPos, HolePush.mc.player.getDistanceSq(rSTPos));
            }
        }
        final List<Map.Entry<BlockPos, Double>> list = new ArrayList<Map.Entry<BlockPos, Double>>(distantMap.entrySet());
        list.sort((Comparator<? super Map.Entry<BlockPos, Double>>)Map.Entry.comparingByValue());
        if (list.size() == 0) {
            return null;
        }
        return list.get(0).getKey();
    }
    
    public static boolean caCheck(final double checkRange, final int min, final int max, final int baseValue, final int weight, final boolean onlyGet) {
        if (onlyGet) {
            return HolePush.var <= baseValue;
        }
        if (baseValue >= max || baseValue <= min) {
            return false;
        }
        final ArrayList<Entity> crystalList = new ArrayList<Entity>();
        for (final Entity entity : HolePush.mc.world.loadedEntityList) {
            if (entity instanceof EntityEnderCrystal && HolePush.mc.player.getDistance(entity.posX, entity.posY, entity.posZ) < checkRange) {
                crystalList.add(entity);
            }
        }
        HolePush.hasCry = (crystalList.size() != 0);
        if (HolePush.hasCry != HolePush.oldCry) {
            HolePush.oldCry = HolePush.hasCry;
            HolePush.var += weight;
        }
        else {
            --HolePush.var;
        }
        if (HolePush.var >= max) {
            HolePush.var = max;
        }
        if (HolePush.var <= min) {
            HolePush.var = min;
        }
        return HolePush.var <= baseValue;
    }
    
    public static EnumFacing getFacing(final BlockPos pos) {
        final ImmutableMap<IProperty<?>, Comparable<?>> properties = (ImmutableMap<IProperty<?>, Comparable<?>>)HolePush.mc.world.getBlockState(pos).getProperties();
        for (final IProperty<?> prop : properties.keySet()) {
            if (prop.getValueClass() == EnumFacing.class && (prop.getName().equals("facing") || prop.getName().equals("rotation"))) {
                return (EnumFacing)properties.get((Object)prop);
            }
        }
        return null;
    }
    
    public PushInfo getPistonPos(final EntityPlayer player, final boolean raytrace) {
        if (player == null || player.equals((Object)HolePush.mc.player)) {
            return null;
        }
        final BlockPos playerPos = getFlooredPosition((Entity)player);
        if (HolePush.mc.world.getBlockState(playerPos.add(0, 1, 0)).getBlock().equals(Blocks.PISTON_HEAD)) {
            if (!HolePush.mc.world.getBlockState(playerPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                return null;
            }
            final EnumFacing headFac = getFacing(playerPos.add(0, 1, 0));
            if (headFac == null) {
                return null;
            }
            BlockPos pisPos = null;
            switch (headFac) {
                case EAST: {
                    pisPos = playerPos.add(-1, 1, 0);
                    break;
                }
                case WEST: {
                    pisPos = playerPos.add(1, 1, 0);
                    break;
                }
                case NORTH: {
                    pisPos = playerPos.add(0, 1, 1);
                    break;
                }
                case SOUTH: {
                    pisPos = playerPos.add(0, 1, -1);
                    break;
                }
            }
            if (pisPos != null && HolePush.mc.world.getBlockState(pisPos).getBlock() instanceof BlockPistonBase) {
                final ArrayList<BlockPos> l = haveNeighborBlock(pisPos, Blocks.REDSTONE_BLOCK);
                if (l.size() == 1) {
                    final BlockPos rstPos = l.get(0);
                    if (raytrace && !rayTraceRangeCheck(rstPos, 0.0, 0.0)) {
                        return null;
                    }
                    if (Math.sqrt(HolePush.mc.player.getDistanceSq(rstPos)) > 6.0) {
                        return null;
                    }
                    return new PushInfo(pisPos, rstPos, headFac, true);
                }
            }
            return null;
        }
        else {
            if (!HolePush.mc.world.getBlockState(playerPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                return null;
            }
            final HashMap<PushInfo, Double> distantMap = new HashMap<PushInfo, Double>();
            for (int i = 0; i < 4; ++i) {
                int xOffSet = 0;
                int zOffSet = 0;
                EnumFacing Pisfac;
                if (i == 0) {
                    xOffSet = 1;
                    Pisfac = EnumFacing.WEST;
                }
                else if (i == 1) {
                    xOffSet = -1;
                    Pisfac = EnumFacing.EAST;
                }
                else if (i == 2) {
                    zOffSet = 1;
                    Pisfac = EnumFacing.NORTH;
                }
                else {
                    zOffSet = -1;
                    Pisfac = EnumFacing.SOUTH;
                }
                if (HolePush.mc.world.getBlockState(playerPos.add(-xOffSet, 1, -zOffSet)).getBlock().equals(Blocks.AIR) && ((HolePush.mc.world.getBlockState(playerPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && HolePush.mc.world.getBlockState(playerPos.add(-xOffSet, 2, -zOffSet)).getBlock().equals(Blocks.AIR)) || HolePush.mc.world.getBlockState(playerPos.add(-xOffSet, 0, -zOffSet)).getBlock().equals(Blocks.AIR))) {
                    final PushInfo pushInfo = new PushInfo(playerPos.add(xOffSet, 1, zOffSet), raytrace, (boolean)this.noPlaceRstOnBreakPos.getValue(), Pisfac, false);
                    if (pushInfo.check()) {
                        distantMap.put(pushInfo, Math.sqrt(HolePush.mc.player.getDistanceSq(pushInfo.pistonPos)));
                    }
                }
                else if (!HolePush.mc.world.getBlockState(playerPos).getBlock().equals(Blocks.AIR) && !HolePush.mc.world.getBlockState(playerPos).getBlock().equals(Blocks.WEB) && HolePush.mc.world.getBlockState(playerPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && HolePush.mc.world.getBlockState(playerPos.add(xOffSet, 2, zOffSet)).getBlock().equals(Blocks.AIR)) {
                    final PushInfo pushInfo = new PushInfo(playerPos.add(xOffSet, 1, zOffSet), raytrace, (boolean)this.noPlaceRstOnBreakPos.getValue(), Pisfac, false);
                    if (pushInfo.check()) {
                        distantMap.put(pushInfo, Math.sqrt(HolePush.mc.player.getDistanceSq(pushInfo.pistonPos)));
                    }
                }
            }
            final List<Map.Entry<PushInfo, Double>> list = new ArrayList<Map.Entry<PushInfo, Double>>(distantMap.entrySet());
            list.sort((Comparator<? super Map.Entry<PushInfo, Double>>)Map.Entry.comparingByValue());
            int a;
            if (this.farPlace.getValue()) {
                for (a = list.size() - 1; a >= 0; --a) {
                    if ((!(boolean)this.noPlacePisOnBreakPos.getValue() || !list.get(a).getKey().pistonPos.equals((Object)this.savePos)) && list.get(a).getValue() < (double)this.placeRange.getValue()) {
                        if (!raytrace) {
                            break;
                        }
                        if (rayTraceRangeCheck(list.get(a).getKey().pistonPos, 0.0, 0.0)) {
                            break;
                        }
                    }
                }
            }
            else {
                for (a = 0; a <= list.size() - 1; ++a) {
                    if ((!(boolean)this.noPlacePisOnBreakPos.getValue() || !list.get(a).getKey().pistonPos.equals((Object)this.savePos)) && list.get(a).getValue() < (double)this.placeRange.getValue()) {
                        if (!raytrace) {
                            break;
                        }
                        if (rayTraceRangeCheck(list.get(a).getKey().pistonPos, 0.0, 0.0)) {
                            break;
                        }
                    }
                }
            }
            if (a == -1) {
                return null;
            }
            return (list.size() >= 1) ? list.get(0).getKey() : null;
        }
    }
    
    public static boolean rayTraceRangeCheck(final BlockPos pos, final double range, final double height) {
        final RayTraceResult result = HolePush.mc.world.rayTraceBlocks(new Vec3d(HolePush.mc.player.posX, HolePush.mc.player.posY + HolePush.mc.player.getEyeHeight(), HolePush.mc.player.posZ), new Vec3d((double)pos.getX(), pos.getY() + height, (double)pos.getZ()), false, true, false);
        return result == null || HolePush.mc.player.getDistanceSq(pos) <= range * range;
    }
    
    public static boolean isNoBBoxBlocked(final BlockPos pos) {
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(pos);
        final List<Entity> l = (List<Entity>)HolePush.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, axisAlignedBB);
        for (final Entity entity : l) {
            if (!(entity instanceof EntityEnderCrystal) && !(entity instanceof EntityItem) && !(entity instanceof EntityArrow)) {
                if (entity instanceof EntityXPOrb) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }
    
    public static BlockPos getFlooredPosition(final Entity entity) {
        return new BlockPos(Math.floor(entity.posX), Math.floor(entity.posY), Math.floor(entity.posZ));
    }
    
    public static boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : HolePush.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isFacing(final BlockPos pos, final EnumFacing enumFacing) {
        final ImmutableMap<IProperty<?>, Comparable<?>> properties = (ImmutableMap<IProperty<?>, Comparable<?>>)HolePush.mc.world.getBlockState(pos).getProperties();
        for (final IProperty<?> prop : properties.keySet()) {
            if (prop.getValueClass() == EnumFacing.class && (prop.getName().equals("facing") || prop.getName().equals("rotation")) && properties.get((Object)prop) == enumFacing) {
                return true;
            }
        }
        return false;
    }
    
    static {
        HolePush.hasCry = false;
        HolePush.oldCry = false;
        HolePush.var = 0;
    }
    
    public class PushInfo
    {
        public BlockPos pistonPos;
        private final BlockPos rstPos;
        public EnumFacing pisFac;
        public boolean pullMode;
        
        public PushInfo(final BlockPos pistonPos, final BlockPos rstPos, final EnumFacing pisFac, final boolean pullMode) {
            this.pistonPos = pistonPos;
            this.rstPos = rstPos;
            this.pisFac = pisFac;
            this.pullMode = pullMode;
        }
        
        public PushInfo(final BlockPos pistonPos, final boolean rayTrace, final boolean instaMineC, final EnumFacing pisFac, final boolean pullMode) {
            this.pistonPos = pistonPos;
            this.rstPos = HolePush.this.getRSTPos2(pistonPos, 6.0, rayTrace, instaMineC, false);
            this.pisFac = pisFac;
            this.pullMode = pullMode;
        }
        
        public boolean check() {
            return this.rstPos != null && this.pistonPos != null && (HolePush.mc.world.getBlockState(this.pistonPos).getBlock().equals(Blocks.AIR) || (HolePush.mc.world.getBlockState(this.pistonPos).getBlock().equals(Blocks.PISTON) && HolePush.isFacing(this.pistonPos, this.pisFac)) || (HolePush.mc.world.getBlockState(this.pistonPos).getBlock().equals(Blocks.STICKY_PISTON) && HolePush.isFacing(this.pistonPos, this.pisFac))) && !HolePush.intersectsWithEntity(this.pistonPos) && !HolePush.intersectsWithEntity(this.rstPos) && (HolePush.mc.world.getBlockState(this.rstPos).getBlock().equals(Blocks.AIR) || HolePush.mc.world.getBlockState(this.rstPos).getBlock().equals(Blocks.REDSTONE_BLOCK));
        }
    }
}
