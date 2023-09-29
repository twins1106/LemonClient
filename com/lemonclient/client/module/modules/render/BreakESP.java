//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import me.zero.alpine.listener.*;
import java.util.*;
import java.util.function.*;
import com.lemonclient.api.event.events.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.render.*;
import net.minecraft.client.renderer.*;

@Module.Declaration(name = "BreakESP", category = Category.Render)
public class BreakESP extends Module
{
    ModeSetting renderType;
    IntegerSetting lineWidth;
    IntegerSetting range;
    BooleanSetting cancelAnimation;
    ColorSetting color;
    @EventHandler
    private final Listener<DrawBlockDamageEvent> drawBlockDamageEventListener;
    
    public BreakESP() {
        this.renderType = this.registerMode("Render", (List)Arrays.asList("Outline", "Fill", "Both"), "Both");
        this.lineWidth = this.registerInteger("Width", 1, 0, 5);
        this.range = this.registerInteger("Range", 100, 1, 200);
        this.cancelAnimation = this.registerBoolean("No Animation", true);
        this.color = this.registerColor("Color", new GSColor(0, 255, 0, 255));
        this.drawBlockDamageEventListener = (Listener<DrawBlockDamageEvent>)new Listener(event -> {
            if (this.cancelAnimation.getValue()) {
                event.cancel();
            }
        }, new Predicate[0]);
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (BreakESP.mc.player == null || BreakESP.mc.world == null) {
            return;
        }
        BlockPos blockPos;
        int progress;
        AxisAlignedBB axisAlignedBB;
        BreakESP.mc.renderGlobal.damagedBlocks.forEach((integer, destroyBlockProgress) -> {
            if (destroyBlockProgress != null) {
                blockPos = destroyBlockProgress.getPosition();
                if (BreakESP.mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR) {
                    if (blockPos.getDistance((int)BreakESP.mc.player.posX, (int)BreakESP.mc.player.posY, (int)BreakESP.mc.player.posZ) <= (int)this.range.getValue()) {
                        progress = destroyBlockProgress.getPartialBlockDamage();
                        axisAlignedBB = BreakESP.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)BreakESP.mc.world, blockPos);
                        this.renderESP(axisAlignedBB, progress, this.color.getValue());
                    }
                }
            }
        });
    }
    
    private void renderESP(final AxisAlignedBB axisAlignedBB, final int progress, final GSColor color) {
        final GSColor fillColor = new GSColor(color, 50);
        final GSColor outlineColor = new GSColor(color, 255);
        final double centerX = axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0;
        final double centerY = axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) / 2.0;
        final double centerZ = axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0;
        final double progressValX = progress * ((axisAlignedBB.maxX - centerX) / 10.0);
        final double progressValY = progress * ((axisAlignedBB.maxY - centerY) / 10.0);
        final double progressValZ = progress * ((axisAlignedBB.maxZ - centerZ) / 10.0);
        final AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(centerX - progressValX, centerY - progressValY, centerZ - progressValZ, centerX + progressValX, centerY + progressValY, centerZ + progressValZ);
        final String s = (String)this.renderType.getValue();
        switch (s) {
            case "Fill": {
                RenderUtil.drawBox(axisAlignedBB2, true, 0.0, fillColor, 63);
                break;
            }
            case "Outline": {
                RenderUtil.drawBoundingBox(axisAlignedBB2, (double)(int)this.lineWidth.getValue(), outlineColor);
                break;
            }
            case "Both": {
                RenderUtil.drawBox(axisAlignedBB2, true, 0.0, fillColor, 63);
                RenderUtil.drawBoundingBox(axisAlignedBB2, (double)(int)this.lineWidth.getValue(), outlineColor);
                break;
            }
        }
    }
}
