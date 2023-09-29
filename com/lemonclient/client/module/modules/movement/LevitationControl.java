//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;

@Module.Declaration(name = "LevitationControl", category = Category.Movement)
public class LevitationControl extends Module
{
    DoubleSetting upAmplifier;
    DoubleSetting downAmplifier;
    
    public LevitationControl() {
        this.upAmplifier = this.registerDouble("Amplifier Up", 1.0, 1.0, 3.0);
        this.downAmplifier = this.registerDouble("Amplifier Down", 1.0, 1.0, 3.0);
    }
    
    public void onUpdate() {
        if (LevitationControl.mc.player.isPotionActive(MobEffects.LEVITATION)) {
            final int amplifier = Objects.requireNonNull(LevitationControl.mc.player.getActivePotionEffect((Potion)Objects.requireNonNull(Potion.getPotionById(25)))).getAmplifier();
            if (LevitationControl.mc.gameSettings.keyBindJump.isKeyDown()) {
                LevitationControl.mc.player.motionY = (0.05 * (amplifier + 1) - LevitationControl.mc.player.motionY) * 0.2 * (double)this.upAmplifier.getValue();
            }
            else if (LevitationControl.mc.gameSettings.keyBindSneak.isKeyDown()) {
                LevitationControl.mc.player.motionY = -((0.05 * (amplifier + 1) - LevitationControl.mc.player.motionY) * 0.2 * (double)this.downAmplifier.getValue());
            }
            else {
                LevitationControl.mc.player.motionY = 0.0;
            }
        }
    }
}
