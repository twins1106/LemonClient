//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import com.lemonclient.api.util.misc.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.item.*;
import com.lemonclient.client.module.modules.combat.*;
import net.minecraft.network.play.client.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.api.util.render.*;

@Module.Declaration(name = "LemonAura", category = Category.Dev)
public class LemonAura extends Module
{
    IntegerSetting targetRange;
    BooleanSetting place;
    IntegerSetting placeDelay;
    IntegerSetting placeRange;
    BooleanSetting autoSwitch;
    BooleanSetting silentSwitch;
    BooleanSetting opPlace;
    BooleanSetting explode;
    IntegerSetting explodeDelay;
    IntegerSetting breakRange;
    IntegerSetting wallRangeBreak;
    BooleanSetting packetBreak;
    BooleanSetting swingArm;
    BooleanSetting ignoreSelfDmg;
    IntegerSetting maxSelf;
    IntegerSetting minDmg;
    BooleanSetting smartMode;
    IntegerSetting dmgError;
    BooleanSetting antiSuicide;
    IntegerSetting pauseHealth;
    DoubleSetting slabHeight;
    IntegerSetting width;
    ColorSetting color;
    IntegerSetting ufoAlpha;
    IntegerSetting Alpha;
    public EntityPlayer target;
    BlockPos placePos;
    int Pdelay;
    int Bdelay;
    private EnumHand oldhand;
    
    public LemonAura() {
        this.targetRange = this.registerInteger("Target Range", 10, 0, 16);
        this.place = this.registerBoolean("Place", true);
        this.placeDelay = this.registerInteger("PlaceDelay", 6, 0, 20);
        this.placeRange = this.registerInteger("PlaceRange", 6, 0, 10);
        this.autoSwitch = this.registerBoolean("Switch", true);
        this.silentSwitch = this.registerBoolean("SilentSwitch", false);
        this.opPlace = this.registerBoolean("1.13", false);
        this.explode = this.registerBoolean("Explode", true);
        this.explodeDelay = this.registerInteger("ExplodeDelay", 6, 0, 20);
        this.breakRange = this.registerInteger("ExplodeRange", 6, 0, 10);
        this.wallRangeBreak = this.registerInteger("WallRange", 3, 0, 10);
        this.packetBreak = this.registerBoolean("PacketBreak", true);
        this.swingArm = this.registerBoolean("Swing Arm", true);
        this.ignoreSelfDmg = this.registerBoolean("IgnoreSelfDamage", false);
        this.maxSelf = this.registerInteger("MaxSelfDamage", 8, 0, 36);
        this.minDmg = this.registerInteger("MinDamage", 3, 0, 36);
        this.smartMode = this.registerBoolean("SmartMode", true);
        this.dmgError = this.registerInteger("DamageError", 3, 1, 15);
        this.antiSuicide = this.registerBoolean("AntiSuicide", true);
        this.pauseHealth = this.registerInteger("RequireHealth", 3, 0, 36);
        this.slabHeight = this.registerDouble("Slab Height", 0.5, 0.0, 1.0);
        this.width = this.registerInteger("Width", 1, 1, 10);
        this.color = this.registerColor("Base Color", new GSColor(255, 0, 0, 50));
        this.ufoAlpha = this.registerInteger("Alpha", 120, 0, 255);
        this.Alpha = this.registerInteger("Outline Alpha", 255, 0, 255);
        this.oldhand = null;
    }
    
    public void onEnable() {
        this.placePos = null;
    }
    
    public void onUpdate() {
        if (LemonAura.mc.world == null || LemonAura.mc.player == null || LemonAura.mc.player.isDead) {
            return;
        }
        if ((int)this.pauseHealth.getValue() > LemonAura.mc.player.getHealth()) {
            return;
        }
        this.doCrystalAura();
    }
    
    public void doCrystalAura() {
        this.target = PlayerUtil.findClosestTarget((double)(int)this.targetRange.getValue(), this.target);
        if (this.target == null) {
            return;
        }
        if (this.place.getValue()) {
            this.doPlace();
        }
        if (this.explode.getValue()) {
            this.doBreak();
        }
    }
    
