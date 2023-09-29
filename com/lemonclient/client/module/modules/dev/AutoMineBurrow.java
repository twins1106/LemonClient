//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.client.module.modules.exploits.*;
import com.lemonclient.client.module.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.entity.player.*;
import com.lemonclient.client.module.modules.combat.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "AutoMineBurrow", category = Category.Dev)
public class AutoMineBurrow extends Module
{
    BooleanSetting instantMine;
    BooleanSetting forcebreak;
    ModeSetting breakBlock;
    BooleanSetting swing;
    BooleanSetting rotate;
    IntegerSetting range;
    private BlockPos pos;
    private BlockPos savepos;
    public double yaw;
    public double pitch;
    @EventHandler
    private final Listener<PacketEvent.Send> listener;
    
    public AutoMineBurrow() {
        this.instantMine = this.registerBoolean("Packet Mine", true);
        this.forcebreak = this.registerBoolean("Force Break", true, () -> (Boolean)this.instantMine.getValue());
        this.breakBlock = this.registerMode("Break Block", (List)Arrays.asList("Normal", "Packet"), "Packet");
        this.swing = this.registerBoolean("Swing", true);
        this.rotate = this.registerBoolean("Rotate", false);
        this.range = this.registerInteger("Range", 5, 0, 10);
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (!(boolean)this.rotate.getValue()) {
                return;
            }
            if (event.getPacket() instanceof CPacketPlayer) {
                final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
                packet.yaw = (float)this.yaw;
                packet.pitch = (float)this.pitch;
            }
        }, new Predicate[0]);
    }
    
    public void onEnable() {
        this.pos = null;
    }
    
    public void onUpdate() {
        if (AutoMineBurrow.mc.world == null || AutoMineBurrow.mc.player == null || AutoMineBurrow.mc.player.isDead) {
            this.savepos = null;
            return;
        }
        if ((boolean)this.instantMine.getValue() && !ModuleManager.isModuleEnabled((Class)InstantMine.class)) {
            this.disable();
            return;
        }
        if (this.savepos != null && AutoMineBurrow.mc.player.getDistanceSq(this.savepos) > (int)this.range.getValue() * (int)this.range.getValue()) {
            this.savepos = null;
        }
        if (this.pos != null && (AutoMineBurrow.mc.world.isAirBlock(this.pos) || AutoMineBurrow.mc.player.getDistanceSq(this.pos) > (int)this.range.getValue() * (int)this.range.getValue())) {
            this.pos = null;
        }
        if (Objects.isNull(this.pos)) {
            this.pos = this.getCityPos();
            if (this.pos == null) {
                return;
            }
            this.pos = new BlockPos((double)this.pos.x, this.pos.getY() + 0.8, (double)this.pos.z);
            final double[] rotate = EntityUtil.calculateLookAt(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5, (Entity)AutoMineBurrow.mc.player);
            this.yaw = rotate[0];
            this.pitch = rotate[1];
        }
        if (AutoMineBurrow.mc.world.isAirBlock(this.pos)) {
            this.pos = null;
            return;
        }
        if (AutoMineBurrow.mc.world.getBlockState(this.pos).getBlock() == Blocks.BEDROCK) {
            this.pos = null;
            return;
        }
        this.dobreak();
        if ((boolean)this.instantMine.getValue() && (boolean)this.forcebreak.getValue()) {
            InstantMine.INSTANCE.breaker(this.pos);
        }
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
    
    public BlockPos getCityPos() {
        final EntityPlayer player = PlayerUtil.findClosestTarget((double)(int)this.range.getValue(), (EntityPlayer)null);
        if (Objects.isNull(player)) {
            return null;
        }
        final BlockPos[] offsets = { new BlockPos(0, 0, 0) };
        final List<CityPos> cityPositions = new ArrayList<CityPos>();
        final BlockPos playerPos = AntiBurrow.getEntityPos((Entity)player);
        for (final BlockPos offset : offsets) {
            final BlockPos pos = playerPos.add((Vec3i)offset);
            if (AutoMineBurrow.mc.player.getDistanceSq(pos) <= (int)this.range.getValue() * (int)this.range.getValue()) {
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
        final CityPos finallyPos = cityPositions.stream().max(Comparator.comparing(e -> e.level)).orElse(null);
        if (Objects.isNull(finallyPos)) {
            return null;
        }
        return finallyPos.pos;
    }
    
    private void dobreak() {
        if (!this.isPos2(this.savepos, this.pos)) {
            if (this.instantMine.getValue()) {
                if (this.isPos2(InstantMine.INSTANCE.lastBlock, this.pos)) {
                    return;
                }
                InstantMine.INSTANCE.breaker(this.pos);
            }
            else {
                if (this.swing.getValue()) {
                    AutoMineBurrow.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                if (((String)this.breakBlock.getValue()).equals("Packet")) {
                    AutoMineBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.pos, EnumFacing.UP));
                    AutoMineBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.pos, EnumFacing.UP));
                }
                else {
                    AutoMineBurrow.mc.playerController.onPlayerDamageBlock(this.pos, EnumFacing.UP);
                }
            }
            this.savepos = this.pos;
        }
    }
    
    public class CityPos
    {
        private BlockPos pos;
        private int level;
        
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
