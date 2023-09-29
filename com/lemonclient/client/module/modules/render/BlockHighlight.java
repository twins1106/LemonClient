//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import java.util.*;
import com.lemonclient.api.event.events.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.render.*;

@Module.Declaration(name = "BlockHighlight", category = Category.Render)
public class BlockHighlight extends Module
{
    ModeSetting renderLook;
    ModeSetting renderType;
    IntegerSetting lineWidth;
    ColorSetting renderColor;
    private int lookInt;
    
    public BlockHighlight() {
        this.renderLook = this.registerMode("Render", (List)Arrays.asList("Block", "Side"), "Block");
        this.renderType = this.registerMode("Type", (List)Arrays.asList("Outline", "Fill", "Both"), "Outline");
        this.lineWidth = this.registerInteger("Width", 1, 1, 5);
        this.renderColor = this.registerColor("Color", new GSColor(255, 0, 0, 255));
    }
    
    public void onWorldRender(final RenderEvent event) {
        final RayTraceResult rayTraceResult = BlockHighlight.mc.objectMouseOver;
        if (rayTraceResult == null) {
            return;
        }
        final EnumFacing enumFacing = BlockHighlight.mc.objectMouseOver.sideHit;
        if (enumFacing == null) {
            return;
        }
        final GSColor colorWithOpacity = new GSColor(this.renderColor.getValue(), 50);
        final String s = (String)this.renderLook.getValue();
        switch (s) {
            case "Block": {
                this.lookInt = 0;
                break;
            }
            case "Side": {
                this.lookInt = 1;
                break;
            }
        }
        if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
            final BlockPos blockPos = rayTraceResult.getBlockPos();
            final AxisAlignedBB axisAlignedBB = BlockHighlight.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)BlockHighlight.mc.world, blockPos);
            if (BlockHighlight.mc.world.getBlockState(blockPos).getMaterial() != Material.AIR) {
                final String s2 = (String)this.renderType.getValue();
                switch (s2) {
                    case "Outline": {
                        this.renderOutline(axisAlignedBB, (int)this.lineWidth.getValue(), this.renderColor.getValue(), enumFacing, this.lookInt);
                        break;
                    }
                    case "Fill": {
                        this.renderFill(axisAlignedBB, colorWithOpacity, enumFacing, this.lookInt);
                        break;
                    }
                    case "Both": {
                        this.renderOutline(axisAlignedBB, (int)this.lineWidth.getValue(), this.renderColor.getValue(), enumFacing, this.lookInt);
                        this.renderFill(axisAlignedBB, colorWithOpacity, enumFacing, this.lookInt);
                        break;
                    }
                }
            }
        }
    }
    
    public void renderOutline(final AxisAlignedBB axisAlignedBB, final int width, final GSColor color, final EnumFacing enumFacing, final int lookInt) {
        if (lookInt == 0) {
            RenderUtil.drawBoundingBox(axisAlignedBB, (double)width, color);
        }
        else if (lookInt == 1) {
            RenderUtil.drawBoundingBoxWithSides(axisAlignedBB, width, color, this.findRenderingSide(enumFacing));
        }
    }
    
    public void renderFill(final AxisAlignedBB axisAlignedBB, final GSColor color, final EnumFacing enumFacing, final int lookInt) {
        int facing = 0;
        if (lookInt == 0) {
            facing = 63;
        }
        else if (lookInt == 1) {
            facing = this.findRenderingSide(enumFacing);
        }
        RenderUtil.drawBox(axisAlignedBB, true, 1.0, color, facing);
    }
    
    private int findRenderingSide(final EnumFacing enumFacing) {
        int facing = 0;
        if (enumFacing == EnumFacing.EAST) {
            facing = 32;
        }
        else if (enumFacing == EnumFacing.WEST) {
            facing = 16;
        }
        else if (enumFacing == EnumFacing.NORTH) {
            facing = 4;
        }
        else if (enumFacing == EnumFacing.SOUTH) {
            facing = 8;
        }
        else if (enumFacing == EnumFacing.UP) {
            facing = 2;
        }
        else if (enumFacing == EnumFacing.DOWN) {
            facing = 1;
        }
        return facing;
    }
}
