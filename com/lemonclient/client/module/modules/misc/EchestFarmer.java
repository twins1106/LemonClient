//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.*;
import net.minecraft.item.*;
import java.util.function.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import com.lemonclient.client.module.modules.combat.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import com.lemonclient.client.module.modules.exploits.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import com.lemonclient.api.event.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.client.manager.managers.*;
import net.minecraft.util.math.*;

@Module.Declaration(name = "EchestFarmer", category = Category.Misc)
public class EchestFarmer extends Module
{
    ModeSetting breakBlock;
    ModeSetting HowplaceBlock;
    IntegerSetting stackCount;
    IntegerSetting tickDelay;
    BooleanSetting rotate;
    BooleanSetting forceRotation;
    private int delayTimeTicks;
    private int echestToMine;
    private int slotObby;
    private int slotPick;
    BlockPos blockAim;
    private boolean looking;
    private boolean noSpace;
    private boolean materialsNeeded;
    private boolean prevBreak;
    private ArrayList<EnumFacing> sides;
    Vec3d lastHitVec;
    @EventHandler
    private final Listener<OnUpdateWalkingPlayerEvent> onUpdateWalkingPlayerEventListener;
    
    public EchestFarmer() {
        this.breakBlock = this.registerMode("Break Block", (List)Arrays.asList("Normal", "Packet", "PacketMine"), "Packet");
        this.HowplaceBlock = this.registerMode("Place Block", (List)Arrays.asList("Near", "Looking"), "Looking");
        this.stackCount = this.registerInteger("N^Stack", 0, 0, 64);
        this.tickDelay = this.registerInteger("Tick Delay", 5, 0, 10);
        this.rotate = this.registerBoolean("Rotate", false);
        this.forceRotation = this.registerBoolean("ForceRotation", false);
        this.sides = new ArrayList<EnumFacing>();
        this.onUpdateWalkingPlayerEventListener = (Listener<OnUpdateWalkingPlayerEvent>)new Listener(event -> {
            if (event.getPhase() != Phase.PRE || !(boolean)this.rotate.getValue() || this.lastHitVec == null || !(boolean)this.forceRotation.getValue()) {
                return;
            }
            final Vec2f rotation = RotationUtil.getRotationTo(this.lastHitVec);
            final PlayerPacket packet = new PlayerPacket((Module)this, rotation);
            PlayerPacketManager.INSTANCE.addPacket(packet);
        }, new Predicate[0]);
    }
    
    public void onEnable() {
        SpoofRotationUtil.ROTATION_UTIL.onEnable();
        this.initValues();
    }
    
