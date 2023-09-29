//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.player.*;

@Module.Declaration(name = "BoundsMove", category = Category.Movement)
public class BoundsMove extends Module
{
    ModeSetting bound;
    
    public BoundsMove() {
        this.bound = this.registerMode("Bounds", PhaseUtil.bound, PhaseUtil.normal);
    }
    
    public void onUpdate() {
        if (BoundsMove.mc.player.moveForward != 0.0f || BoundsMove.mc.player.moveStrafing != 0.0f) {
            PhaseUtil.doBounds((String)this.bound.getValue(), true);
        }
    }
}
