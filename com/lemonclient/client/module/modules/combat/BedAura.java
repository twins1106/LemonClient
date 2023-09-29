//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.misc.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.client.module.*;
import com.lemonclient.client.module.modules.qwq.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.world.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.api.util.render.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.network.play.client.*;
import com.mojang.realmsclient.gui.*;
import com.lemonclient.api.event.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.client.manager.managers.*;

@Module.Declaration(name = "BedAura", category = Category.Combat, priority = 999)
public class BedAura extends Module
{
    ModeSetting page;
    BooleanSetting predict;
    BooleanSetting predictself;
    IntegerSetting tickPredict;
    BooleanSetting calculateYPredict;
    IntegerSetting startDecrease;
    IntegerSetting exponentStartDecrease;
    IntegerSetting decreaseY;
    IntegerSetting exponentDecreaseY;
    IntegerSetting increaseY;
    IntegerSetting exponentIncreaseY;
    BooleanSetting splitXZ;
    BooleanSetting manualOutHole;
    BooleanSetting aboveHoleManual;
    BooleanSetting stairPredict;
    IntegerSetting nStair;
    DoubleSetting speedActivationStair;
    ModeSetting targetMode;
    BooleanSetting monster;
    BooleanSetting neutral;
    BooleanSetting animal;
    BooleanSetting highVersion;
    BooleanSetting base;
    BooleanSetting basepacket;
    BooleanSetting baseswing;
    DoubleSetting toggleDmg;
    IntegerSetting basedelay;
    DoubleSetting baseminDmg;
    IntegerSetting maxY;
    BooleanSetting packetPlace;
    BooleanSetting placeswing;
    BooleanSetting breakswing;
    BooleanSetting packetswing;
    IntegerSetting startdelay;
    IntegerSetting calcdelay;
    IntegerSetting placedelay;
    IntegerSetting breakdelay;
    ModeSetting time;
    ModeSetting calctime;
    DoubleSetting Range;
    DoubleSetting yRange;
    IntegerSetting enemyRange;
    ModeSetting handmode;
    BooleanSetting autoSwitch;
    BooleanSetting silentSwitch;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting autorotate;
    BooleanSetting pause1;
    BooleanSetting refill;
    ModeSetting clickmode;
    ModeSetting refillmode;
    IntegerSetting slotS;
    BooleanSetting force;
    DoubleSetting minDmg;
    DoubleSetting maxSelfDmg;
    BooleanSetting ignore;
    BooleanSetting suicide;
    IntegerSetting safehealth;
    IntegerSetting facePlaceValue;
    IntegerSetting faceplacedamage;
    BooleanSetting forceplace;
    BooleanSetting showDamage;
    BooleanSetting selfDamage;
    ModeSetting hudDisplay;
    ColorSetting color;
    ColorSetting color2;
    IntegerSetting ufoAlpha;
    BooleanSetting outline;
    IntegerSetting width;
    BooleanSetting fix;
    BooleanSetting gradient;
    BooleanSetting outgradient;
    IntegerSetting alpha;
    BooleanSetting anime;
    DoubleSetting movingPlaceSpeed;
    BooleanSetting fade;
    IntegerSetting fadealpha;
    IntegerSetting lifeTime;
    BooleanSetting rendertest;
    PredictUtil.PredictSettings settings;
    managerClassRenderBlocks managerClassRenderBlocks;
    EntityPlayer renderEntity;
    Entity Entity;
    BlockPos render;
    BlockPos render2;
    BlockPos pos;
    BlockPos basepos;
    boolean canbaseplace;
    boolean burrowed;
    boolean shouldbaseplace;
    float Damage;
    float SelfDamage;
    String face;
    Vec3d movingPlaceNow;
    Vec3d movingPosNow;
    BlockPos lastBestPlace;
    BlockPos lastBestPos;
    Timing starttiming;
    Timing basetiming;
    Timing calctiming;
    Timing placetiming;
    Timing breaktiming;
    Timing rotatetiming;
    EnumHand hand;
    int slot;
    int oldslot;
    Vec2f rotation;
    BlockPos[] sides;
    @EventHandler
    private final Listener<OnUpdateWalkingPlayerEvent> onUpdateWalkingPlayerEventListener;
    
