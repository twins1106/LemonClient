//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.qwq;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.client.module.modules.combat.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "AutoCityyyy", category = Category.qwq)
public class qwq1 extends Module
{
    ModeSetting target;
    ModeSetting breakBlock;
    IntegerSetting range;
    BooleanSetting swing;
    BooleanSetting rotate;
    BooleanSetting onlyObby;
    BooleanSetting onlyinhole;
    BooleanSetting only;
    private BlockPos blockMine;
    private BlockPos savepos;
    private EntityPlayer aimTarget;
    private boolean isMining;
    float pitch;
    float yaw;
    @EventHandler
    private final Listener<PacketEvent.Send> listener;
    
    public qwq1() {
        this.target = this.registerMode("Target", (List)Arrays.asList("Nearest", "Looking"), "Nearest");
        this.breakBlock = this.registerMode("Break Block", (List)Arrays.asList("Normal", "Packet"), "Packet");
        this.range = this.registerInteger("Range", 6, 0, 10);
        this.swing = this.registerBoolean("Swing", false);
        this.rotate = this.registerBoolean("Rotate", true);
        this.onlyObby = this.registerBoolean("Only Obby", false);
        this.onlyinhole = this.registerBoolean("Only InHole", true);
        this.only = this.registerBoolean("Only 1x1", true);
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (!(boolean)this.rotate.getValue()) {
                return;
            }
            if (event.getPacket() instanceof CPacketPlayer) {
                final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
                packet.yaw = this.yaw;
                packet.pitch = this.pitch;
            }
        }, new Predicate[0]);
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
        if (qwq1.mc.world == null || qwq1.mc.player == null || qwq1.mc.player.isDead) {
            this.resetValues();
            return;
        }
        if (this.savepos != null && qwq1.mc.player.getDistanceSq(this.savepos) > (int)this.range.getValue() * (int)this.range.getValue()) {
            this.savepos = null;
        }
        if (this.isMining && this.blockMine != null) {
            if (qwq1.mc.world.isAirBlock(this.blockMine) || qwq1.mc.player.getDistanceSq(this.blockMine) > (int)this.range.getValue() * (int)this.range.getValue()) {
                this.resetValues();
            }
            return;
        }
        if (((String)this.target.getValue()).equals("Nearest")) {
            this.aimTarget = PlayerUtil.findClosestTarget((double)(int)this.range.getValue(), this.aimTarget);
        }
        else if (((String)this.target.getValue()).equals("Looking")) {
            this.aimTarget = PlayerUtil.findLookingPlayer((double)(int)this.range.getValue());
        }
        if (Objects.isNull(this.aimTarget)) {
            return;
        }
        final BlockPos[] offsets = { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        final List<CityPos> cityPositions = new ArrayList<CityPos>();
        final BlockPos playerPos = AntiBurrow.getEntityPos((Entity)this.aimTarget);
        if (this.onlyinhole.getValue()) {
            final HoleUtil.HoleInfo holeInfo = HoleUtil.isHole(playerPos, false, false, false);
            final HoleUtil.HoleType holeType = holeInfo.getType();
            if (holeType == HoleUtil.HoleType.NONE) {
                return;
            }
            if ((boolean)this.only.getValue() && holeType != HoleUtil.HoleType.SINGLE) {
                return;
            }
        }
        for (final BlockPos offset : offsets) {
            final BlockPos pos = playerPos.add((Vec3i)offset);
            if (qwq1.mc.player.getDistanceSq(pos) <= (int)this.range.getValue() * (int)this.range.getValue()) {
                if (!BlockUtil.getBlock(pos).equals(Blocks.BEDROCK)) {
                    if (!(boolean)this.onlyObby.getValue() || BlockUtil.getBlock(pos).equals(Blocks.OBSIDIAN)) {
                        int level = 0;
                        final BlockPos a = pos.add((Vec3i)offset);
                        if (BlockUtil.getBlock(a).equals(Blocks.AIR)) {
                            level = 2;
                        }
                        else if (BlockUtil.getBlock(a.add((Vec3i)offset)).equals(Blocks.AIR)) {
                            level = 1;
                        }
                        cityPositions.add(new CityPos(pos, level));
                    }
                }
            }
        }
        final CityPos finalpos = cityPositions.stream().max(Comparator.comparing(e -> e.level)).orElse(null);
        if (finalpos == null) {
            return;
        }
        this.blockMine = finalpos.pos;
        if (this.blockMine == this.savepos || this.blockMine == null) {
            return;
        }
        if (this.swing.getValue()) {
            qwq1.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        if (((String)this.breakBlock.getValue()).equalsIgnoreCase("Packet")) {
            qwq1.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.blockMine, EnumFacing.UP));
            qwq1.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.blockMine, EnumFacing.UP));
        }
        else {
            qwq1.mc.playerController.onPlayerDamageBlock(this.blockMine, EnumFacing.UP);
        }
        this.isMining = true;
        this.savepos = this.blockMine;
    }
    
    public static class CityPos
    {
        private final BlockPos pos;
        private final int level;
        
        public CityPos(final BlockPos pos, final int level) {
            this.pos = pos;
            this.level = level;
        }
        
        public BlockPos getPos() {
            return this.pos;
        }
        
        public int getLevel() {
            return this.level;
        }
    }
}
