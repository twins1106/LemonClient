//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import com.lemonclient.client.module.modules.combat.*;
import net.minecraft.util.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;

@Module.Declaration(name = "AutoSpawner", category = Category.Misc)
public class AutoSpawner extends Module
{
    ModeSetting useMode;
    BooleanSetting party;
    ModeSetting entityMode;
    BooleanSetting nametagWithers;
    DoubleSetting placeRange;
    IntegerSetting delay;
    BooleanSetting rotate;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting swing;
    private static boolean isSneaking;
    private BlockPos placeTarget;
    private boolean rotationPlaceableX;
    private boolean rotationPlaceableZ;
    private int bodySlot;
    private int headSlot;
    private int buildStage;
    private int delayStep;
    
    public AutoSpawner() {
        this.useMode = this.registerMode("Use Mode", (List)Arrays.asList("Single", "Spam"), "Spam");
        this.party = this.registerBoolean("Wither Party", false);
        this.entityMode = this.registerMode("Entity Mode", (List)Arrays.asList("Snow", "Iron", "Wither"), "Wither");
        this.nametagWithers = this.registerBoolean("Nametag", true);
        this.placeRange = this.registerDouble("Place Range", 3.5, 1.0, 10.0);
        this.delay = this.registerInteger("Delay", 20, 0, 100);
        this.rotate = this.registerBoolean("Rotate", true);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.swing = this.registerBoolean("Swing", true);
    }
    
