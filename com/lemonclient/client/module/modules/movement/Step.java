//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.*;
import java.util.function.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.lemonclient.api.util.misc.*;

@Module.Declaration(name = "Step", category = Category.Movement)
public class Step extends Module
{
    ModeSetting mode;
    ModeSetting height;
    ModeSetting vHeight;
    ModeSetting bHeight;
    BooleanSetting abnormal;
    BooleanSetting onGround;
    BooleanSetting timer;
    DoubleSetting multiplier;
    BooleanSetting debug;
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
    
    public Step() {
        this.mode = this.registerMode("Mode", (List)Arrays.asList("NCP", "Vanilla", "Beta"), "NCP");
        this.height = this.registerMode("Height", (List)Arrays.asList("1", "1.5", "2", "2.5"), "2.5", () -> ((String)this.mode.getValue()).equalsIgnoreCase("NCP"));
        this.vHeight = this.registerMode("Height", (List)Arrays.asList("1", "1.5", "2", "2.5"), "2.5", () -> ((String)this.mode.getValue()).equalsIgnoreCase("Vanilla"));
        this.bHeight = this.registerMode("Height", (List)Arrays.asList("1", "1.5", "2", "2.5"), "2", () -> ((String)this.mode.getValue()).equalsIgnoreCase("Beta"));
        this.abnormal = this.registerBoolean("Abnormal", false, () -> !((String)this.mode.getValue()).equalsIgnoreCase("Vanilla"));
        this.onGround = this.registerBoolean("On Ground", false);
        this.timer = this.registerBoolean("Timer", false, () -> !((String)this.mode.getValue()).equalsIgnoreCase("VANILLA"));
        this.multiplier = this.registerDouble("Multiplier", 1.0, 0.0, 3.0, () -> (boolean)this.timer.getValue() && this.timer.isVisible());
        this.debug = this.registerBoolean("Debug Height", false);
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
            final double step = event.getBB().minY - Step.mc.player.posY;
            if (this.debug.getValue()) {
                MessageBus.sendClientDeleteMessage("Stepping " + step + " blocks", "Stepping ... Blocks", 9);
            }
            if (((String)this.mode.getValue()).equalsIgnoreCase("Vanilla")) {
                return;
            }
            if (((String)this.mode.getValue()).equalsIgnoreCase("NCP")) {
                if (step == 0.625 && (boolean)this.abnormal.getValue()) {
                    this.sendOffsets(this.pointFiveToOne);
                    if (this.timer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.pointFiveToOne.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 1.0 || ((step == 0.875 || step == 1.0625 || step == 0.9375) && (boolean)this.abnormal.getValue())) {
                    this.sendOffsets(this.one);
                    if (this.timer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.one.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 1.5) {
                    this.sendOffsets(this.oneFive);
                    if (this.timer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.oneFive.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 1.875 && (boolean)this.abnormal.getValue()) {
                    this.sendOffsets(this.oneEightSevenFive);
                    if (this.timer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.oneEightSevenFive.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 1.625 && (boolean)this.abnormal.getValue()) {
                    this.sendOffsets(this.oneSixTwoFive);
                    if (this.timer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.oneSixTwoFive.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 2.0) {
                    this.sendOffsets(this.two);
                    if (this.timer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.two.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 2.5) {
                    this.sendOffsets(this.twoFive);
                    if (this.timer.getValue()) {
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
                    if (this.timer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.betaShared.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 2.0) {
                    this.sendOffsets(this.betaShared);
                    this.sendOffsets(this.betaTwo);
                    if (this.timer.getValue()) {
                        TimerUtils.setTickLength(50.0f * (this.betaShared.length + this.betaTwo.length + 1) * ((((Double)this.multiplier.getValue()).floatValue() == 0.0f) ? 1.0f : ((Double)this.multiplier.getValue()).floatValue()));
                        this.prevTickTimer = true;
                    }
                }
                else if (step == 2.5) {
                    this.sendOffsets(this.betaShared);
                    this.sendOffsets(this.betaTwoFive);
                    if (this.timer.getValue()) {
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
    
    public void onUpdate() {
        Step.mc.player.stepHeight = (((boolean)this.onGround.getValue() && !Step.mc.player.onGround) ? 0.5f : this.getHeight((String)this.mode.getValue()));
        if (this.prevTickTimer) {
            this.prevTickTimer = false;
            TimerUtils.setTickLength(50.0f);
        }
    }
    
    float getHeight(final String mode) {
        return Float.parseFloat(mode.equals("Beta") ? ((String)this.bHeight.getValue()) : (mode.equals("Vanilla") ? this.vHeight.getValue() : ((String)this.height.getValue())));
    }
    
    protected void onDisable() {
        Step.mc.player.stepHeight = 0.5f;
        TimerUtils.setTickLength(50.0f);
    }
    
    void sendOffsets(final double[] offsets) {
        for (final double i : offsets) {
            Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + i + 0.0, Step.mc.player.posZ, false));
        }
    }
}
