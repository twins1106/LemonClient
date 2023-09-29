//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "AutoPhase", category = Category.Combat)
public class AutoPhase extends Module
{
    BooleanSetting packet;
    BooleanSetting swing;
    BlockPos originalPos;
    boolean down;
    List<Block> blockList;
    BlockPos[] sides;
    BlockPos[] height;
    
    public AutoPhase() {
        this.packet = this.registerBoolean("Packet Place", true);
        this.swing = this.registerBoolean("Swing", true);
        this.blockList = Arrays.asList(Blocks.BEDROCK, Blocks.OBSIDIAN, Blocks.ENDER_CHEST, Blocks.ANVIL);
        this.sides = new BlockPos[] { new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0) };
        this.height = new BlockPos[] { new BlockPos(0, 0, 0), new BlockPos(0, 1, 0) };
    }
    
    public void onEnable() {
        this.down = false;
    }
    
    public void onDisable() {
        this.down = false;
    }
    
    public void onUpdate() {
        if (!this.down) {
            this.originalPos = PlayerUtil.getPlayerPos();
            if (BurrowUtil.findHotbarBlock((Class)BlockTrapDoor.class) == -1) {
                this.disable();
                return;
            }
            if (!AutoPhase.mc.world.isAirBlock(this.originalPos)) {
                this.disable();
                return;
            }
            BlockPos neighbour;
            EnumFacing opposite;
            if (!AutoPhase.mc.world.isAirBlock(this.originalPos.south())) {
                neighbour = this.originalPos.offset(EnumFacing.SOUTH);
                opposite = EnumFacing.SOUTH.getOpposite();
            }
            else if (!AutoPhase.mc.world.isAirBlock(this.originalPos.north())) {
                neighbour = this.originalPos.offset(EnumFacing.NORTH);
                opposite = EnumFacing.NORTH.getOpposite();
            }
            else if (!AutoPhase.mc.world.isAirBlock(this.originalPos.east())) {
                neighbour = this.originalPos.offset(EnumFacing.EAST);
                opposite = EnumFacing.EAST.getOpposite();
            }
            else {
                if (AutoPhase.mc.world.isAirBlock(this.originalPos.west())) {
                    this.disable();
                    return;
                }
                neighbour = this.originalPos.offset(EnumFacing.WEST);
                opposite = EnumFacing.WEST.getOpposite();
            }
            final double x = AutoPhase.mc.player.posX;
            final double y = AutoPhase.mc.player.posY;
            final double z = AutoPhase.mc.player.posZ;
            AutoPhase.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y + 0.20000000298023224, z, AutoPhase.mc.player.onGround));
            AutoPhase.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(BurrowUtil.findHotbarBlock((Class)BlockTrapDoor.class)));
            rightClickBlock(neighbour, opposite, new Vec3d(0.5, 0.8, 0.5), (boolean)this.packet.getValue(), (boolean)this.swing.getValue());
            AutoPhase.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(AutoPhase.mc.player.inventory.currentItem));
            AutoPhase.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, AutoPhase.mc.player.onGround));
            int bedrocks = 0;
            int blocks = 0;
            double xAdd = 0.0;
            double zAdd = 0.0;
            for (final BlockPos side : this.sides) {
                for (final BlockPos add : this.sides) {
                    if (!this.isPos2(this.originalPos, this.originalPos.add((Vec3i)side).add((Vec3i)add))) {
                        if (!this.isPos2(side, add)) {
                            int bedrock = 0;
                            int block = 0;
                            final BlockPos sidePos = this.originalPos.add((Vec3i)side);
                            final BlockPos addPos = this.originalPos.add((Vec3i)add);
                            for (final BlockPos heigh : this.height) {
                                final Block sideState = AutoPhase.mc.world.getBlockState(sidePos.add((Vec3i)heigh)).getBlock();
                                final Block addState = AutoPhase.mc.world.getBlockState(addPos.add((Vec3i)heigh)).getBlock();
                                if (this.blockList.contains(sideState)) {
                                    ++block;
                                }
                                if (sideState == Blocks.BEDROCK) {
                                    ++bedrock;
                                }
                                if (this.blockList.contains(addState)) {
                                    ++block;
                                }
                                if (addState == Blocks.BEDROCK) {
                                    ++bedrock;
                                }
                            }
                            boolean shouldSet = false;
                            if (block > blocks) {
                                shouldSet = true;
                            }
                            else if (block == blocks && bedrock > bedrocks) {
                                shouldSet = true;
                            }
                            if (shouldSet) {
                                bedrocks = bedrock;
                                blocks = block;
                                xAdd = this.getAdd(side.x + add.x);
                                zAdd = this.getAdd(side.z + add.z);
                            }
                        }
                    }
                }
            }
            AutoPhase.mc.player.setPosition(this.originalPos.getX() + xAdd, AutoPhase.mc.player.posY, this.originalPos.getZ() + zAdd);
            this.down = (AutoPhase.mc.player.posX == this.originalPos.getX() + xAdd && AutoPhase.mc.player.posZ == this.originalPos.getZ() + zAdd);
        }
        if (this.down) {
            AutoPhase.mc.player.motionX = 0.0;
            AutoPhase.mc.player.motionZ = 0.0;
            AutoPhase.mc.playerController.onPlayerDamageBlock(this.originalPos, EnumFacing.UP);
            if (AutoPhase.mc.world.isAirBlock(this.originalPos)) {
                this.disable();
            }
        }
    }
    
    private double getAdd(final int pos) {
        if (pos == 1) {
            return 0.99999999;
        }
        return 0.0;
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
    
    public static void rightClickBlock(final BlockPos pos, final EnumFacing facing, final Vec3d hVec, final boolean packet, final boolean swing) {
        final Vec3d hitVec = new Vec3d((Vec3i)pos).add(hVec).add(new Vec3d(facing.getDirectionVec()).scale(0.5));
        if (packet) {
            rightClickBlock(pos, hitVec, EnumHand.MAIN_HAND, facing);
        }
        else {
            AutoPhase.mc.playerController.processRightClickBlock(AutoPhase.mc.player, AutoPhase.mc.world, pos, facing, hitVec, EnumHand.MAIN_HAND);
        }
        if (swing) {
            AutoPhase.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction) {
        final float f = (float)(vec.x - pos.getX());
        final float f2 = (float)(vec.y - pos.getY());
        final float f3 = (float)(vec.z - pos.getZ());
        AutoPhase.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
    }
}
