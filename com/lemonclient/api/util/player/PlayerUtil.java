//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.player;

import net.minecraft.client.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.lemonclient.api.util.world.*;

public class PlayerUtil
{
    private static final Minecraft mc;
    
    public static void setPosition(final double x, final double y, final double z) {
        PlayerUtil.mc.player.setPosition(x, y, z);
    }
    
    public static void setPosition(final BlockPos pos) {
        PlayerUtil.mc.player.setPosition(pos.getX() + 0.5, (double)pos.getY(), pos.getZ() + 0.5);
    }
    
    public static Vec3d getMotionVector() {
        return new Vec3d(PlayerUtil.mc.player.motionX, PlayerUtil.mc.player.motionY, PlayerUtil.mc.player.motionZ);
    }
    
    public static void vClip(final double d) {
        PlayerUtil.mc.player.setPosition(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.posY + d, PlayerUtil.mc.player.posZ);
    }
    
    public static void move(final double x, final double y, final double z) {
        PlayerUtil.mc.player.move(MoverType.SELF, x, y, z);
    }
    
    public static void setMotionVector(final Vec3d vec) {
        PlayerUtil.mc.player.motionX = vec.x;
        PlayerUtil.mc.player.motionY = vec.y;
        PlayerUtil.mc.player.motionZ = vec.z;
    }
    
