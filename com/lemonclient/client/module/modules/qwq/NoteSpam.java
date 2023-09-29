//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.qwq;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.util.*;

@Module.Declaration(name = "NoteSpam", category = Category.qwq)
public class NoteSpam extends Module
{
    ModeSetting timeMode;
    DoubleSetting range;
    IntegerSetting max;
    
    public NoteSpam() {
        this.timeMode = this.registerMode("Time Mode", (List)Arrays.asList("onUpdate", "Tick", "Fast"), "Fast");
        this.range = this.registerDouble("Range", 5.5, 1.0, 10.0);
        this.max = this.registerInteger("MaxBlocks", 30, 1, 150);
    }
    
    public void onUpdate() {
        if (((String)this.timeMode.getValue()).equalsIgnoreCase("onUpdate")) {
            this.doNoteSpam();
        }
    }
    
    public void onTick() {
        if (((String)this.timeMode.getValue()).equalsIgnoreCase("Tick")) {
            this.doNoteSpam();
        }
    }
    
    public void fast() {
        if (((String)this.timeMode.getValue()).equalsIgnoreCase("Fast")) {
            this.doNoteSpam();
        }
    }
    
    private void doNoteSpam() {
        if (NoteSpam.mc.world == null || NoteSpam.mc.player == null || NoteSpam.mc.player.isDead) {
            return;
        }
        int counter = 0;
        final List<BlockPos> posList = (List<BlockPos>)EntityUtil.getSphere(PlayerUtil.getPlayerPos(), (Double)this.range.getValue(), (Double)this.range.getValue(), false, true, 0);
        for (final BlockPos b : posList) {
            if (BlockUtil.getBlock(b) == Blocks.NOTEBLOCK) {
                NoteSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, b, EnumFacing.UP));
                if (++counter > (int)this.max.getValue()) {
                    return;
                }
                continue;
            }
        }
    }
}
