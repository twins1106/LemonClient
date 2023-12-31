//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.qwq;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.potion.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.api.util.render.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import com.lemonclient.api.event.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.client.manager.managers.*;
import net.minecraft.util.math.*;

@Module.Declaration(name = "Mine", category = Category.qwq, priority = 250)
public class Mine extends Module
{
    public static Mine INSTANCE;
    ModeSetting page;
    ModeSetting time;
    ModeSetting mode;
    BooleanSetting double_mine;
    BooleanSetting breakAgain;
    BooleanSetting reset;
    BooleanSetting maxSwitchBack;
    IntegerSetting switchBackDelay;
    DoubleSetting minHealth;
    BooleanSetting updateController;
    BooleanSetting cancel;
    BooleanSetting avoid;
    BooleanSetting continueBreaking;
    BooleanSetting disableContinueShift;
    BooleanSetting continueBreakingAlways;
    BooleanSetting haste;
    BooleanSetting autoswitch;
    BooleanSetting switchBack;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting bypass;
    BooleanSetting spammer;
    BooleanSetting ignoreChecks;
    BooleanSetting swing;
    BooleanSetting packetswing;
    BooleanSetting startPick;
    BooleanSetting showProgress;
    IntegerSetting spammerTickDelay;
    IntegerSetting breakerTickDelay;
    BooleanSetting forceRotation;
    IntegerSetting rangeDisableBreaker;
    BooleanSetting display;
    ColorSetting blockColor;
    ColorSetting doneColor;
    ColorSetting doubleBlockColor;
    ModeSetting renderMode;
    IntegerSetting alpha;
    IntegerSetting width;
    public BlockPos doubleMine;
    public float doubleMineDamage;
    public boolean mined;
    private int tickSpammer;
    private int breakTick;
    public float mineDamage;
    boolean switched;
    public BlockPos lastBlock;
    private BlockPos continueBlock;
    public boolean pickStillBol;
    private boolean minedBefore;
    private Vec3d lastHitVec;
    Timing switchTiming;
    @EventHandler
    private final Listener<OnUpdateWalkingPlayerEvent> onUpdateWalkingPlayerEventListener;
    boolean broke;
    @EventHandler
    private final Listener<DamageBlockEvent> listener;
    @EventHandler
    private final Listener<PacketEvent.Send> packetListener;
    
