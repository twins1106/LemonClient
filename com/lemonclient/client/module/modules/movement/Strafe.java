//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.network.play.server.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.math.*;

@Module.Declaration(name = "Strafe", category = Category.Movement, priority = 500)
public class Strafe extends Module
{
    DoubleSetting timerBoost;
    BooleanSetting groundBoost;
    BooleanSetting airBoost;
    BooleanSetting velocityBoost;
    BooleanSetting check;
    DoubleSetting multiply;
    DoubleSetting max;
    BooleanSetting speedPotionBoost;
    DoubleSetting speed;
    DoubleSetting sprintSpeed;
    BooleanSetting autoJump;
    boolean running;
    int burrowTicks;
    int jumpTicks;
    private double velocity;
    Timer kbTimer;
    @EventHandler
    private final Listener<PlayerMoveEvent> listener;
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener;
    
    public Strafe() {
        this.timerBoost = this.registerDouble("Timer Boost", 1.09, 1.0, 1.5);
        this.groundBoost = this.registerBoolean("Ground Boost", false);
        this.airBoost = this.registerBoolean("Air Boost", true);
        this.velocityBoost = this.registerBoolean("Velocity Boost", true);
        this.check = this.registerBoolean("Air Boost Check", true, () -> (boolean)this.airBoost.getValue() && (boolean)this.velocityBoost.getValue());
        this.multiply = this.registerDouble("Multiply", 0.8, 0.1, 1.0, () -> (Boolean)this.velocityBoost.getValue());
        this.max = this.registerDouble("Maximum", 0.5, 0.0, 1.0, () -> (Boolean)this.velocityBoost.getValue());
        this.speedPotionBoost = this.registerBoolean("Speed Potion Boost", true);
        this.speed = this.registerDouble("Speed", 0.22, 0.1, 0.5);
        this.sprintSpeed = this.registerDouble("Sprint Speed", 0.2873, 0.1, 0.5);
        this.autoJump = this.registerBoolean("Auto Jump", false);
        this.running = false;
        this.burrowTicks = 34;
        this.jumpTicks = 0;
        this.kbTimer = new Timer();
        this.listener = (Listener<PlayerMoveEvent>)new Listener(event -> {
            if (Strafe.mc.player.motionX == 0.0 && Strafe.mc.player.motionZ == 0.0) {
                return;
            }
            ++this.jumpTicks;
            final BlockPos playerPos = PlayerUtil.getPlayerPos();
            final AxisAlignedBB box = Strafe.mc.world.getBlockState(playerPos).getCollisionBoundingBox((IBlockAccess)Strafe.mc.world, playerPos);
            if (box != null && box.maxY + playerPos.y > Strafe.mc.player.posY) {
                this.burrowTicks = 0;
            }
            else {
                ++this.burrowTicks;
            }
            if (this.shouldStrafe()) {
                final boolean onGround = Strafe.mc.player.onGround;
                final boolean inputting = MotionUtil.isMoving((EntityLivingBase)Strafe.mc.player);
                if ((onGround && (boolean)this.groundBoost.getValue()) || (!onGround && (boolean)this.airBoost.getValue() && (!(boolean)this.check.getValue() || this.kbTimer.hasReached(50L)))) {
                    if (inputting) {
                        final double yaw = MotionUtil.calcMoveYaw();
                        double baseSpeed = (double)(Strafe.mc.player.isSprinting() ? this.sprintSpeed.getValue() : ((double)this.speed.getValue()));
                        if (Strafe.mc.player.isSneaking()) {
                            baseSpeed *= 0.20000000298023224;
                        }
                        if (this.speedPotionBoost.getValue()) {
                            baseSpeed = this.applySpeedPotionEffects(baseSpeed);
                        }
                        double speed = Math.max(Math.hypot(Strafe.mc.player.motionX, Strafe.mc.player.motionZ), baseSpeed);
                        if ((boolean)this.velocityBoost.getValue() && !this.kbTimer.hasReached(50L)) {
                            speed += Math.min(this.velocity * (double)this.multiply.getValue(), (double)this.max.getValue());
                        }
                        event.setX(-Math.sin(yaw) * speed);
                        event.setZ(Math.cos(yaw) * speed);
                        if (this.burrowTicks >= 10) {
                            TimerUtils.setTickLength((float)(50.0 / (double)this.timerBoost.getValue()));
                        }
                        this.running = true;
                    }
                    else if (this.running) {
                        Strafe.mc.player.motionX = 0.0;
                        Strafe.mc.player.motionZ = 0.0;
                        this.running = false;
                    }
                }
                if ((boolean)this.autoJump.getValue() && inputting && onGround && this.jumpTicks >= 5) {
                    Strafe.mc.player.jump();
                    event.setX(1.2);
                    event.setZ(1.2);
                }
            }
        }, new Predicate[0]);
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketExplosion) {
                this.velocity = Math.abs(((SPacketExplosion)event.getPacket()).motionX) + Math.abs(((SPacketExplosion)event.getPacket()).motionZ);
                this.kbTimer.reset();
            }
            if (event.getPacket() instanceof SPacketEntityVelocity) {
                if (((SPacketEntityVelocity)event.getPacket()).getEntityID() != Strafe.mc.player.getEntityId()) {
                    return;
                }
                if (this.velocity < Math.abs(((SPacketEntityVelocity)event.getPacket()).motionX) + Math.abs(((SPacketEntityVelocity)event.getPacket()).motionZ)) {
                    this.velocity = Math.abs(((SPacketEntityVelocity)event.getPacket()).motionX) + Math.abs(((SPacketEntityVelocity)event.getPacket()).motionZ);
                    this.kbTimer.reset();
                }
            }
        }, new Predicate[0]);
    }
    
    public void onDisable() {
        this.running = false;
        this.burrowTicks = 34;
        this.jumpTicks = 0;
    }
    
    private double applySpeedPotionEffects(final double baseSpeed) {
        double result = baseSpeed;
        if (Strafe.mc.player.isPotionActive(MobEffects.SPEED)) {
            result += 0.2873 * (Objects.requireNonNull(Strafe.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier() + 1) * 0.2;
        }
        if (Strafe.mc.player.isPotionActive(MobEffects.SLOWNESS)) {
            result -= 0.2873 * (Objects.requireNonNull(Strafe.mc.player.getActivePotionEffect(MobEffects.SLOWNESS)).getAmplifier() + 1) * 0.15;
        }
        return result;
    }
    
    private boolean shouldStrafe() {
        return !Strafe.mc.player.isOnLadder() && !Strafe.mc.player.isElytraFlying() && !Strafe.mc.player.capabilities.isFlying && !Strafe.mc.player.isInWater() && !Strafe.mc.player.isInLava() && !Strafe.mc.world.containsAnyLiquid(Strafe.mc.player.getEntityBoundingBox().expand(0.0, -1.0, 0.0));
    }
}
