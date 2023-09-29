//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import java.util.*;

@Module.Declaration(name = "AntiFog", category = Category.Render)
public class AntiFog extends Module
{
    public static String type;
    ModeSetting mode;
    
    public AntiFog() {
        this.mode = this.registerMode("Mode", (List)Arrays.asList("NoFog", "Air"), "NoFog");
    }
    
    public void onUpdate() {
        AntiFog.type = (String)this.mode.getValue();
    }
}
