//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.lemonclient.client.*;
import com.lemonclient.client.module.modules.misc.*;
import com.lemonclient.client.module.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.event.events.*;
import java.util.*;

@Mixin({ Entity.class })
public class MixinEntity
{
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    
    @Inject(method = { "applyEntityCollision" }, at = { @At("HEAD") }, cancellable = true)
    public void velocity(final Entity entityIn, final CallbackInfo ci) {
        final EntityCollisionEvent event = new EntityCollisionEvent();
        LemonClient.EVENT_BUS.post((Object)event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Redirect(method = { "move" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSneaking()Z"))
    public boolean isSneaking(final Entity entity) {
        return (ModuleManager.isModuleEnabled((Class)Scaffold.class) && !Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown()) || entity.isSneaking();
    }
    
    @Inject(method = { "move" }, at = { @At("HEAD") })
    public void move(final MoverType type, final double tx, final double ty, final double tz, final CallbackInfo ci) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.getCurrentServerData() == null) {
            return;
        }
        double x = tx;
        double y = ty;
        double z = tz;
        if (ci.isCancelled()) {
            return;
        }
        AxisAlignedBB bb = mc.player.getEntityBoundingBox();
        if (!mc.player.noClip) {
            if (type.equals((Object)MoverType.PISTON)) {
                return;
            }
            mc.world.profiler.startSection("move");
            if (mc.player.isInWeb) {
                return;
            }
            double d2 = x;
            final double d3 = y;
            double d4 = z;
            if ((type == MoverType.SELF || type == MoverType.PLAYER) && mc.player.onGround && mc.player.isSneaking()) {
                while (x != 0.0 && mc.world.getCollisionBoxes((Entity)mc.player, bb.offset(x, (double)(-mc.player.stepHeight), 0.0)).isEmpty()) {
                    if (x < 0.05 && x >= -0.05) {
                        x = 0.0;
                    }
                    else if (x > 0.0) {
                        x -= 0.05;
                    }
                    else {
                        x += 0.05;
                    }
                    d2 = x;
                }
                while (z != 0.0 && mc.world.getCollisionBoxes((Entity)mc.player, bb.offset(0.0, (double)(-mc.player.stepHeight), z)).isEmpty()) {
                    if (z < 0.05 && z >= -0.05) {
                        z = 0.0;
                    }
                    else if (z > 0.0) {
                        z -= 0.05;
                    }
                    else {
                        z += 0.05;
                    }
                    d4 = z;
                }
                while (x != 0.0 && z != 0.0 && mc.world.getCollisionBoxes((Entity)mc.player, bb.offset(x, (double)(-mc.player.stepHeight), z)).isEmpty()) {
                    if (x < 0.05 && x >= -0.05) {
                        x = 0.0;
                    }
                    else if (x > 0.0) {
                        x -= 0.05;
                    }
                    else {
                        x += 0.05;
                    }
                    d2 = x;
                    if (z < 0.05 && z >= -0.05) {
                        z = 0.0;
                    }
                    else if (z > 0.0) {
                        z -= 0.05;
                    }
                    else {
                        z += 0.05;
                    }
                    d4 = z;
                }
            }
            final List<AxisAlignedBB> list1 = (List<AxisAlignedBB>)mc.world.getCollisionBoxes((Entity)mc.player, bb.expand(x, y, z));
            if (y != 0.0) {
                for (int k = 0, l = list1.size(); k < l; ++k) {
                    y = list1.get(k).calculateYOffset(bb, y);
                }
                bb = bb.offset(0.0, y, 0.0);
            }
            if (x != 0.0) {
                for (int j5 = 0, l2 = list1.size(); j5 < l2; ++j5) {
                    x = list1.get(j5).calculateXOffset(bb, x);
                }
                if (x != 0.0) {
                    bb = bb.offset(x, 0.0, 0.0);
                }
            }
            if (z != 0.0) {
                for (int k2 = 0, i6 = list1.size(); k2 < i6; ++k2) {
                    z = list1.get(k2).calculateZOffset(bb, z);
                }
                if (z != 0.0) {
                    bb = bb.offset(0.0, 0.0, z);
                }
            }
            final boolean flag = mc.player.onGround || (d3 != y && d3 < 0.0);
            if (mc.player.stepHeight > 0.0f && flag && (d2 != x || d4 != z)) {
                final double d5 = x;
                final double d6 = z;
                y = mc.player.stepHeight;
                final List<AxisAlignedBB> list2 = (List<AxisAlignedBB>)mc.world.getCollisionBoxes((Entity)mc.player, bb.expand(d2, y, d4));
                AxisAlignedBB axisalignedbb2 = bb;
                final AxisAlignedBB axisalignedbb3 = axisalignedbb2.expand(d2, 0.0, d4);
                double d7 = y;
                for (int j6 = 0, k3 = list2.size(); j6 < k3; ++j6) {
                    d7 = list2.get(j6).calculateYOffset(axisalignedbb3, d7);
                }
                axisalignedbb2 = axisalignedbb2.offset(0.0, d7, 0.0);
                double d8 = d2;
                for (int l3 = 0, i7 = list2.size(); l3 < i7; ++l3) {
                    d8 = list2.get(l3).calculateXOffset(axisalignedbb2, d8);
                }
                axisalignedbb2 = axisalignedbb2.offset(d8, 0.0, 0.0);
                double d9 = d4;
                for (int j7 = 0, k4 = list2.size(); j7 < k4; ++j7) {
                    d9 = list2.get(j7).calculateZOffset(axisalignedbb2, d9);
                }
                axisalignedbb2 = axisalignedbb2.offset(0.0, 0.0, d9);
                AxisAlignedBB axisalignedbb4 = bb;
                double d10 = y;
                for (int l4 = 0, i8 = list2.size(); l4 < i8; ++l4) {
                    d10 = list2.get(l4).calculateYOffset(axisalignedbb4, d10);
                }
                axisalignedbb4 = axisalignedbb4.offset(0.0, d10, 0.0);
                double d11 = d2;
                for (int j8 = 0, k5 = list2.size(); j8 < k5; ++j8) {
                    d11 = list2.get(j8).calculateXOffset(axisalignedbb4, d11);
                }
                axisalignedbb4 = axisalignedbb4.offset(d11, 0.0, 0.0);
                double d12 = d4;
                for (int l5 = 0, i9 = list2.size(); l5 < i9; ++l5) {
                    d12 = list2.get(l5).calculateZOffset(axisalignedbb4, d12);
                }
                axisalignedbb4 = axisalignedbb4.offset(0.0, 0.0, d12);
                final double d13 = d8 * d8 + d9 * d9;
                final double d14 = d11 * d11 + d12 * d12;
                if (d13 > d14) {
                    x = d8;
                    z = d9;
                    y = -d7;
                    bb = axisalignedbb2;
                }
                else {
                    x = d11;
                    z = d12;
                    y = -d10;
                    bb = axisalignedbb4;
                }
                for (int j9 = 0, k6 = list2.size(); j9 < k6; ++j9) {
                    y = list2.get(j9).calculateYOffset(bb, y);
                }
                bb = bb.offset(0.0, y, 0.0);
                if (d5 * d5 + d6 * d6 < x * x + z * z) {
                    final StepEvent event = new StepEvent(bb);
                    LemonClient.EVENT_BUS.post((Object)event);
                    if (event.isCancelled()) {
                        mc.player.stepHeight = 0.5f;
                    }
                }
            }
        }
    }
}
