//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import java.util.concurrent.*;
import com.google.common.collect.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.world.*;
import java.util.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import com.lemonclient.api.util.render.*;

@Module.Declaration(name = "HoleESP", category = Category.Render)
public class HoleESP extends Module
{
    public IntegerSetting range;
    IntegerSetting Yrange;
    BooleanSetting single;
    BooleanSetting Double;
    BooleanSetting fourBlocks;
    BooleanSetting custom;
    ModeSetting type;
    ModeSetting mode;
    BooleanSetting hideOwn;
    BooleanSetting flatOwn;
    BooleanSetting fov;
    DoubleSetting slabHeight;
    DoubleSetting outslabHeight;
    IntegerSetting width;
    ColorSetting bedrockColor;
    ColorSetting obsidianColor;
    ColorSetting twobedrockColor;
    ColorSetting twoobsidianColor;
    ColorSetting fourColor;
    ColorSetting customColor;
    IntegerSetting alpha;
    IntegerSetting ufoAlpha;
    private ConcurrentHashMap<AxisAlignedBB, GSColor> holes;
    
    public HoleESP() {
        this.range = this.registerInteger("Range", 5, 1, 20);
        this.Yrange = this.registerInteger("Y Range", 5, 1, 20);
        this.single = this.registerBoolean("1x1", true);
        this.Double = this.registerBoolean("2x1", true);
        this.fourBlocks = this.registerBoolean("4x4", true);
        this.custom = this.registerBoolean("Custom", true);
        this.type = this.registerMode("Render", (List)Arrays.asList("Outline", "Fill", "Both"), "Both");
        this.mode = this.registerMode("Mode", (List)Arrays.asList("Air", "Ground", "Flat", "Slab", "Double"), "Air");
        this.hideOwn = this.registerBoolean("Hide Own", false);
        this.flatOwn = this.registerBoolean("Flat Own", false);
        this.fov = this.registerBoolean("In Fov", false);
        this.slabHeight = this.registerDouble("Slab Height", 0.5, 0.0, 2.0);
        this.outslabHeight = this.registerDouble("Outline Height", 0.5, 0.0, 2.0);
        this.width = this.registerInteger("Width", 1, 1, 10);
        this.bedrockColor = this.registerColor("Bedrock Color", new GSColor(0, 255, 0));
        this.obsidianColor = this.registerColor("Obsidian Color", new GSColor(255, 0, 0));
        this.twobedrockColor = this.registerColor("2x1 Bedrock Color", new GSColor(0, 255, 0));
        this.twoobsidianColor = this.registerColor("2x1 Obsidian Color", new GSColor(255, 0, 0));
        this.fourColor = this.registerColor("4x4 Color", new GSColor(255, 0, 0));
        this.customColor = this.registerColor("Custom Color", new GSColor(0, 0, 255));
        this.alpha = this.registerInteger("Alpha", 50, 0, 255);
        this.ufoAlpha = this.registerInteger("UFOAlpha", 255, 0, 255);
    }
    
