//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.network.play.server.*;
import com.lemonclient.api.util.player.*;
import java.util.function.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import com.lemonclient.client.module.modules.combat.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import java.util.*;

@Module.Declaration(name = "PistonAura", category = Category.Dev)
public class PistonAura extends Module
{
    IntegerSetting preDelay;
    IntegerSetting pistonDelay;
    IntegerSetting crystalDelay;
    IntegerSetting redstoneDelay;
    IntegerSetting breakDelay;
    IntegerSetting destroyDelay;
    ModeSetting targetFindType;
    ModeSetting redstone;
    BooleanSetting breakSync;
    IntegerSetting maxDelay;
    IntegerSetting breakAttempts;
    BooleanSetting packetPlace;
    BooleanSetting packetCrystal;
    BooleanSetting packetBreak;
    BooleanSetting antiWeakness;
    BooleanSetting swingArm;
    BooleanSetting silentSwitch;
    BooleanSetting packetSwitch;
    BooleanSetting toggle;
    BooleanSetting multiThreading;
    IntegerSetting maxY;
    IntegerSetting range;
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
    public int attempts;
    public EnumHand oldhand;
    public Timing preTimer;
    public Timing pistonTimer;
    public Timing crystalTimer;
    public Timing redstoneTimer;
    public Timing breakTimer;
    public Timing destroyTimer;
    public Timing maxTimer;
    public boolean preparedSpace;
    public boolean placedPiston;
    public boolean placedCrystal;
    public boolean placedRedstone;
    public boolean synced;
    public boolean brokeCrystal;
    int oldSlot;
    boolean burrowed;
    boolean moving;
    @EventHandler
    private final Listener<PacketEvent.Receive> packetReceiveListener;
    @EventHandler
    private final Listener<PacketEvent.Receive> PacketReceived;
    
