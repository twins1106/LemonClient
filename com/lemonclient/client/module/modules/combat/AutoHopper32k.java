//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import java.text.*;
import com.lemonclient.api.setting.values.*;
import java.math.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.mojang.realmsclient.gui.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.player.social.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.math.*;
import java.util.*;

@Module.Declaration(name = "AutoHopper32k", category = Category.Combat)
public class AutoHopper32k extends Module
{
    private static final DecimalFormat df;
    BooleanSetting moveToHotbar;
    DoubleSetting placeRange;
    IntegerSetting yOffset;
    BooleanSetting placeCloseToEnemy;
    BooleanSetting placeObiOnTop;
    BooleanSetting silent;
    BooleanSetting debugMessages;
    int oldslot;
    private int swordSlot;
    private static boolean isSneaking;
    private static final List<Block> blackList;
    public static final List<Block> shulkerList;
    
    public AutoHopper32k() {
        this.moveToHotbar = this.registerBoolean("Move 32k to Hotbar", true);
        this.placeRange = this.registerDouble("Place range", 4.0, 0.0, 10.0);
        this.yOffset = this.registerInteger("Y Offset (Hopper)", 2, 0, 10);
        this.placeCloseToEnemy = this.registerBoolean("Place close to enemy", false);
        this.placeObiOnTop = this.registerBoolean("Place Obi on Top", true);
        this.silent = this.registerBoolean("Silent", true);
        this.debugMessages = this.registerBoolean("Debug Messages", true);
    }
    
