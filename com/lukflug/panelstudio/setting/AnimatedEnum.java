//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.setting;

import com.lukflug.panelstudio.base.*;

public final class AnimatedEnum
{
    private final IEnumSetting setting;
    private final Animation animation;
    
    public AnimatedEnum(final IEnumSetting setting, final Animation animation) {
        this.setting = setting;
        this.animation = animation;
    }
    
    public double getValue() {
        final int index = this.setting.getValueIndex();
        if (this.animation.getTarget() != index) {
            this.animation.setValue((double)index);
        }
        return this.animation.getValue();
    }
}
