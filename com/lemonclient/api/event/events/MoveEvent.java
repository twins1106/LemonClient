//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event.events;

import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class MoveEvent extends Event
{
    private double motionX;
    private double motionY;
    private double motionZ;
    
    public MoveEvent(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
    
    public double getMotionX() {
        return this.motionX;
    }
    
    public double getMotionY() {
        return this.motionY;
    }
    
    public double getMotionZ() {
        return this.motionZ;
    }
    
    public void setMotionX(final double motionX) {
        this.motionX = motionX;
    }
    
    public void setMotionY(final double motionY) {
        this.motionY = motionY;
    }
    
    public void setMotionZ(final double motionZ) {
        this.motionZ = motionZ;
    }
}
