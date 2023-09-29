//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.world.*;
import com.lemonclient.api.util.player.*;

@Module.Declaration(name = "AutoCenter", category = Category.Combat)
public class AutoCenter extends Module
{
    BooleanSetting once;
    
    public AutoCenter() {
        this.once = this.registerBoolean("Once", true);
    }
    
    public void onUpdate() {
        if (AutoCenter.mc.world == null || AutoCenter.mc.player == null || AutoCenter.mc.player.isDead) {
            this.disable();
            return;
        }
        PlayerUtil.centerPlayer(BlockUtil.getCenterOfBlock(AutoCenter.mc.player.posX, AutoCenter.mc.player.posY, AutoCenter.mc.player.posZ));
        if (this.once.getValue()) {
            this.disable();
        }
    }
}
