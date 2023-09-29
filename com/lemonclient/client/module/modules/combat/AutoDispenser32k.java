//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import java.text.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.world.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.block.*;
import java.math.*;
import com.lemonclient.api.util.player.social.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.misc.*;

@Module.Declaration(name = "AutoDispenser32k", category = Category.Combat)
public class AutoDispenser32k extends Module
{
    private static final DecimalFormat df;
    private int hopperSlot;
    private int redstoneSlot;
    private int shulkerSlot;
    private int dispenserSlot;
    private int obiSlot;
    private int stage;
    private BlockPos placeTarget;
    DoubleSetting placerange;
    DoubleSetting yRange;
    BooleanSetting placeclosetoenemy;
    BooleanSetting hopperWait;
    BooleanSetting silent;
    BooleanSetting debugMessages;
    private EnumFacing f;
    private int delay;
    private int delaycount;
    private int oldslot;
    
    public AutoDispenser32k() {
        this.placerange = this.registerDouble("PlaceRange", 4.0, 0.0, 10.0);
        this.yRange = this.registerDouble("Y Range", 2.5, 0.0, 10.0);
        this.placeclosetoenemy = this.registerBoolean("Place Close To Enemy", true);
        this.hopperWait = this.registerBoolean("HopperWait", true);
        this.silent = this.registerBoolean("Silent", true);
        this.debugMessages = this.registerBoolean("Debug Messages", false);
    }
    
    public List<BlockPos> getPlaceableBlocks() {
        final List<BlockPos> toreturn = new ArrayList<BlockPos>();
        for (final BlockPos w : EntityUtil.getSphere(PlayerUtil.getEyesPos(), (Double)this.placerange.getValue(), (Double)this.yRange.getValue(), false, false, 0)) {
            final double[] lookat = EntityUtil.calculateLookAt((double)w.x, (double)(w.y + 1), (double)w.z, (Entity)AutoDispenser32k.mc.player);
            final double lookatyaw = (lookat[0] + 45.0 > 360.0) ? (lookat[0] - 360.0) : lookat[0];
            final float yaw = (float)lookatyaw;
            final boolean isNegative = yaw < 0.0f;
            final int dir = Math.round(Math.abs(yaw)) % 360;
            EnumFacing f;
            if (135 < dir && dir < 225) {
                f = EnumFacing.SOUTH;
            }
            else if (225 < dir && dir < 315) {
                if (isNegative) {
                    f = EnumFacing.EAST;
                }
                else {
                    f = EnumFacing.WEST;
                }
            }
            else if (45 < dir && dir < 135) {
                if (isNegative) {
                    f = EnumFacing.WEST;
                }
                else {
                    f = EnumFacing.EAST;
                }
            }
            else {
                f = EnumFacing.NORTH;
            }
            if (this.isAreaPlaceable(w, f)) {
                toreturn.add(w);
            }
        }
        return toreturn;
    }
    
