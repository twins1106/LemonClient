//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "AntiContainer", category = Category.Misc)
public class AntiContainer extends Module
{
    BooleanSetting Chest;
    BooleanSetting EnderChest;
    BooleanSetting Trapped_Chest;
    BooleanSetting Hopper;
    BooleanSetting Dispenser;
    BooleanSetting Furnace;
    BooleanSetting Beacon;
    BooleanSetting Crafting_Table;
    BooleanSetting Anvil;
    BooleanSetting Enchanting_table;
    BooleanSetting Brewing_Stand;
    BooleanSetting ShulkerBox;
    @EventHandler
    private final Listener<PacketEvent.Send> listener;
    
    public AntiContainer() {
        this.Chest = this.registerBoolean("Chest", true);
        this.EnderChest = this.registerBoolean("EnderChest", true);
        this.Trapped_Chest = this.registerBoolean("Trapped_Chest", true);
        this.Hopper = this.registerBoolean("Hopper", true);
        this.Dispenser = this.registerBoolean("Dispenser", true);
        this.Furnace = this.registerBoolean("Furnace", true);
        this.Beacon = this.registerBoolean("Beacon", true);
        this.Crafting_Table = this.registerBoolean("Crafting_Table", true);
        this.Anvil = this.registerBoolean("Anvil", true);
        this.Enchanting_table = this.registerBoolean("Enchanting_table", true);
        this.Brewing_Stand = this.registerBoolean("Brewing_Stand", true);
        this.ShulkerBox = this.registerBoolean("ShulkerBox", true);
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                final BlockPos pos = ((CPacketPlayerTryUseItemOnBlock)event.getPacket()).getPos();
                if (this.check(pos)) {
                    event.cancel();
                }
            }
        }, new Predicate[0]);
    }
    
    public boolean check(final BlockPos pos) {
        return (AntiContainer.mc.world.getBlockState(pos).getBlock() == Blocks.CHEST && (boolean)this.Chest.getValue()) || (AntiContainer.mc.world.getBlockState(pos).getBlock() == Blocks.ENDER_CHEST && (boolean)this.EnderChest.getValue()) || (AntiContainer.mc.world.getBlockState(pos).getBlock() == Blocks.TRAPPED_CHEST && (boolean)this.Trapped_Chest.getValue()) || (AntiContainer.mc.world.getBlockState(pos).getBlock() == Blocks.HOPPER && (boolean)this.Hopper.getValue()) || (AntiContainer.mc.world.getBlockState(pos).getBlock() == Blocks.DISPENSER && (boolean)this.Dispenser.getValue()) || (AntiContainer.mc.world.getBlockState(pos).getBlock() == Blocks.FURNACE && (boolean)this.Furnace.getValue()) || (AntiContainer.mc.world.getBlockState(pos).getBlock() == Blocks.BEACON && (boolean)this.Beacon.getValue()) || (AntiContainer.mc.world.getBlockState(pos).getBlock() == Blocks.CRAFTING_TABLE && (boolean)this.Crafting_Table.getValue()) || (AntiContainer.mc.world.getBlockState(pos).getBlock() == Blocks.ANVIL && (boolean)this.Anvil.getValue()) || (AntiContainer.mc.world.getBlockState(pos).getBlock() == Blocks.ENCHANTING_TABLE && (boolean)this.Enchanting_table.getValue()) || (AntiContainer.mc.world.getBlockState(pos).getBlock() == Blocks.BREWING_STAND && (boolean)this.Brewing_Stand.getValue()) || (AntiContainer.mc.world.getBlockState(pos).getBlock() instanceof BlockShulkerBox && (boolean)this.ShulkerBox.getValue());
    }
}
