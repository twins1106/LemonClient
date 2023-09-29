//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.world.*;

@Module.Declaration(name = "EntitySpeed", category = Category.Movement)
public class EntitySpeed extends Module
{
    DoubleSetting speed;
    
    public EntitySpeed() {
        this.speed = this.registerDouble("Speed", 1.0, 0.0, 3.8);
    }
    
    public void onUpdate() {
        if (EntitySpeed.mc.player.ridingEntity != null) {
            final double[] dir = MotionUtil.forward((double)this.speed.getValue());
            EntitySpeed.mc.player.ridingEntity.motionX = dir[0];
            EntitySpeed.mc.player.ridingEntity.motionZ = dir[1];
        }
    }
}
