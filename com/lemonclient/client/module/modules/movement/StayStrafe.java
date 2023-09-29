//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.entity.*;
import com.lemonclient.api.event.*;

@Module.Declaration(name = "StayStrafe", category = Category.Movement)
public class StayStrafe extends Module
{
    ModeSetting mode;
    private double lastDist;
    private double moveSpeed;
    int stage;
    @EventHandler
    private final Listener<MotionUpdate> listener;
    @EventHandler
    private final Listener<MoveEvent> moveEventListener;
    
    public StayStrafe() {
        this.mode = this.registerMode("Mode", (List)Arrays.asList("Normal", "Strict"), "Strict");
        this.listener = (Listener<MotionUpdate>)new Listener(event -> {
            if (event.getEra() == LemonClientEvent.Era.PRE || StayStrafe.mc.world == null || StayStrafe.mc.player == null || StayStrafe.mc.player.isDead) {
                return;
            }
            this.lastDist = Math.sqrt((StayStrafe.mc.player.posX - StayStrafe.mc.player.prevPosX) * (StayStrafe.mc.player.posX - StayStrafe.mc.player.prevPosX) + (StayStrafe.mc.player.posZ - StayStrafe.mc.player.prevPosZ) * (StayStrafe.mc.player.posZ - StayStrafe.mc.player.prevPosZ));
        }, new Predicate[0]);
        this.moveEventListener = (Listener<MoveEvent>)new Listener(event -> {
            if (StayStrafe.mc.world == null || StayStrafe.mc.player == null || StayStrafe.mc.player.isDead) {
                return;
            }
            if (StayStrafe.mc.player.isInWater()) {
                return;
            }
            if (StayStrafe.mc.player.isInLava()) {
                return;
            }
            if (!StayStrafe.mc.player.isEntityAlive() || StayStrafe.mc.player.isElytraFlying() || StayStrafe.mc.player.capabilities.isFlying) {
                return;
            }
            if (StayStrafe.mc.player.onGround) {
                this.stage = 2;
            }
            switch (this.stage) {
                case 0: {
                    ++this.stage;
                    this.lastDist = 0.0;
                    break;
                }
                case 2: {
                    double motionY = 0.40123128;
                    if (!StayStrafe.mc.player.onGround) {
                        break;
                    }
                    if (!StayStrafe.mc.gameSettings.keyBindJump.isKeyDown()) {
                        break;
                    }
                    if (StayStrafe.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                        motionY += (Objects.requireNonNull(StayStrafe.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST)).getAmplifier() + 1) * 0.1f;
                    }
                    event.setMotionY(StayStrafe.mc.player.motionY = motionY);
                    this.moveSpeed *= (((String)this.mode.getValue()).equals("Normal") ? 1.67 : 2.149);
                    break;
                }
                case 3: {
                    this.moveSpeed = this.lastDist - (((String)this.mode.getValue()).equals("Normal") ? 0.6896 : 0.795) * (this.lastDist - this.getBaseMoveSpeed());
                    break;
                }
                default: {
                    if ((StayStrafe.mc.world.getCollisionBoxes((Entity)StayStrafe.mc.player, StayStrafe.mc.player.getEntityBoundingBox().offset(0.0, StayStrafe.mc.player.motionY, 0.0)).size() > 0 || StayStrafe.mc.player.collidedVertically) && this.stage > 0) {
                        this.stage = ((StayStrafe.mc.player.moveForward != 0.0f || StayStrafe.mc.player.moveStrafing != 0.0f) ? 1 : 0);
                    }
                    this.moveSpeed = this.lastDist - this.lastDist / (((String)this.mode.getValue()).equals("Normal") ? 730.0 : 159.0);
                    break;
                }
            }
            this.moveSpeed = ((!StayStrafe.mc.gameSettings.keyBindJump.isKeyDown() && StayStrafe.mc.player.onGround) ? this.getBaseMoveSpeed() : Math.max(this.moveSpeed, this.getBaseMoveSpeed()));
            double n = StayStrafe.mc.player.movementInput.moveForward;
            double n2 = StayStrafe.mc.player.movementInput.moveStrafe;
            final double n3 = StayStrafe.mc.player.rotationYaw;
            if (n == 0.0 && n2 == 0.0) {
                event.setMotionX(0.0);
                event.setMotionZ(0.0);
            }
            else if (n != 0.0 && n2 != 0.0) {
                n *= Math.sin(0.7853981633974483);
                n2 *= Math.cos(0.7853981633974483);
            }
            final double n4 = ((String)this.mode.getValue()).equals("Normal") ? 0.993 : 0.99;
            event.setMotionX((n * this.moveSpeed * -Math.sin(Math.toRadians(n3)) + n2 * this.moveSpeed * Math.cos(Math.toRadians(n3))) * n4);
            event.setMotionZ((n * this.moveSpeed * Math.cos(Math.toRadians(n3)) - n2 * this.moveSpeed * -Math.sin(Math.toRadians(n3))) * n4);
            ++this.stage;
            event.setCanceled(true);
        }, new Predicate[0]);
    }
    
    public double getBaseMoveSpeed() {
        double n = 0.2873;
        if (!StayStrafe.mc.player.isPotionActive(MobEffects.SPEED)) {
            return n;
        }
        n *= 1.0 + 0.2 * (Objects.requireNonNull(StayStrafe.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier() + 1);
        return n;
    }
}
