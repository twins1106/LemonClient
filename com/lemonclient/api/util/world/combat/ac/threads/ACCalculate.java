//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world.combat.ac.threads;

import net.minecraft.util.math.*;
import com.lemonclient.api.util.world.combat.ac.*;
import javax.annotation.*;
import java.util.concurrent.*;
import java.util.*;
import net.minecraft.entity.*;

public class ACCalculate implements Callable<List<CrystalInfo.PlaceInfo>>
{
    private final ACSettings settings;
    private final List<PlayerInfo> targets;
    private final List<BlockPos> blocks;
    private final long globalTimeoutTime;
    
    public ACCalculate(final ACSettings settings, final List<PlayerInfo> targets, final List<BlockPos> blocks, final long globalTimeoutTime) {
        this.settings = settings;
        this.targets = targets;
        this.blocks = blocks;
        this.globalTimeoutTime = globalTimeoutTime;
    }
    
    @Override
    public List<CrystalInfo.PlaceInfo> call() {
        return this.getPlayers(this.startThreads());
    }
    
    @Nonnull
    private List<Future<CrystalInfo.PlaceInfo>> startThreads() {
        final List<Future<CrystalInfo.PlaceInfo>> output = new ArrayList<Future<CrystalInfo.PlaceInfo>>();
        for (final PlayerInfo target : this.targets) {
            output.add(ACHelper.executor.submit((Callable<CrystalInfo.PlaceInfo>)new ACSubThread(this.settings, this.blocks, target)));
        }
        return output;
    }
    
    private List<CrystalInfo.PlaceInfo> getPlayers(final List<Future<CrystalInfo.PlaceInfo>> input) {
        final List<CrystalInfo.PlaceInfo> place = new ArrayList<CrystalInfo.PlaceInfo>();
        for (final Future<CrystalInfo.PlaceInfo> future : input) {
            while (!future.isDone() && !future.isCancelled() && System.currentTimeMillis() <= this.globalTimeoutTime) {}
            if (future.isDone()) {
                CrystalInfo.PlaceInfo crystal = null;
                try {
                    crystal = future.get();
                }
                catch (InterruptedException ex) {}
                catch (ExecutionException ex2) {}
                if (crystal == null) {
                    continue;
                }
                place.add(crystal);
            }
            else {
                future.cancel(true);
            }
        }
        if (this.settings.crystalPriority.equalsIgnoreCase("Health")) {
            place.sort(Comparator.comparingDouble(i -> -i.target.health));
        }
        else if (this.settings.crystalPriority.equalsIgnoreCase("Closest")) {
            place.sort(Comparator.comparingDouble(i -> -this.settings.player.entity.getDistanceSq((Entity)i.target.entity)));
        }
        else {
            place.sort(Comparator.comparingDouble(i -> i.damage));
        }
        return place;
    }
}
