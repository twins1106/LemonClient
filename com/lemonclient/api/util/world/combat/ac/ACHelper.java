//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world.combat.ac;

import net.minecraft.client.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import net.minecraftforge.event.entity.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.api.util.world.combat.ac.threads.*;
import com.lemonclient.api.util.world.*;
import java.util.stream.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.lemonclient.client.*;
import net.minecraft.world.*;
import java.util.concurrent.*;

public enum ACHelper implements Listenable
{
    INSTANCE;
    
    private static final Minecraft mc;
    private static final List<CrystalInfo.PlaceInfo> EMPTY_LIST;
    private static final EntityEnderCrystal GENERIC_CRYSTAL;
    public static final ThreadPoolExecutor executor;
    private static final ExecutorService mainExecutors;
    private Future<List<CrystalInfo.PlaceInfo>> mainThreadOutput;
    private final ConcurrentHashMap<BlockPos, EntityEnderCrystal> placedCrystals;
    private ACSettings settings;
    private List<BlockPos> possiblePlacements;
    private List<EntityEnderCrystal> targetableCrystals;
    private final List<PlayerInfo> targetsInfo;
    private List<BlockPos> threadPlacements;
    @EventHandler
    private final Listener<EntityJoinWorldEvent> entitySpawnListener;
    
    private ACHelper() {
        this.placedCrystals = new ConcurrentHashMap<BlockPos, EntityEnderCrystal>();
        this.settings = null;
        this.possiblePlacements = new ArrayList<BlockPos>();
        this.targetableCrystals = new ArrayList<EntityEnderCrystal>();
        this.targetsInfo = new ArrayList<PlayerInfo>();
        this.threadPlacements = new ArrayList<BlockPos>();
        this.entitySpawnListener = (Listener<EntityJoinWorldEvent>)new Listener(event -> {
            final Entity entity = event.getEntity();
            if (entity instanceof EntityEnderCrystal && this.settings != null && this.settings.breakMode.equalsIgnoreCase("Own")) {
                final EntityEnderCrystal crystal = (EntityEnderCrystal)entity;
                final BlockPos crystalPos = EntityUtil.getPosition((Entity)crystal);
                synchronized (this.placedCrystals) {
                    this.placedCrystals.computeIfPresent(crystalPos, (i, j) -> crystal);
                }
            }
        }, new Predicate[0]);
    }
    
    public void startCalculations(final long timeout) {
        if (this.mainThreadOutput != null) {
            this.mainThreadOutput.cancel(true);
        }
        this.mainThreadOutput = ACHelper.mainExecutors.submit((Callable<List<CrystalInfo.PlaceInfo>>)new ACCalculate(this.settings, this.targetsInfo, this.threadPlacements, timeout));
    }
    
    public List<CrystalInfo.PlaceInfo> getOutput(final boolean wait) {
        if (this.mainThreadOutput == null) {
            return ACHelper.EMPTY_LIST;
        }
        if (wait) {
            while (!this.mainThreadOutput.isDone() && !this.mainThreadOutput.isCancelled()) {}
        }
        else {
            if (!this.mainThreadOutput.isDone()) {
                return null;
            }
            if (this.mainThreadOutput.isCancelled()) {
                return ACHelper.EMPTY_LIST;
            }
        }
        List<CrystalInfo.PlaceInfo> output = ACHelper.EMPTY_LIST;
        try {
            output = this.mainThreadOutput.get();
        }
        catch (InterruptedException ex) {}
        catch (ExecutionException ex2) {}
        this.mainThreadOutput = null;
        return output;
    }
    
    public void recalculateValues(final ACSettings settings, final PlayerInfo self, final float armourPercent, final double enemyDistance) {
        this.settings = settings;
        final double entityRangeSq = enemyDistance * enemyDistance;
        final List<EntityPlayer> targets = (List<EntityPlayer>)ACHelper.mc.world.playerEntities.stream().filter(entity -> self.entity.getDistanceSq(entity) <= entityRangeSq).filter(entity -> !EntityUtil.basicChecksEntity(entity)).filter(entity -> entity.getHealth() > 0.0f).filter(entity -> entity.getName().length() > 0).collect(Collectors.toList());
        this.targetableCrystals = (List<EntityEnderCrystal>)ACHelper.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).map(entity -> entity).collect(Collectors.toList());
        final boolean own = settings.breakMode.equalsIgnoreCase("Own");
        if (own) {
            synchronized (this.placedCrystals) {
                this.targetableCrystals.removeIf(crystal -> !this.placedCrystals.containsKey(EntityUtil.getPosition(crystal)));
                this.placedCrystals.values().removeIf(crystal -> crystal.isDead);
            }
        }
        final float damage;
        this.targetableCrystals.removeIf(crystal -> {
            damage = DamageUtil.calculateDamageThreaded(crystal.posX, crystal.posY, crystal.posZ, self, false);
            if (damage > settings.maxSelfDamage) {
                return true;
            }
            else {
                return (settings.antiSuicide && damage > self.health) || self.entity.getDistanceSq((Entity)crystal) >= settings.breakRangeSq;
            }
        });
        final float damage2;
        (this.possiblePlacements = CrystalUtil.findCrystalBlocks(settings.placeRange, settings.endCrystalMode)).removeIf(crystal -> {
            damage2 = DamageUtil.calculateDamageThreaded(crystal.getX() + 0.5, crystal.getY() + 1.0, crystal.getZ() + 0.5, settings.player, false);
            if (damage2 > settings.maxSelfDamage) {
                return true;
            }
            else {
                return settings.antiSuicide && damage2 > settings.player.health;
            }
        });
        this.threadPlacements = CrystalUtil.findCrystalBlocksExcludingCrystals(settings.placeRange, settings.endCrystalMode);
        this.targetsInfo.clear();
        for (final EntityPlayer target : targets) {
            this.targetsInfo.add(new PlayerInfo(target, armourPercent));
        }
    }
    
    public void onPlaceCrystal(final BlockPos target) {
        if (this.settings.breakMode.equalsIgnoreCase("Own")) {
            final BlockPos up = target.up();
            synchronized (this.placedCrystals) {
                this.placedCrystals.put(up, ACHelper.GENERIC_CRYSTAL);
            }
        }
    }
    
    public void onEnable() {
        LemonClient.EVENT_BUS.subscribe((Listenable)this);
    }
    
    public void onDisable() {
        LemonClient.EVENT_BUS.unsubscribe((Listenable)this);
        synchronized (this.placedCrystals) {
            this.placedCrystals.clear();
        }
        if (this.mainThreadOutput != null) {
            this.mainThreadOutput.cancel(true);
        }
    }
    
    public ACSettings getSettings() {
        return this.settings;
    }
    
    public List<BlockPos> getPossiblePlacements() {
        return this.possiblePlacements;
    }
    
    public List<EntityEnderCrystal> getTargetableCrystals() {
        return this.targetableCrystals;
    }
    
    static {
        mc = Minecraft.getMinecraft();
        EMPTY_LIST = new ArrayList<CrystalInfo.PlaceInfo>();
        GENERIC_CRYSTAL = new EntityEnderCrystal((World)null, 398.0, 398.0, 398.0);
        executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
        mainExecutors = Executors.newSingleThreadExecutor();
    }
}