    public Mine() {
        this.page = this.registerMode("Page", (List)Arrays.asList("Settings", "Render"), "Settings");
        this.time = this.registerMode("Time Mode", (List)Arrays.asList("Tick", "onUpdate", "Both", "Fast"), "onUpdate", () -> ((String)this.page.getValue()).equals("Settings"));
        this.mode = this.registerMode("Mode", (List)Arrays.asList("Packet", "Instant", "Breaker"), "Breaker", () -> ((String)this.page.getValue()).equals("Settings"));
        this.double_mine = this.registerBoolean("Double Mine", true, () -> ((String)this.page.getValue()).equals("Settings"));
        this.breakAgain = this.registerBoolean("Break", true, () -> this.double_mine.isVisible());
        this.reset = this.registerBoolean("Rest Damage", true, () -> this.double_mine.isVisible());
        this.maxSwitchBack = this.registerBoolean("Max Switch Back", true, () -> this.double_mine.isVisible());
        this.switchBackDelay = this.registerInteger("Max Switch Back Delay", 2, 0, 10000, () -> this.maxSwitchBack.isVisible() && (boolean)this.maxSwitchBack.getValue());
        this.minHealth = this.registerDouble("Min Health", 16.0, 0.0, 36.0, () -> ((String)this.page.getValue()).equals("Settings"));
        this.updateController = this.registerBoolean("Update Controller", true, () -> this.double_mine.isVisible());
        this.cancel = this.registerBoolean("Packet Cancel", true, () -> ((String)this.page.getValue()).equals("Settings"));
        this.avoid = this.registerBoolean("Avoid Bedrock", true, () -> ((String)this.page.getValue()).equals("Settings"));
        this.continueBreaking = this.registerBoolean("Continue Breaking", false, () -> ((String)this.page.getValue()).equals("Settings"));
        this.disableContinueShift = this.registerBoolean("Shift to Stop", false, () -> ((String)this.page.getValue()).equals("Settings"));
        this.continueBreakingAlways = this.registerBoolean("Always Continue", false, () -> ((String)this.page.getValue()).equals("Settings"));
        this.haste = this.registerBoolean("Haste", false, () -> ((String)this.page.getValue()).equals("Settings"));
        this.autoswitch = this.registerBoolean("Auto Switch", true, () -> ((String)this.page.getValue()).equals("Settings"));
        this.switchBack = this.registerBoolean("Switch Back", true, () -> ((String)this.page.getValue()).equals("Settings"));
        this.packetSwitch = this.registerBoolean("Packet Switch", false, () -> ((String)this.page.getValue()).equals("Settings"));
        this.check = this.registerBoolean("Switch Check", false, () -> ((String)this.page.getValue()).equals("Settings"));
        this.bypass = this.registerBoolean("Silent Bypass", false, () -> ((String)this.page.getValue()).equals("Settings"));
        this.spammer = this.registerBoolean("Spammer", true, () -> ((String)this.page.getValue()).equals("Settings"));
        this.ignoreChecks = this.registerBoolean("Ignore Checks", true, () -> ((String)this.page.getValue()).equals("Settings"));
        this.swing = this.registerBoolean("Swing", false, () -> ((String)this.page.getValue()).equals("Settings"));
        this.packetswing = this.registerBoolean("Packet Swing", false, () -> ((String)this.page.getValue()).equals("Settings"));
        this.startPick = this.registerBoolean("Start Pick", false, () -> ((String)this.page.getValue()).equals("Settings"));
        this.showProgress = this.registerBoolean("Show Progress", false, () -> ((String)this.page.getValue()).equals("Render"));
        this.spammerTickDelay = this.registerInteger("Spammer Delay", 0, 0, 100, () -> ((String)this.page.getValue()).equals("Render"));
        this.breakerTickDelay = this.registerInteger("Breaker Delay", 0, 0, 100, () -> ((String)this.page.getValue()).equals("Render"));
        this.forceRotation = this.registerBoolean("Force Rotation", false, () -> ((String)this.page.getValue()).equals("Render"));
        this.rangeDisableBreaker = this.registerInteger("Stop Range", 16, 0, 128, () -> ((String)this.page.getValue()).equals("Render"));
        this.display = this.registerBoolean("Display", true, () -> ((String)this.page.getValue()).equals("Render"));
        this.blockColor = this.registerColor("Block Color", new GSColor(255, 0, 0), () -> ((String)this.page.getValue()).equals("Render") && (boolean)this.display.getValue());
        this.doneColor = this.registerColor("Done Color", new GSColor(0, 255, 0), () -> ((String)this.page.getValue()).equals("Render") && (boolean)this.display.getValue());
        this.doubleBlockColor = this.registerColor("Double Color", new GSColor(0, 255, 0), () -> ((String)this.page.getValue()).equals("Render") && (boolean)this.display.getValue());
        this.renderMode = this.registerMode("Render", (List)Arrays.asList("Outline", "Fill", "Both"), "Both", () -> ((String)this.page.getValue()).equals("Render") && (boolean)this.display.getValue());
        this.alpha = this.registerInteger("Alpha", 50, 0, 255, () -> ((String)this.page.getValue()).equals("Render") && (boolean)this.display.getValue());
        this.width = this.registerInteger("Width", 1, 1, 10, () -> ((String)this.page.getValue()).equals("Render") && (boolean)this.display.getValue());
        this.doubleMineDamage = -1.0f;
        this.tickSpammer = 0;
        this.breakTick = 0;
        this.mineDamage = -1.0f;
        this.continueBlock = null;
        this.pickStillBol = false;
        this.minedBefore = false;
        this.lastHitVec = null;
        this.switchTiming = new Timing();
        this.onUpdateWalkingPlayerEventListener = (Listener<OnUpdateWalkingPlayerEvent>)new Listener(event -> {
            if (event.getPhase() != Phase.PRE || this.lastHitVec == null || !(boolean)this.forceRotation.getValue() || this.lastBlock == null) {
                return;
            }
            final Vec2f rotation = RotationUtil.getRotationTo(this.lastHitVec);
            final PlayerPacket packet = new PlayerPacket((Module)this, rotation);
            PlayerPacketManager.INSTANCE.addPacket(packet);
        }, new Predicate[0]);
        this.broke = false;
        this.listener = (Listener<DamageBlockEvent>)new Listener(event -> {
            if (Mine.mc.world == null || Mine.mc.player == null || Mine.mc.player.isDead) {
                return;
            }
            if (Mine.mc.world.getBlockState(event.getBlockPos()).getBlock() == Blocks.BEDROCK && (boolean)this.avoid.getValue()) {
                event.cancel();
                return;
            }
            if (this.canBreak(event.getBlockPos()) || event.getBlockPos() == null) {
                if (this.lastBlock != null && this.doubleMine == null) {
                    this.doubleMine = this.lastBlock;
                    this.doubleMineDamage = (this.reset.getValue() ? 0.0f : this.mineDamage);
                    this.switched = false;
                }
                this.lastBlock = null;
                return;
            }
            if (this.isPos2(this.lastBlock, event.getBlockPos()) && !this.isPos2(this.continueBlock, event.getBlockPos())) {
                event.cancel();
                return;
            }
            if (this.forceRotation.getValue()) {
                this.setVec3d(event.getBlockPos(), event.getEnumFacing());
            }
            if (this.continueBreaking.getValue()) {
                this.continueBlock = event.getBlockPos();
            }
            final String s = (String)this.mode.getValue();
            switch (s) {
                case "Packet": {
                    if (this.swing.getValue()) {
                        if (this.packetswing.getValue()) {
                            Mine.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        }
                        else {
                            Mine.mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                    }
                    Mine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
                    Mine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
                    event.cancel();
                    if (this.lastBlock != null && this.doubleMine == null && !Mine.mc.world.isAirBlock(this.lastBlock)) {
                        this.doubleMine = this.lastBlock;
                        this.doubleMineDamage = (this.reset.getValue() ? 0.0f : this.mineDamage);
                        this.switched = false;
                    }
                    this.lastBlock = event.getBlockPos();
                    this.mineDamage = 0.0f;
                    this.pickStillBol = false;
                    break;
                }
                case "Instant": {
                    if (this.swing.getValue()) {
                        if (this.packetswing.getValue()) {
                            Mine.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        }
                        else {
                            Mine.mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                    }
                    Mine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
                    Mine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
                    Mine.mc.playerController.onPlayerDestroyBlock(event.getBlockPos());
                    Mine.mc.world.setBlockToAir(event.getBlockPos());
                    break;
                }
                case "Breaker": {
                    this.breakerAlgo(event);
                    break;
                }
            }
        }, new Predicate[0]);
        this.packetListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getPacket() instanceof CPacketPlayerDigging) {
                final CPacketPlayerDigging packet = (CPacketPlayerDigging)event.getPacket();
                final BlockPos pos = packet.getPosition();
                if (packet.getAction().equals((Object)CPacketPlayerDigging.Action.START_DESTROY_BLOCK) && (this.isPos2(pos, this.lastBlock) || ((boolean)this.avoid.getValue() && Mine.mc.world.getBlockState(pos).getBlock() == Blocks.BEDROCK))) {
                    event.cancel();
                }
            }
            if ((boolean)this.cancel.getValue() && event.getPacket() instanceof SPacketBlockBreakAnim) {
                event.cancel();
            }
        }, new Predicate[0]);
        Mine.INSTANCE = this;
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
    
