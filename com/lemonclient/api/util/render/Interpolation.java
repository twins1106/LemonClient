//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.render;

import net.minecraft.util.math.*;
import com.lemonclient.api.util.player.*;

public class Interpolation
{
    public static AxisAlignedBB interpolateAxis(final AxisAlignedBB bb) {
        return new AxisAlignedBB(bb.minX - BurrowUtil.mc.getRenderManager().viewerPosX, bb.minY - BurrowUtil.mc.getRenderManager().viewerPosY, bb.minZ - BurrowUtil.mc.getRenderManager().viewerPosZ, bb.maxX - BurrowUtil.mc.getRenderManager().viewerPosX, bb.maxY - BurrowUtil.mc.getRenderManager().viewerPosY, bb.maxZ - BurrowUtil.mc.getRenderManager().viewerPosZ);
    }
}
