//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "AutoCreeper", category = Category.Combat)
public class AutoCreeper extends Module
{
    IntegerSetting delay;
    DoubleSetting range;
    BooleanSetting rotate;
    BooleanSetting silent;
    EntityPlayer target;
    int oldSlot;
    int slot;
    Vec2f rot;
    @EventHandler
    private final Listener<PacketEvent.Send> sendListener;
    
    public AutoCreeper() {
        this.delay = this.registerInteger("Delay", 3, 0, 20);
        this.range = this.registerDouble("Range", 5.0, 0.0, 6.0);
        this.rotate = this.registerBoolean("Rotate", true);
        this.silent = this.registerBoolean("Silent Switch", true);
        this.target = null;
        this.slot = -1;
        this.sendListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if ((boolean)this.rotate.getValue() && this.target != null) {
                this.rot = RotationUtil.getRotationTo(this.target.getPositionVector());
                if (event.getPacket() instanceof CPacketPlayer) {
                    ((CPacketPlayer)event.getPacket()).yaw = this.rot.x;
                    ((CPacketPlayer)event.getPacket()).pitch = this.rot.y;
                }
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        if (this.target == null || this.target.getDistance((Entity)AutoCreeper.mc.player) > (double)this.range.getValue()) {
            this.target = this.getTarget();
        }
        else if (AutoCreeper.mc.player.ticksExisted % (int)this.delay.getValue() == 0) {
            this.slot = this.getSlot();
            if (this.slot == -1) {
                this.disable();
            }
            this.oldSlot = AutoCreeper.mc.player.inventory.currentItem;
            if (this.silent.getValue()) {
                AutoCreeper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.slot));
            }
            else {
                AutoCreeper.mc.player.inventory.currentItem = this.slot;
            }
            AutoCreeper.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(new BlockPos(this.target.posX, Math.ceil(this.target.posY - 0.5) - 1.0, this.target.posZ), EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            if (this.silent.getValue()) {
                AutoCreeper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldSlot));
            }
            else {
                AutoCreeper.mc.player.inventory.currentItem = this.oldSlot;
            }
        }
    }
    
    public EntityPlayer getTarget() {
        return this.target = PlayerUtil.findClosestTarget();
    }
    
    public int getSlot() {
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoCreeper.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY || stack.getItem() != Items.SPAWN_EGG) {
                newSlot = i;
                break;
            }
        }
        return newSlot;
    }
}
