//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import com.lemonclient.api.event.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.misc.*;
import com.lemonclient.api.util.player.*;

public class OnUpdateWalkingPlayerEvent extends LemonClientEvent implements MultiPhase<OnUpdateWalkingPlayerEvent>
{
    private final Phase phase;
    private boolean moving;
    private boolean rotating;
    private Vec3d position;
    private Vec2f rotation;
    
    public OnUpdateWalkingPlayerEvent(final Vec3d position, final Vec2f rotation) {
        this(position, rotation, Phase.PRE);
    }
    
    private OnUpdateWalkingPlayerEvent(final Vec3d position, final Vec2f rotation, final Phase phase) {
        this.moving = false;
        this.rotating = false;
        this.position = position;
        this.rotation = rotation;
        this.phase = phase;
    }
    
    @Override
    public OnUpdateWalkingPlayerEvent nextPhase() {
        return new OnUpdateWalkingPlayerEvent(this.position, this.rotation, EnumUtils.next(this.phase));
    }
    
    public void apply(final PlayerPacket packet) {
        final Vec3d position = packet.getPosition();
        final Vec2f rotation = packet.getRotation();
        if (position != null) {
            this.moving = true;
            this.position = position;
        }
        if (rotation != null) {
            this.rotating = true;
            this.rotation = rotation;
        }
    }
    
    public boolean isMoving() {
        return this.moving;
    }
    
    public boolean isRotating() {
        return this.rotating;
    }
    
    public Vec3d getPosition() {
        return this.position;
    }
    
    public Vec2f getRotation() {
        return this.rotation;
    }
    
    @Override
    public Phase getPhase() {
        return this.phase;
    }
}
