//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.item.*;

@Module.Declaration(name = "ShulkerAura", category = Category.Dev)
public class ShulkerAura extends Module
{
    BooleanSetting self;
    public boolean inShulker;
    public HashMap<BlockPos, Integer> openedShulkers;
    private final List<BlockPos> selfPlaced;
    @EventHandler
    private final Listener<PacketEvent.Send> listener;
    
    public ShulkerAura() {
        this.self = this.registerBoolean("Self", false);
        this.inShulker = false;
        this.openedShulkers = new HashMap<BlockPos, Integer>();
        this.selfPlaced = new ArrayList<BlockPos>();
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (ShulkerAura.mc.world == null || ShulkerAura.mc.player == null || ShulkerAura.mc.player.isDead) {
                return;
            }
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                final CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
                if (ShulkerAura.mc.player.getHeldItem(packet.getHand()).getItem() instanceof ItemShulkerBox) {
                    this.selfPlaced.add(packet.getPos().offset(packet.getDirection()));
                }
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        final HashMap<BlockPos, Integer> tempShulkers = new HashMap<BlockPos, Integer>(this.openedShulkers);
        for (final Map.Entry e : this.openedShulkers.entrySet()) {
            if (e.getValue() <= 0) {
                tempShulkers.remove(e.getKey());
            }
            tempShulkers.replace(e.getKey(), e.getValue() - 1);
        }
        this.openedShulkers.clear();
        this.openedShulkers.putAll(tempShulkers);
        if (!(ShulkerAura.mc.currentScreen instanceof GuiContainer) || ShulkerAura.mc.currentScreen instanceof GuiShulkerBox) {
            if (ShulkerAura.mc.currentScreen instanceof GuiShulkerBox) {
                if (this.inShulker) {
                    ShulkerAura.mc.displayGuiScreen((GuiScreen)null);
                }
                this.inShulker = false;
            }
            else {
                for (int x = -4; x <= 4; ++x) {
                    for (int y = -4; y <= 4; ++y) {
                        for (int z = -4; z <= 4; ++z) {
                            final BlockPos pos = ShulkerAura.mc.player.getPosition().add(x, y, z);
                            if ((boolean)this.self.getValue() || !this.selfPlaced.contains(pos)) {
                                if (ShulkerAura.mc.world.getBlockState(pos).getBlock() instanceof BlockShulkerBox && !this.openedShulkers.containsKey(pos) && ShulkerAura.mc.player.getPositionVector().distanceTo(new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5)) <= 5.25) {
                                    ShulkerAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                                    this.openedShulkers.put(pos, 300);
                                    this.inShulker = true;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
