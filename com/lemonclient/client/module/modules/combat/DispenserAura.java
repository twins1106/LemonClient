//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.network.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.client.module.modules.exploits.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

@Module.Declaration(name = "DispenserAura", category = Category.Combat)
public class DispenserAura extends Module
{
    DoubleSetting range;
    ModeSetting mode;
    BooleanSetting instantMine;
    BooleanSetting rotate;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    List<BlockPos> selfList;
    public int breakingSlot;
    public BlockPos breakingBlock;
    @EventHandler
    private final Listener<PacketEvent.Send> listener;
    
    public DispenserAura() {
        this.range = this.registerDouble("Range", 4.5, 0.0, 10.0);
        this.mode = this.registerMode("Mode", (List)Arrays.asList("Block", "Mine"), "Block");
        this.instantMine = this.registerBoolean("Packet Mine", true, () -> ((String)this.mode.getValue()).equals("Mine"));
        this.rotate = this.registerBoolean("Rotate", false);
        this.packet = this.registerBoolean("Packet Place", false);
        this.swing = this.registerBoolean("Swing", false);
        this.packetSwitch = this.registerBoolean("Packet Switch", false);
        this.check = this.registerBoolean("Switch Check", false);
        this.selfList = new ArrayList<BlockPos>();
        this.breakingSlot = 0;
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (DispenserAura.mc.world == null || DispenserAura.mc.player == null || DispenserAura.mc.player.isDead) {
                return;
            }
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                final CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
                final int slot = DispenserAura.mc.player.inventory.currentItem;
                if (slot > 8 || slot < 0) {
                    return;
                }
                final ItemStack stack = DispenserAura.mc.player.inventory.getStackInSlot(DispenserAura.mc.player.inventory.currentItem);
                if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block == Blocks.DISPENSER) {
                        this.selfList.add(packet.getPos().offset(packet.getDirection()));
                    }
                }
            }
        }, new Predicate[0]);
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || DispenserAura.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                DispenserAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                DispenserAura.mc.player.inventory.currentItem = slot;
            }
            DispenserAura.mc.playerController.updateController();
        }
    }
    
    public void fast() {
        if (!(DispenserAura.mc.currentScreen instanceof GuiContainer)) {
            TileEntityDispenser dispenser = null;
            for (final TileEntity t : DispenserAura.mc.world.loadedTileEntityList) {
                if (t instanceof TileEntityDispenser && !this.selfList.contains(t.getPos()) && DispenserAura.mc.player.getDistance(t.getPos().getX() + 0.5, t.getPos().getY() + 0.5, t.getPos().getZ() + 0.5) <= (double)this.range.getValue() + 1.0) {
                    dispenser = (TileEntityDispenser)t;
                    break;
                }
            }
            if (dispenser != null) {
                if (((String)this.mode.getValue()).equals("Block")) {
                    final BlockPos jamPos = dispenser.getPos().offset((EnumFacing)DispenserAura.mc.world.getBlockState(dispenser.getPos()).getValue((IProperty)PropertyDirection.create("facing")));
                    if (DispenserAura.mc.player.getDistance(jamPos.getX() + 0.5, jamPos.getY() + 0.5, jamPos.getZ() + 0.5) > (double)this.range.getValue()) {
                        return;
                    }
                    int i;
                    for (i = 0; i <= 8; ++i) {
                        final Item item = DispenserAura.mc.player.inventory.getStackInSlot(i).getItem();
                        if (item instanceof ItemBlock && !(((ItemBlock)item).getBlock() instanceof BlockShulkerBox) && ((ItemBlock)item).getBlock().getDefaultState().isFullCube()) {
                            break;
                        }
                    }
                    final int oldslot = DispenserAura.mc.player.inventory.currentItem;
                    this.switchTo(i);
                    BurrowUtil.placeBlock(jamPos, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
                    this.switchTo(oldslot);
                }
                else {
                    if (this.instantMine.getValue()) {
                        InstantMine.INSTANCE.breaker(dispenser.getPos());
                        return;
                    }
                    int pickaxeSlot = DispenserAura.mc.player.inventory.currentItem;
                    for (int i = 0; i < 8; ++i) {
                        if (DispenserAura.mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemPickaxe) {
                            pickaxeSlot = i;
                        }
                    }
                    if (this.breakingBlock != null) {
                        DispenserAura.mc.player.inventory.currentItem = ((DispenserAura.mc.world.getBlockState(this.breakingBlock).getBlock() == Blocks.AIR) ? this.breakingSlot : pickaxeSlot);
                        if (DispenserAura.mc.world.getBlockState(this.breakingBlock).getBlock() != Blocks.AIR && DispenserAura.mc.player.getPositionVector().distanceTo(new Vec3d((Vec3i)this.breakingBlock).add(0.5, 0.5, 0.5)) <= 4.5) {
                            DispenserAura.mc.playerController.onPlayerDamageBlock(this.breakingBlock, EnumFacing.UP);
                            DispenserAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                            return;
                        }
                        this.breakingBlock = null;
                    }
                    else {
                        this.breakingSlot = DispenserAura.mc.player.inventory.currentItem;
                        DispenserAura.mc.player.inventory.currentItem = pickaxeSlot;
                        DispenserAura.mc.playerController.onPlayerDamageBlock(dispenser.getPos(), EnumFacing.UP);
                        DispenserAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                        this.breakingBlock = dispenser.getPos();
                    }
                }
            }
        }
    }
}
