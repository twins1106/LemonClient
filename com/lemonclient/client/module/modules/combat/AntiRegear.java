//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.client.module.modules.exploits.*;
import com.lemonclient.client.module.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;

@Module.Declaration(name = "AntiRegear", category = Category.Combat)
public class AntiRegear extends Module
{
    BooleanSetting instantMine;
    BooleanSetting forcebreak;
    BooleanSetting packet;
    DoubleSetting reach;
    BooleanSetting swing;
    private final List<BlockPos> selfPlaced;
    BlockPos savepos;
    @EventHandler
    private final Listener<PacketEvent.Send> listener;
    
    public AntiRegear() {
        this.instantMine = this.registerBoolean("Packet Mine", true);
        this.forcebreak = this.registerBoolean("Force Break", true, () -> (Boolean)this.instantMine.getValue());
        this.packet = this.registerBoolean("Packet Break", false, () -> !(boolean)this.instantMine.getValue());
        this.reach = this.registerDouble("Range", 4.0, 0.0, 10.0);
        this.swing = this.registerBoolean("Swing", true);
        this.selfPlaced = new ArrayList<BlockPos>();
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (AntiRegear.mc.world == null || AntiRegear.mc.player == null || AntiRegear.mc.player.isDead) {
                return;
            }
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                final CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
                if (AntiRegear.mc.player.getHeldItem(packet.getHand()).getItem() instanceof ItemShulkerBox) {
                    this.selfPlaced.add(packet.getPos().offset(packet.getDirection()));
                }
            }
        }, new Predicate[0]);
    }
    
    public void fast() {
        if (AntiRegear.mc.world == null || AntiRegear.mc.player == null || AntiRegear.mc.player.isDead) {
            this.savepos = null;
            return;
        }
        if ((boolean)this.instantMine.getValue() && !ModuleManager.isModuleEnabled((Class)InstantMine.class)) {
            this.disable();
            return;
        }
        if (this.savepos != null) {
            if (AntiRegear.mc.world.getBlockState(this.savepos) instanceof BlockShulkerBox && AntiRegear.mc.player.getDistanceSq(this.savepos) <= (double)this.reach.getValue() * (double)this.reach.getValue()) {
                return;
            }
            this.savepos = null;
        }
        final List<BlockPos> sphere = (List<BlockPos>)EntityUtil.getSphere(PlayerUtil.getEyesPos(), (Double)this.reach.getValue(), (Double)this.reach.getValue(), false, false, 0);
        for (final BlockPos pos2 : sphere) {
            if (!(AntiRegear.mc.world.getBlockState(pos2).getBlock() instanceof BlockShulkerBox)) {
                continue;
            }
            if (this.selfPlaced.contains(pos2)) {
                continue;
            }
            if ((boolean)this.instantMine.getValue() && (boolean)this.forcebreak.getValue() && !this.isPos2(InstantMine.INSTANCE.lastBlock, pos2)) {
                InstantMine.INSTANCE.breaker(pos2);
                return;
            }
            if (this.isPos2(this.savepos, pos2)) {
                continue;
            }
            if (this.instantMine.getValue()) {
                if (this.isPos2(InstantMine.INSTANCE.lastBlock, pos2)) {
                    return;
                }
                InstantMine.INSTANCE.breaker(pos2);
            }
            else {
                if (this.swing.getValue()) {
                    AntiRegear.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                if (this.packet.getValue()) {
                    AntiRegear.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos2, EnumFacing.UP));
                    AntiRegear.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos2, EnumFacing.UP));
                }
                else {
                    AntiRegear.mc.playerController.onPlayerDamageBlock(pos2, EnumFacing.UP);
                }
            }
            this.savepos = pos2;
        }
        this.selfPlaced.removeIf(pos -> AntiRegear.mc.world.isAirBlock(pos));
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
}