    protected void onEnable() {
        if (AutoHopper32k.mc.player == null || AutoHopper32k.mc.world == null || AutoHopper32k.mc.player.isDead) {
            this.disable();
            return;
        }
        this.oldslot = AutoHopper32k.mc.player.inventory.currentItem;
        AutoHopper32k.df.setRoundingMode(RoundingMode.CEILING);
        int hopperSlot = -1;
        int shulkerSlot = -1;
        int obiSlot = -1;
        this.swordSlot = -1;
        for (int i = 0; i < 9 && (hopperSlot == -1 || shulkerSlot == -1 || obiSlot == -1); ++i) {
            final ItemStack stack = AutoHopper32k.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block == Blocks.HOPPER) {
                        hopperSlot = i;
                    }
                    else if (AutoHopper32k.shulkerList.contains(block)) {
                        shulkerSlot = i;
                    }
                    else if (block == Blocks.OBSIDIAN) {
                        obiSlot = i;
                    }
                }
            }
        }
        if (hopperSlot == -1) {
            if (this.debugMessages.getValue()) {
                MessageBus.sendClientPrefixMessage("Hopper missing, " + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getModuleColor() + "AutoHopper32k" + ChatFormatting.GRAY + " disabling.");
            }
            this.disable();
            return;
        }
        if (shulkerSlot == -1) {
            if (this.debugMessages.getValue()) {
                MessageBus.sendClientPrefixMessage("Shulker missing, " + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getModuleColor() + "AutoHopper32k" + ChatFormatting.GRAY + " disabling.");
            }
            this.disable();
            return;
        }
        final double range = Math.ceil((double)this.placeRange.getValue());
        final double yRange = Math.ceil((int)this.yOffset.getValue());
        final List<BlockPos> placeTargetList = (List<BlockPos>)EntityUtil.getSphere(PlayerUtil.getEyesPos(), Double.valueOf(range), Double.valueOf(yRange), false, false, 0);
        final Map<BlockPos, Double> placeTargetMap = new HashMap<BlockPos, Double>();
        BlockPos placeTarget = null;
        boolean useRangeSorting = false;
        for (final BlockPos placeTargetTest : placeTargetList) {
            for (final Entity entity : AutoHopper32k.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityPlayer)) {
                    continue;
                }
                if (entity == AutoHopper32k.mc.player) {
                    continue;
                }
                if (SocialManager.isFriend(entity.getName())) {
                    continue;
                }
                if ((int)this.yOffset.getValue() != 0 && Math.abs(AutoHopper32k.mc.player.getPosition().y - placeTargetTest.y) > Math.abs((int)this.yOffset.getValue())) {
                    continue;
                }
                if (!this.isAreaPlaceable(placeTargetTest)) {
                    continue;
                }
                final double distanceToEntity = entity.getDistance((double)placeTargetTest.x, (double)placeTargetTest.y, (double)placeTargetTest.z);
                placeTargetMap.put(placeTargetTest, placeTargetMap.containsKey(placeTargetTest) ? (placeTargetMap.get(placeTargetTest) + distanceToEntity) : distanceToEntity);
                useRangeSorting = true;
            }
        }
        if (placeTargetMap.size() > 0) {
            final Map map;
            placeTargetMap.forEach((k, v) -> {
                if (!this.isAreaPlaceable(k)) {
                    map.remove(k);
                }
                return;
            });
            if (placeTargetMap.size() == 0) {
                useRangeSorting = false;
            }
        }
        if (useRangeSorting) {
            if (this.placeCloseToEnemy.getValue()) {
                placeTarget = Collections.min((Collection<? extends Map.Entry<BlockPos, V>>)placeTargetMap.entrySet(), (Comparator<? super Map.Entry<BlockPos, V>>)Map.Entry.comparingByValue()).getKey();
            }
            else {
                placeTarget = Collections.max((Collection<? extends Map.Entry<BlockPos, V>>)placeTargetMap.entrySet(), (Comparator<? super Map.Entry<BlockPos, V>>)Map.Entry.comparingByValue()).getKey();
            }
        }
        else {
            for (final BlockPos pos : placeTargetList) {
                if (this.isAreaPlaceable(pos)) {
                    placeTarget = pos;
                    break;
                }
            }
        }
        if (placeTarget == null) {
            if (this.debugMessages.getValue()) {
                MessageBus.sendClientPrefixMessage("No valid position in range to place, " + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getModuleColor() + "AutoHopper32k" + ChatFormatting.GRAY + " disabling.");
            }
            this.disable();
            return;
        }
        if (this.debugMessages.getValue()) {
            MessageBus.sendClientPrefixMessage("AutoHopper32k Place Target: " + placeTarget.x + " " + placeTarget.y + " " + placeTarget.z + " Distance: " + AutoHopper32k.df.format(AutoHopper32k.mc.player.getPositionVector().distanceTo(new Vec3d((Vec3i)placeTarget))));
        }
        AutoHopper32k.mc.player.inventory.currentItem = hopperSlot;
        placeBlock(new BlockPos((Vec3i)placeTarget));
        AutoHopper32k.mc.player.inventory.currentItem = shulkerSlot;
        placeBlock(new BlockPos((Vec3i)placeTarget.add(0, 1, 0)));
        if (this.silent.getValue()) {
            AutoHopper32k.mc.player.inventory.currentItem = this.oldslot;
        }
        if ((boolean)this.placeObiOnTop.getValue() && BurrowUtil.findHotbarBlock((Class)BlockObsidian.class) != -1) {
            AutoHopper32k.mc.player.inventory.currentItem = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
            placeBlock(new BlockPos((Vec3i)placeTarget.add(0, 2, 0)));
            if (this.silent.getValue()) {
                AutoHopper32k.mc.player.inventory.currentItem = this.oldslot;
            }
        }
        if (AutoHopper32k.isSneaking) {
            AutoHopper32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoHopper32k.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            AutoHopper32k.isSneaking = false;
        }
        if (!(boolean)this.silent.getValue()) {
            AutoHopper32k.mc.player.inventory.currentItem = shulkerSlot;
        }
        final BlockPos hopperPos = new BlockPos((Vec3i)placeTarget);
        AutoHopper32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(hopperPos, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        this.swordSlot = shulkerSlot + 32;
    }
    
    public void onUpdate() {
        if (AutoHopper32k.mc.player == null || AutoHopper32k.mc.world == null || AutoHopper32k.mc.player.isDead) {
            this.disable();
            return;
        }
        if (!(AutoHopper32k.mc.currentScreen instanceof GuiContainer)) {
            return;
        }
        if (!(boolean)this.moveToHotbar.getValue()) {
            this.disable();
            return;
        }
        if (this.swordSlot == -1) {
            return;
        }
        boolean swapReady = !((GuiContainer)AutoHopper32k.mc.currentScreen).inventorySlots.getSlot(0).getStack().isEmpty;
        if (!((GuiContainer)AutoHopper32k.mc.currentScreen).inventorySlots.getSlot(this.swordSlot).getStack().isEmpty) {
            swapReady = false;
        }
        if (swapReady) {
            AutoHopper32k.mc.playerController.windowClick(((GuiContainer)AutoHopper32k.mc.currentScreen).inventorySlots.windowId, 0, this.swordSlot - 32, ClickType.SWAP, (EntityPlayer)AutoHopper32k.mc.player);
            this.disable();
        }
    }
    
    private boolean isAreaPlaceable(final BlockPos blockPos) {
        for (final Entity entity : AutoHopper32k.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos))) {
            if (entity instanceof EntityLivingBase) {
                return false;
            }
        }
        if (!AutoHopper32k.mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) {
            return false;
        }
        if (!AutoHopper32k.mc.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial().isReplaceable()) {
            return false;
        }
        if (AutoHopper32k.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() instanceof BlockAir) {
            return false;
        }
        if (AutoHopper32k.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() instanceof BlockLiquid) {
            return false;
        }
        if (AutoHopper32k.mc.player.getPositionVector().distanceTo(new Vec3d((Vec3i)blockPos)) > (double)this.placeRange.getValue()) {
            return false;
        }
        final Block block = AutoHopper32k.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock();
        return !AutoHopper32k.blackList.contains(block) && !AutoHopper32k.shulkerList.contains(block) && AutoHopper32k.mc.player.getPositionVector().distanceTo(new Vec3d((Vec3i)blockPos).add(0.0, 1.0, 0.0)) <= (double)this.placeRange.getValue();
    }
    
    private static void placeBlock(final BlockPos pos) {
        if (!AutoHopper32k.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return;
        }
        if (!checkForNeighbours(pos)) {
            return;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (AutoHopper32k.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(AutoHopper32k.mc.world.getBlockState(neighbor), false)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                final Block neighborPos = AutoHopper32k.mc.world.getBlockState(neighbor).getBlock();
                if (AutoHopper32k.blackList.contains(neighborPos) || AutoHopper32k.shulkerList.contains(neighborPos)) {
                    AutoHopper32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoHopper32k.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    AutoHopper32k.isSneaking = true;
                }
                faceVectorPacketInstant(hitVec);
                AutoHopper32k.mc.playerController.processRightClickBlock(AutoHopper32k.mc.player, AutoHopper32k.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                AutoHopper32k.mc.player.swingArm(EnumHand.MAIN_HAND);
                AutoHopper32k.mc.rightClickDelayTimer = 4;
                return;
            }
        }
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getNeededRotations2(vec);
        AutoHopper32k.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], AutoHopper32k.mc.player.onGround));
    }
    
    private static float[] getNeededRotations2(final Vec3d vec) {
        final Vec3d eyesPos = BlockUtil.getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { AutoHopper32k.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - AutoHopper32k.mc.player.rotationYaw), AutoHopper32k.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - AutoHopper32k.mc.player.rotationPitch) };
    }
    
    private static boolean checkForNeighbours(final BlockPos blockPos) {
        if (!hasNeighbour(blockPos)) {
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbour = blockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    private static boolean hasNeighbour(final BlockPos blockPos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = blockPos.offset(side);
            if (!AutoHopper32k.mc.world.getBlockState(neighbour).getMaterial().isReplaceable()) {
                return true;
            }
        }
        return false;
    }
    
    static {
        df = new DecimalFormat("#.#");
        blackList = Arrays.asList(Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR);
        shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
    }
}
