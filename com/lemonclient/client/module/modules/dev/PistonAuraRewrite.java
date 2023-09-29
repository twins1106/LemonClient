//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import com.lemonclient.api.util.world.*;
import com.lemonclient.api.util.player.*;
import java.util.function.*;
import net.minecraft.block.*;
import com.lemonclient.client.module.modules.combat.*;
import net.minecraft.network.play.client.*;
import com.lemonclient.client.module.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import java.util.*;

@Module.Declaration(name = "PistonAuraRewrite", category = Category.Dev)
public class PistonAuraRewrite extends Module
{
    IntegerSetting preDelay;
    IntegerSetting pistonDelay;
    IntegerSetting breakDelay;
    IntegerSetting destroyDelay;
    IntegerSetting range;
    ModeSetting targetFindType;
    IntegerSetting targetrange;
    BooleanSetting breakSync;
    IntegerSetting maxDelay;
    IntegerSetting breakAttempts;
    ModeSetting redstone;
    BooleanSetting toggle;
    BooleanSetting multiThreading;
    BooleanSetting sound;
    IntegerSetting maxY;
    BooleanSetting packetPlace;
    BooleanSetting packetCrystal;
    BooleanSetting packetBreak;
    BooleanSetting antiWeakness;
    BooleanSetting silentSwitch;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting swing;
    BooleanSetting pause;
    ModeSetting type;
    BooleanSetting pause1;
    public static EntityPlayer target;
    public BlockPos pistonPos;
    public BlockPos crystalPos;
    public BlockPos redStonePos;
    public int pistonSlot;
    public int crystalSlot;
    public int redStoneSlot;
    public int obbySlot;
    public boolean isTorchSlot;
    public Timing preTimer;
    public Timing pistonTimer;
    public Timing breakTimer;
    public Timing destroyTimer;
    public Timing maxTimer;
    public boolean preparedSpace;
    public boolean placedPiston;
    public boolean placedRedstone;
    public boolean placedCrystal;
    public boolean brokeCrystal;
    public boolean synced;
    public int attempts;
    public int lastCrystal;
    int oldslot;
    boolean burrowed;
    boolean moving;
    @EventHandler
    private final Listener<PacketEvent.Receive> listener;
    
