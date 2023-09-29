//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.util.world.*;
import com.lemonclient.client.module.*;

@Module.Declaration(name = "HoleDefence", category = Category.Combat)
public class HoleDefence extends Module
{
    BooleanSetting surround;
    BooleanSetting anticity;
    
    public HoleDefence() {
        this.surround = this.registerBoolean("Disable Surround", true);
        this.anticity = this.registerBoolean("Enable AntiCity", true);
    }
    
    public void onUpdate() {
        if (HoleDefence.mc.world == null || HoleDefence.mc.player == null || HoleDefence.mc.player.isDead) {
            return;
        }
        BlockPos playerPos = PlayerUtil.getPlayerPos();
        playerPos = new BlockPos((double)playerPos.x, playerPos.y + 0.55, (double)playerPos.z);
        final HoleUtil.HoleInfo holeInfo = HoleUtil.isHole(playerPos, false, false, false);
        final HoleUtil.HoleType holeType = holeInfo.getType();
        if (HoleDefence.mc.player.onGround && holeType != HoleUtil.HoleType.NONE) {
            if (this.surround.getValue()) {
                ((Surround)ModuleManager.getModule((Class)Surround.class)).disable();
            }
            if (this.anticity.getValue()) {
                ((SmartAntiCity)ModuleManager.getModule((Class)SmartAntiCity.class)).enable();
            }
        }
    }
}