    private void initValues() {
        final boolean prevBreak = false;
        this.looking = prevBreak;
        this.noSpace = prevBreak;
        this.prevBreak = prevBreak;
        this.delayTimeTicks = 0;
        this.materialsNeeded = true;
        final int obbyCount = EchestFarmer.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() instanceof ItemBlock && ((ItemBlock)itemStack.getItem()).getBlock() == Blocks.OBSIDIAN).mapToInt(ItemStack::getCount).sum();
        final int stackWanted = ((int)this.stackCount.getValue() == 0) ? -1 : ((int)this.stackCount.getValue() * 64);
        this.echestToMine = (stackWanted - obbyCount) / 8;
        if (((String)this.HowplaceBlock.getValue()).equals("Looking")) {
            try {
                this.blockAim = EchestFarmer.mc.objectMouseOver.getBlockPos();
                final BlockPos blockAim = this.blockAim;
                ++blockAim.y;
            }
            catch (NullPointerException e) {
                this.disable();
                return;
            }
            if (BlockUtil.getPlaceableSide(this.blockAim) == null) {
                this.looking = false;
                return;
            }
            this.sides.clear();
            this.sides.add(EnumFacing.getDirectionFromEntityLiving(this.blockAim, (EntityLivingBase)EchestFarmer.mc.player));
        }
        else {
            for (final int[] sur : new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } }) {
                for (final int h : new int[] { 1, 0 }) {
                    if (BlockUtil.getBlock(EchestFarmer.mc.player.posX + sur[0], EchestFarmer.mc.player.posY + h, EchestFarmer.mc.player.posZ + sur[1]) instanceof BlockAir && BlockUtil.getPlaceableSide(new BlockPos(EchestFarmer.mc.player.posX + sur[0], EchestFarmer.mc.player.posY + h, EchestFarmer.mc.player.posZ + sur[1])) != null && !PistonCrystal.someoneInCoords(EchestFarmer.mc.player.posX + sur[0], EchestFarmer.mc.player.posY + h, EchestFarmer.mc.player.posZ + sur[1])) {
                        this.blockAim = new BlockPos(EchestFarmer.mc.player.posX + sur[0], EchestFarmer.mc.player.posY + h, EchestFarmer.mc.player.posZ + sur[1]);
                        break;
                    }
                }
                if (this.blockAim != null) {
                    break;
                }
            }
            if (this.blockAim == null) {
                this.noSpace = false;
                return;
            }
        }
        if (this.isToggleMsg()) {
            if ((int)this.stackCount.getValue() == 0) {
                PistonCrystal.printDebug("Starting farming obby", Boolean.valueOf(false));
            }
            else {
                PistonCrystal.printDebug(String.format("N^obby: %d, N^stack: %d, echest needed: %d", obbyCount, stackWanted, this.echestToMine), Boolean.valueOf(false));
            }
        }
        this.slotPick = InventoryUtil.findFirstItemSlot((Class)Items.DIAMOND_PICKAXE.getClass(), 0, 9);
        this.slotObby = InventoryUtil.findFirstBlockSlot((Class)Blocks.ENDER_CHEST.getClass(), 0, 9);
        if (this.slotObby == -1 || this.slotPick == -1) {
            this.materialsNeeded = false;
        }
    }
    
    public void onDisable() {
        String output = "";
        if (!this.materialsNeeded) {
            output = "No materials detected... " + ((this.slotObby == -1) ? "No Echest detected " : "") + ((this.slotPick == -1) ? "No Pick detected" : "");
        }
        else if (this.noSpace) {
            output = "Not enough space";
        }
        else if (this.looking) {
            output = "Impossible to place";
        }
        if (!output.equals("")) {
            PistonCrystal.printDebug(output, Boolean.valueOf(true));
        }
        else if (this.echestToMine == 0) {
            PistonCrystal.printDebug("Mined every echest", Boolean.valueOf(false));
        }
    }
    
    public void onUpdate() {
        if (EchestFarmer.mc.player == null) {
            this.disable();
            return;
        }
        if (this.delayTimeTicks < (int)this.tickDelay.getValue()) {
            ++this.delayTimeTicks;
            return;
        }
        this.delayTimeTicks = 0;
        if (this.blockAim == null || !this.materialsNeeded || this.slotPick == -1 || this.looking || this.noSpace) {
            this.disable();
            return;
        }
        if (BlockUtil.getBlock(this.blockAim) instanceof BlockAir) {
            if (this.prevBreak && --this.echestToMine == 0) {
                this.disable();
                return;
            }
            this.placeBlock(this.blockAim);
            this.prevBreak = false;
        }
        else {
            if (EchestFarmer.mc.player.inventory.currentItem != this.slotPick) {
                EchestFarmer.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.slotPick));
                EchestFarmer.mc.player.inventory.currentItem = this.slotPick;
                EchestFarmer.mc.playerController.updateController();
            }
            final EnumFacing sideBreak = BlockUtil.getPlaceableSide(this.blockAim);
            if (sideBreak != null) {
                final String s = (String)this.breakBlock.getValue();
                switch (s) {
                    case "PacketMine": {
                        InstantMine.INSTANCE.breaker(this.blockAim);
                        break;
                    }
                    case "Packet": {
                        if (!this.prevBreak) {
                            EchestFarmer.mc.player.swingArm(EnumHand.MAIN_HAND);
                            EchestFarmer.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.blockAim, sideBreak));
                            EchestFarmer.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.blockAim, sideBreak));
                            this.prevBreak = true;
                            break;
                        }
                        break;
                    }
                    case "Normal": {
                        EchestFarmer.mc.player.swingArm(EnumHand.MAIN_HAND);
                        EchestFarmer.mc.playerController.onPlayerDamageBlock(this.blockAim, sideBreak);
                        this.prevBreak = true;
                        break;
                    }
                }
            }
        }
    }
    
    private void placeBlock(final BlockPos pos) {
        EnumHand handSwing;
        if (this.slotObby == 11) {
            handSwing = EnumHand.OFF_HAND;
        }
        else {
            handSwing = EnumHand.MAIN_HAND;
            if (EchestFarmer.mc.player.inventory.currentItem != this.slotObby) {
                EchestFarmer.mc.player.inventory.currentItem = this.slotObby;
                EchestFarmer.mc.playerController.updateController();
            }
        }
        if ((EchestFarmer.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock && ((ItemBlock)EchestFarmer.mc.player.getHeldItemMainhand().getItem()).getBlock() != Blocks.ENDER_CHEST) || (EchestFarmer.mc.player.getHeldItemOffhand().getItem() instanceof ItemBlock && ((ItemBlock)EchestFarmer.mc.player.getHeldItemOffhand().getItem()).getBlock() != Blocks.ENDER_CHEST)) {
            return;
        }
        if (this.forceRotation.getValue()) {
            final EnumFacing side = BlockUtil.getPlaceableSide(this.blockAim);
            if (side == null) {
                return;
            }
            final BlockPos neighbour = this.blockAim.offset(side);
            final EnumFacing opposite = side.getOpposite();
            if (!BlockUtil.canBeClicked(neighbour)) {
                return;
            }
            this.lastHitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        }
        PlacementUtil.place(pos, handSwing, (boolean)this.rotate.getValue(), true);
    }
}