    public void onUpdate() {
        if (((String)this.time.getValue()).equals("onUpdate") || ((String)this.time.getValue()).equals("Both")) {
            this.mine();
        }
    }
    
    public void onTick() {
        if (((String)this.time.getValue()).equals("Tick") || ((String)this.time.getValue()).equals("Both")) {
            this.mine();
        }
    }
    
    public void fast() {
        if (((String)this.time.getValue()).equals("Fast")) {
            this.mine();
        }
    }
    
    private void mine() {
        if (Mine.mc.world == null || Mine.mc.player == null || Mine.mc.player.isDead) {
            final BlockPos lastBlock = null;
            this.doubleMine = lastBlock;
            this.continueBlock = lastBlock;
            this.lastBlock = lastBlock;
            final float n = 0.0f;
            this.doubleMineDamage = n;
            this.mineDamage = n;
            this.mined = false;
            this.switched = false;
            this.switchTiming.reset();
            return;
        }
        if (!(boolean)this.double_mine.getValue()) {
            if (this.switched) {
                this.switchToDoubleMine(Mine.mc.player.inventory.currentItem);
            }
            this.doubleMine = null;
            this.doubleMineDamage = 0.0f;
            this.switched = false;
            this.switchTiming.reset();
        }
        if (this.lastBlock != null && (Mine.mc.world.getBlockState(this.lastBlock).getBlock() == Blocks.BEDROCK || ((int)this.rangeDisableBreaker.getValue() != 0 && Mine.mc.player.getDistanceSq(this.lastBlock) > (int)this.rangeDisableBreaker.getValue() * (int)this.rangeDisableBreaker.getValue()))) {
            this.lastBlock = null;
            this.mineDamage = 0.0f;
        }
        if (this.doubleMine != null && (Mine.mc.world.isAirBlock(this.doubleMine) || (this.switched && (boolean)this.maxSwitchBack.getValue() && this.switchTiming.passedMs((long)(int)this.switchBackDelay.getValue())))) {
            if (this.switched) {
                this.switchToDoubleMine(Mine.mc.player.inventory.currentItem);
            }
            this.doubleMine = null;
            this.doubleMineDamage = 0.0f;
            this.switched = false;
            this.switchTiming.reset();
        }
        if (this.isPos2(this.doubleMine, this.lastBlock)) {
            if (this.switched) {
                this.switchToDoubleMine(Mine.mc.player.inventory.currentItem);
            }
            this.mineDamage = this.doubleMineDamage;
            this.doubleMine = null;
            this.doubleMineDamage = 0.0f;
            this.switched = false;
            this.switchTiming.reset();
        }
        if (Mine.mc.player.getHealth() + Mine.mc.player.getAbsorptionAmount() < (double)this.minHealth.getValue() && this.switched) {
            this.switchToDoubleMine(Mine.mc.player.inventory.currentItem);
            this.switched = false;
        }
        if ((boolean)this.continueBreaking.getValue() && this.continueBlock != null) {
            if ((boolean)this.disableContinueShift.getValue() && Mine.mc.gameSettings.keyBindSneak.isKeyDown()) {
                this.continueBlock = null;
            }
            else {
                if (BlockUtil.getBlock(this.continueBlock) instanceof BlockAir) {
                    this.broke = true;
                }
                if (!(BlockUtil.getBlock(this.continueBlock) instanceof BlockAir) && (this.broke || (boolean)this.continueBreakingAlways.getValue())) {
                    if (this.swing.getValue()) {
                        if (this.packetswing.getValue()) {
                            Mine.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        }
                        else {
                            Mine.mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                    }
                    Mine.mc.playerController.onPlayerDamageBlock(this.continueBlock, EnumFacing.UP);
                    this.broke = false;
                }
            }
        }
        if (this.lastBlock != null && this.mineDamage >= 1.0f) {
            final int prev = Mine.mc.player.inventory.currentItem;
            final int slot = this.findItem(this.lastBlock);
            if (((boolean)this.autoswitch.getValue() || (boolean)this.bypass.getValue()) && slot != -1) {
                if (this.bypass.getValue()) {
                    switchToBypass(slot);
                }
                else {
                    this.switchTo(slot);
                }
                if (this.lastBlock != null) {
                    Mine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.lastBlock, EnumFacing.UP));
                }
            }
            if (!this.pickStillBol) {
                if (!(boolean)this.switchBack.getValue()) {
                    this.mineDamage = 0.0f;
                    this.pickStillBol = true;
                }
                else if (this.bypass.getValue()) {
                    switchToBypass(prev);
                }
                else {
                    this.switchTo(prev);
                }
            }
        }
        if (this.doubleMine != null && this.doubleMineDamage >= 1.0f) {
            final int slot2 = this.findItem(this.doubleMine);
            if (((boolean)this.autoswitch.getValue() || (boolean)this.bypass.getValue()) && slot2 != -1) {
                if (Mine.mc.player.getHealth() + Mine.mc.player.getAbsorptionAmount() >= (double)this.minHealth.getValue()) {
                    this.switchToDoubleMine(slot2);
                }
                if ((boolean)this.breakAgain.getValue() && this.doubleMine != null) {
                    Mine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.doubleMine, EnumFacing.UP));
                }
                this.switched = true;
            }
        }
        if (this.haste.getValue()) {
            final PotionEffect effect = new PotionEffect(MobEffects.HASTE, 80950, 1, false, false);
            Mine.mc.player.addPotionEffect(new PotionEffect(effect));
        }
        if (!(boolean)this.haste.getValue() && Mine.mc.player.isPotionActive(MobEffects.HASTE)) {
            Mine.mc.player.removePotionEffect(MobEffects.HASTE);
        }
        if (((String)this.mode.getValue()).equals("Breaker") && this.lastBlock != null && (boolean)this.spammer.getValue() && this.tickSpammer++ >= (int)this.spammerTickDelay.getValue()) {
            this.tickSpammer = 0;
            if (this.mineDamage >= 1.0f) {
                this.minedBefore = true;
                this.lastHitVec = null;
            }
            if (this.minedBefore && ((boolean)this.ignoreChecks.getValue() || !(BlockUtil.getBlock(this.lastBlock) instanceof BlockAir))) {
                if (this.forceRotation.getValue()) {
                    this.setVec3d(this.lastBlock, EnumFacing.UP);
                }
                else {
                    this.breakerBreak();
                }
            }
        }
    }
    
    public void breakerBreak() {
        if (this.lastBlock == null) {
            return;
        }
        int oldSlot = -1;
        if (this.minedBefore && ((boolean)this.autoswitch.getValue() || (boolean)this.bypass.getValue())) {
            final int slot = this.findItem(this.lastBlock);
            if (slot != -1) {
                oldSlot = Mine.mc.player.inventory.currentItem;
                if (this.bypass.getValue()) {
                    switchToBypass(slot);
                }
                else {
                    this.switchTo(slot);
                }
            }
        }
        Mine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.lastBlock, EnumFacing.UP));
        if (this.swing.getValue()) {
            if (this.packetswing.getValue()) {
                Mine.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
            }
            else {
                Mine.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
        this.mined = true;
        if (oldSlot != -1 && (boolean)this.switchBack.getValue()) {
            if (this.bypass.getValue()) {
                switchToBypass(oldSlot);
            }
            else {
                this.switchTo(oldSlot);
            }
            if (!this.minedBefore || ((String)this.mode.getValue()).equals("Packet")) {
                this.pickStillBol = !(boolean)this.switchBack.getValue();
            }
        }
    }
    
    private void setVec3d(final BlockPos pos, final EnumFacing side) {
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        this.lastHitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
    }
    
    public void breakerAlgo(final DamageBlockEvent event) {
        if (!this.isPos2(this.lastBlock, event.getBlockPos()) || this.isPos2(this.continueBlock, event.getBlockPos())) {
            if (this.startPick.getValue()) {
                final int pick = this.findItem(event.getBlockPos());
                if (pick != -1) {
                    Mine.mc.player.inventory.currentItem = pick;
                }
            }
            this.minedBefore = false;
            if (this.swing.getValue()) {
                if (this.packetswing.getValue()) {
                    Mine.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                }
                else {
                    Mine.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }
            Mine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
            if (this.lastBlock != null && this.doubleMine == null && !Mine.mc.world.isAirBlock(this.lastBlock)) {
                this.doubleMine = this.lastBlock;
                this.doubleMineDamage = (this.reset.getValue() ? 0.0f : this.mineDamage);
                this.switched = false;
            }
            this.lastBlock = event.getBlockPos();
            this.mineDamage = 0.0f;
            event.cancel();
        }
        if ((int)this.breakerTickDelay.getValue() == 0 || (int)this.breakerTickDelay.getValue() <= this.breakTick++) {
            this.breakerBreak();
            this.breakTick = 0;
        }
        this.pickStillBol = false;
        if ((boolean)this.autoswitch.getValue() || (boolean)this.bypass.getValue()) {
            this.pickStillBol = !(boolean)this.switchBack.getValue();
        }
    }
    
    public void breaker(final BlockPos block) {
        if ((this.lastBlock != null && this.isPos2(block, this.lastBlock)) || this.isPos2(this.continueBlock, block)) {
            return;
        }
        if (this.startPick.getValue()) {
            final int pick = this.findItem(block);
            if (pick != -1) {
                Mine.mc.player.inventory.currentItem = pick;
            }
        }
        this.minedBefore = false;
        if (this.swing.getValue()) {
            if (this.packetswing.getValue()) {
                Mine.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
            }
            else {
                Mine.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
        Mine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, block, EnumFacing.UP));
        if (this.lastBlock != null && this.doubleMine == null && !Mine.mc.world.isAirBlock(this.lastBlock)) {
            this.doubleMine = this.lastBlock;
            this.doubleMineDamage = (this.reset.getValue() ? 0.0f : this.mineDamage);
            this.switched = false;
        }
        this.lastBlock = block;
        if ((int)this.breakerTickDelay.getValue() == 0 || (int)this.breakerTickDelay.getValue() <= this.breakTick++) {
            this.breakerBreak();
            this.breakTick = 0;
        }
        this.mineDamage = 0.0f;
        this.pickStillBol = false;
    }
    
    private boolean canBreak(final BlockPos pos) {
        if (pos == null) {
            return true;
        }
        final IBlockState blockState = Mine.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)Mine.mc.world, pos) == -1.0f;
    }
    
    public void onDisable() {
        if (this.haste.getValue()) {
            Mine.mc.player.removePotionEffect(MobEffects.HASTE);
        }
        this.breakTick = 0;
        final BlockPos lastBlock = null;
        this.doubleMine = lastBlock;
        this.continueBlock = lastBlock;
        this.lastBlock = lastBlock;
        if (this.switched) {
            this.switchToDoubleMine(Mine.mc.player.inventory.currentItem);
        }
        final float n = 0.0f;
        this.doubleMineDamage = n;
        this.mineDamage = n;
        this.switched = false;
        this.switchTiming.reset();
    }
    
    public void onWorldRender(final RenderEvent event) {
        this.calc();
        if (this.lastBlock != null) {
            if (((String)this.mode.getValue()).equals("Breaker") || (((String)this.mode.getValue()).equals("Packet") && !(BlockUtil.getBlock(this.lastBlock) instanceof BlockAir))) {
                final float damage = (this.mineDamage >= 1.0f) ? 1.0f : this.mineDamage;
                final GSColor color = (damage == 1.0f) ? this.doneColor.getValue() : this.blockColor.getValue();
                this.renderBox(this.lastBlock, color, damage, (boolean)this.showProgress.getValue(), (boolean)this.display.getValue(), (String)this.renderMode.getValue(), (int)this.alpha.getValue(), (int)this.width.getValue());
            }
            else {
                this.lastBlock = null;
            }
        }
        if ((boolean)this.double_mine.getValue() && this.doubleMine != null) {
            final float damage = (this.doubleMineDamage >= 1.0f) ? 1.0f : this.doubleMineDamage;
            final GSColor color = this.doubleBlockColor.getValue();
            this.renderBox(this.doubleMine, color, damage, (boolean)this.showProgress.getValue(), (boolean)this.display.getValue(), (String)this.renderMode.getValue(), (int)this.alpha.getValue(), (int)this.width.getValue());
        }
    }
    
    public static GSColor getColor(final int damage) {
        return GSColor.fromHSB((1 + damage * 32) % 11520 / 11520.0f, 1.0f, 1.0f);
    }
    
    private void renderBox(final BlockPos blockPos, final GSColor color, final float mineDamage, final boolean show, final boolean display, final String mode, final int alpha, final int width) {
        final AxisAlignedBB getSelectedBoundingBox = new AxisAlignedBB(blockPos);
        final Vec3d getCenter = getSelectedBoundingBox.getCenter();
        final float prognum = mineDamage * 100.0f;
        if (show) {
            final String[] progress = { String.format("%.1f", prognum) };
            RenderUtil.drawNametag(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, progress, getColor((int)prognum), 1);
        }
        if (display) {
            this.renderESP(new AxisAlignedBB(getCenter.x, getCenter.y, getCenter.z, getCenter.x, getCenter.y, getCenter.z).grow((getSelectedBoundingBox.minX - getSelectedBoundingBox.maxX) * 0.5 * MathHelper.clamp(mineDamage, 0.0f, 1.0f), (getSelectedBoundingBox.minY - getSelectedBoundingBox.maxY) * 0.5 * MathHelper.clamp(mineDamage, 0.0f, 1.0f), (getSelectedBoundingBox.minZ - getSelectedBoundingBox.maxZ) * 0.5 * MathHelper.clamp(mineDamage, 0.0f, 1.0f)), color, mode, alpha, width);
        }
    }
    
    private void renderESP(final AxisAlignedBB axisAlignedBB, final GSColor color, final String mode, final int alpha, final int width) {
        final GSColor fillColor = new GSColor(color, alpha);
        final GSColor outlineColor = new GSColor(color, 255);
        switch (mode) {
            case "Fill": {
                RenderUtil.drawBox(axisAlignedBB, true, 0.0, fillColor, 63);
                break;
            }
            case "Outline": {
                RenderUtil.drawBoundingBox(axisAlignedBB, (double)width, outlineColor);
                break;
            }
            default: {
                RenderUtil.drawBox(axisAlignedBB, true, 0.0, fillColor, 63);
                RenderUtil.drawBoundingBox(axisAlignedBB, (double)width, outlineColor);
                break;
            }
        }
    }
    
    private void calc() {
        if (Mine.mc.world == null || Mine.mc.player == null || Mine.mc.player.isDead) {
            return;
        }
        if (this.mineDamage >= 1.0f) {
            this.mineDamage = 1.0f;
        }
        else if (this.lastBlock != null) {
            this.mineDamage += this.getBlockStrength(Mine.mc.world.getBlockState(this.lastBlock), this.lastBlock);
        }
        if (this.doubleMineDamage >= 1.0f) {
            this.doubleMineDamage = 1.0f;
        }
        else if (this.doubleMine != null) {
            this.doubleMineDamage += this.getBlockStrength(Mine.mc.world.getBlockState(this.doubleMine), this.doubleMine);
        }
    }
    
    public ItemStack getEfficientItem(final IBlockState blockState, final BlockPos pos) {
        int n = -1;
        double n2 = 0.0;
        for (int i = 0; i < 9; ++i) {
            if (!Mine.mc.player.inventory.getStackInSlot(i).isEmpty()) {
                float getDestroySpeed = Mine.mc.player.inventory.getStackInSlot(i).getDestroySpeed(blockState);
                if (getDestroySpeed > 1.0f) {
                    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, Mine.mc.player.inventory.getStackInSlot(i)) > 0) {
                        getDestroySpeed += (float)(StrictMath.pow(EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, Mine.mc.player.inventory.getStackInSlot(i)), 2.0) + 1.0);
                    }
                    if (getDestroySpeed > n2) {
                        n2 = getDestroySpeed;
                        n = i;
                    }
                }
            }
        }
        if (n != -1) {
            return Mine.mc.player.inventory.getStackInSlot(n);
        }
        return Mine.mc.player.inventory.getStackInSlot(this.findItem(pos));
    }
    
    public float getDestroySpeed(final IBlockState blockState, final BlockPos pos) {
        float n = 1.0f;
        if (this.getEfficientItem(blockState, pos) != null && !this.getEfficientItem(blockState, pos).isEmpty()) {
            n *= this.getEfficientItem(blockState, pos).getDestroySpeed(blockState);
        }
        return n;
    }
    
    public float getDigSpeed(final IBlockState blockState, final BlockPos pos) {
        float destroySpeed = this.getDestroySpeed(blockState, pos);
        if (destroySpeed > 1.0f) {
            final ItemStack efficientItem = this.getEfficientItem(blockState, pos);
            final int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, efficientItem);
            if (getEnchantmentLevel > 0 && !efficientItem.isEmpty()) {
                destroySpeed += (float)(StrictMath.pow(getEnchantmentLevel, 2.0) + 1.0);
            }
        }
        if (Mine.mc.player.isPotionActive(MobEffects.HASTE)) {
            destroySpeed *= 1.0f + (Objects.requireNonNull(Mine.mc.player.getActivePotionEffect(MobEffects.HASTE)).getAmplifier() + 1) * 0.2f;
        }
        if (Mine.mc.player.isPotionActive(MobEffects.MINING_FATIGUE)) {
            float n = 0.0f;
            switch (Objects.requireNonNull(Mine.mc.player.getActivePotionEffect(MobEffects.MINING_FATIGUE)).getAmplifier()) {
                case 0: {
                    n = 0.3f;
                    break;
                }
                case 1: {
                    n = 0.09f;
                    break;
                }
                case 2: {
                    n = 0.0027f;
                    break;
                }
                default: {
                    n = 8.1E-4f;
                    break;
                }
            }
            destroySpeed *= n;
        }
        if (Mine.mc.player.isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier((EntityLivingBase)Mine.mc.player)) {
            destroySpeed /= 5.0f;
        }
        if (!Mine.mc.player.onGround) {
            destroySpeed /= 5.0f;
        }
        return Math.max(destroySpeed, 0.0f);
    }
    
    public float getBlockStrength(final IBlockState blockState, final BlockPos blockPos) {
        final float getBlockHardness = blockState.getBlockHardness((World)Mine.mc.world, blockPos);
        if (getBlockHardness < 0.0f) {
            return 0.0f;
        }
        return this.getDigSpeed(blockState, blockPos) / getBlockHardness / ((12000 * Minecraft.debugFPS == 0) ? 1 : Minecraft.debugFPS);
    }
    
    public int findItem(final BlockPos pos) {
        if (pos == null) {
            return Mine.mc.player.inventory.currentItem;
        }
        return findBestTool(pos, Mine.mc.world.getBlockState(pos));
    }
    
    public static int findBestTool(final BlockPos pos, final IBlockState state) {
        int result = Mine.mc.player.inventory.currentItem;
        if (state.getBlockHardness((World)Mine.mc.world, pos) > 0.0f) {
            double speed = getSpeed(state, Mine.mc.player.getHeldItemMainhand());
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = Mine.mc.player.inventory.getStackInSlot(i);
                final double stackSpeed = getSpeed(state, stack);
                if (stackSpeed > speed) {
                    speed = stackSpeed;
                    result = i;
                }
            }
        }
        return result;
    }
    
    public static double getSpeed(final IBlockState state, final ItemStack stack) {
        final double str = stack.getDestroySpeed(state);
        final int effect = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
        return Math.max(str + ((str > 1.0) ? (effect * effect + 1.0) : 0.0), 0.0);
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || Mine.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                Mine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                Mine.mc.player.inventory.currentItem = slot;
            }
            Mine.mc.playerController.updateController();
        }
    }
    
    private void switchToDoubleMine(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || Mine.mc.player.inventory.currentItem != slot)) {
            Mine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            if (this.updateController.getValue()) {
                Mine.mc.playerController.updateController();
            }
        }
    }
    
    public static void switchToBypass(final int slot) {
        if (Mine.mc.player.inventory.currentItem != slot && slot > -1 && slot < 9) {
            final int lastSlot = Mine.mc.player.inventory.currentItem;
            final int targetSlot = hotbarToInventory(slot);
            final int currentSlot = hotbarToInventory(lastSlot);
            Mine.mc.playerController.windowClick(0, targetSlot, 0, ClickType.SWAP, (EntityPlayer)Mine.mc.player);
            Mine.mc.playerController.windowClick(0, currentSlot, 0, ClickType.SWAP, (EntityPlayer)Mine.mc.player);
            Mine.mc.playerController.windowClick(0, targetSlot, 0, ClickType.SWAP, (EntityPlayer)Mine.mc.player);
            Mine.mc.playerController.updateController();
        }
    }
    
    public static int hotbarToInventory(final int slot) {
        if (slot == -2) {
            return 45;
        }
        if (slot > -1 && slot < 9) {
            return 36 + slot;
        }
        return slot;
    }
}
