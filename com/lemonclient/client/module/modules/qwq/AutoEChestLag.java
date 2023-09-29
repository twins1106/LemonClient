//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.qwq;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.api.util.world.*;
import java.util.stream.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import java.util.*;

@Module.Declaration(name = "AutoEChestLag", category = Category.qwq)
public class AutoEChestLag extends Module
{
    IntegerSetting range;
    IntegerSetting delay;
    IntegerSetting BPT;
    BooleanSetting silent;
    BooleanSetting rotate;
    BooleanSetting packet;
    BooleanSetting swing;
    int waitted;
    int placed;
    
    public AutoEChestLag() {
        this.range = this.registerInteger("Range", 5, 0, 10);
        this.delay = this.registerInteger("Delay", 1, 0, 10);
        this.BPT = this.registerInteger("BlockPerTick", 4, 0, 20);
        this.silent = this.registerBoolean("Silent", true);
        this.rotate = this.registerBoolean("Rotate", true);
        this.packet = this.registerBoolean("Packet", true);
        this.swing = this.registerBoolean("Swing", true);
    }
    
    public void onUpdate() {
        if (AutoEChestLag.mc.world == null || AutoEChestLag.mc.player == null || AutoEChestLag.mc.player.isDead) {
            return;
        }
        if (this.waitted++ < (int)this.delay.getValue()) {
            return;
        }
        final int slot = BurrowUtil.findHotbarBlock((Class)BlockEnderChest.class);
        if (slot == -1) {
            return;
        }
        final int oldslot = AutoEChestLag.mc.player.inventory.currentItem;
        final List<BlockPos> Pos = (List<BlockPos>)EntityUtil.getSphere(PlayerUtil.getPlayerPos(), Double.valueOf((double)this.range.getValue()), Double.valueOf((double)this.range.getValue()), false, true, 0).stream().filter(blockPos -> AutoEChestLag.mc.world.isAirBlock(blockPos)).collect(Collectors.toList());
        this.placed = 0;
        for (final BlockPos pos : Pos) {
            if (this.placed >= (int)this.BPT.getValue()) {
                break;
            }
            this.switchTo(slot);
            BurrowUtil.placeBlock(pos, EnumHand.MAIN_HAND, (boolean)this.rotate.getValue(), (boolean)this.packet.getValue(), false, (boolean)this.swing.getValue());
            if (this.silent.getValue()) {
                this.switchTo(oldslot);
            }
            ++this.placed;
        }
    }
    
    private void switchTo(final int slot) {
        if (AutoEChestLag.mc.player.inventory.currentItem != slot && slot > -1 && slot < 9) {
            AutoEChestLag.mc.player.inventory.currentItem = slot;
            AutoEChestLag.mc.playerController.updateController();
        }
    }
}
