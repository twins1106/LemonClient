//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.util.misc.*;
import com.lemonclient.api.setting.values.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import net.minecraft.entity.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.client.entity.*;

@Module.Declaration(name = "Speed", category = Category.Movement)
public class Speed extends Module
{
    private final Timer timer;
    public int yl;
    ModeSetting mode;
    DoubleSetting speed;
    BooleanSetting jump;
    BooleanSetting boost;
    DoubleSetting multiply;
    DoubleSetting max;
    DoubleSetting gSpeed;
    DoubleSetting yPortSpeed;
    DoubleSetting onGroundSpeed;
    BooleanSetting strictOG;
    DoubleSetting jumpHeight;
    IntegerSetting jumpDelay;
    BooleanSetting useTimer;
    DoubleSetting timerVal;
    private boolean slowDown;
    private double playerSpeed;
    private double velocity;
    Timer kbTimer;
    @EventHandler
    private final Listener<PlayerMoveEvent> playerMoveEventListener;
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener;
    
    public Speed() {
        this.timer = new Timer();
        this.mode = this.registerMode("Mode", (List)Arrays.asList("Strafe", "GroundStrafe", "OnGround", "Fake", "YPort"), "Strafe");
        this.speed = this.registerDouble("Speed", 2.0, 0.0, 10.0, () -> ((String)this.mode.getValue()).equals("Strafe") || ((String)this.mode.getValue()).equalsIgnoreCase("Beta"));
        this.jump = this.registerBoolean("Jump", true, () -> ((String)this.mode.getValue()).equals("Strafe") || ((String)this.mode.getValue()).equalsIgnoreCase("Beta"));
        this.boost = this.registerBoolean("Boost", false, () -> ((String)this.mode.getValue()).equalsIgnoreCase("Strafe") || ((String)this.mode.getValue()).equalsIgnoreCase("Beta"));
        this.multiply = this.registerDouble("Multiply", 0.8, 0.1, 1.0, () -> (boolean)this.boost.getValue() && this.boost.isVisible());
        this.max = this.registerDouble("Maximum", 0.5, 0.0, 1.0, () -> (boolean)this.boost.getValue() && this.boost.isVisible());
        this.gSpeed = this.registerDouble("Ground Speed", 0.3, 0.0, 0.5, () -> ((String)this.mode.getValue()).equals("GroundStrafe"));
        this.yPortSpeed = this.registerDouble("Speed YPort", 0.06, 0.01, 0.15, () -> ((String)this.mode.getValue()).equals("YPort"));
        this.onGroundSpeed = this.registerDouble("Speed OnGround", 1.5, 0.01, 3.0, () -> ((String)this.mode.getValue()).equalsIgnoreCase("OnGround"));
        this.strictOG = this.registerBoolean("Head Block Only", false, () -> ((String)this.mode.getValue()).equalsIgnoreCase("OnGround"));
        this.jumpHeight = this.registerDouble("Jump Speed", 0.41, 0.0, 1.0, () -> (((String)this.mode.getValue()).equalsIgnoreCase("Strafe") && (boolean)this.jump.getValue()) || (((String)this.mode.getValue()).equalsIgnoreCase("Beta") && (boolean)this.jump.getValue()));
        this.jumpDelay = this.registerInteger("Jump Delay", 300, 0, 1000, () -> (((String)this.mode.getValue()).equalsIgnoreCase("Strafe") && (boolean)this.jump.getValue()) || (((String)this.mode.getValue()).equalsIgnoreCase("Beta") && (boolean)this.jump.getValue()));
        this.useTimer = this.registerBoolean("Timer", false);
        this.timerVal = this.registerDouble("Timer Speed", 1.088, 0.8, 1.2);
        this.kbTimer = new Timer();
        this.playerMoveEventListener = (Listener<PlayerMoveEvent>)new Listener(event -> {
            if (Speed.mc.player.isInLava() || Speed.mc.player.isInWater() || Speed.mc.player.isOnLadder() || Speed.mc.player.isInWeb || Anchor.active) {
                return;
            }
            if (Speed.mc.player.motionX == 0.0 && Speed.mc.player.motionZ == 0.0) {
                return;
            }
            if (((String)this.mode.getValue()).equalsIgnoreCase("Strafe")) {
                double speedY = (double)this.jumpHeight.getValue();
                if (Speed.mc.player.onGround && (boolean)this.jump.getValue()) {
                    Speed.mc.player.jump();
                }
                if (Speed.mc.player.onGround && MotionUtil.isMoving((EntityLivingBase)Speed.mc.player) && this.timer.hasReached((long)(int)this.jumpDelay.getValue())) {
                    if (Speed.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                        speedY += (Objects.requireNonNull(Speed.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST)).getAmplifier() + 1) * 0.1f;
                    }
                    if (this.jump.getValue()) {
                        event.setY(Speed.mc.player.motionY = speedY);
                    }
                    this.playerSpeed = (double)(MotionUtil.getBaseMoveSpeed() * ((EntityUtil.isColliding(0.0, -0.5, 0.0) instanceof BlockLiquid && !EntityUtil.isInLiquid()) ? 0.91 : this.speed.getValue()));
                    this.slowDown = true;
                    this.timer.reset();
                }
                else if (this.slowDown || Speed.mc.player.collidedHorizontally) {
                    this.playerSpeed -= ((EntityUtil.isColliding(0.0, -0.8, 0.0) instanceof BlockLiquid && !EntityUtil.isInLiquid()) ? 0.4 : (0.7 * MotionUtil.getBaseMoveSpeed()));
                    this.slowDown = false;
                }
                else {
                    this.playerSpeed -= this.playerSpeed / 159.0;
                }
                this.playerSpeed = Math.max(this.playerSpeed, MotionUtil.getBaseMoveSpeed());
                if ((boolean)this.boost.getValue() && !this.kbTimer.hasReached(50L)) {
                    this.playerSpeed += Math.min(this.velocity * (double)this.multiply.getValue(), (double)this.max.getValue());
                }
                final double[] dir = MotionUtil.forward(this.playerSpeed);
                event.setX(dir[0]);
                event.setZ(dir[1]);
            }
            if (((String)this.mode.getValue()).equalsIgnoreCase("GroundStrafe")) {
                this.playerSpeed = (double)this.gSpeed.getValue();
                this.playerSpeed *= MotionUtil.getBaseMoveSpeed() / 0.2873;
                if (Speed.mc.player.onGround) {
                    final double[] dir2 = MotionUtil.forward(this.playerSpeed);
                    event.setX(dir2[0]);
                    event.setZ(dir2[1]);
                }
            }
            else if (((String)this.mode.getValue()).equalsIgnoreCase("OnGround")) {
                if (Speed.mc.player.collidedHorizontally) {
                    return;
                }
                double offset = 0.4;
                if (Speed.mc.world.collidesWithAnyBlock(Speed.mc.player.boundingBox.offset(0.0, 0.4, 0.0))) {
                    offset = 2.0 - Speed.mc.player.boundingBox.maxY;
                }
                else if (this.strictOG.getValue()) {
                    return;
                }
                final EntityPlayerSP player = Speed.mc.player;
                player.posY -= offset;
                Speed.mc.player.motionY = -1000.0;
                Speed.mc.player.cameraPitch = 0.3f;
                Speed.mc.player.distanceWalkedModified = 44.0f;
                if (Speed.mc.player.onGround) {
                    final EntityPlayerSP player2 = Speed.mc.player;
                    player2.posY += offset;
                    Speed.mc.player.motionY = offset;
                    Speed.mc.player.distanceWalkedOnStepModified = 44.0f;
                    final EntityPlayerSP player3 = Speed.mc.player;
                    player3.motionX *= (double)this.onGroundSpeed.getValue();
                    final EntityPlayerSP player4 = Speed.mc.player;
                    player4.motionZ *= (double)this.onGroundSpeed.getValue();
                    Speed.mc.player.cameraPitch = 0.0f;
                }
            }
        }, new Predicate[0]);
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketExplosion) {
                this.velocity = Math.abs(((SPacketExplosion)event.getPacket()).motionX) + Math.abs(((SPacketExplosion)event.getPacket()).motionZ);
                this.kbTimer.reset();
            }
            if (event.getPacket() instanceof SPacketEntityVelocity) {
                if (((SPacketEntityVelocity)event.getPacket()).getEntityID() != Speed.mc.player.getEntityId()) {
                    return;
                }
                if (this.velocity < Math.abs(((SPacketEntityVelocity)event.getPacket()).motionX) + Math.abs(((SPacketEntityVelocity)event.getPacket()).motionZ)) {
                    this.velocity = Math.abs(((SPacketEntityVelocity)event.getPacket()).motionX) + Math.abs(((SPacketEntityVelocity)event.getPacket()).motionZ);
                    this.kbTimer.reset();
                }
            }
        }, new Predicate[0]);
    }
    
    public void onEnable() {
        this.playerSpeed = MotionUtil.getBaseMoveSpeed();
        this.yl = (int)Speed.mc.player.posY;
    }
    
    public void onDisable() {
        this.timer.reset();
    }
    
    public void onUpdate() {
        if (Speed.mc.player == null || Speed.mc.world == null) {
            this.disable();
            return;
        }
        if (Anchor.active) {
            return;
        }
        if (((String)this.mode.getValue()).equalsIgnoreCase("YPort")) {
            this.handleYPortSpeed();
        }
        if (this.useTimer.getValue()) {
            TimerUtils.setTickLength(50.0f / ((Double)this.timerVal.getValue()).floatValue());
        }
    }
    
    private void handleYPortSpeed() {
        if (!MotionUtil.isMoving((EntityLivingBase)Speed.mc.player) || (Speed.mc.player.isInWater() && Speed.mc.player.isInLava()) || Speed.mc.player.collidedHorizontally) {
            return;
        }
        if (Speed.mc.player.onGround) {
            Speed.mc.player.jump();
            MotionUtil.setSpeed((EntityLivingBase)Speed.mc.player, MotionUtil.getBaseMoveSpeed() + (double)this.yPortSpeed.getValue());
        }
        else {
            Speed.mc.player.motionY = -1.0;
        }
    }
    
    public String getHudInfo() {
        return "[" + ChatFormatting.WHITE + (String)this.mode.getValue() + ChatFormatting.GRAY + "]";
    }
}
