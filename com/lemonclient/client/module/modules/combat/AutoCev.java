//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import java.util.*;
import com.lemonclient.client.module.modules.exploits.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

@Module.Declaration(name = "AutoCev", category = Category.Combat)
public class AutoCev extends Module
{
    BooleanSetting high;
    IntegerSetting delay;
    IntegerSetting range;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting rotate;
    BooleanSetting instantMine;
    BooleanSetting forcebreak;
    BooleanSetting silent;
    IntegerSetting blockpertick;
    ModeSetting breakBlock;
    int ob;
    int crystal;
    int pick;
    int waited;
    int place;
    BlockPos pos;
    BlockPos savepos;
    BlockPos cevpos;
    EntityPlayer enemy;
    EntityPlayer saveenemy;
    boolean blocked;
    BlockPos[] block;
    BlockPos[] highcevblock;
    
    public AutoCev() {
        this.high = this.registerBoolean("High Cev", true);
        this.delay = this.registerInteger("Delay", 0, 0, 20);
        this.range = this.registerInteger("Range", 5, 0, 10);
        this.packet = this.registerBoolean("Packet Place", true);
        this.swing = this.registerBoolean("Swing", true);
        this.rotate = this.registerBoolean("Rotate", false);
        this.instantMine = this.registerBoolean("Packet Mine", true);
        this.forcebreak = this.registerBoolean("Force Break", true);
        this.silent = this.registerBoolean("Silent Switch", true);
        this.blockpertick = this.registerInteger("BlocksPerTick", 4, 0, 20);
        this.breakBlock = this.registerMode("Break Block", (List)Arrays.asList("Normal", "Packet"), "Packet");
        this.block = new BlockPos[] { new BlockPos(0, 0, 1), new BlockPos(0, 1, 1), new BlockPos(0, 2, 1), new BlockPos(0, 0, -1), new BlockPos(0, 1, -1), new BlockPos(0, 2, -1), new BlockPos(1, 0, 0), new BlockPos(1, 1, 0), new BlockPos(1, 2, 0), new BlockPos(-1, 0, 0), new BlockPos(-1, 1, 0), new BlockPos(-1, 2, 0) };
        this.highcevblock = new BlockPos[] { new BlockPos(0, 3, 1), new BlockPos(0, 3, -1), new BlockPos(1, 3, 0), new BlockPos(-1, 3, 0) };
    }
    
    public void onEnable() {
        this.enemy = null;
        final int n = 0;
        this.place = n;
        this.waited = n;
        this.crystal = n;
        this.ob = n;
        this.pos = null;
        this.blocked = false;
    }
    
    public void onUpdate() {
        if (AutoCev.mc.world == null || AutoCev.mc.player == null || AutoCev.mc.player.isDead) {
            this.disable();
            return;
        }
        this.ob = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
        this.crystal = getItemHotbar(Items.END_CRYSTAL);
        if (this.ob == -1 || this.crystal == -1) {
            return;
        }
        this.enemy = PlayerUtil.findTarget((double)(int)this.range.getValue(), this.enemy);
        if (this.enemy == null) {
            return;
        }
        this.pos = new BlockPos((Vec3i)AntiBurrow.getEntityPos((Entity)this.enemy));
        if (!AutoCev.mc.world.isAirBlock(this.pos.add(0, 4, 0)) || !AutoCev.mc.world.isAirBlock(this.pos) || !AutoCev.mc.world.isAirBlock(this.pos.add(0, 1, 0))) {
            return;
        }
        if (this.enemy != this.saveenemy) {
            this.blocked = false;
        }
        this.saveenemy = this.enemy;
        for (final Entity target : AutoCev.mc.world.loadedEntityList) {
            if (target instanceof EntityEnderCrystal) {
                AutoCev.mc.playerController.attackEntity((EntityPlayer)AutoCev.mc.player, target);
            }
        }
        this.place = 0;
        if (this.waited++ < (int)this.delay.getValue()) {
            return;
        }
        this.waited = 0;
        if ((this.blocked || !AutoCev.mc.world.isAirBlock(this.pos.add(0, 3, 0))) && (boolean)this.high.getValue()) {
            this.highcev();
            this.blocked = true;
        }
        else {
            this.cev();
        }
    }
    
