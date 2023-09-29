//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import com.lemonclient.api.util.player.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;

@Module.Declaration(name = "Surround", category = Category.Combat)
public class Surround extends Module
{
    ModeSetting time;
    BooleanSetting once;
    BooleanSetting echest;
    BooleanSetting floor;
    IntegerSetting delay;
    IntegerSetting range;
    IntegerSetting bpt;
    BooleanSetting rotate;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting hit;
    BooleanSetting packetBreak;
    BooleanSetting antiWeakness;
    BooleanSetting packetswitch;
    List<EntityEnderCrystal> crystals;
    List<BlockPos> surround;
    List<BlockPos> hasEntity;
    List<BlockPos> posList;
    List<BlockPos> floorPos;
    int placed;
    int waited;
    int slot;
    double y;
    BlockPos[] sides;
    BlockPos[] neighbour;
    
    public Surround() {
        this.time = this.registerMode("Time Mode", (List)Arrays.asList("Tick", "onUpdate"), "Tick");
        this.once = this.registerBoolean("Once", true);
        this.echest = this.registerBoolean("Ender Chest", true);
        this.floor = this.registerBoolean("Floor", true);
        this.delay = this.registerInteger("Delay", 0, 0, 20);
        this.range = this.registerInteger("Range", 5, 0, 10);
        this.bpt = this.registerInteger("BlocksPerTick", 4, 0, 20);
        this.rotate = this.registerBoolean("Rotate", false);
        this.packet = this.registerBoolean("Packet Place", false);
        this.swing = this.registerBoolean("Swing", false);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.hit = this.registerBoolean("Hit", true);
        this.packetBreak = this.registerBoolean("Packet Break", false);
        this.antiWeakness = this.registerBoolean("Anti Weakness", true);
        this.packetswitch = this.registerBoolean("Silent Switch", true);
        this.crystals = new ArrayList<EntityEnderCrystal>();
        this.surround = new ArrayList<BlockPos>();
        this.hasEntity = new ArrayList<BlockPos>();
        this.posList = new ArrayList<BlockPos>();
        this.floorPos = new ArrayList<BlockPos>();
        this.sides = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) };
        this.neighbour = new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1), new BlockPos(0, 1, 0) };
    }
    
    public void onEnable() {
        if (Surround.mc.world == null || Surround.mc.player == null || Surround.mc.player.isDead) {
            this.disable();
            return;
        }
        this.y = (int)Surround.mc.player.posY;
    }
    
    public void onUpdate() {
        if (((String)this.time.getValue()).equals("onUpdate")) {
            this.doSurround();
        }
    }
    
    public void onTick() {
        if (((String)this.time.getValue()).equals("Tick")) {
            this.doSurround();
        }
    }
    
    private void doSurround() {
        if (Surround.mc.world == null || Surround.mc.player == null || Surround.mc.player.isDead || this.y + 1.1 < Surround.mc.player.posY || this.y - 1.0 > Surround.mc.player.posY || (this.y + 1.0 < Surround.mc.player.posY && MotionUtil.Moving((EntityLivingBase)Surround.mc.player))) {
            this.disable();
            return;
        }
        this.slot = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
        if (this.slot == -1 && (boolean)this.echest.getValue()) {
            this.slot = BurrowUtil.findHotbarBlock((Class)BlockEnderChest.class);
        }
        if (this.slot == -1) {
            return;
        }
        if (this.waited++ < (int)this.delay.getValue()) {
            return;
        }
        final int n = 0;
        this.placed = n;
        this.waited = n;
        this.calc();
        if ((boolean)this.hit.getValue() && !this.crystals.isEmpty()) {
            Entity crystal = null;
            final Iterator<EntityEnderCrystal> iterator = this.crystals.iterator();
            if (iterator.hasNext()) {
                final EntityEnderCrystal enderCrystal = (EntityEnderCrystal)(crystal = (Entity)iterator.next());
            }
            this.breakCrystal(crystal);
        }
        if (this.floor.getValue()) {
            for (final BlockPos pos : this.floorPos) {
                this.surround.add(pos.down());
            }
        }
        if (this.surround.isEmpty()) {
            return;
        }
        for (final BlockPos pos : this.surround) {
            if (this.placed >= (int)this.bpt.getValue()) {
                break;
            }
            if (!Surround.mc.world.isAirBlock(pos) && Surround.mc.world.getBlockState(pos).getBlock() != Blocks.FIRE && !(Surround.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid)) {
                continue;
            }
            EnumFacing face = BurrowUtil.getFirstFacing(pos);
            if (face == null) {
                boolean canPlace = false;
                for (final BlockPos side : this.neighbour) {
                    final BlockPos blockPos = pos.add((Vec3i)side);
                    if (!this.intersectsWithEntity(blockPos)) {
                        if (BlockUtil.hasNeighbour(blockPos)) {
                            this.placeBlock(blockPos, BurrowUtil.getFirstFacing(blockPos));
                            canPlace = true;
                            break;
                        }
                    }
                }
                if (!canPlace) {
                    continue;
                }
                face = BurrowUtil.getFirstFacing(pos);
            }
            this.placeBlock(pos, face);
        }
        if (this.once.getValue()) {
            this.disable();
        }
    }
    
    private void breakCrystal(final Entity crystal) {
        if (crystal == null) {
            return;
        }
        final int oldSlot = Surround.mc.player.inventory.currentItem;
        if ((boolean)this.antiWeakness.getValue() && Surround.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = Surround.mc.player.inventory.getStackInSlot(i);
                if (stack != ItemStack.EMPTY) {
                    if (stack.getItem() instanceof ItemSword) {
                        newSlot = i;
                        break;
                    }
                    if (stack.getItem() instanceof ItemTool) {
                        newSlot = i;
                        break;
                    }
                }
            }
            if (newSlot != -1) {
                this.switchTo(newSlot);
            }
        }
        if (!(boolean)this.packetBreak.getValue()) {
            CrystalUtil.breakCrystal(crystal, (boolean)this.swing.getValue());
        }
        else {
            CrystalUtil.breakCrystalPacket(crystal, (boolean)this.swing.getValue());
        }
        if (this.packetswitch.getValue()) {
            this.switchTo(oldSlot);
        }
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || Surround.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                Surround.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                Surround.mc.player.inventory.currentItem = slot;
            }
            Surround.mc.playerController.updateController();
        }
    }
    
    private void placeBlock(final BlockPos pos, final EnumFacing side) {
        if (this.placed >= (int)this.bpt.getValue()) {
            return;
        }
        if (this.intersectsWithEntity(pos)) {
            return;
        }
        if (side == null) {
            return;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        if ((BlockUtil.blackList.contains(Surround.mc.world.getBlockState(neighbour).getBlock()) || BlockUtil.shulkerList.contains(Surround.mc.world.getBlockState(neighbour).getBlock())) && !Surround.mc.player.isSneaking()) {
            Surround.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Surround.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            Surround.mc.player.setSneaking(true);
        }
        if (this.rotate.getValue()) {
            BurrowUtil.faceVector(hitVec, true);
        }
        final int oldslot = Surround.mc.player.inventory.currentItem;
        this.switchTo(this.slot);
        BurrowUtil.rightClickBlock(neighbour, hitVec, EnumHand.MAIN_HAND, opposite, (boolean)this.packet.getValue(), (boolean)this.swing.getValue());
        this.switchTo(oldslot);
        ++this.placed;
    }
    
    private void calc() {
        this.crystals = new ArrayList<EntityEnderCrystal>();
        this.surround = new ArrayList<BlockPos>();
        this.hasEntity = new ArrayList<BlockPos>();
        this.posList = new ArrayList<BlockPos>();
        this.floorPos = new ArrayList<BlockPos>();
        BlockPos playerPos = PlayerUtil.getPlayerPos();
        playerPos = new BlockPos((double)playerPos.x, playerPos.y + 0.55, (double)playerPos.z);
        this.addPos(playerPos);
        if (!this.hasEntity.isEmpty()) {
            this.entityCalc();
        }
    }
    
    private void entityCalc() {
        (this.posList = new ArrayList<BlockPos>()).addAll(this.hasEntity);
        this.hasEntity = new ArrayList<BlockPos>();
        for (final BlockPos pos : this.posList) {
            this.addPos(pos);
        }
        this.hasEntity.removeIf(blockPos -> blockPos == null || this.floorPos.contains(blockPos) || Surround.mc.player.getDistanceSq(blockPos) > (int)this.range.getValue() * (int)this.range.getValue());
        this.surround.removeIf(blockPos -> blockPos == null || Surround.mc.player.getDistanceSq(blockPos) > (int)this.range.getValue() * (int)this.range.getValue());
        if (!this.hasEntity.isEmpty()) {
            this.entityCalc();
        }
    }
    
    private void addPos(final BlockPos pos) {
        if (this.floorPos.contains(pos)) {
            return;
        }
        for (final BlockPos side : this.sides) {
            final BlockPos blockPos = pos.add((Vec3i)side);
            if (this.intersectsWithEntity(blockPos)) {
                this.hasEntity.add(blockPos);
            }
            else {
                this.surround.add(blockPos);
            }
        }
        this.floorPos.add(pos);
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : Surround.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (!new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                continue;
            }
            if (entity instanceof EntityEnderCrystal) {
                this.crystals.add((EntityEnderCrystal)entity);
            }
            else {
                if (entity instanceof EntityPlayer) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
}
