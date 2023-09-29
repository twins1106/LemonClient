//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.util.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.server.*;

@Module.Declaration(name = "HoleSnap", category = Category.Movement, priority = 120)
public class HoleSnap extends Module
{
    ModeSetting timeMode;
    IntegerSetting speed;
    IntegerSetting downRange;
    IntegerSetting upRange;
    DoubleSetting hRange;
    DoubleSetting timer;
    BooleanSetting step;
    ModeSetting mode;
    ModeSetting height;
    ModeSetting vHeight;
    ModeSetting bHeight;
    BooleanSetting onGround;
    BooleanSetting abnormal;
    BooleanSetting stepTimer;
    DoubleSetting multiplier;
    IntegerSetting centerSpeed;
    IntegerSetting timeoutTicks;
    BooleanSetting only;
    BooleanSetting single;
    BooleanSetting twoblocks;
    BooleanSetting custom;
    BooleanSetting four;
    private int stuckTicks;
    private int enabledTicks;
    BlockPos originPos;
    boolean isActive;
    @EventHandler
    private final Listener<PacketEvent.Receive> listener;
    @EventHandler
    private final Listener<PlayerMoveEvent> playerMoveListener;
    double[] pointFiveToOne;
    double[] one;
    double[] oneFive;
    double[] oneSixTwoFive;
    double[] oneEightSevenFive;
    double[] two;
    double[] twoFive;
    double[] betaShared;
    double[] betaTwo;
    double[] betaTwoFive;
    boolean prevTickTimer;
    @EventHandler
    private final Listener<StepEvent> stepEventListener;
    
