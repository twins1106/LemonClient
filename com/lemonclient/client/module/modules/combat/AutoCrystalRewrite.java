//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.item.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import org.apache.logging.log4j.*;
import com.mojang.realmsclient.gui.*;
import com.lemonclient.api.util.misc.*;
import org.lwjgl.input.*;
import net.minecraft.entity.player.*;
import java.util.concurrent.*;
import com.lemonclient.api.util.world.combat.*;
import com.lemonclient.api.util.world.combat.ac.*;
import com.lemonclient.client.module.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.math.*;
import com.lemonclient.client.module.modules.qwq.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import java.util.stream.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.api.util.render.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.lemonclient.mixin.mixins.accessor.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import com.lemonclient.api.event.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.client.manager.managers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.potion.*;
import net.minecraft.client.*;

@Module.Declaration(name = "AutoCrystal", category = Category.Combat, priority = 100)
public class AutoCrystalRewrite extends Module
{
    BooleanSetting logicTarget;
    ModeSetting logic;
    BooleanSetting oneStop;
    ModeSetting targetPlacing;
    ModeSetting targetBreaking;
    BooleanSetting renderTarget;
    BooleanSetting stopGapple;
    IntegerSetting tickWaitEat;
    DoubleSetting damageBalance;
    public BooleanSetting newPlace;
    BooleanSetting ignoreTerrain;
    BooleanSetting bindIgnoreTerrain;
    BooleanSetting entityPredict;
    IntegerSetting offset;
    IntegerSetting tryAttack;
    IntegerSetting delayAttacks;
    IntegerSetting midDelayAttacks;
    BooleanSetting ranges;
    DoubleSetting rangeEnemyPlace;
    DoubleSetting rangeEnemyBreaking;
    public DoubleSetting placeRange;
    public DoubleSetting breakRange;
    DoubleSetting crystalWallPlace;
    DoubleSetting wallrangeBreak;
    IntegerSetting maxYTarget;
    IntegerSetting minYTarget;
    BooleanSetting place;
    ModeSetting placeDelay;
    IntegerSetting tickDelayPlace;
    IntegerSetting timeDelayPlace;
    IntegerSetting vanillaSpeedPlace;
    BooleanSetting placeOnCrystal;
    DoubleSetting minDamagePlace;
    DoubleSetting maxSelfDamagePlace;
    BooleanSetting relativeDamagePlace;
    DoubleSetting relativeDamageValuePlace;
    IntegerSetting armourFacePlace;
    IntegerSetting facePlaceValue;
    DoubleSetting minFacePlaceDmg;
    BooleanSetting antiSuicidepl;
    BooleanSetting includeCrystalMapping;
    ModeSetting limitPacketPlace;
    IntegerSetting limitTickPlace;
    IntegerSetting limitTickTime;
    ModeSetting swingModepl;
    BooleanSetting hideClientpl;
    BooleanSetting autoWeb;
    BooleanSetting stopCrystal;
    BooleanSetting focusWebRotate;
    BooleanSetting onlyAutoWebActive;
    BooleanSetting switchWeb;
    BooleanSetting silentSwitchWeb;
    BooleanSetting switchBackWeb;
    BooleanSetting switchBackEnd;
    BooleanSetting breakNearCrystal;
    BooleanSetting breakSection;
    ModeSetting breakDelay;
    IntegerSetting tickDelayBreak;
    IntegerSetting timeDelayBreak;
    IntegerSetting vanillaSpeedBreak;
    ModeSetting chooseCrystal;
    DoubleSetting minDamageBreak;
    DoubleSetting maxSelfDamageBreak;
    BooleanSetting relativeDamageBreak;
    DoubleSetting relativeDamageValueBreak;
    ModeSetting swingModebr;
    BooleanSetting hideClientbr;
    ModeSetting breakTypeCrystal;
    ModeSetting limitBreakPacket;
    IntegerSetting lomitBreakPacketTick;
    IntegerSetting limitBreakPacketTime;
    ModeSetting firstHit;
    IntegerSetting firstHitTick;
    IntegerSetting fitstHitTime;
    BooleanSetting cancelCrystal;
    BooleanSetting setDead;
    BooleanSetting placeAfterBreak;
    BooleanSetting instaPlace;
    BooleanSetting checkinstaPlace;
    BooleanSetting forcePlace;
    BooleanSetting antiWeakness;
    ModeSetting slowBreak;
    DoubleSetting speedActivation;
    IntegerSetting tickSlowBreak;
    IntegerSetting timeSlowBreak;
    BooleanSetting predictHit;
    IntegerSetting predictHitDelay;
    BooleanSetting antiSuicidebr;
    BooleanSetting antiCity;
    BooleanSetting destroyCrystal;
    BooleanSetting destroyAboveCrystal;
    BooleanSetting allowNon1x1;
    BooleanSetting misc;
    BooleanSetting switchHotbar;
    BooleanSetting switchBack;
    IntegerSetting tickSwitchBack;
    BooleanSetting waitGappleSwitch;
    BooleanSetting silentSwitch;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting renders;
    ModeSetting typePlace;
    ModeSetting placeDimension;
    DoubleSetting rangeCirclePl;
    DoubleSetting slabHeightPlace;
    BooleanSetting OutLineSection;
    IntegerSetting outlineWidthpl;
    ColorSetting firstVerticeOutlineBot;
    BooleanSetting FillSection;
    ColorSetting firstVerticeFillBot;
    IntegerSetting firstVerticeFillAlpha;
    ModeSetting typeBreak;
    ModeSetting breakDimension;
    DoubleSetting rangeCircleBr;
    DoubleSetting slabHeightBreak;
    BooleanSetting OutLineSectionbr;
    ColorSetting firstVerticeOutlineBotbr;
    BooleanSetting FillSectionbr;
    ColorSetting firstVerticeFillBotbr;
    IntegerSetting firstVerticeFillAlphabr;
    BooleanSetting showTextpl;
    ColorSetting colorPlaceText;
    DoubleSetting textYPlace;
    BooleanSetting showTextbr;
    ColorSetting colorBreakText;
    DoubleSetting textYBreak;
    BooleanSetting movingPlace;
    DoubleSetting movingPlaceSpeed;
    BooleanSetting movingBreak;
    DoubleSetting movingBreakSpeed;
    IntegerSetting extendedPlace;
    IntegerSetting extendedBreak;
    BooleanSetting fadeCapl;
    BooleanSetting fadeCabr;
    IntegerSetting lifeTime;
    BooleanSetting placeDominant;
    BooleanSetting circleRender;
    IntegerSetting life;
    DoubleSetting circleRange;
    ColorSetting color;
    BooleanSetting desyncCircle;
    IntegerSetting stepRainbowCircle;
    BooleanSetting increaseHeight;
    DoubleSetting speedIncrease;
    BooleanSetting predictSection;
    BooleanSetting predictSurround;
    BooleanSetting predictPacketSurround;
    IntegerSetting percentSurround;
    IntegerSetting tickPacketBreak;
    IntegerSetting tickMaxPacketBreak;
    DoubleSetting maxSelfDamageSur;
    BooleanSetting predictSelfPlace;
    BooleanSetting predictPlaceEnemy;
    BooleanSetting predictSelfDBreaking;
    BooleanSetting predictBreakingEnemy;
    BooleanSetting pingPredict;
    IntegerSetting tickPredict;
    BooleanSetting calculateYPredict;
    IntegerSetting startDecrease;
    IntegerSetting exponentStartDecrease;
    IntegerSetting decreaseY;
    IntegerSetting exponentDecreaseY;
    IntegerSetting increaseY;
    IntegerSetting exponentIncreaseY;
    BooleanSetting splitXZ;
    IntegerSetting widthPredict;
    BooleanSetting manualOutHole;
    BooleanSetting aboveHoleManual;
    BooleanSetting stairPredict;
    IntegerSetting nStair;
    DoubleSetting speedActivationStair;
    BooleanSetting threading;
    IntegerSetting nThread;
    IntegerSetting maxTarget;
    IntegerSetting placeTimeout;
    IntegerSetting predictPlaceTimeout;
    IntegerSetting breakTimeout;
    IntegerSetting predictBreakTimeout;
    BooleanSetting strict;
    BooleanSetting raytrace;
    BooleanSetting rotate;
    BooleanSetting preRotate;
    IntegerSetting tickAfterRotation;
    ModeSetting focusPlaceType;
    BooleanSetting recalculateDamage;
    IntegerSetting tickWaitFocusPlace;
    IntegerSetting timeWaitFocusPlace;
    BooleanSetting yawCheck;
    IntegerSetting yawStep;
    BooleanSetting pitchCheck;
    IntegerSetting pitchStep;
    BooleanSetting placeStrictDirection;
    BooleanSetting predictBreakRotation;
    BooleanSetting blockRotation;
    BooleanSetting debugMenu;
    BooleanSetting timeCalcPlacement;
    BooleanSetting timeCalcBreaking;
    IntegerSetting nCalc;
    BooleanSetting debugPredict;
    BooleanSetting showPredictions;
    BooleanSetting hudDisplayShow;
    BooleanSetting showPlaceName;
    BooleanSetting showPlaceDamage;
    BooleanSetting showPlaceCrystalsSecond;
    BooleanSetting cleanPlace;
    BooleanSetting showBreakName;
    BooleanSetting showBreakDamage;
    BooleanSetting showBreakCrystalsSecond;
    BooleanSetting cleanBreak;
    StringSetting letterIgnoreTerrain;
    StringSetting forceFacePlace;
    StringSetting anvilCity;
    IntegerSetting placeAnvil;
    public static boolean stopAC;
    boolean checkTimePlace;
    boolean checkTimeBreak;
    boolean placedCrystal;
    boolean brokenCrystal;
    boolean isRotating;
    int oldSlot;
    int tick;
    int tickBeforePlace;
    int tickBeforeBreak;
    int slotChange;
    int tickSwitch;
    int oldSlotBackWeb;
    int oldSlotObby;
    int slotWebBack;
    int highestId;
    int placeRender;
    int breakRender;
    double xPlayerRotation;
    double yPlayerRotation;
    Timer timerPlace;
    Timer timerBreak;
    long timePlace;
    long timeBreak;
    Vec3d lastHitVec;
    crystalPlaceWait listCrystalsPlaced;
    crystalPlaceWait listCrystalsSecondWait;
    crystalPlaceWait crystalSecondPlace;
    crystalPlaceWait breakPacketLimit;
    crystalPlaceWait existsCrystal;
    crystalPlaceWait crystalSecondBreak;
    crystalPlaceWait attempedCrystalBreak;
    managerClassRenderBlocks managerRenderBlocks;
    crystalPlaced endCrystalPlaced;
    crystalTime crystalPlace;
    EntityEnderCrystal forceBreak;
    BlockPos forceBreakPlace;
    ArrayList<display> toDisplay;
    ArrayList<Long> durationsPlace;
    ArrayList<Long> durationsBreaking;
    ArrayList<packetBlock> packetsBlocks;
    ArrayList<slowBreakPlayers> listPlayersBreak;
    ArrayList<renderClass> toRender;
    ThreadPoolExecutor executor;
    CrystalInfo.PlaceInfo bestPlace;
    CrystalInfo.NewBreakInfo bestBreak;
    float forcePlaceDamage;
    PlayerInfo forcePlaceTarget;
    BlockPos forcePlaceCrystal;
    int tickEat;
    boolean isAnvilling;
    BlockPos crystalAnvil;
    BlockPos blockCity;
    Vec3d movingPlaceNow;
    Vec3d movingBreakNow;
    BlockPos lastBestPlace;
    BlockPos lastBestBreak;
    @EventHandler
    private final Listener<DamageBlockEvent> listener;
    @EventHandler
    private final Listener<OnUpdateWalkingPlayerEvent> onUpdateWalkingPlayerEventListener;
    @EventHandler
    private final Listener<PacketEvent.Send> packetSendListener;
    @EventHandler
    private final Listener<PacketEvent.Receive> packetReceiveListener;
    