    public PistonAura() {
        this.preDelay = this.registerInteger("Block Delay", 0, 0, 25);
        this.pistonDelay = this.registerInteger("Piston Delay", 0, 0, 25);
        this.crystalDelay = this.registerInteger("Crystal Delay", 0, 0, 25);
        this.redstoneDelay = this.registerInteger("Redstone Delay", 0, 0, 25);
        this.breakDelay = this.registerInteger("Break Delay", 1, 0, 25);
        this.destroyDelay = this.registerInteger("Destroy Delay", 1, 0, 25);
        this.targetFindType = this.registerMode("Target", (List)Arrays.asList("Nearest", "Looking"), "Nearest");
        this.redstone = this.registerMode("Redstone", (List)Arrays.asList("Block", "Torch"), "Block");
        this.breakSync = this.registerBoolean("Break Sync", true);
        this.maxDelay = this.registerInteger("Max Delay", 30, 0, 60);
        this.breakAttempts = this.registerInteger("Break Attempts", 7, 1, 20);
        this.packetPlace = this.registerBoolean("Packet Place", true);
        this.packetCrystal = this.registerBoolean("Packet Crystal", true);
        this.packetBreak = this.registerBoolean("Packet Break", true);
        this.antiWeakness = this.registerBoolean("Anti Weakness", false);
        this.swingArm = this.registerBoolean("Swing Arm", true);
        this.silentSwitch = this.registerBoolean("Switch Back", true);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.toggle = this.registerBoolean("Toggle", true);
        this.multiThreading = this.registerBoolean("Multi Threading", false);
        this.maxY = this.registerInteger("MaxY", 3, 1, 5);
        this.range = this.registerInteger("Range", 6, 0, 10);
        this.pause = this.registerBoolean("Pause When Move", true);
        this.type = this.registerMode("Type", (List)Arrays.asList("Motion", "Pos", "Both"), "Pos");
        this.pause1 = this.registerBoolean("Pause When Burrow", true);
        this.redStonePos = null;
        this.obbySlot = -1;
        this.attempts = 0;
        this.oldhand = null;
        this.maxTimer = new Timing();
        this.brokeCrystal = false;
        this.packetReceiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketSoundEffect) {
                final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
                if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                    final SPacketSoundEffect sPacketSoundEffect;
                    new ArrayList(PistonAura.mc.world.loadedEntityList).forEach(e -> {
                        if (e instanceof EntityEnderCrystal && e.getDistanceSq(sPacketSoundEffect.getX(), sPacketSoundEffect.getY(), sPacketSoundEffect.getZ()) < 36.0) {
                            e.setDead();
                        }
                    });
                }
            }
        }, new Predicate[0]);
        this.PacketReceived = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event != null && event.getPacket() != null && PistonAura.mc.player != null && PistonAura.mc.world != null && event.getPacket() instanceof SPacketSoundEffect) {
                final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
                if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                    removeEntities(packet, 12.0f);
                }
            }
        }, new Predicate[0]);
    }
    
    public void onEnable() {
        this.reset();
    }
    
    public void onUpdate() {
        this.doPA();
    }
    
    public void doPA() {
        this.moving = false;
        this.burrowed = false;
        final String s = (String)this.type.getValue();
        switch (s) {
            case "Motion": {
                if (PistonAura.mc.player.motionX != 0.0 || PistonAura.mc.player.motionZ != 0.0 || PistonAura.mc.player.motionY > -0.078) {
                    this.moving = true;
                    break;
                }
                break;
            }
            case "Pos": {
                if (MotionUtil.isMoving((EntityLivingBase)PistonAura.mc.player)) {
                    this.moving = true;
                    break;
                }
                break;
            }
            case "Both": {
                if (MotionUtil.isMoving((EntityLivingBase)PistonAura.mc.player) || PistonAura.mc.player.motionX != 0.0 || PistonAura.mc.player.motionY > -0.078 || PistonAura.mc.player.motionZ != 0.0) {
                    this.moving = true;
                    break;
                }
                break;
            }
        }
        final BlockPos originalPos = AntiBurrow.getEntityPos((Entity)PistonAura.mc.player);
        if (PistonAura.mc.world.getBlockState(originalPos).getBlock() == Blocks.OBSIDIAN || PistonAura.mc.world.getBlockState(originalPos).getBlock() == Blocks.ENDER_CHEST) {
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
        if (PistonAura.mc.world == null || PistonAura.mc.player == null || PistonAura.mc.player.isDead) {
            return;
        }
        try {
            if (this.setup()) {
                return;
            }
            final BlockPos targetPos = new BlockPos(PistonAura.target.posX, PistonAura.target.posY, PistonAura.target.posZ);
            this.oldSlot = PistonAura.mc.player.inventory.currentItem;
            if (this.preTimer.passedX((double)(int)this.preDelay.getValue()) && !this.preparedSpace) {
                this.preTimer.reset();
                this.pistonTimer.reset();
                this.preparedSpace = this.prepareSpace();
            }
            if (this.pistonTimer.passedX((double)(int)this.pistonDelay.getValue()) && this.preparedSpace && !this.placedPiston) {
                this.crystalTimer.reset();
                this.setItem(this.pistonSlot);
                final float[] angle = MathUtil.calcAngle(new Vec3d((Vec3i)this.crystalPos), new Vec3d((Vec3i)targetPos));
                PistonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0] + 180.0f, angle[1], true));
                SelfAnvil.placeBlock(this.pistonPos, (boolean)this.packetPlace.getValue());
                this.placedPiston = true;
            }
            if (this.crystalTimer.passedX((double)(int)this.crystalDelay.getValue()) && this.placedPiston && !this.placedCrystal) {
                this.redstoneTimer.reset();
                this.setItem(this.crystalSlot);
                final EnumHand hand = (this.crystalSlot != 999) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
                if (this.packetCrystal.getValue()) {
                    PistonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.crystalPos, EnumFacing.DOWN, hand, 0.0f, 0.0f, 0.0f));
                }
                else {
                    PistonAura.mc.playerController.processRightClickBlock(PistonAura.mc.player, PistonAura.mc.world, this.crystalPos, EnumFacing.DOWN, new Vec3d(0.0, 0.0, 0.0), hand);
                }
                this.placedCrystal = true;
            }
            if (this.redstoneTimer.passedX((double)(int)this.redstoneDelay.getValue()) && this.placedCrystal && !this.placedRedstone) {
                this.breakTimer.reset();
                this.setItem(this.redStoneSlot);
                if (((String)this.redstone.getValue()).equalsIgnoreCase("Block")) {
                    EnumFacing facing = null;
                    for (final EnumFacing facing2 : EnumFacing.values()) {
                        if (this.pistonPos.add(facing2.getDirectionVec()).equals((Object)this.redStonePos)) {
                            facing = facing2;
                        }
                    }
                    if (facing == null) {
                        return;
                    }
                    SelfAnvil.rightClickBlock(this.pistonPos, facing, (boolean)this.packetPlace.getValue());
                }
                else {
                    SelfAnvil.placeBlock(this.redStonePos, (boolean)this.packetPlace.getValue());
                }
                this.placedRedstone = true;
            }
            if (this.breakTimer.passedX((double)(int)this.breakDelay.getValue()) && this.placedRedstone && !this.brokeCrystal) {
                this.maxTimer.reset();
                final Entity crystal = (Entity)PistonAura.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> PistonAura.target.getDistance(e) < 5.0f).min(Comparator.comparing(e -> PistonAura.target.getDistance(e))).orElse(null);
                if (crystal == null) {
                    if (this.attempts <= (int)this.breakAttempts.getValue()) {
                        ++this.attempts;
                    }
                    else {
                        this.reset();
                    }
                    this.restoreItem();
                    return;
                }
                final int oldSlot = PistonAura.mc.player.inventory.currentItem;
                if ((boolean)this.antiWeakness.getValue() && PistonAura.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                    int newSlot = -1;
                    for (int i = 0; i < 9; ++i) {
                        final ItemStack stack = PistonAura.mc.player.inventory.getStackInSlot(i);
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
                    PistonAura.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                }
                else {
                    PistonAura.mc.playerController.attackEntity((EntityPlayer)PistonAura.mc.player, crystal);
                }
                if (this.silentSwitch.getValue()) {
                    this.switchTo(oldSlot);
                }
                final EnumHand hand2 = EnumHand.MAIN_HAND;
                if (this.swingArm.getValue()) {
                    PistonAura.mc.player.swingArm(hand2);
                }
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
                    final Entity crystal = (Entity)PistonAura.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> PistonAura.target.getDistance(e) < 5.0f).min(Comparator.comparing(e -> PistonAura.target.getDistance(e))).orElse(null);
                    if (crystal == null) {
                        this.restoreItem();
                        return;
                    }
                    final int oldSlot = PistonAura.mc.player.inventory.currentItem;
                    if ((boolean)this.antiWeakness.getValue() && PistonAura.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                        int newSlot = -1;
                        for (int i = 0; i < 9; ++i) {
                            final ItemStack stack = PistonAura.mc.player.inventory.getStackInSlot(i);
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
                        PistonAura.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                    }
                    else {
                        PistonAura.mc.playerController.attackEntity((EntityPlayer)PistonAura.mc.player, crystal);
                    }
                    if (this.silentSwitch.getValue()) {
                        this.switchTo(oldSlot);
                    }
                    final EnumHand hand2 = EnumHand.MAIN_HAND;
                    if (this.swingArm.getValue()) {
                        PistonAura.mc.player.swingArm(hand2);
                    }
                }
            }
            if (this.destroyTimer.passedX((double)(int)this.destroyDelay.getValue()) && this.synced) {
                this.reset();
            }
            this.restoreItem();
        }
        catch (Exception ex) {}
    }
    
    public static void removeEntities(final SPacketSoundEffect packet, final float range) {
        final BlockPos pos = new BlockPos(packet.getX(), packet.getY(), packet.getZ());
        final ArrayList<Entity> toRemove = new ArrayList<Entity>();
        for (final Entity entity : PistonAura.mc.world.loadedEntityList) {
            if (entity instanceof EntityEnderCrystal) {
                if (entity.getDistanceSq(pos) > MathUtil.square(range)) {
                    continue;
                }
                toRemove.add(entity);
            }
        }
        for (final Entity entity : toRemove) {
            entity.setDead();
        }
    }
    
    void switchTo(final int slot) {
        if (PistonAura.mc.player.inventory.currentItem != slot && slot > -1 && slot < 9) {
            PistonAura.mc.player.inventory.currentItem = slot;
            PistonAura.mc.playerController.updateController();
        }
    }
    
    public boolean setup() {
        if (!this.findMaterials()) {
            if (this.toggle.getValue()) {
                this.disable();
            }
            return true;
        }
        if (!this.findTarget()) {
            if (this.toggle.getValue()) {
                this.disable();
            }
            return true;
        }
        if (this.pistonPos == null && !this.findSpace(PistonAura.target)) {
            if (this.toggle.getValue()) {
                this.disable();
            }
            return true;
        }
        return false;
    }
    
    public boolean prepareSpace() {
        final BlockPos targetPos = new BlockPos(PistonAura.target.posX, PistonAura.target.posY, PistonAura.target.posZ);
        final BlockPos piston = this.pistonPos.add(0, -1, 0);
        final BlockPos redstone = this.redStonePos.add(0, -1, 0);
        if (BlockUtil.getBlock(piston) == Blocks.AIR) {
            final BlockPos offset = new BlockPos(this.crystalPos.getX() - targetPos.getX(), 0, this.crystalPos.getZ() - targetPos.getZ());
            final BlockPos crystalOffset = this.crystalPos.add((Vec3i)offset);
            final BlockPos crystalOffset2 = crystalOffset.add((Vec3i)offset);
            if (BlockUtil.hasNeighbour(piston)) {
                this.setItem(this.obbySlot);
                SelfAnvil.placeBlock(piston, (boolean)this.packetPlace.getValue());
                return false;
            }
            if (BlockUtil.getBlock(crystalOffset) == Blocks.AIR) {
                this.setItem(this.obbySlot);
                SelfAnvil.placeBlock(crystalOffset, (boolean)this.packetPlace.getValue());
                return false;
            }
            if (BlockUtil.getBlock(crystalOffset2) == Blocks.AIR) {
                this.setItem(this.obbySlot);
                SelfAnvil.placeBlock(crystalOffset2, (boolean)this.packetPlace.getValue());
                return false;
            }
        }
        if (BlockUtil.getBlock(redstone) == Blocks.AIR && (this.pistonPos.getX() != this.redStonePos.getX() || this.pistonPos.getZ() != this.redStonePos.getZ())) {
            this.setItem(this.obbySlot);
            SelfAnvil.placeBlock(redstone, (boolean)this.packetPlace.getValue());
            return false;
        }
        return true;
    }
    
    public boolean findSpace(final EntityPlayer target) {
        final BlockPos mypos = new BlockPos(PistonAura.mc.player.posX, PistonAura.mc.player.posY, PistonAura.mc.player.posZ);
        final BlockPos targetPos = new BlockPos(target.posX, target.posY, target.posZ);
        final BlockPos[] offsets = { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        final List<AuraPos> auraPos = new ArrayList<AuraPos>();
        for (int y = 0; y <= (int)this.maxY.getValue(); ++y) {
            for (final BlockPos offset : offsets) {
                final BlockPos crystalPos = targetPos.add((Vec3i)offset.add(0, y, 0));
                if (BlockUtil.getBlock(crystalPos) == Blocks.OBSIDIAN || BlockUtil.getBlock(crystalPos) == Blocks.BEDROCK) {
                    if (BlockUtil.getBlock(crystalPos.add(0, 1, 0)) == Blocks.AIR) {
                        if (BlockUtil.getBlock(crystalPos.add(0, 2, 0)) == Blocks.AIR) {
                            if (!this.checkPos(crystalPos)) {
                                final BlockPos normal = crystalPos.add((Vec3i)offset);
                                final BlockPos oneBlock = normal.add((Vec3i)offset);
                                final BlockPos side0 = normal.add(offset.getZ(), offset.getY(), offset.getX());
                                final BlockPos side2 = normal.add(offset.getZ() * -1, offset.getY(), offset.getX() * -1);
                                final BlockPos side3 = oneBlock.add(offset.getZ(), offset.getY(), offset.getX());
                                final BlockPos side4 = oneBlock.add(offset.getZ() * -1, offset.getY(), offset.getX() * -1);
                                final List<PistonPos> pistons = new ArrayList<PistonPos>();
                                this.add(pistons, new PistonPos(normal, false));
                                this.add(pistons, new PistonPos(oneBlock, true));
                                this.add(pistons, new PistonPos(side0, false));
                                this.add(pistons, new PistonPos(side2, false));
                                this.add(pistons, new PistonPos(side3, true));
                                this.add(pistons, new PistonPos(side4, true));
                                final BlockPos blockPos;
                                final BlockPos headPos;
                                final BlockPos blockPos2;
                                final PistonPos piston = pistons.stream().filter(p -> BlockUtil.getBlock(p.pos) == Blocks.AIR).filter(p -> {
                                    headPos = p.pos.add(blockPos.getX() * -1, 0, blockPos.getZ() * -1);
                                    return BlockUtil.getBlock(headPos) == Blocks.AIR && !this.checkPos(headPos);
                                }).filter(p -> this.getRedStonePos(crystalPos, p, offset) != null).filter(p -> !this.checkPos(p.pos)).filter(p -> PlayerUtil.getDistanceI(p.pos) <= (int)this.range.getValue()).filter(p -> blockPos2.getDistance(p.pos.getX(), p.pos.getY(), p.pos.getZ()) >= 3.6 + (p.pos.getY() - (blockPos2.getY() + 1)) || p.pos.getY() <= blockPos2.getY() + 1).min(Comparator.comparing(p -> PlayerUtil.getDistanceI(p.pos))).orElse(null);
                                if (piston != null) {
                                    auraPos.add(new AuraPos(piston.pos, crystalPos, this.getRedStonePos(crystalPos, piston, offset)));
                                }
                            }
                        }
                    }
                }
            }
        }
        final AuraPos best = auraPos.stream().min(Comparator.comparing((Function<? super AuraPos, ? extends Comparable>)AuraPos::range)).orElse(null);
        if (best == null) {
            return false;
        }
        this.pistonPos = best.piston;
        this.crystalPos = best.crystal;
        this.redStonePos = best.redstone;
        return true;
    }
    
    public BlockPos getRedStonePos(final BlockPos crystalPos, final PistonPos piston, final BlockPos offset) {
        final BlockPos pistonPos = piston.pos;
        if (((String)this.redstone.getValue()).equalsIgnoreCase("Block")) {
            final List<BlockPos> redstone = new ArrayList<BlockPos>();
            redstone.add(pistonPos.add((Vec3i)offset));
            if (piston.allowUpside) {
                redstone.add(pistonPos.add(0, 1, 0));
            }
            return redstone.stream().filter(p -> BlockUtil.getBlock(p) == Blocks.AIR).filter(p -> !this.checkPos(p)).min(Comparator.comparing((Function<? super BlockPos, ? extends Comparable>)PlayerUtil::getDistance)).orElse(null);
        }
        if (((String)this.redstone.getValue()).equalsIgnoreCase("Torch")) {
            final BlockPos[] offsets = { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
            final List<BlockPos> torchs = new ArrayList<BlockPos>();
            for (final BlockPos offs : offsets) {
                final BlockPos torch = pistonPos.add((Vec3i)offs);
                final BlockPos pistonP = pistonPos.add(offset.getX() * -1, 0, offset.getZ() * -1);
                if (torch.getX() != crystalPos.getX() || torch.getZ() != crystalPos.getZ()) {
                    if (torch.getX() != pistonP.getX() || torch.getZ() != pistonP.getZ()) {
                        if (BlockUtil.getBlock(torch) == Blocks.AIR) {
                            if (!this.checkPos(torch)) {
                                torchs.add(torch);
                            }
                        }
                    }
                }
            }
            return torchs.stream().min(Comparator.comparing((Function<? super BlockPos, ? extends Comparable>)PlayerUtil::getDistance)).orElse(null);
        }
        return null;
    }
    
    public void add(final List<PistonPos> pistons, final PistonPos pos) {
        pistons.add(new PistonPos(pos.pos.add(0, 1, 0), pos.allowUpside));
        pistons.add(new PistonPos(pos.pos.add(0, 2, 0), true));
    }
    
    public static int findHotbarBlock(final Block blockIn) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = PistonAura.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock && ((ItemBlock)stack.getItem()).getBlock() == blockIn) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean findTarget() {
        if (((String)this.targetFindType.getValue()).equals("Nearest")) {
            PistonAura.target = PlayerUtil.getNearestPlayer((double)(int)this.range.getValue());
        }
        else if (((String)this.targetFindType.getValue()).equals("Looking")) {
            PistonAura.target = PlayerUtil.findLookingPlayer((double)(int)this.range.getValue());
        }
        return PistonAura.target != null;
    }
    
    public boolean findMaterials() {
        this.pistonSlot = findHotbarBlock((Block)Blocks.PISTON);
        this.obbySlot = findHotbarBlock(Blocks.OBSIDIAN);
        this.crystalSlot = AutoCev.getItemHotbar(Items.END_CRYSTAL);
        if (this.pistonSlot == -1) {
            this.pistonSlot = findHotbarBlock((Block)Blocks.STICKY_PISTON);
        }
        if (PistonAura.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            this.crystalSlot = 999;
        }
        final int block = findHotbarBlock(Blocks.REDSTONE_BLOCK);
        final int torch = findHotbarBlock(Blocks.REDSTONE_TORCH);
        if (((String)this.redstone.getValue()).equalsIgnoreCase("Block")) {
            this.redStoneSlot = block;
        }
        if (((String)this.redstone.getValue()).equalsIgnoreCase("Torch")) {
            this.redStoneSlot = torch;
        }
        return this.pistonSlot != -1 && this.obbySlot != -1 && this.crystalSlot != -1 && this.redStoneSlot != -1;
    }
    
    public void reset() {
        PistonAura.target = null;
        this.pistonPos = null;
        this.crystalPos = null;
        this.redStonePos = null;
        this.pistonSlot = -1;
        this.crystalSlot = -1;
        this.redStoneSlot = -1;
        this.obbySlot = -1;
        this.oldhand = null;
        this.preTimer = new Timing();
        this.pistonTimer = new Timing();
        this.crystalTimer = new Timing();
        this.redstoneTimer = new Timing();
        this.breakTimer = new Timing();
        this.destroyTimer = new Timing();
        this.maxTimer = new Timing();
        this.preparedSpace = false;
        this.placedPiston = false;
        this.placedCrystal = false;
        this.placedRedstone = false;
        this.brokeCrystal = false;
        this.synced = false;
        this.attempts = 0;
    }
    
    public boolean checkPos(final BlockPos pos) {
        final BlockPos mypos = new BlockPos(PistonAura.mc.player.posX, PistonAura.mc.player.posY, PistonAura.mc.player.posZ);
        return pos.getX() == mypos.getX() && pos.getZ() == mypos.getZ();
    }
    
    public void setItem(final int slot) {
        if (slot == 999) {
            return;
        }
        if (this.silentSwitch.getValue()) {
            this.oldhand = null;
            if (PistonAura.mc.player.isHandActive()) {
                this.oldhand = PistonAura.mc.player.getActiveHand();
            }
        }
        if (this.packetSwitch.getValue()) {
            PistonAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            PistonAura.mc.player.inventory.currentItem = slot;
            PistonAura.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.silentSwitch.getValue()) {
            if (this.oldhand != null) {
                PistonAura.mc.player.setActiveHand(this.oldhand);
            }
            if (this.packetSwitch.getValue()) {
                PistonAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldSlot));
            }
            else {
                PistonAura.mc.player.inventory.currentItem = this.oldSlot;
                PistonAura.mc.playerController.updateController();
            }
            this.oldhand = null;
        }
    }
    
    static {
        PistonAura.target = null;
    }
    
    public static class AuraPos
    {
        public BlockPos piston;
        public BlockPos crystal;
        public BlockPos redstone;
        
        public AuraPos(final BlockPos piston, final BlockPos crystal, final BlockPos redstone) {
            this.piston = piston;
            this.crystal = crystal;
            this.redstone = redstone;
        }
        
        public double range() {
            final double pistonRange = PlayerUtil.getDistanceI(this.piston);
            final double crystalRange = PlayerUtil.getDistanceI(this.crystal);
            final double redstoneRange = PlayerUtil.getDistanceI(this.redstone);
            return Math.max(Math.max(pistonRange, crystalRange), redstoneRange);
        }
    }
    
    public static class PistonPos
    {
        public BlockPos pos;
        public boolean allowUpside;
        
        public PistonPos(final BlockPos pos, final boolean allowUpside) {
            this.pos = pos;
            this.allowUpside = allowUpside;
        }
    }
}
