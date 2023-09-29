//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world.combat.ac.threads;

import java.util.concurrent.*;
import java.util.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.world.combat.ac.*;

public class ACSubThread implements Callable<CrystalInfo.PlaceInfo>
{
    private final ACSettings settings;
    private final List<BlockPos> possibleLocations;
    private final PlayerInfo target;
    
    public ACSubThread(final ACSettings setting, final List<BlockPos> possibleLocations, final PlayerInfo target) {
        this.settings = setting;
        this.possibleLocations = possibleLocations;
        this.target = target;
    }
    
    @Override
    public CrystalInfo.PlaceInfo call() {
        return this.getPlacement();
    }
    
    private CrystalInfo.PlaceInfo getPlacement() {
        if (this.possibleLocations == null) {
            return null;
        }
        return ACUtil.calculateBestPlacement(this.settings, this.target, (List)this.possibleLocations);
    }
}
