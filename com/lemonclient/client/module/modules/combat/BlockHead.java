//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import com.lemonclient.api.util.player.*;

@Module.Declaration(name = "BlockHead", category = Category.Combat)
public class BlockHead extends Module
{
    IntegerSetting delay;
    IntegerSetting range;
    IntegerSetting bpt;
    BooleanSetting rotate;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting pause;
    int ob;
    int waited;
    int placed;
    BlockPos[] block;
    BlockPos[] sides;
    
    public BlockHead() {
        this.delay = this.registerInteger("Delay", 0, 0, 20);
        this.range = this.registerInteger("Range", 5, 0, 10);
        this.bpt = this.registerInteger("BlocksPerTick", 4, 0, 20);
        this.rotate = this.registerBoolean("Rotate", false);
        this.packet = this.registerBoolean("Packet Place", false);
        this.swing = this.registerBoolean("Swing", false);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.pause = this.registerBoolean("BedrockHole", true);
        this.block = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(1, 1, 0), new BlockPos(1, 2, 0), new BlockPos(0, 2, 0) };
        this.sides = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) };
    }
    
    public static boolean isPlayerInHole(final EntityPlayer target) {
        final BlockPos blockPos = getLocalPlayerPosFloored(target);
        final HoleUtil.HoleInfo holeInfo = HoleUtil.isHole(blockPos, true, true, false);
        final HoleUtil.HoleType holeType = holeInfo.getType();
        return holeType == HoleUtil.HoleType.SINGLE;
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || BlockHead.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                BlockHead.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                BlockHead.mc.player.inventory.currentItem = slot;
                BlockHead.mc.playerController.updateController();
            }
        }
    }
    
    public static BlockPos getLocalPlayerPosFloored(final EntityPlayer target) {
        return new BlockPos(target.getPositionVector());
    }
    
    public void onUpdate() {
        if (BlockHead.mc.world == null || BlockHead.mc.player == null || BlockHead.mc.player.isDead) {
            return;
        }
        this.placed = 0;
        if (this.waited++ < (int)this.delay.getValue()) {
            return;
        }
        this.waited = 0;
        final EntityPlayer target = PlayerUtil.getNearestPlayer((double)(int)this.range.getValue());
        if (target == null) {
            return;
        }
        if (BlockHead.mc.player.getDistance((Entity)target) <= 5.0f && isPlayerInHole(target)) {
            final int oldslot = BlockHead.mc.player.inventory.currentItem;
            final BlockPos pos = AntiBurrow.getEntityPos((Entity)target);
            this.ob = MelonCevBreaker.findHotbarBlock(BlockObsidian.class);
            if (this.ob == -1) {
                return;
            }
            int bedrock = 0;
            for (final BlockPos side : this.sides) {
                if (BlockHead.mc.world.getBlockState(pos.add((Vec3i)side)).getBlock() == Blocks.BEDROCK) {
                    ++bedrock;
                }
            }
            if (bedrock >= 4 && !(boolean)this.pause.getValue()) {
                return;
            }
            for (final BlockPos add : this.block) {
                if (this.placed > (int)this.bpt.getValue()) {
                    return;
                }
                final BlockPos obsi = pos.add((Vec3i)add);
                if (BlockHead.mc.world.isAirBlock(obsi)) {
                    this.switchTo(this.ob);
                    BurrowUtil.placeBlock(obsi, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
                    this.switchTo(oldslot);
                }
                ++this.placed;
            }
        }
    }
}
