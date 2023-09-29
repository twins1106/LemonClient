//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.player.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.world.combat.*;

@Module.Declaration(name = "Blocker", category = Category.Combat)
public class Blocker extends Module
{
    ModeSetting time;
    ModeSetting breakType;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting rotate;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    BooleanSetting anvilBlocker;
    BooleanSetting pistonBlocker;
    BooleanSetting pistonBlockernew;
    BooleanSetting antiFacePlace;
    IntegerSetting BlocksPerTick;
    ModeSetting blockPlaced;
    IntegerSetting tickDelay;
    DoubleSetting range;
    DoubleSetting yrange;
    List<BlockPos> pistonList;
    private int delayTimeTicks;
    private boolean activedBefore;
    
    public Blocker() {
        this.time = this.registerMode("Time Mode", (List)Arrays.asList("Tick", "onUpdate", "Both", "Fast"), "Tick");
        this.breakType = this.registerMode("Type", (List)Arrays.asList("Vanilla", "Packet"), "Vanilla");
        this.packet = this.registerBoolean("Packet Place", false);
        this.swing = this.registerBoolean("Swing", false);
        this.rotate = this.registerBoolean("Rotate", true);
        this.packetSwitch = this.registerBoolean("Packet Switch", true);
        this.check = this.registerBoolean("Switch Check", true);
        this.anvilBlocker = this.registerBoolean("Anvil", true);
        this.pistonBlocker = this.registerBoolean("Break Piston", true);
        this.pistonBlockernew = this.registerBoolean("Block Piston", true);
        this.antiFacePlace = this.registerBoolean("Shift AntiFacePlace", true);
        this.BlocksPerTick = this.registerInteger("Blocks Per Tick", 4, 0, 10);
        this.blockPlaced = this.registerMode("Block Place", (List)Arrays.asList("Pressure", "String"), "String");
        this.tickDelay = this.registerInteger("Tick Delay", 5, 0, 10);
        this.range = this.registerDouble("Range", 5.0, 0.0, 10.0);
        this.yrange = this.registerDouble("YRange", 5.0, 0.0, 10.0);
        this.pistonList = new ArrayList<BlockPos>();
        this.delayTimeTicks = 0;
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || Blocker.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                Blocker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                Blocker.mc.player.inventory.currentItem = slot;
            }
            Blocker.mc.playerController.updateController();
        }
    }
    
    public void onEnable() {
        this.pistonList = new ArrayList<BlockPos>();
        SpoofRotationUtil.ROTATION_UTIL.onEnable();
        PlacementUtil.onEnable();
    }
    
    public void onDisable() {
        SpoofRotationUtil.ROTATION_UTIL.onDisable();
        PlacementUtil.onDisable();
    }
    
    public void onUpdate() {
        if (((String)this.time.getValue()).equals("onUpdate") || ((String)this.time.getValue()).equals("Both")) {
            this.block();
        }
    }
    
    public void onTick() {
        if (((String)this.time.getValue()).equals("Tick") || ((String)this.time.getValue()).equals("Both")) {
            this.block();
        }
    }
    
    public void fast() {
        if (((String)this.time.getValue()).equals("Fast")) {
            this.block();
        }
    }
    
    private void block() {
        if (Blocker.mc.player == null || Blocker.mc.world == null || Blocker.mc.player.isDead) {
            this.pistonList.clear();
            return;
        }
        if (this.delayTimeTicks < (int)this.tickDelay.getValue()) {
            ++this.delayTimeTicks;
        }
        else {
            SpoofRotationUtil.ROTATION_UTIL.shouldSpoofAngles(true);
            this.delayTimeTicks = 0;
            if (this.anvilBlocker.getValue()) {
                this.blockAnvil();
            }
            if (this.pistonBlocker.getValue()) {
                this.blockPiston();
            }
            if (this.pistonBlockernew.getValue()) {
                this.blockPA();
            }
            if ((boolean)this.antiFacePlace.getValue() && Blocker.mc.gameSettings.keyBindSneak.isPressed()) {
                this.antiFacePlace();
            }
        }
    }
    
    private List<BlockPos> posList() {
        return (List<BlockPos>)EntityUtil.getSphere(PlayerUtil.getPlayerPos(), (Double)this.range.getValue(), (Double)this.yrange.getValue(), false, false, 0);
    }
    
    private void antiFacePlace() {
        int blocksPlaced = 0;
        for (final Vec3d surround : new Vec3d[] { new Vec3d(1.0, 1.0, 0.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(0.0, 1.0, -1.0) }) {
            final BlockPos pos = new BlockPos(Blocker.mc.player.posX + surround.x, Blocker.mc.player.posY, Blocker.mc.player.posZ + surround.z);
            final Block temp;
            if ((temp = BlockUtil.getBlock(pos)) instanceof BlockObsidian || temp == Blocks.BEDROCK) {
                if (blocksPlaced++ == 0) {
                    AntiCrystal.getHotBarPressure((String)this.blockPlaced.getValue());
                }
                PlacementUtil.placeItem(new BlockPos((double)pos.getX(), pos.getY() + surround.y, (double)pos.getZ()), EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (Class)Items.STRING.getClass());
                if (blocksPlaced == (int)this.BlocksPerTick.getValue()) {
                    return;
                }
            }
        }
    }
    
    private void blockPA() {
        final int slot = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
        if (slot == -1) {
            return;
        }
        for (final BlockPos pos : this.posList()) {
            if (!this.pistonList.contains(pos) && Blocker.mc.world.getBlockState(pos).getBlock() instanceof BlockPistonBase) {
                this.pistonList.add(pos);
            }
        }
        this.pistonList.removeIf(blockPos -> Blocker.mc.player.getDistanceSq(blockPos) > (double)this.range.getValue() * (double)this.range.getValue());
        for (final BlockPos pos : this.pistonList) {
            if (!Blocker.mc.world.isAirBlock(pos)) {
                continue;
            }
            final int oldslot = Blocker.mc.player.inventory.currentItem;
            this.switchTo(slot);
            BurrowUtil.placeBlock(pos, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
            this.switchTo(oldslot);
        }
        this.pistonList.removeIf(blockPos -> Blocker.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN);
    }
    
    private void blockAnvil() {
        boolean found = false;
        for (final Entity t : Blocker.mc.world.loadedEntityList) {
            if (t instanceof EntityFallingBlock) {
                final Block ex = ((EntityFallingBlock)t).fallTile.getBlock();
                if (!(ex instanceof BlockAnvil) || (int)t.posX != (int)Blocker.mc.player.posX || (int)t.posZ != (int)Blocker.mc.player.posZ || !(BlockUtil.getBlock(Blocker.mc.player.posX, Blocker.mc.player.posY + 2.0, Blocker.mc.player.posZ) instanceof BlockAir)) {
                    continue;
                }
                this.placeBlock(new BlockPos(Blocker.mc.player.posX, Blocker.mc.player.posY + 2.0, Blocker.mc.player.posZ));
                MessageBus.sendClientDeleteMessage(((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getEnabledColor() + "AutoAnvil detected... Anvil Blocked!", "Blocker", 9);
                found = true;
            }
        }
        if (!found && this.activedBefore) {
            this.activedBefore = false;
        }
    }
    
    private void blockPiston() {
        for (final Entity t : Blocker.mc.world.loadedEntityList) {
            if (t instanceof EntityEnderCrystal && t.posX >= Blocker.mc.player.posX - 1.5 && t.posX <= Blocker.mc.player.posX + 1.5 && t.posZ >= Blocker.mc.player.posZ - 1.5 && t.posZ <= Blocker.mc.player.posZ + 1.5) {
                for (int i = -2; i < 3; ++i) {
                    for (int j = -2; j < 3; ++j) {
                        if ((i == 0 || j == 0) && BlockUtil.getBlock(t.posX + i, t.posY, t.posZ + j) instanceof BlockPistonBase) {
                            this.breakCrystalPiston(t);
                            MessageBus.sendClientDeleteMessage(((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getEnabledColor() + "PistonCrystal detected... Destroyed crystal!", "Blocker", 9);
                        }
                    }
                }
            }
        }
    }
    
    private void placeBlock(final BlockPos pos) {
        if (!Blocker.mc.world.isAirBlock(pos)) {
            return;
        }
        final int oldslot = Blocker.mc.player.inventory.currentItem;
        final int obsidianSlot = BurrowUtil.findHotbarBlock((Class)BlockObsidian.class);
        if (obsidianSlot == -1) {
            return;
        }
        this.switchTo(obsidianSlot);
        BurrowUtil.placeBlock(pos, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
        this.switchTo(oldslot);
    }
    
    private void breakCrystalPiston(final Entity crystal) {
        if (this.rotate.getValue()) {
            SpoofRotationUtil.ROTATION_UTIL.lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, (EntityPlayer)Blocker.mc.player);
        }
        if (((String)this.breakType.getValue()).equals("Vanilla")) {
            CrystalUtil.breakCrystal(crystal, (boolean)this.swing.getValue());
        }
        else {
            CrystalUtil.breakCrystalPacket(crystal, (boolean)this.swing.getValue());
        }
        if (this.rotate.getValue()) {
            SpoofRotationUtil.ROTATION_UTIL.resetRotation();
        }
    }
}
