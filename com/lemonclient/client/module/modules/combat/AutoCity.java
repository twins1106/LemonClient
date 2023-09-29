//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.lemonclient.client.module.modules.exploits.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.api.util.render.*;

@Module.Declaration(name = "AutoCity", category = Category.Combat)
public class AutoCity extends Module
{
    IntegerSetting range;
    DoubleSetting minDamage;
    DoubleSetting maxDamage;
    ModeSetting target;
    BooleanSetting onlyObby;
    BooleanSetting instantMine;
    BooleanSetting forcebreak;
    ModeSetting mineMode;
    BooleanSetting swing;
    ModeSetting renderMode;
    IntegerSetting width;
    ColorSetting color;
    BooleanSetting onlyinhole;
    BooleanSetting only;
    BooleanSetting newPlace;
    BooleanSetting disableAfter;
    private BlockPos blockMine;
    private BlockPos savepos;
    private EntityPlayer aimTarget;
    private boolean isMining;
    
    public AutoCity() {
        this.range = this.registerInteger("Range", 6, 0, 8);
        this.minDamage = this.registerDouble("Min Damage", 5.0, 0.0, 10.0);
        this.maxDamage = this.registerDouble("Max Self Damage", 7.0, 0.0, 20.0);
        this.target = this.registerMode("Target", (List)Arrays.asList("Nearest", "Looking"), "Nearest");
        this.onlyObby = this.registerBoolean("Only Obby", false);
        this.instantMine = this.registerBoolean("Packet Mine", true);
        this.forcebreak = this.registerBoolean("Force Break", true, () -> (Boolean)this.instantMine.getValue());
        this.mineMode = this.registerMode("Mine Mode", (List)Arrays.asList("Packet", "Vanilla"), "Packet", () -> !(boolean)this.instantMine.getValue());
        this.swing = this.registerBoolean("Swing", true, () -> !(boolean)this.instantMine.getValue());
        this.renderMode = this.registerMode("Render", (List)Arrays.asList("Outline", "Fill", "Both", "None"), "Both");
        this.width = this.registerInteger("Width", 1, 1, 10);
        this.color = this.registerColor("Color", new GSColor(102, 51, 153));
        this.onlyinhole = this.registerBoolean("Only InHole", true);
        this.only = this.registerBoolean("Only 1x1", true);
        this.newPlace = this.registerBoolean("New Place", false);
        this.disableAfter = this.registerBoolean("Disable After", true);
    }
    
    public void onEnable() {
        this.resetValues();
    }
    
    void resetValues() {
        this.aimTarget = null;
        this.blockMine = null;
        this.isMining = false;
    }
    
