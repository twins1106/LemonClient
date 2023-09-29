//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.*;
import java.util.function.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import com.lemonclient.api.event.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.client.manager.managers.*;
import net.minecraft.util.math.*;

@Module.Declaration(name = "AutoSkull", category = Category.Combat)
public class AutoSkull extends Module
{
    BooleanSetting rotate;
    BooleanSetting onShift;
    BooleanSetting instaActive;
    BooleanSetting disableAfter;
    BooleanSetting forceRotation;
    BooleanSetting noUp;
    BooleanSetting onlyHoles;
    IntegerSetting tickDelay;
    IntegerSetting preSwitch;
    IntegerSetting afterSwitch;
    DoubleSetting playerDistance;
    BooleanSetting autoTrap;
    IntegerSetting BlocksPerTick;
    private static final Vec3d[] AIR;
    private int delayTimeTicks;
    private boolean noObby;
    private boolean activedBefore;
    private int oldSlot;
    private Vec3d lastHitVec;
    private int preRotationTick;
    private int afterRotationTick;
    private boolean alrPlaced;
    @EventHandler
    private final Listener<OnUpdateWalkingPlayerEvent> onUpdateWalkingPlayerEventListener;
    private boolean firstShift;
    private final ArrayList<EnumFacing> exd;
    
    public AutoSkull() {
        this.rotate = this.registerBoolean("Rotate", true);
        this.onShift = this.registerBoolean("On Shift", false);
        this.instaActive = this.registerBoolean("Insta Active", true);
        this.disableAfter = this.registerBoolean("Disable After", true);
        this.forceRotation = this.registerBoolean("Force Rotation", false);
        this.noUp = this.registerBoolean("No Up", false);
        this.onlyHoles = this.registerBoolean("Only Holes", false);
        this.tickDelay = this.registerInteger("Tick Delay", 5, 0, 10);
        this.preSwitch = this.registerInteger("Pre Switch", 0, 0, 20);
        this.afterSwitch = this.registerInteger("After Switch", 0, 0, 20);
        this.playerDistance = this.registerDouble("Player Distance", 0.0, 0.0, 6.0);
        this.autoTrap = this.registerBoolean("AutoTrap", false);
        this.BlocksPerTick = this.registerInteger("Blocks Per Tick", 4, 0, 10);
        this.delayTimeTicks = 0;
        this.lastHitVec = new Vec3d(-1.0, -1.0, -1.0);
        this.onUpdateWalkingPlayerEventListener = (Listener<OnUpdateWalkingPlayerEvent>)new Listener(event -> {
            if (event.getPhase() != Phase.PRE || !(boolean)this.rotate.getValue() || this.lastHitVec == null || !(boolean)this.forceRotation.getValue()) {
                return;
            }
            final Vec2f rotation = RotationUtil.getRotationTo(this.lastHitVec);
            final PlayerPacket packet = new PlayerPacket((Module)this, rotation);
            PlayerPacketManager.INSTANCE.addPacket(packet);
        }, new Predicate[0]);
        this.exd = new ArrayList<EnumFacing>() {
            {
                this.add(EnumFacing.DOWN);
                this.add(EnumFacing.UP);
            }
        };
    }
    
    public void onEnable() {
        SpoofRotationUtil.ROTATION_UTIL.onEnable();
        PlacementUtil.onEnable();
        if (AutoSkull.mc.player == null) {
            this.disable();
            return;
        }
        final boolean noObby = false;
        this.alrPlaced = noObby;
        this.firstShift = noObby;
        this.noObby = noObby;
        this.lastHitVec = null;
        final int n = 0;
        this.afterRotationTick = n;
        this.preRotationTick = n;
    }
    
    public void onDisable() {
        SpoofRotationUtil.ROTATION_UTIL.onDisable();
        PlacementUtil.onDisable();
        if (AutoSkull.mc.player == null) {
            return;
        }
        if (this.noObby) {
            this.setDisabledMessage("Skull not found... Blocker turned " + ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getDisabledColor() + "OFF" + ChatFormatting.GRAY + "!");
        }
    }
    