    public void doPlace() {
        if ((int)this.placeDelay.getValue() == 0 || this.Pdelay++ >= (int)this.placeDelay.getValue()) {
            this.Pdelay = 0;
            this.placePos = null;
            EnumHand hand;
            if (this.autoSwitch.getValue()) {
                if (LemonAura.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                    hand = EnumHand.OFF_HAND;
                }
                else {
                    final int crystalSlot = AutoCev.getItemHotbar(Items.END_CRYSTAL);
                    if (crystalSlot == -1) {
                        return;
                    }
                    this.setItem(crystalSlot);
                    hand = EnumHand.MAIN_HAND;
                }
            }
            else if (LemonAura.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
                hand = EnumHand.MAIN_HAND;
            }
            else {
                if (LemonAura.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
                    return;
                }
                hand = EnumHand.OFF_HAND;
            }
            final BlockPos bestPos = this.calculateBestPlacement(this.target);
            if (bestPos == null) {
                return;
            }
            this.placePos = bestPos;
            LemonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(bestPos, EnumFacing.UP, hand, 0.5f, 0.5f, 0.5f));
            this.restoreItem();
        }
    }
    
    public BlockPos calculateBestPlacement(final EntityPlayer player) {
        BlockPos best = null;
        float bestDamage = 0.0f;
        final List<BlockPos> possiblePlacePositions = (List<BlockPos>)CrystalUtil.possiblePlacePositions((float)(int)this.placeRange.getValue(), true, (boolean)this.opPlace.getValue());
        for (final BlockPos crystal : possiblePlacePositions) {
            final float currentDamage = DamageUtil.calculateDamage(crystal.getX() + 0.5, crystal.getY() + 1.0, crystal.getZ() + 0.5, (Entity)player);
            final float SelfDamage = DamageUtil.calculateDamage(crystal.getX() + 0.5, crystal.getY() + 1.0, crystal.getZ() + 0.5, (Entity)LemonAura.mc.player);
            if ((SelfDamage >= currentDamage || SelfDamage >= LemonAura.mc.player.getHealth() + LemonAura.mc.player.getAbsorptionAmount() || SelfDamage > (int)this.maxSelf.getValue()) && !(boolean)this.ignoreSelfDmg.getValue() && this.isValidCrystal(crystal.x, crystal.y, crystal.z)) {
                continue;
            }
            if (currentDamage == bestDamage) {
                if (best != null) {
                    continue;
                }
                bestDamage = currentDamage;
                best = crystal;
            }
            else {
                if (currentDamage <= bestDamage) {
                    continue;
                }
                bestDamage = currentDamage;
                best = crystal;
            }
        }
        return best;
    }
    
    public void doBreak() {
        if ((int)this.explodeDelay.getValue() == 0 || this.Bdelay++ >= (int)this.explodeDelay.getValue()) {
            this.Bdelay = 0;
            final List<Entity> crystalList = new ArrayList<Entity>();
            for (final Entity crystal : LemonAura.mc.player.world.loadedEntityList) {
                if (crystal == null) {
                    return;
                }
                if (!(crystal instanceof EntityEnderCrystal)) {
                    continue;
                }
                crystalList.add(crystal);
            }
            if (crystalList.isEmpty()) {
                return;
            }
            Entity bestCrystal = null;
            final Iterator<Entity> iterator2 = crystalList.iterator();
            while (iterator2.hasNext()) {
                final Entity crystal2 = bestCrystal = iterator2.next();
            }
            if (bestCrystal == null || bestCrystal.isDead) {
                return;
            }
            final BlockPos pos = AntiBurrow.getEntityPos(bestCrystal);
            if (PlayerUtil.getDistance(pos) > (int)this.breakRange.getValue()) {
                return;
            }
            if (!CrystalUtil.canSeePos(pos) && PlayerUtil.getDistance(pos) > (int)this.wallRangeBreak.getValue()) {
                return;
            }
            try {
                if (this.packetBreak.getValue()) {
                    this.breakCrystalPacket(bestCrystal);
                }
                else {
                    this.breakCrystal(bestCrystal);
                }
            }
            catch (NullPointerException ex) {}
        }
    }
    
    private void breakCrystal(final Entity crystal) {
        LemonAura.mc.playerController.attackEntity((EntityPlayer)LemonAura.mc.player, crystal);
        if (this.swingArm.getValue()) {
            LemonAura.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    private void breakCrystalPacket(final Entity crystal) {
        LemonAura.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
        if (this.swingArm.getValue()) {
            LemonAura.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    public boolean isValidCrystal(final double posX, final double posY, final double posZ) {
        final BlockPos pos = new BlockPos(posX, posY, posZ);
        if (PlayerUtil.getDistance(pos) > (int)this.breakRange.getValue()) {
            return false;
        }
        if (!CrystalUtil.canSeePos(pos) && PlayerUtil.getDistance(pos) > (int)this.wallRangeBreak.getValue()) {
            return false;
        }
        final double selfDamage = CrystalUtil.calculateDamage(posX, posY, posZ, (Entity)LemonAura.mc.player);
        if (selfDamage > (int)this.maxSelf.getValue() && !(boolean)this.ignoreSelfDmg.getValue()) {
            return false;
        }
        if (LemonAura.mc.player.getHealth() + LemonAura.mc.player.getAbsorptionAmount() - selfDamage <= 0.0 && ((boolean)this.antiSuicide.getValue() || (boolean)this.ignoreSelfDmg.getValue())) {
            return false;
        }
        final double enemyDamage = CrystalUtil.calculateDamage(posX, posY, posZ, (Entity)this.target);
        return enemyDamage >= (int)this.minDmg.getValue() && (selfDamage <= enemyDamage || Math.abs(selfDamage - enemyDamage) < (int)this.dmgError.getValue() || !(boolean)this.smartMode.getValue());
    }
    
    public void setItem(final int slot) {
        if (this.silentSwitch.getValue()) {
            this.oldhand = null;
            if (LemonAura.mc.player.isHandActive()) {
                this.oldhand = LemonAura.mc.player.getActiveHand();
            }
            LemonAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            LemonAura.mc.player.inventory.currentItem = slot;
            LemonAura.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.silentSwitch.getValue()) {
            if (this.oldhand != null) {
                LemonAura.mc.player.setActiveHand(this.oldhand);
            }
            LemonAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(LemonAura.mc.player.inventory.currentItem));
            this.oldhand = null;
        }
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (this.placePos != null) {
            RenderUtil.drawBox(this.placePos, (double)this.slabHeight.getValue(), new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 63);
            RenderUtil.drawBoundingBox(this.placePos, (double)this.slabHeight.getValue(), (float)(int)this.width.getValue(), new GSColor(this.color.getValue(), (int)this.Alpha.getValue()));
        }
    }
}
