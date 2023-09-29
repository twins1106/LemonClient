//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.misc.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import net.minecraft.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.entity.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import com.lemonclient.api.util.player.*;

@Module.Declaration(name = "Scaffold", category = Category.Misc)
public class Scaffold extends Module
{
    ModeSetting logic;
    IntegerSetting distance;
    IntegerSetting distanceP;
    ModeSetting towerMode;
    DoubleSetting downSpeed;
    IntegerSetting delay;
    BooleanSetting rotate;
    private final Listener<PlayerJumpEvent> jumpEventListener;
    int timer;
    int oldSlot;
    int newSlot;
    double oldTower;
    EntityPlayer predPlayer;
    BlockPos scaffold;
    BlockPos towerPos;
    BlockPos downPos;
    BlockPos rotateTo;
    Timer cancelTimer;
    CPacketPlayer.Rotation RotVec;
    @EventHandler
    private final Listener<PacketEvent.Send> sendListener;
    @EventHandler
    private final Listener<PlayerMoveEvent> moveEventListener;
    
    public Scaffold() {
        this.logic = this.registerMode("Place Logic", (List)Arrays.asList("Predict", "Player"), "Predict");
        this.distance = this.registerInteger("Distance Predict", 2, 0, 20);
        this.distanceP = this.registerInteger("Distance Player", 2, 0, 20);
        this.towerMode = this.registerMode("Tower Mode", (List)Arrays.asList("Jump", "Motion", "FakeJump", "None"), "Motion");
        this.downSpeed = this.registerDouble("DownSpeed", 0.0, 0.0, 0.2);
        this.delay = this.registerInteger("Jump Delay", 2, 1, 10);
        this.rotate = this.registerBoolean("Rotate", false);
        this.jumpEventListener = (Listener<PlayerJumpEvent>)new Listener(event -> {
            if (((String)this.towerMode.getValue()).equalsIgnoreCase("FakeJump")) {
                event.cancel();
            }
        }, new Predicate[0]);
        this.cancelTimer = new Timer();
        this.RotVec = null;
        this.sendListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getPacket() instanceof CPacketPlayer.PositionRotation && (boolean)this.rotate.getValue()) {
                final CPacketPlayer.PositionRotation e = (CPacketPlayer.PositionRotation)event.getPacket();
                if (this.RotVec == null) {
                    return;
                }
                e.pitch = this.RotVec.pitch;
                e.yaw = this.RotVec.yaw;
            }
        }, new Predicate[0]);
        this.moveEventListener = (Listener<PlayerMoveEvent>)new Listener(event -> {
            this.oldSlot = Scaffold.mc.player.inventory.currentItem;
            this.towerPos = new BlockPos(Scaffold.mc.player.posX, Scaffold.mc.player.posY - 1.0, Scaffold.mc.player.posZ);
            this.downPos = new BlockPos(Scaffold.mc.player.posX, Scaffold.mc.player.posY - 2.0, Scaffold.mc.player.posZ);
            if (((String)this.logic.getValue()).equalsIgnoreCase("Predict")) {
                final PredictUtil.PredictSettings predset = new PredictUtil.PredictSettings((int)this.distance.getValue(), false, 0, 0, 0, 0, 0, 0, false, 0, false, false, false, false, false, 0, 696969.0);
                this.predPlayer = PredictUtil.predictPlayer((EntityPlayer)Scaffold.mc.player, predset);
                this.scaffold = new BlockPos(this.predPlayer.posX, this.predPlayer.posY - 1.0, this.predPlayer.posZ);
            }
            else if (((String)this.logic.getValue()).equalsIgnoreCase("Player")) {
                final double[] dir = MotionUtil.forward(MotionUtil.getMotion((EntityPlayer)Scaffold.mc.player) * (int)this.distanceP.getValue());
                this.scaffold = new BlockPos(Scaffold.mc.player.posX + dir[0], Scaffold.mc.player.posY, Scaffold.mc.player.posZ + dir[1]).down();
            }
            this.newSlot = -1;
            if (!Block.getBlockFromItem(Scaffold.mc.player.getHeldItemMainhand().item).getDefaultState().isFullBlock()) {
                for (int i = 0; i < 9; ++i) {
                    final ItemStack stack = Scaffold.mc.player.inventory.getStackInSlot(i);
                    if (stack != ItemStack.EMPTY) {
                        if (stack.getItem() instanceof ItemBlock) {
                            if (Block.getBlockFromItem(stack.getItem()).getDefaultState().isFullBlock()) {
                                if (!(((ItemBlock)stack.getItem()).getBlock() instanceof BlockFalling) || !Scaffold.mc.world.getBlockState(this.scaffold).getMaterial().isReplaceable()) {
                                    this.newSlot = i;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            else {
                this.newSlot = Scaffold.mc.player.inventory.currentItem;
            }
            if (this.newSlot == -1) {
                return;
            }
            final String s = (String)this.towerMode.getValue();
            switch (s) {
                case "Jump": {
                    if (Scaffold.mc.player.onGround) {
                        this.oldTower = Scaffold.mc.player.posY;
                        Scaffold.mc.player.jump();
                    }
                    if (Math.floor(Scaffold.mc.player.posY) == this.oldTower + 1.0 && !Scaffold.mc.player.onGround) {
                        Scaffold.mc.player.motionY = -(Scaffold.mc.player.posY - Math.floor(Scaffold.mc.player.posY));
                    }
                    this.placeBlockPacket(this.towerPos, false);
                    break;
                }
                case "FakeJump": {
                    if (Scaffold.mc.player.ticksExisted % (int)this.delay.getValue() == 0 && Scaffold.mc.player.onGround && Scaffold.mc.gameSettings.keyBindJump.isKeyDown()) {
                        PlayerUtil.fakeJump(3);
                        Scaffold.mc.player.setPosition(Scaffold.mc.player.posX, Scaffold.mc.player.posY + 1.0013359791121, Scaffold.mc.player.posZ);
                        this.placeBlockPacket(this.towerPos, false);
                        break;
                    }
                    break;
                }
            }
            if (Scaffold.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.placeBlockPacket(this.towerPos, false);
            }
            if (!Scaffold.mc.gameSettings.keyBindJump.isKeyDown() && !Scaffold.mc.gameSettings.keyBindSprint.isKeyDown()) {
                this.placeBlockPacket(this.scaffold, true);
            }
            final double[] dir = MotionUtil.forward((double)this.downSpeed.getValue());
            if (Scaffold.mc.gameSettings.keyBindSprint.isKeyDown()) {
                this.placeBlockPacket(this.downPos, false);
                Scaffold.mc.player.motionX = dir[0];
                Scaffold.mc.player.motionZ = dir[1];
            }
        }, new Predicate[0]);
    }
    
    protected void onEnable() {
        this.timer = 0;
    }
    
    public void onUpdate() {
        if (Scaffold.mc.player.onGround) {
            this.timer = 0;
        }
        else {
            ++this.timer;
        }
        if (this.timer == (int)this.delay.getValue() && Scaffold.mc.gameSettings.keyBindJump.isKeyDown() && !this.cancelTimer.hasReached(1200L, true)) {
            Scaffold.mc.player.jump();
            this.timer = 0;
        }
    }
    
    boolean placeBlockPacket(final BlockPos pos, final boolean allowSupport) {
        final EntityPlayerSP player = Scaffold.mc.player;
        player.rotationYaw += (float)((Scaffold.mc.player.ticksExisted % 2 == 0) ? 1.0E-5 : -1.0E-5);
        final boolean shouldplace = Scaffold.mc.world.getBlockState(pos).getBlock().isReplaceable((IBlockAccess)Scaffold.mc.world, pos) && BlockUtil.getPlaceableSide(pos) != null;
        this.rotateTo = pos;
        if (shouldplace) {
            final boolean swap = this.newSlot != Scaffold.mc.player.inventory.currentItem;
            if (swap) {
                Scaffold.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.newSlot));
                Scaffold.mc.player.inventory.currentItem = this.newSlot;
            }
            this.RotVec = PlacementUtil.placeBlockGetRotate(pos, EnumHand.MAIN_HAND, false, (ArrayList)null, false);
            if (swap) {
                Scaffold.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldSlot));
                Scaffold.mc.player.inventory.currentItem = this.oldSlot;
            }
            return this.RotVec != null;
        }
        if (allowSupport && BlockUtil.getPlaceableSide(pos) == null) {
            this.clutch();
        }
        return false;
    }
    
    public void clutch() {
        final BlockPos xpPos = new BlockPos(Scaffold.mc.player.posX + 1.0, Scaffold.mc.player.posY - 1.0, Scaffold.mc.player.posZ);
        final BlockPos xmPos = new BlockPos(Scaffold.mc.player.posX - 1.0, Scaffold.mc.player.posY - 1.0, Scaffold.mc.player.posZ);
        final BlockPos zpPos = new BlockPos(Scaffold.mc.player.posX, Scaffold.mc.player.posY - 1.0, Scaffold.mc.player.posZ + 1.0);
        final BlockPos zmPos = new BlockPos(Scaffold.mc.player.posX, Scaffold.mc.player.posY - 1.0, Scaffold.mc.player.posZ - 1.0);
        if (!this.placeBlockPacket(xpPos, false) && !this.placeBlockPacket(xmPos, false) && !this.placeBlockPacket(zpPos, false)) {
            this.placeBlockPacket(zmPos, false);
        }
    }
}