    public static boolean isInsideBlock() {
        try {
            final AxisAlignedBB playerBoundingBox = PlayerUtil.mc.player.getEntityBoundingBox();
            for (int x = MathHelper.floor(playerBoundingBox.minX); x < MathHelper.floor(playerBoundingBox.maxX) + 1; ++x) {
                for (int y = MathHelper.floor(playerBoundingBox.minY); y < MathHelper.floor(playerBoundingBox.maxY) + 1; ++y) {
                    for (int z = MathHelper.floor(playerBoundingBox.minZ); z < MathHelper.floor(playerBoundingBox.maxZ) + 1; ++z) {
                        final Block block = PlayerUtil.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                        if (!(block instanceof BlockAir)) {
                            AxisAlignedBB boundingBox = Objects.requireNonNull(block.getCollisionBoundingBox(PlayerUtil.mc.world.getBlockState(new BlockPos(x, y, z)), (IBlockAccess)PlayerUtil.mc.world, new BlockPos(x, y, z))).offset((double)x, (double)y, (double)z);
                            if (block instanceof BlockHopper) {
                                boundingBox = new AxisAlignedBB((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1));
                            }
                            if (playerBoundingBox.intersects(boundingBox)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            return false;
        }
        return false;
    }
    
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(PlayerUtil.mc.player.posX), Math.floor(PlayerUtil.mc.player.posY), Math.floor(PlayerUtil.mc.player.posZ));
    }
    
    public static boolean isPlayerClipped() {
        return !PlayerUtil.mc.world.getCollisionBoxes((Entity)PlayerUtil.mc.player, PlayerUtil.mc.player.getEntityBoundingBox()).isEmpty();
    }
    
    public static void fakeJump() {
        fakeJump(5);
    }
    
    public static void fakeJump(final int packets) {
        if (packets > 0 && packets != 5) {
            PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.posY, PlayerUtil.mc.player.posZ, true));
        }
        if (packets > 1) {
            PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.posY + 0.419999986887, PlayerUtil.mc.player.posZ, true));
        }
        if (packets > 2) {
            PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.posY + 0.7531999805212, PlayerUtil.mc.player.posZ, true));
        }
        if (packets > 3) {
            PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.posY + 1.0013359791121, PlayerUtil.mc.player.posZ, true));
        }
        if (packets > 4) {
            PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.posY + 1.1661092609382, PlayerUtil.mc.player.posZ, true));
        }
    }
    
    public static double getDistance(final Entity entity) {
        return PlayerUtil.mc.player.getDistance(entity);
    }
    
    public static double getDistance(final BlockPos pos) {
        return PlayerUtil.mc.player.getDistance((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
    }
    
    public static double getDistanceI(final BlockPos pos) {
        return getPlayerPos().getDistance(pos.getX(), pos.getY(), pos.getZ());
    }
    
    public static BlockPos getEyesPos() {
        return new BlockPos(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.posY + PlayerUtil.mc.player.getEyeHeight(), PlayerUtil.mc.player.posZ);
    }
    
    public static EntityPlayer getNearestPlayer(final double range) {
        return (EntityPlayer)PlayerUtil.mc.world.playerEntities.stream().filter(p -> PlayerUtil.mc.player.getDistance(p) <= range).filter(p -> PlayerUtil.mc.player.entityId != p.entityId).filter(p -> !EntityUtil.basicChecksEntity(p)).min(Comparator.comparing(p -> PlayerUtil.mc.player.getDistance(p))).orElse(null);
    }
    
    public EntityPlayer getTarget(final double range) {
        EntityPlayer target = null;
        double distance = Math.pow(range, 2.0) + 1.0;
        for (final EntityPlayer player : PlayerUtil.mc.world.playerEntities) {
            if (EntityUtil.basicChecksEntity(player)) {
                continue;
            }
            if (target == null) {
                target = player;
                distance = PlayerUtil.mc.player.getDistanceSq((Entity)player);
            }
            else {
                if (PlayerUtil.mc.player.getDistanceSq((Entity)player) >= distance) {
                    continue;
                }
                target = player;
                distance = PlayerUtil.mc.player.getDistanceSq((Entity)player);
            }
        }
        return target;
    }
    
    public static EntityPlayer findClosestTarget(double rangeMax, final EntityPlayer aimTarget) {
        rangeMax *= rangeMax;
        final List<EntityPlayer> playerList = (List<EntityPlayer>)PlayerUtil.mc.world.playerEntities;
        EntityPlayer closestTarget = null;
        for (final EntityPlayer entityPlayer : playerList) {
            if (PlayerUtil.mc.player.getDistanceSq((Entity)entityPlayer) > rangeMax) {
                continue;
            }
            if (EntityUtil.basicChecksEntity(entityPlayer)) {
                continue;
            }
            if (aimTarget == null) {
                closestTarget = entityPlayer;
            }
            else {
                if (PlayerUtil.mc.player.getDistanceSq((Entity)entityPlayer) >= PlayerUtil.mc.player.getDistanceSq((Entity)aimTarget)) {
                    continue;
                }
                closestTarget = entityPlayer;
            }
        }
        return closestTarget;
    }
    
    public static EntityPlayer findTarget(double rangeMax, final EntityPlayer aimTarget) {
        rangeMax *= rangeMax;
        final List<EntityPlayer> playerList = (List<EntityPlayer>)PlayerUtil.mc.world.playerEntities;
        EntityPlayer closestTarget = null;
        for (final EntityPlayer entityPlayer : playerList) {
            if (EntityUtil.basicChecksEntity(entityPlayer)) {
                continue;
            }
            if (!entityPlayer.onGround) {
                continue;
            }
            if (aimTarget == null && PlayerUtil.mc.player.getDistanceSq((Entity)entityPlayer) <= rangeMax) {
                closestTarget = entityPlayer;
            }
            else {
                if (aimTarget == null || PlayerUtil.mc.player.getDistanceSq((Entity)entityPlayer) > rangeMax || PlayerUtil.mc.player.getDistanceSq((Entity)entityPlayer) >= PlayerUtil.mc.player.getDistanceSq((Entity)aimTarget)) {
                    continue;
                }
                closestTarget = entityPlayer;
            }
        }
        return closestTarget;
    }
    
    public static EntityPlayer findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)PlayerUtil.mc.world.playerEntities;
        EntityPlayer closestTarget = null;
        for (final EntityPlayer entityPlayer : playerList) {
            if (EntityUtil.basicChecksEntity(entityPlayer)) {
                continue;
            }
            if (closestTarget == null) {
                closestTarget = entityPlayer;
            }
            else {
                if (PlayerUtil.mc.player.getDistanceSq((Entity)entityPlayer) >= PlayerUtil.mc.player.getDistanceSq((Entity)closestTarget)) {
                    continue;
                }
                closestTarget = entityPlayer;
            }
        }
        return closestTarget;
    }
    
    public static EntityPlayer findLookingPlayer(final double rangeMax) {
        final ArrayList<EntityPlayer> listPlayer = new ArrayList<EntityPlayer>();
        for (final EntityPlayer playerSin : PlayerUtil.mc.world.playerEntities) {
            if (EntityUtil.basicChecksEntity(playerSin)) {
                continue;
            }
            if (PlayerUtil.mc.player.getDistance((Entity)playerSin) > rangeMax) {
                continue;
            }
            listPlayer.add(playerSin);
        }
        EntityPlayer target = null;
        final Vec3d positionEyes = PlayerUtil.mc.player.getPositionEyes(PlayerUtil.mc.getRenderPartialTicks());
        final Vec3d rotationEyes = PlayerUtil.mc.player.getLook(PlayerUtil.mc.getRenderPartialTicks());
        final int precision = 2;
        for (int i = 0; i < (int)rangeMax; ++i) {
            for (int j = precision; j > 0; --j) {
                for (final EntityPlayer targetTemp : listPlayer) {
                    final AxisAlignedBB playerBox = targetTemp.getEntityBoundingBox();
                    final double xArray = positionEyes.x + rotationEyes.x * i + rotationEyes.x / j;
                    final double yArray = positionEyes.y + rotationEyes.y * i + rotationEyes.y / j;
                    final double zArray = positionEyes.z + rotationEyes.z * i + rotationEyes.z / j;
                    if (playerBox.maxY >= yArray && playerBox.minY <= yArray && playerBox.maxX >= xArray && playerBox.minX <= xArray && playerBox.maxZ >= zArray && playerBox.minZ <= zArray) {
                        target = targetTemp;
                    }
                }
            }
        }
        return target;
    }
    
    public static float getHealth() {
        return PlayerUtil.mc.player.getHealth() + PlayerUtil.mc.player.getAbsorptionAmount();
    }
    
    public static void centerPlayer(final Vec3d centeredBlock) {
        double newX = -2.0;
        double newZ = -2.0;
        final int xRel = (PlayerUtil.mc.player.posX < 0.0) ? -1 : 1;
        final int zRel = (PlayerUtil.mc.player.posZ < 0.0) ? -1 : 1;
        if (BlockUtil.getBlock(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.posY - 1.0, PlayerUtil.mc.player.posZ) instanceof BlockAir) {
            if (Math.abs(PlayerUtil.mc.player.posX % 1.0) * 100.0 <= 30.0) {
                newX = Math.round(PlayerUtil.mc.player.posX - 0.3 * xRel) + 0.5 * -xRel;
            }
            else if (Math.abs(PlayerUtil.mc.player.posX % 1.0) * 100.0 >= 70.0) {
                newX = Math.round(PlayerUtil.mc.player.posX + 0.3 * xRel) - 0.5 * -xRel;
            }
            if (Math.abs(PlayerUtil.mc.player.posZ % 1.0) * 100.0 <= 30.0) {
                newZ = Math.round(PlayerUtil.mc.player.posZ - 0.3 * zRel) + 0.5 * -zRel;
            }
            else if (Math.abs(PlayerUtil.mc.player.posZ % 1.0) * 100.0 >= 70.0) {
                newZ = Math.round(PlayerUtil.mc.player.posZ + 0.3 * zRel) - 0.5 * -zRel;
            }
        }
        if (newX == -2.0) {
            if (PlayerUtil.mc.player.posX > Math.round(PlayerUtil.mc.player.posX)) {
                newX = Math.round(PlayerUtil.mc.player.posX) + 0.5;
            }
            else if (PlayerUtil.mc.player.posX < Math.round(PlayerUtil.mc.player.posX)) {
                newX = Math.round(PlayerUtil.mc.player.posX) - 0.5;
            }
            else {
                newX = PlayerUtil.mc.player.posX;
            }
        }
        if (newZ == -2.0) {
            if (PlayerUtil.mc.player.posZ > Math.round(PlayerUtil.mc.player.posZ)) {
                newZ = Math.round(PlayerUtil.mc.player.posZ) + 0.5;
            }
            else if (PlayerUtil.mc.player.posZ < Math.round(PlayerUtil.mc.player.posZ)) {
                newZ = Math.round(PlayerUtil.mc.player.posZ) - 0.5;
            }
            else {
                newZ = PlayerUtil.mc.player.posZ;
            }
        }
        PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(newX, PlayerUtil.mc.player.posY, newZ, true));
        PlayerUtil.mc.player.setPosition(newX, PlayerUtil.mc.player.posY, newZ);
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