    public AutoCrystalRewrite() {
        this.logicTarget = this.registerBoolean("Logic Section", true);
        this.logic = this.registerMode("Logic", (List)Arrays.asList("Place->Break", "Break->Place", "Place", "Break"), "Place->Break", () -> (Boolean)this.logicTarget.getValue());
        this.oneStop = this.registerBoolean("One Stop", false, () -> (boolean)this.logicTarget.getValue() && (((String)this.logic.getValue()).equals("Place->Break") || ((String)this.logic.getValue()).equals("Break->Place")));
        this.targetPlacing = this.registerMode("Target Placing", (List)Arrays.asList("Nearest", "Lowest", "Damage"), "Nearest", () -> (Boolean)this.logicTarget.getValue());
        this.targetBreaking = this.registerMode("Target Breaking", (List)Arrays.asList("Nearest", "Lowest", "Damage"), "Nearest", () -> (Boolean)this.logicTarget.getValue());
        this.renderTarget = this.registerBoolean("RenderTarget", false, () -> (Boolean)this.logicTarget.getValue());
        this.stopGapple = this.registerBoolean("Stop Gapple", false, () -> (Boolean)this.logicTarget.getValue());
        this.tickWaitEat = this.registerInteger("Tick Wait Eat", 4, 0, 10, () -> (boolean)this.logicTarget.getValue() && (boolean)this.stopGapple.getValue());
        this.damageBalance = this.registerDouble("Damage Balance", 2.5, 0.0, 6.0, () -> (Boolean)this.logicTarget.getValue());
        this.newPlace = this.registerBoolean("1.13 mode", false, () -> (Boolean)this.logicTarget.getValue());
        this.ignoreTerrain = this.registerBoolean("Ignore Terrain", false, () -> (Boolean)this.logicTarget.getValue());
        this.bindIgnoreTerrain = this.registerBoolean("Bind IgnoreTerrain", false, () -> (boolean)this.logicTarget.getValue() && (boolean)this.ignoreTerrain.getValue());
        this.entityPredict = this.registerBoolean("Entity Predict", false, () -> (Boolean)this.logicTarget.getValue());
        this.offset = this.registerInteger("OffSet Predict", 0, 0, 10, () -> (boolean)this.logicTarget.getValue() && (boolean)this.entityPredict.getValue());
        this.tryAttack = this.registerInteger("Try Attack", 1, 1, 10, () -> (boolean)this.logicTarget.getValue() && (boolean)this.entityPredict.getValue());
        this.delayAttacks = this.registerInteger("Delay Attacks", 50, 0, 500, () -> (boolean)this.logicTarget.getValue() && (boolean)this.entityPredict.getValue());
        this.midDelayAttacks = this.registerInteger("Mid Delay Attack", 5, 0, 100, () -> (boolean)this.logicTarget.getValue() && (boolean)this.entityPredict.getValue());
        this.ranges = this.registerBoolean("Range Section", false);
        this.rangeEnemyPlace = this.registerDouble("Range Enemy Place", 7.0, 0.0, 12.0, () -> (Boolean)this.ranges.getValue());
        this.rangeEnemyBreaking = this.registerDouble("Range Enemy Breaking", 7.0, 0.0, 12.0, () -> (Boolean)this.ranges.getValue());
        this.placeRange = this.registerDouble("Place Range", 6.0, 0.0, 8.0, () -> (Boolean)this.ranges.getValue());
        this.breakRange = this.registerDouble("Break Range", 6.0, 0.0, 8.0, () -> (Boolean)this.ranges.getValue());
        this.crystalWallPlace = this.registerDouble("Wall Range Place", 3.5, 0.0, 8.0, () -> (Boolean)this.ranges.getValue());
        this.wallrangeBreak = this.registerDouble("Wall Range Break", 3.5, 0.0, 8.0, () -> (Boolean)this.ranges.getValue());
        this.maxYTarget = this.registerInteger("Max Y", 3, 0, 5, () -> (Boolean)this.ranges.getValue());
        this.minYTarget = this.registerInteger("Min Y", 3, 0, 5, () -> (Boolean)this.ranges.getValue());
        this.place = this.registerBoolean("Place Section", false);
        this.placeDelay = this.registerMode("Place Delay", (List)Arrays.asList("Tick", "Time", "Vanilla"), "Tick", () -> (Boolean)this.place.getValue());
        this.tickDelayPlace = this.registerInteger("Tick Delay Place", 0, 0, 20, () -> (boolean)this.place.getValue() && ((String)this.placeDelay.getValue()).equals("Tick"));
        this.timeDelayPlace = this.registerInteger("TIme Delay Place", 0, 0, 2000, () -> (boolean)this.place.getValue() && ((String)this.placeDelay.getValue()).equals("Time"));
        this.vanillaSpeedPlace = this.registerInteger("Vanilla Speed pl", 19, 0, 20, () -> (boolean)this.place.getValue() && ((String)this.placeDelay.getValue()).equals("Vanilla"));
        this.placeOnCrystal = this.registerBoolean("Place On Crystal", false, () -> (Boolean)this.place.getValue());
        this.minDamagePlace = this.registerDouble("Min Damage Place", 5.0, 0.0, 30.0, () -> (Boolean)this.place.getValue());
        this.maxSelfDamagePlace = this.registerDouble("Max Self Damage Place", 12.0, 0.0, 30.0, () -> (Boolean)this.place.getValue());
        this.relativeDamagePlace = this.registerBoolean("Relative Damage Pl", false, () -> (Boolean)this.place.getValue());
        this.relativeDamageValuePlace = this.registerDouble("Damage Relative Damage Pl", 0.8, 0.0, 1.0, () -> (boolean)this.place.getValue() && (boolean)this.relativeDamagePlace.getValue());
        this.armourFacePlace = this.registerInteger("Armour Health%", 20, 0, 100, () -> (Boolean)this.place.getValue());
        this.facePlaceValue = this.registerInteger("FacePlace HP", 8, 0, 36, () -> (Boolean)this.place.getValue());
        this.minFacePlaceDmg = this.registerDouble("FacePlace Dmg", 2.0, 0.0, 10.0, () -> (Boolean)this.place.getValue());
        this.antiSuicidepl = this.registerBoolean("AntiSuicide pl", true, () -> (Boolean)this.place.getValue());
        this.includeCrystalMapping = this.registerBoolean("Include Crystal Mapping", true, () -> (Boolean)this.place.getValue());
        this.limitPacketPlace = this.registerMode("Limit Packet Place", (List)Arrays.asList("None", "Tick", "Time"), "None", () -> (Boolean)this.place.getValue());
        this.limitTickPlace = this.registerInteger("Limit Tick Place", 0, 0, 20, () -> (boolean)this.place.getValue() && ((String)this.limitPacketPlace.getValue()).equals("Tick"));
        this.limitTickTime = this.registerInteger("Limit Time Place", 0, 0, 2000, () -> (boolean)this.place.getValue() && ((String)this.limitPacketPlace.getValue()).equals("Time"));
        this.swingModepl = this.registerMode("Swing Mode pl", (List)Arrays.asList("Client", "Server", "None"), "Server", () -> (Boolean)this.place.getValue());
        this.hideClientpl = this.registerBoolean("Hide Client pl", false, () -> (boolean)this.place.getValue() && ((String)this.swingModepl.getValue()).equals("Server"));
        this.autoWeb = this.registerBoolean("Auto Web", false, () -> (Boolean)this.place.getValue());
        this.stopCrystal = this.registerBoolean("Stop Crystal", true, () -> (boolean)this.place.getValue() && (boolean)this.autoWeb.getValue());
        this.focusWebRotate = this.registerBoolean("Focus Ber Rotate", false, () -> (boolean)this.place.getValue() && (boolean)this.autoWeb.getValue());
        this.onlyAutoWebActive = this.registerBoolean("On AutoWeb active", true, () -> (boolean)this.place.getValue() && (boolean)this.autoWeb.getValue());
        this.switchWeb = this.registerBoolean("Switch Web", false, () -> (boolean)this.place.getValue() && (boolean)this.autoWeb.getValue());
        this.silentSwitchWeb = this.registerBoolean("Silent Switch Web", false, () -> (boolean)this.place.getValue() && (boolean)this.autoWeb.getValue());
        this.switchBackWeb = this.registerBoolean("Switch Back Web", false, () -> (boolean)this.place.getValue() && (boolean)this.autoWeb.getValue() && (boolean)this.switchWeb.getValue() && !(boolean)this.silentSwitchWeb.getValue());
        this.switchBackEnd = this.registerBoolean("Switch Back Web End", false, () -> (boolean)this.place.getValue() && (boolean)this.autoWeb.getValue() && (boolean)this.switchWeb.getValue() && !(boolean)this.silentSwitchWeb.getValue() && (boolean)this.switchBackWeb.getValue());
        this.breakNearCrystal = this.registerBoolean("Break Near Crystal", false, () -> (Boolean)this.place.getValue());
        this.breakSection = this.registerBoolean("Break Section", false);
        this.breakDelay = this.registerMode("Break Delay", (List)Arrays.asList("Tick", "Time", "Vanilla"), "Tick", () -> (Boolean)this.breakSection.getValue());
        this.tickDelayBreak = this.registerInteger("Tick Delay Place", 0, 0, 20, () -> (boolean)this.breakSection.getValue() && ((String)this.breakDelay.getValue()).equals("Tick"));
        this.timeDelayBreak = this.registerInteger("TIme Delay Place", 0, 0, 2000, () -> (boolean)this.breakSection.getValue() && ((String)this.breakDelay.getValue()).equals("Time"));
        this.vanillaSpeedBreak = this.registerInteger("Vanilla Speed br", 19, 0, 20, () -> (boolean)this.breakSection.getValue() && ((String)this.breakDelay.getValue()).equals("Vanilla"));
        this.chooseCrystal = this.registerMode("Choose Type", (List)Arrays.asList("Own", "All", "Smart"), "Smart", () -> (Boolean)this.breakSection.getValue());
        this.minDamageBreak = this.registerDouble("Min Damage Break", 5.0, 0.0, 30.0, () -> (Boolean)this.breakSection.getValue());
        this.maxSelfDamageBreak = this.registerDouble("Max Self Damage Break", 12.0, 0.0, 30.0, () -> (boolean)this.breakSection.getValue() && ((String)this.chooseCrystal.getValue()).equals("Smart"));
        this.relativeDamageBreak = this.registerBoolean("Relative Damage Br", false, () -> (Boolean)this.breakSection.getValue());
        this.relativeDamageValueBreak = this.registerDouble("Damage Relative Damage Br", 0.8, 0.0, 1.0, () -> (boolean)this.breakSection.getValue() && (boolean)this.relativeDamagePlace.getValue());
        this.swingModebr = this.registerMode("Swing Mode br", (List)Arrays.asList("Client", "Server", "None"), "Server", () -> (Boolean)this.breakSection.getValue());
        this.hideClientbr = this.registerBoolean("Hide Client br", false, () -> (boolean)this.breakSection.getValue() && ((String)this.swingModebr.getValue()).equals("Server"));
        this.breakTypeCrystal = this.registerMode("Break Type", (List)Arrays.asList("Packet", "Vanilla"), "Packet", () -> (Boolean)this.breakSection.getValue());
        this.limitBreakPacket = this.registerMode("Limit Break Packet", (List)Arrays.asList("Tick", "Time", "None"), "None", () -> (Boolean)this.breakSection.getValue());
        this.lomitBreakPacketTick = this.registerInteger("Limit Break Tick", 4, 0, 20, () -> (boolean)this.breakSection.getValue() && ((String)this.limitBreakPacket.getValue()).equals("Tick"));
        this.limitBreakPacketTime = this.registerInteger("Limit Break Time", 500, 0, 2000, () -> (boolean)this.breakSection.getValue() && ((String)this.limitBreakPacket.getValue()).equals("Time"));
        this.firstHit = this.registerMode("First Hit", (List)Arrays.asList("Tick", "Time", "None"), "None", () -> (Boolean)this.breakSection.getValue());
        this.firstHitTick = this.registerInteger("Tick First Hit", 0, 0, 20, () -> (boolean)this.breakSection.getValue() && ((String)this.firstHit.getValue()).equals("Tick"));
        this.fitstHitTime = this.registerInteger("TIme First Hit", 0, 0, 2000, () -> (boolean)this.breakSection.getValue() && ((String)this.firstHit.getValue()).equals("Time"));
        this.cancelCrystal = this.registerBoolean("Cancel Crystal", false, () -> (Boolean)this.breakSection.getValue());
        this.setDead = this.registerBoolean("Set Dead", true, () -> (Boolean)this.breakSection.getValue());
        this.placeAfterBreak = this.registerBoolean("Place After", false, () -> (Boolean)this.breakSection.getValue());
        this.instaPlace = this.registerBoolean("Insta Place", false, () -> (boolean)this.breakSection.getValue() && (boolean)this.placeAfterBreak.getValue());
        this.checkinstaPlace = this.registerBoolean("Check Insta Place", false, () -> (boolean)this.breakSection.getValue() && (boolean)this.placeAfterBreak.getValue() && (boolean)this.instaPlace.getValue());
        this.forcePlace = this.registerBoolean("Force Place", false, () -> (boolean)this.breakSection.getValue() && (boolean)this.placeAfterBreak.getValue() && (boolean)this.instaPlace.getValue());
        this.antiWeakness = this.registerBoolean("Anti Weakness", false, () -> (Boolean)this.breakSection.getValue());
        this.slowBreak = this.registerMode("Slow Break", (List)Arrays.asList("None", "Tick", "Time"), "None", () -> (Boolean)this.breakSection.getValue());
        this.speedActivation = this.registerDouble("Speed Activation", 0.5, 0.0, 1.0, () -> (boolean)this.breakSection.getValue() && !((String)this.slowBreak.getValue()).equals("None"));
        this.tickSlowBreak = this.registerInteger("Tick Slow Break", 3, 0, 20, () -> (boolean)this.breakSection.getValue() && ((String)this.slowBreak.getValue()).equals("Tick"));
        this.timeSlowBreak = this.registerInteger("Time Slow Break", 3, 0, 10, () -> (boolean)this.breakSection.getValue() && ((String)this.slowBreak.getValue()).equals("Time"));
        this.predictHit = this.registerBoolean("Predict Hit", false, () -> (Boolean)this.breakSection.getValue());
        this.predictHitDelay = this.registerInteger("Predict Hit Delay", 0, 0, 500, () -> (boolean)this.breakSection.getValue() && (boolean)this.predictHit.getValue());
        this.antiSuicidebr = this.registerBoolean("AntiSuicide br", true, () -> (Boolean)this.breakSection.getValue());
        this.antiCity = this.registerBoolean("Anti City", false, () -> (Boolean)this.breakSection.getValue());
        this.destroyCrystal = this.registerBoolean("Destroy Stuck Crystal", false, () -> (boolean)this.breakSection.getValue() && (boolean)this.antiCity.getValue());
        this.destroyAboveCrystal = this.registerBoolean("Destroy Above Crystal", false, () -> (boolean)this.breakSection.getValue() && (boolean)this.antiCity.getValue());
        this.allowNon1x1 = this.registerBoolean("Allow non 1x1", false, () -> (boolean)this.breakSection.getValue() && (boolean)this.antiCity.getValue());
        this.misc = this.registerBoolean("Misc Section", false);
        this.switchHotbar = this.registerBoolean("Switch Crystal", false, () -> (Boolean)this.misc.getValue());
        this.switchBack = this.registerBoolean("Switch Back", false, () -> (boolean)this.misc.getValue() && (boolean)this.switchHotbar.getValue());
        this.tickSwitchBack = this.registerInteger("Tick Switch Back", 5, 0, 50, () -> (boolean)this.misc.getValue() && (boolean)this.switchHotbar.getValue() && (boolean)this.switchBack.getValue());
        this.waitGappleSwitch = this.registerBoolean("Wait Gapple Switch", false, () -> (boolean)this.misc.getValue() && (boolean)this.switchHotbar.getValue() && (boolean)this.stopGapple.getValue());
        this.silentSwitch = this.registerBoolean("Silent Switch", false, () -> (boolean)this.misc.getValue() && (boolean)this.switchHotbar.getValue());
        this.packetSwitch = this.registerBoolean("Packet Switch", false, () -> (boolean)this.misc.getValue() && (boolean)this.switchHotbar.getValue());
        this.check = this.registerBoolean("Switch Check", false, () -> (boolean)this.misc.getValue() && (boolean)this.switchHotbar.getValue());
        this.renders = this.registerBoolean("Renders", false);
        this.typePlace = this.registerMode("Render Place", (List)Arrays.asList("None", "Outline", "Fill", "Both"), "Both", () -> (Boolean)this.renders.getValue());
        this.placeDimension = this.registerMode("Place Dimension", (List)Arrays.asList("Box", "Flat", "Slab", "Circle"), "Box", () -> (boolean)this.renders.getValue() && !((String)this.typePlace.getValue()).equals("None"));
        this.rangeCirclePl = this.registerDouble("Range Circle Pl", 0.5, 0.1, 1.5, () -> (boolean)this.renders.getValue() && ((String)this.placeDimension.getValue()).equals("Circle"));
        this.slabHeightPlace = this.registerDouble("Slab height Place", 0.2, 0.0, 1.0, () -> (boolean)this.renders.getValue() && ((String)this.placeDimension.getValue()).equals("Slab"));
        this.OutLineSection = this.registerBoolean("OutLine Section Custom pl", false, () -> (((String)this.typePlace.getValue()).equals("Outline") || ((String)this.typePlace.getValue()).equals("Both")) && (boolean)this.renders.getValue());
        this.outlineWidthpl = this.registerInteger("Outline Width", 5, 1, 5, () -> (((String)this.typePlace.getValue()).equals("Outline") || ((String)this.typePlace.getValue()).equals("Both")) && (boolean)this.renders.getValue() && (boolean)this.OutLineSection.getValue());
        this.firstVerticeOutlineBot = this.registerColor("Outline place", new GSColor(255, 16, 19, 50), () -> (((String)this.typePlace.getValue()).equals("Outline") || ((String)this.typePlace.getValue()).equals("Both")) && (boolean)this.OutLineSection.getValue() && (boolean)this.renders.getValue());
        this.FillSection = this.registerBoolean("Fill Section Custom pl", false, () -> (((String)this.typePlace.getValue()).equals("Fill") || ((String)this.typePlace.getValue()).equals("Both")) && (boolean)this.renders.getValue());
        this.firstVerticeFillBot = this.registerColor("Fill place", new GSColor(17, 89, 100, 50), () -> (((String)this.typePlace.getValue()).equals("Fill") || ((String)this.typePlace.getValue()).equals("Both")) && (boolean)this.FillSection.getValue() && (boolean)this.renders.getValue());
        this.firstVerticeFillAlpha = this.registerInteger("Fill place alpha", 50, 0, 255, () -> (((String)this.typePlace.getValue()).equals("Fill") || ((String)this.typePlace.getValue()).equals("Both")) && (boolean)this.FillSection.getValue() && (boolean)this.renders.getValue());
        this.typeBreak = this.registerMode("Render Break", (List)Arrays.asList("None", "Outline", "Fill", "Both"), "Both", () -> (Boolean)this.renders.getValue());
        this.breakDimension = this.registerMode("Break Dimension", (List)Arrays.asList("Box", "Flat", "Slab", "Circle"), "Box", () -> (boolean)this.renders.getValue() & !((String)this.typeBreak.getValue()).equals("None"));
        this.rangeCircleBr = this.registerDouble("Range Circle Br", 0.5, 0.1, 1.5, () -> (boolean)this.renders.getValue() && ((String)this.breakDimension.getValue()).equals("Circle"));
        this.slabHeightBreak = this.registerDouble("Slab height Break", 0.2, 0.0, 1.0, () -> (boolean)this.renders.getValue() && ((String)this.breakDimension.getValue()).equals("Slab"));
        this.OutLineSectionbr = this.registerBoolean("OutLine Section Custom br", false, () -> (((String)this.typeBreak.getValue()).equals("Outline") || ((String)this.typeBreak.getValue()).equals("Both")) && (boolean)this.renders.getValue());
        this.firstVerticeOutlineBotbr = this.registerColor("Outline break", new GSColor(16, 50, 100, 255), () -> (((String)this.typeBreak.getValue()).equals("Outline") || ((String)this.typeBreak.getValue()).equals("Both")) && (boolean)this.OutLineSectionbr.getValue() && (boolean)this.renders.getValue());
        this.FillSectionbr = this.registerBoolean("Fill Section Custom br", false, () -> (((String)this.typeBreak.getValue()).equals("Fill") || ((String)this.typeBreak.getValue()).equals("Both")) && (boolean)this.renders.getValue());
        this.firstVerticeFillBotbr = this.registerColor("Fill break", new GSColor(17, 89, 100, 50), () -> (((String)this.typeBreak.getValue()).equals("Fill") || ((String)this.typeBreak.getValue()).equals("Both")) && (boolean)this.FillSectionbr.getValue() && (boolean)this.renders.getValue());
        this.firstVerticeFillAlphabr = this.registerInteger("Fill break alpha", 50, 0, 255, () -> (((String)this.typeBreak.getValue()).equals("Fill") || ((String)this.typeBreak.getValue()).equals("Both")) && (boolean)this.FillSectionbr.getValue() && (boolean)this.renders.getValue());
        this.showTextpl = this.registerBoolean("Show text Place", true, () -> (Boolean)this.renders.getValue());
        this.colorPlaceText = this.registerColor("Color Place Text", new GSColor(0, 255, 255), () -> (boolean)this.renders.getValue() && (boolean)this.showTextpl.getValue());
        this.textYPlace = this.registerDouble("Text Y Place", 0.5, -1.0, 1.0, () -> (boolean)this.renders.getValue() && (boolean)this.showTextpl.getValue());
        this.showTextbr = this.registerBoolean("Show text Brea", true, () -> (Boolean)this.renders.getValue());
        this.colorBreakText = this.registerColor("Color Break Text", new GSColor(0, 255, 255), () -> (boolean)this.renders.getValue() && (boolean)this.showTextbr.getValue());
        this.textYBreak = this.registerDouble("Text Y Break", 0.5, -1.0, 1.0, () -> (boolean)this.renders.getValue() && (boolean)this.showTextbr.getValue());
        this.movingPlace = this.registerBoolean("Moving Place", false, () -> (Boolean)this.renders.getValue());
        this.movingPlaceSpeed = this.registerDouble("Moving Place Speed", 0.1, 0.01, 0.5, () -> (boolean)this.renders.getValue() && (boolean)this.movingPlace.getValue());
        this.movingBreak = this.registerBoolean("Moving Break", false, () -> (Boolean)this.renders.getValue());
        this.movingBreakSpeed = this.registerDouble("Moving Break Speed", 0.1, 0.01, 0.5, () -> (boolean)this.renders.getValue() && (boolean)this.movingPlace.getValue());
        this.extendedPlace = this.registerInteger("Extended place", 5, 0, 20, () -> (Boolean)this.renders.getValue());
        this.extendedBreak = this.registerInteger("Extended break", 5, 0, 20, () -> (Boolean)this.renders.getValue());
        this.fadeCapl = this.registerBoolean("Fade Ca pl", true, () -> (Boolean)this.renders.getValue());
        this.fadeCabr = this.registerBoolean("Fade Ca br", true, () -> (Boolean)this.renders.getValue());
        this.lifeTime = this.registerInteger("Life Time", 3000, 0, 5000, () -> (boolean)this.renders.getValue() && ((boolean)this.fadeCapl.getValue() || (boolean)this.fadeCabr.getValue()));
        this.placeDominant = this.registerBoolean("Place Dominant", false, () -> (boolean)this.renders.getValue() && (!((String)this.typePlace.getValue()).equals("None") || !((String)this.typeBreak.getValue()).equals("None")));
        this.circleRender = this.registerBoolean("Circle Render", false, () -> (Boolean)this.renders.getValue());
        this.life = this.registerInteger("Life", 300, 0, 1000, () -> (boolean)this.renders.getValue() && (boolean)this.circleRender.getValue());
        this.circleRange = this.registerDouble("Circle Range", 1.0, 0.0, 3.0, () -> (boolean)this.renders.getValue() && (boolean)this.circleRender.getValue());
        this.color = this.registerColor("Color", new GSColor(255, 255, 255, 255), () -> (boolean)this.renders.getValue() && (boolean)this.circleRender.getValue());
        this.desyncCircle = this.registerBoolean("Desync Circle", false, () -> (boolean)this.renders.getValue() && (boolean)this.circleRender.getValue());
        this.stepRainbowCircle = this.registerInteger("Step Rainbow Circle", 1, 1, 100, () -> (boolean)this.renders.getValue() && (boolean)this.circleRender.getValue());
        this.increaseHeight = this.registerBoolean("Increase Height", true, () -> (boolean)this.renders.getValue() && (boolean)this.circleRender.getValue());
        this.speedIncrease = this.registerDouble("Speed Increase", 0.01, 0.3, 0.001, () -> (boolean)this.renders.getValue() && (boolean)this.circleRender.getValue());
        this.predictSection = this.registerBoolean("Predict Section", false);
        this.predictSurround = this.registerBoolean("Predict Surround", false, () -> (Boolean)this.predictSection.getValue());
        this.predictPacketSurround = this.registerBoolean("Predict Packet Surround", false, () -> (boolean)this.predictSection.getValue() && (boolean)this.predictSurround.getValue());
        this.percentSurround = this.registerInteger("Percent Surround", 80, 0, 100, () -> (boolean)this.predictSection.getValue() && (boolean)this.predictSurround.getValue() && !(boolean)this.predictPacketSurround.getValue());
        this.tickPacketBreak = this.registerInteger("Tick Packet Break", 40, 0, 100, () -> (boolean)this.predictSection.getValue() && (boolean)this.predictSurround.getValue() && (boolean)this.predictPacketSurround.getValue());
        this.tickMaxPacketBreak = this.registerInteger("Tick Max Packet Break", 40, 0, 150, () -> (boolean)this.predictSection.getValue() && (boolean)this.predictSurround.getValue() && (boolean)this.predictPacketSurround.getValue());
        this.maxSelfDamageSur = this.registerDouble("Max Self Dam Sur", 7.0, 0.0, 20.0, () -> (boolean)this.predictSection.getValue() && (boolean)this.predictSurround.getValue());
        this.predictSelfPlace = this.registerBoolean("Predict Self Place", false, () -> (Boolean)this.predictSection.getValue());
        this.predictPlaceEnemy = this.registerBoolean("Predict Place Enemy", false, () -> (Boolean)this.predictSection.getValue());
        this.predictSelfDBreaking = this.registerBoolean("Predict Self Break", false, () -> (Boolean)this.predictSection.getValue());
        this.predictBreakingEnemy = this.registerBoolean("Predict Break Enemy", false, () -> (Boolean)this.predictSection.getValue());
        this.pingPredict = this.registerBoolean("Ping Predict", false, () -> (Boolean)this.predictSection.getValue());
        this.tickPredict = this.registerInteger("Tick Predict", 8, 0, 30, () -> (boolean)this.predictSection.getValue() && !(boolean)this.pingPredict.getValue());
        this.calculateYPredict = this.registerBoolean("Calculate Y Predict", true, () -> (Boolean)this.predictSection.getValue());
        this.startDecrease = this.registerInteger("Start Decrease", 39, 0, 200, () -> (boolean)this.predictSection.getValue() && (boolean)this.calculateYPredict.getValue());
        this.exponentStartDecrease = this.registerInteger("Exponent Start", 2, 1, 5, () -> (boolean)this.predictSection.getValue() && (boolean)this.calculateYPredict.getValue());
        this.decreaseY = this.registerInteger("Decrease Y", 2, 1, 5, () -> (boolean)this.predictSection.getValue() && (boolean)this.calculateYPredict.getValue());
        this.exponentDecreaseY = this.registerInteger("Exponent Decrease Y", 1, 1, 3, () -> (boolean)this.predictSection.getValue() && (boolean)this.calculateYPredict.getValue());
        this.increaseY = this.registerInteger("Increase Y", 3, 1, 5, () -> (boolean)this.predictSection.getValue() && (boolean)this.calculateYPredict.getValue());
        this.exponentIncreaseY = this.registerInteger("Exponent Increase Y", 2, 1, 3, () -> (boolean)this.predictSection.getValue() && (boolean)this.calculateYPredict.getValue());
        this.splitXZ = this.registerBoolean("Split XZ", true, () -> (Boolean)this.predictSection.getValue());
        this.widthPredict = this.registerInteger("Line Width", 2, 0, 5, () -> (Boolean)this.predictSection.getValue());
        this.manualOutHole = this.registerBoolean("Manual Out Hole", false, () -> (Boolean)this.predictSection.getValue());
        this.aboveHoleManual = this.registerBoolean("Above Hole Manual", false, () -> (boolean)this.predictSection.getValue() && (boolean)this.manualOutHole.getValue());
        this.stairPredict = this.registerBoolean("Stair Predict", false, () -> (Boolean)this.predictSection.getValue());
        this.nStair = this.registerInteger("N Stair", 2, 1, 4, () -> (boolean)this.predictSection.getValue() && (boolean)this.stairPredict.getValue());
        this.speedActivationStair = this.registerDouble("Speed Activation Stair", 0.11, 0.0, 1.0, () -> (boolean)this.predictSection.getValue() && (boolean)this.stairPredict.getValue());
        this.threading = this.registerBoolean("Threading Section", false);
        this.nThread = this.registerInteger("N Thread", 4, 1, 20, () -> (Boolean)this.threading.getValue());
        this.maxTarget = this.registerInteger("Max Target", 5, 1, 30, () -> (Boolean)this.threading.getValue());
        this.placeTimeout = this.registerInteger("Place Timeout", 100, 0, 1000, () -> (Boolean)this.threading.getValue());
        this.predictPlaceTimeout = this.registerInteger("Predict Place Timeout", 100, 0, 1000, () -> (Boolean)this.threading.getValue());
        this.breakTimeout = this.registerInteger("Break Timeout", 100, 0, 1000, () -> (Boolean)this.threading.getValue());
        this.predictBreakTimeout = this.registerInteger("Predict Break Timeout", 100, 0, 1000, () -> (Boolean)this.threading.getValue());
        this.strict = this.registerBoolean("Strict Section", false);
        this.raytrace = this.registerBoolean("Raytrace", false, () -> (Boolean)this.strict.getValue());
        this.rotate = this.registerBoolean("Rotate", false, () -> (Boolean)this.strict.getValue());
        this.preRotate = this.registerBoolean("Pre Rotate", false, () -> (boolean)this.strict.getValue() && (boolean)this.rotate.getValue());
        this.tickAfterRotation = this.registerInteger("Tick After Rotation", 0, 0, 10, () -> (boolean)this.strict.getValue() && (boolean)this.rotate.getValue());
        this.focusPlaceType = this.registerMode("Focus Place Type", (List)Arrays.asList("Disabled", "Tick", "Time"), "Disabled", () -> (Boolean)this.strict.getValue());
        this.recalculateDamage = this.registerBoolean("Recalculate Damage", true, () -> (boolean)this.strict.getValue() && !((String)this.focusPlaceType.getValue()).equals("Disabled"));
        this.tickWaitFocusPlace = this.registerInteger("Tick Wait Focus Pl", 4, 0, 20, () -> (boolean)this.strict.getValue() && ((String)this.focusPlaceType.getValue()).equals("Tick"));
        this.timeWaitFocusPlace = this.registerInteger("Time Wait Focus Pl", 100, 0, 2000, () -> (boolean)this.strict.getValue() && ((String)this.focusPlaceType.getValue()).equals("Time"));
        this.yawCheck = this.registerBoolean("Yaw Check", false, () -> (Boolean)this.strict.getValue());
        this.yawStep = this.registerInteger("Yaw Step", 40, 0, 180, () -> (boolean)this.strict.getValue() && (boolean)this.yawCheck.getValue());
        this.pitchCheck = this.registerBoolean("Pitch Check", false, () -> (Boolean)this.strict.getValue());
        this.pitchStep = this.registerInteger("Pitch Step", 40, 0, 180, () -> (boolean)this.strict.getValue() && (boolean)this.pitchCheck.getValue());
        this.placeStrictDirection = this.registerBoolean("Place Strict Predict", false, () -> (boolean)this.strict.getValue() && ((boolean)this.pitchCheck.getValue() || (boolean)this.yawCheck.getValue()));
        this.predictBreakRotation = this.registerBoolean("Predict Break Rotation", false, () -> (boolean)this.strict.getValue() && ((boolean)this.pitchCheck.getValue() || (boolean)this.yawCheck.getValue()));
        this.blockRotation = this.registerBoolean("Block Rotation", true, () -> (boolean)this.strict.getValue() && ((boolean)this.pitchCheck.getValue() || (boolean)this.yawCheck.getValue()));
        this.debugMenu = this.registerBoolean("Debug Section", false);
        this.timeCalcPlacement = this.registerBoolean("Calc Placement Time", false, () -> (Boolean)this.debugMenu.getValue());
        this.timeCalcBreaking = this.registerBoolean("Calc Breaking Time", false, () -> (Boolean)this.debugMenu.getValue());
        this.nCalc = this.registerInteger("N Calc", 100, 1, 1000, () -> (boolean)this.debugMenu.getValue() && ((boolean)this.timeCalcPlacement.getValue() || (boolean)this.timeCalcBreaking.getValue()));
        this.debugPredict = this.registerBoolean("Debug Predict", false, () -> (Boolean)this.debugMenu.getValue());
        this.showPredictions = this.registerBoolean("Show Predictions", false, () -> (boolean)this.debugMenu.getValue() && (boolean)this.debugPredict.getValue());
        this.hudDisplayShow = this.registerBoolean("Hud Display Section", false);
        this.showPlaceName = this.registerBoolean("Show Place Name", false, () -> (Boolean)this.hudDisplayShow.getValue());
        this.showPlaceDamage = this.registerBoolean("Show Place Damage", false, () -> (Boolean)this.hudDisplayShow.getValue());
        this.showPlaceCrystalsSecond = this.registerBoolean("Show c/s place", false, () -> (Boolean)this.hudDisplayShow.getValue());
        this.cleanPlace = this.registerBoolean("Clean Place", true, () -> (Boolean)this.hudDisplayShow.getValue());
        this.showBreakName = this.registerBoolean("Show break Name", false, () -> (Boolean)this.hudDisplayShow.getValue());
        this.showBreakDamage = this.registerBoolean("Show break Damage", false, () -> (Boolean)this.hudDisplayShow.getValue());
        this.showBreakCrystalsSecond = this.registerBoolean("Show c/s break", false, () -> (Boolean)this.hudDisplayShow.getValue());
        this.cleanBreak = this.registerBoolean("Clean break", true, () -> (Boolean)this.hudDisplayShow.getValue());
        this.letterIgnoreTerrain = this.registerString("Ignore Terrain", "");
        this.forceFacePlace = this.registerString("Force FacePlace", "");
        this.anvilCity = this.registerString("Anvil City", "");
        this.placeAnvil = this.registerInteger("Place Anvil", 10, 0, 100);
        this.tick = 0;
        this.tickBeforePlace = 0;
        this.highestId = -100000;
        this.timerPlace = new Timer();
        this.timerBreak = new Timer();
        this.timePlace = 0L;
        this.timeBreak = 0L;
        this.listCrystalsPlaced = new crystalPlaceWait();
        this.listCrystalsSecondWait = new crystalPlaceWait();
        this.crystalSecondPlace = new crystalPlaceWait();
        this.breakPacketLimit = new crystalPlaceWait();
        this.existsCrystal = new crystalPlaceWait();
        this.crystalSecondBreak = new crystalPlaceWait();
        this.attempedCrystalBreak = new crystalPlaceWait();
        this.managerRenderBlocks = new managerClassRenderBlocks();
        this.endCrystalPlaced = new crystalPlaced();
        this.crystalPlace = null;
        this.forceBreak = null;
        this.forceBreakPlace = null;
        this.toDisplay = new ArrayList<display>();
        this.durationsPlace = new ArrayList<Long>();
        this.durationsBreaking = new ArrayList<Long>();
        this.packetsBlocks = new ArrayList<packetBlock>();
        this.listPlayersBreak = new ArrayList<slowBreakPlayers>();
        this.toRender = new ArrayList<renderClass>();
        this.executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
        this.bestPlace = new CrystalInfo.PlaceInfo(-100.0f, (PlayerInfo)null, (BlockPos)null, 100.0);
        this.bestBreak = new CrystalInfo.NewBreakInfo(-100.0f, (PlayerInfo)null, (EntityEnderCrystal)null, 100.0);
        this.forcePlaceCrystal = null;
        this.tickEat = 0;
        this.isAnvilling = false;
        this.crystalAnvil = null;
        this.blockCity = null;
        this.movingPlaceNow = new Vec3d(-1.0, -1.0, -1.0);
        this.movingBreakNow = new Vec3d(-1.0, -1.0, -1.0);
        this.lastBestPlace = null;
        this.lastBestBreak = null;
        this.listener = (Listener<DamageBlockEvent>)new Listener(event -> {
            try {
                if (AutoCrystalRewrite.mc.world == null || AutoCrystalRewrite.mc.player == null || !(boolean)this.predictPacketSurround.getValue()) {
                    return;
                }
                if (!this.canBreak(event.getBlockPos()) || event.getBlockPos() == null) {
                    return;
                }
                final BlockPos blockPos = event.getBlockPos();
                if (AutoCrystalRewrite.mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR) {
                    return;
                }
                if (this.packetsBlocks.stream().anyMatch(e -> this.sameBlockPos(e.block, blockPos))) {
                    return;
                }
                if (blockPos.getDistance((int)AutoCrystalRewrite.mc.player.posX, (int)AutoCrystalRewrite.mc.player.posY, (int)AutoCrystalRewrite.mc.player.posZ) <= (double)this.placeRange.getValue()) {
                    final float armourPercent = (int)this.armourFacePlace.getValue() / 100.0f;
                    for (final Vec3i surround : new Vec3i[] { new Vec3i(1, 0, 0), new Vec3i(-1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(0, 0, -1) }) {
                        final List<Entity> players = new ArrayList<Entity>(AutoCrystalRewrite.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos.add(surround))));
                        PlayerInfo info = null;
                        for (final Entity pl : players) {
                            if (pl instanceof EntityPlayer && pl != AutoCrystalRewrite.mc.player && pl.posY + 0.5 >= blockPos.y) {
                                final EntityPlayer temp;
                                info = new PlayerInfo(temp = (EntityPlayer)pl, armourPercent, (float)temp.getTotalArmorValue(), (float)temp.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
                                break;
                            }
                        }
                        boolean quit = false;
                        if (info != null) {
                            BlockPos coords = null;
                            double damage = Double.MIN_VALUE;
                            final Block toReplace = BlockUtil.getBlock(blockPos);
                            AutoCrystalRewrite.mc.world.setBlockToAir(blockPos);
                            for (final Vec3i placement : new Vec3i[] { new Vec3i(1, -1, 0), new Vec3i(-1, -1, 0), new Vec3i(0, -1, 1), new Vec3i(0, -1, -1) }) {
                                final BlockPos temp2;
                                if (CrystalUtil.canPlaceCrystal(temp2 = blockPos.add(placement), (boolean)this.newPlace.getValue())) {
                                    if (DamageUtil.calculateDamage(temp2.getX() + 0.5, temp2.getY() + 1.0, temp2.getZ() + 0.5, (Entity)AutoCrystalRewrite.mc.player, (boolean)this.ignoreTerrain.getValue()) + (double)this.damageBalance.getValue() < (double)this.maxSelfDamageSur.getValue()) {
                                        if (!(boolean)this.placeOnCrystal.getValue() && !this.isCrystalHere(temp2)) {
                                            quit = true;
                                            break;
                                        }
                                        final float damagePlayer = DamageUtil.calculateDamageThreaded(temp2.getX() + 0.5, temp2.getY() + 1.0, temp2.getZ() + 0.5, info, (boolean)this.ignoreTerrain.getValue());
                                        if (damagePlayer > damage) {
                                            damage = damagePlayer;
                                            coords = temp2;
                                            quit = true;
                                        }
                                    }
                                }
                            }
                            AutoCrystalRewrite.mc.world.setBlockState(blockPos, toReplace.getDefaultState());
                            if (coords != null) {
                                this.packetsBlocks.add(new packetBlock(coords, (int)this.tickPacketBreak.getValue(), (int)this.tickMaxPacketBreak.getValue()));
                            }
                            if (quit) {
                                break;
                            }
                        }
                    }
                }
            }
            catch (Exception ex) {}
        }, new Predicate[0]);
        this.onUpdateWalkingPlayerEventListener = (Listener<OnUpdateWalkingPlayerEvent>)new Listener(event -> {
            if (event.getPhase() != Phase.PRE || !(boolean)this.rotate.getValue() || this.lastHitVec == null || AutoCrystalRewrite.mc.world == null || AutoCrystalRewrite.mc.player == null) {
                return;
            }
            if (this.tick++ > (int)this.tickAfterRotation.getValue()) {
                this.lastHitVec = null;
                this.tick = 0;
                this.isRotating = false;
                final double n = Double.MAX_VALUE;
                this.xPlayerRotation = n;
                this.yPlayerRotation = n;
            }
            else {
                final Vec2f rotationWanted = RotationUtil.getRotationTo(this.lastHitVec);
                Vec2f nowRotation;
                if ((boolean)this.yawCheck.getValue() || (boolean)this.pitchCheck.getValue()) {
                    if (this.yPlayerRotation == Double.MIN_VALUE) {
                        this.yPlayerRotation = rotationWanted.y;
                    }
                    else {
                        final double distanceDo = rotationWanted.y - this.yPlayerRotation;
                        final int direction = (distanceDo > 0.0) ? 1 : -1;
                        if (Math.abs(distanceDo) > (int)this.pitchStep.getValue()) {
                            this.yPlayerRotation = RotationUtil.normalizeAngle(this.yPlayerRotation + (int)this.pitchStep.getValue() * direction);
                            this.tick = 0;
                        }
                        else {
                            this.yPlayerRotation = rotationWanted.y;
                        }
                    }
                    if (this.xPlayerRotation == Double.MIN_VALUE) {
                        this.xPlayerRotation = rotationWanted.x;
                    }
                    else {
                        double distanceDo = rotationWanted.x - this.xPlayerRotation;
                        if (Math.abs(distanceDo) > 180.0) {
                            distanceDo = RotationUtil.normalizeAngle(distanceDo);
                        }
                        final int direction = (distanceDo > 0.0) ? 1 : -1;
                        if (Math.abs(distanceDo) > (int)this.yawStep.getValue()) {
                            this.xPlayerRotation = RotationUtil.normalizeAngle(this.xPlayerRotation + (int)this.yawStep.getValue() * direction);
                            this.tick = 0;
                        }
                        else {
                            this.xPlayerRotation = rotationWanted.x;
                        }
                    }
                    nowRotation = new Vec2f((float)this.xPlayerRotation, (float)this.yPlayerRotation);
                }
                else {
                    nowRotation = rotationWanted;
                }
                final PlayerPacket packet = new PlayerPacket((Module)this, nowRotation);
                PlayerPacketManager.INSTANCE.addPacket(packet);
            }
        }, new Predicate[0]);
        this.packetSendListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (AutoCrystalRewrite.mc.world == null || AutoCrystalRewrite.mc.player == null) {
                return;
            }
            if ((boolean)this.entityPredict.getValue() && event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                final CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
                if (this.bestPlace.crystal != null && this.sameBlockPos(packet.getPos(), this.bestPlace.crystal)) {
                    int idx = 0;
                    for (int i = 1 - (int)this.offset.getValue(); i <= (int)this.tryAttack.getValue(); ++i) {
                        this.updateHighestID();
                        final java.util.Timer t = new java.util.Timer();
                        final int finalI = i;
                        t.schedule(new TimerTask() {
                            final /* synthetic */ int val$finalI;
                            final /* synthetic */ java.util.Timer val$t;
                            
                            @Override
                            public void run() {
                                AutoCrystalRewrite.this.attackID(AutoCrystalRewrite.this.highestId + this.val$finalI);
                                this.val$t.cancel();
                            }
                        }, (int)this.delayAttacks.getValue() + ++idx * (int)this.midDelayAttacks.getValue());
                    }
                }
            }
        }, new Predicate[0]);
        this.packetReceiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            try {
                if (AutoCrystalRewrite.mc.world == null || AutoCrystalRewrite.mc.player == null) {
                    return;
                }
                if (event.getPacket() instanceof SPacketSpawnObject) {
                    final SPacketSpawnObject SpawnObject = (SPacketSpawnObject)event.getPacket();
                    if (this.entityPredict.getValue()) {
                        this.checkID(SpawnObject.getEntityID());
                    }
                    if (SpawnObject.getType() == 51) {
                        final double[] positions = { SpawnObject.getX() - 0.5, SpawnObject.getY() - 0.5, SpawnObject.getZ() - 0.5 };
                        if (!((String)this.limitPacketPlace.getValue()).equals("None")) {
                            this.listCrystalsPlaced.removeCrystal(positions[0], positions[1], positions[2]);
                        }
                        if (this.crystalPlace != null && this.sameBlockPos(new BlockPos(positions[0], positions[1], positions[2]), this.crystalPlace.posCrystal)) {
                            this.crystalPlace = null;
                        }
                        if ((boolean)this.showPlaceCrystalsSecond.getValue() && this.listCrystalsSecondWait.removeCrystal(positions[0], positions[1], positions[2])) {
                            this.crystalSecondPlace.addCrystal(null, 1000);
                        }
                        final String s = (String)this.firstHit.getValue();
                        switch (s) {
                            case "Tick": {
                                this.existsCrystal.addCrystal(new BlockPos(positions[0], positions[1], positions[2]), 0, (int)this.firstHitTick.getValue());
                                break;
                            }
                            case "Time": {
                                this.existsCrystal.addCrystal(new BlockPos(positions[0], positions[1], positions[2]), (int)this.fitstHitTime.getValue());
                                break;
                            }
                        }
                        if (this.predictHit.getValue()) {
                            boolean hit = false;
                            final String s2 = (String)this.chooseCrystal.getValue();
                            switch (s2) {
                                case "All": {
                                    hit = true;
                                    break;
                                }
                                case "Own": {
                                    if (this.endCrystalPlaced.hasCrystal(new BlockPos(positions[0], positions[1], positions[2]))) {
                                        hit = true;
                                        break;
                                    }
                                    break;
                                }
                                case "Smart": {
                                    if (this.sameBlockPos(this.getTargetPlacing((String)this.targetPlacing.getValue()).crystal, new BlockPos(positions[0], positions[1], positions[2]))) {
                                        hit = true;
                                        break;
                                    }
                                    break;
                                }
                            }
                            if (hit) {
                                final java.util.Timer t = new java.util.Timer();
                                t.schedule(new TimerTask() {
                                    final /* synthetic */ SPacketSpawnObject val$SpawnObject;
                                    final /* synthetic */ java.util.Timer val$t;
                                    
                                    @Override
                                    public void run() {
                                        final CPacketUseEntity attack = new CPacketUseEntity();
                                        ((AccessorCPacketAttack)attack).setId(this.val$SpawnObject.getEntityID());
                                        ((AccessorCPacketAttack)attack).setAction(CPacketUseEntity.Action.ATTACK);
                                        AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)attack);
                                        AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                                        this.val$t.cancel();
                                    }
                                }, (int)this.predictHitDelay.getValue());
                            }
                        }
                    }
                }
                else if (event.getPacket() instanceof SPacketSoundEffect) {
                    final SPacketSoundEffect packetSoundEffect = (SPacketSoundEffect)event.getPacket();
                    if (packetSoundEffect.getCategory() == SoundCategory.BLOCKS && packetSoundEffect.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                        for (final Entity entity : new ArrayList<Entity>(AutoCrystalRewrite.mc.world.loadedEntityList)) {
                            if (entity instanceof EntityEnderCrystal) {
                                if ((boolean)this.setDead.getValue() && entity.getDistanceSq(packetSoundEffect.getX(), packetSoundEffect.getY(), packetSoundEffect.getZ()) <= 36.0) {
                                    try {
                                        entity.setDead();
                                    }
                                    catch (Exception ex) {}
                                }
                                if (this.attempedCrystalBreak.removeCrystal(packetSoundEffect.getX(), packetSoundEffect.getY(), packetSoundEffect.getZ())) {
                                    this.crystalSecondBreak.addCrystal(null, 1000);
                                }
                                if (this.crystalAnvil == null || !this.sameBlockPos(this.crystalAnvil, new BlockPos(packetSoundEffect.getX(), packetSoundEffect.getY(), packetSoundEffect.getZ()))) {
                                    continue;
                                }
                                final int slot = InventoryUtil.findFirstBlockSlot((Class)Blocks.ANVIL.getClass(), 0, 8);
                                if (slot == -1 || !(BlockUtil.getBlock(this.blockCity) instanceof BlockAir)) {
                                    continue;
                                }
                                final int oldSlot = AutoCrystalRewrite.mc.player.inventory.currentItem;
                                this.switchTo(slot);
                                PlacementUtil.place(this.blockCity, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), false);
                                this.switchTo(oldSlot);
                            }
                        }
                        this.breakPacketLimit.removeCrystal(packetSoundEffect.getX(), packetSoundEffect.getY(), packetSoundEffect.getZ());
                    }
                }
                else if (event.getPacket() instanceof SPacketSpawnExperienceOrb) {
                    this.checkID(((SPacketSpawnExperienceOrb)event.getPacket()).getEntityID());
                }
                else if (event.getPacket() instanceof SPacketSpawnPlayer) {
                    this.checkID(((SPacketSpawnPlayer)event.getPacket()).getEntityID());
                }
                else if (event.getPacket() instanceof SPacketSpawnGlobalEntity) {
                    this.checkID(((SPacketSpawnGlobalEntity)event.getPacket()).getEntityId());
                }
                else if (event.getPacket() instanceof SPacketSpawnPainting) {
                    this.checkID(((SPacketSpawnPainting)event.getPacket()).getEntityID());
                }
                else if (event.getPacket() instanceof SPacketSpawnMob) {
                    this.checkID(((SPacketSpawnMob)event.getPacket()).getEntityID());
                }
            }
            catch (ConcurrentModificationException e) {
                PistonCrystal.printDebug("Prevented a crash from the ca. If this repet, spam me in dm", true);
                final Logger LOGGER = LogManager.getLogger("GameSense");
                LOGGER.error("[AutoCrystalRewrite] error during the creation of the structure.");
                if (e.getMessage() != null) {
                    LOGGER.error("[AutoCrystalRewrite] error message: " + e.getClass().getName() + " " + e.getMessage());
                }
                else {
                    LOGGER.error("[AutoCrystalRewrite] cannot find the cause");
                }
                final int i5 = 0;
                if (e.getStackTrace().length != 0) {
                    LOGGER.error("[AutoCrystalRewrite] StackTrace Start");
                    for (final StackTraceElement errorMess : e.getStackTrace()) {
                        LOGGER.error("[AutoCrystalRewrite] " + errorMess.toString());
                    }
                    LOGGER.error("[AutoCrystalRewrite] StackTrace End");
                }
            }
        }, new Predicate[0]);
    }
    
    public void onEnable() {
        final int tickBeforePlace = 0;
        this.tick = tickBeforePlace;
        this.tickBeforeBreak = tickBeforePlace;
        this.tickBeforePlace = tickBeforePlace;
        final long n = 0L;
        this.timeBreak = n;
        this.timePlace = n;
        final int n2 = 0;
        this.breakRender = n2;
        this.placeRender = n2;
        final int n3 = -1;
        this.oldSlotObby = n3;
        this.slotWebBack = n3;
        this.tickSwitch = n3;
        this.oldSlotBackWeb = n3;
        final boolean checkTimeBreak = this.checkTimeBreak;
        this.brokenCrystal = checkTimeBreak;
        this.placedCrystal = checkTimeBreak;
        this.checkTimePlace = checkTimeBreak;
        final double n4 = Double.MAX_VALUE;
        this.xPlayerRotation = n4;
        this.yPlayerRotation = n4;
        this.forceBreak = null;
        this.forceBreakPlace = null;
        this.lastHitVec = null;
        this.bestPlace = new CrystalInfo.PlaceInfo(-100.0f, (PlayerInfo)null, (BlockPos)null, 100.0);
        this.bestBreak = new CrystalInfo.NewBreakInfo(-100.0f, (PlayerInfo)null, (EntityEnderCrystal)null, 100.0);
        final boolean b = false;
        this.isAnvilling = b;
        this.isRotating = b;
        AutoCrystalRewrite.stopAC = false;
        this.crystalAnvil = null;
        this.highestId = 0;
    }
    
    public void onDisable() {
        this.bestPlace = new CrystalInfo.PlaceInfo(-100.0f, (PlayerInfo)null, (BlockPos)null, 100.0);
        this.bestBreak = new CrystalInfo.NewBreakInfo(-100.0f, (PlayerInfo)null, (EntityEnderCrystal)null, 100.0);
        this.movingPlaceNow = new Vec3d(-1.0, -1.0, -1.0);
        this.movingBreakNow = new Vec3d(-1.0, -1.0, -1.0);
    }
    
    boolean stopGapple(final boolean decrease) {
        if (this.stopGapple.getValue()) {
            Item item;
            if (AutoCrystalRewrite.mc.player.isHandActive() && ((item = AutoCrystalRewrite.mc.player.getHeldItemMainhand().getItem()) == Items.GOLDEN_APPLE || item == Items.CHORUS_FRUIT || (item = AutoCrystalRewrite.mc.player.getHeldItemOffhand().getItem()) == Items.GOLDEN_APPLE || item == Items.CHORUS_FRUIT)) {
                if (decrease) {
                    this.tickEat = (int)this.tickWaitEat.getValue();
                }
                return true;
            }
            if (this.tickEat > 0) {
                if (decrease) {
                    --this.tickEat;
                }
                return true;
            }
        }
        return false;
    }
    
    void updateCounters() {
        this.listCrystalsPlaced.updateCrystals();
        this.listCrystalsSecondWait.updateCrystals();
        this.crystalSecondPlace.updateCrystals();
        this.endCrystalPlaced.updateCrystals();
        this.existsCrystal.updateCrystals();
        for (int i = 0; i < this.packetsBlocks.size(); ++i) {
            if (!this.packetsBlocks.get(i).update()) {
                this.packetsBlocks.remove(i);
                --i;
            }
        }
        for (int i = 0; i < this.toRender.size(); ++i) {
            if (this.toRender.get(i).update()) {
                this.toRender.remove(i);
                --i;
            }
        }
        this.breakPacketLimit.updateCrystals();
        this.listPlayersBreak.removeIf(slowBreakPlayers::update);
        this.crystalSecondBreak.updateCrystals();
        this.attempedCrystalBreak.updateCrystals();
        this.managerRenderBlocks.update((int)this.lifeTime.getValue());
    }
    
    public void onUpdate() {
        if (AutoCrystalRewrite.mc.world == null || AutoCrystalRewrite.mc.player == null || AutoCrystalRewrite.mc.player.isDead || AutoCrystalRewrite.stopAC) {
            return;
        }
        this.toDisplay.clear();
        this.updateCounters();
        if (this.entityPredict.getValue()) {
            this.updateHighestID();
        }
        if (this.stopGapple(true)) {
            return;
        }
        try {
            final String s = (String)this.logic.getValue();
            switch (s) {
                case "Place->Break": {
                    if (!this.placeCrystals() || !(boolean)this.oneStop.getValue()) {
                        this.breakCrystals();
                        break;
                    }
                    break;
                }
                case "Break->Place": {
                    if (!this.breakCrystals() || !(boolean)this.oneStop.getValue()) {
                        this.placeCrystals();
                        break;
                    }
                    break;
                }
                case "Place": {
                    this.placeCrystals();
                    if (this.bestBreak.crystal != null) {
                        this.bestBreak = new CrystalInfo.NewBreakInfo(0.0f, (PlayerInfo)null, (EntityEnderCrystal)null, 0.0);
                        break;
                    }
                    break;
                }
                case "Break": {
                    this.breakCrystals();
                    if (this.bestPlace.crystal != null) {
                        this.bestPlace = new CrystalInfo.PlaceInfo(0.0f, (PlayerInfo)null, (BlockPos)null, 0.0);
                        break;
                    }
                    break;
                }
            }
        }
        catch (Exception e) {
            PistonCrystal.printDebug("Prevented a crash from the ca. If this repet, spam me in dm", true);
            final Logger LOGGER = LogManager.getLogger("GameSense");
            LOGGER.error("[AutoCrystalRewrite] error during the creation of the structure.");
            if (e.getMessage() != null) {
                LOGGER.error("[AutoCrystalRewrite] error message: " + e.getClass().getName() + " " + e.getMessage());
            }
            else {
                LOGGER.error("[AutoCrystalRewrite] cannot find the cause");
            }
            if (e.getStackTrace().length != 0) {
                LOGGER.error("[AutoCrystalRewrite] StackTrace Start");
                for (final StackTraceElement errorMess : e.getStackTrace()) {
                    LOGGER.error("[AutoCrystalRewrite] " + errorMess.toString());
                }
                LOGGER.error("[AutoCrystalRewrite] StackTrace End");
            }
        }
        PlacementUtil.stopSneaking();
    }
    
    public String getHudInfo() {
        final StringBuilder t = new StringBuilder();
        boolean place = false;
        if (this.bestPlace.target != null) {
            if (this.showPlaceName.getValue()) {
                t.append(ChatFormatting.GRAY + "[").append(ChatFormatting.WHITE + (this.cleanPlace.getValue() ? "" : "Place Name: ")).append(this.bestPlace.target.entity.getName());
                place = true;
            }
            if (this.showPlaceDamage.getValue()) {
                if (!place) {
                    t.append(ChatFormatting.GRAY + "[").append(ChatFormatting.WHITE + (this.cleanPlace.getValue() ? "" : "Place damage: ")).append((int)this.bestPlace.damage);
                    place = true;
                }
                else {
                    t.append(this.cleanPlace.getValue() ? " " : " Damage: ").append((int)this.bestPlace.damage);
                }
            }
        }
        int temp;
        if ((boolean)this.showPlaceCrystalsSecond.getValue() && (temp = this.crystalSecondPlace.countCrystals()) > 0) {
            if (!place) {
                t.append(ChatFormatting.GRAY + "[").append(ChatFormatting.WHITE + (this.cleanPlace.getValue() ? "Place c/s: " : "")).append(temp);
                place = true;
            }
            else {
                t.append(this.cleanPlace.getValue() ? " c/s: " : " ").append(temp);
            }
        }
        if (this.bestBreak.target != null) {
            if (this.showBreakName.getValue()) {
                if (!place) {
                    t.append(ChatFormatting.GRAY + "[").append(ChatFormatting.WHITE + (this.cleanBreak.getValue() ? "" : "Break Name: ")).append(this.bestBreak.target.entity.getName());
                    place = true;
                }
                else {
                    t.append(this.cleanPlace.getValue() ? " " : " Name: ").append(this.bestBreak.target.entity.getName());
                }
            }
            if (this.showBreakDamage.getValue()) {
                if (!place) {
                    t.append(ChatFormatting.GRAY + "[").append(ChatFormatting.WHITE + (this.cleanBreak.getValue() ? "" : "Break damage: ")).append((int)this.bestBreak.damage);
                    place = true;
                }
                else {
                    t.append(this.cleanPlace.getValue() ? " " : " Damage: ").append((int)this.bestBreak.damage);
                }
            }
        }
        if ((boolean)this.showBreakCrystalsSecond.getValue() && (temp = this.crystalSecondBreak.countCrystals()) > 0) {
            if (!place) {
                t.append(ChatFormatting.GRAY + "[").append(ChatFormatting.WHITE + (this.cleanBreak.getValue() ? "Break b/s: " : "")).append(temp);
                place = true;
            }
            else {
                t.append(this.cleanPlace.getValue() ? " b/s: " : " ").append(temp);
            }
        }
        if (place) {
            t.append(ChatFormatting.GRAY + "]");
        }
        return t.toString();
    }
    
    CrystalInfo.PlaceInfo getTargetPlacing(final String mode) {
        if (this.anvilCity.getText().length() > 0) {
            if (Keyboard.isKeyDown(KeyBoardClass.getKeyFromChar(this.anvilCity.getText().charAt(0))) && this.bestBreak.damage > 5.0f) {
                if (this.crystalAnvil != null && this.blockCity != null && BlockUtil.getBlock(this.blockCity) instanceof BlockAir) {
                    final int slot = InventoryUtil.findFirstBlockSlot((Class)Blocks.ANVIL.getClass(), 0, 8);
                    if (slot != -1) {
                        final int oldSlot = AutoCrystalRewrite.mc.player.inventory.currentItem;
                        this.switchTo(slot);
                        PlacementUtil.place(this.blockCity, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), false);
                        this.switchTo(oldSlot);
                    }
                }
                if (this.crystalAnvil != null) {
                    if (!AutoCrystalRewrite.mc.world.getLoadedEntityList().stream().filter(e -> e instanceof EntityEnderCrystal && e.getPosition() == this.crystalAnvil).findAny().isPresent()) {
                        return new CrystalInfo.PlaceInfo(8.0f, (PlayerInfo)null, this.crystalAnvil, 6.0);
                    }
                    this.crystalAnvil = null;
                }
            }
            else {
                this.isAnvilling = false;
                this.crystalAnvil = null;
            }
        }
        else {
            this.isAnvilling = false;
            this.crystalAnvil = null;
        }
        final PredictUtil.PredictSettings settings = new PredictUtil.PredictSettings(((boolean)this.pingPredict.getValue()) ? (getPing() / 50) : ((int)this.tickPredict.getValue()), (boolean)this.calculateYPredict.getValue(), (int)this.startDecrease.getValue(), (int)this.exponentStartDecrease.getValue(), (int)this.decreaseY.getValue(), (int)this.exponentDecreaseY.getValue(), (int)this.increaseY.getValue(), (int)this.exponentIncreaseY.getValue(), (boolean)this.splitXZ.getValue(), (int)this.widthPredict.getValue(), (boolean)this.debugPredict.getValue(), (boolean)this.showPredictions.getValue(), (boolean)this.manualOutHole.getValue(), (boolean)this.aboveHoleManual.getValue(), (boolean)this.stairPredict.getValue(), (int)this.nStair.getValue(), (double)this.speedActivationStair.getValue());
        final int nThread = (int)this.nThread.getValue();
        final float armourPercent = (int)this.armourFacePlace.getValue() / 100.0f;
        final double minDamage = (double)this.minDamagePlace.getValue();
        final double minFacePlaceDamage = (double)this.minFacePlaceDmg.getValue();
        double minFacePlaceHp = (int)this.facePlaceValue.getValue();
        if (this.forceFacePlace.getText().length() > 0 && Keyboard.isKeyDown(KeyBoardClass.getKeyFromChar(this.forceFacePlace.getText().charAt(0)))) {
            minFacePlaceHp = 36.0;
        }
        final double enemyRangeSQ = (double)this.rangeEnemyPlace.getValue() * (double)this.rangeEnemyPlace.getValue();
        final double maxSelfDamage = (double)this.maxSelfDamagePlace.getValue();
        final double wallRangePlaceSQ = (double)this.crystalWallPlace.getValue() * (double)this.crystalWallPlace.getValue();
        final boolean raytraceValue = (boolean)this.raytrace.getValue();
        final int maxYTarget = (int)this.maxYTarget.getValue();
        final int minYTarget = (int)this.minYTarget.getValue();
        final int placeTimeout = (int)this.placeTimeout.getValue();
        boolean ignoreTerrainValue = false;
        if (this.ignoreTerrain.getValue()) {
            if (this.bindIgnoreTerrain.getValue()) {
                if (this.letterIgnoreTerrain.getText().length() > 0 && Keyboard.isKeyDown(KeyBoardClass.getKeyFromChar(this.letterIgnoreTerrain.getText().charAt(0)))) {
                    ignoreTerrainValue = true;
                }
            }
            else {
                ignoreTerrainValue = true;
            }
        }
        final boolean relativeDamage = (boolean)this.relativeDamagePlace.getValue();
        final double valueRelativeDamage = (double)this.relativeDamageValuePlace.getValue();
        CrystalInfo.PlaceInfo bestPlace = new CrystalInfo.PlaceInfo(-100.0f, (PlayerInfo)null, (BlockPos)null, 100.0);
        final ArrayList<BlockPos> webRemoved = new ArrayList<BlockPos>();
        switch (mode) {
            case "Lowest":
            case "Nearest": {
                final EntityPlayer targetEP = mode.equals("Lowest") ? this.getBasicPlayers(enemyRangeSQ).min((x, y) -> (int)x.getHealth()).orElse(null) : this.getBasicPlayers(enemyRangeSQ).min(Comparator.comparingDouble(x -> x.getDistanceSq((Entity)AutoCrystalRewrite.mc.player))).orElse(null);
                if (targetEP == null) {
                    break;
                }
                if (BlockUtil.getBlock(targetEP.posX, targetEP.posY, targetEP.posZ) instanceof BlockWeb) {
                    AutoCrystalRewrite.mc.world.setBlockToAir(new BlockPos(targetEP.posX, targetEP.posY, targetEP.posZ));
                    webRemoved.add(new BlockPos(targetEP.posX, targetEP.posY, targetEP.posZ));
                }
                final PlayerInfo player = new PlayerInfo((EntityPlayer)(((boolean)this.predictSelfPlace.getValue()) ? PredictUtil.predictPlayer((EntityPlayer)AutoCrystalRewrite.mc.player, settings) : AutoCrystalRewrite.mc.player), false, (float)AutoCrystalRewrite.mc.player.getTotalArmorValue(), (float)AutoCrystalRewrite.mc.player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
                final List<List<PositionInfo>> possibleCrystals = this.getPossibleCrystalsPlacing(player, maxSelfDamage, raytraceValue, wallRangePlaceSQ, ignoreTerrainValue);
                if (possibleCrystals == null) {
                    break;
                }
                final PlayerInfo target = new PlayerInfo(targetEP, armourPercent, (float)targetEP.getTotalArmorValue(), (float)targetEP.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
                bestPlace = this.calcualteBestPlace(nThread, possibleCrystals, player.entity.posX, player.entity.posY, player.entity.posZ, target, minDamage, minFacePlaceHp, minFacePlaceDamage, maxSelfDamage, maxYTarget, minYTarget, placeTimeout, new CrystalInfo.PlaceInfo(0.0f, (PlayerInfo)null, (BlockPos)null, 0.0), ignoreTerrainValue, relativeDamage, valueRelativeDamage);
                break;
            }
            case "Damage": {
                List<EntityPlayer> players = this.getBasicPlayers(enemyRangeSQ).sorted(new Sortbyroll()).collect((Collector<? super EntityPlayer, ?, List<EntityPlayer>>)Collectors.toList());
                if (players.size() == 0) {
                    break;
                }
                for (final EntityPlayer et : players) {
                    if (BlockUtil.getBlock(et.posX, et.posY, et.posZ) instanceof BlockWeb) {
                        AutoCrystalRewrite.mc.world.setBlockToAir(new BlockPos(et.posX, et.posY, et.posZ));
                        webRemoved.add(new BlockPos(et.posX, et.posY, et.posZ));
                    }
                }
                if (this.predictPlaceEnemy.getValue()) {
                    players = this.getPlayersThreaded(nThread, players, settings, (int)this.predictPlaceTimeout.getValue());
                }
                final PlayerInfo player = new PlayerInfo((EntityPlayer)(((boolean)this.predictSelfPlace.getValue()) ? PredictUtil.predictPlayer((EntityPlayer)AutoCrystalRewrite.mc.player, settings) : AutoCrystalRewrite.mc.player), false, (float)AutoCrystalRewrite.mc.player.getTotalArmorValue(), (float)AutoCrystalRewrite.mc.player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
                final List<List<PositionInfo>> possibleCrystals = this.getPossibleCrystalsPlacing(player, maxSelfDamage, raytraceValue, wallRangePlaceSQ, ignoreTerrainValue);
                if (possibleCrystals == null) {
                    break;
                }
                int count = 0;
                for (final EntityPlayer playerTemp : players) {
                    if (count++ >= (int)this.maxTarget.getValue()) {
                        break;
                    }
                    final PlayerInfo target = new PlayerInfo(playerTemp, armourPercent, (float)playerTemp.getTotalArmorValue(), (float)playerTemp.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
                    bestPlace = this.calcualteBestPlace(nThread, possibleCrystals, player.entity.posX, player.entity.posY, player.entity.posZ, target, minDamage, minFacePlaceHp, minFacePlaceDamage, maxSelfDamage, maxYTarget, minYTarget, placeTimeout, bestPlace, ignoreTerrainValue, relativeDamage, valueRelativeDamage);
                }
                break;
            }
        }
        for (final BlockPos web : webRemoved) {
            AutoCrystalRewrite.mc.world.setBlockState(web, Blocks.WEB.getDefaultState());
        }
        if (bestPlace.target != null) {
            this.placeRender = 0;
            boolean found = false;
            for (final renderClass rend : this.toRender) {
                if (rend.reset(bestPlace.target.entity.entityId)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                this.toRender.add(new renderClass(bestPlace.target.entity.entityId, (int)this.life.getValue(), this.color.getValue(), (double)this.circleRange.getValue(), (boolean)this.desyncCircle.getValue(), (int)this.stepRainbowCircle.getValue(), (double)this.circleRange.getValue(), (int)this.stepRainbowCircle.getValue(), (boolean)this.increaseHeight.getValue(), (double)this.speedIncrease.getValue()));
            }
        }
        return bestPlace;
    }
    
    CrystalInfo.PlaceInfo calcualteBestPlace(final int nThread, final List<List<PositionInfo>> possibleCrystals, final double posX, final double posY, final double posZ, final PlayerInfo target, final double minDamage, final double minFacePlaceHp, final double minFacePlaceDamage, final double maxSelfDamage, final int maxYTarget, final int minYTarget, final int placeTimeout, final CrystalInfo.PlaceInfo old, final boolean ignoreTerrain, final boolean relativeDamage, final double valueRelativeDamage) {
        final Collection<Future<?>> futures = new LinkedList<Future<?>>();
        for (int i = 0; i < nThread; ++i) {
            final int finalI = i;
            futures.add(this.executor.submit(() -> this.calculateBestPlaceTarget(possibleCrystals.get(finalI), posX, posY, posZ, target, minDamage, minFacePlaceHp, minFacePlaceDamage, maxSelfDamage, maxYTarget, minYTarget, ignoreTerrain, relativeDamage, valueRelativeDamage)));
        }
        final Stack<CrystalInfo.PlaceInfo> results = new Stack<CrystalInfo.PlaceInfo>();
        for (final Future<?> future : futures) {
            try {
                final CrystalInfo.PlaceInfo temp = (CrystalInfo.PlaceInfo)future.get(placeTimeout, TimeUnit.MILLISECONDS);
                if (temp == null) {
                    continue;
                }
                results.add(temp);
            }
            catch (ExecutionException | InterruptedException | TimeoutException ex2) {
                final Exception ex;
                final Exception e = ex;
                e.printStackTrace();
            }
        }
        results.add(old);
        return this.getResultPlace(results);
    }
    
    CrystalInfo.PlaceInfo getResultPlace(final Stack<CrystalInfo.PlaceInfo> result) {
        CrystalInfo.PlaceInfo returnValue = new CrystalInfo.PlaceInfo(0.0f, (PlayerInfo)null, (BlockPos)null, 100.0);
        while (!result.isEmpty()) {
            final CrystalInfo.PlaceInfo now = result.pop();
            if (now.damage == returnValue.damage) {
                if (now.distance >= returnValue.distance) {
                    continue;
                }
                returnValue = now;
            }
            else {
                if (now.damage <= returnValue.damage) {
                    continue;
                }
                returnValue = now;
            }
        }
        return returnValue;
    }
    
    List<List<PositionInfo>> getPossibleCrystalsPlacing(final PlayerInfo self, final double maxSelfDamage, final boolean raytrace, final double wallRangeSQ, final boolean ignoreTerrain) {
        final List<PositionInfo> damagePos = new ArrayList<PositionInfo>();
        final float damage;
        RayTraceResult result;
        final List<PositionInfo> list;
        (this.includeCrystalMapping.getValue() ? CrystalUtil.findCrystalBlocksExcludingCrystals(((Double)this.placeRange.getValue()).floatValue(), (boolean)this.newPlace.getValue()) : CrystalUtil.findCrystalBlocks(((Double)this.placeRange.getValue()).floatValue(), (boolean)this.newPlace.getValue())).forEach(crystal -> {
            damage = (float)(DamageUtil.calculateDamageThreaded(crystal.getX() + 0.5, crystal.getY() + 1.0, crystal.getZ() + 0.5, self, ignoreTerrain) + (double)this.damageBalance.getValue());
            if (damage < maxSelfDamage && (!(boolean)this.antiSuicidepl.getValue() || damage < self.health)) {
                result = AutoCrystalRewrite.mc.world.rayTraceBlocks(new Vec3d(AutoCrystalRewrite.mc.player.posX, AutoCrystalRewrite.mc.player.posY + AutoCrystalRewrite.mc.player.getEyeHeight(), AutoCrystalRewrite.mc.player.posZ), new Vec3d(crystal.getX() + 0.5, crystal.getY() + 1.0, crystal.getZ() + 0.5));
                if (result == null || this.sameBlockPos(result.getBlockPos(), crystal) || (!raytrace && AutoCrystalRewrite.mc.player.getDistanceSq(crystal) <= wallRangeSQ)) {
                    list.add(new PositionInfo(crystal, (double)damage));
                }
            }
            return;
        });
        return this.splitList(damagePos, (int)this.nThread.getValue());
    }
    
    List<List<PositionInfo>> splitList(final List<PositionInfo> start, final int nThreads) {
        if (nThreads == 1) {
            return new ArrayList<List<PositionInfo>>() {
                {
                    this.add(start);
                }
            };
        }
        final int count;
        if ((count = start.size()) == 0) {
            return null;
        }
        final List<List<PositionInfo>> output = new ArrayList<List<PositionInfo>>(nThreads);
        for (int i = 0; i < nThreads; ++i) {
            output.add(new ArrayList<PositionInfo>());
        }
        for (int i = 0; i < count; ++i) {
            output.get(i % nThreads).add(start.get(i));
        }
        return output;
    }
    
    CrystalInfo.PlaceInfo calculateBestPlaceTarget(final List<PositionInfo> possibleLocations, final double x, final double y, final double z, final PlayerInfo target, final double minDamage, final double minFacePlaceHealth, final double minFacePlaceDamage, final double maxSelfDamage, final int maxYTarget, final int minYTarget, final boolean ignoreTerrain, final boolean relativeDamage, final double valueRelativeDamage) {
        PositionInfo best = new PositionInfo();
        for (final PositionInfo crystal : possibleLocations) {
            double temp;
            if ((temp = target.entity.posY - crystal.pos.getY() - 1.0) > 0.0) {
                if (temp > minYTarget) {
                    continue;
                }
            }
            else if (temp < -maxYTarget) {
                continue;
            }
            final float currentDamage = DamageUtil.calculateDamageThreaded(crystal.pos.getX() + 0.5, crystal.pos.getY() + 1.0, crystal.pos.getZ() + 0.5, target, ignoreTerrain);
            if (currentDamage == best.damage) {
                if (best.pos != null && (temp = crystal.pos.distanceSq(x, y, z)) != best.distance && currentDamage / maxSelfDamage <= best.rapp && temp >= best.distance) {
                    continue;
                }
                best = crystal;
                best.setEnemyDamage((double)currentDamage);
                best.distance = target.entity.getDistanceSq(crystal.pos.getX() + 0.5, crystal.pos.getY() + 1.0, crystal.pos.getZ() + 0.5);
                best.distancePlayer = AutoCrystalRewrite.mc.player.getDistanceSq(crystal.pos.getX() + 0.5, crystal.pos.getY() + 1.0, crystal.pos.getZ() + 0.5);
            }
            else {
                if (currentDamage <= best.damage) {
                    continue;
                }
                if (relativeDamage && crystal.getSelfDamage() / currentDamage > valueRelativeDamage) {
                    continue;
                }
                best = crystal;
                best.setEnemyDamage((double)currentDamage);
                best.distance = target.entity.getDistanceSq(crystal.pos.getX() + 0.5, crystal.pos.getY() + 1.0, crystal.pos.getZ() + 0.5);
                best.distancePlayer = AutoCrystalRewrite.mc.player.getDistanceSq(crystal.pos.getX() + 0.5, crystal.pos.getY() + 1.0, crystal.pos.getZ() + 0.5);
            }
        }
        if (best.pos != null && (best.damage >= minDamage || ((target.health <= minFacePlaceHealth || target.lowArmour) && best.damage >= minFacePlaceDamage))) {
            return new CrystalInfo.PlaceInfo((float)best.damage, target, best.pos, best.distancePlayer);
        }
        return null;
    }
    
    boolean canStartPlacing() {
        final String s = (String)this.placeDelay.getValue();
        switch (s) {
            case "Tick": {
                if (this.tickBeforePlace == 0) {
                    return true;
                }
                --this.tickBeforePlace;
                break;
            }
            case "Time": {
                if (!this.checkTimePlace) {
                    return true;
                }
                if (System.currentTimeMillis() - this.timePlace >= (int)this.timeDelayPlace.getValue()) {
                    this.checkTimePlace = false;
                    return true;
                }
                break;
            }
            case "Vanilla": {
                if (this.timerPlace.getTimePassed() / 50L >= 20 - (int)this.vanillaSpeedPlace.getValue()) {
                    this.timerPlace.reset();
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    boolean placeCrystals() {
        if (this.placedCrystal) {
            return this.placedCrystal = false;
        }
        if (!this.canStartPlacing()) {
            return false;
        }
        final EnumHand hand = this.getHandCrystal();
        if (hand == null) {
            return false;
        }
        if (this.crystalPlace != null) {
            if (this.raytrace.getValue()) {
                final RayTraceResult result = AutoCrystalRewrite.mc.world.rayTraceBlocks(new Vec3d(AutoCrystalRewrite.mc.player.posX, AutoCrystalRewrite.mc.player.posY + AutoCrystalRewrite.mc.player.getEyeHeight(), AutoCrystalRewrite.mc.player.posZ), new Vec3d(this.crystalPlace.posCrystal.getX() + 0.5, this.crystalPlace.posCrystal.getY() + 0.5, this.crystalPlace.posCrystal.getZ() + 0.5));
                if (result == null || result.sideHit == null) {
                    this.crystalPlace = null;
                }
            }
            if (this.crystalPlace != null && (boolean)this.recalculateDamage.getValue() && this.isCrystalGood(this.crystalPlace.posCrystal) == null) {
                this.crystalPlace = null;
            }
            if (this.crystalPlace != null && this.crystalPlace.posCrystal != null) {
                if (this.crystalPlace.isReady()) {
                    this.crystalPlace = null;
                }
                else {
                    if (this.isPlacingWeb()) {
                        return true;
                    }
                    if (this.crystalPlace != null) {
                        return this.placeCrystal(this.crystalPlace.posCrystal, hand, false);
                    }
                }
            }
        }
        long inizio = 0L;
        if (this.timeCalcPlacement.getValue()) {
            inizio = System.currentTimeMillis();
        }
        boolean instaPlaceBol = false;
        if (this.forcePlaceCrystal != null && (boolean)this.forcePlace.getValue()) {
            this.bestPlace = new CrystalInfo.PlaceInfo(this.forcePlaceDamage, this.forcePlaceTarget, this.forcePlaceCrystal, -10.0);
            this.placeRender = 0;
            instaPlaceBol = true;
        }
        else {
            this.bestPlace = this.getTargetPlacing((String)this.targetPlacing.getValue());
            if (this.forcePlaceCrystal != null && this.bestPlace.crystal != null && this.sameBlockPos(this.forcePlaceCrystal, this.bestPlace.crystal)) {
                instaPlaceBol = true;
            }
            this.placeRender = 0;
        }
        if (this.timeCalcPlacement.getValue()) {
            final long fine = System.currentTimeMillis();
            this.durationsPlace.add(fine - inizio);
            if (this.durationsPlace.size() > (int)this.nCalc.getValue()) {
                double sum = this.durationsPlace.stream().mapToDouble(a -> a).sum();
                sum /= (int)this.nCalc.getValue();
                this.durationsPlace.clear();
                PistonCrystal.printDebug(String.format("N: %d Value: %f", this.nCalc.getValue(), sum), false);
            }
        }
        if ((boolean)this.instaPlace.getValue() && this.bestPlace.target == null && this.forcePlaceCrystal != null) {
            this.bestPlace = new CrystalInfo.PlaceInfo(this.forcePlaceDamage, this.forcePlaceTarget, this.forcePlaceCrystal, -10.0);
            instaPlaceBol = true;
            this.placeRender = 0;
        }
        this.forcePlaceCrystal = null;
        if (this.bestPlace.crystal != null) {
            if (this.showTextpl.getValue()) {
                this.toDisplay.add(new display(String.valueOf((int)this.bestPlace.damage), this.bestPlace.crystal, this.colorPlaceText.getValue(), (double)this.textYPlace.getValue()));
            }
            return this.isPlacingWeb() || this.placeCrystal(this.bestPlace.crystal, hand, instaPlaceBol);
        }
        if ((boolean)this.switchBack.getValue() && this.oldSlotObby != -1) {
            if (this.tickSwitch > 0) {
                --this.tickSwitch;
            }
            else if (this.tickSwitch == 0) {
                AutoCrystalRewrite.mc.player.inventory.currentItem = this.oldSlotObby;
                this.tickSwitch = -1;
            }
        }
        return false;
    }
    
    boolean isPlacingWeb() {
        if ((boolean)this.autoWeb.getValue() && this.bestPlace != null && this.bestPlace.target != null && (!(boolean)this.onlyAutoWebActive.getValue() || ModuleManager.isModuleEnabled((Class)AutoWeb.class))) {
            if (BlockUtil.getBlock(this.bestPlace.getTarget().posX, this.bestPlace.getTarget().posY, this.bestPlace.getTarget().posZ) instanceof BlockAir && this.placeWeb(new BlockPos(this.bestPlace.getTarget().posX, this.bestPlace.getTarget().posY, this.bestPlace.getTarget().posZ)) && (boolean)this.stopCrystal.getValue()) {
                return true;
            }
        }
        else if (this.oldSlotBackWeb != -1) {
            AutoCrystalRewrite.mc.player.inventory.currentItem = this.oldSlotBackWeb;
            this.oldSlotBackWeb = -1;
        }
        return false;
    }
    
    boolean placeWeb(final BlockPos target) {
        final EnumFacing side = BlockUtil.getPlaceableSide(target);
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = target.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockUtil.canBeClicked(neighbour)) {
            return false;
        }
        int oldSlot = -1;
        if (!(AutoCrystalRewrite.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBlock) || ((ItemBlock)AutoCrystalRewrite.mc.player.inventory.getCurrentItem().getItem()).getBlock() != Blocks.WEB) {
            final int slot = InventoryUtil.findFirstBlockSlot((Class)Blocks.WEB.getClass(), 0, 8);
            oldSlot = AutoCrystalRewrite.mc.player.inventory.currentItem;
            if (slot == -1) {
                return false;
            }
            if (this.silentSwitchWeb.getValue()) {
                this.switchTo(slot);
            }
            else if (this.switchBackEnd.getValue()) {
                this.oldSlotBackWeb = oldSlot;
                AutoCrystalRewrite.mc.player.inventory.currentItem = slot;
                oldSlot = -1;
            }
            else if (this.switchBackWeb.getValue()) {
                AutoCrystalRewrite.mc.player.inventory.currentItem = slot;
            }
            else {
                if (!(boolean)this.switchWeb.getValue()) {
                    return false;
                }
                AutoCrystalRewrite.mc.player.inventory.currentItem = slot;
                oldSlot = -1;
            }
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = AutoCrystalRewrite.mc.world.getBlockState(neighbour).getBlock();
        if (this.preRotate.getValue()) {
            BlockUtil.faceVectorPacketInstant(hitVec, Boolean.valueOf(true));
        }
        if (this.focusWebRotate.getValue()) {
            this.lastHitVec = hitVec;
            this.tick = 0;
        }
        boolean isSneaking = false;
        if (BlockUtil.blackList.contains(neighbourBlock) || BlockUtil.shulkerList.contains(neighbourBlock)) {
            AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoCrystalRewrite.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            isSneaking = true;
        }
        AutoCrystalRewrite.mc.playerController.processRightClickBlock(AutoCrystalRewrite.mc.player, AutoCrystalRewrite.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        AutoCrystalRewrite.mc.player.swingArm(EnumHand.MAIN_HAND);
        if (isSneaking) {
            AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoCrystalRewrite.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        if (oldSlot != -1) {
            if (this.silentSwitchWeb.getValue()) {
                this.switchTo(oldSlot);
            }
            else {
                AutoCrystalRewrite.mc.player.inventory.currentItem = oldSlot;
            }
        }
        AutoCrystalRewrite.mc.playerController.updateController();
        return true;
    }
    
    EntityPlayer isCrystalGood(final BlockPos crystal) {
        final float damage;
        if ((damage = (float)(DamageUtil.calculateDamage(crystal.getX() + 0.5, crystal.getY() + 1.0, crystal.getZ() + 0.5, (Entity)AutoCrystalRewrite.mc.player, (boolean)this.ignoreTerrain.getValue()) + (double)this.damageBalance.getValue())) >= (double)this.maxSelfDamagePlace.getValue() && (!(boolean)this.antiSuicidepl.getValue() || damage < PlayerUtil.getHealth())) {
            return null;
        }
        final double rangeSQ = (double)this.rangeEnemyPlace.getValue() * (double)this.rangeEnemyPlace.getValue();
        final Optional<EntityPlayer> a = (Optional<EntityPlayer>)AutoCrystalRewrite.mc.world.playerEntities.stream().filter(entity -> !EntityUtil.basicChecksEntity(entity)).filter(entity -> entity.getHealth() > 0.0f).filter(entity -> AutoCrystalRewrite.mc.player.getDistanceSq(entity) <= rangeSQ).filter(entity -> DamageUtil.calculateDamage(crystal.getX() + 0.5, crystal.getY() + 1.0, crystal.getZ() + 0.5, entity, (boolean)this.ignoreTerrain.getValue()) >= (double)this.minDamagePlace.getValue()).findAny();
        return a.orElse(null);
    }
    
    EnumHand getHandCrystal() {
        this.slotChange = -1;
        if (AutoCrystalRewrite.mc.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal) {
            return EnumHand.OFF_HAND;
        }
        if (AutoCrystalRewrite.mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal) {
            return EnumHand.MAIN_HAND;
        }
        if (this.switchHotbar.getValue()) {
            final int slot = InventoryUtil.findFirstItemSlot((Class)ItemEndCrystal.class, 0, 8);
            if (slot != -1) {
                if ((boolean)this.waitGappleSwitch.getValue() && AutoCrystalRewrite.mc.player.inventory.getStackInSlot(AutoCrystalRewrite.mc.player.inventory.currentItem).getItem() == Items.GOLDEN_APPLE && this.oldSlot == slot) {
                    return null;
                }
                this.slotChange = slot;
                return EnumHand.MAIN_HAND;
            }
        }
        return null;
    }
    
    boolean placeCrystal(final BlockPos pos, final EnumHand handSwing, final boolean instaPlace) {
        if (!(boolean)this.placeOnCrystal.getValue() && !this.isCrystalHere(pos) && !instaPlace) {
            return false;
        }
        final BlockPos posUp = pos.up();
        final AxisAlignedBB box = new AxisAlignedBB((double)posUp.getX(), (double)posUp.getY(), (double)posUp.getZ(), posUp.getX() + 1.0, posUp.getY() + 2.0, posUp.getZ() + 1.0);
        final List<Entity> a = (List<Entity>)AutoCrystalRewrite.mc.world.getEntitiesWithinAABB((Class)Entity.class, box, entity -> entity instanceof EntityEnderCrystal && !this.sameBlockPos(entity.getPosition().add(0, -1, 0), pos));
        if (a.size() > 0) {
            if (this.breakNearCrystal.getValue()) {
                this.forceBreak = (EntityEnderCrystal)a.get(0);
                this.forceBreakPlace = pos;
            }
            return false;
        }
        if (this.listCrystalsPlaced.CrystalExists(pos) != -1) {
            return false;
        }
        if (this.rotate.getValue()) {
            this.lastHitVec = new Vec3d((Vec3i)pos).add(0.5, 1.0, 0.5);
            this.tick = 0;
            if ((boolean)this.yawCheck.getValue() || (boolean)this.pitchCheck.getValue()) {
                final Vec2f rotationWanted = RotationUtil.getRotationTo(this.lastHitVec);
                if (!(boolean)this.blockRotation.getValue() || !this.isRotating) {
                    this.yPlayerRotation = (this.pitchCheck.getValue() ? ((this.yPlayerRotation == Double.MAX_VALUE) ? AutoCrystalRewrite.mc.player.getPitchYaw().x : this.yPlayerRotation) : Double.MIN_VALUE);
                    this.xPlayerRotation = (this.yawCheck.getValue() ? ((this.xPlayerRotation == Double.MAX_VALUE) ? RotationUtil.normalizeAngle(AutoCrystalRewrite.mc.player.getPitchYaw().y) : this.xPlayerRotation) : Double.MIN_VALUE);
                    this.isRotating = true;
                }
                if ((boolean)this.rotate.getValue() && (boolean)this.placeStrictDirection.getValue()) {
                    if (this.yawCheck.getValue()) {
                        double distanceDo = rotationWanted.x - this.xPlayerRotation;
                        if (Math.abs(distanceDo) > 180.0) {
                            distanceDo = RotationUtil.normalizeAngle(distanceDo);
                        }
                        if (Math.abs(distanceDo) > (int)this.yawStep.getValue()) {
                            return true;
                        }
                    }
                    if (this.pitchCheck.getValue()) {
                        final double distanceDo = rotationWanted.y - this.yPlayerRotation;
                        if (Math.abs(distanceDo) > (int)this.pitchStep.getValue()) {
                            return true;
                        }
                    }
                }
                else if (this.xPlayerRotation != rotationWanted.x || this.yPlayerRotation != rotationWanted.y) {
                    return true;
                }
            }
        }
        int oldslot = -1;
        if (handSwing == EnumHand.MAIN_HAND) {
            if (this.slotChange != -1) {
                if (this.silentSwitch.getValue()) {
                    oldslot = AutoCrystalRewrite.mc.player.inventory.currentItem;
                    this.switchTo(this.slotChange);
                }
                else if (this.slotChange != AutoCrystalRewrite.mc.player.inventory.currentItem) {
                    if (this.switchBack.getValue()) {
                        this.tickSwitch = (int)this.tickSwitchBack.getValue();
                        this.oldSlotObby = AutoCrystalRewrite.mc.player.inventory.currentItem;
                    }
                    this.switchTo(this.slotChange);
                }
            }
            else if (this.oldSlot != AutoCrystalRewrite.mc.player.inventory.currentItem) {
                this.switchTo(AutoCrystalRewrite.mc.player.inventory.currentItem);
            }
        }
        if (this.raytrace.getValue()) {
            final RayTraceResult result = AutoCrystalRewrite.mc.world.rayTraceBlocks(new Vec3d(AutoCrystalRewrite.mc.player.posX, AutoCrystalRewrite.mc.player.posY + AutoCrystalRewrite.mc.player.getEyeHeight(), AutoCrystalRewrite.mc.player.posZ), new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));
            if (result == null || result.sideHit == null) {
                return false;
            }
            final EnumFacing enumFacing = result.sideHit;
            if ((boolean)this.rotate.getValue() && (boolean)this.preRotate.getValue()) {
                final Vec2f rot = RotationUtil.getRotationTo(this.lastHitVec);
                AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rot.x, rot.y, AutoCrystalRewrite.mc.player.onGround));
            }
            AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, enumFacing, handSwing, 0.0f, 0.0f, 0.0f));
        }
        else if (this.placeStrictDirection.getValue()) {
            EnumFacing result2;
            if (AutoCrystalRewrite.mc.player.posY + 0.63 > pos.getY()) {
                result2 = EnumFacing.UP;
            }
            else {
                final double xDiff = pos.getX() - AutoCrystalRewrite.mc.player.posX + 0.5;
                final double zDiff = pos.getZ() - AutoCrystalRewrite.mc.player.posZ + 0.5;
                result2 = ((Math.abs(xDiff) > Math.abs(zDiff)) ? ((xDiff > 0.0) ? EnumFacing.WEST : EnumFacing.EAST) : ((zDiff > 0.0) ? EnumFacing.NORTH : EnumFacing.SOUTH));
            }
            if ((boolean)this.rotate.getValue() && (boolean)this.preRotate.getValue()) {
                final Vec2f rot2 = RotationUtil.getRotationTo(this.lastHitVec);
                AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rot2.x, rot2.y, AutoCrystalRewrite.mc.player.onGround));
            }
            AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, result2, handSwing, 0.0f, 0.0f, 0.0f));
        }
        else {
            if ((boolean)this.rotate.getValue() && (boolean)this.preRotate.getValue()) {
                final Vec2f rot3 = RotationUtil.getRotationTo(this.lastHitVec);
                AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rot3.x, rot3.y, AutoCrystalRewrite.mc.player.onGround));
            }
            if (pos.getY() == 255) {
                AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.DOWN, handSwing, 0.0f, 0.0f, 0.0f));
            }
            else {
                AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.UP, handSwing, 0.0f, 0.0f, 0.0f));
            }
        }
        if (!((String)this.swingModepl.getValue()).equals("None")) {
            this.swingArm((String)this.swingModepl.getValue(), (boolean)this.hideClientpl.getValue(), handSwing);
        }
        if (this.slotChange != -1 && oldslot != -1 && (boolean)this.silentSwitch.getValue()) {
            this.switchTo(oldslot);
        }
        this.tickBeforePlace = (int)this.tickDelayPlace.getValue();
        this.checkTimePlace = true;
        this.timePlace = System.currentTimeMillis();
        final String s = (String)this.limitPacketPlace.getValue();
        switch (s) {
            case "Tick": {
                this.listCrystalsPlaced.addCrystal(pos, 0, (int)this.limitTickPlace.getValue());
                break;
            }
            case "Time": {
                this.listCrystalsPlaced.addCrystal(pos, (int)this.limitTickTime.getValue());
                break;
            }
        }
        this.endCrystalPlaced.addCrystal(pos);
        if (this.showPlaceCrystalsSecond.getValue()) {
            this.listCrystalsSecondWait.addCrystal(pos, 2000);
        }
        if (this.crystalPlace == null) {
            final String s2 = (String)this.focusPlaceType.getValue();
            switch (s2) {
                case "Tick": {
                    this.crystalPlace = new crystalTime(pos, 0, (int)this.tickWaitFocusPlace.getValue());
                    break;
                }
                case "Time": {
                    this.crystalPlace = new crystalTime(pos, (int)this.timeWaitFocusPlace.getValue());
                    break;
                }
            }
        }
        return true;
    }
    
    boolean isCrystalHere(final BlockPos pos) {
        final BlockPos posUp = pos.up();
        final AxisAlignedBB box = new AxisAlignedBB((double)posUp.getX(), (double)posUp.getY(), (double)posUp.getZ(), posUp.getX() + 1.0, posUp.getY() + 2.0, posUp.getZ() + 1.0);
        return AutoCrystalRewrite.mc.world.getEntitiesWithinAABB((Class)Entity.class, box, entity -> entity instanceof EntityEnderCrystal && this.sameBlockPos(entity.getPosition(), pos)).isEmpty();
    }
    
    CrystalInfo.NewBreakInfo getTargetBreaking(final String mode) {
        final PredictUtil.PredictSettings settings = new PredictUtil.PredictSettings(((boolean)this.pingPredict.getValue()) ? (getPing() / 50) : ((int)this.tickPredict.getValue()), (boolean)this.calculateYPredict.getValue(), (int)this.startDecrease.getValue(), (int)this.exponentStartDecrease.getValue(), (int)this.decreaseY.getValue(), (int)this.exponentDecreaseY.getValue(), (int)this.increaseY.getValue(), (int)this.exponentIncreaseY.getValue(), (boolean)this.splitXZ.getValue(), (int)this.widthPredict.getValue(), (boolean)this.debugPredict.getValue(), (boolean)this.showPredictions.getValue(), (boolean)this.manualOutHole.getValue(), (boolean)this.aboveHoleManual.getValue(), (boolean)this.stairPredict.getValue(), (int)this.nStair.getValue(), (double)this.speedActivationStair.getValue());
        final int nThread = (int)this.nThread.getValue();
        final double enemyRangeSQ = (double)this.rangeEnemyBreaking.getValue() * (double)this.rangeEnemyBreaking.getValue();
        final double maxSelfDamage = (double)this.maxSelfDamageBreak.getValue();
        final boolean rayTrace = (boolean)this.raytrace.getValue();
        final double wallRangeSQ = (double)this.wallrangeBreak.getValue() * (double)this.wallrangeBreak.getValue();
        final float armourPercent = (int)this.armourFacePlace.getValue() / 100.0f;
        final int maxYTarget = (int)this.maxYTarget.getValue();
        final int minYTarget = (int)this.minYTarget.getValue();
        double minFacePlaceHp = (int)this.facePlaceValue.getValue();
        if (this.forceFacePlace.getText().length() > 0 && Keyboard.isKeyDown(KeyBoardClass.getKeyFromChar(this.forceFacePlace.getText().charAt(0)))) {
            minFacePlaceHp = 36.0;
        }
        final double minFacePlaceDamage = (double)this.minFacePlaceDmg.getValue();
        final double minDamage = (double)this.minDamageBreak.getValue();
        final double rangeSQ = (double)this.breakRange.getValue() * (double)this.breakRange.getValue();
        final int breakTimeout = (int)this.breakTimeout.getValue();
        final boolean relativeDamage = (boolean)this.relativeDamageBreak.getValue();
        final double valueRelativeDamage = (double)this.relativeDamageValueBreak.getValue();
        boolean ignoreTerrainValue = false;
        final boolean antiSuicide = (boolean)this.antiSuicidebr.getValue();
        if (this.ignoreTerrain.getValue()) {
            if (this.bindIgnoreTerrain.getValue()) {
                if (this.letterIgnoreTerrain.getText().length() > 0 && Keyboard.isKeyDown(KeyBoardClass.getKeyFromChar(this.letterIgnoreTerrain.getText().charAt(0)))) {
                    ignoreTerrainValue = true;
                }
            }
            else {
                ignoreTerrainValue = true;
            }
        }
        CrystalInfo.NewBreakInfo bestBreak = new CrystalInfo.NewBreakInfo(-100.0f, (PlayerInfo)null, (EntityEnderCrystal)null, 100.0);
        final ArrayList<BlockPos> webRemoved = new ArrayList<BlockPos>();
        switch (mode) {
            case "Nearest":
            case "Lowest": {
                final EntityPlayer targetEP = mode.equals("Lowest") ? this.getBasicPlayers(enemyRangeSQ).min((x, y) -> (int)x.getHealth()).orElse(null) : this.getBasicPlayers(enemyRangeSQ).min(Comparator.comparingDouble(x -> x.getDistanceSq((Entity)AutoCrystalRewrite.mc.player))).orElse(null);
                if (targetEP == null) {
                    break;
                }
                if (BlockUtil.getBlock(targetEP.posX, targetEP.posY, targetEP.posZ) instanceof BlockWeb) {
                    AutoCrystalRewrite.mc.world.setBlockToAir(new BlockPos(targetEP.posX, targetEP.posY, targetEP.posZ));
                    webRemoved.add(new BlockPos(targetEP.posX, targetEP.posY, targetEP.posZ));
                }
                final PlayerInfo player = new PlayerInfo((EntityPlayer)(((boolean)this.predictSelfDBreaking.getValue()) ? PredictUtil.predictPlayer((EntityPlayer)AutoCrystalRewrite.mc.player, settings) : AutoCrystalRewrite.mc.player), false, (float)AutoCrystalRewrite.mc.player.getTotalArmorValue(), (float)AutoCrystalRewrite.mc.player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
                final List<List<PositionInfo>> possibleCrystals = this.getPossibleCrystalsBreaking(player, maxSelfDamage, rayTrace, wallRangeSQ, rangeSQ, antiSuicide, ignoreTerrainValue);
                if (possibleCrystals == null) {
                    break;
                }
                final PlayerInfo target = new PlayerInfo(targetEP, armourPercent, (float)targetEP.getTotalArmorValue(), (float)targetEP.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
                bestBreak = this.calcualteBestBreak(nThread, possibleCrystals, player.entity.posX, player.entity.posY, player.entity.posZ, target, minDamage, minFacePlaceHp, minFacePlaceDamage, maxSelfDamage, maxYTarget, minYTarget, breakTimeout, new CrystalInfo.NewBreakInfo(0.0f, (PlayerInfo)null, (EntityEnderCrystal)null, 0.0), ignoreTerrainValue, relativeDamage, valueRelativeDamage);
                break;
            }
            case "Damage": {
                List<EntityPlayer> players = this.getBasicPlayers(enemyRangeSQ).sorted(new Sortbyroll()).collect((Collector<? super EntityPlayer, ?, List<EntityPlayer>>)Collectors.toList());
                if (players.size() == 0) {
                    break;
                }
                for (final EntityPlayer et : players) {
                    if (BlockUtil.getBlock(et.posX, et.posY, et.posZ) instanceof BlockWeb) {
                        AutoCrystalRewrite.mc.world.setBlockToAir(new BlockPos(et.posX, et.posY, et.posZ));
                        webRemoved.add(new BlockPos(et.posX, et.posY, et.posZ));
                    }
                }
                if (this.predictPlaceEnemy.getValue()) {
                    players = this.getPlayersThreaded(nThread, players, settings, (int)this.predictBreakTimeout.getValue());
                }
                final PlayerInfo player = new PlayerInfo((EntityPlayer)(((boolean)this.predictSelfDBreaking.getValue()) ? PredictUtil.predictPlayer((EntityPlayer)AutoCrystalRewrite.mc.player, settings) : AutoCrystalRewrite.mc.player), false, (float)AutoCrystalRewrite.mc.player.getTotalArmorValue(), (float)AutoCrystalRewrite.mc.player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
                final List<List<PositionInfo>> possibleCrystals = this.getPossibleCrystalsBreaking(player, maxSelfDamage, rayTrace, wallRangeSQ, rangeSQ, antiSuicide, ignoreTerrainValue);
                if (possibleCrystals == null) {
                    break;
                }
                int count = 0;
                for (final EntityPlayer playerTemp : players) {
                    if (count++ >= (int)this.maxTarget.getValue()) {
                        break;
                    }
                    final PlayerInfo target = new PlayerInfo(playerTemp, armourPercent, (float)playerTemp.getTotalArmorValue(), (float)playerTemp.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
                    if (target != null && ModuleManager.isModuleEnabled("AutoEz")) {
                        AutoEz.INSTANCE.addTargetedPlayer(target.entity.getName());
                    }
                    bestBreak = this.calcualteBestBreak(nThread, possibleCrystals, player.entity.posX, player.entity.posY, player.entity.posZ, target, minDamage, minFacePlaceHp, minFacePlaceDamage, maxSelfDamage, maxYTarget, minYTarget, breakTimeout, bestBreak, ignoreTerrainValue, relativeDamage, valueRelativeDamage);
                }
                break;
            }
        }
        for (final BlockPos web : webRemoved) {
            AutoCrystalRewrite.mc.world.setBlockState(web, Blocks.WEB.getDefaultState());
        }
        if (bestBreak.target != null) {
            this.breakRender = 0;
        }
        return bestBreak;
    }
    
    List<List<PositionInfo>> getPossibleCrystalsBreaking(final PlayerInfo self, final double maxSelfDamage, final boolean raytrace, final double wallRangeSQ, final double rangeSQ, final boolean antiSuicide, final boolean ignoreTerrain) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: invokespecial   java/util/ArrayList.<init>:()V
        //     7: astore          damagePos
        //     9: getstatic       com/lemonclient/client/module/modules/combat/AutoCrystalRewrite.mc:Lnet/minecraft/client/Minecraft;
        //    12: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //    15: getfield        net/minecraft/client/multiplayer/WorldClient.loadedEntityList:Ljava/util/List;
        //    18: invokeinterface java/util/List.stream:()Ljava/util/stream/Stream;
        //    23: aload_0         /* this */
        //    24: dload           rangeSQ
        //    26: invokedynamic   BootstrapMethod #217, test:(Lcom/lemonclient/client/module/modules/combat/AutoCrystalRewrite;D)Ljava/util/function/Predicate;
        //    31: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //    36: invokedynamic   BootstrapMethod #218, apply:()Ljava/util/function/Function;
        //    41: invokeinterface java/util/stream/Stream.map:(Ljava/util/function/Function;)Ljava/util/stream/Stream;
        //    46: invokestatic    java/util/stream/Collectors.toList:()Ljava/util/stream/Collector;
        //    49: invokeinterface java/util/stream/Stream.collect:(Ljava/util/stream/Collector;)Ljava/lang/Object;
        //    54: checkcast       Ljava/util/List;
        //    57: aload_0         /* this */
        //    58: iload           antiSuicide
        //    60: aload_1         /* self */
        //    61: iload           ignoreTerrain
        //    63: iload           raytrace
        //    65: dload           wallRangeSQ
        //    67: aload           damagePos
        //    69: dload_2         /* maxSelfDamage */
        //    70: invokedynamic   BootstrapMethod #219, accept:(Lcom/lemonclient/client/module/modules/combat/AutoCrystalRewrite;ZLcom/lemonclient/api/util/world/combat/ac/PlayerInfo;ZZDLjava/util/List;D)Ljava/util/function/Consumer;
        //    75: invokeinterface java/util/List.forEach:(Ljava/util/function/Consumer;)V
        //    80: aload_0         /* this */
        //    81: aload           damagePos
        //    83: aload_0         /* this */
        //    84: getfield        com/lemonclient/client/module/modules/combat/AutoCrystalRewrite.nThread:Lcom/lemonclient/api/setting/values/IntegerSetting;
        //    87: invokevirtual   com/lemonclient/api/setting/values/IntegerSetting.getValue:()Ljava/lang/Object;
        //    90: checkcast       Ljava/lang/Integer;
        //    93: invokevirtual   java/lang/Integer.intValue:()I
        //    96: invokevirtual   com/lemonclient/client/module/modules/combat/AutoCrystalRewrite.splitList:(Ljava/util/List;I)Ljava/util/List;
        //    99: areturn        
        //    Signature:
        //  (Lcom/lemonclient/api/util/world/combat/ac/PlayerInfo;DZDDZZ)Ljava/util/List<Ljava/util/List<Lcom/lemonclient/api/util/world/combat/ac/PositionInfo;>;>;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.Decompiler.decompile(Decompiler.java:70)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompile(Deobfuscator3000.java:538)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompileAndDeobfuscate(Deobfuscator3000.java:552)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.processMod(Deobfuscator3000.java:510)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.lambda$21(Deobfuscator3000.java:329)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    CrystalInfo.NewBreakInfo calcualteBestBreak(final int nThread, final List<List<PositionInfo>> possibleCrystals, final double posX, final double posY, final double posZ, final PlayerInfo target, final double minDamage, final double minFacePlaceHp, final double minFacePlaceDamage, final double maxSelfDamage, final int maxYTarget, final int minYTarget, final int placeTimeout, final CrystalInfo.NewBreakInfo oldBreak, final boolean ignoreTerrain, final boolean relativeDamage, final double valueRelativeDamage) {
        final Collection<Future<?>> futures = new LinkedList<Future<?>>();
        for (int i = 0; i < nThread; ++i) {
            final int finalI = i;
            futures.add(this.executor.submit(() -> this.calculateBestBreakTarget(possibleCrystals.get(finalI), posX, posY, posZ, target, minDamage, minFacePlaceHp, minFacePlaceDamage, maxSelfDamage, maxYTarget, minYTarget, ignoreTerrain, relativeDamage, valueRelativeDamage)));
        }
        final Stack<CrystalInfo.NewBreakInfo> results = new Stack<CrystalInfo.NewBreakInfo>();
        for (final Future<?> future : futures) {
            try {
                final CrystalInfo.NewBreakInfo temp = (CrystalInfo.NewBreakInfo)future.get(placeTimeout, TimeUnit.MILLISECONDS);
                if (temp == null) {
                    continue;
                }
                results.add(temp);
            }
            catch (ExecutionException | InterruptedException | TimeoutException ex2) {
                final Exception ex;
                final Exception e = ex;
                e.printStackTrace();
            }
        }
        results.add(oldBreak);
        return this.getResultBreak(results);
    }
    
    CrystalInfo.NewBreakInfo calculateBestBreakTarget(final List<PositionInfo> possibleLocations, final double x, final double y, final double z, final PlayerInfo target, final double minDamage, final double minFacePlaceHealth, final double minFacePlaceDamage, final double maxSelfDamage, final int maxYTarget, final int minYTarget, final boolean ignoreTerrain, final boolean relativeDamage, final double valueRelativeDamage) {
        PositionInfo best = new PositionInfo();
        for (final PositionInfo crystal : possibleLocations) {
            double temp;
            if ((temp = target.entity.posY - crystal.crystal.posY - 1.0) > 0.0) {
                if (temp > minYTarget) {
                    continue;
                }
            }
            else if (temp < -maxYTarget) {
                continue;
            }
            final float currentDamage = DamageUtil.calculateDamageThreaded(crystal.crystal.posX, crystal.crystal.posY, crystal.crystal.posZ, target, ignoreTerrain);
            if (currentDamage == best.damage) {
                if (best.crystal != null && (temp = crystal.crystal.getDistanceSq(x, y, z)) != best.distance && currentDamage / maxSelfDamage <= best.rapp && temp >= best.distance) {
                    continue;
                }
                best = crystal;
                best.setEnemyDamage((double)currentDamage);
                best.distance = target.entity.getDistanceSq(crystal.crystal.posX, crystal.crystal.posY, crystal.crystal.posZ);
                best.distancePlayer = AutoCrystalRewrite.mc.player.getDistanceSq(crystal.crystal.posX, crystal.crystal.posY, crystal.crystal.posZ);
            }
            else {
                if (currentDamage <= best.damage) {
                    continue;
                }
                if (relativeDamage && crystal.getSelfDamage() / currentDamage > valueRelativeDamage) {
                    continue;
                }
                best = crystal;
                best.setEnemyDamage((double)currentDamage);
                best.distance = target.entity.getDistanceSq(crystal.crystal.posX, crystal.crystal.posY, crystal.crystal.posZ);
                best.distancePlayer = AutoCrystalRewrite.mc.player.getDistanceSq(crystal.crystal.posX, crystal.crystal.posY, crystal.crystal.posZ);
            }
        }
        if (best.crystal != null && (best.damage >= minDamage || ((target.health <= minFacePlaceHealth || target.lowArmour) && best.damage >= minFacePlaceDamage))) {
            return new CrystalInfo.NewBreakInfo((float)best.damage, target, best.crystal, best.distancePlayer);
        }
        return null;
    }
    
    CrystalInfo.NewBreakInfo getResultBreak(final Stack<CrystalInfo.NewBreakInfo> result) {
        CrystalInfo.NewBreakInfo returnValue = new CrystalInfo.NewBreakInfo(0.0f, (PlayerInfo)null, (EntityEnderCrystal)null, 100.0);
        while (!result.isEmpty()) {
            final CrystalInfo.NewBreakInfo now = result.pop();
            if (now.damage == returnValue.damage) {
                if (now.distance >= returnValue.distance) {
                    continue;
                }
                returnValue = now;
            }
            else {
                if (now.damage <= returnValue.damage) {
                    continue;
                }
                returnValue = now;
            }
        }
        return returnValue;
    }
    
    boolean canStartBreaking() {
        final String s = (String)this.breakDelay.getValue();
        switch (s) {
            case "Tick": {
                if (this.tickBeforeBreak == 0) {
                    return true;
                }
                --this.tickBeforeBreak;
                break;
            }
            case "Time": {
                if (!this.checkTimeBreak) {
                    return true;
                }
                if (System.currentTimeMillis() - this.timeBreak >= (int)this.timeDelayBreak.getValue()) {
                    this.checkTimeBreak = false;
                    return true;
                }
                break;
            }
            case "Vanilla": {
                if (this.timerBreak.getTimePassed() / 50L >= 20 - (int)this.vanillaSpeedBreak.getValue()) {
                    this.timerBreak.reset();
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    boolean breakCrystals() {
        if (this.brokenCrystal) {
            return this.brokenCrystal = false;
        }
        if (!this.canStartBreaking()) {
            return false;
        }
        if ((boolean)this.antiCity.getValue() && this.forceBreak == null) {
            this.forceBreak = this.possibleCrystal();
        }
        long inizio = 0L;
        if (this.timeCalcBreaking.getValue()) {
            inizio = System.currentTimeMillis();
        }
        if (this.forceBreak == null) {
            this.bestBreak = this.getTargetBreaking((String)this.targetBreaking.getValue());
        }
        if (this.timeCalcBreaking.getValue()) {
            final long fine = System.currentTimeMillis();
            this.durationsBreaking.add(fine - inizio);
            if (this.durationsPlace.size() > (int)this.nCalc.getValue()) {
                double sum = this.durationsBreaking.stream().mapToDouble(a -> a).sum();
                sum /= (int)this.nCalc.getValue();
                this.durationsBreaking.clear();
                PistonCrystal.printDebug(String.format("N: %d Value: %f", this.nCalc.getValue(), sum), false);
            }
        }
        if (this.forceBreak != null) {
            return this.breakCrystal(this.forceBreak);
        }
        if (this.bestBreak.crystal != null) {
            if (this.showTextbr.getValue()) {
                this.toDisplay.add(new display(String.valueOf((int)this.bestBreak.damage), this.bestBreak.crystal.getPosition().add(0, -1, 0), this.colorBreakText.getValue(), (double)this.textYBreak.getValue()));
            }
            if (this.listPlayersBreak.stream().noneMatch(e -> this.bestBreak.target.entity.getName().equals(e.name)) || this.isMoving(this.bestBreak.target.entity.getName())) {
                return this.breakCrystal(this.bestBreak.crystal);
            }
        }
        return false;
    }
    
    boolean isMoving(final String name) {
        for (final EntityPlayer e : AutoCrystalRewrite.mc.world.playerEntities) {
            if (e.getName().equals(name)) {
                if (Math.abs(e.posX - e.prevPosX) + Math.abs(e.posZ - e.prevPosZ) > (double)this.speedActivation.getValue()) {
                    this.listPlayersBreak.removeIf(f -> f.name.equals(name));
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    
    boolean breakCrystal(final EntityEnderCrystal cr) {
        final BlockPos pos = cr.getPosition();
        if (this.rotate.getValue()) {
            this.lastHitVec = new Vec3d((Vec3i)pos).add(0.5, 0.0, 0.5);
            this.tick = 0;
            if ((boolean)this.yawCheck.getValue() || (boolean)this.pitchCheck.getValue()) {
                final Vec2f rotationWanted = RotationUtil.getRotationTo(this.lastHitVec);
                if (!(boolean)this.blockRotation.getValue() || !this.isRotating) {
                    this.yPlayerRotation = (this.pitchCheck.getValue() ? ((this.yPlayerRotation == Double.MAX_VALUE) ? AutoCrystalRewrite.mc.player.getPitchYaw().x : this.yPlayerRotation) : Double.MIN_VALUE);
                    this.xPlayerRotation = (this.yawCheck.getValue() ? ((this.xPlayerRotation == Double.MAX_VALUE) ? RotationUtil.normalizeAngle(AutoCrystalRewrite.mc.player.getPitchYaw().y) : this.xPlayerRotation) : Double.MIN_VALUE);
                    this.isRotating = true;
                }
                if (this.placeStrictDirection.getValue()) {
                    boolean back = false;
                    if (this.yawCheck.getValue()) {
                        double distanceDo = rotationWanted.x - this.xPlayerRotation;
                        if (Math.abs(distanceDo) > 180.0) {
                            distanceDo = RotationUtil.normalizeAngle(distanceDo);
                        }
                        if (Math.abs(distanceDo) > (int)this.yawStep.getValue()) {
                            back = true;
                        }
                    }
                    if (this.pitchCheck.getValue()) {
                        final double distanceDo = rotationWanted.y - this.yPlayerRotation;
                        if (Math.abs(distanceDo) > (int)this.pitchStep.getValue()) {
                            back = true;
                        }
                    }
                    if (back) {
                        if (!(boolean)this.predictBreakRotation.getValue()) {
                            return false;
                        }
                        if (this.lookingCrystal(cr)) {
                            return false;
                        }
                    }
                }
                else if (this.xPlayerRotation != rotationWanted.x || this.yPlayerRotation != rotationWanted.y) {
                    return false;
                }
            }
        }
        if ((boolean)this.antiWeakness.getValue() && AutoCrystalRewrite.mc.player.isPotionActive(MobEffects.WEAKNESS) && AutoCrystalRewrite.mc.player.getActivePotionEffects().stream().noneMatch(e -> e.getEffectName().contains("damageBoost") && e.getAmplifier() > 0)) {
            final int slotSword = InventoryUtil.findFirstItemSlot((Class)ItemSword.class, 0, 8);
            if (slotSword == -1) {
                return false;
            }
            if (slotSword != AutoCrystalRewrite.mc.player.inventory.currentItem) {
                this.switchTo(slotSword);
                return false;
            }
        }
        if ((boolean)this.rotate.getValue() && (boolean)this.preRotate.getValue()) {
            final Vec2f rot = RotationUtil.getRotationTo(this.lastHitVec);
            AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rot.x, rot.y, AutoCrystalRewrite.mc.player.onGround));
        }
        if (!((String)this.swingModebr.getValue()).equals("None")) {
            this.swingArm((String)this.swingModebr.getValue(), (boolean)this.hideClientbr.getValue(), null);
        }
        if (((String)this.breakTypeCrystal.getValue()).equalsIgnoreCase("Swing")) {
            AutoCrystalRewrite.mc.playerController.attackEntity((EntityPlayer)AutoCrystalRewrite.mc.player, (Entity)cr);
        }
        else {
            AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)cr));
        }
        if (this.cancelCrystal.getValue()) {
            cr.setDead();
            AutoCrystalRewrite.mc.world.removeAllEntities();
            AutoCrystalRewrite.mc.world.getLoadedEntityList();
        }
        final String s = (String)this.limitBreakPacket.getValue();
        switch (s) {
            case "Tick": {
                this.breakPacketLimit.addCrystalId(cr.getPosition(), cr.entityId, 0, (int)this.lomitBreakPacketTick.getValue());
                break;
            }
            case "Time": {
                this.breakPacketLimit.addCrystalId(cr.getPosition(), cr.entityId, (int)this.limitBreakPacketTime.getValue());
                break;
            }
        }
        this.tickBeforeBreak = (int)this.tickDelayBreak.getValue();
        this.checkTimeBreak = true;
        this.timeBreak = System.currentTimeMillis();
        if (this.placeAfterBreak.getValue()) {
            final BlockPos position = (this.forceBreak == null) ? cr.getPosition().add(0, -1, 0) : ((this.forceBreakPlace == null) ? cr.getPosition().add(0, -1, 0) : this.forceBreakPlace);
            if (this.instaPlace.getValue()) {
                BlockPos crystal = null;
                if (this.checkinstaPlace.getValue()) {
                    crystal = this.getTargetPlacing((String)this.targetPlacing.getValue()).crystal;
                }
                if ((boolean)this.checkinstaPlace.getValue() && crystal != null && !this.sameBlockPos(position, crystal)) {
                    crystal = position;
                }
                final EnumHand hand = this.getHandCrystal();
                if (hand != null && crystal != null) {
                    this.placeCrystal(crystal, hand, true);
                }
            }
            else {
                this.forcePlaceCrystal = position;
                if (this.forceBreak == null) {
                    this.forcePlaceDamage = this.bestBreak.damage;
                    this.forcePlaceTarget = this.bestBreak.target;
                }
                else {
                    this.forcePlaceDamage = 10.0f;
                    this.forcePlaceTarget = new PlayerInfo((EntityPlayer)AutoCrystalRewrite.mc.player, 0.0f);
                }
            }
        }
        this.forceBreak = null;
        this.forceBreakPlace = null;
        if (this.bestBreak.target != null && Math.abs(this.bestBreak.target.entity.posX - this.bestBreak.target.entity.prevPosX) + Math.abs(this.bestBreak.target.entity.posZ - this.bestBreak.target.entity.prevPosZ) < (double)this.speedActivation.getValue()) {
            final String s2 = (String)this.slowBreak.getValue();
            switch (s2) {
                case "Tick": {
                    this.listPlayersBreak.add(new slowBreakPlayers(this.bestBreak.target.entity.getName(), (int)this.tickSlowBreak.getValue(), false));
                    break;
                }
                case "Time": {
                    this.listPlayersBreak.add(new slowBreakPlayers(this.bestBreak.target.entity.getName(), (int)this.timeSlowBreak.getValue()));
                    break;
                }
            }
        }
        if (this.showBreakCrystalsSecond.getValue()) {
            this.attempedCrystalBreak.addCrystalId(cr.getPosition(), cr.entityId, 500);
        }
        if (this.anvilCity.getText().length() > 0) {
            if (Keyboard.isKeyDown(KeyBoardClass.getKeyFromChar(this.anvilCity.getText().charAt(0))) && this.bestBreak.damage > 5.0f) {
                boolean isCity = false;
                BlockPos anvilPosition = BlockPos.ORIGIN;
                final int[] endCrystalPositions = { (int)cr.posX, (int)cr.posY, (int)cr.posZ };
                for (final Vec3i surround : new Vec3i[] { new Vec3i(1, 0, 0), new Vec3i(-1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(0, 0, -1) }) {
                    final int[] surroundPosition = { endCrystalPositions[0] + surround.x, endCrystalPositions[1], endCrystalPositions[2] + surround.z };
                    for (final EntityPlayer t : this.getBasicPlayers(40.0).collect((Collector<? super EntityPlayer, ?, List<? super EntityPlayer>>)Collectors.toList())) {
                        final int[] playerPosition = { (int)t.posX, (int)t.posY, (int)t.posZ };
                        if (playerPosition[1] == surroundPosition[1]) {
                            if (playerPosition[0] == surroundPosition[0]) {
                                if (Math.abs(playerPosition[2] - surroundPosition[2]) == 1 && BlockUtil.getBlock(cr.getPosition().add(surround)) instanceof BlockAir) {
                                    isCity = true;
                                    anvilPosition = cr.getPosition().add(surround);
                                    break;
                                }
                                continue;
                            }
                            else {
                                if (playerPosition[2] == surroundPosition[2] && Math.abs(playerPosition[0] - surroundPosition[0]) == 1 && BlockUtil.getBlock(cr.getPosition().add(surround)) instanceof BlockAir) {
                                    isCity = true;
                                    anvilPosition = cr.getPosition().add(surround);
                                    break;
                                }
                                continue;
                            }
                        }
                    }
                }
                if (isCity) {
                    final int slot = InventoryUtil.findFirstBlockSlot((Class)Blocks.ANVIL.getClass(), 0, 8);
                    if (slot != -1) {
                        this.isAnvilling = true;
                        final java.util.Timer t2 = new java.util.Timer();
                        final BlockPos finalCity = anvilPosition;
                        this.blockCity = anvilPosition;
                        this.crystalAnvil = cr.getPosition().add(0, -1, 0);
                        t2.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                final int oldSlot = AutoCrystalRewrite.mc.player.inventory.currentItem;
                                AutoCrystalRewrite.this.switchTo(slot);
                                PlacementUtil.place(finalCity, EnumHand.MAIN_HAND, (boolean)AutoCrystalRewrite.this.rotate.getValue(), false);
                                AutoCrystalRewrite.this.switchTo(oldSlot);
                                t2.cancel();
                            }
                        }, (int)this.placeAnvil.getValue());
                    }
                }
            }
            else {
                this.isAnvilling = false;
            }
        }
        else {
            this.isAnvilling = false;
        }
        return true;
    }
    
    private void swingArm(final String swingMode, final boolean hideClient, final EnumHand handSwingDef) {
        EnumHand[] handSwing;
        if (handSwingDef == null) {
            switch (swingMode) {
                case "Both": {
                    handSwing = new EnumHand[] { EnumHand.MAIN_HAND, EnumHand.OFF_HAND };
                    break;
                }
                case "Offhand": {
                    handSwing = new EnumHand[] { EnumHand.OFF_HAND };
                    break;
                }
                default: {
                    handSwing = new EnumHand[] { EnumHand.MAIN_HAND };
                    break;
                }
            }
        }
        else {
            handSwing = new EnumHand[] { handSwingDef };
        }
        for (final EnumHand hand : handSwing) {
            if (hideClient) {
                if (this.hideClientbr.getValue()) {
                    AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
                }
                else {
                    AutoCrystalRewrite.mc.player.swingArm(hand);
                }
            }
            else {
                final ItemStack stack = AutoCrystalRewrite.mc.player.getHeldItem(hand);
                if (!stack.isEmpty() && stack.getItem().onEntitySwing((EntityLivingBase)AutoCrystalRewrite.mc.player, stack)) {
                    return;
                }
                AutoCrystalRewrite.mc.player.swingProgressInt = -1;
                AutoCrystalRewrite.mc.player.isSwingInProgress = true;
                AutoCrystalRewrite.mc.player.swingingHand = hand;
            }
        }
    }
    
    EntityEnderCrystal possibleCrystal() {
        final List<BlockPos> offsetPattern = this.getOffsets();
        for (final BlockPos pos : offsetPattern) {
            for (final Entity entity : AutoCrystalRewrite.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos))) {
                if (entity instanceof EntityEnderCrystal && (boolean)this.destroyCrystal.getValue()) {
                    return (EntityEnderCrystal)entity;
                }
            }
            if (this.destroyAboveCrystal.getValue()) {
                for (final Entity entity : new ArrayList<Entity>(AutoCrystalRewrite.mc.world.loadedEntityList)) {
                    if (entity instanceof EntityEnderCrystal && this.sameBlockPos(entity.getPosition(), pos)) {
                        return (EntityEnderCrystal)entity;
                    }
                }
            }
        }
        return null;
    }
    
    List<BlockPos> getOffsets() {
        final BlockPos playerPos = this.getPlayerPos();
        final ArrayList<BlockPos> offsets = new ArrayList<BlockPos>();
        if (this.allowNon1x1.getValue()) {
            final double decimalX = Math.abs(AutoCrystalRewrite.mc.player.posX) - Math.floor(Math.abs(AutoCrystalRewrite.mc.player.posX));
            final double decimalZ = Math.abs(AutoCrystalRewrite.mc.player.posZ) - Math.floor(Math.abs(AutoCrystalRewrite.mc.player.posZ));
            final int lengthXPos = this.calcLength(decimalX, false);
            final int lengthXNeg = this.calcLength(decimalX, true);
            final int lengthZPos = this.calcLength(decimalZ, false);
            final int lengthZNeg = this.calcLength(decimalZ, true);
            final ArrayList<BlockPos> tempOffsets = new ArrayList<BlockPos>();
            offsets.addAll(this.getOverlapPos());
            for (int x = 1; x < lengthXPos + 1; ++x) {
                tempOffsets.add(this.addToPlayer(playerPos, x, 0.0, 1 + lengthZPos));
                tempOffsets.add(this.addToPlayer(playerPos, x, 0.0, -(1 + lengthZNeg)));
            }
            for (int x = 0; x <= lengthXNeg; ++x) {
                tempOffsets.add(this.addToPlayer(playerPos, -x, 0.0, 1 + lengthZPos));
                tempOffsets.add(this.addToPlayer(playerPos, -x, 0.0, -(1 + lengthZNeg)));
            }
            for (int z = 1; z < lengthZPos + 1; ++z) {
                tempOffsets.add(this.addToPlayer(playerPos, 1 + lengthXPos, 0.0, z));
                tempOffsets.add(this.addToPlayer(playerPos, -(1 + lengthXNeg), 0.0, z));
            }
            for (int z = 0; z <= lengthZNeg; ++z) {
                tempOffsets.add(this.addToPlayer(playerPos, 1 + lengthXPos, 0.0, -z));
                tempOffsets.add(this.addToPlayer(playerPos, -(1 + lengthXNeg), 0.0, -z));
            }
            for (final BlockPos pos : tempOffsets) {
                if (getDown(pos)) {
                    offsets.add(pos.add(0, -1, 0));
                }
                offsets.add(pos);
            }
        }
        else {
            offsets.add(playerPos.add(0, -1, 0));
            for (final int[] surround : new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } }) {
                if (getDown(playerPos.add(surround[0], 0, surround[1]))) {
                    offsets.add(playerPos.add(surround[0], -1, surround[1]));
                }
                offsets.add(playerPos.add(surround[0], 0, surround[1]));
            }
        }
        return offsets;
    }
    
    public static boolean getDown(final BlockPos pos) {
        for (final EnumFacing e : EnumFacing.values()) {
            if (!AutoCrystalRewrite.mc.world.isAirBlock(pos.add(e.getDirectionVec()))) {
                return false;
            }
        }
        return true;
    }
    
    BlockPos addToPlayer(final BlockPos playerPos, double x, double y, double z) {
        if (playerPos.getX() < 0) {
            x = -x;
        }
        if (playerPos.getY() < 0) {
            y = -y;
        }
        if (playerPos.getZ() < 0) {
            z = -z;
        }
        return playerPos.add(x, y, z);
    }
    
    List<BlockPos> getOverlapPos() {
        final ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
        final double decimalX = AutoCrystalRewrite.mc.player.posX - Math.floor(AutoCrystalRewrite.mc.player.posX);
        final double decimalZ = AutoCrystalRewrite.mc.player.posZ - Math.floor(AutoCrystalRewrite.mc.player.posZ);
        final int offX = this.calcOffset(decimalX);
        final int offZ = this.calcOffset(decimalZ);
        positions.add(this.getPlayerPos());
        for (int x = 0; x <= Math.abs(offX); ++x) {
            for (int z = 0; z <= Math.abs(offZ); ++z) {
                final int properX = x * offX;
                final int properZ = z * offZ;
                positions.add(this.getPlayerPos().add(properX, -1, properZ));
            }
        }
        return positions;
    }
    
    int calcOffset(final double dec) {
        return (dec >= 0.7) ? 1 : ((dec <= 0.3) ? -1 : 0);
    }
    
    int calcLength(final double decimal, final boolean negative) {
        if (negative) {
            return (decimal <= 0.3) ? 1 : 0;
        }
        return (decimal >= 0.7) ? 1 : 0;
    }
    
    BlockPos getPlayerPos() {
        final double decimalPoint = AutoCrystalRewrite.mc.player.posY - Math.floor(AutoCrystalRewrite.mc.player.posY);
        return new BlockPos(AutoCrystalRewrite.mc.player.posX, (decimalPoint > 0.8) ? (Math.floor(AutoCrystalRewrite.mc.player.posY) + 1.0) : Math.floor(AutoCrystalRewrite.mc.player.posY), AutoCrystalRewrite.mc.player.posZ);
    }
    
    List<EntityPlayer> getPredicts(final List<EntityPlayer> players, final PredictUtil.PredictSettings settings) {
        players.replaceAll(entity -> PredictUtil.predictPlayer(entity, settings));
        return players;
    }
    
    List<List<EntityPlayer>> splitListEntity(final List<EntityPlayer> start, final int nThreads) {
        if (nThreads == 1) {
            return new ArrayList<List<EntityPlayer>>() {
                {
                    this.add(start);
                }
            };
        }
        final int count;
        if ((count = start.size()) == 0) {
            return null;
        }
        final List<List<EntityPlayer>> output = new ArrayList<List<EntityPlayer>>(nThreads);
        for (int i = 0; i < nThreads; ++i) {
            output.add(new ArrayList<EntityPlayer>());
        }
        for (int i = 0; i < count; ++i) {
            output.get(i % nThreads).add(start.get(i));
        }
        return output;
    }
    
    Stream<EntityPlayer> getBasicPlayers(final double rangeEnemySQ) {
        try {
            return (Stream<EntityPlayer>)AutoCrystalRewrite.mc.world.playerEntities.stream().filter(entity -> entity.getDistanceSq((Entity)AutoCrystalRewrite.mc.player) <= rangeEnemySQ).filter(entity -> !EntityUtil.basicChecksEntity(entity)).filter(entity -> entity.getHealth() > 0.0f);
        }
        catch (Exception e) {
            return new ArrayList<EntityPlayer>().stream();
        }
    }
    
    boolean lookingCrystal(final EntityEnderCrystal cr) {
        final Vec3d positionEyes = AutoCrystalRewrite.mc.player.getPositionEyes(AutoCrystalRewrite.mc.getRenderPartialTicks());
        final Vec3d rotationEyes = new Vec3d(Math.cos(this.xPlayerRotation) * Math.cos(this.yPlayerRotation), Math.sin(this.xPlayerRotation) * Math.cos(this.yPlayerRotation), Math.sin(this.yPlayerRotation));
        final int precision = 2;
        for (int i = 0; i < ((Double)this.breakRange.getValue()).intValue() + 1; ++i) {
            for (int j = precision; j > 0; --j) {
                final AxisAlignedBB playerBox = cr.getEntityBoundingBox();
                final double xArray = positionEyes.x + rotationEyes.x * i + rotationEyes.x / j;
                final double yArray = positionEyes.y + rotationEyes.y * i + rotationEyes.y / j;
                final double zArray = positionEyes.z + rotationEyes.z * i + rotationEyes.z / j;
                if (playerBox.maxY >= yArray && playerBox.minY <= yArray && playerBox.maxX >= xArray && playerBox.minX <= xArray && playerBox.maxZ >= zArray && playerBox.minZ <= zArray) {
                    return true;
                }
            }
        }
        return false;
    }
    
    boolean sameBlockPos(final BlockPos first, final BlockPos second) {
        return first != null && second != null && first.getX() == second.getX() && first.getY() == second.getY() && first.getZ() == second.getZ();
    }
    
    AxisAlignedBB getBox(final BlockPos centreBlock) {
        final double minX = centreBlock.getX();
        final double maxX = centreBlock.getX() + 1;
        final double minZ = centreBlock.getZ();
        final double maxZ = centreBlock.getZ() + 1;
        return new AxisAlignedBB(minX, (double)centreBlock.getY(), minZ, maxX, (double)(centreBlock.getY() + 1), maxZ);
    }
    
    AxisAlignedBB getBox(final double x, final double y, final double z) {
        final double maxX = x + 1.0;
        final double maxZ = z + 1.0;
        return new AxisAlignedBB(x, y, z, maxX, y + 1.0, maxZ);
    }
    
    public void onWorldRender(final RenderEvent render) {
        if (!this.isEnabled()) {
            this.bestPlace = null;
            this.bestBreak = null;
            this.managerRenderBlocks.blocks.clear();
            this.movingPlaceNow = new Vec3d(0.0, 0.0, 0.0);
            this.movingBreakNow = new Vec3d(0.0, 0.0, 0.0);
        }
        if (this.renderTarget.getValue()) {
            this.toRender.forEach(renderClass::render);
        }
        this.managerRenderBlocks.render();
        if (this.bestPlace != null && this.bestPlace.crystal != null) {
            if (!(boolean)this.movingPlace.getValue()) {
                this.drawBoxMain((String)this.typePlace.getValue(), this.bestPlace.crystal, (String)this.placeDimension.getValue(), (double)this.slabHeightPlace.getValue(), true, -1);
            }
            else {
                this.lastBestPlace = this.bestPlace.crystal;
            }
            if (this.fadeCapl.getValue()) {
                this.managerRenderBlocks.addRender(true, this.bestPlace.crystal);
            }
        }
        if ((boolean)this.movingPlace.getValue() && this.lastBestPlace != null) {
            if (this.movingPlaceNow.y == -1.0 && this.movingBreakNow.x == -1.0 && this.movingPlaceNow.z == -1.0) {
                this.movingPlaceNow = new Vec3d((double)(float)this.lastBestPlace.getX(), (double)(float)this.lastBestPlace.getY(), (double)(float)this.lastBestPlace.getZ());
            }
            this.movingPlaceNow = new Vec3d(this.movingPlaceNow.x + (this.lastBestPlace.getX() - this.movingPlaceNow.x) * ((Double)this.movingPlaceSpeed.getValue()).floatValue(), this.movingPlaceNow.y + (this.lastBestPlace.getY() - this.movingPlaceNow.y) * ((Double)this.movingPlaceSpeed.getValue()).floatValue(), this.movingPlaceNow.z + (this.lastBestPlace.getZ() - this.movingPlaceNow.z) * ((Double)this.movingPlaceSpeed.getValue()).floatValue());
            this.drawBoxMain((String)this.typePlace.getValue(), this.movingPlaceNow.x, this.movingPlaceNow.y, this.movingPlaceNow.z, (String)this.placeDimension.getValue(), (double)this.slabHeightPlace.getValue(), true);
            if (Math.abs(this.movingPlaceNow.x - this.lastBestPlace.getX()) <= 0.125 && Math.abs(this.movingPlaceNow.y - this.lastBestPlace.getY()) <= 0.125 && Math.abs(this.movingPlaceNow.z - this.lastBestPlace.getZ()) <= 0.125) {
                this.lastBestPlace = null;
            }
        }
        if (this.bestBreak != null && this.bestBreak.crystal != null && (!(boolean)this.placeDominant.getValue() || (this.bestPlace != null && this.bestPlace.crystal != null && !this.sameBlockPos(this.bestPlace.crystal, this.bestBreak.crystal.getPosition().add(0, -1, 0))))) {
            if (!(boolean)this.movingBreak.getValue()) {
                this.drawBoxMain((String)this.typeBreak.getValue(), this.bestBreak.crystal.getPosition().add(0, -1, 0), (String)this.breakDimension.getValue(), (double)this.slabHeightBreak.getValue(), false, -1);
            }
            else if (this.movingBreak.getValue()) {
                this.lastBestBreak = this.bestBreak.crystal.getPosition().add(0, -1, 0);
            }
            if (this.fadeCabr.getValue()) {
                this.managerRenderBlocks.addRender(false, this.bestBreak.crystal.getPosition().add(0, -1, 0));
            }
        }
        if ((boolean)this.movingBreak.getValue() && this.lastBestBreak != null) {
            if (this.movingBreakNow.y == -1.0 && this.movingBreakNow.x == -1.0 && this.movingBreakNow.z == -1.0) {
                final BlockPos pos = this.bestBreak.crystal.getPosition().add(0, -1, 0);
                this.movingBreakNow = new Vec3d((double)(float)pos.getX(), (double)(float)pos.getY(), (double)(float)pos.getZ());
            }
            this.movingBreakNow = new Vec3d(this.movingBreakNow.x + (this.lastBestBreak.getX() - this.movingBreakNow.x) * ((Double)this.movingBreakSpeed.getValue()).floatValue(), this.movingBreakNow.y + (this.lastBestBreak.getY() - this.movingBreakNow.y) * ((Double)this.movingBreakSpeed.getValue()).floatValue(), this.movingBreakNow.z + (this.lastBestBreak.getZ() - this.movingBreakNow.z) * ((Double)this.movingBreakSpeed.getValue()).floatValue());
            this.drawBoxMain((String)this.typeBreak.getValue(), this.movingBreakNow.x, this.movingBreakNow.y, this.movingBreakNow.z, (String)this.breakDimension.getValue(), (double)this.slabHeightBreak.getValue(), false);
            if (Math.abs(this.movingBreakNow.x - this.lastBestBreak.getX()) <= 0.125 && Math.abs(this.movingBreakNow.y - this.lastBestBreak.getY()) <= 0.125 && Math.abs(this.movingBreakNow.z - this.lastBestBreak.getZ()) <= 0.125) {
                this.lastBestBreak = null;
            }
        }
        this.toDisplay.forEach(display::draw);
        if ((boolean)this.predictSurround.getValue() && !(boolean)this.predictPacketSurround.getValue()) {
            EnumHand hand;
            BlockPos blockPos;
            AutoCrystalRewrite.mc.renderGlobal.damagedBlocks.forEach((integer, destroyBlockProgress) -> {
                if (this.stopGapple(false)) {
                    return;
                }
                else {
                    hand = this.getHandCrystal();
                    if (hand == null) {
                        return;
                    }
                    else {
                        if (destroyBlockProgress != null) {
                            blockPos = destroyBlockProgress.getPosition();
                            if (AutoCrystalRewrite.mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR) {
                                if (blockPos.getDistance((int)AutoCrystalRewrite.mc.player.posX, (int)AutoCrystalRewrite.mc.player.posY, (int)AutoCrystalRewrite.mc.player.posZ) <= (double)this.placeRange.getValue() && destroyBlockProgress.getPartialBlockDamage() / 2 * 25 >= (int)this.percentSurround.getValue()) {
                                    this.placeSurroundBlock(blockPos, hand);
                                }
                            }
                        }
                        return;
                    }
                }
            });
        }
        if (this.placeRender++ > (int)this.extendedPlace.getValue()) {
            this.bestPlace = new CrystalInfo.PlaceInfo(-100.0f, (PlayerInfo)null, (BlockPos)null, 100.0);
        }
        if (this.breakRender++ > (int)this.extendedBreak.getValue()) {
            this.bestBreak = new CrystalInfo.NewBreakInfo(-100.0f, (PlayerInfo)null, (EntityEnderCrystal)null, 100.0);
        }
    }
    
    void drawBoxMain(final String type, final BlockPos position, final String dimension, final double heightSlab, final boolean place, final int alpha) {
        if (dimension.equals("Circle")) {
            final int alphaValue = (alpha == -1) ? this.firstVerticeOutlineBot.getColor().getAlpha() : alpha;
            RenderUtil.drawCircle(position.x + 0.5f, (float)(position.getY() + 1), position.z + 0.5f, place ? ((Double)this.rangeCirclePl.getValue()) : ((Double)this.rangeCircleBr.getValue()), place ? new GSColor(this.firstVerticeOutlineBot.getValue(), alphaValue) : new GSColor(this.firstVerticeOutlineBotbr.getValue(), alphaValue));
        }
        else {
            AxisAlignedBB box = this.getBox(position);
            int mask = 63;
            if (dimension.equals("Flat")) {
                mask = 2;
                box = new AxisAlignedBB(box.minX, box.maxY, box.minZ, box.maxX, box.maxY, box.maxZ);
            }
            else if (dimension.equals("Slab")) {
                box = new AxisAlignedBB(box.minX, box.maxY - heightSlab, box.minZ, box.maxX, box.maxY, box.maxZ);
            }
            switch (type) {
                case "Outline": {
                    this.displayOutline(box, place, alpha);
                    break;
                }
                case "Fill": {
                    this.displayFill(box, mask, place, alpha);
                    break;
                }
                case "Both": {
                    this.displayFill(box, mask, place, alpha);
                    this.displayOutline(box, place, alpha);
                    break;
                }
            }
        }
    }
    
    void drawBoxMain(final String type, final double x, final double y, final double z, final String dimension, final double heightSlab, final boolean place) {
        if (dimension.equals("Circle")) {
            final int alphaValue = this.firstVerticeOutlineBot.getColor().getAlpha();
            RenderUtil.drawCircle((float)(x + 0.5), (float)(y + 1.0), (float)(z + 0.5), place ? ((Double)this.rangeCirclePl.getValue()) : ((Double)this.rangeCircleBr.getValue()), place ? new GSColor(this.firstVerticeOutlineBot.getValue(), alphaValue) : new GSColor(this.firstVerticeOutlineBotbr.getValue(), alphaValue));
        }
        else {
            AxisAlignedBB box = this.getBox(x, y, z);
            int mask = 63;
            if (dimension.equals("Flat")) {
                mask = 2;
                box = new AxisAlignedBB(box.minX, box.maxY, box.minZ, box.maxX, box.maxY, box.maxZ);
            }
            else if (dimension.equals("Slab")) {
                box = new AxisAlignedBB(box.minX, box.maxY - heightSlab, box.minZ, box.maxX, box.maxY, box.maxZ);
            }
            switch (type) {
                case "Outline": {
                    this.displayOutline(box, place, -1);
                    break;
                }
                case "Fill": {
                    this.displayFill(box, mask, place, -1);
                    break;
                }
                case "Both": {
                    this.displayFill(box, mask, place, -1);
                    this.displayOutline(box, place, -1);
                    break;
                }
            }
        }
    }
    
    void displayOutline(final AxisAlignedBB box, final boolean place, final int alpha) {
        this.renderCustomOutline(box, place, alpha);
    }
    
    void displayFill(final AxisAlignedBB box, final int mask, final boolean place, final int alpha) {
        this.renderFillCustom(box, mask, place, alpha);
    }
    
    private boolean canBreak(final BlockPos pos) {
        final IBlockState blockState = AutoCrystalRewrite.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)AutoCrystalRewrite.mc.world, pos) != -1.0f;
    }
    
    void placeSurroundBlock(final BlockPos blockPos, final EnumHand hand) {
        final float armourPercent = (int)this.armourFacePlace.getValue() / 100.0f;
        for (final Vec3i surround : new Vec3i[] { new Vec3i(1, 0, 0), new Vec3i(-1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(0, 0, -1) }) {
            final List<Entity> players = new ArrayList<Entity>(AutoCrystalRewrite.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos.add(surround))));
            PlayerInfo info = null;
            for (final Entity pl : players) {
                if (pl instanceof EntityPlayer && pl != AutoCrystalRewrite.mc.player && pl.posY + 0.5 >= blockPos.y) {
                    final EntityPlayer temp;
                    info = new PlayerInfo(temp = (EntityPlayer)pl, armourPercent, (float)temp.getTotalArmorValue(), (float)temp.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
                    break;
                }
            }
            boolean quit = false;
            if (info != null) {
                BlockPos coords = null;
                double damage = Double.MIN_VALUE;
                final Block toReplace = BlockUtil.getBlock(blockPos);
                AutoCrystalRewrite.mc.world.setBlockToAir(blockPos);
                for (final Vec3i placement : new Vec3i[] { new Vec3i(1, -1, 0), new Vec3i(-1, -1, 0), new Vec3i(0, -1, 1), new Vec3i(0, -1, -1) }) {
                    final BlockPos temp2;
                    if (CrystalUtil.canPlaceCrystal(temp2 = blockPos.add(placement), (boolean)this.newPlace.getValue())) {
                        if (DamageUtil.calculateDamage(temp2.getX() + 0.5, temp2.getY() + 1.0, temp2.getZ() + 0.5, (Entity)AutoCrystalRewrite.mc.player, (boolean)this.ignoreTerrain.getValue()) + (double)this.damageBalance.getValue() < (double)this.maxSelfDamageSur.getValue()) {
                            if (!(boolean)this.placeOnCrystal.getValue() && !this.isCrystalHere(temp2)) {
                                quit = true;
                                break;
                            }
                            final float damagePlayer = DamageUtil.calculateDamageThreaded(temp2.getX() + 0.5, temp2.getY() + 1.0, temp2.getZ() + 0.5, info, (boolean)this.ignoreTerrain.getValue());
                            if (damagePlayer > damage) {
                                damage = damagePlayer;
                                coords = temp2;
                                quit = true;
                            }
                        }
                    }
                }
                AutoCrystalRewrite.mc.world.setBlockState(blockPos, toReplace.getDefaultState());
                if (coords != null) {
                    this.placeCrystal(coords, hand, false);
                    this.placedCrystal = true;
                }
                if (quit) {
                    break;
                }
            }
        }
    }
    
    private void renderCustomOutline(final AxisAlignedBB hole, final boolean place, final int alpha) {
        final ArrayList<GSColor> colors = new ArrayList<GSColor>();
        if (place) {
            colors.add(new GSColor(this.firstVerticeOutlineBot.getValue(), (alpha == -1) ? this.firstVerticeOutlineBot.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBot.getValue(), (alpha == -1) ? this.firstVerticeOutlineBot.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBot.getValue(), (alpha == -1) ? this.firstVerticeOutlineBot.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBot.getValue(), (alpha == -1) ? this.firstVerticeOutlineBot.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBot.getValue(), (alpha == -1) ? this.firstVerticeOutlineBot.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBot.getValue(), (alpha == -1) ? this.firstVerticeOutlineBot.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBot.getValue(), (alpha == -1) ? this.firstVerticeOutlineBot.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBot.getValue(), (alpha == -1) ? this.firstVerticeOutlineBot.getColor().getAlpha() : alpha));
        }
        else {
            colors.add(new GSColor(this.firstVerticeOutlineBotbr.getValue(), (alpha == -1) ? this.firstVerticeOutlineBotbr.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBotbr.getValue(), (alpha == -1) ? this.firstVerticeOutlineBotbr.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBotbr.getValue(), (alpha == -1) ? this.firstVerticeOutlineBotbr.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBotbr.getValue(), (alpha == -1) ? this.firstVerticeOutlineBotbr.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBotbr.getValue(), (alpha == -1) ? this.firstVerticeOutlineBotbr.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBotbr.getValue(), (alpha == -1) ? this.firstVerticeOutlineBotbr.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBotbr.getValue(), (alpha == -1) ? this.firstVerticeOutlineBotbr.getColor().getAlpha() : alpha));
            colors.add(new GSColor(this.firstVerticeOutlineBotbr.getValue(), (alpha == -1) ? this.firstVerticeOutlineBotbr.getColor().getAlpha() : alpha));
        }
        RenderUtil.drawBoundingBox(hole, (double)(int)this.outlineWidthpl.getValue(), (GSColor[])colors.toArray(new GSColor[7]));
    }
    
    void renderFillCustom(final AxisAlignedBB hole, final int mask, final boolean place, final int alpha) {
        final ArrayList<GSColor> colors = new ArrayList<GSColor>();
        if (place) {
            colors.add(new GSColor(this.firstVerticeFillBot.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlpha.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBot.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlpha.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBot.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlpha.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBot.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlpha.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBot.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlpha.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBot.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlpha.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBot.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlpha.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBot.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlpha.getValue()) : alpha));
        }
        else {
            colors.add(new GSColor(this.firstVerticeFillBotbr.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlphabr.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBotbr.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlphabr.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBotbr.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlphabr.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBotbr.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlphabr.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBotbr.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlphabr.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBotbr.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlphabr.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBotbr.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlphabr.getValue()) : alpha));
            colors.add(new GSColor(this.firstVerticeFillBotbr.getValue(), (alpha == -1) ? ((int)this.firstVerticeFillAlphabr.getValue()) : alpha));
        }
        RenderUtil.drawBoxProva2(hole, (GSColor[])colors.toArray(new GSColor[7]), mask);
    }
    
    List<EntityPlayer> getPlayersThreaded(final int nThread, final List<EntityPlayer> players, final PredictUtil.PredictSettings settings, final int timeOut) {
        final List<List<EntityPlayer>> list = this.splitListEntity(players, nThread);
        final List<EntityPlayer> output = new ArrayList<EntityPlayer>();
        final Collection<Future<?>> futures = new LinkedList<Future<?>>();
        for (int i = 0; i < nThread; ++i) {
            final int finalI = i;
            futures.add(this.executor.submit(() -> this.getPredicts(list.get(finalI), settings)));
        }
        for (final Future<?> future : futures) {
            try {
                final List<EntityPlayer> temp = (List<EntityPlayer>)future.get(timeOut, TimeUnit.MILLISECONDS);
                if (temp == null) {
                    continue;
                }
                output.addAll(temp);
            }
            catch (ExecutionException | InterruptedException | TimeoutException ex2) {
                final Exception ex;
                final Exception e = ex;
                e.printStackTrace();
            }
        }
        return output;
    }
    
    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.highestId = -10000;
    }
    
    void updateHighestID() {
        for (final Entity entity : AutoCrystalRewrite.mc.world.loadedEntityList) {
            if (entity.getEntityId() <= this.highestId) {
                continue;
            }
            this.highestId = entity.getEntityId();
        }
    }
    
    void checkID(final int id) {
        if (id > this.highestId) {
            this.highestId = id;
        }
    }
    
    void attackID(final int id) {
        try {
            final Entity entity = AutoCrystalRewrite.mc.world.getEntityByID(id);
            if (entity == null || entity instanceof EntityEnderCrystal) {
                final CPacketUseEntity attack = new CPacketUseEntity();
                ((AccessorCPacketAttack)attack).setId(id);
                ((AccessorCPacketAttack)attack).setAction(CPacketUseEntity.Action.ATTACK);
                AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)attack);
                AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
            }
        }
        catch (Exception e) {
            PistonCrystal.printDebug("Prevented a crash from the ca. If this repet, spam me in dm", true);
            final Logger LOGGER = LogManager.getLogger("GameSense");
            LOGGER.error("[AutoCrystalRewrite] error during the creation of the structure.");
            if (e.getMessage() != null) {
                LOGGER.error("[AutoCrystalRewrite] error message: " + e.getClass().getName() + " " + e.getMessage());
            }
            else {
                LOGGER.error("[AutoCrystalRewrite] cannot find the cause");
            }
            if (e.getStackTrace().length != 0) {
                LOGGER.error("[AutoCrystalRewrite] StackTrace Start");
                for (final StackTraceElement errorMess : e.getStackTrace()) {
                    LOGGER.error("[AutoCrystalRewrite] " + errorMess.toString());
                }
                LOGGER.error("[AutoCrystalRewrite] StackTrace End");
            }
        }
    }
    
    private static int getPing() {
        int p = -1;
        if (AutoCrystalRewrite.mc.player == null || AutoCrystalRewrite.mc.getConnection() == null || AutoCrystalRewrite.mc.getConnection().getPlayerInfo(AutoCrystalRewrite.mc.player.getName()) == null) {
            p = -1;
        }
        else {
            p = AutoCrystalRewrite.mc.getConnection().getPlayerInfo(AutoCrystalRewrite.mc.player.getName()).getResponseTime();
        }
        return p;
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || AutoCrystalRewrite.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                AutoCrystalRewrite.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                AutoCrystalRewrite.mc.player.inventory.currentItem = slot;
            }
            AutoCrystalRewrite.mc.playerController.updateController();
        }
    }
    
    static {
        AutoCrystalRewrite.stopAC = false;
    }
    
    static class renderClass
    {
        final int id;
        long start;
        final long life;
        final double circleRange;
        final GSColor color;
        final boolean desyncCircle;
        final int stepRainbowCircle;
        final double range;
        final int desync;
        final boolean increaseHeight;
        final double speedIncrease;
        double nowHeigth;
        boolean up;
        
        public renderClass(final int id, final long life, final GSColor color, final double circleRange, final boolean desyncCircle, final int stepRainbowCircle, final double range, final int desync, final boolean increaseHeight, final double speedIncrease) {
            this.nowHeigth = 0.0;
            this.up = true;
            this.increaseHeight = increaseHeight;
            this.speedIncrease = speedIncrease;
            this.id = id;
            this.range = range;
            this.start = System.currentTimeMillis();
            this.life = life;
            this.desync = desync;
            this.circleRange = circleRange;
            this.color = color;
            this.desyncCircle = desyncCircle;
            this.stepRainbowCircle = stepRainbowCircle;
        }
        
        boolean update() {
            return System.currentTimeMillis() - this.start > this.life;
        }
        
        boolean reset(final int id) {
            if (this.id == id) {
                this.start = System.currentTimeMillis();
                return true;
            }
            return false;
        }
        
        void render() {
            final Entity e = AutoCrystalRewrite.mc.world.getEntityByID(this.id);
            if (e != null) {
                double inc = 0.0;
                if (this.increaseHeight) {
                    this.nowHeigth += this.speedIncrease * (this.up ? 1 : -1);
                    if (this.nowHeigth > e.height) {
                        this.up = false;
                    }
                    else if (this.nowHeigth < 0.0) {
                        this.up = true;
                    }
                    inc = this.nowHeigth;
                }
                if (this.desyncCircle) {
                    RenderUtil.drawCircle((float)e.posX, (float)(e.posY + inc), (float)e.posZ, Double.valueOf(this.range), this.desync, this.color.getAlpha());
                }
                else {
                    RenderUtil.drawCircle((float)e.posX, (float)(e.posY + inc), (float)e.posZ, Double.valueOf(this.range), this.color);
                }
            }
        }
    }
    
    static class Sortbyroll implements Comparator<EntityPlayer>
    {
        @Override
        public int compare(final EntityPlayer o1, final EntityPlayer o2) {
            return (int)(o1.getDistanceSq((Entity)AutoCrystalRewrite.mc.player) - o2.getDistanceSq((Entity)AutoCrystalRewrite.mc.player));
        }
    }
    
    static class display
    {
        AxisAlignedBB box;
        BlockPos block;
        final GSColor color;
        int width;
        int type;
        String[] text;
        double yDiff;
        
        public display(final AxisAlignedBB box, final GSColor color, final int width) {
            this.box = box;
            this.color = color;
            this.width = width;
            this.type = 0;
        }
        
        public display(final String text, final BlockPos block, final GSColor color, final double yDiff) {
            this.text = new String[] { text };
            this.block = block;
            this.color = color;
            this.type = 1;
            this.yDiff = yDiff;
        }
        
        void draw() {
            switch (this.type) {
                case 0: {
                    RenderUtil.drawBoundingBox(this.box, (double)this.width, this.color);
                    break;
                }
                case 1: {
                    RenderUtil.drawNametag(this.block.getX() + 0.5, this.block.getY() + this.yDiff, this.block.getZ() + 0.5, this.text, this.color, 1);
                    break;
                }
            }
        }
    }
    
    static class crystalTime
    {
        BlockPos posCrystal;
        int idCrystal;
        final int type;
        int tick;
        int finishTick;
        long start;
        int finish;
        
        public crystalTime(final BlockPos posCrystal, final int tick, final int finishTick) {
            this.idCrystal = -100;
            this.posCrystal = posCrystal;
            this.tick = tick;
            this.type = 0;
            this.finishTick = finishTick;
        }
        
        public crystalTime(final BlockPos posCrystal, final int finish) {
            this.idCrystal = -100;
            this.posCrystal = posCrystal;
            this.start = System.currentTimeMillis();
            this.finish = finish;
            this.type = 1;
        }
        
        public crystalTime(final BlockPos pos, final int id, final int finish, final boolean lol) {
            this.idCrystal = -100;
            this.posCrystal = pos;
            this.idCrystal = id;
            this.start = System.currentTimeMillis();
            this.finish = finish;
            this.type = 1;
        }
        
        public crystalTime(final BlockPos pos, final int id, final int tick, final int finishTick) {
            this.idCrystal = -100;
            this.posCrystal = pos;
            this.idCrystal = id;
            this.tick = tick;
            this.type = 0;
            this.finishTick = finishTick;
        }
        
        boolean isReady() {
            switch (this.type) {
                case 0: {
                    return ++this.tick >= this.finishTick;
                }
                case 1: {
                    return System.currentTimeMillis() - this.start >= this.finish;
                }
                default: {
                    return true;
                }
            }
        }
    }
    
    class crystalPlaceWait
    {
        ArrayList<crystalTime> listWait;
        
        crystalPlaceWait() {
            this.listWait = new ArrayList<crystalTime>();
        }
        
        void addCrystal(final BlockPos cryst, final int finish) {
            this.listWait.add(new crystalTime(cryst, finish));
        }
        
        void addCrystal(final BlockPos cryst, final int tick, final int tickFinish) {
            this.removeCrystal((double)cryst.getX(), (double)cryst.getY(), (double)cryst.getZ());
            this.listWait.add(new crystalTime(cryst, tick, tickFinish));
        }
        
        void addCrystalId(final BlockPos cryst, final int id, final int finish) {
            this.listWait.add(new crystalTime(cryst, id, finish, false));
        }
        
        void addCrystalId(final BlockPos cryst, final int id, final int tick, final int tickFinish) {
            this.removeCrystal((double)cryst.getX(), (double)cryst.getY(), (double)cryst.getZ());
            this.listWait.add(new crystalTime(cryst, id, tick, tickFinish));
        }
        
        boolean removeCrystal(final Double x, final Double y, final Double z) {
            final int i = this.CrystalExists(new BlockPos((double)x, (double)y, (double)z));
            if (i != -1) {
                this.listWait.remove(i);
                return true;
            }
            return false;
        }
        
        int CrystalExists(final BlockPos pos) {
            for (int i = 0; i < this.listWait.size(); ++i) {
                if (AutoCrystalRewrite.this.sameBlockPos(pos, this.listWait.get(i).posCrystal)) {
                    return i;
                }
            }
            return -1;
        }
        
        boolean crystalIdExists(final int id) {
            try {
                return this.listWait.stream().anyMatch(e -> e.idCrystal == id);
            }
            catch (NullPointerException | ConcurrentModificationException ex2) {
                final RuntimeException ex;
                final RuntimeException ignored = ex;
                return false;
            }
        }
        
        void updateCrystals() {
            for (int i = 0; i < this.listWait.size(); ++i) {
                try {
                    if (this.listWait.get(i).isReady()) {
                        this.listWait.remove(i);
                        --i;
                    }
                }
                catch (NullPointerException e) {
                    this.listWait.remove(i);
                    --i;
                }
            }
        }
        
        int countCrystals() {
            return this.listWait.size();
        }
    }
    
    static class slowBreakPlayers
    {
        final String name;
        int tick;
        int finalTick;
        long start;
        int finish;
        
        public slowBreakPlayers(final String name, final int finalTick, final boolean ignored) {
            this.tick = Integer.MAX_VALUE;
            this.start = Long.MAX_VALUE;
            this.name = name;
            this.finalTick = finalTick;
            this.tick = 0;
        }
        
        public slowBreakPlayers(final String name, final int finish) {
            this.tick = Integer.MAX_VALUE;
            this.start = Long.MAX_VALUE;
            this.name = name;
            this.finish = finish;
            this.start = System.currentTimeMillis();
        }
        
        boolean update() {
            if (this.tick == Integer.MAX_VALUE) {
                return System.currentTimeMillis() - this.start >= this.finish;
            }
            return ++this.tick >= this.finalTick;
        }
    }
    
    class packetBlock
    {
        public final BlockPos block;
        public int tick;
        public final int startTick;
        public final int finishTish;
        
        public packetBlock(final BlockPos block, final int startTick, final int finishTick) {
            this.block = block;
            this.tick = 0;
            this.startTick = startTick;
            this.finishTish = finishTick;
        }
        
        boolean update() {
            ++this.tick;
            if (this.tick > this.startTick) {
                if (this.tick > this.finishTish) {
                    return false;
                }
                if (AutoCrystalRewrite.this.stopGapple(false)) {
                    return true;
                }
                final EnumHand hand = AutoCrystalRewrite.this.getHandCrystal();
                if (hand == null) {
                    return true;
                }
                if (!CrystalUtil.canPlaceCrystal(this.block, (boolean)AutoCrystalRewrite.this.newPlace.getValue())) {
                    return true;
                }
                AutoCrystalRewrite.this.placeCrystal(this.block, hand, false);
                AutoCrystalRewrite.this.placedCrystal = true;
            }
            return true;
        }
    }
    
    class crystalPlaced
    {
        ArrayList<crystalTime> endCrystalPlaced;
        
        crystalPlaced() {
            this.endCrystalPlaced = new ArrayList<crystalTime>();
        }
        
        void addCrystal(final BlockPos pos) {
            this.endCrystalPlaced.removeIf(check -> AutoCrystalRewrite.this.sameBlockPos(check.posCrystal, pos));
            this.endCrystalPlaced.add(new crystalTime(pos, 5000));
        }
        
        boolean hasCrystal(final EntityEnderCrystal crystal) {
            final BlockPos now = crystal.getPosition().add(0, -1, 0);
            return this.endCrystalPlaced.stream().anyMatch(check -> AutoCrystalRewrite.this.sameBlockPos(check.posCrystal, now));
        }
        
        boolean hasCrystal(final BlockPos crystal) {
            return this.endCrystalPlaced.stream().anyMatch(check -> AutoCrystalRewrite.this.sameBlockPos(check.posCrystal, crystal));
        }
        
        void updateCrystals() {
            for (int i = 0; i < this.endCrystalPlaced.size(); ++i) {
                if (this.endCrystalPlaced.get(i).isReady()) {
                    this.endCrystalPlaced.remove(i);
                    --i;
                }
            }
        }
    }
    
    class renderBlock
    {
        private final BlockPos pos;
        private long start;
        private final boolean place;
        
        public renderBlock(final boolean place, final BlockPos pos) {
            this.place = place;
            this.start = System.currentTimeMillis();
            this.pos = pos;
        }
        
        void resetTime() {
            this.start = System.currentTimeMillis();
        }
        
        void render() {
            if (this.place) {
                AutoCrystalRewrite.this.drawBoxMain((String)AutoCrystalRewrite.this.typePlace.getValue(), this.pos, (String)AutoCrystalRewrite.this.placeDimension.getValue(), (double)AutoCrystalRewrite.this.slabHeightPlace.getValue(), true, this.returnGradient());
            }
            else {
                AutoCrystalRewrite.this.drawBoxMain((String)AutoCrystalRewrite.this.typeBreak.getValue(), this.pos, (String)AutoCrystalRewrite.this.breakDimension.getValue(), (double)AutoCrystalRewrite.this.slabHeightBreak.getValue(), false, this.returnGradient());
            }
        }
        
        public int returnGradient() {
            final long end = this.start + (int)AutoCrystalRewrite.this.lifeTime.getValue();
            int result = (int)((end - System.currentTimeMillis()) / (float)(end - this.start) * 100.0f);
            if (result < 0) {
                result = 0;
            }
            int startFade;
            if (this.place) {
                startFade = AutoCrystalRewrite.this.firstVerticeFillBot.getValue().getAlpha();
            }
            else {
                startFade = AutoCrystalRewrite.this.firstVerticeFillBotbr.getValue().getAlpha();
            }
            return (int)(startFade * (result / 100.0));
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
                if ((AutoCrystalRewrite.this.bestBreak.crystal != null && AutoCrystalRewrite.this.sameBlockPos(e.pos, AutoCrystalRewrite.this.bestBreak.crystal.getPosition().add(0, -1, 0))) || (AutoCrystalRewrite.this.bestPlace.crystal != null && AutoCrystalRewrite.this.sameBlockPos(e.pos, AutoCrystalRewrite.this.bestPlace.crystal))) {
                    e.resetTime();
                }
                else {
                    e.render();
                }
            });
        }
        
        void addRender(final boolean place, final BlockPos pos) {
            boolean render = true;
            for (final renderBlock block : this.blocks) {
                if (AutoCrystalRewrite.this.sameBlockPos(block.pos, pos) && block.place == place) {
                    render = false;
                    block.resetTime();
                    break;
                }
            }
            if (render) {
                this.blocks.add(new renderBlock(place, pos));
            }
        }
    }
}