    public void onUpdate() {
        if (HoleESP.mc.player == null || HoleESP.mc.world == null) {
            return;
        }
        if (this.holes == null) {
            this.holes = new ConcurrentHashMap<AxisAlignedBB, GSColor>();
        }
        else {
            this.holes.clear();
        }
        final HashSet<BlockPos> possibleHoles = (HashSet<BlockPos>)Sets.newHashSet();
        final List<BlockPos> blockPosList = (List<BlockPos>)EntityUtil.getSphere(PlayerUtil.getPlayerPos(), java.lang.Double.valueOf((double)this.range.getValue()), java.lang.Double.valueOf((double)this.Yrange.getValue()), false, false, 0);
        for (final BlockPos pos2 : blockPosList) {
            if ((boolean)this.fov.getValue() && !RotationUtil.isInFov(pos2)) {
                continue;
            }
            if (!HoleESP.mc.world.getBlockState(pos2).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (HoleESP.mc.world.getBlockState(pos2.add(0, -1, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (!HoleESP.mc.world.getBlockState(pos2.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (!HoleESP.mc.world.getBlockState(pos2.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            possibleHoles.add(pos2);
        }
        final HoleUtil.HoleInfo holeInfo;
        final HoleUtil.HoleType holeType;
        HoleUtil.BlockSafety holeSafety;
        AxisAlignedBB centreBlocks;
        GSColor colour;
        GSColor colour2;
        GSColor colour3;
        GSColor colour4;
        possibleHoles.forEach(pos -> {
            holeInfo = HoleUtil.isHole(pos, false, false, true);
            holeType = holeInfo.getType();
            if (holeType != HoleUtil.HoleType.NONE) {
                holeSafety = holeInfo.getSafety();
                centreBlocks = holeInfo.getCentre();
                if (centreBlocks != null) {
                    if ((boolean)this.fourBlocks.getValue() && holeType == HoleUtil.HoleType.FOUR) {
                        colour = new GSColor(this.fourColor.getValue(), 255);
                        this.holes.put(centreBlocks, colour);
                    }
                    else if ((boolean)this.custom.getValue() && holeType == HoleUtil.HoleType.CUSTOM) {
                        colour2 = new GSColor(this.customColor.getValue(), 255);
                        this.holes.put(centreBlocks, colour2);
                    }
                    else if ((boolean)this.Double.getValue() && holeType == HoleUtil.HoleType.DOUBLE) {
                        if (holeSafety == HoleUtil.BlockSafety.UNBREAKABLE) {
                            colour3 = new GSColor(this.twobedrockColor.getValue(), 255);
                        }
                        else {
                            colour3 = new GSColor(this.twoobsidianColor.getValue(), 255);
                        }
                        this.holes.put(centreBlocks, colour3);
                    }
                    else if ((boolean)this.single.getValue() && holeType == HoleUtil.HoleType.SINGLE) {
                        if (holeSafety == HoleUtil.BlockSafety.UNBREAKABLE) {
                            colour4 = new GSColor(this.bedrockColor.getValue(), 255);
                        }
                        else {
                            colour4 = new GSColor(this.obsidianColor.getValue(), 255);
                        }
                        this.holes.put(centreBlocks, colour4);
                    }
                }
            }
        });
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (HoleESP.mc.player == null || HoleESP.mc.world == null || this.holes == null || this.holes.isEmpty()) {
            return;
        }
        this.holes.forEach(this::renderHoles);
    }
    
    private void renderHoles(final AxisAlignedBB hole, final GSColor color) {
        final String s = (String)this.type.getValue();
        switch (s) {
            case "Outline": {
                this.renderOutline(hole, color);
                break;
            }
            case "Fill": {
                this.renderFill(hole, color);
                break;
            }
            case "Both": {
                this.renderOutline(hole, color);
                this.renderFill(hole, color);
                break;
            }
        }
    }
    
    private void renderFill(final AxisAlignedBB hole, final GSColor color) {
        final GSColor fillColor = new GSColor(color, (int)this.alpha.getValue());
        final int ufoAlpha = (int)this.ufoAlpha.getValue() * 50 / 255;
        if ((boolean)this.hideOwn.getValue() && hole.intersects(HoleESP.mc.player.getEntityBoundingBox())) {
            return;
        }
        final String s = (String)this.mode.getValue();
        switch (s) {
            case "Air": {
                if ((boolean)this.flatOwn.getValue() && hole.intersects(HoleESP.mc.player.getEntityBoundingBox())) {
                    RenderUtil.drawBox(hole, true, 1.0, fillColor, ufoAlpha, 1);
                    break;
                }
                RenderUtil.drawBox(hole, true, 1.0, fillColor, ufoAlpha, 63);
                break;
            }
            case "Ground": {
                RenderUtil.drawBox(hole.offset(0.0, -1.0, 0.0), true, 1.0, new GSColor(fillColor, ufoAlpha), fillColor.getAlpha(), 63);
                break;
            }
            case "Flat": {
                RenderUtil.drawBox(hole, true, 1.0, fillColor, ufoAlpha, 1);
                break;
            }
            case "Slab": {
                if ((boolean)this.flatOwn.getValue() && hole.intersects(HoleESP.mc.player.getEntityBoundingBox())) {
                    RenderUtil.drawBox(hole, true, 1.0, fillColor, ufoAlpha, 1);
                    break;
                }
                RenderUtil.drawBox(hole, false, (double)this.slabHeight.getValue(), fillColor, ufoAlpha, 63);
                break;
            }
            case "Double": {
                if ((boolean)this.flatOwn.getValue() && hole.intersects(HoleESP.mc.player.getEntityBoundingBox())) {
                    RenderUtil.drawBox(hole, true, 1.0, fillColor, ufoAlpha, 1);
                    break;
                }
                RenderUtil.drawBox(hole.setMaxY(hole.maxY + 1.0), true, 2.0, fillColor, ufoAlpha, 63);
                break;
            }
        }
    }
    
    private void renderOutline(final AxisAlignedBB hole, final GSColor color) {
        final GSColor outlineColor = new GSColor(color, 255);
        if ((boolean)this.hideOwn.getValue() && hole.intersects(HoleESP.mc.player.getEntityBoundingBox())) {
            return;
        }
        final String s = (String)this.mode.getValue();
        switch (s) {
            case "Air": {
                if ((boolean)this.flatOwn.getValue() && hole.intersects(HoleESP.mc.player.getEntityBoundingBox())) {
                    RenderUtil.drawBoundingBoxWithSides(hole, (int)this.width.getValue(), outlineColor, (int)this.ufoAlpha.getValue(), 1);
                    break;
                }
                RenderUtil.drawBoundingBox(hole, (double)(int)this.width.getValue(), outlineColor, (int)this.ufoAlpha.getValue());
                break;
            }
            case "Ground": {
                RenderUtil.drawBoundingBox(hole.offset(0.0, -1.0, 0.0), (double)(int)this.width.getValue(), new GSColor(outlineColor, (int)this.ufoAlpha.getValue()), outlineColor.getAlpha());
                break;
            }
            case "Flat": {
                RenderUtil.drawBoundingBoxWithSides(hole, (int)this.width.getValue(), outlineColor, (int)this.ufoAlpha.getValue(), 1);
                break;
            }
            case "Slab": {
                if ((boolean)this.flatOwn.getValue() && hole.intersects(HoleESP.mc.player.getEntityBoundingBox())) {
                    RenderUtil.drawBoundingBoxWithSides(hole, (int)this.width.getValue(), outlineColor, (int)this.ufoAlpha.getValue(), 1);
                    break;
                }
                RenderUtil.drawBoundingBox(hole.setMaxY(hole.minY + (double)this.outslabHeight.getValue()), (double)(int)this.width.getValue(), outlineColor, (int)this.ufoAlpha.getValue());
                break;
            }
            case "Double": {
                if ((boolean)this.flatOwn.getValue() && hole.intersects(HoleESP.mc.player.getEntityBoundingBox())) {
                    RenderUtil.drawBoundingBoxWithSides(hole, (int)this.width.getValue(), outlineColor, (int)this.ufoAlpha.getValue(), 1);
                    break;
                }
                RenderUtil.drawBoundingBox(hole.setMaxY(hole.maxY + 1.0), (double)(int)this.width.getValue(), outlineColor, (int)this.ufoAlpha.getValue());
                break;
            }
        }
    }
}
