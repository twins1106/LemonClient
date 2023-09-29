//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import org.lwjgl.opengl.*;
import com.lemonclient.client.*;

@Module.Declaration(name = "Window", category = Category.HUD)
public class Window extends Module
{
    BooleanSetting loop;
    IntegerSetting delay;
    Timing timing;
    
    public Window() {
        this.loop = this.registerBoolean("Loop", false);
        this.delay = this.registerInteger("Loop Delay(Tick)", 2, 0, 40);
        this.timing = new Timing();
    }
    
    public void onUpdate() {
        if (!(boolean)this.loop.getValue()) {
            Display.setTitle("Lemon Client v0.0.6");
            LemonClient.setWindowIcon();
            this.disable();
        }
        else if (this.timing.passedMs((long)((int)this.delay.getValue() * 50))) {
            Display.setTitle("Lemon Client v0.0.6");
            LemonClient.setWindowIcon();
            this.timing.reset();
        }
    }
}
