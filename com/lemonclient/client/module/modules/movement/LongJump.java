//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.entity.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.network.play.server.*;

@Module.Declaration(name = "LongJump", category = Category.Movement)
public class LongJump extends Module
{
    ModeSetting mode;
    DoubleSetting speed;
    DoubleSetting farSpeed;
    IntegerSetting farAccel;
    DoubleSetting initialFar;
    BooleanSetting jump;
    DoubleSetting jumpHeightVelo;
    BooleanSetting allowY;
    DoubleSetting xzvelocity;
    DoubleSetting yvelocity;
    DoubleSetting factorMax;
    DoubleSetting normalSpeed;
    BooleanSetting lagback;
    DoubleSetting jumpHeight;
    Double playerSpeed;
    boolean slowDown;
    boolean hasaccel;
    public boolean velo;
    float mf;
    int i;
    private final Timer timer;
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener;
    @EventHandler
    private final Listener<PlayerMoveEvent> playerMoveEventListener;
    
    public LongJump() {
        this.mode = this.registerMode("mode", (List)Arrays.asList("BHop", "Peak", "Ground", "Velocity", "Continuous"), "Peak");
        this.speed = this.registerDouble("BHop speed", 2.15, 0.0, 10.0, () -> ((String)this.mode.getValue()).equalsIgnoreCase("BHop"));
        this.farSpeed = this.registerDouble("Peak Speed", 1.0, 0.0, 10.0, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Peak"));
        this.farAccel = this.registerInteger("Peak Acceleration", 0, 1, 5, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Peak"));
        this.initialFar = this.registerDouble("Peak Hop Speed", 1.0, 0.0, 10.0, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Peak"));
        this.jump = this.registerBoolean("Jump", true, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Velocity"));
        this.jumpHeightVelo = this.registerDouble("Jump Height Velocity", 1.0, 0.0, 10.0, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Velocity"));
        this.allowY = this.registerBoolean("Velocity Multiply", true, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Velocity"));
        this.xzvelocity = this.registerDouble("XZ Velocity Multiplier", 0.1, 0.0, 5.0, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Velocity"));
        this.yvelocity = this.registerDouble("Y Velocity Multiplier", 0.1, 0.0, 2.0, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Velocity"));
        this.factorMax = this.registerDouble("Factor", 0.0, 0.0, 50.0, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Continuous"));
        this.normalSpeed = this.registerDouble("Ground Speed", 3.0, 0.0, 10.0, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Ground"));
        this.lagback = this.registerBoolean("Disable On LagBack", false);
        this.jumpHeight = this.registerDouble("jumpHeight", 0.41, 0.0, 1.0);
        this.timer = new Timer();
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketPlayerPosLook && (boolean)this.lagback.getValue()) {
                this.disable();
            }
            if ((event.getPacket() instanceof SPacketExplosion || event.getPacket() instanceof SPacketEntityVelocity) && LongJump.mc.player != null) {
                if (((String)this.mode.getValue()).equals("Velocity")) {
                    final double[] dir = MotionUtil.forward(1.0);
                    if ((boolean)this.jump.getValue() && LongJump.mc.player.onGround) {
                        LongJump.mc.player.jumpMovementFactor = ((Double)this.jumpHeightVelo.getValue()).floatValue();
                        LongJump.mc.player.motionY = (double)this.jumpHeight.getValue();
                    }
                    this.velo = false;
                    if (event.getPacket() instanceof SPacketEntityVelocity && ((boolean)this.allowY.getValue() || ((SPacketEntityVelocity)event.getPacket()).motionY <= 0)) {
                        ((SPacketEntityVelocity)event.getPacket()).motionY *= (int)(double)this.yvelocity.getValue();
                        ((SPacketEntityVelocity)event.getPacket()).motionX = (int)(((SPacketEntityVelocity)event.getPacket()).motionX * (double)this.xzvelocity.getValue() * dir[0]);
                        ((SPacketEntityVelocity)event.getPacket()).motionZ = (int)(((SPacketEntityVelocity)event.getPacket()).motionZ * (double)this.xzvelocity.getValue() * dir[1]);
                    }
                    if (event.getPacket() instanceof SPacketExplosion && ((boolean)this.allowY.getValue() || ((SPacketExplosion)event.getPacket()).motionY <= 0.0f)) {
                        ((SPacketExplosion)event.getPacket()).motionY = (float)(int)(((SPacketExplosion)event.getPacket()).motionY * (double)this.yvelocity.getValue());
                        ((SPacketExplosion)event.getPacket()).motionX = (float)(int)(((SPacketExplosion)event.getPacket()).motionX * (double)this.xzvelocity.getValue() * dir[0]);
                        ((SPacketExplosion)event.getPacket()).motionZ = (float)(int)(((SPacketExplosion)event.getPacket()).motionZ * (double)this.xzvelocity.getValue() * dir[1]);
                    }
                }
                else {
                    this.velo = true;
                }
            }
        }, new Predicate[0]);
        this.playerMoveEventListener = (Listener<PlayerMoveEvent>)new Listener(event -> {
            if (((String)this.mode.getValue()).equals("BHop")) {
                if (LongJump.mc.player.isInLava() || LongJump.mc.player.isInWater() || LongJump.mc.player.isOnLadder() || LongJump.mc.player.isInWeb || Anchor.active) {
                    return;
                }
                double speedY = (double)this.jumpHeight.getValue();
                if (LongJump.mc.player.onGround && MotionUtil.isMoving((EntityLivingBase)LongJump.mc.player) && this.timer.hasReached(300L)) {
                    if (LongJump.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                        speedY += (LongJump.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f;
                    }
                    event.setY(LongJump.mc.player.motionY = speedY);
                    this.playerSpeed = (Double)(MotionUtil.getBaseMoveSpeed() * ((EntityUtil.isColliding(0.0, -0.5, 0.0) instanceof BlockLiquid && !EntityUtil.isInLiquid()) ? 0.9 : this.speed.getValue()));
                    this.slowDown = true;
                    this.timer.reset();
                }
                else if (this.slowDown || LongJump.mc.player.collidedHorizontally) {
                    final double doubleValue = this.playerSpeed;
                    double n;
                    if (EntityUtil.isColliding(0.0, -0.8, 0.0) instanceof BlockLiquid && !EntityUtil.isInLiquid()) {
                        n = 0.4;
                    }
                    else {
                        final double n2 = 0.7;
                        final Double value = MotionUtil.getBaseMoveSpeed();
                        this.playerSpeed = value;
                        n = n2 * value;
                    }
                    this.playerSpeed = doubleValue - n;
                    this.slowDown = false;
                }
                else {
                    this.playerSpeed -= this.playerSpeed / 159.0;
                }
                this.playerSpeed = Math.max(this.playerSpeed, MotionUtil.getBaseMoveSpeed());
                final double[] dir = MotionUtil.forward((double)this.playerSpeed);
                event.setX(dir[0]);
                event.setZ(dir[1]);
            }
        }, new Predicate[0]);
    }
    
    public void onEnable() {
        this.playerSpeed = MotionUtil.getBaseMoveSpeed();
        this.mf = LongJump.mc.player.jumpMovementFactor;
    }
    
    public void onDisable() {
        this.timer.reset();
        LongJump.mc.player.jumpMovementFactor = this.mf;
    }
    
    public void onUpdate() {
        double[] dir = MotionUtil.forward((double)this.playerSpeed);
        if (((String)this.mode.getValue()).equalsIgnoreCase("Peak")) {
            if (LongJump.mc.player.onGround) {
                this.hasaccel = false;
            }
            if (LongJump.mc.player.onGround && LongJump.mc.gameSettings.keyBindForward.isKeyDown()) {
                LongJump.mc.player.motionX = dir[0] * ((Double)this.initialFar.getValue()).floatValue();
                LongJump.mc.player.motionZ = dir[1] * ((Double)this.initialFar.getValue()).floatValue();
                LongJump.mc.player.motionY = (double)this.jumpHeight.getValue();
                this.i = 0;
            }
            if (LongJump.mc.player.motionY <= 0.0 && !this.hasaccel) {
                this.hasaccel = !LongJump.mc.player.onGround;
                if (((Integer)this.farAccel.getValue()).equals(0)) {
                    LongJump.mc.player.jumpMovementFactor = ((Double)this.farSpeed.getValue()).floatValue();
                }
                else {
                    ++this.i;
                    LongJump.mc.player.jumpMovementFactor = this.i * (((Double)this.farSpeed.getValue()).floatValue() / (int)this.farAccel.getValue());
                }
            }
        }
        else if (((String)this.mode.getValue()).equalsIgnoreCase("Continuous")) {
            if (LongJump.mc.player.onGround) {
                if (MotionUtil.isMoving((EntityLivingBase)LongJump.mc.player)) {
                    LongJump.mc.player.jump();
                }
                LongJump.mc.player.jumpMovementFactor = this.mf;
                LongJump.mc.player.motionY = (double)this.jumpHeight.getValue();
                dir = MotionUtil.forward(MotionUtil.getBaseMoveSpeed());
                LongJump.mc.player.motionX = dir[0];
                LongJump.mc.player.motionZ = dir[1];
            }
            else {
                LongJump.mc.player.jumpMovementFactor = 0.02f * ((Double)this.factorMax.getValue()).floatValue();
            }
        }
        else if (((String)this.mode.getValue()).equalsIgnoreCase("Ground") && LongJump.mc.player.onGround && MotionUtil.isMoving((EntityLivingBase)LongJump.mc.player)) {
            LongJump.mc.player.motionY = (double)this.jumpHeight.getValue();
            LongJump.mc.player.motionX = dir[0] * (double)this.normalSpeed.getValue();
            LongJump.mc.player.motionZ = dir[1] * (double)this.normalSpeed.getValue();
        }
    }
    
    public String getHudInfo() {
        return "[" + ChatFormatting.WHITE + (String)this.mode.getValue() + ChatFormatting.GRAY + "]";
    }
}
