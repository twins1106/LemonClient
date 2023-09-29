//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.api.setting.values.*;
import com.lemonclient.client.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.mojang.realmsclient.gui.*;
import com.lemonclient.api.util.misc.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.util.math.*;

@Module.Declaration(name = "AutoString", category = Category.Dev)
public class AutoString extends Module
{
    ModeSetting time;
    IntegerSetting delay;
    BooleanSetting ground;
    BooleanSetting hole;
    BooleanSetting out;
    BooleanSetting toggle;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting rotate;
    BooleanSetting head;
    List<BlockPos> posList;
    List<BlockPos> entity;
    List<BlockPos> pos;
    int waitted;
    boolean keydown;
    BlockPos[] sides;
    
    public AutoString() {
        this.time = this.registerMode("Time Mode", (List)Arrays.asList("Tick", "onUpdate", "Both", "Fast"), "Tick");
        this.delay = this.registerInteger("Delay", 0, 0, 20);
        this.ground = this.registerBoolean("OnGround Only", false);
        this.hole = this.registerBoolean("InHole Only", false);
        this.out = this.registerBoolean("Keybind OutHole", true, () -> (Boolean)this.hole.getValue());
        this.toggle = this.registerBoolean("Toggle", true, () -> (boolean)this.out.getValue() && (boolean)this.hole.getValue());
        this.packet = this.registerBoolean("Packet Place", false);
        this.swing = this.registerBoolean("Swing", true);
        this.rotate = this.registerBoolean("Rotate", true);
        this.head = this.registerBoolean("Head", true);
        this.posList = new ArrayList<BlockPos>();
        this.entity = new ArrayList<BlockPos>();
        this.pos = new ArrayList<BlockPos>();
        this.sides = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) };
    }
    
    public void onEnable() {
        this.waitted = 0;
    }
    
    public void onUpdate() {
        if (((String)this.time.getValue()).equals("onUpdate") || ((String)this.time.getValue()).equals("Both")) {
            this.placeString();
        }
    }
    
    public void onTick() {
        if (((String)this.time.getValue()).equals("Tick") || ((String)this.time.getValue()).equals("Both")) {
            this.placeString();
        }
    }
    
    public void fast() {
        if (((String)this.time.getValue()).equals("Fast")) {
            this.placeString();
        }
    }
    
    private void placeString() {
        if (AutoString.mc.world == null || AutoString.mc.player == null || AutoString.mc.player.isDead) {
            return;
        }
        if (this.out.getValue()) {
            if (this.toggle.getValue()) {
                if (LemonClient.AutoStringBind.isPressed()) {
                    this.keydown = !this.keydown;
                    MessageBus.sendClientDeleteMessage("AutoString toggle " + (this.keydown ? (((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getEnabledColor() + "ON") : (((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getDisabledColor() + "OFF")) + ChatFormatting.GRAY + "!", "AutoString", 8);
                }
            }
            else {
                this.keydown = LemonClient.AutoStringBind.isKeyDown();
            }
        }
        if (LemonClient.AutoStringBind.isKeyDown()) {
            this.keydown = !this.keydown;
        }
        final int slot = InventoryUtil.getItemInHotbar(Items.STRING);
        if (slot == -1) {
            return;
        }
        if (this.waitted++ < (int)this.delay.getValue()) {
            return;
        }
        this.waitted = 0;
        if ((boolean)this.ground.getValue() && !AutoString.mc.player.onGround) {
            return;
        }
        if (!(boolean)this.hole.getValue() || HoleUtil.isInHole((Entity)AutoString.mc.player, false, true, false) || this.keydown) {
            this.calc();
            if (this.head.getValue()) {
                this.pos.addAll(this.posList);
                for (final BlockPos pos : this.pos) {
                    this.posList.add(pos.add(0, 1, 0));
                }
            }
            for (final BlockPos pos : this.posList) {
                if (!AutoString.mc.world.isAirBlock(pos) && AutoString.mc.world.getBlockState(pos).getBlock() != Blocks.FIRE) {
                    continue;
                }
                final int oldslot = AutoString.mc.player.inventory.currentItem;
                BurrowUtil.switchToSlot(slot);
                BurrowUtil.placeBlock(pos, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
                BurrowUtil.switchToSlot(oldslot);
            }
        }
    }
    
    private void calc() {
        this.posList = new ArrayList<BlockPos>();
        this.entity = new ArrayList<BlockPos>();
        this.pos = new ArrayList<BlockPos>();
        final BlockPos pos = new BlockPos(Math.floor(AutoString.mc.player.posX), Math.floor(AutoString.mc.player.posY + 0.5), Math.floor(AutoString.mc.player.posZ));
        this.posList.add(pos);
        for (final BlockPos side : this.sides) {
            final BlockPos blockPos = pos.add((Vec3i)side);
            if (this.intersectsWithPlayer(blockPos)) {
                this.entity.add(blockPos);
            }
        }
        if (!this.entity.isEmpty()) {
            this.loopCalc();
        }
    }
    
    private void loopCalc() {
        if (this.entity.isEmpty()) {
            return;
        }
        this.pos.addAll(this.entity);
        this.entity.clear();
        for (final BlockPos hasEntity : this.pos) {
            for (final BlockPos side : this.sides) {
                final BlockPos blockPos = hasEntity.add((Vec3i)side);
                if (this.intersectsWithPlayer(blockPos) && !this.posList.contains(blockPos)) {
                    this.entity.add(blockPos);
                }
            }
            this.posList.add(hasEntity);
        }
        if (!this.entity.isEmpty()) {
            this.loopCalc();
        }
    }
    
    private boolean intersectsWithPlayer(final BlockPos pos) {
        return new AxisAlignedBB(pos).intersects(AutoString.mc.player.getEntityBoundingBox());
    }
    
    public String getHudInfo() {
        String t = "";
        if ((boolean)this.out.getValue() && (boolean)this.hole.getValue()) {
            t = (this.keydown ? ("[" + ChatFormatting.WHITE + "On" + ChatFormatting.GRAY + "]") : ("[" + ChatFormatting.WHITE + "Off" + ChatFormatting.GRAY + "]"));
        }
        return t;
    }
}
