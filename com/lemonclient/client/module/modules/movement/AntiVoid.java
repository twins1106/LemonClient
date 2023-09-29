//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.util.math.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;
import com.lemonclient.api.util.player.*;

@Module.Declaration(name = "AntiVoid", category = Category.Movement)
public class AntiVoid extends Module
{
    ModeSetting mode;
    DoubleSetting height;
    BooleanSetting chorus;
    BooleanSetting packetfly;
    boolean chor;
    @EventHandler
    private final Listener<PlayerMoveEvent> playerMoveEventListener;
    
    public AntiVoid() {
        this.mode = this.registerMode("Mode", (List)Arrays.asList("Freeze", "Glitch", "Catch"), "Freeze");
        this.height = this.registerDouble("Height", 2.0, 0.0, 5.0);
        this.chorus = this.registerBoolean("Chorus", false, () -> ((String)this.mode.getValue()).equals("Freeze"));
        this.packetfly = this.registerBoolean("PacketFly", false, () -> ((String)this.mode.getValue()).equals("Catch"));
        this.playerMoveEventListener = (Listener<PlayerMoveEvent>)new Listener(event -> {
            try {
                if (AntiVoid.mc.player.posY < (double)this.height.getValue() + 0.1 && ((String)this.mode.getValue()).equalsIgnoreCase("Freeze") && AntiVoid.mc.world.getBlockState(new BlockPos(AntiVoid.mc.player.posX, 0.0, AntiVoid.mc.player.posZ)).getMaterial().isReplaceable()) {
                    final String s = (String)this.mode.getValue();
                    switch (s) {
                        case "Freeze": {
                            AntiVoid.mc.player.posY = (double)this.height.getValue();
                            event.setY(0.0);
                            if (AntiVoid.mc.player.getRidingEntity() != null) {
                                AntiVoid.mc.player.ridingEntity.setVelocity(0.0, 0.0, 0.0);
                            }
                            if (!(boolean)this.chorus.getValue()) {
                                break;
                            }
                            int newSlot = -1;
                            for (int i = 0; i < 9; ++i) {
                                final ItemStack stack = AntiVoid.mc.player.inventory.getStackInSlot(i);
                                if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemChorusFruit) {
                                    newSlot = i;
                                    break;
                                }
                            }
                            if (newSlot == -1) {
                                newSlot = 1;
                                MessageBus.sendClientPrefixMessage(((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getDisabledColor() + "Out of chorus!");
                                this.chor = false;
                            }
                            else {
                                this.chor = true;
                            }
                            if (!this.chor) {
                                break;
                            }
                            AntiVoid.mc.player.inventory.currentItem = newSlot;
                            if (AntiVoid.mc.player.canEat(true)) {
                                AntiVoid.mc.player.setActiveHand(EnumHand.MAIN_HAND);
                                break;
                            }
                            break;
                        }
                        case "Glitch": {
                            AntiVoid.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AntiVoid.mc.player.posX, AntiVoid.mc.player.posY + 69.0, AntiVoid.mc.player.posZ, AntiVoid.mc.player.onGround));
                            break;
                        }
                        case "Catch": {
                            final int oldSlot = AntiVoid.mc.player.inventory.currentItem;
                            int newSlot2 = -1;
                            for (int j = 0; j < 9; ++j) {
                                final ItemStack stack2 = AntiVoid.mc.player.inventory.getStackInSlot(j);
                                if (stack2 != ItemStack.EMPTY) {
                                    if (stack2.getItem() instanceof ItemBlock) {
                                        if (Block.getBlockFromItem(stack2.getItem()).getDefaultState().isFullBlock()) {
                                            if (!(((ItemBlock)stack2.getItem()).getBlock() instanceof BlockFalling)) {
                                                newSlot2 = j;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (newSlot2 == -1) {
                                newSlot2 = 1;
                                MessageBus.sendClientPrefixMessage(((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getDisabledColor() + "Out of valid blocks. Disabling!");
                                this.disable();
                            }
                            AntiVoid.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(newSlot2));
                            PlacementUtil.place(new BlockPos(AntiVoid.mc.player.posX, 0.0, AntiVoid.mc.player.posZ), EnumHand.MAIN_HAND, true);
                            if (AntiVoid.mc.world.getBlockState(new BlockPos(AntiVoid.mc.player.posX, 0.0, AntiVoid.mc.player.posZ)).getMaterial().isReplaceable() && (boolean)this.packetfly.getValue()) {
                                AntiVoid.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(AntiVoid.mc.player.posX + AntiVoid.mc.player.motionX, AntiVoid.mc.player.posY + 0.0624, AntiVoid.mc.player.posZ + AntiVoid.mc.player.motionZ, AntiVoid.mc.player.rotationYaw, AntiVoid.mc.player.rotationPitch, false));
                                AntiVoid.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(AntiVoid.mc.player.posX, AntiVoid.mc.player.posY + 69420.0, AntiVoid.mc.player.posZ, AntiVoid.mc.player.rotationYaw, AntiVoid.mc.player.rotationPitch, false));
                            }
                            AntiVoid.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(oldSlot));
                            break;
                        }
                    }
                }
            }
            catch (Exception ex) {}
        }, new Predicate[0]);
    }
}
