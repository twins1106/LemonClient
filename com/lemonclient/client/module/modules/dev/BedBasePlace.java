//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import com.lemonclient.api.util.world.*;
import java.util.stream.*;
import com.lemonclient.api.util.player.*;
import java.util.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import java.util.function.*;

@Module.Declaration(name = "BedBase", category = Category.Dev)
public class BedBasePlace extends Module
{
    ModeSetting targetMode;
    BooleanSetting base;
    BooleanSetting basepacket;
    BooleanSetting baseswing;
    DoubleSetting toggleDmg;
    IntegerSetting basedelay;
    DoubleSetting baseminDmg;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    IntegerSetting maxY;
    IntegerSetting calcdelay;
    DoubleSetting Range;
    DoubleSetting yRange;
    IntegerSetting enemyRange;
    DoubleSetting minDmg;
    DoubleSetting maxSelfDmg;
    BooleanSetting ignore;
    BooleanSetting suicide;
    IntegerSetting facePlaceValue;
    IntegerSetting faceplacedamage;
    BooleanSetting forceplace;
    boolean canbaseplace;
    BlockPos pos;
    BlockPos basepos;
    boolean shouldbaseplace;
    int basewaited;
    int calcwaited;
    BlockPos[] sides;
    
    public BedBasePlace() {
        this.targetMode = this.registerMode("Target", (List)Arrays.asList("Nearest", "Damage", "Health"), "Nearest");
        this.base = this.registerBoolean("Place Base", true);
        this.basepacket = this.registerBoolean("Packet Base Place", true);
        this.baseswing = this.registerBoolean("Base Swing", true);
        this.toggleDmg = this.registerDouble("Toggle Damage", 8.0, 0.0, 36.0);
        this.basedelay = this.registerInteger("Base Delay", 0, 0, 20);
        this.baseminDmg = this.registerDouble("Base MinDmg", 8.0, 0.0, 36.0);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.maxY = this.registerInteger("Max Y", 1, 0, 3);
        this.calcdelay = this.registerInteger("Calc Delay", 0, 0, 20);
        this.Range = this.registerDouble("Place Range", 5.0, 0.0, 10.0);
        this.yRange = this.registerDouble("Y Range", 2.5, 0.0, 10.0);
        this.enemyRange = this.registerInteger("Enemy Range", 10, 0, 16);
        this.minDmg = this.registerDouble("Min Damage", 8.0, 0.0, 36.0);
        this.maxSelfDmg = this.registerDouble("Max Self Dmg", 10.0, 1.0, 36.0);
        this.ignore = this.registerBoolean("Ignore Self Dmg", false);
        this.suicide = this.registerBoolean("Anti Suicide", true);
        this.facePlaceValue = this.registerInteger("FacePlace HP", 8, 0, 36);
        this.faceplacedamage = this.registerInteger("FP Min Damage", 1, 0, 36);
        this.forceplace = this.registerBoolean("Force Place", false);
        this.sides = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) };
    }
    
    public void onUpdate() {
        if (BedBasePlace.mc.player == null || BedBasePlace.mc.world == null || BedBasePlace.mc.player.isDead) {
            return;
        }
        if (!this.inNether()) {
            return;
        }
        if ((boolean)this.base.getValue() && this.basewaited++ >= (int)this.basedelay.getValue()) {
            this.canbaseplace = true;
            this.basewaited = 0;
        }
        this.calc();
    }
    
    private boolean inNether() {
        return BedBasePlace.mc.player.dimension != 0;
    }
    
    private void calc() {
        if (this.calcwaited++ >= (int)this.calcdelay.getValue()) {
            final EntityPlayer renderEntity = this.getTarget();
            if (renderEntity == null) {
                return;
            }
            BlockPos bedpos;
            if (((String)this.targetMode.getValue()).equals("Damage")) {
                bedpos = this.pos;
            }
            else {
                bedpos = this.calculateBestPlacement(renderEntity);
            }
            if (bedpos == null) {
                return;
            }
            if ((boolean)this.base.getValue() && this.shouldbaseplace) {
                this.canbaseplace = false;
                final int obsiSlot = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
                final int oldSlot = BedBasePlace.mc.player.inventory.currentItem;
                this.switchTo(obsiSlot);
                BurrowUtil.placeBlock(this.basepos, EnumHand.MAIN_HAND, false, (boolean)this.basepacket.getValue(), false, (boolean)this.baseswing.getValue());
                this.switchTo(oldSlot);
            }
        }
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || BedBasePlace.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                BedBasePlace.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                BedBasePlace.mc.player.inventory.currentItem = slot;
            }
            BedBasePlace.mc.playerController.updateController();
        }
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : BedBasePlace.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    private List<EntityPlayer> getTargetList(final double range) {
        return (List<EntityPlayer>)BedBasePlace.mc.world.playerEntities.stream().filter(p -> BedBasePlace.mc.player.getDistance(p) <= range).filter(p -> BedBasePlace.mc.player.entityId != p.entityId).filter(p -> !EntityUtil.basicChecksEntity(p)).collect(Collectors.toList());
    }
    
    public static EntityPlayer getNearestPlayer(final List<EntityPlayer> list) {
        return list.stream().min(Comparator.comparing(p -> BedBasePlace.mc.player.getDistance(p))).orElse(null);
    }
    
    private EntityPlayer getTarget() {
        final String s = (String)this.targetMode.getValue();
        switch (s) {
            case "Nearest": {
                return PlayerUtil.getNearestPlayer((double)(int)this.enemyRange.getValue());
            }
            case "Damage": {
                final List<EntityPlayer> targetList = new ArrayList<EntityPlayer>();
                BlockPos bestPos = null;
                BlockPos bestBase = null;
                boolean needBase = false;
                EntityPlayer bestTarget = null;
                float highestDamage = 0.0f;
                for (final EntityPlayer player : this.getTargetList((int)this.enemyRange.getValue())) {
                    final double targetHp = player.getHealth() + player.getAbsorptionAmount();
                    if (!player.isDead) {
                        if (targetHp <= 0.0) {
                            continue;
                        }
                        final double Hp = BedBasePlace.mc.player.getHealth() + BedBasePlace.mc.player.getAbsorptionAmount();
                        BlockPos best = null;
                        float bestDamage = 0.0f;
                        BlockPos posBase = null;
                        boolean shouldBase = false;
                        for (final BlockPos crystal : this.findBlocksExcluding()) {
                            boolean can = this.canbaseplace;
                            if (bestDamage >= (double)this.toggleDmg.getValue()) {
                                can = false;
                            }
                            final boolean canplace = this.canPlaceBed(crystal);
                            if (!canplace) {
                                if (!(boolean)this.base.getValue() || !can) {
                                    continue;
                                }
                                if (crystal.getY() >= (int)player.posY + (int)this.maxY.getValue()) {
                                    continue;
                                }
                            }
                            else {
                                can = false;
                            }
                            final float currentDamage = DamageUtil.calcDamage(crystal.getX() + 0.5, crystal.getY() + 1.5625, crystal.getZ() + 0.5, (Entity)player);
                            if (currentDamage <= bestDamage) {
                                continue;
                            }
                            if (targetHp > (int)this.facePlaceValue.getValue()) {
                                if (currentDamage < (double)this.minDmg.getValue()) {
                                    continue;
                                }
                            }
                            else if (currentDamage < (int)this.faceplacedamage.getValue()) {
                                continue;
                            }
                            if (currentDamage < (double)this.baseminDmg.getValue()) {
                                can = false;
                            }
                            final float SelfDamage = DamageUtil.calcDamage(crystal.getX() + 0.5, crystal.getY() + 1.5625, crystal.getZ() + 0.5, (Entity)BedBasePlace.mc.player);
                            if (!BedBasePlace.mc.player.isCreative()) {
                                if (SelfDamage > (double)this.maxSelfDmg.getValue()) {
                                    if (currentDamage >= targetHp) {
                                        if (!(boolean)this.forceplace.getValue()) {
                                            continue;
                                        }
                                    }
                                    else if (!(boolean)this.ignore.getValue()) {
                                        continue;
                                    }
                                }
                                if (!can && (boolean)this.suicide.getValue() && SelfDamage >= Hp) {
                                    continue;
                                }
                            }
                            if (can) {
                                if (BurrowUtil.findHotbarBlock((Class)BlockObsidian.class) == -1) {
                                    continue;
                                }
                                posBase = this.getBestBasePos(crystal);
                                if (posBase == null) {
                                    continue;
                                }
                                shouldBase = true;
                                bestDamage = currentDamage;
                                best = crystal;
                            }
                            else {
                                posBase = null;
                                shouldBase = false;
                            }
                            if (!canplace) {
                                continue;
                            }
                            bestDamage = currentDamage;
                            best = crystal;
                        }
                        if ((int)bestDamage == (int)highestDamage) {
                            targetList.add(player);
                        }
                        else {
                            if (bestDamage <= highestDamage) {
                                continue;
                            }
                            highestDamage = bestDamage;
                            bestTarget = player;
                            bestPos = best;
                            targetList.clear();
                            bestBase = posBase;
                            needBase = shouldBase;
                        }
                    }
                }
                if (targetList.isEmpty()) {
                    this.pos = bestPos;
                    this.basepos = bestBase;
                    this.shouldbaseplace = needBase;
                }
                else {
                    bestTarget = getNearestPlayer(targetList);
                    this.pos = this.calculateBestPlacement(bestTarget);
                }
                return bestTarget;
            }
            case "Health": {
                final List<EntityPlayer> targetList = new ArrayList<EntityPlayer>();
                double health = 37.0;
                EntityPlayer bestTarget2 = null;
                for (final EntityPlayer player2 : this.getTargetList((int)this.enemyRange.getValue())) {
                    if (bestTarget2 == null) {
                        bestTarget2 = player2;
                        health = player2.getHealth() + player2.getAbsorptionAmount();
                    }
                    else if ((int)health == (int)(player2.getHealth() + player2.getAbsorptionAmount())) {
                        targetList.add(player2);
                    }
                    else {
                        if (health <= player2.getHealth() + player2.getAbsorptionAmount()) {
                            continue;
                        }
                        targetList.clear();
                        bestTarget2 = player2;
                        health = player2.getHealth() + player2.getAbsorptionAmount();
                    }
                }
                if (targetList.isEmpty()) {
                    return bestTarget2;
                }
                return getNearestPlayer(targetList);
            }
            default: {
                return null;
            }
        }
    }
    
    private boolean block(final BlockPos pos) {
        final boolean canplace = BedBasePlace.mc.world.getBlockState(pos).isSideSolid((IBlockAccess)BedBasePlace.mc.world, pos, EnumFacing.UP) && BedBasePlace.mc.world.getBlockState(pos).getBlock() != Blocks.BED;
        final boolean bed = BedBasePlace.mc.world.getBlockState(pos.up()).getBlock() == Blocks.BED;
        if (bed) {
            BedBasePlace.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos.up(), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        }
        return canplace || bed;
    }
    
    private boolean canPlaceBed(final BlockPos blockPos) {
        return this.block(blockPos) && BedBasePlace.mc.world.isAirBlock(blockPos.up()) && ((this.inRange(blockPos.east().up()) && this.block(blockPos.east()) && BedBasePlace.mc.world.isAirBlock(blockPos.east().up())) || (this.inRange(blockPos.north().up()) && this.block(blockPos.north()) && BedBasePlace.mc.world.isAirBlock(blockPos.north().up())) || (this.inRange(blockPos.west().up()) && this.block(blockPos.west()) && BedBasePlace.mc.world.isAirBlock(blockPos.west().up())) || (this.inRange(blockPos.south().up()) && this.block(blockPos.south()) && BedBasePlace.mc.world.isAirBlock(blockPos.south().up())));
    }
    
    private boolean canPlaceBedWithoutBase(final BlockPos blockPos) {
        return this.block(blockPos) && BedBasePlace.mc.world.isAirBlock(blockPos.up()) && ((this.inRange(blockPos.east().up()) && BedBasePlace.mc.world.isAirBlock(blockPos.east().up())) || (this.inRange(blockPos.north().up()) && BedBasePlace.mc.world.isAirBlock(blockPos.north().up())) || (this.inRange(blockPos.west().up()) && BedBasePlace.mc.world.isAirBlock(blockPos.west().up())) || (this.inRange(blockPos.south().up()) && BedBasePlace.mc.world.isAirBlock(blockPos.south().up())));
    }
    
    private boolean canPlace(final BlockPos basePos, final BlockPos headPos) {
        BlockPos pos;
        if (BedBasePlace.mc.player.getHorizontalFacing().equals((Object)EnumFacing.SOUTH)) {
            pos = new BlockPos(headPos.x, headPos.y, headPos.z - 1);
        }
        else if (BedBasePlace.mc.player.getHorizontalFacing().equals((Object)EnumFacing.WEST)) {
            pos = new BlockPos(headPos.x + 1, headPos.y, headPos.z);
        }
        else if (BedBasePlace.mc.player.getHorizontalFacing().equals((Object)EnumFacing.NORTH)) {
            pos = new BlockPos(headPos.x, headPos.y, headPos.z + 1);
        }
        else {
            pos = new BlockPos(headPos.x - 1, headPos.y, headPos.z);
        }
        return this.inRange(basePos.up()) && this.isPos2(basePos, pos) && BedBasePlace.mc.world.isAirBlock(pos.up());
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
    
    private BlockPos getBestBasePos(final BlockPos pos) {
        BlockPos bestPos = null;
        double bestRange = 1000.0;
        for (final BlockPos side : this.sides) {
            final BlockPos base = pos.add((Vec3i)side);
            if (this.inRange(base)) {
                if (BedBasePlace.mc.world.isAirBlock(base.add(0, 1, 0)) && this.inRange(base.add(0, 1, 0)) && !this.intersectsWithEntity(base)) {
                    if (this.canPlace(base, pos)) {
                        return base;
                    }
                    if (bestPos == null) {
                        bestRange = BedBasePlace.mc.player.getDistanceSq(base);
                        bestPos = base;
                    }
                    else if (bestRange > BedBasePlace.mc.player.getDistanceSq(base)) {
                        bestRange = BedBasePlace.mc.player.getDistanceSq(base);
                        bestPos = base;
                    }
                }
            }
        }
        return bestPos;
    }
    
    private boolean inRange(final BlockPos pos) {
        final double y = pos.y - PlayerUtil.getEyesPos().y;
        return BedBasePlace.mc.player.getDistanceSq(pos) <= (double)this.Range.getValue() * (double)this.Range.getValue() && y * y <= (double)this.yRange.getValue() * (double)this.yRange.getValue();
    }
    
    private List<BlockPos> findBlocksExcluding() {
        return (List<BlockPos>)EntityUtil.getSphere(PlayerUtil.getEyesPos(), Double.valueOf((double)this.Range.getValue() + 1.0), (Double)this.yRange.getValue(), false, true, 0).stream().filter(this::canPlaceBedWithoutBase).collect(Collectors.toList());
    }
    
    public BlockPos calculateBestPlacement(final EntityPlayer player) {
        final double targetHp = player.getHealth() + player.getAbsorptionAmount();
        if (player.isDead || targetHp <= 0.0) {
            return null;
        }
        final double Hp = BedBasePlace.mc.player.getHealth() + BedBasePlace.mc.player.getAbsorptionAmount();
        BlockPos best = null;
        float bestDamage = 0.0f;
        for (final BlockPos crystal : this.findBlocksExcluding()) {
            boolean can = this.canbaseplace;
            if (bestDamage >= (double)this.toggleDmg.getValue()) {
                can = false;
            }
            final boolean canplace = this.canPlaceBed(crystal);
            if (!canplace) {
                if (!(boolean)this.base.getValue() || !can) {
                    continue;
                }
                if (crystal.getY() >= (int)player.posY + (int)this.maxY.getValue()) {
                    continue;
                }
            }
            else {
                can = false;
            }
            final float currentDamage = DamageUtil.calcDamage(crystal.getX() + 0.5, crystal.getY() + 1.5625, crystal.getZ() + 0.5, (Entity)player);
            if (currentDamage <= bestDamage) {
                continue;
            }
            if (targetHp > (int)this.facePlaceValue.getValue()) {
                if (currentDamage < (double)this.minDmg.getValue()) {
                    continue;
                }
            }
            else if (currentDamage < (int)this.faceplacedamage.getValue()) {
                continue;
            }
            if (currentDamage < (double)this.baseminDmg.getValue()) {
                can = false;
            }
            final float SelfDamage = DamageUtil.calcDamage(crystal.getX() + 0.5, crystal.getY() + 1.5625, crystal.getZ() + 0.5, (Entity)BedBasePlace.mc.player);
            if (!BedBasePlace.mc.player.isCreative()) {
                if (SelfDamage > (double)this.maxSelfDmg.getValue()) {
                    if (currentDamage >= targetHp) {
                        if (!(boolean)this.forceplace.getValue()) {
                            continue;
                        }
                    }
                    else if (!(boolean)this.ignore.getValue()) {
                        continue;
                    }
                }
                if (!can && (boolean)this.suicide.getValue() && SelfDamage >= Hp) {
                    continue;
                }
            }
            if (can) {
                if (BurrowUtil.findHotbarBlock((Class)BlockObsidian.class) == -1) {
                    continue;
                }
                this.basepos = this.getBestBasePos(crystal);
                if (this.basepos == null) {
                    continue;
                }
                this.shouldbaseplace = true;
                bestDamage = currentDamage;
                best = crystal;
            }
            else {
                this.basepos = null;
                this.shouldbaseplace = false;
            }
            if (!canplace) {
                continue;
            }
            bestDamage = currentDamage;
            best = crystal;
        }
        return best;
    }
    
    public void onEnable() {
        final int n = 0;
        this.basewaited = n;
        this.calcwaited = n;
    }
}
