//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.qwq;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;

@Module.Declaration(name = "AntiRegearrrr", category = Category.qwq)
public class qwq3 extends Module
{
    DoubleSetting reach;
    BooleanSetting packet;
    BooleanSetting swing;
    private final List<BlockPos> selfPlaced;
    BlockPos savepos;
    @EventHandler
    private final Listener<PacketEvent.Send> listener;
    
    public qwq3() {
        this.reach = this.registerDouble("Range", 4.0, 0.0, 10.0);
        this.packet = this.registerBoolean("Packet Break", false);
        this.swing = this.registerBoolean("Swing", true);
        this.selfPlaced = new ArrayList<BlockPos>();
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (qwq3.mc.world == null || qwq3.mc.player == null || qwq3.mc.player.isDead) {
                return;
            }
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                final CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
                if (qwq3.mc.player.getHeldItem(packet.getHand()).getItem() instanceof ItemShulkerBox) {
                    this.selfPlaced.add(packet.getPos().offset(packet.getDirection()));
                }
            }
        }, new Predicate[0]);
    }
    
    public void fast() {
        if (qwq3.mc.world == null || qwq3.mc.player == null || qwq3.mc.player.isDead) {
            this.savepos = null;
            return;
        }
        if (this.savepos != null) {
            if (qwq3.mc.world.getBlockState(this.savepos) instanceof BlockShulkerBox && qwq3.mc.player.getDistanceSq(this.savepos) <= (double)this.reach.getValue() * (double)this.reach.getValue()) {
                return;
            }
            this.savepos = null;
        }
        final List<BlockPos> sphere = (List<BlockPos>)EntityUtil.getSphere(PlayerUtil.getEyesPos(), (Double)this.reach.getValue(), (Double)this.reach.getValue(), false, false, 0);
        for (final BlockPos pos2 : sphere) {
            if (!(qwq3.mc.world.getBlockState(pos2).getBlock() instanceof BlockShulkerBox)) {
                continue;
            }
            if (this.selfPlaced.contains(pos2)) {
                continue;
            }
            if (this.isPos2(this.savepos, pos2)) {
                continue;
            }
            if (this.swing.getValue()) {
                qwq3.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            if (this.packet.getValue()) {
                qwq3.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos2, EnumFacing.UP));
                qwq3.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos2, EnumFacing.UP));
            }
            else {
                qwq3.mc.playerController.onPlayerDamageBlock(pos2, EnumFacing.UP);
            }
            this.savepos = pos2;
        }
        this.selfPlaced.removeIf(pos -> qwq3.mc.world.isAirBlock(pos));
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
}
