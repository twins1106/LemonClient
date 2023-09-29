//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.text.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

@Module.Declaration(name = "TickShift", category = Category.Movement)
public class TickShift extends Module
{
    IntegerSetting limit;
    DoubleSetting timer;
    BooleanSetting doDecay;
    DoubleSetting min;
    int ticks;
    
    public TickShift() {
        this.limit = this.registerInteger("Limit", 16, 1, 50);
        this.timer = this.registerDouble("Timer", 2.0, 1.0, 5.0);
        this.doDecay = this.registerBoolean("Decay", false);
        this.min = this.registerDouble("Lowest", 1.4, 1.0, 5.0);
    }
    
    protected void onEnable() {
        TimerUtils.setTickLength(50.0f);
        this.ticks = 0;
    }
    
    protected void onDisable() {
        TimerUtils.setTickLength(50.0f);
    }
    
    public void onUpdate() {
        if (this.isMoving()) {
            if (this.ticks > 0 && !isPlayerClipped()) {
                final double diff = (double)this.timer.getValue() - (double)this.min.getValue();
                final double steps = diff / (int)this.limit.getValue();
                final double ourTimer = (double)(this.doDecay.getValue() ? ((double)this.min.getValue() + steps) : this.timer.getValue());
                if (this.ticks > 0) {
                    TimerUtils.setTickLength(((boolean)this.doDecay.getValue()) ? ((float)Math.max(50.0 / ourTimer, 50.0)) : (50.0f / ((Double)this.timer.getValue()).floatValue()));
                }
            }
            if (this.ticks > 0) {
                --this.ticks;
                if (this.ticks == 0) {
                    TimerUtils.setTickLength(50.0f);
                }
            }
        }
        else {
            if (!MotionUtil.isMoving((EntityLivingBase)TickShift.mc.player)) {
                TickShift.mc.player.motionX = 0.0;
                TickShift.mc.player.motionZ = 0.0;
            }
            TimerUtils.setTickLength(50.0f);
            if (this.ticks < (int)this.limit.getValue()) {
                ++this.ticks;
            }
        }
    }
    
    public String getHudInfo() {
        return TextFormatting.GRAY + "[" + this.getColour(this.ticks) + this.ticks + TextFormatting.GRAY + "]";
    }
    
    public TextFormatting getColour(final int ticks) {
        if (ticks == 0) {
            return TextFormatting.RED;
        }
        if (ticks <= (int)this.limit.getValue()) {
            return TextFormatting.GREEN;
        }
        return TextFormatting.GOLD;
    }
    
    public static boolean isPlayerClipped() {
        return !TickShift.mc.world.getCollisionBoxes((Entity)TickShift.mc.player, TickShift.mc.player.getEntityBoundingBox()).isEmpty();
    }
    
    boolean isMoving() {
        return getMotion((EntityPlayer)TickShift.mc.player) + Math.abs(TickShift.mc.player.posY - TickShift.mc.player.prevPosY) != 0.0;
    }
    
    public static double getMotion(final EntityPlayer entity) {
        return Math.abs(entity.motionX) + Math.abs(entity.motionZ);
    }
}