    public void onUpdate() {
        if (AutoCity.mc.world == null || AutoCity.mc.player == null || AutoCity.mc.player.isDead) {
            this.savepos = null;
            return;
        }
        if ((boolean)this.instantMine.getValue() && !ModuleManager.isModuleEnabled((Class)InstantMine.class)) {
            this.disable();
            return;
        }
        if (this.savepos != null && AutoCity.mc.player.getDistanceSq(this.savepos) > (int)this.range.getValue() * (int)this.range.getValue()) {
            this.savepos = null;
        }
        if (this.isMining && this.blockMine != null) {
            if (AutoCity.mc.world.isAirBlock(this.blockMine) || AutoCity.mc.player.getDistanceSq(this.blockMine) > (int)this.range.getValue() * (int)this.range.getValue()) {
                this.resetValues();
                if (this.disableAfter.getValue()) {
                    this.disable();
                }
            }
            else if ((boolean)this.instantMine.getValue() && (boolean)this.forcebreak.getValue()) {
                InstantMine.INSTANCE.lastBlock = this.blockMine;
            }
            return;
        }
        if (((String)this.target.getValue()).equals("Nearest")) {
            this.aimTarget = PlayerUtil.findClosestTarget((double)(int)this.range.getValue(), this.aimTarget);
        }
        else if (((String)this.target.getValue()).equals("Looking")) {
            this.aimTarget = PlayerUtil.findLookingPlayer((double)(int)this.range.getValue());
        }
        if (this.aimTarget == null) {
            return;
        }
        if (this.onlyinhole.getValue()) {
            final HoleUtil.HoleInfo holeInfo = HoleUtil.isHole(AntiBurrow.getEntityPos((Entity)this.aimTarget), false, false, false);
            final HoleUtil.HoleType holeType = holeInfo.getType();
            if (holeType == HoleUtil.HoleType.NONE) {
                return;
            }
            if ((boolean)this.only.getValue() && holeType != HoleUtil.HoleType.SINGLE) {
                return;
            }
        }
        boolean found = false;
        for (final int[] positions : new int[][] { { 1, 0, 0 }, { -1, 0, 0 }, { 0, 0, 1 }, { 0, 0, -1 } }) {
            final BlockPos blockPos = new BlockPos(this.aimTarget.posX + positions[0], this.aimTarget.posY + positions[1] + ((this.aimTarget.posY % 1.0 > 0.2) ? 0.5 : 0.0), this.aimTarget.posZ + positions[2]);
            final Block toCheck = BlockUtil.getBlock(blockPos);
            Label_0948: {
                if (!(toCheck instanceof BlockAir)) {
                    if (toCheck != Blocks.BEDROCK) {
                        if (this.onlyObby.getValue()) {
                            if (!(toCheck instanceof BlockObsidian)) {
                                break Label_0948;
                            }
                        }
                        else if (toCheck.blockResistance > 6001.0f) {
                            break Label_0948;
                        }
                        AutoCity.mc.world.setBlockToAir(blockPos);
                        for (final Vec3i placement : new Vec3i[] { new Vec3i(1, -1, 0), new Vec3i(-1, -1, 0), new Vec3i(0, -1, 1), new Vec3i(0, -1, -1) }) {
                            final BlockPos temp;
                            if (CrystalUtil.canPlaceCrystal(temp = blockPos.add(placement), (boolean)this.newPlace.getValue())) {
                                if (DamageUtil.calculateDamage(temp.getX() + 0.5, temp.getY() + 1.0, temp.getZ() + 0.5, (Entity)AutoCity.mc.player) < (double)this.maxDamage.getValue()) {
                                    final float damagePlayer = DamageUtil.calculateDamage(temp.getX() + 0.5, temp.getY() + 1.0, temp.getZ() + 0.5, (Entity)this.aimTarget);
                                    if (damagePlayer >= (double)this.minDamage.getValue()) {
                                        found = true;
                                        this.blockMine = blockPos;
                                        break;
                                    }
                                }
                            }
                        }
                        AutoCity.mc.world.setBlockState(blockPos, toCheck.getDefaultState());
                        if (found) {
                            break;
                        }
                    }
                }
            }
        }
        if (!found) {
            if (this.disableAfter.getValue()) {
                this.disable();
            }
            return;
        }
        if (InstantMine.INSTANCE.lastBlock == this.blockMine) {
            return;
        }
        if (this.blockMine == this.savepos || this.blockMine == null) {
            return;
        }
        if (this.instantMine.getValue()) {
            if (InstantMine.INSTANCE.lastBlock == this.blockMine) {
                return;
            }
            InstantMine.INSTANCE.breaker(this.blockMine);
        }
        else {
            if (this.swing.getValue()) {
                AutoCity.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            if (((String)this.mineMode.getValue()).equalsIgnoreCase("Packet")) {
                AutoCity.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.blockMine, EnumFacing.UP));
                AutoCity.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.blockMine, EnumFacing.UP));
            }
            else {
                AutoCity.mc.playerController.onPlayerDamageBlock(this.blockMine, EnumFacing.UP);
            }
        }
        this.isMining = true;
        this.savepos = this.blockMine;
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (this.blockMine == null) {
            return;
        }
        this.renderBox(this.blockMine);
    }
    
    private void renderBox(final BlockPos blockPos) {
        final GSColor gsColor1 = new GSColor(this.color.getValue(), 255);
        final GSColor gsColor2 = new GSColor(this.color.getValue(), 50);
        final String s = (String)this.renderMode.getValue();
        switch (s) {
            case "Both": {
                RenderUtil.drawBox(blockPos, 1.0, gsColor2, 63);
                RenderUtil.drawBoundingBox(blockPos, 1.0, (float)(int)this.width.getValue(), gsColor1);
                break;
            }
            case "Outline": {
                RenderUtil.drawBoundingBox(blockPos, 1.0, (float)(int)this.width.getValue(), gsColor1);
                break;
            }
            case "Fill": {
                RenderUtil.drawBox(blockPos, 1.0, gsColor2, 63);
                break;
            }
        }
    }
}
