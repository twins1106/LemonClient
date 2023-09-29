//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.math.*;

@Module.Declaration(name = "PitchAnchor", category = Category.Movement)
public class PitchAnchor extends Module
{
    IntegerSetting centerSpeed;
    IntegerSetting height;
    IntegerSetting Pitch;
    BooleanSetting Pull;
    
    public PitchAnchor() {
        this.centerSpeed = this.registerInteger("Center Speed", 2, 10, 1);
        this.height = this.registerInteger("Height", 2, 1, 10);
        this.Pitch = this.registerInteger("Pitch", 60, 0, 90);
        this.Pull = this.registerBoolean("Center", true);
    }
    
    public Vec3d getCenter(final AxisAlignedBB box) {
        return new Vec3d(box.minX + (box.maxX - box.minX) / 2.0, box.minY, box.minZ + (box.maxZ - box.minZ) / 2.0);
    }
    
    public void onUpdate() {
        if (PitchAnchor.mc.player.rotationPitch >= (int)this.Pitch.getValue()) {
            BlockPos currentBlock = PlayerUtil.getPlayerPos();
            for (int i = 0; i < (int)this.height.getValue(); ++i) {
                currentBlock = currentBlock.down();
                if (PitchAnchor.mc.world.isAirBlock(currentBlock) && PitchAnchor.mc.world.isAirBlock(currentBlock.up())) {
                    if (PitchAnchor.mc.world.isAirBlock(currentBlock.up(2))) {
                        final HoleUtil.HoleInfo holeInfo = HoleUtil.isHole(currentBlock, false, false, false);
                        final HoleUtil.HoleType holeType = holeInfo.getType();
                        if (holeType != HoleUtil.HoleType.NONE) {
                            if (!(boolean)this.Pull.getValue()) {
                                PitchAnchor.mc.player.motionX = 0.0;
                                PitchAnchor.mc.player.motionZ = 0.0;
                            }
                            else {
                                final Vec3d center = this.getCenter(holeInfo.getCentre());
                                final double XDiff = Math.abs(center.x - PitchAnchor.mc.player.posX);
                                final double ZDiff = Math.abs(center.z - PitchAnchor.mc.player.posZ);
                                if (XDiff > 0.1 || ZDiff > 0.1) {
                                    final double MotionX = center.x - PitchAnchor.mc.player.posX;
                                    final double MotionZ = center.z - PitchAnchor.mc.player.posZ;
                                    PitchAnchor.mc.player.motionX = MotionX / (int)this.centerSpeed.getValue();
                                    PitchAnchor.mc.player.motionZ = MotionZ / (int)this.centerSpeed.getValue();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
