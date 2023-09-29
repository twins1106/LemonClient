//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import net.minecraft.util.math.*;
import com.lemonclient.client.module.modules.exploits.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

@Module.Declaration(name = "AntiBurrow", category = Category.Combat)
public class AntiBurrow extends Module
{
    BooleanSetting instantMine;
    BooleanSetting forcebreak;
    BooleanSetting packet;
    IntegerSetting range;
    BooleanSetting swing;
    BlockPos savepos;
    BlockPos Ppos;
    boolean mined;
    
    public AntiBurrow() {
        this.instantMine = this.registerBoolean("Packet Mine", true);
        this.forcebreak = this.registerBoolean("Force Break", true, () -> (Boolean)this.instantMine.getValue());
        this.packet = this.registerBoolean("Packet Mine", false, () -> !(boolean)this.instantMine.getValue());
        this.range = this.registerInteger("Range", 5, 0, 10);
        this.swing = this.registerBoolean("Swing", true);
    }
    
    public void onUpdate() {
        if (AntiBurrow.mc.world == null || AntiBurrow.mc.player == null || AntiBurrow.mc.player.isDead) {
            this.savepos = null;
            return;
        }
        if ((boolean)this.instantMine.getValue() && !ModuleManager.isModuleEnabled((Class)InstantMine.class)) {
            this.disable();
            return;
        }
        if (this.savepos != null && AntiBurrow.mc.player.getDistanceSq(this.savepos) > (int)this.range.getValue() * (int)this.range.getValue()) {
            this.savepos = null;
        }
        if (this.Ppos != null && (AntiBurrow.mc.world.isAirBlock(this.Ppos) || AntiBurrow.mc.player.getDistanceSq(this.Ppos) > (int)this.range.getValue() * (int)this.range.getValue())) {
            this.Ppos = null;
        }
        if (this.Ppos == null) {
            return;
        }
        final EntityPlayer player = PlayerUtil.findClosestTarget((double)(int)this.range.getValue(), (EntityPlayer)null);
        if (player == null) {
            return;
        }
        this.Ppos = getEntityPos((Entity)player);
        this.Ppos = new BlockPos((double)this.Ppos.x, this.Ppos.getY() + 0.8, (double)this.Ppos.z);
        if (!AntiBurrow.mc.world.isAirBlock(this.Ppos)) {
            if (AntiBurrow.mc.world.getBlockState(this.Ppos).getBlock() == Blocks.BEDROCK) {
                return;
            }
            if (this.mined && (boolean)this.instantMine.getValue() && (boolean)this.forcebreak.getValue()) {
                InstantMine.INSTANCE.lastBlock = this.Ppos;
            }
            if (this.isPos2(this.Ppos, this.savepos)) {
                return;
            }
            if (this.instantMine.getValue()) {
                if (this.isPos2(InstantMine.INSTANCE.lastBlock, this.Ppos)) {
                    return;
                }
                InstantMine.INSTANCE.breaker(this.Ppos);
                this.mined = true;
            }
            else {
                if (this.swing.getValue()) {
                    AntiBurrow.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                if (this.packet.getValue()) {
                    AntiBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.Ppos, EnumFacing.UP));
                    AntiBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.Ppos, EnumFacing.UP));
                }
                else {
                    AntiBurrow.mc.playerController.onPlayerDamageBlock(this.Ppos, EnumFacing.UP);
                }
            }
            this.savepos = this.Ppos;
        }
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
    
    public static BlockPos getEntityPos(final Entity e) {
        return new BlockPos(Math.floor(e.posX), Math.floor(e.posY), Math.floor(e.posZ));
    }
}
