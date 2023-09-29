//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import java.util.function.*;

@Module.Declaration(name = "CevBlocker", category = Category.Combat)
public class CevBlocker extends Module
{
    ModeSetting time;
    BooleanSetting high;
    BooleanSetting pa;
    BooleanSetting bevel;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting rotate;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    private List<BlockPos> cevPositions;
    
    public CevBlocker() {
        this.time = this.registerMode("Time Mode", (List)Arrays.asList("Tick", "onUpdate", "Both", "Fast"), "Tick");
        this.high = this.registerBoolean("High Cev", true);
        this.pa = this.registerBoolean("Ignore Bedrock", true);
        this.bevel = this.registerBoolean("Bevel", true);
        this.packet = this.registerBoolean("Packet Place", true);
        this.swing = this.registerBoolean("Swing", true);
        this.rotate = this.registerBoolean("Rotate", true);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.cevPositions = new ArrayList<BlockPos>();
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || CevBlocker.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                CevBlocker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                CevBlocker.mc.player.inventory.currentItem = slot;
            }
            CevBlocker.mc.playerController.updateController();
        }
    }
    
    public void onUpdate() {
        if (((String)this.time.getValue()).equals("onUpdate") || ((String)this.time.getValue()).equals("Both")) {
            this.doBlock();
        }
    }
    
    public void onTick() {
        if (((String)this.time.getValue()).equals("Tick") || ((String)this.time.getValue()).equals("Both")) {
            this.doBlock();
        }
    }
    
    public void fast() {
        if (((String)this.time.getValue()).equals("Fast")) {
            this.doBlock();
        }
    }
    
    private void doBlock() {
        if (CevBlocker.mc.world == null || CevBlocker.mc.player == null) {
            return;
        }
        final BlockPos[] highpos = { new BlockPos(0, 3, 0), new BlockPos(0, 4, 0), new BlockPos(1, 2, 0), new BlockPos(-1, 2, 0), new BlockPos(0, 2, 1), new BlockPos(0, 2, -1) };
        final BlockPos[] hight2 = { new BlockPos(1, 2, 1), new BlockPos(1, 2, -1), new BlockPos(-1, 2, 1), new BlockPos(-1, 2, -1) };
        final BlockPos[] offsets = { new BlockPos(0, 2, 0), new BlockPos(1, 1, 0), new BlockPos(-1, 1, 0), new BlockPos(0, 1, 1), new BlockPos(0, 1, -1) };
        final BlockPos[] offsets2 = { new BlockPos(1, 1, 1), new BlockPos(1, 1, -1), new BlockPos(-1, 1, 1), new BlockPos(-1, 1, -1) };
        for (final BlockPos offset : offsets) {
            this.check(offset);
        }
        if (this.high.getValue()) {
            for (final BlockPos offset : highpos) {
                this.check(offset);
            }
        }
        if (this.bevel.getValue()) {
            for (final BlockPos offset : offsets2) {
                this.check(offset);
            }
            if (this.high.getValue()) {
                for (final BlockPos offset : hight2) {
                    this.check(offset);
                }
            }
        }
        final Iterator<BlockPos> iterator = this.cevPositions.iterator();
        while (iterator.hasNext()) {
            final BlockPos pos = iterator.next();
            if (!Objects.isNull(this.getCrystal(pos))) {
                continue;
            }
            final int obby = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
            if (obby == -1) {
                return;
            }
            final int currentItem = CevBlocker.mc.player.inventory.currentItem;
            this.switchTo(obby);
            CevBlocker.mc.playerController.updateController();
            if (CevBlocker.mc.world.isAirBlock(pos)) {
                BurrowUtil.placeBlock(pos, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
                BurrowUtil.placeBlock(pos.up(), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
            }
            else {
                BurrowUtil.placeBlock(pos.up(), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
            }
            this.switchTo(currentItem);
            iterator.remove();
        }
    }
    
    public void check(final BlockPos offset) {
        final BlockPos playerPos = PlayerUtil.getPlayerPos();
        final BlockPos offsetPos = playerPos.add((Vec3i)offset);
        final Entity crystal = this.getCrystal(offsetPos);
        if (Objects.isNull(crystal)) {
            return;
        }
        final BlockPos crystalPos = AntiBurrow.getEntityPos(crystal).add(0, -1, 0);
        if ((boolean)this.pa.getValue() && !CevBlocker.mc.world.isAirBlock(crystalPos) && CevBlocker.mc.world.getBlockState(crystalPos).getBlock() != Blocks.OBSIDIAN) {
            return;
        }
        if (!CevBlocker.mc.world.isAirBlock(playerPos.up().up())) {
            CevBlocker.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(CevBlocker.mc.player.posX, playerPos.getY() + 0.2, CevBlocker.mc.player.posZ, false));
        }
        CevBlocker.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
        CevBlocker.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        if (!this.cevPositions.contains(crystalPos)) {
            this.cevPositions.add(crystalPos);
        }
    }
    
    private Entity getCrystal(final BlockPos pos) {
        return (Entity)CevBlocker.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> AntiBurrow.getEntityPos(e).add(0, -1, 0).equals((Object)pos)).min(Comparator.comparing((Function<? super T, ? extends Comparable>)this::getDistance)).orElse(null);
    }
    
    public double getDistance(final Entity e) {
        return CevBlocker.mc.player.getDistance(e);
    }
    
    public void onDisable() {
        this.cevPositions = new ArrayList<BlockPos>();
    }
}
