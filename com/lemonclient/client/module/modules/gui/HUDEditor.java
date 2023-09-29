//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.gui;

import com.lemonclient.client.module.*;
import com.lemonclient.client.*;

@Module.Declaration(name = "HudEditor", category = Category.GUI, drawn = false)
public class HUDEditor extends Module
{
    public void onEnable() {
        LemonClient.INSTANCE.gameSenseGUI.enterHUDEditor();
        this.disable();
    }
}
