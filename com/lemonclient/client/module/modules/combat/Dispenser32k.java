//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import java.text.*;
import com.lemonclient.api.setting.values.*;
import java.math.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.world.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.mojang.realmsclient.gui.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

@Module.Declaration(name = "Dispenser32k", category = Category.Combat)
public class Dispenser32k extends Module
{
    private static final DecimalFormat df;
    BooleanSetting grabItem;
    BooleanSetting silent;
    BooleanSetting debugMessages;
    int oldslot;
    private int stage;
    private BlockPos placeTarget;
    private int obiSlot;
    private int dispenserSlot;
    private int shulkerSlot;
    private int redstoneSlot;
    private int hopperSlot;
    private boolean isSneaking;
    
    public Dispenser32k() {
        this.grabItem = this.registerBoolean("Grab Item", false);
        this.silent = this.registerBoolean("Silent", true);
        this.debugMessages = this.registerBoolean("Debug Messages", false);
    }
    
    protected void onEnable() {
        if (Dispenser32k.mc.player == null || Dispenser32k.mc.world == null || Dispenser32k.mc.player.isDead) {
            this.disable();
            return;
        }
        this.oldslot = Dispenser32k.mc.player.inventory.currentItem;
        Dispenser32k.df.setRoundingMode(RoundingMode.CEILING);
        this.placeTarget = null;
        this.obiSlot = -1;
        this.dispenserSlot = -1;
        this.shulkerSlot = -1;
        this.redstoneSlot = -1;
        this.hopperSlot = -1;
        this.isSneaking = false;
        for (int i = 0; i < 9 && (this.obiSlot == -1 || this.dispenserSlot == -1 || this.shulkerSlot == -1 || this.redstoneSlot == -1 || this.hopperSlot == -1); ++i) {
            final ItemStack stack = Dispenser32k.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
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
        }
        if (this.obiSlot == -1 || this.dispenserSlot == -1 || this.shulkerSlot == -1 || this.redstoneSlot == -1 || this.hopperSlot == -1) {
            if (this.debugMessages.getValue()) {
                MessageBus.sendClientPrefixMessage("Auto32kItems missing, " + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getModuleColor() + "Dispenser32k" + ChatFormatting.GRAY + " disabling.");
            }
            this.disable();
            return;
        }
        if (Dispenser32k.mc.objectMouseOver == null || Dispenser32k.mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK) {
            if (this.debugMessages.getValue()) {
                MessageBus.sendClientPrefixMessage("Not a valid place target, " + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getModuleColor() + "Dispenser32k" + ChatFormatting.GRAY + " disabling.");
            }
            this.disable();
            return;
        }
        this.placeTarget = Dispenser32k.mc.objectMouseOver.getBlockPos().up();
        this.stage = 0;
        if (this.debugMessages.getValue()) {
            MessageBus.sendClientPrefixMessage("Dispenser32k: Succesfully placed 32k");
        }
    }
    
    public void onUpdate() {
        if (Dispenser32k.mc.player == null || Dispenser32k.mc.world == null || Dispenser32k.mc.player.isDead) {
            this.disable();
            return;
        }
        if (this.stage == 0) {
            Dispenser32k.mc.player.inventory.currentItem = this.obiSlot;
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget), EnumFacing.DOWN);
            Dispenser32k.mc.player.inventory.currentItem = this.dispenserSlot;
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget.add(0, 1, 0)), EnumFacing.DOWN);
            if (this.silent.getValue()) {
                Dispenser32k.mc.player.inventory.currentItem = this.oldslot;
            }
            Dispenser32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Dispenser32k.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
            Dispenser32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget.add(0, 1, 0), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            this.stage = 1;
            return;
        }
        if (this.stage == 1) {
            if (!(Dispenser32k.mc.currentScreen instanceof GuiContainer)) {
                return;
            }
            Dispenser32k.mc.playerController.windowClick(Dispenser32k.mc.player.openContainer.windowId, 1, this.shulkerSlot, ClickType.SWAP, (EntityPlayer)Dispenser32k.mc.player);
            Dispenser32k.mc.player.closeScreen();
            Dispenser32k.mc.player.inventory.currentItem = this.redstoneSlot;
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget.add(0, 2, 0)), EnumFacing.DOWN);
            if (this.silent.getValue()) {
                Dispenser32k.mc.player.inventory.currentItem = this.oldslot;
            }
            this.stage = 2;
        }
        else {
            if (this.stage != 2) {
                if (this.stage == 3) {
                    if (!(Dispenser32k.mc.currentScreen instanceof GuiContainer)) {
                        return;
                    }
                    if (((GuiContainer)Dispenser32k.mc.currentScreen).inventorySlots.getSlot(0).getStack().isEmpty) {
                        return;
                    }
                    Dispenser32k.mc.playerController.windowClick(Dispenser32k.mc.player.openContainer.windowId, 0, Dispenser32k.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Dispenser32k.mc.player);
                    this.disable();
                }
                return;
            }
            final Block block = Dispenser32k.mc.world.getBlockState(this.placeTarget.offset(Dispenser32k.mc.player.getHorizontalFacing().getOpposite()).up()).getBlock();
            if (block instanceof BlockAir || block instanceof BlockLiquid) {
                return;
            }
            Dispenser32k.mc.player.inventory.currentItem = this.hopperSlot;
            this.placeBlock(new BlockPos((Vec3i)this.placeTarget.offset(Dispenser32k.mc.player.getHorizontalFacing().getOpposite())), Dispenser32k.mc.player.getHorizontalFacing());
            if (this.silent.getValue()) {
                Dispenser32k.mc.player.inventory.currentItem = this.oldslot;
            }
            Dispenser32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Dispenser32k.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
            Dispenser32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget.offset(Dispenser32k.mc.player.getHorizontalFacing().getOpposite()), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            if (!(boolean)this.silent.getValue()) {
                Dispenser32k.mc.player.inventory.currentItem = this.shulkerSlot;
            }
            if (!(boolean)this.grabItem.getValue()) {
                this.disable();
                return;
            }
            this.stage = 3;
        }
    }
    
    private void placeBlock(final BlockPos pos, final EnumFacing side) {
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!this.isSneaking) {
            Dispenser32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Dispenser32k.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        Dispenser32k.mc.playerController.processRightClickBlock(Dispenser32k.mc.player, Dispenser32k.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        Dispenser32k.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
    
    static {
        df = new DecimalFormat("#.#");
    }
}
