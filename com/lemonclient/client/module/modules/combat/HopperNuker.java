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
import net.minecraft.init.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

@Module.Declaration(name = "HopperNuker", category = Category.Combat)
public class HopperNuker extends Module
{
    BooleanSetting instantMine;
    BooleanSetting forcebreak;
    BooleanSetting packet;
    DoubleSetting range;
    BooleanSetting swing;
    private final List<BlockPos> selfPlaced;
    BlockPos savepos;
    @EventHandler
    private final Listener<PacketEvent.Send> listener;
    
    public HopperNuker() {
        this.instantMine = this.registerBoolean("Packet Mine", true);
        this.forcebreak = this.registerBoolean("Force Break", true, () -> (Boolean)this.instantMine.getValue());
        this.packet = this.registerBoolean("Packet Break", false, () -> !(boolean)this.instantMine.getValue());
        this.range = this.registerDouble("Range", 6.0, 0.0, 10.0);
        this.swing = this.registerBoolean("Swing", true);
        this.selfPlaced = new ArrayList<BlockPos>();
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (HopperNuker.mc.world == null || HopperNuker.mc.player == null || HopperNuker.mc.player.isDead) {
                return;
            }
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                final CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
                final int slot = HopperNuker.mc.player.inventory.currentItem;
                if (slot > 8 || slot < 0) {
                    return;
                }
                final ItemStack stack = HopperNuker.mc.player.inventory.getStackInSlot(HopperNuker.mc.player.inventory.currentItem);
                if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block == Blocks.HOPPER) {
                        this.selfPlaced.add(packet.getPos().offset(packet.getDirection()));
                    }
                }
            }
        }, new Predicate[0]);
    }
    
    public void fast() {
        if (HopperNuker.mc.world == null || HopperNuker.mc.player == null || HopperNuker.mc.player.isDead) {
            this.savepos = null;
            return;
        }
        if ((boolean)this.instantMine.getValue() && !ModuleManager.isModuleEnabled((Class)InstantMine.class)) {
            this.disable();
            return;
        }
        if (this.savepos != null) {
            if (HopperNuker.mc.world.getBlockState(this.savepos).getBlock() == Blocks.HOPPER && HopperNuker.mc.player.getDistanceSq(this.savepos) <= (double)this.range.getValue() * (double)this.range.getValue()) {
                return;
            }
            this.savepos = null;
        }
        final List<BlockPos> sphere = (List<BlockPos>)EntityUtil.getSphere(PlayerUtil.getEyesPos(), (Double)this.range.getValue(), (Double)this.range.getValue(), false, false, 0);
        for (final BlockPos pos2 : sphere) {
            if (HopperNuker.mc.world.getBlockState(pos2).getBlock() != Blocks.HOPPER) {
                continue;
            }
            if (!(HopperNuker.mc.world.getBlockState(pos2.up()).getBlock() instanceof BlockShulkerBox)) {
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
                    HopperNuker.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                if (this.packet.getValue()) {
                    HopperNuker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos2, EnumFacing.UP));
                    HopperNuker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos2, EnumFacing.UP));
                }
                else {
                    HopperNuker.mc.playerController.onPlayerDamageBlock(pos2, EnumFacing.UP);
                }
            }
            this.savepos = pos2;
        }
        this.selfPlaced.removeIf(pos -> HopperNuker.mc.world.isAirBlock(pos));
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
}
