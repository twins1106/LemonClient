//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;

@Module.Declaration(name = "FastFall", category = Category.Movement)
public class FastFall extends Module
{
    DoubleSetting dist;
    DoubleSetting speed;
    
    public FastFall() {
        this.dist = this.registerDouble("Min Distance", 3.0, 0.0, 25.0);
        this.speed = this.registerDouble("Multiplier", 3.0, 0.0, 10.0);
    }
    
    public void onUpdate() {
        if (FastFall.mc.world.isAirBlock(new BlockPos(FastFall.mc.player.getPositionVector())) && FastFall.mc.player.onGround && (!FastFall.mc.player.isElytraFlying() || FastFall.mc.player.fallDistance < (double)this.dist.getValue() || !FastFall.mc.player.capabilities.isFlying)) {
            final EntityPlayerSP player = FastFall.mc.player;
            player.motionY -= (double)this.speed.getValue();
        }
    }
}
