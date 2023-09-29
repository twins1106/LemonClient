//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

@Module.Declaration(name = "BoatFly", category = Category.Movement)
public class BoatFly extends Module
{
    DoubleSetting speed;
    DoubleSetting ySpeed;
    DoubleSetting glideSpeed;
    BooleanSetting hover;
    BooleanSetting bypass;
    
    public BoatFly() {
        this.speed = this.registerDouble("Speed", 2.0, 0.0, 10.0);
        this.ySpeed = this.registerDouble("Y Speed", 1.0, 0.0, 10.0);
        this.glideSpeed = this.registerDouble("Glide Speed", 0.0, -10.0, 10.0);
        this.hover = this.registerBoolean("Hover", false);
        this.bypass = this.registerBoolean("Bypass", false);
    }
    
    public void onUpdate() {
        final Entity e = BoatFly.mc.player.ridingEntity;
        if (e == null) {
            return;
        }
        if (BoatFly.mc.gameSettings.keyBindJump.isKeyDown()) {
            e.motionY = (double)this.ySpeed.getValue();
        }
        else if (BoatFly.mc.gameSettings.keyBindSneak.isKeyDown()) {
            e.motionY = -(double)this.ySpeed.getValue();
        }
        else {
            e.motionY = (double)(((boolean)this.hover.getValue() && BoatFly.mc.player.ticksExisted % 2 == 0) ? this.glideSpeed.getValue() : (-(double)this.glideSpeed.getValue()));
        }
        if (MotionUtil.isMoving((EntityLivingBase)BoatFly.mc.player)) {
            final double[] dir = MotionUtil.forward((double)this.speed.getValue());
            e.motionX = dir[0];
            e.motionZ = dir[1];
        }
        else {
            e.motionX = 0.0;
            e.motionZ = 0.0;
        }
        if ((boolean)this.bypass.getValue() && BoatFly.mc.player.ticksExisted % 4 == 0 && BoatFly.mc.player.ridingEntity instanceof EntityBoat) {
            BoatFly.mc.playerController.interactWithEntity((EntityPlayer)BoatFly.mc.player, BoatFly.mc.player.ridingEntity, EnumHand.MAIN_HAND);
        }
    }
}
