//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import net.minecraftforge.fml.common.gameevent.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import org.lwjgl.input.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "MCP", category = Category.Misc)
public class MCP extends Module
{
    BooleanSetting clipRotate;
    IntegerSetting pearlPitch;
    IntegerSetting slot;
    BooleanSetting packetSwitch;
    BooleanSetting check;
    int pearlInvSlot;
    int currentItem;
    Timing timer;
    @EventHandler
    private final Listener<InputEvent.MouseInputEvent> listener;
    
    public MCP() {
        this.clipRotate = this.registerBoolean("clipRotate", false);
        this.pearlPitch = this.registerInteger("Pitch", 85, -90, 90);
        this.slot = this.registerInteger("Slot", 1, 1, 9);
        this.packetSwitch = this.registerBoolean("Packet Switch", false);
        this.check = this.registerBoolean("Switch Check", false);
        this.timer = new Timing();
        this.listener = (Listener<InputEvent.MouseInputEvent>)new Listener(event -> {
            if (MCP.mc.world == null || MCP.mc.player == null || MCP.mc.player.isDead || MCP.mc.player.inventory == null) {
                return;
            }
            if (Mouse.getEventButton() == 2) {
                if (MCP.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY) {
                    return;
                }
                if ((boolean)this.clipRotate.getValue() && MCP.mc.player.onGround) {
                    MCP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(MCP.mc.player.rotationYaw, (float)this.pearlPitch.getValue(), MCP.mc.player.onGround));
                }
                if ((boolean)this.clipRotate.getValue() && !MCP.mc.player.onGround) {
                    return;
                }
                this.pearlInvSlot = InventoryUtil.findFirstItemSlot((Class)ItemEnderPearl.class, 0, 35);
                final int pearlHotSlot = InventoryUtil.findFirstItemSlot((Class)ItemEnderPearl.class, 0, 8);
                this.currentItem = MCP.mc.player.inventory.currentItem;
                if (this.pearlInvSlot == -1 && pearlHotSlot == -1) {
                    return;
                }
                if (pearlHotSlot == -1) {
                    this.swap(this.pearlInvSlot);
                    final int oldslot = MCP.mc.player.inventory.currentItem;
                    this.switchTo((int)this.slot.getValue() - 1);
                    MCP.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    this.switchTo(oldslot);
                    if (this.timer.passedMs(100L)) {
                        this.swap(this.pearlInvSlot);
                        this.timer.reset();
                    }
                }
                else {
                    final int oldSlot = MCP.mc.player.inventory.currentItem;
                    this.switchTo(pearlHotSlot);
                    MCP.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    this.switchTo(oldSlot);
                }
            }
        }, new Predicate[0]);
    }
    
    private void swap(final int InvSlot) {
        if ((int)this.slot.getValue() == 1) {
            MCP.mc.playerController.windowClick(0, InvSlot, 0, ClickType.SWAP, (EntityPlayer)MCP.mc.player);
        }
        else {
            MCP.mc.playerController.windowClick(0, InvSlot, 0, ClickType.SWAP, (EntityPlayer)MCP.mc.player);
            MCP.mc.playerController.windowClick(0, (int)this.slot.getValue() + 35, 0, ClickType.SWAP, (EntityPlayer)MCP.mc.player);
            MCP.mc.playerController.windowClick(0, InvSlot, 0, ClickType.SWAP, (EntityPlayer)MCP.mc.player);
        }
        MCP.mc.playerController.updateController();
    }
    
    private void switchTo(final int slot) {
        if (slot > -1 && slot < 9 && (!(boolean)this.check.getValue() || MCP.mc.player.inventory.currentItem != slot)) {
            if (this.packetSwitch.getValue()) {
                MCP.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            else {
                MCP.mc.player.inventory.currentItem = slot;
            }
            MCP.mc.playerController.updateController();
        }
    }
}