    private void placeBlock(final BlockPos pos, final boolean rotate) {
        final EnumFacing side = getPlaceableSide(pos);
        if (side == null) {
            return;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = AutoSpawner.mc.world.getBlockState(neighbour).getBlock();
        if (!AutoSpawner.isSneaking && (BlockUtil.blackList.contains(neighbourBlock) || BlockUtil.shulkerList.contains(neighbourBlock))) {
            AutoSpawner.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoSpawner.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            AutoSpawner.isSneaking = true;
        }
        if (rotate) {
            AutoHopper32k.faceVectorPacketInstant(hitVec);
        }
        AutoSpawner.mc.playerController.processRightClickBlock(AutoSpawner.mc.player, AutoSpawner.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        if (this.swing.getValue()) {
            AutoSpawner.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        AutoSpawner.mc.rightClickDelayTimer = 4;
    }
    
    private void useNameTag() {
        final int originalSlot = AutoSpawner.mc.player.inventory.currentItem;
        for (final Entity w : AutoSpawner.mc.world.getLoadedEntityList()) {
            if (w instanceof EntityWither && w.getDisplayName().getUnformattedText().equalsIgnoreCase("Wither")) {
                final EntityWither wither = (EntityWither)w;
                if (AutoSpawner.mc.player.getDistance((Entity)wither) > (double)this.placeRange.getValue()) {
                    continue;
                }
                this.selectNameTags();
                AutoSpawner.mc.playerController.interactWithEntity((EntityPlayer)AutoSpawner.mc.player, (Entity)wither, EnumHand.MAIN_HAND);
            }
        }
        this.switchTo(originalSlot);
    }
    
    private void selectNameTags() {
        int tagSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoSpawner.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (!(stack.getItem() instanceof ItemBlock)) {
                    final Item tag = stack.getItem();
                    if (tag instanceof ItemNameTag) {
                        tagSlot = i;
                    }
                }
            }
        }
        if (tagSlot == -1) {
            return;
        }
        this.switchTo(tagSlot);
    }
    
    private static EnumFacing getPlaceableSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (AutoSpawner.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(AutoSpawner.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = AutoSpawner.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable() && !(blockState.getBlock() instanceof BlockTallGrass) && !(blockState.getBlock() instanceof BlockDeadBush)) {
                    return side;
                }
            }
        }
        return null;
    }
    
    protected void onEnable() {
        this.buildStage = 1;
        this.delayStep = 1;
    }
    
    private boolean checkBlocksInHotbar() {
        this.headSlot = -1;
        this.bodySlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoSpawner.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (((String)this.entityMode.getValue()).equals("Wither")) {
                    if (stack.getItem() == Items.SKULL && stack.getItemDamage() == 1) {
                        if (AutoSpawner.mc.player.inventory.getStackInSlot(i).stackSize >= 3) {
                            this.headSlot = i;
                        }
                        continue;
                    }
                    else {
                        if (!(stack.getItem() instanceof ItemBlock)) {
                            continue;
                        }
                        final Block block = ((ItemBlock)stack.getItem()).getBlock();
                        if (block instanceof BlockSoulSand && AutoSpawner.mc.player.inventory.getStackInSlot(i).stackSize >= 4) {
                            this.bodySlot = i;
                        }
                    }
                }
                if (((String)this.entityMode.getValue()).equals("Iron")) {
                    if (!(stack.getItem() instanceof ItemBlock)) {
                        continue;
                    }
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if ((block == Blocks.LIT_PUMPKIN || block == Blocks.PUMPKIN) && AutoSpawner.mc.player.inventory.getStackInSlot(i).stackSize >= 1) {
                        this.headSlot = i;
                    }
                    if (block == Blocks.IRON_BLOCK && AutoSpawner.mc.player.inventory.getStackInSlot(i).stackSize >= 4) {
                        this.bodySlot = i;
                    }
                }
                if (((String)this.entityMode.getValue()).equals("Snow")) {
                    if (stack.getItem() instanceof ItemBlock) {
                        final Block block = ((ItemBlock)stack.getItem()).getBlock();
                        if ((block == Blocks.LIT_PUMPKIN || block == Blocks.PUMPKIN) && AutoSpawner.mc.player.inventory.getStackInSlot(i).stackSize >= 1) {
                            this.headSlot = i;
                        }
                        if (block == Blocks.SNOW && AutoSpawner.mc.player.inventory.getStackInSlot(i).stackSize >= 2) {
                            this.bodySlot = i;
                        }
                    }
                }
            }
        }
        return this.bodySlot != -1 && this.headSlot != -1;
    }
    
    private boolean testStructure() {
        if (((String)this.entityMode.getValue()).equals("Wither")) {
            return this.testWitherStructure();
        }
        if (((String)this.entityMode.getValue()).equals("Iron")) {
            return this.testIronGolemStructure();
        }
        return ((String)this.entityMode.getValue()).equals("Snow") && this.testSnowGolemStructure();
    }
    
    private boolean testWitherStructure() {
        boolean noRotationPlaceable = true;
        this.rotationPlaceableX = true;
        this.rotationPlaceableZ = true;
        boolean isShitGrass = false;
        if (AutoSpawner.mc.world.getBlockState(this.placeTarget) == null) {
            return false;
        }
        final Block block = AutoSpawner.mc.world.getBlockState(this.placeTarget).getBlock();
        if (block instanceof BlockTallGrass || block instanceof BlockDeadBush) {
            isShitGrass = true;
        }
        if (getPlaceableSide(this.placeTarget.up()) == null) {
            return false;
        }
        for (final BlockPos pos : BodyParts.bodyBase) {
            if (this.placingIsBlocked(this.placeTarget.add((Vec3i)pos))) {
                noRotationPlaceable = false;
            }
        }
        for (final BlockPos pos : BodyParts.ArmsX) {
            if (this.placingIsBlocked(this.placeTarget.add((Vec3i)pos)) || this.placingIsBlocked(this.placeTarget.add((Vec3i)pos.down()))) {
                this.rotationPlaceableX = false;
            }
        }
        for (final BlockPos pos : BodyParts.ArmsZ) {
            if (this.placingIsBlocked(this.placeTarget.add((Vec3i)pos)) || this.placingIsBlocked(this.placeTarget.add((Vec3i)pos.down()))) {
                this.rotationPlaceableZ = false;
            }
        }
        for (final BlockPos pos : BodyParts.headsX) {
            if (this.placingIsBlocked(this.placeTarget.add((Vec3i)pos))) {
                this.rotationPlaceableX = false;
            }
        }
        for (final BlockPos pos : BodyParts.headsZ) {
            if (this.placingIsBlocked(this.placeTarget.add((Vec3i)pos))) {
                this.rotationPlaceableZ = false;
            }
        }
        return !isShitGrass && noRotationPlaceable && (this.rotationPlaceableX || this.rotationPlaceableZ);
    }
    
    private boolean testIronGolemStructure() {
        boolean noRotationPlaceable = true;
        this.rotationPlaceableX = true;
        this.rotationPlaceableZ = true;
        boolean isShitGrass = false;
        if (AutoSpawner.mc.world.getBlockState(this.placeTarget) == null) {
            return false;
        }
        final Block block = AutoSpawner.mc.world.getBlockState(this.placeTarget).getBlock();
        if (block instanceof BlockTallGrass || block instanceof BlockDeadBush) {
            isShitGrass = true;
        }
        if (getPlaceableSide(this.placeTarget.up()) == null) {
            return false;
        }
        for (final BlockPos pos : BodyParts.bodyBase) {
            if (this.placingIsBlocked(this.placeTarget.add((Vec3i)pos))) {
                noRotationPlaceable = false;
            }
        }
        for (final BlockPos pos : BodyParts.ArmsX) {
            if (this.placingIsBlocked(this.placeTarget.add((Vec3i)pos)) || this.placingIsBlocked(this.placeTarget.add((Vec3i)pos.down()))) {
                this.rotationPlaceableX = false;
            }
        }
        for (final BlockPos pos : BodyParts.ArmsZ) {
            if (this.placingIsBlocked(this.placeTarget.add((Vec3i)pos)) || this.placingIsBlocked(this.placeTarget.add((Vec3i)pos.down()))) {
                this.rotationPlaceableZ = false;
            }
        }
        for (final BlockPos pos : BodyParts.head) {
            if (this.placingIsBlocked(this.placeTarget.add((Vec3i)pos))) {
                noRotationPlaceable = false;
            }
        }
        return !isShitGrass && noRotationPlaceable && (this.rotationPlaceableX || this.rotationPlaceableZ);
    }
    
    private boolean testSnowGolemStructure() {
        boolean noRotationPlaceable = true;
        boolean isShitGrass = false;
        if (AutoSpawner.mc.world.getBlockState(this.placeTarget) == null) {
            return false;
        }
        final Block block = AutoSpawner.mc.world.getBlockState(this.placeTarget).getBlock();
        if (block instanceof BlockTallGrass || block instanceof BlockDeadBush) {
            isShitGrass = true;
        }
        if (getPlaceableSide(this.placeTarget.up()) == null) {
            return false;
        }
        for (final BlockPos pos : BodyParts.bodyBase) {
            if (this.placingIsBlocked(this.placeTarget.add((Vec3i)pos))) {
                noRotationPlaceable = false;
            }
        }
        for (final BlockPos pos : BodyParts.head) {
            if (this.placingIsBlocked(this.placeTarget.add((Vec3i)pos))) {
                noRotationPlaceable = false;
            }
        }
        return !isShitGrass && noRotationPlaceable;
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || AutoSpawner.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                AutoSpawner.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                AutoSpawner.mc.player.inventory.currentItem = slot;
            }
            AutoSpawner.mc.playerController.updateController();
        }
    }
    
    public void onUpdate() {
        if (AutoSpawner.mc.world == null || AutoSpawner.mc.player == null || AutoSpawner.mc.player.isDead) {
            return;
        }
        if ((boolean)this.nametagWithers.getValue() && ((boolean)this.party.getValue() || (!(boolean)this.party.getValue() && ((String)this.entityMode.getValue()).equals("Wither")))) {
            this.useNameTag();
        }
        if (this.buildStage == 1) {
            AutoSpawner.isSneaking = false;
            this.rotationPlaceableX = false;
            this.rotationPlaceableZ = false;
            if (this.party.getValue()) {
                this.entityMode.setValue((Object)"Wither");
            }
            if (!this.checkBlocksInHotbar()) {
                if (((String)this.useMode.getValue()).equals("Single")) {
                    this.disable();
                }
                return;
            }
            final List<BlockPos> blockPosList = (List<BlockPos>)EntityUtil.getSphere(AutoSpawner.mc.player.getPosition().down(), (Double)this.placeRange.getValue(), (Double)this.placeRange.getValue(), false, true, 0);
            boolean noPositionInArea = true;
            for (final BlockPos pos : blockPosList) {
                this.placeTarget = pos.down();
                if (this.testStructure()) {
                    noPositionInArea = false;
                    break;
                }
            }
            if (noPositionInArea) {
                if (((String)this.useMode.getValue()).equals("Single")) {
                    this.disable();
                }
                return;
            }
            final int oldslot = AutoSpawner.mc.player.inventory.currentItem;
            this.switchTo(this.bodySlot);
            for (final BlockPos pos2 : BodyParts.bodyBase) {
                this.placeBlock(this.placeTarget.add((Vec3i)pos2), (boolean)this.rotate.getValue());
            }
            if (((String)this.entityMode.getValue()).equals("Wither") || ((String)this.entityMode.getValue()).equals("Iron")) {
                if (this.rotationPlaceableX) {
                    for (final BlockPos pos2 : BodyParts.ArmsX) {
                        this.placeBlock(this.placeTarget.add((Vec3i)pos2), (boolean)this.rotate.getValue());
                    }
                }
                else if (this.rotationPlaceableZ) {
                    for (final BlockPos pos2 : BodyParts.ArmsZ) {
                        this.placeBlock(this.placeTarget.add((Vec3i)pos2), (boolean)this.rotate.getValue());
                    }
                }
            }
            this.switchTo(oldslot);
            this.buildStage = 2;
        }
        else if (this.buildStage == 2) {
            final int oldslot2 = AutoSpawner.mc.player.inventory.currentItem;
            this.switchTo(this.headSlot);
            if (((String)this.entityMode.getValue()).equals("Wither")) {
                if (this.rotationPlaceableX) {
                    for (final BlockPos pos3 : BodyParts.headsX) {
                        this.placeBlock(this.placeTarget.add((Vec3i)pos3), (boolean)this.rotate.getValue());
                    }
                }
                else if (this.rotationPlaceableZ) {
                    for (final BlockPos pos3 : BodyParts.headsZ) {
                        this.placeBlock(this.placeTarget.add((Vec3i)pos3), (boolean)this.rotate.getValue());
                    }
                }
            }
            if (((String)this.entityMode.getValue()).equals("Iron") || ((String)this.entityMode.getValue()).equals("Snow")) {
                for (final BlockPos pos3 : BodyParts.head) {
                    this.placeBlock(this.placeTarget.add((Vec3i)pos3), (boolean)this.rotate.getValue());
                }
            }
            if (AutoSpawner.isSneaking) {
                AutoSpawner.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoSpawner.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                AutoSpawner.isSneaking = false;
            }
            if (((String)this.useMode.getValue()).equals("Single")) {
                this.disable();
            }
            this.switchTo(oldslot2);
            this.buildStage = 3;
        }
        else if (this.buildStage == 3) {
            if (this.delayStep < (int)this.delay.getValue()) {
                ++this.delayStep;
            }
            else {
                this.delayStep = 1;
                this.buildStage = 1;
            }
        }
    }
    
    private boolean placingIsBlocked(final BlockPos pos) {
        final Block block = AutoSpawner.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir)) {
            return true;
        }
        for (final Entity entity : AutoSpawner.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                return true;
            }
        }
        return false;
    }
    
    private static class BodyParts
    {
        private static final BlockPos[] bodyBase;
        private static final BlockPos[] ArmsX;
        private static final BlockPos[] ArmsZ;
        private static final BlockPos[] headsX;
        private static final BlockPos[] headsZ;
        private static final BlockPos[] head;
        
        static {
            bodyBase = new BlockPos[] { new BlockPos(0, 1, 0), new BlockPos(0, 2, 0) };
            ArmsX = new BlockPos[] { new BlockPos(-1, 2, 0), new BlockPos(1, 2, 0) };
            ArmsZ = new BlockPos[] { new BlockPos(0, 2, -1), new BlockPos(0, 2, 1) };
            headsX = new BlockPos[] { new BlockPos(0, 3, 0), new BlockPos(-1, 3, 0), new BlockPos(1, 3, 0) };
            headsZ = new BlockPos[] { new BlockPos(0, 3, 0), new BlockPos(0, 3, -1), new BlockPos(0, 3, 1) };
            head = new BlockPos[] { new BlockPos(0, 3, 0) };
        }
    }
}
