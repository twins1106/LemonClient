//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.util.font.*;
import java.util.function.*;
import com.lemonclient.api.util.render.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

@Module.Declaration(name = "BreakingWarning", category = Category.Dev)
public class BreakingWarning extends Module
{
    DoubleSetting minRange;
    BooleanSetting obsidianOnly;
    BooleanSetting pickaxeOnly;
    private Boolean warn;
    private String playerName;
    private int delay;
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener;
    public static RootFontRenderer fontRenderer;
    
    public BreakingWarning() {
        this.minRange = this.registerDouble("Min Range", 1.5, 0.0, 10.0);
        this.obsidianOnly = this.registerBoolean("Obsidian Only", true);
        this.pickaxeOnly = this.registerBoolean("Pickaxe Only", true);
        this.warn = false;
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketBlockBreakAnim) {
                final SPacketBlockBreakAnim packet = (SPacketBlockBreakAnim)event.getPacket();
                final int progress = packet.getProgress();
                final int breakerId = packet.getBreakerId();
                final BlockPos pos = packet.getPosition();
                final Block block = BreakingWarning.mc.world.getBlockState(pos).getBlock();
                final EntityPlayer breaker = (EntityPlayer)BreakingWarning.mc.world.getEntityByID(breakerId);
                if (breaker == null) {
                    return;
                }
                if ((boolean)this.obsidianOnly.getValue() && !block.equals(Blocks.OBSIDIAN)) {
                    return;
                }
                if ((boolean)this.pickaxeOnly.getValue() && (breaker.itemStackMainHand.isEmpty() || !(breaker.itemStackMainHand.getItem() instanceof ItemPickaxe))) {
                    return;
                }
                if (this.pastDistance((EntityPlayer)BreakingWarning.mc.player, pos, (double)this.minRange.getValue())) {
                    this.playerName = breaker.getName();
                    this.warn = true;
                    this.delay = 0;
                    if (progress == 255) {
                        this.warn = false;
                    }
                }
            }
        }, new Predicate[0]);
    }
    
    public void onRender() {
        if (!this.warn) {
            return;
        }
        if (this.delay++ > 100) {
            this.warn = false;
        }
        final String text = this.playerName + " is breaking blocks near you!";
        final FontRenderer renderer = (FontRenderer)BreakingWarning.fontRenderer;
        int divider = BreakingWarning.mc.gameSettings.guiScale;
        if (divider == 0) {
            divider = 3;
        }
        renderer.drawStringWithShadow(BreakingWarning.mc.displayWidth / divider / 2 - renderer.getStringWidth(text) / 2, BreakingWarning.mc.displayHeight / divider / 2 - 16, 240, 87, 70, text);
    }
    
    private boolean pastDistance(final EntityPlayer player, final BlockPos pos, final double dist) {
        return player.getDistanceSqToCenter(pos) <= Math.pow(dist, 2.0);
    }
    
    static {
        BreakingWarning.fontRenderer = new RootFontRenderer(1.0f);
    }
}
