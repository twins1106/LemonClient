//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import com.lemonclient.api.util.world.combat.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;

@Module.Declaration(name = "SmartAntiCity", category = Category.Combat)
public class SmartAntiCity extends Module
{
    IntegerSetting delay;
    IntegerSetting bpt;
    BooleanSetting center;
    BooleanSetting rotate;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting packetBreak;
    BooleanSetting antiWeakness;
    BooleanSetting packetswitch;
    int placed;
    int waited;
    int obsidian;
    private BlockPos startPos;
    List<BlockPos> posList;
    BlockPos[] sides;
    BlockPos[] high;
    
    public SmartAntiCity() {
        this.delay = this.registerInteger("Delay", 0, 0, 20);
        this.bpt = this.registerInteger("BlocksPerTick", 4, 0, 20);
        this.center = this.registerBoolean("TPCenter", true);
        this.rotate = this.registerBoolean("Rotate", true);
        this.packet = this.registerBoolean("Packet", true);
        this.swing = this.registerBoolean("Swing", true);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.packetBreak = this.registerBoolean("Packet Break", false);
        this.antiWeakness = this.registerBoolean("Anti Weakness", true);
        this.packetswitch = this.registerBoolean("Silent Switch", true);
        this.posList = new ArrayList<BlockPos>();
        this.sides = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) };
        this.high = new BlockPos[] { new BlockPos(0, 0, 0), new BlockPos(0, 1, 0) };
    }
    
    public void onEnable() {
        this.posList.clear();
        this.startPos = PlayerUtil.getPlayerPos();
        this.startPos = new BlockPos((double)this.startPos.x, this.startPos.y + 0.55, (double)this.startPos.z);
        if (this.center.getValue()) {
            PlayerUtil.centerPlayer(BlockUtil.getCenterOfBlock(SmartAntiCity.mc.player.posX, SmartAntiCity.mc.player.posY, SmartAntiCity.mc.player.posZ));
        }
    }
    
    public void onUpdate() {
        if (SmartAntiCity.mc.world == null || SmartAntiCity.mc.player == null || SmartAntiCity.mc.player.isDead) {
            this.startPos = null;
            this.disable();
            return;
        }
        if (this.waited++ < (int)this.delay.getValue()) {
            return;
        }
        final BlockPos playerPos = PlayerUtil.getPlayerPos();
        if (this.startPos == null || !this.startPos.equals((Object)playerPos)) {
            this.disable();
            return;
        }
        this.obsidian = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
        if (this.obsidian == -1) {
            return;
        }
        this.waited = 0;
        this.placed = 0;
        final Vec3d a = SmartAntiCity.mc.player.getPositionVector();
        final BlockPos pos = new BlockPos(SmartAntiCity.mc.player.posX, SmartAntiCity.mc.player.posY, SmartAntiCity.mc.player.posZ);
        for (final BlockPos side : this.sides) {
            if (this.placed >= (int)this.bpt.getValue()) {
                break;
            }
            for (final BlockPos add : this.high) {
                final BlockPos blockPos = side.add((Vec3i)add);
                final BlockPos doubleSide = side.add((Vec3i)side);
                final BlockPos doubleSideAdded = doubleSide.add((Vec3i)add);
                final int x = blockPos.x;
                final int y = blockPos.y;
                final int z = blockPos.z;
                final int x2 = doubleSideAdded.x;
                final int y2 = doubleSideAdded.y;
                final int z2 = doubleSideAdded.z;
                if (this.checkCrystal(a, EntityUtil.getVarOffsets(x, y, z)) != null && this.checkCrystal(a, EntityUtil.getVarOffsets(doubleSideAdded.x, doubleSideAdded.y, doubleSideAdded.z)) != null) {
                    this.breakCrystal(this.checkCrystal(a, EntityUtil.getVarOffsets(x, y, z)));
                    this.place(a, EntityUtil.getVarOffsets(x, y, z));
                }
                if (this.placed >= (int)this.bpt.getValue()) {
                    break;
                }
                if (this.getBlock(pos.add((Vec3i)side)).getBlock() == Blocks.AIR && this.getBlock(pos.add((Vec3i)doubleSide)).getBlock() == Blocks.AIR && this.getBlock(pos.add((Vec3i)doubleSideAdded)).getBlock() == Blocks.AIR) {
                    if (this.checkCrystal(a, EntityUtil.getVarOffsets(x2, y2, z2)) != null) {
                        this.breakCrystal(this.checkCrystal(a, EntityUtil.getVarOffsets(x2, y2, z2)));
                    }
                    if (!this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)doubleSideAdded))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)doubleSide)))) {
                        this.perform(pos.add((Vec3i)side));
                        this.perform(pos.add((Vec3i)doubleSide));
                        this.perform(pos.add((Vec3i)doubleSideAdded));
                    }
                }
            }
            final BlockPos sideUp = side.up();
            final BlockPos doubleSide2 = side.add((Vec3i)side);
            final BlockPos doubleSideUp = doubleSide2.up();
            final int sideX = side.x;
            final int sideY = side.y;
            final int sideZ = side.z;
            final int doubleSideX = doubleSideUp.x;
            final int doubleSideY = doubleSideUp.y;
            final int doubleSideZ = doubleSideUp.z;
            if (this.placed >= (int)this.bpt.getValue()) {
                break;
            }
            if (this.getBlock(pos.add((Vec3i)side)).getBlock() == Blocks.AIR && this.getBlock(pos.add((Vec3i)side.down())).getBlock() == Blocks.AIR) {
                if (this.checkCrystal(a, EntityUtil.getVarOffsets(sideX, sideY, sideZ)) != null) {
                    this.breakCrystal(this.checkCrystal(a, EntityUtil.getVarOffsets(sideX, sideY, sideZ)));
                }
                if (!this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side).down())) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side).down(2)))) {
                    this.perform(pos.add((Vec3i)side.down()));
                    this.perform(pos.add((Vec3i)side));
                }
            }
            if (this.placed >= (int)this.bpt.getValue()) {
                break;
            }
            if (this.getBlock(pos.add((Vec3i)side)).getBlock() == Blocks.AIR && this.getBlock(pos.add((Vec3i)doubleSide2)).getBlock() == Blocks.AIR) {
                if (this.checkCrystal(a, EntityUtil.getVarOffsets(doubleSideX, doubleSideY, doubleSideZ)) != null) {
                    this.breakCrystal(this.checkCrystal(a, EntityUtil.getVarOffsets(doubleSideX, doubleSideY, doubleSideZ)));
                }
                if (!this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side).down())) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)doubleSide2))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)doubleSide2).down()))) {
                    if (this.checkCrystal(a, EntityUtil.getVarOffsets(doubleSideX, doubleSideY, doubleSideZ)) != null && this.checkCrystal(a, EntityUtil.getVarOffsets(sideX, sideY, sideZ)) != null) {
                        this.breakCrystal(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 2)));
                    }
                    this.perform(pos.add((Vec3i)side));
                    this.perform(pos.add((Vec3i)doubleSide2));
                    this.perform(pos.add((Vec3i)doubleSide2).down());
                    this.perform(pos.add((Vec3i)doubleSide2.add((Vec3i)side)));
                }
            }
            if (this.placed >= (int)this.bpt.getValue()) {
                break;
            }
            final int xy = sideX + sideZ;
            if (this.getBlock(pos.add((Vec3i)side)).getBlock() == Blocks.AIR && this.getBlock(pos.add((Vec3i)side.up())).getBlock() == Blocks.AIR) {
                if (!this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side).down()))) {
                    if (!this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)sideUp)))) {
                        this.perform(pos.add((Vec3i)side));
                        this.perform(pos.add((Vec3i)sideUp));
                        this.perform(pos.add(xy, 1, xy));
                        this.perform(pos.add((Vec3i)doubleSideUp));
                    }
                }
                else if (this.getBlock(pos.add((Vec3i)doubleSide2)).getBlock() == Blocks.AIR && this.getBlock(pos.add((Vec3i)doubleSideUp)).getBlock() == Blocks.AIR) {
                    this.perform(pos.add((Vec3i)doubleSide2));
                    this.perform(pos.add((Vec3i)doubleSideUp));
                    if (side.x == 0) {
                        this.perform(pos.add(1, 0, doubleSideZ));
                        this.perform(pos.add(1, 1, doubleSideZ));
                    }
                    else {
                        this.perform(pos.add(doubleSideX, 0, 1));
                        this.perform(pos.add(doubleSideX, 1, 1));
                    }
                }
            }
            if (this.placed >= (int)this.bpt.getValue()) {
                break;
            }
            if (this.getBlock(pos.add((Vec3i)side)).getBlock() == Blocks.AIR && this.getBlock(pos.add(xy, 0, xy)).getBlock() == Blocks.AIR) {
                if (this.checkCrystal(a, EntityUtil.getVarOffsets(xy, 0, xy)) != null) {
                    this.breakCrystal(this.checkCrystal(a, EntityUtil.getVarOffsets(xy, 0, xy)));
                }
                if (!this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side).down())) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add(xy, -1, xy))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add(xy, 0, xy)))) {
                    this.perform(pos.add((Vec3i)side));
                    this.perform(pos.add(xy, 0, xy));
                }
            }
            final int x3 = (sideX == 0) ? (-sideZ) : sideX;
            final int z3 = (sideZ == 0) ? (-sideX) : sideZ;
            if (this.placed >= (int)this.bpt.getValue()) {
                break;
            }
            if (this.getBlock(pos.add((Vec3i)side)).getBlock() == Blocks.AIR && this.getBlock(pos.add(x3, 0, z3)).getBlock() == Blocks.AIR) {
                if (this.checkCrystal(a, EntityUtil.getVarOffsets(x3, 0, z3)) != null) {
                    this.breakCrystal(this.checkCrystal(a, EntityUtil.getVarOffsets(x3, 0, z3)));
                }
                if (!this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side).down())) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add(x3, -1, z3))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add(x3, 0, z3)))) {
                    this.perform(pos.add((Vec3i)side));
                    this.perform(pos.add(x3, 0, z3));
                }
            }
            if (this.placed >= (int)this.bpt.getValue()) {
                break;
            }
            if (this.getBlock(pos.add((Vec3i)side)).getBlock() == Blocks.AIR && this.getBlock(pos.add(xy, 0, xy)).getBlock() == Blocks.AIR && this.getBlock(pos.add(xy, 1, xy)).getBlock() == Blocks.AIR && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add(xy, 1, xy))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add(xy, 0, xy))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side).down()))) {
                this.perform(pos.add((Vec3i)side));
                this.perform(pos.add(xy, 0, xy));
                this.perform(pos.add(xy, 1, xy));
            }
            if (this.placed >= (int)this.bpt.getValue()) {
                break;
            }
            if (this.getBlock(pos.add((Vec3i)side)).getBlock() == Blocks.AIR && this.getBlock(pos.add(x3, 0, z3)).getBlock() == Blocks.AIR && this.getBlock(pos.add(x3, 1, z3)).getBlock() == Blocks.AIR && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add(x3, 1, z3))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add(x3, 0, z3))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side))) && !this.intersectsWithEntity(new BlockPos((Vec3i)pos.add((Vec3i)side).down()))) {
                this.perform(pos.add((Vec3i)side));
                this.perform(pos.add(x3, 0, z3));
                this.perform(pos.add(x3, 1, z3));
            }
        }
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || SmartAntiCity.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                SmartAntiCity.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                SmartAntiCity.mc.player.inventory.currentItem = slot;
            }
            SmartAntiCity.mc.playerController.updateController();
        }
    }
    
    Entity checkCrystal(final Vec3d pos, final Vec3d[] list) {
        Entity crystal = null;
        for (final Vec3d vec3d : list) {
            final BlockPos position = new BlockPos(pos).add(vec3d.x, vec3d.y, vec3d.z);
            for (final Entity entity : SmartAntiCity.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(position))) {
                if (entity instanceof EntityEnderCrystal && crystal == null) {
                    crystal = entity;
                }
            }
        }
        return crystal;
    }
    
    private void breakCrystal(final Entity crystal) {
        if (crystal == null) {
            return;
        }
        final int oldSlot = SmartAntiCity.mc.player.inventory.currentItem;
        if ((boolean)this.antiWeakness.getValue() && SmartAntiCity.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = SmartAntiCity.mc.player.inventory.getStackInSlot(i);
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
    
    private IBlockState getBlock(final BlockPos block) {
        return SmartAntiCity.mc.world.getBlockState(block);
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : SmartAntiCity.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    private void place(final Vec3d pos, final Vec3d[] list) {
        for (final Vec3d vec3d : list) {
            if (this.placed >= (int)this.bpt.getValue()) {
                return;
            }
            final BlockPos position = new BlockPos(pos).add(vec3d.x, vec3d.y, vec3d.z);
            this.perform(position);
        }
    }
    
    private void perform(final BlockPos pos) {
        if (this.placed >= (int)this.bpt.getValue()) {
            return;
        }
        if (!SmartAntiCity.mc.world.isAirBlock(pos)) {
            return;
        }
        if (this.intersectsWithEntity(pos)) {
            return;
        }
        final int old = SmartAntiCity.mc.player.inventory.currentItem;
        this.switchTo(this.obsidian);
        BurrowUtil.placeBlock(pos, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
        this.switchTo(old);
        ++this.placed;
    }
}
