//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

@Module.Declaration(name = "HoleFill", category = Category.Combat)
public class HoleFill extends Module
{
    ModeSetting page;
    ModeSetting target;
    IntegerSetting tickPredict;
    BooleanSetting calculateYPredict;
    IntegerSetting startDecrease;
    IntegerSetting exponentStartDecrease;
    IntegerSetting decreaseY;
    IntegerSetting exponentDecreaseY;
    IntegerSetting increaseY;
    IntegerSetting exponentIncreaseY;
    BooleanSetting splitXZ;
    BooleanSetting manualOutHole;
    BooleanSetting aboveHoleManual;
    BooleanSetting stairPredict;
    IntegerSetting nStair;
    DoubleSetting speedActivationStair;
    ModeSetting time;
    BooleanSetting multi;
    ModeSetting mode;
    BooleanSetting upPlate;
    IntegerSetting placeDelay;
    IntegerSetting bpc;
    DoubleSetting range;
    DoubleSetting playerRange;
    BooleanSetting rotate;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting doubleHole;
    BooleanSetting fourHole;
    boolean trapdoor;
    int placed;
    int waited;
    
    public HoleFill() {
        this.page = this.registerMode("Page", (List)Arrays.asList("Target", "Place"), "Target");
        this.target = this.registerMode("Target", (List)Arrays.asList("Normal", "Predict", "Both"), "Predict", () -> ((String)this.page.getValue()).equals("Target"));
        this.tickPredict = this.registerInteger("Tick Predict", 8, 0, 30, () -> ((String)this.page.getValue()).equals("Target"));
        this.calculateYPredict = this.registerBoolean("Calculate Y Predict", true, () -> ((String)this.page.getValue()).equals("Target"));
        this.startDecrease = this.registerInteger("Start Decrease", 39, 0, 200, () -> (boolean)this.calculateYPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.exponentStartDecrease = this.registerInteger("Exponent Start", 2, 1, 5, () -> (boolean)this.calculateYPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.decreaseY = this.registerInteger("Decrease Y", 2, 1, 5, () -> (boolean)this.calculateYPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.exponentDecreaseY = this.registerInteger("Exponent Decrease Y", 1, 1, 3, () -> (boolean)this.calculateYPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.increaseY = this.registerInteger("Increase Y", 3, 1, 5, () -> (boolean)this.calculateYPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.exponentIncreaseY = this.registerInteger("Exponent Increase Y", 2, 1, 3, () -> (boolean)this.calculateYPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.splitXZ = this.registerBoolean("Split XZ", true, () -> ((String)this.page.getValue()).equals("Target"));
        this.manualOutHole = this.registerBoolean("Manual Out Hole", false, () -> ((String)this.page.getValue()).equals("Target"));
        this.aboveHoleManual = this.registerBoolean("Above Hole Manual", false, () -> (boolean)this.manualOutHole.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.stairPredict = this.registerBoolean("Stair Predict", false, () -> ((String)this.page.getValue()).equals("Target"));
        this.nStair = this.registerInteger("N Stair", 2, 1, 4, () -> (boolean)this.stairPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.speedActivationStair = this.registerDouble("Speed Activation Stair", 0.3, 0.0, 1.0, () -> (boolean)this.stairPredict.getValue() && ((String)this.page.getValue()).equals("Target"));
        this.time = this.registerMode("Time Mode", (List)Arrays.asList("Tick", "onUpdate", "Both", "Fast"), "Tick", () -> ((String)this.page.getValue()).equals("Place"));
        this.multi = this.registerBoolean("Multi Threading", true, () -> ((String)this.page.getValue()).equals("Place"));
        this.mode = this.registerMode("Type", (List)Arrays.asList("Obby", "Echest", "Both", "Web", "Slab", "Skull", "Trapdoor", "All"), "Obby", () -> ((String)this.page.getValue()).equals("Place"));
        this.upPlate = this.registerBoolean("Up Slab", true, () -> ((String)this.page.getValue()).equals("Place"));
        this.placeDelay = this.registerInteger("Delay", 2, 0, 10, () -> ((String)this.page.getValue()).equals("Place"));
        this.bpc = this.registerInteger("Block pre Tick", 6, 1, 20, () -> ((String)this.page.getValue()).equals("Place"));
        this.range = this.registerDouble("Range", 4.0, 0.0, 10.0, () -> ((String)this.page.getValue()).equals("Place"));
        this.playerRange = this.registerDouble("Player Range", 3.0, 0.0, 6.0, () -> ((String)this.page.getValue()).equals("Place"));
        this.rotate = this.registerBoolean("Rotate", false, () -> ((String)this.page.getValue()).equals("Place"));
        this.packet = this.registerBoolean("Packet Place", false, () -> ((String)this.page.getValue()).equals("Place"));
        this.swing = this.registerBoolean("Swing", false, () -> ((String)this.page.getValue()).equals("Place"));
        this.packetSwitch = this.registerBoolean("Packet Switch", true, () -> ((String)this.page.getValue()).equals("Place"));
        this.check = this.registerBoolean("Switch Check", true, () -> ((String)this.page.getValue()).equals("Place"));
        this.doubleHole = this.registerBoolean("Double Hole", false, () -> ((String)this.page.getValue()).equals("Place"));
        this.fourHole = this.registerBoolean("FourBlocks Hole", false, () -> ((String)this.page.getValue()).equals("Place"));
    }
    
    public void onUpdate() {
        if (((String)this.time.getValue()).equals("onUpdate") || ((String)this.time.getValue()).equals("Both")) {
            this.doHoleFill();
        }
    }
    
    public void onTick() {
        if (((String)this.time.getValue()).equals("Tick") || ((String)this.time.getValue()).equals("Both")) {
            this.doHoleFill();
        }
    }
    
    public void fast() {
        if (((String)this.time.getValue()).equals("Fast")) {
            this.doHoleFill();
        }
    }
    
    private void doHoleFill() {
        this.file();
        if (this.multi.getValue()) {
            MultiThreading.runAsync(this::file);
        }
    }
    
    private void file() {
        if (HoleFill.mc.world == null || HoleFill.mc.player == null || HoleFill.mc.player.isDead) {
            return;
        }
        if (this.waited++ < (int)this.placeDelay.getValue()) {
            return;
        }
        this.waited = 0;
        this.placed = 0;
        final List<BlockPos> holePos = new ArrayList<BlockPos>();
        final PredictUtil.PredictSettings settings = new PredictUtil.PredictSettings((int)this.tickPredict.getValue(), (boolean)this.calculateYPredict.getValue(), (int)this.startDecrease.getValue(), (int)this.exponentStartDecrease.getValue(), (int)this.decreaseY.getValue(), (int)this.exponentDecreaseY.getValue(), (int)this.increaseY.getValue(), (int)this.exponentIncreaseY.getValue(), (boolean)this.splitXZ.getValue(), 0, false, false, (boolean)this.manualOutHole.getValue(), (boolean)this.aboveHoleManual.getValue(), (boolean)this.stairPredict.getValue(), (int)this.nStair.getValue(), (double)this.speedActivationStair.getValue());
        final List<EntityPlayer> targets = new ArrayList<EntityPlayer>(HoleFill.mc.world.playerEntities);
        targets.removeIf(player -> EntityUtil.basicChecksEntity(player) || HoleFill.mc.player.getDistance((Entity)player) > (double)this.range.getValue() + (double)this.playerRange.getValue() || this.inHole(player));
        final List<EntityPlayer> listPlayer = new ArrayList<EntityPlayer>();
        if (!((String)this.target.getValue()).equals("Predict")) {
            listPlayer.addAll(targets);
        }
        if (!((String)this.target.getValue()).equals("Normal")) {
            for (final EntityPlayer player2 : targets) {
                final EntityPlayer entityPlayer = PredictUtil.predictPlayer(player2, settings);
                listPlayer.add(entityPlayer);
            }
        }
        for (final EntityPlayer target : listPlayer) {
            for (final BlockPos pos : EntityUtil.getSphere(new BlockPos(target.posX, target.posY, target.posZ), (Double)this.playerRange.getValue(), (Double)this.playerRange.getValue(), false, false, 0)) {
                final HoleUtil.HoleInfo holeInfo = HoleUtil.isHole(pos, false, true, false);
                final HoleUtil.HoleType holeType = holeInfo.getType();
                if (holeType != HoleUtil.HoleType.NONE) {
                    final AxisAlignedBB centreBlocks = holeInfo.getCentre();
                    if (centreBlocks == null) {
                        continue;
                    }
                    if (HoleFill.mc.player.getDistanceSq(pos) > (double)this.range.getValue() * (double)this.range.getValue()) {
                        return;
                    }
                    if ((boolean)this.fourHole.getValue() && holeType == HoleUtil.HoleType.FOUR) {
                        holePos.add(pos);
                    }
                    if ((boolean)this.doubleHole.getValue() && holeType == HoleUtil.HoleType.DOUBLE) {
                        holePos.add(pos);
                    }
                    else {
                        if (holeType != HoleUtil.HoleType.SINGLE) {
                            continue;
                        }
                        holePos.add(pos);
                    }
                }
            }
        }
        boolean found;
        final List<EntityPlayer> list;
        final Iterator<EntityPlayer> iterator4;
        EntityPlayer player3;
        holePos.removeIf(blockPos -> {
            if (!HoleFill.mc.world.isAirBlock(blockPos) || this.intersectsWithEntity(blockPos) || !HoleFill.mc.world.isAirBlock(blockPos.up()) || !HoleFill.mc.world.isAirBlock(blockPos.up(2))) {
                return true;
            }
            else {
                found = false;
                list.iterator();
                while (iterator4.hasNext()) {
                    player3 = iterator4.next();
                    if (player3.getDistance(blockPos.x + 0.5, (double)blockPos.y, blockPos.z + 0.5) < (double)this.playerRange.getValue() && player3.posY > blockPos.y) {
                        found = true;
                        break;
                    }
                }
                return !found;
            }
        });
        for (final BlockPos pos2 : holePos) {
            if (this.placed >= (int)this.bpc.getValue()) {
                break;
            }
            this.placeBlock(pos2);
        }
    }
    
    public boolean inHole(final EntityPlayer aimTarget) {
        final HoleUtil.HoleInfo holeInfo = HoleUtil.isHole(AntiBurrow.getEntityPos((Entity)aimTarget), false, true, false);
        final HoleUtil.HoleType holeType = holeInfo.getType();
        return holeType != HoleUtil.HoleType.NONE;
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : HoleFill.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || HoleFill.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                HoleFill.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                HoleFill.mc.player.inventory.currentItem = slot;
            }
            HoleFill.mc.playerController.updateController();
        }
    }
    
    private void placeBlock(final BlockPos pos) {
        if (this.intersectsWithEntity(pos) || !HoleFill.mc.world.isAirBlock(pos)) {
            return;
        }
        final int slot = this.findRightBlock();
        if (slot == -1) {
            return;
        }
        this.trapdoor = (slot == InventoryUtil.findFirstBlockSlot((Class)BlockTrapDoor.class, 0, 8) || ((boolean)this.upPlate.getValue() && slot == BurrowUtil.findHotbarBlock((Class)BlockSlab.class)));
        final EnumFacing side = this.trapdoor ? BurrowUtil.getTrapdoorFacing(pos) : BurrowUtil.getFirstFacing(pos);
        if (side == null) {
            return;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, this.trapdoor ? 0.8 : 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        if ((BlockUtil.blackList.contains(HoleFill.mc.world.getBlockState(neighbour).getBlock()) || BlockUtil.shulkerList.contains(HoleFill.mc.world.getBlockState(neighbour).getBlock())) && !HoleFill.mc.player.isSneaking()) {
            HoleFill.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)HoleFill.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            HoleFill.mc.player.setSneaking(true);
        }
        if (this.rotate.getValue()) {
            BurrowUtil.faceVector(hitVec, true);
        }
        final int oldslot = HoleFill.mc.player.inventory.currentItem;
        this.switchTo(slot);
        BurrowUtil.rightClickBlock(neighbour, hitVec, EnumHand.MAIN_HAND, opposite, (boolean)this.packet.getValue(), (boolean)this.swing.getValue());
        this.switchTo(oldslot);
        ++this.placed;
    }
    
    private int findRightBlock() {
        final String s = (String)this.mode.getValue();
        switch (s) {
            case "All": {
                int newHand = InventoryUtil.findFirstBlockSlot((Class)BlockTrapDoor.class, 0, 8);
                if (newHand == -1) {
                    newHand = InventoryUtil.findSkullSlot(false, false);
                }
                if (newHand == -1) {
                    newHand = InventoryUtil.findFirstBlockSlot((Class)BlockWeb.class, 0, 8);
                }
                if (newHand == -1) {
                    newHand = BurrowUtil.findHotbarBlock((Class)BlockSlab.class);
                }
                if (newHand == -1) {
                    newHand = InventoryUtil.findFirstBlockSlot((Class)BlockObsidian.class, 0, 8);
                }
                if (newHand == -1) {
                    newHand = InventoryUtil.findFirstBlockSlot((Class)BlockEnderChest.class, 0, 8);
                }
                return newHand;
            }
            case "Both": {
                final int newHand = InventoryUtil.findFirstBlockSlot((Class)BlockObsidian.class, 0, 8);
                if (newHand == -1) {
                    return InventoryUtil.findFirstBlockSlot((Class)BlockEnderChest.class, 0, 8);
                }
                return newHand;
            }
            case "Obby": {
                return InventoryUtil.findFirstBlockSlot((Class)BlockObsidian.class, 0, 8);
            }
            case "Echest": {
                return InventoryUtil.findFirstBlockSlot((Class)BlockEnderChest.class, 0, 8);
            }
            case "Web": {
                return InventoryUtil.findFirstBlockSlot((Class)BlockWeb.class, 0, 8);
            }
            case "Plate": {
                return InventoryUtil.findFirstBlockSlot((Class)BlockSlab.class, 0, 8);
            }
            case "Skull": {
                return InventoryUtil.findSkullSlot(false, false);
            }
            case "Trapdoor": {
                return InventoryUtil.findFirstBlockSlot((Class)BlockTrapDoor.class, 0, 8);
            }
            default: {
                return -1;
            }
        }
    }
}
