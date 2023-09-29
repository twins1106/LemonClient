//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.player;

import net.minecraft.util.math.*;
import com.lemonclient.client.module.*;

public class PlayerPacket
{
    private final int priority;
    private final Vec3d position;
    private final Vec2f rotation;
    
    public PlayerPacket(final Module module, final Vec2f rotation) {
        this(module, null, rotation);
    }
    
    public PlayerPacket(final Module module, final Vec3d position) {
        this(module, position, null);
    }
    
    public PlayerPacket(final Module module, final Vec3d position, final Vec2f rotation) {
        this(module.getPriority(), position, rotation);
    }
    
    private PlayerPacket(final int priority, final Vec3d position, final Vec2f rotation) {
        this.priority = priority;
        this.position = position;
        this.rotation = rotation;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public Vec3d getPosition() {
        return this.position;
    }
    
    public Vec2f getRotation() {
        return this.rotation;
    }
}
