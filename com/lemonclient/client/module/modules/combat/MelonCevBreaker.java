//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.item.*;
import com.lemonclient.api.util.world.combat.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.api.util.render.*;

@Module.Declaration(name = "MelonCivBreaker", category = Category.Combat)
public class MelonCevBreaker extends Module
{
    public int oldSlot;
    ModeSetting time;
    IntegerSetting delay;
    BooleanSetting swing;
    BooleanSetting packet;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting antiWeakness;
    DoubleSetting range;
    BooleanSetting autoDis;
    ColorSetting color;
    IntegerSetting ufoAlpha;
    IntegerSetting Alpha;
    List<BlockPos> blocks;
    boolean flag;
    int progress;
    int firsttime;
    int pickaxeitem;
    int crystalitem;
    int ObiItem;
    int currentitem;
    private BlockPos cobi;
    EntityPlayer entityPlayer;
    int waited;
    
    public MelonCevBreaker() {
        this.time = this.registerMode("Time Mode", (List)Arrays.asList("Tick", "onUpdate", "Fast"), "Tick");
        this.delay = this.registerInteger("Delay", 1, 0, 20, () -> ((String)this.time.getValue()).equals("Fast"));
        this.swing = this.registerBoolean("Swing", false);
        this.packet = this.registerBoolean("Packet", false);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.antiWeakness = this.registerBoolean("Anti Weakness", false);
        this.range = this.registerDouble("Range", 5.0, 0.0, 6.0);
        this.autoDis = this.registerBoolean("Auto Disable", false);
        this.color = this.registerColor("Color", new GSColor(120, 0, 0));
        this.ufoAlpha = this.registerInteger("Alpha", 120, 0, 255);
        this.Alpha = this.registerInteger("Outline Alpha", 255, 0, 255);
        this.blocks = new ArrayList<BlockPos>();
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || MelonCevBreaker.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                MelonCevBreaker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                MelonCevBreaker.mc.player.inventory.currentItem = slot;
            }
            MelonCevBreaker.mc.playerController.updateController();
        }
    }
    
    public void onEnable() {
        this.progress = 0;
        this.flag = false;
        this.firsttime = 0;
        this.cobi = null;
    }
    
    private boolean validposition(final BlockPos blockPos) {
        final EnumFacing enumFacing = BurrowUtil.getFirstFacing(blockPos);
        return enumFacing != null && MelonCevBreaker.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && MelonCevBreaker.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock() == Blocks.AIR && MelonCevBreaker.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock() == Blocks.AIR && !this.intersectsWithEntity(blockPos) && !this.intersectsWithEntity2(blockPos.up());
    }
    
    public static int findHotbarBlock(final Class<?> clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = MelonCevBreaker.mc.player.inventory.getStackInSlot(i);
            if (itemStack != ItemStack.EMPTY) {
                if (clazz.isInstance(itemStack.getItem())) {
                    return i;
                }
                if (itemStack.getItem() instanceof ItemBlock) {
                    if (clazz.isInstance(((ItemBlock)itemStack.getItem()).getBlock())) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : MelonCevBreaker.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean intersectsWithEntity2(final BlockPos pos) {
        for (final Entity entity : MelonCevBreaker.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (entity instanceof EntityEnderCrystal) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    private void placeBlock(final BlockPos blockPos, final boolean bl2) {
        final EnumFacing enumFacing = BurrowUtil.getFirstFacing(blockPos);
        if (enumFacing == null) {
            return;
        }
        final BlockPos blockPos2 = blockPos.offset(enumFacing);
        final EnumFacing enumFacing2 = enumFacing.getOpposite();
        final Vec3d vec3d = new Vec3d((Vec3i)blockPos2).add(0.5, 0.5, 0.5).add(new Vec3d(enumFacing2.getDirectionVec()).scale(0.5));
        final Block block = MelonCevBreaker.mc.world.getBlockState(blockPos2).getBlock();
        if (!MelonCevBreaker.mc.player.isSneaking() && (BlockUtil.blackList.contains(block) || BlockUtil.shulkerList.contains(block))) {
            MelonCevBreaker.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)MelonCevBreaker.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            MelonCevBreaker.mc.player.setSneaking(true);
        }
        this.rightClickBlock(blockPos2, vec3d, EnumHand.MAIN_HAND, enumFacing2, bl2);
        MelonCevBreaker.mc.rightClickDelayTimer = 4;
    }
    
    private void rightClickBlock(final BlockPos blockPos, final Vec3d vec3d, final EnumHand enumHand, final EnumFacing enumFacing, final boolean bl) {
        if (bl) {
            MelonCevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(blockPos, enumFacing, enumHand, 0.5f, 1.0f, 0.5f));
        }
        else {
            MelonCevBreaker.mc.playerController.processRightClickBlock(MelonCevBreaker.mc.player, MelonCevBreaker.mc.world, blockPos, enumFacing, vec3d, enumHand);
        }
        if (this.swing.getValue()) {
            MelonCevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        MelonCevBreaker.mc.rightClickDelayTimer = 4;
    }
    
    public static void placeCrystalOnBlock(final BlockPos blockPos, final EnumHand enumHand, final boolean bl) {
        MelonCevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(blockPos, EnumFacing.UP, enumHand, 0.0f, 0.0f, 0.0f));
        if (bl) {
            MelonCevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    private void reset() {
        this.firsttime = 0;
        this.cobi = null;
        this.flag = false;
    }
    
    public void onUpdate() {
        if (!((String)this.time.getValue()).equals("onUpdate")) {
            return;
        }
        this.doCev();
    }
    
    public void onTick() {
        if (!((String)this.time.getValue()).equals("Tick")) {
            return;
        }
        this.doCev();
    }
    
    public void fast() {
        if (!((String)this.time.getValue()).equals("Fast")) {
            return;
        }
        if (this.waited++ < (int)this.delay.getValue()) {
            return;
        }
        this.waited = 0;
        this.doCev();
    }
    
    private void doCev() {
        if (MelonCevBreaker.mc.world == null || MelonCevBreaker.mc.player == null || MelonCevBreaker.mc.player.isDead) {
            this.reset();
            if (this.autoDis.getValue()) {
                this.disable();
            }
            return;
        }
        this.oldSlot = MelonCevBreaker.mc.player.inventory.currentItem;
        this.pickaxeitem = findHotbarBlock(ItemPickaxe.class);
        this.crystalitem = findHotbarBlock(ItemEndCrystal.class);
        this.ObiItem = findHotbarBlock(BlockObsidian.class);
        this.currentitem = MelonCevBreaker.mc.player.inventory.currentItem;
        if (this.pickaxeitem == -1 || this.crystalitem == -1 || this.ObiItem == -1) {
            return;
        }
        this.entityPlayer = PlayerUtil.getNearestPlayer((double)this.range.getValue());
        if (this.entityPlayer == null) {
            this.reset();
            if (this.autoDis.getValue()) {
                this.disable();
            }
            return;
        }
        final BlockPos blockPos = new BlockPos((Entity)this.entityPlayer).add(-1.0, 1.0, 0.0);
        final BlockPos blockPos2 = new BlockPos((Entity)this.entityPlayer).add(0.0, 1.0, 1.0);
        final BlockPos blockPos3 = new BlockPos((Entity)this.entityPlayer).add(0.0, 1.0, -1.0);
        final BlockPos blockPos4 = new BlockPos((Entity)this.entityPlayer).add(1.0, 1.0, 0.0);
        final BlockPos blockPos5 = new BlockPos((Entity)this.entityPlayer).add(0.0, 2.0, 0.0);
        this.blocks.clear();
        this.blocks.add(blockPos);
        this.blocks.add(blockPos2);
        this.blocks.add(blockPos3);
        this.blocks.add(blockPos4);
        this.blocks.add(blockPos5);
        if (!this.isPos2(this.cobi, blockPos) && !this.isPos2(this.cobi, blockPos2) && !this.isPos2(this.cobi, blockPos3) && !this.isPos2(this.cobi, blockPos4) && !this.isPos2(this.cobi, blockPos5)) {
            if (this.autoDis.getValue()) {
                this.disable();
                return;
            }
            this.reset();
            this.cobi = this.getBestPos();
        }
        if (this.cobi != null && this.validposition(this.cobi)) {
            switch (this.progress) {
                case 0: {
                    if (this.firsttime < 1) {
                        this.flag = true;
                    }
                    this.switchTo(this.ObiItem);
                    this.placeBlock(this.cobi, (boolean)this.packet.getValue());
                    this.switchTo(this.oldSlot);
                    this.switchTo(this.crystalitem);
                    placeCrystalOnBlock(this.cobi, EnumHand.MAIN_HAND, (boolean)this.swing.getValue());
                    this.switchTo(this.oldSlot);
                    ++this.progress;
                    break;
                }
                case 1: {
                    if (this.firsttime < 1) {
                        MelonCevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.cobi, EnumFacing.UP));
                    }
                    this.switchTo(this.crystalitem);
                    MelonCevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.cobi, EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                    if (this.swing.getValue()) {
                        MelonCevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
                    }
                    this.switchTo(this.oldSlot);
                    this.switchTo(this.pickaxeitem);
                    MelonCevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.cobi, EnumFacing.UP));
                    this.switchTo(this.oldSlot);
                    if (MelonCevBreaker.mc.world.isAirBlock(this.cobi)) {
                        for (final Entity entity : MelonCevBreaker.mc.world.loadedEntityList) {
                            if (this.entityPlayer.getDistance(entity) <= (double)this.range.getValue()) {
                                if (!(entity instanceof EntityEnderCrystal)) {
                                    continue;
                                }
                                this.breakCrystalPiston(entity);
                            }
                        }
                    }
                    ++this.progress;
                    break;
                }
                case 2: {
                    int n = 0;
                    for (final Entity entity2 : MelonCevBreaker.mc.world.loadedEntityList) {
                        if (this.entityPlayer.getDistance(entity2) <= (double)this.range.getValue()) {
                            if (!(entity2 instanceof EntityEnderCrystal)) {
                                continue;
                            }
                            this.breakCrystalPiston(entity2);
                            ++n;
                        }
                    }
                    if (n != 0 && !this.flag) {
                        break;
                    }
                    ++this.progress;
                    break;
                }
                case 3: {
                    this.switchTo(this.ObiItem);
                    this.placeBlock(this.cobi, (boolean)this.packet.getValue());
                    this.switchTo(this.oldSlot);
                    this.progress = 0;
                    ++this.firsttime;
                    break;
                }
            }
            return;
        }
        this.reset();
        if (this.autoDis.getValue()) {
            this.disable();
        }
    }
    
    public BlockPos getBestPos() {
        if (this.blocks.isEmpty()) {
            return null;
        }
        BlockPos bestPos = null;
        double bestDistance = 0.0;
        for (final BlockPos pos : this.blocks) {
            if (!this.validposition(pos)) {
                continue;
            }
            final double distance = MelonCevBreaker.mc.player.getDistanceSq(pos);
            if (bestPos == null) {
                bestPos = pos;
                bestDistance = distance;
            }
            else {
                if (distance >= bestDistance) {
                    continue;
                }
                bestPos = pos;
                bestDistance = distance;
            }
        }
        return bestPos;
    }
    
    private void breakCrystalPiston(final Entity crystal) {
        if (crystal == null) {
            return;
        }
        final int oldSlot = MelonCevBreaker.mc.player.inventory.currentItem;
        if ((boolean)this.antiWeakness.getValue() && MelonCevBreaker.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
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
                MelonCevBreaker.mc.player.inventory.currentItem = newSlot;
            }
        }
        CrystalUtil.breakCrystal(crystal, false);
        if (this.swing.getValue()) {
            MelonCevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        MelonCevBreaker.mc.player.inventory.currentItem = oldSlot;
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (this.entityPlayer != null && this.cobi != null) {
            final EnumFacing enumFacing = BurrowUtil.getFirstFacing(this.cobi);
            if (enumFacing == null) {
                return;
            }
            RenderUtil.drawBox(this.cobi, 1.0, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 63);
            RenderUtil.drawBoundingBox(this.cobi, 1.0, 1.0f, new GSColor(this.color.getValue(), (int)this.Alpha.getValue()));
        }
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
}
