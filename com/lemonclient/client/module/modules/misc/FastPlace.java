//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.init.*;

@Module.Declaration(name = "FastPlace", category = Category.Misc)
public class FastPlace extends Module
{
    BooleanSetting exp;
    BooleanSetting crystals;
    BooleanSetting offhandCrystal;
    BooleanSetting everything;
    
    public FastPlace() {
        this.exp = this.registerBoolean("Exp", false);
        this.crystals = this.registerBoolean("Crystals", false);
        this.offhandCrystal = this.registerBoolean("Offhand Crystal", false);
        this.everything = this.registerBoolean("Everything", false);
    }
    
    public void onUpdate() {
        if (((boolean)this.exp.getValue() && FastPlace.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE) || FastPlace.mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) {
            FastPlace.mc.rightClickDelayTimer = 0;
        }
        if ((boolean)this.crystals.getValue() && FastPlace.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
            FastPlace.mc.rightClickDelayTimer = 0;
        }
        if ((boolean)this.offhandCrystal.getValue() && FastPlace.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            FastPlace.mc.rightClickDelayTimer = 0;
        }
        if (this.everything.getValue()) {
            FastPlace.mc.rightClickDelayTimer = 0;
        }
        FastPlace.mc.playerController.blockHitDelay = 0;
    }
}