    public void onUpdate() {
        if (AutoSkull.mc.player == null) {
            this.disable();
            return;
        }
        if (this.noObby) {
            this.disable();
            return;
        }
        if (this.delayTimeTicks < (int)this.tickDelay.getValue()) {
            ++this.delayTimeTicks;
        }
        else {
            this.delayTimeTicks = 0;
            if ((boolean)this.onlyHoles.getValue() && HoleUtil.isHole(EntityUtil.getPosition((Entity)AutoSkull.mc.player), true, true, false).getType() == HoleUtil.HoleType.NONE) {
                return;
            }
            SpoofRotationUtil.ROTATION_UTIL.shouldSpoofAngles(true);
            if ((boolean)this.autoTrap.getValue() && BlockUtil.getBlock(new BlockPos((Vec3i)AutoSkull.mc.player.getPosition().add(0.0, 0.4, 0.0))) instanceof BlockSkull) {
                final EntityPlayer closest = PlayerUtil.findClosestTarget(2.0, (EntityPlayer)null);
                if (closest != null && (int)closest.posX == (int)AutoSkull.mc.player.posX && (int)closest.posZ == (int)AutoSkull.mc.player.posZ && closest.posY > AutoSkull.mc.player.posY && closest.posY < AutoSkull.mc.player.posY + 2.0) {
                    for (int blocksPlaced = 0, offsetSteps = 0; blocksPlaced <= (int)this.BlocksPerTick.getValue() && offsetSteps < 10; ++offsetSteps) {
                        final BlockPos offsetPos = new BlockPos(AutoSkull.AIR[offsetSteps]);
                        final BlockPos targetPos = AutoSkull.mc.player.getPosition().add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
                        if (this.placeBlock(targetPos)) {
                            ++blocksPlaced;
                        }
                    }
                }
            }
            if (this.instaActive.getValue()) {
                this.placeBlock();
                return;
            }
            if ((boolean)this.onShift.getValue() && AutoSkull.mc.gameSettings.keyBindSneak.isKeyDown()) {
                if (!this.firstShift) {
                    this.placeBlock();
                }
                return;
            }
            if (this.firstShift && !AutoSkull.mc.gameSettings.keyBindSneak.isKeyDown()) {
                this.firstShift = false;
            }
            if ((double)this.playerDistance.getValue() != 0.0 && PlayerUtil.findClosestTarget((double)this.playerDistance.getValue(), (EntityPlayer)null) != null) {
                this.placeBlock();
            }
        }
    }
    
    private boolean placeBlock(final BlockPos pos) {
        final EnumHand handSwing = EnumHand.MAIN_HAND;
        final int obsidianSlot = InventoryUtil.findObsidianSlot(false, false);
        if (AutoSkull.mc.player.inventory.currentItem != obsidianSlot && obsidianSlot != 9) {
            AutoSkull.mc.player.inventory.currentItem = obsidianSlot;
        }
        return PlacementUtil.place(pos, handSwing, (boolean)this.rotate.getValue(), true);
    }
    
    private void placeBlock() {
        if (AutoSkull.mc.player.onGround) {
            final BlockPos pos = new BlockPos(AutoSkull.mc.player.posX, AutoSkull.mc.player.posY + 0.4, AutoSkull.mc.player.posZ);
            if (BlockUtil.getBlock(pos) instanceof BlockAir) {
                final EnumHand handSwing = EnumHand.MAIN_HAND;
                final int skullSlot = InventoryUtil.findSkullSlot(false, this.activedBefore);
                if (skullSlot == -1) {
                    this.noObby = true;
                    return;
                }
                if (skullSlot == 9) {
                    this.activedBefore = true;
                    return;
                }
                if (AutoSkull.mc.player.inventory.currentItem != skullSlot && skullSlot != 9) {
                    this.oldSlot = AutoSkull.mc.player.inventory.currentItem;
                    AutoSkull.mc.player.inventory.currentItem = skullSlot;
                }
                if ((int)this.preSwitch.getValue() > 0 && this.preRotationTick++ == (int)this.preSwitch.getValue()) {
                    this.lastHitVec = new Vec3d((double)pos.x, (double)pos.y, (double)pos.z);
                    return;
                }
                Label_0311: {
                    if (!this.alrPlaced) {
                        if (this.noUp.getValue()) {
                            if (PlacementUtil.place(pos, handSwing, (boolean)this.rotate.getValue(), (ArrayList)this.exd)) {
                                break Label_0311;
                            }
                            if (PlacementUtil.place(pos, handSwing, (boolean)this.rotate.getValue())) {
                                break Label_0311;
                            }
                        }
                        else if (PlacementUtil.place(pos, handSwing, (boolean)this.rotate.getValue())) {
                            break Label_0311;
                        }
                        this.lastHitVec = null;
                        return;
                    }
                }
                this.alrPlaced = true;
                if ((int)this.afterSwitch.getValue() > 0 && this.afterRotationTick++ == (int)this.afterSwitch.getValue()) {
                    this.lastHitVec = new Vec3d((double)pos.x, (double)pos.y, (double)pos.z);
                    return;
                }
                if (this.oldSlot != -1) {
                    AutoSkull.mc.player.inventory.currentItem = this.oldSlot;
                    this.oldSlot = -1;
                }
                this.firstShift = true;
                final boolean b = false;
                this.alrPlaced = b;
                this.activedBefore = b;
                if (this.disableAfter.getValue()) {
                    this.disable();
                }
                final int n = 0;
                this.afterRotationTick = n;
                this.preRotationTick = n;
                this.lastHitVec = null;
            }
        }
    }
    
    static {
        AIR = new Vec3d[] { new Vec3d(-1.0, -1.0, -1.0), new Vec3d(-1.0, 0.0, -1.0), new Vec3d(-1.0, 1.0, -1.0), new Vec3d(-1.0, 2.0, -1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(1.0, 2.0, 1.0), new Vec3d(0.0, 2.0, 1.0) };
    }
}