    public BedAura() {
        this.page = this.registerMode("Page", (List)Arrays.asList("General", "Target", "Base", "Delay", "Calc", "Switch", "Render"), "General");
        this.predict = this.registerBoolean("Predict", true, () -> ((String)this.page.getValue()).equals("Target"));
        this.predictself = this.registerBoolean("Predict Self", true, () -> ((String)this.page.getValue()).equals("Target"));
        this.tickPredict = this.registerInteger("Tick Predict", 8, 0, 30, () -> ((String)this.page.getValue()).equals("Target"));
        this.calculateYPredict = this.registerBoolean("Calculate Y Predict", true, () -> ((String)this.page.getValue()).equals("Target"));
        this.startDecrease = this.registerInteger("Start Decrease", 39, 0, 200, () -> (boolean)this.calculateYPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.exponentStartDecrease = this.registerInteger("Exponent Start", 2, 1, 5, () -> (boolean)this.calculateYPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.decreaseY = this.registerInteger("Decrease Y", 2, 1, 5, () -> (boolean)this.calculateYPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.exponentDecreaseY = this.registerInteger("Exponent Decrease Y", 1, 1, 3, () -> (boolean)this.calculateYPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.increaseY = this.registerInteger("Increase Y", 3, 1, 5, () -> (boolean)this.calculateYPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.exponentIncreaseY = this.registerInteger("Exponent Increase Y", 2, 1, 3, () -> (boolean)this.calculateYPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.splitXZ = this.registerBoolean("Split XZ", true, () -> ((String)this.page.getValue()).equals("Target"));
        this.manualOutHole = this.registerBoolean("Manual Out Hole", false, () -> ((String)this.page.getValue()).equals("Target"));
        this.aboveHoleManual = this.registerBoolean("Above Hole Manual", false, () -> (boolean)this.manualOutHole.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.stairPredict = this.registerBoolean("Stair Predict", false, () -> ((String)this.page.getValue()).equals("Target"));
        this.nStair = this.registerInteger("N Stair", 2, 1, 4, () -> (boolean)this.stairPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.speedActivationStair = this.registerDouble("Speed Activation Stair", 0.3, 0.0, 1.0, () -> (boolean)this.stairPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.targetMode = this.registerMode("Target", (List)Arrays.asList("Nearest", "Damage", "Health"), "Nearest", () -> ((String)this.page.getValue()).equals("General"));
        this.monster = this.registerBoolean("Monsters", true, () -> ((String)this.page.getValue()).equals("General"));
        this.neutral = this.registerBoolean("Neutrals", true, () -> ((String)this.page.getValue()).equals("General"));
        this.animal = this.registerBoolean("Animals", true, () -> ((String)this.page.getValue()).equals("General"));
        this.highVersion = this.registerBoolean("1.13", true, () -> ((String)this.page.getValue()).equals("Base"));
        this.base = this.registerBoolean("Place Base", true, () -> ((String)this.page.getValue()).equals("Base") && !(boolean)this.highVersion.getValue());
        this.basepacket = this.registerBoolean("Packet Base Place", true, () -> ((String)this.page.getValue()).equals("Base") && (boolean)this.base.getValue() && !(boolean)this.highVersion.getValue());
        this.baseswing = this.registerBoolean("Base Swing", true, () -> ((String)this.page.getValue()).equals("Base") && (boolean)this.base.getValue() && !(boolean)this.highVersion.getValue());
        this.toggleDmg = this.registerDouble("Toggle Damage", 8.0, 0.0, 36.0, () -> ((String)this.page.getValue()).equals("Base") && (boolean)this.base.getValue() && !(boolean)this.highVersion.getValue());
        this.basedelay = this.registerInteger("Base Delay", 0, 0, 1000, () -> ((String)this.page.getValue()).equals("Base") && (boolean)this.base.getValue() && !(boolean)this.highVersion.getValue());
        this.baseminDmg = this.registerDouble("Base MinDmg", 8.0, 0.0, 36.0, () -> ((String)this.page.getValue()).equals("Base") && (boolean)this.base.getValue() && !(boolean)this.highVersion.getValue());
        this.maxY = this.registerInteger("Max Y", 1, 0, 3, () -> ((String)this.page.getValue()).equals("Base") && (boolean)this.base.getValue() && !(boolean)this.highVersion.getValue());
        this.packetPlace = this.registerBoolean("Packet Place", true, () -> ((String)this.page.getValue()).equals("General"));
        this.placeswing = this.registerBoolean("Place Swing", true, () -> ((String)this.page.getValue()).equals("General"));
        this.breakswing = this.registerBoolean("Break Swing", true, () -> ((String)this.page.getValue()).equals("General"));
        this.packetswing = this.registerBoolean("Packet Swing", true, () -> ((String)this.page.getValue()).equals("General"));
        this.startdelay = this.registerInteger("Start Delay", 0, 0, 1000, () -> ((String)this.page.getValue()).equals("Delay"));
        this.calcdelay = this.registerInteger("Calc Delay", 0, 0, 1000, () -> ((String)this.page.getValue()).equals("Delay"));
        this.placedelay = this.registerInteger("Place Delay", 0, 0, 1000, () -> ((String)this.page.getValue()).equals("Delay"));
        this.breakdelay = this.registerInteger("Break Delay", 0, 0, 1000, () -> ((String)this.page.getValue()).equals("Delay"));
        this.time = this.registerMode("Time Mode", (List)Arrays.asList("Tick", "onUpdate", "Both", "Fast"), "Tick", () -> ((String)this.page.getValue()).equals("Delay"));
        this.calctime = this.registerMode("Calc Time Mode", (List)Arrays.asList("Tick", "onUpdate", "Both", "Fast"), "Tick", () -> ((String)this.page.getValue()).equals("Delay"));
        this.Range = this.registerDouble("Place Range", 5.0, 0.0, 10.0, () -> ((String)this.page.getValue()).equals("Calc"));
        this.yRange = this.registerDouble("Y Range", 2.5, 0.0, 10.0, () -> ((String)this.page.getValue()).equals("Calc"));
        this.enemyRange = this.registerInteger("Enemy Range", 10, 0, 16, () -> ((String)this.page.getValue()).equals("Calc"));
        this.handmode = this.registerMode("Hand", (List)Arrays.asList("Main", "Off", "Auto"), "Auto", () -> ((String)this.page.getValue()).equals("Calc"));
        this.autoSwitch = this.registerBoolean("Auto Switch", true, () -> ((String)this.page.getValue()).equals("Switch"));
        this.silentSwitch = this.registerBoolean("Switch Back", true, () -> ((String)this.page.getValue()).equals("Switch"));
        this.packetSwitch = this.registerBoolean("Packet Switch", true, () -> ((String)this.page.getValue()).equals("Switch"));
        this.check = this.registerBoolean("Switch Check", true, () -> ((String)this.page.getValue()).equals("Switch"));
        this.autorotate = this.registerBoolean("Auto Rotate", true, () -> ((String)this.page.getValue()).equals("Calc"));
        this.pause1 = this.registerBoolean("Pause When Burrow", true, () -> ((String)this.page.getValue()).equals("Calc"));
        this.refill = this.registerBoolean("Refill Beds", true, () -> ((String)this.page.getValue()).equals("Switch"));
        this.clickmode = this.registerMode("Click Mode", (List)Arrays.asList("Quick", "Swap", "Pickup"), "Quick", () -> ((String)this.page.getValue()).equals("Switch"));
        this.refillmode = this.registerMode("Refill Mode", (List)Arrays.asList("All", "Only"), "All", () -> ((String)this.page.getValue()).equals("Switch"));
        this.slotS = this.registerInteger("Slot", 1, 1, 9, () -> ((String)this.page.getValue()).equals("Switch"));
        this.force = this.registerBoolean("Force Refill", false, () -> ((String)this.page.getValue()).equals("Switch"));
        this.minDmg = this.registerDouble("Min Damage", 8.0, 0.0, 36.0, () -> ((String)this.page.getValue()).equals("Calc"));
        this.maxSelfDmg = this.registerDouble("Max Self Dmg", 10.0, 1.0, 36.0, () -> ((String)this.page.getValue()).equals("Calc"));
        this.ignore = this.registerBoolean("Ignore Self Dmg", false, () -> ((String)this.page.getValue()).equals("Calc"));
        this.suicide = this.registerBoolean("Anti Suicide", true, () -> ((String)this.page.getValue()).equals("Calc"));
        this.safehealth = this.registerInteger("Safe Health", 4, 0, 36, () -> ((String)this.page.getValue()).equals("Calc"));
        this.facePlaceValue = this.registerInteger("FacePlace HP", 8, 0, 36, () -> ((String)this.page.getValue()).equals("Calc"));
        this.faceplacedamage = this.registerInteger("FP Min Damage", 1, 0, 36, () -> ((String)this.page.getValue()).equals("Calc"));
        this.forceplace = this.registerBoolean("Force Place", false, () -> ((String)this.page.getValue()).equals("Calc"));
        this.showDamage = this.registerBoolean("Render Dmg", true, () -> ((String)this.page.getValue()).equals("Render"));
        this.selfDamage = this.registerBoolean("Self Dmg", true, () -> ((String)this.page.getValue()).equals("Render"));
        this.hudDisplay = this.registerMode("HUD", (List)Arrays.asList("Target", "Damage", "Both", "None"), "None", () -> ((String)this.page.getValue()).equals("Render"));
        this.color = this.registerColor("Hand Color", new GSColor(255, 0, 0, 50), () -> ((String)this.page.getValue()).equals("Render"));
        this.color2 = this.registerColor("Base Color", new GSColor(0, 255, 0, 50), () -> ((String)this.page.getValue()).equals("Render"));
        this.ufoAlpha = this.registerInteger("Alpha", 120, 0, 255, () -> ((String)this.page.getValue()).equals("Render"));
        this.outline = this.registerBoolean("Outline", true, () -> ((String)this.page.getValue()).equals("Render"));
        this.width = this.registerInteger("Width", 1, 1, 10, () -> ((String)this.page.getValue()).equals("Render"));
        this.fix = this.registerBoolean("Render Fix", true, () -> ((String)this.page.getValue()).equals("Render"));
        this.gradient = this.registerBoolean("Gradient", true, () -> ((String)this.page.getValue()).equals("Render"));
        this.outgradient = this.registerBoolean("Outline Gradient", true, () -> ((String)this.page.getValue()).equals("Render"));
        this.alpha = this.registerInteger("Gradient Alpha", 120, 0, 255, () -> ((String)this.page.getValue()).equals("Render"));
        this.anime = this.registerBoolean("Animation", true, () -> ((String)this.page.getValue()).equals("Render"));
        this.movingPlaceSpeed = this.registerDouble("Moving Speed", 0.1, 0.01, 0.5, () -> ((String)this.page.getValue()).equals("Render"));
        this.fade = this.registerBoolean("Fade", true, () -> ((String)this.page.getValue()).equals("Render"));
        this.fadealpha = this.registerInteger("Fade Alpha", 50, 0, 255, () -> ((String)this.page.getValue()).equals("Render"));
        this.lifeTime = this.registerInteger("Fade Time", 3000, 0, 5000, () -> ((String)this.page.getValue()).equals("Render"));
        this.rendertest = this.registerBoolean("Render Test", false, () -> ((String)this.page.getValue()).equals("Render"));
        this.managerClassRenderBlocks = new managerClassRenderBlocks();
        this.movingPlaceNow = new Vec3d(-1.0, -1.0, -1.0);
        this.movingPosNow = new Vec3d(-1.0, -1.0, -1.0);
        this.lastBestPlace = null;
        this.lastBestPos = null;
        this.starttiming = new Timing();
        this.basetiming = new Timing();
        this.calctiming = new Timing();
        this.placetiming = new Timing();
        this.breaktiming = new Timing();
        this.rotatetiming = new Timing();
        this.sides = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) };
        this.onUpdateWalkingPlayerEventListener = (Listener<OnUpdateWalkingPlayerEvent>)new Listener(event -> {
            if (event.getPhase() != Phase.PRE || this.rotation == null) {
                return;
            }
            final PlayerPacket packet = new PlayerPacket((Module)this, this.rotation);
            PlayerPacketManager.INSTANCE.addPacket(packet);
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        if (BedAura.mc.player == null || BedAura.mc.world == null || BedAura.mc.player.isDead || this.inNether()) {
            return;
        }
        if (((String)this.time.getValue()).equals("onUpdate") || ((String)this.time.getValue()).equals("Both")) {
            this.doCheck();
        }
        if (((String)this.calctime.getValue()).equals("onUpdate") || ((String)this.calctime.getValue()).equals("Both")) {
            this.calc();
        }
        if (((String)this.time.getValue()).equals("onUpdate") || ((String)this.time.getValue()).equals("Both")) {
            this.bedaura();
        }
    }
    
    public void onTick() {
        if (BedAura.mc.player == null || BedAura.mc.world == null || BedAura.mc.player.isDead || this.inNether()) {
            return;
        }
        if (((String)this.time.getValue()).equals("Tick") || ((String)this.time.getValue()).equals("Both")) {
            this.doCheck();
        }
        if (((String)this.calctime.getValue()).equals("Tick") || ((String)this.calctime.getValue()).equals("Both")) {
            this.calc();
        }
        if (((String)this.time.getValue()).equals("Tick") || ((String)this.time.getValue()).equals("Both")) {
            this.bedaura();
        }
    }
    
    public void fast() {
        if (BedAura.mc.player == null || BedAura.mc.world == null || BedAura.mc.player.isDead || this.inNether()) {
            return;
        }
        if (((String)this.time.getValue()).equals("Fast")) {
            this.doCheck();
        }
        if (((String)this.calctime.getValue()).equals("Fast")) {
            this.calc();
        }
        if (((String)this.time.getValue()).equals("Fast")) {
            this.bedaura();
        }
    }
    
    private void doCheck() {
        this.managerClassRenderBlocks.update((int)this.lifeTime.getValue());
        if (!this.starttiming.passedMs((long)(int)this.startdelay.getValue())) {
            return;
        }
        this.check();
        if ((boolean)this.base.getValue() && this.basetiming.passedMs((long)(int)this.basedelay.getValue())) {
            this.canbaseplace = true;
            this.basetiming.reset();
        }
    }
    
    private void check() {
        this.burrowed = false;
        final BlockPos originalPos = new BlockPos(BedAura.mc.player.posX, BedAura.mc.player.posY, BedAura.mc.player.posZ);
        if (BedAura.mc.world.getBlockState(originalPos).getBlock() == Blocks.BEDROCK || BedAura.mc.world.getBlockState(originalPos).getBlock() == Blocks.OBSIDIAN || BedAura.mc.world.getBlockState(originalPos).getBlock() == Blocks.ENDER_CHEST) {
            this.burrowed = true;
        }
    }
    
    private boolean inNether() {
        return BedAura.mc.player.dimension == 0;
    }
    
    private void calc() {
        if (this.calctiming.passedMs((long)(int)this.calcdelay.getValue())) {
            this.settings = new PredictUtil.PredictSettings((int)this.tickPredict.getValue(), (boolean)this.calculateYPredict.getValue(), (int)this.startDecrease.getValue(), (int)this.exponentStartDecrease.getValue(), (int)this.decreaseY.getValue(), (int)this.exponentDecreaseY.getValue(), (int)this.increaseY.getValue(), (int)this.exponentIncreaseY.getValue(), (boolean)this.splitXZ.getValue(), 0, false, false, (boolean)this.manualOutHole.getValue(), (boolean)this.aboveHoleManual.getValue(), (boolean)this.stairPredict.getValue(), (int)this.nStair.getValue(), (double)this.speedActivationStair.getValue());
            boolean player = true;
            final BlockPos blockPos = null;
            this.render2 = blockPos;
            this.render = blockPos;
            final float n = 0.0f;
            this.SelfDamage = n;
            this.Damage = n;
            this.renderEntity = this.getTarget();
            if (this.renderEntity != null && ModuleManager.isModuleEnabled("AutoEz")) {
                AutoEz.INSTANCE.addTargetedPlayer(this.renderEntity.getName());
            }
            this.Entity = null;
            this.rotation = null;
            if (this.renderEntity == null) {
                final List<Entity> entityList = new ArrayList<Entity>();
                for (final Entity entity : BedAura.mc.world.loadedEntityList) {
                    if (BedAura.mc.player.getDistance(entity) <= (int)this.enemyRange.getValue()) {
                        if (entity.isDead) {
                            continue;
                        }
                        if ((boolean)this.monster.getValue() && EntityUtil.isMobAggressive(entity)) {
                            entityList.add(entity);
                        }
                        if ((boolean)this.neutral.getValue() && EntityUtil.isNeutralMob(entity)) {
                            entityList.add(entity);
                        }
                        if (!(boolean)this.animal.getValue() || !EntityUtil.isPassive(entity)) {
                            continue;
                        }
                        entityList.add(entity);
                    }
                }
                this.Entity = getNearestEntity(entityList);
                if (this.Entity == null) {
                    return;
                }
                player = false;
            }
            boolean offhand = false;
            if (!((String)this.handmode.getValue()).equals("Main") && BedAura.mc.player.getHeldItemOffhand().getItem() == Items.BED) {
                offhand = true;
            }
            if (!offhand && !((String)this.handmode.getValue()).equals("Off")) {
                this.oldslot = BedAura.mc.player.inventory.currentItem;
                if (this.refill.getValue()) {
                    this.refill_bed();
                }
                this.slot = BurrowUtil.findHotbarBlock((Class)ItemBed.class);
                if (this.slot == -1) {
                    return;
                }
            }
            this.hand = (offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            BlockPos bedpos;
            if (player) {
                if (((String)this.targetMode.getValue()).equals("Damage")) {
                    bedpos = this.pos;
                }
                else {
                    bedpos = this.calculateBestPlacement(this.renderEntity);
                }
            }
            else {
                bedpos = this.calculatePlacement(this.Entity);
            }
            if (bedpos == null) {
                return;
            }
            if ((boolean)this.base.getValue() && this.shouldbaseplace) {
                this.canbaseplace = false;
                final int obsiSlot = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
                final int oldSlot = BedAura.mc.player.inventory.currentItem;
                this.switchTo(obsiSlot);
                BurrowUtil.placeBlock(this.basepos, EnumHand.MAIN_HAND, false, (boolean)this.basepacket.getValue(), false, (boolean)this.baseswing.getValue());
                this.switchTo(oldSlot);
            }
            final BlockPos pos = bedpos;
            if (player) {
                this.Damage = DamageUtil.calcDamage(bedpos.getX() + 0.5, bedpos.getY() + 1.5626, bedpos.getZ() + 0.5626, (Entity)this.renderEntity);
            }
            else {
                this.Damage = DamageUtil.calcDamage(bedpos.getX() + 0.5, bedpos.getY() + 1.5626, bedpos.getZ() + 0.5626, this.Entity);
            }
            this.SelfDamage = DamageUtil.calcDamage(bedpos.getX() + 0.5, bedpos.getY() + 1.5626, bedpos.getZ() + 0.5626, (Entity)BedAura.mc.player);
            if (this.inRange(bedpos.north().up()) && BedAura.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH)) {
                this.face = "SOUTH";
                bedpos = new BlockPos(bedpos.x, bedpos.y + 1, bedpos.z - 1);
            }
            else if (this.inRange(bedpos.east().up()) && BedAura.mc.player.getHorizontalFacing().equals((Object)EnumFacing.WEST)) {
                this.face = "WEST";
                bedpos = new BlockPos(bedpos.x + 1, bedpos.y + 1, bedpos.z);
            }
            else if (this.inRange(bedpos.south().up()) && BedAura.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH)) {
                this.face = "NORTH";
                bedpos = new BlockPos(bedpos.x, bedpos.y + 1, bedpos.z + 1);
            }
            else if (this.inRange(bedpos.west().up())) {
                this.face = "EAST";
                bedpos = new BlockPos(bedpos.x - 1, bedpos.y + 1, bedpos.z);
            }
            if ((BedAura.mc.world.isAirBlock(bedpos.down()) || (!BedAura.mc.world.isAirBlock(bedpos) && BedAura.mc.world.getBlockState(bedpos).getBlock() != Blocks.BED)) && (boolean)this.autorotate.getValue() && (!(boolean)this.pause1.getValue() || !this.burrowed)) {
                bedpos = pos;
                if (this.inRange(bedpos.east().up()) && this.block(bedpos.east()) && (BedAura.mc.world.isAirBlock(bedpos.east().up()) || BedAura.mc.world.getBlockState(bedpos.east().up()).getBlock() == Blocks.BED)) {
                    this.face = "WEST";
                    this.rotation = new Vec2f(90.0f, BedAura.mc.player.rotationPitch);
                    bedpos = new BlockPos(bedpos.x + 1, bedpos.y + 1, bedpos.z);
                }
                else if (this.inRange(bedpos.north().up()) && this.block(bedpos.north()) && (BedAura.mc.world.isAirBlock(bedpos.north().up()) || BedAura.mc.world.getBlockState(bedpos.north().up()).getBlock() == Blocks.BED)) {
                    this.face = "SOUTH";
                    this.rotation = new Vec2f(0.0f, BedAura.mc.player.rotationPitch);
                    bedpos = new BlockPos(bedpos.x, bedpos.y + 1, bedpos.z - 1);
                }
                else if (this.inRange(bedpos.west().up()) && this.block(bedpos.west()) && (BedAura.mc.world.isAirBlock(bedpos.west().up()) || BedAura.mc.world.getBlockState(bedpos.west().up()).getBlock() == Blocks.BED)) {
                    this.face = "EAST";
                    this.rotation = new Vec2f(-90.0f, BedAura.mc.player.rotationPitch);
                    bedpos = new BlockPos(bedpos.x - 1, bedpos.y + 1, bedpos.z);
                }
                else {
                    if (!this.inRange(bedpos.south().up()) || !this.block(bedpos.south()) || (!BedAura.mc.world.isAirBlock(bedpos.south().up()) && BedAura.mc.world.getBlockState(bedpos.south().up()).getBlock() != Blocks.BED)) {
                        return;
                    }
                    this.face = "NORTH";
                    this.rotation = new Vec2f(180.0f, BedAura.mc.player.rotationPitch);
                    bedpos = new BlockPos(bedpos.x, bedpos.y + 1, bedpos.z + 1);
                }
            }
            this.render2 = bedpos;
            this.render = pos.up();
            this.calctiming.reset();
        }
    }
    
    private void bedaura() {
        if (this.render == null || this.render2 == null) {
            return;
        }
        if (BedAura.mc.world.getBlockState(this.render).getBlock() == Blocks.BED || BedAura.mc.world.getBlockState(this.render2).getBlock() == Blocks.BED) {
            this.Break();
        }
        else {
            this.place();
            this.Break();
        }
    }
    
    private void Break() {
        if (this.breaktiming.passedMs((long)(int)this.breakdelay.getValue())) {
            EnumFacing side = BurrowUtil.getFirstFacing(this.render2);
            if (side == null) {
                side = EnumFacing.UP;
            }
            if (BedAura.mc.player.isSneaking()) {
                BedAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedAura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            final Vec3d hitfacing = this.getHitVecOffset(side);
            BedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.render2, side, this.hand, (float)hitfacing.x, (float)hitfacing.y, (float)hitfacing.z));
            if (this.breakswing.getValue()) {
                this.swing(this.hand);
            }
            this.breaktiming.reset();
        }
    }
    
    private Vec3d getHitVecOffset(final EnumFacing face) {
        final Vec3i vec = face.getDirectionVec();
        return new Vec3d((double)(vec.x * 0.5f + 0.5f), (double)(vec.y * 0.5f + 0.5f), (double)(vec.z * 0.5f + 0.5f));
    }
    
    private void place() {
        if (this.placetiming.passedMs((long)(int)this.placedelay.getValue())) {
            if ((boolean)this.rendertest.getValue() || this.render2 == null || !BedAura.mc.world.isAirBlock(this.render2)) {
                return;
            }
            final BlockPos neighbour = this.render2.offset(EnumFacing.DOWN);
            final EnumFacing opposite = EnumFacing.DOWN.getOpposite();
            final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
            boolean sneak = false;
            if (BlockUtil.blackList.contains(BedAura.mc.world.getBlockState(neighbour).getBlock()) && !BedAura.mc.player.isSneaking()) {
                BedAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedAura.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                sneak = true;
            }
            if (this.autoSwitch.getValue()) {
                this.switchTo(this.slot);
            }
            BurrowUtil.rightClickBlock(neighbour, hitVec, this.hand, opposite, (boolean)this.packetPlace.getValue());
            if (this.placeswing.getValue()) {
                this.swing(this.hand);
            }
            if (this.silentSwitch.getValue()) {
                this.switchTo(this.oldslot);
            }
            if (sneak) {
                BedAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BedAura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.placetiming.reset();
        }
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || BedAura.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                BedAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                BedAura.mc.player.inventory.currentItem = slot;
            }
            BedAura.mc.playerController.updateController();
        }
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : BedAura.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    private List<EntityPlayer> getTargetList(final double range) {
        return (List<EntityPlayer>)BedAura.mc.world.playerEntities.stream().filter(p -> BedAura.mc.player.getDistance(p) <= range).filter(p -> BedAura.mc.player.entityId != p.entityId).filter(p -> !EntityUtil.basicChecksEntity(p)).collect(Collectors.toList());
    }
    
    public static EntityPlayer getNearestPlayer(final List<EntityPlayer> list) {
        return list.stream().min(Comparator.comparing(p -> BedAura.mc.player.getDistance(p))).orElse(null);
    }
    
    public static Entity getNearestEntity(final List<Entity> list) {
        return list.stream().min(Comparator.comparing(p -> BedAura.mc.player.getDistance(p))).orElse(null);
    }
    
    private EntityPlayer getTarget() {
        final String s = (String)this.targetMode.getValue();
        switch (s) {
            case "Nearest": {
                return PlayerUtil.getNearestPlayer((double)(int)this.enemyRange.getValue());
            }
            case "Damage": {
                final HashMap<EntityPlayer, BlockPos> list = new HashMap<EntityPlayer, BlockPos>();
                BlockPos bestPos = null;
                BlockPos bestBase = null;
                boolean needBase = false;
                EntityPlayer bestTarget = null;
                float highestDamage = 0.0f;
                for (final EntityPlayer player : this.getTargetList((int)this.enemyRange.getValue())) {
                    final double targetHp = player.getHealth() + player.getAbsorptionAmount();
                    if (!player.isDead) {
                        if (targetHp <= 0.0) {
                            continue;
                        }
                        final EntityPlayer target = this.predict.getValue() ? PredictUtil.predictPlayer(player, this.settings) : player;
                        final EntityPlayer self = (EntityPlayer)(((boolean)this.predictself.getValue()) ? PredictUtil.predictPlayer((EntityPlayer)BedAura.mc.player, this.settings) : BedAura.mc.player);
                        final double Hp = self.getHealth() + self.getAbsorptionAmount();
                        BlockPos best = null;
                        float bestDamage = 0.0f;
                        BlockPos posBase = null;
                        boolean shouldBase = false;
                        for (final BlockPos crystal : this.findBlocksExcluding()) {
                            boolean can = this.canbaseplace;
                            if (can && bestDamage >= (double)this.toggleDmg.getValue()) {
                                can = false;
                            }
                            final boolean canplace = this.canPlaceBed(crystal);
                            if (!canplace) {
                                if (!(boolean)this.base.getValue() || !can) {
                                    continue;
                                }
                                if (crystal.getY() >= (int)target.posY + (int)this.maxY.getValue()) {
                                    continue;
                                }
                            }
                            else {
                                can = false;
                            }
                            final float currentDamage = DamageUtil.calcDamage(crystal.getX() + 0.5, crystal.getY() + 1.5626, crystal.getZ() + 0.5, (Entity)target);
                            if (currentDamage <= bestDamage) {
                                continue;
                            }
                            if (targetHp > (int)this.facePlaceValue.getValue()) {
                                if (currentDamage < (double)this.minDmg.getValue()) {
                                    continue;
                                }
                            }
                            else if (currentDamage < (int)this.faceplacedamage.getValue()) {
                                continue;
                            }
                            if (currentDamage < (double)this.baseminDmg.getValue()) {
                                can = false;
                            }
                            final float SelfDamage = DamageUtil.calcDamage(crystal.getX() + 0.5, crystal.getY() + 1.5626, crystal.getZ() + 0.5, (Entity)self);
                            if (!self.isCreative()) {
                                if (SelfDamage > (double)this.maxSelfDmg.getValue()) {
                                    if (currentDamage >= targetHp) {
                                        if (!(boolean)this.forceplace.getValue()) {
                                            continue;
                                        }
                                    }
                                    else if (!(boolean)this.ignore.getValue()) {
                                        continue;
                                    }
                                }
                                if (!can && (boolean)this.suicide.getValue() && SelfDamage >= Hp) {
                                    continue;
                                }
                            }
                            if (can) {
                                if (BurrowUtil.findHotbarBlock((Class)BlockObsidian.class) == -1) {
                                    continue;
                                }
                                posBase = this.getBestBasePos(crystal);
                                if (posBase == null) {
                                    continue;
                                }
                                shouldBase = true;
                                bestDamage = currentDamage;
                                best = crystal;
                            }
                            else {
                                posBase = null;
                                shouldBase = false;
                            }
                            if (!canplace) {
                                continue;
                            }
                            bestDamage = currentDamage;
                            best = crystal;
                        }
                        if ((int)bestDamage == (int)highestDamage) {
                            list.put(target, best);
                        }
                        else {
                            if (bestDamage <= highestDamage) {
                                continue;
                            }
                            highestDamage = bestDamage;
                            bestTarget = target;
                            bestPos = best;
                            list.clear();
                            bestBase = posBase;
                            needBase = shouldBase;
                        }
                    }
                }
                if (list.isEmpty()) {
                    this.pos = bestPos;
                    this.basepos = bestBase;
                    this.shouldbaseplace = needBase;
                }
                else {
                    double distance = 0.0;
                    EntityPlayer player2 = null;
                    BlockPos blockPos = null;
                    for (final EntityPlayer entityPlayer : list.keySet()) {
                        final double range = BedAura.mc.player.getDistanceSq((BlockPos)list.get(entityPlayer));
                        if (player2 == null) {
                            player2 = entityPlayer;
                            blockPos = list.get(entityPlayer);
                            distance = range;
                        }
                        else {
                            if (distance <= range) {
                                continue;
                            }
                            player2 = entityPlayer;
                            blockPos = list.get(entityPlayer);
                            distance = range;
                        }
                    }
                    bestTarget = player2;
                    this.pos = blockPos;
                }
                return bestTarget;
            }
            case "Health": {
                final List<EntityPlayer> targetList = new ArrayList<EntityPlayer>();
                double health = 37.0;
                EntityPlayer bestTarget2 = null;
                for (final EntityPlayer player3 : this.getTargetList((int)this.enemyRange.getValue())) {
                    if (bestTarget2 == null) {
                        bestTarget2 = player3;
                        health = player3.getHealth() + player3.getAbsorptionAmount();
                    }
                    else if ((int)health == (int)(player3.getHealth() + player3.getAbsorptionAmount())) {
                        targetList.add(player3);
                    }
                    else {
                        if (health <= player3.getHealth() + player3.getAbsorptionAmount()) {
                            continue;
                        }
                        targetList.clear();
                        bestTarget2 = player3;
                        health = player3.getHealth() + player3.getAbsorptionAmount();
                    }
                }
                if (targetList.isEmpty()) {
                    return bestTarget2;
                }
                return getNearestPlayer(targetList);
            }
            default: {
                return null;
            }
        }
    }
    
    private boolean block(final BlockPos pos) {
        return BedAura.mc.world.getBlockState(pos).getBlock() != Blocks.BED && BedAura.mc.world.getBlockState(pos).getBlock() != Blocks.TRIPWIRE && (BedAura.mc.world.getBlockState(pos.up()).getBlock() == Blocks.BED || (BedAura.mc.world.isAirBlock(pos.up()) && ((boolean)this.highVersion.getValue() || BedAura.mc.world.getBlockState(pos).isSideSolid((IBlockAccess)BedAura.mc.world, pos, EnumFacing.UP))));
    }
    
    private boolean canPlaceBed(final BlockPos blockPos) {
        if (!this.block(blockPos)) {
            return false;
        }
        if ((boolean)this.autorotate.getValue() && (!(boolean)this.pause1.getValue() || !this.burrowed)) {
            return (this.inRange(blockPos.east().up()) && this.block(blockPos.east()) && (BedAura.mc.world.isAirBlock(blockPos.east().up()) || BedAura.mc.world.getBlockState(blockPos.east().up()).getBlock() == Blocks.BED)) || (this.inRange(blockPos.north().up()) && this.block(blockPos.north()) && (BedAura.mc.world.isAirBlock(blockPos.north().up()) || BedAura.mc.world.getBlockState(blockPos.north().up()).getBlock() == Blocks.BED)) || (this.inRange(blockPos.west().up()) && this.block(blockPos.west()) && (BedAura.mc.world.isAirBlock(blockPos.west().up()) || BedAura.mc.world.getBlockState(blockPos.west().up()).getBlock() == Blocks.BED)) || (this.inRange(blockPos.south().up()) && this.block(blockPos.south()) && BedAura.mc.world.isAirBlock(blockPos.south().up())) || BedAura.mc.world.getBlockState(blockPos.south().up()).getBlock() == Blocks.BED;
        }
        BlockPos pos;
        if (BedAura.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH)) {
            pos = new BlockPos(blockPos.x, blockPos.y, blockPos.z - 1);
        }
        else if (BedAura.mc.player.getHorizontalFacing().equals((Object)EnumFacing.WEST)) {
            pos = new BlockPos(blockPos.x + 1, blockPos.y, blockPos.z);
        }
        else if (BedAura.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH)) {
            pos = new BlockPos(blockPos.x, blockPos.y, blockPos.z + 1);
        }
        else {
            pos = new BlockPos(blockPos.x - 1, blockPos.y, blockPos.z);
        }
        return this.block(pos) && (BedAura.mc.world.isAirBlock(pos.up()) || BedAura.mc.world.getBlockState(pos.up()).getBlock() == Blocks.BED) && this.inRange(pos.up());
    }
    
    private boolean canPlaceBedWithoutBase(final BlockPos blockPos) {
        if (!this.block(blockPos)) {
            return false;
        }
        if ((boolean)this.autorotate.getValue() && (!(boolean)this.pause1.getValue() || !this.burrowed)) {
            return (this.inRange(blockPos.east().up()) && (this.block(blockPos.east()) || BedAura.mc.world.isAirBlock(blockPos.east().up()))) || (this.inRange(blockPos.north().up()) && (this.block(blockPos.north()) || BedAura.mc.world.isAirBlock(blockPos.north().up()))) || (this.inRange(blockPos.west().up()) && (this.block(blockPos.west()) || BedAura.mc.world.isAirBlock(blockPos.west().up()))) || (this.inRange(blockPos.south().up()) && (this.block(blockPos.south()) || BedAura.mc.world.isAirBlock(blockPos.south().up())));
        }
        BlockPos pos;
        if (this.inRange(blockPos.north().up()) && BedAura.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH)) {
            pos = new BlockPos(blockPos.x, blockPos.y, blockPos.z - 1);
        }
        else if (this.inRange(blockPos.east().up()) && BedAura.mc.player.getHorizontalFacing().equals((Object)EnumFacing.WEST)) {
            pos = new BlockPos(blockPos.x + 1, blockPos.y, blockPos.z);
        }
        else if (this.inRange(blockPos.south().up()) && BedAura.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH)) {
            pos = new BlockPos(blockPos.x, blockPos.y, blockPos.z + 1);
        }
        else {
            pos = new BlockPos(blockPos.x - 1, blockPos.y, blockPos.z);
        }
        return this.inRange(pos.up()) && (this.block(pos) || BedAura.mc.world.isAirBlock(pos.up()));
    }
    
    private boolean canPlace(final BlockPos basePos, final BlockPos headPos) {
        BlockPos pos;
        if (BedAura.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH)) {
            pos = new BlockPos(headPos.x, headPos.y, headPos.z - 1);
        }
        else if (BedAura.mc.player.getHorizontalFacing().equals((Object)EnumFacing.WEST)) {
            pos = new BlockPos(headPos.x + 1, headPos.y, headPos.z);
        }
        else if (BedAura.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH)) {
            pos = new BlockPos(headPos.x, headPos.y, headPos.z + 1);
        }
        else {
            pos = new BlockPos(headPos.x - 1, headPos.y, headPos.z);
        }
        return this.inRange(basePos.up()) && this.isPos2(basePos, pos) && (this.block(pos) || BedAura.mc.world.isAirBlock(pos.up()));
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
    
    private BlockPos getBestBasePos(final BlockPos pos) {
        BlockPos bestPos = null;
        double bestRange = 1000.0;
        for (final BlockPos side : this.sides) {
            final BlockPos base = pos.add((Vec3i)side);
            if (this.inRange(base)) {
                if ((BedAura.mc.world.isAirBlock(base.up()) || BedAura.mc.world.getBlockState(base.up()).getBlock() == Blocks.BED) && this.inRange(base.add(0, 1, 0)) && !this.intersectsWithEntity(base)) {
                    if (this.canPlace(base, pos)) {
                        return base;
                    }
                    if (bestPos == null) {
                        bestRange = BedAura.mc.player.getDistanceSq(base);
                        bestPos = base;
                    }
                    else if (bestRange > BedAura.mc.player.getDistanceSq(base)) {
                        bestRange = BedAura.mc.player.getDistanceSq(base);
                        bestPos = base;
                    }
                }
            }
        }
        return bestPos;
    }
    
    private boolean inRange(final BlockPos pos) {
        final double y = pos.y - PlayerUtil.getEyesPos().y;
        return BedAura.mc.player.getDistanceSq(pos) <= (double)this.Range.getValue() * (double)this.Range.getValue() && y * y <= (double)this.yRange.getValue() * (double)this.yRange.getValue();
    }
    
    private List<BlockPos> findBlocksExcluding() {
        return (List<BlockPos>)EntityUtil.getSphere(PlayerUtil.getEyesPos(), Double.valueOf((double)this.Range.getValue() + 1.0), (Double)this.yRange.getValue(), false, false, 0).stream().filter(this::canPlaceBedWithoutBase).collect(Collectors.toList());
    }
    
    private List<BlockPos> findBlocks() {
        return (List<BlockPos>)EntityUtil.getSphere(PlayerUtil.getEyesPos(), Double.valueOf((double)this.Range.getValue() + 1.0), (Double)this.yRange.getValue(), false, false, 0).stream().filter(this::canPlaceBed).collect(Collectors.toList());
    }
    
    public BlockPos calculateBestPlacement(final EntityPlayer player) {
        final double targetHp = player.getHealth() + player.getAbsorptionAmount();
        if (player.isDead || targetHp <= 0.0) {
            return null;
        }
        EntityPlayer target;
        if (this.predict.getValue()) {
            target = PredictUtil.predictPlayer(player, this.settings);
        }
        else {
            target = player;
        }
        EntityPlayer self;
        if (this.predictself.getValue()) {
            self = PredictUtil.predictPlayer((EntityPlayer)BedAura.mc.player, this.settings);
        }
        else {
            self = (EntityPlayer)BedAura.mc.player;
        }
        final double Hp = self.getHealth() + self.getAbsorptionAmount();
        BlockPos best = null;
        float bestDamage = 0.0f;
        for (final BlockPos crystal : this.findBlocksExcluding()) {
            boolean can = this.canbaseplace;
            if (bestDamage >= (double)this.toggleDmg.getValue()) {
                can = false;
            }
            final boolean canplace = this.canPlaceBed(crystal);
            if (!canplace) {
                if (!(boolean)this.base.getValue() || !can) {
                    continue;
                }
                if (crystal.getY() >= (int)target.posY + (int)this.maxY.getValue()) {
                    continue;
                }
            }
            else {
                can = false;
            }
            final float currentDamage = DamageUtil.calcDamage(crystal.getX() + 0.5, crystal.getY() + 1.5626, crystal.getZ() + 0.5, (Entity)target);
            if (currentDamage <= bestDamage) {
                continue;
            }
            if (targetHp > (int)this.facePlaceValue.getValue()) {
                if (currentDamage < (double)this.minDmg.getValue()) {
                    continue;
                }
            }
            else if (currentDamage < (int)this.faceplacedamage.getValue()) {
                continue;
            }
            if (currentDamage < (double)this.baseminDmg.getValue()) {
                can = false;
            }
            final float SelfDamage = DamageUtil.calcDamage(crystal.getX() + 0.5, crystal.getY() + 1.5626, crystal.getZ() + 0.5, (Entity)self);
            if (!self.isCreative()) {
                if (SelfDamage > (double)this.maxSelfDmg.getValue()) {
                    if (currentDamage >= targetHp) {
                        if (!(boolean)this.forceplace.getValue()) {
                            continue;
                        }
                    }
                    else if (!(boolean)this.ignore.getValue()) {
                        continue;
                    }
                }
                if (!can && (boolean)this.suicide.getValue() && SelfDamage >= Hp) {
                    continue;
                }
            }
            if (can) {
                if (BurrowUtil.findHotbarBlock((Class)BlockObsidian.class) == -1) {
                    continue;
                }
                this.basepos = this.getBestBasePos(crystal);
                if (this.basepos == null) {
                    continue;
                }
                this.shouldbaseplace = true;
                bestDamage = currentDamage;
                best = crystal;
            }
            else {
                this.basepos = null;
                this.shouldbaseplace = false;
            }
            if (!canplace) {
                continue;
            }
            bestDamage = currentDamage;
            best = crystal;
        }
        return best;
    }
    
    public BlockPos calculatePlacement(final Entity player) {
        EntityPlayer self;
        if (this.predictself.getValue()) {
            self = PredictUtil.predictPlayer((EntityPlayer)BedAura.mc.player, this.settings);
        }
        else {
            self = (EntityPlayer)BedAura.mc.player;
        }
        BlockPos best = null;
        float bestDamage = 0.0f;
        for (final BlockPos crystal : this.findBlocks()) {
            final float currentDamage = DamageUtil.calcDamage(crystal.getX() + 0.5, crystal.getY() + 1.5626, crystal.getZ() + 0.5, player);
            final float SelfDamage = DamageUtil.calcDamage(crystal.getX() + 0.5, crystal.getY() + 1.5626, crystal.getZ() + 0.5, (Entity)self);
            final double Hp = self.getHealth() + self.getAbsorptionAmount() - (int)this.safehealth.getValue();
            final boolean Force = !(boolean)this.forceplace.getValue();
            if (!self.isCreative()) {
                if (!(boolean)this.ignore.getValue() && SelfDamage > (double)this.maxSelfDmg.getValue() && Force) {
                    continue;
                }
                if ((boolean)this.suicide.getValue() && SelfDamage >= Hp) {
                    continue;
                }
            }
            if (currentDamage < (double)this.minDmg.getValue() && currentDamage < (int)this.faceplacedamage.getValue()) {
                continue;
            }
            if (currentDamage == bestDamage) {
                if (best != null) {
                    continue;
                }
                bestDamage = currentDamage;
                best = crystal;
            }
            else {
                if (currentDamage <= bestDamage) {
                    continue;
                }
                bestDamage = currentDamage;
                best = crystal;
            }
        }
        if (player.isDead) {
            return null;
        }
        return best;
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (!this.isEnabled()) {
            this.render = null;
            this.render2 = null;
            this.renderEntity = null;
            this.Entity = null;
            this.managerClassRenderBlocks.blocks.clear();
            this.movingPlaceNow = new Vec3d(0.0, 0.0, 0.0);
        }
        if (this.render == null || this.render2 == null || (this.renderEntity == null && this.Entity == null)) {
            return;
        }
        this.managerClassRenderBlocks.render();
        if (!(boolean)this.anime.getValue()) {
            this.drawRender();
        }
        else {
            this.lastBestPlace = this.render;
            this.lastBestPos = this.render2;
        }
        if (this.fade.getValue()) {
            this.managerClassRenderBlocks.addRender(this.render, this.render2);
        }
        if ((boolean)this.anime.getValue() && this.lastBestPlace != null && this.lastBestPos != null) {
            if (this.movingPlaceNow.y == -1.0 && this.movingPlaceNow.x == -1.0 && this.movingPlaceNow.z == -1.0) {
                this.movingPlaceNow = new Vec3d((double)(float)this.lastBestPlace.getX(), (double)(float)this.lastBestPlace.getY(), (double)(float)this.lastBestPlace.getZ());
            }
            if (this.movingPosNow.y == -1.0 && this.movingPosNow.x == -1.0 && this.movingPosNow.z == -1.0) {
                this.movingPosNow = new Vec3d((double)(float)this.lastBestPos.getX(), (double)(float)this.lastBestPos.getY(), (double)(float)this.lastBestPos.getZ());
            }
            this.movingPlaceNow = new Vec3d(this.movingPlaceNow.x + (this.lastBestPlace.getX() - this.movingPlaceNow.x) * ((Double)this.movingPlaceSpeed.getValue()).floatValue(), this.movingPlaceNow.y + (this.lastBestPlace.getY() - this.movingPlaceNow.y) * ((Double)this.movingPlaceSpeed.getValue()).floatValue(), this.movingPlaceNow.z + (this.lastBestPlace.getZ() - this.movingPlaceNow.z) * ((Double)this.movingPlaceSpeed.getValue()).floatValue());
            this.movingPosNow = new Vec3d(this.movingPosNow.x + (this.lastBestPos.getX() - this.movingPosNow.x) * ((Double)this.movingPlaceSpeed.getValue()).floatValue(), this.movingPosNow.y + (this.lastBestPos.getY() - this.movingPosNow.y) * ((Double)this.movingPlaceSpeed.getValue()).floatValue(), this.movingPosNow.z + (this.lastBestPos.getZ() - this.movingPosNow.z) * ((Double)this.movingPlaceSpeed.getValue()).floatValue());
            this.drawBoxMain(this.movingPlaceNow.x, this.movingPlaceNow.y, this.movingPlaceNow.z, this.movingPosNow.x, this.movingPosNow.y, this.movingPosNow.z);
            if (Math.abs(this.movingPlaceNow.x - this.lastBestPlace.getX()) <= 0.125 && Math.abs(this.movingPlaceNow.y - this.lastBestPlace.getY()) <= 0.125 && Math.abs(this.movingPlaceNow.z - this.lastBestPlace.getZ()) <= 0.125) {
                this.lastBestPlace = null;
            }
            if (Math.abs(this.movingPosNow.x - this.lastBestPos.getX()) <= 0.125 && Math.abs(this.movingPosNow.y - this.lastBestPos.getY()) <= 0.125 && Math.abs(this.movingPosNow.z - this.lastBestPos.getZ()) <= 0.125) {
                this.lastBestPos = null;
            }
        }
    }
    
    private void drawAnimationRender(final AxisAlignedBB box1, final AxisAlignedBB box2) {
        final boolean x1 = box1.minX < box2.minX;
        final boolean z2 = box1.minZ > box2.minZ;
        AxisAlignedBB box3;
        if (x1) {
            if (z2) {
                box3 = new AxisAlignedBB(box1.minX, box1.minY, box2.minZ, box2.minX + 1.0, box2.minY + 0.5626, box1.minZ + 1.0);
            }
            else {
                box3 = new AxisAlignedBB(box1.minX, box1.minY, box1.minZ, box2.minX + 1.0, box2.minY + 0.5626, box2.minZ + 1.0);
            }
        }
        else if (z2) {
            box3 = new AxisAlignedBB(box2.minX, box1.minY, box2.minZ, box1.minX + 1.0, box2.minY + 0.5626, box1.minZ + 1.0);
        }
        else {
            box3 = new AxisAlignedBB(box2.minX, box1.minY, box1.minZ, box1.minX + 1.0, box2.minY + 0.5626, box2.minZ + 1.0);
        }
        if (new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()).equals((Object)new GSColor(this.color2.getValue(), (int)this.ufoAlpha.getValue()))) {
            RenderUtil.drawBox(box3, false, 0.5626, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 63);
            if (this.outline.getValue()) {
                RenderUtil.drawBoundingBox(box3, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255));
            }
        }
        else if (this.fix.getValue()) {
            final String face = this.face;
            switch (face) {
                case "WEST": {
                    if (this.gradient.getValue()) {
                        RenderUtil.drawBoxDire(box3, 0.5626, new GSColor(this.color.getValue(), (int)this.alpha.getValue()), 0, 16);
                        RenderUtil.drawBoxDire(box3, 0.5626, new GSColor(this.color2.getValue(), (int)this.alpha.getValue()), 0, 32);
                        if (this.outgradient.getValue()) {
                            RenderUtil.drawBoundingBoxDire(box3, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255), 0, 16);
                            RenderUtil.drawBoundingBoxDire(box3, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255), 0, 32);
                            break;
                        }
                        break;
                    }
                    else {
                        RenderUtil.drawBox(new AxisAlignedBB(box1.minX, box1.minY, box1.minZ, box1.minX + 1.0, box1.minY + 0.5626, box1.minZ + 1.0), false, 0.5626, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 31);
                        RenderUtil.drawBox(new AxisAlignedBB(box2.minX, box2.minY, box2.minZ, box2.minX + 1.0, box2.minY + 0.5626, box2.minZ + 1.0), false, 0.5626, new GSColor(this.color2.getValue(), (int)this.ufoAlpha.getValue()), 47);
                        if (this.outline.getValue()) {
                            RenderUtil.drawBoundingBox(new AxisAlignedBB(box1.minX, box1.minY, box1.minZ, box1.minX + 1.0, box1.minY + 0.5626, box1.minZ + 1.0), (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255));
                            RenderUtil.drawBoundingBox(new AxisAlignedBB(box2.minX, box2.minY, box2.minZ, box2.minX + 1.0, box2.minY + 0.5626, box2.minZ + 1.0), (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255));
                            break;
                        }
                        break;
                    }
                    break;
                }
                case "EAST": {
                    if (this.gradient.getValue()) {
                        RenderUtil.drawBoxDire(box3, 0.5626, new GSColor(this.color.getValue(), (int)this.alpha.getValue()), 0, 32);
                        RenderUtil.drawBoxDire(box3, 0.5626, new GSColor(this.color2.getValue(), (int)this.alpha.getValue()), 0, 16);
                        if (this.outgradient.getValue()) {
                            RenderUtil.drawBoundingBoxDire(box3, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255), 0, 32);
                            RenderUtil.drawBoundingBoxDire(box3, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255), 0, 16);
                            break;
                        }
                        break;
                    }
                    else {
                        RenderUtil.drawBox(new AxisAlignedBB(box1.minX, box1.minY, box1.minZ, box1.minX + 1.0, box1.minY + 0.5626, box1.minZ + 1.0), false, 0.5626, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 47);
                        RenderUtil.drawBox(new AxisAlignedBB(box2.minX, box2.minY, box2.minZ, box2.minX + 1.0, box2.minY + 0.5626, box2.minZ + 1.0), false, 0.5626, new GSColor(this.color2.getValue(), (int)this.ufoAlpha.getValue()), 31);
                        if (this.outline.getValue()) {
                            RenderUtil.drawBoundingBox(new AxisAlignedBB(box1.minX, box1.minY, box1.minZ, box1.minX + 1.0, box1.minY + 0.5626, box1.minZ + 1.0), (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255));
                            RenderUtil.drawBoundingBox(new AxisAlignedBB(box2.minX, box2.minY, box2.minZ, box2.minX + 1.0, box2.minY + 0.5626, box2.minZ + 1.0), (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255));
                            break;
                        }
                        break;
                    }
                    break;
                }
                case "SOUTH": {
                    if (this.gradient.getValue()) {
                        RenderUtil.drawBoxDire(box3, 0.5626, new GSColor(this.color.getValue(), (int)this.alpha.getValue()), 0, 8);
                        RenderUtil.drawBoxDire(box3, 0.5626, new GSColor(this.color2.getValue(), (int)this.alpha.getValue()), 0, 4);
                        if (this.outgradient.getValue()) {
                            RenderUtil.drawBoundingBoxDire(box3, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255), 0, 8);
                            RenderUtil.drawBoundingBoxDire(box3, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255), 0, 4);
                            break;
                        }
                        break;
                    }
                    else {
                        RenderUtil.drawBox(new AxisAlignedBB(box1.minX, box1.minY, box1.minZ, box1.minX + 1.0, box1.minY + 0.5626, box1.minZ + 1.0), false, 0.5626, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 59);
                        RenderUtil.drawBox(new AxisAlignedBB(box2.minX, box2.minY, box2.minZ, box2.minX + 1.0, box2.minY + 0.5626, box2.minZ + 1.0), false, 0.5626, new GSColor(this.color2.getValue(), (int)this.ufoAlpha.getValue()), 55);
                        if (this.outline.getValue()) {
                            RenderUtil.drawBoundingBox(new AxisAlignedBB(box1.minX, box1.minY, box1.minZ, box1.minX + 1.0, box1.minY + 0.5626, box1.minZ + 1.0), (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255));
                            RenderUtil.drawBoundingBox(new AxisAlignedBB(box2.minX, box2.minY, box2.minZ, box2.minX + 1.0, box2.minY + 0.5626, box2.minZ + 1.0), (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255));
                            break;
                        }
                        break;
                    }
                    break;
                }
                case "NORTH": {
                    if (this.gradient.getValue()) {
                        RenderUtil.drawBoxDire(box3, 0.5626, new GSColor(this.color.getValue(), (int)this.alpha.getValue()), 0, 4);
                        RenderUtil.drawBoxDire(box3, 0.5626, new GSColor(this.color2.getValue(), (int)this.alpha.getValue()), 0, 8);
                        if (this.outgradient.getValue()) {
                            RenderUtil.drawBoundingBoxDire(box3, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255), 0, 4);
                            RenderUtil.drawBoundingBoxDire(box3, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255), 0, 8);
                            break;
                        }
                        break;
                    }
                    else {
                        RenderUtil.drawBox(new AxisAlignedBB(box1.minX, box1.minY, box1.minZ, box1.minX + 1.0, box1.minY + 0.5626, box1.minZ + 1.0), false, 0.5626, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 55);
                        RenderUtil.drawBox(new AxisAlignedBB(box2.minX, box2.minY, box2.minZ, box2.minX + 1.0, box2.minY + 0.5626, box2.minZ + 1.0), false, 0.5626, new GSColor(this.color2.getValue(), (int)this.ufoAlpha.getValue()), 59);
                        if (this.outline.getValue()) {
                            RenderUtil.drawBoundingBox(new AxisAlignedBB(box1.minX, box1.minY, box1.minZ, box1.minX + 1.0, box1.minY + 0.5626, box1.minZ + 1.0), (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255));
                            RenderUtil.drawBoundingBox(new AxisAlignedBB(box2.minX, box2.minY, box2.minZ, box2.minX + 1.0, box2.minY + 0.5626, box2.minZ + 1.0), (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255));
                            break;
                        }
                        break;
                    }
                    break;
                }
            }
        }
        else {
            RenderUtil.drawBox(new AxisAlignedBB(box1.minX, box1.minY, box1.minZ, box1.minX + 1.0, box1.minY + 0.5626, box1.minZ + 1.0), false, 0.5626, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 63);
            RenderUtil.drawBox(new AxisAlignedBB(box2.minX, box2.minY, box2.minZ, box2.minX + 1.0, box2.minY + 0.5626, box2.minZ + 1.0), false, 0.5626, new GSColor(this.color2.getValue(), (int)this.ufoAlpha.getValue()), 63);
            if ((boolean)this.outline.getValue() && !(boolean)this.outgradient.getValue()) {
                RenderUtil.drawBoundingBox(new AxisAlignedBB(box1.minX, box1.minY, box1.minZ, box1.minX + 1.0, box1.minY + 0.5626, box1.minZ + 1.0), (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255));
                RenderUtil.drawBoundingBox(new AxisAlignedBB(box2.minX, box2.minY, box2.minZ, box2.minX + 1.0, box2.minY + 0.5626, box2.minZ + 1.0), (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255));
            }
        }
        if (this.showDamage.getValue()) {
            String[] damageText = { String.format("%.1f", this.Damage) };
            if (this.selfDamage.getValue()) {
                damageText = new String[] { String.format("%.1f", this.Damage) + "/" + String.format("%.1f", this.SelfDamage) };
            }
            RenderUtil.drawNametag(box1.minX + 0.5, box1.minY + 0.2813, box1.minZ + 0.5, damageText, new GSColor(255, 255, 255), 1);
        }
    }
    
    private void drawFade(final BlockPos render, final BlockPos render2, final int alpha, final int outlineAlpha) {
        if (render == null || render2 == null || alpha < 0 || outlineAlpha < 0) {
            return;
        }
        if (new GSColor(this.color.getValue(), alpha).equals((Object)new GSColor(this.color2.getValue(), alpha))) {
            AxisAlignedBB box;
            if (render.x - render2.x < 0 || render.z - render2.z < 0) {
                box = new AxisAlignedBB((double)render.x, (double)render.y, (double)render.z, (double)(render2.getX() + 1), render2.getY() + 0.5626, (double)(render2.getZ() + 1));
            }
            else {
                box = new AxisAlignedBB((double)render2.x, (double)render2.y, (double)render2.z, (double)(render.getX() + 1), render.getY() + 0.5626, (double)(render.getZ() + 1));
            }
            RenderUtil.drawBox(box, false, 0.5626, new GSColor(this.color.getValue(), alpha), 63);
            if (this.outline.getValue()) {
                RenderUtil.drawBoundingBox(box, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), outlineAlpha));
            }
        }
        else {
            if (this.fix.getValue()) {
                if (this.gradient.getValue()) {
                    AxisAlignedBB box;
                    if (render.x - render2.x < 0 || render.z - render2.z < 0) {
                        box = new AxisAlignedBB((double)render.x, (double)render.y, (double)render.z, (double)(render2.getX() + 1), render2.getY() + 0.5626, (double)(render2.getZ() + 1));
                    }
                    else {
                        box = new AxisAlignedBB((double)render2.x, (double)render2.y, (double)render2.z, (double)(render.getX() + 1), render.getY() + 0.5626, (double)(render.getZ() + 1));
                    }
                    if (render.x - render2.x < 0) {
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color.getValue(), alpha), 0, 16);
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color2.getValue(), alpha), 0, 32);
                        if (this.outgradient.getValue()) {
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), outlineAlpha), 0, 16);
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), outlineAlpha), 0, 32);
                        }
                    }
                    else if (render.z - render2.z < 0) {
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color.getValue(), alpha), 0, 4);
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color2.getValue(), alpha), 0, 8);
                        if (this.outgradient.getValue()) {
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), outlineAlpha), 0, 4);
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), outlineAlpha), 0, 8);
                        }
                    }
                    else if (render.z - render2.z == 0) {
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color.getValue(), alpha), 0, 32);
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color2.getValue(), alpha), 0, 16);
                        if (this.outgradient.getValue()) {
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), outlineAlpha), 0, 32);
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), outlineAlpha), 0, 16);
                        }
                    }
                    else {
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color.getValue(), alpha), 0, 8);
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color2.getValue(), alpha), 0, 4);
                        if (this.outgradient.getValue()) {
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), outlineAlpha), 0, 8);
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), outlineAlpha), 0, 4);
                        }
                    }
                }
                else if (render.x - render2.x < 0) {
                    RenderUtil.drawBox(render, 0.5626, new GSColor(this.color.getValue(), alpha), 31);
                    RenderUtil.drawBox(render2, 0.5626, new GSColor(this.color2.getValue(), alpha), 47);
                }
                else if (render.z - render2.z < 0) {
                    RenderUtil.drawBox(render, 0.5626, new GSColor(this.color.getValue(), alpha), 55);
                    RenderUtil.drawBox(render2, 0.5626, new GSColor(this.color2.getValue(), alpha), 59);
                }
                else if (render.z - render2.z == 0) {
                    RenderUtil.drawBox(render, 0.5626, new GSColor(this.color.getValue(), alpha), 47);
                    RenderUtil.drawBox(render2, 0.5626, new GSColor(this.color2.getValue(), alpha), 31);
                }
                else {
                    RenderUtil.drawBox(render, 0.5626, new GSColor(this.color.getValue(), alpha), 59);
                    RenderUtil.drawBox(render2, 0.5626, new GSColor(this.color2.getValue(), alpha), 55);
                }
            }
            else {
                RenderUtil.drawBox(render, 0.5626, new GSColor(this.color.getValue(), alpha), 63);
                RenderUtil.drawBox(render2, 0.5626, new GSColor(this.color2.getValue(), alpha), 63);
            }
            if ((boolean)this.outline.getValue() && !(boolean)this.outgradient.getValue()) {
                RenderUtil.drawBoundingBox(render, 0.5626, (float)(int)this.width.getValue(), new GSColor(this.color.getValue(), outlineAlpha));
                RenderUtil.drawBoundingBox(render2, 0.5626, (float)(int)this.width.getValue(), new GSColor(this.color2.getValue(), outlineAlpha));
            }
        }
    }
    
    private void drawRender() {
        if (new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()).equals((Object)new GSColor(this.color2.getValue(), (int)this.ufoAlpha.getValue()))) {
            AxisAlignedBB box;
            if (this.render.x - this.render2.x < 0 || this.render.z - this.render2.z < 0) {
                box = new AxisAlignedBB((double)this.render.x, (double)this.render.y, (double)this.render.z, (double)(this.render2.getX() + 1), this.render2.getY() + 0.5626, (double)(this.render2.getZ() + 1));
            }
            else {
                box = new AxisAlignedBB((double)this.render2.x, (double)this.render2.y, (double)this.render2.z, (double)(this.render.getX() + 1), this.render.getY() + 0.5626, (double)(this.render.getZ() + 1));
            }
            RenderUtil.drawBox(box, false, 0.5626, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 63);
            if (this.outline.getValue()) {
                RenderUtil.drawBoundingBox(box, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255));
            }
        }
        else {
            if (this.fix.getValue()) {
                if (this.gradient.getValue()) {
                    AxisAlignedBB box;
                    if (this.render.x - this.render2.x < 0 || this.render.z - this.render2.z < 0) {
                        box = new AxisAlignedBB((double)this.render.x, (double)this.render.y, (double)this.render.z, (double)(this.render2.getX() + 1), this.render2.getY() + 0.5626, (double)(this.render2.getZ() + 1));
                    }
                    else {
                        box = new AxisAlignedBB((double)this.render2.x, (double)this.render2.y, (double)this.render2.z, (double)(this.render.getX() + 1), this.render.getY() + 0.5626, (double)(this.render.getZ() + 1));
                    }
                    if (this.render.x - this.render2.x < 0) {
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color.getValue(), (int)this.alpha.getValue()), 0, 16);
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color2.getValue(), (int)this.alpha.getValue()), 0, 32);
                        if (this.outgradient.getValue()) {
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255), 0, 16);
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255), 0, 32);
                        }
                    }
                    else if (this.render.z - this.render2.z < 0) {
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color.getValue(), (int)this.alpha.getValue()), 0, 4);
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color2.getValue(), (int)this.alpha.getValue()), 0, 8);
                        if (this.outgradient.getValue()) {
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255), 0, 4);
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255), 0, 8);
                        }
                    }
                    else if (this.render.z - this.render2.z == 0) {
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color.getValue(), (int)this.alpha.getValue()), 0, 32);
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color2.getValue(), (int)this.alpha.getValue()), 0, 16);
                        if (this.outgradient.getValue()) {
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255), 0, 32);
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255), 0, 16);
                        }
                    }
                    else {
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color.getValue(), (int)this.alpha.getValue()), 0, 8);
                        RenderUtil.drawBoxDire(box, 0.5626, new GSColor(this.color2.getValue(), (int)this.alpha.getValue()), 0, 4);
                        if (this.outgradient.getValue()) {
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255), 0, 8);
                            RenderUtil.drawBoundingBoxDire(box, 0.5626, (double)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255), 0, 4);
                        }
                    }
                }
                else if (this.render.x - this.render2.x < 0) {
                    RenderUtil.drawBox(this.render, 0.5626, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 31);
                    RenderUtil.drawBox(this.render2, 0.5626, new GSColor(this.color2.getValue(), (int)this.ufoAlpha.getValue()), 47);
                }
                else if (this.render.z - this.render2.z < 0) {
                    RenderUtil.drawBox(this.render, 0.5626, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 55);
                    RenderUtil.drawBox(this.render2, 0.5626, new GSColor(this.color2.getValue(), (int)this.ufoAlpha.getValue()), 59);
                }
                else if (this.render.z - this.render2.z == 0) {
                    RenderUtil.drawBox(this.render, 0.5626, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 47);
                    RenderUtil.drawBox(this.render2, 0.5626, new GSColor(this.color2.getValue(), (int)this.ufoAlpha.getValue()), 31);
                }
                else {
                    RenderUtil.drawBox(this.render, 0.5626, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 59);
                    RenderUtil.drawBox(this.render2, 0.5626, new GSColor(this.color2.getValue(), (int)this.ufoAlpha.getValue()), 55);
                }
            }
            else {
                RenderUtil.drawBox(this.render, 0.5626, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 63);
                RenderUtil.drawBox(this.render2, 0.5626, new GSColor(this.color2.getValue(), (int)this.ufoAlpha.getValue()), 63);
            }
            if ((boolean)this.outline.getValue() && !(boolean)this.outgradient.getValue()) {
                RenderUtil.drawBoundingBox(this.render, 0.5626, (float)(int)this.width.getValue(), new GSColor(this.color.getValue(), 255));
                RenderUtil.drawBoundingBox(this.render2, 0.5626, (float)(int)this.width.getValue(), new GSColor(this.color2.getValue(), 255));
            }
        }
        if (this.showDamage.getValue()) {
            String[] damageText = { String.format("%.1f", this.Damage) };
            if (this.selfDamage.getValue()) {
                damageText = new String[] { String.format("%.1f", this.Damage) + "/" + String.format("%.1f", this.SelfDamage) };
            }
            RenderUtil.drawNametag(this.render.getX() + 0.5, this.render.getY() + 0.2813, this.render.getZ() + 0.5, damageText, new GSColor(255, 255, 255), 1);
        }
    }
    
    AxisAlignedBB getBox(final double x, final double y, final double z) {
        final double maxX = x + 1.0;
        final double maxZ = z + 1.0;
        return new AxisAlignedBB(x, y, z, maxX, y, maxZ);
    }
    
    void drawBoxMain(final BlockPos pos, final BlockPos pos2, final int alpha, final int outline) {
        this.drawFade(pos, pos2, alpha, outline);
    }
    
    void drawBoxMain(final double x, final double y, final double z, final double x2, final double y2, final double z2) {
        AxisAlignedBB box = this.getBox(x, y, z);
        AxisAlignedBB box2 = this.getBox(x2, y2, z2);
        box = new AxisAlignedBB(box.minX, box.maxY, box.minZ, box.maxX, box.maxY + 0.5626, box.maxZ);
        box2 = new AxisAlignedBB(box2.minX, box2.maxY, box2.minZ, box2.maxX, box2.maxY + 0.5626, box2.maxZ);
        this.drawAnimationRender(box, box2);
    }
    
    public void refill_bed() {
        if (!(BedAura.mc.currentScreen instanceof GuiContainer) || BedAura.mc.currentScreen instanceof GuiInventory) {
            final int airslot = this.is_space();
            if (airslot != -1) {
                int i = 9;
                while (i < 36) {
                    if (BedAura.mc.player.inventory.getStackInSlot(i).getItem() == Items.BED) {
                        if (((String)this.clickmode.getValue()).equalsIgnoreCase("Quick")) {
                            BedAura.mc.playerController.windowClick(BedAura.mc.player.inventoryContainer.windowId, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)BedAura.mc.player);
                            BedAura.mc.playerController.updateController();
                            break;
                        }
                        if (((String)this.clickmode.getValue()).equalsIgnoreCase("Swap")) {
                            BedAura.mc.playerController.windowClick(0, i, 0, ClickType.SWAP, (EntityPlayer)BedAura.mc.player);
                            BedAura.mc.playerController.windowClick(0, airslot + 36, 0, ClickType.SWAP, (EntityPlayer)BedAura.mc.player);
                            BedAura.mc.playerController.windowClick(0, i, 0, ClickType.SWAP, (EntityPlayer)BedAura.mc.player);
                            BedAura.mc.playerController.updateController();
                            break;
                        }
                        BedAura.mc.playerController.windowClick(BedAura.mc.player.inventoryContainer.windowId, i, 0, ClickType.PICKUP, (EntityPlayer)BedAura.mc.player);
                        BedAura.mc.playerController.windowClick(BedAura.mc.player.inventoryContainer.windowId, airslot + 36, 0, ClickType.PICKUP, (EntityPlayer)BedAura.mc.player);
                        BedAura.mc.playerController.updateController();
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
        }
    }
    
    private int is_space() {
        int slot = -1;
        if (this.force.getValue()) {
            if (((String)this.refillmode.getValue()).equals("Only")) {
                final int slot2 = (int)this.slotS.getValue() - 1;
                if (BedAura.mc.player.inventory.getStackInSlot(slot2).getItem() != Items.BED) {
                    slot = slot2;
                }
            }
            else {
                for (int i = 0; i < 9; ++i) {
                    if (BedAura.mc.player.inventory.getStackInSlot(i).getItem() != Items.BED) {
                        slot = i;
                    }
                }
            }
        }
        else if (((String)this.refillmode.getValue()).equals("Only")) {
            final int slot2 = (int)this.slotS.getValue() - 1;
            if (BedAura.mc.player.inventory.getStackInSlot(slot2).getItem() == Items.AIR) {
                slot = slot2;
            }
        }
        else {
            for (int i = 0; i < 9; ++i) {
                if (BedAura.mc.player.inventory.getStackInSlot(i).getItem() == Items.AIR) {
                    slot = i;
                }
            }
        }
        return slot;
    }
    
    private void swing(final EnumHand hand) {
        if (this.packetswing.getValue()) {
            BedAura.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
        }
        else {
            BedAura.mc.player.swingArm(hand);
        }
    }
    
    public void onEnable() {
        this.starttiming.reset();
        this.calctiming.reset();
        this.basetiming.reset();
        this.placetiming.reset();
        this.breaktiming.reset();
        this.rotatetiming.reset();
    }
    
    public void onDisable() {
        this.render = null;
        this.render2 = null;
        this.movingPlaceNow = new Vec3d(-1.0, -1.0, -1.0);
        this.movingPosNow = new Vec3d(-1.0, -1.0, -1.0);
    }
    
    public String getHudInfo() {
        String t = "";
        if (((String)this.hudDisplay.getValue()).equalsIgnoreCase("Target")) {
            if (this.renderEntity == null) {
                if (this.Entity != null) {
                    t = "[" + ChatFormatting.WHITE + this.Entity.getName() + ChatFormatting.GRAY + "]";
                }
                else {
                    t = "[" + ChatFormatting.WHITE + "None" + ChatFormatting.GRAY + "]";
                }
            }
            else {
                t = "[" + ChatFormatting.WHITE + this.renderEntity.getName() + ChatFormatting.GRAY + "]";
            }
        }
        else if (((String)this.hudDisplay.getValue()).equalsIgnoreCase("Damage")) {
            if (this.render != null) {
                t = "[" + ChatFormatting.WHITE + "Damage: " + String.format("%.1f", this.Damage) + " Self: " + String.format("%.1f", this.SelfDamage) + ChatFormatting.GRAY + "]";
            }
        }
        else if (((String)this.hudDisplay.getValue()).equalsIgnoreCase("Both")) {
            if (this.renderEntity == null) {
                if (this.Entity != null) {
                    t = "[" + ChatFormatting.WHITE + this.Entity.getName();
                }
                else {
                    t = "[" + ChatFormatting.WHITE + "None";
                }
            }
            else {
                t = "[" + ChatFormatting.WHITE + this.renderEntity.getName();
            }
            t = t + ChatFormatting.GRAY + " " + ChatFormatting.WHITE + "Damage: " + String.format("%.1f", this.Damage) + " Self: " + String.format("%.1f", this.SelfDamage) + ChatFormatting.GRAY + "]";
        }
        return t;
    }
    
    class renderBlock
    {
        private final BlockPos pos;
        private final BlockPos pos2;
        private long start;
        
        public renderBlock(final BlockPos pos, final BlockPos pos2) {
            this.start = System.currentTimeMillis();
            this.pos = pos;
            this.pos2 = pos2;
        }
        
        void resetTime() {
            this.start = System.currentTimeMillis();
        }
        
        void render() {
            BedAura.this.drawBoxMain(this.pos, this.pos2, this.returnGradient(), this.returnOutlineGradient());
        }
        
        public int returnGradient() {
            final long end = this.start + (int)BedAura.this.lifeTime.getValue();
            int result = (int)((end - System.currentTimeMillis()) / (float)(end - this.start) * 100.0f);
            if (result < 0) {
                result = 0;
            }
            final int startFade = (int)BedAura.this.fadealpha.getValue();
            final int endFade = 0;
            return (int)((startFade - (double)endFade) * (result / 100.0));
        }
        
        public int returnOutlineGradient() {
            final long end = this.start + (int)BedAura.this.lifeTime.getValue();
            int result = (int)((end - System.currentTimeMillis()) / (float)(end - this.start) * 100.0f);
            if (result < 0) {
                result = 0;
            }
            final int startFade = 255;
            final int endFade = 0;
            return (int)((startFade - (double)endFade) * (result / 100.0));
        }
    }
    
    class managerClassRenderBlocks
    {
        ArrayList<renderBlock> blocks;
        
        managerClassRenderBlocks() {
            this.blocks = new ArrayList<renderBlock>();
        }
        
        void update(final int time) {
            this.blocks.removeIf(e -> System.currentTimeMillis() - e.start > time);
        }
        
        void render() {
            this.blocks.forEach(e -> {
                if (BedAura.this.render != null && ((BedAura.this.isPos2(e.pos, BedAura.this.render) && BedAura.this.isPos2(e.pos2, BedAura.this.render2)) || (BedAura.this.isPos2(e.pos2, BedAura.this.render) && BedAura.this.isPos2(e.pos, BedAura.this.render2)))) {
                    e.resetTime();
                }
                else {
                    e.render();
                }
            });
        }
        
        void addRender(final BlockPos pos, final BlockPos pos2) {
            boolean render = true;
            for (final renderBlock block : this.blocks) {
                if ((BedAura.this.isPos2(block.pos, pos) && BedAura.this.isPos2(block.pos2, pos2)) || (BedAura.this.isPos2(block.pos2, pos2) && BedAura.this.isPos2(block.pos, pos))) {
                    render = false;
                    block.resetTime();
                    break;
                }
            }
            if (render) {
                this.blocks.add(new renderBlock(pos, pos2));
            }
        }
    }
}