    public HoleSnap() {
        this.timeMode = this.registerMode("Time Mode", (List)Arrays.asList("onUpdate", "MoveEvent"), "MoveEvent");
        this.speed = this.registerInteger("Speed", 2, 10, 1, () -> !((String)this.timeMode.getValue()).equals("MoveEvent"));
        this.downRange = this.registerInteger("Down Range", 5, 0, 8);
        this.upRange = this.registerInteger("Up Range", 1, 0, 8);
        this.hRange = this.registerDouble("H Range", 4.0, 1.0, 8.0);
        this.timer = this.registerDouble("Timer", 2.0, 1.0, 50.0);
        this.step = this.registerBoolean("Auto Step", true);
        this.mode = this.registerMode("Mode", (List)Arrays.asList("NCP", "Vanilla", "Beta"), "NCP", () -> (Boolean)this.step.getValue());
        this.height = this.registerMode("Height", (List)Arrays.asList("1", "1.5", "2", "2.5"), "2.5", () -> (boolean)this.step.getValue() && ((String)this.mode.getValue()).equalsIgnoreCase("NCP"));
        this.vHeight = this.registerMode("Height", (List)Arrays.asList("1", "1.5", "2", "2.5"), "2.5", () -> (boolean)this.step.getValue() && ((String)this.mode.getValue()).equalsIgnoreCase("Vanilla"));
        this.bHeight = this.registerMode("Height", (List)Arrays.asList("1", "1.5", "2", "2.5"), "2", () -> (boolean)this.step.getValue() && ((String)this.mode.getValue()).equalsIgnoreCase("Beta"));
        this.onGround = this.registerBoolean("On Ground", false, () -> (Boolean)this.step.getValue());
        this.abnormal = this.registerBoolean("Abnormal", false, () -> (boolean)this.step.getValue() && !((String)this.mode.getValue()).equalsIgnoreCase("Vanilla"));
        this.stepTimer = this.registerBoolean("Step Timer", false, () -> (boolean)this.step.getValue() && !((String)this.mode.getValue()).equalsIgnoreCase("VANILLA"));
        this.multiplier = this.registerDouble("Multiplier", 1.0, 0.0, 3.0, () -> (boolean)this.step.getValue() && (boolean)this.stepTimer.getValue() && this.stepTimer.isVisible());
        this.centerSpeed = this.registerInteger("Center Speed", 2, 10, 1);
        this.timeoutTicks = this.registerInteger("Timeout Ticks", 10, 0, 100);
        this.only = this.registerBoolean("Only 1x1", true);
        this.single = this.registerBoolean("Single Hole", true, () -> !(boolean)this.only.getValue());
        this.twoblocks = this.registerBoolean("Double Hole", true, () -> !(boolean)this.only.getValue());
        this.custom = this.registerBoolean("Custom Hole", true, () -> !(boolean)this.only.getValue());
        this.four = this.registerBoolean("Four Blocks", true, () -> !(boolean)this.only.getValue());
        this.stuckTicks = 0;
        this.enabledTicks = 0;
        this.listener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                this.disable();
            }
        }, new Predicate[0]);
        this.playerMoveListener = (Listener<PlayerMoveEvent>)new Listener(event -> {
            if (!((String)this.timeMode.getValue()).equals("MoveEvent")) {
                return;
            }
            if (++this.enabledTicks > (int)this.timeoutTicks.getValue()) {
                this.disable();
                return;
            }
            if (!HoleSnap.mc.player.isEntityAlive() || HoleSnap.mc.player.isElytraFlying() || HoleSnap.mc.player.capabilities.isFlying) {
                return;
            }
            final double currentSpeed = Math.hypot(HoleSnap.mc.player.motionX, HoleSnap.mc.player.motionZ);
            if (currentSpeed <= 0.05) {
                this.originPos = PlayerUtil.getPlayerPos();
            }
            if (this.shouldDisable(currentSpeed)) {
                this.disable();
                return;
            }
            final BlockPos hole = this.findHoles();
            if (hole != null) {
                final double x = hole.getX() + 0.5;
                final double y = hole.getY();
                final double z = hole.getZ() + 0.5;
                this.enabledTicks = 0;
                if (this.checkYRange((int)HoleSnap.mc.player.posY, this.originPos.y) && HoleSnap.mc.player.getDistance(x + 0.5, HoleSnap.mc.player.posY, z + 0.5) <= (double)this.hRange.getValue()) {
                    TimerUtils.setTickLength((float)(50.0 / (double)this.timer.getValue()));
                    final Vec3d playerPos = HoleSnap.mc.player.getPositionVector();
                    final double yawRad = Math.toRadians(RotationUtil.getRotationTo(playerPos, new Vec3d(x, y, z)).x);
                    final double dist = Math.hypot(x - playerPos.x, z - playerPos.z);
                    final double baseSpeed = this.applySpeedPotionEffects();
                    final double speed = HoleSnap.mc.player.onGround ? baseSpeed : Math.max(currentSpeed + 0.02, baseSpeed);
                    final double cappedSpeed = Math.min(speed, dist);
                    HoleSnap.mc.player.motionX = 0.0;
                    HoleSnap.mc.player.motionZ = 0.0;
                    event.setX(-Math.sin(yawRad) * cappedSpeed);
                    event.setZ(Math.cos(yawRad) * cappedSpeed);
                }
            }
            if (HoleSnap.mc.player.collidedHorizontally && hole == null) {
                ++this.stuckTicks;
            }
            else {
                this.stuckTicks = 0;
            }
        }, new Predicate[0]);
        this.pointFiveToOne = new double[] { 0.41999998688698 };
        this.one = new double[] { 0.41999998688698, 0.7531999805212 };
        this.oneFive = new double[] { 0.42, 0.753, 1.001, 1.084, 1.006 };
        this.oneSixTwoFive = new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372 };
        this.oneEightSevenFive = new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652 };
        this.two = new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869 };
        this.twoFive = new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907 };
        this.betaShared = new double[] { 0.419999986887, 0.7531999805212, 1.0013359791121, 1.1661092609382, 1.249187078744682, 1.176759275064238 };
        this.betaTwo = new double[] { 1.596759261951216, 1.929959255585439 };
        this.betaTwoFive = new double[] { 1.596759261951216, 1.929959255585439, 2.178095254176385, 2.3428685360024515, 2.425946353808919 };
        this.stepEventListener = (Listener<StepEvent>)new Listener(event -> {
            final double step = event.getBB().minY - HoleSnap.mc.player.posY;
            if (((String)this.mode.getValue()).equalsIgnoreCase("Vanilla")) {
                return;
            }
            if (((String)this.mode.getValue()).equalsIgnoreCase("NCP")) {
                if (step == 0.625 && (boolean)this.abnormal.getValue()) {
                    this.sendOffsets(this.pointFiveToOne);
                    if (this.stepTimer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.pointFiveToOne.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 1.0 || ((step == 0.875 || step == 1.0625 || step == 0.9375) && (boolean)this.abnormal.getValue())) {
                    this.sendOffsets(this.one);
                    if (this.stepTimer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.one.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 1.5) {
                    this.sendOffsets(this.oneFive);
                    if (this.stepTimer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.oneFive.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 1.875 && (boolean)this.abnormal.getValue()) {
                    this.sendOffsets(this.oneEightSevenFive);
                    if (this.stepTimer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.oneEightSevenFive.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 1.625 && (boolean)this.abnormal.getValue()) {
                    this.sendOffsets(this.oneSixTwoFive);
                    if (this.stepTimer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.oneSixTwoFive.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 2.0) {
                    this.sendOffsets(this.two);
                    if (this.stepTimer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.two.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 2.5) {
                    this.sendOffsets(this.twoFive);
                    if (this.stepTimer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.twoFive.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else {
                    event.cancel();
                }
            }
            else if (((String)this.mode.getValue()).equalsIgnoreCase("Beta")) {
                if (step == 1.5) {
                    this.sendOffsets(this.betaShared);
                    if (this.stepTimer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.betaShared.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 2.0) {
                    this.sendOffsets(this.betaShared);
                    this.sendOffsets(this.betaTwo);
                    if (this.stepTimer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.betaShared.length + this.betaTwo.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 2.5) {
                    this.sendOffsets(this.betaShared);
                    this.sendOffsets(this.betaTwoFive);
                    if (this.stepTimer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.betaShared.length + this.betaTwoFive.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else {
                    event.cancel();
                }
            }
        }, new Predicate[0]);
    }
    
    public void onEnable() {
        this.originPos = PlayerUtil.getPlayerPos();
        this.isActive = true;
    }
    
    public void onDisable() {
        this.isActive = false;
        final int n = 0;
        this.enabledTicks = n;
        this.stuckTicks = n;
        TimerUtils.setTickLength(50.0f);
        HoleSnap.mc.player.stepHeight = 0.5f;
    }
    
    public void onUpdate() {
        if (HoleSnap.mc.world == null || HoleSnap.mc.player == null || HoleSnap.mc.player.isDead) {
            this.disable();
            return;
        }
        HoleSnap.mc.player.stepHeight = (((boolean)this.onGround.getValue() && !HoleSnap.mc.player.onGround) ? 0.5f : this.getHeight((String)this.mode.getValue()));
        if (((String)this.timeMode.getValue()).equals("onUpdate")) {
            this.doHoleSnap();
        }
    }
    
    float getHeight(final String mode) {
        return Float.parseFloat(mode.equals("Beta") ? ((String)this.bHeight.getValue()) : (mode.equals("Vanilla") ? this.vHeight.getValue() : ((String)this.height.getValue())));
    }
    
    private void doHoleSnap() {
        if (++this.enabledTicks > (int)this.timeoutTicks.getValue()) {
            this.disable();
            return;
        }
        if (!HoleSnap.mc.player.isEntityAlive() || HoleSnap.mc.player.isElytraFlying() || !HoleSnap.mc.player.capabilities.isFlying) {
            return;
        }
        final double currentSpeed = Math.hypot(HoleSnap.mc.player.motionX, HoleSnap.mc.player.motionZ);
        if (currentSpeed <= 0.05) {
            this.originPos = PlayerUtil.getPlayerPos();
        }
        if (this.shouldDisable(currentSpeed)) {
            this.disable();
            return;
        }
        final BlockPos hole = this.findHoles();
        if (hole != null) {
            final double x = hole.getX() + 0.5;
            final double y = hole.getY();
            final double z = hole.getZ() + 0.5;
            this.enabledTicks = 0;
            if (this.checkYRange((int)HoleSnap.mc.player.posY, this.originPos.y) && HoleSnap.mc.player.getDistance(x + 0.5, HoleSnap.mc.player.posY, z + 0.5) <= (double)this.hRange.getValue()) {
                TimerUtils.setTickLength((float)(50.0 / (double)this.timer.getValue()));
                final Vec3d playerPos = HoleSnap.mc.player.getPositionVector();
                final double yawRad = Math.toRadians(RotationUtil.getRotationTo(playerPos, new Vec3d(x, y, z)).x);
                final double dist = Math.hypot(x - playerPos.x, z - playerPos.z);
                final double baseSpeed = this.applySpeedPotionEffects();
                final double speed = HoleSnap.mc.player.onGround ? baseSpeed : Math.max(currentSpeed + 0.02, baseSpeed);
                final double cappedSpeed = Math.min(speed, dist);
                HoleSnap.mc.player.motionX = -Math.sin(yawRad) * cappedSpeed / (int)this.speed.getValue();
                HoleSnap.mc.player.motionZ = Math.cos(yawRad) * cappedSpeed / (int)this.speed.getValue();
            }
        }
        if (HoleSnap.mc.player.collidedHorizontally && hole == null) {
            ++this.stuckTicks;
        }
        else {
            this.stuckTicks = 0;
        }
    }
    
    private BlockPos findHoles() {
        final NonNullList<BlockPos> holes = (NonNullList<BlockPos>)NonNullList.create();
        final List<BlockPos> blockPosList = (List<BlockPos>)EntityUtil.getSphere(PlayerUtil.getPlayerPos(), (Double)this.hRange.getValue(), Double.valueOf(8.0), false, true, 0);
        HoleUtil.HoleInfo holeInfo;
        HoleUtil.HoleType holeType;
        final NonNullList list;
        blockPosList.forEach(pos -> {
            if (!this.checkYRange((int)HoleSnap.mc.player.posY, pos.y)) {
                return;
            }
            else {
                holeInfo = HoleUtil.isHole(pos, (boolean)this.only.getValue(), false, false);
                holeType = holeInfo.getType();
                if (holeType != HoleUtil.HoleType.NONE) {
                    if (!(boolean)this.only.getValue()) {
                        Label_0163_3: {
                            switch (holeType) {
                                case SINGLE: {
                                    if (!(boolean)this.single.getValue()) {
                                        return;
                                    }
                                    else {
                                        break Label_0163_3;
                                    }
                                    break;
                                }
                                case DOUBLE: {
                                    if (!(boolean)this.twoblocks.getValue()) {
                                        return;
                                    }
                                    else {
                                        break Label_0163_3;
                                    }
                                    break;
                                }
                                case CUSTOM: {
                                    if (!(boolean)this.custom.getValue()) {
                                        return;
                                    }
                                    else {
                                        break Label_0163_3;
                                    }
                                    break;
                                }
                                case FOUR: {
                                    if (!(boolean)this.four.getValue()) {
                                        return;
                                    }
                                    else {
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (HoleSnap.mc.world.isAirBlock(pos) && HoleSnap.mc.world.isAirBlock(pos.add(0, 1, 0)) && HoleSnap.mc.world.isAirBlock(pos.add(0, 2, 0))) {
                        list.add((Object)pos);
                    }
                }
                return;
            }
        });
        return holes.stream().filter(p -> HoleSnap.mc.player.getDistance((double)p.x, (double)p.y, (double)p.z) <= 8.0).min(Comparator.comparing(p -> HoleSnap.mc.player.getDistance((double)p.x, (double)p.y, (double)p.z))).orElse(null);
    }
    
    private double applySpeedPotionEffects() {
        double result = 0.2873;
        if (HoleSnap.mc.player.isPotionActive(MobEffects.SPEED)) {
            result += 0.2873 * (Objects.requireNonNull(HoleSnap.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier() + 1) * 0.2;
        }
        if (HoleSnap.mc.player.isPotionActive(MobEffects.SLOWNESS)) {
            result -= 0.2873 * (Objects.requireNonNull(HoleSnap.mc.player.getActivePotionEffect(MobEffects.SLOWNESS)).getAmplifier() + 1) * 0.15;
        }
        return result;
    }
    
    private boolean shouldDisable(final Double currentSpeed) {
        if (HoleSnap.mc.player.posY > this.originPos.getY()) {
            return false;
        }
        if (this.stuckTicks > 5 && currentSpeed < 0.05) {
            return true;
        }
        final HoleUtil.HoleInfo holeInfo = HoleUtil.isHole(PlayerUtil.getPlayerPos(), false, false, false);
        final HoleUtil.HoleType holeType = holeInfo.getType();
        if (!HoleSnap.mc.player.onGround || holeType == HoleUtil.HoleType.NONE) {
            return false;
        }
        if ((boolean)this.only.getValue() && holeType != HoleUtil.HoleType.SINGLE) {
            return false;
        }
        switch (holeType) {
            case SINGLE: {
                if (!(boolean)this.single.getValue()) {
                    return false;
                }
            }
            case DOUBLE: {
                if (!(boolean)this.twoblocks.getValue()) {
                    return false;
                }
            }
            case CUSTOM: {
                if (!(boolean)this.custom.getValue()) {
                    return false;
                }
            }
            case FOUR: {
                if (!(boolean)this.four.getValue()) {
                    return false;
                }
                break;
            }
        }
        final Vec3d center = this.getCenter(holeInfo.getCentre());
        final double XDiff = Math.abs(center.x - HoleSnap.mc.player.posX);
        final double ZDiff = Math.abs(center.z - HoleSnap.mc.player.posZ);
        if (XDiff > 0.1 || ZDiff > 0.1) {
            final double MotionX = center.x - HoleSnap.mc.player.posX;
            final double MotionZ = center.z - HoleSnap.mc.player.posZ;
            HoleSnap.mc.player.motionX = MotionX / (int)this.centerSpeed.getValue();
            HoleSnap.mc.player.motionZ = MotionZ / (int)this.centerSpeed.getValue();
        }
        return true;
    }
    
    void sendOffsets(final double[] offsets) {
        for (final double i : offsets) {
            HoleSnap.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(HoleSnap.mc.player.posX, HoleSnap.mc.player.posY + i + 0.0, HoleSnap.mc.player.posZ, false));
        }
    }
    
    public Vec3d getCenter(final AxisAlignedBB box) {
        return new Vec3d(box.minX + (box.maxX - box.minX) / 2.0, box.minY, box.minZ + (box.maxZ - box.minZ) / 2.0);
    }
    
    private boolean checkYRange(final int playerY, final int holeY) {
        if (playerY >= holeY) {
            return playerY - holeY <= (int)this.downRange.getValue();
        }
        return holeY - playerY <= -(int)this.upRange.getValue();
    }
}
