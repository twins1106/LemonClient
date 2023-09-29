//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import net.minecraft.client.entity.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import com.lemonclient.client.module.modules.dev.*;
import com.lemonclient.client.module.*;
import com.lemonclient.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.lemonclient.client.module.modules.movement.*;
import org.spongepowered.asm.mixin.injection.*;
import com.lemonclient.client.manager.managers.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.event.events.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import com.lemonclient.client.module.modules.exploits.*;

@Mixin({ EntityPlayerSP.class })
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer
{
    @Shadow
    @Final
    public NetHandlerPlayClient connection;
    @Shadow
    protected Minecraft mc;
    @Shadow
    private boolean prevOnGround;
    @Shadow
    private float lastReportedYaw;
    @Shadow
    private float lastReportedPitch;
    @Shadow
    private int positionUpdateTicks;
    @Shadow
    private double lastReportedPosX;
    @Shadow
    private double lastReportedPosY;
    @Shadow
    private double lastReportedPosZ;
    @Shadow
    private boolean autoJumpEnabled;
    @Shadow
    private boolean serverSprintState;
    @Shadow
    private boolean serverSneakState;
    
    public MixinEntityPlayerSP() {
        super((World)Minecraft.getMinecraft().world, Minecraft.getMinecraft().session.getProfile());
    }
    
    @Shadow
    protected abstract boolean isCurrentViewEntity();
    
    @Inject(method = { "pushOutOfBlocks" }, at = { @At("HEAD") }, cancellable = true)
    public void pushOutOfBlocks(final double x, final double y, final double z, final CallbackInfoReturnable<Boolean> cir) {
        if (ModuleManager.isModuleEnabled((Class)AntiPush.class)) {
            cir.cancel();
        }
    }
    
    @Inject(method = { "isHandActive" }, at = { @At("HEAD") }, cancellable = true)
    public void isHandActive(final CallbackInfoReturnable<Boolean> info) {
        final EventPlayerIsHandActive event = new EventPlayerIsHandActive();
        LemonClient.EVENT_BUS.post((Object)event);
        if (event.isCancelled()) {
            info.cancel();
            info.setReturnValue((Object)false);
        }
    }
    
    @Inject(method = { "Lnet/minecraft/client/entity/EntityPlayerSP;setServerBrand(Ljava/lang/String;)V" }, at = { @At("HEAD") })
    public void getBrand(final String serverBrand, final CallbackInfo callbackInfo) {
        if (LemonClient.serverUtil != null) {
            LemonClient.serverUtil.setServerBrand(serverBrand);
        }
    }
    
    @Redirect(method = { "move" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    public void move(final AbstractClientPlayer player, final MoverType type, final double x, final double y, final double z) {
        final PlayerMoveEvent moveEvent = new PlayerMoveEvent(type, x, y, z);
        LemonClient.EVENT_BUS.post((Object)moveEvent);
        super.move(type, moveEvent.getX(), moveEvent.getY(), moveEvent.getZ());
    }
    
    @ModifyArg(method = { "setSprinting" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;setSprinting(Z)V"), index = 0)
    public boolean modifySprinting(final boolean sprinting) {
        final EntityPlayerSP player = Minecraft.getMinecraft().player;
        final Sprint sprint = (Sprint)ModuleManager.getModule((Class)Sprint.class);
        return (player != null && sprint.isEnabled() && sprint.shouldSprint(player)) || sprinting;
    }
    
    @Inject(method = { "onUpdate" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;onUpdateWalkingPlayer()V", shift = At.Shift.AFTER) })
    private void onUpdateInvokeOnUpdateWalkingPlayer(final CallbackInfo ci) {
        final Vec3d serverSidePos = PlayerPacketManager.INSTANCE.getServerSidePosition();
        final float serverSideRotationX = PlayerPacketManager.INSTANCE.getServerSideRotation().x;
        final float serverSideRotationY = PlayerPacketManager.INSTANCE.getServerSideRotation().y;
        this.lastReportedPosX = serverSidePos.x;
        this.lastReportedPosY = serverSidePos.y;
        this.lastReportedPosZ = serverSidePos.z;
        this.lastReportedYaw = serverSideRotationX;
        this.lastReportedPitch = serverSideRotationY;
        this.rotationYawHead = serverSideRotationX;
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") }, cancellable = true)
    public void onUpdateWalkingPlayerPre(final CallbackInfo callbackInfo) {
        Vec3d position = new Vec3d(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        Vec2f rotation = new Vec2f(this.rotationYaw, this.rotationPitch);
        OnUpdateWalkingPlayerEvent event = new OnUpdateWalkingPlayerEvent(position, rotation);
        LemonClient.EVENT_BUS.post((Object)event);
        event = event.nextPhase();
        LemonClient.EVENT_BUS.post((Object)event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
            final boolean moving = event.isMoving() || this.isMoving(position);
            final boolean rotating = event.isRotating() || this.isRotating(rotation);
            position = event.getPosition();
            rotation = event.getRotation();
            ++this.positionUpdateTicks;
            this.sendSprintPacket();
            this.sendSneakPacket();
            this.sendPlayerPacket(moving, rotating, position, rotation);
        }
        event = event.nextPhase();
        LemonClient.EVENT_BUS.post((Object)event);
    }
    
    private void sendSprintPacket() {
        final boolean sprinting = this.isSprinting();
        if (sprinting != this.serverSprintState) {
            if (sprinting) {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            this.serverSprintState = sprinting;
        }
    }
    
    private void sendSneakPacket() {
        final boolean sneaking = this.isSneaking();
        if (sneaking != this.serverSneakState) {
            if (sneaking) {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.serverSneakState = sneaking;
        }
    }
    
    public void sendPlayerPacket(boolean moving, final boolean rotating, final Vec3d position, final Vec2f rotation) {
        if (!this.isCurrentViewEntity()) {
            return;
        }
        if (this.isRiding()) {
            this.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.motionX, -999.0, this.motionZ, rotation.x, rotation.y, this.onGround));
            moving = false;
        }
        else if (moving && rotating) {
            this.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(position.x, position.y, position.z, rotation.x, rotation.y, this.onGround));
        }
        else if (moving) {
            this.connection.sendPacket((Packet)new CPacketPlayer.Position(position.x, position.y, position.z, this.onGround));
        }
        else if (rotating) {
            this.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotation.x, rotation.y, this.onGround));
        }
        else if (this.prevOnGround != this.onGround) {
            this.connection.sendPacket((Packet)new CPacketPlayer(this.onGround));
        }
        if (moving) {
            this.lastReportedPosX = position.x;
            this.lastReportedPosY = position.y;
            this.lastReportedPosZ = position.z;
            this.positionUpdateTicks = 0;
        }
        if (rotating) {
            this.lastReportedYaw = rotation.x;
            this.lastReportedPitch = rotation.y;
        }
        this.prevOnGround = this.onGround;
        this.autoJumpEnabled = this.mc.gameSettings.autoJump;
    }
    
    private boolean isMoving(final Vec3d position) {
        final double xDiff = position.x - this.lastReportedPosX;
        final double yDiff = position.y - this.lastReportedPosY;
        final double zDiff = position.z - this.lastReportedPosZ;
        return xDiff * xDiff + yDiff * yDiff + zDiff * zDiff > 9.0E-4 || this.positionUpdateTicks >= 20;
    }
    
    private boolean isRotating(final Vec2f rotation) {
        final double yawDiff = rotation.x - this.lastReportedYaw;
        final double pitchDiff = rotation.y - this.lastReportedPitch;
        return yawDiff != 0.0 || pitchDiff != 0.0;
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V"))
    public void closeScreenHook(final EntityPlayerSP entityPlayerSP) {
        final Portal portal = (Portal)ModuleManager.getModule((Class)Portal.class);
        if (!portal.isEnabled() || !(boolean)portal.chat.getValue()) {
            entityPlayerSP.closeScreen();
        }
    }
}