    public List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }
    
    private boolean isAreaPlaceable(final BlockPos blockPos, final EnumFacing f) {
        for (final EntityPlayer w : AutoDispenser32k.mc.world.playerEntities) {
            if (Math.sqrt(w.getDistanceSq((double)blockPos.x, AutoDispenser32k.mc.player.posY, (double)blockPos.z)) <= 2.0) {
                return false;
            }
        }
        final List<Entity> entityList = new ArrayList<Entity>();
        entityList.addAll(AutoDispenser32k.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos)));
        entityList.addAll(AutoDispenser32k.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos.add(0, 1, 0))));
        entityList.addAll(AutoDispenser32k.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos.add(0, 2, 0))));
        entityList.addAll(AutoDispenser32k.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos.add(0, 2, 0))));
        entityList.addAll(AutoDispenser32k.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos.offset(f))));
        entityList.addAll(AutoDispenser32k.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos.add(0, 1, 0).offset(f))));
        for (final Entity entity : entityList) {
            if (entity instanceof EntityLivingBase) {
                return false;
            }
        }
        return AutoDispenser32k.mc.world.getBlockState(blockPos).getMaterial().isReplaceable() && AutoDispenser32k.mc.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial().isReplaceable() && (Math.abs(blockPos.y + 1 - AutoDispenser32k.mc.player.posY) < 2.0 || Math.sqrt(AutoDispenser32k.mc.player.getDistanceSq((double)blockPos.x, AutoDispenser32k.mc.player.posY, (double)blockPos.z)) > 2.0) && AutoDispenser32k.mc.world.getBlockState(blockPos.add(0, 2, 0)).getMaterial().isReplaceable() && AutoDispenser32k.mc.world.getBlockState(blockPos.offset(f)).getMaterial().isReplaceable() && !(AutoDispenser32k.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() instanceof BlockAir) && !(AutoDispenser32k.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() instanceof BlockLiquid) && AutoDispenser32k.mc.world.getBlockState(blockPos.add(0, 1, 0).offset(f)).getMaterial().isReplaceable();
    }
    
    public void onEnable() {
        if (AutoDispenser32k.mc.player == null || AutoDispenser32k.mc.world == null || AutoDispenser32k.mc.player.isDead) {
            this.disable();
            return;
        }
        this.oldslot = AutoDispenser32k.mc.player.inventory.currentItem;
        final int hopperSlot = -1;
        this.obiSlot = hopperSlot;
        this.dispenserSlot = hopperSlot;
        this.shulkerSlot = hopperSlot;
        this.redstoneSlot = hopperSlot;
        this.hopperSlot = hopperSlot;
        for (int i = 0; i < 9 && (this.obiSlot == -1 || this.dispenserSlot == -1 || this.shulkerSlot == -1 || this.redstoneSlot == -1 || this.hopperSlot == -1); ++i) {
            final ItemStack stack = AutoDispenser32k.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block == Blocks.HOPPER) {
                    this.hopperSlot = i;
                }
                else if (BlockUtil.shulkerList.contains(block)) {
                    this.shulkerSlot = i;
                }
                else if (block == Blocks.OBSIDIAN) {
                    this.obiSlot = i;
                }
                else if (block == Blocks.DISPENSER) {
                    this.dispenserSlot = i;
                }
                else if (block == Blocks.REDSTONE_BLOCK) {
                    this.redstoneSlot = i;
                }
            }
        }
        if (this.obiSlot == -1 || this.dispenserSlot == -1 || this.shulkerSlot == -1 || this.redstoneSlot == -1 || this.hopperSlot == -1) {
            if (this.debugMessages.getValue()) {
                MessageBus.sendClientPrefixMessage("Item missing, " + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getModuleColor() + "AutoDispenser32k" + ChatFormatting.GRAY + " disabling.");
            }
            this.disable();
        }
        this.stage = 0;
    }
    
    public void onUpdate() {
        if (AutoDispenser32k.mc.player == null || AutoDispenser32k.mc.world == null || AutoDispenser32k.mc.player.isDead) {
            this.disable();
            return;
        }
        AutoDispenser32k.df.setRoundingMode(RoundingMode.CEILING);
        switch (this.stage) {
            case 0: {
                this.delay = 10;
                this.delaycount = 0;
                final List<BlockPos> canPlaceLocation = this.getPlaceableBlocks();
                if (this.placeclosetoenemy.getValue()) {
                    final EntityPlayer targetPlayer = (EntityPlayer)AutoDispenser32k.mc.world.playerEntities.stream().filter(e -> e != AutoDispenser32k.mc.player && !SocialManager.isFriend(e.getName())).min(Comparator.comparing(e -> AutoDispenser32k.mc.player.getDistance(e))).orElse(null);
                    this.placeTarget = ((targetPlayer != null) ? canPlaceLocation.stream().min(Comparator.comparing(e -> BlockUtil.blockDistance((double)e.x, (double)e.y, (double)e.z, (Entity)targetPlayer))).orElse(null) : canPlaceLocation.stream().max(Comparator.comparing(e -> BlockUtil.blockDistance((double)e.x, (double)e.y, (double)e.z, (Entity)AutoDispenser32k.mc.player))).orElse(null));
                }
                else {
                    this.placeTarget = canPlaceLocation.stream().max(Comparator.comparing(e -> BlockUtil.blockDistance((double)e.x, (double)e.y, (double)e.z, (Entity)AutoDispenser32k.mc.player))).orElse(null);
                }
                if (this.placeTarget == null) {
                    if (this.debugMessages.getValue()) {
                        MessageBus.sendClientPrefixMessage("No suitable place to place, " + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getModuleColor() + "AutoDispenser32k" + ChatFormatting.GRAY + " disabling.");
                    }
                    this.disable();
                    break;
                }
                AutoDispenser32k.mc.player.inventory.currentItem = this.obiSlot;
                placeBlockScaffold(new BlockPos((Vec3i)this.placeTarget));
                if (this.silent.getValue()) {
                    AutoDispenser32k.mc.player.inventory.currentItem = this.oldslot;
                }
                final double[] lookat = EntityUtil.calculateLookAt((double)this.placeTarget.x, (double)(this.placeTarget.y + 1), (double)this.placeTarget.z, (Entity)AutoDispenser32k.mc.player);
                final double lookatyaw = (lookat[0] + 45.0 > 360.0) ? (lookat[0] - 360.0) : lookat[0];
                final float yaw = (float)lookatyaw;
                final boolean isNegative = yaw < 0.0f;
                final int dir = Math.round(Math.abs(yaw)) % 360;
                if (135 < dir && dir < 225) {
                    this.f = EnumFacing.SOUTH;
                }
                else if (225 < dir && dir < 315) {
                    if (isNegative) {
                        this.f = EnumFacing.EAST;
                    }
                    else {
                        this.f = EnumFacing.WEST;
                    }
                }
                else if (45 < dir && dir < 135) {
                    if (isNegative) {
                        this.f = EnumFacing.WEST;
                    }
                    else {
                        this.f = EnumFacing.EAST;
                    }
                }
                else {
                    this.f = EnumFacing.NORTH;
                }
                AutoDispenser32k.mc.player.inventory.currentItem = this.dispenserSlot;
                placeBlockScaffold(new BlockPos((Vec3i)this.placeTarget.add(0, 1, 0)));
                AutoDispenser32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget.add(0, 1, 0), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                if (this.silent.getValue()) {
                    AutoDispenser32k.mc.player.inventory.currentItem = this.oldslot;
                }
                ++this.stage;
                break;
            }
            case 1: {
                if (AutoDispenser32k.mc.currentScreen instanceof GuiContainer) {
                    AutoDispenser32k.mc.playerController.windowClick(AutoDispenser32k.mc.player.openContainer.windowId, 1, this.shulkerSlot, ClickType.SWAP, (EntityPlayer)AutoDispenser32k.mc.player);
                    AutoDispenser32k.mc.player.closeScreen();
                    ++this.stage;
                    break;
                }
                break;
            }
            case 2: {
                AutoDispenser32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoDispenser32k.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                AutoDispenser32k.mc.player.inventory.currentItem = this.redstoneSlot;
                placeBlockScaffold(new BlockPos((Vec3i)this.placeTarget.add(0, 2, 0)));
                if (this.silent.getValue()) {
                    AutoDispenser32k.mc.player.inventory.currentItem = this.oldslot;
                }
                AutoDispenser32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoDispenser32k.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                ++this.stage;
                break;
            }
            case 3: {
                if (!(boolean)this.hopperWait.getValue()) {
                    AutoDispenser32k.mc.player.inventory.currentItem = this.hopperSlot;
                    placeBlockScaffold(new BlockPos((Vec3i)this.placeTarget.offset(this.f)));
                    if (this.silent.getValue()) {
                        AutoDispenser32k.mc.player.inventory.currentItem = this.oldslot;
                    }
                    AutoDispenser32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget.offset(this.f), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                    AutoDispenser32k.mc.player.inventory.currentItem = this.shulkerSlot;
                    if (this.silent.getValue()) {
                        AutoDispenser32k.mc.player.inventory.currentItem = this.oldslot;
                    }
                    this.stage = 0;
                    this.disable();
                    break;
                }
                if (this.delaycount >= this.delay) {
                    AutoDispenser32k.mc.player.inventory.currentItem = this.hopperSlot;
                    placeBlockScaffold(new BlockPos((Vec3i)this.placeTarget.offset(this.f)));
                    if (this.silent.getValue()) {
                        AutoDispenser32k.mc.player.inventory.currentItem = this.oldslot;
                    }
                    AutoDispenser32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget.offset(this.f), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                    if (!(boolean)this.silent.getValue()) {
                        AutoDispenser32k.mc.player.inventory.currentItem = this.shulkerSlot;
                    }
                    this.stage = 0;
                    if (this.debugMessages.getValue()) {
                        MessageBus.sendClientPrefixMessage("AutoDispenser32k Place Target: " + this.placeTarget.x + " " + this.placeTarget.y + " " + this.placeTarget.z + " Distance: " + AutoDispenser32k.df.format(AutoDispenser32k.mc.player.getPositionVector().distanceTo(new Vec3d((Vec3i)this.placeTarget))));
                    }
                    this.disable();
                    break;
                }
                ++this.delaycount;
                break;
            }
        }
    }
    
    public static void placeBlockScaffold(final BlockPos pos) {
        boolean needShift = false;
        final Vec3d eyesPos = new Vec3d(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight(), Wrapper.getPlayer().posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (BlockUtil.shulkerList.contains(AutoDispenser32k.mc.world.getBlockState(neighbor).getBlock()) || BlockUtil.blackList.contains(AutoDispenser32k.mc.world.getBlockState(neighbor).getBlock())) {
                needShift = true;
            }
            if (BlockUtil.canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) <= 36.0) {
                    AutoHopper32k.faceVectorPacketInstant(hitVec);
                    if (needShift) {
                        AutoDispenser32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoDispenser32k.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    com.lemonclient.api.util.world.combat.BlockUtil.processRightClickBlock(neighbor, side2, hitVec);
                    Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
                    if (needShift) {
                        AutoDispenser32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoDispenser32k.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    AutoDispenser32k.mc.rightClickDelayTimer = 4;
                    return;
                }
            }
        }
    }
    
    static {
        df = new DecimalFormat("#.#");
    }
}
