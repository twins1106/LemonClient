//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import com.lemonclient.api.util.player.*;
import java.util.*;

@Module.Declaration(name = "AutoTrap", category = Category.Combat)
public class AutoTrap extends Module
{
    IntegerSetting delay;
    IntegerSetting range;
    IntegerSetting bpt;
    BooleanSetting top;
    BooleanSetting rotate;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting pause;
    int ob;
    int waited;
    int placed;
    List<BlockPos> posList;
    BlockPos[] sides;
    BlockPos[] blocks;
    BlockPos[] block;
    
    public AutoTrap() {
        this.delay = this.registerInteger("Delay", 0, 0, 20);
        this.range = this.registerInteger("Range", 5, 0, 10);
        this.bpt = this.registerInteger("BlocksPerTick", 4, 0, 20);
        this.top = this.registerBoolean("Top+", false);
        this.rotate = this.registerBoolean("Rotate", false);
        this.packet = this.registerBoolean("Packet Place", false);
        this.swing = this.registerBoolean("Swing", false);
        this.packetSwitch = this.registerBoolean("Packet Switch", false);
        this.check = this.registerBoolean("Switch Check", true);
        this.pause = this.registerBoolean("BedrockHole", true);
        this.posList = new ArrayList<BlockPos>();
        this.sides = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) };
        this.blocks = new BlockPos[] { new BlockPos(0, 1, 0), new BlockPos(0, 2, 0) };
        this.block = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(1, 1, 0), new BlockPos(1, 2, 0) };
    }
    
    public static boolean isPlayerInHole(final EntityPlayer target) {
        final BlockPos blockPos = getLocalPlayerPosFloored(target);
        final HoleUtil.HoleInfo holeInfo = HoleUtil.isHole(blockPos, true, true, false);
        final HoleUtil.HoleType holeType = holeInfo.getType();
        return holeType == HoleUtil.HoleType.SINGLE;
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || AutoTrap.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                AutoTrap.mc.player.inventory.currentItem = slot;
                AutoTrap.mc.playerController.updateController();
            }
        }
    }
    
    public static BlockPos getLocalPlayerPosFloored(final EntityPlayer target) {
        return new BlockPos(target.getPositionVector());
    }
    
    public void onUpdate() {
        if (AutoTrap.mc.world == null || AutoTrap.mc.player == null || AutoTrap.mc.player.isDead) {
            this.posList.clear();
            return;
        }
        this.placed = 0;
        if ((int)this.delay.getValue() > 0) {
            if (this.waited++ < (int)this.delay.getValue()) {
                return;
            }
            this.waited = 0;
        }
        final EntityPlayer target = PlayerUtil.getNearestPlayer((double)(int)this.range.getValue());
        if (target == null) {
            return;
        }
        if (AutoTrap.mc.player.getDistance((Entity)target) > (int)this.range.getValue() || !isPlayerInHole(target) || MotionUtil.isMoving((EntityLivingBase)target)) {
            this.posList.clear();
        }
        this.posList.removeIf(b -> !AutoTrap.mc.world.isAirBlock(b));
        if (this.posList.isEmpty() && AutoTrap.mc.player.getDistance((Entity)target) < (int)this.range.getValue() && isPlayerInHole(target)) {
            final BlockPos pos = AntiBurrow.getEntityPos((Entity)target);
            this.posList.add(pos.add(0, 2, 0));
            if (this.top.getValue()) {
                this.posList.add(pos.add(0, 3, 0));
            }
            this.ob = MelonCevBreaker.findHotbarBlock(BlockObsidian.class);
            if (this.ob == -1) {
                return;
            }
            int obby = 0;
            for (final BlockPos side : this.sides) {
                if (AutoTrap.mc.world.getBlockState(pos.add((Vec3i)side)).getBlock() != Blocks.BEDROCK) {
                    for (final BlockPos blockPos : this.blocks) {
                        this.posList.add(pos.add((Vec3i)side).add((Vec3i)blockPos));
                    }
                    ++obby;
                }
            }
            if (obby == 0) {
                if (!(boolean)this.pause.getValue()) {
                    return;
                }
                for (final BlockPos block : this.block) {
                    this.posList.add(pos.add((Vec3i)block));
                }
            }
        }
        final int oldslot = AutoTrap.mc.player.inventory.currentItem;
        this.posList.removeIf(b -> !AutoTrap.mc.world.isAirBlock(b));
        if (this.posList.isEmpty()) {
            return;
        }
        for (final BlockPos block2 : this.posList) {
            if (!AutoTrap.mc.world.isAirBlock(block2)) {
                continue;
            }
            if (this.placed > (int)this.bpt.getValue()) {
                return;
            }
            this.switchTo(this.ob);
            BurrowUtil.placeBlock(block2, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
            this.switchTo(oldslot);
            ++this.placed;
        }
    }
}
