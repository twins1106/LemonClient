//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.world.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.mojang.realmsclient.gui.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.client.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.block.*;
import net.minecraft.client.gui.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

@Module.Declaration(name = "Hopper32k", category = Category.Combat)
public class Hopper32k extends Module
{
    BooleanSetting rotate;
    BooleanSetting placeObiOnTop;
    BooleanSetting silent;
    BooleanSetting debugMessages;
    int oldslot;
    private boolean isSneaking;
    
    public Hopper32k() {
        this.rotate = this.registerBoolean("Rotate", false);
        this.placeObiOnTop = this.registerBoolean("Place Obi on Top", true);
        this.silent = this.registerBoolean("Silent", true);
        this.debugMessages = this.registerBoolean("Debug Messages", true);
    }
    
    protected void onEnable() {
        if (Hopper32k.mc.player == null || Hopper32k.mc.world == null || Hopper32k.mc.player.isDead) {
            this.disable();
            return;
        }
        this.oldslot = Hopper32k.mc.player.inventory.currentItem;
        int hopperslot = -1;
        int shulkerSlot = -1;
        this.isSneaking = false;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Hopper32k.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block == Blocks.HOPPER) {
                        hopperslot = i;
                    }
                    else if (BlockUtil.shulkerList.contains(block)) {
                        shulkerSlot = i;
                    }
                }
            }
        }
        if (hopperslot == -1) {
            if (this.debugMessages.getValue()) {
                MessageBus.sendClientPrefixMessage("Hopper missing, " + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getModuleColor() + "Hopper32k" + ChatFormatting.GRAY + " disabling.");
            }
            this.disable();
            return;
        }
        if (shulkerSlot == -1) {
            if (this.debugMessages.getValue()) {
                MessageBus.sendClientPrefixMessage("Shulker missing, " + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getModuleColor() + "Hopper32k" + ChatFormatting.GRAY + " disabling.");
            }
            this.disable();
            return;
        }
        final RayTraceResult lookingAt = Minecraft.getMinecraft().objectMouseOver;
        if (lookingAt != null && lookingAt.typeOfHit == RayTraceResult.Type.BLOCK) {
            final BlockPos placeTarget = Hopper32k.mc.objectMouseOver.getBlockPos().up();
            Hopper32k.mc.player.inventory.currentItem = hopperslot;
            this.placeBlock(new BlockPos((Vec3i)placeTarget));
            Hopper32k.mc.player.inventory.currentItem = shulkerSlot;
            this.placeBlock(new BlockPos((Vec3i)placeTarget.up()));
            if (this.silent.getValue()) {
                Hopper32k.mc.player.inventory.currentItem = this.oldslot;
            }
            if ((boolean)this.placeObiOnTop.getValue() && BurrowUtil.findHotbarBlock((Class)BlockObsidian.class) != -1) {
                Hopper32k.mc.player.inventory.currentItem = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
                this.placeBlock(new BlockPos((Vec3i)placeTarget.add(0, 2, 0)));
                if (this.silent.getValue()) {
                    Hopper32k.mc.player.inventory.currentItem = this.oldslot;
                }
                else {
                    Hopper32k.mc.player.inventory.currentItem = shulkerSlot;
                }
            }
            Hopper32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Hopper32k.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
            Hopper32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(placeTarget, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            if (this.debugMessages.getValue()) {
                MessageBus.sendClientPrefixMessage("Hopper32k: Succesfully placed 32k");
            }
            this.disable();
        }
        else {
            this.disable();
        }
    }
    
    public void onUpdate() {
        if (Hopper32k.mc.player == null || Hopper32k.mc.world == null || Hopper32k.mc.player.isDead) {
            this.disable();
            return;
        }
        if (Hopper32k.mc.currentScreen instanceof GuiHopper) {
            final GuiHopper gui = (GuiHopper)Hopper32k.mc.currentScreen;
            for (int slot = 32; slot <= 40; ++slot) {
                if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, gui.inventorySlots.getSlot(slot).getStack()) > 5) {
                    Hopper32k.mc.player.inventory.currentItem = slot - 32;
                    break;
                }
            }
            if (!(gui.inventorySlots.inventorySlots.get(0).getStack().getItem() instanceof ItemAir)) {
                int slot = Hopper32k.mc.player.inventory.currentItem;
                boolean pull = false;
                for (int i = 40; i >= 32; --i) {
                    if (gui.inventorySlots.getSlot(i).getStack().isEmpty()) {
                        slot = i;
                        pull = true;
                        break;
                    }
                }
                if (pull) {
                    Hopper32k.mc.playerController.windowClick(gui.inventorySlots.windowId, 0, 0, ClickType.PICKUP, (EntityPlayer)Hopper32k.mc.player);
                    Hopper32k.mc.playerController.windowClick(gui.inventorySlots.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)Hopper32k.mc.player);
                    this.disable();
                }
            }
        }
    }
    
    private void placeBlock(final BlockPos pos) {
        final BlockPos neighbour = pos.offset(EnumFacing.DOWN);
        final EnumFacing opposite = EnumFacing.DOWN.getOpposite();
        if (!this.isSneaking) {
            Hopper32k.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Hopper32k.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        if (this.rotate.getValue()) {
            BlockUtil.faceVectorPacketInstant2(hitVec);
        }
        Hopper32k.mc.playerController.processRightClickBlock(Hopper32k.mc.player, Hopper32k.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        Hopper32k.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
}