    public void highcev() {
        this.cevpos = this.pos.add(0, 3, 0);
        if (!AutoCev.mc.world.isAirBlock(this.cevpos.down())) {
            this.cevpos = this.cevpos.down();
            this.dobreak();
            return;
        }
        if (this.cevpos != this.savepos) {
            this.dobreak();
        }
        if ((boolean)this.instantMine.getValue() && (boolean)this.forcebreak.getValue()) {
            InstantMine.INSTANCE.lastBlock = this.cevpos;
        }
        final int oldslot = AutoCev.mc.player.inventory.currentItem;
        AutoCev.mc.player.inventory.currentItem = this.ob;
        AutoCev.mc.playerController.updateController();
        for (final BlockPos add : this.block) {
            if (this.place > (int)this.blockpertick.getValue()) {
                break;
            }
            if (AutoCev.mc.world.isAirBlock(this.pos.add((Vec3i)add)) && !AutoCev.mc.world.isAirBlock(this.pos.add((Vec3i)add).down())) {
                BurrowUtil.placeBlock(this.pos.add((Vec3i)add), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
                ++this.place;
            }
        }
        for (final BlockPos add : this.highcevblock) {
            if (this.place > (int)this.blockpertick.getValue()) {
                break;
            }
            if (AutoCev.mc.world.isAirBlock(this.pos.add((Vec3i)add)) && !AutoCev.mc.world.isAirBlock(this.pos.add((Vec3i)add).down())) {
                BurrowUtil.placeBlock(this.pos.add((Vec3i)add), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
                ++this.place;
            }
        }
        BurrowUtil.placeBlock(this.pos.up(3), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
        AutoCev.mc.player.inventory.currentItem = this.crystal;
        if (this.crystal == -1) {
            return;
        }
        BurrowUtil.placeBlock(this.pos.add(0, 4, 0), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
        if (!(boolean)this.silent.getValue()) {
            this.pick = getItemHotbar(Items.DIAMOND_PICKAXE);
            AutoCev.mc.player.inventory.currentItem = this.pick;
            AutoCev.mc.playerController.updateController();
        }
        if (this.silent.getValue()) {
            AutoCev.mc.player.inventory.currentItem = oldslot;
        }
    }
    
    public void cev() {
        this.cevpos = this.pos.add(0, 2, 0);
        if (this.cevpos != this.savepos) {
            this.dobreak();
        }
        if ((boolean)this.instantMine.getValue() && (boolean)this.forcebreak.getValue()) {
            InstantMine.INSTANCE.lastBlock = this.cevpos;
        }
        final int oldslot = AutoCev.mc.player.inventory.currentItem;
        AutoCev.mc.player.inventory.currentItem = this.ob;
        AutoCev.mc.playerController.updateController();
        for (final BlockPos add : this.block) {
            if (this.place > (int)this.blockpertick.getValue()) {
                break;
            }
            if (AutoCev.mc.world.isAirBlock(this.pos.add((Vec3i)add)) && !AutoCev.mc.world.isAirBlock(this.pos.add((Vec3i)add).down())) {
                BurrowUtil.placeBlock(this.pos.add((Vec3i)add), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
                ++this.place;
            }
        }
        BurrowUtil.placeBlock(this.pos.up(2), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
        AutoCev.mc.player.inventory.currentItem = this.crystal;
        if (this.crystal == -1) {
            return;
        }
        BurrowUtil.placeBlock(this.pos.add(0, 3, 0), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
        if (!(boolean)this.silent.getValue()) {
            this.pick = getItemHotbar(Items.DIAMOND_PICKAXE);
            AutoCev.mc.player.inventory.currentItem = this.pick;
            AutoCev.mc.playerController.updateController();
        }
        if (this.silent.getValue()) {
            AutoCev.mc.player.inventory.currentItem = oldslot;
        }
    }
    
    public static int getItemHotbar(final Item input) {
        for (int i = 0; i < 9; ++i) {
            final Item item = AutoCev.mc.player.inventory.getStackInSlot(i).getItem();
            if (Item.getIdFromItem(item) == Item.getIdFromItem(input)) {
                return i;
            }
        }
        return -1;
    }
    
    private void dobreak() {
        if (AutoCev.mc.world.isAirBlock(this.cevpos)) {
            return;
        }
        if (this.instantMine.getValue()) {
            if (InstantMine.INSTANCE.lastBlock == this.cevpos) {
                return;
            }
            InstantMine.INSTANCE.breaker(this.cevpos);
        }
        else {
            if (this.swing.getValue()) {
                AutoCev.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            if (((String)this.breakBlock.getValue()).equals("Packet")) {
                AutoCev.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.cevpos, EnumFacing.UP));
                AutoCev.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.cevpos, EnumFacing.UP));
            }
            else {
                AutoCev.mc.playerController.onPlayerDamageBlock(this.cevpos, EnumFacing.UP);
            }
        }
        this.savepos = this.cevpos;
    }
}