    public PistonAuraRewrite() {
        this.preDelay = this.registerInteger("BlockDelay", 0, 0, 25);
        this.pistonDelay = this.registerInteger("PistonDelay", 0, 0, 25);
        this.breakDelay = this.registerInteger("BreakDelay", 1, 0, 25);
        this.destroyDelay = this.registerInteger("DestroyDelay", 1, 0, 25);
        this.range = this.registerInteger("Range", 5, 0, 10);
        this.targetFindType = this.registerMode("Target", (List)Arrays.asList("Nearest", "Looking"), "Nearest");
        this.targetrange = this.registerInteger("Target Range", 8, 0, 16);
        this.breakSync = this.registerBoolean("BreakSync", true);
        this.maxDelay = this.registerInteger("MaxDelay", 30, 0, 60);
        this.breakAttempts = this.registerInteger("BreakAttempts", 7, 1, 20);
        this.redstone = this.registerMode("Redstone", (List)Arrays.asList("Block", "Torch", "Both"), "Both");
        this.toggle = this.registerBoolean("Toggle", true);
        this.multiThreading = this.registerBoolean("MultiThreading", false);
        this.sound = this.registerBoolean("Sound", true);
        this.maxY = this.registerInteger("MaxY", 3, 5, 1);
        this.packetPlace = this.registerBoolean("PacketPlace", true);
        this.packetCrystal = this.registerBoolean("PacketCrystal", true);
        this.packetBreak = this.registerBoolean("PacketBreak", true);
        this.antiWeakness = this.registerBoolean("Anti Weakness", false);
        this.silentSwitch = this.registerBoolean("SilentSwitch", true);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.swing = this.registerBoolean("Swing", true);
        this.pause = this.registerBoolean("Pause When Move", true);
        this.type = this.registerMode("Type", (List)Arrays.asList("Motion", "Pos", "Both"), "Pos");
        this.pause1 = this.registerBoolean("Pause When Burrow", true);
        this.redStonePos = null;
        this.obbySlot = -1;
        this.isTorchSlot = false;
        this.maxTimer = new Timing();
        this.synced = false;
        this.attempts = 0;
        this.lastCrystal = -1;
        this.listener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (PistonAuraRewrite.mc.world == null || PistonAuraRewrite.mc.player == null || PistonAuraRewrite.mc.player.isDead) {
                return;
            }
            if (!ModuleManager.isModuleEnabled((Class)PistonAuraRewrite.class)) {
                return;
            }
            if ((boolean)this.sound.getValue() && event.getPacket() instanceof SPacketSoundEffect && ((SPacketSoundEffect)event.getPacket()).getCategory() == SoundCategory.BLOCKS && ((SPacketSoundEffect)event.getPacket()).getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (final Entity crystal : new ArrayList<Entity>(PistonAuraRewrite.mc.world.loadedEntityList)) {
                    if (crystal instanceof EntityEnderCrystal && crystal.getDistance(((SPacketSoundEffect)event.getPacket()).getX(), ((SPacketSoundEffect)event.getPacket()).getY(), ((SPacketSoundEffect)event.getPacket()).getZ()) <= (int)this.range.getValue() + 5) {
                        crystal.setDead();
                    }
                }
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        this.doPA();
    }
    
    public void onEnable() {
        this.lastCrystal = -1;
        this.reset();
    }
    
    public void doPA() {
        this.moving = false;
        this.burrowed = false;
        final String s = (String)this.type.getValue();
        switch (s) {
            case "Motion": {
                if (PistonAuraRewrite.mc.player.motionX != 0.0 || PistonAuraRewrite.mc.player.motionZ != 0.0 || PistonAuraRewrite.mc.player.motionY > -0.078) {
                    this.moving = true;
                    break;
                }
                break;
            }
            case "Pos": {
                if (MotionUtil.isMoving((EntityLivingBase)PistonAuraRewrite.mc.player)) {
                    this.moving = true;
                    break;
                }
                break;
            }
            case "Both": {
                if (MotionUtil.isMoving((EntityLivingBase)PistonAuraRewrite.mc.player) || PistonAuraRewrite.mc.player.motionX != 0.0 || PistonAuraRewrite.mc.player.motionY > -0.078 || PistonAuraRewrite.mc.player.motionZ != 0.0) {
                    this.moving = true;
                    break;
                }
                break;
            }
        }
        final BlockPos originalPos = AntiBurrow.getEntityPos((Entity)PistonAuraRewrite.mc.player);
        if (PistonAuraRewrite.mc.world.getBlockState(originalPos).getBlock() == Blocks.OBSIDIAN || PistonAuraRewrite.mc.world.getBlockState(originalPos).getBlock() == Blocks.ENDER_CHEST) {
            this.burrowed = true;
        }
        if ((boolean)this.pause.getValue() && this.moving) {
            return;
        }
        if ((boolean)this.pause1.getValue() && this.burrowed) {
            return;
        }
        if (this.multiThreading.getValue()) {
            MultiThreading.runAsync(this::_doPA);
        }
        else {
            this._doPA();
        }
    }
    
    public void _doPA() {
        if (PistonAuraRewrite.mc.world == null || PistonAuraRewrite.mc.player == null || PistonAuraRewrite.mc.player.isDead) {
            return;
        }
        if (!this.setup()) {
            return;
        }
        final BlockPos targetPos = new BlockPos(PistonAuraRewrite.target.posX, PistonAuraRewrite.target.posY, PistonAuraRewrite.target.posZ);
        if (this.preTimer.passedX((double)(int)this.preDelay.getValue()) && !this.preparedSpace) {
            this.pistonTimer.reset();
            this.preparedSpace = this.prepareSpace();
        }
        if (this.preparedSpace && !this.placedPiston && !this.placedRedstone && this.redStonePos.equals((Object)this.pistonPos.add(0, -1, 0))) {
            this.oldslot = PistonAuraRewrite.mc.player.inventory.currentItem;
            this.switchTo(this.redStoneSlot);
            BurrowUtil.placeBlock(this.redStonePos, EnumHand.MAIN_HAND, false, (boolean)this.packetPlace.getValue(), false, (boolean)this.swing.getValue());
            if (this.silentSwitch.getValue()) {
                this.switchTo(this.oldslot);
            }
            this.placedRedstone = true;
        }
        if (this.pistonTimer.passedX((double)(int)this.pistonDelay.getValue()) && this.preparedSpace && !this.placedPiston) {
            this.oldslot = PistonAuraRewrite.mc.player.inventory.currentItem;
            this.switchTo(this.pistonSlot);
            final float[] angle = MathUtil.calcAngle(new Vec3d((Vec3i)this.crystalPos), new Vec3d((Vec3i)targetPos));
            PistonAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0] + 180.0f, angle[1], true));
            BurrowUtil.placeBlock(this.pistonPos, EnumHand.MAIN_HAND, false, (boolean)this.packetPlace.getValue(), false, (boolean)this.swing.getValue());
            if (this.silentSwitch.getValue()) {
                this.switchTo(this.oldslot);
            }
            this.placedPiston = true;
        }
        if (this.placedPiston && !this.placedRedstone) {
            this.oldslot = PistonAuraRewrite.mc.player.inventory.currentItem;
            this.switchTo(this.redStoneSlot);
            if (!this.isTorchSlot) {
                EnumFacing facing = null;
                for (final EnumFacing facing2 : EnumFacing.values()) {
                    if (this.pistonPos.add(facing2.getDirectionVec()).equals((Object)this.redStonePos)) {
                        facing = facing2;
                    }
                }
                if (facing == null) {
                    return;
                }
                this.rightClickBlock(this.pistonPos, facing, (boolean)this.packetPlace.getValue());
            }
            else {
                BurrowUtil.placeBlock(this.redStonePos, EnumHand.MAIN_HAND, false, (boolean)this.packetPlace.getValue(), false, (boolean)this.swing.getValue());
            }
            if (this.silentSwitch.getValue()) {
                this.switchTo(this.oldslot);
            }
            this.placedRedstone = true;
        }
        if (this.placedRedstone && !this.placedCrystal) {
            this.oldslot = PistonAuraRewrite.mc.player.inventory.currentItem;
            this.switchTo(this.crystalSlot);
            final EnumHand hand = (this.crystalSlot != 999) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
            if (this.packetCrystal.getValue()) {
                PistonAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.crystalPos, EnumFacing.DOWN, hand, 0.0f, 0.0f, 0.0f));
            }
            else {
                PistonAuraRewrite.mc.playerController.processRightClickBlock(PistonAuraRewrite.mc.player, PistonAuraRewrite.mc.world, this.crystalPos, EnumFacing.DOWN, new Vec3d(0.0, 0.0, 0.0), hand);
            }
            if (this.silentSwitch.getValue()) {
                this.switchTo(this.oldslot);
            }
            this.breakTimer.reset();
            this.placedCrystal = true;
        }
        if (this.breakTimer.passedX((double)(int)this.breakDelay.getValue()) && this.placedCrystal && !this.brokeCrystal) {
            final Entity crystal = (Entity)PistonAuraRewrite.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> PistonAuraRewrite.target.getDistance(e) < 5.0f).min(Comparator.comparing(e -> PistonAuraRewrite.target.getDistance(e))).orElse(null);
            if (crystal == null) {
                if (this.attempts <= (int)this.breakAttempts.getValue()) {
                    ++this.attempts;
                }
                else {
                    this.reset();
                }
                return;
            }
            final int oldSlot = PistonAuraRewrite.mc.player.inventory.currentItem;
            if ((boolean)this.antiWeakness.getValue() && PistonAuraRewrite.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                int newSlot = -1;
                for (int i = 0; i < 9; ++i) {
                    final ItemStack stack = PistonAuraRewrite.mc.player.inventory.getStackInSlot(i);
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
            if (this.packetBreak.getValue()) {
                PistonAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
            }
            else {
                PistonAuraRewrite.mc.playerController.attackEntity((EntityPlayer)PistonAuraRewrite.mc.player, crystal);
            }
            if ((boolean)this.silentSwitch.getValue() && (boolean)this.silentSwitch.getValue()) {
                this.switchTo(oldSlot);
            }
            final EnumHand hand2 = EnumHand.MAIN_HAND;
            if (this.swing.getValue()) {
                PistonAuraRewrite.mc.player.swingArm(hand2);
            }
            this.maxTimer.reset();
            this.brokeCrystal = true;
        }
        if (!(boolean)this.breakSync.getValue()) {
            this.synced = true;
        }
        if ((boolean)this.breakSync.getValue() && this.brokeCrystal && !this.synced) {
            this.destroyTimer.reset();
            if (this.maxTimer.passedDms((double)(int)this.maxDelay.getValue())) {
                this.synced = true;
            }
            if (BlockUtil.getBlock(this.pistonPos) == Blocks.AIR) {
                this.synced = true;
            }
            else {
                final Entity crystal = (Entity)PistonAuraRewrite.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> PistonAuraRewrite.target.getDistance(e) < 5.0f).min(Comparator.comparing(e -> PistonAuraRewrite.target.getDistance(e))).orElse(null);
                if (crystal == null) {
                    return;
                }
                this.lastCrystal = crystal.entityId;
                final int oldSlot = PistonAuraRewrite.mc.player.inventory.currentItem;
                if ((boolean)this.antiWeakness.getValue() && PistonAuraRewrite.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                    int newSlot = -1;
                    for (int i = 0; i < 9; ++i) {
                        final ItemStack stack = PistonAuraRewrite.mc.player.inventory.getStackInSlot(i);
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
                if (this.packetBreak.getValue()) {
                    PistonAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                }
                else {
                    PistonAuraRewrite.mc.playerController.attackEntity((EntityPlayer)PistonAuraRewrite.mc.player, crystal);
                }
                if ((boolean)this.silentSwitch.getValue() && (boolean)this.silentSwitch.getValue()) {
                    this.switchTo(oldSlot);
                }
                final EnumHand hand2 = EnumHand.MAIN_HAND;
                if (this.swing.getValue()) {
                    PistonAuraRewrite.mc.player.swingArm(hand2);
                }
            }
        }
        if (this.destroyTimer.passedX((double)(int)this.destroyDelay.getValue()) && this.synced) {
            this.reset();
        }
    }
    
    public void rightClickBlock(final BlockPos pos, final EnumFacing facing, final boolean packet) {
        final Vec3d hitVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5).add(new Vec3d(facing.getDirectionVec()).scale(0.5));
        if (packet) {
            this.rightClickBlock(pos, hitVec, EnumHand.MAIN_HAND, facing);
        }
        else {
            PistonAuraRewrite.mc.playerController.processRightClickBlock(PistonAuraRewrite.mc.player, PistonAuraRewrite.mc.world, pos, facing, hitVec, EnumHand.MAIN_HAND);
            if (this.swing.getValue()) {
                PistonAuraRewrite.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
    
    public void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction) {
        final float f = (float)(vec.x - pos.getX());
        final float f2 = (float)(vec.y - pos.getY());
        final float f3 = (float)(vec.z - pos.getZ());
        PistonAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
        if (this.swing.getValue()) {
            PistonAuraRewrite.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        PistonAuraRewrite.mc.rightClickDelayTimer = 4;
    }
    
    public boolean prepareSpace() {
        final BlockPos targetPos = new BlockPos(PistonAuraRewrite.target.posX, PistonAuraRewrite.target.posY, PistonAuraRewrite.target.posZ);
        final BlockPos redstone = this.redStonePos.add(0, -1, 0);
        final BlockPos base = this.pistonPos.add(0, -1, 0);
        if (BlockUtil.getBlock(base) == Blocks.AIR) {
            final BlockPos offset = new BlockPos(this.crystalPos.getX() - targetPos.getX(), 0, this.crystalPos.getZ() - targetPos.getZ());
            final BlockPos crystalOffset = this.crystalPos.add((Vec3i)offset);
            final BlockPos crystalOffset2 = crystalOffset.add((Vec3i)offset);
            if (BlockUtil.hasNeighbour(base) || base.equals((Object)this.redStonePos)) {
                if (!base.equals((Object)this.redStonePos)) {
                    this.oldslot = PistonAuraRewrite.mc.player.inventory.currentItem;
                    this.switchTo(this.obbySlot);
                    BurrowUtil.placeBlock(base, EnumHand.MAIN_HAND, false, (boolean)this.packetPlace.getValue(), false, (boolean)this.swing.getValue());
                    if (this.silentSwitch.getValue()) {
                        this.switchTo(this.oldslot);
                    }
                    return false;
                }
            }
            else {
                if (BlockUtil.getBlock(crystalOffset) == Blocks.AIR) {
                    this.oldslot = PistonAuraRewrite.mc.player.inventory.currentItem;
                    this.switchTo(this.obbySlot);
                    BurrowUtil.placeBlock(crystalOffset, EnumHand.MAIN_HAND, false, (boolean)this.packetPlace.getValue(), false, (boolean)this.swing.getValue());
                    if (this.silentSwitch.getValue()) {
                        this.switchTo(this.oldslot);
                    }
                    return false;
                }
                if (BlockUtil.getBlock(crystalOffset2) == Blocks.AIR) {
                    this.oldslot = PistonAuraRewrite.mc.player.inventory.currentItem;
                    this.switchTo(this.obbySlot);
                    BurrowUtil.placeBlock(crystalOffset2, EnumHand.MAIN_HAND, false, (boolean)this.packetPlace.getValue(), false, (boolean)this.swing.getValue());
                    if (this.silentSwitch.getValue()) {
                        this.switchTo(this.oldslot);
                    }
                    return false;
                }
            }
        }
        if (BlockUtil.getBlock(redstone) == Blocks.AIR && (this.pistonPos.getX() != this.redStonePos.getX() || this.pistonPos.getZ() != this.redStonePos.getZ()) && this.isTorchSlot) {
            this.oldslot = PistonAuraRewrite.mc.player.inventory.currentItem;
            this.switchTo(this.obbySlot);
            BurrowUtil.placeBlock(redstone, EnumHand.MAIN_HAND, false, (boolean)this.packetPlace.getValue(), false, (boolean)this.swing.getValue());
            if (this.silentSwitch.getValue()) {
                this.switchTo(this.oldslot);
            }
            return false;
        }
        return true;
    }
    
    public boolean setup() {
        if (!this.findMaterials()) {
            this.disablePA();
            return false;
        }
        if (!this.findTarget()) {
            this.disablePA();
            return false;
        }
        if (this.pistonPos == null && !this.findSpace(PistonAuraRewrite.target)) {
            this.disablePA();
            return false;
        }
        return true;
    }
    
    public void reset() {
        this.preTimer = new Timing();
        this.pistonTimer = new Timing();
        this.breakTimer = new Timing();
        this.maxTimer = new Timing();
        this.destroyTimer = new Timing();
        this.preparedSpace = false;
        this.placedPiston = false;
        this.placedRedstone = false;
        this.placedCrystal = false;
        this.brokeCrystal = false;
        this.synced = false;
        this.pistonPos = null;
        this.crystalPos = null;
        this.redStonePos = null;
        this.attempts = 0;
    }
    
    public boolean findSpace(final EntityPlayer target) {
        final BlockPos mypos = new BlockPos(PistonAuraRewrite.mc.player.posX, PistonAuraRewrite.mc.player.posY, PistonAuraRewrite.mc.player.posZ);
        final BlockPos targetPos = new BlockPos(target.posX, target.posY, target.posZ);
        final BlockPos[] offsets = { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        final List<PistonAuraPos> can = new ArrayList<PistonAuraPos>();
        for (int y = 0; y <= (int)this.maxY.getValue(); ++y) {
            for (final BlockPos offset : offsets) {
                final BlockPos crystalPos = targetPos.add(offset.getX(), y, offset.getZ());
                if (BlockUtil.getBlock(crystalPos) == Blocks.OBSIDIAN || BlockUtil.getBlock(crystalPos) == Blocks.BEDROCK) {
                    if (BlockUtil.getBlock(crystalPos.add(0, 1, 0)) == Blocks.AIR) {
                        if (BlockUtil.getBlock(crystalPos.add(0, 2, 0)) == Blocks.AIR) {
                            if (PlayerUtil.getDistanceI(crystalPos) <= (int)this.range.getValue()) {
                                if (!this.checkPos(crystalPos)) {
                                    final BlockPos normal = crystalPos.add((Vec3i)offset);
                                    final BlockPos oneBlock = normal.add((Vec3i)offset);
                                    final BlockPos side0 = normal.add(offset.getZ(), offset.getY(), offset.getX());
                                    final BlockPos side2 = normal.add(offset.getZ() * -1, offset.getY(), offset.getX() * -1);
                                    final BlockPos side3 = oneBlock.add(offset.getZ(), offset.getY(), offset.getX());
                                    final BlockPos side4 = oneBlock.add(offset.getZ() * -1, offset.getY(), offset.getX() * -1);
                                    final BlockPos side5 = crystalPos.add(offset.getZ(), offset.getY(), offset.getX());
                                    final BlockPos side6 = crystalPos.add(offset.getZ() * -1, offset.getY(), offset.getX() * -1);
                                    final List<PistonPos> pistons = new ArrayList<PistonPos>();
                                    this.add(pistons, new PistonPos(normal));
                                    this.add(pistons, new PistonPos(oneBlock));
                                    this.add(pistons, new PistonPos(side0));
                                    this.add(pistons, new PistonPos(side2));
                                    this.add(pistons, new PistonPos(side3));
                                    this.add(pistons, new PistonPos(side4));
                                    this.add(pistons, new PistonPos(side5, true));
                                    this.add(pistons, new PistonPos(side6, true));
                                    final BlockPos blockPos;
                                    final BlockPos headPos;
                                    final BlockPos blockPos2;
                                    final PistonPos piston = pistons.stream().filter(p -> BlockUtil.getBlock(p.pos) == Blocks.AIR).filter(p -> PlayerUtil.getDistanceI(p.pos) <= (int)this.range.getValue()).filter(p -> !this.checkPos(p.pos)).filter(p -> {
                                        headPos = p.pos.add(blockPos.getX() * -1, 0, blockPos.getZ() * -1);
                                        return BlockUtil.getBlock(headPos) == Blocks.AIR && !this.checkPos(headPos);
                                    }).filter(p -> blockPos2.getDistance(p.pos.getX(), p.pos.getY(), p.pos.getZ()) >= 3.6 + (p.pos.getY() - (blockPos2.getY() + 1)) || p.pos.getY() <= blockPos2.getY() + 1).filter(p -> this.getRedStonePos(p.pos, crystalPos, offset) != null).min(Comparator.comparing(p -> PlayerUtil.getDistanceI(p.pos))).orElse(null);
                                    if (piston != null) {
                                        final PistonAuraPos pos = new PistonAuraPos(crystalPos, piston.pos, this.getRedStonePos(piston.pos, crystalPos, offset));
                                        can.add(pos);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        final PistonAuraPos best = can.stream().min(Comparator.comparing((Function<? super PistonAuraPos, ? extends Comparable>)PistonAuraPos::range)).orElse(null);
        if (best == null) {
            return false;
        }
        this.crystalPos = best.crystal;
        this.pistonPos = best.piston;
        this.redStonePos = best.redstone;
        return true;
    }
    
    public BlockPos getRedStonePos(final BlockPos pistonPos, final BlockPos crystalPos, final BlockPos _offset) {
        final BlockPos reverseOffset = new BlockPos(_offset.getX() * -1, _offset.getY(), _offset.getZ() * -1);
        final BlockPos pistonOffset = pistonPos.add((Vec3i)reverseOffset);
        final BlockPos[] array;
        final BlockPos[] offsets = array = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(0, 1, 0), new BlockPos(0, -1, 0) };
        for (final BlockPos offset : array) {
            final BlockPos redstone = pistonPos.add((Vec3i)offset);
            if (!redstone.equals((Object)pistonOffset)) {
                if (BlockUtil.getBlock(redstone) == Blocks.REDSTONE_BLOCK || (BlockUtil.getBlock(redstone) == Blocks.REDSTONE_TORCH && offset.getY() != 1)) {
                    return redstone;
                }
            }
        }
        final List<BlockPos> pos = new ArrayList<BlockPos>();
        for (final BlockPos offset2 : offsets) {
            final BlockPos redstone2 = pistonPos.add((Vec3i)offset2);
            if (!this.isTorchSlot || offset2.getY() != 1) {
                if (BlockUtil.getBlock(redstone2) == Blocks.AIR) {
                    if (redstone2.getX() != crystalPos.getX() || redstone2.getZ() != crystalPos.getZ()) {
                        if (!redstone2.equals((Object)pistonOffset)) {
                            if (!this.checkPos(redstone2)) {
                                pos.add(redstone2);
                            }
                        }
                    }
                }
            }
        }
        return pos.stream().filter(b -> PlayerUtil.getDistance(b) <= (int)this.range.getValue()).max(Comparator.comparing((Function<? super BlockPos, ? extends Comparable>)PlayerUtil::getDistanceI)).orElse(null);
    }
    
    public boolean checkPos(final BlockPos pos) {
        final BlockPos mypos = new BlockPos(PistonAuraRewrite.mc.player.posX, PistonAuraRewrite.mc.player.posY, PistonAuraRewrite.mc.player.posZ);
        return pos.getX() == mypos.getX() && pos.getZ() == mypos.getZ();
    }
    
    public void add(final List<PistonPos> pistons, final PistonPos pos) {
        pistons.add(new PistonPos(pos.pos.add(0, 1, 0), pos.swap));
        pistons.add(new PistonPos(pos.pos.add(0, 2, 0), pos.swap));
    }
    
    public boolean findMaterials() {
        this.pistonSlot = PistonAura.findHotbarBlock((Block)Blocks.PISTON);
        this.obbySlot = PistonAura.findHotbarBlock(Blocks.OBSIDIAN);
        this.crystalSlot = AutoCev.getItemHotbar(Items.END_CRYSTAL);
        if (this.pistonSlot == -1) {
            this.pistonSlot = PistonAura.findHotbarBlock((Block)Blocks.STICKY_PISTON);
        }
        if (PistonAuraRewrite.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            this.crystalSlot = 999;
        }
        final int block = PistonAura.findHotbarBlock(Blocks.REDSTONE_BLOCK);
        final int torch = PistonAura.findHotbarBlock(Blocks.REDSTONE_TORCH);
        if (((String)this.redstone.getValue()).equals("Block")) {
            this.redStoneSlot = block;
        }
        if (((String)this.redstone.getValue()).equals("Torch")) {
            this.redStoneSlot = torch;
        }
        if (((String)this.redstone.getValue()).equals("Both")) {
            if (block != -1) {
                this.redStoneSlot = block;
            }
            else {
                this.redStoneSlot = torch;
            }
        }
        this.isTorchSlot = (this.redStoneSlot == torch);
        return this.pistonSlot != -1 && this.obbySlot != -1 && this.crystalSlot != -1 && this.redStoneSlot != -1;
    }
    
    public boolean findTarget() {
        if (((String)this.targetFindType.getValue()).equals("Nearest")) {
            PistonAuraRewrite.target = PlayerUtil.getNearestPlayer((double)(int)this.targetrange.getValue());
        }
        if (((String)this.targetFindType.getValue()).equals("Looking")) {
            PistonAuraRewrite.target = PlayerUtil.findLookingPlayer((double)(int)this.targetrange.getValue());
        }
        return PistonAuraRewrite.target != null;
    }
    
    public void disablePA() {
        if (this.toggle.getValue()) {
            this.disable();
        }
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || PistonAuraRewrite.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                PistonAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                PistonAuraRewrite.mc.player.inventory.currentItem = slot;
                PistonAuraRewrite.mc.playerController.updateController();
            }
        }
    }
    
    static {
        PistonAuraRewrite.target = null;
    }
    
    public static class PistonAuraPos
    {
        public BlockPos crystal;
        public BlockPos piston;
        public BlockPos redstone;
        
        public PistonAuraPos(final BlockPos crystal, final BlockPos piston, final BlockPos redstone) {
            this.crystal = crystal;
            this.piston = piston;
            this.redstone = redstone;
        }
        
        public double range() {
            final double crystalRange = PlayerUtil.getDistanceI(this.crystal);
            final double pistonRange = PlayerUtil.getDistanceI(this.piston);
            return Math.max(pistonRange, crystalRange);
        }
    }
    
    public static class PistonPos
    {
        public BlockPos pos;
        public boolean swap;
        
        public PistonPos(final BlockPos pos) {
            this.pos = pos;
            this.swap = false;
        }
        
        public PistonPos(final BlockPos pos, final boolean swap) {
            this.pos = pos;
            this.swap = swap;
        }
    }
}
