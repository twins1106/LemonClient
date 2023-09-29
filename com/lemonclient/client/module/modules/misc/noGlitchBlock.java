//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;

@Module.Declaration(name = "NoGlitchBlock", category = Category.Misc)
public class noGlitchBlock extends Module
{
    public BooleanSetting breakBlock;
    public BooleanSetting placeBlock;
    
    public noGlitchBlock() {
        this.breakBlock = this.registerBoolean("Break", true);
        this.placeBlock = this.registerBoolean("Place", true);
    }
}
