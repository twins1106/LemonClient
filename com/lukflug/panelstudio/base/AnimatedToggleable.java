//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.base;

import java.util.function.*;

public final class AnimatedToggleable implements IToggleable
{
    private final IToggleable toggle;
    private final Animation animation;
    
    public AnimatedToggleable(final IToggleable toggle, final Animation animation) {
        if (toggle != null) {
            this.toggle = toggle;
        }
        else {
            this.toggle = new SimpleToggleable(false);
        }
        if (animation != null) {
            this.animation = animation;
        }
        else {
            this.animation = new Animation(System::currentTimeMillis) {
                @Override
                protected int getSpeed() {
                    return 0;
                }
            };
        }
        if (this.toggle.isOn()) {
            this.animation.initValue(1.0);
        }
        else {
            this.animation.initValue(0.0);
        }
    }
    
    @Override
    public void toggle() {
        this.toggle.toggle();
        if (this.toggle.isOn()) {
            this.animation.setValue(1.0);
        }
        else {
            this.animation.setValue(0.0);
        }
    }
    
    @Override
    public boolean isOn() {
        return this.toggle.isOn();
    }
    
    public double getValue() {
        if (this.animation.getTarget() != (this.toggle.isOn() ? 1 : 0)) {
            if (this.toggle.isOn()) {
                this.animation.setValue(1.0);
            }
            else {
                this.animation.setValue(0.0);
            }
        }
        return this.animation.getValue();
    }
}
