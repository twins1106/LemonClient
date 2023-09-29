//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.util.player.*;
import com.lemonclient.api.util.world.*;
import com.lemonclient.api.util.misc.*;

@Module.Declaration(name = "HoleDetect", category = Category.Dev)
public class HoleDetect extends Module
{
    public static boolean inhole;
    public static boolean lastinhole;
    
    public void onEnable() {
        HoleDetect.inhole = (HoleDetect.lastinhole = false);
    }
    
    public void onUpdate() {
        final HoleUtil.HoleInfo holeInfo = HoleUtil.isHole(PlayerUtil.getPlayerPos(), false, true, false);
        final HoleUtil.HoleType holeType = holeInfo.getType();
        HoleDetect.inhole = (holeType != HoleUtil.HoleType.NONE);
        if (!HoleDetect.inhole && HoleDetect.lastinhole) {
            MessageBus.sendClientDeleteMessage("¡×cYou are now not in a hole!", "holedetect", 6);
            HoleDetect.lastinhole = false;
        }
        if (HoleDetect.inhole && !HoleDetect.lastinhole) {
            MessageBus.sendClientDeleteMessage("¡×aYou are now in a hole!", "holedetect", 6);
            HoleDetect.lastinhole = true;
        }
    }
}
