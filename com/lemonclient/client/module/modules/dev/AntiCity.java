//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;

@Module.Declaration(name = "AntiCity", category = Category.Dev)
public class AntiCity extends Module
{
    BooleanSetting echest;
    BooleanSetting smart;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting rotate;
    IntegerSetting delay;
    IntegerSetting blockpertick;
    List<BlockPos> posList;
    List<BlockPos> SposList;
    BlockPos[] offset;
    boolean inHole;
    boolean breaked;
    int slot;
    int placed;
    int waited;
    BlockPos playerPos;
    
    public AntiCity() {
        this.echest = this.registerBoolean("Ender Chest", false);
        this.smart = this.registerBoolean("Smart Mode", false);
        this.packet = this.registerBoolean("Packet Place", true);
        this.swing = this.registerBoolean("Swing", true);
        this.rotate = this.registerBoolean("Rotate", true);
        this.delay = this.registerInteger("Delay", 0, 0, 20);
        this.blockpertick = this.registerInteger("BlocksPerTick", 4, 0, 20);
        this.posList = new ArrayList<BlockPos>();
        this.SposList = new ArrayList<BlockPos>();
        this.offset = new BlockPos[] { new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0) };
    }
    
    public static boolean isMoving(final EntityLivingBase entity) {
        return entity.moveForward != 0.0f || entity.moveStrafing != 0.0f || entity.moveVertical != 0.0f || entity.motionY > 0.0;
    }
    
    public void onUpdate() {
        if (AntiCity.mc.world == null || AntiCity.mc.player == null || AntiCity.mc.player.isDead) {
            return;
        }
        ++this.waited;
        if (this.waited < (int)this.delay.getValue()) {
            return;
        }
        this.waited = 0;
        if (isMoving((EntityLivingBase)AntiCity.mc.player)) {
            this.SposList.clear();
            this.breaked = false;
            this.inHole = false;
        }
        this.posList.clear();
        this.placed = 0;
        this.slot = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
        if (this.slot == -1 && (boolean)this.echest.getValue()) {
            this.slot = BurrowUtil.findHotbarBlock((Class)BlockEnderChest.class);
        }
        if (this.slot == -1) {
            return;
        }
        this.playerPos = PlayerUtil.getPlayerPos();
        for (final BlockPos offsets : this.offset) {
            if (AntiCity.mc.world.getBlockState(this.playerPos.add((Vec3i)offsets)).getBlock() != Blocks.BEDROCK) {
                if (this.smart.getValue()) {
                    if (!this.inHole) {
                        final HoleUtil.HoleInfo holeInfo = HoleUtil.isHole(this.playerPos, false, true, false);
                        final HoleUtil.HoleType holeType = holeInfo.getType();
                        if (holeType == HoleUtil.HoleType.SINGLE) {
                            this.inHole = true;
                        }
                        this.SposList.clear();
                    }
                    else if (AntiCity.mc.world.isAirBlock(this.playerPos.add((Vec3i)offsets))) {
                        this.breaked = true;
                        for (final BlockPos offset : this.offset) {
                            if (AntiCity.mc.world.isAirBlock(this.playerPos.add((Vec3i)offsets).add((Vec3i)offset))) {
                                this.SposList.add(this.playerPos.add((Vec3i)offsets).add((Vec3i)offset));
                            }
                        }
                        if (AntiCity.mc.world.isAirBlock(this.playerPos.add((Vec3i)offsets).up())) {
                            this.SposList.add(this.playerPos.add((Vec3i)offsets));
                            this.SposList.add(this.playerPos.add((Vec3i)offsets).up());
                        }
                    }
                }
                else {
                    for (final BlockPos offset : this.offset) {
                        if (AntiCity.mc.world.isAirBlock(this.playerPos.add((Vec3i)offsets).add((Vec3i)offset))) {
                            this.posList.add(this.playerPos.add((Vec3i)offsets).add((Vec3i)offset));
                        }
                    }
                }
            }
        }
        final int oldslot = AntiCity.mc.player.inventory.currentItem;
        this.posList.removeIf(pos -> this.isPos2(pos, this.playerPos));
        this.SposList.removeIf(pos -> this.isPos2(pos, this.playerPos) || !AntiCity.mc.world.isAirBlock(pos));
        if (this.breaked && this.SposList.isEmpty()) {
            this.breaked = false;
            this.inHole = false;
        }
        for (final BlockPos pos2 : this.smart.getValue() ? this.SposList : this.posList) {
            if (this.intersectsWithEntity(pos2)) {
                continue;
            }
            if (this.placed >= (int)this.blockpertick.getValue()) {
                return;
            }
            switchTo(this.slot);
            BurrowUtil.placeBlock(pos2, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
            switchTo(oldslot);
            ++this.placed;
        }
        if ((isMoving((EntityLivingBase)AntiCity.mc.player) || this.posList.isEmpty()) && !(boolean)this.smart.getValue()) {
            this.disable();
        }
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : AntiCity.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    public static void switchTo(final int slot) {
        if (AntiCity.mc.player.inventory.currentItem != slot && slot > -1 && slot < 9) {
            AntiCity.mc.player.inventory.currentItem = slot;
            AntiCity.mc.playerController.updateController();
        }
    }
    
    private boolean isPos2(final BlockPos pos1, final BlockPos pos2) {
        return pos1 != null && pos2 != null && pos1.x == pos2.x && pos1.y == pos2.y && pos1.z == pos2.z;
    }
}
